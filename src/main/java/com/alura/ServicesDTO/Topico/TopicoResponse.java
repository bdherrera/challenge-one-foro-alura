package com.alura.ServicesDTO.Topico;

import com.alura.modelo.Estatus;
import com.alura.modelo.Tag;
import com.alura.modelo.Topico;

import java.util.Date;

public record TopicoResponse(Long id,
                             Long idUsuario,
                             Long idCurso,
                             String titulo,
                             String mensaje,
                             Date fechaDeCreacion,
                             Estatus estatus,
                             Tag tag) {

    public TopicoResponse(Topico topico) {
        this(topico.getId(),
                topico.getUsuario().getId(),
                topico.getCurso().getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaDeCreacion(),
                topico.getEstatus(),
                topico.getTag());
    }
}
