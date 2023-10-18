package androidx.constraintlayout.core.motion.parse;

import androidx.constraintlayout.core.motion.utils.TypedBundle;
import androidx.constraintlayout.core.parser.CLElement;
import androidx.constraintlayout.core.parser.CLKey;
import androidx.constraintlayout.core.parser.CLObject;
import androidx.constraintlayout.core.parser.CLParser;
import androidx.constraintlayout.core.parser.CLParsingException;

public class KeyParser {

    private interface DataType {
        int get(int i);
    }

    private interface Ids {
        int get(String str);
    }

    private static TypedBundle parse(String str, Ids table, DataType dtype) {
        TypedBundle bundle = new TypedBundle();
        try {
            CLObject parsedContent = CLParser.parse(str);
            int n = parsedContent.size();
            for (int i = 0; i < n; i++) {
                CLKey clkey = (CLKey) parsedContent.get(i);
                String type = clkey.content();
                CLElement value = clkey.getValue();
                int id = table.get(type);
                if (id != -1) {
                    switch (dtype.get(id)) {
                        case 1:
                            bundle.add(id, parsedContent.getBoolean(i));
                            break;
                        case 2:
                            bundle.add(id, value.getInt());
                            System.out.println("parse " + type + " INT_MASK > " + value.getInt());
                            break;
                        case 4:
                            bundle.add(id, value.getFloat());
                            System.out.println("parse " + type + " FLOAT_MASK > " + value.getFloat());
                            break;
                        case 8:
                            bundle.add(id, value.content());
                            System.out.println("parse " + type + " STRING_MASK > " + value.content());
                            break;
                    }
                } else {
                    System.err.println("unknown type " + type);
                }
            }
        } catch (CLParsingException e) {
            e.printStackTrace();
        }
        return bundle;
    }

    public static TypedBundle parseAttributes(String str) {
        return parse(str, new KeyParser$$ExternalSyntheticLambda0(), new KeyParser$$ExternalSyntheticLambda1());
    }

    public static void main(String[] args) {
        parseAttributes("{frame:22,\ntarget:'widget1',\neasing:'easeIn',\ncurveFit:'spline',\nprogress:0.3,\nalpha:0.2,\nelevation:0.7,\nrotationZ:23,\nrotationX:25.0,\nrotationY:27.0,\npivotX:15,\npivotY:17,\npivotTarget:'32',\npathRotate:23,\nscaleX:0.5,\nscaleY:0.7,\ntranslationX:5,\ntranslationY:7,\ntranslationZ:11,\n}");
    }
}
