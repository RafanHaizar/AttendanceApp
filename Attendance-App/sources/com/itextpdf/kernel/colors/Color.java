package com.itextpdf.kernel.colors;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.colorspace.PdfCieBasedCs;
import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
import com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs;
import com.itextpdf.kernel.pdf.colorspace.PdfSpecialCs;
import java.io.Serializable;
import java.util.Arrays;

public class Color implements Serializable {
    private static final long serialVersionUID = -6639782922289701126L;
    protected PdfColorSpace colorSpace;
    protected float[] colorValue;

    protected Color(PdfColorSpace colorSpace2, float[] colorValue2) {
        this.colorSpace = colorSpace2;
        if (colorValue2 == null) {
            this.colorValue = new float[colorSpace2.getNumberOfComponents()];
        } else {
            this.colorValue = colorValue2;
        }
    }

    public static Color makeColor(PdfColorSpace colorSpace2) {
        return makeColor(colorSpace2, (float[]) null);
    }

    public static Color makeColor(PdfColorSpace colorSpace2, float[] colorValue2) {
        Color indexed;
        Color deviceN;
        Color separation;
        Color lab;
        Color iccBased;
        Color calRgb;
        Color calGray;
        Color deviceCmyk;
        Color deviceRgb;
        Color deviceGray;
        Color c = null;
        boolean unknownColorSpace = false;
        if (colorSpace2 instanceof PdfDeviceCs) {
            if (colorSpace2 instanceof PdfDeviceCs.Gray) {
                if (colorValue2 != null) {
                    float f = colorValue2[0];
                } else {
                    deviceGray = new DeviceGray();
                }
                c = deviceGray;
            } else if (colorSpace2 instanceof PdfDeviceCs.Rgb) {
                if (colorValue2 != null) {
                    float f2 = colorValue2[0];
                    float f3 = colorValue2[1];
                    float f4 = colorValue2[2];
                } else {
                    deviceRgb = new DeviceRgb();
                }
                c = deviceRgb;
            } else if (colorSpace2 instanceof PdfDeviceCs.Cmyk) {
                if (colorValue2 != null) {
                    float f5 = colorValue2[0];
                    float f6 = colorValue2[1];
                    float f7 = colorValue2[2];
                    float f8 = colorValue2[3];
                } else {
                    deviceCmyk = new DeviceCmyk();
                }
                c = deviceCmyk;
            } else {
                unknownColorSpace = true;
            }
        } else if (colorSpace2 instanceof PdfCieBasedCs) {
            if (colorSpace2 instanceof PdfCieBasedCs.CalGray) {
                PdfCieBasedCs.CalGray calGray2 = (PdfCieBasedCs.CalGray) colorSpace2;
                if (colorValue2 != null) {
                    float f9 = colorValue2[0];
                } else {
                    calGray = new CalGray(calGray2);
                }
                c = calGray;
            } else if (colorSpace2 instanceof PdfCieBasedCs.CalRgb) {
                PdfCieBasedCs.CalRgb calRgb2 = (PdfCieBasedCs.CalRgb) colorSpace2;
                if (colorValue2 == null) {
                    calRgb = new CalRgb(calRgb2);
                }
                c = calRgb;
            } else if (colorSpace2 instanceof PdfCieBasedCs.IccBased) {
                PdfCieBasedCs.IccBased iccBased2 = (PdfCieBasedCs.IccBased) colorSpace2;
                if (colorValue2 == null) {
                    iccBased = new IccBased(iccBased2);
                }
                c = iccBased;
            } else if (colorSpace2 instanceof PdfCieBasedCs.Lab) {
                PdfCieBasedCs.Lab lab2 = (PdfCieBasedCs.Lab) colorSpace2;
                if (colorValue2 == null) {
                    lab = new Lab(lab2);
                }
                c = lab;
            } else {
                unknownColorSpace = true;
            }
        } else if (colorSpace2 instanceof PdfSpecialCs) {
            if (colorSpace2 instanceof PdfSpecialCs.Separation) {
                PdfSpecialCs.Separation separation2 = (PdfSpecialCs.Separation) colorSpace2;
                if (colorValue2 != null) {
                    float f10 = colorValue2[0];
                } else {
                    separation = new Separation(separation2);
                }
                c = separation;
            } else if (colorSpace2 instanceof PdfSpecialCs.DeviceN) {
                PdfSpecialCs.DeviceN deviceN2 = (PdfSpecialCs.DeviceN) colorSpace2;
                if (colorValue2 == null) {
                    deviceN = new DeviceN(deviceN2);
                }
                c = deviceN;
            } else if (colorSpace2 instanceof PdfSpecialCs.Indexed) {
                if (colorValue2 != null) {
                    int i = (int) colorValue2[0];
                } else {
                    indexed = new Indexed(colorSpace2);
                }
                c = indexed;
            } else {
                unknownColorSpace = true;
            }
        } else if (colorSpace2 instanceof PdfSpecialCs.Pattern) {
            c = new Color(colorSpace2, colorValue2);
        } else {
            unknownColorSpace = true;
        }
        if (!unknownColorSpace) {
            return c;
        }
        throw new PdfException("Unknown color space.");
    }

    public static DeviceRgb convertCmykToRgb(DeviceCmyk cmykColor) {
        float blackComp = 1.0f - cmykColor.getColorValue()[3];
        return new DeviceRgb((1.0f - cmykColor.getColorValue()[0]) * blackComp, (1.0f - cmykColor.getColorValue()[1]) * blackComp, (1.0f - cmykColor.getColorValue()[2]) * blackComp);
    }

    public static DeviceCmyk convertRgbToCmyk(DeviceRgb rgbColor) {
        float redComp = rgbColor.getColorValue()[0];
        float greenComp = rgbColor.getColorValue()[1];
        float blueComp = rgbColor.getColorValue()[2];
        float k = 1.0f - Math.max(Math.max(redComp, greenComp), blueComp);
        return new DeviceCmyk(((1.0f - redComp) - k) / (1.0f - k), ((1.0f - greenComp) - k) / (1.0f - k), ((1.0f - blueComp) - k) / (1.0f - k), k);
    }

    public int getNumberOfComponents() {
        return this.colorValue.length;
    }

    public PdfColorSpace getColorSpace() {
        return this.colorSpace;
    }

    public float[] getColorValue() {
        return this.colorValue;
    }

    public void setColorValue(float[] value) {
        if (this.colorValue.length == value.length) {
            this.colorValue = value;
            return;
        }
        throw new PdfException(PdfException.IncorrectNumberOfComponents, (Object) this);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Color color = (Color) o;
        PdfColorSpace pdfColorSpace = this.colorSpace;
        if (pdfColorSpace == null ? color.colorSpace == null : pdfColorSpace.getPdfObject().equals(color.colorSpace.getPdfObject())) {
            if (Arrays.equals(this.colorValue, color.colorValue)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        PdfColorSpace pdfColorSpace = this.colorSpace;
        int i = 0;
        int hashCode = (pdfColorSpace != null ? pdfColorSpace.getPdfObject().hashCode() : 0) * 31;
        float[] fArr = this.colorValue;
        if (fArr != null) {
            i = Arrays.hashCode(fArr);
        }
        return hashCode + i;
    }
}
