package br.com.vetvision.supervisor.infrastructure.persistence;

import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;
import br.com.vetvision.supervisor.domain.model.solicitacao.SolicitacaoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SolicitacaoRepositoryJPA extends CrudRepository<Solicitacao, Integer>, SolicitacaoRepository {

    @Override
    default Solicitacao cria(Solicitacao solicitacao) {
        return save(solicitacao);
    }

    @Override
    default Optional<Solicitacao> consultaPorId(Integer solicitacaoId) {
        return findById(solicitacaoId);
    }

    @Override
    default List<Solicitacao> consultaPorClinica(String clinicaCnpj) {
        return findByClinicaCnpj(clinicaCnpj);
    }

    List<Solicitacao> findByClinicaCnpj(String clinicaCnpj);
}
