package com.itextpdf.p026io.font;

import com.itextpdf.p026io.font.constants.FontResources;
import com.itextpdf.p026io.util.IntHashtable;
import com.itextpdf.p026io.util.ResourceUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

/* renamed from: com.itextpdf.io.font.CidFontProperties */
public class CidFontProperties {
    private static final Map<String, Map<String, Object>> allFonts = new HashMap();
    private static final Map<String, Set<String>> registryNames;

    static {
        HashMap hashMap = new HashMap();
        registryNames = hashMap;
        try {
            loadRegistry();
            for (String font : (Set) hashMap.get("fonts")) {
                allFonts.put(font, readFontProperties(font));
            }
        } catch (Exception e) {
        }
    }

    public static boolean isCidFont(String fontName, String enc) {
        Map<String, Set<String>> map = registryNames;
        if (!map.containsKey("fonts") || !map.get("fonts").contains(fontName)) {
            return false;
        }
        if (enc.equals(PdfEncodings.IDENTITY_H) || enc.equals(PdfEncodings.IDENTITY_V)) {
            return true;
        }
        Set<String> encodings = map.get((String) allFonts.get(fontName).get("Registry"));
        if (encodings == null || !encodings.contains(enc)) {
            return false;
        }
        return true;
    }

    public static String getCompatibleFont(String enc) {
        for (Map.Entry<String, Set<String>> e : registryNames.entrySet()) {
            if (e.getValue().contains(enc)) {
                String registry = e.getKey();
                for (Map.Entry<String, Map<String, Object>> e1 : allFonts.entrySet()) {
                    if (registry.equals(e1.getValue().get("Registry"))) {
                        return e1.getKey();
                    }
                }
                continue;
            }
        }
        return null;
    }

    public static Map<String, Map<String, Object>> getAllFonts() {
        return allFonts;
    }

    public static Map<String, Set<String>> getRegistryNames() {
        return registryNames;
    }

    private static void loadRegistry() throws IOException {
        InputStream resource = ResourceUtil.getResourceStream("com/itextpdf/io/font/cmap/cjk_registry.properties");
        Properties p = new Properties();
        p.load(resource);
        resource.close();
        for (Object key : p.keySet()) {
            String[] sp = p.getProperty((String) key).split(" ");
            Set<String> hs = new HashSet<>();
            for (String s : sp) {
                if (s.length() > 0) {
                    hs.add(s);
                }
            }
            registryNames.put((String) key, hs);
        }
    }

    private static Map<String, Object> readFontProperties(String name) throws IOException {
        InputStream resource = ResourceUtil.getResourceStream(FontResources.CMAPS + (name + ".properties"));
        Properties p = new Properties();
        p.load(resource);
        resource.close();
        IntHashtable W = createMetric(p.getProperty("W"));
        p.remove("W");
        IntHashtable W2 = createMetric(p.getProperty("W2"));
        p.remove("W2");
        Map<String, Object> map = new HashMap<>();
        for (Object obj : p.keySet()) {
            map.put((String) obj, p.getProperty((String) obj));
        }
        map.put("W", W);
        map.put("W2", W2);
        return map;
    }

    private static IntHashtable createMetric(String s) {
        IntHashtable h = new IntHashtable();
        StringTokenizer tk = new StringTokenizer(s);
        while (tk.hasMoreTokens()) {
            h.put(Integer.parseInt(tk.nextToken()), Integer.parseInt(tk.nextToken()));
        }
        return h;
    }
}
