package br.com.vetvision.supervisor.domain.model.solicitacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Clinica {

    @Id
    private String cnpj;

    @Column
    private String nome;

    @Column
    private String endereco;

    @Column
    private String contato;   // para notificação

    public Clinica(String cnpj, String nome, String endereco, String contato) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.endereco = endereco;
        this.contato = contato;
    }
    public Clinica() {
    }

}
