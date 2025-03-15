package br.com.booknrest.booknrest.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class HorarioDeFuncionamento {
    private Long id;
    private DayOfWeek diaDaSemana;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaAbertura;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaFechamento;

    @JsonIgnore
    private Restaurante restaurante;

    public HorarioDeFuncionamento(Long id, DayOfWeek diaDaSemana, LocalTime horaAbertura, LocalTime horaFechamento) {
        this.id = id;
        this.diaDaSemana = diaDaSemana;
        this.horaAbertura = horaAbertura;
        this.horaFechamento = horaFechamento;
    }
}