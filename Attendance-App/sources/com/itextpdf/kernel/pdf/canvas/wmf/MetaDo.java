package com.itextpdf.kernel.pdf.canvas.wmf;

import com.google.android.material.internal.ViewUtils;
import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.p026io.font.FontProgram;
import com.itextpdf.p026io.image.ImageData;
import com.itextpdf.p026io.image.ImageDataFactory;
import com.itextpdf.p026io.image.ImageType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import kotlin.UByte;

public class MetaDo {
    public static final int META_ANIMATEPALETTE = 1078;
    public static final int META_ARC = 2071;
    public static final int META_BITBLT = 2338;
    public static final int META_CHORD = 2096;
    public static final int META_CREATEBRUSHINDIRECT = 764;
    public static final int META_CREATEFONTINDIRECT = 763;
    public static final int META_CREATEPALETTE = 247;
    public static final int META_CREATEPATTERNBRUSH = 505;
    public static final int META_CREATEPENINDIRECT = 762;
    public static final int META_CREATEREGION = 1791;
    public static final int META_DELETEOBJECT = 496;
    public static final int META_DIBBITBLT = 2368;
    public static final int META_DIBCREATEPATTERNBRUSH = 322;
    public static final int META_DIBSTRETCHBLT = 2881;
    public static final int META_ELLIPSE = 1048;
    public static final int META_ESCAPE = 1574;
    public static final int META_EXCLUDECLIPRECT = 1045;
    public static final int META_EXTFLOODFILL = 1352;
    public static final int META_EXTTEXTOUT = 2610;
    public static final int META_FILLREGION = 552;
    public static final int META_FLOODFILL = 1049;
    public static final int META_FRAMEREGION = 1065;
    public static final int META_INTERSECTCLIPRECT = 1046;
    public static final int META_INVERTREGION = 298;
    public static final int META_LINETO = 531;
    public static final int META_MOVETO = 532;
    public static final int META_OFFSETCLIPRGN = 544;
    public static final int META_OFFSETVIEWPORTORG = 529;
    public static final int META_OFFSETWINDOWORG = 527;
    public static final int META_PAINTREGION = 299;
    public static final int META_PATBLT = 1565;
    public static final int META_PIE = 2074;
    public static final int META_POLYGON = 804;
    public static final int META_POLYLINE = 805;
    public static final int META_POLYPOLYGON = 1336;
    public static final int META_REALIZEPALETTE = 53;
    public static final int META_RECTANGLE = 1051;
    public static final int META_RESIZEPALETTE = 313;
    public static final int META_RESTOREDC = 295;
    public static final int META_ROUNDRECT = 1564;
    public static final int META_SAVEDC = 30;
    public static final int META_SCALEVIEWPORTEXT = 1042;
    public static final int META_SCALEWINDOWEXT = 1040;
    public static final int META_SELECTCLIPREGION = 300;
    public static final int META_SELECTOBJECT = 301;
    public static final int META_SELECTPALETTE = 564;
    public static final int META_SETBKCOLOR = 513;
    public static final int META_SETBKMODE = 258;
    public static final int META_SETDIBTODEV = 3379;
    public static final int META_SETMAPMODE = 259;
    public static final int META_SETMAPPERFLAGS = 561;
    public static final int META_SETPALENTRIES = 55;
    public static final int META_SETPIXEL = 1055;
    public static final int META_SETPOLYFILLMODE = 262;
    public static final int META_SETRELABS = 261;
    public static final int META_SETROP2 = 260;
    public static final int META_SETSTRETCHBLTMODE = 263;
    public static final int META_SETTEXTALIGN = 302;
    public static final int META_SETTEXTCHAREXTRA = 264;
    public static final int META_SETTEXTCOLOR = 521;
    public static final int META_SETTEXTJUSTIFICATION = 522;
    public static final int META_SETVIEWPORTEXT = 526;
    public static final int META_SETVIEWPORTORG = 525;
    public static final int META_SETWINDOWEXT = 524;
    public static final int META_SETWINDOWORG = 523;
    public static final int META_STRETCHBLT = 2851;
    public static final int META_STRETCHDIB = 3907;
    public static final int META_TEXTOUT = 1313;
    int bottom;

    /* renamed from: cb */
    public PdfCanvas f1498cb;

    /* renamed from: in */
    public InputMeta f1499in;
    int inch;
    int left;
    int right;
    MetaState state = new MetaState();
    int top;

    public MetaDo(InputStream in, PdfCanvas cb) {
        this.f1498cb = cb;
        this.f1499in = new InputMeta(in);
    }

