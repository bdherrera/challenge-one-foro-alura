package com.alura.ServicesDTO.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActualizarUsuarioRequest(@NotBlank @Email String email, @NotNull String password) {
}
