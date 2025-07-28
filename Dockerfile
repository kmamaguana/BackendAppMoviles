# Etapa de construcción
FROM eclipse-temurin:23-jdk as build

WORKDIR /app

# Copia todo el proyecto
COPY . .

# Da permisos de ejecución a gradlew
RUN chmod +x ./gradlew

# Compila sin ejecutar tests
RUN ./gradlew clean build -x test

# Etapa final
FROM eclipse-temurin:23-jre

WORKDIR /app

# Copia el JAR desde la etapa anterior
COPY --from=build /app/build/libs/*.jar app.jar

# Copia explícitamente los recursos por si no están embebidos en el JAR
COPY src/main/resources /app/resources

# Expone el puerto que usa tu app Ktor
EXPOSE 8089

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]