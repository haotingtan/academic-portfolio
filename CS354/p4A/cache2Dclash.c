////////////////////////////////////////////////////////////////////////////////
// Main File:        cache2Dclash.c
// This File:        cache2Dclash.c
// Other Files:      cache1D.c, cache2Drows.c, cache2Dcols.c
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

/* Global variable - 2-D array of integers with 128 rows x 8 columns.
 */
int arr2D[128][8];

/* The innermost loop iterates over the columns of the array. The middle 
 * loop iterates over the rows, incrementing by 64 instead of 1.  The outermost 
 * loop repeats this 100 times. As the array is traversed, set each element of 
 * the array to the sum of the three corresponding indexes
 */
int main() {
    for (int proc=0; proc<100; proc++) {
	for (int row=0; row<128; row += 64) {
	    for (int col=0; col<8; col++) {
                arr2D[row][col] = proc + row + col;
            }
	}
    }

    return 0;
}

// end of the cache2Dclash.c
