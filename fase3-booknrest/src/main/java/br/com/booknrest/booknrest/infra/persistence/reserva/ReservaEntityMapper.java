package br.com.booknrest.booknrest.infra.persistence.reserva;

import br.com.booknrest.booknrest.entities.Cliente;
import br.com.booknrest.booknrest.entities.Reserva;
import br.com.booknrest.booknrest.entities.Restaurante;
import br.com.booknrest.booknrest.infra.persistence.restaurante.RestauranteEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class ReservaEntityMapper {

    private final RestauranteEntityMapper restauranteMapper;

    public ReservaEntityMapper(RestauranteEntityMapper restauranteMapper) {
        this.restauranteMapper = restauranteMapper;
    }

    public ClienteEntity toEntity(Cliente cliente) {
        return new ClienteEntity(cliente.getId(), cliente.getNome(), cliente.getTelefone());
    }

    public ReservaEntity toEntity(Reserva reserva) {
        Restaurante restaurante = reserva.getRestaurante();

        return new ReservaEntity(reserva.getId(), restauranteMapper.toEntity(restaurante), toEntity(reserva.getCliente()), reserva.getDataHora(), reserva.getQuantidadePessoas(), reserva.getStatus());
    }

    public Cliente toDomain(ClienteEntity cliente) {
        return new Cliente(cliente.getId(), cliente.getNome(), cliente.getTelefone());
    }

    public Reserva toDomain(ReservaEntity reserva) {
        Restaurante restaurante = restauranteMapper.toDomain(reserva.getRestaurante());
        return new Reserva(reserva.getId(), restaurante, toDomain(reserva.getCliente()), reserva.getDataHora(), reserva.getQuantidadePessoas(), reserva.getStatus());
    }
}
