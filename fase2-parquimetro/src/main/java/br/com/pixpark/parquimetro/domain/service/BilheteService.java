package br.com.pixpark.parquimetro.domain.service;

import br.com.pixpark.parquimetro.domain.model.Bilhete;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class BilheteService {

    public void validarTempoBilhete(Bilhete bilhete){
        
    }

    public long tempoEstacionado(Bilhete bilhete){
        var entrada = bilhete.getMomentoDaSolicitacao();
        var saida = LocalDateTime.now();
        var tempoEstacionado = Duration.between(entrada, saida).toMinutes();
        return tempoEstacionado;
    }
}
