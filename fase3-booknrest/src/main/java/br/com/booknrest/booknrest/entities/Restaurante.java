package br.com.booknrest.booknrest.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

@Data
public class Restaurante {
    private Long id;
    private String nome;
    private String localizacao;
    private String tipoCozinha;

    @Setter(AccessLevel.NONE)
    private List<HorarioDeFuncionamento> horarioDeFuncionamento = new ArrayList<>();

    private int capacidade;

    public Restaurante(Long id, String nome, String localizacao, String tipoCozinha, int capacidade) {
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.tipoCozinha = tipoCozinha;
        this.capacidade = capacidade;
    }

    public void adicionaHorarioDeFuncionamento(HorarioDeFuncionamento horario) {
        horario.setRestaurante(this); // Ensure the bidirectional link is set
        this.horarioDeFuncionamento.add(horario);
    }

    public List<HorarioDeFuncionamento> getHorarioDeFuncionamento() {
        return unmodifiableList(this.horarioDeFuncionamento);
    }
}
