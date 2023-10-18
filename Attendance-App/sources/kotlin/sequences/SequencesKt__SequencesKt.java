package kotlin.sequences;

import com.itextpdf.kernel.xmp.PdfConst;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;

@Metadata(mo112d1 = {"\u0000L\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\u001c\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\u001a.\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0014\b\u0004\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0004H\bø\u0001\u0000\u001a\u0012\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002\u001ab\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\b0\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\t\"\u0004\b\u0002\u0010\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\t0\f2\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u0002H\t\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00050\u000eH\u0000\u001a&\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00102\u000e\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0004\u001a<\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00102\u000e\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u00042\u0014\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u000e\u001a=\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00102\b\u0010\u0013\u001a\u0004\u0018\u0001H\u00022\u0014\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u000eH\u0007¢\u0006\u0002\u0010\u0014\u001a+\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0012\u0010\u0016\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0017\"\u0002H\u0002¢\u0006\u0002\u0010\u0018\u001a\u001c\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0005\u001a\u001c\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001\u001aC\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\b0\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\b*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00050\u000eH\u0002¢\u0006\u0002\b\u001c\u001a)\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u001d0\u0001H\u0007¢\u0006\u0002\b\u001e\u001a\"\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0001\u001a2\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0004H\u0007\u001a!\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001H\b\u001a\u001e\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0007\u001a&\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010#\u001a\u00020$H\u0007\u001a@\u0010%\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020'\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0'0&\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\b*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\b0&0\u0001\u0002\u0007\n\u0005\b20\u0001¨\u0006("}, mo113d2 = {"Sequence", "Lkotlin/sequences/Sequence;", "T", "iterator", "Lkotlin/Function0;", "", "emptySequence", "flatMapIndexed", "R", "C", "source", "transform", "Lkotlin/Function2;", "", "Lkotlin/Function1;", "generateSequence", "", "nextFunction", "seedFunction", "seed", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lkotlin/sequences/Sequence;", "sequenceOf", "elements", "", "([Ljava/lang/Object;)Lkotlin/sequences/Sequence;", "asSequence", "constrainOnce", "flatten", "flatten$SequencesKt__SequencesKt", "", "flattenSequenceOfIterable", "ifEmpty", "defaultValue", "orEmpty", "shuffled", "random", "Lkotlin/random/Random;", "unzip", "Lkotlin/Pair;", "", "kotlin-stdlib"}, mo114k = 5, mo115mv = {1, 7, 1}, mo117xi = 49, mo118xs = "kotlin/sequences/SequencesKt")
/* compiled from: Sequences.kt */
class SequencesKt__SequencesKt extends SequencesKt__SequencesJVMKt {
    private static final <T> Sequence<T> Sequence(Function0<? extends Iterator<? extends T>> iterator) {
        Intrinsics.checkNotNullParameter(iterator, "iterator");
        return new SequencesKt__SequencesKt$Sequence$1(iterator);
    }

    public static final <T> Sequence<T> asSequence(Iterator<? extends T> $this$asSequence) {
        Intrinsics.checkNotNullParameter($this$asSequence, "<this>");
        return SequencesKt.constrainOnce(new SequencesKt__SequencesKt$asSequence$$inlined$Sequence$1($this$asSequence));
    }

    public static final <T> Sequence<T> sequenceOf(T... elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return elements.length == 0 ? SequencesKt.emptySequence() : ArraysKt.asSequence(elements);
    }

    public static final <T> Sequence<T> emptySequence() {
        return EmptySequence.INSTANCE;
    }

    private static final <T> Sequence<T> orEmpty(Sequence<? extends T> $this$orEmpty) {
        return $this$orEmpty == null ? SequencesKt.emptySequence() : $this$orEmpty;
    }

    public static final <T> Sequence<T> ifEmpty(Sequence<? extends T> $this$ifEmpty, Function0<? extends Sequence<? extends T>> defaultValue) {
        Intrinsics.checkNotNullParameter($this$ifEmpty, "<this>");
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        return SequencesKt.sequence(new SequencesKt__SequencesKt$ifEmpty$1($this$ifEmpty, defaultValue, (Continuation<? super SequencesKt__SequencesKt$ifEmpty$1>) null));
    }

    public static final <T> Sequence<T> flatten(Sequence<? extends Sequence<? extends T>> $this$flatten) {
        Intrinsics.checkNotNullParameter($this$flatten, "<this>");
        return flatten$SequencesKt__SequencesKt($this$flatten, SequencesKt__SequencesKt$flatten$1.INSTANCE);
    }

    public static final <T> Sequence<T> flattenSequenceOfIterable(Sequence<? extends Iterable<? extends T>> $this$flatten) {
        Intrinsics.checkNotNullParameter($this$flatten, "<this>");
        return flatten$SequencesKt__SequencesKt($this$flatten, SequencesKt__SequencesKt$flatten$2.INSTANCE);
    }

    private static final <T, R> Sequence<R> flatten$SequencesKt__SequencesKt(Sequence<? extends T> $this$flatten, Function1<? super T, ? extends Iterator<? extends R>> iterator) {
        if ($this$flatten instanceof TransformingSequence) {
            return ((TransformingSequence) $this$flatten).flatten$kotlin_stdlib(iterator);
        }
        return new FlatteningSequence<>($this$flatten, SequencesKt__SequencesKt$flatten$3.INSTANCE, iterator);
    }

    public static final <T, R> Pair<List<T>, List<R>> unzip(Sequence<? extends Pair<? extends T, ? extends R>> $this$unzip) {
        Intrinsics.checkNotNullParameter($this$unzip, "<this>");
        ArrayList listT = new ArrayList();
        ArrayList listR = new ArrayList();
        for (Pair pair : $this$unzip) {
            listT.add(pair.getFirst());
            listR.add(pair.getSecond());
        }
        return TuplesKt.m281to(listT, listR);
    }

    public static final <T> Sequence<T> shuffled(Sequence<? extends T> $this$shuffled) {
        Intrinsics.checkNotNullParameter($this$shuffled, "<this>");
        return SequencesKt.shuffled($this$shuffled, Random.Default);
    }

    public static final <T> Sequence<T> shuffled(Sequence<? extends T> $this$shuffled, Random random) {
        Intrinsics.checkNotNullParameter($this$shuffled, "<this>");
        Intrinsics.checkNotNullParameter(random, "random");
        return SequencesKt.sequence(new SequencesKt__SequencesKt$shuffled$1($this$shuffled, random, (Continuation<? super SequencesKt__SequencesKt$shuffled$1>) null));
    }

    public static final <T, C, R> Sequence<R> flatMapIndexed(Sequence<? extends T> source, Function2<? super Integer, ? super T, ? extends C> transform, Function1<? super C, ? extends Iterator<? extends R>> iterator) {
        Intrinsics.checkNotNullParameter(source, PdfConst.Source);
        Intrinsics.checkNotNullParameter(transform, "transform");
        Intrinsics.checkNotNullParameter(iterator, "iterator");
        return SequencesKt.sequence(new SequencesKt__SequencesKt$flatMapIndexed$1(source, transform, iterator, (Continuation<? super SequencesKt__SequencesKt$flatMapIndexed$1>) null));
    }

    public static final <T> Sequence<T> constrainOnce(Sequence<? extends T> $this$constrainOnce) {
        Intrinsics.checkNotNullParameter($this$constrainOnce, "<this>");
        return $this$constrainOnce instanceof ConstrainedOnceSequence ? $this$constrainOnce : new ConstrainedOnceSequence<>($this$constrainOnce);
    }

    public static final <T> Sequence<T> generateSequence(Function0<? extends T> nextFunction) {
        Intrinsics.checkNotNullParameter(nextFunction, "nextFunction");
        return SequencesKt.constrainOnce(new GeneratorSequence(nextFunction, new SequencesKt__SequencesKt$generateSequence$1(nextFunction)));
    }

    public static final <T> Sequence<T> generateSequence(T seed, Function1<? super T, ? extends T> nextFunction) {
        Intrinsics.checkNotNullParameter(nextFunction, "nextFunction");
        if (seed == null) {
            return EmptySequence.INSTANCE;
        }
        return new GeneratorSequence<>(new SequencesKt__SequencesKt$generateSequence$2(seed), nextFunction);
    }

    public static final <T> Sequence<T> generateSequence(Function0<? extends T> seedFunction, Function1<? super T, ? extends T> nextFunction) {
        Intrinsics.checkNotNullParameter(seedFunction, "seedFunction");
        Intrinsics.checkNotNullParameter(nextFunction, "nextFunction");
        return new GeneratorSequence<>(seedFunction, nextFunction);
    }
}
