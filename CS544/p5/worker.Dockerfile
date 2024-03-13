FROM p5-base
CMD /spark-3.5.0-bin-hadoop3/sbin/start-worker.sh spark://boss:7077 -c 1 -m 512M && sleep infinity