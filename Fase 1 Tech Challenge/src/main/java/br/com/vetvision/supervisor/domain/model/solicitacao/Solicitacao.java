package br.com.vetvision.supervisor.domain.model.solicitacao;

import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;
import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinario;
import br.com.vetvision.supervisor.domain.model.plano.TipoExame;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity(name = "Solicitacao")
@Table(name = "Solicitacao")
public class Solicitacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "clinica_cnpj")
    private Clinica clinica;

    @Embedded
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_exame")
    private TipoExame exameSolicitado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_cnpj")
    private PlanoVeterinario plano;

    @Column
    private LocalDateTime momentoCriacao;

    @Transient
    private OfertaAtendimento ofertaAtual;

    public Solicitacao(Clinica clinica, Pet pet, TipoExame exameSolicitado, PlanoVeterinario plano) {
        this.clinica = clinica;
        this.pet = pet;
        this.exameSolicitado = exameSolicitado;
        this.plano = plano;
        this.momentoCriacao = LocalDateTime.now();
    }
}
