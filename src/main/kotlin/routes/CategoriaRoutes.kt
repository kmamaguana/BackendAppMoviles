package com.example.routes

import com.example.dto.CategoriaDTO
import com.example.dto.CategoriaCreateUpdateDTO
import com.example.services.CategoriaService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.categoriaRoutes(categoriaService: CategoriaService) {
    route("/categorias") {
        get {
            call.respond(categoriaService.obtenerTodos())
        }
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Falta id")
            val categoria = categoriaService.obtenerPorId(id)
            if (categoria != null) call.respond(categoria)
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
        post {
            val dto = call.receive<CategoriaCreateUpdateDTO>()
            val creado = categoriaService.crear(dto)
            call.respond(HttpStatusCode.Created, creado)
        }
        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Falta id")
            val dto = call.receive<CategoriaCreateUpdateDTO>()
            val actualizado = categoriaService.actualizar(id, dto)
            if (actualizado != null) call.respond(actualizado)
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Falta id")
            val eliminado = categoriaService.eliminar(id)
            if (eliminado) call.respondText("Eliminado")
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
    }
} 