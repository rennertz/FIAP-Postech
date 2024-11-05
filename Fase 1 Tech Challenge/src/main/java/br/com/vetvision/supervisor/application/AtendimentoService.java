package br.com.vetvision.supervisor.application;

import br.com.vetvision.supervisor.domain.model.exame.Exame;
import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;
import br.com.vetvision.supervisor.domain.model.plano.TipoExame;
import br.com.vetvision.supervisor.domain.model.solicitacao.Clinica;
import br.com.vetvision.supervisor.domain.model.solicitacao.Pet;
import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;

import java.time.LocalDateTime;
import java.util.List;

public interface AtendimentoService {

    SolicitacaoDTO solicitarExame(NovaSolicitacaoDTO solicitacao);

    Solicitacao consultarSolicitacao(Integer solicitacaoId);
    List<Solicitacao> consultarSolicitacoes(String cnpjClinica);

    Exame aceitarOferta(OfertaAtendimento ofertaAtendimento);

    record NovaSolicitacaoDTO(
            @NotNull @Valid Clinica clinica,
            @NotNull @Valid Pet pet,
            @NotNull @NotBlank String tipoExame,
            @NotBlank @CNPJ String planoCnpj) {}
    record SolicitacaoDTO(Clinica clinica, Pet pet, TipoExame exameSolicitado, PlanoResumidoDTO planoSelecionado,
                          Boolean estaAtiva, LocalDateTime momentoCriacao, OfertaAtendimento ofertaAtual) {}
    record PlanoResumidoDTO(String cnpj, String nome) {}

}
