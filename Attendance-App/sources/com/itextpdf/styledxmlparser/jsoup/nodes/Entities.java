package com.itextpdf.styledxmlparser.jsoup.nodes;

import com.itextpdf.styledxmlparser.jsoup.SerializationException;
import com.itextpdf.styledxmlparser.jsoup.helper.StringUtil;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.parser.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.CharsetEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import kotlin.text.Typography;
import org.bouncycastle.crypto.tls.CipherSuite;

public class Entities {
    private static final Map<String, Character> base;
    /* access modifiers changed from: private */
    public static final Map<Character, String> baseByVal;
    private static final Map<String, Character> full;
    /* access modifiers changed from: private */
    public static final Map<Character, String> fullByVal;
    private static final Object[][] xhtmlArray;
    /* access modifiers changed from: private */
    public static final Map<Character, String> xhtmlByVal = new HashMap();

    private enum CoreCharset {
        ascii,
        utf,
        fallback
    }

    public static class EscapeMode {
        public static final EscapeMode base;
        public static final EscapeMode extended;
        private static Map<String, EscapeMode> nameValueMap;
        public static final EscapeMode xhtml;
        private Map<Character, String> map;
        private String name;

        static {
            EscapeMode escapeMode = new EscapeMode(Entities.xhtmlByVal, "xhtml");
            xhtml = escapeMode;
            EscapeMode escapeMode2 = new EscapeMode(Entities.baseByVal, "base");
            base = escapeMode2;
            EscapeMode escapeMode3 = new EscapeMode(Entities.fullByVal, "extended");
            extended = escapeMode3;
            HashMap hashMap = new HashMap();
            nameValueMap = hashMap;
            hashMap.put(escapeMode.name, escapeMode);
            nameValueMap.put(escapeMode2.name, escapeMode2);
            nameValueMap.put(escapeMode3.name, escapeMode3);
        }

        public static EscapeMode valueOf(String name2) {
            return nameValueMap.get(name2);
        }

        private EscapeMode(Map<Character, String> map2, String name2) {
            this.map = map2;
            this.name = name2;
        }

        public Map<Character, String> getMap() {
            return this.map;
        }

        public String name() {
            return this.name;
        }
    }

    private Entities() {
    }

    public static boolean isNamedEntity(String name) {
        return full.containsKey(name);
    }

    public static boolean isBaseNamedEntity(String name) {
        return base.containsKey(name);
    }

    public static Character getCharacterByName(String name) {
        return full.get(name);
    }

    static String escape(String string, Document.OutputSettings out) {
        StringBuilder accum = new StringBuilder(string.length() * 2);
        try {
            escape(accum, string, out, false, false, false);
            return accum.toString();
        } catch (IOException e) {
            throw new SerializationException((Throwable) e);
        }
    }

    static void escape(Appendable accum, String str, Document.OutputSettings outputSettings, boolean inAttribute, boolean normaliseWhite, boolean stripLeadingWhite) throws IOException {
        Appendable appendable = accum;
        boolean lastWasWhite = false;
        boolean reachedNonWhite = false;
        EscapeMode escapeMode = outputSettings.escapeMode();
        CharsetEncoder encoder = outputSettings.encoder();
        CoreCharset coreCharset = getCoreCharsetByName(outputSettings.charset().name());
        Map<Character, String> map = escapeMode.getMap();
        int length = str.length();
        int offset = 0;
        while (offset < length) {
            int codePoint = str.codePointAt(offset);
            if (normaliseWhite) {
                if (StringUtil.isWhitespace(codePoint)) {
                    if ((!stripLeadingWhite || reachedNonWhite) && !lastWasWhite) {
                        accum.append(' ');
                        lastWasWhite = true;
                    }
                    offset += Character.charCount(codePoint);
                } else {
                    lastWasWhite = false;
                    reachedNonWhite = true;
                }
            }
            if (codePoint < 65536) {
                char c = (char) codePoint;
                switch (c) {
                    case '\"':
                        if (!inAttribute) {
                            accum.append(c);
                            break;
                        } else {
                            accum.append("&quot;");
                            break;
                        }
                    case '&':
                        accum.append("&amp;");
                        break;
                    case '<':
                        if (inAttribute && escapeMode != EscapeMode.xhtml) {
                            accum.append(c);
                            break;
                        } else {
                            accum.append("&lt;");
                            break;
                        }
                        break;
                    case '>':
                        if (inAttribute) {
                            accum.append(c);
                            break;
                        } else {
                            accum.append("&gt;");
                            break;
                        }
                    case CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256:
                        if (escapeMode == EscapeMode.xhtml) {
                            accum.append("&#xa0;");
                            break;
                        } else {
                            accum.append("&nbsp;");
                            break;
                        }
                    default:
                        if (!canEncode(coreCharset, c, encoder)) {
                            if (!map.containsKey(Character.valueOf(c))) {
                                accum.append("&#x").append(Integer.toHexString(codePoint)).append(';');
                                break;
                            } else {
                                accum.append(Typography.amp).append(map.get(Character.valueOf(c))).append(';');
                                break;
                            }
                        } else {
                            accum.append(c);
                            break;
                        }
                }
            } else {
                String c2 = new String(Character.toChars(codePoint));
                if (encoder.canEncode(c2)) {
                    accum.append(c2);
                } else {
                    accum.append("&#x").append(Integer.toHexString(codePoint)).append(';');
                }
            }
            offset += Character.charCount(codePoint);
        }
        String str2 = str;
    }

