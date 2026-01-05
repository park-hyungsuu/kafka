kafka 설치

참조 URL
https://cladren123.tistory.com/190


1. zookeeper 실행
C:\prj\kafka_2.13-3.8.1>bin\windows\zookeeper-server-start.bat config\zookeeper.properties

2. kafka 실행
C:\prj\kafka_2.13-3.8.1>bin\windows\kafka-server-start.bat config\server.properties

3. topic 생성
C:\prj\kafka_2.13-3.8.1>bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --topic test-topic

4. 생성되어진 topic 목록 조회
C:\prj\kafka_2.13-3.8.1>bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092

5. 생성되어진 topic에 메세지  보내기
C:\prj\kafka_2.13-3.8.1>bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic test-topic

5. topic에 있는메세지 가져오기
C:\prj\kafka_2.13-3.8.1>bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test-topic