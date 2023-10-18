package kotlin.internal;

import com.itextpdf.svg.SvgConstants;
import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

@Metadata(mo112d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\u001a \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005H\u0001\u001a\"\u0010\b\u001a\u0002H\t\"\n\b\u0000\u0010\t\u0018\u0001*\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\b¢\u0006\u0002\u0010\f\u001a\b\u0010\r\u001a\u00020\u0005H\u0002\"\u0010\u0010\u0000\u001a\u00020\u00018\u0000X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, mo113d2 = {"IMPLEMENTATIONS", "Lkotlin/internal/PlatformImplementations;", "apiVersionIsAtLeast", "", "major", "", "minor", "patch", "castToBaseType", "T", "", "instance", "(Ljava/lang/Object;)Ljava/lang/Object;", "getJavaVersion", "kotlin-stdlib"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: PlatformImplementations.kt */
public final class PlatformImplementationsKt {
    public static final PlatformImplementations IMPLEMENTATIONS;

    static {
        PlatformImplementations platformImplementations;
        Object newInstance;
        Object newInstance2;
        int version = getJavaVersion();
        if (version >= 65544 || version < 65536) {
            try {
                newInstance2 = Class.forName("kotlin.internal.jdk8.JDK8PlatformImplementations").newInstance();
                Intrinsics.checkNotNullExpressionValue(newInstance2, "forName(\"kotlin.internal…entations\").newInstance()");
                if (newInstance2 != null) {
                    platformImplementations = (PlatformImplementations) newInstance2;
                    IMPLEMENTATIONS = platformImplementations;
                }
                throw new NullPointerException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
            } catch (ClassCastException e) {
                ClassLoader classLoader = newInstance2.getClass().getClassLoader();
                ClassLoader classLoader2 = PlatformImplementations.class.getClassLoader();
                if (!Intrinsics.areEqual((Object) classLoader, (Object) classLoader2)) {
                    throw new ClassNotFoundException("Instance class was loaded from a different classloader: " + classLoader + ", base type classloader: " + classLoader2, e);
                }
                throw e;
            } catch (ClassNotFoundException e2) {
                try {
                    Object newInstance3 = Class.forName("kotlin.internal.JRE8PlatformImplementations").newInstance();
                    Intrinsics.checkNotNullExpressionValue(newInstance3, "forName(\"kotlin.internal…entations\").newInstance()");
                    if (newInstance3 != null) {
                        try {
                            platformImplementations = (PlatformImplementations) newInstance3;
                        } catch (ClassCastException e3) {
                            ClassLoader classLoader3 = newInstance3.getClass().getClassLoader();
                            ClassLoader classLoader4 = PlatformImplementations.class.getClassLoader();
                            if (!Intrinsics.areEqual((Object) classLoader3, (Object) classLoader4)) {
                                throw new ClassNotFoundException("Instance class was loaded from a different classloader: " + classLoader3 + ", base type classloader: " + classLoader4, e3);
                            }
                            throw e3;
                        }
                    } else {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                    }
                } catch (ClassNotFoundException e4) {
                }
            }
        }
        if (version >= 65543 || version < 65536) {
            try {
                newInstance = Class.forName("kotlin.internal.jdk7.JDK7PlatformImplementations").newInstance();
                Intrinsics.checkNotNullExpressionValue(newInstance, "forName(\"kotlin.internal…entations\").newInstance()");
                if (newInstance != null) {
                    platformImplementations = (PlatformImplementations) newInstance;
                    IMPLEMENTATIONS = platformImplementations;
                }
                throw new NullPointerException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
            } catch (ClassCastException e5) {
                ClassLoader classLoader5 = newInstance.getClass().getClassLoader();
                ClassLoader classLoader6 = PlatformImplementations.class.getClassLoader();
                if (!Intrinsics.areEqual((Object) classLoader5, (Object) classLoader6)) {
                    throw new ClassNotFoundException("Instance class was loaded from a different classloader: " + classLoader5 + ", base type classloader: " + classLoader6, e5);
                }
                throw e5;
            } catch (ClassNotFoundException e6) {
                try {
                    Object newInstance4 = Class.forName("kotlin.internal.JRE7PlatformImplementations").newInstance();
                    Intrinsics.checkNotNullExpressionValue(newInstance4, "forName(\"kotlin.internal…entations\").newInstance()");
                    if (newInstance4 != null) {
                        try {
                            platformImplementations = (PlatformImplementations) newInstance4;
                        } catch (ClassCastException e7) {
                            ClassLoader classLoader7 = newInstance4.getClass().getClassLoader();
                            ClassLoader classLoader8 = PlatformImplementations.class.getClassLoader();
                            if (!Intrinsics.areEqual((Object) classLoader7, (Object) classLoader8)) {
                                throw new ClassNotFoundException("Instance class was loaded from a different classloader: " + classLoader7 + ", base type classloader: " + classLoader8, e7);
                            }
                            throw e7;
                        }
                    } else {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                    }
                } catch (ClassNotFoundException e8) {
                }
            }
        }
        platformImplementations = new PlatformImplementations();
        IMPLEMENTATIONS = platformImplementations;
    }

    private static final /* synthetic */ <T> T castToBaseType(Object instance) {
        try {
            Intrinsics.reifiedOperationMarker(1, SvgConstants.Attributes.PATH_DATA_SHORTHAND_CURVE_TO);
            return instance;
        } catch (ClassCastException e) {
            ClassLoader instanceCL = instance.getClass().getClassLoader();
            Intrinsics.reifiedOperationMarker(4, SvgConstants.Attributes.PATH_DATA_SHORTHAND_CURVE_TO);
            Class<Object> cls = Object.class;
            Class cls2 = cls;
            ClassLoader baseTypeCL = cls.getClassLoader();
            if (!Intrinsics.areEqual((Object) instanceCL, (Object) baseTypeCL)) {
                throw new ClassNotFoundException("Instance class was loaded from a different classloader: " + instanceCL + ", base type classloader: " + baseTypeCL, e);
            }
            throw e;
        }
    }

    private static final int getJavaVersion() {
        String version = System.getProperty("java.specification.version");
        if (version == null) {
            return 65542;
        }
        int firstDot = StringsKt.indexOf$default((CharSequence) version, '.', 0, false, 6, (Object) null);
        if (firstDot < 0) {
            try {
                return Integer.parseInt(version) * 65536;
            } catch (NumberFormatException e) {
                return 65542;
            }
        } else {
            int secondDot = StringsKt.indexOf$default((CharSequence) version, '.', firstDot + 1, false, 4, (Object) null);
            if (secondDot < 0) {
                secondDot = version.length();
            }
            String firstPart = version.substring(0, firstDot);
            Intrinsics.checkNotNullExpressionValue(firstPart, "this as java.lang.String…ing(startIndex, endIndex)");
            String secondPart = version.substring(firstDot + 1, secondDot);
            Intrinsics.checkNotNullExpressionValue(secondPart, "this as java.lang.String…ing(startIndex, endIndex)");
            try {
                return (Integer.parseInt(firstPart) * 65536) + Integer.parseInt(secondPart);
            } catch (NumberFormatException e2) {
                return 65542;
            }
        }
    }

    public static final boolean apiVersionIsAtLeast(int major, int minor, int patch) {
        return KotlinVersion.CURRENT.isAtLeast(major, minor, patch);
    }
}
