package com.itextpdf.forms.xfdf;

import java.util.ArrayList;
import java.util.List;

public class AnnotsObject {
    private List<AnnotObject> annotsList = new ArrayList();

    public List<AnnotObject> getAnnotsList() {
        return this.annotsList;
    }

    public AnnotsObject addAnnot(AnnotObject annot) {
        this.annotsList.add(annot);
        return this;
    }
}
