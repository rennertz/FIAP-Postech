package br.com.vetvision.supervisor.application.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExcecaoDeSistema extends RuntimeException {
    private final MensagemErro mensagemErro;

    public ExcecaoDeSistema(HttpStatus httpStatus, String menssagem) {
        super(menssagem);
        this.mensagemErro = new MensagemErro(httpStatus.value(), httpStatus.getReasonPhrase(), menssagem);
    }
}
