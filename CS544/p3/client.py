import grpc
import sys
import threading
import modelserver_pb2
import modelserver_pb2_grpc
import csv
from queue import Queue

class PredictionClient:
    def __init__(self, server_addr, coefs, result_queue):
        self.coefs = coefs
        self.channel = grpc.insecure_channel(server_addr)
        self.stub = modelserver_pb2_grpc.ModelServerStub(self.channel)
        self.result_queue = result_queue

    def SetCoefs(self):
        request = modelserver_pb2.SetCoefsRequest(coefs = self.coefs)
        response = self.stub.SetCoefs(request)
        if response.error != "":
            print(f"Error ocurred when set coefs: {response.error}")
            sys.exit(1)
    
    def Predict(self, file):
        hits, misses = 0, 0 
        with open(file, "r") as f:
            csv_reader = csv.reader(f)
            for row in csv_reader:
                X = [float(entry) for entry in row]
                request = modelserver_pb2.PredictRequest(X = X)
                response = self.stub.Predict(request)
                if response.error != "":
                    print(f"Error ocurred when predict: {response.error}")
                    sys.exit(1)
                if response.hit == True:
                    hits += 1
                else:
                    misses += 1
        self.result_queue.put((hits, misses))  # Put the results in the queue

def main():
    threads = []
    total_hits, total_misses = 0, 0

    server_addr = f"localhost:{sys.argv[1]}"
    coefs = [float(coef) for coef in sys.argv[2].split(",")]
    files = sys.argv[3:]

    result_queue = Queue()  # Create a queue to collect results

    client = PredictionClient(server_addr, coefs, result_queue)
    client.SetCoefs()

    for file in files:
        thread = threading.Thread(target=client.Predict, args=(file,))
        thread.start()
        threads.append(thread)
    
    for thread in threads:
        thread.join()

    # Collect results from the queue
    while not result_queue.empty():
        hits, misses = result_queue.get()
        total_hits += hits
        total_misses += misses
    
    if total_hits + total_misses == 0:
        hit_rate = 0
    else:
        hit_rate = total_hits/(total_hits + total_misses)

    print(hit_rate)

if __name__ == "__main__":
    main()
