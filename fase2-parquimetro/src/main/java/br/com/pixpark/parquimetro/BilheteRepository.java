package br.com.pixpark.parquimetro;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface BilheteRepository extends MongoRepository<Bilhete, String> {

    Bilhete findByPlaca(String placa);
    ArrayList<Bilhete> findAllByPlaca(String placa);
}
