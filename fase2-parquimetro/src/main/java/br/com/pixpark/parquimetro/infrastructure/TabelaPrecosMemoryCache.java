package br.com.pixpark.parquimetro.infrastructure;

import br.com.pixpark.parquimetro.domain.model.TabelaPrecos;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TabelaPrecosMemoryCache {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TabelaPrecosMemoryCache.class);

    private Optional<TabelaPrecos> precosVigentesEmMemoria = Optional.empty();

    public void set(TabelaPrecos table) {
        precosVigentesEmMemoria = Optional.of(table);
        log.info("Tabela de precos salva em memória: {}", table);
    }

    public Optional<TabelaPrecos> getLast() {
        return precosVigentesEmMemoria;
    }
}
