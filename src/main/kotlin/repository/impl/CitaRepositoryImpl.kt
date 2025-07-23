package com.example.repository.impl

import com.example.db.Citas
import com.example.db.Mascotas
import com.example.db.Clientes
import com.example.db.Usuarios
import com.example.domain.repository.CitaRepository
import com.example.dto.CitaDTO
import com.example.dto.CitaCreateUpdateDTO
import com.example.mappers.toCitaDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class CitaRepositoryImpl : CitaRepository {
    override fun findAll(): List<CitaDTO> = transaction {
        (Citas innerJoin Mascotas innerJoin Clientes innerJoin Usuarios)
            .selectAll()
            .map {
                val nombreMascota = it[Mascotas.nombre]
                val nombreCliente = it[Usuarios.nombre]
                it.toCitaDTO(nombreMascota, nombreCliente)
            }
    }

    override fun findById(id: String): CitaDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        (Citas innerJoin Mascotas innerJoin Clientes innerJoin Usuarios)
            .select { Citas.id     eq intId }
            .map {
                val nombreMascota = it[Mascotas.nombre]
                val nombreCliente = it[Usuarios.nombre]
                it.toCitaDTO(nombreMascota, nombreCliente)
            }
            .singleOrNull()
    }

    override fun save(dto: CitaCreateUpdateDTO): CitaDTO = transaction {
        val id = Citas.insertAndGetId {
            it[mascotaId] = dto.mascotaId.toInt()
            it[fecha] = java.time.LocalDateTime.parse(dto.fecha)
            it[estado] = dto.estado
            it[notas] = dto.notas
            it[creadoEn] = java.time.LocalDateTime.parse(dto.creadoEn)
        }
        findById(id.value.toString())!!
    }

    override fun update(id: String, dto: CitaCreateUpdateDTO): CitaDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        val updatedRows = Citas.update({ Citas.id eq intId }) {
            it[mascotaId] = dto.mascotaId.toInt()
            it[fecha] = java.time.LocalDateTime.parse(dto.fecha)
            it[estado] = dto.estado
            it[notas] = dto.notas
            it[creadoEn] = java.time.LocalDateTime.parse(dto.creadoEn)
        }
        if (updatedRows > 0) findById(id) else null
    }

    override fun deleteById(id: String): Boolean = transaction {
        val intId = id.toIntOrNull() ?: return@transaction false
        Citas.deleteWhere { Citas.id eq intId } > 0
    }
} 