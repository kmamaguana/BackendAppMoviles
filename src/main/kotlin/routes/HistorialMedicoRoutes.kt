package com.example.routes

import com.example.dto.HistorialMedicoDTO
import com.example.dto.HistorialMedicoCreateUpdateDTO
import com.example.services.HistorialMedicoService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.historialMedicoRoutes(historialMedicoService: HistorialMedicoService) {
    route("/historial_medico") {
        get {
            call.respond(historialMedicoService.obtenerTodos())
        }
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Falta id")
            val historial = historialMedicoService.obtenerPorId(id)
            if (historial != null) call.respond(historial)
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
        post {
            val dto = call.receive<HistorialMedicoCreateUpdateDTO>()
            val creado = historialMedicoService.crear(dto)
            call.respond(HttpStatusCode.Created, creado)
        }
        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Falta id")
            val dto = call.receive<HistorialMedicoCreateUpdateDTO>()
            val actualizado = historialMedicoService.actualizar(id, dto)
            if (actualizado != null) call.respond(actualizado)
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Falta id")
            val eliminado = historialMedicoService.eliminar(id)
            if (eliminado) call.respondText("Eliminado")
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
    }
} 