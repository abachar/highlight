package fr.abachar.highlight.fragment;

public class Fragment {

    private Type type;
    private String text;

    public Fragment(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    public Type getType() {
        return type;
    }

    public String getText() {
        return text;
    }
}
