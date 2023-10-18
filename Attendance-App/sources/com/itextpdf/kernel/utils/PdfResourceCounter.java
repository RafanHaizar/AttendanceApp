package com.itextpdf.kernel.utils;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfIndirectReference;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfOutputStream;
import java.util.HashMap;
import java.util.Map;

public class PdfResourceCounter {
    private Map<Integer, PdfObject> resources = new HashMap();

    public PdfResourceCounter(PdfObject obj) {
        process(obj);
    }

    /* access modifiers changed from: protected */
    public final void process(PdfObject obj) {
        PdfIndirectReference ref = obj.getIndirectReference();
        if (ref == null) {
            loopOver(obj);
        } else if (!this.resources.containsKey(Integer.valueOf(ref.getObjNumber()))) {
            this.resources.put(Integer.valueOf(ref.getObjNumber()), obj);
            loopOver(obj);
        }
    }

    /* access modifiers changed from: protected */
    public final void loopOver(PdfObject obj) {
        switch (obj.getType()) {
            case 1:
                PdfArray array = (PdfArray) obj;
                for (int i = 0; i < array.size(); i++) {
                    process(array.get(i));
                }
                return;
            case 3:
            case 9:
                PdfDictionary dict = (PdfDictionary) obj;
                if (!PdfName.Pages.equals(dict.get(PdfName.Type))) {
                    for (PdfName name : dict.keySet()) {
                        process(dict.get(name));
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    public Map<Integer, PdfObject> getResources() {
        return this.resources;
    }

    public long getLength(Map<Integer, PdfObject> res) {
        long length = 0;
        for (Integer intValue : this.resources.keySet()) {
            int ref = intValue.intValue();
            if (res == null || !res.containsKey(Integer.valueOf(ref))) {
                PdfOutputStream os = new PdfOutputStream(new IdleOutputStream());
                os.write(this.resources.get(Integer.valueOf(ref)).clone());
                length += os.getCurrentPos();
            }
        }
        return length;
    }
}
