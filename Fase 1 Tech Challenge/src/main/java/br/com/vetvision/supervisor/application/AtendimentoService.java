package br.com.vetvision.supervisor.application;

import br.com.vetvision.supervisor.domain.model.exame.Exame;
import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;
import br.com.vetvision.supervisor.domain.model.solicitacao.Clinica;
import br.com.vetvision.supervisor.domain.model.solicitacao.Pet;
import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;

import java.util.List;

public interface AtendimentoService {

    Solicitacao solicitarExame(SolicitacaoDTO solicitacao);

    Solicitacao consultarSolicitacao(Integer solicitacaoId);
    List<Solicitacao> consultarSolicitacoes(String cnpjClinica);

    Exame aceitarOferta(OfertaAtendimento ofertaAtendimento);

    record SolicitacaoDTO(Clinica clinica, Pet pet, String tipoExame, String cnpjPlano) {}

}
