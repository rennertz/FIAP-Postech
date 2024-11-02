package br.com.vetvision.supervisor.infrastructure.persistence;

import br.com.vetvision.supervisor.domain.model.solicitacao.Clinica;
import br.com.vetvision.supervisor.domain.model.solicitacao.ClinicaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ClinicaRepositoryJPA extends CrudRepository<Clinica, String>, ClinicaRepository {

    default boolean clinicaExiste(String cnpj){ return findById(cnpj).isPresent();};
    default Clinica criarClinica(Clinica clinica){return save(clinica);};

   ;


}
