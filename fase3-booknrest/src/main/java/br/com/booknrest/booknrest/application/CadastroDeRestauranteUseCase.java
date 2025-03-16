package br.com.booknrest.booknrest.application;

import br.com.booknrest.booknrest.entities.Restaurante;
import br.com.booknrest.booknrest.exceptions.ErroDeValidacao;
import br.com.booknrest.booknrest.gateway.RestauranteGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroDeRestauranteUseCase {

    final RestauranteGateway gateway;

    public CadastroDeRestauranteUseCase(RestauranteGateway gateway) {
        this.gateway = gateway;
    }

    public Restaurante salvaRestaurante(Restaurante req) {
        gateway.obtemPeloNome(req.getNome()).ifPresent(restaurante -> {
            throw new ErroDeValidacao("Restaurante com o mesmo nome já existe");
        });
        return gateway.cadastra(req);
    }

    public List<Restaurante> obtemRestaurantes() {
        return gateway.buscaTodos();
    }
}
