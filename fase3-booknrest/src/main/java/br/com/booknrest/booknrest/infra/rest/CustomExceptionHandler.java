package br.com.booknrest.booknrest.infra.rest;

import br.com.booknrest.booknrest.exceptions.ErroDeValidacao;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Captura com precedência sobre captura genérica. Trata erros específicos
 */
@Order(50)
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ErroDeValidacao.class })
    protected ResponseEntity<Object> handleErroDeValidacao(ErroDeValidacao ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        return handleExceptionInternal(ex, new MensagemDeErro(status, ex),
                new HttpHeaders(), status, request);
    }
}
