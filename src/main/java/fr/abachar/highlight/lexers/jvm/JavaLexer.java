package fr.abachar.highlight.lexers.jvm;

import fr.abachar.highlight.*;
import fr.abachar.highlight.rules.RuleCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Abdelhakim bachar
 */
public class JavaLexer extends RegexLexer {

    /**
     *
     */
    public JavaLexer() {
        this.name = "Java";
        this.flags = Pattern.DOTALL | Pattern.MULTILINE;
    }

    /**
     * Build lexer rules
     */
    @Override
    protected void initializeRules() {

        // Current lexer
        final Lexer _this = this;

        addState("root", new StateBuilder()

                .rule("^(\\s*(?:[a-zA-Z_][a-zA-Z0-9_\\.\\[\\]]*\\s+)+?)([a-zA-Z_][a-zA-Z0-9_]*)(\\s*)(\\()", new RuleCallback() {
                    public void execute(Context context) {

                        Matcher m = context.getMatcher();

                        // Delegate
                        if (logger.isInfoEnabled()) {
                            logger.info("Delegating [{}] to JavaLexer", m.group(1).replace("\n", "\\n"));
                        }
                        delegate(context, new JavaLexer(), m.group(1));

                        context.addToken(m.group(2), TokenType.NameFunction);
                        context.addToken(m.group(3), TokenType.Text);
                        context.addToken(m.group(4), TokenType.Operator);
                    }
                })
                .rule("\\s+", TokenType.Text)
                .rule("//.*?$", TokenType.CommentSingle)
                .rule("/\\*.*?\\*/", TokenType.CommentMultiline)
                .rule("@[a-zA-Z_][a-zA-Z0-9_]*", TokenType.NameDecorator)
                .rule("(assert|break|case|catch|continue|default|do|else|finally|for|if|goto|instanceof|new|return|switch|this|throw|try|while)", TokenType.Keyword)
                .rule("(abstract|const|enum|extends|final|implements|native|private|protected|public|static|strictfp|super|synchronized|throws|transient|volatile)", TokenType.KeywordDeclaration)
                .rule("(boolean|byte|char|double|float|int|long|short|void)", TokenType.KeywordType)
                .rule("package", TokenType.KeywordNamespace, "#push:package")
                .rule("(true|false|null)", TokenType.KeywordConstant)
                .rule("(class|interface)", TokenType.KeywordDeclaration, "#push:class")
                .rule("import", TokenType.KeywordNamespace, "#push:import")
                .rule("\"(\\\\\\\\|\\\\\"|[^\"])*\"", TokenType.LiteralString)
                .rule("'\\\\.'|'[^\\\\]'|'\\\\u[0-9a-fA-F]{4}'", TokenType.LiteralStringChar)
                .rule("(\\.)([a-zA-Z_][a-zA-Z0-9_]*)", byGroups(TokenType.Operator, TokenType.NameAttribute))
                .rule("[a-zA-Z_][a-zA-Z0-9_]*:", TokenType.NameLabel)
                .rule("[a-zA-Z_\\$][a-zA-Z0-9_]*", TokenType.Name)
                .rule("[~\\^\\*!%&\\[\\]\\(\\)\\{\\}<>\\|+=:;,./?-]", TokenType.Operator)
                .rule("[0-9][0-9]*\\.[0-9]+([eE][0-9]+)?[fd]?", TokenType.LiteralNumberFloat)
                .rule("0x[0-9a-f]+", TokenType.LiteralNumberHex)
                .rule("[0-9]+L?", TokenType.LiteralNumberInteger)
        );

        addState("class", new StateBuilder()
                .rule("\\s+", TokenType.Text)
                .rule("[a-zA-Z_][a-zA-Z0-9_]*", TokenType.NameClass, "#pop")
        );

        addState("import", new StateBuilder()
                .rule("\\s+", TokenType.Text)
                .rule("[a-zA-Z0-9_.]+\\*?", TokenType.NameNamespace, "#pop")
        );

        addState("package", new StateBuilder()
                .rule("\\s+", TokenType.Text)
                .rule("[a-zA-Z0-9_.]+\\*?", TokenType.NameNamespace, "#pop")
        );
    }
}
