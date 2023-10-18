package com.itextpdf.styledxmlparser.util;

import java.util.HashSet;
import java.util.Set;

public class WhiteSpaceUtil {
    private static final Set<Character> EM_SPACES;

    static {
        HashSet hashSet = new HashSet();
        EM_SPACES = hashSet;
        hashSet.add(8194);
        hashSet.add(8195);
        hashSet.add(8201);
    }

    public static String collapseConsecutiveSpaces(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!isNonEmSpace(s.charAt(i))) {
                sb.append(s.charAt(i));
            } else if (sb.length() == 0 || !isNonEmSpace(sb.charAt(sb.length() - 1))) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public static boolean isNonEmSpace(char ch) {
        return Character.isWhitespace(ch) && !EM_SPACES.contains(Character.valueOf(ch));
    }
}
