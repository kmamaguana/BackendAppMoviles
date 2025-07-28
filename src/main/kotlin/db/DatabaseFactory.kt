package com.example.db

import io.github.cdimascio.dotenv.dotenv
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun connectToDatabase() {
    val dotenv = dotenv() // Esto carga automáticamente el .env

    val jdbcUrl = dotenv["DB_URL"]
    val driver = dotenv["DB_DRIVER"]
    val user = dotenv["DB_USER"]
    val password = dotenv["DB_PASSWORD"]

    Database.connect(jdbcUrl, driver, user, password)

    // Ejecutar migraciones automáticamente
    transaction {
        SchemaUtils.createMissingTablesAndColumns(
            Usuarios, ReseteoContrasena, Clientes, Mascotas, Servicios,
            Citas, CitaServicios, HistorialMedico, Categorias, Productos,
            Carrito, Ordenes, OrdenDetalles, ReportesAdmin, HistorialCliente,
            NivelesFidelidad
        )
    }

    // Ejecutar migraciones personalizadas
    DatabaseMigration.runMigrations()
}
