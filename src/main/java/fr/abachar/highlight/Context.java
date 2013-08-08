package fr.abachar.highlight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

/**
 *
 */
public class Context {

    private static final Logger logger = LoggerFactory.getLogger(Context.class);

    private Long contextId;

    /**
     * Input
     */
    private String input;
    private int position;

    /**
     * Current step
     */
    Matcher matcher;

    /**
     *
     */
    List<Token> tokens = new ArrayList<Token>();

    /**
     *
     */
    LinkedList<String> stateStack = new LinkedList<String>();

    public Context() {
        contextId = (long) (100000l * Math.random());
    }


    public void addToken(String text, TokenType token) {
        if (logger.isInfoEnabled()) {
            logger.info("({}) ADD TOKEN [{}:{}] -> [{}]", contextId, peekState(), token.name(), text.replace("\n", "\\n"));
        }

        if ((text != null) && (text.length() > 0)) {
            tokens.add(new Token(token, text));
        }
    }

    public String peekState() {
        return stateStack.peek();
    }

    public String popState() {
        if (logger.isInfoEnabled()) {
            logger.info("({}) --------------------------------------- POP", contextId);
        }
        return stateStack.pop();
    }

    public void pushState(String state) {
        if (logger.isInfoEnabled()) {
            logger.info("({}) --------------------------------------- PUSH ({})", contextId, state);
        }
        stateStack.push(state);
    }

    public String getText() {
        return matcher.group();
    }

    public Matcher getMatcher() {
        return matcher;
    }

    public String getInput() {
        return input;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
