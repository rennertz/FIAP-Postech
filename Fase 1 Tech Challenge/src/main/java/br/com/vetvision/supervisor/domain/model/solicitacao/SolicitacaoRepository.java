package br.com.vetvision.supervisor.domain.model.solicitacao;

import java.util.List;
import java.util.Optional;

public interface SolicitacaoRepository {

    Solicitacao cria(Solicitacao solicitacao);
    Optional<Solicitacao> consultaPorId(Integer solicitacaoId);
    List<Solicitacao> consultaPorClinica(String cnpjClinica);

}
