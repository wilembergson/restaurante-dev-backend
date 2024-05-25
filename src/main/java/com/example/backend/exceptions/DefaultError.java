package com.example.backend.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class DefaultError extends RuntimeException{
    private HttpStatus statusCode;

    public DefaultError(String mensagem, HttpStatus statusCode){
        super(mensagem);
        this.statusCode = statusCode;
    }

    public Object messageError(){
        return Map.of("mensagem", getMessage());
    }

    public HttpStatus getStatusCode(){
        return this.statusCode;
    }
}
