# P6: Cassandra, Weather Data

## Overview

NOAA (National Oceanic and Atmospheric Administration) collects
weather data from all over the world.  In this project, you'll explore
how you could (1) store this data in Cassandra, (2) write a server for
data collection, and (3) analyze the collected data via Spark. You can
find the datasets we are going to be using in the `datasets` directory
of `p6`. 

We'll also explore read/write availability tradeoffs.  When always
want sensors to be able to upload data, but it is OK if we cannot
always read the latest stats (we prefer an error over inconsistent
results).

Learning objectives:
* create Cassandra schemas involving partition keys and cluster keys
* move data between Spark and Cassandra
* configure queries to achieve a tradeoff between read and write availability

## Cluster Setup

We provide the Dockerfile and docker-compose.yml for this project.
You can run the following:

* `docker build . -t p6-base`
* `docker compose up -d`

The `p6-db-1` container will be running JupyterLab as well.  Check the
Docker port forwarding configuration and setup a tunnel to connect via
your browser.

Create a notebook `p6.ipynb` in the `/nb` directory for your work.

## Part 1: Station Data

#### Schema Creation

Connect to the Cassandra cluster using this code:

```python
from cassandra.cluster import Cluster
cluster = Cluster(['p6-db-1', 'p6-db-2', 'p6-db-3'])
cass = cluster.connect()
```

Then write code to do the following:

* drop a `weather` keyspace if it already exists
* create a `weather` keyspace with 3x replication
* inside `weather`, create a `station_record` type containing two ints: `tmin` and `tmax`
* inside `weather`, create a `stations` table

The `stations` table should have four columns: `id` (text), `name` (text), `date` (date), `record` (weather.station_record):

* `id` is a partition key and corresponds to a station's ID (like 'USC00470273')
* `date` is a cluster key, ascending
* `name` is a static field (because there is only one name per ID).  Example: 'UW ARBORETUM - MADISON'
* `record` is a regular field because there will be many records per station partition.

#### Q1: What is the Schema of `stations`?


#### Station Data

Create a local Spark session like this:

```python
from pyspark.sql import SparkSession
spark = (SparkSession.builder
         .appName("p6")
         .config('spark.jars.packages', 'com.datastax.spark:spark-cassandra-connector_2.12:3.4.0')
         .config("spark.sql.extensions", "com.datastax.spark.connector.CassandraSparkExtensions")
         .getOrCreate())
```


Use Spark and `SUBSTRING` to extract `ID`, `STATE`, and `NAME` from
`nb/ghcnd-stations.txt`.  

Filter your results to the state of Wisconsin, collect the rows in
your notebook so you can loop over them, and do an `INSERT` into your
`weather.stations` table for each station ID and name. 


#### Q2: what is the name corresponding to station ID `USW00014837`?

Write a Cassandra query to obtain the answer.

#### Q3: what is the token for the `USC00470273` station?

#### Q4: what is the first vnode token in the ring following the token for `USC00470273`?

Handle the case where the ring "wraps around" (meaning the row token is bigger than any vnode).

## Part 2: Weather Data

#### Server

Now you'll write gRPC-based `nb/server.py` file that receives
temperature data and records it to `weather.stations`.  You could
imagine various sensor devices acting as clients that make gRPC calls
to `server.py` to record data, but for simplicity we'll make the
client calls from `p6.ipynb`. 

Build the `station.proto` we provide to get `station_pb2.py` and
`station_pb2_grpc`

In `server.py`, implement the interface from
`station_pb2_grpc.StationServicer`.  RecordTemps will insert new
temperature highs/lows to `weather.stations`. `StationMax` will
return the maximum `tmax` ever seen for the given station.

#### Error Handling

Note that `RecordTempsReply` and `StationMaxReply` both have a string
field called `error`.

For a successful request, these should contain the empty string, `""`.

If executing a Cassandra statement raises a `cassandra.Unavailable`
except `e`, then the `error` should have a string like this:

```python
'need 3 replicas, but only have 2'
```

#### Running the server

Launch your server in the same container as your notebook.  There are
multiple ways you could do this -- one option is to run `docker exec`
from your VM, using a command like this:

```
docker exec -it p6-db-1 python3 /nb/server.py
```

#### Data Upload

Now in your `p6.ipynb`, unzip `records.zip` to get a `records.parquet` directory. Then use Spark to load this and re-arrange the data so that there is (a) one row per station/date combination, and (b) tmin and tmax columns. You can ignore other measurements.

Collect and loop over the results, making a call to the server with
for each row to insert the measurements to the database.

Change number types and date formats as necessary. Note that CQL requires that
you insert date data in `yyyy-mm-dd` format

#### Q5: what is the max temperature ever seen for station USW00014837?


## Part 3: Spark Analysis

#### `stations` view

Create a temporary view in Spark named `stations` that corresponds to
the `stations` table in Cassandra.


#### Q6: what tables/views are available in the Spark catalog?


#### Q7: what is the average difference between tmax and tmin, for each of the four stations that have temperature records?

## Part 4: Disaster Strikes

**Important:** run a `docker` command to kill the `p6-db-2` container.

#### Q8: what does `nodetool status` output?


#### Q9: if you make a `StationMax` RPC call, what does the `error` field contain in `StationMaxReply` reply?

Choose any station you like for the call.

#### Q10: if you make a `RecordTempsRequest` RPC call, what does `error` contain in the `RecordTempsReply` reply?
