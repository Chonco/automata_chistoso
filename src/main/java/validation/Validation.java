package validation;

import java.util.*;

public class Validation {
    private final String[] inputParts;
    private String input;
    private int currentInputIndex = 0;

    private final String[] reservedKeywords = new String[]{"if", "else", "switch", "case", "default",
            "for", "while", "break", "int", "String", "double", "char"};

    public Validation(String input) {
        this.inputParts = separateInput(input);
    }

    private String[] separateInput(String input) {
        List<String> parts = new ArrayList<>();

        int from = 0;
        int to = 0;
        for (; to < input.length(); to++) {
            if (input.charAt(to) == ' ' || input.charAt(to) == '\t' || input.charAt(to) == '\n') {
                String part = input.substring(from, to);
                parts.add(part);

                from = to + 1;
            }
        }

        if (from < to) {
            String part = input.substring(from, to);
            parts.add(part);
        }

        return parts.stream()
                .filter(part -> part.length() != 0)
                .toList()
                .toArray(new String[0]);
    }

    public Map<Token, Integer> validate() {
        int inputIndex = 0;
        final Map<Token, Integer> results = new HashMap<>();

        do {
            input = this.inputParts[inputIndex++];
            currentInputIndex = 0;

            Token tokenResult = switch (input.charAt(currentInputIndex)) {
                case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> firstState();
                case '+', '-' -> thirdState();
                case '*', '%' -> fourthState();
                case '(', ')' -> fifthState();
                case '=' -> sixthState();
                case '&' -> seventhState();
                case '|' -> eighthState();
                case '!' -> tenthState();
                case '<', '>' -> eleventhState();
                case '{', '}' -> thirteenthState();
                case 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                        'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                        'w', 'x', 'y', 'z' -> fourteenthState();
                case '/' -> fifteenthState();
                default -> null;
            };

            if (tokenResult == null) continue;

            if (tokenResult == Token.IDENTIFIER && isReservedKeyword(input)) {
                tokenResult = Token.RESERVED_KEYWORD;
            }

            if (results.containsKey(tokenResult)) {
                results.put(tokenResult, results.get(tokenResult) + 1);
            } else {
                results.put(tokenResult, 1);
            }

        } while (inputIndex < inputParts.length);

        return results;
    }

