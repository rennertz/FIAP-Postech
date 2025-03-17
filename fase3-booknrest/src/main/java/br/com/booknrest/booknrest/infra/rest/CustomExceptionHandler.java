package br.com.booknrest.booknrest.infra.rest;

import br.com.booknrest.booknrest.exceptions.ErroDeValidacao;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

/**
 * Captura com precedência sobre captura genérica. Trata erros específicos
 */
@Order(50)
@Hidden
@ControllerAdvice(basePackages = {"br.com.booknrest.booknrest.infra.rest"})
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        logger.info("Invalid arguments found : " + ex.getMessage());
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() +": "+ fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        MensagemDeErro response = new MensagemDeErro(httpStatus, errors);
        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(value = { ErroDeValidacao.class })
    protected ResponseEntity<MensagemDeErro> handleErroDeValidacao(ErroDeValidacao ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new MensagemDeErro(status, ex), status);
    }
}
