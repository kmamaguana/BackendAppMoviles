package com.example.db

import org.jetbrains.exposed.sql.Database

fun connectToDatabase() {
    val jdbcUrl = "jdbc:postgresql://localhost:5432/vetapp"
    val driver = "org.postgresql.Driver"
    val user = "postgres"
    val password = "12345"

    Database.connect(jdbcUrl, driver, user, password)
}
