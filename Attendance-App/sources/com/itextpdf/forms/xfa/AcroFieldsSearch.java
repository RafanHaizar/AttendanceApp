package com.itextpdf.forms.xfa;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class AcroFieldsSearch extends Xml2Som {
    private Map<String, String> acroShort2LongName = new HashMap();

    public AcroFieldsSearch(Collection<String> items) {
        this.inverseSearch = new HashMap();
        for (String itemName : items) {
            String itemShort = getShortName(itemName);
            this.acroShort2LongName.put(itemShort, itemName);
            inverseSearchAdd(this.inverseSearch, splitParts(itemShort), itemName);
        }
    }

    public Map<String, String> getAcroShort2LongName() {
        return this.acroShort2LongName;
    }

    public void setAcroShort2LongName(Map<String, String> acroShort2LongName2) {
        this.acroShort2LongName = acroShort2LongName2;
    }
}
