package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.HelperWidget;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.C0657R;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.core.p001os.EnvironmentCompat;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ConstraintSet {
    private static final int ALPHA = 43;
    private static final int ANIMATE_CIRCLE_ANGLE_TO = 82;
    private static final int ANIMATE_RELATIVE_TO = 64;
    private static final int BARRIER_ALLOWS_GONE_WIDGETS = 75;
    private static final int BARRIER_DIRECTION = 72;
    private static final int BARRIER_MARGIN = 73;
    private static final int BARRIER_TYPE = 1;
    public static final int BASELINE = 5;
    private static final int BASELINE_MARGIN = 93;
    private static final int BASELINE_TO_BASELINE = 1;
    private static final int BASELINE_TO_BOTTOM = 92;
    private static final int BASELINE_TO_TOP = 91;
    public static final int BOTTOM = 4;
    private static final int BOTTOM_MARGIN = 2;
    private static final int BOTTOM_TO_BOTTOM = 3;
    private static final int BOTTOM_TO_TOP = 4;
    public static final int CHAIN_PACKED = 2;
    public static final int CHAIN_SPREAD = 0;
    public static final int CHAIN_SPREAD_INSIDE = 1;
    private static final int CHAIN_USE_RTL = 71;
    private static final int CIRCLE = 61;
    private static final int CIRCLE_ANGLE = 63;
    private static final int CIRCLE_RADIUS = 62;
    public static final int CIRCLE_REFERENCE = 8;
    private static final int CONSTRAINED_HEIGHT = 81;
    private static final int CONSTRAINED_WIDTH = 80;
    private static final int CONSTRAINT_REFERENCED_IDS = 74;
    private static final int CONSTRAINT_TAG = 77;
    private static final boolean DEBUG = false;
    private static final int DIMENSION_RATIO = 5;
    private static final int DRAW_PATH = 66;
    private static final int EDITOR_ABSOLUTE_X = 6;
    private static final int EDITOR_ABSOLUTE_Y = 7;
    private static final int ELEVATION = 44;
    public static final int END = 7;
    private static final int END_MARGIN = 8;
    private static final int END_TO_END = 9;
    private static final int END_TO_START = 10;
    private static final String ERROR_MESSAGE = "XML parser error must be within a Constraint ";
    public static final int GONE = 8;
    private static final int GONE_BASELINE_MARGIN = 94;
    private static final int GONE_BOTTOM_MARGIN = 11;
    private static final int GONE_END_MARGIN = 12;
    private static final int GONE_LEFT_MARGIN = 13;
    private static final int GONE_RIGHT_MARGIN = 14;
    private static final int GONE_START_MARGIN = 15;
    private static final int GONE_TOP_MARGIN = 16;
    private static final int GUIDELINE_USE_RTL = 99;
    private static final int GUIDE_BEGIN = 17;
    private static final int GUIDE_END = 18;
    private static final int GUIDE_PERCENT = 19;
    private static final int HEIGHT_DEFAULT = 55;
    private static final int HEIGHT_MAX = 57;
    private static final int HEIGHT_MIN = 59;
    private static final int HEIGHT_PERCENT = 70;
    public static final int HORIZONTAL = 0;
    private static final int HORIZONTAL_BIAS = 20;
    public static final int HORIZONTAL_GUIDELINE = 0;
    private static final int HORIZONTAL_STYLE = 41;
    private static final int HORIZONTAL_WEIGHT = 39;
    private static final int INTERNAL_MATCH_CONSTRAINT = -3;
    private static final int INTERNAL_MATCH_PARENT = -1;
    private static final int INTERNAL_WRAP_CONTENT = -2;
    private static final int INTERNAL_WRAP_CONTENT_CONSTRAINED = -4;
    public static final int INVISIBLE = 4;
    private static final String KEY_PERCENT_PARENT = "parent";
    private static final String KEY_RATIO = "ratio";
    private static final String KEY_WEIGHT = "weight";
    private static final int LAYOUT_CONSTRAINT_HEIGHT = 96;
    private static final int LAYOUT_CONSTRAINT_WIDTH = 95;
    private static final int LAYOUT_HEIGHT = 21;
    private static final int LAYOUT_VISIBILITY = 22;
    private static final int LAYOUT_WIDTH = 23;
    private static final int LAYOUT_WRAP_BEHAVIOR = 97;
    public static final int LEFT = 1;
    private static final int LEFT_MARGIN = 24;
    private static final int LEFT_TO_LEFT = 25;
    private static final int LEFT_TO_RIGHT = 26;
    public static final int MATCH_CONSTRAINT = 0;
    public static final int MATCH_CONSTRAINT_PERCENT = 2;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    private static final int MOTION_STAGGER = 79;
    private static final int MOTION_TARGET = 98;
    private static final int ORIENTATION = 27;
    public static final int PARENT_ID = 0;
    private static final int PATH_MOTION_ARC = 76;
    private static final int PROGRESS = 68;
    private static final int QUANTIZE_MOTION_INTERPOLATOR = 86;
    private static final int QUANTIZE_MOTION_INTERPOLATOR_ID = 89;
    private static final int QUANTIZE_MOTION_INTERPOLATOR_STR = 90;
    private static final int QUANTIZE_MOTION_INTERPOLATOR_TYPE = 88;
    private static final int QUANTIZE_MOTION_PHASE = 85;
    private static final int QUANTIZE_MOTION_STEPS = 84;
    public static final int RIGHT = 2;
    private static final int RIGHT_MARGIN = 28;
    private static final int RIGHT_TO_LEFT = 29;
    private static final int RIGHT_TO_RIGHT = 30;
    public static final int ROTATE_LEFT_OF_PORTRATE = 4;
    public static final int ROTATE_NONE = 0;
    public static final int ROTATE_PORTRATE_OF_LEFT = 2;
    public static final int ROTATE_PORTRATE_OF_RIGHT = 1;
    public static final int ROTATE_RIGHT_OF_PORTRATE = 3;
    private static final int ROTATION = 60;
    private static final int ROTATION_X = 45;
    private static final int ROTATION_Y = 46;
    private static final int SCALE_X = 47;
    private static final int SCALE_Y = 48;
    public static final int START = 6;
    private static final int START_MARGIN = 31;
    private static final int START_TO_END = 32;
    private static final int START_TO_START = 33;
    private static final String TAG = "ConstraintSet";
    public static final int TOP = 3;
    private static final int TOP_MARGIN = 34;
    private static final int TOP_TO_BOTTOM = 35;
    private static final int TOP_TO_TOP = 36;
    private static final int TRANSFORM_PIVOT_TARGET = 83;
    private static final int TRANSFORM_PIVOT_X = 49;
    private static final int TRANSFORM_PIVOT_Y = 50;
    private static final int TRANSITION_EASING = 65;
    private static final int TRANSITION_PATH_ROTATE = 67;
    private static final int TRANSLATION_X = 51;
    private static final int TRANSLATION_Y = 52;
    private static final int TRANSLATION_Z = 53;
    public static final int UNSET = -1;
    private static final int UNUSED = 87;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_BIAS = 37;
    public static final int VERTICAL_GUIDELINE = 1;
    private static final int VERTICAL_STYLE = 42;
    private static final int VERTICAL_WEIGHT = 40;
    private static final int VIEW_ID = 38;
    /* access modifiers changed from: private */
    public static final int[] VISIBILITY_FLAGS = {0, 4, 8};
    private static final int VISIBILITY_MODE = 78;
    public static final int VISIBILITY_MODE_IGNORE = 1;
    public static final int VISIBILITY_MODE_NORMAL = 0;
    public static final int VISIBLE = 0;
    private static final int WIDTH_DEFAULT = 54;
    private static final int WIDTH_MAX = 56;
    private static final int WIDTH_MIN = 58;
    private static final int WIDTH_PERCENT = 69;
    public static final int WRAP_CONTENT = -2;
    private static SparseIntArray mapToConstant = new SparseIntArray();
    private static SparseIntArray overrideMapToConstant = new SparseIntArray();
    public String derivedState = "";
    /* access modifiers changed from: private */
    public HashMap<Integer, Constraint> mConstraints = new HashMap<>();
    private boolean mForceId = true;
    public String mIdString;
    public int mRotate = 0;
    private HashMap<String, ConstraintAttribute> mSavedAttributes = new HashMap<>();
    private boolean mValidate;

    static {
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintLeft_toLeftOf, 25);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintLeft_toRightOf, 26);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintRight_toLeftOf, 29);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintRight_toRightOf, 30);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintTop_toTopOf, 36);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintTop_toBottomOf, 35);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintBottom_toTopOf, 4);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintBottom_toBottomOf, 3);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintBaseline_toBaselineOf, 1);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintBaseline_toTopOf, 91);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintBaseline_toBottomOf, 92);
        mapToConstant.append(C0657R.styleable.Constraint_layout_editor_absoluteX, 6);
        mapToConstant.append(C0657R.styleable.Constraint_layout_editor_absoluteY, 7);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintGuide_begin, 17);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintGuide_end, 18);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintGuide_percent, 19);
        mapToConstant.append(C0657R.styleable.Constraint_guidelineUseRtl, 99);
        mapToConstant.append(C0657R.styleable.Constraint_android_orientation, 27);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintStart_toEndOf, 32);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintStart_toStartOf, 33);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintEnd_toStartOf, 10);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintEnd_toEndOf, 9);
        mapToConstant.append(C0657R.styleable.Constraint_layout_goneMarginLeft, 13);
        mapToConstant.append(C0657R.styleable.Constraint_layout_goneMarginTop, 16);
        mapToConstant.append(C0657R.styleable.Constraint_layout_goneMarginRight, 14);
        mapToConstant.append(C0657R.styleable.Constraint_layout_goneMarginBottom, 11);
        mapToConstant.append(C0657R.styleable.Constraint_layout_goneMarginStart, 15);
        mapToConstant.append(C0657R.styleable.Constraint_layout_goneMarginEnd, 12);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintVertical_weight, 40);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintHorizontal_weight, 39);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintHorizontal_chainStyle, 41);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintVertical_chainStyle, 42);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintHorizontal_bias, 20);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintVertical_bias, 37);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintDimensionRatio, 5);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintLeft_creator, 87);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintTop_creator, 87);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintRight_creator, 87);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintBottom_creator, 87);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintBaseline_creator, 87);
        mapToConstant.append(C0657R.styleable.Constraint_android_layout_marginLeft, 24);
        mapToConstant.append(C0657R.styleable.Constraint_android_layout_marginRight, 28);
        mapToConstant.append(C0657R.styleable.Constraint_android_layout_marginStart, 31);
        mapToConstant.append(C0657R.styleable.Constraint_android_layout_marginEnd, 8);
        mapToConstant.append(C0657R.styleable.Constraint_android_layout_marginTop, 34);
        mapToConstant.append(C0657R.styleable.Constraint_android_layout_marginBottom, 2);
        mapToConstant.append(C0657R.styleable.Constraint_android_layout_width, 23);
        mapToConstant.append(C0657R.styleable.Constraint_android_layout_height, 21);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintWidth, 95);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintHeight, 96);
        mapToConstant.append(C0657R.styleable.Constraint_android_visibility, 22);
        mapToConstant.append(C0657R.styleable.Constraint_android_alpha, 43);
        mapToConstant.append(C0657R.styleable.Constraint_android_elevation, 44);
        mapToConstant.append(C0657R.styleable.Constraint_android_rotationX, 45);
        mapToConstant.append(C0657R.styleable.Constraint_android_rotationY, 46);
        mapToConstant.append(C0657R.styleable.Constraint_android_rotation, 60);
        mapToConstant.append(C0657R.styleable.Constraint_android_scaleX, 47);
        mapToConstant.append(C0657R.styleable.Constraint_android_scaleY, 48);
        mapToConstant.append(C0657R.styleable.Constraint_android_transformPivotX, 49);
        mapToConstant.append(C0657R.styleable.Constraint_android_transformPivotY, 50);
        mapToConstant.append(C0657R.styleable.Constraint_android_translationX, 51);
        mapToConstant.append(C0657R.styleable.Constraint_android_translationY, 52);
        mapToConstant.append(C0657R.styleable.Constraint_android_translationZ, 53);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintWidth_default, 54);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintHeight_default, 55);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintWidth_max, 56);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintHeight_max, 57);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintWidth_min, 58);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintHeight_min, 59);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintCircle, 61);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintCircleRadius, 62);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintCircleAngle, 63);
        mapToConstant.append(C0657R.styleable.Constraint_animateRelativeTo, 64);
        mapToConstant.append(C0657R.styleable.Constraint_transitionEasing, 65);
        mapToConstant.append(C0657R.styleable.Constraint_drawPath, 66);
        mapToConstant.append(C0657R.styleable.Constraint_transitionPathRotate, 67);
        mapToConstant.append(C0657R.styleable.Constraint_motionStagger, 79);
        mapToConstant.append(C0657R.styleable.Constraint_android_id, 38);
        mapToConstant.append(C0657R.styleable.Constraint_motionProgress, 68);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintWidth_percent, 69);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintHeight_percent, 70);
        mapToConstant.append(C0657R.styleable.Constraint_layout_wrapBehaviorInParent, 97);
        mapToConstant.append(C0657R.styleable.Constraint_chainUseRtl, 71);
        mapToConstant.append(C0657R.styleable.Constraint_barrierDirection, 72);
        mapToConstant.append(C0657R.styleable.Constraint_barrierMargin, 73);
        mapToConstant.append(C0657R.styleable.Constraint_constraint_referenced_ids, 74);
        mapToConstant.append(C0657R.styleable.Constraint_barrierAllowsGoneWidgets, 75);
        mapToConstant.append(C0657R.styleable.Constraint_pathMotionArc, 76);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constraintTag, 77);
        mapToConstant.append(C0657R.styleable.Constraint_visibilityMode, 78);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constrainedWidth, 80);
        mapToConstant.append(C0657R.styleable.Constraint_layout_constrainedHeight, 81);
        mapToConstant.append(C0657R.styleable.Constraint_polarRelativeTo, 82);
        mapToConstant.append(C0657R.styleable.Constraint_transformPivotTarget, 83);
        mapToConstant.append(C0657R.styleable.Constraint_quantizeMotionSteps, 84);
        mapToConstant.append(C0657R.styleable.Constraint_quantizeMotionPhase, 85);
        mapToConstant.append(C0657R.styleable.Constraint_quantizeMotionInterpolator, 86);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_editor_absoluteY, 6);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_editor_absoluteY, 7);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_orientation, 27);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_goneMarginLeft, 13);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_goneMarginTop, 16);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_goneMarginRight, 14);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_goneMarginBottom, 11);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_goneMarginStart, 15);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_goneMarginEnd, 12);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintVertical_weight, 40);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintHorizontal_weight, 39);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintHorizontal_chainStyle, 41);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintVertical_chainStyle, 42);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintHorizontal_bias, 20);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintVertical_bias, 37);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintDimensionRatio, 5);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintLeft_creator, 87);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintTop_creator, 87);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintRight_creator, 87);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintBottom_creator, 87);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintBaseline_creator, 87);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_layout_marginLeft, 24);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_layout_marginRight, 28);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_layout_marginStart, 31);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_layout_marginEnd, 8);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_layout_marginTop, 34);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_layout_marginBottom, 2);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_layout_width, 23);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_layout_height, 21);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintWidth, 95);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintHeight, 96);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_visibility, 22);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_alpha, 43);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_elevation, 44);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_rotationX, 45);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_rotationY, 46);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_rotation, 60);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_scaleX, 47);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_scaleY, 48);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_transformPivotX, 49);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_transformPivotY, 50);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_translationX, 51);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_translationY, 52);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_translationZ, 53);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintWidth_default, 54);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintHeight_default, 55);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintWidth_max, 56);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintHeight_max, 57);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintWidth_min, 58);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintHeight_min, 59);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintCircleRadius, 62);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintCircleAngle, 63);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_animateRelativeTo, 64);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_transitionEasing, 65);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_drawPath, 66);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_transitionPathRotate, 67);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_motionStagger, 79);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_android_id, 38);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_motionTarget, 98);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_motionProgress, 68);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintWidth_percent, 69);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintHeight_percent, 70);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_chainUseRtl, 71);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_barrierDirection, 72);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_barrierMargin, 73);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_constraint_referenced_ids, 74);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_barrierAllowsGoneWidgets, 75);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_pathMotionArc, 76);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constraintTag, 77);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_visibilityMode, 78);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constrainedWidth, 80);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_constrainedHeight, 81);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_polarRelativeTo, 82);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_transformPivotTarget, 83);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_quantizeMotionSteps, 84);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_quantizeMotionPhase, 85);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_quantizeMotionInterpolator, 86);
        overrideMapToConstant.append(C0657R.styleable.ConstraintOverride_layout_wrapBehaviorInParent, 97);
    }

    public HashMap<String, ConstraintAttribute> getCustomAttributeSet() {
        return this.mSavedAttributes;
    }

    public Constraint getParameters(int mId) {
        return get(mId);
    }

    public void readFallback(ConstraintSet set) {
        for (Integer key : set.mConstraints.keySet()) {
            int id = key.intValue();
            Constraint parent = set.mConstraints.get(key);
            if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
                this.mConstraints.put(Integer.valueOf(id), new Constraint());
            }
            Constraint constraint = this.mConstraints.get(Integer.valueOf(id));
            if (constraint != null) {
                if (!constraint.layout.mApply) {
                    constraint.layout.copyFrom(parent.layout);
                }
                if (!constraint.propertySet.mApply) {
                    constraint.propertySet.copyFrom(parent.propertySet);
                }
                if (!constraint.transform.mApply) {
                    constraint.transform.copyFrom(parent.transform);
                }
                if (!constraint.motion.mApply) {
                    constraint.motion.copyFrom(parent.motion);
                }
                for (String s : parent.mCustomConstraints.keySet()) {
                    if (!constraint.mCustomConstraints.containsKey(s)) {
                        constraint.mCustomConstraints.put(s, parent.mCustomConstraints.get(s));
                    }
                }
            }
        }
    }

    public void readFallback(ConstraintLayout constraintLayout) {
        int count = constraintLayout.getChildCount();
        int i = 0;
        while (i < count) {
            View view = constraintLayout.getChildAt(i);
            ConstraintLayout.LayoutParams param = (ConstraintLayout.LayoutParams) view.getLayoutParams();
            int id = view.getId();
            if (!this.mForceId || id != -1) {
                if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
                    this.mConstraints.put(Integer.valueOf(id), new Constraint());
                }
                Constraint constraint = this.mConstraints.get(Integer.valueOf(id));
                if (constraint != null) {
                    if (!constraint.layout.mApply) {
                        constraint.fillFrom(id, param);
                        if (view instanceof ConstraintHelper) {
                            constraint.layout.mReferenceIds = ((ConstraintHelper) view).getReferencedIds();
                            if (view instanceof Barrier) {
                                Barrier barrier = (Barrier) view;
                                constraint.layout.mBarrierAllowsGoneWidgets = barrier.getAllowsGoneWidget();
                                constraint.layout.mBarrierDirection = barrier.getType();
                                constraint.layout.mBarrierMargin = barrier.getMargin();
                            }
                        }
                        constraint.layout.mApply = true;
                    }
                    if (!constraint.propertySet.mApply) {
                        constraint.propertySet.visibility = view.getVisibility();
                        constraint.propertySet.alpha = view.getAlpha();
                        constraint.propertySet.mApply = true;
                    }
                    if (!constraint.transform.mApply) {
                        constraint.transform.mApply = true;
                        constraint.transform.rotation = view.getRotation();
                        constraint.transform.rotationX = view.getRotationX();
                        constraint.transform.rotationY = view.getRotationY();
                        constraint.transform.scaleX = view.getScaleX();
                        constraint.transform.scaleY = view.getScaleY();
                        float pivotX = view.getPivotX();
                        float pivotY = view.getPivotY();
                        if (!(((double) pivotX) == 0.0d && ((double) pivotY) == 0.0d)) {
                            constraint.transform.transformPivotX = pivotX;
                            constraint.transform.transformPivotY = pivotY;
                        }
                        constraint.transform.translationX = view.getTranslationX();
                        constraint.transform.translationY = view.getTranslationY();
                        if (Build.VERSION.SDK_INT >= 21) {
                            constraint.transform.translationZ = view.getTranslationZ();
                            if (constraint.transform.applyElevation) {
                                constraint.transform.elevation = view.getElevation();
                            }
                        }
                    }
                }
                i++;
            } else {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
        }
    }

    public void applyDeltaFrom(ConstraintSet cs) {
        for (Constraint from : cs.mConstraints.values()) {
            if (from.mDelta != null) {
                if (from.mTargetString != null) {
                    for (Integer intValue : this.mConstraints.keySet()) {
                        Constraint potential = getConstraint(intValue.intValue());
                        if (potential.layout.mConstraintTag != null && from.mTargetString.matches(potential.layout.mConstraintTag)) {
                            from.mDelta.applyDelta(potential);
                            potential.mCustomConstraints.putAll((HashMap) from.mCustomConstraints.clone());
                        }
                    }
                } else {
                    from.mDelta.applyDelta(getConstraint(from.mViewId));
                }
            }
        }
    }

    static void parseDimensionConstraints(Object data, TypedArray a, int attr, int orientation) {
        if (data != null) {
            int finalValue = 0;
            boolean finalConstrained = false;
            switch (a.peekValue(attr).type) {
                case 3:
                    parseDimensionConstraintsString(data, a.getString(attr), orientation);
                    return;
                case 5:
                    finalValue = a.getDimensionPixelSize(attr, 0);
                    break;
                default:
                    int value = a.getInt(attr, 0);
                    switch (value) {
                        case -4:
                            finalValue = -2;
                            finalConstrained = true;
                            break;
                        case -3:
                            finalValue = 0;
                            break;
                        case -2:
                        case -1:
                            finalValue = value;
                            break;
                    }
            }
            if ((data instanceof ConstraintLayout.LayoutParams) != 0) {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) data;
                if (orientation == 0) {
                    params.width = finalValue;
                    params.constrainedWidth = finalConstrained;
                    return;
                }
                params.height = finalValue;
                params.constrainedHeight = finalConstrained;
            } else if (data instanceof Layout) {
                Layout params2 = (Layout) data;
                if (orientation == 0) {
                    params2.mWidth = finalValue;
                    params2.constrainedWidth = finalConstrained;
                    return;
                }
                params2.mHeight = finalValue;
                params2.constrainedHeight = finalConstrained;
            } else if (data instanceof Constraint.Delta) {
                Constraint.Delta params3 = (Constraint.Delta) data;
                if (orientation == 0) {
                    params3.add(23, finalValue);
                    params3.add(80, finalConstrained);
                    return;
                }
                params3.add(21, finalValue);
                params3.add(81, finalConstrained);
            }
        }
    }

    static void parseDimensionRatioString(ConstraintLayout.LayoutParams params, String value) {
        int commaIndex;
        String dimensionRatio = value;
        float dimensionRatioValue = Float.NaN;
        int dimensionRatioSide = -1;
        if (dimensionRatio != null) {
            int len = dimensionRatio.length();
            int commaIndex2 = dimensionRatio.indexOf(44);
            if (commaIndex2 <= 0 || commaIndex2 >= len - 1) {
                commaIndex = 0;
            } else {
                String dimension = dimensionRatio.substring(0, commaIndex2);
                if (dimension.equalsIgnoreCase("W")) {
                    dimensionRatioSide = 0;
                } else if (dimension.equalsIgnoreCase("H")) {
                    dimensionRatioSide = 1;
                }
                commaIndex = commaIndex2 + 1;
            }
            int colonIndex = dimensionRatio.indexOf(58);
            if (colonIndex < 0 || colonIndex >= len - 1) {
                String r = dimensionRatio.substring(commaIndex);
                if (r.length() > 0) {
                    try {
                        dimensionRatioValue = Float.parseFloat(r);
                    } catch (NumberFormatException e) {
                    }
                }
            } else {
                String nominator = dimensionRatio.substring(commaIndex, colonIndex);
                String denominator = dimensionRatio.substring(colonIndex + 1);
                if (nominator.length() > 0 && denominator.length() > 0) {
                    try {
                        float nominatorValue = Float.parseFloat(nominator);
                        float denominatorValue = Float.parseFloat(denominator);
                        if (nominatorValue > 0.0f && denominatorValue > 0.0f) {
                            dimensionRatioValue = dimensionRatioSide == 1 ? Math.abs(denominatorValue / nominatorValue) : Math.abs(nominatorValue / denominatorValue);
                        }
                    } catch (NumberFormatException e2) {
                    }
                }
            }
        }
        params.dimensionRatio = dimensionRatio;
        params.dimensionRatioValue = dimensionRatioValue;
        params.dimensionRatioSide = dimensionRatioSide;
    }

    static void parseDimensionConstraintsString(Object data, String value, int orientation) {
        if (value != null) {
            int equalIndex = value.indexOf(61);
            int len = value.length();
            if (equalIndex > 0 && equalIndex < len - 1) {
                String key = value.substring(0, equalIndex);
                String val = value.substring(equalIndex + 1);
                if (val.length() > 0) {
                    String key2 = key.trim();
                    String val2 = val.trim();
                    if (KEY_RATIO.equalsIgnoreCase(key2)) {
                        if (data instanceof ConstraintLayout.LayoutParams) {
                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) data;
                            if (orientation == 0) {
                                params.width = 0;
                            } else {
                                params.height = 0;
                            }
                            parseDimensionRatioString(params, val2);
                        } else if (data instanceof Layout) {
                            ((Layout) data).dimensionRatio = val2;
                        } else if (data instanceof Constraint.Delta) {
                            ((Constraint.Delta) data).add(5, val2);
                        }
                    } else if (KEY_WEIGHT.equalsIgnoreCase(key2)) {
                        try {
                            float weight = Float.parseFloat(val2);
                            if (data instanceof ConstraintLayout.LayoutParams) {
                                ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams) data;
                                if (orientation == 0) {
                                    params2.width = 0;
                                    params2.horizontalWeight = weight;
                                } else {
                                    params2.height = 0;
                                    params2.verticalWeight = weight;
                                }
                            } else if (data instanceof Layout) {
                                Layout params3 = (Layout) data;
                                if (orientation == 0) {
                                    params3.mWidth = 0;
                                    params3.horizontalWeight = weight;
                                    return;
                                }
                                params3.mHeight = 0;
                                params3.verticalWeight = weight;
                            } else if (data instanceof Constraint.Delta) {
                                Constraint.Delta params4 = (Constraint.Delta) data;
                                if (orientation == 0) {
                                    params4.add(23, 0);
                                    params4.add(39, weight);
                                    return;
                                }
                                params4.add(21, 0);
                                params4.add(40, weight);
                            }
                        } catch (NumberFormatException e) {
                        }
                    } else if (KEY_PERCENT_PARENT.equalsIgnoreCase(key2)) {
                        try {
                            float percent = Math.max(0.0f, Math.min(1.0f, Float.parseFloat(val2)));
                            if (data instanceof ConstraintLayout.LayoutParams) {
                                ConstraintLayout.LayoutParams params5 = (ConstraintLayout.LayoutParams) data;
                                if (orientation == 0) {
                                    params5.width = 0;
                                    params5.matchConstraintPercentWidth = percent;
                                    params5.matchConstraintDefaultWidth = 2;
                                } else {
                                    params5.height = 0;
                                    params5.matchConstraintPercentHeight = percent;
                                    params5.matchConstraintDefaultHeight = 2;
                                }
                            } else if (data instanceof Layout) {
                                Layout params6 = (Layout) data;
                                if (orientation == 0) {
                                    params6.mWidth = 0;
                                    params6.widthPercent = percent;
                                    params6.widthDefault = 2;
                                    return;
                                }
                                params6.mHeight = 0;
                                params6.heightPercent = percent;
                                params6.heightDefault = 2;
                            } else if (data instanceof Constraint.Delta) {
                                Constraint.Delta params7 = (Constraint.Delta) data;
                                if (orientation == 0) {
                                    params7.add(23, 0);
                                    params7.add(54, 2);
                                    return;
                                }
                                params7.add(21, 0);
                                params7.add(55, 2);
                            }
                        } catch (NumberFormatException e2) {
                        }
                    }
                }
            }
        }
    }

    public static class Layout {
        private static final int BARRIER_ALLOWS_GONE_WIDGETS = 75;
        private static final int BARRIER_DIRECTION = 72;
        private static final int BARRIER_MARGIN = 73;
        private static final int BASELINE_MARGIN = 80;
        private static final int BASELINE_TO_BASELINE = 1;
        private static final int BASELINE_TO_BOTTOM = 78;
        private static final int BASELINE_TO_TOP = 77;
        private static final int BOTTOM_MARGIN = 2;
        private static final int BOTTOM_TO_BOTTOM = 3;
        private static final int BOTTOM_TO_TOP = 4;
        private static final int CHAIN_USE_RTL = 71;
        private static final int CIRCLE = 61;
        private static final int CIRCLE_ANGLE = 63;
        private static final int CIRCLE_RADIUS = 62;
        private static final int CONSTRAINED_HEIGHT = 88;
        private static final int CONSTRAINED_WIDTH = 87;
        private static final int CONSTRAINT_REFERENCED_IDS = 74;
        private static final int CONSTRAINT_TAG = 89;
        private static final int DIMENSION_RATIO = 5;
        private static final int EDITOR_ABSOLUTE_X = 6;
        private static final int EDITOR_ABSOLUTE_Y = 7;
        private static final int END_MARGIN = 8;
        private static final int END_TO_END = 9;
        private static final int END_TO_START = 10;
        private static final int GONE_BASELINE_MARGIN = 79;
        private static final int GONE_BOTTOM_MARGIN = 11;
        private static final int GONE_END_MARGIN = 12;
        private static final int GONE_LEFT_MARGIN = 13;
        private static final int GONE_RIGHT_MARGIN = 14;
        private static final int GONE_START_MARGIN = 15;
        private static final int GONE_TOP_MARGIN = 16;
        private static final int GUIDE_BEGIN = 17;
        private static final int GUIDE_END = 18;
        private static final int GUIDE_PERCENT = 19;
        private static final int GUIDE_USE_RTL = 90;
        private static final int HEIGHT_DEFAULT = 82;
        private static final int HEIGHT_MAX = 83;
        private static final int HEIGHT_MIN = 85;
        private static final int HEIGHT_PERCENT = 70;
        private static final int HORIZONTAL_BIAS = 20;
        private static final int HORIZONTAL_STYLE = 39;
        private static final int HORIZONTAL_WEIGHT = 37;
        private static final int LAYOUT_CONSTRAINT_HEIGHT = 42;
        private static final int LAYOUT_CONSTRAINT_WIDTH = 41;
        private static final int LAYOUT_HEIGHT = 21;
        private static final int LAYOUT_WIDTH = 22;
        private static final int LAYOUT_WRAP_BEHAVIOR = 76;
        private static final int LEFT_MARGIN = 23;
        private static final int LEFT_TO_LEFT = 24;
        private static final int LEFT_TO_RIGHT = 25;
        private static final int ORIENTATION = 26;
        private static final int RIGHT_MARGIN = 27;
        private static final int RIGHT_TO_LEFT = 28;
        private static final int RIGHT_TO_RIGHT = 29;
        private static final int START_MARGIN = 30;
        private static final int START_TO_END = 31;
        private static final int START_TO_START = 32;
        private static final int TOP_MARGIN = 33;
        private static final int TOP_TO_BOTTOM = 34;
        private static final int TOP_TO_TOP = 35;
        public static final int UNSET = -1;
        public static final int UNSET_GONE_MARGIN = Integer.MIN_VALUE;
        private static final int UNUSED = 91;
        private static final int VERTICAL_BIAS = 36;
        private static final int VERTICAL_STYLE = 40;
        private static final int VERTICAL_WEIGHT = 38;
        private static final int WIDTH_DEFAULT = 81;
        private static final int WIDTH_MAX = 84;
        private static final int WIDTH_MIN = 86;
        private static final int WIDTH_PERCENT = 69;
        private static SparseIntArray mapToConstant;
        public int baselineMargin = 0;
        public int baselineToBaseline = -1;
        public int baselineToBottom = -1;
        public int baselineToTop = -1;
        public int bottomMargin = 0;
        public int bottomToBottom = -1;
        public int bottomToTop = -1;
        public float circleAngle = 0.0f;
        public int circleConstraint = -1;
        public int circleRadius = 0;
        public boolean constrainedHeight = false;
        public boolean constrainedWidth = false;
        public String dimensionRatio = null;
        public int editorAbsoluteX = -1;
        public int editorAbsoluteY = -1;
        public int endMargin = 0;
        public int endToEnd = -1;
        public int endToStart = -1;
        public int goneBaselineMargin = Integer.MIN_VALUE;
        public int goneBottomMargin = Integer.MIN_VALUE;
        public int goneEndMargin = Integer.MIN_VALUE;
        public int goneLeftMargin = Integer.MIN_VALUE;
        public int goneRightMargin = Integer.MIN_VALUE;
        public int goneStartMargin = Integer.MIN_VALUE;
        public int goneTopMargin = Integer.MIN_VALUE;
        public int guideBegin = -1;
        public int guideEnd = -1;
        public float guidePercent = -1.0f;
        public boolean guidelineUseRtl = true;
        public int heightDefault = 0;
        public int heightMax = 0;
        public int heightMin = 0;
        public float heightPercent = 1.0f;
        public float horizontalBias = 0.5f;
        public int horizontalChainStyle = 0;
        public float horizontalWeight = -1.0f;
        public int leftMargin = 0;
        public int leftToLeft = -1;
        public int leftToRight = -1;
        public boolean mApply = false;
        public boolean mBarrierAllowsGoneWidgets = true;
        public int mBarrierDirection = -1;
        public int mBarrierMargin = 0;
        public String mConstraintTag;
        public int mHeight;
        public int mHelperType = -1;
        public boolean mIsGuideline = false;
        public boolean mOverride = false;
        public String mReferenceIdString;
        public int[] mReferenceIds;
        public int mWidth;
        public int mWrapBehavior = 0;
        public int orientation = -1;
        public int rightMargin = 0;
        public int rightToLeft = -1;
        public int rightToRight = -1;
        public int startMargin = 0;
        public int startToEnd = -1;
        public int startToStart = -1;
        public int topMargin = 0;
        public int topToBottom = -1;
        public int topToTop = -1;
        public float verticalBias = 0.5f;
        public int verticalChainStyle = 0;
        public float verticalWeight = -1.0f;
        public int widthDefault = 0;
        public int widthMax = 0;
        public int widthMin = 0;
        public float widthPercent = 1.0f;

        public void copyFrom(Layout src) {
            this.mIsGuideline = src.mIsGuideline;
            this.mWidth = src.mWidth;
            this.mApply = src.mApply;
            this.mHeight = src.mHeight;
            this.guideBegin = src.guideBegin;
            this.guideEnd = src.guideEnd;
            this.guidePercent = src.guidePercent;
            this.guidelineUseRtl = src.guidelineUseRtl;
            this.leftToLeft = src.leftToLeft;
            this.leftToRight = src.leftToRight;
            this.rightToLeft = src.rightToLeft;
            this.rightToRight = src.rightToRight;
            this.topToTop = src.topToTop;
            this.topToBottom = src.topToBottom;
            this.bottomToTop = src.bottomToTop;
            this.bottomToBottom = src.bottomToBottom;
            this.baselineToBaseline = src.baselineToBaseline;
            this.baselineToTop = src.baselineToTop;
            this.baselineToBottom = src.baselineToBottom;
            this.startToEnd = src.startToEnd;
            this.startToStart = src.startToStart;
            this.endToStart = src.endToStart;
            this.endToEnd = src.endToEnd;
            this.horizontalBias = src.horizontalBias;
            this.verticalBias = src.verticalBias;
            this.dimensionRatio = src.dimensionRatio;
            this.circleConstraint = src.circleConstraint;
            this.circleRadius = src.circleRadius;
            this.circleAngle = src.circleAngle;
            this.editorAbsoluteX = src.editorAbsoluteX;
            this.editorAbsoluteY = src.editorAbsoluteY;
            this.orientation = src.orientation;
            this.leftMargin = src.leftMargin;
            this.rightMargin = src.rightMargin;
            this.topMargin = src.topMargin;
            this.bottomMargin = src.bottomMargin;
            this.endMargin = src.endMargin;
            this.startMargin = src.startMargin;
            this.baselineMargin = src.baselineMargin;
            this.goneLeftMargin = src.goneLeftMargin;
            this.goneTopMargin = src.goneTopMargin;
            this.goneRightMargin = src.goneRightMargin;
            this.goneBottomMargin = src.goneBottomMargin;
            this.goneEndMargin = src.goneEndMargin;
            this.goneStartMargin = src.goneStartMargin;
            this.goneBaselineMargin = src.goneBaselineMargin;
            this.verticalWeight = src.verticalWeight;
            this.horizontalWeight = src.horizontalWeight;
            this.horizontalChainStyle = src.horizontalChainStyle;
            this.verticalChainStyle = src.verticalChainStyle;
            this.widthDefault = src.widthDefault;
            this.heightDefault = src.heightDefault;
            this.widthMax = src.widthMax;
            this.heightMax = src.heightMax;
            this.widthMin = src.widthMin;
            this.heightMin = src.heightMin;
            this.widthPercent = src.widthPercent;
            this.heightPercent = src.heightPercent;
            this.mBarrierDirection = src.mBarrierDirection;
            this.mBarrierMargin = src.mBarrierMargin;
            this.mHelperType = src.mHelperType;
            this.mConstraintTag = src.mConstraintTag;
            int[] iArr = src.mReferenceIds;
            if (iArr == null || src.mReferenceIdString != null) {
                this.mReferenceIds = null;
            } else {
                this.mReferenceIds = Arrays.copyOf(iArr, iArr.length);
            }
            this.mReferenceIdString = src.mReferenceIdString;
            this.constrainedWidth = src.constrainedWidth;
            this.constrainedHeight = src.constrainedHeight;
            this.mBarrierAllowsGoneWidgets = src.mBarrierAllowsGoneWidgets;
            this.mWrapBehavior = src.mWrapBehavior;
        }

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mapToConstant = sparseIntArray;
            sparseIntArray.append(C0657R.styleable.Layout_layout_constraintLeft_toLeftOf, 24);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintLeft_toRightOf, 25);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintRight_toLeftOf, 28);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintRight_toRightOf, 29);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintTop_toTopOf, 35);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintTop_toBottomOf, 34);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintBottom_toTopOf, 4);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintBottom_toBottomOf, 3);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintBaseline_toBaselineOf, 1);
            mapToConstant.append(C0657R.styleable.Layout_layout_editor_absoluteX, 6);
            mapToConstant.append(C0657R.styleable.Layout_layout_editor_absoluteY, 7);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintGuide_begin, 17);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintGuide_end, 18);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintGuide_percent, 19);
            mapToConstant.append(C0657R.styleable.Layout_guidelineUseRtl, 90);
            mapToConstant.append(C0657R.styleable.Layout_android_orientation, 26);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintStart_toEndOf, 31);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintStart_toStartOf, 32);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintEnd_toStartOf, 10);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintEnd_toEndOf, 9);
            mapToConstant.append(C0657R.styleable.Layout_layout_goneMarginLeft, 13);
            mapToConstant.append(C0657R.styleable.Layout_layout_goneMarginTop, 16);
            mapToConstant.append(C0657R.styleable.Layout_layout_goneMarginRight, 14);
            mapToConstant.append(C0657R.styleable.Layout_layout_goneMarginBottom, 11);
            mapToConstant.append(C0657R.styleable.Layout_layout_goneMarginStart, 15);
            mapToConstant.append(C0657R.styleable.Layout_layout_goneMarginEnd, 12);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintVertical_weight, 38);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintHorizontal_weight, 37);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintHorizontal_chainStyle, 39);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintVertical_chainStyle, 40);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintHorizontal_bias, 20);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintVertical_bias, 36);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintDimensionRatio, 5);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintLeft_creator, 91);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintTop_creator, 91);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintRight_creator, 91);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintBottom_creator, 91);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintBaseline_creator, 91);
            mapToConstant.append(C0657R.styleable.Layout_android_layout_marginLeft, 23);
            mapToConstant.append(C0657R.styleable.Layout_android_layout_marginRight, 27);
            mapToConstant.append(C0657R.styleable.Layout_android_layout_marginStart, 30);
            mapToConstant.append(C0657R.styleable.Layout_android_layout_marginEnd, 8);
            mapToConstant.append(C0657R.styleable.Layout_android_layout_marginTop, 33);
            mapToConstant.append(C0657R.styleable.Layout_android_layout_marginBottom, 2);
            mapToConstant.append(C0657R.styleable.Layout_android_layout_width, 22);
            mapToConstant.append(C0657R.styleable.Layout_android_layout_height, 21);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintWidth, 41);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintHeight, 42);
            mapToConstant.append(C0657R.styleable.Layout_layout_constrainedWidth, 41);
            mapToConstant.append(C0657R.styleable.Layout_layout_constrainedHeight, 42);
            mapToConstant.append(C0657R.styleable.Layout_layout_wrapBehaviorInParent, 76);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintCircle, 61);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintCircleRadius, 62);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintCircleAngle, 63);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintWidth_percent, 69);
            mapToConstant.append(C0657R.styleable.Layout_layout_constraintHeight_percent, 70);
            mapToConstant.append(C0657R.styleable.Layout_chainUseRtl, 71);
            mapToConstant.append(C0657R.styleable.Layout_barrierDirection, 72);
            mapToConstant.append(C0657R.styleable.Layout_barrierMargin, 73);
            mapToConstant.append(C0657R.styleable.Layout_constraint_referenced_ids, 74);
            mapToConstant.append(C0657R.styleable.Layout_barrierAllowsGoneWidgets, 75);
        }

        /* access modifiers changed from: package-private */
        public void fillFromAttributeList(Context context, AttributeSet attrs) {
            TypedArray a = context.obtainStyledAttributes(attrs, C0657R.styleable.Layout);
            this.mApply = true;
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                switch (mapToConstant.get(attr)) {
                    case 1:
                        this.baselineToBaseline = ConstraintSet.lookupID(a, attr, this.baselineToBaseline);
                        break;
                    case 2:
                        this.bottomMargin = a.getDimensionPixelSize(attr, this.bottomMargin);
                        break;
                    case 3:
                        this.bottomToBottom = ConstraintSet.lookupID(a, attr, this.bottomToBottom);
                        break;
                    case 4:
                        this.bottomToTop = ConstraintSet.lookupID(a, attr, this.bottomToTop);
                        break;
                    case 5:
                        this.dimensionRatio = a.getString(attr);
                        break;
                    case 6:
                        this.editorAbsoluteX = a.getDimensionPixelOffset(attr, this.editorAbsoluteX);
                        break;
                    case 7:
                        this.editorAbsoluteY = a.getDimensionPixelOffset(attr, this.editorAbsoluteY);
                        break;
                    case 8:
                        this.endMargin = a.getDimensionPixelSize(attr, this.endMargin);
                        break;
                    case 9:
                        this.endToEnd = ConstraintSet.lookupID(a, attr, this.endToEnd);
                        break;
                    case 10:
                        this.endToStart = ConstraintSet.lookupID(a, attr, this.endToStart);
                        break;
                    case 11:
                        this.goneBottomMargin = a.getDimensionPixelSize(attr, this.goneBottomMargin);
                        break;
                    case 12:
                        this.goneEndMargin = a.getDimensionPixelSize(attr, this.goneEndMargin);
                        break;
                    case 13:
                        this.goneLeftMargin = a.getDimensionPixelSize(attr, this.goneLeftMargin);
                        break;
                    case 14:
                        this.goneRightMargin = a.getDimensionPixelSize(attr, this.goneRightMargin);
                        break;
                    case 15:
                        this.goneStartMargin = a.getDimensionPixelSize(attr, this.goneStartMargin);
                        break;
                    case 16:
                        this.goneTopMargin = a.getDimensionPixelSize(attr, this.goneTopMargin);
                        break;
                    case 17:
                        this.guideBegin = a.getDimensionPixelOffset(attr, this.guideBegin);
                        break;
                    case 18:
                        this.guideEnd = a.getDimensionPixelOffset(attr, this.guideEnd);
                        break;
                    case 19:
                        this.guidePercent = a.getFloat(attr, this.guidePercent);
                        break;
                    case 20:
                        this.horizontalBias = a.getFloat(attr, this.horizontalBias);
                        break;
                    case 21:
                        this.mHeight = a.getLayoutDimension(attr, this.mHeight);
                        break;
                    case 22:
                        this.mWidth = a.getLayoutDimension(attr, this.mWidth);
                        break;
                    case 23:
                        this.leftMargin = a.getDimensionPixelSize(attr, this.leftMargin);
                        break;
                    case 24:
                        this.leftToLeft = ConstraintSet.lookupID(a, attr, this.leftToLeft);
                        break;
                    case 25:
                        this.leftToRight = ConstraintSet.lookupID(a, attr, this.leftToRight);
                        break;
                    case 26:
                        this.orientation = a.getInt(attr, this.orientation);
                        break;
                    case 27:
                        this.rightMargin = a.getDimensionPixelSize(attr, this.rightMargin);
                        break;
                    case 28:
                        this.rightToLeft = ConstraintSet.lookupID(a, attr, this.rightToLeft);
                        break;
                    case 29:
                        this.rightToRight = ConstraintSet.lookupID(a, attr, this.rightToRight);
                        break;
                    case 30:
                        this.startMargin = a.getDimensionPixelSize(attr, this.startMargin);
                        break;
                    case 31:
                        this.startToEnd = ConstraintSet.lookupID(a, attr, this.startToEnd);
                        break;
                    case 32:
                        this.startToStart = ConstraintSet.lookupID(a, attr, this.startToStart);
                        break;
                    case 33:
                        this.topMargin = a.getDimensionPixelSize(attr, this.topMargin);
                        break;
                    case 34:
                        this.topToBottom = ConstraintSet.lookupID(a, attr, this.topToBottom);
                        break;
                    case 35:
                        this.topToTop = ConstraintSet.lookupID(a, attr, this.topToTop);
                        break;
                    case 36:
                        this.verticalBias = a.getFloat(attr, this.verticalBias);
                        break;
                    case 37:
                        this.horizontalWeight = a.getFloat(attr, this.horizontalWeight);
                        break;
                    case 38:
                        this.verticalWeight = a.getFloat(attr, this.verticalWeight);
                        break;
                    case 39:
                        this.horizontalChainStyle = a.getInt(attr, this.horizontalChainStyle);
                        break;
                    case 40:
                        this.verticalChainStyle = a.getInt(attr, this.verticalChainStyle);
                        break;
                    case 41:
                        ConstraintSet.parseDimensionConstraints(this, a, attr, 0);
                        break;
                    case 42:
                        ConstraintSet.parseDimensionConstraints(this, a, attr, 1);
                        break;
                    case 61:
                        this.circleConstraint = ConstraintSet.lookupID(a, attr, this.circleConstraint);
                        break;
                    case 62:
                        this.circleRadius = a.getDimensionPixelSize(attr, this.circleRadius);
                        break;
                    case 63:
                        this.circleAngle = a.getFloat(attr, this.circleAngle);
                        break;
                    case 69:
                        this.widthPercent = a.getFloat(attr, 1.0f);
                        break;
                    case 70:
                        this.heightPercent = a.getFloat(attr, 1.0f);
                        break;
                    case 71:
                        Log.e(ConstraintSet.TAG, "CURRENTLY UNSUPPORTED");
                        break;
                    case 72:
                        this.mBarrierDirection = a.getInt(attr, this.mBarrierDirection);
                        break;
                    case 73:
                        this.mBarrierMargin = a.getDimensionPixelSize(attr, this.mBarrierMargin);
                        break;
                    case 74:
                        this.mReferenceIdString = a.getString(attr);
                        break;
                    case 75:
                        this.mBarrierAllowsGoneWidgets = a.getBoolean(attr, this.mBarrierAllowsGoneWidgets);
                        break;
                    case 76:
                        this.mWrapBehavior = a.getInt(attr, this.mWrapBehavior);
                        break;
                    case 77:
                        this.baselineToTop = ConstraintSet.lookupID(a, attr, this.baselineToTop);
                        break;
                    case 78:
                        this.baselineToBottom = ConstraintSet.lookupID(a, attr, this.baselineToBottom);
                        break;
                    case 79:
                        this.goneBaselineMargin = a.getDimensionPixelSize(attr, this.goneBaselineMargin);
                        break;
                    case 80:
                        this.baselineMargin = a.getDimensionPixelSize(attr, this.baselineMargin);
                        break;
                    case 81:
                        this.widthDefault = a.getInt(attr, this.widthDefault);
                        break;
                    case 82:
                        this.heightDefault = a.getInt(attr, this.heightDefault);
                        break;
                    case 83:
                        this.heightMax = a.getDimensionPixelSize(attr, this.heightMax);
                        break;
                    case 84:
                        this.widthMax = a.getDimensionPixelSize(attr, this.widthMax);
                        break;
                    case 85:
                        this.heightMin = a.getDimensionPixelSize(attr, this.heightMin);
                        break;
                    case 86:
                        this.widthMin = a.getDimensionPixelSize(attr, this.widthMin);
                        break;
                    case 87:
                        this.constrainedWidth = a.getBoolean(attr, this.constrainedWidth);
                        break;
                    case 88:
                        this.constrainedHeight = a.getBoolean(attr, this.constrainedHeight);
                        break;
                    case 89:
                        this.mConstraintTag = a.getString(attr);
                        break;
                    case 90:
                        this.guidelineUseRtl = a.getBoolean(attr, this.guidelineUseRtl);
                        break;
                    case 91:
                        Log.w(ConstraintSet.TAG, "unused attribute 0x" + Integer.toHexString(attr) + "   " + mapToConstant.get(attr));
                        break;
                    default:
                        Log.w(ConstraintSet.TAG, "Unknown attribute 0x" + Integer.toHexString(attr) + "   " + mapToConstant.get(attr));
                        break;
                }
            }
            a.recycle();
        }

        public void dump(MotionScene scene, StringBuilder stringBuilder) {
            Field[] fields = getClass().getDeclaredFields();
            stringBuilder.append("\n");
            for (Field field : fields) {
                String name = field.getName();
                if (!Modifier.isStatic(field.getModifiers())) {
                    try {
                        Object value = field.get(this);
                        Class<?> type = field.getType();
                        if (type == Integer.TYPE) {
                            Integer iValue = (Integer) value;
                            if (iValue.intValue() != -1) {
                                String stringId = scene.lookUpConstraintName(iValue.intValue());
                                stringBuilder.append("    ");
                                stringBuilder.append(name);
                                stringBuilder.append(" = \"");
                                stringBuilder.append(stringId == null ? iValue : stringId);
                                stringBuilder.append("\"\n");
                            }
                        } else if (type == Float.TYPE) {
                            Float fValue = (Float) value;
                            if (fValue.floatValue() != -1.0f) {
                                stringBuilder.append("    ");
                                stringBuilder.append(name);
                                stringBuilder.append(" = \"");
                                stringBuilder.append(fValue);
                                stringBuilder.append("\"\n");
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static class Transform {
        private static final int ELEVATION = 11;
        private static final int ROTATION = 1;
        private static final int ROTATION_X = 2;
        private static final int ROTATION_Y = 3;
        private static final int SCALE_X = 4;
        private static final int SCALE_Y = 5;
        private static final int TRANSFORM_PIVOT_TARGET = 12;
        private static final int TRANSFORM_PIVOT_X = 6;
        private static final int TRANSFORM_PIVOT_Y = 7;
        private static final int TRANSLATION_X = 8;
        private static final int TRANSLATION_Y = 9;
        private static final int TRANSLATION_Z = 10;
        private static SparseIntArray mapToConstant;
        public boolean applyElevation = false;
        public float elevation = 0.0f;
        public boolean mApply = false;
        public float rotation = 0.0f;
        public float rotationX = 0.0f;
        public float rotationY = 0.0f;
        public float scaleX = 1.0f;
        public float scaleY = 1.0f;
        public int transformPivotTarget = -1;
        public float transformPivotX = Float.NaN;
        public float transformPivotY = Float.NaN;
        public float translationX = 0.0f;
        public float translationY = 0.0f;
        public float translationZ = 0.0f;

        public void copyFrom(Transform src) {
            this.mApply = src.mApply;
            this.rotation = src.rotation;
            this.rotationX = src.rotationX;
            this.rotationY = src.rotationY;
            this.scaleX = src.scaleX;
            this.scaleY = src.scaleY;
            this.transformPivotX = src.transformPivotX;
            this.transformPivotY = src.transformPivotY;
            this.transformPivotTarget = src.transformPivotTarget;
            this.translationX = src.translationX;
            this.translationY = src.translationY;
            this.translationZ = src.translationZ;
            this.applyElevation = src.applyElevation;
            this.elevation = src.elevation;
        }

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mapToConstant = sparseIntArray;
            sparseIntArray.append(C0657R.styleable.Transform_android_rotation, 1);
            mapToConstant.append(C0657R.styleable.Transform_android_rotationX, 2);
            mapToConstant.append(C0657R.styleable.Transform_android_rotationY, 3);
            mapToConstant.append(C0657R.styleable.Transform_android_scaleX, 4);
            mapToConstant.append(C0657R.styleable.Transform_android_scaleY, 5);
            mapToConstant.append(C0657R.styleable.Transform_android_transformPivotX, 6);
            mapToConstant.append(C0657R.styleable.Transform_android_transformPivotY, 7);
            mapToConstant.append(C0657R.styleable.Transform_android_translationX, 8);
            mapToConstant.append(C0657R.styleable.Transform_android_translationY, 9);
            mapToConstant.append(C0657R.styleable.Transform_android_translationZ, 10);
            mapToConstant.append(C0657R.styleable.Transform_android_elevation, 11);
            mapToConstant.append(C0657R.styleable.Transform_transformPivotTarget, 12);
        }

        /* access modifiers changed from: package-private */
        public void fillFromAttributeList(Context context, AttributeSet attrs) {
            TypedArray a = context.obtainStyledAttributes(attrs, C0657R.styleable.Transform);
            this.mApply = true;
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                switch (mapToConstant.get(attr)) {
                    case 1:
                        this.rotation = a.getFloat(attr, this.rotation);
                        break;
                    case 2:
                        this.rotationX = a.getFloat(attr, this.rotationX);
                        break;
                    case 3:
                        this.rotationY = a.getFloat(attr, this.rotationY);
                        break;
                    case 4:
                        this.scaleX = a.getFloat(attr, this.scaleX);
                        break;
                    case 5:
                        this.scaleY = a.getFloat(attr, this.scaleY);
                        break;
                    case 6:
                        this.transformPivotX = a.getDimension(attr, this.transformPivotX);
                        break;
                    case 7:
                        this.transformPivotY = a.getDimension(attr, this.transformPivotY);
                        break;
                    case 8:
                        this.translationX = a.getDimension(attr, this.translationX);
                        break;
                    case 9:
                        this.translationY = a.getDimension(attr, this.translationY);
                        break;
                    case 10:
                        if (Build.VERSION.SDK_INT < 21) {
                            break;
                        } else {
                            this.translationZ = a.getDimension(attr, this.translationZ);
                            break;
                        }
                    case 11:
                        if (Build.VERSION.SDK_INT < 21) {
                            break;
                        } else {
                            this.applyElevation = true;
                            this.elevation = a.getDimension(attr, this.elevation);
                            break;
                        }
                    case 12:
                        this.transformPivotTarget = ConstraintSet.lookupID(a, attr, this.transformPivotTarget);
                        break;
                }
            }
            a.recycle();
        }
    }

    public static class PropertySet {
        public float alpha = 1.0f;
        public boolean mApply = false;
        public float mProgress = Float.NaN;
        public int mVisibilityMode = 0;
        public int visibility = 0;

        public void copyFrom(PropertySet src) {
            this.mApply = src.mApply;
            this.visibility = src.visibility;
            this.alpha = src.alpha;
            this.mProgress = src.mProgress;
            this.mVisibilityMode = src.mVisibilityMode;
        }

        /* access modifiers changed from: package-private */
        public void fillFromAttributeList(Context context, AttributeSet attrs) {
            TypedArray a = context.obtainStyledAttributes(attrs, C0657R.styleable.PropertySet);
            this.mApply = true;
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == C0657R.styleable.PropertySet_android_alpha) {
                    this.alpha = a.getFloat(attr, this.alpha);
                } else if (attr == C0657R.styleable.PropertySet_android_visibility) {
                    this.visibility = a.getInt(attr, this.visibility);
                    this.visibility = ConstraintSet.VISIBILITY_FLAGS[this.visibility];
                } else if (attr == C0657R.styleable.PropertySet_visibilityMode) {
                    this.mVisibilityMode = a.getInt(attr, this.mVisibilityMode);
                } else if (attr == C0657R.styleable.PropertySet_motionProgress) {
                    this.mProgress = a.getFloat(attr, this.mProgress);
                }
            }
            a.recycle();
        }
    }

    public static class Motion {
        private static final int ANIMATE_CIRCLE_ANGLE_TO = 6;
        private static final int ANIMATE_RELATIVE_TO = 5;
        private static final int INTERPOLATOR_REFERENCE_ID = -2;
        private static final int INTERPOLATOR_UNDEFINED = -3;
        private static final int MOTION_DRAW_PATH = 4;
        private static final int MOTION_STAGGER = 7;
        private static final int PATH_MOTION_ARC = 2;
        private static final int QUANTIZE_MOTION_INTERPOLATOR = 10;
        private static final int QUANTIZE_MOTION_PHASE = 9;
        private static final int QUANTIZE_MOTION_STEPS = 8;
        private static final int SPLINE_STRING = -1;
        private static final int TRANSITION_EASING = 3;
        private static final int TRANSITION_PATH_ROTATE = 1;
        private static SparseIntArray mapToConstant;
        public int mAnimateCircleAngleTo = 0;
        public int mAnimateRelativeTo = -1;
        public boolean mApply = false;
        public int mDrawPath = 0;
        public float mMotionStagger = Float.NaN;
        public int mPathMotionArc = -1;
        public float mPathRotate = Float.NaN;
        public int mPolarRelativeTo = -1;
        public int mQuantizeInterpolatorID = -1;
        public String mQuantizeInterpolatorString = null;
        public int mQuantizeInterpolatorType = -3;
        public float mQuantizeMotionPhase = Float.NaN;
        public int mQuantizeMotionSteps = -1;
        public String mTransitionEasing = null;

        public void copyFrom(Motion src) {
            this.mApply = src.mApply;
            this.mAnimateRelativeTo = src.mAnimateRelativeTo;
            this.mTransitionEasing = src.mTransitionEasing;
            this.mPathMotionArc = src.mPathMotionArc;
            this.mDrawPath = src.mDrawPath;
            this.mPathRotate = src.mPathRotate;
            this.mMotionStagger = src.mMotionStagger;
            this.mPolarRelativeTo = src.mPolarRelativeTo;
        }

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mapToConstant = sparseIntArray;
            sparseIntArray.append(C0657R.styleable.Motion_motionPathRotate, 1);
            mapToConstant.append(C0657R.styleable.Motion_pathMotionArc, 2);
            mapToConstant.append(C0657R.styleable.Motion_transitionEasing, 3);
            mapToConstant.append(C0657R.styleable.Motion_drawPath, 4);
            mapToConstant.append(C0657R.styleable.Motion_animateRelativeTo, 5);
            mapToConstant.append(C0657R.styleable.Motion_animateCircleAngleTo, 6);
            mapToConstant.append(C0657R.styleable.Motion_motionStagger, 7);
            mapToConstant.append(C0657R.styleable.Motion_quantizeMotionSteps, 8);
            mapToConstant.append(C0657R.styleable.Motion_quantizeMotionPhase, 9);
            mapToConstant.append(C0657R.styleable.Motion_quantizeMotionInterpolator, 10);
        }

        /* access modifiers changed from: package-private */
        public void fillFromAttributeList(Context context, AttributeSet attrs) {
            TypedArray a = context.obtainStyledAttributes(attrs, C0657R.styleable.Motion);
            this.mApply = true;
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                switch (mapToConstant.get(attr)) {
                    case 1:
                        this.mPathRotate = a.getFloat(attr, this.mPathRotate);
                        break;
                    case 2:
                        this.mPathMotionArc = a.getInt(attr, this.mPathMotionArc);
                        break;
                    case 3:
                        if (a.peekValue(attr).type != 3) {
                            this.mTransitionEasing = Easing.NAMED_EASING[a.getInteger(attr, 0)];
                            break;
                        } else {
                            this.mTransitionEasing = a.getString(attr);
                            break;
                        }
                    case 4:
                        this.mDrawPath = a.getInt(attr, 0);
                        break;
                    case 5:
                        this.mAnimateRelativeTo = ConstraintSet.lookupID(a, attr, this.mAnimateRelativeTo);
                        break;
                    case 6:
                        this.mAnimateCircleAngleTo = a.getInteger(attr, this.mAnimateCircleAngleTo);
                        break;
                    case 7:
                        this.mMotionStagger = a.getFloat(attr, this.mMotionStagger);
                        break;
                    case 8:
                        this.mQuantizeMotionSteps = a.getInteger(attr, this.mQuantizeMotionSteps);
                        break;
                    case 9:
                        this.mQuantizeMotionPhase = a.getFloat(attr, this.mQuantizeMotionPhase);
                        break;
                    case 10:
                        TypedValue type = a.peekValue(attr);
                        if (type.type != 1) {
                            if (type.type != 3) {
                                this.mQuantizeInterpolatorType = a.getInteger(attr, this.mQuantizeInterpolatorID);
                                break;
                            } else {
                                String string = a.getString(attr);
                                this.mQuantizeInterpolatorString = string;
                                if (string.indexOf("/") <= 0) {
                                    this.mQuantizeInterpolatorType = -1;
                                    break;
                                } else {
                                    this.mQuantizeInterpolatorID = a.getResourceId(attr, -1);
                                    this.mQuantizeInterpolatorType = -2;
                                    break;
                                }
                            }
                        } else {
                            int resourceId = a.getResourceId(attr, -1);
                            this.mQuantizeInterpolatorID = resourceId;
                            if (resourceId == -1) {
                                break;
                            } else {
                                this.mQuantizeInterpolatorType = -2;
                                break;
                            }
                        }
                }
            }
            a.recycle();
        }
    }

    public static class Constraint {
        public final Layout layout = new Layout();
        public HashMap<String, ConstraintAttribute> mCustomConstraints = new HashMap<>();
        Delta mDelta;
        String mTargetString;
        int mViewId;
        public final Motion motion = new Motion();
        public final PropertySet propertySet = new PropertySet();
        public final Transform transform = new Transform();

        static class Delta {
            private static final int INITIAL_BOOLEAN = 4;
            private static final int INITIAL_FLOAT = 10;
            private static final int INITIAL_INT = 10;
            private static final int INITIAL_STRING = 5;
            int mCountBoolean = 0;
            int mCountFloat = 0;
            int mCountInt = 0;
            int mCountString = 0;
            int[] mTypeBoolean = new int[4];
            int[] mTypeFloat = new int[10];
            int[] mTypeInt = new int[10];
            int[] mTypeString = new int[5];
            boolean[] mValueBoolean = new boolean[4];
            float[] mValueFloat = new float[10];
            int[] mValueInt = new int[10];
            String[] mValueString = new String[5];

            Delta() {
            }

            /* access modifiers changed from: package-private */
            public void add(int type, int value) {
                int i = this.mCountInt;
                int[] iArr = this.mTypeInt;
                if (i >= iArr.length) {
                    this.mTypeInt = Arrays.copyOf(iArr, iArr.length * 2);
                    int[] iArr2 = this.mValueInt;
                    this.mValueInt = Arrays.copyOf(iArr2, iArr2.length * 2);
                }
                int[] iArr3 = this.mTypeInt;
                int i2 = this.mCountInt;
                iArr3[i2] = type;
                int[] iArr4 = this.mValueInt;
                this.mCountInt = i2 + 1;
                iArr4[i2] = value;
            }

            /* access modifiers changed from: package-private */
            public void add(int type, float value) {
                int i = this.mCountFloat;
                int[] iArr = this.mTypeFloat;
                if (i >= iArr.length) {
                    this.mTypeFloat = Arrays.copyOf(iArr, iArr.length * 2);
                    float[] fArr = this.mValueFloat;
                    this.mValueFloat = Arrays.copyOf(fArr, fArr.length * 2);
                }
                int[] iArr2 = this.mTypeFloat;
                int i2 = this.mCountFloat;
                iArr2[i2] = type;
                float[] fArr2 = this.mValueFloat;
                this.mCountFloat = i2 + 1;
                fArr2[i2] = value;
            }

            /* access modifiers changed from: package-private */
            public void add(int type, String value) {
                int i = this.mCountString;
                int[] iArr = this.mTypeString;
                if (i >= iArr.length) {
                    this.mTypeString = Arrays.copyOf(iArr, iArr.length * 2);
                    String[] strArr = this.mValueString;
                    this.mValueString = (String[]) Arrays.copyOf(strArr, strArr.length * 2);
                }
                int[] iArr2 = this.mTypeString;
                int i2 = this.mCountString;
                iArr2[i2] = type;
                String[] strArr2 = this.mValueString;
                this.mCountString = i2 + 1;
                strArr2[i2] = value;
            }

            /* access modifiers changed from: package-private */
            public void add(int type, boolean value) {
                int i = this.mCountBoolean;
                int[] iArr = this.mTypeBoolean;
                if (i >= iArr.length) {
                    this.mTypeBoolean = Arrays.copyOf(iArr, iArr.length * 2);
                    boolean[] zArr = this.mValueBoolean;
                    this.mValueBoolean = Arrays.copyOf(zArr, zArr.length * 2);
                }
                int[] iArr2 = this.mTypeBoolean;
                int i2 = this.mCountBoolean;
                iArr2[i2] = type;
                boolean[] zArr2 = this.mValueBoolean;
                this.mCountBoolean = i2 + 1;
                zArr2[i2] = value;
            }

            /* access modifiers changed from: package-private */
            public void applyDelta(Constraint c) {
                for (int i = 0; i < this.mCountInt; i++) {
                    ConstraintSet.setDeltaValue(c, this.mTypeInt[i], this.mValueInt[i]);
                }
                for (int i2 = 0; i2 < this.mCountFloat; i2++) {
                    ConstraintSet.setDeltaValue(c, this.mTypeFloat[i2], this.mValueFloat[i2]);
                }
                for (int i3 = 0; i3 < this.mCountString; i3++) {
                    ConstraintSet.setDeltaValue(c, this.mTypeString[i3], this.mValueString[i3]);
                }
                for (int i4 = 0; i4 < this.mCountBoolean; i4++) {
                    ConstraintSet.setDeltaValue(c, this.mTypeBoolean[i4], this.mValueBoolean[i4]);
                }
            }

            /* access modifiers changed from: package-private */
            public void printDelta(String tag) {
                Log.v(tag, "int");
                for (int i = 0; i < this.mCountInt; i++) {
                    Log.v(tag, this.mTypeInt[i] + " = " + this.mValueInt[i]);
                }
                Log.v(tag, "float");
                for (int i2 = 0; i2 < this.mCountFloat; i2++) {
                    Log.v(tag, this.mTypeFloat[i2] + " = " + this.mValueFloat[i2]);
                }
                Log.v(tag, "strings");
                for (int i3 = 0; i3 < this.mCountString; i3++) {
                    Log.v(tag, this.mTypeString[i3] + " = " + this.mValueString[i3]);
                }
                Log.v(tag, TypedValues.Custom.S_BOOLEAN);
                for (int i4 = 0; i4 < this.mCountBoolean; i4++) {
                    Log.v(tag, this.mTypeBoolean[i4] + " = " + this.mValueBoolean[i4]);
                }
            }
        }

        public void applyDelta(Constraint c) {
            Delta delta = this.mDelta;
            if (delta != null) {
                delta.applyDelta(c);
            }
        }

        public void printDelta(String tag) {
            Delta delta = this.mDelta;
            if (delta != null) {
                delta.printDelta(tag);
            } else {
                Log.v(tag, "DELTA IS NULL");
            }
        }

        private ConstraintAttribute get(String attributeName, ConstraintAttribute.AttributeType attributeType) {
            if (this.mCustomConstraints.containsKey(attributeName)) {
                ConstraintAttribute ret = this.mCustomConstraints.get(attributeName);
                if (ret.getType() == attributeType) {
                    return ret;
                }
                throw new IllegalArgumentException("ConstraintAttribute is already a " + ret.getType().name());
            }
            ConstraintAttribute ret2 = new ConstraintAttribute(attributeName, attributeType);
            this.mCustomConstraints.put(attributeName, ret2);
            return ret2;
        }

        /* access modifiers changed from: private */
        public void setStringValue(String attributeName, String value) {
            get(attributeName, ConstraintAttribute.AttributeType.STRING_TYPE).setStringValue(value);
        }

        /* access modifiers changed from: private */
        public void setFloatValue(String attributeName, float value) {
            get(attributeName, ConstraintAttribute.AttributeType.FLOAT_TYPE).setFloatValue(value);
        }

        /* access modifiers changed from: private */
        public void setIntValue(String attributeName, int value) {
            get(attributeName, ConstraintAttribute.AttributeType.INT_TYPE).setIntValue(value);
        }

        /* access modifiers changed from: private */
        public void setColorValue(String attributeName, int value) {
            get(attributeName, ConstraintAttribute.AttributeType.COLOR_TYPE).setColorValue(value);
        }

        public Constraint clone() {
            Constraint clone = new Constraint();
            clone.layout.copyFrom(this.layout);
            clone.motion.copyFrom(this.motion);
            clone.propertySet.copyFrom(this.propertySet);
            clone.transform.copyFrom(this.transform);
            clone.mViewId = this.mViewId;
            clone.mDelta = this.mDelta;
            return clone;
        }

        /* access modifiers changed from: private */
        public void fillFromConstraints(ConstraintHelper helper, int viewId, Constraints.LayoutParams param) {
            fillFromConstraints(viewId, param);
            if (helper instanceof Barrier) {
                this.layout.mHelperType = 1;
                Barrier barrier = (Barrier) helper;
                this.layout.mBarrierDirection = barrier.getType();
                this.layout.mReferenceIds = barrier.getReferencedIds();
                this.layout.mBarrierMargin = barrier.getMargin();
            }
        }

        /* access modifiers changed from: private */
        public void fillFromConstraints(int viewId, Constraints.LayoutParams param) {
            fillFrom(viewId, param);
            this.propertySet.alpha = param.alpha;
            this.transform.rotation = param.rotation;
            this.transform.rotationX = param.rotationX;
            this.transform.rotationY = param.rotationY;
            this.transform.scaleX = param.scaleX;
            this.transform.scaleY = param.scaleY;
            this.transform.transformPivotX = param.transformPivotX;
            this.transform.transformPivotY = param.transformPivotY;
            this.transform.translationX = param.translationX;
            this.transform.translationY = param.translationY;
            this.transform.translationZ = param.translationZ;
            this.transform.elevation = param.elevation;
            this.transform.applyElevation = param.applyElevation;
        }

        /* access modifiers changed from: private */
        public void fillFrom(int viewId, ConstraintLayout.LayoutParams param) {
            this.mViewId = viewId;
            this.layout.leftToLeft = param.leftToLeft;
            this.layout.leftToRight = param.leftToRight;
            this.layout.rightToLeft = param.rightToLeft;
            this.layout.rightToRight = param.rightToRight;
            this.layout.topToTop = param.topToTop;
            this.layout.topToBottom = param.topToBottom;
            this.layout.bottomToTop = param.bottomToTop;
            this.layout.bottomToBottom = param.bottomToBottom;
            this.layout.baselineToBaseline = param.baselineToBaseline;
            this.layout.baselineToTop = param.baselineToTop;
            this.layout.baselineToBottom = param.baselineToBottom;
            this.layout.startToEnd = param.startToEnd;
            this.layout.startToStart = param.startToStart;
            this.layout.endToStart = param.endToStart;
            this.layout.endToEnd = param.endToEnd;
            this.layout.horizontalBias = param.horizontalBias;
            this.layout.verticalBias = param.verticalBias;
            this.layout.dimensionRatio = param.dimensionRatio;
            this.layout.circleConstraint = param.circleConstraint;
            this.layout.circleRadius = param.circleRadius;
            this.layout.circleAngle = param.circleAngle;
            this.layout.editorAbsoluteX = param.editorAbsoluteX;
            this.layout.editorAbsoluteY = param.editorAbsoluteY;
            this.layout.orientation = param.orientation;
            this.layout.guidePercent = param.guidePercent;
            this.layout.guideBegin = param.guideBegin;
            this.layout.guideEnd = param.guideEnd;
            this.layout.mWidth = param.width;
            this.layout.mHeight = param.height;
            this.layout.leftMargin = param.leftMargin;
            this.layout.rightMargin = param.rightMargin;
            this.layout.topMargin = param.topMargin;
            this.layout.bottomMargin = param.bottomMargin;
            this.layout.baselineMargin = param.baselineMargin;
            this.layout.verticalWeight = param.verticalWeight;
            this.layout.horizontalWeight = param.horizontalWeight;
            this.layout.verticalChainStyle = param.verticalChainStyle;
            this.layout.horizontalChainStyle = param.horizontalChainStyle;
            this.layout.constrainedWidth = param.constrainedWidth;
            this.layout.constrainedHeight = param.constrainedHeight;
            this.layout.widthDefault = param.matchConstraintDefaultWidth;
            this.layout.heightDefault = param.matchConstraintDefaultHeight;
            this.layout.widthMax = param.matchConstraintMaxWidth;
            this.layout.heightMax = param.matchConstraintMaxHeight;
            this.layout.widthMin = param.matchConstraintMinWidth;
            this.layout.heightMin = param.matchConstraintMinHeight;
            this.layout.widthPercent = param.matchConstraintPercentWidth;
            this.layout.heightPercent = param.matchConstraintPercentHeight;
            this.layout.mConstraintTag = param.constraintTag;
            this.layout.goneTopMargin = param.goneTopMargin;
            this.layout.goneBottomMargin = param.goneBottomMargin;
            this.layout.goneLeftMargin = param.goneLeftMargin;
            this.layout.goneRightMargin = param.goneRightMargin;
            this.layout.goneStartMargin = param.goneStartMargin;
            this.layout.goneEndMargin = param.goneEndMargin;
            this.layout.goneBaselineMargin = param.goneBaselineMargin;
            this.layout.mWrapBehavior = param.wrapBehaviorInParent;
            int i = Build.VERSION.SDK_INT;
            this.layout.endMargin = param.getMarginEnd();
            this.layout.startMargin = param.getMarginStart();
        }

        public void applyTo(ConstraintLayout.LayoutParams param) {
            param.leftToLeft = this.layout.leftToLeft;
            param.leftToRight = this.layout.leftToRight;
            param.rightToLeft = this.layout.rightToLeft;
            param.rightToRight = this.layout.rightToRight;
            param.topToTop = this.layout.topToTop;
            param.topToBottom = this.layout.topToBottom;
            param.bottomToTop = this.layout.bottomToTop;
            param.bottomToBottom = this.layout.bottomToBottom;
            param.baselineToBaseline = this.layout.baselineToBaseline;
            param.baselineToTop = this.layout.baselineToTop;
            param.baselineToBottom = this.layout.baselineToBottom;
            param.startToEnd = this.layout.startToEnd;
            param.startToStart = this.layout.startToStart;
            param.endToStart = this.layout.endToStart;
            param.endToEnd = this.layout.endToEnd;
            param.leftMargin = this.layout.leftMargin;
            param.rightMargin = this.layout.rightMargin;
            param.topMargin = this.layout.topMargin;
            param.bottomMargin = this.layout.bottomMargin;
            param.goneStartMargin = this.layout.goneStartMargin;
            param.goneEndMargin = this.layout.goneEndMargin;
            param.goneTopMargin = this.layout.goneTopMargin;
            param.goneBottomMargin = this.layout.goneBottomMargin;
            param.horizontalBias = this.layout.horizontalBias;
            param.verticalBias = this.layout.verticalBias;
            param.circleConstraint = this.layout.circleConstraint;
            param.circleRadius = this.layout.circleRadius;
            param.circleAngle = this.layout.circleAngle;
            param.dimensionRatio = this.layout.dimensionRatio;
            param.editorAbsoluteX = this.layout.editorAbsoluteX;
            param.editorAbsoluteY = this.layout.editorAbsoluteY;
            param.verticalWeight = this.layout.verticalWeight;
            param.horizontalWeight = this.layout.horizontalWeight;
            param.verticalChainStyle = this.layout.verticalChainStyle;
            param.horizontalChainStyle = this.layout.horizontalChainStyle;
            param.constrainedWidth = this.layout.constrainedWidth;
            param.constrainedHeight = this.layout.constrainedHeight;
            param.matchConstraintDefaultWidth = this.layout.widthDefault;
            param.matchConstraintDefaultHeight = this.layout.heightDefault;
            param.matchConstraintMaxWidth = this.layout.widthMax;
            param.matchConstraintMaxHeight = this.layout.heightMax;
            param.matchConstraintMinWidth = this.layout.widthMin;
            param.matchConstraintMinHeight = this.layout.heightMin;
            param.matchConstraintPercentWidth = this.layout.widthPercent;
            param.matchConstraintPercentHeight = this.layout.heightPercent;
            param.orientation = this.layout.orientation;
            param.guidePercent = this.layout.guidePercent;
            param.guideBegin = this.layout.guideBegin;
            param.guideEnd = this.layout.guideEnd;
            param.width = this.layout.mWidth;
            param.height = this.layout.mHeight;
            if (this.layout.mConstraintTag != null) {
                param.constraintTag = this.layout.mConstraintTag;
            }
            param.wrapBehaviorInParent = this.layout.mWrapBehavior;
            param.setMarginStart(this.layout.startMargin);
            param.setMarginEnd(this.layout.endMargin);
            param.validate();
        }
    }

    public void clone(Context context, int constraintLayoutId) {
        clone((ConstraintLayout) LayoutInflater.from(context).inflate(constraintLayoutId, (ViewGroup) null));
    }

    public void clone(ConstraintSet set) {
        this.mConstraints.clear();
        for (Integer key : set.mConstraints.keySet()) {
            Constraint constraint = set.mConstraints.get(key);
            if (constraint != null) {
                this.mConstraints.put(key, constraint.clone());
            }
        }
    }

    public void clone(ConstraintLayout constraintLayout) {
        int count = constraintLayout.getChildCount();
        this.mConstraints.clear();
        int i = 0;
        while (i < count) {
            View view = constraintLayout.getChildAt(i);
            ConstraintLayout.LayoutParams param = (ConstraintLayout.LayoutParams) view.getLayoutParams();
            int id = view.getId();
            if (!this.mForceId || id != -1) {
                if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
                    this.mConstraints.put(Integer.valueOf(id), new Constraint());
                }
                Constraint constraint = this.mConstraints.get(Integer.valueOf(id));
                if (constraint != null) {
                    constraint.mCustomConstraints = ConstraintAttribute.extractAttributes(this.mSavedAttributes, view);
                    constraint.fillFrom(id, param);
                    constraint.propertySet.visibility = view.getVisibility();
                    constraint.propertySet.alpha = view.getAlpha();
                    constraint.transform.rotation = view.getRotation();
                    constraint.transform.rotationX = view.getRotationX();
                    constraint.transform.rotationY = view.getRotationY();
                    constraint.transform.scaleX = view.getScaleX();
                    constraint.transform.scaleY = view.getScaleY();
                    float pivotX = view.getPivotX();
                    float pivotY = view.getPivotY();
                    if (!(((double) pivotX) == 0.0d && ((double) pivotY) == 0.0d)) {
                        constraint.transform.transformPivotX = pivotX;
                        constraint.transform.transformPivotY = pivotY;
                    }
                    constraint.transform.translationX = view.getTranslationX();
                    constraint.transform.translationY = view.getTranslationY();
                    if (Build.VERSION.SDK_INT >= 21) {
                        constraint.transform.translationZ = view.getTranslationZ();
                        if (constraint.transform.applyElevation) {
                            constraint.transform.elevation = view.getElevation();
                        }
                    }
                    if (view instanceof Barrier) {
                        Barrier barrier = (Barrier) view;
                        constraint.layout.mBarrierAllowsGoneWidgets = barrier.getAllowsGoneWidget();
                        constraint.layout.mReferenceIds = barrier.getReferencedIds();
                        constraint.layout.mBarrierDirection = barrier.getType();
                        constraint.layout.mBarrierMargin = barrier.getMargin();
                    }
                }
                i++;
            } else {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
        }
    }

    public void clone(Constraints constraints) {
        int count = constraints.getChildCount();
        this.mConstraints.clear();
        int i = 0;
        while (i < count) {
            View view = constraints.getChildAt(i);
            Constraints.LayoutParams param = (Constraints.LayoutParams) view.getLayoutParams();
            int id = view.getId();
            if (!this.mForceId || id != -1) {
                if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
                    this.mConstraints.put(Integer.valueOf(id), new Constraint());
                }
                Constraint constraint = this.mConstraints.get(Integer.valueOf(id));
                if (constraint != null) {
                    if (view instanceof ConstraintHelper) {
                        constraint.fillFromConstraints((ConstraintHelper) view, id, param);
                    }
                    constraint.fillFromConstraints(id, param);
                }
                i++;
            } else {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
        }
    }

    public void applyTo(ConstraintLayout constraintLayout) {
        applyToInternal(constraintLayout, true);
        constraintLayout.setConstraintSet((ConstraintSet) null);
        constraintLayout.requestLayout();
    }

    public void applyToWithoutCustom(ConstraintLayout constraintLayout) {
        applyToInternal(constraintLayout, false);
        constraintLayout.setConstraintSet((ConstraintSet) null);
    }

    public void applyCustomAttributes(ConstraintLayout constraintLayout) {
        Constraint constraint;
        int count = constraintLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = constraintLayout.getChildAt(i);
            int id = view.getId();
            if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
                Log.w(TAG, "id unknown " + Debug.getName(view));
            } else if (this.mForceId && id == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            } else if (this.mConstraints.containsKey(Integer.valueOf(id)) && (constraint = this.mConstraints.get(Integer.valueOf(id))) != null) {
                ConstraintAttribute.setAttributes(view, constraint.mCustomConstraints);
            }
        }
    }

    public void applyToHelper(ConstraintHelper helper, ConstraintWidget child, ConstraintLayout.LayoutParams layoutParams, SparseArray<ConstraintWidget> mapIdToWidget) {
        Constraint constraint;
        int id = helper.getId();
        if (this.mConstraints.containsKey(Integer.valueOf(id)) && (constraint = this.mConstraints.get(Integer.valueOf(id))) != null && (child instanceof HelperWidget)) {
            helper.loadParameters(constraint, (HelperWidget) child, layoutParams, mapIdToWidget);
        }
    }

    public void applyToLayoutParams(int id, ConstraintLayout.LayoutParams layoutParams) {
        Constraint constraint;
        if (this.mConstraints.containsKey(Integer.valueOf(id)) && (constraint = this.mConstraints.get(Integer.valueOf(id))) != null) {
            constraint.applyTo(layoutParams);
        }
    }

    /* access modifiers changed from: package-private */
    public void applyToInternal(ConstraintLayout constraintLayout, boolean applyPostLayout) {
        int count = constraintLayout.getChildCount();
        HashSet<Integer> used = new HashSet<>(this.mConstraints.keySet());
        for (int i = 0; i < count; i++) {
            View view = constraintLayout.getChildAt(i);
            int id = view.getId();
            if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
                Log.w(TAG, "id unknown " + Debug.getName(view));
            } else if (this.mForceId && id == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            } else if (id != -1) {
                if (this.mConstraints.containsKey(Integer.valueOf(id))) {
                    used.remove(Integer.valueOf(id));
                    Constraint constraint = this.mConstraints.get(Integer.valueOf(id));
                    if (constraint != null) {
                        if (view instanceof Barrier) {
                            constraint.layout.mHelperType = 1;
                            Barrier barrier = (Barrier) view;
                            barrier.setId(id);
                            barrier.setType(constraint.layout.mBarrierDirection);
                            barrier.setMargin(constraint.layout.mBarrierMargin);
                            barrier.setAllowsGoneWidget(constraint.layout.mBarrierAllowsGoneWidgets);
                            if (constraint.layout.mReferenceIds != null) {
                                barrier.setReferencedIds(constraint.layout.mReferenceIds);
                            } else if (constraint.layout.mReferenceIdString != null) {
                                constraint.layout.mReferenceIds = convertReferenceString(barrier, constraint.layout.mReferenceIdString);
                                barrier.setReferencedIds(constraint.layout.mReferenceIds);
                            }
                        }
                        ConstraintLayout.LayoutParams param = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                        param.validate();
                        constraint.applyTo(param);
                        if (applyPostLayout) {
                            ConstraintAttribute.setAttributes(view, constraint.mCustomConstraints);
                        }
                        view.setLayoutParams(param);
                        if (constraint.propertySet.mVisibilityMode == 0) {
                            view.setVisibility(constraint.propertySet.visibility);
                        }
                        view.setAlpha(constraint.propertySet.alpha);
                        view.setRotation(constraint.transform.rotation);
                        view.setRotationX(constraint.transform.rotationX);
                        view.setRotationY(constraint.transform.rotationY);
                        view.setScaleX(constraint.transform.scaleX);
                        view.setScaleY(constraint.transform.scaleY);
                        if (constraint.transform.transformPivotTarget != -1) {
                            View center = ((View) view.getParent()).findViewById(constraint.transform.transformPivotTarget);
                            if (center != null) {
                                float cy = ((float) (center.getTop() + center.getBottom())) / 2.0f;
                                float cx = ((float) (center.getLeft() + center.getRight())) / 2.0f;
                                if (view.getRight() - view.getLeft() > 0 && view.getBottom() - view.getTop() > 0) {
                                    view.setPivotX(cx - ((float) view.getLeft()));
                                    view.setPivotY(cy - ((float) view.getTop()));
                                }
                            }
                        } else {
                            if (!Float.isNaN(constraint.transform.transformPivotX)) {
                                view.setPivotX(constraint.transform.transformPivotX);
                            }
                            if (!Float.isNaN(constraint.transform.transformPivotY)) {
                                view.setPivotY(constraint.transform.transformPivotY);
                            }
                        }
                        view.setTranslationX(constraint.transform.translationX);
                        view.setTranslationY(constraint.transform.translationY);
                        if (Build.VERSION.SDK_INT >= 21) {
                            view.setTranslationZ(constraint.transform.translationZ);
                            if (constraint.transform.applyElevation) {
                                view.setElevation(constraint.transform.elevation);
                            }
                        }
                    }
                } else {
                    Log.v(TAG, "WARNING NO CONSTRAINTS for view " + id);
                }
            }
        }
        Iterator<Integer> it = used.iterator();
        while (it.hasNext()) {
            Integer id2 = it.next();
            Constraint constraint2 = this.mConstraints.get(id2);
            if (constraint2 != null) {
                if (constraint2.layout.mHelperType == 1) {
                    Barrier barrier2 = new Barrier(constraintLayout.getContext());
                    barrier2.setId(id2.intValue());
                    if (constraint2.layout.mReferenceIds != null) {
                        barrier2.setReferencedIds(constraint2.layout.mReferenceIds);
                    } else if (constraint2.layout.mReferenceIdString != null) {
                        constraint2.layout.mReferenceIds = convertReferenceString(barrier2, constraint2.layout.mReferenceIdString);
                        barrier2.setReferencedIds(constraint2.layout.mReferenceIds);
                    }
                    barrier2.setType(constraint2.layout.mBarrierDirection);
                    barrier2.setMargin(constraint2.layout.mBarrierMargin);
                    ConstraintLayout.LayoutParams param2 = constraintLayout.generateDefaultLayoutParams();
                    barrier2.validateParams();
                    constraint2.applyTo(param2);
                    constraintLayout.addView(barrier2, param2);
                }
                if (constraint2.layout.mIsGuideline) {
                    Guideline g = new Guideline(constraintLayout.getContext());
                    g.setId(id2.intValue());
                    ConstraintLayout.LayoutParams param3 = constraintLayout.generateDefaultLayoutParams();
                    constraint2.applyTo(param3);
                    constraintLayout.addView(g, param3);
                }
            }
        }
        for (int i2 = 0; i2 < count; i2++) {
            View view2 = constraintLayout.getChildAt(i2);
            if (view2 instanceof ConstraintHelper) {
                ((ConstraintHelper) view2).applyLayoutFeaturesInConstraintSet(constraintLayout);
            }
        }
    }

    public void center(int centerID, int firstID, int firstSide, int firstMargin, int secondId, int secondSide, int secondMargin, float bias) {
        int i = firstSide;
        float f = bias;
        if (firstMargin < 0) {
            throw new IllegalArgumentException("margin must be > 0");
        } else if (secondMargin < 0) {
            throw new IllegalArgumentException("margin must be > 0");
        } else if (f <= 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("bias must be between 0 and 1 inclusive");
        } else if (i == 1 || i == 2) {
            int i2 = centerID;
            connect(i2, 1, firstID, firstSide, firstMargin);
            connect(i2, 2, secondId, secondSide, secondMargin);
            Constraint constraint = this.mConstraints.get(Integer.valueOf(centerID));
            if (constraint != null) {
                constraint.layout.horizontalBias = f;
            }
        } else if (i == 6 || i == 7) {
            int i3 = centerID;
            connect(i3, 6, firstID, firstSide, firstMargin);
            connect(i3, 7, secondId, secondSide, secondMargin);
            Constraint constraint2 = this.mConstraints.get(Integer.valueOf(centerID));
            if (constraint2 != null) {
                constraint2.layout.horizontalBias = f;
            }
        } else {
            int i4 = centerID;
            connect(i4, 3, firstID, firstSide, firstMargin);
            connect(i4, 4, secondId, secondSide, secondMargin);
            Constraint constraint3 = this.mConstraints.get(Integer.valueOf(centerID));
            if (constraint3 != null) {
                constraint3.layout.verticalBias = f;
            }
        }
    }

    public void centerHorizontally(int centerID, int leftId, int leftSide, int leftMargin, int rightId, int rightSide, int rightMargin, float bias) {
        connect(centerID, 1, leftId, leftSide, leftMargin);
        connect(centerID, 2, rightId, rightSide, rightMargin);
        Constraint constraint = this.mConstraints.get(Integer.valueOf(centerID));
        if (constraint != null) {
            constraint.layout.horizontalBias = bias;
            return;
        }
        float f = bias;
    }

    public void centerHorizontallyRtl(int centerID, int startId, int startSide, int startMargin, int endId, int endSide, int endMargin, float bias) {
        connect(centerID, 6, startId, startSide, startMargin);
        connect(centerID, 7, endId, endSide, endMargin);
        Constraint constraint = this.mConstraints.get(Integer.valueOf(centerID));
        if (constraint != null) {
            constraint.layout.horizontalBias = bias;
            return;
        }
        float f = bias;
    }

    public void centerVertically(int centerID, int topId, int topSide, int topMargin, int bottomId, int bottomSide, int bottomMargin, float bias) {
        connect(centerID, 3, topId, topSide, topMargin);
        connect(centerID, 4, bottomId, bottomSide, bottomMargin);
        Constraint constraint = this.mConstraints.get(Integer.valueOf(centerID));
        if (constraint != null) {
            constraint.layout.verticalBias = bias;
            return;
        }
        float f = bias;
    }

    public void createVerticalChain(int topId, int topSide, int bottomId, int bottomSide, int[] chainIds, float[] weights, int style) {
        int[] iArr = chainIds;
        float[] fArr = weights;
        if (iArr.length < 2) {
            int i = style;
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        } else if (fArr == null || fArr.length == iArr.length) {
            if (fArr != null) {
                get(iArr[0]).layout.verticalWeight = fArr[0];
            }
            get(iArr[0]).layout.verticalChainStyle = style;
            connect(iArr[0], 3, topId, topSide, 0);
            for (int i2 = 1; i2 < iArr.length; i2++) {
                int i3 = iArr[i2];
                connect(iArr[i2], 3, iArr[i2 - 1], 4, 0);
                connect(iArr[i2 - 1], 4, iArr[i2], 3, 0);
                if (fArr != null) {
                    get(iArr[i2]).layout.verticalWeight = fArr[i2];
                }
            }
            connect(iArr[iArr.length - 1], 4, bottomId, bottomSide, 0);
        } else {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        }
    }

    public void createHorizontalChain(int leftId, int leftSide, int rightId, int rightSide, int[] chainIds, float[] weights, int style) {
        createHorizontalChain(leftId, leftSide, rightId, rightSide, chainIds, weights, style, 1, 2);
    }

    public void createHorizontalChainRtl(int startId, int startSide, int endId, int endSide, int[] chainIds, float[] weights, int style) {
        createHorizontalChain(startId, startSide, endId, endSide, chainIds, weights, style, 6, 7);
    }

    private void createHorizontalChain(int leftId, int leftSide, int rightId, int rightSide, int[] chainIds, float[] weights, int style, int left, int right) {
        int[] iArr = chainIds;
        float[] fArr = weights;
        if (iArr.length < 2) {
            int i = style;
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        } else if (fArr == null || fArr.length == iArr.length) {
            if (fArr != null) {
                get(iArr[0]).layout.horizontalWeight = fArr[0];
            }
            get(iArr[0]).layout.horizontalChainStyle = style;
            connect(iArr[0], left, leftId, leftSide, -1);
            for (int i2 = 1; i2 < iArr.length; i2++) {
                int i3 = iArr[i2];
                connect(iArr[i2], left, iArr[i2 - 1], right, -1);
                connect(iArr[i2 - 1], right, iArr[i2], left, -1);
                if (fArr != null) {
                    get(iArr[i2]).layout.horizontalWeight = fArr[i2];
                }
            }
            connect(iArr[iArr.length - 1], right, rightId, rightSide, -1);
        } else {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        }
    }

    public void connect(int startID, int startSide, int endID, int endSide, int margin) {
        if (!this.mConstraints.containsKey(Integer.valueOf(startID))) {
            this.mConstraints.put(Integer.valueOf(startID), new Constraint());
        }
        Constraint constraint = this.mConstraints.get(Integer.valueOf(startID));
        if (constraint != null) {
            switch (startSide) {
                case 1:
                    if (endSide == 1) {
                        constraint.layout.leftToLeft = endID;
                        constraint.layout.leftToRight = -1;
                    } else if (endSide == 2) {
                        constraint.layout.leftToRight = endID;
                        constraint.layout.leftToLeft = -1;
                    } else {
                        throw new IllegalArgumentException("Left to " + sideToString(endSide) + " undefined");
                    }
                    constraint.layout.leftMargin = margin;
                    return;
                case 2:
                    if (endSide == 1) {
                        constraint.layout.rightToLeft = endID;
                        constraint.layout.rightToRight = -1;
                    } else if (endSide == 2) {
                        constraint.layout.rightToRight = endID;
                        constraint.layout.rightToLeft = -1;
                    } else {
                        throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                    }
                    constraint.layout.rightMargin = margin;
                    return;
                case 3:
                    if (endSide == 3) {
                        constraint.layout.topToTop = endID;
                        constraint.layout.topToBottom = -1;
                        constraint.layout.baselineToBaseline = -1;
                        constraint.layout.baselineToTop = -1;
                        constraint.layout.baselineToBottom = -1;
                    } else if (endSide == 4) {
                        constraint.layout.topToBottom = endID;
                        constraint.layout.topToTop = -1;
                        constraint.layout.baselineToBaseline = -1;
                        constraint.layout.baselineToTop = -1;
                        constraint.layout.baselineToBottom = -1;
                    } else {
                        throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                    }
                    constraint.layout.topMargin = margin;
                    return;
                case 4:
                    if (endSide == 4) {
                        constraint.layout.bottomToBottom = endID;
                        constraint.layout.bottomToTop = -1;
                        constraint.layout.baselineToBaseline = -1;
                        constraint.layout.baselineToTop = -1;
                        constraint.layout.baselineToBottom = -1;
                    } else if (endSide == 3) {
                        constraint.layout.bottomToTop = endID;
                        constraint.layout.bottomToBottom = -1;
                        constraint.layout.baselineToBaseline = -1;
                        constraint.layout.baselineToTop = -1;
                        constraint.layout.baselineToBottom = -1;
                    } else {
                        throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                    }
                    constraint.layout.bottomMargin = margin;
                    return;
                case 5:
                    if (endSide == 5) {
                        constraint.layout.baselineToBaseline = endID;
                        constraint.layout.bottomToBottom = -1;
                        constraint.layout.bottomToTop = -1;
                        constraint.layout.topToTop = -1;
                        constraint.layout.topToBottom = -1;
                        return;
                    } else if (endSide == 3) {
                        constraint.layout.baselineToTop = endID;
                        constraint.layout.bottomToBottom = -1;
                        constraint.layout.bottomToTop = -1;
                        constraint.layout.topToTop = -1;
                        constraint.layout.topToBottom = -1;
                        return;
                    } else if (endSide == 4) {
                        constraint.layout.baselineToBottom = endID;
                        constraint.layout.bottomToBottom = -1;
                        constraint.layout.bottomToTop = -1;
                        constraint.layout.topToTop = -1;
                        constraint.layout.topToBottom = -1;
                        return;
                    } else {
                        throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                    }
                case 6:
                    if (endSide == 6) {
                        constraint.layout.startToStart = endID;
                        constraint.layout.startToEnd = -1;
                    } else if (endSide == 7) {
                        constraint.layout.startToEnd = endID;
                        constraint.layout.startToStart = -1;
                    } else {
                        throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                    }
                    constraint.layout.startMargin = margin;
                    return;
                case 7:
                    if (endSide == 7) {
                        constraint.layout.endToEnd = endID;
                        constraint.layout.endToStart = -1;
                    } else if (endSide == 6) {
                        constraint.layout.endToStart = endID;
                        constraint.layout.endToEnd = -1;
                    } else {
                        throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                    }
                    constraint.layout.endMargin = margin;
                    return;
                default:
                    throw new IllegalArgumentException(sideToString(startSide) + " to " + sideToString(endSide) + " unknown");
            }
        }
    }

    public void connect(int startID, int startSide, int endID, int endSide) {
        if (!this.mConstraints.containsKey(Integer.valueOf(startID))) {
            this.mConstraints.put(Integer.valueOf(startID), new Constraint());
        }
        Constraint constraint = this.mConstraints.get(Integer.valueOf(startID));
        if (constraint != null) {
            switch (startSide) {
                case 1:
                    if (endSide == 1) {
                        constraint.layout.leftToLeft = endID;
                        constraint.layout.leftToRight = -1;
                        return;
                    } else if (endSide == 2) {
                        constraint.layout.leftToRight = endID;
                        constraint.layout.leftToLeft = -1;
                        return;
                    } else {
                        throw new IllegalArgumentException("left to " + sideToString(endSide) + " undefined");
                    }
                case 2:
                    if (endSide == 1) {
                        constraint.layout.rightToLeft = endID;
                        constraint.layout.rightToRight = -1;
                        return;
                    } else if (endSide == 2) {
                        constraint.layout.rightToRight = endID;
                        constraint.layout.rightToLeft = -1;
                        return;
                    } else {
                        throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                    }
                case 3:
                    if (endSide == 3) {
                        constraint.layout.topToTop = endID;
                        constraint.layout.topToBottom = -1;
                        constraint.layout.baselineToBaseline = -1;
                        constraint.layout.baselineToTop = -1;
                        constraint.layout.baselineToBottom = -1;
                        return;
                    } else if (endSide == 4) {
                        constraint.layout.topToBottom = endID;
                        constraint.layout.topToTop = -1;
                        constraint.layout.baselineToBaseline = -1;
                        constraint.layout.baselineToTop = -1;
                        constraint.layout.baselineToBottom = -1;
                        return;
                    } else {
                        throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                    }
                case 4:
                    if (endSide == 4) {
                        constraint.layout.bottomToBottom = endID;
                        constraint.layout.bottomToTop = -1;
                        constraint.layout.baselineToBaseline = -1;
                        constraint.layout.baselineToTop = -1;
                        constraint.layout.baselineToBottom = -1;
                        return;
                    } else if (endSide == 3) {
                        constraint.layout.bottomToTop = endID;
                        constraint.layout.bottomToBottom = -1;
                        constraint.layout.baselineToBaseline = -1;
                        constraint.layout.baselineToTop = -1;
                        constraint.layout.baselineToBottom = -1;
                        return;
                    } else {
                        throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                    }
                case 5:
                    if (endSide == 5) {
                        constraint.layout.baselineToBaseline = endID;
                        constraint.layout.bottomToBottom = -1;
                        constraint.layout.bottomToTop = -1;
                        constraint.layout.topToTop = -1;
                        constraint.layout.topToBottom = -1;
                        return;
                    } else if (endSide == 3) {
                        constraint.layout.baselineToTop = endID;
                        constraint.layout.bottomToBottom = -1;
                        constraint.layout.bottomToTop = -1;
                        constraint.layout.topToTop = -1;
                        constraint.layout.topToBottom = -1;
                        return;
                    } else if (endSide == 4) {
                        constraint.layout.baselineToBottom = endID;
                        constraint.layout.bottomToBottom = -1;
                        constraint.layout.bottomToTop = -1;
                        constraint.layout.topToTop = -1;
                        constraint.layout.topToBottom = -1;
                        return;
                    } else {
                        throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                    }
                case 6:
                    if (endSide == 6) {
                        constraint.layout.startToStart = endID;
                        constraint.layout.startToEnd = -1;
                        return;
                    } else if (endSide == 7) {
                        constraint.layout.startToEnd = endID;
                        constraint.layout.startToStart = -1;
                        return;
                    } else {
                        throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                    }
                case 7:
                    if (endSide == 7) {
                        constraint.layout.endToEnd = endID;
                        constraint.layout.endToStart = -1;
                        return;
                    } else if (endSide == 6) {
                        constraint.layout.endToStart = endID;
                        constraint.layout.endToEnd = -1;
                        return;
                    } else {
                        throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                    }
                default:
                    throw new IllegalArgumentException(sideToString(startSide) + " to " + sideToString(endSide) + " unknown");
            }
        }
    }

    public void centerHorizontally(int viewId, int toView) {
        if (toView == 0) {
            center(viewId, 0, 1, 0, 0, 2, 0, 0.5f);
        } else {
            center(viewId, toView, 2, 0, toView, 1, 0, 0.5f);
        }
    }

    public void centerHorizontallyRtl(int viewId, int toView) {
        if (toView == 0) {
            center(viewId, 0, 6, 0, 0, 7, 0, 0.5f);
        } else {
            center(viewId, toView, 7, 0, toView, 6, 0, 0.5f);
        }
    }

    public void centerVertically(int viewId, int toView) {
        if (toView == 0) {
            center(viewId, 0, 3, 0, 0, 4, 0, 0.5f);
        } else {
            center(viewId, toView, 4, 0, toView, 3, 0, 0.5f);
        }
    }

    public void clear(int viewId) {
        this.mConstraints.remove(Integer.valueOf(viewId));
    }

    public void clear(int viewId, int anchor) {
        Constraint constraint;
        if (this.mConstraints.containsKey(Integer.valueOf(viewId)) && (constraint = this.mConstraints.get(Integer.valueOf(viewId))) != null) {
            switch (anchor) {
                case 1:
                    constraint.layout.leftToRight = -1;
                    constraint.layout.leftToLeft = -1;
                    constraint.layout.leftMargin = -1;
                    constraint.layout.goneLeftMargin = Integer.MIN_VALUE;
                    return;
                case 2:
                    constraint.layout.rightToRight = -1;
                    constraint.layout.rightToLeft = -1;
                    constraint.layout.rightMargin = -1;
                    constraint.layout.goneRightMargin = Integer.MIN_VALUE;
                    return;
                case 3:
                    constraint.layout.topToBottom = -1;
                    constraint.layout.topToTop = -1;
                    constraint.layout.topMargin = 0;
                    constraint.layout.goneTopMargin = Integer.MIN_VALUE;
                    return;
                case 4:
                    constraint.layout.bottomToTop = -1;
                    constraint.layout.bottomToBottom = -1;
                    constraint.layout.bottomMargin = 0;
                    constraint.layout.goneBottomMargin = Integer.MIN_VALUE;
                    return;
                case 5:
                    constraint.layout.baselineToBaseline = -1;
                    constraint.layout.baselineToTop = -1;
                    constraint.layout.baselineToBottom = -1;
                    constraint.layout.baselineMargin = 0;
                    constraint.layout.goneBaselineMargin = Integer.MIN_VALUE;
                    return;
                case 6:
                    constraint.layout.startToEnd = -1;
                    constraint.layout.startToStart = -1;
                    constraint.layout.startMargin = 0;
                    constraint.layout.goneStartMargin = Integer.MIN_VALUE;
                    return;
                case 7:
                    constraint.layout.endToStart = -1;
                    constraint.layout.endToEnd = -1;
                    constraint.layout.endMargin = 0;
                    constraint.layout.goneEndMargin = Integer.MIN_VALUE;
                    return;
                case 8:
                    constraint.layout.circleAngle = -1.0f;
                    constraint.layout.circleRadius = -1;
                    constraint.layout.circleConstraint = -1;
                    return;
                default:
                    throw new IllegalArgumentException("unknown constraint");
            }
        }
    }

    public void setMargin(int viewId, int anchor, int value) {
        Constraint constraint = get(viewId);
        switch (anchor) {
            case 1:
                constraint.layout.leftMargin = value;
                return;
            case 2:
                constraint.layout.rightMargin = value;
                return;
            case 3:
                constraint.layout.topMargin = value;
                return;
            case 4:
                constraint.layout.bottomMargin = value;
                return;
            case 5:
                constraint.layout.baselineMargin = value;
                return;
            case 6:
                constraint.layout.startMargin = value;
                return;
            case 7:
                constraint.layout.endMargin = value;
                return;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
    }

    public void setGoneMargin(int viewId, int anchor, int value) {
        Constraint constraint = get(viewId);
        switch (anchor) {
            case 1:
                constraint.layout.goneLeftMargin = value;
                return;
            case 2:
                constraint.layout.goneRightMargin = value;
                return;
            case 3:
                constraint.layout.goneTopMargin = value;
                return;
            case 4:
                constraint.layout.goneBottomMargin = value;
                return;
            case 5:
                constraint.layout.goneBaselineMargin = value;
                return;
            case 6:
                constraint.layout.goneStartMargin = value;
                return;
            case 7:
                constraint.layout.goneEndMargin = value;
                return;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
    }

    public void setHorizontalBias(int viewId, float bias) {
        get(viewId).layout.horizontalBias = bias;
    }

    public void setVerticalBias(int viewId, float bias) {
        get(viewId).layout.verticalBias = bias;
    }

    public void setDimensionRatio(int viewId, String ratio) {
        get(viewId).layout.dimensionRatio = ratio;
    }

    public void setVisibility(int viewId, int visibility) {
        get(viewId).propertySet.visibility = visibility;
    }

    public void setVisibilityMode(int viewId, int visibilityMode) {
        get(viewId).propertySet.mVisibilityMode = visibilityMode;
    }

    public int getVisibilityMode(int viewId) {
        return get(viewId).propertySet.mVisibilityMode;
    }

    public int getVisibility(int viewId) {
        return get(viewId).propertySet.visibility;
    }

    public int getHeight(int viewId) {
        return get(viewId).layout.mHeight;
    }

    public int getWidth(int viewId) {
        return get(viewId).layout.mWidth;
    }

    public void setAlpha(int viewId, float alpha) {
        get(viewId).propertySet.alpha = alpha;
    }

    public boolean getApplyElevation(int viewId) {
        return get(viewId).transform.applyElevation;
    }

    public void setApplyElevation(int viewId, boolean apply) {
        if (Build.VERSION.SDK_INT >= 21) {
            get(viewId).transform.applyElevation = apply;
        }
    }

    public void setElevation(int viewId, float elevation) {
        if (Build.VERSION.SDK_INT >= 21) {
            get(viewId).transform.elevation = elevation;
            get(viewId).transform.applyElevation = true;
        }
    }

    public void setRotation(int viewId, float rotation) {
        get(viewId).transform.rotation = rotation;
    }

    public void setRotationX(int viewId, float rotationX) {
        get(viewId).transform.rotationX = rotationX;
    }

    public void setRotationY(int viewId, float rotationY) {
        get(viewId).transform.rotationY = rotationY;
    }

    public void setScaleX(int viewId, float scaleX) {
        get(viewId).transform.scaleX = scaleX;
    }

    public void setScaleY(int viewId, float scaleY) {
        get(viewId).transform.scaleY = scaleY;
    }

    public void setTransformPivotX(int viewId, float transformPivotX) {
        get(viewId).transform.transformPivotX = transformPivotX;
    }

    public void setTransformPivotY(int viewId, float transformPivotY) {
        get(viewId).transform.transformPivotY = transformPivotY;
    }

    public void setTransformPivot(int viewId, float transformPivotX, float transformPivotY) {
        Constraint constraint = get(viewId);
        constraint.transform.transformPivotY = transformPivotY;
        constraint.transform.transformPivotX = transformPivotX;
    }

    public void setTranslationX(int viewId, float translationX) {
        get(viewId).transform.translationX = translationX;
    }

    public void setTranslationY(int viewId, float translationY) {
        get(viewId).transform.translationY = translationY;
    }

    public void setTranslation(int viewId, float translationX, float translationY) {
        Constraint constraint = get(viewId);
        constraint.transform.translationX = translationX;
        constraint.transform.translationY = translationY;
    }

    public void setTranslationZ(int viewId, float translationZ) {
        if (Build.VERSION.SDK_INT >= 21) {
            get(viewId).transform.translationZ = translationZ;
        }
    }

    public void setEditorAbsoluteX(int viewId, int position) {
        get(viewId).layout.editorAbsoluteX = position;
    }

    public void setEditorAbsoluteY(int viewId, int position) {
        get(viewId).layout.editorAbsoluteY = position;
    }

    public void setLayoutWrapBehavior(int viewId, int behavior) {
        if (behavior >= 0 && behavior <= 3) {
            get(viewId).layout.mWrapBehavior = behavior;
        }
    }

    public void constrainHeight(int viewId, int height) {
        get(viewId).layout.mHeight = height;
    }

    public void constrainWidth(int viewId, int width) {
        get(viewId).layout.mWidth = width;
    }

    public void constrainCircle(int viewId, int id, int radius, float angle) {
        Constraint constraint = get(viewId);
        constraint.layout.circleConstraint = id;
        constraint.layout.circleRadius = radius;
        constraint.layout.circleAngle = angle;
    }

    public void constrainMaxHeight(int viewId, int height) {
        get(viewId).layout.heightMax = height;
    }

    public void constrainMaxWidth(int viewId, int width) {
        get(viewId).layout.widthMax = width;
    }

    public void constrainMinHeight(int viewId, int height) {
        get(viewId).layout.heightMin = height;
    }

    public void constrainMinWidth(int viewId, int width) {
        get(viewId).layout.widthMin = width;
    }

    public void constrainPercentWidth(int viewId, float percent) {
        get(viewId).layout.widthPercent = percent;
    }

    public void constrainPercentHeight(int viewId, float percent) {
        get(viewId).layout.heightPercent = percent;
    }

    public void constrainDefaultHeight(int viewId, int height) {
        get(viewId).layout.heightDefault = height;
    }

    public void constrainedWidth(int viewId, boolean constrained) {
        get(viewId).layout.constrainedWidth = constrained;
    }

    public void constrainedHeight(int viewId, boolean constrained) {
        get(viewId).layout.constrainedHeight = constrained;
    }

    public void constrainDefaultWidth(int viewId, int width) {
        get(viewId).layout.widthDefault = width;
    }

    public void setHorizontalWeight(int viewId, float weight) {
        get(viewId).layout.horizontalWeight = weight;
    }

    public void setVerticalWeight(int viewId, float weight) {
        get(viewId).layout.verticalWeight = weight;
    }

    public void setHorizontalChainStyle(int viewId, int chainStyle) {
        get(viewId).layout.horizontalChainStyle = chainStyle;
    }

    public void setVerticalChainStyle(int viewId, int chainStyle) {
        get(viewId).layout.verticalChainStyle = chainStyle;
    }

    public void addToHorizontalChain(int viewId, int leftId, int rightId) {
        connect(viewId, 1, leftId, leftId == 0 ? 1 : 2, 0);
        connect(viewId, 2, rightId, rightId == 0 ? 2 : 1, 0);
        if (leftId != 0) {
            connect(leftId, 2, viewId, 1, 0);
        }
        if (rightId != 0) {
            connect(rightId, 1, viewId, 2, 0);
        }
    }

    public void addToHorizontalChainRTL(int viewId, int leftId, int rightId) {
        connect(viewId, 6, leftId, leftId == 0 ? 6 : 7, 0);
        connect(viewId, 7, rightId, rightId == 0 ? 7 : 6, 0);
        if (leftId != 0) {
            connect(leftId, 7, viewId, 6, 0);
        }
        if (rightId != 0) {
            connect(rightId, 6, viewId, 7, 0);
        }
    }

    public void addToVerticalChain(int viewId, int topId, int bottomId) {
        connect(viewId, 3, topId, topId == 0 ? 3 : 4, 0);
        connect(viewId, 4, bottomId, bottomId == 0 ? 4 : 3, 0);
        if (topId != 0) {
            connect(topId, 4, viewId, 3, 0);
        }
        if (bottomId != 0) {
            connect(bottomId, 3, viewId, 4, 0);
        }
    }

    public void removeFromVerticalChain(int viewId) {
        if (this.mConstraints.containsKey(Integer.valueOf(viewId))) {
            Constraint constraint = this.mConstraints.get(Integer.valueOf(viewId));
            if (constraint != null) {
                int topId = constraint.layout.topToBottom;
                int bottomId = constraint.layout.bottomToTop;
                if (!(topId == -1 && bottomId == -1)) {
                    if (topId != -1 && bottomId != -1) {
                        connect(topId, 4, bottomId, 3, 0);
                        connect(bottomId, 3, topId, 4, 0);
                    } else if (constraint.layout.bottomToBottom != -1) {
                        connect(topId, 4, constraint.layout.bottomToBottom, 4, 0);
                    } else if (constraint.layout.topToTop != -1) {
                        connect(bottomId, 3, constraint.layout.topToTop, 3, 0);
                    }
                }
            } else {
                return;
            }
        }
        clear(viewId, 3);
        clear(viewId, 4);
    }

    public void removeFromHorizontalChain(int viewId) {
        Constraint constraint;
        if (this.mConstraints.containsKey(Integer.valueOf(viewId)) && (constraint = this.mConstraints.get(Integer.valueOf(viewId))) != null) {
            int leftId = constraint.layout.leftToRight;
            int rightId = constraint.layout.rightToLeft;
            if (leftId == -1 && rightId == -1) {
                int startId = constraint.layout.startToEnd;
                int endId = constraint.layout.endToStart;
                if (!(startId == -1 && endId == -1)) {
                    if (startId != -1 && endId != -1) {
                        connect(startId, 7, endId, 6, 0);
                        connect(endId, 6, leftId, 7, 0);
                    } else if (endId != -1) {
                        if (constraint.layout.rightToRight != -1) {
                            connect(leftId, 7, constraint.layout.rightToRight, 7, 0);
                        } else if (constraint.layout.leftToLeft != -1) {
                            connect(endId, 6, constraint.layout.leftToLeft, 6, 0);
                        }
                    }
                }
                clear(viewId, 6);
                clear(viewId, 7);
                return;
            }
            if (leftId != -1 && rightId != -1) {
                connect(leftId, 2, rightId, 1, 0);
                connect(rightId, 1, leftId, 2, 0);
            } else if (constraint.layout.rightToRight != -1) {
                connect(leftId, 2, constraint.layout.rightToRight, 2, 0);
            } else if (constraint.layout.leftToLeft != -1) {
                connect(rightId, 1, constraint.layout.leftToLeft, 1, 0);
            }
            clear(viewId, 1);
            clear(viewId, 2);
        }
    }

    public void create(int guidelineID, int orientation) {
        Constraint constraint = get(guidelineID);
        constraint.layout.mIsGuideline = true;
        constraint.layout.orientation = orientation;
    }

    public void createBarrier(int id, int direction, int margin, int... referenced) {
        Constraint constraint = get(id);
        constraint.layout.mHelperType = 1;
        constraint.layout.mBarrierDirection = direction;
        constraint.layout.mBarrierMargin = margin;
        constraint.layout.mIsGuideline = false;
        constraint.layout.mReferenceIds = referenced;
    }

    public void setGuidelineBegin(int guidelineID, int margin) {
        get(guidelineID).layout.guideBegin = margin;
        get(guidelineID).layout.guideEnd = -1;
        get(guidelineID).layout.guidePercent = -1.0f;
    }

    public void setGuidelineEnd(int guidelineID, int margin) {
        get(guidelineID).layout.guideEnd = margin;
        get(guidelineID).layout.guideBegin = -1;
        get(guidelineID).layout.guidePercent = -1.0f;
    }

    public void setGuidelinePercent(int guidelineID, float ratio) {
        get(guidelineID).layout.guidePercent = ratio;
        get(guidelineID).layout.guideEnd = -1;
        get(guidelineID).layout.guideBegin = -1;
    }

    public int[] getReferencedIds(int id) {
        Constraint constraint = get(id);
        if (constraint.layout.mReferenceIds == null) {
            return new int[0];
        }
        return Arrays.copyOf(constraint.layout.mReferenceIds, constraint.layout.mReferenceIds.length);
    }

    public void setReferencedIds(int id, int... referenced) {
        get(id).layout.mReferenceIds = referenced;
    }

    public void setBarrierType(int id, int type) {
        get(id).layout.mHelperType = type;
    }

    public void removeAttribute(String attributeName) {
        this.mSavedAttributes.remove(attributeName);
    }

    public void setIntValue(int viewId, String attributeName, int value) {
        get(viewId).setIntValue(attributeName, value);
    }

    public void setColorValue(int viewId, String attributeName, int value) {
        get(viewId).setColorValue(attributeName, value);
    }

    public void setFloatValue(int viewId, String attributeName, float value) {
        get(viewId).setFloatValue(attributeName, value);
    }

    public void setStringValue(int viewId, String attributeName, String value) {
        get(viewId).setStringValue(attributeName, value);
    }

    private void addAttributes(ConstraintAttribute.AttributeType attributeType, String... attributeName) {
        for (int i = 0; i < attributeName.length; i++) {
            if (this.mSavedAttributes.containsKey(attributeName[i])) {
                ConstraintAttribute constraintAttribute = this.mSavedAttributes.get(attributeName[i]);
                if (!(constraintAttribute == null || constraintAttribute.getType() == attributeType)) {
                    throw new IllegalArgumentException("ConstraintAttribute is already a " + constraintAttribute.getType().name());
                }
            } else {
                this.mSavedAttributes.put(attributeName[i], new ConstraintAttribute(attributeName[i], attributeType));
            }
        }
    }

    public void parseIntAttributes(Constraint set, String attributes) {
        String[] sp = attributes.split(",");
        for (int i = 0; i < sp.length; i++) {
            String[] attr = sp[i].split("=");
            if (attr.length != 2) {
                Log.w(TAG, " Unable to parse " + sp[i]);
            } else {
                set.setFloatValue(attr[0], (float) Integer.decode(attr[1]).intValue());
            }
        }
    }

    public void parseColorAttributes(Constraint set, String attributes) {
        String[] sp = attributes.split(",");
        for (int i = 0; i < sp.length; i++) {
            String[] attr = sp[i].split("=");
            if (attr.length != 2) {
                Log.w(TAG, " Unable to parse " + sp[i]);
            } else {
                set.setColorValue(attr[0], Color.parseColor(attr[1]));
            }
        }
    }

    public void parseFloatAttributes(Constraint set, String attributes) {
        String[] sp = attributes.split(",");
        for (int i = 0; i < sp.length; i++) {
            String[] attr = sp[i].split("=");
            if (attr.length != 2) {
                Log.w(TAG, " Unable to parse " + sp[i]);
            } else {
                set.setFloatValue(attr[0], Float.parseFloat(attr[1]));
            }
        }
    }

    public void parseStringAttributes(Constraint set, String attributes) {
        String[] sp = splitString(attributes);
        for (int i = 0; i < sp.length; i++) {
            String[] attr = sp[i].split("=");
            Log.w(TAG, " Unable to parse " + sp[i]);
            set.setStringValue(attr[0], attr[1]);
        }
    }

    private static String[] splitString(String str) {
        char[] chars = str.toCharArray();
        ArrayList<String> list = new ArrayList<>();
        boolean inDouble = false;
        int start = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ',' && !inDouble) {
                list.add(new String(chars, start, i - start));
                start = i + 1;
            } else if (chars[i] == '\"') {
                inDouble = !inDouble;
            }
        }
        list.add(new String(chars, start, chars.length - start));
        return (String[]) list.toArray(new String[list.size()]);
    }

    public void addIntAttributes(String... attributeName) {
        addAttributes(ConstraintAttribute.AttributeType.INT_TYPE, attributeName);
    }

    public void addColorAttributes(String... attributeName) {
        addAttributes(ConstraintAttribute.AttributeType.COLOR_TYPE, attributeName);
    }

    public void addFloatAttributes(String... attributeName) {
        addAttributes(ConstraintAttribute.AttributeType.FLOAT_TYPE, attributeName);
    }

    public void addStringAttributes(String... attributeName) {
        addAttributes(ConstraintAttribute.AttributeType.STRING_TYPE, attributeName);
    }

    private Constraint get(int id) {
        if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
            this.mConstraints.put(Integer.valueOf(id), new Constraint());
        }
        return this.mConstraints.get(Integer.valueOf(id));
    }

    private String sideToString(int side) {
        switch (side) {
            case 1:
                return "left";
            case 2:
                return "right";
            case 3:
                return CommonCssConstants.TOP;
            case 4:
                return CommonCssConstants.BOTTOM;
            case 5:
                return "baseline";
            case 6:
                return "start";
            case 7:
                return "end";
            default:
                return CommonCssConstants.UNDEFINED_NAME;
        }
    }

    public void load(Context context, int resourceId) {
        XmlPullParser parser = context.getResources().getXml(resourceId);
        try {
            for (int eventType = parser.getEventType(); eventType != 1; eventType = parser.next()) {
                switch (eventType) {
                    case 0:
                        String document = parser.getName();
                        break;
                    case 2:
                        String tagName = parser.getName();
                        Constraint constraint = fillFromAttributeList(context, Xml.asAttributeSet(parser), false);
                        if (tagName.equalsIgnoreCase("Guideline")) {
                            constraint.layout.mIsGuideline = true;
                        }
                        this.mConstraints.put(Integer.valueOf(constraint.mViewId), constraint);
                        break;
                    case 3:
                        break;
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void load(android.content.Context r11, org.xmlpull.v1.XmlPullParser r12) {
        /*
            r10 = this;
            r0 = 0
            r1 = 0
            int r2 = r12.getEventType()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
        L_0x0006:
            r3 = 1
            if (r2 == r3) goto L_0x01e1
            r4 = 3
            r5 = 2
            r6 = -1
            r7 = 0
            switch(r2) {
                case 0: goto L_0x01d5;
                case 1: goto L_0x0010;
                case 2: goto L_0x0062;
                case 3: goto L_0x0012;
                default: goto L_0x0010;
            }     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
        L_0x0010:
            goto L_0x01da
        L_0x0012:
            java.lang.String r8 = r12.getName()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r0 = r8
            java.util.Locale r8 = java.util.Locale.ROOT     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.String r8 = r0.toLowerCase(r8)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            int r9 = r8.hashCode()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            switch(r9) {
                case -2075718416: goto L_0x0042;
                case -190376483: goto L_0x0039;
                case 426575017: goto L_0x002f;
                case 2146106725: goto L_0x0025;
                default: goto L_0x0024;
            }     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
        L_0x0024:
            goto L_0x004c
        L_0x0025:
            java.lang.String r3 = "constraintset"
            boolean r3 = r8.equals(r3)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            if (r3 == 0) goto L_0x0024
            r3 = 0
            goto L_0x004d
        L_0x002f:
            java.lang.String r3 = "constraintoverride"
            boolean r3 = r8.equals(r3)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            if (r3 == 0) goto L_0x0024
            r3 = 2
            goto L_0x004d
        L_0x0039:
            java.lang.String r4 = "constraint"
            boolean r4 = r8.equals(r4)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            if (r4 == 0) goto L_0x0024
            goto L_0x004d
        L_0x0042:
            java.lang.String r3 = "guideline"
            boolean r3 = r8.equals(r3)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            if (r3 == 0) goto L_0x0024
            r3 = 3
            goto L_0x004d
        L_0x004c:
            r3 = -1
        L_0x004d:
            switch(r3) {
                case 0: goto L_0x005e;
                case 1: goto L_0x0051;
                case 2: goto L_0x0051;
                case 3: goto L_0x0051;
                default: goto L_0x0050;
            }     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
        L_0x0050:
            goto L_0x005f
        L_0x0051:
            java.util.HashMap<java.lang.Integer, androidx.constraintlayout.widget.ConstraintSet$Constraint> r3 = r10.mConstraints     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            int r4 = r1.mViewId     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r3.put(r4, r1)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r1 = 0
            goto L_0x005f
        L_0x005e:
            return
        L_0x005f:
            r0 = 0
            goto L_0x01da
        L_0x0062:
            java.lang.String r8 = r12.getName()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r0 = r8
            int r8 = r0.hashCode()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            switch(r8) {
                case -2025855158: goto L_0x00cb;
                case -1984451626: goto L_0x00c1;
                case -1962203927: goto L_0x00b7;
                case -1269513683: goto L_0x00ad;
                case -1238332596: goto L_0x00a3;
                case -71750448: goto L_0x0099;
                case 366511058: goto L_0x008e;
                case 1331510167: goto L_0x0085;
                case 1791837707: goto L_0x007a;
                case 1803088381: goto L_0x0070;
                default: goto L_0x006e;
            }     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
        L_0x006e:
            goto L_0x00d5
        L_0x0070:
            java.lang.String r4 = "Constraint"
            boolean r4 = r0.equals(r4)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            if (r4 == 0) goto L_0x006e
            r4 = 0
            goto L_0x00d6
        L_0x007a:
            java.lang.String r4 = "CustomAttribute"
            boolean r4 = r0.equals(r4)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            if (r4 == 0) goto L_0x006e
            r4 = 8
            goto L_0x00d6
        L_0x0085:
            java.lang.String r5 = "Barrier"
            boolean r5 = r0.equals(r5)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            if (r5 == 0) goto L_0x006e
            goto L_0x00d6
        L_0x008e:
            java.lang.String r4 = "CustomMethod"
            boolean r4 = r0.equals(r4)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            if (r4 == 0) goto L_0x006e
            r4 = 9
            goto L_0x00d6
        L_0x0099:
            java.lang.String r4 = "Guideline"
            boolean r4 = r0.equals(r4)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            if (r4 == 0) goto L_0x006e
            r4 = 2
            goto L_0x00d6
        L_0x00a3:
            java.lang.String r4 = "Transform"
            boolean r4 = r0.equals(r4)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            if (r4 == 0) goto L_0x006e
            r4 = 5
            goto L_0x00d6
        L_0x00ad:
            java.lang.String r4 = "PropertySet"
            boolean r4 = r0.equals(r4)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            if (r4 == 0) goto L_0x006e
            r4 = 4
            goto L_0x00d6
        L_0x00b7:
            java.lang.String r4 = "ConstraintOverride"
            boolean r4 = r0.equals(r4)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            if (r4 == 0) goto L_0x006e
            r4 = 1
            goto L_0x00d6
        L_0x00c1:
            java.lang.String r4 = "Motion"
            boolean r4 = r0.equals(r4)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            if (r4 == 0) goto L_0x006e
            r4 = 7
            goto L_0x00d6
        L_0x00cb:
            java.lang.String r4 = "Layout"
            boolean r4 = r0.equals(r4)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            if (r4 == 0) goto L_0x006e
            r4 = 6
            goto L_0x00d6
        L_0x00d5:
            r4 = -1
        L_0x00d6:
            java.lang.String r5 = "XML parser error must be within a Constraint "
            switch(r4) {
                case 0: goto L_0x01ca;
                case 1: goto L_0x01c0;
                case 2: goto L_0x01ae;
                case 3: goto L_0x01a0;
                case 4: goto L_0x0179;
                case 5: goto L_0x0151;
                case 6: goto L_0x0129;
                case 7: goto L_0x0101;
                case 8: goto L_0x00dd;
                case 9: goto L_0x00dd;
                default: goto L_0x00db;
            }
        L_0x00db:
            goto L_0x01d4
        L_0x00dd:
            if (r1 == 0) goto L_0x00e6
            java.util.HashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r3 = r1.mCustomConstraints     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            androidx.constraintlayout.widget.ConstraintAttribute.parse(r11, r12, r3)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            goto L_0x01d4
        L_0x00e6:
            java.lang.RuntimeException r3 = new java.lang.RuntimeException     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r4.<init>()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            int r5 = r12.getLineNumber()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.String r4 = r4.toString()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r3.<init>(r4)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            throw r3     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
        L_0x0101:
            if (r1 == 0) goto L_0x010e
            androidx.constraintlayout.widget.ConstraintSet$Motion r3 = r1.motion     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            android.util.AttributeSet r4 = android.util.Xml.asAttributeSet(r12)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r3.fillFromAttributeList(r11, r4)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            goto L_0x01d4
        L_0x010e:
            java.lang.RuntimeException r3 = new java.lang.RuntimeException     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r4.<init>()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            int r5 = r12.getLineNumber()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.String r4 = r4.toString()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r3.<init>(r4)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            throw r3     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
        L_0x0129:
            if (r1 == 0) goto L_0x0136
            androidx.constraintlayout.widget.ConstraintSet$Layout r3 = r1.layout     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            android.util.AttributeSet r4 = android.util.Xml.asAttributeSet(r12)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r3.fillFromAttributeList(r11, r4)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            goto L_0x01d4
        L_0x0136:
            java.lang.RuntimeException r3 = new java.lang.RuntimeException     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r4.<init>()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            int r5 = r12.getLineNumber()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.String r4 = r4.toString()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r3.<init>(r4)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            throw r3     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
        L_0x0151:
            if (r1 == 0) goto L_0x015e
            androidx.constraintlayout.widget.ConstraintSet$Transform r3 = r1.transform     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            android.util.AttributeSet r4 = android.util.Xml.asAttributeSet(r12)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r3.fillFromAttributeList(r11, r4)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            goto L_0x01d4
        L_0x015e:
            java.lang.RuntimeException r3 = new java.lang.RuntimeException     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r4.<init>()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            int r5 = r12.getLineNumber()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.String r4 = r4.toString()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r3.<init>(r4)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            throw r3     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
        L_0x0179:
            if (r1 == 0) goto L_0x0185
            androidx.constraintlayout.widget.ConstraintSet$PropertySet r3 = r1.propertySet     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            android.util.AttributeSet r4 = android.util.Xml.asAttributeSet(r12)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r3.fillFromAttributeList(r11, r4)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            goto L_0x01d4
        L_0x0185:
            java.lang.RuntimeException r3 = new java.lang.RuntimeException     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r4.<init>()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            int r5 = r12.getLineNumber()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            java.lang.String r4 = r4.toString()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r3.<init>(r4)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            throw r3     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
        L_0x01a0:
            android.util.AttributeSet r4 = android.util.Xml.asAttributeSet(r12)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            androidx.constraintlayout.widget.ConstraintSet$Constraint r4 = r10.fillFromAttributeList(r11, r4, r7)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r1 = r4
            androidx.constraintlayout.widget.ConstraintSet$Layout r4 = r1.layout     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r4.mHelperType = r3     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            goto L_0x01d4
        L_0x01ae:
            android.util.AttributeSet r4 = android.util.Xml.asAttributeSet(r12)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            androidx.constraintlayout.widget.ConstraintSet$Constraint r4 = r10.fillFromAttributeList(r11, r4, r7)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r1 = r4
            androidx.constraintlayout.widget.ConstraintSet$Layout r4 = r1.layout     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r4.mIsGuideline = r3     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            androidx.constraintlayout.widget.ConstraintSet$Layout r4 = r1.layout     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r4.mApply = r3     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            goto L_0x01d4
        L_0x01c0:
            android.util.AttributeSet r4 = android.util.Xml.asAttributeSet(r12)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            androidx.constraintlayout.widget.ConstraintSet$Constraint r3 = r10.fillFromAttributeList(r11, r4, r3)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r1 = r3
            goto L_0x01d4
        L_0x01ca:
            android.util.AttributeSet r3 = android.util.Xml.asAttributeSet(r12)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            androidx.constraintlayout.widget.ConstraintSet$Constraint r3 = r10.fillFromAttributeList(r11, r3, r7)     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r1 = r3
        L_0x01d4:
            goto L_0x01da
        L_0x01d5:
            java.lang.String r3 = r12.getName()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
        L_0x01da:
            int r3 = r12.next()     // Catch:{ XmlPullParserException -> 0x01e7, IOException -> 0x01e2 }
            r2 = r3
            goto L_0x0006
        L_0x01e1:
            goto L_0x01eb
        L_0x01e2:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x01ec
        L_0x01e7:
            r1 = move-exception
            r1.printStackTrace()
        L_0x01eb:
        L_0x01ec:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.ConstraintSet.load(android.content.Context, org.xmlpull.v1.XmlPullParser):void");
    }

    /* access modifiers changed from: private */
    public static int lookupID(TypedArray a, int index, int def) {
        int ret = a.getResourceId(index, def);
        if (ret == -1) {
            return a.getInt(index, -1);
        }
        return ret;
    }

    private Constraint fillFromAttributeList(Context context, AttributeSet attrs, boolean override) {
        Constraint c = new Constraint();
        TypedArray a = context.obtainStyledAttributes(attrs, override ? C0657R.styleable.ConstraintOverride : C0657R.styleable.Constraint);
        populateConstraint(context, c, a, override);
        a.recycle();
        return c;
    }

    public static Constraint buildDelta(Context context, XmlPullParser parser) {
        AttributeSet attrs = Xml.asAttributeSet(parser);
        Constraint c = new Constraint();
        TypedArray a = context.obtainStyledAttributes(attrs, C0657R.styleable.ConstraintOverride);
        populateOverride(context, c, a);
        a.recycle();
        return c;
    }

    private static void populateOverride(Context ctx, Constraint c, TypedArray a) {
        Constraint constraint = c;
        TypedArray typedArray = a;
        int N = a.getIndexCount();
        Constraint.Delta delta = new Constraint.Delta();
        constraint.mDelta = delta;
        constraint.motion.mApply = false;
        constraint.layout.mApply = false;
        constraint.propertySet.mApply = false;
        constraint.transform.mApply = false;
        for (int i = 0; i < N; i++) {
            int attr = typedArray.getIndex(i);
            switch (overrideMapToConstant.get(attr)) {
                case 2:
                    delta.add(2, typedArray.getDimensionPixelSize(attr, constraint.layout.bottomMargin));
                    break;
                case 5:
                    delta.add(5, typedArray.getString(attr));
                    break;
                case 6:
                    delta.add(6, typedArray.getDimensionPixelOffset(attr, constraint.layout.editorAbsoluteX));
                    break;
                case 7:
                    delta.add(7, typedArray.getDimensionPixelOffset(attr, constraint.layout.editorAbsoluteY));
                    break;
                case 8:
                    delta.add(8, typedArray.getDimensionPixelSize(attr, constraint.layout.endMargin));
                    break;
                case 11:
                    delta.add(11, typedArray.getDimensionPixelSize(attr, constraint.layout.goneBottomMargin));
                    break;
                case 12:
                    delta.add(12, typedArray.getDimensionPixelSize(attr, constraint.layout.goneEndMargin));
                    break;
                case 13:
                    delta.add(13, typedArray.getDimensionPixelSize(attr, constraint.layout.goneLeftMargin));
                    break;
                case 14:
                    delta.add(14, typedArray.getDimensionPixelSize(attr, constraint.layout.goneRightMargin));
                    break;
                case 15:
                    delta.add(15, typedArray.getDimensionPixelSize(attr, constraint.layout.goneStartMargin));
                    break;
                case 16:
                    delta.add(16, typedArray.getDimensionPixelSize(attr, constraint.layout.goneTopMargin));
                    break;
                case 17:
                    delta.add(17, typedArray.getDimensionPixelOffset(attr, constraint.layout.guideBegin));
                    break;
                case 18:
                    delta.add(18, typedArray.getDimensionPixelOffset(attr, constraint.layout.guideEnd));
                    break;
                case 19:
                    delta.add(19, typedArray.getFloat(attr, constraint.layout.guidePercent));
                    break;
                case 20:
                    delta.add(20, typedArray.getFloat(attr, constraint.layout.horizontalBias));
                    break;
                case 21:
                    delta.add(21, typedArray.getLayoutDimension(attr, constraint.layout.mHeight));
                    break;
                case 22:
                    delta.add(22, VISIBILITY_FLAGS[typedArray.getInt(attr, constraint.propertySet.visibility)]);
                    break;
                case 23:
                    delta.add(23, typedArray.getLayoutDimension(attr, constraint.layout.mWidth));
                    break;
                case 24:
                    delta.add(24, typedArray.getDimensionPixelSize(attr, constraint.layout.leftMargin));
                    break;
                case 27:
                    delta.add(27, typedArray.getInt(attr, constraint.layout.orientation));
                    break;
                case 28:
                    delta.add(28, typedArray.getDimensionPixelSize(attr, constraint.layout.rightMargin));
                    break;
                case 31:
                    delta.add(31, typedArray.getDimensionPixelSize(attr, constraint.layout.startMargin));
                    break;
                case 34:
                    delta.add(34, typedArray.getDimensionPixelSize(attr, constraint.layout.topMargin));
                    break;
                case 37:
                    delta.add(37, typedArray.getFloat(attr, constraint.layout.verticalBias));
                    break;
                case 38:
                    constraint.mViewId = typedArray.getResourceId(attr, constraint.mViewId);
                    delta.add(38, constraint.mViewId);
                    break;
                case 39:
                    delta.add(39, typedArray.getFloat(attr, constraint.layout.horizontalWeight));
                    break;
                case 40:
                    delta.add(40, typedArray.getFloat(attr, constraint.layout.verticalWeight));
                    break;
                case 41:
                    delta.add(41, typedArray.getInt(attr, constraint.layout.horizontalChainStyle));
                    break;
                case 42:
                    delta.add(42, typedArray.getInt(attr, constraint.layout.verticalChainStyle));
                    break;
                case 43:
                    delta.add(43, typedArray.getFloat(attr, constraint.propertySet.alpha));
                    break;
                case 44:
                    if (Build.VERSION.SDK_INT < 21) {
                        break;
                    } else {
                        delta.add(44, true);
                        delta.add(44, typedArray.getDimension(attr, constraint.transform.elevation));
                        break;
                    }
                case 45:
                    delta.add(45, typedArray.getFloat(attr, constraint.transform.rotationX));
                    break;
                case 46:
                    delta.add(46, typedArray.getFloat(attr, constraint.transform.rotationY));
                    break;
                case 47:
                    delta.add(47, typedArray.getFloat(attr, constraint.transform.scaleX));
                    break;
                case 48:
                    delta.add(48, typedArray.getFloat(attr, constraint.transform.scaleY));
                    break;
                case 49:
                    delta.add(49, typedArray.getDimension(attr, constraint.transform.transformPivotX));
                    break;
                case 50:
                    delta.add(50, typedArray.getDimension(attr, constraint.transform.transformPivotY));
                    break;
                case 51:
                    delta.add(51, typedArray.getDimension(attr, constraint.transform.translationX));
                    break;
                case 52:
                    delta.add(52, typedArray.getDimension(attr, constraint.transform.translationY));
                    break;
                case 53:
                    if (Build.VERSION.SDK_INT < 21) {
                        break;
                    } else {
                        delta.add(53, typedArray.getDimension(attr, constraint.transform.translationZ));
                        break;
                    }
                case 54:
                    delta.add(54, typedArray.getInt(attr, constraint.layout.widthDefault));
                    break;
                case 55:
                    delta.add(55, typedArray.getInt(attr, constraint.layout.heightDefault));
                    break;
                case 56:
                    delta.add(56, typedArray.getDimensionPixelSize(attr, constraint.layout.widthMax));
                    break;
                case 57:
                    delta.add(57, typedArray.getDimensionPixelSize(attr, constraint.layout.heightMax));
                    break;
                case 58:
                    delta.add(58, typedArray.getDimensionPixelSize(attr, constraint.layout.widthMin));
                    break;
                case 59:
                    delta.add(59, typedArray.getDimensionPixelSize(attr, constraint.layout.heightMin));
                    break;
                case 60:
                    delta.add(60, typedArray.getFloat(attr, constraint.transform.rotation));
                    break;
                case 62:
                    delta.add(62, typedArray.getDimensionPixelSize(attr, constraint.layout.circleRadius));
                    break;
                case 63:
                    delta.add(63, typedArray.getFloat(attr, constraint.layout.circleAngle));
                    break;
                case 64:
                    delta.add(64, lookupID(typedArray, attr, constraint.motion.mAnimateRelativeTo));
                    break;
                case 65:
                    if (typedArray.peekValue(attr).type != 3) {
                        delta.add(65, Easing.NAMED_EASING[typedArray.getInteger(attr, 0)]);
                        break;
                    } else {
                        delta.add(65, typedArray.getString(attr));
                        break;
                    }
                case 66:
                    delta.add(66, typedArray.getInt(attr, 0));
                    break;
                case 67:
                    delta.add(67, typedArray.getFloat(attr, constraint.motion.mPathRotate));
                    break;
                case 68:
                    delta.add(68, typedArray.getFloat(attr, constraint.propertySet.mProgress));
                    break;
                case 69:
                    delta.add(69, typedArray.getFloat(attr, 1.0f));
                    break;
                case 70:
                    delta.add(70, typedArray.getFloat(attr, 1.0f));
                    break;
                case 71:
                    Log.e(TAG, "CURRENTLY UNSUPPORTED");
                    break;
                case 72:
                    delta.add(72, typedArray.getInt(attr, constraint.layout.mBarrierDirection));
                    break;
                case 73:
                    delta.add(73, typedArray.getDimensionPixelSize(attr, constraint.layout.mBarrierMargin));
                    break;
                case 74:
                    delta.add(74, typedArray.getString(attr));
                    break;
                case 75:
                    delta.add(75, typedArray.getBoolean(attr, constraint.layout.mBarrierAllowsGoneWidgets));
                    break;
                case 76:
                    delta.add(76, typedArray.getInt(attr, constraint.motion.mPathMotionArc));
                    break;
                case 77:
                    delta.add(77, typedArray.getString(attr));
                    break;
                case 78:
                    delta.add(78, typedArray.getInt(attr, constraint.propertySet.mVisibilityMode));
                    break;
                case 79:
                    delta.add(79, typedArray.getFloat(attr, constraint.motion.mMotionStagger));
                    break;
                case 80:
                    delta.add(80, typedArray.getBoolean(attr, constraint.layout.constrainedWidth));
                    break;
                case 81:
                    delta.add(81, typedArray.getBoolean(attr, constraint.layout.constrainedHeight));
                    break;
                case 82:
                    delta.add(82, typedArray.getInteger(attr, constraint.motion.mAnimateCircleAngleTo));
                    break;
                case 83:
                    delta.add(83, lookupID(typedArray, attr, constraint.transform.transformPivotTarget));
                    break;
                case 84:
                    delta.add(84, typedArray.getInteger(attr, constraint.motion.mQuantizeMotionSteps));
                    break;
                case 85:
                    delta.add(85, typedArray.getFloat(attr, constraint.motion.mQuantizeMotionPhase));
                    break;
                case 86:
                    TypedValue type = typedArray.peekValue(attr);
                    if (type.type != 1) {
                        if (type.type != 3) {
                            constraint.motion.mQuantizeInterpolatorType = typedArray.getInteger(attr, constraint.motion.mQuantizeInterpolatorID);
                            delta.add(88, constraint.motion.mQuantizeInterpolatorType);
                            break;
                        } else {
                            constraint.motion.mQuantizeInterpolatorString = typedArray.getString(attr);
                            delta.add(90, constraint.motion.mQuantizeInterpolatorString);
                            if (constraint.motion.mQuantizeInterpolatorString.indexOf("/") <= 0) {
                                constraint.motion.mQuantizeInterpolatorType = -1;
                                delta.add(88, constraint.motion.mQuantizeInterpolatorType);
                                break;
                            } else {
                                constraint.motion.mQuantizeInterpolatorID = typedArray.getResourceId(attr, -1);
                                delta.add(89, constraint.motion.mQuantizeInterpolatorID);
                                constraint.motion.mQuantizeInterpolatorType = -2;
                                delta.add(88, constraint.motion.mQuantizeInterpolatorType);
                                break;
                            }
                        }
                    } else {
                        constraint.motion.mQuantizeInterpolatorID = typedArray.getResourceId(attr, -1);
                        delta.add(89, constraint.motion.mQuantizeInterpolatorID);
                        if (constraint.motion.mQuantizeInterpolatorID == -1) {
                            break;
                        } else {
                            constraint.motion.mQuantizeInterpolatorType = -2;
                            delta.add(88, constraint.motion.mQuantizeInterpolatorType);
                            break;
                        }
                    }
                case 87:
                    Log.w(TAG, "unused attribute 0x" + Integer.toHexString(attr) + "   " + mapToConstant.get(attr));
                    break;
                case 93:
                    delta.add(93, typedArray.getDimensionPixelSize(attr, constraint.layout.baselineMargin));
                    break;
                case 94:
                    delta.add(94, typedArray.getDimensionPixelSize(attr, constraint.layout.goneBaselineMargin));
                    break;
                case 95:
                    parseDimensionConstraints(delta, typedArray, attr, 0);
                    break;
                case 96:
                    parseDimensionConstraints(delta, typedArray, attr, 1);
                    break;
                case 97:
                    delta.add(97, typedArray.getInt(attr, constraint.layout.mWrapBehavior));
                    break;
                case 98:
                    if (!MotionLayout.IS_IN_EDIT_MODE) {
                        if (typedArray.peekValue(attr).type != 3) {
                            constraint.mViewId = typedArray.getResourceId(attr, constraint.mViewId);
                            break;
                        } else {
                            constraint.mTargetString = typedArray.getString(attr);
                            break;
                        }
                    } else {
                        constraint.mViewId = typedArray.getResourceId(attr, constraint.mViewId);
                        if (constraint.mViewId != -1) {
                            break;
                        } else {
                            constraint.mTargetString = typedArray.getString(attr);
                            break;
                        }
                    }
                case 99:
                    delta.add(99, typedArray.getBoolean(attr, constraint.layout.guidelineUseRtl));
                    break;
                default:
                    Log.w(TAG, "Unknown attribute 0x" + Integer.toHexString(attr) + "   " + mapToConstant.get(attr));
                    break;
            }
        }
    }

    /* access modifiers changed from: private */
    public static void setDeltaValue(Constraint c, int type, float value) {
        switch (type) {
            case 19:
                c.layout.guidePercent = value;
                return;
            case 20:
                c.layout.horizontalBias = value;
                return;
            case 37:
                c.layout.verticalBias = value;
                return;
            case 39:
                c.layout.horizontalWeight = value;
                return;
            case 40:
                c.layout.verticalWeight = value;
                return;
            case 43:
                c.propertySet.alpha = value;
                return;
            case 44:
                c.transform.elevation = value;
                c.transform.applyElevation = true;
                return;
            case 45:
                c.transform.rotationX = value;
                return;
            case 46:
                c.transform.rotationY = value;
                return;
            case 47:
                c.transform.scaleX = value;
                return;
            case 48:
                c.transform.scaleY = value;
                return;
            case 49:
                c.transform.transformPivotX = value;
                return;
            case 50:
                c.transform.transformPivotY = value;
                return;
            case 51:
                c.transform.translationX = value;
                return;
            case 52:
                c.transform.translationY = value;
                return;
            case 53:
                c.transform.translationZ = value;
                return;
            case 60:
                c.transform.rotation = value;
                return;
            case 63:
                c.layout.circleAngle = value;
                return;
            case 67:
                c.motion.mPathRotate = value;
                return;
            case 68:
                c.propertySet.mProgress = value;
                return;
            case 69:
                c.layout.widthPercent = value;
                return;
            case 70:
                c.layout.heightPercent = value;
                return;
            case 79:
                c.motion.mMotionStagger = value;
                return;
            case 85:
                c.motion.mQuantizeMotionPhase = value;
                return;
            case 87:
                return;
            default:
                Log.w(TAG, "Unknown attribute 0x");
                return;
        }
    }

    /* access modifiers changed from: private */
    public static void setDeltaValue(Constraint c, int type, int value) {
        switch (type) {
            case 2:
                c.layout.bottomMargin = value;
                return;
            case 6:
                c.layout.editorAbsoluteX = value;
                return;
            case 7:
                c.layout.editorAbsoluteY = value;
                return;
            case 8:
                c.layout.endMargin = value;
                return;
            case 11:
                c.layout.goneBottomMargin = value;
                return;
            case 12:
                c.layout.goneEndMargin = value;
                return;
            case 13:
                c.layout.goneLeftMargin = value;
                return;
            case 14:
                c.layout.goneRightMargin = value;
                return;
            case 15:
                c.layout.goneStartMargin = value;
                return;
            case 16:
                c.layout.goneTopMargin = value;
                return;
            case 17:
                c.layout.guideBegin = value;
                return;
            case 18:
                c.layout.guideEnd = value;
                return;
            case 21:
                c.layout.mHeight = value;
                return;
            case 22:
                c.propertySet.visibility = value;
                return;
            case 23:
                c.layout.mWidth = value;
                return;
            case 24:
                c.layout.leftMargin = value;
                return;
            case 27:
                c.layout.orientation = value;
                return;
            case 28:
                c.layout.rightMargin = value;
                return;
            case 31:
                c.layout.startMargin = value;
                return;
            case 34:
                c.layout.topMargin = value;
                return;
            case 38:
                c.mViewId = value;
                return;
            case 41:
                c.layout.horizontalChainStyle = value;
                return;
            case 42:
                c.layout.verticalChainStyle = value;
                return;
            case 54:
                c.layout.widthDefault = value;
                return;
            case 55:
                c.layout.heightDefault = value;
                return;
            case 56:
                c.layout.widthMax = value;
                return;
            case 57:
                c.layout.heightMax = value;
                return;
            case 58:
                c.layout.widthMin = value;
                return;
            case 59:
                c.layout.heightMin = value;
                return;
            case 61:
                c.layout.circleConstraint = value;
                return;
            case 62:
                c.layout.circleRadius = value;
                return;
            case 64:
                c.motion.mAnimateRelativeTo = value;
                return;
            case 66:
                c.motion.mDrawPath = value;
                return;
            case 72:
                c.layout.mBarrierDirection = value;
                return;
            case 73:
                c.layout.mBarrierMargin = value;
                return;
            case 76:
                c.motion.mPathMotionArc = value;
                return;
            case 78:
                c.propertySet.mVisibilityMode = value;
                return;
            case 82:
                c.motion.mAnimateCircleAngleTo = value;
                return;
            case 83:
                c.transform.transformPivotTarget = value;
                return;
            case 84:
                c.motion.mQuantizeMotionSteps = value;
                return;
            case 87:
                return;
            case 88:
                c.motion.mQuantizeInterpolatorType = value;
                return;
            case 89:
                c.motion.mQuantizeInterpolatorID = value;
                return;
            case 93:
                c.layout.baselineMargin = value;
                return;
            case 94:
                c.layout.goneBaselineMargin = value;
                return;
            case 97:
                c.layout.mWrapBehavior = value;
                return;
            default:
                Log.w(TAG, "Unknown attribute 0x");
                return;
        }
    }

    /* access modifiers changed from: private */
    public static void setDeltaValue(Constraint c, int type, String value) {
        switch (type) {
            case 5:
                c.layout.dimensionRatio = value;
                return;
            case 65:
                c.motion.mTransitionEasing = value;
                return;
            case 74:
                c.layout.mReferenceIdString = value;
                c.layout.mReferenceIds = null;
                return;
            case 77:
                c.layout.mConstraintTag = value;
                return;
            case 87:
                return;
            case 90:
                c.motion.mQuantizeInterpolatorString = value;
                return;
            default:
                Log.w(TAG, "Unknown attribute 0x");
                return;
        }
    }

    /* access modifiers changed from: private */
    public static void setDeltaValue(Constraint c, int type, boolean value) {
        switch (type) {
            case 44:
                c.transform.applyElevation = value;
                return;
            case 75:
                c.layout.mBarrierAllowsGoneWidgets = value;
                return;
            case 80:
                c.layout.constrainedWidth = value;
                return;
            case 81:
                c.layout.constrainedHeight = value;
                return;
            case 87:
                return;
            default:
                Log.w(TAG, "Unknown attribute 0x");
                return;
        }
    }

    private void populateConstraint(Context ctx, Constraint c, TypedArray a, boolean override) {
        if (override) {
            populateOverride(ctx, c, a);
            return;
        }
        int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            if (!(attr == C0657R.styleable.Constraint_android_id || C0657R.styleable.Constraint_android_layout_marginStart == attr || C0657R.styleable.Constraint_android_layout_marginEnd == attr)) {
                c.motion.mApply = true;
                c.layout.mApply = true;
                c.propertySet.mApply = true;
                c.transform.mApply = true;
            }
            switch (mapToConstant.get(attr)) {
                case 1:
                    c.layout.baselineToBaseline = lookupID(a, attr, c.layout.baselineToBaseline);
                    break;
                case 2:
                    c.layout.bottomMargin = a.getDimensionPixelSize(attr, c.layout.bottomMargin);
                    break;
                case 3:
                    c.layout.bottomToBottom = lookupID(a, attr, c.layout.bottomToBottom);
                    break;
                case 4:
                    c.layout.bottomToTop = lookupID(a, attr, c.layout.bottomToTop);
                    break;
                case 5:
                    c.layout.dimensionRatio = a.getString(attr);
                    break;
                case 6:
                    c.layout.editorAbsoluteX = a.getDimensionPixelOffset(attr, c.layout.editorAbsoluteX);
                    break;
                case 7:
                    c.layout.editorAbsoluteY = a.getDimensionPixelOffset(attr, c.layout.editorAbsoluteY);
                    break;
                case 8:
                    c.layout.endMargin = a.getDimensionPixelSize(attr, c.layout.endMargin);
                    break;
                case 9:
                    c.layout.endToEnd = lookupID(a, attr, c.layout.endToEnd);
                    break;
                case 10:
                    c.layout.endToStart = lookupID(a, attr, c.layout.endToStart);
                    break;
                case 11:
                    c.layout.goneBottomMargin = a.getDimensionPixelSize(attr, c.layout.goneBottomMargin);
                    break;
                case 12:
                    c.layout.goneEndMargin = a.getDimensionPixelSize(attr, c.layout.goneEndMargin);
                    break;
                case 13:
                    c.layout.goneLeftMargin = a.getDimensionPixelSize(attr, c.layout.goneLeftMargin);
                    break;
                case 14:
                    c.layout.goneRightMargin = a.getDimensionPixelSize(attr, c.layout.goneRightMargin);
                    break;
                case 15:
                    c.layout.goneStartMargin = a.getDimensionPixelSize(attr, c.layout.goneStartMargin);
                    break;
                case 16:
                    c.layout.goneTopMargin = a.getDimensionPixelSize(attr, c.layout.goneTopMargin);
                    break;
                case 17:
                    c.layout.guideBegin = a.getDimensionPixelOffset(attr, c.layout.guideBegin);
                    break;
                case 18:
                    c.layout.guideEnd = a.getDimensionPixelOffset(attr, c.layout.guideEnd);
                    break;
                case 19:
                    c.layout.guidePercent = a.getFloat(attr, c.layout.guidePercent);
                    break;
                case 20:
                    c.layout.horizontalBias = a.getFloat(attr, c.layout.horizontalBias);
                    break;
                case 21:
                    c.layout.mHeight = a.getLayoutDimension(attr, c.layout.mHeight);
                    break;
                case 22:
                    c.propertySet.visibility = a.getInt(attr, c.propertySet.visibility);
                    c.propertySet.visibility = VISIBILITY_FLAGS[c.propertySet.visibility];
                    break;
                case 23:
                    c.layout.mWidth = a.getLayoutDimension(attr, c.layout.mWidth);
                    break;
                case 24:
                    c.layout.leftMargin = a.getDimensionPixelSize(attr, c.layout.leftMargin);
                    break;
                case 25:
                    c.layout.leftToLeft = lookupID(a, attr, c.layout.leftToLeft);
                    break;
                case 26:
                    c.layout.leftToRight = lookupID(a, attr, c.layout.leftToRight);
                    break;
                case 27:
                    c.layout.orientation = a.getInt(attr, c.layout.orientation);
                    break;
                case 28:
                    c.layout.rightMargin = a.getDimensionPixelSize(attr, c.layout.rightMargin);
                    break;
                case 29:
                    c.layout.rightToLeft = lookupID(a, attr, c.layout.rightToLeft);
                    break;
                case 30:
                    c.layout.rightToRight = lookupID(a, attr, c.layout.rightToRight);
                    break;
                case 31:
                    c.layout.startMargin = a.getDimensionPixelSize(attr, c.layout.startMargin);
                    break;
                case 32:
                    c.layout.startToEnd = lookupID(a, attr, c.layout.startToEnd);
                    break;
                case 33:
                    c.layout.startToStart = lookupID(a, attr, c.layout.startToStart);
                    break;
                case 34:
                    c.layout.topMargin = a.getDimensionPixelSize(attr, c.layout.topMargin);
                    break;
                case 35:
                    c.layout.topToBottom = lookupID(a, attr, c.layout.topToBottom);
                    break;
                case 36:
                    c.layout.topToTop = lookupID(a, attr, c.layout.topToTop);
                    break;
                case 37:
                    c.layout.verticalBias = a.getFloat(attr, c.layout.verticalBias);
                    break;
                case 38:
                    c.mViewId = a.getResourceId(attr, c.mViewId);
                    break;
                case 39:
                    c.layout.horizontalWeight = a.getFloat(attr, c.layout.horizontalWeight);
                    break;
                case 40:
                    c.layout.verticalWeight = a.getFloat(attr, c.layout.verticalWeight);
                    break;
                case 41:
                    c.layout.horizontalChainStyle = a.getInt(attr, c.layout.horizontalChainStyle);
                    break;
                case 42:
                    c.layout.verticalChainStyle = a.getInt(attr, c.layout.verticalChainStyle);
                    break;
                case 43:
                    c.propertySet.alpha = a.getFloat(attr, c.propertySet.alpha);
                    break;
                case 44:
                    if (Build.VERSION.SDK_INT < 21) {
                        break;
                    } else {
                        c.transform.applyElevation = true;
                        c.transform.elevation = a.getDimension(attr, c.transform.elevation);
                        break;
                    }
                case 45:
                    c.transform.rotationX = a.getFloat(attr, c.transform.rotationX);
                    break;
                case 46:
                    c.transform.rotationY = a.getFloat(attr, c.transform.rotationY);
                    break;
                case 47:
                    c.transform.scaleX = a.getFloat(attr, c.transform.scaleX);
                    break;
                case 48:
                    c.transform.scaleY = a.getFloat(attr, c.transform.scaleY);
                    break;
                case 49:
                    c.transform.transformPivotX = a.getDimension(attr, c.transform.transformPivotX);
                    break;
                case 50:
                    c.transform.transformPivotY = a.getDimension(attr, c.transform.transformPivotY);
                    break;
                case 51:
                    c.transform.translationX = a.getDimension(attr, c.transform.translationX);
                    break;
                case 52:
                    c.transform.translationY = a.getDimension(attr, c.transform.translationY);
                    break;
                case 53:
                    if (Build.VERSION.SDK_INT < 21) {
                        break;
                    } else {
                        c.transform.translationZ = a.getDimension(attr, c.transform.translationZ);
                        break;
                    }
                case 54:
                    c.layout.widthDefault = a.getInt(attr, c.layout.widthDefault);
                    break;
                case 55:
                    c.layout.heightDefault = a.getInt(attr, c.layout.heightDefault);
                    break;
                case 56:
                    c.layout.widthMax = a.getDimensionPixelSize(attr, c.layout.widthMax);
                    break;
                case 57:
                    c.layout.heightMax = a.getDimensionPixelSize(attr, c.layout.heightMax);
                    break;
                case 58:
                    c.layout.widthMin = a.getDimensionPixelSize(attr, c.layout.widthMin);
                    break;
                case 59:
                    c.layout.heightMin = a.getDimensionPixelSize(attr, c.layout.heightMin);
                    break;
                case 60:
                    c.transform.rotation = a.getFloat(attr, c.transform.rotation);
                    break;
                case 61:
                    c.layout.circleConstraint = lookupID(a, attr, c.layout.circleConstraint);
                    break;
                case 62:
                    c.layout.circleRadius = a.getDimensionPixelSize(attr, c.layout.circleRadius);
                    break;
                case 63:
                    c.layout.circleAngle = a.getFloat(attr, c.layout.circleAngle);
                    break;
                case 64:
                    c.motion.mAnimateRelativeTo = lookupID(a, attr, c.motion.mAnimateRelativeTo);
                    break;
                case 65:
                    if (a.peekValue(attr).type != 3) {
                        c.motion.mTransitionEasing = Easing.NAMED_EASING[a.getInteger(attr, 0)];
                        break;
                    } else {
                        c.motion.mTransitionEasing = a.getString(attr);
                        break;
                    }
                case 66:
                    c.motion.mDrawPath = a.getInt(attr, 0);
                    break;
                case 67:
                    c.motion.mPathRotate = a.getFloat(attr, c.motion.mPathRotate);
                    break;
                case 68:
                    c.propertySet.mProgress = a.getFloat(attr, c.propertySet.mProgress);
                    break;
                case 69:
                    c.layout.widthPercent = a.getFloat(attr, 1.0f);
                    break;
                case 70:
                    c.layout.heightPercent = a.getFloat(attr, 1.0f);
                    break;
                case 71:
                    Log.e(TAG, "CURRENTLY UNSUPPORTED");
                    break;
                case 72:
                    c.layout.mBarrierDirection = a.getInt(attr, c.layout.mBarrierDirection);
                    break;
                case 73:
                    c.layout.mBarrierMargin = a.getDimensionPixelSize(attr, c.layout.mBarrierMargin);
                    break;
                case 74:
                    c.layout.mReferenceIdString = a.getString(attr);
                    break;
                case 75:
                    c.layout.mBarrierAllowsGoneWidgets = a.getBoolean(attr, c.layout.mBarrierAllowsGoneWidgets);
                    break;
                case 76:
                    c.motion.mPathMotionArc = a.getInt(attr, c.motion.mPathMotionArc);
                    break;
                case 77:
                    c.layout.mConstraintTag = a.getString(attr);
                    break;
                case 78:
                    c.propertySet.mVisibilityMode = a.getInt(attr, c.propertySet.mVisibilityMode);
                    break;
                case 79:
                    c.motion.mMotionStagger = a.getFloat(attr, c.motion.mMotionStagger);
                    break;
                case 80:
                    c.layout.constrainedWidth = a.getBoolean(attr, c.layout.constrainedWidth);
                    break;
                case 81:
                    c.layout.constrainedHeight = a.getBoolean(attr, c.layout.constrainedHeight);
                    break;
                case 82:
                    c.motion.mAnimateCircleAngleTo = a.getInteger(attr, c.motion.mAnimateCircleAngleTo);
                    break;
                case 83:
                    c.transform.transformPivotTarget = lookupID(a, attr, c.transform.transformPivotTarget);
                    break;
                case 84:
                    c.motion.mQuantizeMotionSteps = a.getInteger(attr, c.motion.mQuantizeMotionSteps);
                    break;
                case 85:
                    c.motion.mQuantizeMotionPhase = a.getFloat(attr, c.motion.mQuantizeMotionPhase);
                    break;
                case 86:
                    TypedValue type = a.peekValue(attr);
                    if (type.type != 1) {
                        if (type.type != 3) {
                            c.motion.mQuantizeInterpolatorType = a.getInteger(attr, c.motion.mQuantizeInterpolatorID);
                            break;
                        } else {
                            c.motion.mQuantizeInterpolatorString = a.getString(attr);
                            if (c.motion.mQuantizeInterpolatorString.indexOf("/") <= 0) {
                                c.motion.mQuantizeInterpolatorType = -1;
                                break;
                            } else {
                                c.motion.mQuantizeInterpolatorID = a.getResourceId(attr, -1);
                                c.motion.mQuantizeInterpolatorType = -2;
                                break;
                            }
                        }
                    } else {
                        c.motion.mQuantizeInterpolatorID = a.getResourceId(attr, -1);
                        if (c.motion.mQuantizeInterpolatorID == -1) {
                            break;
                        } else {
                            c.motion.mQuantizeInterpolatorType = -2;
                            break;
                        }
                    }
                case 87:
                    Log.w(TAG, "unused attribute 0x" + Integer.toHexString(attr) + "   " + mapToConstant.get(attr));
                    break;
                case 91:
                    c.layout.baselineToTop = lookupID(a, attr, c.layout.baselineToTop);
                    break;
                case 92:
                    c.layout.baselineToBottom = lookupID(a, attr, c.layout.baselineToBottom);
                    break;
                case 93:
                    c.layout.baselineMargin = a.getDimensionPixelSize(attr, c.layout.baselineMargin);
                    break;
                case 94:
                    c.layout.goneBaselineMargin = a.getDimensionPixelSize(attr, c.layout.goneBaselineMargin);
                    break;
                case 95:
                    parseDimensionConstraints(c.layout, a, attr, 0);
                    break;
                case 96:
                    parseDimensionConstraints(c.layout, a, attr, 1);
                    break;
                case 97:
                    c.layout.mWrapBehavior = a.getInt(attr, c.layout.mWrapBehavior);
                    break;
                default:
                    Log.w(TAG, "Unknown attribute 0x" + Integer.toHexString(attr) + "   " + mapToConstant.get(attr));
                    break;
            }
        }
        if (c.layout.mReferenceIdString != null) {
            c.layout.mReferenceIds = null;
        }
    }

    private int[] convertReferenceString(View view, String referenceIdString) {
        Object value;
        String[] split = referenceIdString.split(",");
        Context context = view.getContext();
        int[] tags = new int[split.length];
        int count = 0;
        int i = 0;
        while (i < split.length) {
            String idString = split[i].trim();
            int tag = 0;
            try {
                tag = C0657R.C0660id.class.getField(idString).getInt((Object) null);
            } catch (Exception e) {
            }
            if (tag == 0) {
                tag = context.getResources().getIdentifier(idString, "id", context.getPackageName());
            }
            if (tag == 0 && view.isInEditMode() && (view.getParent() instanceof ConstraintLayout) && (value = ((ConstraintLayout) view.getParent()).getDesignInformation(0, idString)) != null && (value instanceof Integer)) {
                tag = ((Integer) value).intValue();
            }
            tags[count] = tag;
            i++;
            count++;
        }
        if (count != split.length) {
            return Arrays.copyOf(tags, count);
        }
        return tags;
    }

    public Constraint getConstraint(int id) {
        if (this.mConstraints.containsKey(Integer.valueOf(id))) {
            return this.mConstraints.get(Integer.valueOf(id));
        }
        return null;
    }

    public int[] getKnownIds() {
        Integer[] arr = (Integer[]) this.mConstraints.keySet().toArray(new Integer[0]);
        int[] array = new int[arr.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = arr[i].intValue();
        }
        return array;
    }

    public boolean isForceId() {
        return this.mForceId;
    }

    public void setForceId(boolean forceId) {
        this.mForceId = forceId;
    }

    public void setValidateOnParse(boolean validate) {
        this.mValidate = validate;
    }

    public void dump(MotionScene scene, int... ids) {
        HashSet<Integer> set;
        Set<Integer> keys = this.mConstraints.keySet();
        if (ids.length != 0) {
            set = new HashSet<>();
            for (int id : ids) {
                set.add(Integer.valueOf(id));
            }
        } else {
            set = new HashSet<>(keys);
        }
        System.out.println(set.size() + " constraints");
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer id2 : (Integer[]) set.toArray(new Integer[0])) {
            Constraint constraint = this.mConstraints.get(id2);
            if (constraint != null) {
                stringBuilder.append("<Constraint id=");
                stringBuilder.append(id2);
                stringBuilder.append(" \n");
                constraint.layout.dump(scene, stringBuilder);
                stringBuilder.append("/>\n");
            }
        }
        System.out.println(stringBuilder.toString());
    }

    static String getLine(Context context, int resourceId, XmlPullParser pullParser) {
        return ".(" + Debug.getName(context, resourceId) + ".xml:" + pullParser.getLineNumber() + ") \"" + pullParser.getName() + "\"";
    }

    static String getDebugName(int v) {
        for (Field field : ConstraintSet.class.getDeclaredFields()) {
            if (field.getName().contains("_") && field.getType() == Integer.TYPE && Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers())) {
                try {
                    if (field.getInt((Object) null) == v) {
                        return field.getName();
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return "UNKNOWN";
    }

    public void writeState(Writer writer, ConstraintLayout layout, int flags) throws IOException {
        writer.write("\n---------------------------------------------\n");
        if ((flags & 1) == 1) {
            new WriteXmlEngine(writer, layout, flags).writeLayout();
        } else {
            new WriteJsonEngine(writer, layout, flags).writeLayout();
        }
        writer.write("\n---------------------------------------------\n");
    }

    class WriteXmlEngine {
        private static final String SPACE = "\n       ";
        final String BASELINE = "'baseline'";
        final String BOTTOM = "'bottom'";
        final String END = "'end'";
        final String LEFT = "'left'";
        final String RIGHT = "'right'";
        final String START = "'start'";
        final String TOP = "'top'";
        Context context;
        int flags;
        HashMap<Integer, String> idMap = new HashMap<>();
        ConstraintLayout layout;
        int unknownCount = 0;
        Writer writer;

        WriteXmlEngine(Writer writer2, ConstraintLayout layout2, int flags2) throws IOException {
            this.writer = writer2;
            this.layout = layout2;
            this.context = layout2.getContext();
            this.flags = flags2;
        }

        /* access modifiers changed from: package-private */
        public void writeLayout() throws IOException {
            this.writer.write("\n<ConstraintSet>\n");
            for (Integer id : ConstraintSet.this.mConstraints.keySet()) {
                String idName = getName(id.intValue());
                this.writer.write("  <Constraint");
                this.writer.write("\n       android:id=\"" + idName + "\"");
                Layout l = ((Constraint) ConstraintSet.this.mConstraints.get(id)).layout;
                writeBaseDimension("android:layout_width", l.mWidth, -5);
                writeBaseDimension("android:layout_height", l.mHeight, -5);
                writeVariable("app:layout_constraintGuide_begin", (float) l.guideBegin, -1.0f);
                writeVariable("app:layout_constraintGuide_end", (float) l.guideEnd, -1.0f);
                writeVariable("app:layout_constraintGuide_percent", l.guidePercent, -1.0f);
                writeVariable("app:layout_constraintHorizontal_bias", l.horizontalBias, 0.5f);
                writeVariable("app:layout_constraintVertical_bias", l.verticalBias, 0.5f);
                writeVariable("app:layout_constraintDimensionRatio", l.dimensionRatio, (String) null);
                writeXmlConstraint("app:layout_constraintCircle", l.circleConstraint);
                writeVariable("app:layout_constraintCircleRadius", (float) l.circleRadius, 0.0f);
                writeVariable("app:layout_constraintCircleAngle", l.circleAngle, 0.0f);
                writeVariable("android:orientation", (float) l.orientation, -1.0f);
                writeVariable("app:layout_constraintVertical_weight", l.verticalWeight, -1.0f);
                writeVariable("app:layout_constraintHorizontal_weight", l.horizontalWeight, -1.0f);
                writeVariable("app:layout_constraintHorizontal_chainStyle", (float) l.horizontalChainStyle, 0.0f);
                writeVariable("app:layout_constraintVertical_chainStyle", (float) l.verticalChainStyle, 0.0f);
                writeVariable("app:barrierDirection", (float) l.mBarrierDirection, -1.0f);
                writeVariable("app:barrierMargin", (float) l.mBarrierMargin, 0.0f);
                writeDimension("app:layout_marginLeft", l.leftMargin, 0);
                writeDimension("app:layout_goneMarginLeft", l.goneLeftMargin, Integer.MIN_VALUE);
                writeDimension("app:layout_marginRight", l.rightMargin, 0);
                writeDimension("app:layout_goneMarginRight", l.goneRightMargin, Integer.MIN_VALUE);
                writeDimension("app:layout_marginStart", l.startMargin, 0);
                writeDimension("app:layout_goneMarginStart", l.goneStartMargin, Integer.MIN_VALUE);
                writeDimension("app:layout_marginEnd", l.endMargin, 0);
                writeDimension("app:layout_goneMarginEnd", l.goneEndMargin, Integer.MIN_VALUE);
                writeDimension("app:layout_marginTop", l.topMargin, 0);
                writeDimension("app:layout_goneMarginTop", l.goneTopMargin, Integer.MIN_VALUE);
                writeDimension("app:layout_marginBottom", l.bottomMargin, 0);
                writeDimension("app:layout_goneMarginBottom", l.goneBottomMargin, Integer.MIN_VALUE);
                writeDimension("app:goneBaselineMargin", l.goneBaselineMargin, Integer.MIN_VALUE);
                writeDimension("app:baselineMargin", l.baselineMargin, 0);
                writeBoolen("app:layout_constrainedWidth", l.constrainedWidth, false);
                writeBoolen("app:layout_constrainedHeight", l.constrainedHeight, false);
                writeBoolen("app:barrierAllowsGoneWidgets", l.mBarrierAllowsGoneWidgets, true);
                writeVariable("app:layout_wrapBehaviorInParent", (float) l.mWrapBehavior, 0.0f);
                writeXmlConstraint("app:baselineToBaseline", l.baselineToBaseline);
                writeXmlConstraint("app:baselineToBottom", l.baselineToBottom);
                writeXmlConstraint("app:baselineToTop", l.baselineToTop);
                writeXmlConstraint("app:layout_constraintBottom_toBottomOf", l.bottomToBottom);
                writeXmlConstraint("app:layout_constraintBottom_toTopOf", l.bottomToTop);
                writeXmlConstraint("app:layout_constraintEnd_toEndOf", l.endToEnd);
                writeXmlConstraint("app:layout_constraintEnd_toStartOf", l.endToStart);
                writeXmlConstraint("app:layout_constraintLeft_toLeftOf", l.leftToLeft);
                writeXmlConstraint("app:layout_constraintLeft_toRightOf", l.leftToRight);
                writeXmlConstraint("app:layout_constraintRight_toLeftOf", l.rightToLeft);
                writeXmlConstraint("app:layout_constraintRight_toRightOf", l.rightToRight);
                writeXmlConstraint("app:layout_constraintStart_toEndOf", l.startToEnd);
                writeXmlConstraint("app:layout_constraintStart_toStartOf", l.startToStart);
                writeXmlConstraint("app:layout_constraintTop_toBottomOf", l.topToBottom);
                writeXmlConstraint("app:layout_constraintTop_toTopOf", l.topToTop);
                String[] typesConstraintDefault = {"spread", "wrap", "percent"};
                writeEnum("app:layout_constraintHeight_default", l.heightDefault, typesConstraintDefault, 0);
                writeVariable("app:layout_constraintHeight_percent", l.heightPercent, 1.0f);
                writeDimension("app:layout_constraintHeight_min", l.heightMin, 0);
                writeDimension("app:layout_constraintHeight_max", l.heightMax, 0);
                writeBoolen("android:layout_constrainedHeight", l.constrainedHeight, false);
                writeEnum("app:layout_constraintWidth_default", l.widthDefault, typesConstraintDefault, 0);
                writeVariable("app:layout_constraintWidth_percent", l.widthPercent, 1.0f);
                writeDimension("app:layout_constraintWidth_min", l.widthMin, 0);
                writeDimension("app:layout_constraintWidth_max", l.widthMax, 0);
                writeBoolen("android:layout_constrainedWidth", l.constrainedWidth, false);
                writeVariable("app:layout_constraintVertical_weight", l.verticalWeight, -1.0f);
                writeVariable("app:layout_constraintHorizontal_weight", l.horizontalWeight, -1.0f);
                writeVariable("app:layout_constraintHorizontal_chainStyle", l.horizontalChainStyle);
                writeVariable("app:layout_constraintVertical_chainStyle", l.verticalChainStyle);
                writeEnum("app:barrierDirection", l.mBarrierDirection, new String[]{"left", "right", CommonCssConstants.TOP, CommonCssConstants.BOTTOM, "start", "end"}, -1);
                writeVariable("app:layout_constraintTag", l.mConstraintTag, (String) null);
                if (l.mReferenceIds != null) {
                    writeVariable("'ReferenceIds'", l.mReferenceIds);
                }
                this.writer.write(" />\n");
            }
            this.writer.write("</ConstraintSet>\n");
        }

        private void writeBoolen(String dimString, boolean val, boolean def) throws IOException {
            if (val != def) {
                this.writer.write(SPACE + dimString + "=\"" + val + "dp\"");
            }
        }

        private void writeEnum(String dimString, int val, String[] types, int def) throws IOException {
            if (val != def) {
                this.writer.write(SPACE + dimString + "=\"" + types[val] + "\"");
            }
        }

        private void writeDimension(String dimString, int dim, int def) throws IOException {
            if (dim != def) {
                this.writer.write(SPACE + dimString + "=\"" + dim + "dp\"");
            }
        }

        private void writeBaseDimension(String dimString, int dim, int def) throws IOException {
            if (dim == def) {
                return;
            }
            if (dim == -2) {
                this.writer.write(SPACE + dimString + "=\"wrap_content\"");
            } else if (dim == -1) {
                this.writer.write(SPACE + dimString + "=\"match_parent\"");
            } else {
                this.writer.write(SPACE + dimString + "=\"" + dim + "dp\"");
            }
        }

        /* access modifiers changed from: package-private */
        public String getName(int id) {
            if (this.idMap.containsKey(Integer.valueOf(id))) {
                return "@+id/" + this.idMap.get(Integer.valueOf(id)) + "";
            }
            if (id == 0) {
                return ConstraintSet.KEY_PERCENT_PARENT;
            }
            String name = lookup(id);
            this.idMap.put(Integer.valueOf(id), name);
            return "@+id/" + name + "";
        }

        /* access modifiers changed from: package-private */
        public String lookup(int id) {
            if (id != -1) {
                try {
                    return this.context.getResources().getResourceEntryName(id);
                } catch (Exception e) {
                    StringBuilder append = new StringBuilder().append(EnvironmentCompat.MEDIA_UNKNOWN);
                    int i = this.unknownCount + 1;
                    this.unknownCount = i;
                    return append.append(i).toString();
                }
            } else {
                StringBuilder append2 = new StringBuilder().append(EnvironmentCompat.MEDIA_UNKNOWN);
                int i2 = this.unknownCount + 1;
                this.unknownCount = i2;
                return append2.append(i2).toString();
            }
        }

        /* access modifiers changed from: package-private */
        public void writeXmlConstraint(String str, int leftToLeft) throws IOException {
            if (leftToLeft != -1) {
                this.writer.write(SPACE + str);
                this.writer.write("=\"" + getName(leftToLeft) + "\"");
            }
        }

        /* access modifiers changed from: package-private */
        public void writeConstraint(String my, int leftToLeft, String other, int margin, int goneMargin) throws IOException {
            if (leftToLeft != -1) {
                this.writer.write(SPACE + my);
                this.writer.write(":[");
                this.writer.write(getName(leftToLeft));
                this.writer.write(" , ");
                this.writer.write(other);
                if (margin != 0) {
                    this.writer.write(" , " + margin);
                }
                this.writer.write("],\n");
            }
        }

        /* access modifiers changed from: package-private */
        public void writeCircle(int circleConstraint, float circleAngle, int circleRadius) throws IOException {
            if (circleConstraint != -1) {
                this.writer.write("circle");
                this.writer.write(":[");
                this.writer.write(getName(circleConstraint));
                this.writer.write(", " + circleAngle);
                this.writer.write(circleRadius + "]");
            }
        }

        /* access modifiers changed from: package-private */
        public void writeVariable(String name, int value) throws IOException {
            if (value != 0 && value != -1) {
                this.writer.write(SPACE + name + "=\"" + value + "\"\n");
            }
        }

        /* access modifiers changed from: package-private */
        public void writeVariable(String name, float value, float def) throws IOException {
            if (value != def) {
                this.writer.write(SPACE + name);
                this.writer.write("=\"" + value + "\"");
            }
        }

        /* access modifiers changed from: package-private */
        public void writeVariable(String name, String value, String def) throws IOException {
            if (value != null && !value.equals(def)) {
                this.writer.write(SPACE + name);
                this.writer.write("=\"" + value + "\"");
            }
        }

        /* access modifiers changed from: package-private */
        public void writeVariable(String name, int[] value) throws IOException {
            if (value != null) {
                this.writer.write(SPACE + name);
                this.writer.write(":");
                int i = 0;
                while (i < value.length) {
                    this.writer.write((i == 0 ? "[" : ", ") + getName(value[i]));
                    i++;
                }
                this.writer.write("],\n");
            }
        }

        /* access modifiers changed from: package-private */
        public void writeVariable(String name, String value) throws IOException {
            if (value != null) {
                this.writer.write(name);
                this.writer.write(":");
                this.writer.write(", " + value);
                this.writer.write("\n");
            }
        }
    }

    class WriteJsonEngine {
        private static final String SPACE = "       ";
        final String BASELINE = "'baseline'";
        final String BOTTOM = "'bottom'";
        final String END = "'end'";
        final String LEFT = "'left'";
        final String RIGHT = "'right'";
        final String START = "'start'";
        final String TOP = "'top'";
        Context context;
        int flags;
        HashMap<Integer, String> idMap = new HashMap<>();
        ConstraintLayout layout;
        int unknownCount = 0;
        Writer writer;

        WriteJsonEngine(Writer writer2, ConstraintLayout layout2, int flags2) throws IOException {
            this.writer = writer2;
            this.layout = layout2;
            this.context = layout2.getContext();
            this.flags = flags2;
        }

        /* access modifiers changed from: package-private */
        public void writeLayout() throws IOException {
            this.writer.write("\n'ConstraintSet':{\n");
            for (Integer id : ConstraintSet.this.mConstraints.keySet()) {
                this.writer.write(getName(id.intValue()) + ":{\n");
                Layout l = ((Constraint) ConstraintSet.this.mConstraints.get(id)).layout;
                writeDimension("height", l.mHeight, l.heightDefault, l.heightPercent, l.heightMin, l.heightMax, l.constrainedHeight);
                writeDimension("width", l.mWidth, l.widthDefault, l.widthPercent, l.widthMin, l.widthMax, l.constrainedWidth);
                writeConstraint("'left'", l.leftToLeft, "'left'", l.leftMargin, l.goneLeftMargin);
                writeConstraint("'left'", l.leftToRight, "'right'", l.leftMargin, l.goneLeftMargin);
                writeConstraint("'right'", l.rightToLeft, "'left'", l.rightMargin, l.goneRightMargin);
                writeConstraint("'right'", l.rightToRight, "'right'", l.rightMargin, l.goneRightMargin);
                writeConstraint("'baseline'", l.baselineToBaseline, "'baseline'", -1, l.goneBaselineMargin);
                writeConstraint("'baseline'", l.baselineToTop, "'top'", -1, l.goneBaselineMargin);
                writeConstraint("'baseline'", l.baselineToBottom, "'bottom'", -1, l.goneBaselineMargin);
                writeConstraint("'top'", l.topToBottom, "'bottom'", l.topMargin, l.goneTopMargin);
                writeConstraint("'top'", l.topToTop, "'top'", l.topMargin, l.goneTopMargin);
                writeConstraint("'bottom'", l.bottomToBottom, "'bottom'", l.bottomMargin, l.goneBottomMargin);
                writeConstraint("'bottom'", l.bottomToTop, "'top'", l.bottomMargin, l.goneBottomMargin);
                writeConstraint("'start'", l.startToStart, "'start'", l.startMargin, l.goneStartMargin);
                writeConstraint("'start'", l.startToEnd, "'end'", l.startMargin, l.goneStartMargin);
                writeConstraint("'end'", l.endToStart, "'start'", l.endMargin, l.goneEndMargin);
                writeConstraint("'end'", l.endToEnd, "'end'", l.endMargin, l.goneEndMargin);
                writeVariable("'horizontalBias'", l.horizontalBias, 0.5f);
                writeVariable("'verticalBias'", l.verticalBias, 0.5f);
                writeCircle(l.circleConstraint, l.circleAngle, l.circleRadius);
                writeGuideline(l.orientation, l.guideBegin, l.guideEnd, l.guidePercent);
                writeVariable("'dimensionRatio'", l.dimensionRatio);
                writeVariable("'barrierMargin'", l.mBarrierMargin);
                writeVariable("'type'", l.mHelperType);
                writeVariable("'ReferenceId'", l.mReferenceIdString);
                writeVariable("'mBarrierAllowsGoneWidgets'", l.mBarrierAllowsGoneWidgets, true);
                writeVariable("'WrapBehavior'", l.mWrapBehavior);
                writeVariable("'verticalWeight'", l.verticalWeight);
                writeVariable("'horizontalWeight'", l.horizontalWeight);
                writeVariable("'horizontalChainStyle'", l.horizontalChainStyle);
                writeVariable("'verticalChainStyle'", l.verticalChainStyle);
                writeVariable("'barrierDirection'", l.mBarrierDirection);
                if (l.mReferenceIds != null) {
                    writeVariable("'ReferenceIds'", l.mReferenceIds);
                }
                this.writer.write("}\n");
            }
            this.writer.write("}\n");
        }

        private void writeGuideline(int orientation, int guideBegin, int guideEnd, float guidePercent) {
        }

        private void writeDimension(String dimString, int dim, int dimDefault, float dimPercent, int dimMin, int dimMax, boolean constrainedDim) throws IOException {
            if (dim == 0) {
                if (dimMax == -1 && dimMin == -1) {
                    switch (dimDefault) {
                        case 1:
                            this.writer.write(SPACE + dimString + ": '???????????',\n");
                            return;
                        case 2:
                            this.writer.write(SPACE + dimString + ": '" + dimPercent + "%',\n");
                            return;
                        default:
                            return;
                    }
                } else {
                    switch (dimDefault) {
                        case 0:
                            this.writer.write(SPACE + dimString + ": {'spread' ," + dimMin + ", " + dimMax + "}\n");
                            return;
                        case 1:
                            this.writer.write(SPACE + dimString + ": {'wrap' ," + dimMin + ", " + dimMax + "}\n");
                            return;
                        case 2:
                            this.writer.write(SPACE + dimString + ": {'" + dimPercent + "'% ," + dimMin + ", " + dimMax + "}\n");
                            return;
                        default:
                            return;
                    }
                }
            } else if (dim == -2) {
                this.writer.write(SPACE + dimString + ": 'wrap'\n");
            } else if (dim == -1) {
                this.writer.write(SPACE + dimString + ": 'parent'\n");
            } else {
                this.writer.write(SPACE + dimString + ": " + dim + ",\n");
            }
        }

        /* access modifiers changed from: package-private */
        public String getName(int id) {
            if (this.idMap.containsKey(Integer.valueOf(id))) {
                return "'" + this.idMap.get(Integer.valueOf(id)) + "'";
            }
            if (id == 0) {
                return "'parent'";
            }
            String name = lookup(id);
            this.idMap.put(Integer.valueOf(id), name);
            return "'" + name + "'";
        }

        /* access modifiers changed from: package-private */
        public String lookup(int id) {
            if (id != -1) {
                try {
                    return this.context.getResources().getResourceEntryName(id);
                } catch (Exception e) {
                    StringBuilder append = new StringBuilder().append(EnvironmentCompat.MEDIA_UNKNOWN);
                    int i = this.unknownCount + 1;
                    this.unknownCount = i;
                    return append.append(i).toString();
                }
            } else {
                StringBuilder append2 = new StringBuilder().append(EnvironmentCompat.MEDIA_UNKNOWN);
                int i2 = this.unknownCount + 1;
                this.unknownCount = i2;
                return append2.append(i2).toString();
            }
        }

        /* access modifiers changed from: package-private */
        public void writeConstraint(String my, int leftToLeft, String other, int margin, int goneMargin) throws IOException {
            if (leftToLeft != -1) {
                this.writer.write(SPACE + my);
                this.writer.write(":[");
                this.writer.write(getName(leftToLeft));
                this.writer.write(" , ");
                this.writer.write(other);
                if (margin != 0) {
                    this.writer.write(" , " + margin);
                }
                this.writer.write("],\n");
            }
        }

        /* access modifiers changed from: package-private */
        public void writeCircle(int circleConstraint, float circleAngle, int circleRadius) throws IOException {
            if (circleConstraint != -1) {
                this.writer.write("       circle");
                this.writer.write(":[");
                this.writer.write(getName(circleConstraint));
                this.writer.write(", " + circleAngle);
                this.writer.write(circleRadius + "]");
            }
        }

        /* access modifiers changed from: package-private */
        public void writeVariable(String name, int value) throws IOException {
            if (value != 0 && value != -1) {
                this.writer.write(SPACE + name);
                this.writer.write(":");
                this.writer.write(", " + value);
                this.writer.write("\n");
            }
        }

        /* access modifiers changed from: package-private */
        public void writeVariable(String name, float value) throws IOException {
            if (value != -1.0f) {
                this.writer.write(SPACE + name);
                this.writer.write(": " + value);
                this.writer.write(",\n");
            }
        }

        /* access modifiers changed from: package-private */
        public void writeVariable(String name, float value, float def) throws IOException {
            if (value != def) {
                this.writer.write(SPACE + name);
                this.writer.write(": " + value);
                this.writer.write(",\n");
            }
        }

        /* access modifiers changed from: package-private */
        public void writeVariable(String name, boolean value) throws IOException {
            if (value) {
                this.writer.write(SPACE + name);
                this.writer.write(": " + value);
                this.writer.write(",\n");
            }
        }

        /* access modifiers changed from: package-private */
        public void writeVariable(String name, boolean value, boolean def) throws IOException {
            if (value != def) {
                this.writer.write(SPACE + name);
                this.writer.write(": " + value);
                this.writer.write(",\n");
            }
        }

        /* access modifiers changed from: package-private */
        public void writeVariable(String name, int[] value) throws IOException {
            if (value != null) {
                this.writer.write(SPACE + name);
                this.writer.write(": ");
                int i = 0;
                while (i < value.length) {
                    this.writer.write((i == 0 ? "[" : ", ") + getName(value[i]));
                    i++;
                }
                this.writer.write("],\n");
            }
        }

        /* access modifiers changed from: package-private */
        public void writeVariable(String name, String value) throws IOException {
            if (value != null) {
                this.writer.write(SPACE + name);
                this.writer.write(":");
                this.writer.write(", " + value);
                this.writer.write("\n");
            }
        }
    }
}
