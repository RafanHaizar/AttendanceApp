package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;

@Metadata(mo112d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u000b\u001a!\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u0002¢\u0006\u0002\b\u0004\u001a\u0011\u0010\u0005\u001a\u00020\u0006*\u00020\u0002H\u0002¢\u0006\u0002\b\u0007\u001a\u0014\u0010\b\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u001aJ\u0010\t\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0014\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001H\b¢\u0006\u0002\b\u000e\u001a\u0014\u0010\u000f\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u0002\u001a\u001e\u0010\u0011\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002\u001a\n\u0010\u0013\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010\u0014\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002¨\u0006\u0015"}, mo113d2 = {"getIndentFunction", "Lkotlin/Function1;", "", "indent", "getIndentFunction$StringsKt__IndentKt", "indentWidth", "", "indentWidth$StringsKt__IndentKt", "prependIndent", "reindent", "", "resultSizeEstimate", "indentAddFunction", "indentCutFunction", "reindent$StringsKt__IndentKt", "replaceIndent", "newIndent", "replaceIndentByMargin", "marginPrefix", "trimIndent", "trimMargin", "kotlin-stdlib"}, mo114k = 5, mo115mv = {1, 7, 1}, mo117xi = 49, mo118xs = "kotlin/text/StringsKt")
/* compiled from: Indent.kt */
class StringsKt__IndentKt extends StringsKt__AppendableKt {
    public static /* synthetic */ String trimMargin$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "|";
        }
        return StringsKt.trimMargin(str, str2);
    }

    public static final String trimMargin(String $this$trimMargin, String marginPrefix) {
        Intrinsics.checkNotNullParameter($this$trimMargin, "<this>");
        Intrinsics.checkNotNullParameter(marginPrefix, "marginPrefix");
        return StringsKt.replaceIndentByMargin($this$trimMargin, "", marginPrefix);
    }

    public static /* synthetic */ String replaceIndentByMargin$default(String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "";
        }
        if ((i & 2) != 0) {
            str3 = "|";
        }
        return StringsKt.replaceIndentByMargin(str, str2, str3);
    }

    public static final String replaceIndentByMargin(String $this$replaceIndentByMargin, String newIndent, String marginPrefix) {
        Collection destination$iv$iv$iv;
        String str;
        String str2 = $this$replaceIndentByMargin;
        String str3 = marginPrefix;
        Intrinsics.checkNotNullParameter(str2, "<this>");
        Intrinsics.checkNotNullParameter(newIndent, "newIndent");
        Intrinsics.checkNotNullParameter(str3, "marginPrefix");
        if (!StringsKt.isBlank(str3)) {
            List lines = StringsKt.lines(str2);
            int resultSizeEstimate$iv = $this$replaceIndentByMargin.length() + (newIndent.length() * lines.size());
            Function1 indentAddFunction$iv = getIndentFunction$StringsKt__IndentKt(newIndent);
            List $this$reindent$iv = lines;
            int lastIndex$iv = CollectionsKt.getLastIndex($this$reindent$iv);
            Collection destination$iv$iv$iv2 = new ArrayList();
            int index$iv$iv$iv$iv = 0;
            for (Object item$iv$iv$iv$iv : $this$reindent$iv) {
                int index$iv$iv$iv$iv2 = index$iv$iv$iv$iv + 1;
                if (index$iv$iv$iv$iv < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                String value$iv = (String) item$iv$iv$iv$iv;
                int index$iv = index$iv$iv$iv$iv;
                String it$iv$iv$iv = null;
                if ((index$iv == 0 || index$iv == lastIndex$iv) && StringsKt.isBlank(value$iv)) {
                    int i = index$iv;
                    destination$iv$iv$iv = destination$iv$iv$iv2;
                } else {
                    String line = value$iv;
                    CharSequence $this$indexOfFirst$iv = line;
                    int $i$f$indexOfFirst = 0;
                    int index$iv2 = 0;
                    int length = $this$indexOfFirst$iv.length();
                    while (true) {
                        int $i$f$indexOfFirst2 = $i$f$indexOfFirst;
                        if (index$iv2 >= length) {
                            index$iv2 = -1;
                            break;
                        } else if ((CharsKt.isWhitespace($this$indexOfFirst$iv.charAt(index$iv2)) ^ 1) != 0) {
                            break;
                        } else {
                            index$iv2++;
                            $i$f$indexOfFirst = $i$f$indexOfFirst2;
                        }
                    }
                    int firstNonWhitespaceIndex = index$iv2;
                    if (firstNonWhitespaceIndex == -1) {
                        String str4 = line;
                        int i2 = index$iv;
                        destination$iv$iv$iv = destination$iv$iv$iv2;
                        str = null;
                    } else {
                        String line2 = line;
                        int i3 = index$iv;
                        destination$iv$iv$iv = destination$iv$iv$iv2;
                        if (StringsKt.startsWith$default(line, marginPrefix, firstNonWhitespaceIndex, false, 4, (Object) null)) {
                            String line3 = line2;
                            Intrinsics.checkNotNull(line3, "null cannot be cast to non-null type java.lang.String");
                            str = line3.substring(marginPrefix.length() + firstNonWhitespaceIndex);
                            Intrinsics.checkNotNullExpressionValue(str, "this as java.lang.String).substring(startIndex)");
                        } else {
                            str = null;
                        }
                    }
                    if (str == null || (it$iv$iv$iv = (String) indentAddFunction$iv.invoke(str)) == null) {
                        it$iv$iv$iv = value$iv;
                    }
                }
                if (it$iv$iv$iv != null) {
                    destination$iv$iv$iv.add(it$iv$iv$iv);
                }
                String str5 = $this$replaceIndentByMargin;
                destination$iv$iv$iv2 = destination$iv$iv$iv;
                index$iv$iv$iv$iv = index$iv$iv$iv$iv2;
                String str6 = marginPrefix;
            }
            String sb = ((StringBuilder) CollectionsKt.joinTo$default((List) destination$iv$iv$iv2, new StringBuilder(resultSizeEstimate$iv), "\n", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 124, (Object) null)).toString();
            Intrinsics.checkNotNullExpressionValue(sb, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
            return sb;
        }
        throw new IllegalArgumentException("marginPrefix must be non-blank string.".toString());
    }

    public static final String trimIndent(String $this$trimIndent) {
        Intrinsics.checkNotNullParameter($this$trimIndent, "<this>");
        return StringsKt.replaceIndent($this$trimIndent, "");
    }

    public static /* synthetic */ String replaceIndent$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "";
        }
        return StringsKt.replaceIndent(str, str2);
    }

    public static final String replaceIndent(String $this$replaceIndent, String newIndent) {
        int minCommonIndent;
        String it$iv$iv$iv;
        String str = $this$replaceIndent;
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(newIndent, "newIndent");
        List $this$filter$iv = StringsKt.lines(str);
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            if (!StringsKt.isBlank((String) element$iv$iv)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable<String> $this$map$iv = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (String p0 : $this$map$iv) {
            destination$iv$iv2.add(Integer.valueOf(indentWidth$StringsKt__IndentKt(p0)));
        }
        Integer num = (Integer) CollectionsKt.minOrNull((List) destination$iv$iv2);
        if (num != null) {
            minCommonIndent = num.intValue();
        } else {
            minCommonIndent = 0;
        }
        int resultSizeEstimate$iv = $this$replaceIndent.length() + (newIndent.length() * $this$filter$iv.size());
        Function1 indentAddFunction$iv = getIndentFunction$StringsKt__IndentKt(newIndent);
        List $this$reindent$iv = $this$filter$iv;
        int lastIndex$iv = CollectionsKt.getLastIndex($this$reindent$iv);
        Collection destination$iv$iv$iv = new ArrayList();
        int index$iv$iv$iv$iv = 0;
        for (Object item$iv$iv$iv$iv : $this$reindent$iv) {
            int index$iv$iv$iv$iv2 = index$iv$iv$iv$iv + 1;
            if (index$iv$iv$iv$iv < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String value$iv = (String) item$iv$iv$iv$iv;
            int index$iv = index$iv$iv$iv$iv;
            if ((index$iv == 0 || index$iv == lastIndex$iv) && StringsKt.isBlank(value$iv)) {
                it$iv$iv$iv = null;
                int i = index$iv;
            } else {
                int i2 = index$iv;
                String line = StringsKt.drop(value$iv, minCommonIndent);
                if (line == null || (it$iv$iv$iv = (String) indentAddFunction$iv.invoke(line)) == null) {
                    it$iv$iv$iv = value$iv;
                }
            }
            if (it$iv$iv$iv != null) {
                destination$iv$iv$iv.add(it$iv$iv$iv);
            }
            String str2 = $this$replaceIndent;
            index$iv$iv$iv$iv = index$iv$iv$iv$iv2;
        }
        String sb = ((StringBuilder) CollectionsKt.joinTo$default((List) destination$iv$iv$iv, new StringBuilder(resultSizeEstimate$iv), "\n", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 124, (Object) null)).toString();
        Intrinsics.checkNotNullExpressionValue(sb, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
        return sb;
    }

    public static /* synthetic */ String prependIndent$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "    ";
        }
        return StringsKt.prependIndent(str, str2);
    }

    public static final String prependIndent(String $this$prependIndent, String indent) {
        Intrinsics.checkNotNullParameter($this$prependIndent, "<this>");
        Intrinsics.checkNotNullParameter(indent, "indent");
        return SequencesKt.joinToString$default(SequencesKt.map(StringsKt.lineSequence($this$prependIndent), new StringsKt__IndentKt$prependIndent$1(indent)), "\n", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null);
    }

    private static final int indentWidth$StringsKt__IndentKt(String $this$indentWidth) {
        CharSequence $this$indexOfFirst$iv = $this$indentWidth;
        int index$iv = 0;
        int length = $this$indexOfFirst$iv.length();
        while (true) {
            if (index$iv >= length) {
                index$iv = -1;
                break;
            } else if ((CharsKt.isWhitespace($this$indexOfFirst$iv.charAt(index$iv)) ^ 1) != 0) {
                break;
            } else {
                index$iv++;
            }
        }
        int it = index$iv;
        return it == -1 ? $this$indentWidth.length() : it;
    }

    private static final Function1<String, String> getIndentFunction$StringsKt__IndentKt(String indent) {
        if (indent.length() == 0) {
            return StringsKt__IndentKt$getIndentFunction$1.INSTANCE;
        }
        return new StringsKt__IndentKt$getIndentFunction$2(indent);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x006d, code lost:
        if (r0 == null) goto L_0x0074;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final java.lang.String reindent$StringsKt__IndentKt(java.util.List<java.lang.String> r21, int r22, kotlin.jvm.functions.Function1<? super java.lang.String, java.lang.String> r23, kotlin.jvm.functions.Function1<? super java.lang.String, java.lang.String> r24) {
        /*
            r0 = 0
            int r1 = kotlin.collections.CollectionsKt.getLastIndex(r21)
            r2 = r21
            java.lang.Iterable r2 = (java.lang.Iterable) r2
            r3 = 0
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.util.Collection r4 = (java.util.Collection) r4
            r5 = r2
            r6 = 0
            r7 = r5
            r8 = 0
            r9 = 0
            java.util.Iterator r10 = r7.iterator()
        L_0x001a:
            boolean r11 = r10.hasNext()
            if (r11 == 0) goto L_0x0084
            java.lang.Object r11 = r10.next()
            int r12 = r9 + 1
            if (r9 >= 0) goto L_0x002b
            kotlin.collections.CollectionsKt.throwIndexOverflow()
        L_0x002b:
            r13 = r11
            r14 = 0
            r15 = r13
            java.lang.String r15 = (java.lang.String) r15
            r16 = r9
            r17 = r16
            r16 = 0
            r18 = r0
            r0 = r17
            if (r0 == 0) goto L_0x003e
            if (r0 != r1) goto L_0x0055
        L_0x003e:
            r17 = r15
            java.lang.CharSequence r17 = (java.lang.CharSequence) r17
            boolean r17 = kotlin.text.StringsKt.isBlank(r17)
            if (r17 == 0) goto L_0x0055
            r17 = 0
            r19 = r1
            r1 = r23
            r20 = r17
            r17 = r0
            r0 = r20
            goto L_0x0075
        L_0x0055:
            r17 = r0
            r0 = r24
            java.lang.Object r19 = r0.invoke(r15)
            r0 = r19
            java.lang.String r0 = (java.lang.String) r0
            if (r0 == 0) goto L_0x0070
            r19 = r1
            r1 = r23
            java.lang.Object r0 = r1.invoke(r0)
            java.lang.String r0 = (java.lang.String) r0
            if (r0 != 0) goto L_0x0075
            goto L_0x0074
        L_0x0070:
            r19 = r1
            r1 = r23
        L_0x0074:
            r0 = r15
        L_0x0075:
            if (r0 == 0) goto L_0x007d
            r15 = 0
            r4.add(r0)
            goto L_0x007e
        L_0x007d:
        L_0x007e:
            r9 = r12
            r0 = r18
            r1 = r19
            goto L_0x001a
        L_0x0084:
            r18 = r0
            r0 = r4
            java.util.List r0 = (java.util.List) r0
            r4 = r0
            java.lang.Iterable r4 = (java.lang.Iterable) r4
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r2 = r22
            r0.<init>(r2)
            r5 = r0
            java.lang.Appendable r5 = (java.lang.Appendable) r5
            java.lang.String r0 = "\n"
            r6 = r0
            java.lang.CharSequence r6 = (java.lang.CharSequence) r6
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 124(0x7c, float:1.74E-43)
            r13 = 0
            java.lang.Appendable r0 = kotlin.collections.CollectionsKt.joinTo$default(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            java.lang.StringBuilder r0 = (java.lang.StringBuilder) r0
            java.lang.String r0 = r0.toString()
            java.lang.String r3 = "mapIndexedNotNull { inde…\"\\n\")\n        .toString()"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.text.StringsKt__IndentKt.reindent$StringsKt__IndentKt(java.util.List, int, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1):java.lang.String");
    }
}
