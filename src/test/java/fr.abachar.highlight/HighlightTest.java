package fr.abachar.highlight;

import fr.abachar.fr.abachar.highlight.languages.CssLexer;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.Token;
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
        String source = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("sample.css"));
        String output = "";

        //
        CssLexer lexer = new CssLexer(new ANTLRInputStream(source));
        while (true) {
            Token token = lexer.nextToken();
            if (token.getType() == Token.EOF) {
                break;
            }

            switch (token.getType()) {
                case CssLexer.COMMENT:
                    output += "<span class=\"c\">" + token.getText() + "</span>";
                    break;

                default:
                    output += token.getText();
                    break;
            }

            System.out.println("Token: (" + token.getType() + ") " + token.getText());
        }

        output = output.replace("\r\n", "\n");
        output = output.replace("\n", "<br />");
        output = output.replace("  ", "&nbsp; ");
        writeOutpu(output);
    }

    private void writeOutpu(String output) throws IOException {
        StringBuilder sbHtml = new StringBuilder();
        sbHtml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        sbHtml.append("<!DOCTYPE html>");
        sbHtml.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        sbHtml.append("  <head>");
        sbHtml.append("    <style>");
        sbHtml.append("      .highlight { font: 12px Monospace; }");
        sbHtml.append("      .c { color: #999; }");  /* Comment */
        sbHtml.append("      .o { color: #555555 }"); /* Operator */
        sbHtml.append("      .k { color: #006699; }"); /* Keyword */
        sbHtml.append("      .n { color: #CC00FF }"); /* Name */
        sbHtml.append("      .m { color: #FF6600 }"); /* Literal Number */
        sbHtml.append("      .s { color: #d44950 }"); /* Literal String */
        sbHtml.append("    </style>");
        sbHtml.append("  </head>");
        sbHtml.append("  <body>");
        sbHtml.append("    <div class=\"highlight\">").append(output).append("    </div>");
        sbHtml.append("  </body>");
        sbHtml.append("</html>");
        IOUtils.write(sbHtml.toString(), new FileOutputStream("/home/abachar/Bureau/highlight/index.html"));
    }
}
