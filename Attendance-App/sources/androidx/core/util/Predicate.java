package androidx.core.util;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.Objects;

public interface Predicate<T> {
    Predicate<T> and(Predicate<? super T> predicate);

    Predicate<T> negate();

    /* renamed from: or */
    Predicate<T> mo15473or(Predicate<? super T> predicate);

    boolean test(T t);

    @SynthesizedClassV2(kind = 8, versionHash = "ea87655719898b9807d7a88878e9de051d12af172d2fab563c9881b5e404e7d4")
    /* renamed from: androidx.core.util.Predicate$-CC  reason: invalid class name */
    public final /* synthetic */ class CC<T> {
        public static Predicate $default$and(Predicate _this, Predicate other) {
            Objects.requireNonNull(other);
            return new Predicate$$ExternalSyntheticLambda4(_this, other);
        }

        public static /* synthetic */ boolean $private$lambda$and$0(Predicate _this, Predicate other, Object t) {
            return _this.test(t) && other.test(t);
        }

        public static Predicate $default$negate(Predicate _this) {
            return new Predicate$$ExternalSyntheticLambda5(_this);
        }

        public static /* synthetic */ boolean $private$lambda$negate$1(Predicate _this, Object t) {
            return !_this.test(t);
        }

        public static Predicate $default$or(Predicate _this, Predicate other) {
            Objects.requireNonNull(other);
            return new Predicate$$ExternalSyntheticLambda1(_this, other);
        }

        public static /* synthetic */ boolean $private$lambda$or$2(Predicate _this, Predicate other, Object t) {
            return _this.test(t) || other.test(t);
        }

        public static <T> Predicate<T> isEqual(Object targetRef) {
            if (targetRef == null) {
                return new Predicate$$ExternalSyntheticLambda2();
            }
            return new Predicate$$ExternalSyntheticLambda3(targetRef);
        }

        public static <T> Predicate<T> not(Predicate<? super T> target) {
            Objects.requireNonNull(target);
            return target.negate();
        }
    }
}
