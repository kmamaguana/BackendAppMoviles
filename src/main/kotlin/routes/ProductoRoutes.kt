package com.example.routes

import com.example.dto.ProductoDTO
import com.example.dto.ProductoCreateUpdateDTO
import com.example.services.ProductoService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.productoRoutes(productoService: ProductoService) {
    route("/productos") {
        get {
            call.respond(productoService.obtenerTodos())
        }
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Falta id")
            val producto = productoService.obtenerPorId(id)
            if (producto != null) call.respond(producto)
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
        post {
            val dto = call.receive<ProductoCreateUpdateDTO>()
            val creado = productoService.crear(dto)
            call.respond(HttpStatusCode.Created, creado)
        }
        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Falta id")
            val dto = call.receive<ProductoCreateUpdateDTO>()
            val actualizado = productoService.actualizar(id, dto)
            if (actualizado != null) call.respond(actualizado)
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Falta id")
            val eliminado = productoService.eliminar(id)
            if (eliminado) call.respondText("Eliminado")
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
    }
} 