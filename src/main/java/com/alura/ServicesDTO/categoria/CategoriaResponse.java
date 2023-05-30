package com.alura.ServicesDTO.categoria;

import com.alura.modelo.Categoria;

public record CategoriaResponse(Long id, String nombre) {
    public CategoriaResponse(Categoria categoria) {
        this(categoria.getId(),categoria.getNombre());
    }
}