////////////////////////////////////////////////////////////////////////////////
// Main File:        cache2Dcols.c
// This File:        cache2Dcols.c
// Other Files:      cache1D.c, cache2Drows.c, cache2Dclash.c
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

/* Global variable - 2-D array of integers with 3000 rows x 500 columns.
 */
int arr2D[3000][500];

/* traverse the array in  column-wise order. As the array is traversed, 
 * set each element of the array to the sum of its row and col indexes
 */
int main() {
    for (int col=0; col<500; col++) {
        for (int row=0; row<3000; row++) {
            arr2D[row][col] = row + col;
        }
    }

    return 0;
}

// end of the cache2Dcols.c
