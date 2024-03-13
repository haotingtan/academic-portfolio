#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*
 * main:
 * This tool takes a fortune database file, and either a fortune number or a batch file of 
 * fortune numbers, and outputs the correct fortunes to either the file specified on the 
 * command line or STDOUT.  
 * 
 * Author: Haoitng Tan
 * There is a single README.md describing the implementation
 */      
int main(int argc, char* argv[]) {
    
    // name of the fortune file
    char* fortune = NULL; 
    // name of the batch file
    char* batch = NULL;
    // indicate if it is number mode or batch mode: 1 for number mode, 0 for batch mode
    char num_mode = 0;
    // user number under number mode
    int user_num;
    // name of the output file
    char* output = NULL;
    // quotes number in the fortune ile
    int fortune_num;
    // maximum length among the quotes provided
    int max_length;

    if (argc < 5) {
        printf("USAGE: \n\tbadger-fortune -f <file> -n <number> (optionally: -o <output file>) \n\t\t OR \n\tbadger-fortune -f <file> -b <batch file> (optionally: -o <output file>)\n");
        exit(1);
    }

    for (int i=1; i<argc; i++) {
        // file Specified
        if (strcmp(argv[i], "-f") == 0) {
            if (i == (argc - 1)) {
                printf("ERROR: No fortune file was provided\n");
                exit(1);
            } 
            fortune = argv[i+1];
            i++;

        // batch mode Specified
        } else if (strcmp(argv[i], "-b") == 0) {
            if (num_mode == 1) {
                printf("ERROR: You can't use batch mode when specifying a fortune number using -n\n");
                exit(1);
            }
            batch = argv[i+1];
            i++;

        // number mode Specified
        } else if (strcmp(argv[i], "-n") == 0) {
            if (batch != NULL) {
                printf("ERROR: You can't specify a specific fortune number in conjunction with batch mode\n");
                exit(1);
            }
            num_mode = 1;
            user_num = atoi(argv[i+1]);
            i++;
            
        // output to a file Specified
        } else if (strcmp(argv[i], "-o") == 0) {
            output = argv[i+1];
            i++;
            
        } else {
            printf("ERROR: Invalid Flag Types\n");
            exit(1);
        }
    }

    if (fortune == NULL) {
        printf("ERROR: No fortune file was provided\n");
        exit(1);
    } 

    // open the fortune file
    FILE *fortune_file = fopen(fortune, "r");  
    if (fortune_file == NULL) { 
        printf("ERROR: Can't open fortune file\n"); 
        exit(1); 
    } else {
        // check if the fortune file is empty
        fseek(fortune_file, 0, SEEK_END);
        int size = ftell(fortune_file);
        if (0 == size) {
            printf("ERROR: Fortune File Empty\n");
            fclose(fortune_file);
            exit(1);
        }
    }

    fortune_file = fopen(fortune, "r");
    char filetext[10];
    // reading the first line of the fortune file, which is the number of fortunes
    fgets(filetext, 10, fortune_file);
    fortune_num = atoi(filetext);
    // reading the second line of the fortune file, which is the maximum length of the fortunes
    fgets(filetext, 10, fortune_file);
    max_length = atoi(filetext);

    // dynamiclly allocate memory to store the quotes as an array in the heap
    char** fortune_table = malloc(sizeof(char*) * fortune_num);
    if (fortune_table == NULL) {
        fclose(fortune_file);
        exit(1);
    }
    for (int i=0; i<fortune_num; i++) {
        fortune_table[i] = malloc(max_length * sizeof(char));
        if (fortune_table[i] == NULL) {
            for (int j=0; j<i; j++) {
                free(fortune_table[j]);
                fortune_table[j] = NULL;
            }
            free(fortune_table);
            fortune_table = NULL;
            fclose(fortune_file);
            exit(1);
        }
    }

    // the temporary string that stores the the contents of current reading line of the file
    char current_line[max_length];
    int current_index = -1;

    // reading the file and store the corresponded quotes to the index
    while(fgets(current_line, max_length, fortune_file) != NULL) {
        if (strcmp(current_line, "%\n") == 0) {
            current_index += 1;
            continue;
        }
        fortune_table[current_index] = strcat(fortune_table[current_index], current_line);
    }

    FILE *output_file = NULL;
    if (output != NULL) {
        output_file = fopen(output, "w");
        if (output_file == NULL) {
            printf("Can't open output file %s\n", output);
            fclose(fortune_file);
            exit(1);
        }    
    } 

    if (num_mode == 0) {
        FILE *batch_file = fopen(batch, "r");  
        if (batch_file == NULL) { 
            printf("ERROR: Can't open batch file\n"); 
            fclose(fortune_file);
            if (output_file != NULL) {
                fclose(output_file);
            }
            exit(1); 
        } else {
            // check if the batch file is empty or not
            fseek(batch_file, 0, SEEK_END);
            int size = ftell(batch_file);
            if (0 == size) {
                printf("ERROR: Batch File Empty\n");
                fclose(fortune_file);
                fclose(batch_file);
                if (output_file != NULL) {
                    fclose(output_file);
                }
                exit(1);
            }
        }

        // reading the batch file and doing operation
        batch_file = fopen(batch, "r");  
        while(fgets(filetext, 10, batch_file) != NULL) {
            if (atoi(filetext) <= 0) {
                printf("ERROR: Invalid Fortune Number\n\n");
            } else if (atoi(filetext) > fortune_num) {
                printf("ERROR: Invalid Fortune Number\n\n");
            } else {
                if (output_file != NULL) {
                    fputs(fortune_table[atoi(filetext)-1], output_file);
                    fputs("\n\n", output_file);
                } else {
                    printf("%s\n\n", fortune_table[atoi(filetext)-1]);
                }
            }
        }

        fclose(batch_file);

    } else {
        // number mode
        if (user_num <= 0) {
            printf("ERROR: Invalid Fortune Number\n");
            fclose(fortune_file);
            if (output_file != NULL) {
                fclose(output_file);
            }
            exit(1);
        } else if (user_num > fortune_num) {
            printf("ERROR: Invalid Fortune Number\n");
            fclose(fortune_file);
            if (output_file != NULL) {
                fclose(output_file);
            }
            exit(1);
        } else {
            // output to a file
            if (output_file != NULL) {
                fputs(fortune_table[user_num-1], output_file);
            } else {
                // print to the STDOUT
                printf("%s", fortune_table[user_num-1]);
            }
        }
    }

    // closing all opened file(s)
    fclose(fortune_file);
    if (output_file !=  NULL) {
        fclose(output_file);
    }

    // free the allocated memeory
    for (int i=0; i<fortune_num; i++) {
        free(fortune_table[i]);
        fortune_table[i] = NULL;
    }
    free(fortune_table);
    fortune_table = NULL;

    return 0;
}
