package com.es.financywise.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "ingresos")
data class Ingreso(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idIngreso: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    var usuario: Usuario? = null,

    @Column(nullable = false)
    var categoria: String? = null,

    @Column(nullable = true)
    var descripcion: String? = null,

    @Column(nullable = false)
    var monto: Double? = null,

    @Column(nullable = false)
    var fecha: LocalDate? = null
)
