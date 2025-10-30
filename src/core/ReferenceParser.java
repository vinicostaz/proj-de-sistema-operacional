package core;

import java.util.*;

/**
 * Utilitário para converter uma string de entrada em uma lista de inteiros.
 * Exemplo: "7,0,1,2,0,3,0,4,2,3,0,3,2" → [7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2]
 */
public class ReferenceParser {

    public static List<Integer> parse(String input) {
        List<Integer> out = new ArrayList<>();
        if (input == null) return out;

        String[] tokens = input.trim().split("[,\\s]+");
        for (String tok : tokens) {
            try {
                out.add(Integer.parseInt(tok));
            } catch (NumberFormatException ignored) {
            }
        }
        return out;
    }
}

