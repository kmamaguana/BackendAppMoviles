val logback_version = "1.4.14"
val ktor_version = "3.2.1"
val exposed_version = "0.41.1"
val postgresql_version = "42.6.0"
val kotlinx_serialization_version = "1.6.0"
val bcrypt_version = "0.9.0"
val ktor_client_version = "2.3.4"
val dotenv_version = "6.4.1"

plugins {
    kotlin("jvm") version "2.1.10"
    kotlin("plugin.serialization") version "2.1.10"
    id("io.ktor.plugin") version "3.2.1"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    mavenCentral()
}

dependencies {
    // Ktor Server Core
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-config-yaml:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // Exposed ORM
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")

    // PostgreSQL Driver
    implementation("org.postgresql:postgresql:$postgresql_version")

    // Serialización
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinx_serialization_version")

    // Seguridad
    implementation("at.favre.lib:bcrypt:$bcrypt_version")

    // Ktor Client (para chatbot o peticiones HTTP)
    implementation("io.ktor:ktor-client-core:$ktor_client_version")
    implementation("io.ktor:ktor-client-cio:$ktor_client_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_client_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_client_version")

    // Variables de entorno
    implementation("io.github.cdimascio:dotenv-kotlin:$dotenv_version")

    // Testing
    testImplementation("io.ktor:ktor-server-test-host-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}