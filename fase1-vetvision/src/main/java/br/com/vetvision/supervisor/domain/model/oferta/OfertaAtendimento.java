package br.com.vetvision.supervisor.domain.model.oferta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OfertaAtendimento {

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "consultor-cpf")
    private Consultor consultor;

    private Duration prazoParaAtendimento = Duration.ZERO;

    private LocalDateTime foiAceita;

    public void aceitarOferta(){
        this.foiAceita = LocalDateTime.now();
    }
}
