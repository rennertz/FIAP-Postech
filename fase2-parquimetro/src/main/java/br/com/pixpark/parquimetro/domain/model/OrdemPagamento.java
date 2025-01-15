package br.com.pixpark.parquimetro.domain.model
        ;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "OrdemPagamentos")
public class OrdemPagamento {

    @Id
    public String id;

    Double valor;
    LocalDateTime momentoCriado = LocalDateTime.now();
    LocalDateTime momentoPago;
}
