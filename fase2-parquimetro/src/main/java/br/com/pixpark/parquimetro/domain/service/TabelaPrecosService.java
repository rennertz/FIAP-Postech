package br.com.pixpark.parquimetro.domain.service;

import br.com.pixpark.parquimetro.domain.model.TabelaPrecos;
import br.com.pixpark.parquimetro.infrastructure.TabelaPrecosMemoryCache;
import br.com.pixpark.parquimetro.infrastructure.TabelaPrecosRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
public class TabelaPrecosService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TabelaPrecosService.class);

    private final TabelaPrecosRepository repo;
    private final TabelaPrecosMemoryCache memoryCache;

    public TabelaPrecosService(TabelaPrecosRepository repo, TabelaPrecosMemoryCache memoryCache) {
        this.repo = repo;
        this.memoryCache = memoryCache;
    }

    public TabelaPrecos saveTable(TabelaPrecos table) {
        TabelaPrecos saved = repo.save(table);
        memoryCache.set(saved);
        log.info("Tabela de precos salva em DB e em memória: {}", saved);
        return saved;
    }

    public List<TabelaPrecos> getHistoricoValores() {
        return repo.findAll();
    }

    public TabelaPrecos getValoresVigentes() {
        TabelaPrecos tabelaPrecos = repo.findFirstByOrderByInicioVigenciaDesc()
                .orElseThrow(() -> new RuntimeException("Não foi cadastrada tabela de valores"));

        log.debug("Tabela de precos obtida de DB: {}", tabelaPrecos);
        return tabelaPrecos;
    }

    public TabelaPrecos getValoresVigentesCached() {
        Optional<TabelaPrecos> cached = memoryCache.getLast();
        if (cached.isPresent()) {
            log.debug("Tabela de precos obtida em memória: {}", cached.get());
            return cached.get();

        } else {
            TabelaPrecos valoresVigentes = getValoresVigentes();
            memoryCache.set(valoresVigentes);
            return valoresVigentes;
        }
    }

    public BigDecimal pegarValor(Duration tempo){
        long hours = tempo.toHours();
        return repo.findFirstByOrderByInicioVigenciaDesc()
                .map(precos -> precos.getPreco((int) hours))
                .orElseThrow(() -> new RuntimeException("Erro ao encontrar valores do rotativo"));
    }
}
