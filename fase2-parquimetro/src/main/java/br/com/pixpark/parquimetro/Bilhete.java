package br.com.pixpark.parquimetro;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Bilhete{

    String placa;
    Duration tempo;

    public Double pegarValor(){
        return tempo.toMinutes() * 0.8;
    }

}
