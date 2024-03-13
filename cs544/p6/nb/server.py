import grpc
import station_pb2
import station_pb2_grpc
import cassandra
import pandas as pd
from cassandra import ConsistencyLevel
from cassandra.cluster import Cluster
from concurrent import futures


class Station(station_pb2_grpc.StationServicer):
    def __init__(self):
        self.cluster = Cluster(['p6-db-1', 'p6-db-2', 'p6-db-3'])
        self.cass = self.cluster.connect()
        self.cass.execute("use weather")
        self.insert_statement = self.cass.prepare("""
        INSERT INTO weather.stations (id, date, record)
        VALUES (?, ?, {tmin: ?, tmax: ?})
        """)
        self.insert_statement.consistency_level = ConsistencyLevel.ONE
        self.max_statement = self.cass.prepare("""
        SELECT id, MAX(record.tmax) as max FROM weather.stations WHERE id = ?
        """)
        # TODO record.tmax?
        self.max_statement.consistency_level = ConsistencyLevel.THREE

    def RecordTemps(self, request, context): 
        try:
            self.cass.execute(self.insert_statement, (request.station, request.date, request.tmin, request.tmax))
            return station_pb2.RecordTempsReply(error = "")
        except cassandra.Unavailable as e1:
            err_message = f'need {e1.required_replicas} replicas, but only have {e1.alive_replicas}'
            return station_pb2.RecordTempsReply(error = err_message)
        except cassandra.cluster.NoHostAvailable as e2:
            err_message = ''
            for value in e2.errors:
                if isinstance(value, cassandra.Unavailable):
                    err_message = f'need {value.required_replicas} replicas, but only have {value.alive_replicas}'
                    break
            return station_pb2.StationMaxReply(error = err_message)
        except Exception as e3:
            return station_pb2.RecordTempsReply(error = str(e3))

    def StationMax(self, request, context):
        try:
            max = pd.DataFrame(self.cass.execute(self.max_statement, (request.station,)))["max"][0]
            response = station_pb2.StationMaxReply(
                tmax = max,
                error = ""
            )
            return response
        except cassandra.Unavailable as e1:
            err_message = f'need {e1.required_replicas} replicas, but only have {e1.alive_replicas}'
            return station_pb2.StationMaxReply(error = err_message)
        except cassandra.cluster.NoHostAvailable as e2:
            err_message = ''
            for value in e2.errors:
                if isinstance(value, cassandra.Unavailable):
                    err_message = f'need {value.required_replicas} replicas, but only have {value.alive_replicas}'
                    break
            return station_pb2.StationMaxReply(error = err_message)
        except Exception as e3:
            return station_pb2.StationMaxReply(error = str(e3))


def main():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=4), options=(('grpc.so_reuseport', 0),))
    station_pb2_grpc.add_StationServicer_to_server(Station(), server)
    server.add_insecure_port("[::]:5440", )
    server.start()
    server.wait_for_termination()


if __name__ == "__main__":
    main()
