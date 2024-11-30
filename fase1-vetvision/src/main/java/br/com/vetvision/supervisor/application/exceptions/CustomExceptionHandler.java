package br.com.vetvision.supervisor.application.exceptions;

import lombok.extern.java.Log;
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
@Log
@Order(50)
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ExcecaoDeSistema.class })
    protected ResponseEntity<Object> handleExcessaoDeSistema(ExcecaoDeSistema ex, WebRequest request) {
        ex.printStackTrace();
        MensagemErro mensagemErro = ex.getMensagemErro();

        return handleExceptionInternal(ex, mensagemErro,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        ex.printStackTrace();
        MensagemErro mensagemErro = new MensagemErro(
                500, "Erro interno do sistema",
                "Houve um erro no processamento, contate o suporte");

        return handleExceptionInternal(ex, mensagemErro,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}

