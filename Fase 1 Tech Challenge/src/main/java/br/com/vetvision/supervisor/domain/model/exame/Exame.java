package br.com.vetvision.supervisor.domain.model.exame;

import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;
import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;

import java.time.LocalDateTime;

public class Exame {
    private OfertaAtendimento agendamentoConfirmado;

    private String codigoConfirmacao;
    private LocalDateTime momentoAtendimento;

    private Laudo laudo;

}
