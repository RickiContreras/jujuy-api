package com.aiej_electricos.jujuy_api.dto;

import java.time.LocalDateTime;

public record ResponseDTO(
 Long id,
        String nombreUsuario,
        String email,
        Boolean esSocio,
        LocalDateTime fechaCreacion
) {
}
