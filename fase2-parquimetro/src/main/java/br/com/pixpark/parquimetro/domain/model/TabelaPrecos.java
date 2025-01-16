package br.com.pixpark.parquimetro.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "tabela_precos")
public class TabelaPrecos {

    public static final IllegalArgumentException TEMPO_DE_PERMANENCIA_INVALIDO = new IllegalArgumentException("Tempo de permanência inválido");
    public static final IllegalArgumentException TEMPO_DE_PERMANENCIA_EXCESSIVO = new IllegalArgumentException("Tempo de permanência excede máximo permitido");

    @Id
    public String id;
    Instant inicioVigencia = Instant.now();
    SortedMap<Integer, BigDecimal> precos;

    public TabelaPrecos(Map<Integer, BigDecimal> precos) {
        this.precos = new TreeMap<>(precos);
    }

    public BigDecimal getPreco(int horas){
        Integer permanenciaMinima = precos.firstKey();
        Integer permanenciaMaxima = precos.lastKey();

        if (horas < 0) {
            throw TEMPO_DE_PERMANENCIA_INVALIDO;
        }
        if (horas > permanenciaMaxima) {
            throw TEMPO_DE_PERMANENCIA_EXCESSIVO;
        }

        if (horas <= permanenciaMinima) {
            return precos.get(permanenciaMinima);
        } else {
            return tentaObterValor(horas);}

    }

    private BigDecimal tentaObterValor(Integer horas) {
        return Optional.ofNullable(precos.get(horas))
                .orElseThrow(() -> TEMPO_DE_PERMANENCIA_INVALIDO);

    }
}
