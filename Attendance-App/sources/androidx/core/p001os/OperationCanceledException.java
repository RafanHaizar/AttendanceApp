package androidx.core.p001os;

import androidx.core.util.ObjectsCompat;

/* renamed from: androidx.core.os.OperationCanceledException */
public class OperationCanceledException extends RuntimeException {
    public OperationCanceledException() {
        this((String) null);
    }

    public OperationCanceledException(String message) {
        super(ObjectsCompat.toString(message, "The operation has been canceled."));
    }
}
