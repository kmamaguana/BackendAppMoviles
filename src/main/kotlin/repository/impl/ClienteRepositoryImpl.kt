package com.example.repository.impl

import com.example.db.Clientes
import com.example.db.Usuarios
import com.example.domain.repository.ClienteRepository
import com.example.dto.ClienteDTO
import com.example.dto.ClienteCreateUpdateDTO
import com.example.mappers.toClienteDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ClienteRepositoryImpl : ClienteRepository {
    override fun findAll(): List<ClienteDTO> = transaction {
        println("🔍 Buscando clientes activos...")
        
        // Primero verificar cuántos clientes hay en total
        val totalClientes = Clientes.selectAll().count()
        println("📊 Total de clientes en la tabla: $totalClientes")
        
        // Verificar cuántos están activos
        val clientesActivos = Clientes.select { Clientes.activo eq true }.count()
        println("✅ Clientes activos: $clientesActivos")
        
        // Verificar cuántos están inactivos
        val clientesInactivos = Clientes.select { Clientes.activo eq false }.count()
        println("❌ Clientes inactivos: $clientesInactivos")
        
        // Obtener todos los clientes con sus usuarios
        val result = (Clientes innerJoin Usuarios).select { Clientes.activo eq true }
        println("🔗 Consulta JOIN ejecutada")
        
        val clientes = result.map {
            val nombreUsuario = it[Usuarios.nombre]
            val telefono = it[Clientes.telefono]
            val direccion = it[Clientes.direccion]
            val activo = it[Clientes.activo]
            val usuarioId = it[Clientes.usuarioId].value
            
            println("🔍 Datos raw de BD:")
            println("  - Usuario ID: $usuarioId")
            println("  - Nombre: '$nombreUsuario'")
            println("  - Teléfono: '$telefono' (longitud: ${telefono?.length ?: 0})")
            println("  - Dirección: '$direccion' (longitud: ${direccion?.length ?: 0})")
            println("  - Activo: $activo")
            
            val cliente = it.toClienteDTO(nombreUsuario)
            println("👤 Cliente mapeado: ${cliente.nombre} (ID: ${cliente.id}, Activo: ${cliente.activo})")
            println("  - Teléfono mapeado: '${cliente.telefono}' (longitud: ${cliente.telefono.length})")
            println("  - Dirección mapeada: '${cliente.direccion}' (longitud: ${cliente.direccion.length})")
            println("  - Usuario ID mapeado: ${cliente.usuarioId}")
            println()
            cliente
        }
        
        println("📋 Total de clientes retornados: ${clientes.size}")
        clientes
    }

    override fun findById(id: String): ClienteDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        (Clientes innerJoin Usuarios).select { Clientes.id eq intId }
            .map {
                val nombreUsuario = it[Usuarios.nombre]
                it.toClienteDTO(nombreUsuario)
            }
            .singleOrNull()
    }

    override fun save(dto: ClienteCreateUpdateDTO): ClienteDTO = transaction {
        val id = Clientes.insertAndGetId {
            it[usuarioId] = dto.usuarioId.toInt()
            it[telefono] = dto.telefono
            it[direccion] = dto.direccion
            it[activo] = true // Por defecto activo al crear
        }
        findById(id.value.toString())!!
    }

    override fun update(id: String, dto: ClienteCreateUpdateDTO): ClienteDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        val updatedRows = Clientes.update({ Clientes.id eq intId }) {
            it[usuarioId] = dto.usuarioId.toInt()
            it[telefono] = dto.telefono
            it[direccion] = dto.direccion
        }
        if (updatedRows > 0) findById(id) else null
    }

    override fun deleteById(id: String): Boolean = transaction {
        val intId = id.toIntOrNull() ?: return@transaction false
        // Soft delete: marcar como inactivo en lugar de eliminar
        Clientes.update({ Clientes.id eq intId }) {
            it[activo] = false
        } > 0
    }
} 