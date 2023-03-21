FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=./build/libs/mileage-service-1.0.jar
COPY ${JAR_FILE} MileageService.jar
ENTRYPOINT ["java","-jar","MileageService.jar"]