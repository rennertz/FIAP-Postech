package br.com.vetvision.supervisor.application;

import br.com.vetvision.supervisor.application.impl.AtendimentoServiceImpl;
import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;
import org.junit.jupiter.api.Test;


import static br.com.vetvision.supervisor.application.MockObjects.*;
import static org.junit.jupiter.api.Assertions.*;

class AtendimentoServiceTest {

    AtendimentoService atendimentoService = new AtendimentoServiceImpl();

    @Test
    void solicitarExameTest() {
        Solicitacao solicitacao = new Solicitacao(mockClinica, mockPet, mockPlano);

        Solicitacao resultado = atendimentoService.solicitarExame(solicitacao);
        
        assertTrue(resultado.getId() > 0);
        assertNotNull(resultado.getMomentoCriacao());
        assertNull(resultado.getOfertaAtual());
    }

    @Test
    void ofertarAtendimentoTest() {
        assertTrue(true);
    }

    @Test
    void aceitarOfertaTest() {
        assertTrue(true);
    }

    @Test
    void realizarExameTest() {
        assertTrue(true);
    }

    @Test
    void encaminharLaudoTest() {
        assertTrue(true);
    }
}
