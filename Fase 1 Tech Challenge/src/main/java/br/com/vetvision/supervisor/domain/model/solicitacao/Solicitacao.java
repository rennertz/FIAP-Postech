package br.com.vetvision.supervisor.domain.model.solicitacao;

import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinario;
import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;

import java.time.LocalDateTime;

public class Solicitacao {
    private int id;
    private Clinica clinica;
    private Pet pet;
    private PlanoVeterinario plano;
    private LocalDateTime momentoCriacao;

    private OfertaAtendimento ofertaAtual;
}
