package com.itextpdf.p026io.font;

import com.itextpdf.p026io.font.constants.FontResources;
import com.itextpdf.p026io.util.ResourceUtil;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/* renamed from: com.itextpdf.io.font.AdobeGlyphList */
public class AdobeGlyphList {
    private static Map<String, Integer> names2unicode = new HashMap();
    private static Map<Integer, String> unicode2names = new HashMap();

    static {
        InputStream resource = null;
        try {
            resource = ResourceUtil.getResourceStream(FontResources.ADOBE_GLYPH_LIST);
            if (resource != null) {
                byte[] buf = new byte[1024];
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                while (true) {
                    int size = resource.read(buf);
                    if (size < 0) {
                        break;
                    }
                    stream.write(buf, 0, size);
                }
                resource.close();
                InputStream resource2 = null;
                StringTokenizer tk = new StringTokenizer(PdfEncodings.convertToString(stream.toByteArray(), (String) null), "\r\n");
                while (tk.hasMoreTokens()) {
                    String line = tk.nextToken();
                    if (!line.startsWith("#")) {
                        StringTokenizer t2 = new StringTokenizer(line, " ;\r\n\t\f");
                        if (t2.hasMoreTokens()) {
                            String name = t2.nextToken();
                            if (t2.hasMoreTokens()) {
                                String hex = t2.nextToken();
                                if (!t2.hasMoreTokens()) {
                                    int num = Integer.parseInt(hex, 16);
                                    unicode2names.put(Integer.valueOf(num), name);
                                    names2unicode.put(name, Integer.valueOf(num));
                                }
                            }
                        }
                    }
                }
                if (resource2 != null) {
                    try {
                        resource2.close();
                    } catch (Exception e) {
                    }
                }
            } else {
                throw new Exception("com/itextpdf/io/font/AdobeGlyphList.txt not found as resource.");
            }
        } catch (Exception e2) {
            System.err.println("AdobeGlyphList.txt loading error: " + e2.getMessage());
            if (resource != null) {
                resource.close();
            }
        } catch (Throwable th) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e3) {
                }
            }
            throw th;
        }
    }

    public static int nameToUnicode(String name) {
        int v = -1;
        if (names2unicode.containsKey(name)) {
            v = names2unicode.get(name).intValue();
        }
        if (v == -1 && name.length() == 7 && name.toLowerCase().startsWith("uni")) {
            try {
                return Integer.parseInt(name.substring(3), 16);
            } catch (Exception e) {
            }
        }
        return v;
    }

    public static String unicodeToName(int num) {
        return unicode2names.get(Integer.valueOf(num));
    }

    public static int getNameToUnicodeLength() {
        return names2unicode.size();
    }

    public static int getUnicodeToNameLength() {
        return unicode2names.size();
    }
}
