package com.example.mappers

import com.example.db.Mascotas
import com.example.dto.MascotaDTO
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toMascotaDTO(): MascotaDTO = MascotaDTO(
    id = this[Mascotas.id].value.toString(),
    clienteId = this[Mascotas.clienteId].value.toString(),
    nombre = this[Mascotas.nombre],
    especie = this[Mascotas.especie],
    raza = this[Mascotas.raza],
    fechaNacimiento = this[Mascotas.fechaNacimiento].toString(),
    edad = this[Mascotas.edad],
    peso = this[Mascotas.peso].toDouble(),
    sexo = this[Mascotas.sexo],
    esterilizado = this[Mascotas.esterilizado],
    vacunasAlDia = this[Mascotas.vacunasAlDia],
    observaciones = this[Mascotas.observaciones],
    estado = this[Mascotas.estado],
    creadoEn = this[Mascotas.creadoEn].toString()
) 