package br.com.pixpark.parquimetro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@Document(collection = "Precos")
public class TabelaPrecos {

    public static final IllegalArgumentException TEMPO_DE_PERMANENCIA_INVALIDO = new IllegalArgumentException("Tempo de permanência inválido");
    public static final IllegalArgumentException TEMPO_DE_PERMANENCIA_EXCESSIVO = new IllegalArgumentException("Tempo de permanência excede máximo permitido");
    //    @Id
    public String id;
    SortedMap<Integer, BigDecimal> precos =  new TreeMap<>(Map.of(
            1, new BigDecimal("1.50"),
            2, new BigDecimal("2.25"),
            3, new BigDecimal("3.00"),
            4, new BigDecimal("4.50")
    ));
    LocalDateTime inicioVigencia = LocalDateTime.now();

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
