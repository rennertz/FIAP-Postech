package br.com.pixpark.parquimetro;

import br.com.pixpark.parquimetro.domain.model.TabelaPrecos;
import br.com.pixpark.parquimetro.domain.service.TabelaPrecosService;
import br.com.pixpark.parquimetro.infrastructure.TabelaPrecosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TabelaPrecosServiceTest {

    @Mock
    TabelaPrecosRepository precosRepo;

    @InjectMocks
    TabelaPrecosService service;

    @BeforeEach
    void setUp() {

        Map<Integer, BigDecimal> precos = Map.of(
                1, new BigDecimal("1.50"),
                2, new BigDecimal("2.25"),
                3, new BigDecimal("3.00"),
                4, new BigDecimal("4.50")
        );
        TabelaPrecos tabelaPrecos = new TabelaPrecos(precos);
        when(precosRepo.findFirstByOrderByInicioVigenciaDesc()).thenReturn(Optional.of(tabelaPrecos));
    }

    @Test
    void pegarValor() {
        assertEquals(
                new BigDecimal("1.50"),
                service.pegarValor(Duration.ofHours(1)));
        assertEquals(
                new BigDecimal("2.25"),
                service.pegarValor(Duration.ofHours(2)));
        assertEquals(
                new BigDecimal("3.00"),
                service.pegarValor(Duration.ofHours(3)));
        assertEquals(
                new BigDecimal("4.50"),
                service.pegarValor(Duration.ofHours(4)));
    }

    @Test
    void pegarValorErrors() {
        IllegalArgumentException negativeValueException = assertThrows(
                IllegalArgumentException.class,
                () -> service.pegarValor(Duration.ofHours(-1)));
        assertEquals("Tempo de permanência inválido", negativeValueException.getMessage());

        IllegalArgumentException excessiveTimeException = assertThrows(
                IllegalArgumentException.class,
                () -> service.pegarValor(Duration.ofHours(5)));
        assertEquals("Tempo de permanência excede máximo permitido", excessiveTimeException.getMessage());
    }
}