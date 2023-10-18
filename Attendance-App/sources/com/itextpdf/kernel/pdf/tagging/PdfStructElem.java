package com.itextpdf.kernel.pdf.tagging;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.IsoKey;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfIndirectReference;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.VersionConforming;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
import com.itextpdf.p026io.LogMessageConstant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.LoggerFactory;

public class PdfStructElem extends PdfObjectWrapper<PdfDictionary> implements IStructureNode {
    private static final long serialVersionUID = 7204356181229674005L;

    public PdfStructElem(PdfDictionary pdfObject) {
        super(pdfObject);
        setForbidRelease();
    }

    public PdfStructElem(PdfDocument document, PdfName role, PdfPage page) {
        this(document, role);
        ((PdfDictionary) getPdfObject()).put(PdfName.f1374Pg, ((PdfDictionary) page.getPdfObject()).getIndirectReference());
    }

    public PdfStructElem(PdfDocument document, PdfName role, PdfAnnotation annot) {
        this(document, role);
        if (annot.getPage() != null) {
            ((PdfDictionary) getPdfObject()).put(PdfName.f1374Pg, ((PdfDictionary) annot.getPage().getPdfObject()).getIndirectReference());
            return;
        }
        throw new PdfException(PdfException.AnnotationShallHaveReferenceToPage);
    }

    public PdfStructElem(PdfDocument document, PdfName role) {
        this((PdfDictionary) new PdfDictionary().makeIndirect(document));
        ((PdfDictionary) getPdfObject()).put(PdfName.Type, PdfName.StructElem);
        ((PdfDictionary) getPdfObject()).put(PdfName.f1385S, role);
    }

    public static boolean isStructElem(PdfDictionary dictionary) {
        return PdfName.StructElem.equals(dictionary.getAsName(PdfName.Type)) || dictionary.containsKey(PdfName.f1385S);
    }

    public PdfObject getAttributes(boolean createNewIfNull) {
        PdfObject attributes = ((PdfDictionary) getPdfObject()).get(PdfName.f1287A);
        if (attributes != null || !createNewIfNull) {
            return attributes;
        }
        PdfObject attributes2 = new PdfDictionary();
        setAttributes(attributes2);
        return attributes2;
    }

    public void setAttributes(PdfObject attributes) {
        put(PdfName.f1287A, attributes);
    }

