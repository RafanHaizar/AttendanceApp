package com.itextpdf.kernel.pdf.tagging;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.VersionConforming;
import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
import com.itextpdf.kernel.pdf.tagging.ParentTreeHandler;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.LoggerFactory;

public class PdfStructTreeRoot extends PdfObjectWrapper<PdfDictionary> implements IStructureNode {
    private static final long serialVersionUID = 2168384302241193868L;
    private static Map<String, PdfName> staticRoleNames = new ConcurrentHashMap();
    private PdfDocument document;
    private ParentTreeHandler parentTreeHandler;

    public PdfStructTreeRoot(PdfDocument document2) {
        this((PdfDictionary) new PdfDictionary().makeIndirect(document2), document2);
        ((PdfDictionary) getPdfObject()).put(PdfName.Type, PdfName.StructTreeRoot);
    }

    public PdfStructTreeRoot(PdfDictionary pdfObject, PdfDocument document2) {
        super(pdfObject);
        this.document = document2;
        if (document2 == null) {
            ensureObjectIsAddedToDocument(pdfObject);
            this.document = pdfObject.getIndirectReference().getDocument();
        }
        setForbidRelease();
        this.parentTreeHandler = new ParentTreeHandler(this);
        getRoleMap();
    }

    public static PdfName convertRoleToPdfName(String role) {
        PdfName name = PdfName.staticNames.get(role);
        if (name != null) {
            return name;
        }
        PdfName name2 = staticRoleNames.get(role);
        if (name2 != null) {
            return name2;
        }
        PdfName name3 = new PdfName(role);
        staticRoleNames.put(role, name3);
        return name3;
    }

    public PdfStructElem addKid(PdfStructElem structElem) {
        return addKid(-1, structElem);
    }

    public PdfStructElem addKid(int index, PdfStructElem structElem) {
        addKidObject(index, (PdfDictionary) structElem.getPdfObject());
        return structElem;
    }

    public IStructureNode getParent() {
        return null;
    }

    public List<IStructureNode> getKids() {
        PdfObject k = ((PdfDictionary) getPdfObject()).get(PdfName.f1344K);
        List<IStructureNode> kids = new ArrayList<>();
        if (k != null) {
            if (k.isArray()) {
                PdfArray a = (PdfArray) k;
                for (int i = 0; i < a.size(); i++) {
                    ifKidIsStructElementAddToList(a.get(i), kids);
                }
            } else {
                ifKidIsStructElementAddToList(k, kids);
            }
        }
        return kids;
    }

    public PdfArray getKidsObject() {
        PdfArray k = null;
        PdfObject kObj = ((PdfDictionary) getPdfObject()).get(PdfName.f1344K);
        if (kObj != null && kObj.isArray()) {
            k = (PdfArray) kObj;
        }
        if (k == null) {
            k = new PdfArray();
            ((PdfDictionary) getPdfObject()).put(PdfName.f1344K, k);
            setModified();
            if (kObj != null) {
                k.add(kObj);
            }
        }
        return k;
    }

    public void addRoleMapping(String fromRole, String toRole) {
        PdfDictionary roleMap = getRoleMap();
        PdfObject prevVal = roleMap.put(convertRoleToPdfName(fromRole), convertRoleToPdfName(toRole));
        if (prevVal != null && (prevVal instanceof PdfName)) {
            LoggerFactory.getLogger((Class<?>) PdfStructTreeRoot.class).warn(MessageFormat.format(LogMessageConstant.MAPPING_IN_STRUCT_ROOT_OVERWRITTEN, new Object[]{fromRole, prevVal, toRole}));
        }
        if (roleMap.isIndirect()) {
            roleMap.setModified();
        } else {
            setModified();
        }
    }

