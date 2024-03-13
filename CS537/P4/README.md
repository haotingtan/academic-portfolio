# Project 4: Stride Scheduling for xv6

## Project Objective

-   Implement a new scheduling algorithm for the xv6 scheduler.
-   Gain further knowledge of a real kernel (xv6).
-   Familiarize yourself with a scheduler and change it to implement stride scheduling.
-   Make a graph to show your project behaves appropriately.

## Stride Scheduling

-   Each process is assigned tickets, and the stride is inversely proportional to the number of tickets, specifically calculated as `stride = max_stride / tickets`, where `max_stride` is a constant number.
-   Each process has a `pass` value, which starts from the stride and is incremented by the stride every time the process executes for a time slot.
-   The scheduler schedules a runnable process with the minimum pass value to run in the next time slot.

## Implementation Details

-   Set `CPUS := 1` in the Makefile to see the effect of scheduling on a single CPU.
-   We are not concerned about the complexity of retrieving the process with the minimum pass in each scheduler decision.
-   The `max_stride` value should be greater than max tickets allocated to any process and preferably divisible by ticket values for getting more accurate strides (assuming an integer `max_stride`).
-   If the number of tickets is changed while a process is executing, we update the tickets' stride but not the pass value.

## New System Calls

-   `int settickets(int number)`: Sets the number of tickets of the calling process. By default, each process should get one ticket. This routine should return 0 if successful and -1 otherwise.
-   `int getpinfo(struct pstat *)`: Returns information about all running processes, including how many times each process has been chosen to run and the process ID of each process. This routine should return 0 if successful and -1 otherwise.

## pstat Structure

``` c
#ifndef _PSTAT_H_
#define _PSTAT_H_

#include "param.h"

struct pstat {
    int inuse[NPROC];  // whether this slot of the process table is in use (1 or 0)
    int tickets[NPROC]; // the number of tickets this process has
    int strides[NPROC]; // the stride of each process
    int pass[NPROC];  // the current pass value of each process
    int pid[NPROC];  // the PID of each process
    int ticks[NPROC];  // the number of ticks each process has accumulated
};

#endif // _PSTAT_H_
```

## Graph Requirement

-   Make a graph similar to the stride scheduling paper for 2 processes over 10 time slots where the processes have a 3:2 ratio of tickets. Specify the `max_stride` and tickets assigned to each process in the graph.
-   The graph can be generated using plotting software or be hand-drawn, clearly showing the point coordinates.
