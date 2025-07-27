package com.example.utils

import com.example.db.Clientes
import com.example.db.Usuarios
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Test de diagnóstico para verificar el estado de la base de datos
 * Este archivo se puede ejecutar manualmente para diagnosticar problemas
 */
object DatabaseDiagnosticTest {
    
    @JvmStatic
    fun main(args: Array<String>) {
        println("=== Diagnóstico de Base de Datos ===")
        
        transaction {
            println("\n--- Verificando Tablas ---")
            
            // Verificar tabla usuarios
            val totalUsuarios = Usuarios.selectAll().count()
            println("👥 Total de usuarios: $totalUsuarios")
            
            if (totalUsuarios > 0) {
                val usuarios = Usuarios.selectAll().limit(3).map { 
                    "ID: ${it[Usuarios.id]}, Nombre: ${it[Usuarios.nombre]}, Email: ${it[Usuarios.email]}, Rol: ${it[Usuarios.rol]}"
                }
                println("📋 Primeros usuarios:")
                usuarios.forEach { println("  - $it") }
            }
            
            // Verificar tabla clientes
            val totalClientes = Clientes.selectAll().count()
            println("\n🏢 Total de clientes: $totalClientes")
            
            if (totalClientes > 0) {
                val clientes = Clientes.selectAll().limit(3).map { 
                    "ID: ${it[Clientes.id]}, UsuarioID: ${it[Clientes.usuarioId]}, Teléfono: ${it[Clientes.telefono]}, Dirección: ${it[Clientes.direccion]}, Activo: ${it[Clientes.activo]}"
                }
                println("📋 Primeros clientes:")
                clientes.forEach { println("  - $it") }
            }
            
            // Verificar JOIN
            println("\n--- Verificando JOIN ---")
            val joinResult = (Clientes innerJoin Usuarios).selectAll().limit(3)
            val joinCount = joinResult.count()
            println("🔗 Resultados del JOIN: $joinCount")
            
            if (joinCount > 0) {
                val joinData = joinResult.map { 
                    "ClienteID: ${it[Clientes.id]}, Usuario: ${it[Usuarios.nombre]}, Email: ${it[Usuarios.email]}, Activo: ${it[Clientes.activo]}"
                }
                println("📋 Datos del JOIN:")
                joinData.forEach { println("  - $it") }
            }
            
            // Verificar clientes activos
            println("\n--- Verificando Clientes Activos ---")
            val clientesActivos = Clientes.select { Clientes.activo eq true }.count()
            println("✅ Clientes activos: $clientesActivos")
            
            val clientesInactivos = Clientes.select { Clientes.activo eq false }.count()
            println("❌ Clientes inactivos: $clientesInactivos")
            
            // Verificar JOIN con filtro activo
            val joinActivos = (Clientes innerJoin Usuarios).select { Clientes.activo eq true }.count()
            println("🔗 JOIN con clientes activos: $joinActivos")
            
            // Verificar si hay problemas de relación
            println("\n--- Verificando Relaciones ---")
            val clientesSinUsuario = Clientes.selectAll().map { it[Clientes.usuarioId] }.distinct()
            val usuariosExistentes = Usuarios.selectAll().map { it[Usuarios.id] }.distinct()
            
            val clientesSinRelacion = clientesSinUsuario.filter { !usuariosExistentes.contains(it) }
            if (clientesSinRelacion.isNotEmpty()) {
                println("⚠️  Clientes sin usuario relacionado: ${clientesSinRelacion.size}")
                clientesSinRelacion.take(3).forEach { println("  - UsuarioID: $it") }
            } else {
                println("✅ Todas las relaciones están correctas")
            }
        }
        
        println("\n=== Fin del Diagnóstico ===")
    }
} 