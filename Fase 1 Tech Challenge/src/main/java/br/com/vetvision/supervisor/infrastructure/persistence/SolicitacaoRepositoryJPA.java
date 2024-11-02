package br.com.vetvision.supervisor.infrastructure.persistence;

import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;
import br.com.vetvision.supervisor.domain.model.solicitacao.SolicitacaoRepository;
import org.springframework.data.repository.CrudRepository;

public interface SolicitacaoRepositoryJPA extends CrudRepository<Solicitacao, Long>, SolicitacaoRepository {

    @Override
    default Solicitacao criaSolicitacao(Solicitacao solicitacao) {
        return save(solicitacao);
    }
}
