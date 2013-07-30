package fr.abachar.highlight;

import fr.abachar.fr.abachar.highlight.languages.CssLexer;
import fr.abachar.highlight.antlr.ANTLRNoCaseInputStream;
import fr.abachar.highlight.antlr.HighlightLexer;
import fr.abachar.highlight.fragment.Fragment;
import org.antlr.v4.runtime.ANTLRInputStream;
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
    public void test() throws IOException {
        // Get test source file
        String source = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("sample.css.txt"));
        String output = "";

        //
        HighlightLexer lexer = new CssLexer(null);
        if (lexer.isCaseSensitive()) {
            lexer.setInputStream(new ANTLRInputStream(source));
        } else {
            lexer.setInputStream(new ANTLRNoCaseInputStream(source));
        }

        List<Fragment> fragments = lexer.run();
        for (Fragment fragment : fragments) {
            output += "<span class=\"" + fragment.getType().getCssClass() + "\">" + fragment.getText() + "</span>";
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
        sbHtml.append("      .highlight { font: 12px Monospace;}");
        sbHtml.append("      .c { color: #999988 }"); /* Comment */
        sbHtml.append("      .o { color: #000000 }"); /* Operator */
        sbHtml.append("      .k { color: #006699 }"); /* Keyword */
        sbHtml.append("      .n { color: #008080 }"); /* Name */
        sbHtml.append("      .m { color: #009999 }"); /* Literal Number */
        sbHtml.append("      .s { color: #fdf1f4 }"); /* Literal String */
        sbHtml.append("      .t { color: #000000 }"); /* Text */
        sbHtml.append("    </style>");
        sbHtml.append("  </head>");
        sbHtml.append("  <body>");
        sbHtml.append("    <div class=\"highlight\">").append(output).append("    </div>");
        sbHtml.append("  </body>");
        sbHtml.append("</html>");
        IOUtils.write(sbHtml.toString(), new FileOutputStream("/home/abachar/Bureau/highlight/index.html"));
    }
}
