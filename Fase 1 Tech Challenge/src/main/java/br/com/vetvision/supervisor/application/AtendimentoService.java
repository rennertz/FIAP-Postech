package br.com.vetvision.supervisor.application;

import br.com.vetvision.supervisor.domain.model.exame.Exame;
import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;
import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;

public interface AtendimentoService {

    Solicitacao solicitarExame(Solicitacao solicitacao);

    OfertaAtendimento ofertarAtendimento(OfertaAtendimento ofertaAtendimento);
    Exame aceitarOferta(OfertaAtendimento ofertaAtendimento);

    Exame realizarExame(Exame exame);
    Exame encaminharLaudo(Exame exame);
}
