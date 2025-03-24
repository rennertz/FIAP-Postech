package br.com.booknrest.booknrest.infra.persistence.restaurante;

import br.com.booknrest.booknrest.entities.Restaurante;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RestauranteRepositoryJPA extends CrudRepository<RestauranteEntity, Long> {

    Optional<Restaurante> findByNome(String nome);
}
