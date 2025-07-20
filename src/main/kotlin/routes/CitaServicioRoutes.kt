package com.example.routes

import com.example.dto.CitaServicioDTO
import com.example.dto.CitaServicioCreateUpdateDTO
import com.example.services.CitaServicioService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.citaServicioRoutes(citaServicioService: CitaServicioService) {
    route("/cita_servicios") {
        get {
            call.respond(citaServicioService.obtenerTodos())
        }
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Falta id")
            val citaServicio = citaServicioService.obtenerPorId(id)
            if (citaServicio != null) call.respond(citaServicio)
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
        post {
            val dto = call.receive<CitaServicioCreateUpdateDTO>()
            val creado = citaServicioService.crear(dto)
            call.respond(HttpStatusCode.Created, creado)
        }
        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Falta id")
            val dto = call.receive<CitaServicioCreateUpdateDTO>()
            val actualizado = citaServicioService.actualizar(id, dto)
            if (actualizado != null) call.respond(actualizado)
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Falta id")
            val eliminado = citaServicioService.eliminar(id)
            if (eliminado) call.respondText("Eliminado")
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
    }
} 