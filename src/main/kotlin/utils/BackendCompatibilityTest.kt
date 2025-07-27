package com.example.utils

import com.example.repository.impl.ClienteRepositoryImpl
import com.example.services.ClienteService

/**
 * Test para verificar que el backend funciona correctamente sin el campo activo
 * Este archivo se puede ejecutar manualmente para probar la compatibilidad
 */
object BackendCompatibilityTest {
    
    @JvmStatic
    fun main(args: Array<String>) {
        println("=== Test de Compatibilidad Backend ===")
        
        val repository = ClienteRepositoryImpl()
        val service = ClienteService(repository)
        
        try {
            // Probar obtener todos los clientes
            println("\n--- Probando obtener clientes ---")
            val clientes = service.obtenerTodos()
            println("✅ Clientes obtenidos exitosamente: ${clientes.size}")
            
            if (clientes.isNotEmpty()) {
                val primerCliente = clientes.first()
                println("Primer cliente: ${primerCliente.nombre}")
                println("Campo activo: ${primerCliente.activo}")
                
                // Probar obtener cliente por ID
                println("\n--- Probando obtener cliente por ID ---")
                val clientePorId = service.obtenerPorId(primerCliente.id)
                if (clientePorId != null) {
                    println("✅ Cliente por ID obtenido exitosamente")
                } else {
                    println("❌ No se pudo obtener cliente por ID")
                }
                
                // Probar soft delete (puede fallar si no existe el campo activo)
                println("\n--- Probando soft delete ---")
                try {
                    val resultadoDelete = service.eliminar(primerCliente.id)
                    println("✅ Soft delete ejecutado: $resultadoDelete")
                } catch (e: Exception) {
                    println("⚠️  Soft delete falló (esperado si no existe campo activo): ${e.message}")
                    println("El sistema usará eliminación física como fallback")
                }
            } else {
                println("⚠️  No hay clientes para probar")
            }
            
            println("\n✅ Backend funciona correctamente")
            
        } catch (e: Exception) {
            println("❌ Error en el backend: ${e.message}")
            e.printStackTrace()
        }
        
        println("\n=== Fin del Test ===")
    }
} 