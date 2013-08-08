package fr.abachar.highlight;

import fr.abachar.highlight.rules.IncludeRule;
import fr.abachar.highlight.rules.RegexRule;
import fr.abachar.highlight.rules.RuleCallback;

import java.util.regex.Matcher;

/**
 * Created with IntelliJ IDEA.
 * User: abachar
 * Date: 02/08/13
 * Time: 13:26
 * To change this template use File | Settings | File Templates.
 */
public class StateBuilder {

    /**
     *
     */
    private State state = new State();

    public StateBuilder include(String stateName) {
        state.getRules().add(new IncludeRule(stateName));
        return this;
    }

    public StateBuilder rule(String regexp, final TokenType token) {

        RegexRule rule = new RegexRule(regexp, new RuleCallback() {
            public void execute(Context context) {
                context.addToken(context.getText(), token);
            }
        });

        state.getRules().add(rule);
        return this;
    }

    public StateBuilder rule(String regexp, RuleCallback callback) {
        state.getRules().add(new RegexRule(regexp, callback));
        return this;
    }

    public StateBuilder rule(String regexp, final TokenType token, final String nextState) {

        RegexRule rule = new RegexRule(regexp, new RuleCallback() {
            public void execute(Context context) {
                // Create token
                if (token != null) {
                    context.addToken(context.getText(), token);
                }

                if (nextState.startsWith("#pop")) {
                    context.popState();
                } else if (nextState.startsWith("#push")) {
                    String[] states = nextState.substring(6).split(",");
                    for (String state : states) {
                        context.pushState(state);
                    }
                }
            }
        });

        state.getRules().add(rule);
        return this;
    }

    /**
     * @return
     */
    public State getState() {
        return state;
    }
}