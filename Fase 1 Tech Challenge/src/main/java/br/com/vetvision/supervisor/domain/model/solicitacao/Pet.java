package br.com.vetvision.supervisor.domain.model.solicitacao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "MesmoNomeECpfResponsavel", columnNames = { "nome", "cpfResponsavel" }) })
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @NotBlank
    String nome;
    @NotBlank
    String especie;
    @NotNull @Past
    LocalDate dataNascimento;
    @NotBlank
    String nomeResponsavel;

    @CPF
    @NotBlank
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
