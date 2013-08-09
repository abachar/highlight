package fr.abachar.highlight.lexers.web;

import fr.abachar.highlight.Context;
import fr.abachar.highlight.RegexLexer;
import fr.abachar.highlight.StateBuilder;
import fr.abachar.highlight.TokenType;
import fr.abachar.highlight.rules.RuleCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Abdelhakim bachar
 */
public class JavascriptLexer extends RegexLexer {

    /**
     *
     */
    private final static String KEYWORDS = "for|in|while|do|break|return|continue|switch|case|default|if|else|throw|try|catch|finally|new|delete|typeof|instanceof|void|yield|this";
    private final static String DECLARATIONS = "var|let|with|function";
    private final static String RESERVED = "abstract|boolean|byte|char|class|const|debugger|double|enum|export|extends|final|float|goto|implements|import|int|interface|long|native|package|private|protected|public|short|static|super|synchronized|throws|transient|volatile";
    private final static String CONSTANTS = "true|false|null|NaN|Infinity|undefined";
    private final static String BUILTINS = "Array|Boolean|Date|Error|Function|Math|netscape|Number|Object|Packages|RegExp|String|sun|decodeURI|decodeURIComponent|encodeURI|encodeURIComponent|Error|eval|isFinite|isNaN|parseFloat|parseInt|document|this|window";


/**
 *
 */
    public JavascriptLexer() {
        this.name = "Javascript";
        this.flags = Pattern.DOTALL;
    }

    /**
     * Build lexer rules
     */
    @Override
    protected void initializeRules() {

        addState("comments-and-whitespace", new StateBuilder()
                .rule("\\s+", TokenType.Text)
                .rule("<!--", TokenType.Comment)
                .rule("//.*?\\n", TokenType.CommentSingle)
                .rule("/\\*.*?\\*/", TokenType.CommentMultiline)
        );

        addState("slash-starts-regex", new StateBuilder()
                .include("comments-and-whitespace")
                .rule("/(\\\\.|[^/\\\\\\n]|\\[(\\\\.|[^\\]\\\\\\n])*\\])+/(?:[gim]+\\b|\\B)", TokenType.LiteralStringRegex, "#pop")
                .rule("(/)", TokenType.Text, "#push:bad-regex")
                .rule("", TokenType.Text, "#pop")
        );

        addState("bad-regex", new StateBuilder()
                .rule("[^\\n]+", TokenType.Error, "#pop")
        );

        addState("root", new StateBuilder()

                .rule("\\A\\s*#!.*?\\n", TokenType.CommentPreproc)
                .rule("(?<=\\n)(?=\\s|/|<!--)", TokenType.Text, "#push:slash-starts-regex")
                .include("comments-and-whitespace")
                .rule("\\+\\+|--|~|&&|\\|\\||\\\\(?=\\n)|<<|>>>?|===|!==", TokenType.Operator, "#push:slash-starts-regex")
                .rule("[-<>\\+\\*%&\\|\\^/!=]=?", TokenType.Operator, "#push:slash-starts-regex")
                .rule("[\\(\\[;,]", TokenType.Punctuation, "#push:slash-starts-regex")
                .rule("[)\\].]", TokenType.Punctuation)
                .rule("[?]", new RuleCallback() {
                    @Override
                    public void execute(Context context) {
                        context.addToken(context.getText(), TokenType.Punctuation);
                        context.pushState("ternary");
                        context.pushState("slash-starts-regex");
                    }
                })
                .rule("[\\{](?=\\s*([$a-zA-Z_][a-zA-Z0-9_]*|\"[^\\n]*?\")\\s*:)", TokenType.Punctuation, "#push:object")
                .rule("[\\{]", new RuleCallback() {
                    @Override
                    public void execute(Context context) {
                        context.addToken(context.getText(), TokenType.Punctuation);
                        context.pushState("block");
                        context.pushState("slash-starts-regex");
                    }
                })
                .rule("[$a-zA-Z_][a-zA-Z0-9_]*", new RuleCallback() {
                    @Override
                    public void execute(Context context) {
                        String identifier = context.getText();
                        if (isIn(identifier, KEYWORDS)) {
                            context.addToken(identifier, TokenType.Keyword);
                            context.pushState("slash-starts-regex");
                        } else if (isIn(identifier, DECLARATIONS)) {
                            context.addToken(identifier, TokenType.KeywordDeclaration);
                            context.pushState("slash-starts-regex");
                        } else if (isIn(identifier, RESERVED)) {
                            context.addToken(identifier, TokenType.KeywordReserved);
                        } else if (isIn(identifier, CONSTANTS)) {
                            context.addToken(identifier, TokenType.KeywordConstant);
                        } else if (isIn(identifier, BUILTINS)) {
                            context.addToken(identifier, TokenType.NameBuiltin);
                        } else {
                            context.addToken(identifier, TokenType.NameOther);
                        }
                    }
                })
                .rule("[0-9][0-9]*\\.[0-9]+([eE][0-9]+)?[fd]?", TokenType.LiteralNumberFloat)
                .rule("0x[0-9a-fA-F]+", TokenType.LiteralNumberHex)
                .rule("[0-9]+", TokenType.LiteralNumberInteger)
                .rule("\"(\\\\\\\\|\\\\\"|[^\"])*\"", TokenType.LiteralStringDouble)
                .rule("'(\\\\\\\\|\\\\'|[^'])*'", TokenType.LiteralStringDouble)
        );

        addState("block", new StateBuilder()
                .rule("([$a-zA-Z_][a-zA-Z0-9_]*)(\\s*)(:)", byGroups(TokenType.NameLabel, TokenType.Text, TokenType.Punctuation))
                .rule("[\\}]", TokenType.Punctuation, "#pop")
                .include("root")
        );

        addState("object", new StateBuilder()
                .rule("[\\}]", TokenType.Punctuation, "#pop")
                .rule("([$a-zA-Z_][a-zA-Z0-9_]*)(\\s*)(:)", byGroups(TokenType.NameLabel, TokenType.Text, TokenType.Punctuation))
                .include("root")
        );

        addState("ternary", new StateBuilder()
                .rule(":", TokenType.Punctuation, "#pop")
        );
    }
}
