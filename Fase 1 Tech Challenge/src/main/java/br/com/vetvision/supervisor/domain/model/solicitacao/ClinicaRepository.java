package br.com.vetvision.supervisor.domain.model.solicitacao;

import jakarta.validation.Valid;

import java.util.Optional;

public interface ClinicaRepository {
    Optional<Clinica> clinicaExiste(String cnpj);
    Clinica criarClinica(@Valid Clinica clinica);
}
