package androidx.constraintlayout.core.parser;

import java.util.Iterator;

public class CLArray extends CLContainer {
    public CLArray(char[] content) {
        super(content);
    }

    public static CLElement allocate(char[] content) {
        return new CLArray(content);
    }

    /* access modifiers changed from: protected */
    public String toJSON() {
        StringBuilder content = new StringBuilder(getDebugName() + "[");
        boolean first = true;
        for (int i = 0; i < this.mElements.size(); i++) {
            if (!first) {
                content.append(", ");
            } else {
                first = false;
            }
            content.append(((CLElement) this.mElements.get(i)).toJSON());
        }
        return content + "]";
    }

    /* access modifiers changed from: protected */
    public String toFormattedJSON(int indent, int forceIndent) {
        StringBuilder json = new StringBuilder();
        String val = toJSON();
        if (forceIndent > 0 || val.length() + indent >= MAX_LINE) {
            json.append("[\n");
            boolean first = true;
            Iterator it = this.mElements.iterator();
            while (it.hasNext()) {
                CLElement element = (CLElement) it.next();
                if (!first) {
                    json.append(",\n");
                } else {
                    first = false;
                }
                addIndent(json, BASE_INDENT + indent);
                json.append(element.toFormattedJSON(BASE_INDENT + indent, forceIndent - 1));
            }
            json.append("\n");
            addIndent(json, indent);
            json.append("]");
        } else {
            json.append(val);
        }
        return json.toString();
    }
}
