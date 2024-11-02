package br.com.vetvision.supervisor.domain.model.solicitacao;

import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;
import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "Solicitacao")
@Table(name = "Solicitacao")
public class Solicitacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "clinica_cnpj")
    private Clinica clinica;

    @Embedded
    private Pet pet;

    // TODO: remover cascade e salvar com servico proprio
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "plano_cnpj")
    private PlanoVeterinario plano;

    @Getter
    @Column
    private LocalDateTime momentoCriacao;

    @Getter
    @Transient
    private OfertaAtendimento ofertaAtual;

    public Solicitacao(Clinica clinica, Pet pet, PlanoVeterinario plano) {
        this.clinica = clinica;
        this.pet = pet;
        this.plano = plano;
    }

    public int getId() {
        return id;
    }

}
