package com.example.routes

import com.example.dto.ServicioDTO
import com.example.dto.ServicioCreateUpdateDTO
import com.example.services.ServicioService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.servicioRoutes(servicioService: ServicioService) {
    route("/servicios") {
        get {
            println("🌐 GET /servicios - Petición recibida")
            val servicios = servicioService.obtenerTodos()
            println("📤 GET /servicios - Respondiendo con ${servicios.size} servicios")
            
            // Log detallado de respuesta
            servicios.forEach { servicio ->
                println("📤 Servicio en respuesta:")
                println("  - ID: ${servicio.id}")
                println("  - Nombre: '${servicio.nombre}'")
                println("  - Tipo: '${servicio.tipo}'")
                println("  - Descripción: '${servicio.descripcion}'")
                println("  - Precio: ${servicio.precio}")
                println()
            }
            
            call.respond(servicios)
        }
        
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Falta id")
            val servicio = servicioService.obtenerPorId(id)
            if (servicio != null) call.respond(servicio)
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
        
        post {
            val dto = call.receive<ServicioCreateUpdateDTO>()
            val creado = servicioService.crear(dto)
            call.respond(HttpStatusCode.Created, creado)
        }
        
        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Falta id")
            val dto = call.receive<ServicioCreateUpdateDTO>()
            val actualizado = servicioService.actualizar(id, dto)
            if (actualizado != null) call.respond(actualizado)
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
        
        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Falta id")
            val eliminado = servicioService.eliminar(id)
            if (eliminado) call.respondText("Eliminado")
            else call.respond(HttpStatusCode.NotFound, "No encontrado")
        }
        
        // Endpoint de diagnóstico
        get("/diagnostico") {
            println("🔍 GET /servicios/diagnostico - Diagnóstico solicitado")
            
            val servicios = servicioService.obtenerTodos()
            val diagnostico = mapOf(
                "timestamp" to System.currentTimeMillis(),
                "total_servicios" to servicios.size,
                "tipos_servicios" to servicios.map { it.tipo }.distinct(),
                "precio_promedio" to servicios.map { it.precio }.average(),
                "precio_minimo" to servicios.map { it.precio }.minOrNull(),
                "precio_maximo" to servicios.map { it.precio }.maxOrNull(),
                "mensaje" to "Diagnóstico de servicios completado"
            )
            
            println("📊 Diagnóstico: $diagnostico")
            call.respond(diagnostico)
        }
    }
} 