    public void readAll() throws IOException {
        int tsize;
        int lenMarker;
        String s;
        float arc2;
        int y2;
        int x2;
        int y1;
        int x1;
        String s2;
        MetaDo metaDo = this;
        if (metaDo.f1499in.readInt() == -1698247209) {
            metaDo.f1499in.readWord();
            metaDo.left = metaDo.f1499in.readShort();
            metaDo.top = metaDo.f1499in.readShort();
            metaDo.right = metaDo.f1499in.readShort();
            metaDo.bottom = metaDo.f1499in.readShort();
            int readWord = metaDo.f1499in.readWord();
            metaDo.inch = readWord;
            metaDo.state.setScalingX((((float) (metaDo.right - metaDo.left)) / ((float) readWord)) * 72.0f);
            metaDo.state.setScalingY((((float) (metaDo.bottom - metaDo.top)) / ((float) metaDo.inch)) * 72.0f);
            metaDo.state.setOffsetWx(metaDo.left);
            metaDo.state.setOffsetWy(metaDo.top);
            metaDo.state.setExtentWx(metaDo.right - metaDo.left);
            metaDo.state.setExtentWy(metaDo.bottom - metaDo.top);
            metaDo.f1499in.readInt();
            metaDo.f1499in.readWord();
            metaDo.f1499in.skip(18);
            metaDo.f1498cb.setLineCapStyle(1);
            metaDo.f1498cb.setLineJoinStyle(1);
            while (true) {
                int lenMarker2 = metaDo.f1499in.getLength();
                int tsize2 = metaDo.f1499in.readInt();
                if (tsize2 < 3) {
                    metaDo.state.cleanup(metaDo.f1498cb);
                    return;
                }
                int function = metaDo.f1499in.readWord();
                switch (function) {
                    case 0:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i = function;
                        break;
                    case 30:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i2 = function;
                        metaDo.state.saveState(metaDo.f1498cb);
                        break;
                    case META_CREATEPALETTE /*247*/:
                    case 322:
                    case META_CREATEREGION /*1791*/:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i3 = function;
                        metaDo.state.addMetaObject(new MetaObject());
                        break;
                    case 258:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i4 = function;
                        metaDo.state.setBackgroundMode(metaDo.f1499in.readWord());
                        break;
                    case 262:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i5 = function;
                        metaDo.state.setPolyFillMode(metaDo.f1499in.readWord());
                        break;
                    case META_RESTOREDC /*295*/:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i6 = function;
                        metaDo.state.restoreState(metaDo.f1499in.readShort(), metaDo.f1498cb);
                        break;
                    case 301:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i7 = function;
                        metaDo.state.selectMetaObject(metaDo.f1499in.readWord(), metaDo.f1498cb);
                        break;
                    case 302:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i8 = function;
                        metaDo.state.setTextAlign(metaDo.f1499in.readWord());
                        break;
                    case META_DELETEOBJECT /*496*/:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i9 = function;
                        metaDo.state.deleteMetaObject(metaDo.f1499in.readWord());
                        break;
                    case 513:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i10 = function;
                        metaDo.state.setCurrentBackgroundColor(metaDo.f1499in.readColor());
                        break;
                    case 521:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i11 = function;
                        metaDo.state.setCurrentTextColor(metaDo.f1499in.readColor());
                        break;
                    case META_SETWINDOWORG /*523*/:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i12 = function;
                        metaDo.state.setOffsetWy(metaDo.f1499in.readShort());
                        metaDo.state.setOffsetWx(metaDo.f1499in.readShort());
                        break;
                    case META_SETWINDOWEXT /*524*/:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i13 = function;
                        metaDo.state.setExtentWy(metaDo.f1499in.readShort());
                        metaDo.state.setExtentWx(metaDo.f1499in.readShort());
                        break;
                    case 531:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i14 = function;
                        int y = metaDo.f1499in.readShort();
                        int x = metaDo.f1499in.readShort();
                        Point p = metaDo.state.getCurrentPoint();
                        metaDo.f1498cb.moveTo((double) metaDo.state.transformX((int) p.getX()), (double) metaDo.state.transformY((int) p.getY()));
                        metaDo.f1498cb.lineTo((double) metaDo.state.transformX(x), (double) metaDo.state.transformY(y));
                        metaDo.f1498cb.stroke();
                        metaDo.state.setCurrentPoint(new Point(x, y));
                        break;
                    case 532:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i15 = function;
                        metaDo.state.setCurrentPoint(new Point(metaDo.f1499in.readShort(), metaDo.f1499in.readShort()));
                        break;
                    case META_CREATEPENINDIRECT /*762*/:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i16 = function;
                        MetaPen pen = new MetaPen();
                        pen.init(metaDo.f1499in);
                        metaDo.state.addMetaObject(pen);
                        break;
                    case META_CREATEFONTINDIRECT /*763*/:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i17 = function;
                        MetaFont font = new MetaFont();
                        font.init(metaDo.f1499in);
                        metaDo.state.addMetaObject(font);
                        break;
                    case META_CREATEBRUSHINDIRECT /*764*/:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i18 = function;
                        MetaBrush brush = new MetaBrush();
                        brush.init(metaDo.f1499in);
                        metaDo.state.addMetaObject(brush);
                        break;
                    case META_POLYGON /*804*/:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i19 = function;
                        if (!metaDo.isNullStrokeFill(false)) {
                            int len = metaDo.f1499in.readWord();
                            int sx = metaDo.f1499in.readShort();
                            int sy = metaDo.f1499in.readShort();
                            metaDo.f1498cb.moveTo((double) metaDo.state.transformX(sx), (double) metaDo.state.transformY(sy));
                            for (int k = 1; k < len; k++) {
                                metaDo.f1498cb.lineTo((double) metaDo.state.transformX(metaDo.f1499in.readShort()), (double) metaDo.state.transformY(metaDo.f1499in.readShort()));
                            }
                            metaDo.f1498cb.lineTo((double) metaDo.state.transformX(sx), (double) metaDo.state.transformY(sy));
                            strokeAndFill();
                            break;
                        } else {
                            break;
                        }
                    case META_POLYLINE /*805*/:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i20 = function;
                        metaDo.state.setLineJoinPolygon(metaDo.f1498cb);
                        int len2 = metaDo.f1499in.readWord();
                        metaDo.f1498cb.moveTo((double) metaDo.state.transformX(metaDo.f1499in.readShort()), (double) metaDo.state.transformY(metaDo.f1499in.readShort()));
                        for (int k2 = 1; k2 < len2; k2++) {
                            metaDo.f1498cb.lineTo((double) metaDo.state.transformX(metaDo.f1499in.readShort()), (double) metaDo.state.transformY(metaDo.f1499in.readShort()));
                        }
                        metaDo.f1498cb.stroke();
                        break;
                    case META_INTERSECTCLIPRECT /*1046*/:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i21 = function;
                        float b = metaDo.state.transformY(metaDo.f1499in.readShort());
                        float r = metaDo.state.transformX(metaDo.f1499in.readShort());
                        float t = metaDo.state.transformY(metaDo.f1499in.readShort());
                        float l = metaDo.state.transformX(metaDo.f1499in.readShort());
                        metaDo.f1498cb.rectangle((double) l, (double) b, (double) (r - l), (double) (t - b));
                        metaDo.f1498cb.eoClip();
                        metaDo.f1498cb.endPath();
                        break;
                    case META_ELLIPSE /*1048*/:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i22 = function;
                        if (!metaDo.isNullStrokeFill(metaDo.state.getLineNeutral())) {
                            metaDo.f1498cb.arc((double) metaDo.state.transformX(metaDo.f1499in.readShort()), (double) metaDo.state.transformY(metaDo.f1499in.readShort()), (double) metaDo.state.transformX(metaDo.f1499in.readShort()), (double) metaDo.state.transformY(metaDo.f1499in.readShort()), 0.0d, 360.0d);
                            strokeAndFill();
                            break;
                        } else {
                            break;
                        }
                    case META_RECTANGLE /*1051*/:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i23 = function;
                        if (!metaDo.isNullStrokeFill(true)) {
                            float b2 = metaDo.state.transformY(metaDo.f1499in.readShort());
                            float r2 = metaDo.state.transformX(metaDo.f1499in.readShort());
                            float t2 = metaDo.state.transformY(metaDo.f1499in.readShort());
                            float l2 = metaDo.state.transformX(metaDo.f1499in.readShort());
                            metaDo.f1498cb.rectangle((double) l2, (double) b2, (double) (r2 - l2), (double) (t2 - b2));
                            strokeAndFill();
                            break;
                        } else {
                            break;
                        }
                    case META_SETPIXEL /*1055*/:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i24 = function;
                        Color color = metaDo.f1499in.readColor();
                        int y3 = metaDo.f1499in.readShort();
                        int x3 = metaDo.f1499in.readShort();
                        metaDo.f1498cb.saveState();
                        metaDo.f1498cb.setFillColor(color);
                        metaDo.f1498cb.rectangle((double) metaDo.state.transformX(x3), (double) metaDo.state.transformY(y3), 0.20000000298023224d, 0.20000000298023224d);
                        metaDo.f1498cb.fill();
                        metaDo.f1498cb.restoreState();
                        break;
                    case META_TEXTOUT /*1313*/:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i25 = function;
                        int count = metaDo.f1499in.readWord();
                        byte[] text = new byte[count];
                        int k3 = 0;
                        while (k3 < count) {
                            byte c = (byte) metaDo.f1499in.readByte();
                            if (c != 0) {
                                text[k3] = c;
                                k3++;
                            }
                        }
                        try {
                            s = new String(text, 0, k3, "Cp1252");
                        } catch (UnsupportedEncodingException e) {
                            s = new String(text, 0, k3);
                        }
                        metaDo.f1499in.skip(((count + 1) & 65534) - k3);
                        outputText(metaDo.f1499in.readShort(), metaDo.f1499in.readShort(), 0, 0, 0, 0, 0, s);
                        break;
                    case META_POLYPOLYGON /*1336*/:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i26 = function;
                        if (!metaDo.isNullStrokeFill(false)) {
                            int[] lens = new int[metaDo.f1499in.readWord()];
                            for (int k4 = 0; k4 < lens.length; k4++) {
                                lens[k4] = metaDo.f1499in.readWord();
                            }
                            for (int len3 : lens) {
                                int sx2 = metaDo.f1499in.readShort();
                                int sy2 = metaDo.f1499in.readShort();
                                metaDo.f1498cb.moveTo((double) metaDo.state.transformX(sx2), (double) metaDo.state.transformY(sy2));
                                for (int k5 = 1; k5 < len3; k5++) {
                                    metaDo.f1498cb.lineTo((double) metaDo.state.transformX(metaDo.f1499in.readShort()), (double) metaDo.state.transformY(metaDo.f1499in.readShort()));
                                }
                                metaDo.f1498cb.lineTo((double) metaDo.state.transformX(sx2), (double) metaDo.state.transformY(sy2));
                            }
                            strokeAndFill();
                            break;
                        } else {
                            break;
                        }
                    case META_ROUNDRECT /*1564*/:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i27 = function;
                        if (!metaDo.isNullStrokeFill(true)) {
                            float h = metaDo.state.transformY(0) - metaDo.state.transformY(metaDo.f1499in.readShort());
                            float w = metaDo.state.transformX(metaDo.f1499in.readShort()) - metaDo.state.transformX(0);
                            float b3 = metaDo.state.transformY(metaDo.f1499in.readShort());
                            float r3 = metaDo.state.transformX(metaDo.f1499in.readShort());
                            float t3 = metaDo.state.transformY(metaDo.f1499in.readShort());
                            float l3 = metaDo.state.transformX(metaDo.f1499in.readShort());
                            float f = b3;
                            float f2 = r3;
                            float f3 = h;
                            float f4 = w;
                            metaDo.f1498cb.roundRectangle((double) l3, (double) b3, (double) (r3 - l3), (double) (t3 - b3), (double) ((h + w) / 4.0f));
                            strokeAndFill();
                            break;
                        } else {
                            break;
                        }
                    case META_ARC /*2071*/:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i28 = function;
                        if (!metaDo.isNullStrokeFill(metaDo.state.getLineNeutral())) {
                            float yend = metaDo.state.transformY(metaDo.f1499in.readShort());
                            float xend = metaDo.state.transformX(metaDo.f1499in.readShort());
                            float ystart = metaDo.state.transformY(metaDo.f1499in.readShort());
                            float xstart = metaDo.state.transformX(metaDo.f1499in.readShort());
                            float b4 = metaDo.state.transformY(metaDo.f1499in.readShort());
                            float r4 = metaDo.state.transformX(metaDo.f1499in.readShort());
                            float t4 = metaDo.state.transformY(metaDo.f1499in.readShort());
                            float l4 = metaDo.state.transformX(metaDo.f1499in.readShort());
                            float cx = (r4 + l4) / 2.0f;
                            float cy = (t4 + b4) / 2.0f;
                            float arc1 = getArc(cx, cy, xstart, ystart);
                            float arc22 = getArc(cx, cy, xend, yend) - arc1;
                            if (arc22 <= 0.0f) {
                                arc22 += 360.0f;
                            }
                            float f5 = xend;
                            float f6 = ystart;
                            float f7 = xstart;
                            float f8 = l4;
                            float f9 = cx;
                            float f10 = yend;
                            float f11 = r4;
                            float f12 = t4;
                            float f13 = arc1;
                            metaDo.f1498cb.arc((double) l4, (double) b4, (double) r4, (double) t4, (double) arc1, (double) arc22);
                            metaDo.f1498cb.stroke();
                            break;
                        } else {
                            break;
                        }
                    case META_PIE /*2074*/:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i29 = function;
                        if (!metaDo.isNullStrokeFill(metaDo.state.getLineNeutral())) {
                            float yend2 = metaDo.state.transformY(metaDo.f1499in.readShort());
                            float xend2 = metaDo.state.transformX(metaDo.f1499in.readShort());
                            float ystart2 = metaDo.state.transformY(metaDo.f1499in.readShort());
                            float xstart2 = metaDo.state.transformX(metaDo.f1499in.readShort());
                            float b5 = metaDo.state.transformY(metaDo.f1499in.readShort());
                            float r5 = metaDo.state.transformX(metaDo.f1499in.readShort());
                            float t5 = metaDo.state.transformY(metaDo.f1499in.readShort());
                            float l5 = metaDo.state.transformX(metaDo.f1499in.readShort());
                            float cx2 = (r5 + l5) / 2.0f;
                            float cy2 = (t5 + b5) / 2.0f;
                            float arc12 = getArc(cx2, cy2, xstart2, ystart2);
                            float arc23 = getArc(cx2, cy2, xend2, yend2) - arc12;
                            if (arc23 <= 0.0f) {
                                arc23 += 360.0f;
                            }
                            float f14 = yend2;
                            float f15 = xend2;
                            float f16 = ystart2;
                            float f17 = xstart2;
                            float f18 = l5;
                            float cx3 = cx2;
                            float f19 = r5;
                            float f20 = t5;
                            float f21 = arc12;
                            List<double[]> ar = PdfCanvas.bezierArc((double) l5, (double) b5, (double) r5, (double) t5, (double) arc12, (double) arc23);
                            if (ar.size() != 0) {
                                double[] pt = ar.get(0);
                                metaDo = this;
                                float cx4 = cx3;
                                metaDo.f1498cb.moveTo((double) cx4, (double) cy2);
                                metaDo.f1498cb.lineTo(pt[0], pt[1]);
                                for (int k6 = 0; k6 < ar.size(); k6++) {
                                    double[] pt2 = ar.get(k6);
                                    metaDo.f1498cb.curveTo(pt2[2], pt2[3], pt2[4], pt2[5], pt2[6], pt2[7]);
                                }
                                metaDo.f1498cb.lineTo((double) cx4, (double) cy2);
                                strokeAndFill();
                                break;
                            } else {
                                metaDo = this;
                                break;
                            }
                        } else {
                            break;
                        }
                    case META_CHORD /*2096*/:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i30 = function;
                        if (!metaDo.isNullStrokeFill(metaDo.state.getLineNeutral())) {
                            float yend3 = metaDo.state.transformY(metaDo.f1499in.readShort());
                            float xend3 = metaDo.state.transformX(metaDo.f1499in.readShort());
                            float ystart3 = metaDo.state.transformY(metaDo.f1499in.readShort());
                            float xstart3 = metaDo.state.transformX(metaDo.f1499in.readShort());
                            float b6 = metaDo.state.transformY(metaDo.f1499in.readShort());
                            float r6 = metaDo.state.transformX(metaDo.f1499in.readShort());
                            float t6 = metaDo.state.transformY(metaDo.f1499in.readShort());
                            float l6 = metaDo.state.transformX(metaDo.f1499in.readShort());
                            float cx5 = (r6 + l6) / 2.0f;
                            float cy3 = (t6 + b6) / 2.0f;
                            float arc13 = getArc(cx5, cy3, xstart3, ystart3);
                            float arc24 = getArc(cx5, cy3, xend3, yend3) - arc13;
                            if (arc24 <= 0.0f) {
                                arc2 = arc24 + 360.0f;
                            } else {
                                arc2 = arc24;
                            }
                            float f22 = cy3;
                            float f23 = l6;
                            float f24 = yend3;
                            float f25 = xend3;
                            float f26 = ystart3;
                            float f27 = r6;
                            float f28 = b6;
                            float f29 = t6;
                            float f30 = xstart3;
                            float f31 = cx5;
                            List<double[]> ar2 = PdfCanvas.bezierArc((double) l6, (double) b6, (double) r6, (double) t6, (double) arc13, (double) arc2);
                            if (ar2.size() != 0) {
                                double[] pt3 = ar2.get(0);
                                float cx6 = (float) pt3[0];
                                float cy4 = (float) pt3[1];
                                metaDo.f1498cb.moveTo((double) cx6, (double) cy4);
                                for (int k7 = 0; k7 < ar2.size(); k7++) {
                                    double[] pt4 = ar2.get(k7);
                                    metaDo.f1498cb.curveTo(pt4[2], pt4[3], pt4[4], pt4[5], pt4[6], pt4[7]);
                                }
                                metaDo.f1498cb.lineTo((double) cx6, (double) cy4);
                                strokeAndFill();
                                break;
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    case META_EXTTEXTOUT /*2610*/:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i31 = function;
                        int y4 = metaDo.f1499in.readShort();
                        int x4 = metaDo.f1499in.readShort();
                        int count2 = metaDo.f1499in.readWord();
                        int flag = metaDo.f1499in.readWord();
                        if ((flag & 6) != 0) {
                            x1 = metaDo.f1499in.readShort();
                            y1 = metaDo.f1499in.readShort();
                            x2 = metaDo.f1499in.readShort();
                            y2 = metaDo.f1499in.readShort();
                        } else {
                            x1 = 0;
                            y1 = 0;
                            x2 = 0;
                            y2 = 0;
                        }
                        byte[] text2 = new byte[count2];
                        int k8 = 0;
                        while (k8 < count2) {
                            byte c2 = (byte) metaDo.f1499in.readByte();
                            if (c2 != 0) {
                                text2[k8] = c2;
                                k8++;
                            }
                        }
                        try {
                            s2 = new String(text2, 0, k8, "Cp1252");
                        } catch (UnsupportedEncodingException e2) {
                            s2 = new String(text2, 0, k8);
                        }
                        int i32 = k8;
                        byte[] bArr = text2;
                        outputText(x4, y4, flag, x1, y1, x2, y2, s2);
                        break;
                    case META_DIBSTRETCHBLT /*2881*/:
                    case META_STRETCHDIB /*3907*/:
                        int readInt = metaDo.f1499in.readInt();
                        if (function == 3907) {
                            metaDo.f1499in.readWord();
                        }
                        int srcHeight = metaDo.f1499in.readShort();
                        int srcWidth = metaDo.f1499in.readShort();
                        int ySrc = metaDo.f1499in.readShort();
                        float xSrc = metaDo.f1499in.readShort();
                        float destHeight = metaDo.state.transformY(metaDo.f1499in.readShort()) - metaDo.state.transformY(0);
                        float destWidth = metaDo.state.transformX(metaDo.f1499in.readShort()) - metaDo.state.transformX(0);
                        float yDest = metaDo.state.transformY(metaDo.f1499in.readShort());
                        float xDest = metaDo.state.transformX(metaDo.f1499in.readShort());
                        byte[] b7 = new byte[((tsize2 * 2) - (metaDo.f1499in.getLength() - lenMarker2))];
                        for (int k9 = 0; k9 < b7.length; k9++) {
                            b7[k9] = (byte) metaDo.f1499in.readByte();
                        }
                        try {
                            metaDo.f1498cb.saveState();
                            lenMarker = lenMarker2;
                            tsize = tsize2;
                            int i33 = function;
                            float yDest2 = yDest;
                            float xDest2 = xDest;
                            int ySrc2 = ySrc;
                            int xSrc2 = xSrc;
                            try {
                                metaDo.f1498cb.rectangle((double) xDest, (double) yDest, (double) destWidth, (double) destHeight);
                                metaDo.f1498cb.clip();
                                metaDo.f1498cb.endPath();
                                ImageData bmpImage = ImageDataFactory.createBmp(b7, true);
                                PdfImageXObject imageXObject = new PdfImageXObject(bmpImage);
                                float width = (bmpImage.getWidth() * destWidth) / ((float) srcWidth);
                                float height = ((-destHeight) * bmpImage.getHeight()) / ((float) srcHeight);
                                try {
                                    ImageData imageData = bmpImage;
                                    metaDo.f1498cb.addXObject(imageXObject, new Rectangle(xDest2 - ((((float) xSrc2) * destWidth) / ((float) srcWidth)), (yDest2 + ((((float) ySrc2) * destHeight) / ((float) srcHeight))) - height, width, height));
                                    metaDo.f1498cb.restoreState();
                                } catch (Exception e3) {
                                    InputMeta inputMeta = metaDo.f1499in;
                                    inputMeta.skip((tsize * 2) - (inputMeta.getLength() - lenMarker));
                                }
                            } catch (Exception e4) {
                                int i34 = ySrc2;
                                int i35 = xSrc2;
                                InputMeta inputMeta2 = metaDo.f1499in;
                                inputMeta2.skip((tsize * 2) - (inputMeta2.getLength() - lenMarker));
                            }
                        } catch (Exception e5) {
                            float f32 = yDest;
                            float f33 = xDest;
                            lenMarker = lenMarker2;
                            tsize = tsize2;
                            int i36 = function;
                            int lenMarker3 = ySrc;
                            float xDest3 = xSrc;
                            InputMeta inputMeta22 = metaDo.f1499in;
                            inputMeta22.skip((tsize * 2) - (inputMeta22.getLength() - lenMarker));
                        }
                    default:
                        lenMarker = lenMarker2;
                        tsize = tsize2;
                        int i37 = function;
                        break;
                }
                InputMeta inputMeta222 = metaDo.f1499in;
                inputMeta222.skip((tsize * 2) - (inputMeta222.getLength() - lenMarker));
            }
        } else {
            throw new PdfException(PdfException.NotAPlaceableWindowsMetafile);
        }
    }

    public void outputText(int x, int y, int flag, int x1, int y1, int x2, int y2, String text) throws IOException {
        float textWidth;
        float tx;
        float ty;
        float textWidth2;
        MetaFont font = this.state.getCurrentFont();
        float refX = this.state.transformX(x);
        float refY = this.state.transformY(y);
        float angle = this.state.transformAngle(font.getAngle());
        float sin = (float) Math.sin((double) angle);
        float cos = (float) Math.cos((double) angle);
        float fontSize = font.getFontSize(this.state);
        FontProgram fp = font.getFont();
        int align = this.state.getTextAlign();
        int normalizedWidth = 0;
        byte[] bytes = font.encoding.convertToBytes(text);
        int length = bytes.length;
        int i = 0;
        while (i < length) {
            normalizedWidth += fp.getWidth(bytes[i] & UByte.MAX_VALUE);
            i++;
            int i2 = y;
            angle = angle;
        }
        float descender = (float) fp.getFontMetrics().getTypoDescender();
        float ury = (float) fp.getFontMetrics().getBbox()[3];
        this.f1498cb.saveState();
        int i3 = normalizedWidth;
        byte[] bArr = bytes;
        MetaFont font2 = font;
        FontProgram fontProgram = fp;
        float fontSize2 = fontSize;
        float f = sin;
        float f2 = cos;
        float f3 = refX;
        float textWidth3 = (fontSize / 1000.0f) * ((float) normalizedWidth);
        this.f1498cb.concatMatrix((double) cos, (double) sin, (double) (-sin), (double) cos, (double) refX, (double) refY);
        if ((align & 6) == 6) {
            textWidth = textWidth3;
            tx = (-textWidth) / 2.0f;
        } else {
            textWidth = textWidth3;
            if ((align & 2) == 2) {
                tx = -textWidth;
            } else {
                tx = 0.0f;
            }
        }
        if ((align & 24) == 24) {
            ty = 0.0f;
        } else if ((align & 8) == 8) {
            ty = -descender;
        } else {
            ty = -ury;
        }
        if (this.state.getBackgroundMode() == 2) {
            Color textColor = this.state.getCurrentBackgroundColor();
            this.f1498cb.setFillColor(textColor);
            Color color = textColor;
            float f4 = refY;
            float f5 = ury;
            this.f1498cb.rectangle((double) tx, (double) (ty + descender), (double) textWidth, (double) (ury - descender));
            this.f1498cb.fill();
        } else {
            float f6 = ury;
        }
        Color textColor2 = this.state.getCurrentTextColor();
        this.f1498cb.setFillColor(textColor2);
        this.f1498cb.beginText();
        float fontSize3 = fontSize2;
        this.f1498cb.setFontAndSize(PdfFontFactory.createFont(this.state.getCurrentFont().getFont(), "Cp1252", true), fontSize3);
        this.f1498cb.setTextMatrix(tx, ty);
        this.f1498cb.showText(text);
        this.f1498cb.endText();
        if (font2.isUnderline()) {
            int i4 = align;
            Color color2 = textColor2;
            textWidth2 = textWidth;
            this.f1498cb.rectangle((double) tx, (double) (ty - (fontSize3 / 4.0f)), (double) textWidth, (double) (fontSize3 / 15.0f));
            this.f1498cb.fill();
        } else {
            textWidth2 = textWidth;
            int i5 = align;
        }
        if (font2.isStrikeout()) {
            float f7 = tx;
            float f8 = ty;
            this.f1498cb.rectangle((double) tx, (double) ((fontSize3 / 3.0f) + ty), (double) textWidth2, (double) (fontSize3 / 15.0f));
            this.f1498cb.fill();
        } else {
            float f9 = ty;
            float f10 = textWidth2;
        }
        this.f1498cb.restoreState();
    }

    public boolean isNullStrokeFill(boolean isRectangle) {
        MetaPen pen = this.state.getCurrentPen();
        MetaBrush brush = this.state.getCurrentBrush();
        boolean result = true;
        boolean noPen = pen.getStyle() == 5;
        int style = brush.getStyle();
        boolean isBrush = style == 0 || (style == 2 && this.state.getBackgroundMode() == 2);
        if (!noPen || isBrush) {
            result = false;
        }
        if (!noPen) {
            if (isRectangle) {
                this.state.setLineJoinRectangle(this.f1498cb);
            } else {
                this.state.setLineJoinPolygon(this.f1498cb);
            }
        }
        return result;
    }

    public void strokeAndFill() {
        MetaPen pen = this.state.getCurrentPen();
        MetaBrush brush = this.state.getCurrentBrush();
        int penStyle = pen.getStyle();
        int brushStyle = brush.getStyle();
        if (penStyle == 5) {
            this.f1498cb.closePath();
            if (this.state.getPolyFillMode() == 1) {
                this.f1498cb.eoFill();
            } else {
                this.f1498cb.fill();
            }
        } else {
            if (!(brushStyle == 0 || (brushStyle == 2 && this.state.getBackgroundMode() == 2))) {
                this.f1498cb.closePathStroke();
            } else if (this.state.getPolyFillMode() == 1) {
                this.f1498cb.closePathEoFillStroke();
            } else {
                this.f1498cb.closePathFillStroke();
            }
        }
    }

    static float getArc(float xCenter, float yCenter, float xDot, float yDot) {
        double s = Math.atan2((double) (yDot - yCenter), (double) (xDot - xCenter));
        if (s < 0.0d) {
            s += 6.283185307179586d;
        }
        return (float) ((s / 3.141592653589793d) * 180.0d);
    }

    public static byte[] wrapBMP(ImageData image) throws IOException {
        byte[] data;
        if (image.getOriginalType() == ImageType.BMP) {
            if (image.getData() == null) {
                InputStream imgIn = image.getUrl().openStream();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                while (true) {
                    int read = imgIn.read();
                    int b = read;
                    if (read == -1) {
                        break;
                    }
                    out.write(b);
                }
                imgIn.close();
                data = out.toByteArray();
            } else {
                data = image.getData();
            }
            int sizeBmpWords = ((data.length - 14) + 1) >>> 1;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            writeWord(os, 1);
            writeWord(os, 9);
            writeWord(os, ViewUtils.EDGE_TO_EDGE_FLAGS);
            writeDWord(os, sizeBmpWords + 36 + 3);
            writeWord(os, 1);
            writeDWord(os, sizeBmpWords + 14);
            writeWord(os, 0);
            writeDWord(os, 4);
            writeWord(os, 259);
            writeWord(os, 8);
            writeDWord(os, 5);
            writeWord(os, META_SETWINDOWORG);
            writeWord(os, 0);
            writeWord(os, 0);
            writeDWord(os, 5);
            writeWord(os, META_SETWINDOWEXT);
            writeWord(os, (int) image.getHeight());
            writeWord(os, (int) image.getWidth());
            writeDWord(os, sizeBmpWords + 13);
            writeWord(os, META_DIBSTRETCHBLT);
            writeDWord(os, 13369376);
            writeWord(os, (int) image.getHeight());
            writeWord(os, (int) image.getWidth());
            writeWord(os, 0);
            writeWord(os, 0);
            writeWord(os, (int) image.getHeight());
            writeWord(os, (int) image.getWidth());
            writeWord(os, 0);
            writeWord(os, 0);
            os.write(data, 14, data.length - 14);
            if ((data.length & 1) == 1) {
                os.write(0);
            }
            writeDWord(os, 3);
            writeWord(os, 0);
            os.close();
            return os.toByteArray();
        }
        throw new PdfException(PdfException.OnlyBmpCanBeWrappedInWmf);
    }

    public static void writeWord(OutputStream os, int v) throws IOException {
        os.write(v & 255);
        os.write((v >>> 8) & 255);
    }

    public static void writeDWord(OutputStream os, int v) throws IOException {
        writeWord(os, v & 65535);
        writeWord(os, 65535 & (v >>> 16));
    }
}
