package br.com.booknrest.booknrest.infra.persistence.reserva;

import br.com.booknrest.booknrest.infra.persistence.restaurante.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepositoryJPA extends JpaRepository<ReservaEntity, Long> {
    List<ReservaEntity> findByDataHoraIsBetweenAndRestaurante(LocalDateTime dataHoraAfter, LocalDateTime dataHoraBefore, RestauranteEntity restaurante);
}
