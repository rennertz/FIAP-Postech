package br.com.vetvision.supervisor.infrastructure.persistence;

import br.com.vetvision.supervisor.domain.model.solicitacao.Clinica;
import br.com.vetvision.supervisor.domain.model.solicitacao.ClinicaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClinicaRepositoryJPA extends CrudRepository<Clinica, String>, ClinicaRepository {

    @Override
    default Optional<Clinica> clinicaExiste(String cnpj){ return findById(cnpj);}

    @Override
    default Clinica criarClinica(Clinica clinica){return save(clinica);}
}
