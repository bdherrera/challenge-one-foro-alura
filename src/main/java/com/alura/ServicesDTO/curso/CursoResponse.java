package com.alura.ServicesDTO.curso;

import com.alura.modelo.Curso;

public record CursoResponse(Long id, String nombre, Long idCategoria) {

    public CursoResponse(Curso curso) {
        this(curso.getId(),
                curso.getNombre(),
                curso.getCategoria().getId());
    }
}