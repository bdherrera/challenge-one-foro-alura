package com.alura.ServicesDTO.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModificarCursoRequest(@NotBlank String nombre, @NotNull Long idCategoria){
}