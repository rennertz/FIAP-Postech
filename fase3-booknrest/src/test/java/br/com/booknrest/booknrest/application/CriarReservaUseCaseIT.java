package br.com.booknrest.booknrest.application;

import br.com.booknrest.booknrest.entities.Cliente;
import br.com.booknrest.booknrest.exceptions.ErroDeValidacao;
import br.com.booknrest.booknrest.util.GeradorDeNomesAleatorios;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static br.com.booknrest.booknrest.util.ReservaHelperFactory.diaNaProximaSemana;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CriarReservaUseCaseIT {
    public static final String TELEFONE = "10 99999-9999";
    public static final LocalDateTime DATA_HORA_DISPONIVEL = LocalDateTime.of(
            diaNaProximaSemana(DayOfWeek.SATURDAY),
            LocalTime.of(20, 0));


    @Autowired CriarReservaUseCase criarReservaUseCase;


    @Test
    void criarReservaValidaSeHaVagas() {

        Cliente cliente1 = new Cliente(null, GeradorDeNomesAleatorios.geraNome(6), TELEFONE);
        assertDoesNotThrow(() ->
                criarReservaUseCase.criarReserva(1L, cliente1, DATA_HORA_DISPONIVEL, 40)
        );

        Cliente cliente2 = new Cliente(null, GeradorDeNomesAleatorios.geraNome(6), TELEFONE);
        assertThatThrownBy(() ->
                criarReservaUseCase.criarReserva(1L, cliente2, DATA_HORA_DISPONIVEL, 40))
                .isInstanceOf(ErroDeValidacao.class)
                .hasMessage("Restaurante sem vagas nesse dia");
    }
}