# Project 7: File Systems

## Summary
In Project 7, you are tasked with implementing "runscan," an ext2 disk image checker. The primary objectives are:
1. Retrieve jpg files under "top_secret" directory if it exists; otherwise, retrieve all jpg files in the disk image.
2. Generate three files for each jpg file retrieved in the "outputDirectory":
    - An inode details file (`file-<inode>-details.txt`)
    - A copy of the jpg file named by inode number (`file-<inode>.jpg`)
    - A copy of the jpg file named by the original file name.

## Project Description
You will recover jpg files (both undeleted and deleted) from an ext2 disk image, aiding in a fictional police investigation of bank robberies. This task requires a deep understanding of file system structures and utilities like mkfs and debugfs.

## Project Specification
- Your program, `runscan`, takes two arguments: an input disk image and an output directory.
- It should retrieve all jpg files by scanning inodes for jpg magic numbers: FF D8 FF E0, FF D8 FF E1, or FF D8 FF E8.
- For each jpg file found, generate the required output files in the specified directory.

## Part 1: Reconstructing JPG Files
Scan all inodes representing regular files for jpg magic numbers and copy the content of identified jpg files to an output file named after the inode number.

## Part 2: Retrieving File Details
Scan directory data blocks for filenames corresponding to jpg inodes and copy the content of these files using the actual filename. Additionally, retrieve links count, file size, and owner's user ID for these files.
