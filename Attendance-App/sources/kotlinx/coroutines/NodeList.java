package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;

@Metadata(mo112d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bJ\b\u0010\r\u001a\u00020\u000bH\u0016R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00008VX\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u000e"}, mo113d2 = {"Lkotlinx/coroutines/NodeList;", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "Lkotlinx/coroutines/Incomplete;", "()V", "isActive", "", "()Z", "list", "getList", "()Lkotlinx/coroutines/NodeList;", "getString", "", "state", "toString", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: JobSupport.kt */
public final class NodeList extends LockFreeLinkedListHead implements Incomplete {
    public boolean isActive() {
        return true;
    }

    public NodeList getList() {
        return this;
    }

    public final String getString(String state) {
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$getString_u24lambda_u2d1 = sb;
        $this$getString_u24lambda_u2d1.append("List{");
        $this$getString_u24lambda_u2d1.append(state);
        $this$getString_u24lambda_u2d1.append("}[");
        boolean first = true;
        LockFreeLinkedListHead this_$iv = this;
        for (LockFreeLinkedListNode cur$iv = (LockFreeLinkedListNode) this_$iv.getNext(); !Intrinsics.areEqual((Object) cur$iv, (Object) this_$iv); cur$iv = cur$iv.getNextNode()) {
            if (cur$iv instanceof JobNode) {
                JobNode node = (JobNode) cur$iv;
                if (first) {
                    first = false;
                } else {
                    $this$getString_u24lambda_u2d1.append(", ");
                }
                $this$getString_u24lambda_u2d1.append(node);
            }
        }
        $this$getString_u24lambda_u2d1.append("]");
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    public String toString() {
        return DebugKt.getDEBUG() ? getString("Active") : super.toString();
    }
}
