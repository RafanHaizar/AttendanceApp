package androidx.constraintlayout.core.motion.key;

import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.constraintlayout.core.motion.utils.FloatRect;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public class MotionKeyTrigger extends MotionKey {
    public static final String CROSS = "CROSS";
    public static final int KEY_TYPE = 5;
    public static final String NEGATIVE_CROSS = "negativeCross";
    public static final String POSITIVE_CROSS = "positiveCross";
    public static final String POST_LAYOUT = "postLayout";
    private static final String TAG = "KeyTrigger";
    public static final String TRIGGER_COLLISION_ID = "triggerCollisionId";
    public static final String TRIGGER_COLLISION_VIEW = "triggerCollisionView";
    public static final String TRIGGER_ID = "triggerID";
    public static final String TRIGGER_RECEIVER = "triggerReceiver";
    public static final String TRIGGER_SLACK = "triggerSlack";
    public static final int TYPE_CROSS = 312;
    public static final int TYPE_NEGATIVE_CROSS = 310;
    public static final int TYPE_POSITIVE_CROSS = 309;
    public static final int TYPE_POST_LAYOUT = 304;
    public static final int TYPE_TRIGGER_COLLISION_ID = 307;
    public static final int TYPE_TRIGGER_COLLISION_VIEW = 306;
    public static final int TYPE_TRIGGER_ID = 308;
    public static final int TYPE_TRIGGER_RECEIVER = 311;
    public static final int TYPE_TRIGGER_SLACK = 305;
    public static final int TYPE_VIEW_TRANSITION_ON_CROSS = 301;
    public static final int TYPE_VIEW_TRANSITION_ON_NEGATIVE_CROSS = 303;
    public static final int TYPE_VIEW_TRANSITION_ON_POSITIVE_CROSS = 302;
    public static final String VIEW_TRANSITION_ON_CROSS = "viewTransitionOnCross";
    public static final String VIEW_TRANSITION_ON_NEGATIVE_CROSS = "viewTransitionOnNegativeCross";
    public static final String VIEW_TRANSITION_ON_POSITIVE_CROSS = "viewTransitionOnPositiveCross";
    FloatRect mCollisionRect = new FloatRect();
    private String mCross = null;
    private int mCurveFit = -1;
    private boolean mFireCrossReset = true;
    private float mFireLastPos;
    private boolean mFireNegativeReset = true;
    private boolean mFirePositiveReset = true;
    private float mFireThreshold = Float.NaN;
    private String mNegativeCross = null;
    private String mPositiveCross = null;
    private boolean mPostLayout = false;
    FloatRect mTargetRect = new FloatRect();
    private int mTriggerCollisionId = UNSET;
    private int mTriggerID = UNSET;
    private int mTriggerReceiver = UNSET;
    float mTriggerSlack = 0.1f;
    int mViewTransitionOnCross = UNSET;
    int mViewTransitionOnNegativeCross = UNSET;
    int mViewTransitionOnPositiveCross = UNSET;

    public MotionKeyTrigger() {
        this.mType = 5;
        this.mCustom = new HashMap();
    }

    public void getAttributeNames(HashSet<String> hashSet) {
    }

    public void addValues(HashMap<String, SplineSet> hashMap) {
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getId(java.lang.String r3) {
        /*
            r2 = this;
            int r0 = r3.hashCode()
            r1 = -1
            switch(r0) {
                case -1594793529: goto L_0x007b;
                case -966421266: goto L_0x0070;
                case -786670827: goto L_0x0065;
                case -648752941: goto L_0x005a;
                case -638126837: goto L_0x004e;
                case -76025313: goto L_0x0043;
                case -9754574: goto L_0x0038;
                case 364489912: goto L_0x002d;
                case 1301930599: goto L_0x0022;
                case 1401391082: goto L_0x0017;
                case 1535404999: goto L_0x000a;
                default: goto L_0x0008;
            }
        L_0x0008:
            goto L_0x0087
        L_0x000a:
            java.lang.String r0 = "triggerReceiver"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 10
            goto L_0x0088
        L_0x0017:
            java.lang.String r0 = "postLayout"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 3
            goto L_0x0088
        L_0x0022:
            java.lang.String r0 = "viewTransitionOnCross"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 0
            goto L_0x0088
        L_0x002d:
            java.lang.String r0 = "triggerSlack"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 4
            goto L_0x0088
        L_0x0038:
            java.lang.String r0 = "viewTransitionOnNegativeCross"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 2
            goto L_0x0088
        L_0x0043:
            java.lang.String r0 = "triggerCollisionView"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 5
            goto L_0x0088
        L_0x004e:
            java.lang.String r0 = "negativeCross"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 9
            goto L_0x0088
        L_0x005a:
            java.lang.String r0 = "triggerID"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 7
            goto L_0x0088
        L_0x0065:
            java.lang.String r0 = "triggerCollisionId"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 6
            goto L_0x0088
        L_0x0070:
            java.lang.String r0 = "viewTransitionOnPositiveCross"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 1
            goto L_0x0088
        L_0x007b:
            java.lang.String r0 = "positiveCross"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 8
            goto L_0x0088
        L_0x0087:
            r0 = -1
        L_0x0088:
            switch(r0) {
                case 0: goto L_0x00aa;
                case 1: goto L_0x00a7;
                case 2: goto L_0x00a4;
                case 3: goto L_0x00a1;
                case 4: goto L_0x009e;
                case 5: goto L_0x009b;
                case 6: goto L_0x0098;
                case 7: goto L_0x0095;
                case 8: goto L_0x0092;
                case 9: goto L_0x008f;
                case 10: goto L_0x008c;
                default: goto L_0x008b;
            }
        L_0x008b:
            return r1
        L_0x008c:
            r0 = 311(0x137, float:4.36E-43)
            return r0
        L_0x008f:
            r0 = 310(0x136, float:4.34E-43)
            return r0
        L_0x0092:
            r0 = 309(0x135, float:4.33E-43)
            return r0
        L_0x0095:
            r0 = 308(0x134, float:4.32E-43)
            return r0
        L_0x0098:
            r0 = 307(0x133, float:4.3E-43)
            return r0
        L_0x009b:
            r0 = 306(0x132, float:4.29E-43)
            return r0
        L_0x009e:
            r0 = 305(0x131, float:4.27E-43)
            return r0
        L_0x00a1:
            r0 = 304(0x130, float:4.26E-43)
            return r0
        L_0x00a4:
            r0 = 303(0x12f, float:4.25E-43)
            return r0
        L_0x00a7:
            r0 = 302(0x12e, float:4.23E-43)
            return r0
        L_0x00aa:
            r0 = 301(0x12d, float:4.22E-43)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.key.MotionKeyTrigger.getId(java.lang.String):int");
    }

    public MotionKeyTrigger copy(MotionKey src) {
        super.copy(src);
        MotionKeyTrigger k = (MotionKeyTrigger) src;
        this.mCurveFit = k.mCurveFit;
        this.mCross = k.mCross;
        this.mTriggerReceiver = k.mTriggerReceiver;
        this.mNegativeCross = k.mNegativeCross;
        this.mPositiveCross = k.mPositiveCross;
        this.mTriggerID = k.mTriggerID;
        this.mTriggerCollisionId = k.mTriggerCollisionId;
        this.mTriggerSlack = k.mTriggerSlack;
        this.mFireCrossReset = k.mFireCrossReset;
        this.mFireNegativeReset = k.mFireNegativeReset;
        this.mFirePositiveReset = k.mFirePositiveReset;
        this.mFireThreshold = k.mFireThreshold;
        this.mFireLastPos = k.mFireLastPos;
        this.mPostLayout = k.mPostLayout;
        this.mCollisionRect = k.mCollisionRect;
        this.mTargetRect = k.mTargetRect;
        return this;
    }

    public MotionKey clone() {
        return new MotionKeyTrigger().copy((MotionKey) this);
    }

    private void fireCustom(String str, MotionWidget widget) {
        CustomVariable custom;
        boolean callAll = str.length() == 1;
        if (!callAll) {
            str = str.substring(1).toLowerCase(Locale.ROOT);
        }
        for (String name : this.mCustom.keySet()) {
            String lowerCase = name.toLowerCase(Locale.ROOT);
            if ((callAll || lowerCase.matches(str)) && (custom = (CustomVariable) this.mCustom.get(name)) != null) {
                custom.applyToWidget(widget);
            }
        }
    }

    public void conditionallyFire(float position, MotionWidget child) {
    }

    public boolean setValue(int type, int value) {
        switch (type) {
            case 301:
                this.mViewTransitionOnCross = value;
                return true;
            case 302:
                this.mViewTransitionOnPositiveCross = value;
                return true;
            case 303:
                this.mViewTransitionOnNegativeCross = value;
                return true;
            case 307:
                this.mTriggerCollisionId = value;
                return true;
            case 308:
                this.mTriggerID = toInt(Integer.valueOf(value));
                return true;
            case 311:
                this.mTriggerReceiver = value;
                return true;
            default:
                return super.setValue(type, value);
        }
    }

    public boolean setValue(int type, float value) {
        switch (type) {
            case 305:
                this.mTriggerSlack = value;
                return true;
            default:
                return super.setValue(type, value);
        }
    }

    public boolean setValue(int type, String value) {
        switch (type) {
            case 309:
                this.mPositiveCross = value;
                return true;
            case 310:
                this.mNegativeCross = value;
                return true;
            case 312:
                this.mCross = value;
                return true;
            default:
                return super.setValue(type, value);
        }
    }

    public boolean setValue(int type, boolean value) {
        switch (type) {
            case 304:
                this.mPostLayout = value;
                return true;
            default:
                return super.setValue(type, value);
        }
    }
}
