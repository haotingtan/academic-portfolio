import grpc
import torch
import threading
import modelserver_pb2
import modelserver_pb2_grpc

from concurrent import futures
from collections import OrderedDict

lock = threading.Lock()

class PredictionCache:
    def __init__(self, max_cache_size=10):
        global lock
        with lock:
            self.coefs = torch.zeros(1, 1, dtype=torch.float32)
            self.max_cache_size = max_cache_size
            self.cache = OrderedDict()
    
    def SetCoefs(self, coefs):
        global lock
        with lock:
            self.coefs = coefs
            self.cache.clear()
    
    def Predict(self, X):
        X = torch.round(X, decimals=4)
        X_tuple = tuple(X.flatten().tolist())
        
        global lock
        with lock:
            if X_tuple in self.cache:
                y = self.cache[X_tuple]
                flag = True
                # Move the accessed item to the end to mark it as most recently used
                self.cache.move_to_end(X_tuple)
            else:
                y = X @ self.coefs
                if len(self.cache) >= self.max_cache_size:
                    # Remove the least recently used item (the first item in the OrderedDict)
                    removed_X = next(iter(self.cache))
                    self.cache.pop(removed_X)
                self.cache[X_tuple] = y
                flag = False
        
        return y, flag


class ModelServer(modelserver_pb2_grpc.ModelServerServicer):
    def __init__(self):
        self.prediction_cache = PredictionCache()
    
    def SetCoefs(self, request, context):
        try:
            coefs = request.coefs
            coefs_tensor = torch.tensor(coefs, dtype = torch.float32)
            self.prediction_cache.SetCoefs(coefs_tensor)
            return modelserver_pb2.SetCoefsResponse(error = "")
        except Exception as e:
            return modelserver_pb2.SetCoefsResponse(error = str(e))
        
    def Predict(self, request, context):
        try:
            X = request.X
            X_tensor = torch.tensor([X], dtype = torch.float32)
            y, hit = self.prediction_cache.Predict(X_tensor)

            response = modelserver_pb2.PredictResponse(
                y = float(y),
                hit = hit,
                error = ""
            )
            return response
        except Exception as e:
            return modelserver_pb2.PredictResponse(error = str(e))

def main():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=4), options=(('grpc.so_reuseport', 0),))
    modelserver_pb2_grpc.add_ModelServerServicer_to_server(ModelServer(), server)
    server.add_insecure_port("[::]:5440", )
    server.start()
    server.wait_for_termination()

if __name__ == "__main__":
    main()