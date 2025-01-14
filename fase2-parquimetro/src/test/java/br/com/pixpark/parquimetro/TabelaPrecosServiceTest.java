package br.com.pixpark.parquimetro;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class TabelaPrecosServiceTest {

    TabelaPrecosService service = new TabelaPrecosService(new TabelaPrecos());

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