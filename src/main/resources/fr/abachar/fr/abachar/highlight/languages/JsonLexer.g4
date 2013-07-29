lexer grammar JsonLexer;

@header {
    import fr.abachar.highlight.fragment.Fragment;
    import fr.abachar.highlight.fragment.Type;
    import java.util.List;
    import java.util.ArrayList;
}

@members {
    Type tokenType;
}

COLON   : ':';
COMMA   : ',';
LBRACE  : '{';
RBRACE  : '}';
LBRACKET: '[';
RBRACKET: ']';
LITERAL : 'true' | 'false' | 'null';

fragment DOT         : '.';
fragment DIGIT       : '0' .. '9';
fragment HEX_DIGIT   : ('0' .. '9' | 'A' .. 'F' | 'a' .. 'f');
fragment UNICODE_CHAR: ~('"'| '\\');
fragment STRING_CHAR :  UNICODE_CHAR | ESC_SEQ;
fragment ESC_SEQ     : '\\' ('\"' | '\\' | '/' | 'b' | 'f' | 'n' | 'r' | 't' | 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT);
fragment INT         : '-'? ('0' | '1'..'9' DIGIT*);
fragment FRAC        : DOT DIGIT+;
fragment EXP         : ('e' | 'E') ('+' | '-')? DIGIT+;

WS: (' ' | '\r' | '\t' | '\u000C' | '\n' ) -> channel(HIDDEN);

STRING : '"' STRING_CHAR* '"';
INTEGER: INT;
DOUBLE : INT (FRAC EXP? | EXP);