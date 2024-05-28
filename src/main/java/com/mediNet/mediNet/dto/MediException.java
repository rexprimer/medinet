package com.mediNet.mediNet.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class MediException {

    private String message;
    private Throwable throwable;
    private HttpStatus httpStatus;

    public MediException(String message, Throwable throwable, HttpStatus httpStatus) {
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
    }

    //Getters used by lombork
}
