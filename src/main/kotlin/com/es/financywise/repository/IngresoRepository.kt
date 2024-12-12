package com.es.financywise.repository

import com.es.financywise.model.Ingreso
import com.es.financywise.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IngresoRepository : JpaRepository<Ingreso, Long> {
    fun findByUsuario(usuario: Usuario): List<Ingreso>
}