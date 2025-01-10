package br.com.pixpark.parquimetro;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "Veiculos")
public class Bilhete{

    @Id
    public String id;

    String placa;
    Duration tempo;
    LocalDateTime momentoDaSolicitacao = LocalDateTime.now();

    public Double pegarValor(){
       return TabelaPrecos.getPreco(tempo.toHours());
    }

}
