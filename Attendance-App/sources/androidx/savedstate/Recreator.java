package androidx.savedstate;

import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.savedstate.SavedStateRegistry;
import com.itextpdf.kernel.xmp.PdfConst;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0000\u0018\u0000 \u000e2\u00020\u0001:\u0002\u000e\u000fB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, mo113d2 = {"Landroidx/savedstate/Recreator;", "Landroidx/lifecycle/LifecycleEventObserver;", "owner", "Landroidx/savedstate/SavedStateRegistryOwner;", "(Landroidx/savedstate/SavedStateRegistryOwner;)V", "onStateChanged", "", "source", "Landroidx/lifecycle/LifecycleOwner;", "event", "Landroidx/lifecycle/Lifecycle$Event;", "reflectiveNew", "className", "", "Companion", "SavedStateProvider", "savedstate_release"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Recreator.kt */
public final class Recreator implements LifecycleEventObserver {
    public static final String CLASSES_KEY = "classes_to_restore";
    public static final String COMPONENT_KEY = "androidx.savedstate.Restarter";
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private final SavedStateRegistryOwner owner;

    public Recreator(SavedStateRegistryOwner owner2) {
        Intrinsics.checkNotNullParameter(owner2, "owner");
        this.owner = owner2;
    }

    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        Intrinsics.checkNotNullParameter(source, PdfConst.Source);
        Intrinsics.checkNotNullParameter(event, NotificationCompat.CATEGORY_EVENT);
        if (event == Lifecycle.Event.ON_CREATE) {
            source.getLifecycle().removeObserver(this);
            Bundle bundle = this.owner.getSavedStateRegistry().consumeRestoredStateForKey(COMPONENT_KEY);
            if (bundle != null) {
                ArrayList<String> classes = bundle.getStringArrayList(CLASSES_KEY);
                if (classes != null) {
                    for (String className : classes) {
                        reflectiveNew(className);
                    }
                    return;
                }
                throw new IllegalStateException("Bundle with restored state for the component \"androidx.savedstate.Restarter\" must contain list of strings by the key \"classes_to_restore\"");
            }
            return;
        }
        throw new AssertionError("Next event must be ON_CREATE");
    }

    private final void reflectiveNew(String className) {
        try {
            Class clazz = Class.forName(className, false, Recreator.class.getClassLoader()).asSubclass(SavedStateRegistry.AutoRecreated.class);
            Intrinsics.checkNotNullExpressionValue(clazz, "{\n                Class.…class.java)\n            }");
            try {
                Constructor constructor = clazz.getDeclaredConstructor(new Class[0]);
                constructor.setAccessible(true);
                try {
                    Object newInstance = constructor.newInstance(new Object[0]);
                    Intrinsics.checkNotNullExpressionValue(newInstance, "{\n                constr…wInstance()\n            }");
                    ((SavedStateRegistry.AutoRecreated) newInstance).onRecreated(this.owner);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to instantiate " + className, e);
                }
            } catch (NoSuchMethodException e2) {
                throw new IllegalStateException("Class " + clazz.getSimpleName() + " must have default constructor in order to be automatically recreated", e2);
            }
        } catch (ClassNotFoundException e3) {
            throw new RuntimeException("Class " + className + " wasn't found", e3);
        }
    }

    @Metadata(mo112d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0007J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, mo113d2 = {"Landroidx/savedstate/Recreator$SavedStateProvider;", "Landroidx/savedstate/SavedStateRegistry$SavedStateProvider;", "registry", "Landroidx/savedstate/SavedStateRegistry;", "(Landroidx/savedstate/SavedStateRegistry;)V", "classes", "", "", "add", "", "className", "saveState", "Landroid/os/Bundle;", "savedstate_release"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: Recreator.kt */
    public static final class SavedStateProvider implements SavedStateRegistry.SavedStateProvider {
        private final Set<String> classes = new LinkedHashSet();

        public SavedStateProvider(SavedStateRegistry registry) {
            Intrinsics.checkNotNullParameter(registry, "registry");
            registry.registerSavedStateProvider(Recreator.COMPONENT_KEY, this);
        }

        public Bundle saveState() {
            Bundle $this$saveState_u24lambda_u2d0 = new Bundle();
            $this$saveState_u24lambda_u2d0.putStringArrayList(Recreator.CLASSES_KEY, new ArrayList(this.classes));
            return $this$saveState_u24lambda_u2d0;
        }

        public final void add(String className) {
            Intrinsics.checkNotNullParameter(className, "className");
            this.classes.add(className);
        }
    }

    @Metadata(mo112d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000¨\u0006\u0006"}, mo113d2 = {"Landroidx/savedstate/Recreator$Companion;", "", "()V", "CLASSES_KEY", "", "COMPONENT_KEY", "savedstate_release"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: Recreator.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
