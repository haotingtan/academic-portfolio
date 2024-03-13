# Project 8: Implementing Remote Procedure Calls 

## Overview
This project involves implementing a simplified version of Remote Procedure Calls (RPC), inspired by a classic paper. The main objectives are to familiarize yourself with basic distributed systems, process interaction, and the use of pthreads.

## Project Description
You will implement an RPC system comprising a client library and a server that handles requests. The client sends messages containing a client ID and a sequence number to the server, which executes functions defined in `server_functions.h` and `server_functions.c` if the request is new, or discards it if it's a duplicate.

## Requirements
- The server should bind to a specified port and handle up to 100 connected clients.
- The client library should integrate into provided test programs, initiating and blocking on RPC calls to the server.
- Messages between the server and client should include sequence numbers and client IDs to manage duplicate requests and maintain at-most once semantics.

## Implementation Highlights
- Implement server functions: `idle`, `get`, and `put`.
- Use `udp.h` and `udp.c` for network communication.
- Ensure the server tracks connected clients, executes requests with new sequence numbers, and handles duplicate or out-of-order requests appropriately.
- The client should retry requests on timeout and handle responses correctly.