package br.com.pixpark.parquimetro.infrastructure;

import br.com.pixpark.parquimetro.domain.model.TabelaPrecos;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TabelaPrecosRepository extends MongoRepository<TabelaPrecos, String> {

    Optional<TabelaPrecos> findFirstByOrderByInicioVigenciaDesc();
}
