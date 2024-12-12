package com.es.financywise.controller

import com.es.financywise.model.Ingreso
import com.es.financywise.service.IngresoService
import com.es.financywise.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ingresos")
class IngresoController {

    @Autowired
    private lateinit var ingresoService: IngresoService

    @Autowired
    private lateinit var usuarioService: UsuarioService

    /**
     * Registrar un nuevo ingreso asociado a un usuario
     */
    @PostMapping("/{idUsuario}")
    fun createIngreso(
        authentication: Authentication,
        @PathVariable idUsuario: Long,
        @RequestBody nuevoIngreso: Ingreso
    ): ResponseEntity<Any> {
        val usuario = usuarioService.getUsuarioById(idUsuario)

        if (authentication.name == usuario.username || authentication.authorities.any { it.authority == "ROLE_ADMIN" }) {
            val ingresoRegistrado = ingresoService.registerIngreso(idUsuario, nuevoIngreso)
            return ResponseEntity(ingresoRegistrado, HttpStatus.CREATED)
        } else {
            return ResponseEntity(mapOf("message" to "Acceso denegado"), HttpStatus.FORBIDDEN)
        }
    }

    /**
     * Obtener todos los ingresos registrados
     * Solo accesible por admin
     */
    @GetMapping
    fun getAllIngresos(authentication: Authentication): ResponseEntity<Any> {
        if (authentication.authorities.any { it.authority == "ROLE_ADMIN" }) {
            val ingresos = ingresoService.getAllIngresos()
            return if (ingresos.isNotEmpty()) {
                ResponseEntity(ingresos, HttpStatus.OK)
            } else {
                ResponseEntity("No hay ingresos registrados", HttpStatus.NO_CONTENT)
            }
        } else {
            return ResponseEntity(mapOf("message" to "Acceso denegado"), HttpStatus.FORBIDDEN)
        }
    }

    /**
     * Obtener los ingresos de un usuario específico
     */
    @GetMapping("/usuario/{idUsuario}")
    fun getIngresosByUsuario(
        authentication: Authentication,
        @PathVariable idUsuario: Long
    ): ResponseEntity<Any> {
        val usuario = usuarioService.getUsuarioById(idUsuario)

        if (authentication.name == usuario.username || authentication.authorities.any { it.authority == "ROLE_ADMIN" }) {
            val ingresos = ingresoService.getIngresosByUsuario(idUsuario)
            return if (ingresos.isNotEmpty()) {
                ResponseEntity(ingresos, HttpStatus.OK)
            } else {
                ResponseEntity("El usuario no tiene ingresos registrados", HttpStatus.NO_CONTENT)
            }
        } else {
            return ResponseEntity(mapOf("message" to "Acceso denegado"), HttpStatus.FORBIDDEN)
        }
    }

    /**
     * Obtener un ingreso específico por su ID
     */
    @GetMapping("/{idIngreso}")
    fun getIngresoById(
        authentication: Authentication,
        @PathVariable idIngreso: Long
    ): ResponseEntity<Any> {
        val ingreso = ingresoService.getIngresoById(idIngreso)

        return if (validarPropietarioOAdmin(authentication, ingreso)) {
            ResponseEntity(ingreso, HttpStatus.OK)
        } else {
            return ResponseEntity(mapOf("message" to "Acceso denegado: No eres el propietario del ingreso"), HttpStatus.FORBIDDEN)
        }
    }

    /**
     * Actualizar un ingreso existente
     */
    @PutMapping("/{idIngreso}")
    fun updateIngreso(
        authentication: Authentication,
        @PathVariable idIngreso: Long,
        @RequestBody ingresoActualizado: Ingreso
    ): ResponseEntity<Any> {
        val ingresoExistente = ingresoService.getIngresoById(idIngreso)

        return if (validarPropietarioOAdmin(authentication, ingresoExistente)) {
            val ingresoActualizadoFinal = ingresoService.updateIngreso(idIngreso, ingresoActualizado)
            ResponseEntity(ingresoActualizadoFinal, HttpStatus.OK)
        } else {
            return ResponseEntity(mapOf("message" to "Acceso denegado: No eres el propietario del ingreso"), HttpStatus.FORBIDDEN)
        }
    }

    /**
     * Eliminar un ingreso
     */
    @DeleteMapping("/{idIngreso}")
    fun deleteIngreso(
        authentication: Authentication,
        @PathVariable idIngreso: Long
    ): ResponseEntity<Any> {
        val ingresoExistente = ingresoService.getIngresoById(idIngreso)

        return if (validarPropietarioOAdmin(authentication, ingresoExistente)) {
            ingresoService.deleteIngreso(idIngreso)
            ResponseEntity(mapOf("message" to "Ingreso eliminado exitosamente"), HttpStatus.OK)
        } else {
            return ResponseEntity(mapOf("message" to "Acceso denegado: No eres el propietario del ingreso"), HttpStatus.FORBIDDEN)
        }
    }

    /**
     * Método reutilizable para validar si el usuario autenticado es el propietario del ingreso o tiene rol de admin
     */
    private fun validarPropietarioOAdmin(authentication: Authentication, ingreso: Ingreso): Boolean {
        return ingreso.usuario?.username == authentication.name || authentication.authorities.any { it.authority == "ROLE_ADMIN" }
    }
}