## Kafka Command 정리
### 실행 command
```
[zookeeper 구동]
bin\windows\zookeeper-server-start.bat config\zookeeper.properties

[kafka 구동]
bin\windows\kafka-server-start.bat config\server.properties
```

### Topic Command
```
[topic 목록 확인]
bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --list

[topic 정보 확인]
bin\windows\kafka-topics.bat --describe --topic 토픽이름 --bootstrap-server localhost:9092

[topic 생성]
bin\windows\kafka-topics.bat --create --topic 토픽이름 --bootstrap-server localhost:9092 --partitions 1

[topic 삭제]
server.properties 에서 
delete.topic.enable = true 로 설정해야함.
bin\windows\kafka-topics.bat --delete --zookeeper zookeeper:2181 --topic 토픽이름
```

### Post & Pull Command
```
[프로듀서 - post]
bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic 토픽이름

[컨슈머 - pull]
bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic 토픽이름 --from-beginning
```