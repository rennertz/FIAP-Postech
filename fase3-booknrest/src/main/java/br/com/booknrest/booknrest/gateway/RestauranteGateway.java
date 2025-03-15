package br.com.booknrest.booknrest.gateway;

import br.com.booknrest.booknrest.entities.Restaurante;
import br.com.booknrest.booknrest.infra.persistence.restaurante.RestauranteEntity;
import br.com.booknrest.booknrest.infra.persistence.restaurante.RestauranteEntityMapper;
import br.com.booknrest.booknrest.infra.persistence.restaurante.RestauranteRepositoryJPA;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.StreamSupport;

@Repository
public class RestauranteGateway {

    private final RestauranteRepositoryJPA repository;
    private final RestauranteEntityMapper mapper;


    public RestauranteGateway(RestauranteRepositoryJPA repository, RestauranteEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Restaurante cadastra(Restaurante req) {
        RestauranteEntity restaurante = mapper.toEntity(req);
        RestauranteEntity saved = repository.save(restaurante);
        return mapper.toDomain(saved);
    }

    public List<Restaurante> buscaTodos() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(mapper::toDomain)
                .toList();
    }
}
