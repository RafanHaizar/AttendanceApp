package com.itextpdf.svg.utils;

import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.svg.exceptions.SvgLogMessageConstant;
import com.itextpdf.svg.exceptions.SvgProcessingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class TransformUtils {
    private static final String MATRIX = "MATRIX";
    private static final String ROTATE = "ROTATE";
    private static final String SCALE = "SCALE";
    private static final String SKEWX = "SKEWX";
    private static final String SKEWY = "SKEWY";
    private static final String TRANSLATE = "TRANSLATE";

    private TransformUtils() {
    }

    public static AffineTransform parseTransform(String transform) {
        if (transform == null) {
            throw new SvgProcessingException(SvgLogMessageConstant.TRANSFORM_NULL);
        } else if (!transform.isEmpty()) {
            AffineTransform matrix = new AffineTransform();
            for (String transformation : splitString(transform)) {
                AffineTransform newMatrix = transformationStringToMatrix(transformation);
                if (newMatrix != null) {
                    matrix.concatenate(newMatrix);
                }
            }
            return matrix;
        } else {
            throw new SvgProcessingException(SvgLogMessageConstant.TRANSFORM_EMPTY);
        }
    }

    private static List<String> splitString(String transform) {
        ArrayList<String> list = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(transform, ")", false);
        while (tokenizer.hasMoreTokens()) {
            String trim = tokenizer.nextToken().trim();
            if (trim != null && !trim.isEmpty()) {
                list.add(trim + ")");
            }
        }
        return list;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.itextpdf.kernel.geom.AffineTransform transformationStringToMatrix(java.lang.String r3) {
        /*
            java.lang.String r0 = getNameFromString(r3)
            java.lang.String r0 = r0.toUpperCase()
            boolean r1 = r0.isEmpty()
            if (r1 != 0) goto L_0x0094
            int r1 = r0.hashCode()
            switch(r1) {
                case -2027910207: goto L_0x0048;
                case -1871851173: goto L_0x003e;
                case -455540434: goto L_0x0034;
                case 78713130: goto L_0x002a;
                case 78955662: goto L_0x0020;
                case 78955663: goto L_0x0016;
                default: goto L_0x0015;
            }
        L_0x0015:
            goto L_0x0052
        L_0x0016:
            java.lang.String r1 = "SKEWY"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0015
            r1 = 5
            goto L_0x0053
        L_0x0020:
            java.lang.String r1 = "SKEWX"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0015
            r1 = 4
            goto L_0x0053
        L_0x002a:
            java.lang.String r1 = "SCALE"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0015
            r1 = 2
            goto L_0x0053
        L_0x0034:
            java.lang.String r1 = "TRANSLATE"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0015
            r1 = 1
            goto L_0x0053
        L_0x003e:
            java.lang.String r1 = "ROTATE"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0015
            r1 = 3
            goto L_0x0053
        L_0x0048:
            java.lang.String r1 = "MATRIX"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0015
            r1 = 0
            goto L_0x0053
        L_0x0052:
            r1 = -1
        L_0x0053:
            switch(r1) {
                case 0: goto L_0x008b;
                case 1: goto L_0x0082;
                case 2: goto L_0x0079;
                case 3: goto L_0x0070;
                case 4: goto L_0x0067;
                case 5: goto L_0x005e;
                default: goto L_0x0056;
            }
        L_0x0056:
            com.itextpdf.svg.exceptions.SvgProcessingException r1 = new com.itextpdf.svg.exceptions.SvgProcessingException
            java.lang.String r2 = "Unsupported type of transformation."
            r1.<init>(r2)
            throw r1
        L_0x005e:
            java.util.List r1 = getValuesFromTransformationString(r3)
            com.itextpdf.kernel.geom.AffineTransform r1 = createSkewYTransformation(r1)
            return r1
        L_0x0067:
            java.util.List r1 = getValuesFromTransformationString(r3)
            com.itextpdf.kernel.geom.AffineTransform r1 = createSkewXTransformation(r1)
            return r1
        L_0x0070:
            java.util.List r1 = getValuesFromTransformationString(r3)
            com.itextpdf.kernel.geom.AffineTransform r1 = createRotationTransformation(r1)
            return r1
        L_0x0079:
            java.util.List r1 = getValuesFromTransformationString(r3)
            com.itextpdf.kernel.geom.AffineTransform r1 = createScaleTransformation(r1)
            return r1
        L_0x0082:
            java.util.List r1 = getValuesFromTransformationString(r3)
            com.itextpdf.kernel.geom.AffineTransform r1 = createTranslateTransformation(r1)
            return r1
        L_0x008b:
            java.util.List r1 = getValuesFromTransformationString(r3)
            com.itextpdf.kernel.geom.AffineTransform r1 = createMatrixTransformation(r1)
            return r1
        L_0x0094:
            com.itextpdf.svg.exceptions.SvgProcessingException r1 = new com.itextpdf.svg.exceptions.SvgProcessingException
            java.lang.String r2 = "Transformation declaration is not formed correctly."
            r1.<init>(r2)
            goto L_0x009d
        L_0x009c:
            throw r1
        L_0x009d:
            goto L_0x009c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.svg.utils.TransformUtils.transformationStringToMatrix(java.lang.String):com.itextpdf.kernel.geom.AffineTransform");
    }

    private static AffineTransform createSkewYTransformation(List<String> values) {
        if (values.size() == 1) {
            return new AffineTransform(1.0d, Math.tan(Math.toRadians((double) CssUtils.parseFloat(values.get(0)).floatValue())), 0.0d, 1.0d, 0.0d, 0.0d);
        }
        List<String> list = values;
        throw new SvgProcessingException(SvgLogMessageConstant.TRANSFORM_INCORRECT_NUMBER_OF_VALUES);
    }

    private static AffineTransform createSkewXTransformation(List<String> values) {
        if (values.size() == 1) {
            return new AffineTransform(1.0d, 0.0d, Math.tan(Math.toRadians((double) CssUtils.parseFloat(values.get(0)).floatValue())), 1.0d, 0.0d, 0.0d);
        }
        List<String> list = values;
        throw new SvgProcessingException(SvgLogMessageConstant.TRANSFORM_INCORRECT_NUMBER_OF_VALUES);
    }

    private static AffineTransform createRotationTransformation(List<String> values) {
        if (values.size() == 1 || values.size() == 3) {
            double angle = Math.toRadians((double) CssUtils.parseFloat(values.get(0)).floatValue());
            if (values.size() != 3) {
                return AffineTransform.getRotateInstance(angle);
            }
            return AffineTransform.getRotateInstance(angle, (double) CssUtils.parseAbsoluteLength(values.get(1)), (double) CssUtils.parseAbsoluteLength(values.get(2)));
        }
        throw new SvgProcessingException(SvgLogMessageConstant.TRANSFORM_INCORRECT_NUMBER_OF_VALUES);
    }

    private static AffineTransform createScaleTransformation(List<String> values) {
        if (values.size() == 0 || values.size() > 2) {
            throw new SvgProcessingException(SvgLogMessageConstant.TRANSFORM_INCORRECT_NUMBER_OF_VALUES);
        }
        float scaleX = CssUtils.parseRelativeValue(values.get(0), 1.0f);
        return AffineTransform.getScaleInstance((double) scaleX, (double) (values.size() == 2 ? CssUtils.parseRelativeValue(values.get(1), 1.0f) : scaleX));
    }

    private static AffineTransform createTranslateTransformation(List<String> values) {
        if (values.size() == 0 || values.size() > 2) {
            throw new SvgProcessingException(SvgLogMessageConstant.TRANSFORM_INCORRECT_NUMBER_OF_VALUES);
        }
        return AffineTransform.getTranslateInstance((double) CssUtils.parseAbsoluteLength(values.get(0)), (double) (values.size() == 2 ? CssUtils.parseAbsoluteLength(values.get(1)) : 0.0f));
    }

    private static AffineTransform createMatrixTransformation(List<String> values) {
        List<String> list = values;
        if (values.size() == 6) {
            float a = Float.parseFloat(list.get(0));
            float b = Float.parseFloat(list.get(1));
            float c = Float.parseFloat(list.get(2));
            float f = a;
            float f2 = b;
            float f3 = c;
            return new AffineTransform((double) a, (double) b, (double) c, (double) Float.parseFloat(list.get(3)), (double) CssUtils.parseAbsoluteLength(list.get(4)), (double) CssUtils.parseAbsoluteLength(list.get(5)));
        }
        throw new SvgProcessingException(SvgLogMessageConstant.TRANSFORM_INCORRECT_NUMBER_OF_VALUES);
    }

    private static String getNameFromString(String transformation) {
        if (transformation.indexOf("(") != -1) {
            return transformation.substring(0, transformation.indexOf("("));
        }
        throw new SvgProcessingException(SvgLogMessageConstant.INVALID_TRANSFORM_DECLARATION);
    }

    private static List<String> getValuesFromTransformationString(String transformation) {
        return SvgCssUtils.splitValueList(transformation.substring(transformation.indexOf(40) + 1, transformation.indexOf(41)));
    }
}
