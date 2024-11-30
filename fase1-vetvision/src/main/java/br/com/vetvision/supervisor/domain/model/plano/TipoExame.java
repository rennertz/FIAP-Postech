package br.com.vetvision.supervisor.domain.model.plano;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@ToString
@Entity
@Table(name = "tipo_exame")
public class TipoExame {

    @Id
    @Getter
    private String nome;

    @Column
    @Getter
    private BigDecimal valor;

    @Column
    @Getter
    private double comissao;

    @ManyToMany(mappedBy = "examesCobertos")
    private List<PlanoVeterinario> planosQueCobrem;

    public TipoExame() {
    }

    public TipoExame(String nome, BigDecimal valor, double comissao) {
        this.nome = nome;
        this.valor = valor;
        this.comissao = comissao;
    }

    void sanitizaNomeExame() {
        this.nome = this.nome.toUpperCase();
    }
}
