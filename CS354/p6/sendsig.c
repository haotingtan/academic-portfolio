////////////////////////////////////////////////////////////////////////////////
// Main File:        sendsig.c
// This File:        sendsig.c
// Other Files:      mySigHandler.c
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
// Persons:          N.A.
//
// Online sources:   N.A.
////////////////////////////////////////////////////////////////////////////////
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>

/* Generates a program to send signals to mySigHandler program
 *
 * requires two command line arguments: the first argument indicates 
 * the type of signal (-i for SIGINT or -u for SIGUSR1) and the second 
 * argument is the pid of the process to which the signal is sent
 */
int main(int argc, char* argv[]) {

    // checks if the user have correct number of command line arguments
    if (argc != 3) {
        printf("Usage: sendsig <signal type> <pid>\n");
        exit(1);
    }

    // check user's action on type of the signal(-i for SIGINT or -u for SIGUSR1)
    char c = *(argv[1]+1);
    switch (c) {
        case 'u':
            if (kill(atoi(argv[2]), SIGUSR1) == -1) {
                printf("Error: kill return -1 when sending SIGUSR1 singal.\n");
                exit(1);
            }
            break;
        case 'i':
            if (kill(atoi(argv[2]), SIGINT) == -1) {
                printf("Error: kill return -1 when sending SIGINT singal.\n");
                exit(1);
            }
            break;
        default:
            printf("Usage: sendsig <signal type> <pid>\n");
            exit(1);
    }

    return 0;
}
