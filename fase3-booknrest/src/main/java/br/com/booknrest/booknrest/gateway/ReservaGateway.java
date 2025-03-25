package br.com.booknrest.booknrest.gateway;

import br.com.booknrest.booknrest.entities.Reserva;
import br.com.booknrest.booknrest.entities.Restaurante;
import br.com.booknrest.booknrest.infra.persistence.reserva.ReservaEntity;
import br.com.booknrest.booknrest.infra.persistence.reserva.ReservaEntityMapper;
import br.com.booknrest.booknrest.infra.persistence.reserva.ReservaRepositoryJPA;
import br.com.booknrest.booknrest.infra.persistence.restaurante.RestauranteEntityMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservaGateway {
    private final ReservaEntityMapper mapper;
    private final ReservaRepositoryJPA repository;
    private final RestauranteEntityMapper restauranteEntityMapper;

    public ReservaGateway(ReservaEntityMapper mapper, ReservaRepositoryJPA repository, RestauranteEntityMapper restauranteEntityMapper) {
        this.mapper = mapper;
        this.repository = repository;
        this.restauranteEntityMapper = restauranteEntityMapper;
    }

    public Reserva salva(Reserva reserva) {
        ReservaEntity saved = repository.save(mapper.toEntity(reserva));
        return mapper.toDomain(saved);
    }

    public List<Reserva> reservasDoDiaPara(LocalDate dia, Restaurante restaurante) {
        LocalDateTime inicioDia = LocalDateTime.of(dia, LocalTime.MIDNIGHT);
        LocalDateTime finalDia = LocalDateTime.of(dia, LocalTime.MAX);
        List<ReservaEntity> reservas = repository.findByDataHoraIsBetweenAndRestaurante(inicioDia, finalDia, restauranteEntityMapper.toEntity(restaurante));
        return reservas.stream()
                .map(mapper::toDomain)
                .toList();
    }
}
