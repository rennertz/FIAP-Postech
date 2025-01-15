package br.com.pixpark.parquimetro.infrastructure;

import br.com.pixpark.parquimetro.domain.model.OrdemPagamento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemPagamentoRepository extends MongoRepository<OrdemPagamento, String> {
}
