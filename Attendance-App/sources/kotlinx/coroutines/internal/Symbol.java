package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.text.Typography;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0003H\u0016J\u001e\u0010\u0006\u001a\u0002H\u0007\"\u0004\b\u0000\u0010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0001H\b¢\u0006\u0002\u0010\tR\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, mo113d2 = {"Lkotlinx/coroutines/internal/Symbol;", "", "symbol", "", "(Ljava/lang/String;)V", "toString", "unbox", "T", "value", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Symbol.kt */
public final class Symbol {
    public final String symbol;

    public Symbol(String symbol2) {
        this.symbol = symbol2;
    }

    public String toString() {
        return Typography.less + this.symbol + Typography.greater;
    }

    public final <T> T unbox(Object value) {
        if (value == this) {
            return null;
        }
        return value;
    }
}
