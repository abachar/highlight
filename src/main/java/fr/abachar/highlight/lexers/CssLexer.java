package fr.abachar.highlight.lexers;

import fr.abachar.highlight.Context;
import fr.abachar.highlight.Lexer;
import fr.abachar.highlight.StateBuilder;
import fr.abachar.highlight.TokenType;
import fr.abachar.highlight.rules.RuleCallback;

import java.util.regex.Matcher;

/**
 * @author Abdelhakim bachar
 */
public class CssLexer extends Lexer {

    /**
     * Build lexer rules
     */
    @Override
    protected void initializeRules() {

        addState("root", new StateBuilder()
                .include("basics")
                .rule("\\{", TokenType.Punctuation, "#push:stanza")
                .rule(":[a-z0-9_-]+", TokenType.NameDecorator)
                .rule("\\.[a-z0-9_-]+", TokenType.NameClass)
                .rule("#[a-z0-9_-]+", TokenType.NameFunction)
                .rule("@[a-z0-9_-]+", TokenType.NameFunction, "#push:at_rule")
                .rule("[a-z0-9_-]+", TokenType.NameTag)
                .rule("[~^*!%&\\[\\]()<>|+=@:;,./?-]", TokenType.Operator)
                .rule("\"(\\\\\\\\|\\\\\"|[^\"])*\"", TokenType.LiteralStringDouble)
        );

        addState("value", new StateBuilder()
                .include("basics")
                .rule("url\\(.*?\\)", TokenType.LiteralStringOther)
                .rule("#[0-9a-f]{1,6}", TokenType.LiteralNumber)
                .rule("[\\.-]?[0-9]*[\\.]?[0-9]+(em|px|\\%|pt|pc|in|mm|cm|ex)", TokenType.LiteralNumber)
                .rule("[\\[\\]():\\/.,]", TokenType.Punctuation)
                .rule("\"(\\\\\\\\|\\\\\"|[^\"])*\"", TokenType.LiteralStringDouble)
                .rule("'(\\\\\\\\|\\\\'|[^'])*'", TokenType.LiteralStringSingle)
                .rule("[a-z0-9_-]+", new RuleCallback() {
                    public void execute(Context context) {

                        String identifier = context.getText();
                        if (isConstant(identifier)) {
                            context.addToken(identifier, TokenType.NameLabel);
                        } else if (isBuiltin(identifier)) {
                            context.addToken(identifier, TokenType.NameBuiltin);
                        } else {
                            context.addToken(identifier, TokenType.Name);
                        }
                    }
                })
        );

        addState("at_rule", new StateBuilder()
                .rule("\\{(?=\\s*[a-z0-9_-]+\\s*:)", TokenType.Punctuation, "#push:at_stanza")
                .rule("\\{", TokenType.Punctuation, "#push:at_body")
                .rule(";", TokenType.Punctuation, "#pop")
                .include("value")
        );

        addState("at_body", new StateBuilder()
                .include("at_content")
                .include("root")
        );

        addState("at_stanza", new StateBuilder()
                .include("at_content")
                .include("stanza")
        );

        addState("at_content", new StateBuilder()
                .rule("}", new RuleCallback() {
                    public void execute(Context context) {
                        context.addToken(context.getText(), TokenType.Punctuation);
                        context.popState();
                        context.popState();
                    }
                })
        );

        addState("basics", new StateBuilder()
                .rule("\\s+", TokenType.Text)
                .rule("/\\*(?:.|\n)*?\\*/", TokenType.Comment)
        );

        addState("stanza", new StateBuilder()
                .include("basics")
                .rule("}", TokenType.Punctuation, "#pop")
                .rule("([a-z0-9_-]+)(\\s*)(:)", new RuleCallback() {
                    public void execute(Context context) {
                        Matcher m = context.getMatcher();

                        String identifier = m.group(1);
                        if (isAttribute(identifier) || hasVendorPrefixe(identifier)) {
                            context.addToken(identifier, TokenType.NameLabel);
                        } else {
                            context.addToken(identifier, TokenType.NameProperty);
                        }

                        context.addToken(m.group(2), TokenType.Text);
                        context.addToken(m.group(3), TokenType.Punctuation);

                        context.pushState("stanza_value");
                    }
                })
        );

        addState("stanza_value", new StateBuilder()
                .rule(";", TokenType.Punctuation, "#pop")
                .rule("(?=\\})", null, "#pop")
                .rule("!important\\b", TokenType.CommentPreproc)
                .rule("^@.*?$", TokenType.CommentPreproc)
                .include("value")
        );
    }

