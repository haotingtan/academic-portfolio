////////////////////////////////////////////////////////////////////////////////
// Main File:        csim.c
// This File:        csim.c
// Other Files:      csim-ref, test-csim
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

////////////////////////////////////////////////////////////////////////////////
//
// Copyright 2013,2019-2020
// Posting or sharing this file is prohibited, including any changes/additions.
// Used by permission for Fall 2022
//
////////////////////////////////////////////////////////////////////////////////

/*
 * csim.c:  
 * A cache simulator that can replay traces (from Valgrind) and output
 * statistics for the number of hits, misses, and evictions.
 * The replacement policy is LRU.
 *
 * Implementation and assumptions:
 *  1. Each load/store can cause at most one cache miss plus a possible eviction.
 *  2. Instruction loads (I) are ignored.
 *  3. Data modify (M) is treated as a load followed by a store to the same
 *  address. Hence, an M operation can result in two cache hits, or a miss and a
 *  hit plus a possible eviction.
 */  

#include <getopt.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <assert.h>
#include <math.h>
#include <limits.h>
#include <string.h>
#include <errno.h>
#include <stdbool.h>

/******************************************************************************/
/* DO NOT MODIFY THESE VARIABLES **********************************************/

//Globals set by command line args.
int b = 0; //number of block (b) bits
int s = 0; //number of set (s) bits
int E = 0; //number of lines per set

//Globals derived from command line args.
int B; //block size in bytes: B = 2^b
int S; //number of sets: S = 2^s

//Global counters to track cache statistics in access_data().
int hit_cnt = 0;
int miss_cnt = 0;
int evict_cnt = 0;

//Global to control trace output
int verbosity = 0; //print trace if set
/******************************************************************************/
  
  
//Type mem_addr_t: Use when dealing with addresses or address masks.
typedef unsigned long long int mem_addr_t;

//Type cache_line_t: Use when dealing with cache lines.
typedef struct cache_line {                    
    char valid;
    mem_addr_t tag;

    // count of this line, smaller number indicates less recently use
    int count;

} cache_line_t;

//Type cache_set_t: Use when dealing with cache sets
//Note: Each set is a pointer to a heap array of one or more cache lines.
typedef cache_line_t* cache_set_t;

//Type cache_t: Use when dealing with the cache.
//Note: A cache is a pointer to a heap array of one or more sets.
typedef cache_set_t* cache_t;

// Create the cache we're simulating. 
//Note: A cache is a pointer to a heap array of one or more cache sets.
cache_t cache;  

/* init_cache:
 * Allocates the data structure for a cache with S sets and E lines per set.
 * Initializes all valid bits and tags with 0s.
 */                    
void init_cache() {   
    // initilize the values of B and S  
    B = (int)(pow(2.0, b));
    S = (int)(pow(2.0, s));

    // allocate memory for number of sets given by the arugment
    cache = malloc(sizeof(cache_set_t) * S);
    if (cache == NULL) {
        printf("Encounter error when allocate memory");
        exit(1);
    }

    // allocate memory for number of blocks for each set given
    for (int i = 0; i < S; i++) {
        *(cache + i) = malloc(sizeof(cache_line_t) * E);
        if (*(cache + i) == NULL) {
            int t = 0;
            while (t < i) {
                free( *(cache + t) );
                *(cache + t) = NULL;
                t = t + 1;
            }
            free(cache);
            cache = NULL;
            printf("Encounter error when allocate memory");
            exit(1);
        }
    }
     
    // Initializes all valid bits, tags and count with 0s.
    for (int i = 0; i < S; i++) {
        for (int j = 0; j < E; j++) {
            cache[i][j].valid = 0;
            cache[i][j].tag = 0;
            cache[i][j].count = 0;
        }
    }
}


/* free_cache:
 * Frees all heap allocated memory used by the cache.
 */                    
void free_cache() { 
    for (int i = 0; i < S; i++) {
        free( cache[i] );
        cache[i] = NULL;
    }       
    free(cache);
    cache = NULL;   
}


/* access_data:
 * Simulates data access at given "addr" memory address in the cache.
 *
 * If already in cache, increment hit_cnt
 * If not in cache, cache it (set tag), increment miss_cnt
 * If a line is evicted, increment evict_cnt
 */                    
