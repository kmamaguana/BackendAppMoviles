package com.example

import com.example.repository.impl.UsuarioRepositoryImpl
import com.example.routes.*
import com.example.services.AuthService
import com.example.services.UsuarioService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val usuarioRepository = UsuarioRepositoryImpl()
    val usuarioService = UsuarioService(usuarioRepository)
    val authService = AuthService(usuarioRepository, environment.config)

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        authRoutes(authService)
        usuarioRoutes(authService, usuarioService)

        get("/auth/test") {
            call.respondText("Ruta auth funciona")
        }
    }

}

