package org.bouncycastle.asn1.x509;

import java.util.Vector;

public class GeneralNamesBuilder {
    private Vector names = new Vector();

    public GeneralNamesBuilder addName(GeneralName generalName) {
        this.names.addElement(generalName);
        return this;
    }

    public GeneralNamesBuilder addNames(GeneralNames generalNames) {
        GeneralName[] names2 = generalNames.getNames();
        for (int i = 0; i != names2.length; i++) {
            this.names.addElement(names2[i]);
        }
        return this;
    }

    public GeneralNames build() {
        int size = this.names.size();
        GeneralName[] generalNameArr = new GeneralName[size];
        for (int i = 0; i != size; i++) {
            generalNameArr[i] = (GeneralName) this.names.elementAt(i);
        }
        return new GeneralNames(generalNameArr);
    }
}
