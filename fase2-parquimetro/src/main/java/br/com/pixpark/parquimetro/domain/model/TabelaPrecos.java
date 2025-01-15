package br.com.pixpark.parquimetro.domain.model;

import java.util.Map;

public class TabelaPrecos {

    private static final Map<Integer, Double> PRECOS = Map.of(
            1, 33.00,
            2, 41.00,
            3, 48.00,
            4, 53.00,
            5, 58.00,
            6, 62.00,
            7, 66.00
    );

    public static Double getPreco(long h){
        int horas = (int) h;
        if (horas <= 7) {
            return PRECOS.getOrDefault(horas, 0.0);
        } else if (horas > 7 && horas <= 23) {
            return PRECOS.get(7) + (horas - 7) * 2.00;
        } else {
            return 100.00; // Valor diário
        }
    }
}
