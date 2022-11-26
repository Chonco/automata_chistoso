package validation;

import java.util.HashMap;
import java.util.Map;

public class Validation {
    private String input;
    private int index = 0;

    public Validation(String input) {
        this.input = input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Map<Token, Integer> validate() {
        index = 0;
        final Map<Token, Integer> results = new HashMap<>();

        while (true) {
            final Token tokenResult = switch (input.charAt(index)) {
                case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> firstState();

            };

            if (results.containsKey(tokenResult)) {
                results.put(tokenResult, results.get(tokenResult) + 1);
            } else {
                results.put(tokenResult, 1);
            }

            if (index >= input.length()) {
                break;
            }
        }

        return results;
    }

    private Token firstState() {
        index++;
        return switch (input.charAt(index)) {
            case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> firstState();
            case '\n', '\t', ' ' -> Token.INT_NUMBER;
            case '.' -> twentyFirstState();
            default -> Token.ERROR;
        };
    }

    private Token secondState() {
        index++;
        return switch (input.charAt(index)) {
            case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> secondState();
            case '\n', '\t', ' ' -> Token.DECIMAL_NUMBER;
            default -> Token.ERROR;
        };
    }

    private Token twentyFirstState() {
        index++;
        return switch (input.charAt(index)) {
            case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> secondState();
            default -> Token.ERROR;
        };
    }

}
