package fr.abachar.highlight;

/**
 *
 */
public class Token {

    private int pos;

    private TokenType tokenType;

    private String value;

    public Token(int pos, TokenType tokenType, String value) {
        this.pos = pos;
        this.tokenType = tokenType;
        this.value = value;
    }

    public int getPos() {
        return pos;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getValue() {
        return value;
    }
}
