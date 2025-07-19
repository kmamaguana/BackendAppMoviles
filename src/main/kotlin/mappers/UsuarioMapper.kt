package com.example.mappers

import com.example.db.Usuarios
import com.example.dto.UsuarioDTO
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toUsuarioDTO(): UsuarioDTO = UsuarioDTO(
    id = this[Usuarios.id].value.toString(),
    nombre = this[Usuarios.nombre],
    apellido = this[Usuarios.apellido],
    email = this[Usuarios.email],
    rol = this[Usuarios.rol],
    estado = this[Usuarios.estado],
    fechaNacimiento = this[Usuarios.fechaNacimiento].toString(),
    ultimoLogin = this[Usuarios.ultimoLogin]?.toString(),
    creadoEn = this[Usuarios.creadoEn].toString()
)
