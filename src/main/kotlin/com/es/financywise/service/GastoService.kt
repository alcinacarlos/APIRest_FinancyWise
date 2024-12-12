package com.es.financywise.service

import com.es.financywise.error.exception.NotFoundException
import com.es.financywise.model.Gasto
import com.es.financywise.model.Usuario
import com.es.financywise.repository.GastoRepository
import com.es.financywise.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GastoService() {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository
    @Autowired
    private lateinit var gastoRepository: GastoRepository

    /**
     * Método para registrar un nuevo gasto asociado a un usuario
     */
    fun registerGasto(idUsuario: Long, gasto: Gasto): Gasto {
        val usuario: Usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow { NotFoundException("Usuario con ID $idUsuario no encontrado") }

        gasto.usuario = usuario

        return gastoRepository.save(gasto)
    }

    /**
     * Método para recuperar todos los gastos
     */
    fun getAllGastos(): List<Gasto> {
        return gastoRepository.findAll()
    }

    /**
     * Método para obtener los gastos de un usuario específico
     */
    fun getGastosByUsuario(idUsuario: Long): List<Gasto> {
        val usuario: Usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow { NotFoundException("Usuario con ID $idUsuario no encontrado") }

        return gastoRepository.findByUsuario(usuario)
    }

    /**
     * Método para buscar un gasto por su ID
     */
    fun getGastoById(idGasto: Long): Gasto {
        return gastoRepository.findById(idGasto)
            .orElseThrow { NotFoundException("Gasto con ID $idGasto no encontrado") }
    }

    /**
     * Método para actualizar un gasto existente
     */
    fun updateGasto(idGasto: Long, gasto: Gasto): Gasto {
        val gastoExistente = gastoRepository.findById(idGasto)
            .orElseThrow { NotFoundException("Gasto con ID $idGasto no encontrado") }

        gastoExistente.categoria = gasto.categoria ?: gastoExistente.categoria
        gastoExistente.descripcion = gasto.descripcion ?: gastoExistente.descripcion
        gastoExistente.monto = gasto.monto ?: gastoExistente.monto
        gastoExistente.fecha = gasto.fecha ?: gastoExistente.fecha

        return gastoRepository.save(gastoExistente)
    }

    /**
     * Método para eliminar un gasto
     */
    fun deleteGasto(idGasto: Long) {
        val gasto = gastoRepository.findById(idGasto)
            .orElseThrow { NotFoundException("Gasto con ID $idGasto no encontrado") }

        gastoRepository.delete(gasto)
    }
}