package br.com.vetvision.supervisor.domain.model.solicitacao;

public interface ClinicaRepository {
    boolean clinicaExiste(String cnpj);
    Clinica criarClinica(Clinica clinica);
}
