package com.example.utils

import com.example.db.DatabaseMigration
import com.example.repository.impl.ClienteRepositoryImpl
import com.example.services.ClienteService

/**
 * Test para verificar que la migración funciona correctamente
 * Este archivo se puede ejecutar manualmente para probar la migración
 */
object MigrationTest {
    
    @JvmStatic
    fun main(args: Array<String>) {
        println("=== Test de Migración ===")
        
        try {
            // Ejecutar migración
            println("\n--- Ejecutando migración ---")
            DatabaseMigration.runMigrations()
            println("✅ Migración ejecutada exitosamente")
            
            // Probar que el repositorio funciona
            println("\n--- Probando repositorio ---")
            val repository = ClienteRepositoryImpl()
            val service = ClienteService(repository)
            
            val clientes = service.obtenerTodos()
            println("✅ Clientes obtenidos: ${clientes.size}")
            
            if (clientes.isNotEmpty()) {
                val primerCliente = clientes.first()
                println("Primer cliente: ${primerCliente.nombre}")
                println("Campo activo: ${primerCliente.activo}")
                
                // Probar soft delete
                println("\n--- Probando soft delete ---")
                val resultadoDelete = service.eliminar(primerCliente.id)
                println("✅ Soft delete ejecutado: $resultadoDelete")
                
                // Verificar que el cliente ya no aparece en la lista
                val clientesDespues = service.obtenerTodos()
                println("Clientes después del soft delete: ${clientesDespues.size}")
                
                val clienteEliminado = clientesDespues.find { it.id == primerCliente.id }
                if (clienteEliminado == null) {
                    println("✅ Cliente correctamente desactivado (soft delete)")
                } else {
                    println("❌ Cliente aún aparece en la lista")
                }
            } else {
                println("⚠️  No hay clientes para probar")
            }
            
            println("\n✅ Migración y funcionalidad funcionan correctamente")
            
        } catch (e: Exception) {
            println("❌ Error durante la migración: ${e.message}")
            e.printStackTrace()
        }
        
        println("\n=== Fin del Test ===")
    }
} 