package br.com.vetvision.supervisor.domain.model.solicitacao;

import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;
import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinario;
import br.com.vetvision.supervisor.domain.model.plano.TipoExame;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(name = "OfertaAtivaComMesmoPetETipoExame", columnNames = { "pet_id", "tipo_exame", "estaAtiva" }) })
public class Solicitacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "clinica_cnpj")
    private Clinica clinica;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_exame")
    private TipoExame exameSolicitado;

    @ManyToOne
    @JoinColumn(name = "plano_cnpj")
    private PlanoVeterinario plano;

    private LocalDateTime momentoCriacao;

    private Boolean estaAtiva;

    @Transient
    private OfertaAtendimento ofertaAtual;

    public Solicitacao() {
    }

    public Solicitacao(Clinica clinica, Pet pet, TipoExame exameSolicitado, PlanoVeterinario plano) {
        this.clinica = clinica;
        this.pet = pet;
        this.exameSolicitado = exameSolicitado;
        this.plano = plano;
        this.momentoCriacao = LocalDateTime.now();
        this.estaAtiva = Boolean.TRUE;
    }
}
