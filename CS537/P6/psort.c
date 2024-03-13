#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <sys/time.h>
#include <string.h>


 /* define variables for the problem */
struct working_limit {
    int lower;
    int upper;
};
struct chunks {
    int ll;
    int lu;
    int ul;
    int uu;
};
char **pairs;
int num_threads;


void merge(int l, int m, int r) {
    int i, j, k;
    int n1 = m - l + 1;    
    int n2 = r - m;

    char **L, **R; 
    L = malloc(sizeof(char *) * n1);
    R = malloc(sizeof(char *) * n2);
    memcpy(L, pairs + l, n1 * sizeof(char *));
    memcpy(R, pairs + m + 1, n2 * sizeof(char *));


    i = 0; 
    j = 0; 
    k = l; 
    while (i < n1 && j < n2) {
        if (*(int*)L[i] <= *(int*)R[j]) {
            pairs[k] = L[i];
            i++;
        } else {
            pairs[k] = R[j];
            j++;
        }
        k++;
    }
 
    while (i < n1) {
        pairs[k] = L[i];
        i++;
        k++;
    }
 
    while (j < n2) {
        pairs[k] = R[j];
        j++;
        k++;
    }

    free(L);
    free(R);

}

void merge_s(int l, int r) {
    if (l < r) {
        int m = l + (r - l) / 2;
        merge_s(l, m);
        merge_s(m + 1, r);
        merge(l, m, r);
    }
}

void *merge_sort(void *arg) {
    struct working_limit *range = (struct working_limit *)arg;
    int l = range->lower;
    int r = range->upper;

    if (l < r) {
        int m = l + (r - l) / 2;
        merge_s(l, m);
        merge_s(m + 1, r);
        merge(l, m, r);
    }
    pthread_exit(NULL);
}

void *combine(void *arg) {
    struct chunks *range = (struct chunks *)arg;

    int ll = range->ll;
    int lu = range->lu;
    int ul = range->ul;
    int uu = range->uu;
    int limit = uu-ll+1;

    char **temp;
    temp = malloc(sizeof(char *) * limit);
    memcpy(temp, pairs + ll, limit * sizeof(char *));

    for (int i=0; i<limit; i++) {
        if (ll>lu) {
            temp[i] = pairs[ul];
            // temp[i].key = pairs[ul].key;
            // temp[i].index = pairs[ul].index;
            ul += 1;
        } else if (ul>uu) {
            temp[i] = pairs[ll];
            // temp[i].key = pairs[ll].key;
            // temp[i].index = pairs[ll].index;
            ll += 1;
        } else if (*(int*)pairs[ll] < *(int*)pairs[ul]) {
            temp[i] = pairs[ll];
            // temp[i].key = pairs[ll].key;
            // temp[i].index = pairs[ll].index;
            ll += 1;
        } else {
            temp[i] = pairs[ul];
            // temp[i].key = pairs[ul].key;
            // temp[i].index = pairs[ul].index;
            ul += 1;
        }
    }

    ll = range->ll;
    for (int i=0; i<limit; i++) {
        pairs[ll+i] = temp[i];
        // pairs[ll+i].key = temp[i].key;
        // pairs[ll+i].index = temp[i].index;
    }

    pthread_exit(NULL);
}

int main(int argc, char **argv)
{

    // Time
    struct timeval  start, end;
    double time_spent;

    // file I/O
    if (argc != 4) {
        printf("Error: wrong number of arguments\n");
        exit(1);
    }
    if ((num_threads=atoi(argv[3])) < 0) {
        printf("Error: invalid number of threads\n");
        exit(1);
    }
    FILE* input_file = fopen(argv[1], "rb");
    if(input_file == NULL) {
        printf("Error: can't open the input file\n");
        exit(1);
    }
    // check the length of input file
    fseek(input_file, 0L, SEEK_END);
    int record_size = (ftell(input_file) + 1)/100;
    fseek(input_file, 0L, SEEK_SET);

    pairs = (char **)malloc(sizeof(char*) * record_size);
    char buffer[100];
    int index = 0, read;
    while(index != record_size){
        read = fread(buffer, sizeof(buffer), 1, input_file);
        pairs[index] = (char *)malloc(sizeof(char) * 100);
        memcpy(pairs[index], buffer, 100);
        index++;
        if(read != 1)
            break;
    }
    fclose(input_file);

    if (num_threads == 0) {
        num_threads = 1;
    }
    if (num_threads > record_size/2) {
        num_threads = record_size/2;
    }

    int limit = record_size/num_threads;
    struct working_limit range[num_threads];
    int curr = 0;
    for (int i=0; i<num_threads; i++) {
        range[i].lower = curr;
        curr += limit;
        range[i].upper = curr-1;
        if (i == num_threads-1) {
            range[i].upper += record_size % num_threads;
        }
    }

    // Time
    gettimeofday(&start, NULL);

    pthread_t child_threads[num_threads];

    for (int i = 0; i < num_threads; i++) {
        pthread_create(&child_threads[i], NULL, merge_sort, &range[i]);
    }
    for (int i = 0; i < num_threads; i++) {
        pthread_join(child_threads[i], NULL);
    }

    int chunk = num_threads;
    while (chunk > 1) {
        int loop = chunk / 2;
        struct chunks com_range[loop];
        int p = 0;
        int k = 0;
        for (k = 0; k < loop; k++) {
            com_range[k].ll = range[k*2].lower;
            com_range[k].lu = range[k*2].upper;
            com_range[k].ul = range[k*2+1].lower;
            com_range[k].uu = range[k*2+1].upper;
            range[p].lower = range[k*2].lower;
            range[p].upper = range[k*2+1].upper;
            p += 1;
        }
        if (chunk % 2 == 1) {
            range[p].lower = range[(k)*2].lower;
            range[p].upper = range[(k)*2].upper;
            p += 1;
        }
        chunk = chunk / 2 + chunk % 2;

        for (int j = 0; j < loop; j++) {
            pthread_create(&child_threads[j], NULL, combine, &com_range[j]);
        }
        for (int j = 0; j < loop; j++) {
            pthread_join(child_threads[j], NULL);
        }
    }

    //Time
    gettimeofday(&end, NULL);
    time_spent = ((double) ((double) (end.tv_usec - start.tv_usec) / 1000000 +
                            (double) (end.tv_sec - start.tv_sec)));
    printf("%d Time taken for execution: %f seconds\n", num_threads, time_spent);

    int file, r;
    file = creat(argv[2], S_IWUSR | S_IRUSR);
    if (file < -1) {
        printf("Error: creat()\n");
        exit(1); 
    }
    for (int i=0; i<record_size; i++) {
        r = write(file, pairs[i], 100);
        if(r < -1) {
            printf("Error: write()\n");
            exit(1); 
        }
    }
    fsync(file);
    close(file);
    for(int i = 0; i < record_size; i++){
        free(pairs[i]);
    }
    free(pairs);

    return 0;
}
