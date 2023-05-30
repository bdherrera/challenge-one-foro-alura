package com.alura.ServicesDTO.usuario;

import jakarta.validation.constraints.NotNull;

public record DesactivarUsuarioRequest(@NotNull Long id) {
}
