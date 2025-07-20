package com.example.routes

import com.example.dto.NivelFidelidadDTO
import com.example.dto.NivelFidelidadCreateUpdateDTO
import com.example.services.NivelFidelidadService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.nivelFidelidadRoutes(nivelFidelidadService: NivelFidelidadService) {
    route("/niveles_fidelidad") {
        get {
            call.respond(nivelFidelidadService.obtenerTodos())
        }
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Falta id")
            val nivel = nivelFidelidadService.obtenerPorId(id)
            if (nivel != null) call.respond(nivel)
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
        post {
            val dto = call.receive<NivelFidelidadCreateUpdateDTO>()
            val creado = nivelFidelidadService.crear(dto)
            call.respond(HttpStatusCode.Created, creado)
        }
        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Falta id")
            val dto = call.receive<NivelFidelidadCreateUpdateDTO>()
            val actualizado = nivelFidelidadService.actualizar(id, dto)
            if (actualizado != null) call.respond(actualizado)
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Falta id")
            val eliminado = nivelFidelidadService.eliminar(id)
            if (eliminado) call.respondText("Eliminado")
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
    }
} 