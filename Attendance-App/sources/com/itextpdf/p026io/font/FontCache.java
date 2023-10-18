package com.itextpdf.p026io.font;

import com.itextpdf.p026io.font.cmap.AbstractCMap;
import com.itextpdf.p026io.font.cmap.CMapByteCid;
import com.itextpdf.p026io.font.cmap.CMapCidByte;
import com.itextpdf.p026io.font.cmap.CMapCidUni;
import com.itextpdf.p026io.font.cmap.CMapLocationResource;
import com.itextpdf.p026io.font.cmap.CMapParser;
import com.itextpdf.p026io.font.cmap.CMapUniCid;
import com.itextpdf.p026io.font.constants.FontResources;
import com.itextpdf.p026io.util.IntHashtable;
import com.itextpdf.p026io.util.ResourceUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: com.itextpdf.io.font.FontCache */
public class FontCache {
    private static final String CJK_REGISTRY_FILENAME = "cjk_registry.properties";
    private static final String FONTS_PROP = "fonts";
    private static final String REGISTRY_PROP = "Registry";
    private static final String W2_PROP = "W2";
    private static final String W_PROP = "W";
    private static final Map<String, Map<String, Object>> allCidFonts = new LinkedHashMap();
    private static Map<FontCacheKey, FontProgram> fontCache = new ConcurrentHashMap();
    private static final Map<String, Set<String>> registryNames;

    static {
        HashMap hashMap = new HashMap();
        registryNames = hashMap;
        try {
            loadRegistry();
            for (String font : (Set) hashMap.get(FONTS_PROP)) {
                allCidFonts.put(font, readFontProperties(font));
            }
        } catch (Exception e) {
        }
    }

    protected static boolean isPredefinedCidFont(String fontName) {
        Map<String, Set<String>> map = registryNames;
        if (map.containsKey(FONTS_PROP) && map.get(FONTS_PROP).contains(fontName)) {
            return true;
        }
        return false;
    }

    public static String getCompatibleCidFont(String cmap) {
        for (Map.Entry<String, Set<String>> e : registryNames.entrySet()) {
            if (e.getValue().contains(cmap)) {
                String registry = e.getKey();
                for (Map.Entry<String, Map<String, Object>> e1 : allCidFonts.entrySet()) {
                    if (registry.equals(e1.getValue().get(REGISTRY_PROP))) {
                        return e1.getKey();
                    }
                }
                continue;
            }
        }
        return null;
    }

    public static Set<String> getCompatibleCmaps(String fontName) {
        Map<String, Object> cidFonts = getAllPredefinedCidFonts().get(fontName);
        if (cidFonts == null) {
            return null;
        }
        return registryNames.get((String) cidFonts.get(REGISTRY_PROP));
    }

    public static Map<String, Map<String, Object>> getAllPredefinedCidFonts() {
        return allCidFonts;
    }

    public static Map<String, Set<String>> getRegistryNames() {
        return registryNames;
    }

    public static CMapCidUni getCid2UniCmap(String uniMap) {
        return (CMapCidUni) parseCmap(uniMap, new CMapCidUni());
    }

    public static CMapUniCid getUni2CidCmap(String uniMap) {
        return (CMapUniCid) parseCmap(uniMap, new CMapUniCid());
    }

    public static CMapByteCid getByte2CidCmap(String cmap) {
        return (CMapByteCid) parseCmap(cmap, new CMapByteCid());
    }

    public static CMapCidByte getCid2Byte(String cmap) {
        return (CMapCidByte) parseCmap(cmap, new CMapCidByte());
    }

    public static void clearSavedFonts() {
        fontCache.clear();
    }

    public static FontProgram getFont(String fontName) {
        return fontCache.get(FontCacheKey.create(fontName));
    }

    static FontProgram getFont(FontCacheKey key) {
        return fontCache.get(key);
    }

    public static FontProgram saveFont(FontProgram font, String fontName) {
        return saveFont(font, FontCacheKey.create(fontName));
    }

    static FontProgram saveFont(FontProgram font, FontCacheKey key) {
        FontProgram fontFound = fontCache.get(key);
        if (fontFound != null) {
            return fontFound;
        }
        fontCache.put(key, font);
        return font;
    }

    private static void loadRegistry() throws IOException {
        InputStream resource = ResourceUtil.getResourceStream("com/itextpdf/io/font/cmap/cjk_registry.properties");
        try {
            Properties p = new Properties();
            p.load(resource);
            for (Map.Entry<Object, Object> entry : p.entrySet()) {
                String[] splitValue = ((String) entry.getValue()).split(" ");
                Set<String> set = new HashSet<>();
                for (String s : splitValue) {
                    if (s.length() != 0) {
                        set.add(s);
                    }
                }
                registryNames.put((String) entry.getKey(), set);
            }
        } finally {
            if (resource != null) {
                resource.close();
            }
        }
    }

    private static Map<String, Object> readFontProperties(String name) throws IOException {
        InputStream resource = ResourceUtil.getResourceStream(FontResources.CMAPS + name + ".properties");
        try {
            Properties p = new Properties();
            p.load(resource);
            Map<String, Object> fontProperties = new HashMap<>();
            for (Map.Entry<Object, Object> entry : p.entrySet()) {
                fontProperties.put((String) entry.getKey(), entry.getValue());
            }
            fontProperties.put(W_PROP, createMetric((String) fontProperties.get(W_PROP)));
            fontProperties.put(W2_PROP, createMetric((String) fontProperties.get(W2_PROP)));
            return fontProperties;
        } finally {
            if (resource != null) {
                resource.close();
            }
        }
    }

    private static IntHashtable createMetric(String s) {
        IntHashtable h = new IntHashtable();
        StringTokenizer tk = new StringTokenizer(s);
        while (tk.hasMoreTokens()) {
            h.put(Integer.parseInt(tk.nextToken()), Integer.parseInt(tk.nextToken()));
        }
        return h;
    }

    private static <T extends AbstractCMap> T parseCmap(String name, T cmap) {
        try {
            CMapParser.parseCid(name, cmap, new CMapLocationResource());
            return cmap;
        } catch (IOException e) {
            throw new com.itextpdf.p026io.IOException("I/O exception.", (Throwable) e);
        }
    }
}
