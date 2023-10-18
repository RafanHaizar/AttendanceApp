package androidx.fragment.app;

import android.util.Log;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

final class FragmentManagerViewModel extends ViewModel {
    private static final ViewModelProvider.Factory FACTORY = new ViewModelProvider.Factory() {
        public /* synthetic */ ViewModel create(Class cls, CreationExtras creationExtras) {
            return ViewModelProvider.Factory.CC.$default$create(this, cls, creationExtras);
        }

        public <T extends ViewModel> T create(Class<T> cls) {
            return new FragmentManagerViewModel(true);
        }
    };
    private static final String TAG = "FragmentManager";
    private final HashMap<String, FragmentManagerViewModel> mChildNonConfigs = new HashMap<>();
    private boolean mHasBeenCleared = false;
    private boolean mHasSavedSnapshot = false;
    private boolean mIsStateSaved = false;
    private final HashMap<String, Fragment> mRetainedFragments = new HashMap<>();
    private final boolean mStateAutomaticallySaved;
    private final HashMap<String, ViewModelStore> mViewModelStores = new HashMap<>();

    static FragmentManagerViewModel getInstance(ViewModelStore viewModelStore) {
        return (FragmentManagerViewModel) new ViewModelProvider(viewModelStore, FACTORY).get(FragmentManagerViewModel.class);
    }

    FragmentManagerViewModel(boolean stateAutomaticallySaved) {
        this.mStateAutomaticallySaved = stateAutomaticallySaved;
    }

    /* access modifiers changed from: package-private */
    public void setIsStateSaved(boolean isStateSaved) {
        this.mIsStateSaved = isStateSaved;
    }

