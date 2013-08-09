package fr.abachar.highlight.lexers.web;

import fr.abachar.highlight.RegexLexer;
import fr.abachar.highlight.StateBuilder;
import fr.abachar.highlight.TokenType;

import java.util.regex.Pattern;

/**
 * @author Abdelhakim bachar
 */
public class HtmlLexer extends RegexLexer {

    /**
     *
     */
    public HtmlLexer() {
        this.name = "HTML";
        this.flags = Pattern.DOTALL | Pattern.CASE_INSENSITIVE;
    }

    /**
     * Build lexer rules
     */
    @Override
    protected void initializeRules() {

        addState("root", new fr.abachar.highlight.StateBuilder()
                .rule("[^<&]+", TokenType.Text)
                .rule("&\\S*?;", TokenType.NameEntity)
                .rule("\\<\\!\\[CDATA\\[.*?\\]\\]\\>", TokenType.CommentPreproc)
                .rule("<!--", TokenType.Comment, "#push:comment")
                .rule("<\\?.*?\\?>", TokenType.CommentPreproc)
                .rule("<![^>]*>", TokenType.CommentPreproc)
                .rule("<\\s*script\\s*", TokenType.NameTag, "#push:script-content,tag")
                .rule("<\\s*style\\s*", TokenType.NameTag, "#push:style-content,tag")
                .rule("<\\s*[\\w:.-]+", TokenType.NameTag, "#push:tag")
                .rule("<\\s*/\\s*[\\w:.-]+\\s*>", TokenType.NameTag)
                .rule("-->", TokenType.Comment, "#pop")
                .rule("-", TokenType.Comment)
        );

        addState("comment", new fr.abachar.highlight.StateBuilder()
                .rule("[^-]+", TokenType.Comment)
                .rule("-->", TokenType.Comment, "#pop")
                .rule("-", TokenType.Comment)
        );

        addState("tag", new fr.abachar.highlight.StateBuilder()
                .rule("\\s+", TokenType.Text)
                .rule("[a-zA-Z0-9_:-]+\\s*=", TokenType.NameAttribute, "#push:attr")
                .rule("[a-zA-Z0-9_:-]+", TokenType.NameAttribute)
                .rule("/?\\s*>", TokenType.NameTag, "#pop")
        );

        addState("script-content", new fr.abachar.highlight.StateBuilder()
                .rule("<\\s*/\\s*script\\s*>", TokenType.NameTag, "#pop")
                .rule(".+?(?=<\\s*/\\s*script\\s*>)", using(JavascriptLexer.class))
        );

        addState("style-content", new fr.abachar.highlight.StateBuilder()
                .rule("<\\s*/\\s*style\\s*>", TokenType.NameTag, "#pop")
                .rule(".*(?=<\\s*/\\s*style\\s*>)", using(CssLexer.class))
        );

        addState("attr", new fr.abachar.highlight.StateBuilder()
                .rule("\".*?\"", TokenType.LiteralString, "#pop")
                .rule("'.*?'", TokenType.LiteralString, "#pop")
                .rule("[^\\s>]+", TokenType.LiteralString, "#pop")
        );
    }

    /**
     * @author Abdelhakim bachar
     */
    public static class XmlLexer extends RegexLexer {

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
}
