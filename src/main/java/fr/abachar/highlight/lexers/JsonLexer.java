package fr.abachar.highlight.lexers;

import fr.abachar.highlight.Context;
import fr.abachar.highlight.RegexLexer;
import fr.abachar.highlight.StateBuilder;
import fr.abachar.highlight.TokenType;
import fr.abachar.highlight.rules.RuleCallback;

/**
 * @author Abdelhakim bachar
 */
public class JsonLexer extends RegexLexer {

    /**
     * Build lexer rules
     */
    @Override
    protected void initializeRules() {

        addState("root", new StateBuilder()
                .include("value")
        );

        addState("whitespace", new StateBuilder()
                .rule("\\s+", TokenType.Text)
        );

        addState("value", new StateBuilder()
                .include("whitespace")
                .include("simple_value")
                .rule("\\{", TokenType.Punctuation, "#push:object_value")
                .rule("\\[", TokenType.Punctuation, "#push:array_value")
        );

        addState("simple_value", new StateBuilder()
                .rule("true|false|null", TokenType.KeywordConstant)
                .rule("(-?(0|[1-9]\\d*)(\\.\\d+[eE](\\+|-)?\\d+|''[eE](\\+|-)?\\d+|\\.\\d+)')", TokenType.LiteralNumberFloat)
                .rule("-?(0|[1-9]\\d*)", TokenType.LiteralNumberInteger)
                .rule("\"(\\\\\\\\|\\\\\"|[^\"])*\"", TokenType.LiteralStringDouble)
        );

        addState("object_attribute", new StateBuilder()
                .include("value")
                .rule(":", TokenType.Punctuation)
                .rule(",", TokenType.Punctuation, "#pop")
                .rule("\\}", new RuleCallback() {
                    public void execute(Context context) {
                        context.addToken(context.getText(), TokenType.Punctuation);
                        context.popState();
                        context.popState();
                    }
                })
        );

        addState("object_value", new StateBuilder()
                .include("whitespace")
                .rule("\"(\\\\\\\\|\\\\\"|[^\"])*\"", TokenType.NameTag, "#push:object_attribute")
                .rule("\\}", TokenType.Punctuation, "#pop")
        );

        addState("array_value", new StateBuilder()
                .include("whitespace")
                .include("value")
                .rule(",", TokenType.Punctuation)
                .rule("\\]", TokenType.Punctuation, "#pop")
        );
    }
}
