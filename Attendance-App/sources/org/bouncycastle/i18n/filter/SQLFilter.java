package org.bouncycastle.i18n.filter;

public class SQLFilter implements Filter {
    public String doFilter(String str) {
        String str2;
        int i;
        StringBuffer stringBuffer = new StringBuffer(str);
        int i2 = 0;
        while (i2 < stringBuffer.length()) {
            switch (stringBuffer.charAt(i2)) {
                case 10:
                    i = i2 + 1;
                    str2 = "\\n";
                    break;
                case 13:
                    i = i2 + 1;
                    str2 = "\\r";
                    break;
                case '\"':
                    i = i2 + 1;
                    str2 = "\\\"";
                    break;
                case '\'':
                    i = i2 + 1;
                    str2 = "\\'";
                    break;
                case '-':
                    i = i2 + 1;
                    str2 = "\\-";
                    break;
                case '/':
                    i = i2 + 1;
                    str2 = "\\/";
                    break;
                case ';':
                    i = i2 + 1;
                    str2 = "\\;";
                    break;
                case '=':
                    i = i2 + 1;
                    str2 = "\\=";
                    break;
                case '\\':
                    i = i2 + 1;
                    str2 = "\\\\";
                    break;
            }
            stringBuffer.replace(i2, i, str2);
            i2 = i;
            i2++;
        }
        return stringBuffer.toString();
    }

    public String doFilterUrl(String str) {
        return doFilter(str);
    }
}
