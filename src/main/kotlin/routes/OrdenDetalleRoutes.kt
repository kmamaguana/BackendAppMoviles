package com.example.routes

import com.example.dto.OrdenDetalleDTO
import com.example.dto.OrdenDetalleCreateUpdateDTO
import com.example.services.OrdenDetalleService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.ordenDetalleRoutes(ordenDetalleService: OrdenDetalleService) {
    route("/orden_detalles") {
        get {
            call.respond(ordenDetalleService.obtenerTodos())
        }
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Falta id")
            val detalle = ordenDetalleService.obtenerPorId(id)
            if (detalle != null) call.respond(detalle)
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
        post {
            val dto = call.receive<OrdenDetalleCreateUpdateDTO>()
            val creado = ordenDetalleService.crear(dto)
            call.respond(HttpStatusCode.Created, creado)
        }
        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Falta id")
            val dto = call.receive<OrdenDetalleCreateUpdateDTO>()
            val actualizado = ordenDetalleService.actualizar(id, dto)
            if (actualizado != null) call.respond(actualizado)
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Falta id")
            val eliminado = ordenDetalleService.eliminar(id)
            if (eliminado) call.respondText("Eliminado")
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
    }
} 