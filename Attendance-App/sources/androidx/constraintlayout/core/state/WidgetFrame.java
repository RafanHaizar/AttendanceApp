package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.motion.CustomAttribute;
import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.parser.CLElement;
import androidx.constraintlayout.core.parser.CLKey;
import androidx.constraintlayout.core.parser.CLNumber;
import androidx.constraintlayout.core.parser.CLObject;
import androidx.constraintlayout.core.parser.CLParsingException;
import androidx.constraintlayout.core.state.Transition;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.core.p001os.EnvironmentCompat;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class WidgetFrame {
    private static final boolean OLD_SYSTEM = true;
    public static float phone_orientation = Float.NaN;
    public float alpha = Float.NaN;
    public int bottom = 0;
    public float interpolatedPos = Float.NaN;
    public int left = 0;
    public final HashMap<String, CustomVariable> mCustom = new HashMap<>();
    public String name = null;
    public float pivotX = Float.NaN;
    public float pivotY = Float.NaN;
    public int right = 0;
    public float rotationX = Float.NaN;
    public float rotationY = Float.NaN;
    public float rotationZ = Float.NaN;
    public float scaleX = Float.NaN;
    public float scaleY = Float.NaN;
    public int top = 0;
    public float translationX = Float.NaN;
    public float translationY = Float.NaN;
    public float translationZ = Float.NaN;
    public int visibility = 0;
    public ConstraintWidget widget = null;

    public int width() {
        return Math.max(0, this.right - this.left);
    }

    public int height() {
        return Math.max(0, this.bottom - this.top);
    }

    public WidgetFrame() {
    }

    public WidgetFrame(ConstraintWidget widget2) {
        this.widget = widget2;
    }

    public WidgetFrame(WidgetFrame frame) {
        this.widget = frame.widget;
        this.left = frame.left;
        this.top = frame.top;
        this.right = frame.right;
        this.bottom = frame.bottom;
        updateAttributes(frame);
    }

    public void updateAttributes(WidgetFrame frame) {
        this.pivotX = frame.pivotX;
        this.pivotY = frame.pivotY;
        this.rotationX = frame.rotationX;
        this.rotationY = frame.rotationY;
        this.rotationZ = frame.rotationZ;
        this.translationX = frame.translationX;
        this.translationY = frame.translationY;
        this.translationZ = frame.translationZ;
        this.scaleX = frame.scaleX;
        this.scaleY = frame.scaleY;
        this.alpha = frame.alpha;
        this.visibility = frame.visibility;
        this.mCustom.clear();
        if (frame != null) {
            for (CustomVariable c : frame.mCustom.values()) {
                this.mCustom.put(c.getName(), c.copy());
            }
        }
    }

    public boolean isDefaultTransform() {
        if (!Float.isNaN(this.rotationX) || !Float.isNaN(this.rotationY) || !Float.isNaN(this.rotationZ) || !Float.isNaN(this.translationX) || !Float.isNaN(this.translationY) || !Float.isNaN(this.translationZ) || !Float.isNaN(this.scaleX) || !Float.isNaN(this.scaleY) || !Float.isNaN(this.alpha)) {
            return false;
        }
        return OLD_SYSTEM;
    }

    public static void interpolate(int parentWidth, int parentHeight, WidgetFrame frame, WidgetFrame start, WidgetFrame end, Transition transition, float progress) {
        int startWidth;
        int startHeight;
        float startAlpha;
        int endHeight;
        float startAlpha2;
        int interpolateStartFrame;
        int startY;
        int startX;
        float f;
        Iterator<String> it;
        int endX;
        int interpolateStartFrame2;
        int startX2;
        int endX2;
        int i = parentWidth;
        int i2 = parentHeight;
        WidgetFrame widgetFrame = frame;
        WidgetFrame widgetFrame2 = start;
        WidgetFrame widgetFrame3 = end;
        Transition transition2 = transition;
        int frameNumber = (int) (progress * 100.0f);
        int startX3 = widgetFrame2.left;
        int startY2 = widgetFrame2.top;
        int endX3 = widgetFrame3.left;
        int endY = widgetFrame3.top;
        int startWidth2 = widgetFrame2.right - widgetFrame2.left;
        int startHeight2 = widgetFrame2.bottom - widgetFrame2.top;
        int endWidth = widgetFrame3.right - widgetFrame3.left;
        int startWidth3 = startWidth2;
        int endHeight2 = widgetFrame3.bottom - widgetFrame3.top;
        float progressPosition = progress;
        float startAlpha3 = widgetFrame2.alpha;
        int startHeight3 = startHeight2;
        float endAlpha = widgetFrame3.alpha;
        if (widgetFrame2.visibility == 8) {
            startX3 = (int) (((float) startX3) - (((float) endWidth) / 2.0f));
            startY2 = (int) (((float) startY2) - (((float) endHeight2) / 2.0f));
            int startWidth4 = endWidth;
            startHeight = endHeight2;
            if (Float.isNaN(startAlpha3)) {
                startWidth = startWidth4;
                startAlpha = 0.0f;
            } else {
                float f2 = startAlpha3;
                startWidth = startWidth4;
                startAlpha = f2;
            }
        } else {
            startAlpha = startAlpha3;
            startWidth = startWidth3;
            startHeight = startHeight3;
        }
        int startWidth5 = endHeight2;
        int startX4 = startX3;
        if (widgetFrame3.visibility == 8) {
            endX3 = (int) (((float) endX3) - (((float) startWidth) / 2.0f));
            endY = (int) (((float) endY) - (((float) startHeight) / 2.0f));
            endWidth = startWidth;
            endHeight = startHeight;
            if (Float.isNaN(endAlpha)) {
                endAlpha = 0.0f;
            }
        } else {
            endHeight = startWidth5;
        }
        if (Float.isNaN(startAlpha) && !Float.isNaN(endAlpha)) {
            startAlpha = 1.0f;
        }
        if (!Float.isNaN(startAlpha) && Float.isNaN(endAlpha)) {
            endAlpha = 1.0f;
        }
        float startAlpha4 = startAlpha;
        if (widgetFrame2.visibility == 4) {
            startAlpha2 = 0.0f;
        } else {
            startAlpha2 = startAlpha4;
        }
        int startY3 = startY2;
        if (widgetFrame3.visibility == 4) {
            endAlpha = 0.0f;
        }
        if (widgetFrame.widget == null || !transition.hasPositionKeyframes()) {
            int i3 = parentHeight;
            f = progress;
            int i4 = frameNumber;
            startY = startY3;
            startX = startX4;
            interpolateStartFrame = endX3;
        } else {
            Transition.KeyPosition firstPosition = transition2.findPreviousPosition(widgetFrame.widget.stringId, frameNumber);
            Transition.KeyPosition lastPosition = transition2.findNextPosition(widgetFrame.widget.stringId, frameNumber);
            if (firstPosition == lastPosition) {
                lastPosition = null;
            }
            int interpolateEndFrame = 100;
            if (firstPosition != null) {
                int i5 = frameNumber;
                startX4 = (int) (firstPosition.f1001x * ((float) i));
                endX = endX3;
                startX2 = parentHeight;
                interpolateStartFrame2 = firstPosition.frame;
                startY3 = (int) (firstPosition.f1002y * ((float) startX2));
            } else {
                startX2 = parentHeight;
                int i6 = frameNumber;
                endX = endX3;
                interpolateStartFrame2 = 0;
            }
            if (lastPosition != null) {
                Transition.KeyPosition keyPosition = firstPosition;
                endX2 = (int) (lastPosition.f1001x * ((float) i));
                endY = (int) (lastPosition.f1002y * ((float) startX2));
                interpolateEndFrame = lastPosition.frame;
            } else {
                endX2 = endX;
            }
            f = progress;
            progressPosition = ((100.0f * f) - ((float) interpolateStartFrame2)) / ((float) (interpolateEndFrame - interpolateStartFrame2));
            interpolateStartFrame = endX2;
            startY = startY3;
            startX = startX4;
        }
        widgetFrame.widget = widgetFrame2.widget;
        int i7 = (int) (((float) startX) + (((float) (interpolateStartFrame - startX)) * progressPosition));
        widgetFrame.left = i7;
        int i8 = startX;
        int i9 = (int) (((float) startY) + (((float) (endY - startY)) * progressPosition));
        widgetFrame.top = i9;
        int i10 = startY;
        widgetFrame.right = i7 + ((int) (((1.0f - f) * ((float) startWidth)) + (((float) endWidth) * f)));
        widgetFrame.bottom = i9 + ((int) (((1.0f - f) * ((float) startHeight)) + (((float) endHeight) * f)));
        int i11 = startHeight;
        widgetFrame.pivotX = interpolate(widgetFrame2.pivotX, widgetFrame3.pivotX, 0.5f, f);
        widgetFrame.pivotY = interpolate(widgetFrame2.pivotY, widgetFrame3.pivotY, 0.5f, f);
        widgetFrame.rotationX = interpolate(widgetFrame2.rotationX, widgetFrame3.rotationX, 0.0f, f);
        widgetFrame.rotationY = interpolate(widgetFrame2.rotationY, widgetFrame3.rotationY, 0.0f, f);
        widgetFrame.rotationZ = interpolate(widgetFrame2.rotationZ, widgetFrame3.rotationZ, 0.0f, f);
        widgetFrame.scaleX = interpolate(widgetFrame2.scaleX, widgetFrame3.scaleX, 1.0f, f);
        widgetFrame.scaleY = interpolate(widgetFrame2.scaleY, widgetFrame3.scaleY, 1.0f, f);
        widgetFrame.translationX = interpolate(widgetFrame2.translationX, widgetFrame3.translationX, 0.0f, f);
        widgetFrame.translationY = interpolate(widgetFrame2.translationY, widgetFrame3.translationY, 0.0f, f);
        widgetFrame.translationZ = interpolate(widgetFrame2.translationZ, widgetFrame3.translationZ, 0.0f, f);
        widgetFrame.alpha = interpolate(startAlpha2, endAlpha, 1.0f, f);
        Set<String> keys = widgetFrame3.mCustom.keySet();
        widgetFrame.mCustom.clear();
        Iterator<String> it2 = keys.iterator();
        while (it2.hasNext()) {
            String key = it2.next();
            Set<String> keys2 = keys;
            if (widgetFrame2.mCustom.containsKey(key)) {
                CustomVariable startVariable = widgetFrame2.mCustom.get(key);
                CustomVariable endVariable = widgetFrame3.mCustom.get(key);
                CustomVariable interpolated = new CustomVariable(startVariable);
                it = it2;
                widgetFrame.mCustom.put(key, interpolated);
                if (startVariable.numberOfInterpolatedValues() == 1) {
                    String str = key;
                    interpolated.setValue((Object) Float.valueOf(interpolate(startVariable.getValueToInterpolate(), endVariable.getValueToInterpolate(), 0.0f, f)));
                } else {
                    int N = startVariable.numberOfInterpolatedValues();
                    float[] startValues = new float[N];
                    float[] endValues = new float[N];
                    startVariable.getValuesToInterpolate(startValues);
                    endVariable.getValuesToInterpolate(endValues);
                    CustomVariable customVariable = startVariable;
                    int i12 = 0;
                    while (i12 < N) {
                        startValues[i12] = interpolate(startValues[i12], endValues[i12], 0.0f, f);
                        interpolated.setValue(startValues);
                        i12++;
                        N = N;
                        endVariable = endVariable;
                        endValues = endValues;
                    }
                    CustomVariable customVariable2 = endVariable;
                    float[] fArr = endValues;
                }
            } else {
                it = it2;
                String str2 = key;
            }
            widgetFrame = frame;
            widgetFrame2 = start;
            widgetFrame3 = end;
            keys = keys2;
            it2 = it;
        }
    }

    private static float interpolate(float start, float end, float defaultValue, float progress) {
        boolean isStartUnset = Float.isNaN(start);
        boolean isEndUnset = Float.isNaN(end);
        if (isStartUnset && isEndUnset) {
            return Float.NaN;
        }
        if (isStartUnset) {
            start = defaultValue;
        }
        if (isEndUnset) {
            end = defaultValue;
        }
        return ((end - start) * progress) + start;
    }

    public float centerX() {
        int i = this.left;
        return ((float) i) + (((float) (this.right - i)) / 2.0f);
    }

    public float centerY() {
        int i = this.top;
        return ((float) i) + (((float) (this.bottom - i)) / 2.0f);
    }

    public WidgetFrame update() {
        ConstraintWidget constraintWidget = this.widget;
        if (constraintWidget != null) {
            this.left = constraintWidget.getLeft();
            this.top = this.widget.getTop();
            this.right = this.widget.getRight();
            this.bottom = this.widget.getBottom();
            updateAttributes(this.widget.frame);
        }
        return this;
    }

    public WidgetFrame update(ConstraintWidget widget2) {
        if (widget2 == null) {
            return this;
        }
        this.widget = widget2;
        update();
        return this;
    }

    public void addCustomColor(String name2, int color) {
        setCustomAttribute(name2, (int) TypedValues.Custom.TYPE_COLOR, color);
    }

    public int getCustomColor(String name2) {
        if (this.mCustom.containsKey(name2)) {
            return this.mCustom.get(name2).getColorValue();
        }
        return -21880;
    }

    public void addCustomFloat(String name2, float value) {
        setCustomAttribute(name2, 901, value);
    }

    public float getCustomFloat(String name2) {
        if (this.mCustom.containsKey(name2)) {
            return this.mCustom.get(name2).getFloatValue();
        }
        return Float.NaN;
    }

    public void setCustomAttribute(String name2, int type, float value) {
        if (this.mCustom.containsKey(name2)) {
            this.mCustom.get(name2).setFloatValue(value);
        } else {
            this.mCustom.put(name2, new CustomVariable(name2, type, value));
        }
    }

    public void setCustomAttribute(String name2, int type, int value) {
        if (this.mCustom.containsKey(name2)) {
            this.mCustom.get(name2).setIntValue(value);
        } else {
            this.mCustom.put(name2, new CustomVariable(name2, type, value));
        }
    }

    public void setCustomAttribute(String name2, int type, boolean value) {
        if (this.mCustom.containsKey(name2)) {
            this.mCustom.get(name2).setBooleanValue(value);
        } else {
            this.mCustom.put(name2, new CustomVariable(name2, type, value));
        }
    }

    public void setCustomAttribute(String name2, int type, String value) {
        if (this.mCustom.containsKey(name2)) {
            this.mCustom.get(name2).setStringValue(value);
        } else {
            this.mCustom.put(name2, new CustomVariable(name2, type, value));
        }
    }

    public CustomVariable getCustomAttribute(String name2) {
        return this.mCustom.get(name2);
    }

    public Set<String> getCustomAttributeNames() {
        return this.mCustom.keySet();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean setValue(java.lang.String r4, androidx.constraintlayout.core.parser.CLElement r5) throws androidx.constraintlayout.core.parser.CLParsingException {
        /*
            r3 = this;
            int r0 = r4.hashCode()
            r1 = 1
            r2 = 0
            switch(r0) {
                case -1881940865: goto L_0x00d2;
                case -1383228885: goto L_0x00c7;
                case -1349088399: goto L_0x00bc;
                case -1249320806: goto L_0x00b1;
                case -1249320805: goto L_0x00a6;
                case -1249320804: goto L_0x009b;
                case -1225497657: goto L_0x0090;
                case -1225497656: goto L_0x0085;
                case -1225497655: goto L_0x007a;
                case -987906986: goto L_0x006f;
                case -987906985: goto L_0x0063;
                case -908189618: goto L_0x0056;
                case -908189617: goto L_0x0049;
                case 115029: goto L_0x003c;
                case 3317767: goto L_0x0030;
                case 92909918: goto L_0x0024;
                case 108511772: goto L_0x0017;
                case 642850769: goto L_0x000b;
                default: goto L_0x0009;
            }
        L_0x0009:
            goto L_0x00de
        L_0x000b:
            java.lang.String r0 = "interpolatedPos"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 11
            goto L_0x00df
        L_0x0017:
            java.lang.String r0 = "right"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 15
            goto L_0x00df
        L_0x0024:
            java.lang.String r0 = "alpha"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 10
            goto L_0x00df
        L_0x0030:
            java.lang.String r0 = "left"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 14
            goto L_0x00df
        L_0x003c:
            java.lang.String r0 = "top"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 13
            goto L_0x00df
        L_0x0049:
            java.lang.String r0 = "scaleY"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 9
            goto L_0x00df
        L_0x0056:
            java.lang.String r0 = "scaleX"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 8
            goto L_0x00df
        L_0x0063:
            java.lang.String r0 = "pivotY"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 1
            goto L_0x00df
        L_0x006f:
            java.lang.String r0 = "pivotX"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 0
            goto L_0x00df
        L_0x007a:
            java.lang.String r0 = "translationZ"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 7
            goto L_0x00df
        L_0x0085:
            java.lang.String r0 = "translationY"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 6
            goto L_0x00df
        L_0x0090:
            java.lang.String r0 = "translationX"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 5
            goto L_0x00df
        L_0x009b:
            java.lang.String r0 = "rotationZ"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 4
            goto L_0x00df
        L_0x00a6:
            java.lang.String r0 = "rotationY"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 3
            goto L_0x00df
        L_0x00b1:
            java.lang.String r0 = "rotationX"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 2
            goto L_0x00df
        L_0x00bc:
            java.lang.String r0 = "custom"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 17
            goto L_0x00df
        L_0x00c7:
            java.lang.String r0 = "bottom"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 16
            goto L_0x00df
        L_0x00d2:
            java.lang.String r0 = "phone_orientation"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 12
            goto L_0x00df
        L_0x00de:
            r0 = -1
        L_0x00df:
            switch(r0) {
                case 0: goto L_0x015a;
                case 1: goto L_0x0153;
                case 2: goto L_0x014c;
                case 3: goto L_0x0145;
                case 4: goto L_0x013e;
                case 5: goto L_0x0137;
                case 6: goto L_0x0130;
                case 7: goto L_0x0129;
                case 8: goto L_0x0122;
                case 9: goto L_0x011b;
                case 10: goto L_0x0114;
                case 11: goto L_0x010d;
                case 12: goto L_0x0106;
                case 13: goto L_0x00ff;
                case 14: goto L_0x00f8;
                case 15: goto L_0x00f0;
                case 16: goto L_0x00e8;
                case 17: goto L_0x00e3;
                default: goto L_0x00e2;
            }
        L_0x00e2:
            return r2
        L_0x00e3:
            r3.parseCustom(r5)
            goto L_0x0161
        L_0x00e8:
            int r0 = r5.getInt()
            r3.bottom = r0
            goto L_0x0161
        L_0x00f0:
            int r0 = r5.getInt()
            r3.right = r0
            goto L_0x0161
        L_0x00f8:
            int r0 = r5.getInt()
            r3.left = r0
            goto L_0x0161
        L_0x00ff:
            int r0 = r5.getInt()
            r3.top = r0
            goto L_0x0161
        L_0x0106:
            float r0 = r5.getFloat()
            phone_orientation = r0
            goto L_0x0161
        L_0x010d:
            float r0 = r5.getFloat()
            r3.interpolatedPos = r0
            goto L_0x0161
        L_0x0114:
            float r0 = r5.getFloat()
            r3.alpha = r0
            goto L_0x0161
        L_0x011b:
            float r0 = r5.getFloat()
            r3.scaleY = r0
            goto L_0x0161
        L_0x0122:
            float r0 = r5.getFloat()
            r3.scaleX = r0
            goto L_0x0161
        L_0x0129:
            float r0 = r5.getFloat()
            r3.translationZ = r0
            goto L_0x0161
        L_0x0130:
            float r0 = r5.getFloat()
            r3.translationY = r0
            goto L_0x0161
        L_0x0137:
            float r0 = r5.getFloat()
            r3.translationX = r0
            goto L_0x0161
        L_0x013e:
            float r0 = r5.getFloat()
            r3.rotationZ = r0
            goto L_0x0161
        L_0x0145:
            float r0 = r5.getFloat()
            r3.rotationY = r0
            goto L_0x0161
        L_0x014c:
            float r0 = r5.getFloat()
            r3.rotationX = r0
            goto L_0x0161
        L_0x0153:
            float r0 = r5.getFloat()
            r3.pivotY = r0
            goto L_0x0161
        L_0x015a:
            float r0 = r5.getFloat()
            r3.pivotX = r0
        L_0x0161:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.state.WidgetFrame.setValue(java.lang.String, androidx.constraintlayout.core.parser.CLElement):boolean");
    }

    public String getId() {
        ConstraintWidget constraintWidget = this.widget;
        if (constraintWidget == null) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        return constraintWidget.stringId;
    }

    /* access modifiers changed from: package-private */
    public void parseCustom(CLElement custom) throws CLParsingException {
        CLObject obj = (CLObject) custom;
        int n = obj.size();
        for (int i = 0; i < n; i++) {
            CLKey k = (CLKey) obj.get(i);
            String content = k.content();
            CLElement v = k.getValue();
            String vStr = v.content();
            if (vStr.matches("#[0-9a-fA-F]+")) {
                setCustomAttribute(k.content(), (int) TypedValues.Custom.TYPE_COLOR, Integer.parseInt(vStr.substring(1), 16));
            } else if (v instanceof CLNumber) {
                setCustomAttribute(k.content(), 901, v.getFloat());
            } else {
                setCustomAttribute(k.content(), (int) TypedValues.Custom.TYPE_STRING, vStr);
            }
        }
    }

    public StringBuilder serialize(StringBuilder ret) {
        return serialize(ret, false);
    }

    public StringBuilder serialize(StringBuilder ret, boolean sendPhoneOrientation) {
        ret.append("{\n");
        add(ret, "left", this.left);
        add(ret, CommonCssConstants.TOP, this.top);
        add(ret, "right", this.right);
        add(ret, CommonCssConstants.BOTTOM, this.bottom);
        add(ret, "pivotX", this.pivotX);
        add(ret, "pivotY", this.pivotY);
        add(ret, "rotationX", this.rotationX);
        add(ret, "rotationY", this.rotationY);
        add(ret, "rotationZ", this.rotationZ);
        add(ret, "translationX", this.translationX);
        add(ret, "translationY", this.translationY);
        add(ret, "translationZ", this.translationZ);
        add(ret, "scaleX", this.scaleX);
        add(ret, "scaleY", this.scaleY);
        add(ret, "alpha", this.alpha);
        add(ret, "visibility", this.visibility);
        add(ret, "interpolatedPos", this.interpolatedPos);
        if (this.widget != null) {
            for (ConstraintAnchor.Type side : ConstraintAnchor.Type.values()) {
                serializeAnchor(ret, side);
            }
        }
        if (sendPhoneOrientation) {
            add(ret, "phone_orientation", phone_orientation);
        }
        if (sendPhoneOrientation) {
            add(ret, "phone_orientation", phone_orientation);
        }
        if (this.mCustom.size() != 0) {
            ret.append("custom : {\n");
            for (String s : this.mCustom.keySet()) {
                CustomVariable value = this.mCustom.get(s);
                ret.append(s);
                ret.append(": ");
                switch (value.getType()) {
                    case 900:
                        ret.append(value.getIntegerValue());
                        ret.append(",\n");
                        break;
                    case 901:
                    case TypedValues.Custom.TYPE_DIMENSION:
                        ret.append(value.getFloatValue());
                        ret.append(",\n");
                        break;
                    case TypedValues.Custom.TYPE_COLOR:
                        ret.append("'");
                        ret.append(CustomVariable.colorString(value.getIntegerValue()));
                        ret.append("',\n");
                        break;
                    case TypedValues.Custom.TYPE_STRING:
                        ret.append("'");
                        ret.append(value.getStringValue());
                        ret.append("',\n");
                        break;
                    case TypedValues.Custom.TYPE_BOOLEAN:
                        ret.append("'");
                        ret.append(value.getBooleanValue());
                        ret.append("',\n");
                        break;
                }
            }
            ret.append("}\n");
        }
        ret.append("}\n");
        return ret;
    }

    private void serializeAnchor(StringBuilder ret, ConstraintAnchor.Type type) {
        ConstraintAnchor anchor = this.widget.getAnchor(type);
        if (anchor != null && anchor.mTarget != null) {
            ret.append("Anchor");
            ret.append(type.name());
            ret.append(": ['");
            String str = anchor.mTarget.getOwner().stringId;
            ret.append(str == null ? "#PARENT" : str);
            ret.append("', '");
            ret.append(anchor.mTarget.getType().name());
            ret.append("', '");
            ret.append(anchor.mMargin);
            ret.append("'],\n");
        }
    }

    private static void add(StringBuilder s, String title, int value) {
        s.append(title);
        s.append(": ");
        s.append(value);
        s.append(",\n");
    }

    private static void add(StringBuilder s, String title, float value) {
        if (!Float.isNaN(value)) {
            s.append(title);
            s.append(": ");
            s.append(value);
            s.append(",\n");
        }
    }

    /* access modifiers changed from: package-private */
    public void printCustomAttributes() {
        String ss;
        StackTraceElement s = new Throwable().getStackTrace()[1];
        String ss2 = (".(" + s.getFileName() + ":" + s.getLineNumber() + ") " + s.getMethodName()) + " " + (hashCode() % 1000);
        if (this.widget != null) {
            ss = ss2 + "/" + (this.widget.hashCode() % 1000) + " ";
        } else {
            ss = ss2 + "/NULL ";
        }
        HashMap<String, CustomVariable> hashMap = this.mCustom;
        if (hashMap != null) {
            for (String key : hashMap.keySet()) {
                System.out.println(ss + this.mCustom.get(key).toString());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void logv(String str) {
        String ss;
        StackTraceElement s = new Throwable().getStackTrace()[1];
        String ss2 = (".(" + s.getFileName() + ":" + s.getLineNumber() + ") " + s.getMethodName()) + " " + (hashCode() % 1000);
        if (this.widget != null) {
            ss = ss2 + "/" + (this.widget.hashCode() % 1000);
        } else {
            ss = ss2 + "/NULL";
        }
        System.out.println(ss + " " + str);
    }

    public void setCustomValue(CustomAttribute valueAt, float[] mTempValues) {
    }
}
