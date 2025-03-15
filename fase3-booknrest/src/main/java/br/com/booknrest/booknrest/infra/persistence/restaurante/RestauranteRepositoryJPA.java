package br.com.booknrest.booknrest.infra.persistence.restaurante;

import org.springframework.data.repository.CrudRepository;

public interface RestauranteRepositoryJPA extends CrudRepository<RestauranteEntity, String> {

}
