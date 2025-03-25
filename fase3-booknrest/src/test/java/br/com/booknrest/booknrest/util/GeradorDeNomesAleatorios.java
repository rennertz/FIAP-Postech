package br.com.booknrest.booknrest.util;

import java.util.Random;

public class GeradorDeNomesAleatorios {
    private static final String CONSOANTES = "bcdfghjklmnpqrstvwxyz";
    private static final String VOGAIS = "aeiou";

    private static final Random random = new Random();

    public static String geraNome(int length) {
        StringBuilder name = new StringBuilder();

        boolean startWithVowel = random.nextBoolean(); // Randomly start with vowel or consonant

        for (int i = 0; i < length; i++) {
            if ((i % 2 == 0 && startWithVowel) || (i % 2 == 1 && !startWithVowel)) {
                name.append(randomChar(VOGAIS));
            } else {
                name.append(randomChar(CONSOANTES));
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
