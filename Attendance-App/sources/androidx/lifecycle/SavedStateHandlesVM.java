package androidx.lifecycle;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;

@Metadata(mo112d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, mo113d2 = {"Landroidx/lifecycle/SavedStateHandlesVM;", "Landroidx/lifecycle/ViewModel;", "()V", "handles", "", "", "Landroidx/lifecycle/SavedStateHandle;", "getHandles", "()Ljava/util/Map;", "lifecycle-viewmodel-savedstate_release"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: SavedStateHandleSupport.kt */
public final class SavedStateHandlesVM extends ViewModel {
    private final Map<String, SavedStateHandle> handles = new LinkedHashMap();

    public final Map<String, SavedStateHandle> getHandles() {
        return this.handles;
    }
}
