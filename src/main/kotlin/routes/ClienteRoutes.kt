package com.example.routes

import com.example.dto.ClienteDTO
import com.example.dto.ClienteCreateUpdateDTO
import com.example.services.ClienteService
import com.example.db.Clientes
import com.example.db.Usuarios
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.clienteRoutes(clienteService: ClienteService) {
    route("/clientes") {
        get {
            println("🌐 GET /clientes - Petición recibida")
            val clientes = clienteService.obtenerTodos()
            println("📤 GET /clientes - Respondiendo con ${clientes.size} clientes")
            
            // Log detallado de respuesta
            clientes.forEach { cliente ->
                println("📤 Cliente en respuesta:")
                println("  - ID: ${cliente.id}")
                println("  - Usuario ID: ${cliente.usuarioId}")
                println("  - Nombre: '${cliente.nombre}'")
                println("  - Email: '${cliente.email}'")
                println("  - Fecha Nacimiento: '${cliente.fechaNacimiento}'")
                println("  - Teléfono: '${cliente.telefono}'")
                println("  - Dirección: '${cliente.direccion}'")
                println("  - Activo: ${cliente.activo}")
                println()
            }
            
            call.respond(clientes)
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
        
        // Endpoint de diagnóstico
        get("/diagnostico") {
            println("🔍 GET /clientes/diagnostico - Diagnóstico solicitado")
            
            val clientes = clienteService.obtenerTodos()
            val diagnostico = mapOf(
                "timestamp" to System.currentTimeMillis(),
                "total_clientes" to clientes.size,
                "clientes_con_email" to clientes.count { it.email.isNotEmpty() },
                "clientes_con_fecha" to clientes.count { it.fechaNacimiento.isNotEmpty() },
                "mensaje" to "Diagnóstico de clientes completado"
            )
            
            println("📊 Diagnóstico: $diagnostico")
            call.respond(diagnostico)
        }
        
        // Endpoint para ver datos raw de la base de datos
        get("/datos-raw") {
            println("🔍 GET /clientes/datos-raw - Datos raw solicitados")
            val datosRaw = transaction {
                val result = (Clientes innerJoin Usuarios).select { Clientes.activo eq true }
                result.map { row ->
                    val usuarioId = row[Clientes.usuarioId].value
                    val nombre = row[Usuarios.nombre]
                    val email = row[Usuarios.email]
                    val fechaNacimiento = row[Usuarios.fechaNacimiento]
                    val telefono = row[Clientes.telefono]
                    val direccion = row[Clientes.direccion]
                    val activo = row[Clientes.activo]
                    val clienteId = row[Clientes.id].value
                    
                    mapOf(
                        "cliente_id" to clienteId,
                        "usuario_id" to usuarioId,
                        "nombre" to nombre,
                        "email_raw" to email,
                        "fecha_nacimiento_raw" to fechaNacimiento,
                        "telefono_raw" to telefono,
                        "direccion_raw" to direccion,
                        "activo" to activo,
                        "email_es_null" to (email == null),
                        "fecha_es_null" to (fechaNacimiento == null),
                        "email_longitud" to (email?.toString()?.length ?: 0),
                        "fecha_longitud" to (fechaNacimiento?.toString()?.length ?: 0)
                    )
                }
            }
            println("📊 Datos raw: $datosRaw")
            call.respond(datosRaw)
        }
        
        // Endpoint para debug específico del frontend
        get("/debug-frontend") {
            println("🔍 GET /clientes/debug-frontend - Debug frontend solicitado")
            
            val clientes = clienteService.obtenerTodos()
            val debugInfo = mapOf(
                "timestamp" to System.currentTimeMillis(),
                "total_clientes" to clientes.size,
                "clientes_detalle" to clientes.map { cliente ->
                    mapOf(
                        "id" to cliente.id,
                        "usuario_id" to cliente.usuarioId,
                        "nombre" to cliente.nombre,
                        "email" to mapOf(
                            "valor" to cliente.email,
                            "es_null" to (cliente.email == null),
                            "es_vacio" to (cliente.email.isNullOrEmpty()),
                            "longitud" to (cliente.email?.length ?: 0),
                            "tipo" to cliente.email?.javaClass?.simpleName
                        ),
                        "fecha_nacimiento" to mapOf(
                            "valor" to cliente.fechaNacimiento,
                            "es_null" to (cliente.fechaNacimiento == null),
                            "es_vacio" to (cliente.fechaNacimiento.isNullOrEmpty()),
                            "longitud" to (cliente.fechaNacimiento?.length ?: 0),
                            "tipo" to cliente.fechaNacimiento?.javaClass?.simpleName
                        ),
                        "telefono" to cliente.telefono,
                        "direccion" to cliente.direccion,
                        "activo" to cliente.activo
                    )
                }
            )
            
            println("📊 Debug frontend: $debugInfo")
            call.respond(debugInfo)
        }
    }
}

