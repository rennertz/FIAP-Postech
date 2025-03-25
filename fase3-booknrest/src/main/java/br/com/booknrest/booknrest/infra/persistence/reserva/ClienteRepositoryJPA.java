package br.com.booknrest.booknrest.infra.persistence.reserva;

import org.springframework.data.repository.CrudRepository;

public interface ClienteRepositoryJPA extends CrudRepository<ClienteEntity, Long> {
}
