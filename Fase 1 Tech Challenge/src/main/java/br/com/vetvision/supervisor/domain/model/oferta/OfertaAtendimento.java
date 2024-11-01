package br.com.vetvision.supervisor.domain.model.oferta;

import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;

import java.time.Duration;

public class OfertaAtendimento {
    private int id;
    private Solicitacao solicitacao;
    private Consultor consultor;
    private Duration prazoParaAtendimento;
    private boolean foiAceita;
}
