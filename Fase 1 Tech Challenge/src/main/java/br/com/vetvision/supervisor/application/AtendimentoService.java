package br.com.vetvision.supervisor.application;

import br.com.vetvision.supervisor.domain.model.exame.Exame;
import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;
import br.com.vetvision.supervisor.domain.model.plano.TipoExame;
import br.com.vetvision.supervisor.domain.model.solicitacao.Clinica;
import br.com.vetvision.supervisor.domain.model.solicitacao.Pet;
import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;

import java.time.LocalDateTime;
import java.util.List;

public interface AtendimentoService {

    SolicitacaoDTO solicitarExame(NovaSolicitacaoDTO solicitacao);

    Solicitacao consultarSolicitacao(Integer solicitacaoId);
    List<Solicitacao> consultarSolicitacoes(String cnpjClinica);

    Exame aceitarOferta(OfertaAtendimento ofertaAtendimento);

    record NovaSolicitacaoDTO(Clinica clinica, Pet pet, String tipoExame, String planoCnpj) {}
    record SolicitacaoDTO(Clinica clinica, Pet pet, TipoExame exameSolicitado, PlanoResumidoDTO planoSelecionado,
                          Boolean estaAtiva, LocalDateTime momentoCriacao, OfertaAtendimento ofertaAtual) {}
    record PlanoResumidoDTO(String cnpj, String nome) {}

}
