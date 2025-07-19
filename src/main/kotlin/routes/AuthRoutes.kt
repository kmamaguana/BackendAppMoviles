package com.example.routes

import com.example.dto.UsuarioCreateUpdateDTO
import com.example.dto.auth.LoginRequestDTO
import com.example.dto.auth.LoginResponseDTO
import com.example.services.AuthService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes(authService: AuthService) {

    route("/auth") {

        // POST /auth/register → Registro de nuevo usuario
        post("/register") {
            val dto = call.receive<UsuarioCreateUpdateDTO>()
            val usuarioCreado = authService.register(dto)
            call.respond(HttpStatusCode.Created, usuarioCreado)
        }

        // POST /auth/login → Autenticación y generación de token JWT
        post("/login") {
            val loginDto = call.receive<LoginRequestDTO>()
            val result: LoginResponseDTO? = authService.login(loginDto)

            if (result != null) {
                call.respond(HttpStatusCode.OK, result)
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Email o contraseña incorrectos")
            }
        }
    }
}
