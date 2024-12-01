FROM openjdk:17-slim
WORKDIR /ms-customer

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ms-customer.jar

ENTRYPOINT ["java", "-jar", "ms-customer.jar"]
EXPOSE 8092