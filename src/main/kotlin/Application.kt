package com.example

import com.example.db.*
import com.example.routes.chatBotRoutes
import com.example.services.HuggingFaceService
import com.example.services.GeminiService

import com.typesafe.config.ConfigFactory
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import io.github.cdimascio.dotenv.dotenv
import org.slf4j.LoggerFactory

fun main() {
    embeddedServer(Netty, port = 8089, module = Application::module).start(wait = true)
}

fun Application.module() {
    val logger = LoggerFactory.getLogger("Application")

    // Cargar variables de entorno
    val dotenv = dotenv()

    val geminiApiKey = dotenv["GEMINI_API_KEY"] ?: System.getenv("GEMINI_API_KEY")
    val hfApiKey = dotenv["HF_API_KEY"] ?: System.getenv("HF_API_KEY")

    // Validaciones
    if (geminiApiKey.isNullOrBlank()) {
        logger.error("❌ No se encontró GEMINI_API_KEY. Asegúrate de definirla en .env o variables de entorno.")
        throw IllegalStateException("Falta GEMINI_API_KEY")
    } else {
        logger.info("✅ Clave Gemini cargada correctamente: ${geminiApiKey.take(4)}****")
    }

    val huggingFaceApiKey = hfApiKey?.takeIf { it.isNotBlank() } ?: run {
        logger.warn(
            """
            ⚠️ No se encontró HF_API_KEY en el entorno.
            Si quieres usar Hugging Face, agrega:
              - HF_API_KEY=tu_api_key en .env
            """.trimIndent()
        )
        "development_mode_no_api_key"
    }

    // Instanciar servicios
    val geminiService = GeminiService(geminiApiKey)
    val huggingFaceService = HuggingFaceService(huggingFaceApiKey)

    // Mostrar configuración JWT de ejemplo
    val config = ConfigFactory.load()
    logger.info("JWT SECRET: ${config.getString("jwt.secret")}")
    logger.info("JWT ISSUER: ${config.getString("jwt.issuer")}")
    logger.info("JWT AUDIENCE: ${config.getString("jwt.audience")}")
    logger.info("JWT EXPIRES: ${config.getString("jwt.expiresInMs")}")

    // Plugins
    install(ContentNegotiation) {
        json()
    }

    // Rutas
    routing {
        chatBotRoutes(geminiService)  // <- Pasa el servicio de Gemini correctamente
    }

    // Conectar a la base de datos
    connectToDatabase()
    transaction {
        SchemaUtils.create(
            Usuarios,
            ReseteoContrasena,
            Clientes,
            Mascotas,
            Servicios,
            Citas,
            CitaServicios,
            HistorialMedico,
            Categorias,
            Productos,
            Carrito,
            Ordenes,
            OrdenDetalles,
            ReportesAdmin,
            HistorialCliente,
            NivelesFidelidad
        )
    }

    logger.info("🚀 Servidor iniciado en http://localhost:8089")
}
