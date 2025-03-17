package br.com.booknrest.booknrest.gateway;

import br.com.booknrest.booknrest.entities.Restaurante;
import br.com.booknrest.booknrest.exceptions.ErroDeValidacao;
import br.com.booknrest.booknrest.infra.persistence.restaurante.RestauranteEntity;
import br.com.booknrest.booknrest.infra.persistence.restaurante.RestauranteEntityMapper;
import br.com.booknrest.booknrest.infra.persistence.restaurante.RestauranteRepositoryJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
public class RestauranteGateway {

    private static final Logger log = LoggerFactory.getLogger(RestauranteGateway.class);

    private final RestauranteRepositoryJPA repository;
    private final RestauranteEntityMapper mapper;


    public RestauranteGateway(RestauranteRepositoryJPA repository, RestauranteEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Restaurante cadastra(Restaurante req) {
        RestauranteEntity restaurante = mapper.toEntity(req);
        try {
            RestauranteEntity saved = repository.save(restaurante);
            return mapper.toDomain(saved);
        } catch (DataIntegrityViolationException e) {
            log.error("Erro capturado no DB: {}", e.getMessage());
            throw new ErroDeValidacao("Impedido registro duplicado");
        }
    }

    public List<Restaurante> buscaTodos() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(mapper::toDomain)
                .toList();
    }

    public Optional<Restaurante> obtemPeloNome(String nome) {
        return repository.findByNome(nome);
    }
}
