package com.itextpdf.layout.borders;

import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.borders.Border;

public class InsetBorder extends Border3D {
    public InsetBorder(float width) {
        super(width);
    }

    public InsetBorder(DeviceRgb color, float width) {
        super(color, width);
    }

    public InsetBorder(DeviceCmyk color, float width) {
        super(color, width);
    }

    public InsetBorder(DeviceGray color, float width) {
        super(color, width);
    }

    public InsetBorder(DeviceRgb color, float width, float opacity) {
        super(color, width, opacity);
    }

    public InsetBorder(DeviceCmyk color, float width, float opacity) {
        super(color, width, opacity);
    }

    public InsetBorder(DeviceGray color, float width, float opacity) {
        super(color, width, opacity);
    }

    public int getType() {
        return 6;
    }

    /* renamed from: com.itextpdf.layout.borders.InsetBorder$1 */
    static /* synthetic */ class C14581 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$layout$borders$Border$Side;

        static {
            int[] iArr = new int[Border.Side.values().length];
            $SwitchMap$com$itextpdf$layout$borders$Border$Side = iArr;
            try {
                iArr[Border.Side.TOP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$borders$Border$Side[Border.Side.LEFT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$borders$Border$Side[Border.Side.BOTTOM.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$borders$Border$Side[Border.Side.RIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public void setInnerHalfColor(PdfCanvas canvas, Border.Side side) {
        switch (C14581.$SwitchMap$com$itextpdf$layout$borders$Border$Side[side.ordinal()]) {
            case 1:
            case 2:
                canvas.setFillColor(getDarkerColor());
                return;
            case 3:
            case 4:
                canvas.setFillColor(getColor());
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void setOuterHalfColor(PdfCanvas canvas, Border.Side side) {
        switch (C14581.$SwitchMap$com$itextpdf$layout$borders$Border$Side[side.ordinal()]) {
            case 1:
            case 2:
                canvas.setFillColor(getDarkerColor());
                return;
            case 3:
            case 4:
                canvas.setFillColor(getColor());
                return;
            default:
                return;
        }
    }
}
