package br.com.vetvision.supervisor.domain.model.plano;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class PlanoVeterinario {

    @Id
    private String cnpj;

    @Column
    private String nome;

    @OneToMany
    @JoinColumn(name = "nome")
    private List<TipoExame> examesCobertos = new ArrayList<>();

    public PlanoVeterinario(String cnpj, String nome) {
        this.cnpj = cnpj;
        this.nome = nome;
    }

    public void adicionaExameCoberto(TipoExame exame) {
        examesCobertos.add(exame);
    }

    public void removeExameCoberto(TipoExame exame) {
        examesCobertos.remove(exame);
    }

}
