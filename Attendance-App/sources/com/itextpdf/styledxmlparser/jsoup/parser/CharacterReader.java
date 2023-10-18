package com.itextpdf.styledxmlparser.jsoup.parser;

import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
import java.util.Arrays;

final class CharacterReader {
    static final char EOF = '￿';
    private static final int maxCacheLen = 12;
    private final char[] input;
    private final int length;
    private int mark = 0;
    private int pos = 0;
    private final String[] stringCache = new String[512];

    CharacterReader(String input2) {
        Validate.notNull(input2);
        char[] charArray = input2.toCharArray();
        this.input = charArray;
        this.length = charArray.length;
    }

    /* access modifiers changed from: package-private */
    public int pos() {
        return this.pos;
    }

    /* access modifiers changed from: package-private */
    public boolean isEmpty() {
        return this.pos >= this.length;
    }

    /* access modifiers changed from: package-private */
    public char current() {
        int i = this.pos;
        if (i >= this.length) {
            return 65535;
        }
        return this.input[i];
    }

    /* access modifiers changed from: package-private */
    public char consume() {
        int i = this.pos;
        char val = i >= this.length ? 65535 : this.input[i];
        this.pos = i + 1;
        return val;
    }

    /* access modifiers changed from: package-private */
    public void unconsume() {
        this.pos--;
    }

    /* access modifiers changed from: package-private */
    public void advance() {
        this.pos++;
    }

    /* access modifiers changed from: package-private */
    public void mark() {
        this.mark = this.pos;
    }

    /* access modifiers changed from: package-private */
    public void rewindToMark() {
        this.pos = this.mark;
    }

    /* access modifiers changed from: package-private */
    public String consumeAsString() {
        char[] cArr = this.input;
        int i = this.pos;
        this.pos = i + 1;
        return new String(cArr, i, 1);
    }

