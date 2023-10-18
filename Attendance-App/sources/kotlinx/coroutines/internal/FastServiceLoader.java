package kotlinx.coroutines.internal;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.jar.JarFile;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

@Metadata(mo112d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J!\u0010\u0005\u001a\u0004\u0018\u00010\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\u0006\u0010\t\u001a\u00020\u0004H\bJ1\u0010\n\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u000b2\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\bH\u0002¢\u0006\u0002\u0010\u0010J*\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0012\"\u0004\b\u0000\u0010\u000b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\b2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0013\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00060\u0012H\u0000¢\u0006\u0002\b\u0014J/\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0012\"\u0004\b\u0000\u0010\u000b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\b2\u0006\u0010\r\u001a\u00020\u000eH\u0000¢\u0006\u0002\b\u0016J\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\u00122\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00040\u00122\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J,\u0010\u001d\u001a\u0002H\u001e\"\u0004\b\u0000\u0010\u001e*\u00020\u001f2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u0002H\u001e0!H\b¢\u0006\u0002\u0010\"R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000¨\u0006#"}, mo113d2 = {"Lkotlinx/coroutines/internal/FastServiceLoader;", "", "()V", "PREFIX", "", "createInstanceOf", "Lkotlinx/coroutines/internal/MainDispatcherFactory;", "baseClass", "Ljava/lang/Class;", "serviceClass", "getProviderInstance", "S", "name", "loader", "Ljava/lang/ClassLoader;", "service", "(Ljava/lang/String;Ljava/lang/ClassLoader;Ljava/lang/Class;)Ljava/lang/Object;", "load", "", "loadMainDispatcherFactory", "loadMainDispatcherFactory$kotlinx_coroutines_core", "loadProviders", "loadProviders$kotlinx_coroutines_core", "parse", "url", "Ljava/net/URL;", "parseFile", "r", "Ljava/io/BufferedReader;", "use", "R", "Ljava/util/jar/JarFile;", "block", "Lkotlin/Function1;", "(Ljava/util/jar/JarFile;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: FastServiceLoader.kt */
public final class FastServiceLoader {
    public static final FastServiceLoader INSTANCE = new FastServiceLoader();
    private static final String PREFIX = "META-INF/services/";

    private FastServiceLoader() {
    }

    public final List<MainDispatcherFactory> loadMainDispatcherFactory$kotlinx_coroutines_core() {
        ArrayList result;
        MainDispatcherFactory $this$loadMainDispatcherFactory_u24lambda_u2d1;
        MainDispatcherFactory $this$loadMainDispatcherFactory_u24lambda_u2d0;
        Class clz = MainDispatcherFactory.class;
        if (!FastServiceLoaderKt.getANDROID_DETECTED()) {
            return load(clz, clz.getClassLoader());
        }
        try {
            result = new ArrayList(2);
            $this$loadMainDispatcherFactory_u24lambda_u2d1 = null;
            $this$loadMainDispatcherFactory_u24lambda_u2d0 = clz.cast(Class.forName("kotlinx.coroutines.android.AndroidDispatcherFactory", true, clz.getClassLoader()).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
        } catch (ClassNotFoundException e) {
            MainDispatcherFactory mainDispatcherFactory = null;
            $this$loadMainDispatcherFactory_u24lambda_u2d0 = null;
        } catch (Throwable th) {
            return load(clz, clz.getClassLoader());
        }
        if ($this$loadMainDispatcherFactory_u24lambda_u2d0 != null) {
            result.add($this$loadMainDispatcherFactory_u24lambda_u2d0);
        }
        try {
            $this$loadMainDispatcherFactory_u24lambda_u2d1 = clz.cast(Class.forName("kotlinx.coroutines.test.internal.TestMainDispatcherFactory", true, clz.getClassLoader()).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
        } catch (ClassNotFoundException e2) {
            MainDispatcherFactory mainDispatcherFactory2 = null;
        }
        if ($this$loadMainDispatcherFactory_u24lambda_u2d1 != null) {
            result.add($this$loadMainDispatcherFactory_u24lambda_u2d1);
        }
        return result;
    }

    private final MainDispatcherFactory createInstanceOf(Class<MainDispatcherFactory> baseClass, String serviceClass) {
        try {
            return baseClass.cast(Class.forName(serviceClass, true, baseClass.getClassLoader()).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
        } catch (ClassNotFoundException e) {
            MainDispatcherFactory mainDispatcherFactory = null;
            return null;
        }
    }

    private final <S> List<S> load(Class<S> service, ClassLoader loader) {
        try {
            return loadProviders$kotlinx_coroutines_core(service, loader);
        } catch (Throwable th) {
            return CollectionsKt.toList(ServiceLoader.load(service, loader));
        }
    }

    public final <S> List<S> loadProviders$kotlinx_coroutines_core(Class<S> service, ClassLoader loader) {
        ArrayList<T> $this$flatMap$iv = Collections.list(loader.getResources(Intrinsics.stringPlus(PREFIX, service.getName())));
        Intrinsics.checkNotNullExpressionValue($this$flatMap$iv, "list(this)");
        Collection destination$iv$iv = new ArrayList();
        for (T it : $this$flatMap$iv) {
            CollectionsKt.addAll(destination$iv$iv, INSTANCE.parse(it));
        }
        Set providers = CollectionsKt.toSet((List) destination$iv$iv);
        if (!providers.isEmpty()) {
            Iterable<String> $this$map$iv = providers;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (String it2 : $this$map$iv) {
                destination$iv$iv2.add(INSTANCE.getProviderInstance(it2, loader, service));
            }
            return (List) destination$iv$iv2;
        }
        throw new IllegalArgumentException("No providers were loaded with FastServiceLoader".toString());
    }

    private final <S> S getProviderInstance(String name, ClassLoader loader, Class<S> service) {
        Class clazz = Class.forName(name, false, loader);
        if (service.isAssignableFrom(clazz)) {
            return service.cast(clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
        }
        throw new IllegalArgumentException(("Expected service of class " + service + ", but found " + clazz).toString());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x005c, code lost:
        r11 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        kotlin.p002io.CloseableKt.closeFinally(r10, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0060, code lost:
        throw r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0093, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0094, code lost:
        kotlin.p002io.CloseableKt.closeFinally(r1, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0097, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.util.List<java.lang.String> parse(java.net.URL r15) {
        /*
            r14 = this;
            java.lang.String r0 = r15.toString()
            java.lang.String r1 = "jar"
            r2 = 0
            r3 = 2
            r4 = 0
            boolean r1 = kotlin.text.StringsKt.startsWith$default(r0, r1, r2, r3, r4)
            if (r1 == 0) goto L_0x0071
            java.lang.String r1 = "jar:file:"
            java.lang.String r1 = kotlin.text.StringsKt.substringAfter$default((java.lang.String) r0, (java.lang.String) r1, (java.lang.String) r4, (int) r3, (java.lang.Object) r4)
            r5 = 33
            java.lang.String r1 = kotlin.text.StringsKt.substringBefore$default((java.lang.String) r1, (char) r5, (java.lang.String) r4, (int) r3, (java.lang.Object) r4)
            java.lang.String r5 = "!/"
            java.lang.String r3 = kotlin.text.StringsKt.substringAfter$default((java.lang.String) r0, (java.lang.String) r5, (java.lang.String) r4, (int) r3, (java.lang.Object) r4)
            java.util.jar.JarFile r5 = new java.util.jar.JarFile
            r5.<init>(r1, r2)
            r2 = r5
            r5 = r14
            r6 = 0
            r7 = 0
            r8 = r2
            r9 = 0
            java.io.BufferedReader r10 = new java.io.BufferedReader     // Catch:{ all -> 0x0061 }
            java.io.InputStreamReader r11 = new java.io.InputStreamReader     // Catch:{ all -> 0x0061 }
            java.util.zip.ZipEntry r12 = new java.util.zip.ZipEntry     // Catch:{ all -> 0x0061 }
            r12.<init>(r3)     // Catch:{ all -> 0x0061 }
            java.io.InputStream r12 = r8.getInputStream(r12)     // Catch:{ all -> 0x0061 }
            java.lang.String r13 = "UTF-8"
            r11.<init>(r12, r13)     // Catch:{ all -> 0x0061 }
            java.io.Reader r11 = (java.io.Reader) r11     // Catch:{ all -> 0x0061 }
            r10.<init>(r11)     // Catch:{ all -> 0x0061 }
            java.io.Closeable r10 = (java.io.Closeable) r10     // Catch:{ all -> 0x0061 }
            r11 = r10
            java.io.BufferedReader r11 = (java.io.BufferedReader) r11     // Catch:{ all -> 0x005a }
            r12 = 0
            kotlinx.coroutines.internal.FastServiceLoader r13 = INSTANCE     // Catch:{ all -> 0x005a }
            java.util.List r13 = r13.parseFile(r11)     // Catch:{ all -> 0x005a }
            kotlin.p002io.CloseableKt.closeFinally(r10, r4)     // Catch:{ all -> 0x0061 }
            r2.close()     // Catch:{ all -> 0x0058 }
            return r13
        L_0x0058:
            r2 = move-exception
            throw r2
        L_0x005a:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x005c }
        L_0x005c:
            r11 = move-exception
            kotlin.p002io.CloseableKt.closeFinally(r10, r4)     // Catch:{ all -> 0x0061 }
            throw r11     // Catch:{ all -> 0x0061 }
        L_0x0061:
            r4 = move-exception
            r7 = r4
            throw r4     // Catch:{ all -> 0x0065 }
        L_0x0065:
            r4 = move-exception
            r2.close()     // Catch:{ all -> 0x006b }
            throw r4
        L_0x006b:
            r4 = move-exception
            kotlin.ExceptionsKt.addSuppressed(r7, r4)
            throw r7
        L_0x0071:
            java.io.BufferedReader r1 = new java.io.BufferedReader
            java.io.InputStreamReader r2 = new java.io.InputStreamReader
            java.io.InputStream r3 = r15.openStream()
            r2.<init>(r3)
            java.io.Reader r2 = (java.io.Reader) r2
            r1.<init>(r2)
            java.io.Closeable r1 = (java.io.Closeable) r1
            r2 = r1
            java.io.BufferedReader r2 = (java.io.BufferedReader) r2     // Catch:{ all -> 0x0091 }
            r3 = 0
            kotlinx.coroutines.internal.FastServiceLoader r5 = INSTANCE     // Catch:{ all -> 0x0091 }
            java.util.List r5 = r5.parseFile(r2)     // Catch:{ all -> 0x0091 }
            kotlin.p002io.CloseableKt.closeFinally(r1, r4)
            return r5
        L_0x0091:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0093 }
        L_0x0093:
            r3 = move-exception
            kotlin.p002io.CloseableKt.closeFinally(r1, r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.FastServiceLoader.parse(java.net.URL):java.util.List");
    }

    private final <R> R use(JarFile $this$use, Function1<? super JarFile, ? extends R> block) {
        Throwable cause;
        try {
            R invoke = block.invoke($this$use);
            InlineMarker.finallyStart(1);
            $this$use.close();
            InlineMarker.finallyEnd(1);
            return invoke;
        } catch (Throwable e) {
            InlineMarker.finallyStart(1);
            try {
                $this$use.close();
                InlineMarker.finallyEnd(1);
                throw e;
            } catch (Throwable closeException) {
                ExceptionsKt.addSuppressed(cause, closeException);
                throw cause;
            }
        }
    }

    private final List<String> parseFile(BufferedReader r) {
        boolean z;
        char it;
        Set names = new LinkedHashSet();
        while (true) {
            String line = r.readLine();
            if (line == null) {
                return CollectionsKt.toList(names);
            }
            String serviceName = StringsKt.trim((CharSequence) StringsKt.substringBefore$default(line, "#", (String) null, 2, (Object) null)).toString();
            CharSequence $this$all$iv = serviceName;
            boolean z2 = false;
            int i = 0;
            while (true) {
                if (i >= $this$all$iv.length()) {
                    z = true;
                    break;
                }
                char element$iv = $this$all$iv.charAt(i);
                i++;
                char it2 = element$iv;
                if (it2 == '.' || Character.isJavaIdentifierPart(it2)) {
                    it = 1;
                    continue;
                } else {
                    it = 0;
                    continue;
                }
                if (it == 0) {
                    z = false;
                    break;
                }
            }
            if (z) {
                if (serviceName.length() > 0) {
                    z2 = true;
                }
                if (z2) {
                    names.add(serviceName);
                }
            } else {
                throw new IllegalArgumentException(Intrinsics.stringPlus("Illegal service provider class name: ", serviceName).toString());
            }
        }
    }
}
