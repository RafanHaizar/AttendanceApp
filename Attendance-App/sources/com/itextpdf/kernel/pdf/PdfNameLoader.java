package com.itextpdf.kernel.pdf;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

final class PdfNameLoader {
    PdfNameLoader() {
    }

    static Map<String, PdfName> loadNames() {
        Field[] fields = PdfName.class.getDeclaredFields();
        Map<String, PdfName> staticNames = new HashMap<>(fields.length);
        try {
            for (Field field : fields) {
                if ((field.getModifiers() & 25) == 25 && field.getType().equals(PdfName.class)) {
                    PdfName name = (PdfName) field.get((Object) null);
                    staticNames.put(name.getValue(), name);
                }
            }
            return staticNames;
        } catch (Exception e) {
            return null;
        }
    }
}
