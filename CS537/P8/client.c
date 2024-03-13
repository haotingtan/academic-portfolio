#include "client.h"
#include "udp.h"
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <stdio.h>

#define MAXLINE 1024
#define RETRY_INTERVAL 1
#define MAX_RETRIES 5

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

struct server_context {
    int valid;
    int result;
};

struct rpc_connection RPC_init(int src_port, int dst_port, char dst_addr[]) {
    struct rpc_connection rpc;
    rpc.recv_socket = init_socket(src_port);
    struct sockaddr_storage addr;
    socklen_t addrlen;
    populate_sockaddr(AF_INET, dst_port, dst_addr, &addr, &addrlen);
    rpc.dst_addr = *((struct sockaddr *)(&addr));
    rpc.dst_len = addrlen;
    rpc.seq_number = 0;
    srand(getpid());
    rpc.client_id = rand();
    return rpc;
}

// Sleeps the server thread for a few seconds
void RPC_idle(struct rpc_connection *rpc, int time) {
    char message[MAXLINE];
    struct client_context request;
    request.op_code = IDLE;
    request.client_id = rpc->client_id;
    request.sequence_number = rpc->seq_number;
    rpc->seq_number += 1;
    request.time = time;
    memcpy(&message, &request, sizeof(request));
    send_packet(rpc->recv_socket, rpc->dst_addr, rpc->dst_len, message, sizeof(request));
    for (int i=0; i<MAX_RETRIES; i++) {
        struct packet_info rec_client = receive_packet_timeout(rpc->recv_socket, RETRY_INTERVAL);
        if (rec_client.recv_len != -1) {
            struct server_context *receive_server = (struct server_context *)rec_client.buf;
            if (receive_server->valid != -1) {
                return;
            } else {
                sleep(RETRY_INTERVAL);
                i = 0;
                printf("Server is still working on your request\n");
            }
        }
        send_packet(rpc->recv_socket, rpc->dst_addr, rpc->dst_len, message, sizeof(request));
    }
    close_socket(rpc->recv_socket);
    printf("Error: can't connected to the server\n");
    exit(1);
}

// gets the value of a key on the server store
int RPC_get(struct rpc_connection *rpc, int key) {
    char message[MAXLINE];
    struct client_context request;
    request.op_code = GET;
    request.client_id = rpc->client_id;
    request.sequence_number = rpc->seq_number;
    rpc->seq_number += 1;
    request.key = key;
    memcpy(&message, &request, sizeof(request));
    send_packet(rpc->recv_socket, rpc->dst_addr, rpc->dst_len, message, sizeof(request));
    for (int i=0; i<MAX_RETRIES; i++) {
        struct packet_info rec_client = receive_packet_timeout(rpc->recv_socket, RETRY_INTERVAL);
        if (rec_client.recv_len != -1) {
            struct server_context *receive_server = (struct server_context *)rec_client.buf;
            if (receive_server->valid != -1) {
                return receive_server->result;
            } else {
                sleep(RETRY_INTERVAL);
                i = 0;
                printf("Server is still working on your request\n");
            }
        }
        send_packet(rpc->recv_socket, rpc->dst_addr, rpc->dst_len, message, sizeof(request));
    }
    close_socket(rpc->recv_socket);
    printf("Error: can't connected to the server\n");
    exit(1);
}

// sets the value of a key on the server store
int RPC_put(struct rpc_connection *rpc, int key, int value) {
    char message[MAXLINE];
    struct client_context request;
    request.op_code = PUT;
    request.client_id = rpc->client_id;
    request.sequence_number = rpc->seq_number;
    rpc->seq_number += 1;
    request.key = key;
    request.value = value;
    memcpy(&message, &request, sizeof(request));
    send_packet(rpc->recv_socket, rpc->dst_addr, rpc->dst_len, message, sizeof(request));
    for (int i=0; i<MAX_RETRIES; i++) {
        struct packet_info rec_client = receive_packet_timeout(rpc->recv_socket, RETRY_INTERVAL);
        if (rec_client.recv_len != -1) {
            struct server_context *receive_server = (struct server_context *)rec_client.buf;
            if (receive_server->valid != -1) {
                return receive_server->result;
            } else {
                sleep(RETRY_INTERVAL);
                i = 0;
                printf("Server is still working on your request\n");
            }
        }
        send_packet(rpc->recv_socket, rpc->dst_addr, rpc->dst_len, message, sizeof(request));
    }
    close_socket(rpc->recv_socket);
    printf("Error: can't connected to the server\n");
    exit(1);
}

// closes the RPC connection to the server
void RPC_close(struct rpc_connection *rpc) {
    close_socket(rpc->recv_socket);
}
