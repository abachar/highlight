package fr.abachar.highlight.lexers;

import fr.abachar.highlight.RegexLexer;
import fr.abachar.highlight.StateBuilder;
import fr.abachar.highlight.TokenType;

/**
 * @author Abdelhakim bachar
 */
public class XmlLexer extends RegexLexer {

    /**
     * Build lexer rules
     */
    @Override
    protected void initializeRules() {

        addState("root", new StateBuilder()
                .rule("[^<&]+", TokenType.Text)
                .rule("&\\S*?;", TokenType.NameEntity)
                .rule("\\<\\!\\[CDATA\\[.*?\\]\\]\\>", TokenType.CommentPreproc)
                .rule("<!--", TokenType.Comment, "#push:comment")
                .rule("<\\?.*?\\?>", TokenType.CommentPreproc)
                .rule("<![^>]*>", TokenType.CommentPreproc)
                .rule("<\\s*[\\w:.-]+", TokenType.NameTag, "#push:tag")
                .rule("<\\s*/\\s*[\\w:.-]+\\s*>", TokenType.NameTag)
        );


        addState("comment", new StateBuilder()
                .rule("[^-]+", TokenType.Comment)
                .rule("-->", TokenType.Comment, "#pop")
                .rule("-", TokenType.Comment)
        );

        addState("tag", new StateBuilder()
                .rule("\\s+", TokenType.Text)
                .rule("[\\w.:-]+\\s*=", TokenType.NameAttribute, "#push:attr")
                .rule("/?\\s*>", TokenType.NameTag, "#pop")
        );

        addState("attr", new StateBuilder()
                .rule("\\s+", TokenType.Text)
                .rule("\".*?\"", TokenType.LiteralString, "#pop")
                .rule("'.*?'", TokenType.LiteralString, "#pop")
                .rule("[^\\s>]+", TokenType.LiteralString, "#pop")
        );
    }
}
