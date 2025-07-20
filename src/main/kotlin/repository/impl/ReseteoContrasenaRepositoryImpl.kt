package com.example.repository.impl

import com.example.db.ReseteoContrasena
import com.example.domain.repository.ReseteoContrasenaRepository
import com.example.dto.ReseteoContrasenaDTO
import com.example.mappers.toReseteoContrasenaDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ReseteoContrasenaRepositoryImpl : ReseteoContrasenaRepository {
    override fun findAll(): List<ReseteoContrasenaDTO> = transaction {
        ReseteoContrasena.selectAll().map { it.toReseteoContrasenaDTO() }
    }

    override fun findById(id: String): ReseteoContrasenaDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        ReseteoContrasena.select { ReseteoContrasena.id eq intId }
            .map { it.toReseteoContrasenaDTO() }
            .singleOrNull()
    }

    override fun findByToken(token: String): ReseteoContrasenaDTO? = transaction {
        ReseteoContrasena.select { ReseteoContrasena.token eq token }
            .map { it.toReseteoContrasenaDTO() }
            .singleOrNull()
    }

    override fun save(dto: ReseteoContrasenaDTO): ReseteoContrasenaDTO = transaction {
        val id = ReseteoContrasena.insertAndGetId {
            it[usuarioId] = dto.usuarioId.toInt()
            it[token] = dto.token
            it[expiracion] = org.jetbrains.exposed.sql.javatime.JavaLocalDateTimeColumnType().valueFromDB(dto.expiracion) as java.time.LocalDateTime
            it[usado] = dto.usado
        }
        findById(id.value.toString())!!
    }

    override fun update(id: String, dto: ReseteoContrasenaDTO): ReseteoContrasenaDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        val updatedRows = ReseteoContrasena.update({ ReseteoContrasena.id eq intId }) {
            it[usuarioId] = dto.usuarioId.toInt()
            it[token] = dto.token
            it[expiracion] = org.jetbrains.exposed.sql.javatime.JavaLocalDateTimeColumnType().valueFromDB(dto.expiracion) as java.time.LocalDateTime
            it[usado] = dto.usado
        }
        if (updatedRows > 0) findById(id) else null
    }

    override fun deleteById(id: String): Boolean = transaction {
        val intId = id.toIntOrNull() ?: return@transaction false
        ReseteoContrasena.deleteWhere { ReseteoContrasena.id eq intId } > 0
    }
} 