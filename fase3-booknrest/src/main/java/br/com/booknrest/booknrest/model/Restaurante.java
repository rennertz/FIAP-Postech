package br.com.booknrest.booknrest.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

@Data
@Entity
public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String localizacao;
    private String tipoCozinha;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioDeFuncionamento> horarioDeFuncionamento = new ArrayList<>();

    private int capacidade;


    public void adicionaHorarioDeFuncionamento(HorarioDeFuncionamento horario) {
        horario.setRestaurante(this); // Ensure the bidirectional link is set
        this.horarioDeFuncionamento.add(horario);
    }

    public void removeHorarioDeFuncionamento(HorarioDeFuncionamento horario) {
        horario.setRestaurante(null);
        this.horarioDeFuncionamento.remove(horario);
    }

    public List<HorarioDeFuncionamento> getHorarioDeFuncionamento(HorarioDeFuncionamento horario) {
        return unmodifiableList(this.horarioDeFuncionamento);
    }
}
