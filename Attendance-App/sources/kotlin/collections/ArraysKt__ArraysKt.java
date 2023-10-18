package kotlin.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.collections.unsigned.UArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

@Metadata(mo112d1 = {"\u0000H\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a5\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u00032\u0010\u0010\u0004\u001a\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u0003H\u0001¢\u0006\u0004\b\u0005\u0010\u0006\u001a#\u0010\u0007\u001a\u00020\b\"\u0004\b\u0000\u0010\u0002*\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u0003H\u0001¢\u0006\u0004\b\t\u0010\n\u001a?\u0010\u000b\u001a\u00020\f\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\n\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\u0010\u0010\u0010\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0011H\u0002¢\u0006\u0004\b\u0012\u0010\u0013\u001a+\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0015\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00030\u0003¢\u0006\u0002\u0010\u0016\u001a;\u0010\u0017\u001a\u0002H\u0018\"\u0010\b\u0000\u0010\u0019*\u0006\u0012\u0002\b\u00030\u0003*\u0002H\u0018\"\u0004\b\u0001\u0010\u0018*\u0002H\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001bH\bø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a)\u0010\u001d\u001a\u00020\u0001*\b\u0012\u0002\b\u0003\u0018\u00010\u0003H\b\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000¢\u0006\u0002\u0010\u001e\u001aG\u0010\u001f\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u00150 \"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0018*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00180 0\u0003¢\u0006\u0002\u0010!\u0002\u0007\n\u0005\b20\u0001¨\u0006\""}, mo113d2 = {"contentDeepEqualsImpl", "", "T", "", "other", "contentDeepEquals", "([Ljava/lang/Object;[Ljava/lang/Object;)Z", "contentDeepToStringImpl", "", "contentDeepToString", "([Ljava/lang/Object;)Ljava/lang/String;", "contentDeepToStringInternal", "", "result", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "processed", "", "contentDeepToStringInternal$ArraysKt__ArraysKt", "([Ljava/lang/Object;Ljava/lang/StringBuilder;Ljava/util/List;)V", "flatten", "", "([[Ljava/lang/Object;)Ljava/util/List;", "ifEmpty", "R", "C", "defaultValue", "Lkotlin/Function0;", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNullOrEmpty", "([Ljava/lang/Object;)Z", "unzip", "Lkotlin/Pair;", "([Lkotlin/Pair;)Lkotlin/Pair;", "kotlin-stdlib"}, mo114k = 5, mo115mv = {1, 7, 1}, mo117xi = 49, mo118xs = "kotlin/collections/ArraysKt")
/* compiled from: Arrays.kt */
class ArraysKt__ArraysKt extends ArraysKt__ArraysJVMKt {
    public static final <T> List<T> flatten(T[][] $this$flatten) {
        Intrinsics.checkNotNullParameter($this$flatten, "<this>");
        int i = 0;
        for (Object obj : (Object[]) $this$flatten) {
            i += ((Object[]) obj).length;
        }
        ArrayList result = new ArrayList(i);
        int length = ((Object[]) $this$flatten).length;
        for (int i2 = 0; i2 < length; i2++) {
            CollectionsKt.addAll(result, (T[]) $this$flatten[i2]);
        }
        return result;
    }

    public static final <T, R> Pair<List<T>, List<R>> unzip(Pair<? extends T, ? extends R>[] $this$unzip) {
        Intrinsics.checkNotNullParameter($this$unzip, "<this>");
        ArrayList listT = new ArrayList($this$unzip.length);
        ArrayList listR = new ArrayList($this$unzip.length);
        for (Pair pair : $this$unzip) {
            listT.add(pair.getFirst());
            listR.add(pair.getSecond());
        }
        return TuplesKt.m281to(listT, listR);
    }

    private static final boolean isNullOrEmpty(Object[] $this$isNullOrEmpty) {
        if ($this$isNullOrEmpty == null) {
            return true;
        }
        return $this$isNullOrEmpty.length == 0;
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [C] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final <C extends java.lang.Object & R, R> R ifEmpty(C r1, kotlin.jvm.functions.Function0<? extends R> r2) {
        /*
            java.lang.String r0 = "defaultValue"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r2, r0)
            int r0 = r1.length
            if (r0 != 0) goto L_0x000a
            r0 = 1
            goto L_0x000b
        L_0x000a:
            r0 = 0
        L_0x000b:
            if (r0 == 0) goto L_0x0012
            java.lang.Object r0 = r2.invoke()
            goto L_0x0013
        L_0x0012:
            r0 = r1
        L_0x0013:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.collections.ArraysKt__ArraysKt.ifEmpty(java.lang.Object[], kotlin.jvm.functions.Function0):java.lang.Object");
    }

    public static final <T> boolean contentDeepEquals(T[] $this$contentDeepEqualsImpl, T[] other) {
        if ($this$contentDeepEqualsImpl == other) {
            return true;
        }
        if ($this$contentDeepEqualsImpl == null || other == null || $this$contentDeepEqualsImpl.length != other.length) {
            return false;
        }
        int length = $this$contentDeepEqualsImpl.length;
        for (int i = 0; i < length; i++) {
            Object v1 = $this$contentDeepEqualsImpl[i];
            Object v2 = other[i];
            if (v1 != v2) {
                if (v1 == null || v2 == null) {
                    return false;
                }
                if (!(v1 instanceof Object[]) || !(v2 instanceof Object[])) {
                    if (!(v1 instanceof byte[]) || !(v2 instanceof byte[])) {
                        if (!(v1 instanceof short[]) || !(v2 instanceof short[])) {
                            if (!(v1 instanceof int[]) || !(v2 instanceof int[])) {
                                if (!(v1 instanceof long[]) || !(v2 instanceof long[])) {
                                    if (!(v1 instanceof float[]) || !(v2 instanceof float[])) {
                                        if (!(v1 instanceof double[]) || !(v2 instanceof double[])) {
                                            if (!(v1 instanceof char[]) || !(v2 instanceof char[])) {
                                                if (!(v1 instanceof boolean[]) || !(v2 instanceof boolean[])) {
                                                    if (!(v1 instanceof UByteArray) || !(v2 instanceof UByteArray)) {
                                                        if (!(v1 instanceof UShortArray) || !(v2 instanceof UShortArray)) {
                                                            if (!(v1 instanceof UIntArray) || !(v2 instanceof UIntArray)) {
                                                                if (!(v1 instanceof ULongArray) || !(v2 instanceof ULongArray)) {
                                                                    if (!Intrinsics.areEqual(v1, v2)) {
                                                                        return false;
                                                                    }
                                                                } else if (!UArraysKt.m464contentEqualslec5QzE(((ULongArray) v1).m1585unboximpl(), ((ULongArray) v2).m1585unboximpl())) {
                                                                    return false;
                                                                }
                                                            } else if (!UArraysKt.m460contentEqualsKJPZfPQ(((UIntArray) v1).m1507unboximpl(), ((UIntArray) v2).m1507unboximpl())) {
                                                                return false;
                                                            }
                                                        } else if (!UArraysKt.m459contentEqualsFGO6Aew(((UShortArray) v1).m1689unboximpl(), ((UShortArray) v2).m1689unboximpl())) {
                                                            return false;
                                                        }
                                                    } else if (!UArraysKt.m462contentEqualskV0jMPg(((UByteArray) v1).m1429unboximpl(), ((UByteArray) v2).m1429unboximpl())) {
                                                        return false;
                                                    }
                                                } else if (!Arrays.equals((boolean[]) v1, (boolean[]) v2)) {
                                                    return false;
                                                }
                                            } else if (!Arrays.equals((char[]) v1, (char[]) v2)) {
                                                return false;
                                            }
                                        } else if (!Arrays.equals((double[]) v1, (double[]) v2)) {
                                            return false;
                                        }
                                    } else if (!Arrays.equals((float[]) v1, (float[]) v2)) {
                                        return false;
                                    }
                                } else if (!Arrays.equals((long[]) v1, (long[]) v2)) {
                                    return false;
                                }
                            } else if (!Arrays.equals((int[]) v1, (int[]) v2)) {
                                return false;
                            }
                        } else if (!Arrays.equals((short[]) v1, (short[]) v2)) {
                            return false;
                        }
                    } else if (!Arrays.equals((byte[]) v1, (byte[]) v2)) {
                        return false;
                    }
                } else if (!ArraysKt.contentDeepEquals((Object[]) v1, (Object[]) v2)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static final <T> String contentDeepToString(T[] $this$contentDeepToStringImpl) {
        if ($this$contentDeepToStringImpl == null) {
            return "null";
        }
        StringBuilder $this$contentDeepToStringImpl_u24lambda_u2d2 = new StringBuilder((RangesKt.coerceAtMost($this$contentDeepToStringImpl.length, 429496729) * 5) + 2);
        contentDeepToStringInternal$ArraysKt__ArraysKt($this$contentDeepToStringImpl, $this$contentDeepToStringImpl_u24lambda_u2d2, new ArrayList());
        String sb = $this$contentDeepToStringImpl_u24lambda_u2d2.toString();
        Intrinsics.checkNotNullExpressionValue(sb, "StringBuilder(capacity).…builderAction).toString()");
        return sb;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v1, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: long[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: short[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v8, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v11, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v12, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v13, resolved type: byte[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final <T> void contentDeepToStringInternal$ArraysKt__ArraysKt(T[] r5, java.lang.StringBuilder r6, java.util.List<java.lang.Object[]> r7) {
        /*
            boolean r0 = r7.contains(r5)
            if (r0 == 0) goto L_0x000c
            java.lang.String r0 = "[...]"
            r6.append(r0)
            return
        L_0x000c:
            r7.add(r5)
            r0 = 91
            r6.append(r0)
            r0 = 0
            int r1 = r5.length
        L_0x0016:
            if (r0 >= r1) goto L_0x0132
            if (r0 == 0) goto L_0x001f
            java.lang.String r2 = ", "
            r6.append(r2)
        L_0x001f:
            r2 = r5[r0]
            if (r2 != 0) goto L_0x002c
            java.lang.String r3 = "null"
            r6.append(r3)
            goto L_0x012e
        L_0x002c:
            boolean r3 = r2 instanceof java.lang.Object[]
            if (r3 == 0) goto L_0x0038
            r3 = r2
            java.lang.Object[] r3 = (java.lang.Object[]) r3
            contentDeepToStringInternal$ArraysKt__ArraysKt(r3, r6, r7)
            goto L_0x012e
        L_0x0038:
            boolean r3 = r2 instanceof byte[]
            java.lang.String r4 = "toString(this)"
            if (r3 == 0) goto L_0x004e
            r3 = r2
            byte[] r3 = (byte[]) r3
            java.lang.String r3 = java.util.Arrays.toString(r3)
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r4)
            r6.append(r3)
            goto L_0x012e
        L_0x004e:
            boolean r3 = r2 instanceof short[]
            if (r3 == 0) goto L_0x0061
            r3 = r2
            short[] r3 = (short[]) r3
            java.lang.String r3 = java.util.Arrays.toString(r3)
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r4)
            r6.append(r3)
            goto L_0x012e
        L_0x0061:
            boolean r3 = r2 instanceof int[]
            if (r3 == 0) goto L_0x0074
            r3 = r2
            int[] r3 = (int[]) r3
            java.lang.String r3 = java.util.Arrays.toString(r3)
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r4)
            r6.append(r3)
            goto L_0x012e
        L_0x0074:
            boolean r3 = r2 instanceof long[]
            if (r3 == 0) goto L_0x0087
            r3 = r2
            long[] r3 = (long[]) r3
            java.lang.String r3 = java.util.Arrays.toString(r3)
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r4)
            r6.append(r3)
            goto L_0x012e
        L_0x0087:
            boolean r3 = r2 instanceof float[]
            if (r3 == 0) goto L_0x009a
            r3 = r2
            float[] r3 = (float[]) r3
            java.lang.String r3 = java.util.Arrays.toString(r3)
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r4)
            r6.append(r3)
            goto L_0x012e
        L_0x009a:
            boolean r3 = r2 instanceof double[]
            if (r3 == 0) goto L_0x00ad
            r3 = r2
            double[] r3 = (double[]) r3
            java.lang.String r3 = java.util.Arrays.toString(r3)
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r4)
            r6.append(r3)
            goto L_0x012e
        L_0x00ad:
            boolean r3 = r2 instanceof char[]
            if (r3 == 0) goto L_0x00c0
            r3 = r2
            char[] r3 = (char[]) r3
            java.lang.String r3 = java.util.Arrays.toString(r3)
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r4)
            r6.append(r3)
            goto L_0x012e
        L_0x00c0:
            boolean r3 = r2 instanceof boolean[]
            if (r3 == 0) goto L_0x00d2
            r3 = r2
            boolean[] r3 = (boolean[]) r3
            java.lang.String r3 = java.util.Arrays.toString(r3)
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r4)
            r6.append(r3)
            goto L_0x012e
        L_0x00d2:
            boolean r3 = r2 instanceof kotlin.UByteArray
            r4 = 0
            if (r3 == 0) goto L_0x00e8
            r3 = r2
            kotlin.UByteArray r3 = (kotlin.UByteArray) r3
            if (r3 == 0) goto L_0x00e0
            byte[] r4 = r3.m1429unboximpl()
        L_0x00e0:
            java.lang.String r3 = kotlin.collections.unsigned.UArraysKt.m476contentToString2csIQuQ(r4)
            r6.append(r3)
            goto L_0x012e
        L_0x00e8:
            boolean r3 = r2 instanceof kotlin.UShortArray
            if (r3 == 0) goto L_0x00fd
            r3 = r2
            kotlin.UShortArray r3 = (kotlin.UShortArray) r3
            if (r3 == 0) goto L_0x00f5
            short[] r4 = r3.m1689unboximpl()
        L_0x00f5:
            java.lang.String r3 = kotlin.collections.unsigned.UArraysKt.m480contentToStringd6D3K8(r4)
            r6.append(r3)
            goto L_0x012e
        L_0x00fd:
            boolean r3 = r2 instanceof kotlin.UIntArray
            if (r3 == 0) goto L_0x0112
            r3 = r2
            kotlin.UIntArray r3 = (kotlin.UIntArray) r3
            if (r3 == 0) goto L_0x010a
            int[] r4 = r3.m1507unboximpl()
        L_0x010a:
            java.lang.String r3 = kotlin.collections.unsigned.UArraysKt.m479contentToStringXUkPCBk(r4)
            r6.append(r3)
            goto L_0x012e
        L_0x0112:
            boolean r3 = r2 instanceof kotlin.ULongArray
            if (r3 == 0) goto L_0x0127
            r3 = r2
            kotlin.ULongArray r3 = (kotlin.ULongArray) r3
            if (r3 == 0) goto L_0x011f
            long[] r4 = r3.m1585unboximpl()
        L_0x011f:
            java.lang.String r3 = kotlin.collections.unsigned.UArraysKt.m482contentToStringuLth9ew(r4)
            r6.append(r3)
            goto L_0x012e
        L_0x0127:
            java.lang.String r3 = r2.toString()
            r6.append(r3)
        L_0x012e:
            int r0 = r0 + 1
            goto L_0x0016
        L_0x0132:
            r0 = 93
            r6.append(r0)
            int r0 = kotlin.collections.CollectionsKt.getLastIndex(r7)
            r7.remove(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.collections.ArraysKt__ArraysKt.contentDeepToStringInternal$ArraysKt__ArraysKt(java.lang.Object[], java.lang.StringBuilder, java.util.List):void");
    }
}
