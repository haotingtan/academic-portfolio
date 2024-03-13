# P7: Kafka, Weather Data

## Overview

For this project, imagine a scenario where you are receiving daily
weather data for a given location. Your task is to populate this data
into a Kafka stream using a *producer* Python program. A *consumer*
Python program consumes data from the stream to produce JSON files
with summary stats, for use on a web dashboard (you don't need to
build the dashboard yourself). Later in the project, you will also
be visualizing some of the data collected by the consumer.

For simplicity, we use a single Kafka broker instead of using a
cluster. A single producer will generate weather data (max temperature) 
in an infinite loop at an accelerated rate of 1 day per 0.1 seconds 
(you can change this during debugging). Finally, consumers will be 
different processes, launching from the same Python program.

Learning objectives:
* write code for Kafka producers and consumers
* apply streaming techniques to achive "exactly once" semantics
* use manual and automatic assignment of Kafka topics and partitions


## Container setup

Start by creating a `files` directory in your repository. 
Your Python programs and generated files must be stored in this directory.
Next, build a `p7` docker image with Kafka installed using the provided Dockerfile.
Run the Kafka broker in the background using:

```
docker run -d -v ./files:/files --name=p7 p7
```

## Part 1: Kafka Producer

### Topic Initialization

Create a `files/producer.py` that creates a `temperatures` topic with 4
partitions and 1 replica. If the topic already existed, it should
first be deleted.

### Weather Generation

Create a KafkaProducer to send the reports to the `temperatures` topic.

For the Kafka message's value, encode the message as a gRPC protobuf.  
For this, you'll need to create a protobuf file `report.proto` in `files` 
with a `Report` message having the following fields, and build it to get 
a `???_pb2.py` file (review P3 for how to do this if necessary):

* string **date** (format "YYYY-MM-DD") - Date of the observation
* double **degrees**: Observed max-temperature on this date


## Part 2: Kafka Debug Consumer

Create a `files/debug.py` program that initializes a KafkaConsumer. It
could be in a consumer group named "debug".

The consumer should subscribe to the "temperatures" topic; let the
broker automatically assign the partitions.

The consumer should NOT seek to the beginning. The consumer should
loop over messages forever, printing dictionaries corresponding to
each message, like the following:

```
...
{'partition': 2, 'key': 'December', 'date': '2000-12-26', 'degrees': 31.5235}
{'partition': 2, 'key': 'December', 'date': '2000-12-27', 'degrees': 35.5621}
{'partition': 2, 'key': 'December', 'date': '2000-12-28', 'degrees': 4.6093}
{'partition': 2, 'key': 'December', 'date': '2000-12-29', 'degrees': 26.3698}
{'partition': 2, 'key': 'December', 'date': '2000-12-30', 'degrees': 41.9125}
{'partition': 2, 'key': 'December', 'date': '2000-12-31', 'degrees': 46.1511}
{'partition': 2, 'key': 'January', 'date': '2001-01-01', 'degrees': 40.391}
...
```

Use your `debug.py` to verify your producer is writing to the stream
as expected.

## Part 3: Kafka Stats Consumer

Now you'll write a `files/consumer.py` that computes stats on the
`temperatures` topic, outputing results to JSON files after each
batch.

Overview:
* there are 12 months but only 4 partitions, so naturally some partitions will correspond to data from multiple months
* each partition will correspond to one JSON file named `partition-N.json` (where N is the partition number), so there will be 4 JSON files
* we might launch fewer than 4 consumer.py processes, so each process should be capable of keeping multiple JSON files updated


### Offset Checkpointing

Your consumer should have an infinite loop that keeps requesting
messages batches for each assigned partitition.

After processing the messages in a partition of a batch, your consumer
should check the current offset on the partition, use that to update
the "offset" field in the partition dictionary, and write the
partition out to the appropriate `partition-N.json` file.

### Atomic Writes

Be sure to write your JSON files atomically.


### Statistics

In addition to recording `partition` and `offset`, each
`partition-N.json` file should have a key for each month seen in that
partition; the corresponding value should be a dictionary with years
as keys.  Each year will correspond to yet another dictionary with
stats about that month/year combination.

That stats for each month/year combination should include:

* `count`: the number of days for which data is available.
* `sum`: sum of temperatures seen so far (yes, this is an odd metric by itself)
* `avg`: the `sum/count`. This is the only reason we record the sum - so we can recompute the average on a running basis without having to remember and loop over all temperatures each time the file is updated
* `start`: the date of the *first* measurement for the corresponding
month and year combination
* `end`: the date of the *last* measurement for the corresponding
month and year combination

## Part 4: Plotting Stats

Create a `plot.py` program that we can run like this:

```
docker exec -it p7 python3 /files/plot.py
```

It should produce a `files/month.svg` file that has the average max-temperature
