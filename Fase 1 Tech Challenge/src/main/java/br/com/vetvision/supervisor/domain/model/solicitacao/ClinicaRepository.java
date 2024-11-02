package br.com.vetvision.supervisor.domain.model.solicitacao;

import java.util.Optional;

public interface ClinicaRepository {
    Optional<Clinica> clinicaExiste(String cnpj);
    Clinica criarClinica(Clinica clinica);
}
