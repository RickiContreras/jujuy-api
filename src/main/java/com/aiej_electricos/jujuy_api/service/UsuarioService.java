package com.aiej_electricos.jujuy_api.service;

import com.aiej_electricos.jujuy_api.entidades.Usuario;
import com.aiej_electricos.jujuy_api.dto.RegistroUsuarioDto;
import com.aiej_electricos.jujuy_api.dto.ResponseDTO;
import com.aiej_electricos.jujuy_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getContrasena(),
                Collections.emptyList()
        );
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public ResponseDTO registrarUsuario(RegistroUsuarioDto registroUsuarioDto) {
        // Verificar si el email ya está en uso
        if (usuarioRepository.findByEmail(registroUsuarioDto.email()).isPresent()) {
            throw new RuntimeException("El email ya está registrado.");
        }

        // Verificar si el nombre de usuario ya está en uso
        if (usuarioRepository.findByNombreUsuario(registroUsuarioDto.nombreUsuario()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe.");
        }

        // Crear una nueva instancia de la entidad Usuario desde el DTO
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(registroUsuarioDto.nombreUsuario());
        nuevoUsuario.setEmail(registroUsuarioDto.email());
        // Encriptar la contraseña antes de guardarla
        nuevoUsuario.setContrasena(passwordEncoder.encode(registroUsuarioDto.contrasena()));
        nuevoUsuario.setEsSocio(false); // Por defecto, un nuevo usuario no es socio

        // Guardar el nuevo usuario en la base de datos
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        // Mapear la entidad guardada al DTO de respuesta y devolverlo
        return new ResponseDTO(
                usuarioGuardado.getId(),
                usuarioGuardado.getNombreUsuario(),
                usuarioGuardado.getEmail(),
                usuarioGuardado.getEsSocio(),
                usuarioGuardado.getFechaCreacion().toLocalDateTime()
        );
    }
}