package br.com.vetvision.supervisor.infrastructure.persistence;

import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;
import br.com.vetvision.supervisor.domain.model.solicitacao.SolicitacaoRepository;
import org.springframework.data.repository.CrudRepository;

public interface SolicitacaooRepositoryJPA extends CrudRepository<Solicitacao, Long>, SolicitacaoRepository {

    default Solicitacao criaSolicitacao(Solicitacao solicitacao) {
        return save(solicitacao);
    }
}
