package br.com.pixpark.parquimetro.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "veiculos")
public class Bilhete{

    @Id
    public String id;

    String placa;
    Duration tempo;
    LocalDateTime momentoDaSolicitacao = LocalDateTime.now();
    BigDecimal valorPago;
    String meioDePagamento;
}
