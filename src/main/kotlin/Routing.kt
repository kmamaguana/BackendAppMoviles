package com.example

import com.example.repository.impl.ClienteRepositoryImpl
import com.example.repository.impl.UsuarioRepositoryImpl
import com.example.repository.impl.*
import com.example.routes.*
import com.example.services.*
import com.example.routes.chatBotRoutes
import com.example.services.GeminiService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.github.cdimascio.dotenv.dotenv

fun Application.configureRouting() {
    // Cargar API Key de Gemini desde .env o variable de entorno
    val dotenv = dotenv()
    val geminiApiKey = dotenv["GEMINI_API_KEY"] ?: System.getenv("GEMINI_API_KEY")

    // Crear instancia de GeminiService
    val geminiService = GeminiService(geminiApiKey)

    val usuarioRepository = UsuarioRepositoryImpl()
    val usuarioService = UsuarioService(usuarioRepository)
    val authService = AuthService(usuarioRepository, environment.config)

    val reseteoContrasenaService = ReseteoContrasenaService()
    val clienteRepository = ClienteRepositoryImpl()
    val clienteService = ClienteService(clienteRepository)
    val mascotaService = MascotaService()
    val servicioService = ServicioService()
    val citaService = CitaService()
    val citaServicioService = CitaServicioService()
    val historialMedicoService = HistorialMedicoService()
    val categoriaService = CategoriaService()
    val productoService = ProductoService()
    val carritoService = CarritoService()
    val ordenService = OrdenService()
    val ordenDetalleService = OrdenDetalleService()
    val reporteAdminService = ReporteAdminService()
    val historialClienteService = HistorialClienteService()
    val nivelFidelidadService = NivelFidelidadService()

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        authRoutes(authService)
        usuarioRoutes(authService, usuarioService)
        reseteoContrasenaRoutes(reseteoContrasenaService)
        clienteRoutes(clienteService)
        mascotaRoutes(mascotaService)
        servicioRoutes(servicioService)
        citaRoutes(citaService)
        citaServicioRoutes(citaServicioService)
        historialMedicoRoutes(historialMedicoService)
        categoriaRoutes(categoriaService)
        productoRoutes(productoService)
        carritoRoutes(carritoService)
        ordenRoutes(ordenService)
        ordenDetalleRoutes(ordenDetalleService)
        reporteAdminRoutes(reporteAdminService)
        historialClienteRoutes(historialClienteService)
        nivelFidelidadRoutes(nivelFidelidadService)

        // ✅ Ruta del chatbot usando instancia de GeminiService
        chatBotRoutes(geminiService)

        get("/auth/test") {
            call.respondText("Ruta auth funciona")
        }
    }
}
