package androidx.constraintlayout.core.parser;

public class CLParser {
    static boolean DEBUG = false;
    private boolean hasComment = false;
    private int lineNumber;
    private String mContent;

    enum TYPE {
        UNKNOWN,
        OBJECT,
        ARRAY,
        NUMBER,
        STRING,
        KEY,
        TOKEN
    }

    public static CLObject parse(String string) throws CLParsingException {
        return new CLParser(string).parse();
    }

    public CLParser(String content) {
        this.mContent = content;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:77:0x014a, code lost:
        if (r6 != ':') goto L_0x0174;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public androidx.constraintlayout.core.parser.CLObject parse() throws androidx.constraintlayout.core.parser.CLParsingException {
        /*
            r16 = this;
            r0 = r16
            r1 = 0
            java.lang.String r2 = r0.mContent
            char[] r2 = r2.toCharArray()
            r3 = 0
            int r4 = r2.length
            r5 = 1
            r0.lineNumber = r5
            r6 = -1
            r7 = 0
        L_0x0010:
            r8 = 10
            if (r7 >= r4) goto L_0x0026
            char r9 = r2[r7]
            r10 = 123(0x7b, float:1.72E-43)
            if (r9 != r10) goto L_0x001c
            r6 = r7
            goto L_0x0026
        L_0x001c:
            if (r9 != r8) goto L_0x0023
            int r8 = r0.lineNumber
            int r8 = r8 + r5
            r0.lineNumber = r8
        L_0x0023:
            int r7 = r7 + 1
            goto L_0x0010
        L_0x0026:
            r7 = -1
            if (r6 == r7) goto L_0x01d9
            androidx.constraintlayout.core.parser.CLObject r1 = androidx.constraintlayout.core.parser.CLObject.allocate(r2)
            int r7 = r0.lineNumber
            r1.setLine(r7)
            long r9 = (long) r6
            r1.setStart(r9)
            r3 = r1
            int r7 = r6 + 1
        L_0x0039:
            if (r7 >= r4) goto L_0x0195
            char r9 = r2[r7]
            if (r9 != r8) goto L_0x0044
            int r10 = r0.lineNumber
            int r10 = r10 + r5
            r0.lineNumber = r10
        L_0x0044:
            boolean r10 = r0.hasComment
            if (r10 == 0) goto L_0x0053
            if (r9 != r8) goto L_0x004e
            r10 = 0
            r0.hasComment = r10
            goto L_0x0053
        L_0x004e:
            r15 = r6
            r5 = 10
            goto L_0x018d
        L_0x0053:
            if (r3 != 0) goto L_0x0058
            r15 = r6
            goto L_0x0196
        L_0x0058:
            boolean r10 = r3.isDone()
            if (r10 == 0) goto L_0x0068
            androidx.constraintlayout.core.parser.CLElement r3 = r0.getNextJsonElement(r7, r9, r3, r2)
            r15 = r6
            r6 = r9
            r5 = 10
            goto L_0x0174
        L_0x0068:
            boolean r10 = r3 instanceof androidx.constraintlayout.core.parser.CLObject
            r11 = 125(0x7d, float:1.75E-43)
            if (r10 == 0) goto L_0x0086
            if (r9 != r11) goto L_0x007c
            int r10 = r7 + -1
            long r10 = (long) r10
            r3.setEnd(r10)
            r15 = r6
            r6 = r9
            r5 = 10
            goto L_0x0174
        L_0x007c:
            androidx.constraintlayout.core.parser.CLElement r3 = r0.getNextJsonElement(r7, r9, r3, r2)
            r15 = r6
            r6 = r9
            r5 = 10
            goto L_0x0174
        L_0x0086:
            boolean r10 = r3 instanceof androidx.constraintlayout.core.parser.CLArray
            r12 = 93
            if (r10 == 0) goto L_0x00a4
            if (r9 != r12) goto L_0x009a
            int r10 = r7 + -1
            long r10 = (long) r10
            r3.setEnd(r10)
            r15 = r6
            r6 = r9
            r5 = 10
            goto L_0x0174
        L_0x009a:
            androidx.constraintlayout.core.parser.CLElement r3 = r0.getNextJsonElement(r7, r9, r3, r2)
            r15 = r6
            r6 = r9
            r5 = 10
            goto L_0x0174
        L_0x00a4:
            boolean r10 = r3 instanceof androidx.constraintlayout.core.parser.CLString
            r13 = 1
            if (r10 == 0) goto L_0x00c3
            long r10 = r3.start
            int r11 = (int) r10
            char r10 = r2[r11]
            if (r10 != r9) goto L_0x00bd
            long r11 = r3.start
            long r11 = r11 + r13
            r3.setStart(r11)
            int r11 = r7 + -1
            long r11 = (long) r11
            r3.setEnd(r11)
        L_0x00bd:
            r15 = r6
            r6 = r9
            r5 = 10
            goto L_0x0174
        L_0x00c3:
            boolean r10 = r3 instanceof androidx.constraintlayout.core.parser.CLToken
            if (r10 == 0) goto L_0x00fd
            r10 = r3
            androidx.constraintlayout.core.parser.CLToken r10 = (androidx.constraintlayout.core.parser.CLToken) r10
            r15 = r6
            long r5 = (long) r7
            boolean r5 = r10.validate(r9, r5)
            if (r5 == 0) goto L_0x00d3
            goto L_0x00fe
        L_0x00d3:
            androidx.constraintlayout.core.parser.CLParsingException r5 = new androidx.constraintlayout.core.parser.CLParsingException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r8 = "parsing incorrect token "
            java.lang.StringBuilder r6 = r6.append(r8)
            java.lang.String r8 = r10.content()
            java.lang.StringBuilder r6 = r6.append(r8)
            java.lang.String r8 = " at line "
            java.lang.StringBuilder r6 = r6.append(r8)
            int r8 = r0.lineNumber
            java.lang.StringBuilder r6 = r6.append(r8)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6, r10)
            throw r5
        L_0x00fd:
            r15 = r6
        L_0x00fe:
            boolean r5 = r3 instanceof androidx.constraintlayout.core.parser.CLKey
            if (r5 != 0) goto L_0x0109
            boolean r5 = r3 instanceof androidx.constraintlayout.core.parser.CLString
            if (r5 == 0) goto L_0x0107
            goto L_0x0109
        L_0x0107:
            r6 = r9
            goto L_0x012a
        L_0x0109:
            long r5 = r3.start
            int r6 = (int) r5
            char r5 = r2[r6]
            r6 = 39
            if (r5 == r6) goto L_0x0119
            r6 = 34
            if (r5 != r6) goto L_0x0117
            goto L_0x0119
        L_0x0117:
            r6 = r9
            goto L_0x012a
        L_0x0119:
            if (r5 != r9) goto L_0x0129
            r6 = r9
            long r8 = r3.start
            long r8 = r8 + r13
            r3.setStart(r8)
            int r8 = r7 + -1
            long r8 = (long) r8
            r3.setEnd(r8)
            goto L_0x012a
        L_0x0129:
            r6 = r9
        L_0x012a:
            boolean r5 = r3.isDone()
            if (r5 != 0) goto L_0x0172
            if (r6 == r11) goto L_0x014d
            if (r6 == r12) goto L_0x014d
            r5 = 44
            if (r6 == r5) goto L_0x014d
            r5 = 32
            if (r6 == r5) goto L_0x014d
            r5 = 9
            if (r6 == r5) goto L_0x014d
            r5 = 13
            if (r6 == r5) goto L_0x014d
            r5 = 10
            if (r6 == r5) goto L_0x014f
            r8 = 58
            if (r6 != r8) goto L_0x0174
            goto L_0x014f
        L_0x014d:
            r5 = 10
        L_0x014f:
            int r8 = r7 + -1
            long r8 = (long) r8
            r3.setEnd(r8)
            if (r6 == r11) goto L_0x0159
            if (r6 != r12) goto L_0x0174
        L_0x0159:
            androidx.constraintlayout.core.parser.CLElement r3 = r3.getContainer()
            int r8 = r7 + -1
            long r8 = (long) r8
            r3.setEnd(r8)
            boolean r8 = r3 instanceof androidx.constraintlayout.core.parser.CLKey
            if (r8 == 0) goto L_0x0174
            androidx.constraintlayout.core.parser.CLElement r3 = r3.getContainer()
            int r8 = r7 + -1
            long r8 = (long) r8
            r3.setEnd(r8)
            goto L_0x0174
        L_0x0172:
            r5 = 10
        L_0x0174:
            boolean r8 = r3.isDone()
            if (r8 == 0) goto L_0x018d
            boolean r8 = r3 instanceof androidx.constraintlayout.core.parser.CLKey
            if (r8 == 0) goto L_0x0189
            r8 = r3
            androidx.constraintlayout.core.parser.CLKey r8 = (androidx.constraintlayout.core.parser.CLKey) r8
            java.util.ArrayList r8 = r8.mElements
            int r8 = r8.size()
            if (r8 <= 0) goto L_0x018d
        L_0x0189:
            androidx.constraintlayout.core.parser.CLElement r3 = r3.getContainer()
        L_0x018d:
            int r7 = r7 + 1
            r6 = r15
            r5 = 1
            r8 = 10
            goto L_0x0039
        L_0x0195:
            r15 = r6
        L_0x0196:
            if (r3 == 0) goto L_0x01b8
            boolean r5 = r3.isDone()
            if (r5 != 0) goto L_0x01b8
            boolean r5 = r3 instanceof androidx.constraintlayout.core.parser.CLString
            if (r5 == 0) goto L_0x01ac
            long r5 = r3.start
            int r6 = (int) r5
            r5 = 1
            int r6 = r6 + r5
            long r6 = (long) r6
            r3.setStart(r6)
            goto L_0x01ad
        L_0x01ac:
            r5 = 1
        L_0x01ad:
            int r6 = r4 + -1
            long r6 = (long) r6
            r3.setEnd(r6)
            androidx.constraintlayout.core.parser.CLElement r3 = r3.getContainer()
            goto L_0x0196
        L_0x01b8:
            boolean r5 = DEBUG
            if (r5 == 0) goto L_0x01d8
            java.io.PrintStream r5 = java.lang.System.out
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Root: "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r7 = r1.toJSON()
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r5.println(r6)
        L_0x01d8:
            return r1
        L_0x01d9:
            r15 = r6
            androidx.constraintlayout.core.parser.CLParsingException r5 = new androidx.constraintlayout.core.parser.CLParsingException
            java.lang.String r6 = "invalid json content"
            r7 = 0
            r5.<init>(r6, r7)
            goto L_0x01e4
        L_0x01e3:
            throw r5
        L_0x01e4:
            goto L_0x01e3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.parser.CLParser.parse():androidx.constraintlayout.core.parser.CLObject");
    }

    private CLElement getNextJsonElement(int position, char c, CLElement currentElement, char[] content) throws CLParsingException {
        switch (c) {
            case 9:
            case 10:
            case 13:
            case ' ':
            case ',':
            case ':':
                return currentElement;
            case '\"':
            case '\'':
                if (currentElement instanceof CLObject) {
                    return createElement(currentElement, position, TYPE.KEY, true, content);
                }
                return createElement(currentElement, position, TYPE.STRING, true, content);
            case '+':
            case '-':
            case '.':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return createElement(currentElement, position, TYPE.NUMBER, true, content);
            case '/':
                if (position + 1 >= content.length || content[position + 1] != '/') {
                    return currentElement;
                }
                this.hasComment = true;
                return currentElement;
            case '[':
                return createElement(currentElement, position, TYPE.ARRAY, true, content);
            case ']':
            case '}':
                currentElement.setEnd((long) (position - 1));
                CLElement currentElement2 = currentElement.getContainer();
                currentElement2.setEnd((long) position);
                return currentElement2;
            case '{':
                return createElement(currentElement, position, TYPE.OBJECT, true, content);
            default:
                if (!(currentElement instanceof CLContainer) || (currentElement instanceof CLObject)) {
                    return createElement(currentElement, position, TYPE.KEY, true, content);
                }
                CLElement currentElement3 = createElement(currentElement, position, TYPE.TOKEN, true, content);
                CLToken token = (CLToken) currentElement3;
                if (token.validate(c, (long) position)) {
                    return currentElement3;
                }
                throw new CLParsingException("incorrect token <" + c + "> at line " + this.lineNumber, token);
        }
    }

    private CLElement createElement(CLElement currentElement, int position, TYPE type, boolean applyStart, char[] content) {
        CLElement newElement = null;
        if (DEBUG) {
            System.out.println("CREATE " + type + " at " + content[position]);
        }
        switch (C06201.$SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[type.ordinal()]) {
            case 1:
                newElement = CLObject.allocate(content);
                position++;
                break;
            case 2:
                newElement = CLArray.allocate(content);
                position++;
                break;
            case 3:
                newElement = CLString.allocate(content);
                break;
            case 4:
                newElement = CLNumber.allocate(content);
                break;
            case 5:
                newElement = CLKey.allocate(content);
                break;
            case 6:
                newElement = CLToken.allocate(content);
                break;
        }
        if (newElement == null) {
            return null;
        }
        newElement.setLine(this.lineNumber);
        if (applyStart) {
            newElement.setStart((long) position);
        }
        if (currentElement instanceof CLContainer) {
            newElement.setContainer((CLContainer) currentElement);
        }
        return newElement;
    }

    /* renamed from: androidx.constraintlayout.core.parser.CLParser$1 */
    static /* synthetic */ class C06201 {
        static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE;

        static {
            int[] iArr = new int[TYPE.values().length];
            $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE = iArr;
            try {
                iArr[TYPE.OBJECT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[TYPE.ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[TYPE.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[TYPE.NUMBER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[TYPE.KEY.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[TYPE.TOKEN.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }
}
