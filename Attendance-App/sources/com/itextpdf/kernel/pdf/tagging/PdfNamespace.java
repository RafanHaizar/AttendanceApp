package com.itextpdf.kernel.pdf.tagging;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PdfNamespace extends PdfObjectWrapper<PdfDictionary> {
    private static final long serialVersionUID = -4228596885910641569L;

    public PdfNamespace(PdfDictionary dictionary) {
        super(dictionary);
        setForbidRelease();
    }

    public PdfNamespace(String namespaceName) {
        this(new PdfString(namespaceName));
    }

    public PdfNamespace(PdfString namespaceName) {
        this(new PdfDictionary());
        put(PdfName.Type, PdfName.Namespace);
        put(PdfName.f1360NS, namespaceName);
    }

    public PdfNamespace setNamespaceName(String namespaceName) {
        return setNamespaceName(new PdfString(namespaceName));
    }

    public PdfNamespace setNamespaceName(PdfString namespaceName) {
        return put(PdfName.f1360NS, namespaceName);
    }

    public String getNamespaceName() {
        PdfString ns = ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1360NS);
        if (ns != null) {
            return ns.toUnicodeString();
        }
        return null;
    }

    public PdfNamespace setSchema(PdfFileSpec fileSpec) {
        return put(PdfName.Schema, fileSpec.getPdfObject());
    }

    public PdfFileSpec getSchema() {
        return PdfFileSpec.wrapFileSpecObject(((PdfDictionary) getPdfObject()).get(PdfName.Schema));
    }

    public PdfNamespace setNamespaceRoleMap(PdfDictionary roleMapNs) {
        return put(PdfName.RoleMapNS, roleMapNs);
    }

    public PdfDictionary getNamespaceRoleMap() {
        return getNamespaceRoleMap(false);
    }

    public PdfNamespace addNamespaceRoleMapping(String thisNsRole, String defaultNsRole) {
        logOverwritingOfMappingIfNeeded(thisNsRole, getNamespaceRoleMap(true).put(PdfStructTreeRoot.convertRoleToPdfName(thisNsRole), PdfStructTreeRoot.convertRoleToPdfName(defaultNsRole)));
        setModified();
        return this;
    }

    public PdfNamespace addNamespaceRoleMapping(String thisNsRole, String targetNsRole, PdfNamespace targetNs) {
        PdfArray targetMapping = new PdfArray();
        targetMapping.add(PdfStructTreeRoot.convertRoleToPdfName(targetNsRole));
        targetMapping.add(targetNs.getPdfObject());
        logOverwritingOfMappingIfNeeded(thisNsRole, getNamespaceRoleMap(true).put(PdfStructTreeRoot.convertRoleToPdfName(thisNsRole), targetMapping));
        setModified();
        return this;
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }

    private PdfNamespace put(PdfName key, PdfObject value) {
        ((PdfDictionary) getPdfObject()).put(key, value);
        setModified();
        return this;
    }

    private PdfDictionary getNamespaceRoleMap(boolean createIfNotExist) {
        PdfDictionary roleMapNs = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.RoleMapNS);
        if (!createIfNotExist || roleMapNs != null) {
            return roleMapNs;
        }
        PdfDictionary roleMapNs2 = new PdfDictionary();
        put(PdfName.RoleMapNS, roleMapNs2);
        return roleMapNs2;
    }

    private void logOverwritingOfMappingIfNeeded(String thisNsRole, PdfObject prevVal) {
        if (prevVal != null) {
            Logger logger = LoggerFactory.getLogger((Class<?>) PdfNamespace.class);
            String nsNameStr = getNamespaceName();
            if (nsNameStr == null) {
                nsNameStr = "this";
            }
            logger.warn(MessageFormatUtil.format(LogMessageConstant.MAPPING_IN_NAMESPACE_OVERWRITTEN, thisNsRole, nsNameStr));
        }
    }
}
