package br.com.vetvision.supervisor.domain.service;

import java.time.LocalDate;

public interface FinanceiroService {

    // conta bancária do consultor que realizou o exame
    // Valor dependente do tipo de exame
    void pagarConsultor(String contaBancaria, double valorTipoExame, LocalDate dataPagamento);

    void cobrarPagamento(String cnpj, double valor);
}
