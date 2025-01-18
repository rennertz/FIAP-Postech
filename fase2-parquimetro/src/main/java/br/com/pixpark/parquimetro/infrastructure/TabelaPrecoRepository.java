package br.com.pixpark.parquimetro.infrastructure;

import br.com.pixpark.parquimetro.domain.model.TabelaPrecos;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TabelaPrecoRepository {

    static TabelaPrecos tabelaPreco = new TabelaPrecos();

    default List<TabelaPrecos> findAll(){

        List<TabelaPrecos> lista = List.of(tabelaPreco);
        return lista;
    }
}
