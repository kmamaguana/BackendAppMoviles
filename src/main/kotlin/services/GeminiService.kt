package com.example.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class GeminiService(private val apiKey: String) {

    private val endpoint =
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent"

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    // Preguntas frecuentes como contexto
    private val promptBase = """
Eres un asistente virtual para una veterinaria. Solo debes responder preguntas relacionadas con los siguientes datos. 
Si te hacen una pregunta fuera de estos temas, responde con: "⚠️ Lo siento, no tengo información sobre eso. Por favor contacta con un asesor."

Preguntas frecuentes:

1. Horarios de atención:
- Matriz Carapungo: Lunes a Viernes 8 a.m. – 8 p.m., Sábado 7:30 a.m. – 7:30 p.m., Domingo SOLO EMERGENCIAS.
- Sucursal Llano Grande: Lunes a Viernes 7 a.m. – 10 p.m., Domingo 7 a.m. – 10 p.m., Sábado NO HAY ATENCIÓN.

2. Feriados: Atendemos con normalidad, excepto los sábados.

3. Precio peluquería canina: Desde $14, depende del tamaño, corte y pelaje.

4. Servicio a domicilio: Sí, con recargo adicional.

5. Consulta médica: $16.

6. Esterilización: Depende del peso de la mascota.

7. Disponibilidad de medicamentos: Un asesor indicará si hay stock.

8. Vacunas anuales: Múltiple, Bordetella, Rabia ($15 cada una, incluye desparasitación).

9. Edad para la primera vacuna:
- Perros: 6 semanas.
- Gatos: 8 semanas.

10. Servicios ofrecidos: Pet Shop, peluquería canina, farmacia, rayos x, ecografía, consulta general, especialidades (oftalmología, neurología, oncología, dermatología, odontología, felinos, cirugía, traumatología, emergencias 24/7).

11. Productos: Levamisol, Labimec, Frofit, Vitonal B, Nutrique Dog Skin Sensitivity.

12. Peluquería incluye: Corte (raza o gusto), baño, corte de uñas, limpieza de oídos y glándulas perianales, accesorio.

13. Vacuna incluye: Chequeo, vacuna y desparasitación.

14. Métodos de pago: Efectivo, tarjeta y transferencia.

15. Ubicaciones:
- Matriz Carapungo: Av. Galo Plaza Lasso N15 – 396
- Llano Grande: Av. Gabriel García Moreno y Psje. Orquídeas

16. Vacunas para perros: Puppy, Múltiple, Rabia, Bordetella.
    Para gatos: Triple Felina, Leucemia Felina, Rabia.
    Cantidad depende de la edad.

17. Agendamiento de citas: Deja tus datos y disponibilidad para que te contactemos.

18. Emergencias: Llama al 0978951419.
""".trimIndent()

    suspend fun consultarModelo(userPrompt: String): String {
        val fullPrompt = "$promptBase\n\nUsuario: $userPrompt\nAsistente:"

        val requestBody = GeminiRequest(
            contents = listOf(
                Content(
                    parts = listOf(Part(text = fullPrompt))
                )
            )
        )

        return try {
            val response: HttpResponse = httpClient.post("$endpoint?key=$apiKey") {
                contentType(ContentType.Application.Json)
                setBody(requestBody)
            }

            val result = response.body<GeminiResponse>()
            result.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: "⚠️ Sin respuesta del modelo."
        } catch (e: Exception) {
            e.printStackTrace()
            "⚠️ Ocurrió un error al consultar el modelo."
        }
    }
}

@Serializable
data class GeminiRequest(val contents: List<Content>)

@Serializable
data class Content(val parts: List<Part>)

@Serializable
data class Part(val text: String)

@Serializable
data class GeminiResponse(val candidates: List<Candidate>)

@Serializable
data class Candidate(val content: Content)
