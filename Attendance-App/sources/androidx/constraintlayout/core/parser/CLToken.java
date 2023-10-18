package androidx.constraintlayout.core.parser;

public class CLToken extends CLElement {
    int index = 0;
    char[] tokenFalse = "false".toCharArray();
    char[] tokenNull = "null".toCharArray();
    char[] tokenTrue = "true".toCharArray();
    Type type = Type.UNKNOWN;

    enum Type {
        UNKNOWN,
        TRUE,
        FALSE,
        NULL
    }

    public boolean getBoolean() throws CLParsingException {
        if (this.type == Type.TRUE) {
            return true;
        }
        if (this.type == Type.FALSE) {
            return false;
        }
        throw new CLParsingException("this token is not a boolean: <" + content() + ">", this);
    }

    public boolean isNull() throws CLParsingException {
        if (this.type == Type.NULL) {
            return true;
        }
        throw new CLParsingException("this token is not a null: <" + content() + ">", this);
    }

    public CLToken(char[] content) {
        super(content);
    }

    public static CLElement allocate(char[] content) {
        return new CLToken(content);
    }

    /* access modifiers changed from: protected */
    public String toJSON() {
        if (CLParser.DEBUG) {
            return "<" + content() + ">";
        }
        return content();
    }

    /* access modifiers changed from: protected */
    public String toFormattedJSON(int indent, int forceIndent) {
        StringBuilder json = new StringBuilder();
        addIndent(json, indent);
        json.append(content());
        return json.toString();
    }

    public Type getType() {
        return this.type;
    }

    /* renamed from: androidx.constraintlayout.core.parser.CLToken$1 */
    static /* synthetic */ class C06211 {
        static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$parser$CLToken$Type;

        static {
            int[] iArr = new int[Type.values().length];
            $SwitchMap$androidx$constraintlayout$core$parser$CLToken$Type = iArr;
            try {
                iArr[Type.TRUE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLToken$Type[Type.FALSE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLToken$Type[Type.NULL.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLToken$Type[Type.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public boolean validate(char c, long position) {
        boolean isValid = false;
        boolean z = false;
        switch (C06211.$SwitchMap$androidx$constraintlayout$core$parser$CLToken$Type[this.type.ordinal()]) {
            case 1:
                char[] cArr = this.tokenTrue;
                int i = this.index;
                if (cArr[i] == c) {
                    z = true;
                }
                isValid = z;
                if (isValid && i + 1 == cArr.length) {
                    setEnd(position);
                    break;
                }
            case 2:
                char[] cArr2 = this.tokenFalse;
                int i2 = this.index;
                if (cArr2[i2] == c) {
                    z = true;
                }
                isValid = z;
                if (isValid && i2 + 1 == cArr2.length) {
                    setEnd(position);
                    break;
                }
            case 3:
                char[] cArr3 = this.tokenNull;
                int i3 = this.index;
                if (cArr3[i3] == c) {
                    z = true;
                }
                isValid = z;
                if (isValid && i3 + 1 == cArr3.length) {
                    setEnd(position);
                    break;
                }
            case 4:
                char[] cArr4 = this.tokenTrue;
                int i4 = this.index;
                if (cArr4[i4] != c) {
                    if (this.tokenFalse[i4] != c) {
                        if (this.tokenNull[i4] == c) {
                            this.type = Type.NULL;
                            isValid = true;
                            break;
                        }
                    } else {
                        this.type = Type.FALSE;
                        isValid = true;
                        break;
                    }
                } else {
                    this.type = Type.TRUE;
                    isValid = true;
                    break;
                }
                break;
        }
        this.index++;
        return isValid;
    }
}
