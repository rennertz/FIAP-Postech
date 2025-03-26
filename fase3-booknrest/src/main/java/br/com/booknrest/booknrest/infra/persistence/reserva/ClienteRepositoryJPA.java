package br.com.booknrest.booknrest.infra.persistence.reserva;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClienteRepositoryJPA extends CrudRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> getByNome(String nome);
}
