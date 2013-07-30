package fr.abachar.highlight;

import fr.abachar.fr.abachar.highlight.languages.JavaLexer;
import fr.abachar.fr.abachar.highlight.languages.JavaParser;
import fr.abachar.highlight.fragment.Fragment;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

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
public class Highlight2Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String source = getSource();

        JavaLexer lexer = new JavaLexer(new ANTLRInputStream(source));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        parser.setBuildParseTree(true);
        parser.parse();

        request.setAttribute("source", dumpSource(source, parser.getFragments()));

        getServletContext().getRequestDispatcher("/source.jsp").forward(request, response);
    }

    private String dumpSource(String source, List<Fragment> fragments) {

        String ret = "";
        int pos = 0;

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
        ret = ret.replace("  ", "&nbsp; ");

        return ret;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private String getSource() throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("sample.java.txt")));
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