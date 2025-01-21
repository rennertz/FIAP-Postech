package br.com.pixpark.parquimetro.domain.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "veiculos")
public class Bilhete implements Serializable {

    @Id
    public String id;

    String placa;
    Duration tempo;
    LocalDateTime momentoDaSolicitacao = LocalDateTime.now();
    BigDecimal valorPago;
    String meioDePagamento;

    public long getTempoRestanteMinutos() {
        Duration sinceCreation = Duration
                .between(momentoDaSolicitacao, LocalDateTime.now());
        Duration timeLeft = tempo.minus(sinceCreation);

        long minutesLeft = timeLeft.toMinutes() + 1; // corrige truncamento (floor)

        return minutesLeft < 0 ? 0 : minutesLeft;
    }
}
