package com.example.routes

import com.example.dto.OrdenDTO
import com.example.dto.OrdenCreateUpdateDTO
import com.example.services.OrdenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.ordenRoutes(ordenService: OrdenService) {
    route("/ordenes") {
        get {
            call.respond(ordenService.obtenerTodos())
        }
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Falta id")
            val orden = ordenService.obtenerPorId(id)
            if (orden != null) call.respond(orden)
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
        post {
            val dto = call.receive<OrdenCreateUpdateDTO>()
            val creado = ordenService.crear(dto)
            call.respond(HttpStatusCode.Created, creado)
        }
        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Falta id")
            val dto = call.receive<OrdenCreateUpdateDTO>()
            val actualizado = ordenService.actualizar(id, dto)
            if (actualizado != null) call.respond(actualizado)
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Falta id")
            val eliminado = ordenService.eliminar(id)
            if (eliminado) call.respondText("Eliminado")
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
    }
} 