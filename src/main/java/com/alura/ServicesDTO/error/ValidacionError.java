package com.alura.ServicesDTO.error;

import org.springframework.validation.FieldError;

public  record ValidacionError(String campo, String error){
    public ValidacionError(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}