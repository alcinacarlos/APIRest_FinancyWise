package com.es.financywise.repository

import com.es.financywise.model.Gasto
import com.es.financywise.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GastoRepository : JpaRepository<Gasto, Long> {
    fun findByUsuario(usuario: Usuario): List<Gasto>
}