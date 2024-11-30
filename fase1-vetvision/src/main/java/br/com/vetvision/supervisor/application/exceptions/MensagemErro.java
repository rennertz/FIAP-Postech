package br.com.vetvision.supervisor.application.exceptions;

import java.io.Serializable;
import java.time.LocalDateTime;

record MensagemErro(
        LocalDateTime timestamp,
        Integer statusCode,
        String error,
        String message) implements Serializable
{
    public MensagemErro(Integer statusCode, String error, String message) {
        this(LocalDateTime.now(), statusCode, error, message);
    }
}
