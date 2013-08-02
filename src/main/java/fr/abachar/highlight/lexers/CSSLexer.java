package fr.abachar.highlight.lexers;

import fr.abachar.highlight.Lexer;
import fr.abachar.highlight.Token;

import java.util.regex.Matcher;

/**
 * @author Abdelhakim bachar
 */
public class CSSLexer extends Lexer {

    /**
     * Build lexer rules
     */
    protected void initializeRules() {

        addState("root", new StateCreator() {
            public void create() {
                include("basics");
                rule("{", Token.Punctuation, "stanza");
                rule(":[a-z0-9_-]+", Token.NameDecorator);
                rule(".[a-z0-9_-]+", Token.NameClass);
                rule("#[a-z0-9_-]+", Token.NameFunction);
                rule("@[a-z0-9_-]+", Token.NameFunction, "at_rule");
                rule("[a-z0-9_-]+", Token.NameTag);
                rule("[~^*!%&\\[\\]()<>|+=@:;,./?-]", Token.Operator);
            }
        });

        addState("value", new StateCreator() {
            public void create() {
                include("basics");
                rule("url\\(.*?\\)", Token.LiteralStringOther);
                rule("#[0-9a-f]{1,6}", Token.LiteralNumber);
                rule("-?(?:[0-9]+(\\.[0-9]+)?|\\.[0-9]+)(?:em|px|%|pt|pc|in|mm|m|ex|s)?\\b", Token.LiteralNumber);
                rule("[\\[\\]():\\/.,]", Token.Punctuation);
                rule("\"(\\\\\\\\|\\\\\"|[^\"])*\"", Token.LiteralStringDouble);
                rule("'(\\\\\\\\|\\\\'|[^'])*'", Token.LiteralStringSingle);
                rule("[a-z0-9_-]+", new RuleCallback() {
                    public void execute(Context context) {

                        String identifier = context.getText();
                        if (isConstant(identifier)) {
                            context.addToken(identifier, Token.NameLabel);
                        } else if (isBuiltin(identifier)) {
                            context.addToken(identifier, Token.NameBuiltin);
                        } else {
                            context.addToken(identifier, Token.Name);
                        }
                    }
                });
            }
        });

        addState("at_rule", new StateCreator() {
            public void create() {
                rule("{(?=\\s*[a-z0-9_-]+\\s*:)", Token.Punctuation, "at_stanza");
                rule("{", Token.Punctuation, "at_body");
                rule(";", Token.Punctuation, "pop"); // pop is specific state
                include("value");
            }
        });

        addState("at_body", new StateCreator() {
            public void create() {
                include("at_content");
                include("root");
            }
        });

        addState("at_stanza", new StateCreator() {
            public void create() {
                include("at_content");
                include("stanza");
            }
        });

        addState("at_content", new StateCreator() {
            public void create() {
                rule("}", new RuleCallback() {
                    public void execute(Context context) {
                        context.addToken(context.getText(), Token.Punctuation);
                        context.popState();
                        context.popState();
                    }
                });
            }
        });

        addState("basics", new StateCreator() {
            public void create() {
                rule("\\s+", Token.Text);
                rule("/\\*(?:.*?)\\*/", Token.Comment);
            }
        });

        addState("stanza", new StateCreator() {
            public void create() {
                include("basics");
                rule("}", Token.Punctuation, "pop"); // pop is specific state
                rule("([a-z0-9_-]+)(\\s*)(:)", new RuleCallback() {
                    public void execute(Context context) {
                        Matcher m = context.getMatcher();

                        String identifier = m.group(1);
                        if (isAttribute(identifier) || hasVendorPrefixe(identifier)) {
                            context.addToken(identifier, Token.NameLabel);
                        } else {
                            context.addToken(identifier, Token.NameProperty);
                        }

                        context.addToken(m.group(2), Token.Text);
                        context.addToken(m.group(3), Token.Punctuation);

                        context.pushState("stanza_value");
                    }
                });
            }
        });

        addState("stanza_value", new StateCreator() {
            public void create() {
                rule(";", Token.Punctuation, "pop"); // pop is specific state
                // rule("(?=})", Token.Comment);       rule(/(?=})/) { pop! }
                rule("!important\\b", Token.CommentPreproc);
                rule("^@.*?$", Token.CommentPreproc);
                include("value");
            }
        });
    }

    private boolean isAttribute(String identifier) {
        return false;
    }

    private boolean isConstant(String identifier) {
        return false;
    }

    private boolean isBuiltin(String identifier) {
        return false;
    }

    private boolean hasVendorPrefixe(String identifier) {
        return false;
    }
}
