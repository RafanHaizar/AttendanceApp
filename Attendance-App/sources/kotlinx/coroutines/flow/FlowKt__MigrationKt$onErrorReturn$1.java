package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, mo113d2 = {"<anonymous>", "", "T", "it", "", "invoke", "(Ljava/lang/Throwable;)Ljava/lang/Boolean;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Migration.kt */
final class FlowKt__MigrationKt$onErrorReturn$1 extends Lambda implements Function1<Throwable, Boolean> {
    public static final FlowKt__MigrationKt$onErrorReturn$1 INSTANCE = new FlowKt__MigrationKt$onErrorReturn$1();

    FlowKt__MigrationKt$onErrorReturn$1() {
        super(1);
    }

    public final Boolean invoke(Throwable it) {
        return true;
    }
}
