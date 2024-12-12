package com.es.financywise.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rutas_protegidas")
class RutaProtegidaController {


    @GetMapping("/recurso1")
    fun getRecursoProtegidoUno () : String {
        return "Este recurso sólo puede ser accedido por usuarios registrados en la BDD \uD83E\uDD75"
    }

    @GetMapping("/recurso2")
    fun getRecursoProtegidoPublico () : String {
        return "Este recurso puede ser accedido por cualquiera \uD83E\uDD75"
    }

    @GetMapping("/recurso/{id}")
    fun getRecursoProtegido (@PathVariable id: String) : String {
        return "Obtener recurso por su id $id \uD83E\uDD75"
    }

    @DeleteMapping("/recurso/{id}")
    fun deleteRecursoProtegido (@PathVariable id: String) : String {
        return "Eliminar recurso por su id $id \uD83E\uDD75"
    }

    @GetMapping("/usuario_autenticado")
    fun saludarUsuarioAutenticado(
        authentication: Authentication
    ):ResponseEntity<Any>{
        return ResponseEntity("Hola ${authentication.name} qué tal bro", HttpStatus.OK)
    }

    @DeleteMapping("/eliminar/{name}")
    fun eliminarUsuario(
        authentication: Authentication,
        @PathVariable name: String
    ):String {
        if ( authentication.name == name){
            return "Hola $name, te vas a eliminar a ti mismo"
        }

        if (authentication.authorities.any { it.authority == "ROLE_ADMIN"  }){
            return "Saludos admin"
        }
        return "Natty"
    }

}