package br.com.vetvision.supervisor.application.impl;

import br.com.vetvision.supervisor.application.AtendimentoService;
import br.com.vetvision.supervisor.domain.model.exame.Exame;
import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;
import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;
import br.com.vetvision.supervisor.domain.model.solicitacao.SolicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtendimentoServiceImpl implements AtendimentoService {

    private final SolicitacaoRepository solicitacaoRepository;

    @Autowired
    public AtendimentoServiceImpl(SolicitacaoRepository solicitacaoRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
    }

    @Override
    public Solicitacao solicitarExame(Solicitacao solicitacao) {
        return solicitacaoRepository.criaSolicitacao(solicitacao);
    }

    @Override
    public OfertaAtendimento ofertarAtendimento(OfertaAtendimento ofertaAtendimento) {
        return null;
    }

    @Override
    public Exame aceitarOferta(OfertaAtendimento ofertaAtendimento) {
        return null;
    }

    @Override
    public Exame realizarExame(Exame exame) {
        return null;
    }

    @Override
    public Exame encaminharLaudo(Exame exame) {
        return null;
    }
}
