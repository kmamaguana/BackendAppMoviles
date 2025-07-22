 package com.example

import com.example.db.*
import com.typesafe.config.ConfigFactory
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    embeddedServer(Netty, port = 8089, module = Application::module).start(wait = true)
}

fun Application.module() {
    val config = ConfigFactory.load()
    println("JWT SECRET (directo): " + config.getString("jwt.secret"))
    println("JWT ISSUER (directo): " + config.getString("jwt.issuer"))
    println("JWT AUDIENCE (directo): " + config.getString("jwt.audience"))
    println("JWT EXPIRES (directo): " + config.getString("jwt.expiresInMs"))
    environment.config.toMap().forEach { (k, v) ->
        println("CONFIG: $k = $v")
    }
    install(ContentNegotiation) {
        json()
    }
    // Configura rutas (define en otro archivo)
    configureRouting()

    // Conecta a la base de datos
    connectToDatabase()

    // Crea las tablas en la base de datos si no existen
    transaction {
        SchemaUtils.create(
            Usuarios,
            ReseteoContrasena,
            Clientes,
            Mascotas,
            Servicios,
            Citas,
            CitaServicios,
            HistorialMedico,
            Categorias,
            Productos,
            Carrito,
            Ordenes,
            OrdenDetalles,
            ReportesAdmin,
            HistorialCliente,
            NivelesFidelidad
        )
    }
}
