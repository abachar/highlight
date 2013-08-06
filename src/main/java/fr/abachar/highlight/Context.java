package fr.abachar.highlight;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

/**
 *
 */
public class Context {

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


    public void addToken(String text, TokenType token) {
        System.out.printf("ADD TOKEN [%s:%s] -> [%s]\n", peekState(), token.name(), text.replace("\n", "\\n"));
        tokens.add(new Token(0, token, text));
    }

    public String peekState() {
        return stateStack.peek();
    }

    public String popState() {
        System.out.print("--------------------------------------- POP");
        return stateStack.pop();
    }

    public void pushState(String state) {
        System.out.print("--------------------------------------- PUSH (" + state +")");
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
