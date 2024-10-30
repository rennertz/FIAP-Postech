package br.com.vetvision.supervisor.domain.model.plano;

import java.util.ArrayList;
import java.util.List;

public class PlanoVeterinario {
    private String cnpj;
    private String nome;
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
