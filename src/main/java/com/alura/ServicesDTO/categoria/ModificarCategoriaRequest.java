package com.alura.ServicesDTO.categoria;

import jakarta.validation.constraints.NotBlank;

public record ModificarCategoriaRequest(@NotBlank String nombre){
}