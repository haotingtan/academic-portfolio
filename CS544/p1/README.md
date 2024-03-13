# P1: Counting Loans with Dockerized Shell Script

## Overview

In this project, you'll setup your programming environment for the
first part of the semester (virtual machine, Docker).  You'll practice
writing some shell commands to download a zipped file and search
(grep) through its contents.  You'll automate these steps with a shell
script.  The shell script may depend on other programs (like `unzip`),
so you'll deploy it as a Docker image with the necessary installs.

Learning objectives:
* deploy a virtual machine in the cloud
* follow a complicated series of steps to install Docker
* write a shell script to automate several bash commands
* bundle a shell script up as a Docker image/container


## Part 1: Virtual Machine Setup

We'll use Google's Cloud (GCP) for our virtual machines.  

Setup a virtual machine that you'll use for the first few projects
(we'll eventually delete it and create a more powerful one for some of
the later projects).

When you're done, check that you have the correct Operating System and
CPU with `cat /etc/os-release` and `lscpu`.  Save the outputs to hand in too:

```
cat /etc/os-release > os.txt
lscpu > cpu.txt
```

## Part 2: Docker Install

Carefully follow the directions here to install Docker 24.0.5 and Compose 2.20.2 on your virtual machine

Create some more files so we can check your Docker install:

```
docker version > docker.txt
docker compose version > compose.txt
```

## Part 3: Shell Script

This zip file contains a CSV file with data about loan applications in
WI: https://pages.cs.wisc.edu/~harter/cs544/data/hdma-wi-2021.zip.

Try running some shell commands to download the zip, extract the
contents, and print how many lines contain the text "Multifamily"
(case sensitive) -- it's OK if you print other additional output too.

Now, combine these commands in a `count.sh` file; the script should
have a shebang line so that the following runs with bash:

```sh
./count.sh
```

## Part 4: Docker Image

Create a `Dockerfile` that starts from a base image of your choosing
and includes your `count.sh` file.  The Dockerfile should do any
installs needed for your script to run.

