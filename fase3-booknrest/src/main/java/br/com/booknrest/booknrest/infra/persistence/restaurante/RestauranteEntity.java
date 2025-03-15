package br.com.booknrest.booknrest.infra.persistence.restaurante;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

@Data
@Entity
public class RestauranteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String localizacao;
    private String tipoCozinha;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioDeFuncionamentoEntity> horarioDeFuncionamento = new ArrayList<>();

    private int capacidade;

    public RestauranteEntity() {
    }

    public RestauranteEntity(Long id, String nome, String localizacao, String tipoCozinha, int capacidade) {
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.tipoCozinha = tipoCozinha;
        this.capacidade = capacidade;
    }

    public void adicionaHorarioDeFuncionamento(HorarioDeFuncionamentoEntity horario) {
        horario.setRestaurante(this); // garante que o link bidirecional está configurado
        this.horarioDeFuncionamento.add(horario);
    }

    public List<HorarioDeFuncionamentoEntity> getHorarioDeFuncionamento() {
        return unmodifiableList(this.horarioDeFuncionamento);
    }
}
