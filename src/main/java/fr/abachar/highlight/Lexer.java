package fr.abachar.highlight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author abachar
 */
public abstract class Lexer {

    /**
     *
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *
     */
    protected String name;

    /**
     *
     */
    public Lexer() {
    }

    /**
     *
     * @param input
     * @return
     */
    public List<Token> getTokens(String input) {

        // Clean input
        input = input.replace("\r\n", "\n").replace("\r", "\n").replace("\t", "    ");

        // One text block
        List<Token> tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.Text, input));
        return tokens;
    }
}
