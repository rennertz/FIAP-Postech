package br.com.booknrest.booknrest.util;

import br.com.booknrest.booknrest.entities.HorarioDeFuncionamento;
import br.com.booknrest.booknrest.entities.Restaurante;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public class ReservaHelperFactory {

    public static LocalDateTime getProximoDiaAoAbrir(Restaurante restaurante) {
        HorarioDeFuncionamento horario = restaurante.getHorariosDeFuncionamento().getFirst();
        LocalTime aoAbrir = horario.getHoraAbertura();
        LocalDate proximoDia = diaNaProximaSemana(horario.getDiaDaSemana());

        return LocalDateTime.of(proximoDia, aoAbrir);
    }

    public static LocalDate diaNaProximaSemana(DayOfWeek diaDaSemana) {
        return LocalDateTime.now().with(TemporalAdjusters.next(diaDaSemana)).toLocalDate();
    }
}
