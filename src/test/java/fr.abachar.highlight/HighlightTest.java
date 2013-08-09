package fr.abachar.highlight;

import fr.abachar.highlight.lexers.sql.SqlLexer;
import fr.abachar.highlight.lexers.web.HtmlLexer;
import fr.abachar.highlight.lexers.web.JavascriptLexer;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author abachar
 */
public class HighlightTest {

    @Test
    public void testCSSLexer() throws IOException {

        int numLines = 1;
        String input = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("sample.sql.txt"));
        String output = "";
        Lexer lexer = new SqlLexer();

        // Parse
        List<Token> tokens = lexer.getTokens(input);
        for (Token token : tokens) {
            String value = token.getValue();
            for (int i = 0; i < value.length(); i++) {
                char character = value.charAt(i);
                if (character == '\n') {
                    numLines++;
                }
            }

            if (token.getTokenType().getCssClass().length() > 0) {
                output += "<span class=\"" + token.getTokenType().getCssClass() + "\">"
                        + HtmlUtils.htmlEscape(value)
                        + "</span>";
            } else {
                output += HtmlUtils.htmlEscape(value);
            }


        }

        output = output.replace("\n", "<br />");
        output = output.replace("  ", "&nbsp; ");
        writeOutput(output, numLines);
    }

    private void writeOutput(String output, int numLines) throws IOException {
        StringBuilder sbHtml = new StringBuilder();
        sbHtml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        sbHtml.append("<!DOCTYPE html>");
        sbHtml.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        sbHtml.append("  <head>");
        sbHtml.append("    <link href=\"style.css\" rel=\"stylesheet\">");
        sbHtml.append("  </head>");
        sbHtml.append("  <body>");
        sbHtml.append("    <div>");
        sbHtml.append("      <div class=\"linenums\">");
        sbHtml.append("        <ul>");
        for (int i = 1; i <= numLines; i++) {
            sbHtml.append("          <li>").append(i).append("</li>");
        }
        sbHtml.append("        </ul>");
        sbHtml.append("      </div>");
        sbHtml.append("      <div class=\"code hll\">").append(output).append("</div>");
        sbHtml.append("    </div>");
        sbHtml.append("  </body>");
        sbHtml.append("</html>");
        IOUtils.write(sbHtml.toString(), new FileOutputStream("/home/abachar/Bureau/highlight/index.html"));
    }
}
