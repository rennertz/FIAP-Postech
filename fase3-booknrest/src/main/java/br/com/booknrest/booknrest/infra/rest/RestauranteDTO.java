package br.com.booknrest.booknrest.infra.rest;

import br.com.booknrest.booknrest.entities.HorarioDeFuncionamento;
import br.com.booknrest.booknrest.entities.Restaurante;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public record RestauranteDTO(
        Long id,
        @NotBlank
        String nome,
        @NotBlank
        String localizacao,
        @NotBlank
        String tipoCozinha,
        @NotNull @NotEmpty @Valid
        List<HorarioDeFuncionamentoDTO> horariosDeFuncionamento,
        @Positive
        int capacidade
) {

    public record HorarioDeFuncionamentoDTO(
            Long id,

            @NotNull
            DayOfWeek diaDaSemana,

            @NotNull
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
            LocalTime horaAbertura,

            @NotNull
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
            LocalTime horaFechamento
    ) {}


    static Restaurante toModel(RestauranteDTO req) {
        Restaurante novoRestaurante = new Restaurante(req.id(), req.nome(), req.localizacao(), req.tipoCozinha(), req.capacidade());

        req.horariosDeFuncionamento().stream()
                .map(horario ->
                        new HorarioDeFuncionamento(horario.id(), horario.diaDaSemana(), horario.horaAbertura(), horario.horaFechamento()))
                .forEach(
                        novoRestaurante::adicionaHorarioDeFuncionamento);

        return novoRestaurante;
    }
}
