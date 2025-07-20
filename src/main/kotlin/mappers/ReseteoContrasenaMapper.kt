package com.example.mappers

import com.example.db.ReseteoContrasena
import com.example.dto.ReseteoContrasenaDTO
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toReseteoContrasenaDTO(): ReseteoContrasenaDTO = ReseteoContrasenaDTO(
    id = this[ReseteoContrasena.id].value.toString(),
    usuarioId = this[ReseteoContrasena.usuarioId].value.toString(),
    token = this[ReseteoContrasena.token],
    expiracion = this[ReseteoContrasena.expiracion].toString(),
    usado = this[ReseteoContrasena.usado]
) 