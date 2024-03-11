///////////////////////////////////////////////////////////////////////////////
//
// Copyright 2020-2022 Deb Deppeler based on work by Jim Skrentny
// Posting or sharing this file is prohibited, including any changes/additions.
// Used by permission Fall 2022, CS354-deppeler
//
///////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////
// Main File:        p3Heap.c
// This File:        p3Heap.c
// Other Files:      p3Heap.h
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


#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <stdio.h>
#include <string.h>
#include "p3Heap.h"
 
/*
 * This structure serves as the header for each allocated and free block.
 * It also serves as the footer for each free block but only containing size.
 */
typedef struct blockHeader {           

    int size_status;

    /*
     * Size of the block is always a multiple of 8.
     * Size is stored in all block headers and in free block footers.
     *
     * Status is stored only in headers using the two least significant bits.
     *   Bit0 => least significant bit, last bit
     *   Bit0 == 0 => free block
     *   Bit0 == 1 => allocated block
     *
     *   Bit1 => second last bit 
     *   Bit1 == 0 => previous block is free
     *   Bit1 == 1 => previous block is allocated
     * 
     * End Mark: 
     *  The end of the available memory is indicated using a size_status of 1.
     * 
     * Examples:
     * 
     * 1. Allocated block of size 24 bytes:
     *    Allocated Block Header:
     *      If the previous block is free      p-bit=0 size_status would be 25
     *      If the previous block is allocated p-bit=1 size_status would be 27
     * 
     * 2. Free block of size 24 bytes:
     *    Free Block Header:
     *      If the previous block is free      p-bit=0 size_status would be 24
     *      If the previous block is allocated p-bit=1 size_status would be 26
     *    Free Block Footer:
     *      size_status should be 24
     */
} blockHeader;         

/* Global variable - DO NOT CHANGE. It should always point to the first block,
 * i.e., the block at the lowest address.
 */
blockHeader *heap_start = NULL;     

/* Size of heap allocation padded to round to nearest page size.
 */
int alloc_size;

/*
 * Additional global variables may be added as needed below
 */

 
/* 
 * Function for allocating 'size' bytes of heap memory.
 * Argument size: requested size for the payload
 * Returns address of allocated block (payload) on success.
 * Returns NULL on failure.
 *
 * This function must:
 * - Check size - Return NULL if not positive or if larger than heap space.
 * - Determine block size rounding up to a multiple of 8 
 *   and possibly adding padding as a result.
 *
 * - Use BEST-FIT PLACEMENT POLICY to chose a free block
 *
 * - If the BEST-FIT block that is found is exact size match
 *   - 1. Update all heap blocks as needed for any affected blocks
 *   - 2. Return the address of the allocated block payload
 *
 * - If the BEST-FIT block that is found is large enough to split 
 *   - 1. SPLIT the free block into two valid heap blocks:
 *         1. an allocated block
 *         2. a free block
 *         NOTE: both blocks must meet heap block requirements 
 *       - Update all heap block header(s) and footer(s) 
 *              as needed for any affected blocks.
 *   - 2. Return the address of the allocated block payload
 *
 * - If a BEST-FIT block found is NOT found, return NULL
 *   Return NULL unable to find and allocate block for desired size
 *
 * Note: payload address that is returned is NOT the address of the
 *       block header.  It is the address of the start of the 
 *       available memory for the requesterr.
 *
 * Tips: Be careful with pointer arithmetic and scale factors.
 */
void* balloc(int size) {     
    // check size is valid
    if (size <= 0 || size > alloc_size) {
        return NULL;
    }

    // determine the block size
    int block_size;
    if ((size + 4) % 8 == 0 ) {
        block_size = size + 4;
    } else {
        block_size = (((size + 4) / 8) + 1) * 8;
    }

    // best-fit start from the beginning of the heap space
    blockHeader *current = heap_start;

    // Create variables to keep track the closest size 
    // of the block for BEST-FIT
    int closest = alloc_size + 1;
    blockHeader *closest_b = NULL;

    // Check the space until reach the END MARK
    while (current->size_status != 1) {
        // determine the current heap block size
        int current_size = current->size_status - (current->size_status % 8);

        // Check if the heap block is freed or not
        if (current->size_status % 2 == 0) {

            // Case 1: the BEST-FIT block that is found is exact size match
            if (current_size == block_size) {
                // update a-bit
                current->size_status += 1;

                // update next block p-bit if next block is not the END MARK
                blockHeader *next = (blockHeader*)((void*)current + current_size);
                if (next->size_status != 1) {
                    next->size_status += 2;
                }
                
                // update the size_status of the footer
                blockHeader *footer = (blockHeader*)((void*)next - sizeof(blockHeader));
                footer->size_status = 0;
                
                return ((void*)current + sizeof(blockHeader));
            }

            // Case 2: the BEST-FIT block that is found is large enough
            // change the closest_block only if the current 
            // block is smaller than previous closest_block
            if (current_size > block_size && closest > current_size) {
                closest = current_size;
                closest_b = current;
            }
        }

        // check the next block
        current = (blockHeader*)((void*)current + current_size);
    }

    // return NULL if no heap block fit the requested size
    if (closest_b == NULL) {
        return NULL;
    } else {
        // Do the split if the block is large enough
        if (closest - block_size >= 8) {
            // update the footer
            blockHeader *footer = (blockHeader*)((void*)closest_b + closest - 4);
            footer->size_status = closest - block_size;

            // update the size status
            closest_b->size_status = closest_b->size_status % 8 + block_size + 1;

            // update the new free block's header
            blockHeader *new = (blockHeader*)((void*)closest_b + block_size);
            new->size_status = closest - block_size + 2;

        } else {
            // the block is not large enough to split, use the whole

            // update the a-bit
            closest_b->size_status += 1;

            // update the p-bit of the next block if the next
            // block is not END MARK
            blockHeader *next = (blockHeader*)((void*)closest_b + closest);
            if (next->size_status != 1) {
                next->size_status += 2;
            }

            // remove the size in the footer
            blockHeader *footer = (blockHeader*)((void*)next - sizeof(blockHeader));
            footer->size_status = 0;    
        }

        return ((void*)closest_b + sizeof(blockHeader));
    }
} 
 
