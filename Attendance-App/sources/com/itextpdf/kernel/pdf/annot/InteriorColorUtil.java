package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfArray;

class InteriorColorUtil {
    private InteriorColorUtil() {
    }

    public static Color parseInteriorColor(PdfArray color) {
        if (color == null) {
            return null;
        }
        switch (color.size()) {
            case 1:
                return new DeviceGray(color.getAsNumber(0).floatValue());
            case 3:
                return new DeviceRgb(color.getAsNumber(0).floatValue(), color.getAsNumber(1).floatValue(), color.getAsNumber(2).floatValue());
            case 4:
                return new DeviceCmyk(color.getAsNumber(0).floatValue(), color.getAsNumber(1).floatValue(), color.getAsNumber(2).floatValue(), color.getAsNumber(3).floatValue());
            default:
                return null;
        }
    }
}
