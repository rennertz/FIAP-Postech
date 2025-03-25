package br.com.booknrest.booknrest.util;

import br.com.booknrest.booknrest.entities.Restaurante;
import br.com.booknrest.booknrest.infra.rest.RestauranteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public class RestauranteHelperFactory {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    public static RestauranteDTO getRestauranteDtoNomeAleatorio() {
        var list = List.of(getHorarioDeFuncionamento());

        return new RestauranteDTO(null, GeradorDeNomesAleatorios.geraNome(6),
                "Mooca", "frutos do mar", list, 160);
    }

    public static Restaurante getRestauranteNomeAleatorio() {
        return RestauranteDTO.toModel(getRestauranteDtoNomeAleatorio());
    }

    public static String getRestauranteDtoNomeAleatorioJson() {
        try {
            return OBJECT_MAPPER.writeValueAsString(getRestauranteDtoNomeAleatorio());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Nao foi possivel fazer o parsing do JSON para a classe RestauranteDTO");
        }
    }

    private static RestauranteDTO.HorarioDeFuncionamentoDTO getHorarioDeFuncionamento() {
        return new RestauranteDTO.HorarioDeFuncionamentoDTO(
                null, DayOfWeek.MONDAY, LocalTime.of(11, 0), LocalTime.of(14, 0));
    }

}
