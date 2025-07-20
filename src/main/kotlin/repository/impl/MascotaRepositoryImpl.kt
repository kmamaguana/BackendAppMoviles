package com.example.repository.impl

import com.example.db.Mascotas
import com.example.domain.repository.MascotaRepository
import com.example.dto.MascotaDTO
import com.example.dto.MascotaCreateUpdateDTO
import com.example.mappers.toMascotaDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class MascotaRepositoryImpl : MascotaRepository {
    override fun findAll(): List<MascotaDTO> = transaction {
        Mascotas.selectAll().map { it.toMascotaDTO() }
    }

    override fun findById(id: String): MascotaDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        Mascotas.select { Mascotas.id eq intId }
            .map { it.toMascotaDTO() }
            .singleOrNull()
    }

    override fun save(dto: MascotaCreateUpdateDTO): MascotaDTO = transaction {
        val id = Mascotas.insertAndGetId {
            it[clienteId] = dto.clienteId.toInt()
            it[nombre] = dto.nombre
            it[especie] = dto.especie
            it[raza] = dto.raza
            it[fechaNacimiento] = java.time.LocalDate.parse(dto.fechaNacimiento)
            it[edad] = dto.edad
            it[peso] = java.math.BigDecimal.valueOf(dto.peso)
            it[sexo] = dto.sexo
            it[esterilizado] = dto.esterilizado
            it[vacunasAlDia] = dto.vacunasAlDia
            it[observaciones] = dto.observaciones
            it[estado] = dto.estado
            it[creadoEn] = java.time.LocalDateTime.parse(dto.creadoEn)
        }
        findById(id.value.toString())!!
    }

    override fun update(id: String, dto: MascotaCreateUpdateDTO): MascotaDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        val updatedRows = Mascotas.update({ Mascotas.id eq intId }) {
            it[clienteId] = dto.clienteId.toInt()
            it[nombre] = dto.nombre
            it[especie] = dto.especie
            it[raza] = dto.raza
            it[fechaNacimiento] = java.time.LocalDate.parse(dto.fechaNacimiento)
            it[edad] = dto.edad
            it[peso] = java.math.BigDecimal.valueOf(dto.peso)
            it[sexo] = dto.sexo
            it[esterilizado] = dto.esterilizado
            it[vacunasAlDia] = dto.vacunasAlDia
            it[observaciones] = dto.observaciones
            it[estado] = dto.estado
            it[creadoEn] = java.time.LocalDateTime.parse(dto.creadoEn)
        }
        if (updatedRows > 0) findById(id) else null
    }

    override fun deleteById(id: String): Boolean = transaction {
        val intId = id.toIntOrNull() ?: return@transaction false
        Mascotas.deleteWhere { Mascotas.id eq intId } > 0
    }
} 