package br.com.booknrest.booknrest.infra.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Captura exceção mais genérica, caso outras capturas não sejam ativadas
 */
@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<Object> handleConflictGeneric(RuntimeException ex, WebRequest request) {
        ex.printStackTrace();
        MensagemDeErro mensagemErro = new MensagemDeErro(HttpStatus.INTERNAL_SERVER_ERROR,"Não foi possível completar a requisição");

        return handleExceptionInternal(ex, mensagemErro,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
