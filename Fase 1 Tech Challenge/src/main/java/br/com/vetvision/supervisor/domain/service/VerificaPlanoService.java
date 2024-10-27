package br.com.vetvision.supervisor.domain.service;

import br.com.vetvision.supervisor.domain.model.cadastro.PlanoVet;
import br.com.vetvision.supervisor.domain.model.cadastro.TipoExame;
import br.com.vetvision.supervisor.domain.model.exame.Pet;

public interface VerificaPlanoService {

    boolean planoAtivo(PlanoVet planoVet, Pet pet);

    boolean planoCobre(PlanoVet planoVet, TipoExame exame);

}
