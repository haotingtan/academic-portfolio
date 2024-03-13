# Project 5: Copy-on-Write (CoW) Fork in xv6

## Introduction

The goal of this project is to become familiar with Unix-style forking and x86 memory management by converting the simple fork() implementation in xv6 to a copy-on-write fork(). This involves writing a trap handler for page faults, augmenting the physical memory management code, and manipulating page tables.

## The Problem

The fork() system call in xv6 copies all of the parent process's user-space memory into the child, which can be inefficient. This project aims to implement copy-on-write (CoW) fork to defer allocating and copying physical memory pages for the child until the copies are actually needed.

## Implementing Copy-on-Write Fork

### Part A: Adding System Call

-   Add a system call `int getFreePagesCount(void)` to retrieve the total number of free pages in the system.

### Part B: Tracking Page Reference Counts

-   Modify `kernel/kalloc.c` to add a reference count to the page descriptor structure.
-   Implement a variant of `copyuvm()` called `cowuvm()` in `kernel/vm.c` that converts each writeable page table entry in the parent and child to read-only and marks the page tables of both parent and child to point to the same physical pages.

### Part C: Copy-on-Write

-   Implement a CoW page fault handler in `kernel/vm.c` and `kernel/trap.c` to handle page faults arising from write operations on pages marked as read-only.

## Notes

-   Modifications for tracking free physical pages and reference counts are added in `kernel/kalloc.c`.
-   Whenever you make changes to the page table of a process, you must re-install that page table by writing its address into CR3 using `lcr3(PADDR(pgdir))` to ensure the TLB entries are valid.
