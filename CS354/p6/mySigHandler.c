////////////////////////////////////////////////////////////////////////////////
// Main File:        mySigHandler.c
// This File:        mySigHandler.c
// Other Files:      sendsig.c
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
#include <string.h>
#include <signal.h>
#include <unistd.h>
#include <time.h>

/* Global variable - constant alarm duration - 4 seconds.
 */
const int ALARM_SECONDS = 4;

/* Global variable - number of count for handling SIGUER1 singals.
 */
int count_SIGUSER1 = 0;

/* handler to handle when the program receive SIGALRM singals.
 * it will prints out the current program PID and local time
 * and re-arm a new alarm to go off again 4 seconds later.
 */
void handler_SIGALRM(){
    time_t t;  
    if (time(&t) == -1) {
	printf("Error: time() library function return error(-1)\n");
	exit(1);
    }

    if (ctime(&t) != NULL) {
   	printf("PID: %i CURRENT TIME: %s", getpid(), ctime(&t));
    } else {
	printf("Error: ctime() library function return NULL\n");
    	exit(1);
    }

    alarm(ALARM_SECONDS);
}

/* handler to handle when the program receive SIGUSER1 singals.
 * it will prints out "SIGUSR1 handled and counted!"
 * and increament the count of receiving SIGUSR1 singal
 */
void handler_SIGUER1() {
    printf("SIGUSR1 handled and counted!\n");
    count_SIGUSER1 += 1;
}

/* handler to handle when the program receive SIGINT singals.
 * it will prints out message below and exit the process
 */
void handler_SIGINT() {
    printf("\nSIGINT handled.\n");
    printf("SIGUSR1 was handled %d times. Exiting now.\n", count_SIGUSER1);
    exit(0);
}

/* Generates a Program running Infinite loop and print the
 * PID and local time every 4 seconds. It will print the message 
 * when the program receive SIGUSER1 and SIGINT.  
 */
int main() {

    // Register a signal handler to handle the SIGALRM signal
    struct sigaction act_alrm;
    memset(&act_alrm, 0, sizeof(act_alrm)); // zero out the structure
    act_alrm.sa_handler = handler_SIGALRM;
    if (sigaction(SIGALRM, &act_alrm, NULL) != 0) {
        printf("Error: binding SIGALRM Handler\n");
    	exit(1);
    }

    // Register a signal handler to handle the SIGUSER1 signal
    struct sigaction act_user1;
    memset(&act_user1, 0, sizeof(act_user1));
    act_user1.sa_handler = handler_SIGUER1;
    if (sigaction(SIGUSR1, &act_user1, NULL) != 0) {
        printf("Error: binding SIGUSER1 Handler\n");
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

    // Set up an alarm that will go off 4 seconds later
    alarm(ALARM_SECONDS);

    // prompt the user on what is the program doing
    printf("PID and time print every 4 seconds.\n");
    printf("Type Ctrl-C to end the program.\n");

    // Infinite loop without any output
    while (1) { 
    }

    return 0;
}
