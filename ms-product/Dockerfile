FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -Dmaven.test.skip

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

RUN mkdir -p /app/data/imports /app/data/imports/finished /app/data/imports/pending /app/data/imports/waiting

COPY data/imports/pending/products.csv data/imports/pending

ENTRYPOINT ["java", "-jar", "app.jar"]