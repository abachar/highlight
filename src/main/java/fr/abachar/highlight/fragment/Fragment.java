package fr.abachar.highlight.fragment;

public class Fragment {

    private Type type;
    private int start;
    private int stop;

    public Fragment(Type type, int start, int stop) {
        this.type = type;
        this.start = start;
        this.stop = stop;
    }

    public Type getType() {
        return type;
    }

    public int getStart() {
        return start;
    }

    public int getStop() {
        return stop;
    }
}
