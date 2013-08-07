package fr.abachar.highlight;

import fr.abachar.highlight.rules.RuleCallback;

import java.util.List;
import java.util.regex.Matcher;

/**
 * Created with IntelliJ IDEA.
 * User: abachar
 * Date: 06/08/13
 * Time: 17:03
 * To change this template use File | Settings | File Templates.
 */
public abstract class RegexLexer extends Lexer {

    protected RuleCallback byGroups(final TokenType token1, final TokenType token2) {

        return new RuleCallback() {
            public void execute(Context context) {
                Matcher m = context.getMatcher();
                context.addToken(m.group(1), token1);
                context.addToken(m.group(2), token2);
            }
        };
    }

    protected void delegate(Context context, Lexer lexer, String input) {
        List<Token> tokens = lexer.getTokens(input);
        context.getTokens().addAll(tokens);
    }
}
