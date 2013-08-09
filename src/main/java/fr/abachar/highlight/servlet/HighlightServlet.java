package fr.abachar.highlight.servlet;

import fr.abachar.highlight.HtmlUtils;
import fr.abachar.highlight.Token;
import fr.abachar.highlight.lexers.jvm.JavaLexer;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 */
public class HighlightServlet extends HttpServlet {

    /**
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
            ServletException {

        int numLines = 0;
        String input = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("sample.java.txt"));
        String output = "";
        JavaLexer lexer = new JavaLexer();

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

            output += "<span class=\"" + token.getTokenType().getCssClass() + "\">"
                    + HtmlUtils.htmlEscape(value)
                    + "</span>";
        }

        output = output.replace("\n", "<br />");

        request.setAttribute("source", output);
        getServletContext().getRequestDispatcher("/source.jsp").forward(request, response);
    }
}
