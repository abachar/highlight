package fr.abachar.highlight;

/**
 * Created with IntelliJ IDEA.
 * User: abachar
 * Date: 01/08/13
 * Time: 17:52
 * To change this template use File | Settings | File Templates.
 */
public abstract class RegexLexer extends Lexer {

    public static abstract class StateCreator {

        public abstract void create();

        protected void include(String state) {
        }

        protected void rule(String regexp, Token token) {
        }

        protected void rule(String regexp, Token token, String nextState) {
        }
    }

    public static class State {

    }

    protected void addState(String root, StateCreator stateCreator) {
    }
}
