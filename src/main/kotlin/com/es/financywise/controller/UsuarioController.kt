package com.es.financywise.controller

import com.es.financywise.model.Usuario
import com.es.financywise.service.TokenService
import com.es.financywise.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuarios")
class UsuarioController {

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var tokenService: TokenService

    /*
    MÉTODO PARA INSERTAR UN USUARIO
     */
    @PostMapping("/register")
    fun register(@RequestBody newUsuario: Usuario): ResponseEntity<Usuario> {
        val usuarioRegistrado = usuarioService.registerUsuario(newUsuario)
        return ResponseEntity(usuarioRegistrado, HttpStatus.CREATED)
    }


    /*
    MÉTODO (ENDPOINT) PARA HACER UN LOGIN
     */
    @PostMapping("/login")
    fun login(@RequestBody usuario: Usuario): ResponseEntity<Any> {

        val authentication: Authentication
        try {
            authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    usuario.username,
                    usuario.password
                )
            )
        } catch (e: AuthenticationException) {
            return ResponseEntity(mapOf("mensaje" to "Credenciales incorrectas"), HttpStatus.UNAUTHORIZED)
        }


        //si pasamos la autenticacion, sigifica que estamos bien autenticados
        //pasamos a generar token
        val token = tokenService.generarToken(authentication)
        return ResponseEntity(mapOf("token" to token), HttpStatus.CREATED)
    }
    /**
     * Obtener la información de un usuario por su username (Solo el dueño o un administrador pueden acceder).
     */
    @GetMapping("/{username}")
    fun getUsuarioByUsername(
        authentication: Authentication,
        @PathVariable username: String
    ): ResponseEntity<Any> {
        val usuario = usuarioService.findByUsername(username)

        return if (authentication.name == usuario.username || authentication.authorities.any { it.authority == "ROLE_ADMIN" }) {
            ResponseEntity(usuario, HttpStatus.OK)
        } else {
            ResponseEntity(mapOf("message" to "Acceso denegado"), HttpStatus.FORBIDDEN)
        }
    }

    /**
     * Obtener todos los usuarios (Solo accesible por administradores).
     */
    @GetMapping
    fun getAllUsuarios(authentication: Authentication): ResponseEntity<Any> {
        return if (authentication.authorities.any { it.authority == "ROLE_ADMIN" }) {
            val usuarios = usuarioService.getAllUsuarios()
            ResponseEntity(usuarios, HttpStatus.OK)
        } else {
            ResponseEntity(mapOf("message" to "Acceso denegado"), HttpStatus.FORBIDDEN)
        }
    }

    /**
     * Actualizar la información de un usuario (Se requiere que sea el usuario autenticado o un administrador).
     */
    @PutMapping("/{username}")
    fun updateUsuario(
        authentication: Authentication,
        @PathVariable username: String,
        @RequestBody updatedUsuario: Usuario
    ): ResponseEntity<Any> {

        return if (authentication.name == updatedUsuario.username || authentication.authorities.any { it.authority == "ROLE_ADMIN" }) {
            val usuarioActualizado = usuarioService.updateUsuario(updatedUsuario)
            ResponseEntity(usuarioActualizado, HttpStatus.OK)
        } else {
            ResponseEntity(mapOf("message" to "Acceso denegado"), HttpStatus.FORBIDDEN)
        }
    }

    /**
     * Eliminar un usuario por su username (Solo el admin puede eliminar usuarios).
     */
    @DeleteMapping("/{username}")
    fun deleteUsuario(
        authentication: Authentication,
        @PathVariable username: String
    ): ResponseEntity<Any> {
        if (authentication.name == username || authentication.authorities.any { it.authority == "ROLE_ADMIN" }) {
            usuarioService.deleteUsuario(username)
            return ResponseEntity(mapOf("message" to "Usuario eliminado exitosamente"), HttpStatus.OK)
        }
        return ResponseEntity(mapOf("message" to "Acceso denegado"), HttpStatus.FORBIDDEN)
    }



}