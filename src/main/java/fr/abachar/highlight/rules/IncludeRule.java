package fr.abachar.highlight.rules;

/**
 * Created with IntelliJ IDEA.
 * User: abachar
 * Date: 02/08/13
 * Time: 13:37
 * To change this template use File | Settings | File Templates.
 */
public class IncludeRule extends Rule {
    private String stateName;

    public IncludeRule(String stateName) {
        this.stateName = stateName;
    }

    public String getStateName() {
        return stateName;
    }
}
