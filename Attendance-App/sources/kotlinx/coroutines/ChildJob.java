package kotlinx.coroutines;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.Job;

@Metadata(mo112d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H'¨\u0006\u0006"}, mo113d2 = {"Lkotlinx/coroutines/ChildJob;", "Lkotlinx/coroutines/Job;", "parentCancelled", "", "parentJob", "Lkotlinx/coroutines/ParentJob;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
@Deprecated(level = DeprecationLevel.ERROR, message = "This is internal API and may be removed in the future releases")
/* compiled from: Job.kt */
public interface ChildJob extends Job {
    void parentCancelled(ParentJob parentJob);

    @Metadata(mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: Job.kt */
    public static final class DefaultImpls {
        public static <R> R fold(ChildJob childJob, R initial, Function2<? super R, ? super CoroutineContext.Element, ? extends R> operation) {
            return Job.DefaultImpls.fold(childJob, initial, operation);
        }

        public static <E extends CoroutineContext.Element> E get(ChildJob childJob, CoroutineContext.Key<E> key) {
            return Job.DefaultImpls.get(childJob, key);
        }

        public static CoroutineContext minusKey(ChildJob childJob, CoroutineContext.Key<?> key) {
            return Job.DefaultImpls.minusKey(childJob, key);
        }

        public static CoroutineContext plus(ChildJob childJob, CoroutineContext context) {
            return Job.DefaultImpls.plus((Job) childJob, context);
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
        public static Job plus(ChildJob childJob, Job other) {
            return Job.DefaultImpls.plus((Job) childJob, other);
        }
    }
}
