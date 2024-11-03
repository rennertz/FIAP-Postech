package br.com.vetvision.supervisor.application;

import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinario;
import br.com.vetvision.supervisor.domain.model.plano.TipoExame;
import br.com.vetvision.supervisor.domain.model.solicitacao.Clinica;
import br.com.vetvision.supervisor.domain.model.solicitacao.Pet;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface MockObjects {
    Clinica mockClinica = new Clinica(
            "00.000.000/0001-01",
            "Clinica Mock",
            "Rua Mock, No 0",
            "01 00000-0001");

    Pet mockPet = new Pet(
            "MiaMock",
            "Gato",
            LocalDate.of(2020, 1, 1),
            "Cuidador Mock",
            "000.000.001-01");

    PlanoVeterinario mockPlano = new PlanoVeterinario(
            "00.000.000/0002-02",
            "Plano mock");

    TipoExame mockExame = new TipoExame("Ultrassom Gato", BigDecimal.valueOf(59.99), 0.15);
}
