val logback_version = "1.4.14"
val ktor_version = "3.2.1"
val exposed_version = "0.41.1"
val postgresql_version = "42.6.0"
val kotlinx_serialization_version = "1.6.0"
val bcrypt_version = "0.9.0"

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
    // Ktor core y Netty
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-config-yaml:$ktor_version")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // Exposed ORM
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")

    // PostgreSQL Driver
    implementation("org.postgresql:postgresql:$postgresql_version")

    // Seguridad y autenticación
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")
    implementation("at.favre.lib:bcrypt:$bcrypt_version")

    // Serialización
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinx_serialization_version")

    // Testing
    testImplementation("io.ktor:ktor-server-test-host-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    //chatbot
    implementation("io.ktor:ktor-client-core:2.3.3")
    implementation("io.ktor:ktor-client-cio:2.3.3")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.3")

    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")

    // Agrega en tu archivo build.gradle.kts:
    implementation("io.ktor:ktor-client-core:2.3.4")
    implementation("io.ktor:ktor-client-cio:2.3.4")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.4")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
}