    /* access modifiers changed from: protected */
    public void onCleared() {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(TAG, "onCleared called for " + this);
        }
        this.mHasBeenCleared = true;
    }

    /* access modifiers changed from: package-private */
    public boolean isCleared() {
        return this.mHasBeenCleared;
    }

    /* access modifiers changed from: package-private */
    public void addRetainedFragment(Fragment fragment) {
        if (this.mIsStateSaved) {
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v(TAG, "Ignoring addRetainedFragment as the state is already saved");
            }
        } else if (!this.mRetainedFragments.containsKey(fragment.mWho)) {
            this.mRetainedFragments.put(fragment.mWho, fragment);
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v(TAG, "Updating retained Fragments: Added " + fragment);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Fragment findRetainedFragmentByWho(String who) {
        return this.mRetainedFragments.get(who);
    }

    /* access modifiers changed from: package-private */
    public Collection<Fragment> getRetainedFragments() {
        return new ArrayList(this.mRetainedFragments.values());
    }

    /* access modifiers changed from: package-private */
    public boolean shouldDestroy(Fragment fragment) {
        if (!this.mRetainedFragments.containsKey(fragment.mWho)) {
            return true;
        }
        if (this.mStateAutomaticallySaved) {
            return this.mHasBeenCleared;
        }
        return !this.mHasSavedSnapshot;
    }

    /* access modifiers changed from: package-private */
    public void removeRetainedFragment(Fragment fragment) {
        if (!this.mIsStateSaved) {
            if ((this.mRetainedFragments.remove(fragment.mWho) != null) && FragmentManager.isLoggingEnabled(2)) {
                Log.v(TAG, "Updating retained Fragments: Removed " + fragment);
            }
        } else if (FragmentManager.isLoggingEnabled(2)) {
            Log.v(TAG, "Ignoring removeRetainedFragment as the state is already saved");
        }
    }

    /* access modifiers changed from: package-private */
    public FragmentManagerViewModel getChildNonConfig(Fragment f) {
        FragmentManagerViewModel childNonConfig = this.mChildNonConfigs.get(f.mWho);
        if (childNonConfig != null) {
            return childNonConfig;
        }
        FragmentManagerViewModel childNonConfig2 = new FragmentManagerViewModel(this.mStateAutomaticallySaved);
        this.mChildNonConfigs.put(f.mWho, childNonConfig2);
        return childNonConfig2;
    }

    /* access modifiers changed from: package-private */
    public ViewModelStore getViewModelStore(Fragment f) {
        ViewModelStore viewModelStore = this.mViewModelStores.get(f.mWho);
        if (viewModelStore != null) {
            return viewModelStore;
        }
        ViewModelStore viewModelStore2 = new ViewModelStore();
        this.mViewModelStores.put(f.mWho, viewModelStore2);
        return viewModelStore2;
    }

    /* access modifiers changed from: package-private */
    public void clearNonConfigState(Fragment f) {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(TAG, "Clearing non-config state for " + f);
        }
        FragmentManagerViewModel childNonConfig = this.mChildNonConfigs.get(f.mWho);
        if (childNonConfig != null) {
            childNonConfig.onCleared();
            this.mChildNonConfigs.remove(f.mWho);
        }
        ViewModelStore viewModelStore = this.mViewModelStores.get(f.mWho);
        if (viewModelStore != null) {
            viewModelStore.clear();
            this.mViewModelStores.remove(f.mWho);
        }
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public void restoreFromSnapshot(FragmentManagerNonConfig nonConfig) {
        this.mRetainedFragments.clear();
        this.mChildNonConfigs.clear();
        this.mViewModelStores.clear();
        if (nonConfig != null) {
            Collection<Fragment> fragments = nonConfig.getFragments();
            if (fragments != null) {
                for (Fragment fragment : fragments) {
                    if (fragment != null) {
                        this.mRetainedFragments.put(fragment.mWho, fragment);
                    }
                }
            }
            Map<String, FragmentManagerNonConfig> childNonConfigs = nonConfig.getChildNonConfigs();
            if (childNonConfigs != null) {
                for (Map.Entry<String, FragmentManagerNonConfig> entry : childNonConfigs.entrySet()) {
                    FragmentManagerViewModel childViewModel = new FragmentManagerViewModel(this.mStateAutomaticallySaved);
                    childViewModel.restoreFromSnapshot(entry.getValue());
                    this.mChildNonConfigs.put(entry.getKey(), childViewModel);
                }
            }
            Map<String, ViewModelStore> viewModelStores = nonConfig.getViewModelStores();
            if (viewModelStores != null) {
                this.mViewModelStores.putAll(viewModelStores);
            }
        }
        this.mHasSavedSnapshot = false;
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public FragmentManagerNonConfig getSnapshot() {
        if (this.mRetainedFragments.isEmpty() && this.mChildNonConfigs.isEmpty() && this.mViewModelStores.isEmpty()) {
            return null;
        }
        HashMap<String, FragmentManagerNonConfig> childNonConfigs = new HashMap<>();
        for (Map.Entry<String, FragmentManagerViewModel> entry : this.mChildNonConfigs.entrySet()) {
            FragmentManagerNonConfig childNonConfig = entry.getValue().getSnapshot();
            if (childNonConfig != null) {
                childNonConfigs.put(entry.getKey(), childNonConfig);
            }
        }
        this.mHasSavedSnapshot = true;
        if (!this.mRetainedFragments.isEmpty() || !childNonConfigs.isEmpty() || !this.mViewModelStores.isEmpty()) {
            return new FragmentManagerNonConfig(new ArrayList(this.mRetainedFragments.values()), childNonConfigs, new HashMap(this.mViewModelStores));
        }
        return null;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FragmentManagerViewModel that = (FragmentManagerViewModel) o;
        if (!this.mRetainedFragments.equals(that.mRetainedFragments) || !this.mChildNonConfigs.equals(that.mChildNonConfigs) || !this.mViewModelStores.equals(that.mViewModelStores)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((this.mRetainedFragments.hashCode() * 31) + this.mChildNonConfigs.hashCode()) * 31) + this.mViewModelStores.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("FragmentManagerViewModel{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append("} Fragments (");
        Iterator<Fragment> fragmentIterator = this.mRetainedFragments.values().iterator();
        while (fragmentIterator.hasNext()) {
            sb.append(fragmentIterator.next());
            if (fragmentIterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(") Child Non Config (");
        Iterator<String> childNonConfigIterator = this.mChildNonConfigs.keySet().iterator();
        while (childNonConfigIterator.hasNext()) {
            sb.append(childNonConfigIterator.next());
            if (childNonConfigIterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(") ViewModelStores (");
        Iterator<String> viewModelStoreIterator = this.mViewModelStores.keySet().iterator();
        while (viewModelStoreIterator.hasNext()) {
            sb.append(viewModelStoreIterator.next());
            if (viewModelStoreIterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(')');
        return sb.toString();
    }
}
