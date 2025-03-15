package br.com.booknrest.booknrest.application;

import br.com.booknrest.booknrest.entities.Restaurante;
import br.com.booknrest.booknrest.infra.persistence.restaurante.RestauranteEntity;
import br.com.booknrest.booknrest.infra.persistence.restaurante.RestauranteEntityMapper;
import br.com.booknrest.booknrest.infra.persistence.restaurante.RestauranteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class CadastroDeRestauranteUseCase {

    final RestauranteRepository restauranteRepository;

    public CadastroDeRestauranteUseCase(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    public Restaurante salvaRestaurante(Restaurante req) {
        RestauranteEntity restaurante = RestauranteEntityMapper.toEntity(req);

        RestauranteEntity saved = restauranteRepository.save(restaurante);

        return RestauranteEntityMapper.toDomain(saved);
    }

    public List<Restaurante> obtemRestaurantes() {
        return StreamSupport.stream(restauranteRepository.findAll().spliterator(), false)
                .map(RestauranteEntityMapper::toDomain)
                .toList();
    }
}
