package androidx.activity.result.contract;

import android.content.Context;
import android.content.Intent;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\b&\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003:\u0001\u0013B\u0005¢\u0006\u0002\u0010\u0004J\u001d\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00028\u0000H&¢\u0006\u0002\u0010\nJ%\u0010\u000b\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\rJ\u001f\u0010\u000e\u001a\u00028\u00012\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0006H&¢\u0006\u0002\u0010\u0012¨\u0006\u0014"}, mo113d2 = {"Landroidx/activity/result/contract/ActivityResultContract;", "I", "O", "", "()V", "createIntent", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "input", "(Landroid/content/Context;Ljava/lang/Object;)Landroid/content/Intent;", "getSynchronousResult", "Landroidx/activity/result/contract/ActivityResultContract$SynchronousResult;", "(Landroid/content/Context;Ljava/lang/Object;)Landroidx/activity/result/contract/ActivityResultContract$SynchronousResult;", "parseResult", "resultCode", "", "intent", "(ILandroid/content/Intent;)Ljava/lang/Object;", "SynchronousResult", "activity_release"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: ActivityResultContract.kt */
public abstract class ActivityResultContract<I, O> {
    public abstract Intent createIntent(Context context, I i);

    public abstract O parseResult(int i, Intent intent);

    public SynchronousResult<O> getSynchronousResult(Context context, I input) {
        Intrinsics.checkNotNullParameter(context, "context");
        return null;
    }

    @Metadata(mo112d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0006\u0018\u0000*\u0004\b\u0002\u0010\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0002¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0003\u001a\u00028\u0002¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, mo113d2 = {"Landroidx/activity/result/contract/ActivityResultContract$SynchronousResult;", "T", "", "value", "(Ljava/lang/Object;)V", "getValue", "()Ljava/lang/Object;", "Ljava/lang/Object;", "activity_release"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: ActivityResultContract.kt */
    public static final class SynchronousResult<T> {
        private final T value;

        public SynchronousResult(T value2) {
            this.value = value2;
        }

        public final T getValue() {
            return this.value;
        }
    }
}