    private boolean isAttribute(String identifier) {
        String ATTRIBUTES = "align-content align-items align-self alignment-adjust" +
                "          alignment-baseline all anchor-point animation" +
                "          animation-delay animation-direction animation-duration" +
                "          animation-fill-mode animation-iteration-count animation-name" +
                "          animation-play-state animation-timing-function appearance" +
                "          azimuth backface-visibility background background-attachment" +
                "          background-clip background-color background-image" +
                "          background-origin background-position background-repeat" +
                "          background-size baseline-shift binding bleed bookmark-label" +
                "          bookmark-level bookmark-state bookmark-target border" +
                "          border-bottom border-bottom-color border-bottom-left-radius" +
                "          border-bottom-right-radius border-bottom-style" +
                "          border-bottom-width border-collapse border-color" +
                "          border-image border-image-outset border-image-repeat" +
                "          border-image-slice border-image-source border-image-width" +
                "          border-left border-left-color border-left-style" +
                "          border-left-width border-radius border-right" +
                "          border-right-color border-right-style border-right-width" +
                "          border-spacing border-style border-top border-top-color" +
                "          border-top-left-radius border-top-right-radius" +
                "          border-top-style border-top-width border-width bottom" +
                "          box-align box-decoration-break box-direction box-flex" +
                "          box-flex-group box-lines box-ordinal-group box-orient" +
                "          box-pack box-shadow box-sizing break-after break-before" +
                "          break-inside caption-side clear clip clip-path" +
                "          clip-rule color color-profile columns column-count" +
                "          column-fill column-gap column-rule column-rule-color" +
                "          column-rule-style column-rule-width column-span" +
                "          column-width content counter-increment counter-reset" +
                "          crop cue cue-after cue-before cursor direction display" +
                "          dominant-baseline drop-initial-after-adjust" +
                "          drop-initial-after-align drop-initial-before-adjust" +
                "          drop-initial-before-align drop-initial-size" +
                "          drop-initial-value elevation empty-cells filter fit" +
                "          fit-position flex flex-basis flex-direction flex-flow" +
                "          flex-grow flex-shrink flex-wrap float float-offset" +
                "          font font-family font-feature-settings" +
                "          font-kerning font-language-override font-size" +
                "          font-size-adjust font-stretch font-style font-synthesis" +
                "          font-variant font-variant-alternates font-variant-caps" +
                "          font-variant-east-asian font-variant-ligatures" +
                "          font-variant-numeric font-variant-position font-weight" +
                "          grid-cell grid-column grid-column-align grid-column-sizing" +
                "          grid-column-span grid-columns grid-flow grid-row" +
                "          grid-row-align grid-row-sizing grid-row-span" +
                "          grid-rows grid-template hanging-punctuation height" +
                "          hyphenate-after hyphenate-before hyphenate-character" +
                "          hyphenate-lines hyphenate-resource hyphens icon" +
                "          image-orientation image-rendering image-resolution" +
                "          ime-mode inline-box-align justify-content" +
                "          left letter-spacing line-break line-height" +
                "          line-stacking line-stacking-ruby line-stacking-shift" +
                "          line-stacking-strategy list-style list-style-image" +
                "          list-style-position list-style-type margin" +
                "          margin-bottom margin-left margin-right margin-top" +
                "          mark marker-offset marks mark-after mark-before" +
                "          marquee-direction marquee-loop marquee-play-count" +
                "          marquee-speed marquee-style mask max-height max-width" +
                "          min-height min-width move-to nav-down" +
                "          nav-index nav-left nav-right nav-up object-fit" +
                "          object-position opacity order orphans outline" +
                "          outline-color outline-offset outline-style" +
                "          outline-width overflow overflow-style overflow-wrap" +
                "          overflow-x overflow-y padding padding-bottom" +
                "          padding-left padding-right padding-top" +
                "          page page-break-after page-break-before" +
                "          page-break-inside page-policy pause pause-after" +
                "          pause-before perspective perspective-origin" +
                "          phonemes pitch pitch-range play-during pointer-events" +
                "          position presentation-level punctuation-trim quotes" +
                "          rendering-intent resize rest rest-after rest-before" +
                "          richness right rotation rotation-point ruby-align" +
                "          ruby-overhang ruby-position ruby-span size speak" +
                "          speak-as speak-header speak-numeral speak-punctuation" +
                "          speech-rate src stress string-set" +
                "          tab-size table-layout target target-name" +
                "          target-new target-position text-align" +
                "          text-align-last text-combine-horizontal" +
                "          text-decoration text-decoration-color" +
                "          text-decoration-line text-decoration-skip" +
                "          text-decoration-style text-emphasis" +
                "          text-emphasis-color text-emphasis-position" +
                "          text-emphasis-style text-height text-indent" +
                "          text-justify text-orientation text-outline" +
                "          text-overflow text-rendering text-shadow" +
                "          text-space-collapse text-transform" +
                "          text-underline-position text-wrap top" +
                "          transform transform-origin transform-style" +
                "          transition transition-delay transition-duration" +
                "          transition-property transition-timing-function" +
                "          unicode-bidi vertical-align" +
                "          visibility voice-balance voice-duration" +
                "          voice-family voice-pitch voice-pitch-range" +
                "          voice-range voice-rate voice-stress voice-volume" +
                "          volume white-space widows width word-break" +
                "          word-spacing word-wrap writing-mode z-indexalign-content align-items align-self alignment-adjust" +
                "          alignment-baseline all anchor-point animation" +
                "          animation-delay animation-direction animation-duration" +
                "          animation-fill-mode animation-iteration-count animation-name" +
                "          animation-play-state animation-timing-function appearance" +
                "          azimuth backface-visibility background background-attachment" +
                "          background-clip background-color background-image" +
                "          background-origin background-position background-repeat" +
                "          background-size baseline-shift binding bleed bookmark-label" +
                "          bookmark-level bookmark-state bookmark-target border" +
                "          border-bottom border-bottom-color border-bottom-left-radius" +
                "          border-bottom-right-radius border-bottom-style" +
                "          border-bottom-width border-collapse border-color" +
                "          border-image border-image-outset border-image-repeat" +
                "          border-image-slice border-image-source border-image-width" +
                "          border-left border-left-color border-left-style" +
                "          border-left-width border-radius border-right" +
                "          border-right-color border-right-style border-right-width" +
                "          border-spacing border-style border-top border-top-color" +
                "          border-top-left-radius border-top-right-radius" +
                "          border-top-style border-top-width border-width bottom" +
                "          box-align box-decoration-break box-direction box-flex" +
                "          box-flex-group box-lines box-ordinal-group box-orient" +
                "          box-pack box-shadow box-sizing break-after break-before" +
                "          break-inside caption-side clear clip clip-path" +
                "          clip-rule color color-profile columns column-count" +
                "          column-fill column-gap column-rule column-rule-color" +
                "          column-rule-style column-rule-width column-span" +
                "          column-width content counter-increment counter-reset" +
                "          crop cue cue-after cue-before cursor direction display" +
                "          dominant-baseline drop-initial-after-adjust" +
                "          drop-initial-after-align drop-initial-before-adjust" +
                "          drop-initial-before-align drop-initial-size" +
                "          drop-initial-value elevation empty-cells filter fit" +
                "          fit-position flex flex-basis flex-direction flex-flow" +
                "          flex-grow flex-shrink flex-wrap float float-offset" +
                "          font font-family font-feature-settings" +
                "          font-kerning font-language-override font-size" +
                "          font-size-adjust font-stretch font-style font-synthesis" +
                "          font-variant font-variant-alternates font-variant-caps" +
                "          font-variant-east-asian font-variant-ligatures" +
                "          font-variant-numeric font-variant-position font-weight" +
                "          grid-cell grid-column grid-column-align grid-column-sizing" +
                "          grid-column-span grid-columns grid-flow grid-row" +
                "          grid-row-align grid-row-sizing grid-row-span" +
                "          grid-rows grid-template hanging-punctuation height" +
                "          hyphenate-after hyphenate-before hyphenate-character" +
                "          hyphenate-lines hyphenate-resource hyphens icon" +
                "          image-orientation image-rendering image-resolution" +
                "          ime-mode inline-box-align justify-content" +
                "          left letter-spacing line-break line-height" +
                "          line-stacking line-stacking-ruby line-stacking-shift" +
                "          line-stacking-strategy list-style list-style-image" +
                "          list-style-position list-style-type margin" +
                "          margin-bottom margin-left margin-right margin-top" +
                "          mark marker-offset marks mark-after mark-before" +
                "          marquee-direction marquee-loop marquee-play-count" +
                "          marquee-speed marquee-style mask max-height max-width" +
                "          min-height min-width move-to nav-down" +
                "          nav-index nav-left nav-right nav-up object-fit" +
                "          object-position opacity order orphans outline" +
                "          outline-color outline-offset outline-style" +
                "          outline-width overflow overflow-style overflow-wrap" +
                "          overflow-x overflow-y padding padding-bottom" +
                "          padding-left padding-right padding-top" +
                "          page page-break-after page-break-before" +
                "          page-break-inside page-policy pause pause-after" +
                "          pause-before perspective perspective-origin" +
                "          phonemes pitch pitch-range play-during pointer-events" +
                "          position presentation-level punctuation-trim quotes" +
                "          rendering-intent resize rest rest-after rest-before" +
                "          richness right rotation rotation-point ruby-align" +
                "          ruby-overhang ruby-position ruby-span size speak" +
                "          speak-as speak-header speak-numeral speak-punctuation" +
                "          speech-rate src stress string-set" +
                "          tab-size table-layout target target-name" +
                "          target-new target-position text-align" +
                "          text-align-last text-combine-horizontal" +
                "          text-decoration text-decoration-color" +
                "          text-decoration-line text-decoration-skip" +
                "          text-decoration-style text-emphasis" +
                "          text-emphasis-color text-emphasis-position" +
                "          text-emphasis-style text-height text-indent" +
                "          text-justify text-orientation text-outline" +
                "          text-overflow text-rendering text-shadow" +
                "          text-space-collapse text-transform" +
                "          text-underline-position text-wrap top" +
                "          transform transform-origin transform-style" +
                "          transition transition-delay transition-duration" +
                "          transition-property transition-timing-function" +
                "          unicode-bidi vertical-align" +
                "          visibility voice-balance voice-duration" +
                "          voice-family voice-pitch voice-pitch-range" +
                "          voice-range voice-rate voice-stress voice-volume" +
                "          volume white-space widows width word-break" +
                "          word-spacing word-wrap writing-mode z-index";

        String[] attributes = ATTRIBUTES.split(" ");
        for (String attribute : attributes) {
            attribute = attribute.trim();

            if (attribute.equals(identifier)) {
                return true;
            }
        }

        return false;
    }

