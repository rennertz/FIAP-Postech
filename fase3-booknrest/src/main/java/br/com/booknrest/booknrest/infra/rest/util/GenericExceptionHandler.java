package br.com.booknrest.booknrest.infra.rest.util;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Captura exceção mais genérica, caso outras capturas não sejam ativadas
 */
@Hidden
@ControllerAdvice(basePackages = {"br.com.booknrest.booknrest.infra.rest"})
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<MensagemDeErro> handleConflictGeneric(RuntimeException ex, WebRequest request) {
        ex.printStackTrace();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        MensagemDeErro mensagemErro = new MensagemDeErro(status,"Não foi possível completar a requisição");

        return new ResponseEntity<>(mensagemErro, status);
    }
}
