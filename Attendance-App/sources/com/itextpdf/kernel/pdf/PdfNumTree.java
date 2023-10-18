package com.itextpdf.kernel.pdf;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PdfNumTree implements Serializable {
    private static final int NODE_SIZE = 40;
    private static final long serialVersionUID = 2636796232945164670L;
    private PdfCatalog catalog;
    private Map<Integer, PdfObject> items = new HashMap();
    private PdfName treeType;

    public PdfNumTree(PdfCatalog catalog2, PdfName treeType2) {
        this.treeType = treeType2;
        this.catalog = catalog2;
    }

    public Map<Integer, PdfObject> getNumbers() {
        PdfDictionary structTreeRoot;
        if (this.items.size() > 0) {
            return this.items;
        }
        PdfDictionary numbers = null;
        if (this.treeType.equals(PdfName.PageLabels)) {
            numbers = ((PdfDictionary) this.catalog.getPdfObject()).getAsDictionary(PdfName.PageLabels);
        } else if (this.treeType.equals(PdfName.ParentTree) && (structTreeRoot = ((PdfDictionary) this.catalog.getPdfObject()).getAsDictionary(PdfName.StructTreeRoot)) != null) {
            numbers = structTreeRoot.getAsDictionary(PdfName.ParentTree);
        }
        if (numbers != null) {
            readTree(numbers);
        }
        return this.items;
    }

    public void addEntry(int key, PdfObject value) {
        this.items.put(new Integer(key), value);
    }

    public PdfDictionary buildTree() {
        Integer[] numbers = (Integer[]) this.items.keySet().toArray(new Integer[this.items.size()]);
        Arrays.sort(numbers);
        if (numbers.length <= 40) {
            PdfDictionary dic = new PdfDictionary();
            PdfArray ar = new PdfArray();
            for (int k = 0; k < numbers.length; k++) {
                ar.add(new PdfNumber(numbers[k].intValue()));
                ar.add(this.items.get(numbers[k]));
            }
            dic.put(PdfName.Nums, ar);
            return dic;
        }
        int skip = 40;
        PdfDictionary[] kids = new PdfDictionary[(((numbers.length + 40) - 1) / 40)];
        for (int i = 0; i < kids.length; i++) {
            int offset = i * 40;
            int end = Math.min(offset + 40, numbers.length);
            PdfDictionary dic2 = new PdfDictionary();
            PdfArray arr = new PdfArray();
            arr.add(new PdfNumber(numbers[offset].intValue()));
            arr.add(new PdfNumber(numbers[end - 1].intValue()));
            dic2.put(PdfName.Limits, arr);
            PdfArray arr2 = new PdfArray();
            while (offset < end) {
                arr2.add(new PdfNumber(numbers[offset].intValue()));
                arr2.add(this.items.get(numbers[offset]));
                offset++;
            }
            dic2.put(PdfName.Nums, arr2);
            dic2.makeIndirect(this.catalog.getDocument());
            kids[i] = dic2;
        }
        int top = kids.length;
        while (top > 40) {
            skip *= 40;
            int tt = ((numbers.length + skip) - 1) / skip;
            for (int k2 = 0; k2 < tt; k2++) {
                int offset2 = k2 * 40;
                int end2 = Math.min(offset2 + 40, top);
                PdfDictionary dic3 = (PdfDictionary) new PdfDictionary().makeIndirect(this.catalog.getDocument());
                PdfArray arr3 = new PdfArray();
                arr3.add(new PdfNumber(numbers[k2 * skip].intValue()));
                arr3.add(new PdfNumber(numbers[Math.min((k2 + 1) * skip, numbers.length) - 1].intValue()));
                dic3.put(PdfName.Limits, arr3);
                PdfArray arr4 = new PdfArray();
                while (offset2 < end2) {
                    arr4.add(kids[offset2]);
                    offset2++;
                }
                dic3.put(PdfName.Kids, arr4);
                kids[k2] = dic3;
            }
            top = tt;
        }
        PdfArray arr5 = new PdfArray();
        for (int k3 = 0; k3 < top; k3++) {
            arr5.add(kids[k3]);
        }
        PdfDictionary dic4 = new PdfDictionary();
        dic4.put(PdfName.Kids, arr5);
        return dic4;
    }

    private void readTree(PdfDictionary dictionary) {
        if (dictionary != null) {
            iterateItems(dictionary, (PdfNumber) null);
        }
    }

    private PdfNumber iterateItems(PdfDictionary dictionary, PdfNumber leftOver) {
        PdfNumber number;
        PdfArray nums = dictionary.getAsArray(PdfName.Nums);
        if (nums != null) {
            int k = 0;
            while (k < nums.size()) {
                if (leftOver == null) {
                    int i = k + 1;
                    number = nums.getAsNumber(k);
                    k = i;
                } else {
                    number = leftOver;
                    leftOver = null;
                }
                if (k >= nums.size()) {
                    return number;
                }
                this.items.put(Integer.valueOf(number.intValue()), nums.get(k));
                k++;
            }
            return null;
        }
        PdfArray asArray = dictionary.getAsArray(PdfName.Kids);
        PdfArray nums2 = asArray;
        if (asArray == null) {
            return null;
        }
        for (int k2 = 0; k2 < nums2.size(); k2++) {
            leftOver = iterateItems(nums2.getAsDictionary(k2), leftOver);
        }
        return null;
    }
}
