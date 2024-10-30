package br.com.vetvision.supervisor.domain.model.solicitacao;

import java.time.LocalDate;

public class Pet {
    String nome;
    String especie;
    LocalDate dataNascimento;
    String nomeResponsavel;
    String cpfResponsavel;

    public Pet(String nome, String especie, LocalDate dataNascimento, String nomeResponsavel, String cpfResponsavel) {
        this.nome = nome;
        this.especie = especie;
        this.dataNascimento = dataNascimento;
        this.nomeResponsavel = nomeResponsavel;
        this.cpfResponsavel = cpfResponsavel;
    }

}
