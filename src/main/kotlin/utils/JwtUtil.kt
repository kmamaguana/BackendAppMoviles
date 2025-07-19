package com.example.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.dto.UsuarioDTO
import java.util.*

class JwtUtil(secret: String, private val issuer: String, private val audience: String) {
    private val algorithm = Algorithm.HMAC256(secret)

    fun generateToken(usuario: UsuarioDTO): String = JWT.create()
        .withIssuer(issuer)
        .withAudience(audience)
        .withSubject(usuario.id)
        .withClaim("nombre", usuario.nombre)
        .withClaim("rol", usuario.rol)
        .withExpiresAt(Date(System.currentTimeMillis() + 600_000)) // 10 min
        .sign(algorithm)
}
