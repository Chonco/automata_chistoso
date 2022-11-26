package validation;

public enum Token {
    RESERVED_KEYWORD("Palabra reservada"),
    IDENTIFIER("Identificador"),
    RELATIONAL_OPERATOR("Operador relacional"),
    LOGICAL_OPERATOR("Operador lógico"),
    ARITHMETIC_OPERATOR("Operador aritmético"),
    ASSIGNATION("Asignación"),
    INT_NUMBER("Número entero"),
    DECIMAL_NUMBER("Número decimal"),
    COMMENT("Comentario"),
    PARENTHESIS("Paréntesis"),
    CURLY_PARENTHESIS("Llave");

    private final String tagName;

    Token(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }
}
