package com.itextpdf.p026io.font.cmap;

import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.source.PdfTokenizer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

/* renamed from: com.itextpdf.io.font.cmap.CMapParser */
public class CMapParser {
    private static final String CMapName = "CMapName";
    private static final int MAX_LEVEL = 10;
    private static final String Ordering = "Ordering";
    private static final String Registry = "Registry";
    private static final String Supplement = "Supplement";
    private static final String def = "def";
    private static final String endbfchar = "endbfchar";
    private static final String endbfrange = "endbfrange";
    private static final String endcidchar = "endcidchar";
    private static final String endcidrange = "endcidrange";
    private static final String endcodespacerange = "endcodespacerange";
    private static final String usecmap = "usecmap";

    public static void parseCid(String cmapName, AbstractCMap cmap, ICMapLocation location) throws IOException {
        parseCid(cmapName, cmap, location, 0);
    }

    private static void parseCid(String cmapName, AbstractCMap cmap, ICMapLocation location, int level) throws IOException {
        if (level < 10) {
            PdfTokenizer inp = location.getLocation(cmapName);
            try {
                List<CMapObject> list = new ArrayList<>();
                CMapContentParser cp = new CMapContentParser(inp);
                int maxExc = 50;
                while (true) {
                    try {
                        cp.parse(list);
                        if (list.size() == 0) {
                            break;
                        }
                        String last = list.get(list.size() - 1).toString();
                        if (level == 0 && list.size() == 3 && last.equals(def)) {
                            CMapObject cmapObject = list.get(0);
                            if (Registry.equals(cmapObject.toString())) {
                                cmap.setRegistry(list.get(1).toString());
                            } else if (Ordering.equals(cmapObject.toString())) {
                                cmap.setOrdering(list.get(1).toString());
                            } else if (CMapName.equals(cmapObject.toString())) {
                                cmap.setName(list.get(1).toString());
                            } else if (Supplement.equals(cmapObject.toString())) {
                                try {
                                    cmap.setSupplement(((Integer) list.get(1).getValue()).intValue());
                                } catch (Exception e) {
                                }
                            }
                        } else if ((last.equals(endcidchar) || last.equals(endbfchar)) && list.size() >= 3) {
                            int lMax = list.size() - 2;
                            for (int k = 0; k < lMax; k += 2) {
                                if (list.get(k).isString()) {
                                    cmap.addChar(list.get(k).toString(), list.get(k + 1));
                                }
                            }
                        } else if ((last.equals(endcidrange) || last.equals(endbfrange)) && list.size() >= 4) {
                            int lMax2 = list.size() - 3;
                            for (int k2 = 0; k2 < lMax2; k2 += 3) {
                                if (list.get(k2).isString() && list.get(k2 + 1).isString()) {
                                    cmap.addRange(list.get(k2).toString(), list.get(k2 + 1).toString(), list.get(k2 + 2));
                                }
                            }
                        } else if (last.equals(usecmap) && list.size() == 2 && list.get(0).isName()) {
                            parseCid(list.get(0).toString(), cmap, location, level + 1);
                        } else if (last.equals(endcodespacerange)) {
                            for (int i = 0; i < list.size() + 1; i += 2) {
                                if (list.get(i).isHexString() && list.get(i + 1).isHexString()) {
                                    cmap.addCodeSpaceRange(list.get(i).toHexByteArray(), list.get(i + 1).toHexByteArray());
                                }
                            }
                        }
                    } catch (Exception e2) {
                        maxExc--;
                        if (maxExc < 0) {
                            break;
                        }
                    }
                }
            } catch (Exception e3) {
                LoggerFactory.getLogger((Class<?>) CMapParser.class).error(LogMessageConstant.UNKNOWN_ERROR_WHILE_PROCESSING_CMAP);
            } catch (Throwable th) {
                inp.close();
                throw th;
            }
            inp.close();
        }
    }
}
