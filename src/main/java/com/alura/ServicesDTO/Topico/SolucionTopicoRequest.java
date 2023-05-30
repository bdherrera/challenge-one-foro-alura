package com.alura.ServicesDTO.Topico;

import jakarta.validation.constraints.NotNull;

public record SolucionTopicoRequest(@NotNull Long idRespuesta) {
}
