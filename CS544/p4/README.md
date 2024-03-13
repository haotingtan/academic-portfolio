# P4: HDFS Replication

## Overview

HDFS can *partition* large files into blocks to share the storage
across many workers, and it can *replicate* those blocks so that data
is not lost even if some workers die.

In this project, you'll deploy a small HDFS cluster and upload a large
file to it, with different replication settings.  You'll write Python
code to read the file.  When data is partially lost (due to a node
failing), your code will recover as much data as possible from the
damaged file.

If you switched machines, remember to reinstall Docker and also to enable Docker to be run without sudo. Refer to P1 for instructions. 

Learning objectives:
* use the HDFS command line client to upload files
* use the webhdfs API (https://hadoop.apache.org/docs/r3.3.6/hadoop-project-dist/hadoop-hdfs/WebHDFS.html) to read files
* use PyArrow to read HDFS files
* relate replication count to space efficiency and fault tolerance


## Part 1: Deployment and Data Upload

#### Cluster

For this project, you'll deploy a small cluster of containers, one
with Jupyter, one with an HDFS NameNode, and two with HDFS DataNodes.

We have given you `docker-compose.yml` for starting the cluster, but
you need to build some images first.  Start with the following:

```
docker build . -f hdfs.Dockerfile -t p4-hdfs
docker build . -f notebook.Dockerfile -t p4-nb
```

The second image depends on the first one (`p4-hdfs`) allowing us to avoid repeating imports --you can see
this by checking the `FROM` line in "notebook.Dockerfile".

The compose file also needs `p4-nn` (NameNode) and `p4-dn` (DataNode)
images.  Create Dockerfiles for these that can be built like this:

```
docker build . -f namenode.Dockerfile -t p4-nn
docker build . -f datanode.Dockerfile -t p4-dn
```

Requirements:
* like `p4-nb`, both these should use `p4-hdfs` as a base
* `namenode.Dockerfile` should run two commands, `hdfs namenode -format` and `hdfs namenode -D dfs.namenode.stale.datanode.interval=10000 -D dfs.namenode.heartbeat.recheck-interval=30000 -fs hdfs://boss:9000`
* `datanode.Dockerfile` should just run `hdfs datanode -D dfs.datanode.data.dir=/var/datanode -fs hdfs://boss:9000`


#### Data Upload

Connect to JupyterLab running in the `p4-nb` container, and create a
notebook called `p4a.ipynb` in the "/nb" directory (we'll do some
later work in another notebook, `p4b.ipynb`). 

#### Q1: how many live DataNodes are in the cluster?


Write some code (Python or shell) that downloads
https://pages.cs.wisc.edu/~harter/cs544/data/hdma-wi-2021.csv if it
hasn't already been downloaded.

Next, use two `hdfs dfs -cp` commands to upload this same file to HDFS
twice, to the following locations:

* `hdfs://boss:9000/single.csv`
* `hdfs://boss:9000/double.csv`

In both cases, use a 1MB block size (`dfs.block.size`), and
replication (`dfs.replication`) of 1 and 2 for `single.csv` and
`double.csv`, respectively.


#### Q2: what are the logical and physical sizes of the CSV files?


## Part 2: WebHDFS

The documents here describe how we can interact with HDFS via web requests: https://hadoop.apache.org/docs/r3.3.6/hadoop-project-dist/hadoop-hdfs/WebHDFS.html.

By default, WebHDFS runs on port 9870. 

#### Q3: what is the file status for single.csv?

#### Q4: what is the location for the first block of single.csv?

#### Q5: how are the blocks of single.csv distributed across the two DataNode containers?

## Part 3: PyArrow

#### Q6: what are the first 10 bytes of single.csv?

#### Q7: how many lines of single.csv contain the string "Single Family"?

PyArrow's `NativeFile` implements the `RawIOBase` interface (even
though it is not a subclass):
https://docs.python.org/3/library/io.html#io.RawIOBase.

## Part 4: Disaster Strikes

Do the following:
* manually kill one of the DataNode containers with a `docker kill` command
* start a new notebook in Jupyter called `p4b.ipynb` -- use it for the remainder of your work

#### Q8: how many live DataNodes are in the cluster?


**Important** - Add the below line to a cell below q8 but before q9. This is for the autograder. You do not need to run it.
```
import time
time.sleep(30)
```
#### Q9: how are the blocks of single.csv distributed across the DataNode containers?

This is the same as Q5, but you'll need to do a little extra work.
When you make a request to the NameNode, check the status code
(`r.status_code`).  If it is 403, use "lost" as the key for your
count; otherwise, count as normal.


#### Q10: how many times does the text "Single Family" appear in the remaining blocks of single.csv?