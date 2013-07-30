package fr.abachar.highlight.antlr;

import fr.abachar.highlight.fragment.Fragment;
import fr.abachar.highlight.fragment.Type;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

public abstract class HighlightLexer extends Lexer {

    /**
     *
     */
    private List<Fragment> fragments = new ArrayList<Fragment>();

    public HighlightLexer() {
        super();
    }

    public HighlightLexer(CharStream input) {
        super(input);
    }

    /**
     *
     */
    public boolean isCaseSensitive() {
        return true;
    }

    protected boolean isIdentifierInList(String text, String[] list) {
        text = isCaseSensitive() ? text : text.toUpperCase();

        for (String item: list) {
            if (item.equals(text)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return
     */
    public List<Fragment> run() {

        String[] tokenNames = getTokenNames();

        while (true) {
            Token token = this.nextToken();
            if (token.getType() == Token.EOF) {
                break;
            }

            String tokenName = tokenNames[token.getType()];
            if ( Type.contains(tokenName)) {
                fragments.add(new Fragment(Type.valueOf(tokenName), token.getText()));
            } else {
                fragments.add(new Fragment(Type.TEXT, token.getText()));
            }
        }

        return fragments;
    }
}
