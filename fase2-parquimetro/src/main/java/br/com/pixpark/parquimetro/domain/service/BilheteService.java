package br.com.pixpark.parquimetro.domain.service;

import br.com.pixpark.parquimetro.aplication.ParquimetroController;
import br.com.pixpark.parquimetro.domain.model.Bilhete;
import br.com.pixpark.parquimetro.infrastructure.BilheteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BilheteService {

    private final BilheteRepository repo;

    public BilheteService(BilheteRepository repo) {
        this.repo = repo;
    }

    public Bilhete getNovoBilhete(ParquimetroController.DTOGerarBilheteRequest req) {
        Bilhete bilhete = new Bilhete();
        bilhete.setPlaca(req.placa());
        bilhete.setTempo(req.tempo());
        return repo.save(bilhete);
    }

    public Optional<Bilhete> getBilheteBy(String placa) {
        var lista = repo.findAllByPlaca(placa);
        if(lista.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(lista.getLast());
    }
}
