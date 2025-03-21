package br.com.booknrest.booknrest.util;

import br.com.booknrest.booknrest.infra.rest.RestauranteDTO;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

public class RestauranteHelperFactory {

    public static RestauranteDTO getRestauranteDtoNomeAleatorio() {
        var list = List.of(getHorarioDeFuncionamento());

        return new RestauranteDTO(null, RandomNameGenerator.generateName(6),
                "Mooca", "frutos do mar", list, 160);
    }

    private static RestauranteDTO.HorarioDeFuncionamentoDTO getHorarioDeFuncionamento() {
        return new RestauranteDTO.HorarioDeFuncionamentoDTO(
                null, DayOfWeek.MONDAY, LocalTime.of(11, 0), LocalTime.of(14, 0));
    }

    public static class RandomNameGenerator {
        private static final String CONSONANTS = "bcdfghjklmnpqrstvwxyz";
        private static final String VOWELS = "aeiou";

        private static final Random random = new Random();

        public static String generateName(int length) {
            StringBuilder name = new StringBuilder();

            boolean startWithVowel = random.nextBoolean(); // Randomly start with vowel or consonant

            for (int i = 0; i < length; i++) {
                if ((i % 2 == 0 && startWithVowel) || (i % 2 == 1 && !startWithVowel)) {
                    name.append(randomChar(VOWELS));
                } else {
                    name.append(randomChar(CONSONANTS));
                }
            }

            return capitalize(name.toString()); // Capitalize first letter
        }

        private static char randomChar(String letters) {
            return letters.charAt(random.nextInt(letters.length()));
        }

        private static String capitalize(String word) {
            return Character.toUpperCase(word.charAt(0)) + word.substring(1);
        }
    }
}
