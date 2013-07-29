package fr.abachar.highlight.fragment;


public enum Type {
    COMMENT("c"),
    OPERATOR("o"),
    KEYWORD("k"),
    NAME("n"),
    NUMBER("m"),
    STRING("s");

    private String cssClass;

    private Type(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssClass() {
        return cssClass;
    }
}
