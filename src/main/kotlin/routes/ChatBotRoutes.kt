package com.example.routes

import com.example.services.HuggingFaceService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import com.example.services.GeminiService


@Serializable
data class ChatRequest(val prompt: String)

@Serializable
data class ChatResponse(val respuesta: String)

fun Route.chatBotRoutes(geminiService: GeminiService) {
    route("/chatbot") {
        post {
            val request = call.receive<ChatRequest>()
            val prompt = request.prompt
            val respuesta = geminiService.consultarModelo(prompt)
            call.respond(ChatResponse(respuesta.toString()))
        }
    }
}
