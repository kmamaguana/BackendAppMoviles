# Etapa 1: Build
FROM eclipse-temurin:23-jdk AS build

WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

# Etapa 2: Imagen final
FROM eclipse-temurin:23-jre
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar
COPY .env .env  # Copiar el archivo .env al contenedor

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
