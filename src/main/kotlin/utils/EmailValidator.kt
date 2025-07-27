package com.example.utils

/**
 * Clase utilitaria para validación de emails y determinación de roles de usuario
 */
object EmailValidator {
    
    /**
     * Determina si el email corresponde a un administrador
     * @param email El email del usuario
     * @return true si es admin (contiene 'admin' en el dominio), false si es cliente
     */
    fun esAdmin(email: String): Boolean {
        val dominio = obtenerDominio(email)
        return dominio.lowercase().contains("admin")
    }
    
    /**
     * Determina el rol del usuario basándose en el dominio del email
     * @param email El email del usuario
     * @return "ADMIN" si el email contiene 'admin' en el dominio, "CLIENTE" para otros dominios
     */
    fun determinarRol(email: String): String {
        return if (esAdmin(email)) {
            "ADMIN"
        } else {
            "CLIENTE"
        }
    }
    
    /**
     * Obtiene el dominio del email
     * @param email El email del usuario
     * @return El dominio del email (ej: gmail.com, hotmail.com, admin.com)
     */
    fun obtenerDominio(email: String): String {
        return if (email.contains("@")) {
            email.substringAfter("@")
        } else {
            ""
        }
    }
    
    /**
     * Valida si el email tiene un formato válido
     * @param email El email a validar
     * @return true si el email es válido, false en caso contrario
     */
    fun validarEmail(email: String): Boolean {
        if (email.isEmpty()) return false
        
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
        return email.matches(emailRegex.toRegex())
    }
} 