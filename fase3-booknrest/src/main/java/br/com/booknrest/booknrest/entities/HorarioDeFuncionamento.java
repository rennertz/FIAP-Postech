package br.com.booknrest.booknrest.entities;

import br.com.booknrest.booknrest.exceptions.ErroDeValidacao;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

public class HorarioDeFuncionamento {
    private final Long id;
    private final DayOfWeek diaDaSemana;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private final LocalTime horaAbertura;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private final LocalTime horaFechamento;

    public HorarioDeFuncionamento(Long id, DayOfWeek diaDaSemana, LocalTime horaAbertura, LocalTime horaFechamento) {
        if (horaAbertura.isAfter(horaFechamento)) {
            throw new ErroDeValidacao("O horário de fechamento deve ser após o horário de abertura");
        }

        if (Duration.between(horaAbertura, horaFechamento).toHours() < 1) {
            throw new ErroDeValidacao("O Horário de abertura deve durar no mínimo 1 hora");
        }

        this.id = id;
        this.diaDaSemana = diaDaSemana;
        this.horaAbertura = horaAbertura;
        this.horaFechamento = horaFechamento;
    }

    public Long getId() {
        return this.id;
    }

    public DayOfWeek getDiaDaSemana() {
        return this.diaDaSemana;
    }

    public LocalTime getHoraAbertura() {
        return this.horaAbertura;
    }

    public LocalTime getHoraFechamento() {
        return this.horaFechamento;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HorarioDeFuncionamento that = (HorarioDeFuncionamento) o;
        return Objects.equals(id, that.id) &&
                diaDaSemana == that.diaDaSemana &&
                Objects.equals(horaAbertura, that.horaAbertura) &&
                Objects.equals(horaFechamento, that.horaFechamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, diaDaSemana, horaAbertura, horaFechamento);
    }

    @Override
    public String toString() {
        return "HorarioDeFuncionamento(id=" + this.getId() + ", diaDaSemana=" + this.getDiaDaSemana() + ", horaAbertura=" + this.getHoraAbertura() + ", horaFechamento=" + this.getHoraFechamento() + ")";
    }
}