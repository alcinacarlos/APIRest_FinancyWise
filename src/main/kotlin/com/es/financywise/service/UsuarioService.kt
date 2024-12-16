package com.es.financywise.service

import com.es.financywise.error.exception.*
import com.es.financywise.model.Usuario
import com.es.financywise.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.User

@Service
class UsuarioService : UserDetailsService {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder


    /**
     * Implementación de UserDetailsService para Spring Security
     */
    override fun loadUserByUsername(username: String): UserDetails {
        val usuario: Usuario = usuarioRepository
            .findByUsername(username)
            .orElseThrow { NotFoundException("Usuario con username $username no encontrado") }

        return User.builder()
            .username(usuario.username)
            .password(usuario.password)
            .roles(usuario.roles)
            .build()
    }

    /**
     * Método para registrar un nuevo usuario
     */
    fun registerUsuario(usuario: Usuario): Usuario {
        if (!isValidEmail(usuario.email!!)) throw BadRequestException("El email ${usuario.email} no es valido")

        if (usuarioRepository.findByUsername(usuario.username!!).isPresent) {
            throw BadRequestException("El username ${usuario.username} ya está en uso")
        }
        if (usuarioRepository.findByEmail(usuario.email!!).isPresent) {
            throw BadRequestException("El email ${usuario.email} ya está en uso")
        }


        usuario.password = passwordEncoder.encode(usuario.password)
        usuario.roles = "USER"

        return usuarioRepository.save(usuario)
    }
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return email.matches(emailRegex.toRegex())
    }


    /**
     * Método para obtener todos los usuarios
     */
    fun getAllUsuarios(): List<Usuario> {
        return usuarioRepository.findAll()
    }

    /**
     * Método para obtener un usuario por su ID
     */
    fun getUsuarioById(idUsuario: Long): Usuario {
        return usuarioRepository.findById(idUsuario)
            .orElseThrow { NotFoundException("Usuario con ID $idUsuario no encontrado") }
    }

    /**
     * Método para actualizar un usuario existente
     */
    fun updateUsuario(usuario: Usuario): Usuario {
        val usuarioExistente = usuarioRepository.findByUsername(usuario.username!!)
            .orElseThrow { NotFoundException("Usuario con ID ${usuario.username} no encontrado") }

        usuarioExistente.username = usuario.username
        usuarioExistente.email = usuario.email

        if (usuario.password.isNullOrBlank()) {
            usuarioExistente.password = passwordEncoder.encode(usuario.password)
        }

        return usuarioRepository.save(usuarioExistente)
    }

    /**
     * Método para eliminar un usuario por su ID
     */
    fun deleteUsuario(username: String) {
        val usuario = usuarioRepository.findByUsername(username)
            .orElseThrow { NotFoundException("Usuario con ID $username no encontrado") }

        usuarioRepository.delete(usuario)
    }

    /**
     * Método para buscar un usuario por su username
     */

    fun findByUsername(username: String): Usuario{
       return usuarioRepository.findByUsername(username).orElseThrow { NotFoundException("Usuario con username $username no encontrado") }
    }


}