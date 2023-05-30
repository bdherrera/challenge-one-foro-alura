package com.alura.exceptions;

public class RespuestaNoCorrespondeException extends Exception{
    public RespuestaNoCorrespondeException() {
        super();
    }

    public RespuestaNoCorrespondeException(Long idRespuesta, Long idTopico) {
        super("Error: el idRespuesta " + idRespuesta + " no corresponde al idTopico " + idTopico);
    }
}