    private boolean isConstant(String identifier) {

        String CONSTANTS = "indigo gold firebrick indianred yellow darkolivegreen" +
                "          darkseagreen mediumvioletred mediumorchid chartreuse" +
                "          mediumslateblue black springgreen crimson lightsalmon brown" +
                "          turquoise olivedrab cyan silver skyblue gray darkturquoise" +
                "          goldenrod darkgreen darkviolet darkgray lightpink teal" +
                "          darkmagenta lightgoldenrodyellow lavender yellowgreen thistle" +
                "          violet navy orchid blue ghostwhite honeydew cornflowerblue" +
                "          darkblue darkkhaki mediumpurple cornsilk red bisque slategray" +
                "          darkcyan khaki wheat deepskyblue darkred steelblue aliceblue" +
                "          gainsboro mediumturquoise floralwhite coral purple lightgrey" +
                "          lightcyan darksalmon beige azure lightsteelblue oldlace" +
                "          greenyellow royalblue lightseagreen mistyrose sienna lightcoral" +
                "          orangered navajowhite lime palegreen burlywood seashell" +
                "          mediumspringgreen fuchsia papayawhip blanchedalmond peru" +
                "          aquamarine white darkslategray ivory dodgerblue lemonchiffon" +
                "          chocolate orange forestgreen slateblue olive mintcream" +
                "          antiquewhite darkorange cadetblue moccasin limegreen saddlebrown" +
                "          darkslateblue lightskyblue deeppink plum aqua darkgoldenrod" +
                "          maroon sandybrown magenta tan rosybrown pink lightblue" +
                "          palevioletred mediumseagreen dimgray powderblue seagreen snow" +
                "          mediumblue midnightblue paleturquoise palegoldenrod whitesmoke" +
                "          darkorchid salmon lightslategray lawngreen lightgreen tomato" +
                "          hotpink lightyellow lavenderblush linen mediumaquamarine green" +
                "          blueviolet peachpuff";

        String[] constants = CONSTANTS.split(" ");
        for (String constant : constants) {
            constant = constant.trim();

            if (constant.equals(identifier)) {
                return true;
            }
        }

        return false;
    }

