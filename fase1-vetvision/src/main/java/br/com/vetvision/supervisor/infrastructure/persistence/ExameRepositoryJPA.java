package br.com.vetvision.supervisor.infrastructure.persistence;

import br.com.vetvision.supervisor.domain.model.exame.Exame;
import br.com.vetvision.supervisor.domain.model.exame.ExameRepository;
import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ExameRepositoryJPA extends CrudRepository<Exame, String>, ExameRepository {
    @Override
    default Exame criarExame(Solicitacao solicitacao){
        var exame = new Exame();
        exame.setSolicitacao(solicitacao);
        return save(exame);
    };
    @Override
    default Optional<Exame> exameExiste(String codigo){
        return findById(codigo);
    };

    @Override
    default Exame realizarAtendimento(Exame exame){
        exame.atender();
        return save(exame);
    };
}
