package androidx.transition;

import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ViewUtilsBase {
    private static final String TAG = "ViewUtilsBase";
    private static final int VISIBILITY_MASK = 12;
    private static boolean sSetFrameFetched;
    private static Method sSetFrameMethod;
    private static Field sViewFlagsField;
    private static boolean sViewFlagsFieldFetched;
    private float[] mMatrixValues;

    ViewUtilsBase() {
    }

    public void setTransitionAlpha(View view, float alpha) {
        Float savedAlpha = (Float) view.getTag(C1010R.C1013id.save_non_transition_alpha);
        if (savedAlpha != null) {
            view.setAlpha(savedAlpha.floatValue() * alpha);
        } else {
            view.setAlpha(alpha);
        }
    }

    public float getTransitionAlpha(View view) {
        Float savedAlpha = (Float) view.getTag(C1010R.C1013id.save_non_transition_alpha);
        if (savedAlpha != null) {
            return view.getAlpha() / savedAlpha.floatValue();
        }
        return view.getAlpha();
    }

    public void saveNonTransitionAlpha(View view) {
        if (view.getTag(C1010R.C1013id.save_non_transition_alpha) == null) {
            view.setTag(C1010R.C1013id.save_non_transition_alpha, Float.valueOf(view.getAlpha()));
        }
    }

    public void clearNonTransitionAlpha(View view) {
        if (view.getVisibility() == 0) {
            view.setTag(C1010R.C1013id.save_non_transition_alpha, (Object) null);
        }
    }

    public void transformMatrixToGlobal(View view, Matrix matrix) {
        ViewParent parent = view.getParent();
        if (parent instanceof View) {
            View vp = (View) parent;
            transformMatrixToGlobal(vp, matrix);
            matrix.preTranslate((float) (-vp.getScrollX()), (float) (-vp.getScrollY()));
        }
        matrix.preTranslate((float) view.getLeft(), (float) view.getTop());
        Matrix vm = view.getMatrix();
        if (!vm.isIdentity()) {
            matrix.preConcat(vm);
        }
    }

    public void transformMatrixToLocal(View view, Matrix matrix) {
        ViewParent parent = view.getParent();
        if (parent instanceof View) {
            View vp = (View) parent;
            transformMatrixToLocal(vp, matrix);
            matrix.postTranslate((float) vp.getScrollX(), (float) vp.getScrollY());
        }
        matrix.postTranslate((float) (-view.getLeft()), (float) (-view.getTop()));
        Matrix vm = view.getMatrix();
        if (!vm.isIdentity()) {
            Matrix inverted = new Matrix();
            if (vm.invert(inverted)) {
                matrix.postConcat(inverted);
            }
        }
    }

    public void setAnimationMatrix(View view, Matrix matrix) {
        if (matrix == null || matrix.isIdentity()) {
            view.setPivotX((float) (view.getWidth() / 2));
            view.setPivotY((float) (view.getHeight() / 2));
            view.setTranslationX(0.0f);
            view.setTranslationY(0.0f);
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
            view.setRotation(0.0f);
            return;
        }
        float[] values = this.mMatrixValues;
        if (values == null) {
            float[] fArr = new float[9];
            values = fArr;
            this.mMatrixValues = fArr;
        }
        matrix.getValues(values);
        float sin = values[3];
        float cos = ((float) Math.sqrt((double) (1.0f - (sin * sin)))) * ((float) (values[0] < 0.0f ? -1 : 1));
        float dx = values[2];
        float dy = values[5];
        view.setPivotX(0.0f);
        view.setPivotY(0.0f);
        view.setTranslationX(dx);
        view.setTranslationY(dy);
        view.setRotation((float) Math.toDegrees(Math.atan2((double) sin, (double) cos)));
        view.setScaleX(values[0] / cos);
        view.setScaleY(values[4] / cos);
    }

    public void setLeftTopRightBottom(View v, int left, int top, int right, int bottom) {
        fetchSetFrame();
        Method method = sSetFrameMethod;
        if (method != null) {
            try {
                method.invoke(v, new Object[]{Integer.valueOf(left), Integer.valueOf(top), Integer.valueOf(right), Integer.valueOf(bottom)});
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e2) {
                throw new RuntimeException(e2.getCause());
            }
        }
    }

    public void setTransitionVisibility(View view, int visibility) {
        if (!sViewFlagsFieldFetched) {
            try {
                Field declaredField = View.class.getDeclaredField("mViewFlags");
                sViewFlagsField = declaredField;
                declaredField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                Log.i(TAG, "fetchViewFlagsField: ");
            }
            sViewFlagsFieldFetched = true;
        }
        Field field = sViewFlagsField;
        if (field != null) {
            try {
                sViewFlagsField.setInt(view, (field.getInt(view) & -13) | visibility);
            } catch (IllegalAccessException e2) {
            }
        }
    }

    private void fetchSetFrame() {
        if (!sSetFrameFetched) {
            Class<View> cls = View.class;
            try {
                Method declaredMethod = cls.getDeclaredMethod("setFrame", new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE});
                sSetFrameMethod = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "Failed to retrieve setFrame method", e);
            }
            sSetFrameFetched = true;
        }
    }
}
