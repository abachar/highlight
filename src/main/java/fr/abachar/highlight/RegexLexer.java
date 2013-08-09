package fr.abachar.highlight;

import fr.abachar.highlight.rules.IncludeRule;
import fr.abachar.highlight.rules.RegexRule;
import fr.abachar.highlight.rules.Rule;
import fr.abachar.highlight.rules.RuleCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author abachar
 */
public abstract class RegexLexer extends Lexer {

    /**
     *
     */
    protected int flags;

    /**
     *
     */
    private Map<String, State> states;

    /**
     *
     */
    public RegexLexer() {
        super();

        // Initialize rules
        initializeRules();
    }

    /**
     *
     */
    protected abstract void initializeRules();


    /**
     * @param input
     * @return
     */
    @Override
    public List<Token> getTokens(String input) {

        input = input.replace("\r\n", "\n").replace("\r", "\n").replace("\t", "    ");

        Context context = new Context();
        context.setInput(input);
        context.pushState("root");

        int length = input.length();
        while (context.getPosition() < length) {
            if (!step(context, resolveState(context.peekState()))) {
                break;
            }
        }

        return context.getTokens();
    }

    protected boolean isIn(String text, String list) {

        String[] items = list.split("\\|");
        for (String item : items) {
            if (item.equals(text)) {
                return true;
            }
        }
        return false;
    }

    protected RuleCallback byGroups(final TokenType... types) {

        return new RuleCallback() {
            public void execute(Context context) {
                Matcher m = context.getMatcher();
                int index = 0;
                for (TokenType tokenType : types) {
                    context.addToken(m.group(index), tokenType);
                    index++;
                }
            }
        };
    }

    protected RuleCallback using(final Class<? extends Lexer> clazz) {

        return new RuleCallback() {
            public void execute(Context context) {

                try {
                    String input = context.getText();

                    Lexer lexer = clazz.newInstance();
                    List<Token> tokens = lexer.getTokens(input);
                    context.getTokens().addAll(tokens);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    protected void delegate(Context context, Lexer lexer, String input) {
        List<Token> tokens = lexer.getTokens(input);
        context.getTokens().addAll(tokens);
    }


    protected void addState(String stateName, StateBuilder builder) {

        State state = builder.getState();
        state.setName(stateName);
        if (states == null) {
            states = new HashMap<String, State>();
        }
        states.put(stateName, state);
    }

    protected State resolveState(String name) {
        return states.get(name);
    }


    protected boolean step(Context context, State state) {
        for (Rule rule : state.getRules()) {

            if (rule instanceof IncludeRule) {

                if (step(context, resolveState(((IncludeRule) rule).getStateName()))) {
                    return true;
                }

            } else if (rule instanceof RegexRule) {

                if (logger.isDebugEnabled()) {
                    String t = context.getInput().substring(context.getPosition(), Math.min(context.getPosition() + 100, context.getInput().length()));
                    t = t.replace("\n", "\\n");
                    logger.debug("Test [{}] in state [{}] on text [{}", ((RegexRule) rule).getRegex(), state.getName(), t);
                }

                if (runRule(context, (RegexRule) rule)) {
                    return true;
                }

            }
        }

        return false;
    }

    private boolean runRule(Context context, RegexRule rule) {

        Pattern pattern = Pattern.compile(rule.getRegex(), flags);
        Matcher matcher = pattern.matcher(context.getInput());
        matcher.region(context.getPosition(), context.getInput().length());

        if (matcher.lookingAt()) {
            // Set matcher
            context.setMatcher(matcher);
            rule.getCallback().execute(context);
            context.setPosition(matcher.end());

            return true;
        }

        return false;
    }

}
