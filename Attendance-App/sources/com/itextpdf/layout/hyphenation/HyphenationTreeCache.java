package com.itextpdf.layout.hyphenation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HyphenationTreeCache {
    private Map<String, HyphenationTree> hyphenTrees = new HashMap();
    private Set<String> missingHyphenationTrees;

    public HyphenationTree getHyphenationTree(String lang, String country) {
        String key = constructLlccKey(lang, country);
        if (key == null) {
            return null;
        }
        if (this.hyphenTrees.containsKey(key)) {
            return this.hyphenTrees.get(key);
        }
        if (this.hyphenTrees.containsKey(lang)) {
            return this.hyphenTrees.get(lang);
        }
        return null;
    }

    public static String constructLlccKey(String lang, String country) {
        String key = lang;
        if (country == null || country.equals("none")) {
            return key;
        }
        return key + "_" + country;
    }

    public static String constructUserKey(String lang, String country, Map<String, String> hyphPatNames) {
        if (hyphPatNames != null) {
            return hyphPatNames.get(constructLlccKey(lang, country).replace('_', '-'));
        }
        return null;
    }

    public void cache(String key, HyphenationTree hTree) {
        this.hyphenTrees.put(key, hTree);
    }

    public void noteMissing(String key) {
        if (this.missingHyphenationTrees == null) {
            this.missingHyphenationTrees = new HashSet();
        }
        this.missingHyphenationTrees.add(key);
    }

    public boolean isMissing(String key) {
        Set<String> set = this.missingHyphenationTrees;
        return set != null && set.contains(key);
    }
}
