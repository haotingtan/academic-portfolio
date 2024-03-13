import time
import weather
from kafka import KafkaAdminClient, KafkaProducer
from kafka.admin import NewTopic
from kafka.errors import UnknownTopicOrPartitionError
from report_pb2 import Report
from datetime import datetime

broker = 'localhost:9092'
admin_client = KafkaAdminClient(bootstrap_servers=[broker])

try:
    admin_client.delete_topics(["temperatures"])
    print("Deleted topics successfully")
except UnknownTopicOrPartitionError:
    print("Cannot delete topic/s (may not exist yet)")

time.sleep(3) # Deletion sometimes takes a while to reflect

# TODO: Create topic 'temperatures' with 4 partitions and replication factor = 1
admin_client.create_topics([NewTopic(name="temperatures", num_partitions=4, replication_factor=1)])

print("Topics:", admin_client.list_topics())

producer = KafkaProducer(bootstrap_servers = [broker], acks = 'all', retries = 10)

for date, degrees in weather.get_next_weather(delay_sec=0.1):
    proto_report = Report(date = date, degrees = degrees)
    proto_value = proto_report.SerializeToString()
    month = datetime.strptime(date, "%Y-%m-%d").strftime("%B")
    producer.send('temperatures', key = month.encode('utf-8'), value = proto_value)
