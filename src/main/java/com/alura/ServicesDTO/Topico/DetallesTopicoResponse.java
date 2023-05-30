package com.alura.ServicesDTO.Topico;
import com.alura.modelo.Estatus;
import com.alura.modelo.Tag;
import com.alura.modelo.Topico;

import java.util.Date;
public record DetallesTopicoResponse(Long id,
                                     Long idUsuario,
                                     String titulo,
                                     String mensaje,
                                     Date fechaDeCreacion,
                                     Estatus estatus,
                                     Long idCurso,
                                     Tag tag,
                                     Integer cantidadRespuestas) {

    public DetallesTopicoResponse(Topico topico) {
        this(topico.getId(),
                topico.getUsuario().getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaDeCreacion(),
                topico.getEstatus(),
                topico.getCurso().getId(),
                topico.getTag(),
                null);
    }
}
