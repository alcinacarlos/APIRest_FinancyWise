package com.es.financywise.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
@Entity
@Table(name = "usuarios")
data class Usuario(
    @Id
    var username: String? = null,

    @Column(unique = true, nullable = false)
    var email: String? = null,

    @Column(nullable = false)
    var password: String? = null,

    @Column(nullable = false)
    var fecha_creaccion: LocalDateTime? = LocalDateTime.now(),

    var roles: String? = null // e.g., "ROLE_USER,ROLE_ADMIN
)

