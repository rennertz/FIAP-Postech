package br.com.booknrest.booknrest.infra.rest;

import br.com.booknrest.booknrest.exceptions.ErroDeValidacao;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.core.annotation.Order;
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
@Hidden
@ControllerAdvice(basePackages = {"br.com.booknrest.booknrest.infra.rest"})
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ErroDeValidacao.class })
    protected ResponseEntity<MensagemDeErro> handleErroDeValidacao(ErroDeValidacao ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new MensagemDeErro(status, ex), status);
    }
}
