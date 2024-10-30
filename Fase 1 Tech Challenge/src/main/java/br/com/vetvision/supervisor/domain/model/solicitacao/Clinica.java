package br.com.vetvision.supervisor.domain.model.solicitacao;

public class Clinica {
    private String cnpj;
    private String nome;
    private String endereco;
    private String contato;   // para notificação

    public Clinica(String cnpj, String nome, String endereco, String contato) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.endereco = endereco;
        this.contato = contato;
    }
}
