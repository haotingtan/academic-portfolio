FROM p4-hdfs
CMD hdfs datanode -D dfs.datanode.data.dir=/var/datanode -fs hdfs://boss:9000