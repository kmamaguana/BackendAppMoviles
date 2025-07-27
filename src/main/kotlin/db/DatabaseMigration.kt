package com.example.db

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory

/**
 * Clase para manejar migraciones de la base de datos
 * Se ejecuta automáticamente al iniciar la aplicación
 */
object DatabaseMigration {
    private val logger = LoggerFactory.getLogger(DatabaseMigration::class.java)
    
    /**
     * Ejecuta todas las migraciones necesarias
     */
    fun runMigrations() {
        logger.info("Iniciando migraciones de base de datos...")
        
        try {
            addActivoColumnToClientes()
            logger.info("Migraciones completadas exitosamente")
        } catch (e: Exception) {
            logger.error("Error durante las migraciones: ${e.message}", e)
            throw e
        }
    }
    
    /**
     * Agrega la columna 'activo' a la tabla clientes si no existe
     */
    private fun addActivoColumnToClientes() {
        transaction {
            // Verificar si la columna activo existe
            val columnExists = try {
                exec("SELECT activo FROM clientes LIMIT 1") { }
                true
            } catch (e: Exception) {
                false
            }
            
            if (!columnExists) {
                logger.info("Agregando columna 'activo' a la tabla clientes...")
                
                // Agregar la columna activo
                exec("ALTER TABLE clientes ADD COLUMN activo BOOLEAN DEFAULT TRUE")
                
                // Actualizar registros existentes
                exec("UPDATE clientes SET activo = TRUE WHERE activo IS NULL")
                
                // Crear índice para mejorar rendimiento
                try {
                    exec("CREATE INDEX idx_clientes_activo ON clientes(activo)")
                } catch (e: Exception) {
                    logger.warn("No se pudo crear el índice (puede que ya exista): ${e.message}")
                }
                
                logger.info("Columna 'activo' agregada exitosamente")
            } else {
                logger.info("La columna 'activo' ya existe en la tabla clientes")
            }
        }
    }
} 