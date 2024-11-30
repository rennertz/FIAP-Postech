package br.com.vetvision.supervisor.domain.model.oferta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Consultor {

    @Id
    private String cpf;
    private String nome;
    private String endereco;
    private String contaBancaria;
    private String contato;  // para notificação

}
