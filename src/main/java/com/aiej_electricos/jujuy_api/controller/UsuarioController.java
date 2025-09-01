package com.aiej_electricos.jujuy_api.controller;

import com.aiej_electricos.jujuy_api.entidades.Usuario;
import com.aiej_electricos.jujuy_api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/por-email")
    public ResponseEntity<Usuario> findByEmail(@RequestParam String email) {
        Optional<Usuario> usuario = usuarioService.findByEmail(email);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/perfil")
    public ResponseEntity<String> getPerfilUsuario() {
        return ResponseEntity.ok("Acceso al perfil de usuario autorizado. ¡JWT funciona!");
    }
}
