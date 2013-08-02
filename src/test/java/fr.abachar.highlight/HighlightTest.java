package fr.abachar.highlight;

import fr.abachar.highlight.lexers.CSSLexer;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author abachar
 */
public class HighlightTest {

    @Test
    public void testCSSLexer() throws IOException {

        String output = "";
        CSSLexer lexer = new CSSLexer();
        List<Token> tokens = lexer.getTokens(IOUtils.toString(getClass().getClassLoader().getResourceAsStream("sample.css.txt")));
        for (Token token : tokens) {

            output += "<span class=\"" + token.getTokenType().getCssClass() + "\">" + token.getValue()  + "</span>";
        }

        output = output.replace("\r\n", "\n");
        output = output.replace("\n", "<br />");
        output = output.replace("  ", "&nbsp; ");
        writeOutput(output);
    }

    @Test
    public void test() throws IOException {

        String[] rules = {
                "\\s+",
                "/\\*(?:.|\\n)*?\\*/",
                "([a-z0-9_-]+)(\\s*)(:)"
        };

        String input = "/* Ceci est \nun commentaire */   \n font   :";
        int pos = 0;
        while (pos < input.length()) {
            for (String rule : rules) {
                System.out.printf("Test - [%s] at %d\n", rule, pos);

                Pattern pattern = Pattern.compile(rule);
                Matcher matcher = pattern.matcher(input);
                matcher.region(pos, input.length());

                if (matcher.lookingAt()) {

                    if (matcher.groupCount() > 1) {
                        String text = matcher.group();
                        pos = matcher.end();

                        System.out.printf("[%s] => '%s', '%s', '%s'\n", rule, matcher.group(1), matcher.group(2), matcher.group(3));
                    } else {
                        pos = matcher.end();
                        System.out.printf("[%s] => '%s'\n", rule, matcher.group());
                    }

                    break;
                }
            }
        }

        //.rule("\\s+", TokenType.Text)
        //.rule(, TokenType.Comment)

        /*// Get test source file
        String source = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("comment.css.txt"));
        String output = "";

        //
        CSSLexer lexer = new CSSLexer(new ANTLRStringStream(source));
        String[] tokenNames = lexer.getTokenNames();

        while (true) {
            Token token = lexer.nextToken();
            if (token.getType() == Token.EOF) {
                break;
            }

            TokenType type = TokenType.fromIndex(token.getType());
            output += "<span class=\"" + type.getCssClass() + "\">" + token.getText()  + "</span>";
        }

        output = output.replace("\r\n", "\n");
        output = output.replace("\n", "<br />");
        output = output.replace("  ", "&nbsp; ");
        writeOutput(output);*/
    }

    private void writeOutput(String output) throws IOException {
        StringBuilder sbHtml = new StringBuilder();
        sbHtml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        sbHtml.append("<!DOCTYPE html>");
        sbHtml.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        sbHtml.append("  <head>");
        sbHtml.append("    <link href=\"style.css\" rel=\"stylesheet\">");
        sbHtml.append("  </head>");
        sbHtml.append("  <body>");
        sbHtml.append("    <div class=\"hll\">").append(output).append("    </div>");
        sbHtml.append("  </body>");
        sbHtml.append("</html>");
        IOUtils.write(sbHtml.toString(), new FileOutputStream("/home/abachar/Bureau/highlight/index.html"));
    }
}
