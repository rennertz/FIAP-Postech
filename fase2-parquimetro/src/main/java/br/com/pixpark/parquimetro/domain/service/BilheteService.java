package br.com.pixpark.parquimetro.domain.service;

import br.com.pixpark.parquimetro.domain.model.Bilhete;
import br.com.pixpark.parquimetro.infrastructure.BilheteRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Optional;

@Slf4j
@Service
public class BilheteService {

    private final BilheteRepository repo;
    private final TabelaPrecosService precos;
    private final ValueOperations<String, Bilhete> redisValueOps;

    public BilheteService(BilheteRepository repo, TabelaPrecosService precos, RedisOperations<String, Bilhete> cacheOps) {
        this.repo = repo;
        this.precos = precos;
        redisValueOps = cacheOps.opsForValue();
    }

    public Bilhete getNovoBilhete(@NotBlank String placa, @NotBlank Duration tempo) {
        Bilhete novoBilhete = makeNovoBilhete(placa, tempo);

        Bilhete savedBilhete = repo.save(novoBilhete);
        redisValueOps.set(placa, savedBilhete, tempo);
        log.info("Novo bilhete: {}", savedBilhete);
        return savedBilhete;
    }

    private Bilhete makeNovoBilhete(String placa, Duration tempo) {
        Bilhete bilhete = new Bilhete();
        bilhete.setPlaca(placa);
        bilhete.setTempo(tempo);

        BigDecimal valor = precos.getValorBy(tempo);
        bilhete.setValorPago(valor);
        bilhete.setMeioDePagamento("pix");
        return bilhete;
    }

    public Optional<Bilhete> getBilheteBy(String placa) {
        return repo.getUltimoBilhete(placa);
    }

    public Optional<Bilhete> getBilheteCachedBy(String placa) {
        return Optional
                .ofNullable(redisValueOps.get(placa))
                .or(() -> getBilheteBy(placa));
    }
}
