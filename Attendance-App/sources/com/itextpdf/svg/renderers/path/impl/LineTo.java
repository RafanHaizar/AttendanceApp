package com.itextpdf.svg.renderers.path.impl;

import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.svg.exceptions.SvgExceptionMessageConstant;
import java.util.Arrays;

public class LineTo extends AbstractPathShape {
    static final int ARGUMENT_SIZE = 2;

    public LineTo() {
        this(false);
    }

    public LineTo(boolean relative) {
        super(relative);
    }

    public void draw(PdfCanvas canvas) {
        canvas.lineTo((double) CssUtils.parseAbsoluteLength(this.coordinates[0]), (double) CssUtils.parseAbsoluteLength(this.coordinates[1]));
    }

    public void setCoordinates(String[] inputCoordinates, Point startPoint) {
        if (inputCoordinates.length == 2) {
            this.coordinates = new String[]{inputCoordinates[0], inputCoordinates[1]};
            if (isRelative()) {
                this.coordinates = this.copier.makeCoordinatesAbsolute(this.coordinates, new double[]{startPoint.f1280x, startPoint.f1281y});
                return;
            }
            return;
        }
        throw new IllegalArgumentException(MessageFormatUtil.format(SvgExceptionMessageConstant.LINE_TO_EXPECTS_FOLLOWING_PARAMETERS_GOT_0, Arrays.toString(inputCoordinates)));
    }
}
