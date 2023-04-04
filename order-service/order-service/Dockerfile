FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/order-service-1.0.jar
COPY ${JAR_FILE} OrderService.jar
ENTRYPOINT ["java","-jar","OrderService.jar"]