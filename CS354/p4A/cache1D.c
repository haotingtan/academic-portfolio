////////////////////////////////////////////////////////////////////////////////
// Main File:        cache1D.c
// This File:        cache1D.c
// Other Files:      cache2Drows.c, cache2Dcols.c, cache2Dclash.c
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

/* Global variable - array of integers of size 100,000
 */
int arr[100000];

/* iterate over the entire array and set the 
 * value of each element in the array to its index
 */
int main() {
    for (int i=0; i<100000; i++) {
	    arr[i] = i;
    }

    return 0;
}

// end of the cache1D.c
