package br.com.booknrest.booknrest.infra.rest;

import org.springframework.http.HttpStatus;

import java.time.Instant;

record MensagemDeErro(String timestamp, float status, String error, String message) {
    MensagemDeErro(HttpStatus status, RuntimeException ex) {
        this(Instant.now().toString(), status.value(), status.getReasonPhrase(), ex.getMessage());
    }
    MensagemDeErro(HttpStatus status, String mensagem) {
        this(Instant.now().toString(), status.value(), status.getReasonPhrase(), mensagem);
    }
}
