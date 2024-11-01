package br.com.vetvision.supervisor.domain.model.plano;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TipoExame {
    @Id
    private String nome;

    @Column
    private String valor;

    @Column
    private double comissao;
}
