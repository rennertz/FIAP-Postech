package br.com.booknrest.booknrest.application;

import br.com.booknrest.booknrest.entities.Restaurante;
import br.com.booknrest.booknrest.exceptions.ErroDeValidacao;
import br.com.booknrest.booknrest.gateway.RestauranteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


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
        when(gatewayMock.cadastra(getRestaurante())).thenReturn(getRestaurante());
        when(gatewayMock.obtemPeloNome(anyString())).thenReturn(Optional.empty());

        assertDoesNotThrow(() ->
                cadastroDeRestaurante.salvaRestaurante(getRestaurante())
        );
    }

    @Test
    void restauranteJaCadastrado() {
        Restaurante restaurante = getRestaurante();
        when(gatewayMock.obtemPeloNome(anyString())).thenReturn(Optional.of(restaurante));

        assertThrows(ErroDeValidacao.class, () ->
            cadastroDeRestaurante.salvaRestaurante(restaurante)
        );
    }

    private static Restaurante getRestaurante() {
        return new Restaurante(1L, "Moqueca", "Mooca", "frutos do mar", 150);
    }
}