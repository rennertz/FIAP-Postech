package br.com.vetvision.supervisor.domain.model.exame;

import br.com.vetvision.supervisor.domain.model.cadastro.Clinica;
import br.com.vetvision.supervisor.domain.model.cadastro.PlanoVet;

import java.time.LocalDateTime;

public class Exame {
    private int id;
    private Clinica clinica;
    private Pet pet;
    private PlanoVet plano;

    private Oferta ofertaAtual;
    private String codigoConfirmacao;
    private String conteudoLaudo;

    private LocalDateTime momentoCriacao;
    private LocalDateTime momentoExaminado;
}
