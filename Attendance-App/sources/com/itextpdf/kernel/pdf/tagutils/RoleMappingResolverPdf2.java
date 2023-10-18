package com.itextpdf.kernel.pdf.tagutils;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.tagging.PdfNamespace;
import com.itextpdf.kernel.pdf.tagging.PdfStructTreeRoot;
import com.itextpdf.kernel.pdf.tagging.StandardNamespaces;

class RoleMappingResolverPdf2 implements IRoleMappingResolver {
    private static final long serialVersionUID = -564649110244365255L;
    private PdfNamespace currNamespace;
    private PdfName currRole;
    private PdfNamespace defaultNamespace;

    RoleMappingResolverPdf2(String role, PdfNamespace namespace, PdfDocument document) {
        this.currRole = PdfStructTreeRoot.convertRoleToPdfName(role);
        this.currNamespace = namespace;
        String defaultNsName = StandardNamespaces.getDefault();
        PdfNamespace namespaceRoleMap = new PdfNamespace(defaultNsName).setNamespaceRoleMap(document.getStructTreeRoot().getRoleMap());
        this.defaultNamespace = namespaceRoleMap;
        if (this.currNamespace == null) {
            this.currNamespace = namespaceRoleMap;
        }
    }

    public String getRole() {
        return this.currRole.getValue();
    }

    public PdfNamespace getNamespace() {
        return this.currNamespace;
    }

    public boolean currentRoleIsStandard() {
        String roleStrVal = this.currRole.getValue();
        boolean stdRole17 = StandardNamespaces.PDF_1_7.equals(this.currNamespace.getNamespaceName()) && StandardNamespaces.roleBelongsToStandardNamespace(roleStrVal, StandardNamespaces.PDF_1_7);
        boolean stdRole20 = StandardNamespaces.PDF_2_0.equals(this.currNamespace.getNamespaceName()) && StandardNamespaces.roleBelongsToStandardNamespace(roleStrVal, StandardNamespaces.PDF_2_0);
        if (stdRole17 || stdRole20) {
            return true;
        }
        return false;
    }

    public boolean currentRoleShallBeMappedToStandard() {
        return !currentRoleIsStandard() && !StandardNamespaces.isKnownDomainSpecificNamespace(this.currNamespace);
    }

    public boolean resolveNextMapping() {
        PdfObject mapping = null;
        PdfDictionary currNsRoleMap = this.currNamespace.getNamespaceRoleMap();
        if (currNsRoleMap != null) {
            mapping = currNsRoleMap.get(this.currRole);
        }
        boolean z = false;
        if (mapping == null) {
            return false;
        }
        if (mapping.isName()) {
            this.currRole = (PdfName) mapping;
            this.currNamespace = this.defaultNamespace;
            return true;
        } else if (!mapping.isArray()) {
            return false;
        } else {
            PdfName mappedRole = null;
            PdfDictionary mappedNsDict = null;
            PdfArray mappingArr = (PdfArray) mapping;
            if (mappingArr.size() > 1) {
                mappedRole = mappingArr.getAsName(0);
                mappedNsDict = mappingArr.getAsDictionary(1);
            }
            if (!(mappedRole == null || mappedNsDict == null)) {
                z = true;
            }
            boolean mappingWasResolved = z;
            if (!mappingWasResolved) {
                return mappingWasResolved;
            }
            this.currRole = mappedRole;
            this.currNamespace = new PdfNamespace(mappedNsDict);
            return mappingWasResolved;
        }
    }
}