    private Token firstState() {
        currentInputIndex++;
        try {
            return switch (input.charAt(currentInputIndex)) {
                case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> firstState();
                case '\n', '\t', ' ' -> Token.INT_NUMBER;
                case '.' -> twentyFirstState();
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.INT_NUMBER;
        }
    }

    private Token secondState() {
        currentInputIndex++;
        try {
            return switch (input.charAt(currentInputIndex)) {
                case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> secondState();
                case '\n', '\t', ' ' -> Token.DECIMAL_NUMBER;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.DECIMAL_NUMBER;
        }
    }

    private Token thirdState() {
        currentInputIndex++;
        try {
            return switch (input.charAt(currentInputIndex)) {
                case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> firstState();
                case '\n', '\t', ' ' -> Token.ARITHMETIC_OPERATOR;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.ARITHMETIC_OPERATOR;
        }
    }

    private Token fourthState() {
        currentInputIndex++;
        try {
            return switch (input.charAt(currentInputIndex)) {
                case '\n', '\t', ' ' -> Token.ARITHMETIC_OPERATOR;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.ARITHMETIC_OPERATOR;
        }
    }

    private Token fifthState() {
        currentInputIndex++;
        try {
            return switch (input.charAt(currentInputIndex)) {
                case '\n', '\t', ' ' -> Token.PARENTHESIS;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.PARENTHESIS;
        }
    }

    private Token sixthState() {
        currentInputIndex++;
        try {
            return switch (input.charAt(currentInputIndex)) {
                case '=' -> twentiethState();
                case '\n', '\t', ' ' -> Token.ASSIGNATION;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.ASSIGNATION;
        }
    }

    private Token seventhState() {
        currentInputIndex++;

        try {
            return switch (input.charAt(currentInputIndex)) {
                case '&' -> ninthState();
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.ERROR;
        }
    }

    private Token eighthState() {
        currentInputIndex++;
        try {
            return switch (input.charAt(currentInputIndex)) {
                case '|' -> ninthState();
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.ERROR;
        }
    }

    private Token ninthState() {
        currentInputIndex++;
        try {
            return switch (input.charAt(currentInputIndex)) {
                case '\n', '\t', ' ' -> Token.LOGICAL_OPERATOR;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.LOGICAL_OPERATOR;
        }
    }

    private Token tenthState() {
        currentInputIndex++;
        try {
            return switch (input.charAt(currentInputIndex)) {
                case '=' -> twelfthState();
                case '\n', '\t', ' ' -> Token.LOGICAL_OPERATOR;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.LOGICAL_OPERATOR;
        }
    }

    private Token eleventhState() {
        currentInputIndex++;
        try {
            return switch (input.charAt(currentInputIndex)) {
                case '=' -> twelfthState();
                case '\n', '\t', ' ' -> Token.RELATIONAL_OPERATOR;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.RELATIONAL_OPERATOR;
        }
    }

    private Token twelfthState() {
        currentInputIndex++;
        try {
            return switch (input.charAt(currentInputIndex)) {
                case '\n', '\t', ' ' -> Token.RELATIONAL_OPERATOR;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.RELATIONAL_OPERATOR;
        }
    }

    private Token thirteenthState() {
        currentInputIndex++;
        try {
            return switch (input.charAt(currentInputIndex)) {
                case '\n', '\t', ' ' -> Token.CURLY_PARENTHESIS;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.CURLY_PARENTHESIS;
        }
    }

    private Token fourteenthState() {
        currentInputIndex++;
        try {
            return switch (input.charAt(currentInputIndex)) {
                case 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                        'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                        'w', 'x', 'y', 'z', '_', '1', '2', '3', '4', '5', '6',
                        '7', '8', '9', '0' -> fourteenthState();
                case '\n', '\t', ' ' -> Token.IDENTIFIER;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.IDENTIFIER;
        }
    }

    private Token fifteenthState() {
        currentInputIndex++;
        try {
            return switch (input.charAt(currentInputIndex)) {
                case '*' -> sixteenthState();
                case '\n', '\t', ' ' -> Token.ARITHMETIC_OPERATOR;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.ARITHMETIC_OPERATOR;
        }
    }

    private Token sixteenthState() {
        currentInputIndex++;
        try {
            return switch (input.charAt(currentInputIndex)) {
                case '*' -> eighteenthState();
                case '\n', '\t', ' ' -> Token.ERROR;
                default -> sixteenthState();
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.ERROR;
        }
    }

    private Token eighteenthState() {
        currentInputIndex++;
        try {
            return switch (input.charAt(currentInputIndex)) {
                case '/' -> nineteenthState();
                case '*' -> eighteenthState();
                case '\n', '\t', ' ' -> Token.ERROR;
                default -> sixteenthState();
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.ERROR;
        }
    }

    private Token nineteenthState() {
        currentInputIndex++;
        try {
            return switch (input.charAt(currentInputIndex)) {
                case '\n', '\t', ' ' -> Token.COMMENT;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.COMMENT;
        }
    }

    private Token twentiethState() {
        currentInputIndex++;
        try {
            return switch (input.charAt(currentInputIndex)) {
                case '\n', '\t', ' ' -> Token.RELATIONAL_OPERATOR;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.RELATIONAL_OPERATOR;
        }
    }

    private Token twentyFirstState() {
        currentInputIndex++;
        try {
            return switch (input.charAt(currentInputIndex)) {
                case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> secondState();
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.ERROR;
        }
    }

    private boolean isReservedKeyword(String toValidate) {
        return Arrays.asList(reservedKeywords)
                .contains(toValidate);
    }

}
