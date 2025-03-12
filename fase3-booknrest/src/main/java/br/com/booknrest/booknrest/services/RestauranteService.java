package br.com.booknrest.booknrest.services;

import br.com.booknrest.booknrest.model.Restaurante;
import br.com.booknrest.booknrest.repository.RestauranteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class RestauranteService {

    final RestauranteRepository restauranteRepository;

    public RestauranteService(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    public Restaurante salvaRestaurante(Restaurante req) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(req.getNome());
        restaurante.setLocalizacao(req.getLocalizacao());
        restaurante.setTipoCozinha(req.getTipoCozinha());
        restaurante.setCapacidade(req.getCapacidade());

        req.getHorarioDeFuncionamento().forEach(
                restaurante::adicionaHorarioDeFuncionamento);

        return restauranteRepository.save(restaurante);
    }

    public List<Restaurante> obtemRestaurantes() {
        return StreamSupport.stream(restauranteRepository.findAll().spliterator(), false)
                .toList();
    }
}
