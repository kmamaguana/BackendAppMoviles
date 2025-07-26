package com.example.routes

import com.example.dto.UsuarioDTO
import com.example.dto.UsuarioCreateUpdateDTO
import com.example.dto.auth.LoginRequestDTO
import com.example.services.AuthService
import com.example.services.UsuarioService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.usuarioRoutes(authService: AuthService, usuarioService: UsuarioService) {

    route("/usuarios") {

        // Obtener todos
        get {
            call.respond(usuarioService.obtenerTodos())
        }

        // Obtener por id
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Falta id")
            val usuario = usuarioService.obtenerPorId(id)
            if (usuario != null) call.respond(usuario)
            else call.respond(HttpStatusCode.NotFound, "Usuario no encontrado")
        }

        // Crear usuario (registro)
        post {
            val createDto = call.receive<UsuarioCreateUpdateDTO>()
            val hashedPassword = authService.hashPassword(createDto.password)
            val usuarioCreado = usuarioService.crear(createDto, hashedPassword)
            call.respond(HttpStatusCode.Created, usuarioCreado)
        }

        // Actualizar usuario
        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Falta id")
            val updateDto = call.receive<UsuarioCreateUpdateDTO>()

            // Para update, si no se quiere cambiar contraseña, pasar null
            val hashedPassword = if (updateDto.password.isNotBlank()) {
                authService.hashPassword(updateDto.password)
            } else null

            val usuarioActualizado = usuarioService.actualizar(id, updateDto, hashedPassword)
            if (usuarioActualizado != null) call.respond(usuarioActualizado)
            else call.respond(HttpStatusCode.NotFound, "Usuario no encontrado")
        }

        // Eliminar usuario
        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Falta id")
            val eliminado = usuarioService.eliminar(id)
            if (eliminado) call.respondText("Usuario eliminado")
            else call.respond(HttpStatusCode.NotFound, "Usuario no encontrado")
        }
    }

    // Rutas de autenticación
    route("/auth") {

        // Login
        post("/login") {
            val loginDto = call.receive<LoginRequestDTO>()
            val loginResponse = authService.login(loginDto)
            if (loginResponse != null) {
                call.respond(loginResponse)
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Email o contraseña incorrectos")
            }
        }
    }
}


