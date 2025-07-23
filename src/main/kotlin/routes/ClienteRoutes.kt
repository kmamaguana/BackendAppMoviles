package com.example.routes

import com.example.dto.ClienteDTO
import com.example.dto.ClienteCreateUpdateDTO
import com.example.services.ClienteService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.clienteRoutes(clienteService: ClienteService) {
    route("/clientes") {
        get {
            call.respond(clienteService.obtenerTodos())
        }
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Falta id")
            val cliente = clienteService.obtenerPorId(id)
            if (cliente != null) call.respond(cliente)
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
        post {
            val dto = call.receive<ClienteCreateUpdateDTO>()
            val creado = clienteService.crear(dto)
            call.respond(HttpStatusCode.Created, creado)
        }
        post("/login") {
            val loginDto = call.receive<com.example.dto.ClienteLoginRequestDTO>()
            val loginResponse = clienteService.loginCliente(loginDto)
            if (loginResponse != null) {
                call.respond(loginResponse)
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Email o contraseña incorrectos")
            }
        }
        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Falta id")
            val dto = call.receive<ClienteCreateUpdateDTO>()
            val actualizado = clienteService.actualizar(id, dto)
            if (actualizado != null) call.respond(actualizado)
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Falta id")
            val eliminado = clienteService.eliminar(id)
            if (eliminado) call.respondText("Eliminado")
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
    }
} 