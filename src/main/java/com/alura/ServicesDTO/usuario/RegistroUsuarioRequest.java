package com.alura.ServicesDTO.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegistroUsuarioRequest(@NotBlank @Email String email,@NotBlank String password) {
}