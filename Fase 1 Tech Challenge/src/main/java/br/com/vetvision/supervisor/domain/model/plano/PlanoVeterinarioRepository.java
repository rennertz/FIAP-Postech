package br.com.vetvision.supervisor.domain.model.plano;

import java.util.Optional;

public interface PlanoVeterinarioRepository {
    Optional<PlanoVeterinario> planoExiste(String cnpj);
    PlanoVeterinario criarPlano(PlanoVeterinario plano);
}
