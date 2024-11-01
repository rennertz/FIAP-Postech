package br.com.vetvision.supervisor.application;

import br.com.vetvision.supervisor.domain.model.exame.Exame;
import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;

public interface ProvimentoService {

    Exame encaminharLaudo(Exame exame);

    OfertaAtendimento ofertarAtendimento(OfertaAtendimento ofertaAtendimento);

    Exame realizarExame(Exame exame);

}