    public PdfString getLang() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.Lang);
    }

    public void setLang(PdfString lang) {
        put(PdfName.Lang, lang);
    }

    public PdfString getAlt() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.Alt);
    }

    public void setAlt(PdfString alt) {
        put(PdfName.Alt, alt);
    }

    public PdfString getActualText() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.ActualText);
    }

    public void setActualText(PdfString actualText) {
        put(PdfName.ActualText, actualText);
    }

    public PdfString getE() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1320E);
    }

    public void setE(PdfString e) {
        put(PdfName.f1320E, e);
    }

    public PdfName getRole() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.f1385S);
    }

    public void setRole(PdfName role) {
        put(PdfName.f1385S, role);
    }

    public PdfStructElem addKid(PdfStructElem kid) {
        return addKid(-1, kid);
    }

    public PdfStructElem addKid(int index, PdfStructElem kid) {
        addKidObject((PdfDictionary) getPdfObject(), index, kid.getPdfObject());
        return kid;
    }

    public PdfMcr addKid(PdfMcr kid) {
        return addKid(-1, kid);
    }

    public PdfMcr addKid(int index, PdfMcr kid) {
        getDocEnsureIndirectForKids().getStructTreeRoot().getParentTreeHandler().registerMcr(kid);
        addKidObject((PdfDictionary) getPdfObject(), index, kid.getPdfObject());
        return kid;
    }

    public IStructureNode removeKid(int index) {
        return removeKid(index, false);
    }

    public IStructureNode removeKid(int index, boolean prepareForReAdding) {
        PdfObject k = getK();
        if (k == null || (!k.isArray() && index != 0)) {
            throw new IndexOutOfBoundsException();
        }
        if (k.isArray()) {
            PdfArray kidsArray = (PdfArray) k;
            k = kidsArray.get(index);
            kidsArray.remove(index);
            if (kidsArray.isEmpty()) {
                ((PdfDictionary) getPdfObject()).remove(PdfName.f1344K);
            }
        } else {
            ((PdfDictionary) getPdfObject()).remove(PdfName.f1344K);
        }
        setModified();
        IStructureNode removedKid = convertPdfObjectToIPdfStructElem(k);
        PdfDocument doc = getDocument();
        if ((removedKid instanceof PdfMcr) && doc != null && !prepareForReAdding) {
            doc.getStructTreeRoot().getParentTreeHandler().unregisterMcr((PdfMcr) removedKid);
        }
        return removedKid;
    }

    public int removeKid(IStructureNode kid) {
        if (kid instanceof PdfMcr) {
            PdfMcr mcr = (PdfMcr) kid;
            PdfDocument doc = getDocument();
            if (doc != null) {
                doc.getStructTreeRoot().getParentTreeHandler().unregisterMcr(mcr);
            }
            return removeKidObject(mcr.getPdfObject());
        } else if (kid instanceof PdfStructElem) {
            return removeKidObject(((PdfStructElem) kid).getPdfObject());
        } else {
            return -1;
        }
    }

    public IStructureNode getParent() {
        PdfDictionary parent = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1367P);
        if (parent == null) {
            return null;
        }
        if (parent.isFlushed()) {
            PdfDocument pdfDoc = getDocument();
            if (pdfDoc == null) {
                return null;
            }
            PdfStructTreeRoot structTreeRoot = pdfDoc.getStructTreeRoot();
            return structTreeRoot.getPdfObject() == parent ? structTreeRoot : new PdfStructElem(parent);
        } else if (isStructElem(parent)) {
            return new PdfStructElem(parent);
        } else {
            PdfDocument pdfDoc2 = getDocument();
            boolean parentIsRoot = true;
            if (!(pdfDoc2 != null && PdfName.StructTreeRoot.equals(parent.getAsName(PdfName.Type))) && (pdfDoc2 == null || pdfDoc2.getStructTreeRoot().getPdfObject() != parent)) {
                parentIsRoot = false;
            }
            if (parentIsRoot) {
                return pdfDoc2.getStructTreeRoot();
            }
            return null;
        }
    }

    public List<IStructureNode> getKids() {
        PdfObject k = getK();
        List<IStructureNode> kids = new ArrayList<>();
        if (k != null) {
            if (k.isArray()) {
                PdfArray a = (PdfArray) k;
                for (int i = 0; i < a.size(); i++) {
                    addKidObjectToStructElemList(a.get(i), kids);
                }
            } else {
                addKidObjectToStructElemList(k, kids);
            }
        }
        return kids;
    }

    public PdfObject getK() {
        return ((PdfDictionary) getPdfObject()).get(PdfName.f1344K);
    }

    public List<PdfStructElem> getRefsList() {
        PdfArray refsArray = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Ref);
        if (refsArray == null) {
            return Collections.emptyList();
        }
        List<PdfStructElem> refs = new ArrayList<>(refsArray.size());
        for (int i = 0; i < refsArray.size(); i++) {
            refs.add(new PdfStructElem(refsArray.getAsDictionary(i)));
        }
        return refs;
    }

    public void addRef(PdfStructElem ref) {
        if (((PdfDictionary) ref.getPdfObject()).isIndirect()) {
            VersionConforming.validatePdfVersionForDictEntry(getDocument(), PdfVersion.PDF_2_0, PdfName.Ref, PdfName.StructElem);
            PdfArray refsArray = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Ref);
            if (refsArray == null) {
                refsArray = new PdfArray();
                put(PdfName.Ref, refsArray);
            }
            refsArray.add(ref.getPdfObject());
            setModified();
            return;
        }
        throw new PdfException(PdfException.RefArrayItemsInStructureElementDictionaryShallBeIndirectObjects);
    }

    public PdfNamespace getNamespace() {
        PdfDictionary nsDict = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1360NS);
        if (nsDict != null) {
            return new PdfNamespace(nsDict);
        }
        return null;
    }

    public void setNamespace(PdfNamespace namespace) {
        VersionConforming.validatePdfVersionForDictEntry(getDocument(), PdfVersion.PDF_2_0, PdfName.f1360NS, PdfName.StructElem);
        if (namespace != null) {
            put(PdfName.f1360NS, namespace.getPdfObject());
            return;
        }
        ((PdfDictionary) getPdfObject()).remove(PdfName.f1360NS);
        setModified();
    }

    public void setPhoneme(PdfString elementPhoneme) {
        VersionConforming.validatePdfVersionForDictEntry(getDocument(), PdfVersion.PDF_2_0, PdfName.Phoneme, PdfName.StructElem);
        put(PdfName.Phoneme, elementPhoneme);
    }

    public PdfString getPhoneme() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.Phoneme);
    }

    public void setPhoneticAlphabet(PdfName phoneticAlphabet) {
        VersionConforming.validatePdfVersionForDictEntry(getDocument(), PdfVersion.PDF_2_0, PdfName.PhoneticAlphabet, PdfName.StructElem);
        put(PdfName.PhoneticAlphabet, phoneticAlphabet);
    }

    public PdfName getPhoneticAlphabet() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.PhoneticAlphabet);
    }

    public void addAssociatedFile(String description, PdfFileSpec fs) {
        if (((PdfDictionary) fs.getPdfObject()).get(PdfName.AFRelationship) == null) {
            LoggerFactory.getLogger((Class<?>) PdfStructElem.class).error(LogMessageConstant.ASSOCIATED_FILE_SPEC_SHALL_INCLUDE_AFRELATIONSHIP);
        }
        if (description != null) {
            getDocument().getCatalog().getNameTree(PdfName.EmbeddedFiles).addEntry(description, fs.getPdfObject());
        }
        PdfArray afArray = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1289AF);
        if (afArray == null) {
            afArray = new PdfArray();
            put(PdfName.f1289AF, afArray);
        }
        afArray.add(fs.getPdfObject());
    }

    public void addAssociatedFile(PdfFileSpec fs) {
        addAssociatedFile((String) null, fs);
    }

    public PdfArray getAssociatedFiles(boolean create) {
        PdfArray afArray = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1289AF);
        if (afArray != null || !create) {
            return afArray;
        }
        PdfArray afArray2 = new PdfArray();
        put(PdfName.f1289AF, afArray2);
        return afArray2;
    }

    public PdfStructElem put(PdfName key, PdfObject value) {
        ((PdfDictionary) getPdfObject()).put(key, value);
        setModified();
        return this;
    }

    public void flush() {
        PdfDictionary pageDict = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1374Pg);
        if (pageDict == null || (pageDict.getIndirectReference() != null && pageDict.getIndirectReference().isFree())) {
            ((PdfDictionary) getPdfObject()).remove(PdfName.f1374Pg);
        }
        PdfDocument doc = getDocument();
        if (doc != null) {
            doc.checkIsoConformance(getPdfObject(), IsoKey.TAG_STRUCTURE_ELEMENT);
        }
        super.flush();
    }

    static void addKidObject(PdfDictionary parent, int index, PdfObject kid) {
        PdfArray a;
        if (parent.isFlushed()) {
            throw new PdfException(PdfException.CannotAddKidToTheFlushedElement);
        } else if (parent.containsKey(PdfName.f1367P)) {
            PdfObject k = parent.get(PdfName.f1344K);
            if (k == null) {
                parent.put(PdfName.f1344K, kid);
            } else {
                if (k instanceof PdfArray) {
                    a = (PdfArray) k;
                } else {
                    a = new PdfArray();
                    a.add(k);
                    parent.put(PdfName.f1344K, a);
                }
                if (index == -1) {
                    a.add(kid);
                } else {
                    a.add(index, kid);
                }
            }
            parent.setModified();
            if ((kid instanceof PdfDictionary) && isStructElem((PdfDictionary) kid)) {
                if (parent.isIndirect()) {
                    ((PdfDictionary) kid).put(PdfName.f1367P, parent);
                    kid.setModified();
                    return;
                }
                throw new PdfException(PdfException.f1244x971f4981);
            }
        } else {
            throw new PdfException(PdfException.StructureElementShallContainParentObject, (Object) parent);
        }
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }

    /* access modifiers changed from: protected */
    public PdfDocument getDocument() {
        PdfDictionary structDict = (PdfDictionary) getPdfObject();
        PdfIndirectReference indRef = structDict.getIndirectReference();
        if (indRef == null && structDict.getAsDictionary(PdfName.f1367P) != null) {
            indRef = structDict.getAsDictionary(PdfName.f1367P).getIndirectReference();
        }
        if (indRef != null) {
            return indRef.getDocument();
        }
        return null;
    }

    private PdfDocument getDocEnsureIndirectForKids() {
        PdfDocument doc = getDocument();
        if (doc != null) {
            return doc;
        }
        throw new PdfException(PdfException.f1244x971f4981);
    }

    private void addKidObjectToStructElemList(PdfObject k, List<IStructureNode> list) {
        if (k.isFlushed()) {
            list.add((Object) null);
        } else {
            list.add(convertPdfObjectToIPdfStructElem(k));
        }
    }

    private IStructureNode convertPdfObjectToIPdfStructElem(PdfObject obj) {
        switch (obj.getType()) {
            case 3:
                PdfDictionary d = (PdfDictionary) obj;
                if (isStructElem(d)) {
                    return new PdfStructElem(d);
                }
                if (PdfName.MCR.equals(d.getAsName(PdfName.Type))) {
                    return new PdfMcrDictionary(d, this);
                }
                if (PdfName.OBJR.equals(d.getAsName(PdfName.Type))) {
                    return new PdfObjRef(d, this);
                }
                return null;
            case 8:
                return new PdfMcrNumber((PdfNumber) obj, this);
            default:
                return null;
        }
    }

    private int removeKidObject(PdfObject kid) {
        PdfObject k = getK();
        if (k == null) {
            return -1;
        }
        if (!k.isArray() && k != kid && k != kid.getIndirectReference()) {
            return -1;
        }
        int removedIndex = -1;
        if (k.isArray()) {
            removedIndex = removeObjectFromArray((PdfArray) k, kid);
        }
        if (!k.isArray() || (k.isArray() && ((PdfArray) k).isEmpty())) {
            ((PdfDictionary) getPdfObject()).remove(PdfName.f1344K);
            removedIndex = 0;
        }
        setModified();
        return removedIndex;
    }

    private static int removeObjectFromArray(PdfArray array, PdfObject toRemove) {
        int i = 0;
        while (true) {
            if (i >= array.size()) {
                break;
            }
            PdfObject obj = array.get(i);
            if (obj == toRemove || obj == toRemove.getIndirectReference()) {
                array.remove(i);
            } else {
                i++;
            }
        }
        array.remove(i);
        return i;
    }
}
