package androidx.vectordrawable.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.InflateException;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.PathParser;
import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.svg.SvgConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimatorInflaterCompat {
    private static final boolean DBG_ANIMATOR_INFLATER = false;
    private static final int MAX_NUM_POINTS = 100;
    private static final String TAG = "AnimatorInflater";
    private static final int TOGETHER = 0;
    private static final int VALUE_TYPE_COLOR = 3;
    private static final int VALUE_TYPE_FLOAT = 0;
    private static final int VALUE_TYPE_INT = 1;
    private static final int VALUE_TYPE_PATH = 2;
    private static final int VALUE_TYPE_UNDEFINED = 4;

    public static Animator loadAnimator(Context context, int id) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 24) {
            return AnimatorInflater.loadAnimator(context, id);
        }
        return loadAnimator(context, context.getResources(), context.getTheme(), id);
    }

    public static Animator loadAnimator(Context context, Resources resources, Resources.Theme theme, int id) throws Resources.NotFoundException {
        return loadAnimator(context, resources, theme, id, 1.0f);
    }

    public static Animator loadAnimator(Context context, Resources resources, Resources.Theme theme, int id, float pathErrorScale) throws Resources.NotFoundException {
        XmlResourceParser parser = null;
        try {
            XmlResourceParser parser2 = resources.getAnimation(id);
            Animator animator = createAnimatorFromXml(context, resources, theme, parser2, pathErrorScale);
            if (parser2 != null) {
                parser2.close();
            }
            return animator;
        } catch (XmlPullParserException ex) {
            Resources.NotFoundException rnf = new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(id));
            rnf.initCause(ex);
            throw rnf;
        } catch (IOException ex2) {
            Resources.NotFoundException rnf2 = new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(id));
            rnf2.initCause(ex2);
            throw rnf2;
        } catch (Throwable th) {
            if (parser != null) {
                parser.close();
            }
            throw th;
        }
    }

    private static class PathDataEvaluator implements TypeEvaluator<PathParser.PathDataNode[]> {
        private PathParser.PathDataNode[] mNodeArray;

        PathDataEvaluator() {
        }

        PathDataEvaluator(PathParser.PathDataNode[] nodeArray) {
            this.mNodeArray = nodeArray;
        }

        public PathParser.PathDataNode[] evaluate(float fraction, PathParser.PathDataNode[] startPathData, PathParser.PathDataNode[] endPathData) {
            if (PathParser.canMorph(startPathData, endPathData)) {
                if (!PathParser.canMorph(this.mNodeArray, startPathData)) {
                    this.mNodeArray = PathParser.deepCopyNodes(startPathData);
                }
                for (int i = 0; i < startPathData.length; i++) {
                    this.mNodeArray[i].interpolatePathDataNode(startPathData[i], endPathData[i], fraction);
                }
                return this.mNodeArray;
            }
            throw new IllegalArgumentException("Can't interpolate between two incompatible pathData");
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v7, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v11, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.animation.PropertyValuesHolder getPVH(android.content.res.TypedArray r22, int r23, int r24, int r25, java.lang.String r26) {
        /*
            r0 = r22
            r1 = r24
            r2 = r25
            r3 = r26
            android.util.TypedValue r4 = r0.peekValue(r1)
            if (r4 == 0) goto L_0x0010
            r7 = 1
            goto L_0x0011
        L_0x0010:
            r7 = 0
        L_0x0011:
            if (r7 == 0) goto L_0x0016
            int r8 = r4.type
            goto L_0x0017
        L_0x0016:
            r8 = 0
        L_0x0017:
            android.util.TypedValue r9 = r0.peekValue(r2)
            if (r9 == 0) goto L_0x001f
            r10 = 1
            goto L_0x0020
        L_0x001f:
            r10 = 0
        L_0x0020:
            if (r10 == 0) goto L_0x0025
            int r11 = r9.type
            goto L_0x0026
        L_0x0025:
            r11 = 0
        L_0x0026:
            r12 = 4
            r13 = r23
            if (r13 != r12) goto L_0x003f
            if (r7 == 0) goto L_0x0033
            boolean r12 = isColorType(r8)
            if (r12 != 0) goto L_0x003b
        L_0x0033:
            if (r10 == 0) goto L_0x003d
            boolean r12 = isColorType(r11)
            if (r12 == 0) goto L_0x003d
        L_0x003b:
            r12 = 3
            goto L_0x0040
        L_0x003d:
            r12 = 0
            goto L_0x0040
        L_0x003f:
            r12 = r13
        L_0x0040:
            if (r12 != 0) goto L_0x0044
            r13 = 1
            goto L_0x0045
        L_0x0044:
            r13 = 0
        L_0x0045:
            r14 = 0
            r15 = 2
            if (r12 != r15) goto L_0x00f3
            java.lang.String r5 = r0.getString(r1)
            java.lang.String r6 = r0.getString(r2)
            androidx.core.graphics.PathParser$PathDataNode[] r15 = androidx.core.graphics.PathParser.createNodesFromPathData(r5)
            r18 = r4
            androidx.core.graphics.PathParser$PathDataNode[] r4 = androidx.core.graphics.PathParser.createNodesFromPathData(r6)
            if (r15 != 0) goto L_0x006a
            if (r4 == 0) goto L_0x0062
            goto L_0x006a
        L_0x0062:
            r19 = r9
            r20 = r11
            r21 = r14
            goto L_0x00eb
        L_0x006a:
            if (r15 == 0) goto L_0x00d3
            androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$PathDataEvaluator r19 = new androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$PathDataEvaluator
            r19.<init>()
            r20 = r19
            if (r4 == 0) goto L_0x00bf
            boolean r19 = androidx.core.graphics.PathParser.canMorph(r15, r4)
            if (r19 == 0) goto L_0x0094
            r19 = r9
            r9 = 2
            java.lang.Object[] r9 = new java.lang.Object[r9]
            r17 = 0
            r9[r17] = r15
            r16 = 1
            r9[r16] = r4
            r21 = r14
            r14 = r20
            android.animation.PropertyValuesHolder r9 = android.animation.PropertyValuesHolder.ofObject(r3, r14, r9)
            r14 = r9
            r20 = r11
            goto L_0x00d2
        L_0x0094:
            r19 = r9
            r21 = r14
            r14 = r20
            android.view.InflateException r9 = new android.view.InflateException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r20 = r11
            java.lang.String r11 = " Can't morph from "
            java.lang.StringBuilder r2 = r2.append(r11)
            java.lang.StringBuilder r2 = r2.append(r5)
            java.lang.String r11 = " to "
            java.lang.StringBuilder r2 = r2.append(r11)
            java.lang.StringBuilder r2 = r2.append(r6)
            java.lang.String r2 = r2.toString()
            r9.<init>(r2)
            throw r9
        L_0x00bf:
            r19 = r9
            r21 = r14
            r14 = r20
            r20 = r11
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r9 = 0
            r2[r9] = r15
            android.animation.PropertyValuesHolder r2 = android.animation.PropertyValuesHolder.ofObject(r3, r14, r2)
            r14 = r2
        L_0x00d2:
            goto L_0x00ed
        L_0x00d3:
            r19 = r9
            r20 = r11
            r21 = r14
            if (r4 == 0) goto L_0x00eb
            androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$PathDataEvaluator r2 = new androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$PathDataEvaluator
            r2.<init>()
            r9 = 1
            java.lang.Object[] r9 = new java.lang.Object[r9]
            r11 = 0
            r9[r11] = r4
            android.animation.PropertyValuesHolder r14 = android.animation.PropertyValuesHolder.ofObject(r3, r2, r9)
            goto L_0x00ed
        L_0x00eb:
            r14 = r21
        L_0x00ed:
            r11 = r25
            r9 = r20
            goto L_0x01e8
        L_0x00f3:
            r18 = r4
            r19 = r9
            r20 = r11
            r21 = r14
            r2 = 0
            r4 = 3
            if (r12 != r4) goto L_0x0103
            androidx.vectordrawable.graphics.drawable.ArgbEvaluator r2 = androidx.vectordrawable.graphics.drawable.ArgbEvaluator.getInstance()
        L_0x0103:
            r4 = 5
            r5 = 0
            if (r13 == 0) goto L_0x0162
            if (r7 == 0) goto L_0x0146
            if (r8 != r4) goto L_0x0110
            float r6 = r0.getDimension(r1, r5)
            goto L_0x0114
        L_0x0110:
            float r6 = r0.getFloat(r1, r5)
        L_0x0114:
            if (r10 == 0) goto L_0x0136
            r9 = r20
            if (r9 != r4) goto L_0x0121
            r11 = r25
            float r4 = r0.getDimension(r11, r5)
            goto L_0x0127
        L_0x0121:
            r11 = r25
            float r4 = r0.getFloat(r11, r5)
        L_0x0127:
            r5 = 2
            float[] r5 = new float[r5]
            r14 = 0
            r5[r14] = r6
            r15 = 1
            r5[r15] = r4
            android.animation.PropertyValuesHolder r5 = android.animation.PropertyValuesHolder.ofFloat(r3, r5)
            r14 = r5
            goto L_0x0160
        L_0x0136:
            r11 = r25
            r9 = r20
            r14 = 0
            r15 = 1
            float[] r4 = new float[r15]
            r4[r14] = r6
            android.animation.PropertyValuesHolder r4 = android.animation.PropertyValuesHolder.ofFloat(r3, r4)
            r14 = r4
            goto L_0x0160
        L_0x0146:
            r11 = r25
            r9 = r20
            if (r9 != r4) goto L_0x0151
            float r4 = r0.getDimension(r11, r5)
            goto L_0x0155
        L_0x0151:
            float r4 = r0.getFloat(r11, r5)
        L_0x0155:
            r5 = 1
            float[] r5 = new float[r5]
            r6 = 0
            r5[r6] = r4
            android.animation.PropertyValuesHolder r5 = android.animation.PropertyValuesHolder.ofFloat(r3, r5)
            r14 = r5
        L_0x0160:
            goto L_0x01e1
        L_0x0162:
            r11 = r25
            r9 = r20
            if (r7 == 0) goto L_0x01b8
            if (r8 != r4) goto L_0x0170
            float r6 = r0.getDimension(r1, r5)
            int r6 = (int) r6
            goto L_0x0183
        L_0x0170:
            boolean r6 = isColorType(r8)
            if (r6 == 0) goto L_0x017d
            r6 = 0
            int r14 = r0.getColor(r1, r6)
            r6 = r14
            goto L_0x0183
        L_0x017d:
            r6 = 0
            int r14 = r0.getInt(r1, r6)
            r6 = r14
        L_0x0183:
            if (r10 == 0) goto L_0x01ad
            if (r9 != r4) goto L_0x018f
            float r4 = r0.getDimension(r11, r5)
            int r4 = (int) r4
            r5 = r4
            r4 = 0
            goto L_0x01a0
        L_0x018f:
            boolean r4 = isColorType(r9)
            if (r4 == 0) goto L_0x019b
            r4 = 0
            int r5 = r0.getColor(r11, r4)
            goto L_0x01a0
        L_0x019b:
            r4 = 0
            int r5 = r0.getInt(r11, r4)
        L_0x01a0:
            r14 = 2
            int[] r14 = new int[r14]
            r14[r4] = r6
            r15 = 1
            r14[r15] = r5
            android.animation.PropertyValuesHolder r14 = android.animation.PropertyValuesHolder.ofInt(r3, r14)
            goto L_0x01e1
        L_0x01ad:
            r4 = 0
            r15 = 1
            int[] r5 = new int[r15]
            r5[r4] = r6
            android.animation.PropertyValuesHolder r14 = android.animation.PropertyValuesHolder.ofInt(r3, r5)
            goto L_0x01e1
        L_0x01b8:
            if (r10 == 0) goto L_0x01df
            if (r9 != r4) goto L_0x01c4
            float r4 = r0.getDimension(r11, r5)
            int r4 = (int) r4
            r5 = r4
            r4 = 0
            goto L_0x01d5
        L_0x01c4:
            boolean r4 = isColorType(r9)
            if (r4 == 0) goto L_0x01d0
            r4 = 0
            int r5 = r0.getColor(r11, r4)
            goto L_0x01d5
        L_0x01d0:
            r4 = 0
            int r5 = r0.getInt(r11, r4)
        L_0x01d5:
            r6 = 1
            int[] r6 = new int[r6]
            r6[r4] = r5
            android.animation.PropertyValuesHolder r14 = android.animation.PropertyValuesHolder.ofInt(r3, r6)
            goto L_0x01e1
        L_0x01df:
            r14 = r21
        L_0x01e1:
            if (r14 == 0) goto L_0x01e8
            if (r2 == 0) goto L_0x01e8
            r14.setEvaluator(r2)
        L_0x01e8:
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat.getPVH(android.content.res.TypedArray, int, int, int, java.lang.String):android.animation.PropertyValuesHolder");
    }

    private static void parseAnimatorFromTypeArray(ValueAnimator anim, TypedArray arrayAnimator, TypedArray arrayObjectAnimator, float pixelSize, XmlPullParser parser) {
        long duration = (long) TypedArrayUtils.getNamedInt(arrayAnimator, parser, TypedValues.TransitionType.S_DURATION, 1, 300);
        long startDelay = (long) TypedArrayUtils.getNamedInt(arrayAnimator, parser, "startOffset", 2, 0);
        int valueType = TypedArrayUtils.getNamedInt(arrayAnimator, parser, "valueType", 7, 4);
        if (TypedArrayUtils.hasAttribute(parser, "valueFrom") && TypedArrayUtils.hasAttribute(parser, "valueTo")) {
            if (valueType == 4) {
                valueType = inferValueTypeFromValues(arrayAnimator, 5, 6);
            }
            PropertyValuesHolder pvh = getPVH(arrayAnimator, valueType, 5, 6, "");
            if (pvh != null) {
                anim.setValues(new PropertyValuesHolder[]{pvh});
            }
        }
        anim.setDuration(duration);
        anim.setStartDelay(startDelay);
        anim.setRepeatCount(TypedArrayUtils.getNamedInt(arrayAnimator, parser, "repeatCount", 3, 0));
        anim.setRepeatMode(TypedArrayUtils.getNamedInt(arrayAnimator, parser, "repeatMode", 4, 1));
        if (arrayObjectAnimator != null) {
            setupObjectAnimator(anim, arrayObjectAnimator, valueType, pixelSize, parser);
        }
    }

    private static void setupObjectAnimator(ValueAnimator anim, TypedArray arrayObjectAnimator, int valueType, float pixelSize, XmlPullParser parser) {
        ObjectAnimator oa = (ObjectAnimator) anim;
        String pathData = TypedArrayUtils.getNamedString(arrayObjectAnimator, parser, "pathData", 1);
        if (pathData != null) {
            String propertyXName = TypedArrayUtils.getNamedString(arrayObjectAnimator, parser, "propertyXName", 2);
            String propertyYName = TypedArrayUtils.getNamedString(arrayObjectAnimator, parser, "propertyYName", 3);
            if (valueType == 2 || valueType == 4) {
            }
            if (propertyXName == null && propertyYName == null) {
                throw new InflateException(arrayObjectAnimator.getPositionDescription() + " propertyXName or propertyYName is needed for PathData");
            }
            setupPathMotion(PathParser.createPathFromPathData(pathData), oa, 0.5f * pixelSize, propertyXName, propertyYName);
            return;
        }
        oa.setPropertyName(TypedArrayUtils.getNamedString(arrayObjectAnimator, parser, "propertyName", 0));
    }

    private static void setupPathMotion(Path path, ObjectAnimator oa, float precision, String propertyXName, String propertyYName) {
        Path path2 = path;
        ObjectAnimator objectAnimator = oa;
        String str = propertyXName;
        String str2 = propertyYName;
        boolean z = false;
        PathMeasure measureForTotalLength = new PathMeasure(path2, false);
        float totalLength = 0.0f;
        ArrayList<Float> contourLengths = new ArrayList<>();
        contourLengths.add(Float.valueOf(0.0f));
        while (true) {
            totalLength += measureForTotalLength.getLength();
            contourLengths.add(Float.valueOf(totalLength));
            if (!measureForTotalLength.nextContour()) {
                break;
            }
            z = false;
            path2 = path;
        }
        PathMeasure pathMeasure = new PathMeasure(path2, z);
        int numPoints = Math.min(100, ((int) (totalLength / precision)) + 1);
        float[] mX = new float[numPoints];
        float[] mY = new float[numPoints];
        float[] position = new float[2];
        int contourIndex = 0;
        float step = totalLength / ((float) (numPoints - 1));
        float currentDistance = 0.0f;
        int i = 0;
        while (i < numPoints) {
            pathMeasure.getPosTan(currentDistance - contourLengths.get(contourIndex).floatValue(), position, (float[]) null);
            mX[i] = position[0];
            mY[i] = position[1];
            currentDistance += step;
            if (contourIndex + 1 < contourLengths.size() && currentDistance > contourLengths.get(contourIndex + 1).floatValue()) {
                contourIndex++;
                pathMeasure.nextContour();
            }
            i++;
            Path path3 = path;
        }
        PropertyValuesHolder x = null;
        PropertyValuesHolder y = null;
        if (str != null) {
            x = PropertyValuesHolder.ofFloat(str, mX);
        }
        if (str2 != null) {
            y = PropertyValuesHolder.ofFloat(str2, mY);
        }
        if (x == null) {
            objectAnimator.setValues(new PropertyValuesHolder[]{y});
        } else if (y == null) {
            objectAnimator.setValues(new PropertyValuesHolder[]{x});
        } else {
            objectAnimator.setValues(new PropertyValuesHolder[]{x, y});
        }
    }

    private static Animator createAnimatorFromXml(Context context, Resources res, Resources.Theme theme, XmlPullParser parser, float pixelSize) throws XmlPullParserException, IOException {
        return createAnimatorFromXml(context, res, theme, parser, Xml.asAttributeSet(parser), (AnimatorSet) null, 0, pixelSize);
    }

    private static Animator createAnimatorFromXml(Context context, Resources res, Resources.Theme theme, XmlPullParser parser, AttributeSet attrs, AnimatorSet parent, int sequenceOrdering, float pixelSize) throws XmlPullParserException, IOException {
        Resources resources = res;
        Resources.Theme theme2 = theme;
        XmlPullParser xmlPullParser = parser;
        AnimatorSet animatorSet = parent;
        int depth = parser.getDepth();
        Animator anim = null;
        ArrayList<Animator> childAnims = null;
        while (true) {
            int next = parser.next();
            int type = next;
            if (next != 3 || parser.getDepth() > depth) {
                if (type == 1) {
                    Context context2 = context;
                    break;
                } else if (type == 2) {
                    String name = parser.getName();
                    boolean gotValues = false;
                    if (name.equals("objectAnimator")) {
                        Context context3 = context;
                        anim = loadObjectAnimator(context, res, theme, attrs, pixelSize, parser);
                    } else if (name.equals("animator")) {
                        Context context4 = context;
                        anim = loadAnimator(context, res, theme, attrs, (ValueAnimator) null, pixelSize, parser);
                    } else if (name.equals(SvgConstants.Tags.SET)) {
                        Animator anim2 = new AnimatorSet();
                        TypedArray a = TypedArrayUtils.obtainAttributes(resources, theme2, attrs, AndroidResources.STYLEABLE_ANIMATOR_SET);
                        createAnimatorFromXml(context, res, theme, parser, attrs, (AnimatorSet) anim2, TypedArrayUtils.getNamedInt(a, xmlPullParser, "ordering", 0, 0), pixelSize);
                        a.recycle();
                        Context context5 = context;
                        anim = anim2;
                    } else if (name.equals("propertyValuesHolder")) {
                        PropertyValuesHolder[] values = loadValues(context, resources, theme2, xmlPullParser, Xml.asAttributeSet(parser));
                        if (values != null && (anim instanceof ValueAnimator)) {
                            ((ValueAnimator) anim).setValues(values);
                        }
                        gotValues = true;
                    } else {
                        Context context6 = context;
                        throw new RuntimeException("Unknown animator name: " + parser.getName());
                    }
                    if (animatorSet != null && !gotValues) {
                        if (childAnims == null) {
                            childAnims = new ArrayList<>();
                        }
                        childAnims.add(anim);
                    }
                }
            } else {
                Context context7 = context;
                break;
            }
        }
        if (!(animatorSet == null || childAnims == null)) {
            Animator[] animsArray = new Animator[childAnims.size()];
            int index = 0;
            Iterator<Animator> it = childAnims.iterator();
            while (it.hasNext()) {
                animsArray[index] = it.next();
                index++;
            }
            if (sequenceOrdering == 0) {
                animatorSet.playTogether(animsArray);
            } else {
                animatorSet.playSequentially(animsArray);
            }
        }
        return anim;
    }

    private static PropertyValuesHolder[] loadValues(Context context, Resources res, Resources.Theme theme, XmlPullParser parser, AttributeSet attrs) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = parser;
        ArrayList<PropertyValuesHolder> values = null;
        while (true) {
            int eventType = parser.getEventType();
            int type = eventType;
            if (eventType == 3 || type == 1) {
                Resources resources = res;
                Resources.Theme theme2 = theme;
                AttributeSet attributeSet = attrs;
                PropertyValuesHolder[] valuesArray = null;
            } else if (type != 2) {
                parser.next();
            } else {
                if (parser.getName().equals("propertyValuesHolder")) {
                    TypedArray a = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_PROPERTY_VALUES_HOLDER);
                    String propertyName = TypedArrayUtils.getNamedString(a, xmlPullParser, "propertyName", 3);
                    int valueType = TypedArrayUtils.getNamedInt(a, xmlPullParser, "valueType", 2, 4);
                    int valueType2 = valueType;
                    PropertyValuesHolder pvh = loadPvh(context, res, theme, parser, propertyName, valueType);
                    if (pvh == null) {
                        pvh = getPVH(a, valueType2, 0, 1, propertyName);
                    }
                    if (pvh != null) {
                        if (values == null) {
                            values = new ArrayList<>();
                        }
                        values.add(pvh);
                    }
                    a.recycle();
                } else {
                    Resources resources2 = res;
                    Resources.Theme theme3 = theme;
                    AttributeSet attributeSet2 = attrs;
                }
                parser.next();
            }
        }
        Resources resources3 = res;
        Resources.Theme theme22 = theme;
        AttributeSet attributeSet3 = attrs;
        PropertyValuesHolder[] valuesArray2 = null;
        if (values != null) {
            int count = values.size();
            valuesArray2 = new PropertyValuesHolder[count];
            for (int i = 0; i < count; i++) {
                valuesArray2[i] = values.get(i);
            }
        }
        return valuesArray2;
    }

    private static int inferValueTypeOfKeyframe(Resources res, Resources.Theme theme, AttributeSet attrs, XmlPullParser parser) {
        int valueType;
        TypedArray a = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_KEYFRAME);
        boolean hasValue = false;
        TypedValue keyframeValue = TypedArrayUtils.peekNamedValue(a, parser, XfdfConstants.VALUE, 0);
        if (keyframeValue != null) {
            hasValue = true;
        }
        if (!hasValue || !isColorType(keyframeValue.type)) {
            valueType = 0;
        } else {
            valueType = 3;
        }
        a.recycle();
        return valueType;
    }

    private static int inferValueTypeFromValues(TypedArray styledAttributes, int valueFromId, int valueToId) {
        TypedValue tvFrom = styledAttributes.peekValue(valueFromId);
        boolean hasTo = true;
        int toType = 0;
        boolean hasFrom = tvFrom != null;
        int fromType = hasFrom ? tvFrom.type : 0;
        TypedValue tvTo = styledAttributes.peekValue(valueToId);
        if (tvTo == null) {
            hasTo = false;
        }
        if (hasTo) {
            toType = tvTo.type;
        }
        if ((!hasFrom || !isColorType(fromType)) && (!hasTo || !isColorType(toType))) {
            return 0;
        }
        return 3;
    }

    private static void dumpKeyframes(Object[] keyframes, String header) {
        if (keyframes != null && keyframes.length != 0) {
            Log.d(TAG, header);
            int count = keyframes.length;
            for (int i = 0; i < count; i++) {
                Keyframe keyframe = keyframes[i];
                Object obj = "null";
                StringBuilder append = new StringBuilder().append("Keyframe ").append(i).append(": fraction ").append(keyframe.getFraction() < 0.0f ? obj : Float.valueOf(keyframe.getFraction())).append(", , value : ");
                if (keyframe.hasValue()) {
                    obj = keyframe.getValue();
                }
                Log.d(TAG, append.append(obj).toString());
            }
        }
    }

    private static PropertyValuesHolder loadPvh(Context context, Resources res, Resources.Theme theme, XmlPullParser parser, String propertyName, int valueType) throws XmlPullParserException, IOException {
        int type;
        PropertyValuesHolder value;
        PropertyValuesHolder value2;
        int type2;
        ArrayList<Keyframe> keyframes;
        PropertyValuesHolder value3 = null;
        ArrayList<Keyframe> keyframes2 = null;
        int valueType2 = valueType;
        while (true) {
            int next = parser.next();
            type = next;
            if (next == 3 || type == 1) {
                Resources resources = res;
                Resources.Theme theme2 = theme;
                XmlPullParser xmlPullParser = parser;
            } else if (parser.getName().equals("keyframe")) {
                if (valueType2 == 4) {
                    valueType2 = inferValueTypeOfKeyframe(res, theme, Xml.asAttributeSet(parser), parser);
                } else {
                    Resources resources2 = res;
                    Resources.Theme theme3 = theme;
                    XmlPullParser xmlPullParser2 = parser;
                }
                Keyframe keyframe = loadKeyframe(context, res, theme, Xml.asAttributeSet(parser), valueType2, parser);
                if (keyframe != null) {
                    if (keyframes2 == null) {
                        keyframes2 = new ArrayList<>();
                    }
                    keyframes2.add(keyframe);
                }
                parser.next();
            } else {
                Resources resources3 = res;
                Resources.Theme theme4 = theme;
                XmlPullParser xmlPullParser3 = parser;
            }
        }
        Resources resources4 = res;
        Resources.Theme theme22 = theme;
        XmlPullParser xmlPullParser4 = parser;
        if (keyframes2 != null) {
            int size = keyframes2.size();
            int count = size;
            if (size > 0) {
                Keyframe firstKeyframe = keyframes2.get(0);
                Keyframe lastKeyframe = keyframes2.get(count - 1);
                float endFraction = lastKeyframe.getFraction();
                float f = 0.0f;
                if (endFraction < 1.0f) {
                    if (endFraction < 0.0f) {
                        lastKeyframe.setFraction(1.0f);
                    } else {
                        keyframes2.add(keyframes2.size(), createNewKeyframe(lastKeyframe, 1.0f));
                        count++;
                    }
                }
                float startFraction = firstKeyframe.getFraction();
                if (startFraction != 0.0f) {
                    if (startFraction < 0.0f) {
                        firstKeyframe.setFraction(0.0f);
                    } else {
                        keyframes2.add(0, createNewKeyframe(firstKeyframe, 0.0f));
                        count++;
                    }
                }
                Keyframe[] keyframeArray = new Keyframe[count];
                keyframes2.toArray(keyframeArray);
                int i = 0;
                while (i < count) {
                    Keyframe keyframe2 = keyframeArray[i];
                    if (keyframe2.getFraction() >= f) {
                        value2 = value3;
                        keyframes = keyframes2;
                        type2 = type;
                    } else if (i == 0) {
                        keyframe2.setFraction(f);
                        value2 = value3;
                        keyframes = keyframes2;
                        type2 = type;
                    } else if (i == count - 1) {
                        keyframe2.setFraction(1.0f);
                        value2 = value3;
                        keyframes = keyframes2;
                        type2 = type;
                    } else {
                        int startIndex = i;
                        value2 = value3;
                        int j = startIndex + 1;
                        keyframes = keyframes2;
                        int endIndex = i;
                        while (true) {
                            type2 = type;
                            if (j >= count - 1) {
                                break;
                            } else if (keyframeArray[j].getFraction() >= 0.0f) {
                                break;
                            } else {
                                endIndex = j;
                                j++;
                                type = type2;
                            }
                        }
                        distributeKeyframes(keyframeArray, keyframeArray[endIndex + 1].getFraction() - keyframeArray[startIndex - 1].getFraction(), startIndex, endIndex);
                    }
                    i++;
                    keyframes2 = keyframes;
                    type = type2;
                    value3 = value2;
                    f = 0.0f;
                }
                ArrayList<Keyframe> arrayList = keyframes2;
                int i2 = type;
                PropertyValuesHolder value4 = PropertyValuesHolder.ofKeyframe(propertyName, keyframeArray);
                if (valueType2 != 3) {
                    return value4;
                }
                value4.setEvaluator(ArgbEvaluator.getInstance());
                return value4;
            }
            value = null;
            ArrayList<Keyframe> arrayList2 = keyframes2;
            int i3 = type;
            String str = propertyName;
        } else {
            value = null;
            ArrayList<Keyframe> arrayList3 = keyframes2;
            int i4 = type;
            String str2 = propertyName;
        }
        return value;
    }

    private static Keyframe createNewKeyframe(Keyframe sampleKeyframe, float fraction) {
        if (sampleKeyframe.getType() == Float.TYPE) {
            return Keyframe.ofFloat(fraction);
        }
        if (sampleKeyframe.getType() == Integer.TYPE) {
            return Keyframe.ofInt(fraction);
        }
        return Keyframe.ofObject(fraction);
    }

    private static void distributeKeyframes(Keyframe[] keyframes, float gap, int startIndex, int endIndex) {
        float increment = gap / ((float) ((endIndex - startIndex) + 2));
        for (int i = startIndex; i <= endIndex; i++) {
            keyframes[i].setFraction(keyframes[i - 1].getFraction() + increment);
        }
    }

    private static Keyframe loadKeyframe(Context context, Resources res, Resources.Theme theme, AttributeSet attrs, int valueType, XmlPullParser parser) throws XmlPullParserException, IOException {
        Keyframe keyframe;
        TypedArray a = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_KEYFRAME);
        Keyframe keyframe2 = null;
        float fraction = TypedArrayUtils.getNamedFloat(a, parser, "fraction", 3, -1.0f);
        TypedValue keyframeValue = TypedArrayUtils.peekNamedValue(a, parser, XfdfConstants.VALUE, 0);
        boolean hasValue = keyframeValue != null;
        if (valueType == 4) {
            if (!hasValue || !isColorType(keyframeValue.type)) {
                valueType = 0;
            } else {
                valueType = 3;
            }
        }
        if (hasValue) {
            switch (valueType) {
                case 0:
                    keyframe2 = Keyframe.ofFloat(fraction, TypedArrayUtils.getNamedFloat(a, parser, XfdfConstants.VALUE, 0, 0.0f));
                    break;
                case 1:
                case 3:
                    keyframe2 = Keyframe.ofInt(fraction, TypedArrayUtils.getNamedInt(a, parser, XfdfConstants.VALUE, 0, 0));
                    break;
            }
        } else {
            if (valueType == 0) {
                keyframe = Keyframe.ofFloat(fraction);
            } else {
                keyframe = Keyframe.ofInt(fraction);
            }
            keyframe2 = keyframe;
        }
        int resID = TypedArrayUtils.getNamedResourceId(a, parser, "interpolator", 1, 0);
        if (resID > 0) {
            keyframe2.setInterpolator(AnimationUtilsCompat.loadInterpolator(context, resID));
        }
        a.recycle();
        return keyframe2;
    }

    private static ObjectAnimator loadObjectAnimator(Context context, Resources res, Resources.Theme theme, AttributeSet attrs, float pathErrorScale, XmlPullParser parser) throws Resources.NotFoundException {
        ObjectAnimator anim = new ObjectAnimator();
        loadAnimator(context, res, theme, attrs, anim, pathErrorScale, parser);
        return anim;
    }

    private static ValueAnimator loadAnimator(Context context, Resources res, Resources.Theme theme, AttributeSet attrs, ValueAnimator anim, float pathErrorScale, XmlPullParser parser) throws Resources.NotFoundException {
        TypedArray arrayAnimator = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_ANIMATOR);
        TypedArray arrayObjectAnimator = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_PROPERTY_ANIMATOR);
        if (anim == null) {
            anim = new ValueAnimator();
        }
        parseAnimatorFromTypeArray(anim, arrayAnimator, arrayObjectAnimator, pathErrorScale, parser);
        int resID = TypedArrayUtils.getNamedResourceId(arrayAnimator, parser, "interpolator", 0, 0);
        if (resID > 0) {
            anim.setInterpolator(AnimationUtilsCompat.loadInterpolator(context, resID));
        }
        arrayAnimator.recycle();
        if (arrayObjectAnimator != null) {
            arrayObjectAnimator.recycle();
        }
        return anim;
    }

    private static boolean isColorType(int type) {
        return type >= 28 && type <= 31;
    }

    private AnimatorInflaterCompat() {
    }
}
