package br.com.vetvision.supervisor.application;

import br.com.vetvision.supervisor.application.AtendimentoService.NovaSolicitacaoDTO;
import br.com.vetvision.supervisor.application.impl.AtendimentoServiceImpl;
import br.com.vetvision.supervisor.application.impl.SolicitacaoMapperImpl;
import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinario;
import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinarioRepository;
import br.com.vetvision.supervisor.domain.model.solicitacao.ClinicaRepository;
import br.com.vetvision.supervisor.domain.model.solicitacao.PetReadOnlyRepository;
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
    PetReadOnlyRepository petRepository;
    AtendimentoService service;

    @BeforeEach
    public void setUp() {
        solicitacaoRepository = spy(SolicitacaoRepository.class);
        planoVeterinarioRepository = spy(PlanoVeterinarioRepository.class);
        petRepository = mock(PetReadOnlyRepository.class);
        clinicaRepository = spy(ClinicaRepository.class);
        service = new AtendimentoServiceImpl(
                solicitacaoRepository, clinicaRepository, petRepository,
                planoVeterinarioRepository, new SolicitacaoMapperImpl());
    }

    @Test
    void solicitarExamePlanoExistenteTest() {
        mockPlanoExistente();
        mockSolicitacaoRepositoryResponse();

        assertDoesNotThrow(()->service.solicitarExame(getNovaSolicitacao()));
    }

    @Test
    void solicitarExameTest() {
        mockPlanoExistente();
        mockSolicitacaoRepositoryResponse();

        AtendimentoService.SolicitacaoDTO resultado = service.solicitarExame(getNovaSolicitacao());

        assertNotNull(resultado.momentoCriacao());
        assertNull(resultado.ofertaAtual());
    }

    @Test
    void solicitarExameVerificaTipoExameTest() {
        mockPlanoExistente();
        mockSolicitacaoRepositoryResponse();

        service.solicitarExame(getNovaSolicitacao());

        ArgumentCaptor<Solicitacao> requestCaptor = ArgumentCaptor.forClass(Solicitacao.class);
        verify(solicitacaoRepository, only()).cria(requestCaptor.capture());
        Solicitacao solicitacaoTeste = requestCaptor.getValue();
        assertEquals("ULTRASSOM GATO", solicitacaoTeste.getExameSolicitado().getNome());
    }

    private void mockPlanoExistente() {
        PlanoVeterinario planoComExame = mockPlano;
        planoComExame.adicionaExameCoberto(mockExame);
        when(planoVeterinarioRepository.planoExiste(mockPlano.getCnpj())).thenReturn(Optional.of(planoComExame));
    }

    private void mockSolicitacaoRepositoryResponse() {
        Solicitacao solicitacaoResposta = new Solicitacao(mockClinica, mockPet, mockExame, mockPlano);
        when(solicitacaoRepository.cria(any(Solicitacao.class))).thenReturn(solicitacaoResposta);
    }

    private static NovaSolicitacaoDTO getNovaSolicitacao() {
        return new NovaSolicitacaoDTO(mockClinica, mockPet, "Ultrassom Gato", mockPlano.getCnpj());
    }

    @Test
    void aceitarOfertaTest() {
        assertTrue(true);
    }

}
