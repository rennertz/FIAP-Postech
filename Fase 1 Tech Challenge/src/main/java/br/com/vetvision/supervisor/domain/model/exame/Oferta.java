package br.com.vetvision.supervisor.domain.model.exame;

import br.com.vetvision.supervisor.domain.model.cadastro.Consultor;

import java.time.Duration;

public class Oferta {
    private Consultor consultor;
    private Duration prazoParaExame;
    private boolean foiAceita;
}
