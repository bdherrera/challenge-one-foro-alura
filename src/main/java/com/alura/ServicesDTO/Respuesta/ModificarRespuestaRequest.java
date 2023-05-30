package com.alura.ServicesDTO.Respuesta;

import jakarta.validation.constraints.NotBlank;

public record ModificarRespuestaRequest(@NotBlank String mensaje) {
}