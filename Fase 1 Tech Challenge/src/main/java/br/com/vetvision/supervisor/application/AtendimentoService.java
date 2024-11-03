package br.com.vetvision.supervisor.application;

import br.com.vetvision.supervisor.domain.model.exame.Exame;
import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;
import br.com.vetvision.supervisor.domain.model.solicitacao.Clinica;
import br.com.vetvision.supervisor.domain.model.solicitacao.Pet;
import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;

public interface AtendimentoService {

    Solicitacao solicitarExame(SolicitacaoDTO solicitacao);

    Exame aceitarOferta(OfertaAtendimento ofertaAtendimento);

    record SolicitacaoDTO(Clinica clinica, Pet pet, String tipoExame, String cnpjPlano) {}

}
