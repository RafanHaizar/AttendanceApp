package androidx.core.widget;

import android.text.Editable;
import android.text.TextWatcher;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;

@Metadata(mo112d1 = {"\u0000'\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016J*\u0010\u0006\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0016J*\u0010\r\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016¨\u0006\u000f¸\u0006\u0000"}, mo113d2 = {"androidx/core/widget/TextViewKt$addTextChangedListener$textWatcher$1", "Landroid/text/TextWatcher;", "afterTextChanged", "", "s", "Landroid/text/Editable;", "beforeTextChanged", "text", "", "start", "", "count", "after", "onTextChanged", "before", "core-ktx_release"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 176)
/* renamed from: androidx.core.widget.TextViewKt$doAfterTextChanged$$inlined$addTextChangedListener$default$1 */
/* compiled from: TextView.kt */
public final class C0736xbb2cb19c implements TextWatcher {
    final /* synthetic */ Function1 $afterTextChanged;

    public C0736xbb2cb19c(Function1 $afterTextChanged2) {
        this.$afterTextChanged = $afterTextChanged2;
    }

    public void afterTextChanged(Editable s) {
        this.$afterTextChanged.invoke(s);
    }

    public void beforeTextChanged(CharSequence text, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence text, int start, int before, int count) {
    }
}
