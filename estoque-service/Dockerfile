# Etapa 1: Build com Maven usando imagem pronta com JDK e Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copia o conteúdo do cliente-service para dentro da imagem
COPY . .

# Compila o projeto e ignora os testes
RUN mvn clean package -DskipTests

# Etapa 2: Execução com imagem leve do JDK
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copia o .jar gerado da etapa anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