    public PdfDictionary getRoleMap() {
        PdfDictionary roleMap = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.RoleMap);
        if (roleMap != null) {
            return roleMap;
        }
        PdfDictionary roleMap2 = new PdfDictionary();
        ((PdfDictionary) getPdfObject()).put(PdfName.RoleMap, roleMap2);
        setModified();
        return roleMap2;
    }

    public List<PdfNamespace> getNamespaces() {
        PdfArray namespacesArray = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Namespaces);
        if (namespacesArray == null) {
            return Collections.emptyList();
        }
        List<PdfNamespace> namespacesList = new ArrayList<>(namespacesArray.size());
        for (int i = 0; i < namespacesArray.size(); i++) {
            namespacesList.add(new PdfNamespace(namespacesArray.getAsDictionary(i)));
        }
        return namespacesList;
    }

    public void addNamespace(PdfNamespace namespace) {
        getNamespacesObject().add(namespace.getPdfObject());
        setModified();
    }

    public PdfArray getNamespacesObject() {
        PdfArray namespacesArray = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Namespaces);
        if (namespacesArray != null) {
            return namespacesArray;
        }
        PdfArray namespacesArray2 = new PdfArray();
        VersionConforming.validatePdfVersionForDictEntry(getDocument(), PdfVersion.PDF_2_0, PdfName.Namespaces, PdfName.StructTreeRoot);
        ((PdfDictionary) getPdfObject()).put(PdfName.Namespaces, namespacesArray2);
        setModified();
        return namespacesArray2;
    }

    public List<PdfFileSpec> getPronunciationLexiconsList() {
        PdfArray pronunciationLexicons = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.PronunciationLexicon);
        if (pronunciationLexicons == null) {
            return Collections.emptyList();
        }
        List<PdfFileSpec> lexiconsList = new ArrayList<>(pronunciationLexicons.size());
        for (int i = 0; i < pronunciationLexicons.size(); i++) {
            lexiconsList.add(PdfFileSpec.wrapFileSpecObject(pronunciationLexicons.get(i)));
        }
        return lexiconsList;
    }

    public void addPronunciationLexicon(PdfFileSpec pronunciationLexiconFileSpec) {
        PdfArray pronunciationLexicons = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.PronunciationLexicon);
        if (pronunciationLexicons == null) {
            pronunciationLexicons = new PdfArray();
            VersionConforming.validatePdfVersionForDictEntry(getDocument(), PdfVersion.PDF_2_0, PdfName.PronunciationLexicon, PdfName.StructTreeRoot);
            ((PdfDictionary) getPdfObject()).put(PdfName.PronunciationLexicon, pronunciationLexicons);
        }
        pronunciationLexicons.add(pronunciationLexiconFileSpec.getPdfObject());
        setModified();
    }

    public void createParentTreeEntryForPage(PdfPage page) {
        getParentTreeHandler().createParentTreeEntryForPage(page);
    }

    public void savePageStructParentIndexIfNeeded(PdfPage page) {
        getParentTreeHandler().savePageStructParentIndexIfNeeded(page);
    }

    public Collection<PdfMcr> getPageMarkedContentReferences(PdfPage page) {
        ParentTreeHandler.PageMcrsContainer pageMcrs = getParentTreeHandler().getPageMarkedContentReferences(page);
        if (pageMcrs != null) {
            return Collections.unmodifiableCollection(pageMcrs.getAllMcrsAsCollection());
        }
        return null;
    }

    public PdfMcr findMcrByMcid(PdfDictionary pageDict, int mcid) {
        return getParentTreeHandler().findMcrByMcid(pageDict, mcid);
    }

    public PdfObjRef findObjRefByStructParentIndex(PdfDictionary pageDict, int structParentIndex) {
        return getParentTreeHandler().findObjRefByStructParentIndex(pageDict, structParentIndex);
    }

    public PdfName getRole() {
        return null;
    }

    public void flush() {
        for (int i = 0; i < getDocument().getNumberOfPages(); i++) {
            createParentTreeEntryForPage(getDocument().getPage(i + 1));
        }
        ((PdfDictionary) getPdfObject()).put(PdfName.ParentTree, getParentTreeHandler().buildParentTree());
        ((PdfDictionary) getPdfObject()).put(PdfName.ParentTreeNextKey, new PdfNumber(getDocument().getNextStructParentIndex()));
        if (!getDocument().isAppendMode()) {
            flushAllKids(this);
        }
        super.flush();
    }

    public void copyTo(PdfDocument destDocument, Map<PdfPage, PdfPage> page2page) {
        StructureTreeCopier.copyTo(destDocument, page2page, getDocument());
    }

    public void copyTo(PdfDocument destDocument, int insertBeforePage, Map<PdfPage, PdfPage> page2page) {
        StructureTreeCopier.copyTo(destDocument, insertBeforePage, page2page, getDocument());
    }

    public void move(PdfPage fromPage, int insertBeforePage) {
        int i = 1;
        while (i <= getDocument().getNumberOfPages()) {
            if (!getDocument().getPage(i).isFlushed()) {
                i++;
            } else {
                throw new PdfException(MessageFormatUtil.format(PdfException.CannotMovePagesInPartlyFlushedDocument, Integer.valueOf(i)));
            }
        }
        StructureTreeCopier.move(getDocument(), fromPage, insertBeforePage);
    }

    public int getParentTreeNextKey() {
        return ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.ParentTreeNextKey).intValue();
    }

    public int getNextMcidForPage(PdfPage page) {
        return getParentTreeHandler().getNextMcidForPage(page);
    }

    public PdfDocument getDocument() {
        return this.document;
    }

    public void addAssociatedFile(String description, PdfFileSpec fs) {
        if (((PdfDictionary) fs.getPdfObject()).get(PdfName.AFRelationship) == null) {
            LoggerFactory.getLogger((Class<?>) PdfStructTreeRoot.class).error(LogMessageConstant.ASSOCIATED_FILE_SPEC_SHALL_INCLUDE_AFRELATIONSHIP);
        }
        if (description != null) {
            getDocument().getCatalog().getNameTree(PdfName.EmbeddedFiles).addEntry(description, fs.getPdfObject());
        }
        PdfArray afArray = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1289AF);
        if (afArray == null) {
            afArray = new PdfArray();
            ((PdfDictionary) getPdfObject()).put(PdfName.f1289AF, afArray);
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
        ((PdfDictionary) getPdfObject()).put(PdfName.f1289AF, afArray2);
        return afArray2;
    }

    /* access modifiers changed from: package-private */
    public ParentTreeHandler getParentTreeHandler() {
        return this.parentTreeHandler;
    }

    /* access modifiers changed from: package-private */
    public void addKidObject(int index, PdfDictionary structElem) {
        if (index == -1) {
            getKidsObject().add(structElem);
        } else {
            getKidsObject().add(index, structElem);
        }
        if (PdfStructElem.isStructElem(structElem)) {
            if (((PdfDictionary) getPdfObject()).getIndirectReference() != null) {
                structElem.put(PdfName.f1367P, getPdfObject());
            } else {
                throw new PdfException(PdfException.f1244x971f4981);
            }
        }
        setModified();
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }

    private void flushAllKids(IStructureNode elem) {
        for (IStructureNode kid : elem.getKids()) {
            if ((kid instanceof PdfStructElem) && !((PdfStructElem) kid).isFlushed()) {
                flushAllKids(kid);
                ((PdfStructElem) kid).flush();
            }
        }
    }

    private void ifKidIsStructElementAddToList(PdfObject kid, List<IStructureNode> kids) {
        if (kid.isFlushed()) {
            kids.add((Object) null);
        } else if (kid.isDictionary() && PdfStructElem.isStructElem((PdfDictionary) kid)) {
            kids.add(new PdfStructElem((PdfDictionary) kid));
        }
    }
}
