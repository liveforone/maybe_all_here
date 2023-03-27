FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/item-service-1.0.jar
COPY ${JAR_FILE} ItemService.jar
ENTRYPOINT ["java","-jar","ItemService.jar"]