# P3: Model Server

## Overview

In the last project, you trained a simple PyTorch model using `SGD`. Now, you'll write a program that can load a model (similar to the one from P2) and use it to make predictions upon request (this kind of program is called a _model server_).

Your model server will use multiple threads and cache the predictions (to save effort when the server is given the same inputs repeatedly).

You'll start by writing your code in a Python class and calling the methods in it. By the end, though, your model server will run in a Docker container and receive requests over a network (via gRPC calls).

Learning objectives:

-   Use threads and locks correctly
-   Cache expensive compute results with an LRU policy
-   Measure performance statistics like cache hit rate
-   Communicate between clients and servers via gRPC


## Part 1: Prediction Cache

### `PredictionCache` class

Write a class called `PredictionCache` with two methods: `SetCoefs(coefs)` and `Predict(X)` in a file called `server.py`.

`SetCoefs` will store `coefs` in the PredictionCache object; `coefs` will be a PyTorch tensor containing a vertical vector of `float32`s.

`Predict` will take a 2D tensor and use it to predict `y` values (which it will return) using the previously set `coefs`, like this:

```
y = X @ coefs
```


### Caching

Add code for an LRU cache to your `PredictionCache` class. Requirements:

-   `Predict` should round the X values to 4 decimal places before using them for anything (https://pytorch.org/docs/stable/generated/torch.round.html); the idea is to be able to use cached results for inputs that are approximately the same
-   The cache should hold a maximum of 10 entries
-   Whenever `SetCoefs` is called, _invalidate_ the cache (meaning clear out all the entries in the cache) because we won't expect the same predictions for the same inputs now that the model itself has changes
-   The second value returned by `Predict` should indicate whether there was a hit


### Locking

There will eventually be multiple threads calling methods in `PredictionCache` simultaneously, so add a lock.

The lock should:

-   be held when any shared data (for example, attributes in the class) are modified
-   get released at the end of each call, even if there is an exception

## Part 2: Model Server

### Protocol

Create a file called `modelserver.proto` containing a service called `ModelServer`.
Specify `syntax="proto3";` at the top of your file.
`ModelServer` will contain 2 RPCs:

1. `SetCoefs`
    - `Request`: `coefs` (`repeated float`)
    - `Response`: `error` (`string`)
2. `Predict`
    - `Request`: `X` (`repeated float`)
    - `Response`: `y` (`float`), `hit` (`bool`), and `error` (`string`)


### Server

Add a `ModelServer` class to `server.py` that inherits from `modelserver_pb2_grpc.ModelServerServicer`.

`ModelServer` should override the two methods of `ModelServerServicer` and use a `PredictionCache` to help calculate the answers.
You'll need to manipulate the data to translate back and forth between the `repeated float` values from gRPC and the tensors in the shapes needed by `PredictionCache`.

The `error` fields should contain the empty string `""` when all is well, or an error message that can help you debug when there was an exception or other issue (otherwise exceptions happening on the server side won't show up anywhere, which makes troubleshooting difficult).


## Part 3: Client

Write a gRPC client named `client.py` that can be run like this:

```
python3 client.py <PORT> <COEF> <THREAD1-WORK.csv> <THREAD2-WORK.csv> ...
```

For example, say you run `python3 client.py 5440 "1.0,2.0,3.0" x1.csv x2.csv x3.csv`.

For this example, your client should do the following, in order:

1. Connect to the server at port `5440`.
2. Call `SetCoef` with [1.0,2.0,3.0].
3. Launch three threads, each responsible for one of the 3 CSV files.
4. Each thread should loop over the rows in its CSV files. Each row will contain a list of floats that should be used to make a `Predict` call to the server. The threads should collect statistics about the numbers of hits and misses.
5. The main thread should call `join` to wait until the 3 threads are finished.

The client can print other stuff, but its very last line of output should be the overall hit rate. For example, if the hit and total counts for the three threads are 1/1, 0/1, and 3/8, then the overall hit rate would be (1+0+3) / (1+1+8) = 0.4.

## Part 4: Deployment

You should write a `Dockerfile` to build an image with everything needed to run both your server and client. Your Docker image should:

-   Build via `docker build -t p3 .`
-   Run via `docker run -p 127.0.0.1:54321:5440 p3` (i.e., you can map any external port to the internal port of 5440)

You should then be able to run the client outside of the container (using port 54321), or use a `docker exec` to enter the container and run the client with port 5440.
