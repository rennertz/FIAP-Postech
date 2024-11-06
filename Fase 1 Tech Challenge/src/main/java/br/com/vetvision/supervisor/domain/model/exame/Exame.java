package br.com.vetvision.supervisor.domain.model.exame;

import br.com.vetvision.supervisor.domain.model.oferta.OfertaAtendimento;
import br.com.vetvision.supervisor.domain.model.solicitacao.Solicitacao;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Exame {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "solicitacao_id")
    private Solicitacao solicitacao;

    private LocalDateTime momentoAtendimento;

    @Embedded
    private Laudo laudo;

    public String codigoDeConfirmacao(){
        return this.id;
    }

    public void atender(){
        this.momentoAtendimento = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exame exame = (Exame) o;
        return Objects.equals(id, exame.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
