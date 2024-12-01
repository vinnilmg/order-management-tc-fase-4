# Use uma imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho no container
WORKDIR /app

# Copie o arquivo JAR do Spring Boot para o diretório de trabalho no container
COPY target/ms-product-*.jar ms-product.jar

# Exponha a porta 8093
EXPOSE 8093

# Comando para executar o aplicativo
ENTRYPOINT ["java", "-jar", "ms-product.jar"]