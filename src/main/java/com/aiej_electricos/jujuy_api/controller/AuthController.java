package com.aiej_electricos.jujuy_api.controller;

import com.aiej_electricos.jujuy_api.dto.LoginDto;
import com.aiej_electricos.jujuy_api.dto.ResponseDTO;
import com.aiej_electricos.jujuy_api.dto.RegistroUsuarioDto;
import com.aiej_electricos.jujuy_api.dto.TokenDto;
import com.aiej_electricos.jujuy_api.service.UsuarioService;
import com.aiej_electricos.jujuy_api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UsuarioService usuarioService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/registro")
    public ResponseEntity<ResponseDTO> registrarUsuario(@RequestBody @Valid RegistroUsuarioDto registroUsuarioDto) {
        try {
            ResponseDTO nuevoUsuario = usuarioService.registrarUsuario(registroUsuarioDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.contrasena()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        return ResponseEntity.ok(new TokenDto(jwt));
    }
}