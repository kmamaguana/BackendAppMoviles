package com.example.utils

import com.example.repository.impl.ClienteRepositoryImpl
import com.example.dto.ClienteCreateUpdateDTO

/**
 * Test simple para verificar que el soft delete funciona correctamente
 * Este archivo se puede ejecutar manualmente para probar la funcionalidad
 */
object SoftDeleteTest {
    
    @JvmStatic
    fun main(args: Array<String>) {
        println("=== Test de Soft Delete ===")
        
        val repository = ClienteRepositoryImpl()
        
        // Obtener todos los clientes activos
        println("\n--- Clientes Activos ---")
        val clientesActivos = repository.findAll()
        println("Total de clientes activos: ${clientesActivos.size}")
        clientesActivos.forEach { cliente ->
            println("- ${cliente.nombre} (ID: ${cliente.id}, Activo: ${cliente.activo})")
        }
        
        if (clientesActivos.isNotEmpty()) {
            val primerCliente = clientesActivos.first()
            println("\n--- Probando Soft Delete ---")
            println("Cliente a desactivar: ${primerCliente.nombre} (ID: ${primerCliente.id})")
            
            // Realizar soft delete
            val resultado = repository.deleteById(primerCliente.id)
            println("Resultado del soft delete: $resultado")
            
            // Verificar que ya no aparece en la lista de activos
            println("\n--- Verificando Lista Actualizada ---")
            val clientesDespues = repository.findAll()
            println("Total de clientes activos después: ${clientesDespues.size}")
            
            val clienteDesactivado = clientesDespues.find { it.id == primerCliente.id }
            if (clienteDesactivado == null) {
                println("✅ Cliente desactivado correctamente - No aparece en la lista")
            } else {
                println("❌ Error: Cliente aún aparece en la lista")
            }
        } else {
            println("No hay clientes para probar el soft delete")
        }
        
        println("\n=== Fin del Test ===")
    }
} 