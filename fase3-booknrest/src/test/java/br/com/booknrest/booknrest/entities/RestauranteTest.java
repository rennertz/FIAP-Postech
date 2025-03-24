package br.com.booknrest.booknrest.entities;

import br.com.booknrest.booknrest.exceptions.ErroDeValidacao;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RestauranteTest {

    @Test
    void novoRestauranteInvalido() {
        assertDoesNotThrow(() -> new Restaurante(null, "Moqueca", "Mooca", "frutos do mar", 150));

        assertThatThrownBy(() -> new Restaurante(null, null, "Mooca", "frutos do mar", 150))
            .isInstanceOf(ErroDeValidacao.class);
        assertThatThrownBy(() -> new Restaurante(null, "Moqueca", null, "frutos do mar", 150))
            .isInstanceOf(ErroDeValidacao.class);
        assertThatThrownBy(() -> new Restaurante(null, "Moqueca", "Mooca", null, 150))
            .isInstanceOf(ErroDeValidacao.class);
        assertThatThrownBy(() -> new Restaurante(null, "Moqueca", "Mooca", "frutos do mar", 0))
            .isInstanceOf(ErroDeValidacao.class);
        assertThatThrownBy(() -> new Restaurante(null, "Moqueca", "Mooca", "frutos do mar", -5))
            .isInstanceOf(ErroDeValidacao.class);
        assertThatThrownBy(() -> new Restaurante(null, "Moqueca", "Mooca", "frutos do mar", 1001))
            .isInstanceOf(ErroDeValidacao.class);
    }

    @Test
    void horarioValido() {
        assertDoesNotThrow(() ->
                new HorarioDeFuncionamento(1L, DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(23, 0))
        );
    }

    @Test
    void horarioTrocado() {
        Throwable throwable = catchThrowable(() ->
                new HorarioDeFuncionamento(1L, DayOfWeek.MONDAY, LocalTime.of(23, 0), LocalTime.of(19, 0))
        );
        assertThat(throwable).isInstanceOf(ErroDeValidacao.class)
                .hasMessage("O horário de fechamento deve ser após o horário de abertura");
    }

    @Test
    void horarioCurto() {
        Throwable throwable = catchThrowable(() ->
                new HorarioDeFuncionamento(1L, DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(19, 30))
        );
        assertThat(throwable).isInstanceOf(ErroDeValidacao.class)
                .hasMessage("O Horário de abertura deve durar no mínimo 1 hora");
    }

    @Test
    void horariosConflitantes() {
        HorarioDeFuncionamento horario1 = new HorarioDeFuncionamento(1L, DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0));
        HorarioDeFuncionamento horario2 = new HorarioDeFuncionamento(1L, DayOfWeek.MONDAY, LocalTime.of(20, 0), LocalTime.of(23, 0));
        Restaurante restaurante = getRestaurante();

        assertDoesNotThrow(() ->
                restaurante.adicionaHorarioDeFuncionamento(horario1)
        );
        Throwable throwable = catchThrowable(() ->
                restaurante.adicionaHorarioDeFuncionamento(horario2)
        );
        assertThat(throwable).isInstanceOf(ErroDeValidacao.class)
                .hasMessage("Horários conflitantes: seg. entre 19:00 e 22:00 e seg. entre 20:00 e 23:00");
    }

    @Test
    void horariosEmExcesso() {
        HorarioDeFuncionamento horario1 = new HorarioDeFuncionamento(1L, DayOfWeek.MONDAY, LocalTime.of(11, 0), LocalTime.of(14, 0));
        HorarioDeFuncionamento horario2 = new HorarioDeFuncionamento(1L, DayOfWeek.MONDAY, LocalTime.of(15, 0), LocalTime.of(19, 0));
        HorarioDeFuncionamento horario3 = new HorarioDeFuncionamento(1L, DayOfWeek.MONDAY, LocalTime.of(20, 0), LocalTime.of(23, 0));
        Restaurante restaurante = getRestaurante();

        assertDoesNotThrow(() ->
                restaurante.adicionaHorarioDeFuncionamento(horario1)
        );
        assertDoesNotThrow(() ->
                restaurante.adicionaHorarioDeFuncionamento(horario2)
        );

        // deve falhar no 3o horário adicionado na segunda
        Throwable throwable = catchThrowable(() ->
                restaurante.adicionaHorarioDeFuncionamento(horario3)
        );
        assertThat(throwable).isInstanceOf(ErroDeValidacao.class)
                .hasMessage("Não é permitido mais de 2 horarios por dia da semana");
    }

    @Test
    void NaoEstaraAbertoNoPassado() {
        Restaurante restaurante = getRestauranteSegundaFeira();

        LocalDate dia = LocalDate.of(2020, 12, 8);
        LocalDateTime dataHora = LocalDateTime.of(dia, LocalTime.of(20, 0));

        assertThatThrownBy(() -> restaurante.estaraAbertoNoHorario(dataHora))
                .isInstanceOf(ErroDeValidacao.class)
                .hasMessage("A data e hora informada é do passado");
    }

    @Test
    void estaraAbertoNoHorario() {
        Restaurante restaurante = getRestauranteSegundaFeira();

        LocalDate dia = LocalDate.of(2025, 12, 8);
        boolean estaAbertoNoHorario;

        estaAbertoNoHorario = restaurante.estaraAbertoNoHorario(LocalDateTime.of(dia, LocalTime.of(13, 0)));
        assertThat(estaAbertoNoHorario).isTrue();

        estaAbertoNoHorario = restaurante.estaraAbertoNoHorario(LocalDateTime.of(dia, LocalTime.of(16, 0)));
        assertThat(estaAbertoNoHorario).isFalse();

        estaAbertoNoHorario = restaurante.estaraAbertoNoHorario(LocalDateTime.of(dia, LocalTime.of(20, 0)));
        assertThat(estaAbertoNoHorario).isTrue();
    }

    private static Restaurante getRestauranteSegundaFeira() {
        HorarioDeFuncionamento horario1 = new HorarioDeFuncionamento(1L, DayOfWeek.MONDAY, LocalTime.of(11, 0), LocalTime.of(15, 0));
        HorarioDeFuncionamento horario2 = new HorarioDeFuncionamento(1L, DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(23, 0));
        Restaurante restaurante = getRestaurante();
        restaurante.adicionaHorarioDeFuncionamento(horario1);
        restaurante.adicionaHorarioDeFuncionamento(horario2);

        return restaurante;
    }

    private static Restaurante getRestaurante() {
        return new Restaurante(1L, "Moqueca", "Mooca", "frutos do mar", 150);
    }
}