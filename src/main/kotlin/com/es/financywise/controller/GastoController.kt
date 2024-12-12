package com.es.financywise.controller

import com.es.financywise.model.Gasto
import com.es.financywise.service.GastoService
import com.es.financywise.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/gastos")
class GastoController {

    @Autowired
    private lateinit var gastoService: GastoService

    @Autowired
    private lateinit var usuarioService: UsuarioService

    /**
     * Registrar un nuevo gasto asociado a un usuario
     */
    @PostMapping("/{idUsuario}")
    fun createGasto(
        authentication: Authentication,
        @PathVariable idUsuario: Long,
        @RequestBody nuevoGasto: Gasto
    ): ResponseEntity<Any> {
        val usuario = usuarioService.getUsuarioById(idUsuario)

        if (authentication.name == usuario.username || authentication.authorities.any { it.authority == "ROLE_ADMIN" }) {
            val gastoRegistrado = gastoService.registerGasto(idUsuario, nuevoGasto)
            return ResponseEntity(gastoRegistrado, HttpStatus.CREATED)
        } else {
            return ResponseEntity(mapOf("message" to "Acceso denegado"), HttpStatus.FORBIDDEN)
        }
    }

    /**
     * Obtener todos los gastos registrados
     * Solo accesible por admin
     */
    @GetMapping
    fun getAllGastos(authentication: Authentication): ResponseEntity<Any> {
        if (authentication.authorities.any { it.authority == "ROLE_ADMIN" }) {
            val gastos = gastoService.getAllGastos()
            return if (gastos.isNotEmpty()) {
                ResponseEntity(gastos, HttpStatus.OK)
            } else {
                ResponseEntity("No hay gastos registrados", HttpStatus.NO_CONTENT)
            }
        } else {
            return ResponseEntity(mapOf("message" to "Acceso denegado"), HttpStatus.FORBIDDEN)
        }
    }

    /**
     * Obtener los gastos de un usuario específico
     */
    @GetMapping("/usuario/{idUsuario}")
    fun getGastosByUsuario(
        authentication: Authentication,
        @PathVariable idUsuario: Long
    ): ResponseEntity<Any> {
        val usuario = usuarioService.getUsuarioById(idUsuario)

        if (authentication.name == usuario.username || authentication.authorities.any { it.authority == "ROLE_ADMIN" }) {
            val gastos = gastoService.getGastosByUsuario(idUsuario)
            return if (gastos.isNotEmpty()) {
                ResponseEntity(gastos, HttpStatus.OK)
            } else {
                ResponseEntity(mapOf("message" to "El usuario no tiene gastos registrados"), HttpStatus.NO_CONTENT)
            }
        } else {
            return ResponseEntity(mapOf("message" to "Acceso denegado"), HttpStatus.FORBIDDEN)
        }
    }

    /**
     * Obtener un gasto específico por su ID
     */
    @GetMapping("/{idGasto}")
    fun getGastoById(
        authentication: Authentication,
        @PathVariable idGasto: Long
    ): ResponseEntity<Any> {
        val gasto = gastoService.getGastoById(idGasto)

        return if (validarPropietarioOAdmin(authentication, gasto)) {
            ResponseEntity(gasto, HttpStatus.OK)
        } else {
            ResponseEntity(mapOf("message" to "Acceso denegado: No eres el propietario del gasto"), HttpStatus.FORBIDDEN)
        }
    }

    /**
     * Actualizar un gasto existente
     */
    @PutMapping("/{idGasto}")
    fun updateGasto(
        authentication: Authentication,
        @PathVariable idGasto: Long,
        @RequestBody gastoActualizado: Gasto
    ): ResponseEntity<Any> {
        val gastoExistente = gastoService.getGastoById(idGasto)

        if (validarPropietarioOAdmin(authentication, gastoExistente)) {
            val gastoActualizadoFinal = gastoService.updateGasto(idGasto, gastoActualizado)
            return ResponseEntity(gastoActualizadoFinal, HttpStatus.OK)
        } else {
            return ResponseEntity(mapOf("message" to "Acceso denegado: No eres el propietario del gasto"), HttpStatus.FORBIDDEN)
        }
    }

    /**
     * Eliminar un gasto
     */
    @DeleteMapping("/{idGasto}")
    fun deleteGasto(
        authentication: Authentication,
        @PathVariable idGasto: Long
    ): ResponseEntity<Any> {
        val gastoExistente = gastoService.getGastoById(idGasto)

        if (validarPropietarioOAdmin(authentication, gastoExistente)) {
            gastoService.deleteGasto(idGasto)
            return ResponseEntity(mapOf("message" to "Gasto eliminado exitosamente"), HttpStatus.OK)
        } else {
            return ResponseEntity(mapOf("message" to "Acceso denegado: No eres el propietario del gasto"), HttpStatus.FORBIDDEN)
        }
    }

    /**
     * Método reutilizable para validar si el usuario autenticado es el propietario del gasto o tiene rol de admin
     */
    private fun validarPropietarioOAdmin(authentication: Authentication, gasto: Gasto): Boolean {
        return gasto.usuario?.username == authentication.name || authentication.authorities.any { it.authority == "ROLE_ADMIN" }
    }
}