package validation;

public class ValidationResult {
    private final Token token;
    private int value;

    public ValidationResult(Token token) {
        this.token = token;
        this.value = 0;
    }

    public void incrementValue() {
        incrementValue(1);
    }

    public void incrementValue(int value) {
        this.value += value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Token getToken() {
        return token;
    }

    public int getValue() {
        return value;
    }
}
