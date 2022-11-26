package validation;

import java.util.Arrays;
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

    private final String[] reservedKeywords = new String[]{"if", "else", "switch", "case", "default",
            "for", "while", "break", "int", "String", "double", "char"};

    public Map<Token, Integer> validate() {
        index = 0;
        final Map<Token, Integer> results = new HashMap<>();

        do {
            int from = index;
            Token tokenResult = switch (input.charAt(index)) {
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
                default -> Token.ERROR;
            };

            if (tokenResult == Token.IDENTIFIER && isReservedKeyword(from, index)) {
                tokenResult = Token.RESERVED_KEYWORD;
            }

            if (results.containsKey(tokenResult)) {
                results.put(tokenResult, results.get(tokenResult) + 1);
            } else {
                results.put(tokenResult, 1);
            }

        } while (index < input.length());

        return results;
    }

    private Token firstState() {
        index++;
        try {
            return switch (input.charAt(index)) {
                case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> firstState();
                case '\n', '\t', ' ' -> Token.INT_NUMBER;
                case '.' -> twentyFirstState();
                default -> Token.ERROR;
            };
        } catch (ArrayIndexOutOfBoundsException exception) {
            return Token.INT_NUMBER;
        }
    }

    private Token secondState() {
        index++;
        try {
            return switch (input.charAt(index)) {
                case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> secondState();
                case '\n', '\t', ' ' -> Token.DECIMAL_NUMBER;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.DECIMAL_NUMBER;
        }
    }

    private Token thirdState() {
        index++;
        try {
            return switch (input.charAt(index)) {
                case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> firstState();
                case '\n', '\t', ' ' -> Token.ARITHMETIC_OPERATOR;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.ARITHMETIC_OPERATOR;
        }
    }

    private Token fourthState() {
        index++;
        try {
            return switch (input.charAt(index)) {
                case '\n', '\t', ' ' -> Token.ARITHMETIC_OPERATOR;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.ARITHMETIC_OPERATOR;
        }
    }

    private Token fifthState() {
        index++;
        try {
            return switch (input.charAt(index)) {
                case '\n', '\t', ' ' -> Token.PARENTHESIS;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.PARENTHESIS;
        }
    }

    private Token sixthState() {
        index++;
        try {
            return switch (input.charAt(index)) {
                case '=' -> twentiethState();
                case '\n', '\t', ' ' -> Token.ASSIGNATION;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.ASSIGNATION;
        }
    }

    private Token seventhState() {
        index++;

        try {
            return switch (input.charAt(index)) {
                case '&' -> ninthState();
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.ERROR;
        }
    }

    private Token eighthState() {
        index++;
        try {
            return switch (input.charAt(index)) {
                case '|' -> ninthState();
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.ERROR;
        }
    }

    private Token ninthState() {
        index++;
        try {
            return switch (input.charAt(index)) {
                case '\n', '\t', ' ' -> Token.LOGICAL_OPERATOR;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.LOGICAL_OPERATOR;
        }
    }

    private Token tenthState() {
        index++;
        try {
            return switch (input.charAt(index)) {
                case '=' -> twelfthState();
                case '\n', '\t', ' ' -> Token.LOGICAL_OPERATOR;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.LOGICAL_OPERATOR;
        }
    }

    private Token eleventhState() {
        index++;
        try {
            return switch (input.charAt(index)) {
                case '=' -> twelfthState();
                case '\n', '\t', ' ' -> Token.RELATIONAL_OPERATOR;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.RELATIONAL_OPERATOR;
        }
    }

    private Token twelfthState() {
        index++;
        try {
            return switch (input.charAt(index)) {
                case '\n', '\t', ' ' -> Token.RELATIONAL_OPERATOR;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.RELATIONAL_OPERATOR;
        }
    }

    private Token thirteenthState() {
        index++;
        try {
            return switch (input.charAt(index)) {
                case '\n', '\t', ' ' -> Token.CURLY_PARENTHESIS;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.CURLY_PARENTHESIS;
        }
    }

    private Token fourteenthState() {
        index++;
        try {
            return switch (input.charAt(index)) {
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
        index++;
        try {
            return switch (input.charAt(index)) {
                case '*' -> sixteenthState();
                case '\n', '\t', ' ' -> Token.ARITHMETIC_OPERATOR;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.ARITHMETIC_OPERATOR;
        }
    }

    private Token sixteenthState() {
        index++;
        try {
            return switch (input.charAt(index)) {
                case '*' -> eighteenthState();
                case '\n', '\t', ' ' -> Token.ERROR;
                default -> sixteenthState();
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.ERROR;
        }
    }

    private Token eighteenthState() {
        index++;
        try {
            return switch (input.charAt(index)) {
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
        index++;
        try {
            return switch (input.charAt(index)) {
                case '\n', '\t', ' ' -> Token.COMMENT;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.COMMENT;
        }
    }

    private Token twentiethState() {
        index++;
        try {
            return switch (input.charAt(index)) {
                case '\n', '\t', ' ' -> Token.RELATIONAL_OPERATOR;
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.RELATIONAL_OPERATOR;
        }
    }

    private Token twentyFirstState() {
        index++;
        try {
            return switch (input.charAt(index)) {
                case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> secondState();
                default -> Token.ERROR;
            };
        } catch (IndexOutOfBoundsException exception) {
            return Token.ERROR;
        }
    }

    private boolean isReservedKeyword(int from, int to) {
        String toValidate = input.substring(from, to);
        return Arrays.asList(reservedKeywords)
                .contains(toValidate);
    }

}
