package br.com.booknrest.booknrest.application;

import br.com.booknrest.booknrest.entities.Cliente;
import br.com.booknrest.booknrest.entities.Reserva;
import br.com.booknrest.booknrest.entities.Restaurante;
import br.com.booknrest.booknrest.exceptions.ErroDeValidacao;
import br.com.booknrest.booknrest.gateway.ClienteGateway;
import br.com.booknrest.booknrest.gateway.ReservaGateway;
import br.com.booknrest.booknrest.gateway.RestauranteGateway;

import java.time.LocalDateTime;
import java.util.List;

public class CriarReservaUseCase {
    private final ReservaGateway reservaGateway;
    private final RestauranteGateway restauranteGateway;
    private final ClienteGateway clienteGateway;

    public CriarReservaUseCase(ReservaGateway reservaGateway, RestauranteGateway restauranteGateway, ClienteGateway clienteGateway) {
        this.reservaGateway = reservaGateway;
        this.restauranteGateway = restauranteGateway;
        this.clienteGateway = clienteGateway;
    }

    public Reserva criarReserva(Long restauranteId, Cliente cliente, LocalDateTime dataHora, int quantidadePessoas) {
        Restaurante restaurante = restauranteGateway.obtemPeloId(restauranteId)
                .orElseThrow(() -> new ErroDeValidacao("Restaurante não encontrado"));

        Cliente savedCliente = clienteGateway.cadastra(cliente);

        Reserva reserva = new Reserva(null, restaurante, savedCliente, dataHora, quantidadePessoas, Reserva.StatusReserva.PENDENTE);
        avaliaReservasDoDia(reserva);

        return reservaGateway.salva(reserva);
    }

    private void avaliaReservasDoDia(Reserva novaReserva) {
        Restaurante restauranteAReservar = novaReserva.getRestaurante();
        List<Reserva> reservasExistentes = reservaGateway.reservasDoDiaPara(restauranteAReservar);

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

        boolean restauranteNaoComporta = vagasOcupadas + quantidadePessoas > capacidade;
        if (restauranteNaoComporta) {
            throw new ErroDeValidacao("Restaurante sem vagas nesse dia");
        }
    }
}
