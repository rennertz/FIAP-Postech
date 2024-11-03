package br.com.vetvision.supervisor.application.impl;

import br.com.vetvision.supervisor.application.AtendimentoService.SolicitacaoDTO;
import br.com.vetvision.supervisor.application.AtendimentoService.PlanoResumidoDTO;
import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinario;
import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;
import org.mapstruct.Mapper;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, injectionStrategy = CONSTRUCTOR)
public interface SolicitacaoMapper {

    SolicitacaoDTO toSolicitacaoDto(Solicitacao solicitacao, PlanoResumidoDTO planoSelecionado);
    PlanoResumidoDTO toPlanoResumido(PlanoVeterinario plano);
}
