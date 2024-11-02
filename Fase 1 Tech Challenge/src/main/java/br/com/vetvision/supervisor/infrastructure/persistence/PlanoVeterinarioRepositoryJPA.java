package br.com.vetvision.supervisor.infrastructure.persistence;

import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinario;
import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinarioRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlanoVeterinarioRepositoryJPA extends CrudRepository<PlanoVeterinario, String>, PlanoVeterinarioRepository {
    default Optional<PlanoVeterinario> planoExiste(String cnpj){return findById(cnpj);};
    default PlanoVeterinario criarPlano(PlanoVeterinario plano){return save(plano);};
}
