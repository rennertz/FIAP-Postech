package br.com.booknrest.booknrest.infra.rest;

import br.com.booknrest.booknrest.entities.HorarioDeFuncionamento;
import br.com.booknrest.booknrest.entities.Restaurante;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public record RestauranteDTO(
        Long id,
        String nome,
        String localizacao,
        String tipoCozinha,
        List<HorarioDeFuncionamentoDTO> horariosDeFuncionamento,
        int capacidade
) {

    public record HorarioDeFuncionamentoDTO(
            Long id,
            DayOfWeek diaDaSemana,

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
            LocalTime horaAbertura,

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
