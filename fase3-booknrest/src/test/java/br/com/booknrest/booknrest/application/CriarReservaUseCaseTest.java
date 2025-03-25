package br.com.booknrest.booknrest.application;

import br.com.booknrest.booknrest.entities.Cliente;
import br.com.booknrest.booknrest.entities.Reserva;
import br.com.booknrest.booknrest.entities.Restaurante;
import br.com.booknrest.booknrest.exceptions.ErroDeValidacao;
import br.com.booknrest.booknrest.gateway.ClienteGateway;
import br.com.booknrest.booknrest.gateway.ReservaGateway;
import br.com.booknrest.booknrest.gateway.RestauranteGateway;
import br.com.booknrest.booknrest.util.RestauranteHelperFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static br.com.booknrest.booknrest.util.ReservaHelperFactory.getProximoDiaAoAbrir;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CriarReservaUseCaseTest {
    public static final Restaurante RESTAURANTE = RestauranteHelperFactory.getRestauranteNomeAleatorio();
    public static final String TELEFONE = "10 99999-9999";
    public static final Cliente CLIENTE = new Cliente(1L, "Abel", TELEFONE);


    @Mock private ReservaGateway reservaGateway;
    @Mock private RestauranteGateway restauranteGateway;
    @Mock private ClienteGateway clienteGateway;
    AutoCloseable openMocks;

    CriarReservaUseCase criarReservaUseCase;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        criarReservaUseCase = new CriarReservaUseCase(reservaGateway, restauranteGateway, clienteGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void criarReservaValida() {
        when(restauranteGateway.obtemPeloId(1L)).thenReturn(Optional.of(RESTAURANTE));
        when(clienteGateway.cadastra(CLIENTE)).thenReturn(CLIENTE);

        LocalDateTime proximoDiaAoAbrir = getProximoDiaAoAbrir(RESTAURANTE);
        when(reservaGateway.salva(any(Reserva.class))).thenReturn(new Reserva(1L, RESTAURANTE, CLIENTE, proximoDiaAoAbrir, 6, Reserva.StatusReserva.PENDENTE));
        List<Reserva> reservas = List.of(
                new Reserva(1L, RESTAURANTE, new Cliente(2L, "Beto", TELEFONE), proximoDiaAoAbrir, 100, Reserva.StatusReserva.CONFIRMADA)
        );
        when(reservaGateway.reservasDoDiaPara(RESTAURANTE)).thenReturn(reservas);

        assertDoesNotThrow(() ->
                criarReservaUseCase.criarReserva(1L, CLIENTE, proximoDiaAoAbrir, 6)
        );
    }

    @Test
    void criarReservaRestauranteNaoEncontrado() {
        when(restauranteGateway.obtemPeloId(1L)).thenReturn(Optional.empty());

        LocalDateTime proximoDiaAoAbrir = getProximoDiaAoAbrir(RESTAURANTE);

        assertThatThrownBy(() ->
                criarReservaUseCase.criarReserva(1L, CLIENTE, proximoDiaAoAbrir, 6))
                .isInstanceOf(ErroDeValidacao.class)
                .hasMessage("Restaurante não encontrado");
    }

    @Test
    void criarReservaRestauranteClienteJaReservouEsteDia() {
        when(restauranteGateway.obtemPeloId(1L)).thenReturn(Optional.of(RESTAURANTE));
        when(clienteGateway.cadastra(CLIENTE)).thenReturn(CLIENTE);

        LocalDateTime proximoDiaAoAbrir = getProximoDiaAoAbrir(RESTAURANTE);

        List<Reserva> reservas = List.of(
                new Reserva(1L, RESTAURANTE, new Cliente(2L, "Beto", TELEFONE), proximoDiaAoAbrir, 100, Reserva.StatusReserva.CONFIRMADA),
                new Reserva(1L, RESTAURANTE, CLIENTE, proximoDiaAoAbrir, 6, Reserva.StatusReserva.CONFIRMADA)
        );
        when(reservaGateway.reservasDoDiaPara(RESTAURANTE)).thenReturn(reservas);

        assertThatThrownBy(() ->
                criarReservaUseCase.criarReserva(1L, CLIENTE, proximoDiaAoAbrir, 6))
                .isInstanceOf(ErroDeValidacao.class)
                .hasMessage("O cliente já reservou neste dia");
    }

    @Test
    void criarReservaRestauranteCapacidadeEsgotada() {
        when(restauranteGateway.obtemPeloId(1L)).thenReturn(Optional.of(RESTAURANTE));
        when(clienteGateway.cadastra(CLIENTE)).thenReturn(CLIENTE);

        LocalDateTime proximoDiaAoAbrir = getProximoDiaAoAbrir(RESTAURANTE);

        List<Reserva> reservas = List.of(
                new Reserva(1L, RESTAURANTE, new Cliente(2L, "Beto", TELEFONE), proximoDiaAoAbrir, 100, Reserva.StatusReserva.CONFIRMADA),
                new Reserva(1L, RESTAURANTE, new Cliente(3L, "Bebeto", TELEFONE), proximoDiaAoAbrir, 60, Reserva.StatusReserva.CONFIRMADA)
            );
        when(reservaGateway.reservasDoDiaPara(RESTAURANTE)).thenReturn(reservas);

        assertThatThrownBy(() ->
                criarReservaUseCase.criarReserva(1L, CLIENTE, proximoDiaAoAbrir, 6))
                .isInstanceOf(ErroDeValidacao.class)
                .hasMessage("Restaurante sem vagas nesse dia");
    }
}