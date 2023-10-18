package androidx.fragment.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.C0798R;
import com.itextpdf.styledxmlparser.CommonAttributeConstants;

class FragmentLayoutInflaterFactory implements LayoutInflater.Factory2 {
    private static final String TAG = "FragmentManager";
    final FragmentManager mFragmentManager;

    FragmentLayoutInflaterFactory(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return onCreateView((View) null, name, context, attrs);
    }

    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        final FragmentStateManager fragmentStateManager;
        String str = name;
        Context context2 = context;
        AttributeSet attributeSet = attrs;
        if (FragmentContainerView.class.getName().equals(str)) {
            return new FragmentContainerView(context2, attributeSet, this.mFragmentManager);
        }
        Fragment fragment = null;
        if (!"fragment".equals(str)) {
            return null;
        }
        String fname = attributeSet.getAttributeValue((String) null, CommonAttributeConstants.CLASS);
        TypedArray a = context2.obtainStyledAttributes(attributeSet, C0798R.styleable.Fragment);
        if (fname == null) {
            fname = a.getString(C0798R.styleable.Fragment_android_name);
        }
        int id = a.getResourceId(C0798R.styleable.Fragment_android_id, -1);
        String tag = a.getString(C0798R.styleable.Fragment_android_tag);
        a.recycle();
        if (fname == null || !FragmentFactory.isFragmentClass(context.getClassLoader(), fname)) {
            return null;
        }
        int containerId = parent != null ? parent.getId() : 0;
        if (containerId == -1 && id == -1 && tag == null) {
            throw new IllegalArgumentException(attrs.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + fname);
        }
        if (id != -1) {
            fragment = this.mFragmentManager.findFragmentById(id);
        }
        if (fragment == null && tag != null) {
            fragment = this.mFragmentManager.findFragmentByTag(tag);
        }
        if (fragment == null && containerId != -1) {
            fragment = this.mFragmentManager.findFragmentById(containerId);
        }
        if (fragment == null) {
            fragment = this.mFragmentManager.getFragmentFactory().instantiate(context.getClassLoader(), fname);
            fragment.mFromLayout = true;
            fragment.mFragmentId = id != 0 ? id : containerId;
            fragment.mContainerId = containerId;
            fragment.mTag = tag;
            fragment.mInLayout = true;
            fragment.mFragmentManager = this.mFragmentManager;
            fragment.mHost = this.mFragmentManager.getHost();
            fragment.onInflate(this.mFragmentManager.getHost().getContext(), attributeSet, fragment.mSavedFragmentState);
            fragmentStateManager = this.mFragmentManager.addFragment(fragment);
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v(TAG, "Fragment " + fragment + " has been inflated via the <fragment> tag: id=0x" + Integer.toHexString(id));
            }
        } else if (!fragment.mInLayout) {
            fragment.mInLayout = true;
            fragment.mFragmentManager = this.mFragmentManager;
            fragment.mHost = this.mFragmentManager.getHost();
            fragment.onInflate(this.mFragmentManager.getHost().getContext(), attributeSet, fragment.mSavedFragmentState);
            fragmentStateManager = this.mFragmentManager.createOrGetFragmentStateManager(fragment);
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v(TAG, "Retained Fragment " + fragment + " has been re-attached via the <fragment> tag: id=0x" + Integer.toHexString(id));
            }
        } else {
            throw new IllegalArgumentException(attrs.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(id) + ", tag " + tag + ", or parent id 0x" + Integer.toHexString(containerId) + " with another fragment for " + fname);
        }
        fragment.mContainer = (ViewGroup) parent;
        fragmentStateManager.moveToExpectedState();
        fragmentStateManager.ensureInflatedView();
        if (fragment.mView != null) {
            if (id != 0) {
                fragment.mView.setId(id);
            }
            if (fragment.mView.getTag() == null) {
                fragment.mView.setTag(tag);
            }
            fragment.mView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                public void onViewAttachedToWindow(View v) {
                    Fragment fragment = fragmentStateManager.getFragment();
                    fragmentStateManager.moveToExpectedState();
                    SpecialEffectsController.getOrCreateController((ViewGroup) fragment.mView.getParent(), FragmentLayoutInflaterFactory.this.mFragmentManager).forceCompleteAllOperations();
                }

                public void onViewDetachedFromWindow(View v) {
                }
            });
            return fragment.mView;
        }
        throw new IllegalStateException("Fragment " + fname + " did not create a view.");
    }
}