    /* access modifiers changed from: package-private */
    public int nextIndexOf(char c) {
        for (int i = this.pos; i < this.length; i++) {
            if (c == this.input[i]) {
                return i - this.pos;
            }
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public int nextIndexOf(CharSequence seq) {
        char startChar = seq.charAt(0);
        int offset = this.pos;
        while (offset < this.length) {
            if (startChar != this.input[offset]) {
                do {
                    offset++;
                    if (offset >= this.length) {
                        break;
                    }
                } while (startChar == this.input[offset]);
            }
            int i = offset + 1;
            int last = (seq.length() + i) - 1;
            int i2 = this.length;
            if (offset < i2 && last <= i2) {
                int j = 1;
                while (i < last && seq.charAt(j) == this.input[i]) {
                    i++;
                    j++;
                }
                if (i == last) {
                    return offset - this.pos;
                }
            }
            offset++;
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public String consumeTo(char c) {
        int offset = nextIndexOf(c);
        if (offset == -1) {
            return consumeToEnd();
        }
        String consumed = cacheString(this.pos, offset);
        this.pos += offset;
        return consumed;
    }

    /* access modifiers changed from: package-private */
    public String consumeTo(String seq) {
        int offset = nextIndexOf((CharSequence) seq);
        if (offset == -1) {
            return consumeToEnd();
        }
        String consumed = cacheString(this.pos, offset);
        this.pos += offset;
        return consumed;
    }

    /* access modifiers changed from: package-private */
    public String consumeToAny(char... chars) {
        int start = this.pos;
        int remaining = this.length;
        char[] val = this.input;
        loop0:
        while (this.pos < remaining) {
            for (char c : chars) {
                if (val[this.pos] == c) {
                    break loop0;
                }
            }
            this.pos++;
        }
        int i = this.pos;
        return i > start ? cacheString(start, i - start) : "";
    }

    /* access modifiers changed from: package-private */
    public String consumeToAnySorted(char... chars) {
        int start = this.pos;
        int remaining = this.length;
        char[] val = this.input;
        while (true) {
            int i = this.pos;
            if (i >= remaining || Arrays.binarySearch(chars, val[i]) >= 0) {
                int i2 = this.pos;
            } else {
                this.pos++;
            }
        }
        int i22 = this.pos;
        return i22 > start ? cacheString(start, i22 - start) : "";
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x001e  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0024 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String consumeData() {
        /*
            r6 = this;
            int r0 = r6.pos
            int r1 = r6.length
            char[] r2 = r6.input
        L_0x0006:
            int r3 = r6.pos
            if (r3 >= r1) goto L_0x001c
            char r4 = r2[r3]
            r5 = 38
            if (r4 == r5) goto L_0x001c
            r5 = 60
            if (r4 == r5) goto L_0x001c
            if (r4 != 0) goto L_0x0017
            goto L_0x001c
        L_0x0017:
            int r3 = r3 + 1
            r6.pos = r3
            goto L_0x0006
        L_0x001c:
            if (r3 <= r0) goto L_0x0024
            int r3 = r3 - r0
            java.lang.String r3 = r6.cacheString(r0, r3)
            goto L_0x0026
        L_0x0024:
            java.lang.String r3 = ""
        L_0x0026:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.styledxmlparser.jsoup.parser.CharacterReader.consumeData():java.lang.String");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0038 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String consumeTagName() {
        /*
            r6 = this;
            int r0 = r6.pos
            int r1 = r6.length
            char[] r2 = r6.input
        L_0x0006:
            int r3 = r6.pos
            if (r3 >= r1) goto L_0x0030
            char r4 = r2[r3]
            r5 = 9
            if (r4 == r5) goto L_0x0030
            r5 = 10
            if (r4 == r5) goto L_0x0030
            r5 = 13
            if (r4 == r5) goto L_0x0030
            r5 = 12
            if (r4 == r5) goto L_0x0030
            r5 = 32
            if (r4 == r5) goto L_0x0030
            r5 = 47
            if (r4 == r5) goto L_0x0030
            r5 = 62
            if (r4 == r5) goto L_0x0030
            if (r4 != 0) goto L_0x002b
            goto L_0x0030
        L_0x002b:
            int r3 = r3 + 1
            r6.pos = r3
            goto L_0x0006
        L_0x0030:
            if (r3 <= r0) goto L_0x0038
            int r3 = r3 - r0
            java.lang.String r3 = r6.cacheString(r0, r3)
            goto L_0x003a
        L_0x0038:
            java.lang.String r3 = ""
        L_0x003a:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.styledxmlparser.jsoup.parser.CharacterReader.consumeTagName():java.lang.String");
    }

    /* access modifiers changed from: package-private */
    public String consumeToEnd() {
        int i = this.pos;
        String data = cacheString(i, this.length - i);
        this.pos = this.length;
        return data;
    }

    /* access modifiers changed from: package-private */
    public String consumeLetterSequence() {
        char c;
        int start = this.pos;
        while (true) {
            int i = this.pos;
            if (i < this.length && (((c = this.input[i]) >= 'A' && c <= 'Z') || ((c >= 'a' && c <= 'z') || Character.isLetter(c)))) {
                this.pos++;
            }
        }
        return cacheString(start, this.pos - start);
    }

    /* access modifiers changed from: package-private */
    public String consumeLetterThenDigitSequence() {
        char c;
        int start = this.pos;
        while (true) {
            int i = this.pos;
            if (i < this.length && (((c = this.input[i]) >= 'A' && c <= 'Z') || ((c >= 'a' && c <= 'z') || Character.isLetter(c)))) {
                this.pos++;
            }
        }
        while (!isEmpty() && (c = this.input[r2]) >= '0' && c <= '9') {
            this.pos = (r2 = this.pos) + 1;
        }
        return cacheString(start, this.pos - start);
    }

    /* access modifiers changed from: package-private */
    public String consumeHexSequence() {
        int i;
        char c;
        int start = this.pos;
        while (true) {
            i = this.pos;
            if (i < this.length && (((c = this.input[i]) >= '0' && c <= '9') || ((c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f')))) {
                this.pos = i + 1;
            }
        }
        return cacheString(start, i - start);
    }

    /* access modifiers changed from: package-private */
    public String consumeDigitSequence() {
        int i;
        char c;
        int start = this.pos;
        while (true) {
            i = this.pos;
            if (i < this.length && (c = this.input[i]) >= '0' && c <= '9') {
                this.pos = i + 1;
            }
        }
        return cacheString(start, i - start);
    }

    /* access modifiers changed from: package-private */
    public boolean matches(char c) {
        return !isEmpty() && this.input[this.pos] == c;
    }

    /* access modifiers changed from: package-private */
    public boolean matches(String seq) {
        int scanLength = seq.length();
        if (scanLength > this.length - this.pos) {
            return false;
        }
        for (int offset = 0; offset < scanLength; offset++) {
            if (seq.charAt(offset) != this.input[this.pos + offset]) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean matchesIgnoreCase(String seq) {
        int scanLength = seq.length();
        if (scanLength > this.length - this.pos) {
            return false;
        }
        for (int offset = 0; offset < scanLength; offset++) {
            if (Character.toUpperCase(seq.charAt(offset)) != Character.toUpperCase(this.input[this.pos + offset])) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean matchesAny(char... seq) {
        if (isEmpty()) {
            return false;
        }
        char c = this.input[this.pos];
        for (char seek : seq) {
            if (seek == c) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean matchesAnySorted(char[] seq) {
        return !isEmpty() && Arrays.binarySearch(seq, this.input[this.pos]) >= 0;
    }

    /* access modifiers changed from: package-private */
    public boolean matchesLetter() {
        if (isEmpty()) {
            return false;
        }
        char c = this.input[this.pos];
        if ((c < 'A' || c > 'Z') && ((c < 'a' || c > 'z') && !Character.isLetter(c))) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean matchesDigit() {
        char c;
        if (!isEmpty() && (c = this.input[this.pos]) >= '0' && c <= '9') {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean matchConsume(String seq) {
        if (!matches(seq)) {
            return false;
        }
        this.pos += seq.length();
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean matchConsumeIgnoreCase(String seq) {
        if (!matchesIgnoreCase(seq)) {
            return false;
        }
        this.pos += seq.length();
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean containsIgnoreCase(String seq) {
        return nextIndexOf((CharSequence) seq.toLowerCase()) > -1 || nextIndexOf((CharSequence) seq.toUpperCase()) > -1;
    }

    public String toString() {
        char[] cArr = this.input;
        int i = this.pos;
        return new String(cArr, i, this.length - i);
    }

    private String cacheString(int start, int count) {
        char[] val = this.input;
        String[] cache = this.stringCache;
        if (count > 12) {
            return new String(val, start, count);
        }
        int hash = 0;
        int offset = start;
        int i = 0;
        while (i < count) {
            hash = (hash * 31) + val[offset];
            i++;
            offset++;
        }
        int index = (cache.length - 1) & hash;
        String cached = cache[index];
        if (cached == null) {
            String cached2 = new String(val, start, count);
            cache[index] = cached2;
            return cached2;
        } else if (rangeEquals(start, count, cached)) {
            return cached;
        } else {
            String cached3 = new String(val, start, count);
            cache[index] = cached3;
            return cached3;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean rangeEquals(int start, int i, String cached) {
        if (i != cached.length()) {
            return false;
        }
        char[] one = this.input;
        int i2 = start;
        int j = 0;
        while (true) {
            int count = i - 1;
            if (i == 0) {
                return true;
            }
            int i3 = i2 + 1;
            int j2 = j + 1;
            if (one[i2] != cached.charAt(j)) {
                return false;
            }
            i2 = i3;
            i = count;
            j = j2;
        }
    }
}
