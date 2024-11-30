package br.com.vetvision.supervisor.domain.model.solicitacao;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "MesmoNomeECpfResponsavel", columnNames = { "nome", "cpfResponsavel" }) })
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String nome;
    String especie;
    LocalDate dataNascimento;
    String nomeResponsavel;
    String cpfResponsavel;

    public Pet() {
    }

    public Pet(String nome, String especie, LocalDate dataNascimento, String nomeResponsavel, String cpfResponsavel) {
        this.nome = nome;
        this.especie = especie;
        this.dataNascimento = dataNascimento;
        this.nomeResponsavel = nomeResponsavel;
        this.cpfResponsavel = cpfResponsavel;
    }

}
