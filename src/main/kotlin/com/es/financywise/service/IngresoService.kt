package com.es.financywise.service

import com.es.financywise.error.exception.NotFoundException
import com.es.financywise.model.Ingreso
import com.es.financywise.model.Usuario
import com.es.financywise.repository.IngresoRepository
import com.es.financywise.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class IngresoService() {
    @Autowired
    private lateinit var ingresoRepository: IngresoRepository

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    /**
     * Método para registrar un nuevo ingreso asociado a un usuario
     */
    fun registerIngreso(idUsuario: Long, ingreso: Ingreso): Ingreso {
        val usuario: Usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow { NotFoundException("Usuario con ID $idUsuario no encontrado") }

        ingreso.usuario = usuario

        return ingresoRepository.save(ingreso)
    }

    /**
     * Método para recuperar todos los ingresos
     */
    fun getAllIngresos(): List<Ingreso> {
        return ingresoRepository.findAll()
    }

    /**
     * Método para obtener los ingresos de un usuario específico
     */
    fun getIngresosByUsuario(idUsuario: Long): List<Ingreso> {
        val usuario: Usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow { NotFoundException("Usuario con ID $idUsuario no encontrado") }

        return ingresoRepository.findByUsuario(usuario)
    }

    /**
     * Método para buscar un ingreso por su ID
     */
    fun getIngresoById(idIngreso: Long): Ingreso {
        return ingresoRepository.findById(idIngreso)
            .orElseThrow { NotFoundException("Ingreso con ID $idIngreso no encontrado") }
    }

    /**
     * Método para actualizar un ingreso existente
     */
    fun updateIngreso(idIngreso: Long, ingreso: Ingreso): Ingreso {
        val ingresoExistente = ingresoRepository.findById(idIngreso)
            .orElseThrow { NotFoundException("Ingreso con ID $idIngreso no encontrado") }

        // Actualizar datos del ingreso (mantenemos lo existente si no se provee un dato)
        ingresoExistente.categoria = ingreso.categoria ?: ingresoExistente.categoria
        ingresoExistente.descripcion = ingreso.descripcion ?: ingresoExistente.descripcion
        ingresoExistente.monto = ingreso.monto ?: ingresoExistente.monto
        ingresoExistente.fecha = ingreso.fecha ?: ingresoExistente.fecha

        return ingresoRepository.save(ingresoExistente)
    }

    /**
     * Método para eliminar un ingreso
     */
    fun deleteIngreso(idIngreso: Long) {
        val ingreso = ingresoRepository.findById(idIngreso)
            .orElseThrow { NotFoundException("Ingreso con ID $idIngreso no encontrado") }

        ingresoRepository.delete(ingreso)
    }
}