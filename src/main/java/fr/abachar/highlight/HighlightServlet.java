package fr.abachar.highlight;

import fr.abachar.highlight.fragment.Fragment;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author abachar
 */
public class HighlightServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*String source = getSource("sample.css.txt");

        JsonLexer lexer = new JsonLexer(new ANTLRInputStream(source));
        while (true) {
            Token token = lexer.nextToken();
            if (token.getType() == Token.EOF) {
                break;
            }

            System.out.println("Token: (" + token.getType() + ") " + token.getText());
        }*/

        //request.setAttribute("source", dumpSource(source, parser.getFragments()));

        //getServletContext().getRequestDispatcher("/source.jsp").forward(request, response);
    }

    private String dumpSource(String source, List<Fragment> fragments) {

        String ret = "";
        /*int pos = 0;

        for (Fragment fragment : fragments) {
            if (pos < fragment.getStart()) {
                ret += source.substring(pos, fragment.getStart());
            }

            ret += "<span class=\"" + fragment.getType().getCssClass() + "\">" + source.substring(fragment.getStart(), fragment.getStop() + 1) + "</span>";
            pos = fragment.getStop() + 1;
        }

        if (pos < source.length()) {
            ret += source.substring(pos);
        }

        ret = ret.replace("\r\n", "\n");
        ret = ret.replace("\n", "<br />");
        ret = ret.replace("  ", "&nbsp; ");*/

        return ret;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private String getSource(String fileName) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)));
        String source = "";
        char[] buffer = new char[1024];
        int count;
        while ((count = br.read(buffer)) > 0) {
            source += new String(buffer, 0, count);
        }

        br.close();
        return source;
    }
}