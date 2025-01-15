package br.com.pixpark.parquimetro.infrastructure;

import br.com.pixpark.parquimetro.domain.model.Bilhete;
import org.springframework.stereotype.Component;

@Component
public interface EstacionamentoInterface {

    default boolean validarPagamento(Bilhete bilhete){
        //envia valor para cliente pagar
        return true;
    }
}
