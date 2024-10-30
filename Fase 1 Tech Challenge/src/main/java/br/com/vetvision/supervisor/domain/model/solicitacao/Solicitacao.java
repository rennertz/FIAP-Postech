package br.com.vetvision.supervisor.domain.model.solicitacao;

import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinario;
import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;

import java.time.LocalDateTime;

public class Solicitacao {
    private Integer id;
    private Clinica clinica;
    private Pet pet;
    private PlanoVeterinario plano;
    private LocalDateTime momentoCriacao;

    private OfertaAtendimento ofertaAtual;

    public Solicitacao(Clinica clinica, Pet pet, PlanoVeterinario plano) {
        this.clinica = clinica;
        this.pet = pet;
        this.plano = plano;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getMomentoCriacao() {
        return momentoCriacao;
    }

    public OfertaAtendimento getOfertaAtual() {
        return ofertaAtual;
    }
}
