package br.com.pixpark.parquimetro;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Service
public class TabelaPrecosService {

    private final TabelaPrecosRepository repo;

    public TabelaPrecosService(TabelaPrecosRepository repo){
        this.repo = repo;
    }

    public TabelaPrecos saveTable(TabelaPrecos table) {
        return repo.save(table);
    }

    public List<TabelaPrecos> getHistoricoValores() {
        return repo.findAll();
    }

    public TabelaPrecos getValoresVigentes() {
        return repo.findFirstByOrderByInicioVigenciaDesc()
                .orElseThrow(() -> new RuntimeException("Não foi cadastrada tabela de valores"));
    }

    public BigDecimal pegarValor(Duration tempo){
        long hours = tempo.toHours();
        return repo.findFirstByOrderByInicioVigenciaDesc()
                .map(precos -> precos.getPreco((int) hours))
                .orElseThrow(() -> new RuntimeException("Erro ao encontrar valores do rotativo"));
    }
}
