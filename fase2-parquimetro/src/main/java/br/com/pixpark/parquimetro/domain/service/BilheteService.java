package br.com.pixpark.parquimetro.domain.service;

import br.com.pixpark.parquimetro.domain.model.Bilhete;
import br.com.pixpark.parquimetro.infrastructure.BilheteRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class BilheteService {

    private final BilheteRepository repo;
    private final ValueOperations<String, Bilhete> redisValueOps;

    public BilheteService(BilheteRepository repo, RedisOperations<String, Bilhete> cacheOps) {
        this.repo = repo;
        redisValueOps = cacheOps.opsForValue();
    }

    public Bilhete getNovoBilhete(@NotBlank String placa, @NotBlank Duration tempo) {
        Bilhete bilhete = new Bilhete();
        bilhete.setPlaca(placa);
        bilhete.setTempo(tempo);
        Bilhete savedBilhete = repo.save(bilhete);
        redisValueOps.set(placa, savedBilhete, tempo);
        return savedBilhete;
    }

    public Optional<Bilhete> getBilheteBy(String placa) {
        var lista = repo.findAllByPlaca(placa);
        if(lista.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(lista.getLast());
    }

    public Optional<Bilhete> getBilheteCachedBy(String placa) {
        redisValueOps.get(placa);
        return getBilheteBy(placa);
    }
}
