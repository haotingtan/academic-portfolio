# Project 6: Parallel Sorting

## Introduction
In this project, you will write a C application to perform parallel sorting. The objectives are to familiarize yourself with Linux pthreads, learn how to parallelize a program, and program for high performance.

## Project Specification
Your parallel sort (`psort`) will take three command-line arguments: `input` (the input file to read records for sort), `output` (the output file where records will be written after sort), and `numThreads` (the number of threads that shall perform the sort operation).

Example usage: `./psort input output 4`

The input file consists of fixed-size records (100 bytes each), including a key in the first four bytes. The sort will read all records, sort them by key, and write them to the output file. The implementation of the parallel sort operation is left to you.

## Background Reading
- [Intro to Threads](https://pages.cs.wisc.edu/~remzi/OSTEP/threads-intro.pdf)
- [Threads API](http://pages.cs.wisc.edu/~remzi/OSTEP/threads-api.pdf)
- [Locks](http://pages.cs.wisc.edu/~remzi/OSTEP/threads-locks.pdf)
- [Using Locks](http://pages.cs.wisc.edu/~remzi/OSTEP/threads-locks-usage.pdf)
- [Condition Variables](http://pages.cs.wisc.edu/~remzi/OSTEP/threads-cv.pdf)

Good luck!
