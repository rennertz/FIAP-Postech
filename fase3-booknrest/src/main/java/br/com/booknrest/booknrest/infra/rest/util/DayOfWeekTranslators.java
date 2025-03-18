package br.com.booknrest.booknrest.infra.rest.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class DayOfWeekTranslators {
    private static final Map<String, DayOfWeek> FROM_PT = Map.of(
            "segunda-feira", DayOfWeek.MONDAY,
            "terça-feira", DayOfWeek.TUESDAY,
            "quarta-feira", DayOfWeek.WEDNESDAY,
            "quinta-feira", DayOfWeek.THURSDAY,
            "sexta-feira", DayOfWeek.FRIDAY,
            "sabado", DayOfWeek.SATURDAY,
            "domingo", DayOfWeek.SUNDAY
    );
    private static final Map<DayOfWeek, String> TO_PT = FROM_PT.entrySet()
            .stream().collect(
                    toMap(Map.Entry::getValue, Map.Entry::getKey));


    private DayOfWeekTranslators() {
        // classe agrupadora, não deve ser instanciada
    }

    public static class Deserializer extends JsonDeserializer<DayOfWeek> {
        @Override
        public DayOfWeek deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
            String nomeDoDia = jsonParser.getText().toLowerCase(); // Ensure lowercase for case-insensitivity
            DayOfWeek dayOfWeek = FROM_PT.get(nomeDoDia);

            if (dayOfWeek == null) {
                throw new IllegalArgumentException("Nome do dia inválido: " + nomeDoDia + ". Valores possíveis: " + FROM_PT.keySet());
            }

            return dayOfWeek;
        }
    }

    public static class Serializer extends JsonSerializer<DayOfWeek> {
        @Override
        public void serialize(DayOfWeek dayOfWeek, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                throws IOException {

            jsonGenerator.writeString(TO_PT.get(dayOfWeek));
        }
    }
}
