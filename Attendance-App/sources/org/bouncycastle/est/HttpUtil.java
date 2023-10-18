package org.bouncycastle.est;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import kotlin.text.Typography;

class HttpUtil {

    static class Headers extends HashMap<String, String[]> {
        private String actualKey(String str) {
            if (containsKey(str)) {
                return str;
            }
            for (String str2 : keySet()) {
                if (str.equalsIgnoreCase(str2)) {
                    return str2;
                }
            }
            return null;
        }

        private String[] copy(String[] strArr) {
            int length = strArr.length;
            String[] strArr2 = new String[length];
            System.arraycopy(strArr, 0, strArr2, 0, length);
            return strArr2;
        }

        private boolean hasHeader(String str) {
            return actualKey(str) != null;
        }

        public void add(String str, String str2) {
            put(str, HttpUtil.append((String[]) get(str), str2));
        }

        public Object clone() {
            Headers headers = new Headers();
            for (Map.Entry entry : entrySet()) {
                headers.put((String) entry.getKey(), copy((String[]) entry.getValue()));
            }
            return headers;
        }

        public void ensureHeader(String str, String str2) {
            if (!containsKey(str)) {
                set(str, str2);
            }
        }

        public String getFirstValue(String str) {
            String[] values = getValues(str);
            if (values == null || values.length <= 0) {
                return null;
            }
            return values[0];
        }

        public String[] getValues(String str) {
            String actualKey = actualKey(str);
            if (actualKey == null) {
                return null;
            }
            return (String[]) get(actualKey);
        }

        public void set(String str, String str2) {
            put(str, new String[]{str2});
        }
    }

    static class PartLexer {
        int last = 0;

        /* renamed from: p */
        int f641p = 0;
        private final String src;

        PartLexer(String str) {
            this.src = str;
        }

        private String consumeAlpha() {
            char charAt = this.src.charAt(this.f641p);
            while (this.f641p < this.src.length() && ((charAt >= 'a' && charAt <= 'z') || (charAt >= 'A' && charAt <= 'Z'))) {
                int i = this.f641p + 1;
                this.f641p = i;
                charAt = this.src.charAt(i);
            }
            String substring = this.src.substring(this.last, this.f641p);
            this.last = this.f641p;
            return substring;
        }

        private boolean consumeIf(char c) {
            if (this.f641p >= this.src.length() || this.src.charAt(this.f641p) != c) {
                return false;
            }
            this.f641p++;
            return true;
        }

        private String consumeUntil(char c) {
            while (this.f641p < this.src.length() && this.src.charAt(this.f641p) != c) {
                this.f641p++;
            }
            String substring = this.src.substring(this.last, this.f641p);
            this.last = this.f641p;
            return substring;
        }

        private void discard() {
            this.last = this.f641p;
        }

        private void discard(int i) {
            int i2 = this.f641p + i;
            this.f641p = i2;
            this.last = i2;
        }

        private void skipWhiteSpace() {
            while (this.f641p < this.src.length() && this.src.charAt(this.f641p) < '!') {
                this.f641p++;
            }
            this.last = this.f641p;
        }

        /* access modifiers changed from: package-private */
        public Map<String, String> Parse() {
            HashMap hashMap = new HashMap();
            while (this.f641p < this.src.length()) {
                skipWhiteSpace();
                String consumeAlpha = consumeAlpha();
                if (consumeAlpha.length() != 0) {
                    skipWhiteSpace();
                    if (consumeIf('=')) {
                        skipWhiteSpace();
                        if (consumeIf(Typography.quote)) {
                            discard();
                            String consumeUntil = consumeUntil(Typography.quote);
                            discard(1);
                            hashMap.put(consumeAlpha, consumeUntil);
                            skipWhiteSpace();
                            if (!consumeIf(',')) {
                                break;
                            }
                            discard();
                        } else {
                            throw new IllegalArgumentException("Expecting start quote: '\"'");
                        }
                    } else {
                        throw new IllegalArgumentException("Expecting assign: '='");
                    }
                } else {
                    throw new IllegalArgumentException("Expecting alpha label.");
                }
            }
            return hashMap;
        }
    }

    HttpUtil() {
    }

    public static String[] append(String[] strArr, String str) {
        if (strArr == null) {
            return new String[]{str};
        }
        int length = strArr.length;
        String[] strArr2 = new String[(length + 1)];
        System.arraycopy(strArr, 0, strArr2, 0, length);
        strArr2[length] = str;
        return strArr2;
    }

    static String mergeCSL(String str, Map<String, String> map) {
        StringWriter stringWriter = new StringWriter();
        stringWriter.write(str);
        stringWriter.write(32);
        boolean z = false;
        for (Map.Entry next : map.entrySet()) {
            if (!z) {
                z = true;
            } else {
                stringWriter.write(44);
            }
            stringWriter.write((String) next.getKey());
            stringWriter.write("=\"");
            stringWriter.write((String) next.getValue());
            stringWriter.write(34);
        }
        return stringWriter.toString();
    }

    static Map<String, String> splitCSL(String str, String str2) {
        String trim = str2.trim();
        if (trim.startsWith(str)) {
            trim = trim.substring(str.length());
        }
        return new PartLexer(trim).Parse();
    }
}
