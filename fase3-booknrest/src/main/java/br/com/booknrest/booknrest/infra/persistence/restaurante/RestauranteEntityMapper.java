package br.com.booknrest.booknrest.infra.persistence.restaurante;

import br.com.booknrest.booknrest.entities.HorarioDeFuncionamento;
import br.com.booknrest.booknrest.entities.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class RestauranteEntityMapper {

    public RestauranteEntity toEntity(Restaurante restaurante) {
        RestauranteEntity restauranteEntity = new RestauranteEntity(
                restaurante.getId(),
                restaurante.getNome(),
                restaurante.getLocalizacao(),
                restaurante.getTipoCozinha(),
                restaurante.getCapacidade()
        );

        restaurante.getHorarioDeFuncionamento().forEach(horario -> {
            HorarioDeFuncionamentoEntity horarioEntity = toEntity(horario);
            restauranteEntity.adicionaHorarioDeFuncionamento(horarioEntity);
        });

        return restauranteEntity;
    }


    private HorarioDeFuncionamentoEntity toEntity(HorarioDeFuncionamento horario) {
        return new HorarioDeFuncionamentoEntity(
                horario.getId(),
                horario.getDiaDaSemana(),
                horario.getHoraAbertura(),
                horario.getHoraFechamento()
        );
    }

    public Restaurante toDomain(RestauranteEntity restauranteEntity) {
        Restaurante restaurante = new Restaurante(
                restauranteEntity.getId(),
                restauranteEntity.getNome(),
                restauranteEntity.getLocalizacao(),
                restauranteEntity.getTipoCozinha(),
                restauranteEntity.getCapacidade()
        );

        restauranteEntity.getHorarioDeFuncionamento().forEach(horarioEntity -> {
            HorarioDeFuncionamento horario = toDomain(horarioEntity);
            restaurante.adicionaHorarioDeFuncionamento(horario);
        });

        return restaurante;
    }

    private HorarioDeFuncionamento toDomain(HorarioDeFuncionamentoEntity horario) {
        return new HorarioDeFuncionamento(
                horario.getId(),
                horario.getDiaDaSemana(),
                horario.getHoraAbertura(),
                horario.getHoraFechamento()
        );
    }
}
