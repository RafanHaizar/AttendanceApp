package com.itextpdf.styledxmlparser.jsoup.parser;

import com.itextpdf.styledxmlparser.jsoup.helper.StringUtil;
import com.itextpdf.styledxmlparser.jsoup.helper.Validate;

public class TokenQueue {
    private static final char ESC = '\\';
    private int pos = 0;
    private String queue;

    public TokenQueue(String data) {
        Validate.notNull(data);
        this.queue = data;
    }

    public boolean isEmpty() {
        return remainingLength() == 0;
    }

    private int remainingLength() {
        return this.queue.length() - this.pos;
    }

    public char peek() {
        if (isEmpty()) {
            return 0;
        }
        return this.queue.charAt(this.pos);
    }

    public void addFirst(Character c) {
        addFirst(c.toString());
    }

    public void addFirst(String seq) {
        this.queue = seq + this.queue.substring(this.pos);
        this.pos = 0;
    }

    public boolean matches(String seq) {
        return this.queue.regionMatches(true, this.pos, seq, 0, seq.length());
    }

    public boolean matchesCS(String seq) {
        return this.queue.startsWith(seq, this.pos);
    }

    public boolean matchesAny(String... seq) {
        for (String s : seq) {
            if (matches(s)) {
                return true;
            }
        }
        return false;
    }

    public boolean matchesAny(char... seq) {
        if (isEmpty()) {
            return false;
        }
        for (char c : seq) {
            if (this.queue.charAt(this.pos) == c) {
                return true;
            }
        }
        return false;
    }

    public boolean matchesStartTag() {
        return remainingLength() >= 2 && this.queue.charAt(this.pos) == '<' && Character.isLetter(this.queue.charAt(this.pos + 1));
    }

    public boolean matchChomp(String seq) {
        if (!matches(seq)) {
            return false;
        }
        this.pos += seq.length();
        return true;
    }

    public boolean matchesWhitespace() {
        return !isEmpty() && StringUtil.isWhitespace(this.queue.charAt(this.pos));
    }

    public boolean matchesWord() {
        return !isEmpty() && Character.isLetterOrDigit(this.queue.charAt(this.pos));
    }

    public void advance() {
        if (!isEmpty()) {
            this.pos++;
        }
    }

    public char consume() {
        String str = this.queue;
        int i = this.pos;
        this.pos = i + 1;
        return str.charAt(i);
    }

    public void consume(String seq) {
        if (matches(seq)) {
            int len = seq.length();
            if (len <= remainingLength()) {
                this.pos += len;
                return;
            }
            throw new IllegalStateException("Queue not long enough to consume sequence");
        }
        throw new IllegalStateException("Queue did not match expected sequence");
    }

    public String consumeTo(String seq) {
        int offset = this.queue.indexOf(seq, this.pos);
        if (offset == -1) {
            return remainder();
        }
        String consumed = this.queue.substring(this.pos, offset);
        this.pos += consumed.length();
        return consumed;
    }

    public String consumeToIgnoreCase(String seq) {
        int start = this.pos;
        String first = seq.substring(0, 1);
        boolean canScan = first.toLowerCase().equals(first.toUpperCase());
        while (!isEmpty() && !matches(seq)) {
            if (canScan) {
                int indexOf = this.queue.indexOf(first, this.pos);
                int i = this.pos;
                int skip = indexOf - i;
                if (skip == 0) {
                    this.pos = i + 1;
                } else if (skip < 0) {
                    this.pos = this.queue.length();
                } else {
                    this.pos = i + skip;
                }
            } else {
                this.pos++;
            }
        }
        return this.queue.substring(start, this.pos);
    }

    public String consumeToAny(String... seq) {
        int start = this.pos;
        while (!isEmpty() && !matchesAny(seq)) {
            this.pos++;
        }
        return this.queue.substring(start, this.pos);
    }

    public String chompTo(String seq) {
        String data = consumeTo(seq);
        matchChomp(seq);
        return data;
    }

