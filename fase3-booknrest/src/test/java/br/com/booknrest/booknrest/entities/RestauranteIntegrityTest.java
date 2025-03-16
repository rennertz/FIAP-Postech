package br.com.booknrest.booknrest.entities;

import br.com.booknrest.booknrest.exceptions.ErroDeValidacao;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteIntegrityTest {

    @Test
    void horarioValido() {
        assertDoesNotThrow(() ->
                new HorarioDeFuncionamento(1L, DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(23, 0))
        );
    }

    @Test
    void horarioTrocado() {
        assertThrows(ErroDeValidacao.class, () ->
            new HorarioDeFuncionamento(1L, DayOfWeek.MONDAY, LocalTime.of(23, 0), LocalTime.of(19, 0))
        );
    }

    @Test
    void horarioCurto() {
        assertThrows(ErroDeValidacao.class, () ->
                new HorarioDeFuncionamento(1L, DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(19, 30))
        );
    }

    @Test
    void horariosConflitantes() {
        HorarioDeFuncionamento horario1 = new HorarioDeFuncionamento(1L, DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(22, 0));
        HorarioDeFuncionamento horario2 = new HorarioDeFuncionamento(1L, DayOfWeek.MONDAY, LocalTime.of(20, 0), LocalTime.of(23, 0));
        Restaurante restaurante = new Restaurante(1L, "Moqueca", "Mooca", "frutos do mar", 150);

        assertDoesNotThrow(() ->
                restaurante.adicionaHorarioDeFuncionamento(horario1)
        );
        assertThrows(ErroDeValidacao.class, () ->
                restaurante.adicionaHorarioDeFuncionamento(horario2)
        );
    }

    @Test
    void horariosEmExcesso() {
        HorarioDeFuncionamento horario1 = new HorarioDeFuncionamento(1L, DayOfWeek.MONDAY, LocalTime.of(11, 0), LocalTime.of(14, 0));
        HorarioDeFuncionamento horario2 = new HorarioDeFuncionamento(1L, DayOfWeek.MONDAY, LocalTime.of(15, 0), LocalTime.of(19, 0));
        HorarioDeFuncionamento horario3 = new HorarioDeFuncionamento(1L, DayOfWeek.MONDAY, LocalTime.of(20, 0), LocalTime.of(23, 0));
        Restaurante restaurante = new Restaurante(1L, "Moqueca", "Mooca", "frutos do mar", 150);

        assertDoesNotThrow(() ->
                restaurante.adicionaHorarioDeFuncionamento(horario1)
        );
        assertDoesNotThrow(() ->
                restaurante.adicionaHorarioDeFuncionamento(horario2)
        );

        // deve falhar no 3o horário adicionado na segunda
        assertThrows(ErroDeValidacao.class, () ->
                restaurante.adicionaHorarioDeFuncionamento(horario3)
        );
    }
}