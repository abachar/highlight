package fr.abachar.highlight;

import java.util.List;
import java.util.regex.Matcher;

/**
 * Created with IntelliJ IDEA.
 * User: abachar
 * Date: 01/08/13
 * Time: 17:52
 * To change this template use File | Settings | File Templates.
 */
public abstract class Lexer {

    public static interface Rule {
    }

    public static class RegexRule implements Rule {
        private String regex;
        private RuleCallback callback;
    }

    public static class IncludeRule implements Rule {
        private String name;
    }

    public static class State {

        private List<Rule> rules;

    }

    public static abstract class StateCreator {

        public abstract void create();

        protected void include(String state) {
        }

        protected void rule(String regexp, Token token) {
        }

        protected void rule(String regexp, RuleCallback callback) {
        }


        protected void rule(String regexp, Token token, String nextState) {
        }
    }

    public static abstract class RuleCallback {
        public abstract void execute(Context context);
    }

    public static class Context {

        public void addToken(String text, Token token) {
        }

        public String getText() {
            return null /* matcher.group() */;
        }

        public String popState() {
            return null;
        }

        public void pushState(String state) {

        }

        public Matcher getMatcher() {
            return null;
        }
    }

    protected void addState(String root, StateCreator stateCreator) {
    }


}
