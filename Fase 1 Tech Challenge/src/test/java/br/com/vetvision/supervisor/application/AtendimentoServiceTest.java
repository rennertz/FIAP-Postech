package br.com.vetvision.supervisor.application;

import br.com.vetvision.supervisor.application.impl.AtendimentoServiceImpl;
import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;
import br.com.vetvision.supervisor.domain.model.solicitacao.SolicitacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static br.com.vetvision.supervisor.application.MockObjects.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AtendimentoServiceTest {

    SolicitacaoRepository solicitacaoRepository;
    AtendimentoService service;

    @BeforeEach
    public void setUp() {
        solicitacaoRepository = mock(SolicitacaoRepository.class);
        service = new AtendimentoServiceImpl(solicitacaoRepository);
    }

    @Test
    void solicitarExameTest() {
        Solicitacao solicitacao = new Solicitacao(mockClinica, mockPet, mockPlano);
        Solicitacao solicitacaoResposta = new Solicitacao(mockClinica, mockPet, mockPlano);

        when(solicitacaoRepository.criaSolicitacao(solicitacao)).thenReturn(solicitacaoResposta);
        Solicitacao resultado = service.solicitarExame(solicitacao);
        
        assertTrue(resultado.getId() > 0);
        assertNotNull(resultado.getMomentoCriacao());
        assertNull(resultado.getOfertaAtual());
    }

    @Test
    void aceitarOfertaTest() {
        assertTrue(true);
    }

}
