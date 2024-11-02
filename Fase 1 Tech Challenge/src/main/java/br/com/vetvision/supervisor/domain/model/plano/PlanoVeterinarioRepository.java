package br.com.vetvision.supervisor.domain.model.plano;

public interface PlanoVeterinarioRepository {
    boolean planoExiste(String cnpj);
    PlanoVeterinario criarPlano(PlanoVeterinario plano);
}
