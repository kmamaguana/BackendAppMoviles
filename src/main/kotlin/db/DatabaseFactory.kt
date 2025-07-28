package com.example.db

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun connectToDatabase() {
    val jdbcUrl = "jdbc:postgresql://localhost:5432/vetapp"
    val driver = "org.postgresql.Driver"
    val user = "postgres"
    val password = "12345"

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
