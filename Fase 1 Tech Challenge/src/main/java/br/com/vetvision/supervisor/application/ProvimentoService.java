package br.com.vetvision.supervisor.application;

import br.com.vetvision.supervisor.domain.model.exame.Exame;
import br.com.vetvision.supervisor.domain.model.oferta.Consultor;
import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;

import java.time.Duration;

public interface ProvimentoService {

    OfertaConsultorDTO ofertarAtendimento(OfertaConsultorDTO ofertaConsultor);

    Exame realizarExame(String codigo);

    Exame encaminharLaudo(Exame exame);

    OfertaAtendimento buscarOfertaAtual(Integer solicitacaoId);

    OfertaAtendimento aceitarOfertaAtual(Integer solicitacaoId);

    //TODO tirar o "PT"
    record OfertaConsultorDTO(int solicitacao, Consultor consultor, Duration prazo, Boolean ofertaAtual){
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OfertaConsultorDTO that = (OfertaConsultorDTO) o;
            return prazo.equals(that.prazo) &&
                    consultor.getCpf().equals(that.consultor.getCpf());
        }
    };


}