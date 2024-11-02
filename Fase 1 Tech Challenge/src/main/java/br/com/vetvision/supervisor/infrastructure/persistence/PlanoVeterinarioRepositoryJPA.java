package br.com.vetvision.supervisor.infrastructure.persistence;

import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinario;
import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinarioRepository;
import org.springframework.data.repository.CrudRepository;

public interface PlanoVeterinarioRepositoryJPA extends CrudRepository<PlanoVeterinario, String>, PlanoVeterinarioRepository {
    default boolean planoExiste(String cnpj){return findById(cnpj).isPresent();};
    default PlanoVeterinario criarPlano(PlanoVeterinario plano){return save(plano);};
}
