package com.alura.ServicesDTO.Respuesta;

import jakarta.validation.constraints.NotNull;

public record SolucionRespuestaRequest(@NotNull Long idTopico, @NotNull Long idRespuesta) {
}