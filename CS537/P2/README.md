# Project 2: Add System calls to xv6

In this assignment, you will add 2 system calls to a version of xv6.

This project is intended to be a warmup for xv6, and is thus relatively light! You do not need to write many lines of code; instead, a lot of your time will be spent learning where different routines are located in the existing xv6 source code.

## Learning Objectives:

-   Gain comfort looking through more substantial code bases written by others in which you do not need to understand every line
-   Obtain familiarity with the xv6 code base in particular
-   Learn how to add a system call to xv6
-   Add a user-level application that can be used within xv6
-   Become familiar with a few of the data structures in xv6 (e.g., process table)
-   Use the gdb debugger on xv6

## xv6 System Calls

You will be using our version of xv6. Copy the xv6 source code using the following commands:

```         
prompt> cp ~cs537-1/public/xv6.tar.gz .
prompt> tar -xvf xv6.tar.gz
```

If, for development and testing, you would like to run xv6 in an environment other than the CSL instructional Linux cluster, you may need to set up additional software. It is derived from Unix version 6, documented here Links to an external site.. You can read these instructions for the MacOS build environment Links to an external site.. Note that we will run all of our tests and do our grading on the instructional Linux cluster so you should always ensure that the final code you hand in works on those machines.

After you have obtained the source files, you can run make qemu-nox to compile all the code and run it using the QEMU emulator. Test out the unmodified code by running a few of the existing user-level applications, like ls and forktest. With ls inside the emulator, you'll be able to see a few other applications that are available (as well as files that have been created within the xv6 environment).

To quit the emulator, type Ctl-a x.

You will want to become familiar with the Makefile and be comfortable modifying it. In particular, see the list of existing UPROGS. See the different ways of running the environment through make (e.g., qemu-nox or qemu-nox-gdb).

Find where the number of CPUS is set and change this to 1.

# System Calls Details

You will next add 2 new system calls to xv6. 

```
int getnextpid()
```

When called, getnextpid returns the next available PID, which is simply nextpid in proc.c.

```
int getprocstate(int pid, char* state, int n)
```

When called, getprocstate finds the state of the first process with that pid in ptable, and copies the process's procstate to the parameter passed into this system call state, which points to a block of memory of size n bytes; use the same corresponding strings as in void procdump(void); if n is not enough to hold the state string, it should return -1, in which case the content of state is undefined. It should return 0 on success and -1 if the the process is not found.