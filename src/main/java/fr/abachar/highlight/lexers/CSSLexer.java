package fr.abachar.highlight.lexers;

import fr.abachar.highlight.RegexLexer;
import fr.abachar.highlight.Token;

/**
 * @author Abdelhakim bachar
 */
public class CSSLexer extends RegexLexer {

    /**
     * Build lexer rules
     */
    protected void initializeRules() {

        addState("root", new StateCreator() {
            public void create() {
                include("basics");
                rule("{", Token.Punctuation, "stanza");
                rule(":[a-z0-9_-]+", Token.NameDecorator);
                rule(".[a-z0-9_-]+", Token.NameClass);
                rule("#[a-z0-9_-]+", Token.NameFunction);
                rule("@[a-z0-9_-]+", Token.NameFunction, "at_rule");
                rule("[a-z0-9_-]+", Token.NameTag);
                rule("[~^*!%&\\[\\]()<>|+=@:;,./?-]", Token.Operator);
            }
        });

/*
      state :value do
        mixin :basics
        rule /url\(.*?\)/, 'Literal.String.Other'
        rule /#[0-9a-f]{1,6}/i, 'Literal.Number' # colors
        rule /#{number}(?:em|px|%|pt|pc|in|mm|m|ex|s)?\b/, 'Literal.Number'
        rule /[\[\]():\/.,]/, 'Punctuation'
        rule /"(\\\\|\\"|[^"])*"/, 'Literal.String.Single'
        rule /'(\\\\|\\'|[^'])*'/, 'Literal.String.Double'
        rule(identifier) do |m|
          if self.class.constants.include? m[0]
            token 'Name.Constant'
          elsif self.class.builtins.include? m[0]
            token 'Name.Builtin'
          else
            token 'Name'
          end
        end
      end


 */

        addState("at_rule", new StateCreator() {
            public void create() {
                rule("{(?=\\s*[a-z0-9_-]+\\s*:)", Token.Punctuation, "at_stanza");
                rule("{", Token.Punctuation, "at_body");
                rule(";", Token.Punctuation, "pop"); // pop is specific state
                include("value");
            }
        });

        addState("at_body", new StateCreator() {
            public void create() {
                include("at_content");
                include("root");
            }
        });

        addState("at_stanza", new StateCreator() {
            public void create() {
                include("at_content");
                include("stanza");
            }
        });

/*
        addState("at_content", new StateCreator() {
            public void create() {
                rule("}", new RuleCallback() {
                    public void execute(Context context) {
                        context.setToken(Token.Punctuation);
                        context.popState();
                        context.popState();
                    }
                });
        });
*/

        addState("basics", new StateCreator() {
            public void create() {
                rule("\\s+", Token.Text);
                rule("/\\*(?:.*?)\\*/", Token.Comment);
            }
        });

/*
      state :stanza do
        mixin :basics
        rule /}/, 'Punctuation', :pop!
        rule /(#{identifier})(\s*)(:)/m do |m|
          if self.class.attributes.include? m[1]
            group 'Name.Label'
          elsif self.class.vendor_prefixes.any? { |p| m[1].start_with?(p) }
            group 'Name.Label'
          else
            group 'Name.Property'
          end

          group 'Text'
          group 'Punctuation'

          push :stanza_value
        end
      end
 */

        addState("stanza_value", new StateCreator() {
            public void create() {
                rule(";", Token.Punctuation, "pop"); // pop is specific state
                // rule("(?=})", Token.Comment);       rule(/(?=})/) { pop! }
                rule("!important\\b", Token.CommentPreproc);
                rule("^@.*?$", Token.CommentPreproc);
                include("value");
            }
        });
    }
}
