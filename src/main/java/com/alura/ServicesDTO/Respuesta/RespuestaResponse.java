package com.alura.ServicesDTO.Respuesta;

import com.alura.modelo.Respuesta;

import java.util.Date;

public record RespuestaResponse(Long id,
                                Long idUsuario,
                                Long idTopico,
                                String mensaje,
                                Date fechaDeCreacion,
                                Boolean mejorRespuesta) {
    public RespuestaResponse(Respuesta respuesta) {
        this(respuesta.getId(),
                respuesta.getUsuario().getId(),
                respuesta.getTopico().getId(),
                respuesta.getMensaje(),
                respuesta.getFechaDeCreacion(),
                respuesta.getMejorRespuesta());
    }
}