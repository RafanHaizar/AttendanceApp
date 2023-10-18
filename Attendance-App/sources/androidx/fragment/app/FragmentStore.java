package androidx.fragment.app;

import android.util.Log;
import android.view.ViewGroup;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

class FragmentStore {
    private static final String TAG = "FragmentManager";
    private final HashMap<String, FragmentStateManager> mActive = new HashMap<>();
    private final ArrayList<Fragment> mAdded = new ArrayList<>();
    private FragmentManagerViewModel mNonConfig;

    FragmentStore() {
    }

    /* access modifiers changed from: package-private */
    public void setNonConfig(FragmentManagerViewModel nonConfig) {
        this.mNonConfig = nonConfig;
    }

    /* access modifiers changed from: package-private */
    public FragmentManagerViewModel getNonConfig() {
        return this.mNonConfig;
    }

    /* access modifiers changed from: package-private */
    public void resetActiveFragments() {
        this.mActive.clear();
    }

    /* access modifiers changed from: package-private */
    public void restoreAddedFragments(List<String> added) {
        this.mAdded.clear();
        if (added != null) {
            for (String who : added) {
                Fragment f = findActiveFragment(who);
                if (f != null) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v(TAG, "restoreSaveState: added (" + who + "): " + f);
                    }
                    addFragment(f);
                } else {
                    throw new IllegalStateException("No instantiated fragment for (" + who + ")");
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void makeActive(FragmentStateManager newlyActive) {
        Fragment f = newlyActive.getFragment();
        if (!containsActiveFragment(f.mWho)) {
            this.mActive.put(f.mWho, newlyActive);
            if (f.mRetainInstanceChangedWhileDetached) {
                if (f.mRetainInstance) {
                    this.mNonConfig.addRetainedFragment(f);
                } else {
                    this.mNonConfig.removeRetainedFragment(f);
                }
                f.mRetainInstanceChangedWhileDetached = false;
            }
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v(TAG, "Added fragment to active set " + f);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void addFragment(Fragment fragment) {
        if (!this.mAdded.contains(fragment)) {
            synchronized (this.mAdded) {
                this.mAdded.add(fragment);
            }
            fragment.mAdded = true;
            return;
        }
        throw new IllegalStateException("Fragment already added: " + fragment);
    }

    /* access modifiers changed from: package-private */
    public void dispatchStateChange(int state) {
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                fragmentStateManager.setFragmentManagerState(state);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void moveToExpectedState() {
        Iterator<Fragment> it = this.mAdded.iterator();
        while (it.hasNext()) {
            FragmentStateManager fragmentStateManager = this.mActive.get(it.next().mWho);
            if (fragmentStateManager != null) {
                fragmentStateManager.moveToExpectedState();
            }
        }
        for (FragmentStateManager fragmentStateManager2 : this.mActive.values()) {
            if (fragmentStateManager2 != null) {
                fragmentStateManager2.moveToExpectedState();
                Fragment f = fragmentStateManager2.getFragment();
                if (f.mRemoving && !f.isInBackStack()) {
                    makeInactive(fragmentStateManager2);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void removeFragment(Fragment fragment) {
        synchronized (this.mAdded) {
            this.mAdded.remove(fragment);
        }
        fragment.mAdded = false;
    }

    /* access modifiers changed from: package-private */
    public void makeInactive(FragmentStateManager newlyInactive) {
        Fragment f = newlyInactive.getFragment();
        if (f.mRetainInstance) {
            this.mNonConfig.removeRetainedFragment(f);
        }
        if (this.mActive.put(f.mWho, (Object) null) != null && FragmentManager.isLoggingEnabled(2)) {
            Log.v(TAG, "Removed fragment from active set " + f);
        }
    }

    /* access modifiers changed from: package-private */
    public void burpActive() {
        this.mActive.values().removeAll(Collections.singleton((Object) null));
    }

    /* access modifiers changed from: package-private */
    public ArrayList<FragmentState> saveActiveFragments() {
        ArrayList<FragmentState> active = new ArrayList<>(this.mActive.size());
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                Fragment f = fragmentStateManager.getFragment();
                FragmentState fs = fragmentStateManager.saveState();
                active.add(fs);
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v(TAG, "Saved state of " + f + ": " + fs.mSavedFragmentState);
                }
            }
        }
        return active;
    }

    /* access modifiers changed from: package-private */
    public ArrayList<String> saveAddedFragments() {
        synchronized (this.mAdded) {
            if (this.mAdded.isEmpty()) {
                return null;
            }
            ArrayList<String> added = new ArrayList<>(this.mAdded.size());
            Iterator<Fragment> it = this.mAdded.iterator();
            while (it.hasNext()) {
                Fragment f = it.next();
                added.add(f.mWho);
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v(TAG, "saveAllState: adding fragment (" + f.mWho + "): " + f);
                }
            }
            return added;
        }
    }

    /* access modifiers changed from: package-private */
    public List<FragmentStateManager> getActiveFragmentStateManagers() {
        ArrayList<FragmentStateManager> activeFragmentStateManagers = new ArrayList<>();
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                activeFragmentStateManagers.add(fragmentStateManager);
            }
        }
        return activeFragmentStateManagers;
    }

    /* access modifiers changed from: package-private */
    public List<Fragment> getFragments() {
        ArrayList arrayList;
        if (this.mAdded.isEmpty()) {
            return Collections.emptyList();
        }
        synchronized (this.mAdded) {
            arrayList = new ArrayList(this.mAdded);
        }
        return arrayList;
    }

    /* access modifiers changed from: package-private */
    public List<Fragment> getActiveFragments() {
        ArrayList<Fragment> activeFragments = new ArrayList<>();
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                activeFragments.add(fragmentStateManager.getFragment());
            } else {
                activeFragments.add((Object) null);
            }
        }
        return activeFragments;
    }

    /* access modifiers changed from: package-private */
    public int getActiveFragmentCount() {
        return this.mActive.size();
    }

    /* access modifiers changed from: package-private */
    public Fragment findFragmentById(int id) {
        for (int i = this.mAdded.size() - 1; i >= 0; i--) {
            Fragment f = this.mAdded.get(i);
            if (f != null && f.mFragmentId == id) {
                return f;
            }
        }
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                Fragment f2 = fragmentStateManager.getFragment();
                if (f2.mFragmentId == id) {
                    return f2;
                }
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public Fragment findFragmentByTag(String tag) {
        if (tag != null) {
            for (int i = this.mAdded.size() - 1; i >= 0; i--) {
                Fragment f = this.mAdded.get(i);
                if (f != null && tag.equals(f.mTag)) {
                    return f;
                }
            }
        }
        if (tag == null) {
            return null;
        }
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                Fragment f2 = fragmentStateManager.getFragment();
                if (tag.equals(f2.mTag)) {
                    return f2;
                }
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public boolean containsActiveFragment(String who) {
        return this.mActive.get(who) != null;
    }

    /* access modifiers changed from: package-private */
    public FragmentStateManager getFragmentStateManager(String who) {
        return this.mActive.get(who);
    }

    /* access modifiers changed from: package-private */
    public Fragment findFragmentByWho(String who) {
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                Fragment findFragmentByWho = fragmentStateManager.getFragment().findFragmentByWho(who);
                Fragment f = findFragmentByWho;
                if (findFragmentByWho != null) {
                    return f;
                }
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public Fragment findActiveFragment(String who) {
        FragmentStateManager fragmentStateManager = this.mActive.get(who);
        if (fragmentStateManager != null) {
            return fragmentStateManager.getFragment();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public int findFragmentIndexInContainer(Fragment f) {
        ViewGroup container = f.mContainer;
        if (container == null) {
            return -1;
        }
        int fragmentIndex = this.mAdded.indexOf(f);
        for (int i = fragmentIndex - 1; i >= 0; i--) {
            Fragment underFragment = this.mAdded.get(i);
            if (underFragment.mContainer == container && underFragment.mView != null) {
                return container.indexOfChild(underFragment.mView) + 1;
            }
        }
        for (int i2 = fragmentIndex + 1; i2 < this.mAdded.size(); i2++) {
            Fragment overFragment = this.mAdded.get(i2);
            if (overFragment.mContainer == container && overFragment.mView != null) {
                return container.indexOfChild(overFragment.mView);
            }
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        String innerPrefix = prefix + "    ";
        if (!this.mActive.isEmpty()) {
            writer.print(prefix);
            writer.println("Active Fragments:");
            for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
                writer.print(prefix);
                if (fragmentStateManager != null) {
                    Fragment f = fragmentStateManager.getFragment();
                    writer.println(f);
                    f.dump(innerPrefix, fd, writer, args);
                } else {
                    writer.println("null");
                }
            }
        }
        int count = this.mAdded.size();
        if (count > 0) {
            writer.print(prefix);
            writer.println("Added Fragments:");
            for (int i = 0; i < count; i++) {
                writer.print(prefix);
                writer.print("  #");
                writer.print(i);
                writer.print(": ");
                writer.println(this.mAdded.get(i).toString());
            }
        }
    }
}
