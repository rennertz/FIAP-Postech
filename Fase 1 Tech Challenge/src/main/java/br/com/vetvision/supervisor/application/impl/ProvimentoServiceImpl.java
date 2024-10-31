package br.com.vetvision.supervisor.application.impl;

import br.com.vetvision.supervisor.application.ProvimentoService;
import br.com.vetvision.supervisor.domain.model.exame.Exame;
import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;
import org.springframework.stereotype.Service;

@Service
public class ProvimentoServiceImpl implements ProvimentoService {

    @Override
    public OfertaAtendimento ofertarAtendimento(OfertaAtendimento ofertaAtendimento) {
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
