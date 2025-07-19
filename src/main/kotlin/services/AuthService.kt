package com.example.services

import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.domain.repository.UsuarioRepository
import com.example.dto.*
import com.example.dto.auth.LoginRequestDTO
import com.example.dto.auth.LoginResponseDTO
import com.example.db.Usuarios
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class AuthService(
    private val repository: UsuarioRepository,
    private val config: ApplicationConfig
) {

    // Hashea la contraseña usando bcrypt
    fun hashPassword(password: String): String =
        BCrypt.withDefaults().hashToString(12, password.toCharArray())

    // Verifica si una contraseña es válida comparándola con su hash
    fun verifyPassword(password: String, hashed: String): Boolean =
        BCrypt.verifyer().verify(password.toCharArray(), hashed).verified

    // Registra un nuevo usuario
    fun register(dto: UsuarioCreateUpdateDTO): UsuarioDTO {
        val hashedPassword = hashPassword(dto.password)
        return repository.save(dto, hashedPassword)
    }

    // Inicia sesión: verifica email y contraseña, genera JWT
    fun login(loginDto: LoginRequestDTO): LoginResponseDTO? {
        val usuario = repository.findByEmail(loginDto.email) ?: return null

        val userId = usuario.id.toIntOrNull() ?: return null  // ← Convertimos ID de String a Int

        // Obtener contraseña hasheada desde la base de datos
        val row = transaction {
            Usuarios.select { Usuarios.id eq userId }.singleOrNull()
        } ?: return null

        val hashedPassword = row[Usuarios.contraseña]

        // Validar contraseña
        if (!verifyPassword(loginDto.password, hashedPassword)) return null

        // Actualizar campo 'ultimoLogin'
        transaction {
            Usuarios.update({ Usuarios.id eq userId }) {
                it[ultimoLogin] = java.time.LocalDateTime.now()
            }
        }

        // Generar token
        val token = generateToken(usuario)

        return LoginResponseDTO(
            token = token,
            usuarioId = usuario.id,
            nombre = usuario.nombre,
            rol = usuario.rol
        )
    }

    // Genera el token JWT con los datos del usuario
    private fun generateToken(usuario: UsuarioDTO): String {
        val jwtSecret = config.property("jwt.secret").getString()
        val issuer = config.property("jwt.issuer").getString()
        val audience = config.property("jwt.audience").getString()

        val algorithm = Algorithm.HMAC256(jwtSecret)

        return JWT.create()
            .withIssuer(issuer)
            .withAudience(audience)
            .withSubject(usuario.id) // id como String
            .withClaim("nombre", usuario.nombre)
            .withClaim("rol", usuario.rol)
            .withExpiresAt(Date(System.currentTimeMillis() + 600_000)) // 10 minutos
            .sign(algorithm)
    }
}
