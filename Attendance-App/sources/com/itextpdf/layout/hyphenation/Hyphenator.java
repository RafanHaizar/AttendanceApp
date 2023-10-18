package com.itextpdf.layout.hyphenation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Hyphenator {
    private static final char SOFT_HYPHEN = '­';
    private static List<String> additionalHyphenationFileDirectories;
    private static HyphenationTreeCache hTreeCache;
    private static Logger log = LoggerFactory.getLogger((Class<?>) Hyphenator.class);
    private static final Object staticLock = new Object();
    protected String country;
    Map<String, String> hyphPathNames;
    protected String lang;
    int leftMin;
    int rightMin;

    public Hyphenator(String lang2, String country2, int leftMin2, int rightMin2) {
        this.lang = lang2;
        this.country = country2;
        this.leftMin = leftMin2;
        this.rightMin = rightMin2;
    }

    public Hyphenator(String lang2, String country2, int leftMin2, int rightMin2, Map<String, String> hyphPathNames2) {
        this(lang2, country2, leftMin2, rightMin2);
        this.hyphPathNames = hyphPathNames2;
    }

    public static void registerAdditionalHyphenationFileDirectory(String directory) {
        synchronized (staticLock) {
            if (additionalHyphenationFileDirectories == null) {
                additionalHyphenationFileDirectories = new ArrayList();
            }
            additionalHyphenationFileDirectories.add(directory);
        }
    }

    public static HyphenationTreeCache getHyphenationTreeCache() {
        synchronized (staticLock) {
            if (hTreeCache == null) {
                hTreeCache = new HyphenationTreeCache();
            }
        }
        return hTreeCache;
    }

    public static void clearHyphenationTreeCache() {
        synchronized (staticLock) {
            hTreeCache = new HyphenationTreeCache();
        }
    }

    public static HyphenationTree getHyphenationTree(String lang2, String country2, Map<String, String> hyphPathNames2) {
        String llccKey = HyphenationTreeCache.constructLlccKey(lang2, country2);
        HyphenationTreeCache cache = getHyphenationTreeCache();
        if (cache.isMissing(llccKey)) {
            return null;
        }
        HyphenationTree hTree = getHyphenationTree2(lang2, country2, hyphPathNames2);
        if (hTree == null && country2 != null && !country2.equals("none")) {
            String llKey = HyphenationTreeCache.constructLlccKey(lang2, (String) null);
            if (!cache.isMissing(llKey)) {
                hTree = getHyphenationTree2(lang2, (String) null, hyphPathNames2);
                if (hTree != null && log.isDebugEnabled()) {
                    log.debug("Couldn't find hyphenation pattern for lang=\"" + lang2 + "\",country=\"" + country2 + "\". Using general language pattern for lang=\"" + lang2 + "\" instead.");
                }
                if (hTree == null) {
                    cache.noteMissing(llKey);
                } else {
                    cache.cache(llccKey, hTree);
                }
            }
        }
        if (hTree == null) {
            cache.noteMissing(llccKey);
            log.error("Couldn't find hyphenation pattern for lang=\"" + lang2 + "\"" + ((country2 == null || country2.equals("none")) ? "" : ",country=\"" + country2 + "\"") + ".");
        }
        return hTree;
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x0022 A[LOOP:0: B:9:0x0022->B:12:0x0032, LOOP_START, PHI: r2 
      PHI: (r2v5 'hTree' com.itextpdf.layout.hyphenation.HyphenationTree) = (r2v1 'hTree' com.itextpdf.layout.hyphenation.HyphenationTree), (r2v6 'hTree' com.itextpdf.layout.hyphenation.HyphenationTree) binds: [B:8:0x001e, B:12:0x0032] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.itextpdf.layout.hyphenation.HyphenationTree getHyphenationTree2(java.lang.String r6, java.lang.String r7, java.util.Map<java.lang.String, java.lang.String> r8) {
        /*
            java.lang.String r0 = com.itextpdf.layout.hyphenation.HyphenationTreeCache.constructLlccKey(r6, r7)
            com.itextpdf.layout.hyphenation.HyphenationTreeCache r1 = getHyphenationTreeCache()
            com.itextpdf.layout.hyphenation.HyphenationTreeCache r2 = getHyphenationTreeCache()
            com.itextpdf.layout.hyphenation.HyphenationTree r2 = r2.getHyphenationTree(r6, r7)
            if (r2 == 0) goto L_0x0013
            return r2
        L_0x0013:
            java.lang.String r3 = com.itextpdf.layout.hyphenation.HyphenationTreeCache.constructUserKey(r6, r7, r8)
            if (r3 != 0) goto L_0x001a
            r3 = r0
        L_0x001a:
            java.util.List<java.lang.String> r4 = additionalHyphenationFileDirectories
            if (r4 == 0) goto L_0x0036
            java.util.Iterator r4 = r4.iterator()
        L_0x0022:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x0036
            java.lang.Object r5 = r4.next()
            java.lang.String r5 = (java.lang.String) r5
            com.itextpdf.layout.hyphenation.HyphenationTree r2 = getHyphenationTree((java.lang.String) r5, (java.lang.String) r3)
            if (r2 == 0) goto L_0x0035
            goto L_0x0036
        L_0x0035:
            goto L_0x0022
        L_0x0036:
            if (r2 != 0) goto L_0x005b
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "com/itextpdf/hyph/"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r3)
            java.lang.String r5 = ".xml"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.io.InputStream r4 = com.itextpdf.p026io.util.ResourceUtil.getResourceStream(r4)
            if (r4 == 0) goto L_0x005b
            com.itextpdf.layout.hyphenation.HyphenationTree r2 = getHyphenationTree((java.io.InputStream) r4, (java.lang.String) r3)
        L_0x005b:
            if (r2 == 0) goto L_0x0060
            r1.cache(r0, r2)
        L_0x0060:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.hyphenation.Hyphenator.getHyphenationTree2(java.lang.String, java.lang.String, java.util.Map):com.itextpdf.layout.hyphenation.HyphenationTree");
    }

    public static HyphenationTree getHyphenationTree(String searchDirectory, String key) {
        String name = key + ".xml";
        try {
            return getHyphenationTree(new FileInputStream(searchDirectory + File.separator + name), name);
        } catch (IOException ioe) {
            if (!log.isDebugEnabled()) {
                return null;
            }
            log.debug("I/O problem while trying to load " + name + ": " + ioe.getMessage());
            return null;
        }
    }

    public static HyphenationTree getHyphenationTree(InputStream in, String name) {
        if (in == null) {
            return null;
        }
        try {
            HyphenationTree hTree = new HyphenationTree();
            hTree.loadPatterns(in, name);
            try {
                in.close();
            } catch (Exception e) {
            }
            return hTree;
        } catch (HyphenationException ex) {
            log.error("Can't load user patterns from XML file " + name + ": " + ex.getMessage());
            try {
                in.close();
            } catch (Exception e2) {
            }
            return null;
        } catch (Throwable th) {
            try {
                in.close();
            } catch (Exception e3) {
            }
            throw th;
        }
    }

    public static Hyphenation hyphenate(String lang2, String country2, Map<String, String> hyphPathNames2, String word, int leftMin2, int rightMin2) {
        if (wordContainsSoftHyphens(word)) {
            return hyphenateBasedOnSoftHyphens(word, leftMin2, rightMin2);
        }
        HyphenationTree hTree = null;
        if (lang2 != null) {
            hTree = getHyphenationTree(lang2, country2, hyphPathNames2);
        }
        if (hTree != null) {
            return hTree.hyphenate(word, leftMin2, rightMin2);
        }
        return null;
    }

    public static Hyphenation hyphenate(String lang2, String country2, String word, int leftMin2, int rightMin2) {
        return hyphenate(lang2, country2, (Map<String, String>) null, word, leftMin2, rightMin2);
    }

    public Hyphenation hyphenate(String word) {
        return hyphenate(this.lang, this.country, this.hyphPathNames, word, this.leftMin, this.rightMin);
    }

    private static boolean wordContainsSoftHyphens(String word) {
        return word.indexOf(CipherSuite.TLS_RSA_PSK_WITH_AES_256_GCM_SHA384) >= 0;
    }

    private static Hyphenation hyphenateBasedOnSoftHyphens(String word, int leftMin2, int rightMin2) {
        List<Integer> softHyphens = new ArrayList<>();
        int lastSoftHyphenIndex = -1;
        while (true) {
            int indexOf = word.indexOf(CipherSuite.TLS_RSA_PSK_WITH_AES_256_GCM_SHA384, lastSoftHyphenIndex + 1);
            int curSoftHyphenIndex = indexOf;
            if (indexOf <= 0) {
                break;
            }
            softHyphens.add(Integer.valueOf(curSoftHyphenIndex));
            lastSoftHyphenIndex = curSoftHyphenIndex;
        }
        int leftInd = 0;
        int rightInd = softHyphens.size() - 1;
        while (leftInd < softHyphens.size() && word.substring(0, softHyphens.get(leftInd).intValue()).replace(String.valueOf(SOFT_HYPHEN), "").length() < leftMin2) {
            leftInd++;
        }
        while (rightInd >= 0 && word.substring(softHyphens.get(rightInd).intValue() + 1).replace(String.valueOf(SOFT_HYPHEN), "").length() < rightMin2) {
            rightInd--;
        }
        if (leftInd > rightInd) {
            return null;
        }
        int[] hyphenationPoints = new int[((rightInd - leftInd) + 1)];
        for (int i = leftInd; i <= rightInd; i++) {
            hyphenationPoints[i - leftInd] = softHyphens.get(i).intValue();
        }
        return new Hyphenation(word, hyphenationPoints);
    }
}
