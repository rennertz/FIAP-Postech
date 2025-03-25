package br.com.booknrest.booknrest.util;

import br.com.booknrest.booknrest.entities.Cliente;
import br.com.booknrest.booknrest.entities.HorarioDeFuncionamento;
import br.com.booknrest.booknrest.entities.Reserva;
import br.com.booknrest.booknrest.entities.Restaurante;
import br.com.booknrest.booknrest.infra.rest.ReservaDTO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public class ReservaHelperFactory {
    public static final Restaurante RESTAURANTE = RestauranteHelperFactory.getRestauranteNomeAleatorio();
    public static final String TELEFONE = "10 99999-9999";

    public static ReservaDTO getNovaReservaDTO() {
        return new ReservaDTO(null, 1L, ReservaDTO.toDTO(getClienteNomeAleatorio()), LocalDateTime.of(2027, 3, 13, 20, 0), 10, null);
    }

    public static LocalDateTime getProximoDiaAoAbrir(Restaurante restaurante) {
        HorarioDeFuncionamento horario = restaurante.getHorariosDeFuncionamento().getFirst();
        LocalTime aoAbrir = horario.getHoraAbertura();
        LocalDate proximoDia = diaNaProximaSemana(horario.getDiaDaSemana());

        return LocalDateTime.of(proximoDia, aoAbrir);
    }

    public static LocalDate diaNaProximaSemana(DayOfWeek diaDaSemana) {
        return LocalDateTime.now().with(TemporalAdjusters.next(diaDaSemana)).toLocalDate();
    }

    public static Reserva getNovaReservaClienteAleatorio() {
        return new Reserva(RESTAURANTE, getClienteNomeAleatorio(), getProximoDiaAoAbrir(RESTAURANTE), 6);
    }

    public static Cliente getClienteNomeAleatorio() {
        String nomeAleatorio = GeradorDeNomesAleatorios.geraNome(6);
        return new Cliente(null, nomeAleatorio, TELEFONE);
    }
}
