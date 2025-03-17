package br.com.booknrest.booknrest.infra.rest.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;

public class DayOfWeekTranslators {
    public static class Deserializer extends JsonDeserializer<DayOfWeek> {

        private static final Map<String, DayOfWeek> DAY_NAMES_PT = Map.of(
                "segunda-feira", DayOfWeek.MONDAY,
                "terça-feira", DayOfWeek.TUESDAY,
                "quarta-feira", DayOfWeek.WEDNESDAY,
                "quinta-feira", DayOfWeek.THURSDAY,
                "sexta-feira", DayOfWeek.FRIDAY,
                "sabado", DayOfWeek.SATURDAY,
                "domingo", DayOfWeek.SUNDAY
        );

        @Override
        public DayOfWeek deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
            String dayName = jsonParser.getText().toLowerCase(); // Ensure lowercase for case-insensitivity
            DayOfWeek dayOfWeek = DAY_NAMES_PT.get(dayName);

            if (dayOfWeek == null) {
                throw new IllegalArgumentException("Invalid day name: " + dayName);
            }

            return dayOfWeek;
        }
    }

    public static class Serializer extends JsonSerializer<DayOfWeek> {
        private static final Locale LOCALE_PT = Locale.of("pt", "BR");

        @Override
        public void serialize(DayOfWeek dayOfWeek, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                throws IOException {
            String translatedDay = dayOfWeek.getDisplayName(TextStyle.FULL, LOCALE_PT);
            jsonGenerator.writeString(translatedDay);
        }
    }
}
