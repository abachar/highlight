package fr.abachar.highlight.lexers.web;

import fr.abachar.highlight.Context;
import fr.abachar.highlight.RegexLexer;
import fr.abachar.highlight.StateBuilder;
import fr.abachar.highlight.TokenType;
import fr.abachar.highlight.rules.RuleCallback;

import java.util.regex.Pattern;

/**
 * @author Abdelhakim bachar
 */
public class JavascriptLexer extends RegexLexer {

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
                .rule("/\\*.*?\\* /", TokenType.CommentMultiline)
        );

        addState("slash-starts-regex", new StateBuilder()
                .include("comments-and-whitespace")
                .rule("/(\\\\.|[^[/\\\\\\n]|\\[(\\\\.|[^\\]\\\\\\n])*])+/([gim]+\\b|\\B)", TokenType.LiteralStringRegex, "#pop")
                .rule("(?=/)", new RuleCallback() {
                    @Override
                    public void execute(Context context) {
                        context.addToken(context.getText(), TokenType.Text);
                        context.popState();
                        context.pushState("bad-regex");
                    }
                })
                .rule("", TokenType.Text, "#pop")
        );

        addState("bad-regex", new StateBuilder()
                .rule("[^\\n]+", TokenType.Error, "#pop")
        );

        addState("root", new StateBuilder()
                .rule("^(?=\\s|/|<!--)", TokenType.Text, "#push:slash-starts-regex")
                .include("comments-and-whitespace")
                .rule("\\+\\+|--|~|&&|\\?|:|\\|\\||\\\\(?=\\n)|(<<|>>>?|==?|!=?|[-<>+*%&\\|\\^/])=?", TokenType.Operator, "#push:slash-starts-regex")
                .rule("[\\{\\(\\[;,]", TokenType.Punctuation, "#push:slash-starts-regex")
                .rule("[\\}\\)\\].]", TokenType.Punctuation)
                .rule("(for|in|while|do|break|return|continue|switch|case|default|if|else|throw|try|catch|finally|new|delete|typeof|instanceof|void|yield|this)\\b", TokenType.Keyword, "#push:slash-starts-regex")
                .rule("(var|let|with|function)\\b", TokenType.KeywordDeclaration, "#push:slash-starts-regex")
                .rule("(abstract|boolean|byte|char|class|const|debugger|double|enum|export|extends|final|float|goto|implements|import|int|interface|long|native|package|private|protected|public|short|static|super|synchronized|throws|transient|volatile)\\b", TokenType.KeywordReserved)
                .rule("(true|false|null|NaN|Infinity|undefined)\\b", TokenType.KeywordConstant)
                .rule("(Array|Boolean|Date|Error|Function|Math|netscape|Number|Object|Packages|RegExp|String|sun|decodeURI|decodeURIComponent|encodeURI|encodeURIComponent|Error|eval|isFinite|isNaN|parseFloat|parseInt|document|this|window)\\b", TokenType.NameBuiltin)
                .rule("[$a-zA-Z_][a-zA-Z0-9_]*", TokenType.NameOther)
                .rule("[0-9][0-9]*\\.[0-9]+([eE][0-9]+)?[fd]?", TokenType.LiteralNumberFloat)
                .rule("0x[0-9a-fA-F]+", TokenType.LiteralNumberHex)
                .rule("[0-9]+", TokenType.LiteralNumberInteger)
                .rule("\"(\\\\\\\\|\\\\\"|[^\"])*\"", TokenType.LiteralStringDouble)
                .rule("'(\\\\\\\\|\\\\'|[^'])*'", TokenType.LiteralStringSingle)
        );
    }
}
