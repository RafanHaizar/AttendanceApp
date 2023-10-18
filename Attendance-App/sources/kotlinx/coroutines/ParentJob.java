package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.Job;

@Metadata(mo112d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\f\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004H'¨\u0006\u0005"}, mo113d2 = {"Lkotlinx/coroutines/ParentJob;", "Lkotlinx/coroutines/Job;", "getChildJobCancellationCause", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
@Deprecated(level = DeprecationLevel.ERROR, message = "This is internal API and may be removed in the future releases")
/* compiled from: Job.kt */
public interface ParentJob extends Job {
    CancellationException getChildJobCancellationCause();

    @Metadata(mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: Job.kt */
    public static final class DefaultImpls {
        public static <R> R fold(ParentJob parentJob, R initial, Function2<? super R, ? super CoroutineContext.Element, ? extends R> operation) {
            return Job.DefaultImpls.fold(parentJob, initial, operation);
        }

        public static <E extends CoroutineContext.Element> E get(ParentJob parentJob, CoroutineContext.Key<E> key) {
            return Job.DefaultImpls.get(parentJob, key);
        }

        public static CoroutineContext minusKey(ParentJob parentJob, CoroutineContext.Key<?> key) {
            return Job.DefaultImpls.minusKey(parentJob, key);
        }

        public static CoroutineContext plus(ParentJob parentJob, CoroutineContext context) {
            return Job.DefaultImpls.plus((Job) parentJob, context);
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
        public static Job plus(ParentJob parentJob, Job other) {
            return Job.DefaultImpls.plus((Job) parentJob, other);
        }
    }
}
