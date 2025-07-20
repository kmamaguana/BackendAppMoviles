package com.example.repository.impl

import com.example.db.Categorias
import com.example.domain.repository.CategoriaRepository
import com.example.dto.CategoriaDTO
import com.example.dto.CategoriaCreateUpdateDTO
import com.example.mappers.toCategoriaDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class CategoriaRepositoryImpl : CategoriaRepository {
    override fun findAll(): List<CategoriaDTO> = transaction {
        Categorias.selectAll().map { it.toCategoriaDTO() }
    }

    override fun findById(id: String): CategoriaDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        Categorias.select { Categorias.id eq intId }
            .map { it.toCategoriaDTO() }
            .singleOrNull()
    }

    override fun save(dto: CategoriaCreateUpdateDTO): CategoriaDTO = transaction {
        val id = Categorias.insertAndGetId {
            it[nombre] = dto.nombre
            it[descripcion] = dto.descripcion
        }
        findById(id.value.toString())!!
    }

    override fun update(id: String, dto: CategoriaCreateUpdateDTO): CategoriaDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        val updatedRows = Categorias.update({ Categorias.id eq intId }) {
            it[nombre] = dto.nombre
            it[descripcion] = dto.descripcion
        }
        if (updatedRows > 0) findById(id) else null
    }

    override fun deleteById(id: String): Boolean = transaction {
        val intId = id.toIntOrNull() ?: return@transaction false
        Categorias.deleteWhere { Categorias.id eq intId } > 0
    }
} 