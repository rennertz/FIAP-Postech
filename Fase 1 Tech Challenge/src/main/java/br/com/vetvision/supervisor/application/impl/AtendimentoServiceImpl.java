package br.com.vetvision.supervisor.application.impl;

import br.com.vetvision.supervisor.application.AtendimentoService;
import br.com.vetvision.supervisor.domain.model.exame.Exame;
import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;
import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;

public class AtendimentoServiceImpl implements AtendimentoService {
    @Override
    public Solicitacao solicitarExame(Solicitacao solicitacao) {
        return null;
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
