////////////////////////////////////////////////////////////////////////////////
// Main File:        division.c
// This File:        division.c
// Other Files:      N.A.
// Semester:         CS 354 Fall 2022
// Instructor:       deppeler
//
// Author:           Haoting Tan
// CS Login:         haoting
//
/////////////////////////// OTHER SOURCES OF HELP //////////////////////////////
//                   Fully acknowledge and credit all sources of help,
//                   including Peer Mentors, Instructors, and TAs.
//
// Persons:          N.A
//
// Online sources:   N.A.
////////////////////////////////////////////////////////////////////////////////
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <string.h>

/* Global variable - number of count for successfully completed division operations.
 */
int count_suc_op = 0;

/* handler to handle when the program receive SIGFPE singals.
 * prints the number of operations completed successfully and 
 * exit the program.
 */
void handler_SIGFPE() {
    printf("Error: a division by 0 operation was attempted.\n");
    printf("Total number of operations completed successfully: %d\n", count_suc_op);
    printf("The program will be terminated.\n");
    exit(0);
}

/* handler to handle when the program receive SIGINT singals.
 * prints the number of operations completed successfully and 
 * exit the program.
 */
void handler_SIGINT() {
    printf("\nTotal number of operations completed successfully: %d\n", count_suc_op);
    printf("The program will be terminated.\n");
    exit(0);
}

/* Generates a Program running Infinite loop on asking 
 * user doing division until encounter divided by 0 exception 
 * or interrupt signal.
 */
int main() {

    // Register a signal handler to handle the SIGFPE signal
    struct sigaction act_fpe;
    memset(&act_fpe, 0, sizeof(act_fpe)); // zero out the structure
    act_fpe.sa_handler = handler_SIGFPE;
    if (sigaction(SIGFPE, &act_fpe, NULL) != 0) {
        printf("Error: binding SIGFPE Handler\n");
        exit(1);
    }

    // Register a signal handler to handle the SIGINT signal
    struct sigaction act_int;
    memset(&act_int, 0, sizeof(act_int));
    act_int.sa_handler = handler_SIGINT;
    if (sigaction(SIGINT, &act_int, NULL) != 0) {
    	printf("Error: binding SIGINT Handler\n");
    	exit(1);
    }

    while (1) {
        char user_input1[100];
        char user_input2[100];

        // Prompt for and read in one integer value.
        printf("Enter first integer: ");
        if (fgets(user_input1, 100, stdin) == NULL) {
            printf("Error reading user input.\n");
            exit(1);
        }

        // Prompt for and read in a second integer value.
        printf("Enter second integer: ");
        if (fgets(user_input2, 100, stdin) == NULL) {
            printf("Error reading user input.\n");
            exit(1);
        }

        // converts the user input number in string format to integer
        int first_num = atoi(user_input1);
        int second_num = atoi(user_input2);
    
        // prints out the result
        printf("%d / %d is %d with a remainder of %d\n", first_num, 
        second_num, first_num/second_num, first_num%second_num);

        // increment the number of operations completed successfully
        count_suc_op += 1;
    }

    return 0;
}
