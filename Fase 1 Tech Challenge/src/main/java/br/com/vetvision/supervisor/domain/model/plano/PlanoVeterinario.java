package br.com.vetvision.supervisor.domain.model.plano;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Entity
@Table(name = "plano")
public class PlanoVeterinario {

    @Id
    @CNPJ
    @NotBlank
    private String cnpj;

    @Column
    @NotBlank
    private String nome;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "plano_exame",
            joinColumns = @JoinColumn(name = "cnpj"),
            inverseJoinColumns = @JoinColumn(name = "nome"))
    @NotNull
    @Size(min = 1)
    private List<TipoExame> examesCobertos = new ArrayList<>();

    public PlanoVeterinario() {
    }

    public PlanoVeterinario(String cnpj, String nome) {
        this.cnpj = cnpj;
        this.nome = nome;
    }

    public void adicionaExameCoberto(@Valid TipoExame exame) {
        exame.sanitizaNomeExame();
        examesCobertos.add(exame);
    }

    public void removeExameCoberto(TipoExame exame) {
        examesCobertos.remove(exame);
    }

}
