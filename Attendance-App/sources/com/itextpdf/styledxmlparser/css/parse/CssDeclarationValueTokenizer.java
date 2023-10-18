package com.itextpdf.styledxmlparser.css.parse;

public class CssDeclarationValueTokenizer {
    private int functionDepth = 0;
    private boolean inString;
    private int index = -1;
    private String src;
    private char stringQuote;

    public enum TokenType {
        STRING,
        FUNCTION,
        COMMA,
        UNKNOWN
    }

    public CssDeclarationValueTokenizer(String propertyValue) {
        this.src = propertyValue;
    }

    public Token getNextValidToken() {
        Token token = getNextToken();
        while (token != null && !token.isString() && token.getValue().trim().isEmpty()) {
            token = getNextToken();
        }
        if (token != null && this.functionDepth > 0) {
            StringBuilder functionBuffer = new StringBuilder();
            while (token != null && this.functionDepth > 0) {
                processFunctionToken(token, functionBuffer);
                token = getNextToken();
            }
            this.functionDepth = 0;
            if (functionBuffer.length() != 0) {
                if (token != null) {
                    processFunctionToken(token, functionBuffer);
                }
                return new Token(functionBuffer.toString(), TokenType.FUNCTION);
            }
        }
        return token;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0153, code lost:
        return new com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer.Token(r0.toString(), com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer.TokenType.FUNCTION);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer.Token getNextToken() {
        /*
            r8 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            int r1 = r8.index
            java.lang.String r2 = r8.src
            int r2 = r2.length()
            r3 = 1
            int r2 = r2 - r3
            if (r1 < r2) goto L_0x0013
            r1 = 0
            return r1
        L_0x0013:
            boolean r1 = r8.inString
            if (r1 == 0) goto L_0x00ab
            r1 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
        L_0x001d:
            int r4 = r8.index
            int r4 = r4 + r3
            r8.index = r4
            java.lang.String r5 = r8.src
            int r5 = r5.length()
            if (r4 >= r5) goto L_0x00a9
            java.lang.String r4 = r8.src
            int r5 = r8.index
            char r4 = r4.charAt(r5)
            r5 = 0
            if (r1 == 0) goto L_0x008b
            boolean r6 = r8.isHexDigit(r4)
            if (r6 == 0) goto L_0x0046
            int r6 = r2.length()
            r7 = 6
            if (r6 >= r7) goto L_0x0046
            r2.append(r4)
            goto L_0x001d
        L_0x0046:
            int r6 = r2.length()
            if (r6 == 0) goto L_0x0086
            java.lang.String r6 = r2.toString()
            r7 = 16
            int r6 = java.lang.Integer.parseInt(r6, r7)
            boolean r7 = java.lang.Character.isValidCodePoint(r6)
            if (r7 == 0) goto L_0x0060
            r0.appendCodePoint(r6)
            goto L_0x0066
        L_0x0060:
            java.lang.String r7 = "�"
            r0.append(r7)
        L_0x0066:
            r2.setLength(r5)
            char r7 = r8.stringQuote
            if (r4 != r7) goto L_0x007b
            r8.inString = r5
            com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$Token r3 = new com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$Token
            java.lang.String r5 = r0.toString()
            com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$TokenType r7 = com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer.TokenType.STRING
            r3.<init>(r5, r7)
            return r3
        L_0x007b:
            boolean r5 = java.lang.Character.isWhitespace(r4)
            if (r5 != 0) goto L_0x0084
            r0.append(r4)
        L_0x0084:
            r1 = 0
            goto L_0x001d
        L_0x0086:
            r0.append(r4)
            r1 = 0
            goto L_0x001d
        L_0x008b:
            char r6 = r8.stringQuote
            if (r4 != r6) goto L_0x009d
            r8.inString = r5
            com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$Token r3 = new com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$Token
            java.lang.String r5 = r0.toString()
            com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$TokenType r6 = com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer.TokenType.STRING
            r3.<init>(r5, r6)
            return r3
        L_0x009d:
            r5 = 92
            if (r4 != r5) goto L_0x00a4
            r1 = 1
            goto L_0x001d
        L_0x00a4:
            r0.append(r4)
            goto L_0x001d
        L_0x00a9:
            goto L_0x0154
        L_0x00ab:
            int r1 = r8.index
            int r1 = r1 + r3
            r8.index = r1
            java.lang.String r2 = r8.src
            int r2 = r2.length()
            if (r1 >= r2) goto L_0x0154
            java.lang.String r1 = r8.src
            int r2 = r8.index
            char r1 = r1.charAt(r2)
            r2 = 40
            if (r1 != r2) goto L_0x00cd
            int r2 = r8.functionDepth
            int r2 = r2 + r3
            r8.functionDepth = r2
            r0.append(r1)
            goto L_0x00ab
        L_0x00cd:
            r2 = 41
            if (r1 != r2) goto L_0x00e9
            int r2 = r8.functionDepth
            int r2 = r2 - r3
            r8.functionDepth = r2
            r0.append(r1)
            int r2 = r8.functionDepth
            if (r2 != 0) goto L_0x00ab
            com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$Token r2 = new com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$Token
            java.lang.String r3 = r0.toString()
            com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$TokenType r4 = com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer.TokenType.FUNCTION
            r2.<init>(r3, r4)
            return r2
        L_0x00e9:
            r2 = 34
            if (r1 == r2) goto L_0x0144
            r2 = 39
            if (r1 != r2) goto L_0x00f2
            goto L_0x0144
        L_0x00f2:
            r2 = 44
            if (r1 != r2) goto L_0x011f
            boolean r2 = r8.inString
            if (r2 != 0) goto L_0x011f
            int r2 = r8.functionDepth
            if (r2 != 0) goto L_0x011f
            int r2 = r0.length()
            if (r2 != 0) goto L_0x010e
            com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$Token r2 = new com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$Token
            java.lang.String r3 = ","
            com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$TokenType r4 = com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer.TokenType.COMMA
            r2.<init>(r3, r4)
            return r2
        L_0x010e:
            int r2 = r8.index
            int r2 = r2 - r3
            r8.index = r2
            com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$Token r2 = new com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$Token
            java.lang.String r3 = r0.toString()
            com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$TokenType r4 = com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer.TokenType.UNKNOWN
            r2.<init>(r3, r4)
            return r2
        L_0x011f:
            boolean r2 = java.lang.Character.isWhitespace(r1)
            if (r2 == 0) goto L_0x013f
            int r2 = r8.functionDepth
            if (r2 <= 0) goto L_0x012c
            r0.append(r1)
        L_0x012c:
            com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$Token r2 = new com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$Token
            java.lang.String r3 = r0.toString()
            int r4 = r8.functionDepth
            if (r4 <= 0) goto L_0x0139
            com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$TokenType r4 = com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer.TokenType.FUNCTION
            goto L_0x013b
        L_0x0139:
            com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$TokenType r4 = com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer.TokenType.UNKNOWN
        L_0x013b:
            r2.<init>(r3, r4)
            return r2
        L_0x013f:
            r0.append(r1)
            goto L_0x00ab
        L_0x0144:
            r8.stringQuote = r1
            r8.inString = r3
            com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$Token r2 = new com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$Token
            java.lang.String r3 = r0.toString()
            com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$TokenType r4 = com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer.TokenType.FUNCTION
            r2.<init>(r3, r4)
            return r2
        L_0x0154:
            com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$Token r1 = new com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$Token
            java.lang.String r2 = r0.toString()
            com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$TokenType r3 = com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer.TokenType.FUNCTION
            r1.<init>(r2, r3)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer.getNextToken():com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer$Token");
    }

    private boolean isHexDigit(char c) {
        return ('/' < c && c < ':') || ('@' < c && c < 'G') || ('`' < c && c < 'g');
    }

    private void processFunctionToken(Token token, StringBuilder functionBuffer) {
        if (token.isString()) {
            functionBuffer.append(this.stringQuote);
            functionBuffer.append(token.getValue());
            functionBuffer.append(this.stringQuote);
            return;
        }
        functionBuffer.append(token.getValue());
    }

    public static class Token {
        private TokenType type;
        private String value;

        public Token(String value2, TokenType type2) {
            this.value = value2;
            this.type = type2;
        }

        public String getValue() {
            return this.value;
        }

        public TokenType getType() {
            return this.type;
        }

        public boolean isString() {
            return this.type == TokenType.STRING;
        }

        public String toString() {
            return this.value;
        }
    }
}