    private boolean isBuiltin(String identifier) {

        String BUILTINS = "above absolute always armenian aural auto avoid left bottom" +
                "          baseline behind below bidi-override blink block bold bolder" +
                "          both bottom capitalize center center-left center-right circle" +
                "          cjk-ideographic close-quote collapse condensed continuous crop" +
                "          cross crosshair cursive dashed decimal decimal-leading-zero" +
                "          default digits disc dotted double e-resize embed expanded" +
                "          extra-condensed extra-expanded fantasy far-left far-right fast" +
                "          faster fixed georgian groove hebrew help hidden hide high higher" +
                "          hiragana hiragana-iroha icon inherit inline inline-table inset" +
                "          inside invert italic justify katakana katakana-iroha landscape" +
                "          large larger left left-side leftwards level lighter line-through" +
                "          list-item loud low lower lower-alpha lower-greek lower-roman" +
                "          lowercase ltr medium message-box middle mix monospace n-resize" +
                "          narrower ne-resize no-close-quote no-open-quote no-repeat none" +
                "          normal nowrap nw-resize oblique once open-quote outset outside" +
                "          overline pointer portrait px relative repeat repeat-x repeat-y" +
                "          rgb ridge right right-side rightwards s-resize sans-serif scroll" +
                "          se-resize semi-condensed semi-expanded separate serif show" +
                "          silent slow slower small-caps small-caption smaller soft solid" +
                "          spell-out square static status-bar super sw-resize table-caption" +
                "          table-cell table-column table-column-group table-footer-group" +
                "          table-header-group table-row table-row-group text text-bottom" +
                "          text-top thick thin top transparent ultra-condensed" +
                "          ultra-expanded underline upper-alpha upper-latin upper-roman" +
                "          uppercase url visible w-resize wait wider x-fast x-high x-large" +
                "          x-loud x-low x-small x-soft xx-large xx-small yes";

        String[] builtins = BUILTINS.split(" ");
        for (String builtin : builtins) {
            builtin = builtin.trim();

            if (builtin.equals(identifier)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasVendorPrefixe(String identifier) {

        String VENDOR_PREFIXES = "-ah- -atsc- -hp- -khtml- -moz- -ms- -o- -rim- -ro- -tc- -wap- -webkit- -xv- mso- prince-";

        return false;
    }
}
