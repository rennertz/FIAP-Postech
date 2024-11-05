package br.com.vetvision.supervisor.domain.model.solicitacao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CNPJ;

@Getter
@Entity
public class Clinica {

    @Id
    @CNPJ
    @NotBlank
    private String cnpj;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @Pattern(regexp = "\\d\\d \\d{5}-\\d{4}", message = "telefone no formato 'XX XXXXX-XXXX'")
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
