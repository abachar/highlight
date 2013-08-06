package fr.abachar.highlight;

import java.util.HashMap;
import java.util.Map;

/**
 * @author abachar
 * @see "org.springframework.web.util.HtmlUtils"
 */
public class HtmlUtils {

    /**
     * Turn special characters into HTML character references.
     *
     * @param input the (unescaped) input string
     * @return the escaped string
     */
    public static String htmlEscape(String input) {
        if (input == null) {
            return null;
        }

        StringBuilder escaped = new StringBuilder(input.length() * 2);
        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);
            String reference = convertToReference(character);
            if (reference != null) {
                escaped.append(reference);
            } else {
                escaped.append(character);
            }
        }
        return escaped.toString();
    }

    /**
     * Return the reference mapped to the given character or null.
     */
    private static String convertToReference(char character) {
        if (character < 1000 || (character >= 8000 && character < 10000)) {
            int index = (character < 1000 ? character : character - 7000);
            String entityReference = CHARACTER_TO_ENTITY_REFERENCE_MAP.get(index);
            if (entityReference != null) {
                return entityReference;
            }
        }
        return null;
    }

    /**
     *
     */
    private final static Map<Integer, String> CHARACTER_TO_ENTITY_REFERENCE_MAP;

    static {
        CHARACTER_TO_ENTITY_REFERENCE_MAP = new HashMap<Integer, String>();
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(160, "&nbsp;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(161, "&iexcl;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(162, "&cent;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(163, "&pound;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(164, "&curren;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(165, "&yen;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(166, "&brvbar;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(167, "&sect;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(168, "&uml;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(169, "&copy;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(170, "&ordf;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(171, "&laquo;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(172, "&not;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(173, "&shy;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(174, "&reg;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(175, "&macr;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(176, "&deg;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(177, "&plusmn;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(178, "&sup2;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(179, "&sup3;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(180, "&acute;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(181, "&micro;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(182, "&para;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(183, "&middot;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(184, "&cedil;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(185, "&sup1;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(186, "&ordm;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(187, "&raquo;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(188, "&frac14;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(189, "&frac12;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(190, "&frac34;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(191, "&iquest;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(192, "&Agrave;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(193, "&Aacute;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(194, "&Acirc;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(195, "&Atilde;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(196, "&Auml;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(197, "&Aring;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(198, "&AElig;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(199, "&Ccedil;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(200, "&Egrave;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(201, "&Eacute;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(202, "&Ecirc;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(203, "&Euml;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(204, "&Igrave;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(205, "&Iacute;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(206, "&Icirc;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(207, "&Iuml;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(208, "&ETH;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(209, "&Ntilde;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(210, "&Ograve;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(211, "&Oacute;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(212, "&Ocirc;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(213, "&Otilde;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(214, "&Ouml;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(215, "&times;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(216, "&Oslash;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(217, "&Ugrave;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(218, "&Uacute;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(219, "&Ucirc;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(220, "&Uuml;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(221, "&Yacute;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(222, "&THORN;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(223, "&szlig;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(224, "&agrave;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(225, "&aacute;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(226, "&acirc;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(227, "&atilde;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(228, "&auml;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(229, "&aring;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(230, "&aelig;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(231, "&ccedil;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(232, "&egrave;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(233, "&eacute;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(234, "&ecirc;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(235, "&euml;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(236, "&igrave;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(237, "&iacute;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(238, "&icirc;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(239, "&iuml;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(240, "&eth;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(241, "&ntilde;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(242, "&ograve;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(243, "&oacute;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(244, "&ocirc;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(245, "&otilde;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(246, "&ouml;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(247, "&divide;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(248, "&oslash;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(249, "&ugrave;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(250, "&uacute;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(251, "&ucirc;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(252, "&uuml;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(253, "&yacute;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(254, "&thorn;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(255, "&yuml;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(402, "&fnof;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(913, "&Alpha;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(914, "&Beta;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(915, "&Gamma;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(916, "&Delta;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(917, "&Epsilon;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(918, "&Zeta;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(919, "&Eta;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(920, "&Theta;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(921, "&Iota;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(922, "&Kappa;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(923, "&Lambda;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(924, "&Mu;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(925, "&Nu;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(926, "&Xi;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(927, "&Omicron;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(928, "&Pi;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(929, "&Rho;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(931, "&Sigma;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(932, "&Tau;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(933, "&Upsilon;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(934, "&Phi;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(935, "&Chi;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(936, "&Psi;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(937, "&Omega;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(945, "&alpha;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(946, "&beta;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(947, "&gamma;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(948, "&delta;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(949, "&epsilon;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(950, "&zeta;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(951, "&eta;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(952, "&theta;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(953, "&iota;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(954, "&kappa;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(955, "&lambda;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(956, "&mu;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(957, "&nu;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(958, "&xi;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(959, "&omicron;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(960, "&pi;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(961, "&rho;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(962, "&sigmaf;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(963, "&sigma;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(964, "&tau;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(965, "&upsilon;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(966, "&phi;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(967, "&chi;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(968, "&psi;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(969, "&omega;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(977, "&thetasym;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(978, "&upsih;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(982, "&piv;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8226, "&bull;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8230, "&hellip;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8242, "&prime;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8243, "&Prime;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8254, "&oline;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8260, "&frasl;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8472, "&weierp;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8465, "&image;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8476, "&real;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8482, "&trade;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8501, "&alefsym;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8592, "&larr;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8593, "&uarr;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8594, "&rarr;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8595, "&darr;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8596, "&harr;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8629, "&crarr;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8656, "&lArr;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8657, "&uArr;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8658, "&rArr;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8659, "&dArr;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8660, "&hArr;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8704, "&forall;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8706, "&part;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8707, "&exist;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8709, "&empty;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8711, "&nabla;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8712, "&isin;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8713, "&notin;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8715, "&ni;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8719, "&prod;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8721, "&sum;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8722, "&minus;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8727, "&lowast;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8730, "&radic;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8733, "&prop;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8734, "&infin;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8736, "&ang;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8743, "&and;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8744, "&or;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8745, "&cap;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8746, "&cup;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8747, "&int;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8756, "&there4;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8764, "&sim;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8773, "&cong;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8776, "&asymp;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8800, "&ne;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8801, "&equiv;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8804, "&le;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8805, "&ge;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8834, "&sub;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8835, "&sup;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8836, "&nsub;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8838, "&sube;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8839, "&supe;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8853, "&oplus;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8855, "&otimes;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8869, "&perp;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8901, "&sdot;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8968, "&lceil;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8969, "&rceil;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8970, "&lfloor;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8971, "&rfloor;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(9001, "&lang;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(9002, "&rang;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(9674, "&loz;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(9824, "&spades;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(9827, "&clubs;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(9829, "&hearts;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(9830, "&diams;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(34, "&quot;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(38, "&amp;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(60, "&lt;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(62, "&gt;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(338, "&OElig;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(339, "&oelig;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(352, "&Scaron;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(353, "&scaron;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(376, "&Yuml;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(710, "&circ;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(732, "&tilde;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8194, "&ensp;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8195, "&emsp;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8201, "&thinsp;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8204, "&zwnj;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8205, "&zwj;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8206, "&lrm;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8207, "&rlm;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8211, "&ndash;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8212, "&mdash;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8216, "&lsquo;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8217, "&rsquo;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8218, "&sbquo;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8220, "&ldquo;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8221, "&rdquo;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8222, "&bdquo;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8224, "&dagger;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8225, "&Dagger;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8240, "&permil;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8249, "&lsaquo;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8250, "&rsaquo;");
        CHARACTER_TO_ENTITY_REFERENCE_MAP.put(8364, "&euro;");
    }
}
