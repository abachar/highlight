package fr.abachar.highlight;

import fr.abachar.highlight.languages.CSSLexer;

import fr.abachar.highlight.lexer.TokenType;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.Token;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author abachar
 */
public class HighlightTest {

    @Test
    public void test() throws IOException {
        // Get test source file
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
        writeOutput(output);
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
