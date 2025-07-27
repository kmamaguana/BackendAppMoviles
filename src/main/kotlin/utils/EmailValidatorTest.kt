package com.example.utils

/**
 * Test simple para verificar la validación de emails en el backend
 * Este archivo es solo para verificación manual, no es parte de la aplicación
 */
object EmailValidatorTest {
    
    fun testValidacionEmails() {
        println("=== Test de Validación de Emails (Backend) ===")
        
        // Test emails de administrador
        val emailsAdmin = listOf(
            "admin@admin",
            "usuario@admin.com", 
            "supervisor@admin.org",
            "admin@admin.test",
            "da@admin.com"  // El caso que estaba fallando
        )
        
        // Test emails de cliente
        val emailsCliente = listOf(
            "usuario@gmail.com",
            "cliente@hotmail.com",
            "persona@outlook.com",
            "test@yahoo.com"
        )
        
        println("\n--- Emails de Administrador ---")
        emailsAdmin.forEach { email ->
            val esAdmin = EmailValidator.esAdmin(email)
            val rol = EmailValidator.determinarRol(email)
            val dominio = EmailValidator.obtenerDominio(email)
            println("Email: $email")
            println("  Dominio: $dominio")
            println("  Es Admin: $esAdmin")
            println("  Rol: $rol")
            println()
        }
        
        println("\n--- Emails de Cliente ---")
        emailsCliente.forEach { email ->
            val esAdmin = EmailValidator.esAdmin(email)
            val rol = EmailValidator.determinarRol(email)
            val dominio = EmailValidator.obtenerDominio(email)
            println("Email: $email")
            println("  Dominio: $dominio")
            println("  Es Admin: $esAdmin")
            println("  Rol: $rol")
            println()
        }
        
        println("=== Fin del Test ===")
    }
} 