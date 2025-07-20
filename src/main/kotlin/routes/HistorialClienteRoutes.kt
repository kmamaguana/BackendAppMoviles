package com.example.routes

import com.example.dto.HistorialClienteDTO
import com.example.dto.HistorialClienteCreateUpdateDTO
import com.example.services.HistorialClienteService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.historialClienteRoutes(historialClienteService: HistorialClienteService) {
    route("/historial_cliente") {
        get {
            call.respond(historialClienteService.obtenerTodos())
        }
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Falta id")
            val historial = historialClienteService.obtenerPorId(id)
            if (historial != null) call.respond(historial)
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
        post {
            val dto = call.receive<HistorialClienteCreateUpdateDTO>()
            val creado = historialClienteService.crear(dto)
            call.respond(HttpStatusCode.Created, creado)
        }
        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Falta id")
            val dto = call.receive<HistorialClienteCreateUpdateDTO>()
            val actualizado = historialClienteService.actualizar(id, dto)
            if (actualizado != null) call.respond(actualizado)
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Falta id")
            val eliminado = historialClienteService.eliminar(id)
            if (eliminado) call.respondText("Eliminado")
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
    }
} 