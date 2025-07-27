package com.example.services

import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.domain.repository.UsuarioRepository
import com.example.dto.*
import com.example.dto.auth.LoginRequestDTO
import com.example.dto.auth.LoginResponseDTO
import com.example.dto.UsuarioDTO
import com.example.dto.auth.RegisterRequestDTO
import com.example.db.Usuarios
import com.example.utils.EmailValidator
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*
import com.typesafe.config.ConfigFactory
import com.example.services.ClienteService

class AuthService(
    private val repository: UsuarioRepository,
    private val config: ApplicationConfig,
    private val clienteService: ClienteService = ClienteService()    // inyectar servicio de cliente
) {

    // Hashea la contraseña usando bcrypt
    fun hashPassword(password: String): String =
        BCrypt.withDefaults().hashToString(12, password.toCharArray())

    // Verifica si una contraseña es válida comparándola con su hash
    fun verifyPassword(password: String, hashed: String): Boolean =
        BCrypt.verifyer().verify(password.toCharArray(), hashed).verified

    // Registra un nuevo usuario
    fun register(dto: RegisterRequestDTO): UsuarioDTO {
        val hashedPassword = hashPassword(dto.password)
        
        // Usar el rol que viene del frontend, o determinar automáticamente si no viene
        val rol = if (dto.rol.isNotEmpty() && dto.rol != "CLIENTE") {
            dto.rol // Usar el rol enviado desde el frontend
        } else {
            // Lógica de respaldo: usar EmailValidator para determinar el rol
            EmailValidator.determinarRol(dto.email)
        }
        
        val usuarioCreateUpdateDTO = UsuarioCreateUpdateDTO(
            nombre = dto.nombre,
            apellido = dto.apellido,
            email = dto.email,
            password = dto.password,
            rol = rol,
            estado = "ACTIVO",
            fechaNacimiento = dto.fechaNacimiento
        )
        val usuario = repository.save(usuarioCreateUpdateDTO, hashedPassword)
        // Si es cliente, crear registro en la tabla clientes
        if (rol == "CLIENTE") {
            clienteService.crear(
                com.example.dto.ClienteCreateUpdateDTO(
                    usuarioId = usuario.id,
                    telefono = "",
                    direccion = ""
                )
            )
        }
        return usuario
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
        val config = ConfigFactory.load()
        val jwtSecret = config.getString("jwt.secret")
        val issuer = config.getString("jwt.issuer")
        val audience = config.getString("jwt.audience")

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
