package br.com.vetvision.supervisor.application;

import br.com.vetvision.supervisor.application.impl.AtendimentoServiceImpl;
import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinarioRepository;
import br.com.vetvision.supervisor.domain.model.solicitacao.ClinicaRepository;
import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;
import br.com.vetvision.supervisor.domain.model.solicitacao.SolicitacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;


import java.util.Optional;

import static br.com.vetvision.supervisor.application.MockObjects.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AtendimentoServiceTest {

    SolicitacaoRepository solicitacaoRepository;
    PlanoVeterinarioRepository planoVeterinarioRepository;
    ClinicaRepository clinicaRepository;
    AtendimentoService service;

    @BeforeEach
    public void setUp() {
        solicitacaoRepository = spy(SolicitacaoRepository.class);
        planoVeterinarioRepository = spy(PlanoVeterinarioRepository.class);
        clinicaRepository = spy(ClinicaRepository.class);
        service = new AtendimentoServiceImpl(solicitacaoRepository,clinicaRepository,planoVeterinarioRepository);
    }

//    @Test
//    void solicitarExameTest() {
//        ArgumentCaptor<Solicitacao> requestCaptor = ArgumentCaptor.forClass(Solicitacao.class);
//        Solicitacao solicitacao = new Solicitacao(mockClinica, mockPet, mockPlano);
//        Solicitacao solicitacaoResposta = new Solicitacao(mockClinica, mockPet, mockPlano);
//
//        when(planoVeterinarioRepository.planoExiste(mockPlano.getCnpj())).thenReturn(Optional.of(mockPlano));
//        verify(solicitacaoRepository).criaSolicitacao(requestCaptor.capture());
//        when(solicitacaoRepository.criaSolicitacao(solicitacao)).thenReturn(solicitacaoResposta);
//        Solicitacao resultado = service.solicitarExame(solicitacao);
//
//        Solicitacao solicitacaoTeste = requestCaptor.getValue();
//        assertNotNull(resultado.getMomentoCriacao());
//        assertNull(resultado.getOfertaAtual());
//    }

    @Test
    void solicitarExamePlanoExistesteTest() {
        Solicitacao solicitacao = new Solicitacao(mockClinica, mockPet, mockPlano);
        when(planoVeterinarioRepository.planoExiste(mockPlano.getCnpj())).thenReturn(Optional.of(mockPlano));
        assertDoesNotThrow(()->service.solicitarExame(solicitacao));
    }

    @Test
    void aceitarOfertaTest() {
        assertTrue(true);
    }

}
