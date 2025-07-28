# Etapa de construcción
FROM eclipse-temurin:23-jdk as build

WORKDIR /app

# Copia todo el proyecto
COPY . .

# Permisos al gradlew
RUN chmod +x ./gradlew

# Compila sin ejecutar tests
RUN ./gradlew clean build -x test

# Etapa final
FROM eclipse-temurin:23-jre

WORKDIR /app

# Copia el JAR desde la etapa anterior
COPY --from=build /app/build/libs/*.jar app.jar

# Expone el puerto que usa tu app Ktor
EXPOSE 8089

# Comando para ejecutar
ENTRYPOINT ["java", "-jar", "app.jar"]
