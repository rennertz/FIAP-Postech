package br.com.booknrest.booknrest.application;

import br.com.booknrest.booknrest.entities.Restaurante;
import br.com.booknrest.booknrest.gateway.RestauranteGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroDeRestauranteUseCase {

    final RestauranteGateway restauranteGateway;

    public CadastroDeRestauranteUseCase(RestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public Restaurante salvaRestaurante(Restaurante req) {
        return restauranteGateway.cadastra(req);
    }

    public List<Restaurante> obtemRestaurantes() {
        return restauranteGateway.buscaTodos();
    }
}
