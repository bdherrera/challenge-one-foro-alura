package com.alura.exceptions;

public class NoExisteException extends Exception{
    public NoExisteException() {
        super();
    }

    public NoExisteException(String nombreEntidad) {
        super("Error el valor ingresado en el campo " + nombreEntidad + " no existe");
    }
}