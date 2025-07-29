# Etapa de construcción
FROM eclipse-temurin:23-jdk AS build

WORKDIR /app

# Copiar todos los archivos del proyecto
COPY . .

# Permisos de ejecución para gradlew
RUN chmod +x ./gradlew

# Compilar sin ejecutar tests
RUN ./gradlew clean build -x test

# Etapa de ejecución final
FROM eclipse-temurin:23-jre

WORKDIR /app

# Copiar el JAR generado
COPY --from=build /app/build/libs/*.jar app.jar

# Puerto expuesto (opcional si ya lo manejas desde el host)
EXPOSE 8089

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar", "-port=8089"]
