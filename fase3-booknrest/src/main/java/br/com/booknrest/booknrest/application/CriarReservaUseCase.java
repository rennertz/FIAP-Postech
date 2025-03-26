package br.com.booknrest.booknrest.application;

import br.com.booknrest.booknrest.entities.Cliente;
import br.com.booknrest.booknrest.entities.Reserva;
import br.com.booknrest.booknrest.entities.Restaurante;
import br.com.booknrest.booknrest.exceptions.ErroDeValidacao;
import br.com.booknrest.booknrest.gateway.ClienteGateway;
import br.com.booknrest.booknrest.gateway.ReservaGateway;
import br.com.booknrest.booknrest.gateway.RestauranteGateway;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CriarReservaUseCase {
    private static final Logger log = LoggerFactory.getLogger(CriarReservaUseCase.class);
    private final ReservaGateway reservaGateway;
    private final RestauranteGateway restauranteGateway;
    private final ClienteGateway clienteGateway;

    public CriarReservaUseCase(ReservaGateway reservaGateway, RestauranteGateway restauranteGateway, ClienteGateway clienteGateway) {
        this.reservaGateway = reservaGateway;
        this.restauranteGateway = restauranteGateway;
        this.clienteGateway = clienteGateway;
    }

    @Transactional
    public Reserva criarReserva(Long restauranteId, Cliente cliente, LocalDateTime dataHora, int quantidadePessoas) {
        Restaurante restaurante = restauranteGateway.obtemPeloId(restauranteId)
                .orElseThrow(() -> new ErroDeValidacao("Restaurante não encontrado"));

        Cliente clienteSalvo = clienteGateway.getOrSave(cliente);
        Reserva reserva = new Reserva(null, restaurante, clienteSalvo, dataHora, quantidadePessoas, Reserva.StatusReserva.PENDENTE);
        avaliaReservasDoDia(reserva);

        // TODO gerenciamento de reservas
        reserva.confirmar();
        return reservaGateway.salva(reserva);
    }

    private void avaliaReservasDoDia(Reserva novaReserva) {
        LocalDate dia = novaReserva.getDataHora().toLocalDate();
        Restaurante restauranteAReservar = novaReserva.getRestaurante();
        List<Reserva> reservasExistentes = reservaGateway.reservasDoDiaPara(dia, restauranteAReservar);

        log.debug("Reservas existentes no dia: {}", reservasExistentes);

        avaliaSeClienteJaFezReservaNoDia(novaReserva, reservasExistentes);
        avaliaSeRestauranteFicaraLotado(reservasExistentes, novaReserva.getQuantidadePessoas(), restauranteAReservar.getCapacidade());

    }

    private static void avaliaSeClienteJaFezReservaNoDia(Reserva novaReserva, List<Reserva> reservasExistentes) {
        boolean clienteJaReservouNoDia = reservasExistentes.stream()
                .anyMatch(re -> re.getCliente().equals(novaReserva.getCliente()));

        if (clienteJaReservouNoDia) {
            throw new ErroDeValidacao("O cliente já reservou neste dia");
        }
    }

    private static void avaliaSeRestauranteFicaraLotado(List<Reserva> reservasExistentes, int quantidadePessoas, int capacidade) {
        int vagasOcupadas = reservasExistentes.stream()
                .mapToInt(Reserva::getQuantidadePessoas)
                .sum();

        log.debug("Vagas ocupadas: {}, Quantidade de pessoas {}, Capacidade do restaurante {}", vagasOcupadas, quantidadePessoas, capacidade);
        boolean restauranteNaoComporta = vagasOcupadas + quantidadePessoas > capacidade;
        if (restauranteNaoComporta) {
            throw new ErroDeValidacao("Restaurante sem vagas nesse dia");
        }
    }

    public List<Reserva> obtemTodasAsReservas() {
        return reservaGateway.obtemTodasAsReservas();
    }
}
