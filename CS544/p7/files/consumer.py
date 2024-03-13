from kafka import KafkaConsumer, TopicPartition
from report_pb2 import Report
import sys, os, json
from datetime import datetime

def load_json(partition):
    stat_dict = {}
    with open(f"files/partition-{partition}.json", "r") as json_file:
        stat_dict = json.load(json_file)
    return stat_dict


def consume_data(partition_json, messages):
    for message in messages:
        report = Report()
        report.ParseFromString(message.value)
        month = message.key.decode('utf-8')
        if month not in partition_json.keys():
            partition_json[month] = {}
        if report.date[:4] not in partition_json[month].keys():
            partition_json[month][report.date[:4]] = {"count": 0,
                                                    "sum": 0.0,
                                                    "avg": 0.0,
                                                    "end": report.date,
                                                    "start": report.date
                                                    }
        end_date = datetime.strptime(partition_json[month][report.date[:4]]["end"], '%Y-%m-%d')
        start_date = datetime.strptime(partition_json[month][report.date[:4]]["start"], '%Y-%m-%d')
        current_date = datetime.strptime(report.date, '%Y-%m-%d')
        if current_date <= end_date and (partition_json[month])[report.date[:4]]["count"] != 0:
            continue

        (partition_json[month])[report.date[:4]]["count"] += 1
        (partition_json[month])[report.date[:4]]["sum"] += report.degrees
        (partition_json[month])[report.date[:4]]["avg"] = (partition_json[month])[report.date[:4]]["sum"] / (partition_json[month])[report.date[:4]]["count"]
                
        if current_date < start_date:
            (partition_json[month])[report.date[:4]]["start"] = report.date
        if current_date > end_date:
            (partition_json[month])[report.date[:4]]["end"] = report.date


def main():
    if len(sys.argv) < 2 or len(sys.argv) > 5:
        print("wrong number of command-line arguments")
        exit(1)

    partitions = []
    for i in range(1, len(sys.argv)):
        try:
            partition = int(sys.argv[i])
            if partition < 0 or partition > 3:
                print("invalid partition number")
                exit(1)
            partitions.append(partition)
            if not os.path.exists(f"files/partition-{partition}.json"):
                with open(f"files/partition-{partition}.json", 'w') as json_file:
                    dict = {
                        "partition": partition,
                        "offset": 0
                    }
                    json.dump(dict, json_file, indent=4)
        except Exception as e:
            print(str(e))
            exit(1)

    broker = 'localhost:9092'
    consumer = KafkaConsumer(bootstrap_servers=[broker])
    assignment = [TopicPartition('temperatures', partition) for partition in partitions]
    consumer.assign(assignment)

    overall_resp = {}
    for partition in partitions:
        json_content = load_json(partition)
        if json_content == {}:
            print("error: empty json file")
            exit(1)
        consumer.seek(TopicPartition('temperatures', partition), json_content["offset"])
        overall_resp[partition] = json_content
    
    while True:
        batch = consumer.poll(1000)
        for topic_partition, messages in batch.items():
            partition_json = overall_resp[topic_partition.partition]
            consume_data(partition_json, messages)
            partition_json["offset"] = consumer.position(topic_partition)
            partition_num = partition_json["partition"]
            path = f"files/partition-{partition_num}.json"
            path2 = path + ".tmp"
            with open(path2, "w") as f:
                json.dump(partition_json, f, indent=4)
                os.rename(path2, path)
            
            overall_resp[topic_partition.partition] = partition_json


if __name__ == "__main__":
    main()