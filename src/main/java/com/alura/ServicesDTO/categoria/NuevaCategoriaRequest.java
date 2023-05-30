package com.alura.ServicesDTO.categoria;

import jakarta.validation.constraints.NotBlank;

public record NuevaCategoriaRequest(@NotBlank String nombre) {
}