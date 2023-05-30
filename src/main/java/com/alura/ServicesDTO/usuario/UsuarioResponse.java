package com.alura.ServicesDTO.usuario;

import com.alura.modelo.Usuario;

public record UsuarioResponse(Long id, String email) {
    public UsuarioResponse(Usuario usuario) {
        this(usuario.getId(), usuario.getEmail());
    }
}