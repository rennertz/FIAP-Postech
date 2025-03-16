package br.com.booknrest.booknrest.application;

import br.com.booknrest.booknrest.gateway.RestauranteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class CadastroDeRestauranteUseCaseTest {

    CadastroDeRestauranteUseCase cadastroDeRestaurante;

    RestauranteGateway gatewayMock;

    @BeforeEach
    void setUp() {
        gatewayMock = Mockito.mock(RestauranteGateway.class);
        cadastroDeRestaurante = new CadastroDeRestauranteUseCase(gatewayMock);
    }

    @Test
    void salvaRestaurante() {
    }

    @Test
    void obtemRestaurantes() {
    }
}