#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <pthread.h>
#include "server_functions.h"
#include "udp.h"

#define MAXLINE 1024
#define MAX_CLIENTS 100
#define IDLE 0
#define PUT 1
#define GET 2

struct client_context {
    int op_code;
    int client_id;
    int sequence_number;
    int key;
    int value;
    int time;
};

 struct work {
    struct client_context work_data;
    int index;
 };

struct server_context {
    int valid;
    int result;
};

struct databse {
    int dirty_bit;
    int client_id;
    int last_sequence_number;
    int last_result;
};

pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;
struct databse call_table[MAX_CLIENTS];
struct work client_work[MAX_CLIENTS];
pthread_t client_threads[MAX_CLIENTS];
char thread_complete[MAX_CLIENTS];
int num_clients = 0;

void *operation(void *arg) {
    struct work *arg_conv = (struct work *)arg;
    int result;
    if (arg_conv->work_data.op_code == IDLE) {
        idle(arg_conv->work_data.time);
    } else if (arg_conv->work_data.op_code == PUT) {
        // pthread_mutex_lock(&lock);
        result = put(arg_conv->work_data.key, arg_conv->work_data.value);
        // pthread_mutex_unlock(&lock);
    } else if (arg_conv->work_data.op_code == GET) {
        // pthread_mutex_lock(&lock);
        result = get(arg_conv->work_data.key);
        // pthread_mutex_unlock(&lock);
    }

    pthread_mutex_lock(&lock);
    call_table[arg_conv->index].dirty_bit = 1;
    call_table[arg_conv->index].last_result = result;
    pthread_mutex_unlock(&lock);

    pthread_exit(NULL);
}

int main(int argc, char **argv) {
    if (argc != 2) {
        printf("Usage: %s <port>\n", argv[0]);
        exit(0);
    }

    int port = atoi(argv[1]);
    if (port <= 2049 || port >= 65536) {
        printf("Error: invalid port number\n");
        exit(1);
    }
    struct socket curr_socket = init_socket(port);

    while (1) {
        struct packet_info rec_packet = receive_packet(curr_socket);
        struct client_context client_request;
        memcpy(&client_request, &rec_packet.buf, sizeof(client_request));

        int found = -1;
        for (int i=0; i<num_clients; i++) {
            if (call_table[i].client_id == client_request.client_id) {
                found = i; 
            }
        }
        if (found == -1) {
            if (num_clients >= MAX_CLIENTS) {
                continue;
            }
            call_table[num_clients].client_id = client_request.client_id;
            call_table[num_clients].last_sequence_number = -1;
            found = num_clients;
            num_clients += 1;
        } 

        if (client_request.sequence_number < call_table[found].last_sequence_number) {
            continue;
        } else if (client_request.sequence_number > call_table[found].last_sequence_number) {
            memcpy(&client_work[found].work_data, &client_request, sizeof(client_request));
            memcpy(&client_work[found].index, &found, sizeof(found));

            pthread_mutex_lock(&lock);
            call_table[found].dirty_bit = 0;
            call_table[found].last_sequence_number = client_request.sequence_number;
            pthread_mutex_unlock(&lock);

            pthread_create(&client_threads[found], NULL, operation, &client_work[found]);

        } else {
            char message[MAXLINE];
            struct server_context server;
            pthread_mutex_lock(&lock);
            int is_running = call_table[found].dirty_bit;
            pthread_mutex_unlock(&lock);
            if (is_running == 1) {
                pthread_join(client_threads[found], NULL); 
                server.valid = 1;
                server.result = call_table[found].last_result;
                memcpy(message, &server, sizeof(server));
                send_packet(curr_socket, rec_packet.sock, rec_packet.slen, message, sizeof(server));
            } else {
                server.valid = -1;
                memcpy(message, &server, sizeof(server));
                send_packet(curr_socket, rec_packet.sock, rec_packet.slen, message, sizeof(server));
            }
        } 
    }

    close_socket(curr_socket);
    return 0;
}