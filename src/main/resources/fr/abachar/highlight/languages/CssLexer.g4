lexer grammar CssLexer;

options {
    superClass = fr.abachar.highlight.antlr.HighlightLexer;
}

tokens {
	WhiteSpace, Comment, Punctuation, Attribute
}

//
// Mode root
WhiteSpace							: WS					-> type(WhiteSpace);			// rule /\s+/m, 'Text'
Comment								: COMMENT				-> type(Comment);				// rule %r(/\*(?:.*?)\*/)m, 'Comment'
LBrace								: '{' 					-> type(Punctuation), pushMode(Stanza);			// rule /{/, 'Punctuation', :stanza
PseudoIdentifier					: ':' IDENTIFIER;										// rule /:#{identifier}/, 'Name.Decorator'
ClassIdentifier						: '.' IDENTIFIER;										// rule /\.#{identifier}/, 'Name.Class'
HashIdentifier						: '#' IDENTIFIER;										// rule /##{identifier}/, 'Name.Function'
AtIdentifier						: '@' IDENTIFIER		-> pushMode(AtRule);			// rule /@#{identifier}/, 'Keyword', :at_rule
Identifier							: IDENTIFIER;											// rule identifier, 'Name.Tag'
Operator							: OPERATOR;												// rule %r([~^*!%&\[\]()<>|+=@:;,./?-]), 'Operator'

//
// Mode AtRule
mode AtRule;
AtRule_WhiteSpace					: WS;

//
// Mode Stanza
mode Stanza;
Stanza_WhiteSpace					: WS					-> type(WhiteSpace);
Stanza_Comment						: COMMENT				-> type(Comment);
Stanza_RBrace						: '}'     				-> type(Punctuation), popMode;
Stanza_Identifier					: IDENTIFIER WS* ':' 	-> type(Attribute), pushMode(Value); 			// Identifier + ':'

//
// Mode StanzaValue
mode Value;
Value_SemiColon						: ';'					-> popMode;						// rule /;/, 'Punctuation', :pop!
Value_RBrace						: '}'					-> popMode;						// rule(/(?=})/) { pop! }
Value_Important						: '!important';											// rule /!important\b/, 'Comment.Preproc'
Value_WhiteSpace					: WS					-> type(WhiteSpace);
Value_Comment						: COMMENT				-> type(Comment);
Value_Color							: '#' HEX HEX? HEX? HEX? HEX? HEX?;						// rule /#[0-9a-f]{1,6}/i, 'Literal.Number' # colors

//
// FRAGMENTS
//
fragment WS							: [ \t\r\n\f];
fragment HEX						: [0-9a-f];
fragment NONASCII					: [\u0238-\uFFFF];
fragment UNICODE					: '\\' HEX HEX? HEX? HEX? HEX? HEX? ( '\r\n' | [ \t\r\n\f] )?;
fragment ESCAPE						: UNICODE | '\\' ~[\n\r\f0-9a-f];
fragment NMSTART					: '-'? ( [_a-z] | NONASCII | ESCAPE );
fragment NMCHAR						: ( [_a-z0-9-] | NONASCII | ESCAPE );
fragment IDENTIFIER					: NMSTART NMCHAR*;
fragment OPERATOR					: [~^*!%&\[\]()<>|+=@:;,./?-];
fragment COMMENT					: '/*' .*? '*/';




/**
Rule : i=IDENTIFIER w=WS* c=SEMI {
	setTypeAndEmit($i, KEYWORD);
	setTypeAndEmit($w, TEXT);
	setTypeAndEmit($c, PUNCTUATION);
	};
*/