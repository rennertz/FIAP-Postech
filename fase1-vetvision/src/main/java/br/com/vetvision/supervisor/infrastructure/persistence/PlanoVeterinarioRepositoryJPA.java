package br.com.vetvision.supervisor.infrastructure.persistence;

import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinario;
import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinarioRepository;
import br.com.vetvision.supervisor.domain.model.plano.TipoExame;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface PlanoVeterinarioRepositoryJPA extends CrudRepository<PlanoVeterinario, String>, PlanoVeterinarioRepository {
    @Override
    default Optional<PlanoVeterinario> planoExiste(String cnpj){return findById(cnpj);}

    @Override
    default PlanoVeterinario criarPlano(PlanoVeterinario plano){return save(plano);}

    @Override
    default List<PlanoVeterinario> listaPlanos(){
        return StreamSupport.stream(findAll().spliterator(), false).toList();
    }

    @Override
    default Set<TipoExame> listaTipoExames() {
        return StreamSupport.stream(findAll().spliterator(), false)
                .flatMap(plano -> plano.getExamesCobertos().stream())
                .collect(Collectors.toSet());
    }
}