    public String chompToIgnoreCase(String seq) {
        String data = consumeToIgnoreCase(seq);
        matchChomp(seq);
        return data;
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x006b A[EDGE_INSN: B:35:0x006b->B:31:0x006b ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String chompBalanced(char r8, char r9) {
        /*
            r7 = this;
            r0 = -1
            r1 = -1
            r2 = 0
            r3 = 0
            r4 = 0
        L_0x0005:
            boolean r5 = r7.isEmpty()
            if (r5 == 0) goto L_0x000c
            goto L_0x006b
        L_0x000c:
            char r5 = r7.consume()
            java.lang.Character r5 = java.lang.Character.valueOf(r5)
            if (r3 == 0) goto L_0x001a
            r6 = 92
            if (r3 == r6) goto L_0x005f
        L_0x001a:
            r6 = 39
            java.lang.Character r6 = java.lang.Character.valueOf(r6)
            boolean r6 = r5.equals(r6)
            if (r6 != 0) goto L_0x0032
            r6 = 34
            java.lang.Character r6 = java.lang.Character.valueOf(r6)
            boolean r6 = r5.equals(r6)
            if (r6 == 0) goto L_0x003e
        L_0x0032:
            char r6 = r5.charValue()
            if (r6 == r8) goto L_0x003e
            if (r4 != 0) goto L_0x003c
            r6 = 1
            goto L_0x003d
        L_0x003c:
            r6 = 0
        L_0x003d:
            r4 = r6
        L_0x003e:
            if (r4 == 0) goto L_0x0041
            goto L_0x0069
        L_0x0041:
            java.lang.Character r6 = java.lang.Character.valueOf(r8)
            boolean r6 = r5.equals(r6)
            if (r6 == 0) goto L_0x0053
            int r2 = r2 + 1
            r6 = -1
            if (r0 != r6) goto L_0x005f
            int r0 = r7.pos
            goto L_0x005f
        L_0x0053:
            java.lang.Character r6 = java.lang.Character.valueOf(r9)
            boolean r6 = r5.equals(r6)
            if (r6 == 0) goto L_0x005f
            int r2 = r2 + -1
        L_0x005f:
            if (r2 <= 0) goto L_0x0065
            if (r3 == 0) goto L_0x0065
            int r1 = r7.pos
        L_0x0065:
            char r3 = r5.charValue()
        L_0x0069:
            if (r2 > 0) goto L_0x0005
        L_0x006b:
            if (r1 < 0) goto L_0x0074
            java.lang.String r5 = r7.queue
            java.lang.String r5 = r5.substring(r0, r1)
            goto L_0x0076
        L_0x0074:
            java.lang.String r5 = ""
        L_0x0076:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.styledxmlparser.jsoup.parser.TokenQueue.chompBalanced(char, char):java.lang.String");
    }

    public static String unescape(String in) {
        StringBuilder out = new StringBuilder();
        char last = 0;
        for (char c : in.toCharArray()) {
            if (c != '\\') {
                out.append(c);
            } else if (last != 0 && last == '\\') {
                out.append(c);
            }
            last = c;
        }
        return out.toString();
    }

    public boolean consumeWhitespace() {
        boolean seen = false;
        while (matchesWhitespace()) {
            this.pos++;
            seen = true;
        }
        return seen;
    }

    public String consumeWord() {
        int start = this.pos;
        while (matchesWord()) {
            this.pos++;
        }
        return this.queue.substring(start, this.pos);
    }

    public String consumeTagName() {
        int start = this.pos;
        while (!isEmpty() && (matchesWord() || matchesAny(':', '_', '-'))) {
            this.pos++;
        }
        return this.queue.substring(start, this.pos);
    }

    public String consumeElementSelector() {
        int start = this.pos;
        while (!isEmpty() && (matchesWord() || matchesAny('|', '_', '-'))) {
            this.pos++;
        }
        return this.queue.substring(start, this.pos);
    }

    public String consumeCssIdentifier() {
        int start = this.pos;
        while (!isEmpty() && (matchesWord() || matchesAny('-', '_'))) {
            this.pos++;
        }
        return this.queue.substring(start, this.pos);
    }

    public String consumeAttributeKey() {
        int start = this.pos;
        while (!isEmpty() && (matchesWord() || matchesAny('-', '_', ':'))) {
            this.pos++;
        }
        return this.queue.substring(start, this.pos);
    }

    public String remainder() {
        String str = this.queue;
        String remainder = str.substring(this.pos, str.length());
        this.pos = this.queue.length();
        return remainder;
    }

    public String toString() {
        return this.queue.substring(this.pos);
    }
}
