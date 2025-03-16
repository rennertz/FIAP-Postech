package br.com.booknrest.booknrest.infra.persistence.restaurante;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.unmodifiableList;

@Entity
@Table(name = "restaurante")
public class RestauranteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome;
    private String localizacao;
    private String tipoCozinha;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<HorarioDeFuncionamentoEntity> horariosDeFuncionamento = new ArrayList<>();

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
        this.horariosDeFuncionamento.add(horario);
    }

    public List<HorarioDeFuncionamentoEntity> getHorariosDeFuncionamento() {
        return unmodifiableList(this.horariosDeFuncionamento);
    }

    public Long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getLocalizacao() {
        return this.localizacao;
    }

    public String getTipoCozinha() {
        return this.tipoCozinha;
    }

    public int getCapacidade() {
        return this.capacidade;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RestauranteEntity that = (RestauranteEntity) o;
        return capacidade == that.capacidade &&
                Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(localizacao, that.localizacao) &&
                Objects.equals(tipoCozinha, that.tipoCozinha) &&
                Objects.equals(horariosDeFuncionamento, that.horariosDeFuncionamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, localizacao, tipoCozinha, horariosDeFuncionamento, capacidade);
    }

    @Override
    public String toString() {
        return "RestauranteEntity(id=" + this.getId() + ", nome=" + this.getNome() + ", localizacao=" + this.getLocalizacao() + ", tipoCozinha=" + this.getTipoCozinha() + ", horarioDeFuncionamento=" + this.getHorariosDeFuncionamento() + ", capacidade=" + this.getCapacidade() + ")";
    }
}