    static String unescape(String string) {
        return unescape(string, false);
    }

    static String unescape(String string, boolean strict) {
        return Parser.unescapeEntities(string, strict);
    }

    /* renamed from: com.itextpdf.styledxmlparser.jsoup.nodes.Entities$1 */
    static /* synthetic */ class C14931 {

        /* renamed from: $SwitchMap$com$itextpdf$styledxmlparser$jsoup$nodes$Entities$CoreCharset */
        static final /* synthetic */ int[] f1624x6301dc6;

        static {
            int[] iArr = new int[CoreCharset.values().length];
            f1624x6301dc6 = iArr;
            try {
                iArr[CoreCharset.ascii.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1624x6301dc6[CoreCharset.utf.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    private static boolean canEncode(CoreCharset charset, char c, CharsetEncoder fallback) {
        switch (C14931.f1624x6301dc6[charset.ordinal()]) {
            case 1:
                if (c < 128) {
                    return true;
                }
                return false;
            case 2:
                return true;
            default:
                return fallback.canEncode(c);
        }
    }

    private static CoreCharset getCoreCharsetByName(String name) {
        if (name.equals("US-ASCII")) {
            return CoreCharset.ascii;
        }
        if (name.startsWith("UTF-")) {
            return CoreCharset.utf;
        }
        return CoreCharset.fallback;
    }

    static {
        Object[][] objArr = {new Object[]{"quot", 34}, new Object[]{"amp", 38}, new Object[]{"lt", 60}, new Object[]{"gt", 62}};
        xhtmlArray = objArr;
        Map<String, Character> loadEntities = loadEntities("entities-base.properties");
        base = loadEntities;
        baseByVal = toCharacterKey(loadEntities);
        Map<String, Character> loadEntities2 = loadEntities("entities-full.properties");
        full = loadEntities2;
        fullByVal = toCharacterKey(loadEntities2);
        for (Object[] entity : objArr) {
            xhtmlByVal.put(Character.valueOf((char) ((Integer) entity[1]).intValue()), (String) entity[0]);
        }
    }

    private static Map<String, Character> loadEntities(String filename) {
        Properties properties = new Properties();
        Map<String, Character> entities = new HashMap<>();
        try {
            InputStream in = Entities.class.getResourceAsStream(filename);
            properties.load(in);
            in.close();
            for (Object name : properties.keySet()) {
                entities.put((String) name, Character.valueOf((char) Integer.parseInt(properties.getProperty((String) name), 16)));
            }
            return entities;
        } catch (IOException e) {
            throw new MissingResourceException("Error loading entities resource: " + e.getMessage(), "Entities", filename);
        }
    }

    private static Map<Character, String> toCharacterKey(Map<String, Character> inMap) {
        Map<Character, String> outMap = new HashMap<>();
        for (Map.Entry<String, Character> entry : inMap.entrySet()) {
            char character = entry.getValue().charValue();
            String name = entry.getKey();
            if (!outMap.containsKey(Character.valueOf(character))) {
                outMap.put(Character.valueOf(character), name);
            } else if (name.toLowerCase().equals(name)) {
                outMap.put(Character.valueOf(character), name);
            }
        }
        return outMap;
    }
}
