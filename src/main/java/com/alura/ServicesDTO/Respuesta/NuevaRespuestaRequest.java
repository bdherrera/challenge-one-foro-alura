package com.alura.ServicesDTO.Respuesta;


import jakarta.validation.constraints.NotBlank;

public record NuevaRespuestaRequest(@NotBlank String mensaje) {
}