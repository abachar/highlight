package fr.abachar.highlight;

import fr.abachar.highlight.rules.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: abachar
 * Date: 02/08/13
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
public class State {

    /**
     *
     */
    private String name;

    /**
     *
     */
    private List<Rule> rules;

    public State() {
        rules = new ArrayList<Rule>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }
}
