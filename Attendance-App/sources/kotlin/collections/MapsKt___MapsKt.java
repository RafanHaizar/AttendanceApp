package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

@Metadata(mo112d1 = {"\u0000\u0001\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u001f\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000f\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001aJ\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\bø\u0001\u0000\u001a$\u0010\b\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004\u001aJ\u0010\b\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\bø\u0001\u0000\u001a9\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00070\n\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004H\b\u001a6\u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00070\f\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004\u001a'\u0010\r\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004H\b\u001aJ\u0010\r\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\bø\u0001\u0000\u001a[\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\b\b\u0002\u0010\u0010*\u00020\u0011*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042 \u0010\u0012\u001a\u001c\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0006\u0012\u0004\u0018\u0001H\u00100\u0006H\bø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001a]\u0010\u0014\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\b\b\u0002\u0010\u0010*\u00020\u0011*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042 \u0010\u0012\u001a\u001c\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0006\u0012\u0004\u0018\u0001H\u00100\u0006H\bø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001a\\\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00100\u0016\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042$\u0010\u0012\u001a \u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00100\n0\u0006H\bø\u0001\u0000\u001aa\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00100\u0016\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042$\u0010\u0012\u001a \u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00100\f0\u0006H\bø\u0001\u0000¢\u0006\u0002\b\u0017\u001au\u0010\u0018\u001a\u0002H\u0019\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0010\"\u0010\b\u0003\u0010\u0019*\n\u0012\u0006\b\u0000\u0012\u0002H\u00100\u001a*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u001b\u001a\u0002H\u00192$\u0010\u0012\u001a \u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00100\n0\u0006H\bø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001aw\u0010\u0018\u001a\u0002H\u0019\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0010\"\u0010\b\u0003\u0010\u0019*\n\u0012\u0006\b\u0000\u0012\u0002H\u00100\u001a*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u001b\u001a\u0002H\u00192$\u0010\u0012\u001a \u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00100\f0\u0006H\bø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u001c\u001aJ\u0010\u001e\u001a\u00020\u001f\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010 \u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u001f0\u0006H\bø\u0001\u0000\u001aV\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00100\u0016\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0012\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\bø\u0001\u0000\u001a\\\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00100\u0016\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\b\b\u0002\u0010\u0010*\u00020\u0011*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042 \u0010\u0012\u001a\u001c\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0006\u0012\u0004\u0018\u0001H\u00100\u0006H\bø\u0001\u0000\u001au\u0010#\u001a\u0002H\u0019\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\b\b\u0002\u0010\u0010*\u00020\u0011\"\u0010\b\u0003\u0010\u0019*\n\u0012\u0006\b\u0000\u0012\u0002H\u00100\u001a*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u001b\u001a\u0002H\u00192 \u0010\u0012\u001a\u001c\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0006\u0012\u0004\u0018\u0001H\u00100\u0006H\bø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001ao\u0010$\u001a\u0002H\u0019\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0010\"\u0010\b\u0003\u0010\u0019*\n\u0012\u0006\b\u0000\u0012\u0002H\u00100\u001a*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u001b\u001a\u0002H\u00192\u001e\u0010\u0012\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\bø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001ak\u0010%\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100&*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\bø\u0001\u0000¢\u0006\u0002\b(\u001ah\u0010)\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100&*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\bø\u0001\u0000\u001a_\u0010*\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100&*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\bø\u0001\u0000¢\u0006\u0002\u0010+\u001aJ\u0010*\u001a\u00020,\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020,0\u0006H\bø\u0001\u0000\u001aJ\u0010*\u001a\u00020-\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020-0\u0006H\bø\u0001\u0000\u001aa\u0010.\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100&*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\bø\u0001\u0000¢\u0006\u0002\u0010+\u001aQ\u0010.\u001a\u0004\u0018\u00010,\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020,0\u0006H\bø\u0001\u0000¢\u0006\u0002\u0010/\u001aQ\u0010.\u001a\u0004\u0018\u00010-\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020-0\u0006H\bø\u0001\u0000¢\u0006\u0002\u00100\u001aq\u00101\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001a\u00102\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u001003j\n\u0012\u0006\b\u0000\u0012\u0002H\u0010`42\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\bø\u0001\u0000¢\u0006\u0002\u00105\u001as\u00106\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001a\u00102\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u001003j\n\u0012\u0006\b\u0000\u0012\u0002H\u0010`42\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\bø\u0001\u0000¢\u0006\u0002\u00105\u001al\u00107\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000422\u00102\u001a.\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000703j\u0016\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007`4H\b¢\u0006\u0002\b8\u001ai\u00109\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000422\u00102\u001a.\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000703j\u0016\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007`4H\b\u001ak\u0010:\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100&*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\bø\u0001\u0000¢\u0006\u0002\b;\u001ah\u0010<\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100&*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\bø\u0001\u0000\u001a_\u0010=\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100&*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\bø\u0001\u0000¢\u0006\u0002\u0010+\u001aJ\u0010=\u001a\u00020,\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020,0\u0006H\bø\u0001\u0000\u001aJ\u0010=\u001a\u00020-\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020-0\u0006H\bø\u0001\u0000\u001aa\u0010>\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100&*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\bø\u0001\u0000¢\u0006\u0002\u0010+\u001aQ\u0010>\u001a\u0004\u0018\u00010,\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020,0\u0006H\bø\u0001\u0000¢\u0006\u0002\u0010/\u001aQ\u0010>\u001a\u0004\u0018\u00010-\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020-0\u0006H\bø\u0001\u0000¢\u0006\u0002\u00100\u001aq\u0010?\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001a\u00102\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u001003j\n\u0012\u0006\b\u0000\u0012\u0002H\u0010`42\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\bø\u0001\u0000¢\u0006\u0002\u00105\u001as\u0010@\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001a\u00102\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u001003j\n\u0012\u0006\b\u0000\u0012\u0002H\u0010`42\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00100\u0006H\bø\u0001\u0000¢\u0006\u0002\u00105\u001al\u0010A\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000422\u00102\u001a.\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000703j\u0016\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007`4H\b¢\u0006\u0002\bB\u001ai\u0010C\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000422\u00102\u001a.\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000703j\u0016\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007`4H\b\u001a$\u0010D\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004\u001aJ\u0010D\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\bø\u0001\u0000\u001aY\u0010E\u001a\u0002HF\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0016\b\u0002\u0010F*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004*\u0002HF2\u001e\u0010 \u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u001f0\u0006H\bø\u0001\u0000¢\u0006\u0002\u0010G\u001an\u0010H\u001a\u0002HF\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0016\b\u0002\u0010F*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004*\u0002HF23\u0010 \u001a/\u0012\u0013\u0012\u00110\u000e¢\u0006\f\bJ\u0012\b\bK\u0012\u0004\b\b(L\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u001f0IH\bø\u0001\u0000¢\u0006\u0002\u0010M\u001a6\u0010N\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030O0\u0016\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004\u0002\u0007\n\u0005\b20\u0001¨\u0006P"}, mo113d2 = {"all", "", "K", "V", "", "predicate", "Lkotlin/Function1;", "", "any", "asIterable", "", "asSequence", "Lkotlin/sequences/Sequence;", "count", "", "firstNotNullOf", "R", "", "transform", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "firstNotNullOfOrNull", "flatMap", "", "flatMapSequence", "flatMapTo", "C", "", "destination", "(Ljava/util/Map;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "flatMapSequenceTo", "forEach", "", "action", "map", "mapNotNull", "mapNotNullTo", "mapTo", "maxBy", "", "selector", "maxByOrThrow", "maxByOrNull", "maxOf", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/lang/Comparable;", "", "", "maxOfOrNull", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/lang/Double;", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/lang/Float;", "maxOfWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/util/Map;Ljava/util/Comparator;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "maxOfWithOrNull", "maxWith", "maxWithOrThrow", "maxWithOrNull", "minBy", "minByOrThrow", "minByOrNull", "minOf", "minOfOrNull", "minOfWith", "minOfWithOrNull", "minWith", "minWithOrThrow", "minWithOrNull", "none", "onEach", "M", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "onEachIndexed", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "index", "(Ljava/util/Map;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "toList", "Lkotlin/Pair;", "kotlin-stdlib"}, mo114k = 5, mo115mv = {1, 7, 1}, mo117xi = 49, mo118xs = "kotlin/collections/MapsKt")
/* compiled from: _Maps.kt */
class MapsKt___MapsKt extends MapsKt___MapsJvmKt {
    private static final <K, V, R> R firstNotNullOf(Map<? extends K, ? extends V> $this$firstNotNullOf, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> transform) {
        R r;
        Intrinsics.checkNotNullParameter($this$firstNotNullOf, "<this>");
        Intrinsics.checkNotNullParameter(transform, "transform");
        Iterator<Map.Entry<? extends K, ? extends V>> it = $this$firstNotNullOf.entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                r = null;
                break;
            }
            r = transform.invoke(it.next());
            if (r != null) {
                break;
            }
        }
        if (r != null) {
            return r;
        }
        throw new NoSuchElementException("No element of the map was transformed to a non-null value.");
    }

    private static final <K, V, R> R firstNotNullOfOrNull(Map<? extends K, ? extends V> $this$firstNotNullOfOrNull, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> transform) {
        Intrinsics.checkNotNullParameter($this$firstNotNullOfOrNull, "<this>");
        Intrinsics.checkNotNullParameter(transform, "transform");
        for (Map.Entry<? extends K, ? extends V> element : $this$firstNotNullOfOrNull.entrySet()) {
            Object result = transform.invoke(element);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public static final <K, V> List<Pair<K, V>> toList(Map<? extends K, ? extends V> $this$toList) {
        Intrinsics.checkNotNullParameter($this$toList, "<this>");
        if ($this$toList.size() == 0) {
            return CollectionsKt.emptyList();
        }
        Iterator iterator = $this$toList.entrySet().iterator();
        if (!iterator.hasNext()) {
            return CollectionsKt.emptyList();
        }
        Map.Entry first = iterator.next();
        if (!iterator.hasNext()) {
            return CollectionsKt.listOf(new Pair(first.getKey(), first.getValue()));
        }
        ArrayList result = new ArrayList($this$toList.size());
        result.add(new Pair(first.getKey(), first.getValue()));
        do {
            Map.Entry next = iterator.next();
            result.add(new Pair(next.getKey(), next.getValue()));
        } while (iterator.hasNext());
        return result;
    }

    public static final <K, V, R> List<R> flatMap(Map<? extends K, ? extends V> $this$flatMap, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends Iterable<? extends R>> transform) {
        Intrinsics.checkNotNullParameter($this$flatMap, "<this>");
        Intrinsics.checkNotNullParameter(transform, "transform");
        Collection destination$iv = new ArrayList();
        for (Map.Entry<? extends K, ? extends V> element$iv : $this$flatMap.entrySet()) {
            CollectionsKt.addAll(destination$iv, (Iterable) transform.invoke(element$iv));
        }
        return (List) destination$iv;
    }

    public static final <K, V, R> List<R> flatMapSequence(Map<? extends K, ? extends V> $this$flatMap, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends Sequence<? extends R>> transform) {
        Intrinsics.checkNotNullParameter($this$flatMap, "<this>");
        Intrinsics.checkNotNullParameter(transform, "transform");
        Collection destination$iv = new ArrayList();
        for (Map.Entry<? extends K, ? extends V> element$iv : $this$flatMap.entrySet()) {
            CollectionsKt.addAll(destination$iv, (Sequence) transform.invoke(element$iv));
        }
        return (List) destination$iv;
    }

    public static final <K, V, R, C extends Collection<? super R>> C flatMapTo(Map<? extends K, ? extends V> $this$flatMapTo, C destination, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends Iterable<? extends R>> transform) {
        Intrinsics.checkNotNullParameter($this$flatMapTo, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(transform, "transform");
        for (Map.Entry<? extends K, ? extends V> element : $this$flatMapTo.entrySet()) {
            CollectionsKt.addAll(destination, (Iterable) transform.invoke(element));
        }
        return destination;
    }

    public static final <K, V, R, C extends Collection<? super R>> C flatMapSequenceTo(Map<? extends K, ? extends V> $this$flatMapTo, C destination, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends Sequence<? extends R>> transform) {
        Intrinsics.checkNotNullParameter($this$flatMapTo, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(transform, "transform");
        for (Map.Entry<? extends K, ? extends V> element : $this$flatMapTo.entrySet()) {
            CollectionsKt.addAll(destination, (Sequence) transform.invoke(element));
        }
        return destination;
    }

    public static final <K, V, R> List<R> map(Map<? extends K, ? extends V> $this$map, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> transform) {
        Intrinsics.checkNotNullParameter($this$map, "<this>");
        Intrinsics.checkNotNullParameter(transform, "transform");
        Collection destination$iv = new ArrayList($this$map.size());
        for (Map.Entry<? extends K, ? extends V> item$iv : $this$map.entrySet()) {
            destination$iv.add(transform.invoke(item$iv));
        }
        return (List) destination$iv;
    }

    public static final <K, V, R> List<R> mapNotNull(Map<? extends K, ? extends V> $this$mapNotNull, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> transform) {
        Intrinsics.checkNotNullParameter($this$mapNotNull, "<this>");
        Intrinsics.checkNotNullParameter(transform, "transform");
        Collection destination$iv = new ArrayList();
        for (Map.Entry<? extends K, ? extends V> element$iv$iv : $this$mapNotNull.entrySet()) {
            Object it$iv = transform.invoke(element$iv$iv);
            if (it$iv != null) {
                destination$iv.add(it$iv);
            }
        }
        return (List) destination$iv;
    }

    public static final <K, V, R, C extends Collection<? super R>> C mapNotNullTo(Map<? extends K, ? extends V> $this$mapNotNullTo, C destination, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> transform) {
        Intrinsics.checkNotNullParameter($this$mapNotNullTo, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(transform, "transform");
        for (Map.Entry<? extends K, ? extends V> element$iv : $this$mapNotNullTo.entrySet()) {
            Object it = transform.invoke(element$iv);
            if (it != null) {
                destination.add(it);
            }
        }
        return destination;
    }

    public static final <K, V, R, C extends Collection<? super R>> C mapTo(Map<? extends K, ? extends V> $this$mapTo, C destination, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> transform) {
        Intrinsics.checkNotNullParameter($this$mapTo, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(transform, "transform");
        for (Map.Entry<? extends K, ? extends V> item : $this$mapTo.entrySet()) {
            destination.add(transform.invoke(item));
        }
        return destination;
    }

    public static final <K, V> boolean all(Map<? extends K, ? extends V> $this$all, Function1<? super Map.Entry<? extends K, ? extends V>, Boolean> predicate) {
        Intrinsics.checkNotNullParameter($this$all, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        if ($this$all.isEmpty()) {
            return true;
        }
        for (Map.Entry<? extends K, ? extends V> element : $this$all.entrySet()) {
            if (!predicate.invoke(element).booleanValue()) {
                return false;
            }
        }
        return true;
    }

    public static final <K, V> boolean any(Map<? extends K, ? extends V> $this$any) {
        Intrinsics.checkNotNullParameter($this$any, "<this>");
        return !$this$any.isEmpty();
    }

    public static final <K, V> boolean any(Map<? extends K, ? extends V> $this$any, Function1<? super Map.Entry<? extends K, ? extends V>, Boolean> predicate) {
        Intrinsics.checkNotNullParameter($this$any, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        if ($this$any.isEmpty()) {
            return false;
        }
        for (Map.Entry<? extends K, ? extends V> element : $this$any.entrySet()) {
            if (predicate.invoke(element).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    private static final <K, V> int count(Map<? extends K, ? extends V> $this$count) {
        Intrinsics.checkNotNullParameter($this$count, "<this>");
        return $this$count.size();
    }

    public static final <K, V> int count(Map<? extends K, ? extends V> $this$count, Function1<? super Map.Entry<? extends K, ? extends V>, Boolean> predicate) {
        Intrinsics.checkNotNullParameter($this$count, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        if ($this$count.isEmpty()) {
            return 0;
        }
        int count = 0;
        for (Map.Entry<? extends K, ? extends V> element : $this$count.entrySet()) {
            if (predicate.invoke(element).booleanValue()) {
                count++;
            }
        }
        return count;
    }

    public static final <K, V> void forEach(Map<? extends K, ? extends V> $this$forEach, Function1<? super Map.Entry<? extends K, ? extends V>, Unit> action) {
        Intrinsics.checkNotNullParameter($this$forEach, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        for (Map.Entry<? extends K, ? extends V> element : $this$forEach.entrySet()) {
            action.invoke(element);
        }
    }

    private static final <K, V, R extends Comparable<? super R>> Map.Entry<K, V> maxByOrThrow(Map<? extends K, ? extends V> $this$maxBy, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> selector) {
        Intrinsics.checkNotNullParameter($this$maxBy, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator iterator$iv = $this$maxBy.entrySet().iterator();
        if (iterator$iv.hasNext()) {
            Object maxElem$iv = iterator$iv.next();
            if (iterator$iv.hasNext()) {
                Comparable maxValue$iv = (Comparable) selector.invoke(maxElem$iv);
                do {
                    Object e$iv = iterator$iv.next();
                    Comparable v$iv = (Comparable) selector.invoke(e$iv);
                    if (maxValue$iv.compareTo(v$iv) < 0) {
                        maxElem$iv = e$iv;
                        maxValue$iv = v$iv;
                    }
                } while (iterator$iv.hasNext());
            }
            return (Map.Entry) maxElem$iv;
        }
        throw new NoSuchElementException();
    }

    private static final <K, V, R extends Comparable<? super R>> Map.Entry<K, V> maxByOrNull(Map<? extends K, ? extends V> $this$maxByOrNull, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> selector) {
        Object maxElem$iv;
        Intrinsics.checkNotNullParameter($this$maxByOrNull, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator iterator$iv = $this$maxByOrNull.entrySet().iterator();
        if (!iterator$iv.hasNext()) {
            maxElem$iv = null;
        } else {
            maxElem$iv = iterator$iv.next();
            if (iterator$iv.hasNext()) {
                Comparable maxValue$iv = (Comparable) selector.invoke(maxElem$iv);
                do {
                    Object e$iv = iterator$iv.next();
                    Comparable v$iv = (Comparable) selector.invoke(e$iv);
                    if (maxValue$iv.compareTo(v$iv) < 0) {
                        maxElem$iv = e$iv;
                        maxValue$iv = v$iv;
                    }
                } while (iterator$iv.hasNext());
            }
        }
        return (Map.Entry) maxElem$iv;
    }

    private static final <K, V> double maxOf(Map<? extends K, ? extends V> $this$maxOf, Function1<? super Map.Entry<? extends K, ? extends V>, Double> selector) {
        Intrinsics.checkNotNullParameter($this$maxOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator it = $this$maxOf.entrySet().iterator();
        if (it.hasNext()) {
            double doubleValue = selector.invoke(it.next()).doubleValue();
            while (it.hasNext()) {
                doubleValue = Math.max(doubleValue, selector.invoke(it.next()).doubleValue());
            }
            return doubleValue;
        }
        throw new NoSuchElementException();
    }

    /* renamed from: maxOf  reason: collision with other method in class */
    private static final <K, V> float m1696maxOf(Map<? extends K, ? extends V> $this$maxOf, Function1<? super Map.Entry<? extends K, ? extends V>, Float> selector) {
        Intrinsics.checkNotNullParameter($this$maxOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator it = $this$maxOf.entrySet().iterator();
        if (it.hasNext()) {
            float floatValue = selector.invoke(it.next()).floatValue();
            while (it.hasNext()) {
                floatValue = Math.max(floatValue, selector.invoke(it.next()).floatValue());
            }
            return floatValue;
        }
        throw new NoSuchElementException();
    }

    /* renamed from: maxOf  reason: collision with other method in class */
    private static final <K, V, R extends Comparable<? super R>> R m1697maxOf(Map<? extends K, ? extends V> $this$maxOf, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> selector) {
        Intrinsics.checkNotNullParameter($this$maxOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator it = $this$maxOf.entrySet().iterator();
        if (it.hasNext()) {
            R r = (Comparable) selector.invoke(it.next());
            while (it.hasNext()) {
                R r2 = (Comparable) selector.invoke(it.next());
                if (r.compareTo(r2) < 0) {
                    r = r2;
                }
            }
            return r;
        }
        throw new NoSuchElementException();
    }

    /* renamed from: maxOfOrNull  reason: collision with other method in class */
    private static final <K, V> Double m1698maxOfOrNull(Map<? extends K, ? extends V> $this$maxOfOrNull, Function1<? super Map.Entry<? extends K, ? extends V>, Double> selector) {
        Intrinsics.checkNotNullParameter($this$maxOfOrNull, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator it = $this$maxOfOrNull.entrySet().iterator();
        if (!it.hasNext()) {
            return null;
        }
        double doubleValue = selector.invoke(it.next()).doubleValue();
        while (it.hasNext()) {
            doubleValue = Math.max(doubleValue, selector.invoke(it.next()).doubleValue());
        }
        return Double.valueOf(doubleValue);
    }

    /* renamed from: maxOfOrNull  reason: collision with other method in class */
    private static final <K, V> Float m1699maxOfOrNull(Map<? extends K, ? extends V> $this$maxOfOrNull, Function1<? super Map.Entry<? extends K, ? extends V>, Float> selector) {
        Intrinsics.checkNotNullParameter($this$maxOfOrNull, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator it = $this$maxOfOrNull.entrySet().iterator();
        if (!it.hasNext()) {
            return null;
        }
        float floatValue = selector.invoke(it.next()).floatValue();
        while (it.hasNext()) {
            floatValue = Math.max(floatValue, selector.invoke(it.next()).floatValue());
        }
        return Float.valueOf(floatValue);
    }

    private static final <K, V, R extends Comparable<? super R>> R maxOfOrNull(Map<? extends K, ? extends V> $this$maxOfOrNull, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> selector) {
        Intrinsics.checkNotNullParameter($this$maxOfOrNull, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator it = $this$maxOfOrNull.entrySet().iterator();
        if (!it.hasNext()) {
            return null;
        }
        R r = (Comparable) selector.invoke(it.next());
        while (it.hasNext()) {
            R r2 = (Comparable) selector.invoke(it.next());
            if (r.compareTo(r2) < 0) {
                r = r2;
            }
        }
        return r;
    }

    private static final <K, V, R> R maxOfWith(Map<? extends K, ? extends V> $this$maxOfWith, Comparator<? super R> comparator, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> selector) {
        Intrinsics.checkNotNullParameter($this$maxOfWith, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator it = $this$maxOfWith.entrySet().iterator();
        if (it.hasNext()) {
            R invoke = selector.invoke(it.next());
            while (it.hasNext()) {
                R invoke2 = selector.invoke(it.next());
                if (comparator.compare(invoke, invoke2) < 0) {
                    invoke = invoke2;
                }
            }
            return invoke;
        }
        throw new NoSuchElementException();
    }

    private static final <K, V, R> R maxOfWithOrNull(Map<? extends K, ? extends V> $this$maxOfWithOrNull, Comparator<? super R> comparator, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> selector) {
        Intrinsics.checkNotNullParameter($this$maxOfWithOrNull, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator it = $this$maxOfWithOrNull.entrySet().iterator();
        if (!it.hasNext()) {
            return null;
        }
        R invoke = selector.invoke(it.next());
        while (it.hasNext()) {
            R invoke2 = selector.invoke(it.next());
            if (comparator.compare(invoke, invoke2) < 0) {
                invoke = invoke2;
            }
        }
        return invoke;
    }

    private static final <K, V> Map.Entry<K, V> maxWithOrThrow(Map<? extends K, ? extends V> $this$maxWith, Comparator<? super Map.Entry<? extends K, ? extends V>> comparator) {
        Intrinsics.checkNotNullParameter($this$maxWith, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return (Map.Entry) CollectionsKt.maxWithOrThrow($this$maxWith.entrySet(), comparator);
    }

    private static final <K, V> Map.Entry<K, V> maxWithOrNull(Map<? extends K, ? extends V> $this$maxWithOrNull, Comparator<? super Map.Entry<? extends K, ? extends V>> comparator) {
        Intrinsics.checkNotNullParameter($this$maxWithOrNull, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return (Map.Entry) CollectionsKt.maxWithOrNull($this$maxWithOrNull.entrySet(), comparator);
    }

    private static final <K, V, R extends Comparable<? super R>> Map.Entry<K, V> minByOrThrow(Map<? extends K, ? extends V> $this$minBy, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> selector) {
        Intrinsics.checkNotNullParameter($this$minBy, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator iterator$iv = $this$minBy.entrySet().iterator();
        if (iterator$iv.hasNext()) {
            Object minElem$iv = iterator$iv.next();
            if (iterator$iv.hasNext()) {
                Comparable minValue$iv = (Comparable) selector.invoke(minElem$iv);
                do {
                    Object e$iv = iterator$iv.next();
                    Comparable v$iv = (Comparable) selector.invoke(e$iv);
                    if (minValue$iv.compareTo(v$iv) > 0) {
                        minElem$iv = e$iv;
                        minValue$iv = v$iv;
                    }
                } while (iterator$iv.hasNext());
            }
            return (Map.Entry) minElem$iv;
        }
        throw new NoSuchElementException();
    }

    private static final <K, V, R extends Comparable<? super R>> Map.Entry<K, V> minByOrNull(Map<? extends K, ? extends V> $this$minByOrNull, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> selector) {
        Object minElem$iv;
        Intrinsics.checkNotNullParameter($this$minByOrNull, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator iterator$iv = $this$minByOrNull.entrySet().iterator();
        if (!iterator$iv.hasNext()) {
            minElem$iv = null;
        } else {
            minElem$iv = iterator$iv.next();
            if (iterator$iv.hasNext()) {
                Comparable minValue$iv = (Comparable) selector.invoke(minElem$iv);
                do {
                    Object e$iv = iterator$iv.next();
                    Comparable v$iv = (Comparable) selector.invoke(e$iv);
                    if (minValue$iv.compareTo(v$iv) > 0) {
                        minElem$iv = e$iv;
                        minValue$iv = v$iv;
                    }
                } while (iterator$iv.hasNext());
            }
        }
        return (Map.Entry) minElem$iv;
    }

    private static final <K, V> double minOf(Map<? extends K, ? extends V> $this$minOf, Function1<? super Map.Entry<? extends K, ? extends V>, Double> selector) {
        Intrinsics.checkNotNullParameter($this$minOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator it = $this$minOf.entrySet().iterator();
        if (it.hasNext()) {
            double doubleValue = selector.invoke(it.next()).doubleValue();
            while (it.hasNext()) {
                doubleValue = Math.min(doubleValue, selector.invoke(it.next()).doubleValue());
            }
            return doubleValue;
        }
        throw new NoSuchElementException();
    }

    /* renamed from: minOf  reason: collision with other method in class */
    private static final <K, V> float m1700minOf(Map<? extends K, ? extends V> $this$minOf, Function1<? super Map.Entry<? extends K, ? extends V>, Float> selector) {
        Intrinsics.checkNotNullParameter($this$minOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator it = $this$minOf.entrySet().iterator();
        if (it.hasNext()) {
            float floatValue = selector.invoke(it.next()).floatValue();
            while (it.hasNext()) {
                floatValue = Math.min(floatValue, selector.invoke(it.next()).floatValue());
            }
            return floatValue;
        }
        throw new NoSuchElementException();
    }

    /* renamed from: minOf  reason: collision with other method in class */
    private static final <K, V, R extends Comparable<? super R>> R m1701minOf(Map<? extends K, ? extends V> $this$minOf, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> selector) {
        Intrinsics.checkNotNullParameter($this$minOf, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator it = $this$minOf.entrySet().iterator();
        if (it.hasNext()) {
            R r = (Comparable) selector.invoke(it.next());
            while (it.hasNext()) {
                R r2 = (Comparable) selector.invoke(it.next());
                if (r.compareTo(r2) > 0) {
                    r = r2;
                }
            }
            return r;
        }
        throw new NoSuchElementException();
    }

    /* renamed from: minOfOrNull  reason: collision with other method in class */
    private static final <K, V> Double m1702minOfOrNull(Map<? extends K, ? extends V> $this$minOfOrNull, Function1<? super Map.Entry<? extends K, ? extends V>, Double> selector) {
        Intrinsics.checkNotNullParameter($this$minOfOrNull, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator it = $this$minOfOrNull.entrySet().iterator();
        if (!it.hasNext()) {
            return null;
        }
        double doubleValue = selector.invoke(it.next()).doubleValue();
        while (it.hasNext()) {
            doubleValue = Math.min(doubleValue, selector.invoke(it.next()).doubleValue());
        }
        return Double.valueOf(doubleValue);
    }

    /* renamed from: minOfOrNull  reason: collision with other method in class */
    private static final <K, V> Float m1703minOfOrNull(Map<? extends K, ? extends V> $this$minOfOrNull, Function1<? super Map.Entry<? extends K, ? extends V>, Float> selector) {
        Intrinsics.checkNotNullParameter($this$minOfOrNull, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator it = $this$minOfOrNull.entrySet().iterator();
        if (!it.hasNext()) {
            return null;
        }
        float floatValue = selector.invoke(it.next()).floatValue();
        while (it.hasNext()) {
            floatValue = Math.min(floatValue, selector.invoke(it.next()).floatValue());
        }
        return Float.valueOf(floatValue);
    }

    private static final <K, V, R extends Comparable<? super R>> R minOfOrNull(Map<? extends K, ? extends V> $this$minOfOrNull, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> selector) {
        Intrinsics.checkNotNullParameter($this$minOfOrNull, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator it = $this$minOfOrNull.entrySet().iterator();
        if (!it.hasNext()) {
            return null;
        }
        R r = (Comparable) selector.invoke(it.next());
        while (it.hasNext()) {
            R r2 = (Comparable) selector.invoke(it.next());
            if (r.compareTo(r2) > 0) {
                r = r2;
            }
        }
        return r;
    }

    private static final <K, V, R> R minOfWith(Map<? extends K, ? extends V> $this$minOfWith, Comparator<? super R> comparator, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> selector) {
        Intrinsics.checkNotNullParameter($this$minOfWith, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator it = $this$minOfWith.entrySet().iterator();
        if (it.hasNext()) {
            R invoke = selector.invoke(it.next());
            while (it.hasNext()) {
                R invoke2 = selector.invoke(it.next());
                if (comparator.compare(invoke, invoke2) > 0) {
                    invoke = invoke2;
                }
            }
            return invoke;
        }
        throw new NoSuchElementException();
    }

    private static final <K, V, R> R minOfWithOrNull(Map<? extends K, ? extends V> $this$minOfWithOrNull, Comparator<? super R> comparator, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> selector) {
        Intrinsics.checkNotNullParameter($this$minOfWithOrNull, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        Intrinsics.checkNotNullParameter(selector, "selector");
        Iterator it = $this$minOfWithOrNull.entrySet().iterator();
        if (!it.hasNext()) {
            return null;
        }
        R invoke = selector.invoke(it.next());
        while (it.hasNext()) {
            R invoke2 = selector.invoke(it.next());
            if (comparator.compare(invoke, invoke2) > 0) {
                invoke = invoke2;
            }
        }
        return invoke;
    }

    private static final <K, V> Map.Entry<K, V> minWithOrThrow(Map<? extends K, ? extends V> $this$minWith, Comparator<? super Map.Entry<? extends K, ? extends V>> comparator) {
        Intrinsics.checkNotNullParameter($this$minWith, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return (Map.Entry) CollectionsKt.minWithOrThrow($this$minWith.entrySet(), comparator);
    }

    private static final <K, V> Map.Entry<K, V> minWithOrNull(Map<? extends K, ? extends V> $this$minWithOrNull, Comparator<? super Map.Entry<? extends K, ? extends V>> comparator) {
        Intrinsics.checkNotNullParameter($this$minWithOrNull, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return (Map.Entry) CollectionsKt.minWithOrNull($this$minWithOrNull.entrySet(), comparator);
    }

    public static final <K, V> boolean none(Map<? extends K, ? extends V> $this$none) {
        Intrinsics.checkNotNullParameter($this$none, "<this>");
        return $this$none.isEmpty();
    }

    public static final <K, V> boolean none(Map<? extends K, ? extends V> $this$none, Function1<? super Map.Entry<? extends K, ? extends V>, Boolean> predicate) {
        Intrinsics.checkNotNullParameter($this$none, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        if ($this$none.isEmpty()) {
            return true;
        }
        for (Map.Entry<? extends K, ? extends V> element : $this$none.entrySet()) {
            if (predicate.invoke(element).booleanValue()) {
                return false;
            }
        }
        return true;
    }

    public static final <K, V, M extends Map<? extends K, ? extends V>> M onEach(M $this$onEach, Function1<? super Map.Entry<? extends K, ? extends V>, Unit> action) {
        Intrinsics.checkNotNullParameter($this$onEach, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        for (Map.Entry element : $this$onEach.entrySet()) {
            action.invoke(element);
        }
        return $this$onEach;
    }

    public static final <K, V, M extends Map<? extends K, ? extends V>> M onEachIndexed(M $this$onEachIndexed, Function2<? super Integer, ? super Map.Entry<? extends K, ? extends V>, Unit> action) {
        Intrinsics.checkNotNullParameter($this$onEachIndexed, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        int index$iv = 0;
        for (Object item$iv : $this$onEachIndexed.entrySet()) {
            int index$iv2 = index$iv + 1;
            if (index$iv < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            action.invoke(Integer.valueOf(index$iv), item$iv);
            index$iv = index$iv2;
        }
        return $this$onEachIndexed;
    }

    private static final <K, V> Iterable<Map.Entry<K, V>> asIterable(Map<? extends K, ? extends V> $this$asIterable) {
        Intrinsics.checkNotNullParameter($this$asIterable, "<this>");
        return $this$asIterable.entrySet();
    }

    public static final <K, V> Sequence<Map.Entry<K, V>> asSequence(Map<? extends K, ? extends V> $this$asSequence) {
        Intrinsics.checkNotNullParameter($this$asSequence, "<this>");
        return CollectionsKt.asSequence($this$asSequence.entrySet());
    }
}
