package br.com.vetvision.supervisor.domain.model.plano;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Entity
@Table(name = "plano")
public class PlanoVeterinario {

    @Id
    private String cnpj;

    @Column
    private String nome;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "plano_exame",
            joinColumns = @JoinColumn(name = "cnpj"),
            inverseJoinColumns = @JoinColumn(name = "nome"))
    private List<TipoExame> examesCobertos = new ArrayList<>();

    public PlanoVeterinario() {
    }

    public PlanoVeterinario(String cnpj, String nome) {
        this.cnpj = cnpj;
        this.nome = nome;
    }

    public void adicionaExameCoberto(TipoExame exame) {
        exame.sanitizaNomeExame();
        examesCobertos.add(exame);
    }

    public void removeExameCoberto(TipoExame exame) {
        examesCobertos.remove(exame);
    }

}
