package com.itextpdf.layout.borders;

import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.borders.Border;

public class GrooveBorder extends Border3D {
    public GrooveBorder(float width) {
        super(width);
    }

    public GrooveBorder(DeviceRgb color, float width) {
        super(color, width);
    }

    public GrooveBorder(DeviceCmyk color, float width) {
        super(color, width);
    }

    public GrooveBorder(DeviceGray color, float width) {
        super(color, width);
    }

    public GrooveBorder(DeviceRgb color, float width, float opacity) {
        super(color, width, opacity);
    }

    public GrooveBorder(DeviceCmyk color, float width, float opacity) {
        super(color, width, opacity);
    }

    public GrooveBorder(DeviceGray color, float width, float opacity) {
        super(color, width, opacity);
    }

    public int getType() {
        return 5;
    }

    /* renamed from: com.itextpdf.layout.borders.GrooveBorder$1 */
    static /* synthetic */ class C14571 {
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
        switch (C14571.$SwitchMap$com$itextpdf$layout$borders$Border$Side[side.ordinal()]) {
            case 1:
            case 2:
                canvas.setFillColor(getColor());
                return;
            case 3:
            case 4:
                canvas.setFillColor(getDarkerColor());
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void setOuterHalfColor(PdfCanvas canvas, Border.Side side) {
        switch (C14571.$SwitchMap$com$itextpdf$layout$borders$Border$Side[side.ordinal()]) {
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
