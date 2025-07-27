package com.example.mappers

import com.example.db.Clientes
import com.example.db.Usuarios
import com.example.dto.ClienteDTO
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toClienteDTO(nombreUsuario: String): ClienteDTO {
    val telefonoRaw = this[Clientes.telefono]
    val direccionRaw = this[Clientes.direccion]
    val email = this[Usuarios.email]
    val fechaNacimiento = this[Usuarios.fechaNacimiento]
    val id = this[Clientes.id].value.toString()
    val usuarioId = this[Clientes.usuarioId].value.toString()
    val activo = this[Clientes.activo]
    
    println("🔧 ClienteMapper - Mapeando cliente:")
    println("  - ID: $id")
    println("  - Usuario ID: $usuarioId")
    println("  - Nombre: '$nombreUsuario'")
    println("  - Email: '$email'")
    println("  - Fecha Nacimiento: '$fechaNacimiento'")
    println("  - Teléfono raw: '$telefonoRaw' (tipo: ${telefonoRaw?.javaClass?.simpleName})")
    println("  - Dirección raw: '$direccionRaw' (tipo: ${direccionRaw?.javaClass?.simpleName})")
    println("  - Activo: $activo")
    
    // Manejar valores null o vacíos
    val telefono = telefonoRaw ?: ""
    val direccion = direccionRaw ?: ""
    
    println("  - Teléfono procesado: '$telefono' (longitud: ${telefono.length})")
    println("  - Dirección procesada: '$direccion' (longitud: ${direccion.length})")
    println()
    
    return ClienteDTO(
        id = id,
        usuarioId = usuarioId,
        nombre = nombreUsuario,
        email = email,
        fechaNacimiento = fechaNacimiento.toString(),
        telefono = telefono,
        direccion = direccion,
        activo = activo
    )
} 