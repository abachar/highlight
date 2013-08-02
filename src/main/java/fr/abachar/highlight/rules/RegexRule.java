package fr.abachar.highlight.rules;

/**
 * Created with IntelliJ IDEA.
 * User: abachar
 * Date: 02/08/13
 * Time: 13:38
 * To change this template use File | Settings | File Templates.
 */
public class RegexRule extends Rule {
    private String regex;
    private RuleCallback callback;

    public RegexRule(String regex, RuleCallback callback) {
        this.regex = regex;
        this.callback = callback;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public RuleCallback getCallback() {
        return callback;
    }

    public void setCallback(RuleCallback callback) {
        this.callback = callback;
    }
}