void access_data(mem_addr_t addr) { 
    // get the set number(index) and tag number
    int set_and_byte = addr & ((int)(pow(2.0, (double)(s+b))) - 1);
    int tag_bits = addr - set_and_byte;
    tag_bits = addr >> (s+b);
    int set_index = set_and_byte - set_and_byte % ((int)(pow(2.0,(double)b)));

    // shift the set index to get the actual index
    set_index = set_and_byte >> b;

    // the maximum count of the cache line in this set
    int max_count = 0;

    // create a pointer variable to point to the cache hit line
    cache_line_t* tag_match_line = NULL;

    // create a pointer variable to point to the evicted line
    // and initlizes it to the first line of the set index that is derived from the addr
    // NOTE: evicted line has the least count
    cache_line_t* evicted_line = &cache[set_index][0];

    // create a pointer variable to point to the unused(empty) cache line
    cache_line_t* valid_line = NULL;

    // iterate the whole set 
    for (int i=0; i<E; i++) {
        // case 1: the line is valid and tag match
        if (cache[set_index][i].valid == 1 && cache[set_index][i].tag == tag_bits) {
            tag_match_line = &cache[set_index][i];
            hit_cnt += 1;

        } else if (cache[set_index][i].valid == 0) {
            // if the line is un-used and valid-line is point to nothing, set the 
            // valid_line pointing to the current line 
            if (valid_line == NULL) {
                valid_line = &cache[set_index][i];
            }
        } else {
            // keep track of the current line's count, update the lru_line_c if 
            // the current count is smaller than lru_line_c. and update the 
            // evicted_line pointer value also.
            if (cache[set_index][i].count < evicted_line->count) {
                evicted_line = &cache[set_index][i];
            }
        }

        // update the max_count if current line's count is greater than it
        if (cache[set_index][i].count > max_count) {
            max_count = cache[set_index][i].count;
        }
    }

    // if no tag match found(no cache hit)
    if (tag_match_line == NULL) {
        miss_cnt += 1;

        // if there is open space(un-used line) in the set, use the un-used line
        if (valid_line != NULL) {
            valid_line->valid = 1;
            valid_line->tag = tag_bits;
            valid_line->count = max_count + 1;
        } else {
            // if there is no open space in the set, evict the least recently used line
            evict_cnt += 1;
            evicted_line->tag = tag_bits;
            evicted_line->count = max_count + 1;
        }
    } else {
        tag_match_line->count = max_count + 1;
    }

    tag_match_line = NULL;
    evicted_line = NULL;
    valid_line = NULL;
}
  
  
/* replay_trace:
 * Replays the given trace file against the cache.
 *
 * Reads the input trace file line by line.
 * Extracts the type of each memory access : L/S/M
 * TRANSLATE each "L" as a load i.e. 1 memory access
 * TRANSLATE each "S" as a store i.e. 1 memory access
 * TRANSLATE each "M" as a load followed by a store i.e. 2 memory accesses 
 */                    
void replay_trace(char* trace_fn) {           
    char buf[1000];  
    mem_addr_t addr = 0;
    unsigned int len = 0;
    FILE* trace_fp = fopen(trace_fn, "r"); 

    if (!trace_fp) { 
        fprintf(stderr, "%s: %s\n", trace_fn, strerror(errno));
        exit(1);   
    }

    while (fgets(buf, 1000, trace_fp) != NULL) {
        if (buf[1] == 'S' || buf[1] == 'L' || buf[1] == 'M') {
            sscanf(buf+3, "%llx,%u", &addr, &len);
      
            if (verbosity)
                printf("%c %llx,%u ", buf[1], addr, len);

            // GIVEN: 1. addr has the address to be accessed
            //        2. buf[1] has type of acccess(S/L/M)
            // call access_data function here depending on type of access
            if (buf[1] == 'S' || buf[1] == 'L') {
                access_data(addr);
            } else if (buf[1] == 'M') {
                access_data(addr);
                access_data(addr);
            }

            if (verbosity)
                printf("\n");
        }
    }

    fclose(trace_fp);
}  
  
  
/*
 * print_usage:
 * Print information on how to use csim to standard output.
 */                    
void print_usage(char* argv[]) {                 
    printf("Usage: %s [-hv] -s <num> -E <num> -b <num> -t <file>\n", argv[0]);
    printf("Options:\n");
    printf("  -h         Print this help message.\n");
    printf("  -v         Optional verbose flag.\n");
    printf("  -s <num>   Number of s bits for set index.\n");
    printf("  -E <num>   Number of lines per set.\n");
    printf("  -b <num>   Number of b bits for block offsets.\n");
    printf("  -t <file>  Trace file.\n");
    printf("\nExamples:\n");
    printf("  linux>  %s -s 4 -E 1 -b 4 -t traces/yi.trace\n", argv[0]);
    printf("  linux>  %s -v -s 8 -E 2 -b 4 -t traces/yi.trace\n", argv[0]);
    exit(0);
}  
  
  
/*
 * print_summary:
 * Prints a summary of the cache simulation statistics to a file.
 */                    
void print_summary(int hits, int misses, int evictions) {                
    printf("hits:%d misses:%d evictions:%d\n", hits, misses, evictions);
    FILE* output_fp = fopen(".csim_results", "w");
    assert(output_fp);
    fprintf(output_fp, "%d %d %d\n", hits, misses, evictions);
    fclose(output_fp);
}  

  
  
/*
 * main:
 * Main parses command line args, makes the cache, replays the memory accesses
 * free the cache and print the summary statistics.  
 */                    
int main(int argc, char* argv[]) {                      
    char* trace_file = NULL;
    char c;
    
    // Parse the command line arguments: -h, -v, -s, -E, -b, -t 
    while ((c = getopt(argc, argv, "s:E:b:t:vh")) != -1) {
        switch (c) {
            case 'b':
                b = atoi(optarg);
                break;
            case 'E':
                E = atoi(optarg);
                break;
            case 'h':
                print_usage(argv);
                exit(0);
            case 's':
                s = atoi(optarg);
                break;
            case 't':
                trace_file = optarg;
                break;
            case 'v':
                verbosity = 1;
                break;
            default:
                print_usage(argv);
                exit(1);
        }
    }

    //Make sure that all required command line args were specified.
    if (s == 0 || E == 0 || b == 0 || trace_file == NULL) {
        printf("%s: Missing required command line argument\n", argv[0]);
        print_usage(argv);
        exit(1);
    }

    //Initialize cache.
    init_cache();

    //Replay the memory access trace.
    replay_trace(trace_file);

    //Free memory allocated for cache.
    free_cache();

    //Print the statistics to a file.
    //DO NOT REMOVE: This function must be called for test_csim to work.
    print_summary(hit_cnt, miss_cnt, evict_cnt);
    return 0;   
}  

// 202209

