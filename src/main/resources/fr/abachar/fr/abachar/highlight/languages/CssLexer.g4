lexer grammar CssLexer;

//
// RULES
//
BOM:            '\uFEFF';
IDENT:          Ident;
HASH:           '#' Name;
CHARSET:        '@charset ';
IMPORT:         '@' I M P O R T;
NAMESPACE:      '@' N A M E S P A C E;
MEDIA:          '@' M E D I A;
PAGE:           '@' P A G E;
KEYFRAMES:      '@' K E Y F R A M E S;
LEGACY_SUPPORT: '@-' W L I T E '-' L E G A C Y '-' S U P P O R T;
ATKEYWORD:      '@' Ident;
MS_EXPR_FUNC:   E X P R E S S I O N Group;
NOT_FUNC:       N O T '(';
AND:            A N D;
NOT:            N O T;
ONLY:           O N L Y;
FUNCTION:       Ident '(';
PRIO:           '!' Ident;
STRING:         String;
NUMBER:         Num;
PERCENTAGE:     Num '%';
DIMENSION:      Num Ident;
URL_FUNC:       U R L '(' WS* ( String | Url )? WS* ')';
UNICODE_RANGE:  U '+' Hex Hex? Hex? Hex? Hex? Hex? ( '-' Hex Hex? Hex? Hex? Hex? Hex? )?;
CDO:            '<!--';
CDC:            '-->';
LPAREN:         '(';
RPAREN:         ')';
ASTERISK:       '*';
PLUS:           '+';
COMMA:          ',';
MINUS:          '-';
DOT:            '.';
SLASH:          '/';
COLON:          ':';
SEMICOLON:      ';';
LESS:           '<';
GREATER:        '>';
LBRACKET:       '[';
BACKSLASH:      '\\';
RBRACKET:       ']';
LBRACE:         '{';
VBAR:           '|';
RBRACE:         '}';
TILDE:          '~';
MATCH:          [!#$%&*+./:=?@^|~-]? '=';
WS:             [ \t\r\n\f]+;
COMMENT:        '/*' .*? '*/' -> type(COMMENT);
DELIM:          .;

//
// FRAGMENTS
//
fragment Hex: [0-9a-fA-F];
fragment NonAscii: [\u0238-\uFFFF];
fragment Unicode: '\\' Hex Hex? Hex? Hex? Hex? Hex? ( '\r\n' | [ \t\r\n\f] )?;
fragment Escape: Unicode | '\\' ~[\n\r\f0-9a-fA-F];
fragment NmStart: '-'? ( [_a-zA-Z] | NonAscii | Escape );
fragment NmChar: ( [_a-zA-Z0-9-] | NonAscii | Escape );
fragment Ident: NmStart NmChar*;
fragment Name: NmChar+;
fragment Num: [+-]? ( [0-9]+ | [0-9]* '.' [0-9]+ );
fragment String: '"' ( ~[\n\r\f\\"] | '\\' ( '\r\n' | [\r\n\f] ) | Escape )* '"' | '\'' ( ~[\n\r\f\\'] | '\\' ( '\r\n' | [\r\n\f] ) | Escape )* '\'';
fragment Url: ( [!#$%&*-\[\]-~] | NonAscii | Escape )+;
fragment Group: '(' ( Group | ~'('+ )* ')';

fragment A: [aA];
fragment B: [bB];
fragment C: [cC];
fragment D: [dD];
fragment E: [eE];
fragment F: [fF];
fragment G: [gG];
fragment H: [hH];
fragment I: [iI];
fragment K: [kK];
fragment L: [lL];
fragment M: [mM];
fragment N: [nN];
fragment O: [oO];
fragment P: [pP];
fragment R: [rR];
fragment S: [sS];
fragment T: [tT];
fragment U: [uU];
fragment W: [wW];
fragment X: [xX];
fragment Y: [yY];
fragment Z: [zZ];