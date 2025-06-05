# Etapa 1: Build da aplicação com Maven
FROM maven:3.5.0-openjdk-17-slim AS build
WORKDIR /app
COPY . /app
RUN mvn clean install -DskipTests

# Etapa 2: Criando a imagem final com o JAR executável
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/Back.jar /app/Back.jar
ENTRYPOINT ["java", "-jar", "Back.jar"]
