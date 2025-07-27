package com.example.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

class HuggingFaceService(private val apiKey: String) {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    @Serializable
    data class ModelInput(val inputs: String)

    suspend fun consultarModelo(prompt: String): String {
        return try {
            val response: HttpResponse = client.post("https://api-inference.huggingface.co/models/google/flan-t5-small") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $apiKey")
                    append(HttpHeaders.Accept, "application/json")
                }
                contentType(ContentType.Application.Json)
                setBody(ModelInput(inputs = prompt))
            }

            val jsonText = response.bodyAsText()
            val jsonElement = Json.parseToJsonElement(jsonText)

            val generatedText = jsonElement
                .jsonArray
                .firstOrNull()
                ?.jsonObject
                ?.get("generated_text")
                ?.jsonPrimitive
                ?.content

            generatedText ?: "⚠️ Sin respuesta del modelo."
        } catch (e: Exception) {
            "❌ Error al consultar Hugging Face: ${e.message}"
        }
    }
}
