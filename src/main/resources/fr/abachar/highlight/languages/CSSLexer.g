lexer grammar CSSLexer;

@lexer::header {
	package fr.abachar.highlight.languages;

	import fr.abachar.highlight.lexer.Lexer;
    import java.util.regex.Pattern;
    import java.util.regex.Matcher;
}

@lexer::members {
	public static final int AT_RULE = 1;
	public static final int BLOCK = 2;
	public static final int BLOCK_VALUE = 3;
}

//
// Rules
//
WhiteSpace
	: WS { $type = TEXT; }
	;

Comment
	: '/*' .* '*/' { $type = COMMENT; }
	;

LBrace
	: {state() == STATE_ROOT}? => '{' { $type = PUNCTUATION; pushState(BLOCK); }
	;

ClassIdentifier
	: {state() == STATE_ROOT}? => '.' ID { $type = NAME_CLASS; }
	;

HashIdentifier
	: {state() == STATE_ROOT}? => '#' ID { $type = NAME_FUNCTION; }
	;

Identifier
	: {state() == STATE_ROOT}? => ID { $type = NAME_TAG; }
	;

AtKeyword
	: {state() == STATE_ROOT}? => '@' ID { $type = KEYWORD; pushState(AT_RULE); }
	;

Operator
	: {state() == STATE_ROOT}? => ('~'|'^'|'*'|'!'|'%'|'&'|'['|']'|'('|')'|'<'|'>'|'|'|'+'|'='|'@'|':'|';'|','|'.'|'/'|'?'|'-') { $type = OPERATOR; }
	;

RBrace
	: {state() == BLOCK}? => '}' { $type = PUNCTUATION; popState(); }
	| {state() == BLOCK_VALUE}? => '}' { $type = PUNCTUATION; popState(); }
	;

Property
	: {state() == BLOCK}? => id=ID ws=WS* col=COLON {
		setTypeAndEmit($id, NAME_PROPERTY); // $id.text is attributes | vendor_prefixes
		setTypeAndEmit($ws, TEXT);
		setTypeAndEmit($col, PUNCTUATION);
		pushState(BLOCK_VALUE);
	}
	;

SemiColon
	: {state() == BLOCK_VALUE} ';'  { $type = PUNCTUATION; popState(); }
	;

Important
	: {state() == BLOCK_VALUE} '!important'  { $type = COMMENT_PREPROC; }
	;

AtValue
	: {state() == BLOCK_VALUE} '@' .*? { $type = COMMENT_PREPROC; }
	;

//
// FRAGMENTS
//
fragment WS				: (' '|'\t'|'\f'|'\r'|'\n');
fragment ID				: ('a'..'z'|'0'..'9'|'_'|'-')+;
fragment COLON			: ':';