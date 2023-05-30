package com.alura.ServicesDTO.Topico;

import com.alura.modelo.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModificarTopicoRequest(@NotNull Long idCurso,
                                     @NotBlank String titulo,
                                     @NotBlank String mensaje,
                                     @NotNull Tag tag) {
}