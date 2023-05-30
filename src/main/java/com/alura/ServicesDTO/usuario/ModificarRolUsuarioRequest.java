package com.alura.ServicesDTO.usuario;

import com.alura.modelo.Rol;
import jakarta.validation.constraints.NotNull;

public record ModificarRolUsuarioRequest(@NotNull Rol rol) {
}