package com.itextpdf.kernel.pdf.tagutils;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfIndirectReference;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.tagging.IStructureNode;
import com.itextpdf.kernel.pdf.tagging.PdfMcr;
import com.itextpdf.kernel.pdf.tagging.PdfNamespace;
import com.itextpdf.kernel.pdf.tagging.PdfObjRef;
import com.itextpdf.kernel.pdf.tagging.PdfStructElem;
import com.itextpdf.kernel.pdf.tagging.PdfStructTreeRoot;
import com.itextpdf.kernel.pdf.tagging.StandardNamespaces;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.p026io.LogMessageConstant;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TagStructureContext {
    private static final Set<String> allowedRootTagRoles;
    protected TagTreePointer autoTaggingPointer;
    private PdfDocument document;
    private PdfNamespace documentDefaultNamespace;
    private boolean forbidUnknownRoles;
    private Map<String, PdfNamespace> nameToNamespace;
    private Set<PdfDictionary> namespaces;
    private PdfStructElem rootTagElement;
    private PdfVersion tagStructureTargetVersion;
    private WaitingTagsManager waitingTagsManager;

    static {
        HashSet hashSet = new HashSet();
        allowedRootTagRoles = hashSet;
        hashSet.add(StandardRoles.DOCUMENT);
        hashSet.add(StandardRoles.PART);
        hashSet.add(StandardRoles.ART);
        hashSet.add(StandardRoles.SECT);
        hashSet.add(StandardRoles.DIV);
    }

    public TagStructureContext(PdfDocument document2) {
        this(document2, document2.getPdfVersion());
    }

    public TagStructureContext(PdfDocument document2, PdfVersion tagStructureTargetVersion2) {
        this.document = document2;
        if (document2.isTagged()) {
            this.waitingTagsManager = new WaitingTagsManager();
            this.namespaces = new LinkedHashSet();
            this.nameToNamespace = new HashMap();
            this.tagStructureTargetVersion = tagStructureTargetVersion2;
            this.forbidUnknownRoles = true;
            if (targetTagStructureVersionIs2()) {
                initRegisteredNamespaces();
                setNamespaceForNewTagsBasedOnExistingRoot();
                return;
            }
            return;
        }
        throw new PdfException(PdfException.MustBeATaggedDocument);
    }

    public TagStructureContext setForbidUnknownRoles(boolean forbidUnknownRoles2) {
        this.forbidUnknownRoles = forbidUnknownRoles2;
        return this;
    }

    public PdfVersion getTagStructureTargetVersion() {
        return this.tagStructureTargetVersion;
    }

    public TagTreePointer getAutoTaggingPointer() {
        if (this.autoTaggingPointer == null) {
            this.autoTaggingPointer = new TagTreePointer(this.document);
        }
        return this.autoTaggingPointer;
    }

    public WaitingTagsManager getWaitingTagsManager() {
        return this.waitingTagsManager;
    }

    public PdfNamespace getDocumentDefaultNamespace() {
        return this.documentDefaultNamespace;
    }

    public TagStructureContext setDocumentDefaultNamespace(PdfNamespace namespace) {
        this.documentDefaultNamespace = namespace;
        return this;
    }

    public PdfNamespace fetchNamespace(String namespaceName) {
        PdfNamespace ns = this.nameToNamespace.get(namespaceName);
        if (ns != null) {
            return ns;
        }
        PdfNamespace ns2 = new PdfNamespace(namespaceName);
        this.nameToNamespace.put(namespaceName, ns2);
        return ns2;
    }

    public IRoleMappingResolver getRoleMappingResolver(String role) {
        return getRoleMappingResolver(role, (PdfNamespace) null);
    }

    public IRoleMappingResolver getRoleMappingResolver(String role, PdfNamespace namespace) {
        if (targetTagStructureVersionIs2()) {
            return new RoleMappingResolverPdf2(role, namespace, getDocument());
        }
        return new RoleMappingResolver(role, getDocument());
    }

    public boolean checkIfRoleShallBeMappedToStandardRole(String role, PdfNamespace namespace) {
        return resolveMappingToStandardOrDomainSpecificRole(role, namespace) != null;
    }

    public IRoleMappingResolver resolveMappingToStandardOrDomainSpecificRole(String role, PdfNamespace namespace) {
        IRoleMappingResolver mappingResolver = getRoleMappingResolver(role, namespace);
        mappingResolver.resolveNextMapping();
        int i = 0;
        while (mappingResolver.currentRoleShallBeMappedToStandard()) {
            i++;
            if (i > 100) {
                LoggerFactory.getLogger((Class<?>) TagStructureContext.class).error(composeTooMuchTransitiveMappingsException(role, namespace));
                return null;
            } else if (!mappingResolver.resolveNextMapping()) {
                return null;
            }
        }
        return mappingResolver;
    }

    public TagTreePointer removeAnnotationTag(PdfAnnotation annotation) {
        PdfObjRef objRef;
        PdfStructElem structElem = null;
        PdfDictionary annotDic = (PdfDictionary) annotation.getPdfObject();
        PdfNumber structParentIndex = (PdfNumber) annotDic.get(PdfName.StructParent);
        if (!(structParentIndex == null || (objRef = this.document.getStructTreeRoot().findObjRefByStructParentIndex(annotDic.getAsDictionary(PdfName.f1367P), structParentIndex.intValue())) == null)) {
            PdfStructElem parent = (PdfStructElem) objRef.getParent();
            parent.removeKid((IStructureNode) objRef);
            structElem = parent;
        }
        annotDic.remove(PdfName.StructParent);
        annotDic.setModified();
        if (structElem != null) {
            return new TagTreePointer(this.document).setCurrentStructElem(structElem);
        }
        return null;
    }

    public TagTreePointer removeContentItem(PdfPage page, int mcid) {
        PdfMcr mcr = this.document.getStructTreeRoot().findMcrByMcid((PdfDictionary) page.getPdfObject(), mcid);
        if (mcr == null) {
            return null;
        }
        PdfStructElem parent = (PdfStructElem) mcr.getParent();
        parent.removeKid((IStructureNode) mcr);
        return new TagTreePointer(this.document).setCurrentStructElem(parent);
    }

    public TagStructureContext removePageTags(PdfPage page) {
        Collection<PdfMcr> pageMcrs = this.document.getStructTreeRoot().getPageMarkedContentReferences(page);
        if (pageMcrs != null) {
            for (PdfMcr mcr : new ArrayList<>(pageMcrs)) {
                removePageTagFromParent(mcr, mcr.getParent());
            }
        }
        return this;
    }

    public TagStructureContext flushPageTags(PdfPage page) {
        Collection<PdfMcr> pageMcrs = this.document.getStructTreeRoot().getPageMarkedContentReferences(page);
        if (pageMcrs != null) {
            for (PdfMcr mcr : pageMcrs) {
                flushParentIfBelongsToPage((PdfStructElem) mcr.getParent(), page);
            }
        }
        return this;
    }

    public void normalizeDocumentRootTag() {
        boolean forbid = this.forbidUnknownRoles;
        this.forbidUnknownRoles = false;
        List<IStructureNode> rootKids = this.document.getStructTreeRoot().getKids();
        IRoleMappingResolver mapping = null;
        if (rootKids.size() > 0) {
            PdfStructElem firstKid = (PdfStructElem) rootKids.get(0);
            mapping = resolveMappingToStandardOrDomainSpecificRole(firstKid.getRole().getValue(), firstKid.getNamespace());
        }
        if (rootKids.size() != 1 || mapping == null || !mapping.currentRoleIsStandard() || !isRoleAllowedToBeRoot(mapping.getRole())) {
            ((PdfDictionary) this.document.getStructTreeRoot().getPdfObject()).remove(PdfName.f1344K);
            this.rootTagElement = new RootTagNormalizer(this, this.rootTagElement, this.document).makeSingleStandardRootTag(rootKids);
        } else {
            this.rootTagElement = (PdfStructElem) rootKids.get(0);
        }
        this.forbidUnknownRoles = forbid;
    }

    public void prepareToDocumentClosing() {
        this.waitingTagsManager.removeAllWaitingStates();
        actualizeNamespacesInStructTreeRoot();
    }

    public PdfStructElem getPointerStructElem(TagTreePointer pointer) {
        return pointer.getCurrentStructElem();
    }

    public TagTreePointer createPointerForStructElem(PdfStructElem structElem) {
        return new TagTreePointer(structElem, this.document);
    }

    /* access modifiers changed from: package-private */
    public PdfStructElem getRootTag() {
        if (this.rootTagElement == null) {
            normalizeDocumentRootTag();
        }
        return this.rootTagElement;
    }

    /* access modifiers changed from: package-private */
    public PdfDocument getDocument() {
        return this.document;
    }

    /* access modifiers changed from: package-private */
    public void ensureNamespaceRegistered(PdfNamespace namespace) {
        if (namespace != null) {
            PdfDictionary namespaceObj = (PdfDictionary) namespace.getPdfObject();
            if (!this.namespaces.contains(namespaceObj)) {
                this.namespaces.add(namespaceObj);
            }
            this.nameToNamespace.put(namespace.getNamespaceName(), namespace);
        }
    }

    /* access modifiers changed from: package-private */
    public void throwExceptionIfRoleIsInvalid(AccessibilityProperties properties, PdfNamespace pointerCurrentNamespace) {
        PdfNamespace namespace = properties.getNamespace();
        if (namespace == null) {
            namespace = pointerCurrentNamespace;
        }
        throwExceptionIfRoleIsInvalid(properties.getRole(), namespace);
    }

    /* access modifiers changed from: package-private */
    public void throwExceptionIfRoleIsInvalid(String role, PdfNamespace namespace) {
        if (!checkIfRoleShallBeMappedToStandardRole(role, namespace)) {
            String exMessage = composeInvalidRoleException(role, namespace);
            if (!this.forbidUnknownRoles) {
                LoggerFactory.getLogger((Class<?>) TagStructureContext.class).warn(exMessage);
                return;
            }
            throw new PdfException(exMessage);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean targetTagStructureVersionIs2() {
        return PdfVersion.PDF_2_0.compareTo(this.tagStructureTargetVersion) <= 0;
    }

    /* access modifiers changed from: package-private */
    public void flushParentIfBelongsToPage(PdfStructElem parent, PdfPage currentPage) {
        if (!parent.isFlushed() && this.waitingTagsManager.getObjForStructDict((PdfDictionary) parent.getPdfObject()) == null && !(parent.getParent() instanceof PdfStructTreeRoot)) {
            boolean readyToBeFlushed = true;
            Iterator<IStructureNode> it = parent.getKids().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                IStructureNode kid = it.next();
                if (kid instanceof PdfMcr) {
                    PdfDictionary kidPage = ((PdfMcr) kid).getPageObject();
                    if (!kidPage.isFlushed() && (currentPage == null || !kidPage.equals(currentPage.getPdfObject()))) {
                        readyToBeFlushed = false;
                    }
                } else if (kid instanceof PdfStructElem) {
                    readyToBeFlushed = false;
                    break;
                }
            }
            if (readyToBeFlushed) {
                IStructureNode parentsParent = parent.getParent();
                parent.flush();
                if (parentsParent instanceof PdfStructElem) {
                    flushParentIfBelongsToPage((PdfStructElem) parentsParent, currentPage);
                }
            }
        }
    }

    private boolean isRoleAllowedToBeRoot(String role) {
        if (targetTagStructureVersionIs2()) {
            return StandardRoles.DOCUMENT.equals(role);
        }
        return allowedRootTagRoles.contains(role);
    }

    private void setNamespaceForNewTagsBasedOnExistingRoot() {
        String nsStr;
        List<IStructureNode> rootKids = this.document.getStructTreeRoot().getKids();
        if (rootKids.size() > 0) {
            PdfStructElem firstKid = (PdfStructElem) rootKids.get(0);
            IRoleMappingResolver resolvedMapping = resolveMappingToStandardOrDomainSpecificRole(firstKid.getRole().getValue(), firstKid.getNamespace());
            if (resolvedMapping == null || !resolvedMapping.currentRoleIsStandard()) {
                Logger logger = LoggerFactory.getLogger((Class<?>) TagStructureContext.class);
                if (firstKid.getNamespace() != null) {
                    nsStr = firstKid.getNamespace().getNamespaceName();
                } else {
                    nsStr = StandardNamespaces.getDefault();
                }
                logger.warn(MessageFormat.format(LogMessageConstant.EXISTING_TAG_STRUCTURE_ROOT_IS_NOT_STANDARD, new Object[]{firstKid.getRole().getValue(), nsStr}));
            }
            if (resolvedMapping == null || !StandardNamespaces.PDF_1_7.equals(resolvedMapping.getNamespace().getNamespaceName())) {
                this.documentDefaultNamespace = fetchNamespace(StandardNamespaces.PDF_2_0);
                return;
            }
            return;
        }
        this.documentDefaultNamespace = fetchNamespace(StandardNamespaces.PDF_2_0);
    }

    private String composeInvalidRoleException(String role, PdfNamespace namespace) {
        return composeExceptionBasedOnNamespacePresence(role, namespace, PdfException.RoleIsNotMappedToAnyStandardRole, PdfException.RoleInNamespaceIsNotMappedToAnyStandardRole);
    }

    private String composeTooMuchTransitiveMappingsException(String role, PdfNamespace namespace) {
        return composeExceptionBasedOnNamespacePresence(role, namespace, LogMessageConstant.CANNOT_RESOLVE_ROLE_TOO_MUCH_TRANSITIVE_MAPPINGS, LogMessageConstant.CANNOT_RESOLVE_ROLE_IN_NAMESPACE_TOO_MUCH_TRANSITIVE_MAPPINGS);
    }

    private void initRegisteredNamespaces() {
        for (PdfNamespace namespace : this.document.getStructTreeRoot().getNamespaces()) {
            this.namespaces.add(namespace.getPdfObject());
            this.nameToNamespace.put(namespace.getNamespaceName(), namespace);
        }
    }

    private void actualizeNamespacesInStructTreeRoot() {
        if (this.namespaces.size() > 0) {
            PdfStructTreeRoot structTreeRoot = getDocument().getStructTreeRoot();
            PdfArray rootNamespaces = structTreeRoot.getNamespacesObject();
            Set<PdfDictionary> newNamespaces = new LinkedHashSet<>(this.namespaces);
            for (int i = 0; i < rootNamespaces.size(); i++) {
                newNamespaces.remove(rootNamespaces.getAsDictionary(i));
            }
            for (PdfDictionary newNs : newNamespaces) {
                rootNamespaces.add(newNs);
            }
            if (!newNamespaces.isEmpty()) {
                structTreeRoot.setModified();
            }
        }
    }

    private void removePageTagFromParent(IStructureNode pageTag, IStructureNode parent) {
        if (parent instanceof PdfStructElem) {
            PdfStructElem structParent = (PdfStructElem) parent;
            if (!structParent.isFlushed()) {
                structParent.removeKid(pageTag);
                PdfDictionary parentStructDict = (PdfDictionary) structParent.getPdfObject();
                if (this.waitingTagsManager.getObjForStructDict(parentStructDict) == null && parent.getKids().size() == 0 && !(structParent.getParent() instanceof PdfStructTreeRoot)) {
                    removePageTagFromParent(structParent, parent.getParent());
                    PdfIndirectReference indRef = parentStructDict.getIndirectReference();
                    if (indRef != null) {
                        indRef.setFree();
                    }
                }
            } else if (pageTag instanceof PdfMcr) {
                throw new PdfException(PdfException.CannotRemoveTagBecauseItsParentIsFlushed);
            }
        }
    }

    private String composeExceptionBasedOnNamespacePresence(String role, PdfNamespace namespace, String withoutNsEx, String withNsEx) {
        if (namespace == null) {
            return MessageFormat.format(withoutNsEx, new Object[]{role});
        }
        String nsName = namespace.getNamespaceName();
        PdfIndirectReference ref = ((PdfDictionary) namespace.getPdfObject()).getIndirectReference();
        if (ref != null) {
            nsName = nsName + " (" + Integer.toString(ref.getObjNumber()) + " " + Integer.toString(ref.getGenNumber()) + " obj)";
        }
        return MessageFormat.format(withNsEx, new Object[]{role, nsName});
    }
}
