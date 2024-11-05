package br.com.vetvision.supervisor.domain.model.plano;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PlanoVeterinarioRepository {
    Optional<PlanoVeterinario> planoExiste(String cnpj);
    PlanoVeterinario criarPlano(@Valid PlanoVeterinario plano);

    List<PlanoVeterinario> listaPlanos();
    Set<TipoExame> listaTipoExames();
}
