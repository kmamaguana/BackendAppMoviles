package com.example.routes

import com.example.dto.auth.SolicitarReseteoDTO
import com.example.dto.auth.ConfirmarReseteoDTO
import com.example.services.ReseteoContrasenaService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.reseteoContrasenaRoutes(reseteoContrasenaService: ReseteoContrasenaService) {
    route("/reseteo_contraseña") {
        // Solicitar reseteo (POST)
        post {
            val dto = call.receive<SolicitarReseteoDTO>()
            // Aquí deberías implementar la lógica para generar el token y enviarlo por email
            // Por ahora, solo respondemos OK
            call.respond(HttpStatusCode.OK, "Solicitud de reseteo recibida para ${dto.email}")
        }
        // Confirmar reseteo (PUT)
        put("/{token}") {
            val token = call.parameters["token"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Falta token")
            val dto = call.receive<ConfirmarReseteoDTO>()
            // Aquí deberías implementar la lógica para validar el token y cambiar la contraseña
            // Por ahora, solo respondemos OK
            call.respond(HttpStatusCode.OK, "Contraseña cambiada para token $token")
        }
        // (Opcional) Verificar token (GET)
        get("/{token}") {
            val token = call.parameters["token"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Falta token")
            // Aquí deberías implementar la lógica para validar el token
            // Por ahora, solo respondemos OK
            call.respond(HttpStatusCode.OK, "Token $token válido")
        }
    }
} 