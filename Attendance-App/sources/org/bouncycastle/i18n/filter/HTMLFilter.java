package org.bouncycastle.i18n.filter;

public class HTMLFilter implements Filter {
    public String doFilter(String str) {
        String str2;
        int i;
        StringBuffer stringBuffer = new StringBuffer(str);
        int i2 = 0;
        while (i2 < stringBuffer.length()) {
            switch (stringBuffer.charAt(i2)) {
                case '\"':
                    i = i2 + 1;
                    str2 = "&#34";
                    break;
                case '#':
                    i = i2 + 1;
                    str2 = "&#35";
                    break;
                case '%':
                    i = i2 + 1;
                    str2 = "&#37";
                    break;
                case '&':
                    i = i2 + 1;
                    str2 = "&#38";
                    break;
                case '\'':
                    i = i2 + 1;
                    str2 = "&#39";
                    break;
                case '(':
                    i = i2 + 1;
                    str2 = "&#40";
                    break;
                case ')':
                    i = i2 + 1;
                    str2 = "&#41";
                    break;
                case '+':
                    i = i2 + 1;
                    str2 = "&#43";
                    break;
                case '-':
                    i = i2 + 1;
                    str2 = "&#45";
                    break;
                case ';':
                    i = i2 + 1;
                    str2 = "&#59";
                    break;
                case '<':
                    i = i2 + 1;
                    str2 = "&#60";
                    break;
                case '>':
                    i = i2 + 1;
                    str2 = "&#62";
                    break;
                default:
                    i2 -= 3;
                    continue;
            }
            stringBuffer.replace(i2, i, str2);
            i2 += 4;
        }
        return stringBuffer.toString();
    }

    public String doFilterUrl(String str) {
        return doFilter(str);
    }
}
