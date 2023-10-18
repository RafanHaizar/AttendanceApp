package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.geom.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class PdfArray extends PdfObject implements Iterable<PdfObject> {
    private static final long serialVersionUID = 1617495612878046869L;
    protected List<PdfObject> list;

    public PdfArray() {
        this.list = new ArrayList();
    }

    public PdfArray(PdfObject obj) {
        this();
        this.list.add(obj);
    }

    public PdfArray(PdfArray arr) {
        this();
        this.list.addAll(arr.list);
    }

    public PdfArray(Rectangle rectangle) {
        this.list = new ArrayList(4);
        add(new PdfNumber((double) rectangle.getLeft()));
        add(new PdfNumber((double) rectangle.getBottom()));
        add(new PdfNumber((double) rectangle.getRight()));
        add(new PdfNumber((double) rectangle.getTop()));
    }

    public PdfArray(List<? extends PdfObject> objects) {
        this.list = new ArrayList(objects.size());
        for (PdfObject element : objects) {
            add(element);
        }
    }

    public PdfArray(float[] numbers) {
        this.list = new ArrayList(numbers.length);
        for (float f : numbers) {
            this.list.add(new PdfNumber((double) f));
        }
    }

    public PdfArray(double[] numbers) {
        this.list = new ArrayList(numbers.length);
        for (double f : numbers) {
            this.list.add(new PdfNumber(f));
        }
    }

    public PdfArray(int[] numbers) {
        this.list = new ArrayList(numbers.length);
        for (int i : numbers) {
            this.list.add(new PdfNumber((double) ((float) i)));
        }
    }

    public PdfArray(boolean[] values) {
        this.list = new ArrayList(values.length);
        for (boolean b : values) {
            this.list.add(PdfBoolean.valueOf(b));
        }
    }

    public PdfArray(List<String> strings, boolean asNames) {
        this.list = new ArrayList(strings.size());
        for (String s : strings) {
            this.list.add(asNames ? new PdfName(s) : new PdfString(s));
        }
    }

    public PdfArray(Iterable<? extends PdfObject> objects, int initialCapacity) {
        this.list = new ArrayList(initialCapacity);
        for (PdfObject element : objects) {
            add(element);
        }
    }

    public int size() {
        return this.list.size();
    }

    public boolean isEmpty() {
        return this.list.size() == 0;
    }

    public boolean contains(PdfObject o) {
        if (this.list.contains(o)) {
            return true;
        }
        if (o == null) {
            return false;
        }
        Iterator<PdfObject> it = iterator();
        while (it.hasNext()) {
            if (PdfObject.equalContent(o, it.next())) {
                return true;
            }
        }
        return false;
    }

    public Iterator<PdfObject> iterator() {
        return new PdfArrayDirectIterator(this.list);
    }

    public void add(PdfObject pdfObject) {
        this.list.add(pdfObject);
    }

    public void add(int index, PdfObject element) {
        this.list.add(index, element);
    }

    public PdfObject set(int index, PdfObject element) {
        return this.list.set(index, element);
    }

    public void addAll(Collection<PdfObject> c) {
        this.list.addAll(c);
    }

    public void addAll(PdfArray a) {
        if (a != null) {
            addAll((Collection<PdfObject>) a.list);
        }
    }

    public PdfObject get(int index) {
        return get(index, true);
    }

    public void remove(int index) {
        this.list.remove(index);
    }

    public void remove(PdfObject o) {
        if (!this.list.remove(o) && o != null) {
            for (PdfObject pdfObject : this.list) {
                if (PdfObject.equalContent(o, pdfObject)) {
                    this.list.remove(pdfObject);
                    return;
                }
            }
        }
    }

    public void clear() {
        this.list.clear();
    }

    public int indexOf(PdfObject o) {
        if (o == null) {
            return this.list.indexOf((Object) null);
        }
        int index = 0;
        Iterator<PdfObject> it = iterator();
        while (it.hasNext()) {
            if (PdfObject.equalContent(o, it.next())) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public List<PdfObject> subList(int fromIndex, int toIndex) {
        return this.list.subList(fromIndex, toIndex);
    }

    public byte getType() {
        return 1;
    }

    public String toString() {
        String string = "[";
        for (PdfObject entry : this.list) {
            PdfIndirectReference indirectReference = entry.getIndirectReference();
            string = string + (indirectReference == null ? entry.toString() : indirectReference.toString()) + " ";
        }
        return string + "]";
    }

    public PdfObject get(int index, boolean asDirect) {
        if (!asDirect) {
            return this.list.get(index);
        }
        PdfObject obj = this.list.get(index);
        if (obj.getType() == 5) {
            return ((PdfIndirectReference) obj).getRefersTo(true);
        }
        return obj;
    }

    public PdfArray getAsArray(int index) {
        PdfObject direct = get(index, true);
        if (direct == null || direct.getType() != 1) {
            return null;
        }
        return (PdfArray) direct;
    }

    public PdfDictionary getAsDictionary(int index) {
        PdfObject direct = get(index, true);
        if (direct == null || direct.getType() != 3) {
            return null;
        }
        return (PdfDictionary) direct;
    }

    public PdfStream getAsStream(int index) {
        PdfObject direct = get(index, true);
        if (direct == null || direct.getType() != 9) {
            return null;
        }
        return (PdfStream) direct;
    }

    public PdfNumber getAsNumber(int index) {
        PdfObject direct = get(index, true);
        if (direct == null || direct.getType() != 8) {
            return null;
        }
        return (PdfNumber) direct;
    }

    public PdfName getAsName(int index) {
        PdfObject direct = get(index, true);
        if (direct == null || direct.getType() != 6) {
            return null;
        }
        return (PdfName) direct;
    }

    public PdfString getAsString(int index) {
        PdfObject direct = get(index, true);
        if (direct == null || direct.getType() != 10) {
            return null;
        }
        return (PdfString) direct;
    }

    public PdfBoolean getAsBoolean(int index) {
        PdfObject direct = get(index, true);
        if (direct == null || direct.getType() != 2) {
            return null;
        }
        return (PdfBoolean) direct;
    }

    public Rectangle toRectangle() {
        try {
            float x1 = getAsNumber(0).floatValue();
            float y1 = getAsNumber(1).floatValue();
            float x2 = getAsNumber(2).floatValue();
            float y2 = getAsNumber(3).floatValue();
            float llx = Math.min(x1, x2);
            float lly = Math.min(y1, y2);
            return new Rectangle(llx, lly, Math.max(x1, x2) - llx, Math.max(y1, y2) - lly);
        } catch (Exception e) {
            throw new PdfException(PdfException.CannotConvertPdfArrayToRectanle, e, this);
        }
    }

    public float[] toFloatArray() {
        try {
            float[] rslt = new float[size()];
            for (int k = 0; k < rslt.length; k++) {
                rslt[k] = getAsNumber(k).floatValue();
            }
            return rslt;
        } catch (Exception e) {
            throw new PdfException(PdfException.CannotConvertPdfArrayToFloatArray, e, this);
        }
    }

    public double[] toDoubleArray() {
        try {
            double[] rslt = new double[size()];
            for (int k = 0; k < rslt.length; k++) {
                rslt[k] = getAsNumber(k).doubleValue();
            }
            return rslt;
        } catch (Exception e) {
            throw new PdfException(PdfException.CannotConvertPdfArrayToDoubleArray, e, this);
        }
    }

    public long[] toLongArray() {
        try {
            long[] rslt = new long[size()];
            for (int k = 0; k < rslt.length; k++) {
                rslt[k] = getAsNumber(k).longValue();
            }
            return rslt;
        } catch (Exception e) {
            throw new PdfException(PdfException.CannotConvertPdfArrayToLongArray, e, this);
        }
    }

    public int[] toIntArray() {
        try {
            int[] rslt = new int[size()];
            for (int k = 0; k < rslt.length; k++) {
                rslt[k] = getAsNumber(k).intValue();
            }
            return rslt;
        } catch (Exception e) {
            throw new PdfException(PdfException.CannotConvertPdfArrayToIntArray, e, this);
        }
    }

    public boolean[] toBooleanArray() {
        boolean[] rslt = new boolean[size()];
        int k = 0;
        while (k < rslt.length) {
            PdfBoolean tmp = getAsBoolean(k);
            if (tmp != null) {
                rslt[k] = tmp.getValue();
                k++;
            } else {
                throw new PdfException(PdfException.CannotConvertPdfArrayToBooleanArray, (Object) this);
            }
        }
        return rslt;
    }

    /* access modifiers changed from: protected */
    public PdfObject newInstance() {
        return new PdfArray();
    }

    /* access modifiers changed from: protected */
    public void copyContent(PdfObject from, PdfDocument document) {
        super.copyContent(from, document);
        for (PdfObject entry : ((PdfArray) from).list) {
            add(entry.processCopying(document, false));
        }
    }

    /* access modifiers changed from: protected */
    public void releaseContent() {
        this.list = null;
    }
}
