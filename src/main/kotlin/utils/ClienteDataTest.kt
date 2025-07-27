package com.example.utils

import com.example.repository.impl.ClienteRepositoryImpl
import com.example.services.ClienteService

/**
 * Test simple para verificar que el backend está devolviendo datos correctos de clientes
 * Este archivo se puede ejecutar manualmente para probar la funcionalidad
 */
object ClienteDataTest {
    
    @JvmStatic
    fun main(args: Array<String>) {
        println("=== Test de Datos de Clientes ===")
        
        val repository = ClienteRepositoryImpl()
        val service = ClienteService(repository)
        
        // Obtener todos los clientes
        println("\n--- Obteniendo Clientes ---")
        val clientes = service.obtenerTodos()
        println("Total de clientes: ${clientes.size}")
        
        if (clientes.isNotEmpty()) {
            println("\n--- Detalles de Clientes ---")
            clientes.forEachIndexed { index, cliente ->
                println("Cliente ${index + 1}:")
                println("  ID: ${cliente.id}")
                println("  Usuario ID: ${cliente.usuarioId}")
                println("  Nombre: ${cliente.nombre}")
                println("  Teléfono: '${cliente.telefono}' (longitud: ${cliente.telefono.length})")
                println("  Dirección: '${cliente.direccion}' (longitud: ${cliente.direccion.length})")
                println("  Activo: ${cliente.activo}")
                println()
            }
        } else {
            println("❌ No hay clientes en la base de datos")
        }
        
        // Verificar si hay datos de prueba
        println("\n--- Verificando Datos de Prueba ---")
        val clientesConTelefono = clientes.filter { it.telefono.isNotEmpty() }
        val clientesConDireccion = clientes.filter { it.direccion.isNotEmpty() }
        
        println("Clientes con teléfono: ${clientesConTelefono.size}/${clientes.size}")
        println("Clientes con dirección: ${clientesConDireccion.size}/${clientes.size}")
        
        if (clientesConTelefono.isEmpty() || clientesConDireccion.isEmpty()) {
            println("⚠️  ADVERTENCIA: Algunos clientes no tienen teléfono o dirección")
            println("Esto puede causar que las descripciones no se muestren correctamente")
        }
        
        println("\n=== Fin del Test ===")
    }
} 