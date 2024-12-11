package com.es.spliteasy.service

import com.es.spliteasy.model.Usuario
import com.es.spliteasy.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UsuarioService : UserDetailsService {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository


    /*
    TODO
     */
    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario: Usuario = usuarioRepository
            .findByUsername(username!!)
            .orElseThrow()


        return User.builder()
            .username(usuario.username)
            .password(usuario.password)
            .roles(usuario.roles)
            .build( )
    }


    /*
    MÉTODO PARA INSERTAR UN USUARIO
     */
    fun registerUsuario(usuario: Usuario) : Usuario? {

        // Comprobamos que el usuario no existe en la base de datos


        // Creamos la instancia de Usuario


        /*
         La password del newUsuario debe estar hasheada, así que usamos el passwordEncoder que tenemos definido.
         ¿De dónde viene ese passwordEncoder?
         El objeto passwordEncoder lo tenemos que inyectar, y viene desde la clase SecurityConfig
         */


        // Guardamos el newUsuario en la base de datos... igual que siempre



        // Devolvemos el Usuario insertado en la BDD
        return null // Cambiar null por el usuario

    }



}