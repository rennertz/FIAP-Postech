package br.com.vetvision.supervisor.domain.model.oferta;

import java.time.Duration;

public class OfertaAtendimento {
    private int id;
    private Consultor consultor;
    private Duration prazoParaAtendimento;
    private boolean foiAceita;
}
