package br.com.vetvision.supervisor.domain.service;

import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinario;
import br.com.vetvision.supervisor.domain.model.plano.TipoExame;
import br.com.vetvision.supervisor.domain.model.solicitacao.Pet;

public interface VerificaPlanoService {

    boolean planoAtivo(PlanoVeterinario planoVeterinario, Pet pet);

    boolean planoCobre(PlanoVeterinario planoVeterinario, TipoExame exame);

}