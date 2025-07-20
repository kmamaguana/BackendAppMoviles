package com.example

import com.example.repository.impl.ClienteRepositoryImpl
import com.example.repository.impl.UsuarioRepositoryImpl
import com.example.repository.impl.*
import com.example.routes.*
import com.example.services.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val usuarioRepository = UsuarioRepositoryImpl()
    val usuarioService = UsuarioService(usuarioRepository)
    val authService = AuthService(usuarioRepository, environment.config)

    // Instancias de servicios para los nuevos módulos
    val reseteoContrasenaService = ReseteoContrasenaService()
    val ClienteRepository = ClienteRepositoryImpl()
    val clienteService = ClienteService(ClienteRepository)
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

        // Rutas de los nuevos módulos
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

        get("/auth/test") {
            call.respondText("Ruta auth funciona")
        }
    }
}

