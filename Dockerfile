FROM openjdk:17-slim
WORKDIR /ms-shipping

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ms-shipping.jar

ENTRYPOINT ["java", "-jar", "ms-shipping.jar"]
EXPOSE 9090