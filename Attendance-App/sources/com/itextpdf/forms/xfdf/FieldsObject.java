package com.itextpdf.forms.xfdf;

import java.util.ArrayList;
import java.util.List;

public class FieldsObject {
    private List<FieldObject> fieldList = new ArrayList();

    public List<FieldObject> getFieldList() {
        return this.fieldList;
    }

    public FieldsObject addField(FieldObject field) {
        this.fieldList.add(field);
        return this;
    }
}
