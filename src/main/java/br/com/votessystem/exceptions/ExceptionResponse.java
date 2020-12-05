package br.com.votessystem.exceptions;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class ExceptionResponse implements Serializable {

    private static final long serialVersionUID = 1L;

	private String message;

    public ExceptionResponse(String message) {
        this.message = message;
    }
}

