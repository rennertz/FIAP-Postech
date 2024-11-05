package br.com.vetvision.supervisor.domain.model.solicitacao;

import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;
import br.com.vetvision.supervisor.domain.model.plano.PlanoVeterinario;
import br.com.vetvision.supervisor.domain.model.plano.TipoExame;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
    @NotNull
    private Clinica clinica;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pet_id")
    @NotNull
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_exame")
    @NotNull
    private TipoExame exameSolicitado;

    @ManyToOne
    @JoinColumn(name = "plano_cnpj")
    @NotNull
    private PlanoVeterinario plano;

    @Past
    @NotNull
    private LocalDateTime momentoCriacao;

    @NotNull
    private Boolean estaAtiva;

    @Transient
    private OfertaAtendimento ofertaAtual;

    public Solicitacao() {
    }

    public Solicitacao(@Valid Clinica clinica, @Valid Pet pet, @Valid TipoExame exameSolicitado, @Valid PlanoVeterinario plano) {
        this.clinica = clinica;
        this.pet = pet;
        this.exameSolicitado = exameSolicitado;
        this.plano = plano;
        this.momentoCriacao = LocalDateTime.now();
        this.estaAtiva = Boolean.TRUE;
    }
}
