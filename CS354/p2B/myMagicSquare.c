///////////////////////////////////////////////////////////////////////////////
// Copyright 2020 Jim Skrentny
// Posting or sharing this file is prohibited, including any changes/additions.
// Used by permission, CS 354 Spring 2022, Deb Deppeler
////////////////////////////////////////////////////////////////////////////////
   
////////////////////////////////////////////////////////////////////////////////
// Main File:        myMagicSquare.c
// This File:        myMagicSquare.c
// Other Files:      N.A.
// Semester:         CS 354 Lecture 002 Fall 2022
// Instructor:       deppeler
// 
// Author:           Haoting Tan
// CS Login:         haoting
//
/////////////////////////// OTHER SOURCES OF HELP //////////////////////////////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          N.A.
//
// Online sources:   N.A.
//////////////////////////// 80 columns wide ///////////////////////////////////

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Structure that represents a magic square
typedef struct {
    int size;           // dimension of the square
    int **magic_square; // pointer to heap allocated magic square
} MagicSquare;

/* Prompts the user for the magic square's size, reads it,
 * checks if it's an odd number >= 3 (if not display the required
 * error message and exit), and returns the valid number.
 */
int getSize() {
    int user_size;
    // prompts user to enter the magic square's size
    printf("Enter magic square's size (odd integer >=3)\n");
    scanf("%i", &user_size);

    // check if the size is odd; if not, exit
    if (user_size % 2 == 0) {
        printf("Magic square size must be odd.\n");
        exit(1);
    }

    // check if the size is larger or equal to 3; if not, exit
    if (user_size < 3) {
        printf("Magic square size must be >= 3.\n");
        exit(1);
    }

    return user_size;   
} 
   
/* Makes a magic square of size n using the alternate 
 * Siamese magic square algorithm from assignment and 
 * returns a pointer to the completed MagicSquare struct.
 *
 * n the number of rows and columns
 */
MagicSquare *generateMagicSquare(int n) {
    // dynamically allocate the matrix array
    int **matrix_array;
    matrix_array = malloc(sizeof(int*) * n);
    // exit if the allocation fails
    if (matrix_array != NULL) {
        for (int i = 0; i < n; i++) {
            *(matrix_array + i) = malloc(sizeof(int) * n);
            // exit if the allocation fails and free all allocated memory
            if (*(matrix_array + i) == NULL) {
                int t = 0;
                while (t < i) {
                    free( *(matrix_array + t) );
                    *(matrix_array + t) = NULL;
                    t = t + 1;
                }
                free(matrix_array);
                matrix_array = NULL;
                printf("encounter error when dynamically allocate memory\n"); 
                exit(1);
            }
        }
    } else {
        printf("encounter error when dynamically allocate memory\n"); 
        exit(1);
    }
    
    // initialize all elements in the array to be 0
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            *(*(matrix_array + i) + j) = 0;
        }
    }

    // create the magic square using the alternate 
    // Siamese magic square algorithm
    int row = n / 2;
    int col = n - 1;
    for (int i = 0; i < n*n; i++) {
        *(*(matrix_array + row) + col) = i + 1;

        // if the next down-right block is already filled, move to 
        // the lefft block of the current block
        if (*(*(matrix_array + ((row + 1) % n)) + ((col + 1) % n)) != 0) {
            col = (col - 1 + n) % n;
        } else {
            row = (row + 1) % n;
            col = (col + 1) % n;
        }
    }

    // declare a pointer to a magic square Structure
    MagicSquare *magic_square = malloc(sizeof(MagicSquare));

    // checks if the allocation is successd or not; if not,
    // free all alllocated memory
    if ( magic_square == NULL) {
        for (int i = 0; i < n; i++) {
            free( *(matrix_array + i) );
            *(matrix_array + i) = NULL;
        }
        free(matrix_array);
        matrix_array = NULL;
        printf("encounter error when dynamically allocate memory\n"); 
        exit(1);
    }

    // Assign size and magic_square to the magic square
    magic_square -> size = n;
    magic_square -> magic_square = matrix_array;

    return magic_square;    
} 

/* Opens a new file (or overwrites the existing file)
 * and writes the square in the specified format.
 *
 * magic_square the magic square to write to a file
 * filename the name of the output file
 */
void fileOutputMagicSquare(MagicSquare *magic_square, char *filename) {
    // open the output file, exit if path can't find
    FILE *out_fp = fopen(filename, "w");
    if (out_fp == NULL) {
        // Free all dynamically allocated memory.
        for (int i = 0; i < magic_square->size; i++) {
            free( *(magic_square->magic_square + i) );
            *(magic_square->magic_square + i) = NULL;
        }
        free(magic_square->magic_square);
        magic_square->magic_square = NULL;
        free(magic_square);
        magic_square = NULL;
        printf("Can't open the output file  %s.\n", filename);
        exit(1);
    }

    // Write the size and the matrix array into the file
    fprintf(out_fp, "%i\n", magic_square->size);
    for (int i = 0; i < magic_square->size; i++) {
        for (int j = 0; j < magic_square->size; j++) {
            fprintf(out_fp, "%i", *(*(magic_square->magic_square + i) + j));
            if ( j < magic_square->size - 1) {
                fprintf(out_fp, ",");
            }
        }
        fprintf(out_fp, "\n");
    }

    // Close the file; or exit(1) if file can't closed
    if (fclose(out_fp) != 0) {
        // Free all dynamically allocated memory.
        for (int i = 0; i < magic_square->size; i++) {
            free( *(magic_square->magic_square + i) );
            *(magic_square->magic_square + i) = NULL;
        }
        free(magic_square->magic_square);
        magic_square->magic_square = NULL;
        free(magic_square);
        magic_square = NULL;
        printf("Error while closing the output file.\n");
        exit(1);
    } 
}

/* Generates a magic square of the user specified size and
 * output the quare to the output filename
 *
 * Add description of required CLAs here
 */
int main(int argc, char **argv) {
    // Check input arguments to get output filename
    if (argc != 2) {
        printf("Usage: ./myMagicSquare <output_filename>\n");
        exit(1);
    }

    // Get magic square's size from user
    int user_size = getSize();

    // Generate the magic square
    MagicSquare *magic_square_ptr = generateMagicSquare(user_size);

    // Output the magic square
    fileOutputMagicSquare(magic_square_ptr, *(argv + 1));

    // Free all dynamically allocated memory.
    for (int i = 0; i < user_size; i++) {
        free( *(magic_square_ptr->magic_square + i) );
        *(magic_square_ptr->magic_square + i) = NULL;
    }
    free(magic_square_ptr->magic_square);
    magic_square_ptr->magic_square = NULL;
    free(magic_square_ptr);
    magic_square_ptr = NULL;

    return 0;
} 


//     F22 deppeler myMagicSquare.c      