/* 
 * Function for freeing up a previously allocated block.
 * Argument ptr: address of the block to be freed up.
 * Returns 0 on success.
 * Returns -1 on failure.
 * This function should:
 * - Return -1 if ptr is NULL.
 * - Return -1 if ptr is not a multiple of 8.
 * - Return -1 if ptr is outside of the heap space.
 * - Return -1 if ptr block is already freed.
 * - Update header(s) and footer as needed.
 */                    
int bfree(void *ptr) {    
    // check if the ptr is NULL
    if (ptr == NULL) {
        return -1;
    }

    // check if ptr is not a multiple of 8
    if ((unsigned int)(ptr) % 8 != 0) {
        return -1;
    }

    // check if ptr is outside of the heap space
    if (ptr < ((void*)heap_start + sizeof(blockHeader))) {
        return -1;
    }
    if (ptr > ((void*)heap_start + alloc_size - 4)) {
        return -1;
    }

    // check if ptr block is already freed
    blockHeader *block = (blockHeader*)((void*) ptr - sizeof(blockHeader));
    if (block->size_status % 2 == 0) {
        return -1;
    }  

    // update a-bit
    block->size_status = block->size_status - 1;

    // update the p-bit of next block if is not the END MARK
    int block_size = block->size_status - block->size_status % 8;
    blockHeader *next = (blockHeader*)((void*)block + block_size);
    if (next->size_status != 1) {
        next->size_status = next->size_status - 2;
    }

    // update the footer
    blockHeader *footer = (blockHeader*)((void*)next - sizeof(blockHeader));
    footer->size_status = block_size;

    return 0;
} 

/*
 * Function for traversing heap block list and coalescing all adjacent 
 * free blocks.
 *
 * This function is used for delayed coalescing.
 * Updated header size_status and footer size_status as needed.
 */
int coalesce() {

    // delayed coalesce starting from the beginning
    blockHeader *current = heap_start;

    // track how many times coalescing
    int coalesce_count = 0;

    // traves the whole heap space
    while (current->size_status != 1) {
        int current_size = current->size_status - current->size_status % 8;

        // if the current block is free
        if (current->size_status % 2 == 0) {
            blockHeader *next = (blockHeader*)((void*)current + current_size);

            // keep checking the next block if is free until reach 
            // a block is allocated or the END MARK
            while (next->size_status != 1 && next->size_status % 2 == 0) {
                coalesce_count += 1;

                int next_size = next->size_status - next->size_status % 8;
                current_size = current_size + next_size;
                next->size_status = 0;

                blockHeader *footer = (blockHeader*)((void*)next - sizeof(blockHeader));
                footer->size_status = 0;

                next = (blockHeader*)((void*)next + next_size);
            }

            // update the current block's size
            current->size_status = current->size_status % 8 + current_size;
            current = (blockHeader*)((void*)current + current_size);
            // update the current block's footer
            blockHeader *footer = (blockHeader*)((void*)current - sizeof(blockHeader));
            footer->size_status = current_size;

        } else {
            current = (blockHeader*)((void*)current + current_size);
        }
    }

    // if there is no coalescing, return 0; otherwise,
    // return the number of coalescing doing for this call
    if (coalesce_count <= 0) {
        return 0;
    } else {
        return coalesce_count;
    }
}

 
/* 
 * Function used to initialize the memory allocator.
 * Intended to be called ONLY once by a program.
 * Argument sizeOfRegion: the size of the heap space to be allocated.
 * Returns 0 on success.
 * Returns -1 on failure.
 */                    
