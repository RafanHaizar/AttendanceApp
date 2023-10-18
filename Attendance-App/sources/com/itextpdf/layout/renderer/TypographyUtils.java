package com.itextpdf.layout.renderer;

import com.itextpdf.layout.property.BaseDirection;
import com.itextpdf.p026io.font.FontProgram;
import com.itextpdf.p026io.font.TrueTypeFont;
import com.itextpdf.p026io.font.otf.GlyphLine;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.lang.Character;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TypographyUtils {
    private static final String APPLY_KERNING = "applyKerning";
    private static final String APPLY_OTF_SCRIPT = "applyOtfScript";
    private static final String BIDI_ALGORITHM = "bidi.BidiAlgorithm";
    private static final String BIDI_BRACKET_MAP = "bidi.BidiBracketMap";
    private static final String BIDI_CHARACTER_MAP = "bidi.BidiCharacterMap";
    private static final String COMPUTE_REORDERING = "computeReordering";
    private static final String GET_BRACKET_TYPES = "getBracketTypes";
    private static final String GET_BRACKET_VALUES = "getBracketValues";
    private static final String GET_CHARACTER_TYPES = "getCharacterTypes";
    private static final String GET_LEVELS = "getLevels";
    private static final String GET_PAIRED_BRACKET = "getPairedBracket";
    private static final String GET_POSSIBLE_BREAKS = "getPossibleBreaks";
    private static final String GET_SUPPORTED_SCRIPTS = "getSupportedScripts";
    private static final String INVERSE_REORDERING = "inverseReordering";
    private static final String SHAPER = "shaping.Shaper";
    private static final Collection<Character.UnicodeScript> SUPPORTED_SCRIPTS;
    private static final boolean TYPOGRAPHY_MODULE_INITIALIZED;
    private static final String TYPOGRAPHY_PACKAGE = "com.itextpdf.typography.";
    private static final String WORD_WRAPPER = "WordWrapper";
    private static Map<String, Class<?>> cachedClasses = new HashMap();
    private static Map<TypographyMethodSignature, AccessibleObject> cachedMethods = new HashMap();
    private static final Logger logger = LoggerFactory.getLogger((Class<?>) TypographyUtils.class);
    private static final String typographyNotFoundException = "Cannot find pdfCalligraph module, which was implicitly required by one of the layout properties";

    static {
        boolean moduleFound = false;
        try {
            if (getTypographyClass("com.itextpdf.typography.shaping.Shaper") != null) {
                moduleFound = true;
            }
        } catch (ClassNotFoundException e) {
        }
        Collection<Character.UnicodeScript> supportedScripts = null;
        boolean z = false;
        if (moduleFound) {
            try {
                supportedScripts = (Collection) callMethod("com.itextpdf.typography.shaping.Shaper", GET_SUPPORTED_SCRIPTS, new Class[0], new Object[0]);
            } catch (Exception e2) {
                supportedScripts = null;
                logger.error(e2.getMessage());
            }
        }
        if (supportedScripts != null) {
            z = true;
        }
        boolean moduleFound2 = z;
        if (!moduleFound2) {
            cachedClasses.clear();
            cachedMethods.clear();
        }
        TYPOGRAPHY_MODULE_INITIALIZED = moduleFound2;
        SUPPORTED_SCRIPTS = supportedScripts;
    }

    private TypographyUtils() {
    }

    public static boolean isPdfCalligraphAvailable() {
        return TYPOGRAPHY_MODULE_INITIALIZED;
    }

    static void applyOtfScript(FontProgram fontProgram, GlyphLine text, Character.UnicodeScript script, Object typographyConfig) {
        if (!TYPOGRAPHY_MODULE_INITIALIZED) {
            logger.warn(typographyNotFoundException);
            return;
        }
        callMethod("com.itextpdf.typography.shaping.Shaper", APPLY_OTF_SCRIPT, new Class[]{TrueTypeFont.class, GlyphLine.class, Character.UnicodeScript.class, Object.class}, fontProgram, text, script, typographyConfig);
    }

    static void applyKerning(FontProgram fontProgram, GlyphLine text) {
        if (!TYPOGRAPHY_MODULE_INITIALIZED) {
            logger.warn(typographyNotFoundException);
            return;
        }
        callMethod("com.itextpdf.typography.shaping.Shaper", APPLY_KERNING, new Class[]{FontProgram.class, GlyphLine.class}, fontProgram, text);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v1, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static byte[] getBidiLevels(com.itextpdf.layout.property.BaseDirection r13, int[] r14) {
        /*
            boolean r0 = TYPOGRAPHY_MODULE_INITIALIZED
            if (r0 != 0) goto L_0x000d
            org.slf4j.Logger r0 = logger
            java.lang.String r1 = "Cannot find pdfCalligraph module, which was implicitly required by one of the layout properties"
            r0.warn(r1)
            r0 = 0
            return r0
        L_0x000d:
            int[] r0 = com.itextpdf.layout.renderer.TypographyUtils.C14771.$SwitchMap$com$itextpdf$layout$property$BaseDirection
            int r1 = r13.ordinal()
            r0 = r0[r1]
            switch(r0) {
                case 1: goto L_0x001c;
                case 2: goto L_0x001a;
                default: goto L_0x0018;
            }
        L_0x0018:
            r0 = 2
            goto L_0x001e
        L_0x001a:
            r0 = 1
            goto L_0x001e
        L_0x001c:
            r0 = 0
        L_0x001e:
            int r1 = r14.length
            r2 = 3
            java.lang.Class[] r3 = new java.lang.Class[r2]
            r4 = 0
            java.lang.Class<int[]> r5 = int[].class
            r3[r4] = r5
            java.lang.Class r6 = java.lang.Integer.TYPE
            r7 = 1
            r3[r7] = r6
            java.lang.Class r6 = java.lang.Integer.TYPE
            r8 = 2
            r3[r8] = r6
            java.lang.Object[] r6 = new java.lang.Object[r2]
            r6[r4] = r14
            java.lang.Integer r9 = java.lang.Integer.valueOf(r4)
            r6[r7] = r9
            java.lang.Integer r9 = java.lang.Integer.valueOf(r1)
            r6[r8] = r9
            java.lang.String r9 = "com.itextpdf.typography.bidi.BidiCharacterMap"
            java.lang.String r10 = "getCharacterTypes"
            java.lang.Object r3 = callMethod(r9, r10, r3, r6)
            byte[] r3 = (byte[]) r3
            byte[] r3 = (byte[]) r3
            java.lang.Class[] r6 = new java.lang.Class[r2]
            r6[r4] = r5
            java.lang.Class r9 = java.lang.Integer.TYPE
            r6[r7] = r9
            java.lang.Class r9 = java.lang.Integer.TYPE
            r6[r8] = r9
            java.lang.Object[] r9 = new java.lang.Object[r2]
            r9[r4] = r14
            java.lang.Integer r10 = java.lang.Integer.valueOf(r4)
            r9[r7] = r10
            java.lang.Integer r10 = java.lang.Integer.valueOf(r1)
            r9[r8] = r10
            java.lang.String r10 = "com.itextpdf.typography.bidi.BidiBracketMap"
            java.lang.String r11 = "getBracketTypes"
            java.lang.Object r6 = callMethod(r10, r11, r6, r9)
            byte[] r6 = (byte[]) r6
            byte[] r6 = (byte[]) r6
            java.lang.Class[] r9 = new java.lang.Class[r2]
            r9[r4] = r5
            java.lang.Class r11 = java.lang.Integer.TYPE
            r9[r7] = r11
            java.lang.Class r11 = java.lang.Integer.TYPE
            r9[r8] = r11
            java.lang.Object[] r11 = new java.lang.Object[r2]
            r11[r4] = r14
            java.lang.Integer r12 = java.lang.Integer.valueOf(r4)
            r11[r7] = r12
            java.lang.Integer r12 = java.lang.Integer.valueOf(r1)
            r11[r8] = r12
            java.lang.String r12 = "getBracketValues"
            java.lang.Object r9 = callMethod(r10, r12, r9, r11)
            int[] r9 = (int[]) r9
            int[] r9 = (int[]) r9
            r10 = 4
            java.lang.Class[] r11 = new java.lang.Class[r10]
            java.lang.Class<byte[]> r12 = byte[].class
            r11[r4] = r12
            r11[r7] = r12
            r11[r8] = r5
            java.lang.Class r12 = java.lang.Byte.TYPE
            r11[r2] = r12
            java.lang.Object[] r10 = new java.lang.Object[r10]
            r10[r4] = r3
            r10[r7] = r6
            r10[r8] = r9
            java.lang.Byte r8 = java.lang.Byte.valueOf(r0)
            r10[r2] = r8
            java.lang.String r2 = "com.itextpdf.typography.bidi.BidiAlgorithm"
            java.lang.Object r8 = callConstructor(r2, r11, r10)
            java.lang.Class[] r10 = new java.lang.Class[r7]
            r10[r4] = r5
            java.lang.Object[] r5 = new java.lang.Object[r7]
            int[] r7 = new int[r7]
            r7[r4] = r1
            r5[r4] = r7
            java.lang.String r4 = "getLevels"
            java.lang.Object r2 = callMethod(r2, r4, r8, r10, r5)
            byte[] r2 = (byte[]) r2
            byte[] r2 = (byte[]) r2
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.TypographyUtils.getBidiLevels(com.itextpdf.layout.property.BaseDirection, int[]):byte[]");
    }

    /* renamed from: com.itextpdf.layout.renderer.TypographyUtils$1 */
    static /* synthetic */ class C14771 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$layout$property$BaseDirection;

        static {
            int[] iArr = new int[BaseDirection.values().length];
            $SwitchMap$com$itextpdf$layout$property$BaseDirection = iArr;
            try {
                iArr[BaseDirection.LEFT_TO_RIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$BaseDirection[BaseDirection.RIGHT_TO_LEFT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$BaseDirection[BaseDirection.DEFAULT_BIDI.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int[] reorderLine(java.util.List<com.itextpdf.layout.renderer.LineRenderer.RendererGlyph> r12, byte[] r13, byte[] r14) {
        /*
            boolean r0 = TYPOGRAPHY_MODULE_INITIALIZED
            r1 = 0
            if (r0 != 0) goto L_0x000d
            org.slf4j.Logger r0 = logger
            java.lang.String r2 = "Cannot find pdfCalligraph module, which was implicitly required by one of the layout properties"
            r0.warn(r2)
            return r1
        L_0x000d:
            if (r14 != 0) goto L_0x0010
            return r1
        L_0x0010:
            r0 = 1
            java.lang.Class[] r1 = new java.lang.Class[r0]
            java.lang.Class<byte[]> r2 = byte[].class
            r3 = 0
            r1[r3] = r2
            java.lang.Object[] r2 = new java.lang.Object[r0]
            r2[r3] = r13
            java.lang.String r4 = "com.itextpdf.typography.bidi.BidiAlgorithm"
            java.lang.String r5 = "computeReordering"
            java.lang.Object r1 = callMethod(r4, r5, r1, r2)
            int[] r1 = (int[]) r1
            int[] r1 = (int[]) r1
            java.lang.Class[] r2 = new java.lang.Class[r0]
            java.lang.Class<int[]> r5 = int[].class
            r2[r3] = r5
            java.lang.Object[] r5 = new java.lang.Object[r0]
            r5[r3] = r1
            java.lang.String r6 = "inverseReordering"
            java.lang.Object r2 = callMethod(r4, r6, r2, r5)
            int[] r2 = (int[]) r2
            int[] r2 = (int[]) r2
            java.util.ArrayList r4 = new java.util.ArrayList
            int r5 = r13.length
            r4.<init>(r5)
            r5 = 0
        L_0x0043:
            int r6 = r12.size()
            if (r5 >= r6) goto L_0x00b7
            r6 = r1[r5]
            java.lang.Object r6 = r12.get(r6)
            r4.add(r6)
            r6 = r1[r5]
            byte r6 = r14[r6]
            int r6 = r6 % 2
            if (r6 != r0) goto L_0x00b4
            java.lang.Object r6 = r4.get(r5)
            com.itextpdf.layout.renderer.LineRenderer$RendererGlyph r6 = (com.itextpdf.layout.renderer.LineRenderer.RendererGlyph) r6
            com.itextpdf.io.font.otf.Glyph r6 = r6.glyph
            boolean r6 = r6.hasValidUnicode()
            if (r6 == 0) goto L_0x00b4
            java.lang.Object r6 = r4.get(r5)
            com.itextpdf.layout.renderer.LineRenderer$RendererGlyph r6 = (com.itextpdf.layout.renderer.LineRenderer.RendererGlyph) r6
            com.itextpdf.io.font.otf.Glyph r6 = r6.glyph
            int r6 = r6.getUnicode()
            java.lang.Class[] r7 = new java.lang.Class[r0]
            java.lang.Class r8 = java.lang.Integer.TYPE
            r7[r3] = r8
            java.lang.Object[] r8 = new java.lang.Object[r0]
            java.lang.Integer r9 = java.lang.Integer.valueOf(r6)
            r8[r3] = r9
            java.lang.String r9 = "com.itextpdf.typography.bidi.BidiBracketMap"
            java.lang.String r10 = "getPairedBracket"
            java.lang.Object r7 = callMethod(r9, r10, r7, r8)
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            if (r7 == r6) goto L_0x00b4
            java.lang.Object r8 = r4.get(r5)
            com.itextpdf.layout.renderer.LineRenderer$RendererGlyph r8 = (com.itextpdf.layout.renderer.LineRenderer.RendererGlyph) r8
            com.itextpdf.layout.renderer.TextRenderer r8 = r8.renderer
            r9 = 20
            com.itextpdf.kernel.font.PdfFont r8 = r8.getPropertyAsFont(r9)
            com.itextpdf.layout.renderer.LineRenderer$RendererGlyph r9 = new com.itextpdf.layout.renderer.LineRenderer$RendererGlyph
            com.itextpdf.io.font.otf.Glyph r10 = r8.getGlyph(r7)
            java.lang.Object r11 = r4.get(r5)
            com.itextpdf.layout.renderer.LineRenderer$RendererGlyph r11 = (com.itextpdf.layout.renderer.LineRenderer.RendererGlyph) r11
            com.itextpdf.layout.renderer.TextRenderer r11 = r11.renderer
            r9.<init>(r10, r11)
            r4.set(r5, r9)
        L_0x00b4:
            int r5 = r5 + 1
            goto L_0x0043
        L_0x00b7:
            r0 = 0
        L_0x00b8:
            int r3 = r4.size()
            if (r0 >= r3) goto L_0x00de
            java.lang.Object r3 = r4.get(r0)
            com.itextpdf.layout.renderer.LineRenderer$RendererGlyph r3 = (com.itextpdf.layout.renderer.LineRenderer.RendererGlyph) r3
            com.itextpdf.io.font.otf.Glyph r3 = r3.glyph
            boolean r5 = r3.hasPlacement()
            if (r5 == 0) goto L_0x00db
            r5 = r1[r0]
            short r6 = r3.getAnchorDelta()
            int r5 = r5 + r6
            r6 = r2[r5]
            int r7 = r6 - r0
            short r8 = (short) r7
            r3.setAnchorDelta(r8)
        L_0x00db:
            int r0 = r0 + 1
            goto L_0x00b8
        L_0x00de:
            r12.clear()
            r12.addAll(r4)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.TypographyUtils.reorderLine(java.util.List, byte[], byte[]):int[]");
    }

    static Collection<Character.UnicodeScript> getSupportedScripts() {
        if (TYPOGRAPHY_MODULE_INITIALIZED) {
            return SUPPORTED_SCRIPTS;
        }
        logger.warn(typographyNotFoundException);
        return null;
    }

    static Collection<Character.UnicodeScript> getSupportedScripts(Object typographyConfig) {
        if (!TYPOGRAPHY_MODULE_INITIALIZED) {
            logger.warn(typographyNotFoundException);
            return null;
        }
        return (Collection) callMethod("com.itextpdf.typography.shaping.Shaper", GET_SUPPORTED_SCRIPTS, (Object) null, new Class[]{Object.class}, typographyConfig);
    }

    static List<Integer> getPossibleBreaks(String str) {
        return (List) callMethod("com.itextpdf.typography.WordWrapper", GET_POSSIBLE_BREAKS, new Class[]{String.class}, str);
    }

    private static Object callMethod(String className, String methodName, Class[] parameterTypes, Object... args) {
        return callMethod(className, methodName, (Object) null, parameterTypes, args);
    }

    private static Object callMethod(String className, String methodName, Object target, Class[] parameterTypes, Object... args) {
        try {
            return findMethod(className, methodName, parameterTypes).invoke(target, args);
        } catch (NoSuchMethodException e) {
            logger.warn(MessageFormatUtil.format("Cannot find method {0} for class {1}", methodName, className));
            return null;
        } catch (ClassNotFoundException e2) {
            logger.warn(MessageFormatUtil.format("Cannot find class {0}", className));
            return null;
        } catch (IllegalArgumentException e3) {
            logger.warn(MessageFormatUtil.format("Illegal arguments passed to {0}#{1} method call: {2}", className, methodName, e3.getMessage()));
            return null;
        } catch (Exception e4) {
            throw new RuntimeException(e4.toString(), e4);
        }
    }

    private static Object callConstructor(String className, Class[] parameterTypes, Object... args) {
        try {
            return findConstructor(className, parameterTypes).newInstance(args);
        } catch (NoSuchMethodException e) {
            logger.warn(MessageFormatUtil.format("Cannot find constructor for class {0}", className));
            return null;
        } catch (ClassNotFoundException e2) {
            logger.warn(MessageFormatUtil.format("Cannot find class {0}", className));
            return null;
        } catch (Exception exc) {
            throw new RuntimeException(exc.toString(), exc);
        }
    }

    private static Method findMethod(String className, String methodName, Class[] parameterTypes) throws NoSuchMethodException, ClassNotFoundException {
        TypographyMethodSignature tm = new TypographyMethodSignature(className, parameterTypes, methodName);
        Method m = (Method) cachedMethods.get(tm);
        if (m != null) {
            return m;
        }
        Method m2 = findClass(className).getMethod(methodName, parameterTypes);
        cachedMethods.put(tm, m2);
        return m2;
    }

    private static Constructor<?> findConstructor(String className, Class[] parameterTypes) throws NoSuchMethodException, ClassNotFoundException {
        TypographyMethodSignature tc = new TypographyMethodSignature(className, parameterTypes);
        Constructor<?> c = (Constructor) cachedMethods.get(tc);
        if (c != null) {
            return c;
        }
        Constructor<?> c2 = findClass(className).getConstructor(parameterTypes);
        cachedMethods.put(tc, c2);
        return c2;
    }

    private static Class<?> findClass(String className) throws ClassNotFoundException {
        Class<?> c = cachedClasses.get(className);
        if (c != null) {
            return c;
        }
        Class<?> c2 = getTypographyClass(className);
        cachedClasses.put(className, c2);
        return c2;
    }

    private static Class<?> getTypographyClass(String typographyClassName) throws ClassNotFoundException {
        return Class.forName(typographyClassName);
    }

    private static class TypographyMethodSignature {
        protected final String className;
        private final String methodName;
        protected Class[] parameterTypes;

        TypographyMethodSignature(String className2, Class[] parameterTypes2) {
            this(className2, parameterTypes2, (String) null);
        }

        TypographyMethodSignature(String className2, Class[] parameterTypes2, String methodName2) {
            this.methodName = methodName2;
            this.className = className2;
            this.parameterTypes = parameterTypes2;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            TypographyMethodSignature that = (TypographyMethodSignature) o;
            if (!this.className.equals(that.className) || !Arrays.equals(this.parameterTypes, that.parameterTypes)) {
                return false;
            }
            String str = this.methodName;
            if (str != null) {
                return str.equals(that.methodName);
            }
            if (that.methodName == null) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            int result = ((this.className.hashCode() * 31) + Arrays.hashCode(this.parameterTypes)) * 31;
            String str = this.methodName;
            return result + (str != null ? str.hashCode() : 0);
        }
    }
}
