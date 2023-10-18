package androidx.constraintlayout.core.parser;

public class CLNumber extends CLElement {
    float value = Float.NaN;

    public CLNumber(char[] content) {
        super(content);
    }

    public CLNumber(float value2) {
        super((char[]) null);
        this.value = value2;
    }

    public static CLElement allocate(char[] content) {
        return new CLNumber(content);
    }

    /* access modifiers changed from: protected */
    public String toJSON() {
        float value2 = getFloat();
        int intValue = (int) value2;
        if (((float) intValue) == value2) {
            return "" + intValue;
        }
        return "" + value2;
    }

    /* access modifiers changed from: protected */
    public String toFormattedJSON(int indent, int forceIndent) {
        StringBuilder json = new StringBuilder();
        addIndent(json, indent);
        float value2 = getFloat();
        int intValue = (int) value2;
        if (((float) intValue) == value2) {
            json.append(intValue);
        } else {
            json.append(value2);
        }
        return json.toString();
    }

    public boolean isInt() {
        float value2 = getFloat();
        return ((float) ((int) value2)) == value2;
    }

    public int getInt() {
        if (Float.isNaN(this.value)) {
            this.value = (float) Integer.parseInt(content());
        }
        return (int) this.value;
    }

    public float getFloat() {
        if (Float.isNaN(this.value)) {
            this.value = Float.parseFloat(content());
        }
        return this.value;
    }

    public void putValue(float value2) {
        this.value = value2;
    }
}
