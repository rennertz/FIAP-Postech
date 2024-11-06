package br.com.vetvision.supervisor.domain.model.exame;

import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;

import java.util.Optional;

public interface ExameRepository {
    Exame criarExame(Solicitacao solicitacao);
    Optional<Exame> exameExiste(String codigo);
    Exame realizarAtendimento(Exame exame);
}
