package br.com.pixpark.parquimetro.infrastructure;

import br.com.pixpark.parquimetro.domain.model.Bilhete;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface BilheteRepository extends MongoRepository<Bilhete, String> {

    Bilhete findByPlaca(String placa);
    ArrayList<Bilhete> findAllByPlaca(String placa);

    default Optional<Bilhete> getUltimoBilhete(String placa) {
        return findFirstByOrderByMomentoDaSolicitacaoDesc(placa);
    }
    Optional<Bilhete> findFirstByOrderByMomentoDaSolicitacaoDesc(String placa);
}
