package br.com.booknrest.booknrest.infra.rest;

import br.com.booknrest.booknrest.exceptions.ErroDeValidacao;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Captura com precedência sobre captura genérica. Trata erros específicos
 */
@Order(50)
@Hidden
@ControllerAdvice(basePackages = {"br.com.booknrest.booknrest.infra.rest"})
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        logger.info("Invalid arguments found : " + ex.getMessage());
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() +": "+ fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        MensagemDeErro response = new MensagemDeErro(BAD_REQUEST, errors);
        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.info("Invalid arguments found : " + ex.getMessage());
        String errors = tryGetRootMessage(ex);

        MensagemDeErro response = new MensagemDeErro(BAD_REQUEST, errors);
        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    private static String tryGetRootMessage(HttpMessageNotReadableException ex) {
        try {
            return Objects.requireNonNull(ex.getRootCause())
                    .getMessage();
        } catch (NullPointerException e) {
            return "Erro de leitura do payload";
        }
    }

    @ExceptionHandler(value = { ErroDeValidacao.class })
    protected ResponseEntity<MensagemDeErro> handleErroDeValidacao(ErroDeValidacao ex, WebRequest request) {

        return new ResponseEntity<>(new MensagemDeErro(BAD_REQUEST, ex), BAD_REQUEST);
    }
}
