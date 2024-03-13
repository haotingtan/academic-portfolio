FROM ubuntu:latest

RUN apt-get update
RUN apt-get install -y wget unzip

COPY ./count.sh /usr/local/bin/

RUN chmod +x /usr/local/bin/count.sh

CMD ["count.sh"]
