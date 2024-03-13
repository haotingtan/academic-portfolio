default:
	docker rm p7 -f
	docker run -d -v ./files:/files --name=p7 p7
	sleep 3
	docker exec -d p7 python3 /files/producer.py

build:
	docker build . -t p7

consumer:
	docker exec -it p7 python3 /files/consumer.py 0 2

plot:
	docker exec -it p7 python3 /files/plot.py

debug:
	docker exec -it p7 python3 /files/debug.py
	
clean:
	rm -rf files/*.json