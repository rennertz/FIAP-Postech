package br.com.pixpark.parquimetro;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;

//@Service
public class TabelaPrecosService {

    private TabelaPrecos repo;

    public TabelaPrecosService(TabelaPrecos repo){
        this.repo = repo;
    }

    public BigDecimal pegarValor(Duration tempo){
        long hours = tempo.toHours();
        return repo.getPreco((int) hours);
    }
}
