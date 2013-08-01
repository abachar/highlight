package fr.abachar.highlight;

/**
 * @author Abdelhakim bachar
 */
public enum Token {

    Text(""),
    TextWhitespace("w"),
    Error("err"),
    Other("x"),

    Keyword("k"),
    KeywordConstant("kc"),
    KeywordDeclaration("kd"),
    KeywordNamespace("kn"),
    KeywordPseudo("kp"),
    KeywordReserved("kr"),
    KeywordType("kt"),

    Name("n"),
    NameAttribute("na"),
    NameBuiltin("nb"),
    NameBuiltinPseudo("bp"),
    NameClass("nc"),
    NameConstant("no"),
    NameDecorator("nd"),
    NameEntity("ni"),
    NameException("ne"),
    NameFunction("nf"),
    NameProperty("py"),
    NameLabel("nl"),
    NameNamespace("nn"),
    NameOther("nx"),
    NameTag("nt"),
    NameVariable("nv"),
    NameVariableClass("vc"),
    NameVariableGlobal("vg"),
    NameVariableInstance("vi"),

    Literal("l"),
    LiteralDate("ld"),

    LiteralString("s"),
    LiteralStringBacktick("sb"),
    LiteralStringChar("sc"),
    LiteralStringDoc("sd"),
    LiteralStringDouble("s2"),
    LiteralStringEscape("se"),
    LiteralStringHeredoc("sh"),
    LiteralStringInterpol("si"),
    LiteralStringOther("sx"),
    LiteralStringRegex("sr"),
    LiteralStringSingle("s1"),
    LiteralStringSymbol("ss"),

    LiteralNumber("m"),
    LiteralNumberFloat("mf"),
    LiteralNumberHex("mh"),
    LiteralNumberInteger("mi"),
    LiteralNumberIntegerLong("il"),
    LiteralNumberOct("mo"),

    Operator("o"),
    OperatorWord("ow"),

    Punctuation("p"),

    Comment("c"),
    CommentMultiline("cm"),
    CommentPreproc("cp"),
    CommentSingle("c1"),
    CommentSpecial("cs"),

    Generic("g"),
    GenericDeleted("gd"),
    GenericEmph("ge"),
    GenericError("gr"),
    GenericHeading("gh"),
    GenericInserted("gi"),
    GenericOutput("go"),
    GenericPrompt("gp"),
    GenericStrong("gs"),
    GenericSubheading("gu"),
    GenericTraceback("gt"),

    GenericLineno("gl");

    /**
     *
     */
    private String cssClass;

    /**
     * @param cssClass
     */
    private Token(String cssClass) {
        this.cssClass = cssClass;
    }

    /**
     *
     * @return
     */
    public String getCssClass() {
        return cssClass;
    }
}