int init_heap(int sizeOfRegion) {    
 
    static int allocated_once = 0; //prevent multiple myInit calls
 
    int pagesize;   // page size
    int padsize;    // size of padding when heap size not a multiple of page size
    void* mmap_ptr; // pointer to memory mapped area
    int fd;

    blockHeader* end_mark;
  
    if (0 != allocated_once) {
        fprintf(stderr, 
        "Error:mem.c: InitHeap has allocated space during a previous call\n");
        return -1;
    }

    if (sizeOfRegion <= 0) {
        fprintf(stderr, "Error:mem.c: Requested block size is not positive\n");
        return -1;
    }

    // Get the pagesize
    pagesize = getpagesize();

    // Calculate padsize as the padding required to round up sizeOfRegion 
    // to a multiple of pagesize
    padsize = sizeOfRegion % pagesize;
    padsize = (pagesize - padsize) % pagesize;

    alloc_size = sizeOfRegion + padsize;

    // Using mmap to allocate memory
    fd = open("/dev/zero", O_RDWR);
    if (-1 == fd) {
        fprintf(stderr, "Error:mem.c: Cannot open /dev/zero\n");
        return -1;
    }
    mmap_ptr = mmap(NULL, alloc_size, PROT_READ | PROT_WRITE, MAP_PRIVATE, fd, 0);
    if (MAP_FAILED == mmap_ptr) {
        fprintf(stderr, "Error:mem.c: mmap cannot allocate space\n");
        allocated_once = 0;
        return -1;
    }
  
    allocated_once = 1;

    // for double word alignment and end mark
    alloc_size -= 8;

    // Initially there is only one big free block in the heap.
    // Skip first 4 bytes for double word alignment requirement.
    heap_start = (blockHeader*) mmap_ptr + 1;

    // Set the end mark
    end_mark = (blockHeader*)((void*)heap_start + alloc_size);
    end_mark->size_status = 1;

    // Set size in header
    heap_start->size_status = alloc_size;

    // Set p-bit as allocated in header
    // note a-bit left at 0 for free
    heap_start->size_status += 2;

    // Set the footer
    blockHeader *footer = (blockHeader*) ((void*)heap_start + alloc_size - 4);
    footer->size_status = alloc_size;
  
    return 0;
} 
                  
/* 
 * Function to be used for DEBUGGING to help you visualize your heap structure.
 * Prints out a list of all the blocks including this information:
 * No.      : serial number of the block 
 * Status   : free/used (allocated)
 * Prev     : status of previous block free/used (allocated)
 * t_Begin  : address of the first byte in the block (where the header starts) 
 * t_End    : address of the last byte in the block 
 * t_Size   : size of the block as stored in the block header
 */                     
void disp_heap() {     

    int counter;
    char status[6];
    char p_status[6];
    char *t_begin = NULL;
    char *t_end   = NULL;
    int t_size;

    blockHeader *current = heap_start;
    counter = 1;

    int used_size = 0;
    int free_size = 0;
    int is_used   = -1;

    fprintf(stdout, 
	"*********************************** Block List **********************************\n");
    fprintf(stdout, "No.\tStatus\tPrev\tt_Begin\t\tt_End\t\tt_Size\n");
    fprintf(stdout, 
	"---------------------------------------------------------------------------------\n");
  
    while (current->size_status != 1) {
        t_begin = (char*)current;
        t_size = current->size_status;
    
        if (t_size & 1) {
            // LSB = 1 => used block
            strcpy(status, "alloc");
            is_used = 1;
            t_size = t_size - 1;
        } else {
            strcpy(status, "FREE ");
            is_used = 0;
        }

        if (t_size & 2) {
            strcpy(p_status, "alloc");
            t_size = t_size - 2;
        } else {
            strcpy(p_status, "FREE ");
        }

        if (is_used) 
            used_size += t_size;
        else 
            free_size += t_size;

        t_end = t_begin + t_size - 1;
    
        fprintf(stdout, "%d\t%s\t%s\t0x%08lx\t0x%08lx\t%4i\n", counter, status, 
        p_status, (unsigned long int)t_begin, (unsigned long int)t_end, t_size);
    
        current = (blockHeader*)((char*)current + t_size);
        counter = counter + 1;
    }

    fprintf(stdout, 
	"---------------------------------------------------------------------------------\n");
    fprintf(stdout, 
	"*********************************************************************************\n");
    fprintf(stdout, "Total used size = %4d\n", used_size);
    fprintf(stdout, "Total free size = %4d\n", free_size);
    fprintf(stdout, "Total size      = %4d\n", used_size + free_size);
    fprintf(stdout, 
	"*********************************************************************************\n");
    fflush(stdout);

    return;  
} 


// end of myHeap.c (Spring 2022)                                         


