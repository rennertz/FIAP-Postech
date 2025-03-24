package br.com.booknrest.booknrest.entities;

import br.com.booknrest.booknrest.exceptions.ErroDeValidacao;
import br.com.booknrest.booknrest.util.RestauranteHelperFactory;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    public static final Restaurante RESTAURANTE = RestauranteHelperFactory.getRestauranteNomeAleatorio();
    public static final Cliente CLIENTE = new Cliente(null, "Abel", "10 99999-9999");

    @Test
    void novaReservaInvalida() {

        Cliente cliente = CLIENTE;
        LocalDateTime proximoDiaAoAbrir = getProximoDiaAoAbrir();

        assertDoesNotThrow(() -> new Reserva(RESTAURANTE, cliente, proximoDiaAoAbrir, 6));

        assertThatThrownBy(() -> new Reserva(null, cliente, proximoDiaAoAbrir, 6))
                .isInstanceOf(ErroDeValidacao.class);
        assertThatThrownBy(() -> new Reserva(RESTAURANTE, null, proximoDiaAoAbrir, 6))
                .isInstanceOf(ErroDeValidacao.class);
        assertThatThrownBy(() -> new Reserva(RESTAURANTE, cliente, null, 6))
                .isInstanceOf(ErroDeValidacao.class);
        assertThatThrownBy(() -> new Reserva(RESTAURANTE, cliente, null, 0))
                .isInstanceOf(ErroDeValidacao.class);
        assertThatThrownBy(() -> new Reserva(RESTAURANTE, cliente, null, -1))
                .isInstanceOf(ErroDeValidacao.class);
        assertThatThrownBy(() -> new Reserva(RESTAURANTE, cliente, null, 101))
                .isInstanceOf(ErroDeValidacao.class);
    }

    @Test
    void novoClienteInvalido() {

        assertDoesNotThrow(() -> new Cliente(null, "Abel", "10 99999-9999"));

        assertThatThrownBy(() -> new Cliente(null, null, "10 99999-9999"))
                .isInstanceOf(ErroDeValidacao.class);
        assertThatThrownBy(() -> new Cliente(null, "Abel", null))
                .isInstanceOf(ErroDeValidacao.class);
        assertThatThrownBy(() -> new Cliente(null, "Abel", "10 9 9999-9999"))
                .isInstanceOf(ErroDeValidacao.class);
    }

    @Test
    void confirmarReserva() {
        Reserva reserva = new Reserva(RESTAURANTE, CLIENTE, getProximoDiaAoAbrir(), 6);
        assertDoesNotThrow(reserva::confirmar);

        assertThatThrownBy(reserva::confirmar)
                .isInstanceOf(ErroDeValidacao.class)
                .hasMessage("Reserva já foi confirmada.");

        assertDoesNotThrow(reserva::cancelar);

        assertThatThrownBy(reserva::cancelar)
                .isInstanceOf(ErroDeValidacao.class)
                .hasMessage("Reserva já foi cancelada.");

        assertThatThrownBy(reserva::confirmar)
                .isInstanceOf(ErroDeValidacao.class)
                .hasMessage("Reserva não pode ser confirmada.");
    }

    private static LocalDateTime getProximoDiaAoAbrir() {
        HorarioDeFuncionamento horario = RESTAURANTE.getHorariosDeFuncionamento().getFirst();
        LocalTime aoAbrir = horario.getHoraAbertura();
        LocalDate proximoDia = diaNaProximaSemana(horario.getDiaDaSemana()).toLocalDate();

        return LocalDateTime.of(proximoDia, aoAbrir);
    }

    private static LocalDateTime diaNaProximaSemana(DayOfWeek diaDaSemana) {
        return LocalDateTime.now().with(TemporalAdjusters.next(diaDaSemana));
    }
}