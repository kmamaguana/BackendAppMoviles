package com.example.services

import com.example.domain.repository.ClienteRepository
import com.example.dto.ClienteDTO
import com.example.dto.ClienteCreateUpdateDTO
import com.example.dto.ClienteLoginRequestDTO
import com.example.dto.ClienteLoginResponseDTO
import com.example.db.Usuarios
import at.favre.lib.crypto.bcrypt.BCrypt
import org.jetbrains.exposed.sql.select
import com.example.repository.impl.ClienteRepositoryImpl

class ClienteService(
    private val repository: ClienteRepository = ClienteRepositoryImpl()
) {
    fun obtenerTodos(): List<ClienteDTO> {
        println("🚀 ClienteService.obtenerTodos() llamado")
        val clientes = repository.findAll()
        println("📦 ClienteService: ${clientes.size} clientes obtenidos del repositorio")
        
        // Log detallado de cada cliente
        clientes.forEach { cliente ->
            println("📋 Cliente en servicio:")
            println("  - ID: ${cliente.id}")
            println("  - Usuario ID: ${cliente.usuarioId}")
            println("  - Nombre: '${cliente.nombre}'")
            println("  - Teléfono: '${cliente.telefono}' (longitud: ${cliente.telefono.length})")
            println("  - Dirección: '${cliente.direccion}' (longitud: ${cliente.direccion.length})")
            println("  - Activo: ${cliente.activo}")
            println()
        }
        
        return clientes
    }
    fun obtenerPorId(id: String): ClienteDTO? = repository.findById(id)
    fun crear(dto: ClienteCreateUpdateDTO): ClienteDTO = repository.save(dto)
    fun actualizar(id: String, dto: ClienteCreateUpdateDTO): ClienteDTO? = repository.update(id, dto)
    fun eliminar(id: String): Boolean = repository.deleteById(id)

    fun loginCliente(loginDto: ClienteLoginRequestDTO): ClienteLoginResponseDTO? {
        // Buscar usuario por email
        val usuarioRow = org.jetbrains.exposed.sql.transactions.transaction {
            Usuarios.select { Usuarios.email eq loginDto.email }.singleOrNull()
        } ?: return null
        val hashedPassword = usuarioRow[Usuarios.contraseña]
        // Validar contraseña
        if (!BCrypt.verifyer().verify(loginDto.password.toCharArray(), hashedPassword).verified) return null
        // Buscar cliente asociado a ese usuario
        val cliente = obtenerTodos().find { it.usuarioId == usuarioRow[Usuarios.id].value.toString() } ?: return null
        // Generar token (opcional, aquí solo un string de ejemplo)
        val token = "token-cliente-${cliente.id}"
        return ClienteLoginResponseDTO(
            token = token,
            clienteId = cliente.id,
            nombre = usuarioRow[Usuarios.nombre],
            email = usuarioRow[Usuarios.email]
        )
    }
} 