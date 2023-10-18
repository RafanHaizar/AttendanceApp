package com.itextpdf.kernel.pdf.tagutils;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.tagging.PdfNamespace;
import com.itextpdf.kernel.pdf.tagging.PdfStructTreeRoot;
import com.itextpdf.kernel.pdf.tagging.StandardNamespaces;

class RoleMappingResolver implements IRoleMappingResolver {
    private static final long serialVersionUID = -8911597456631422956L;
    private PdfName currRole;
    private PdfDictionary roleMap;

    RoleMappingResolver(String role, PdfDocument document) {
        this.currRole = PdfStructTreeRoot.convertRoleToPdfName(role);
        this.roleMap = document.getStructTreeRoot().getRoleMap();
    }

    public String getRole() {
        return this.currRole.getValue();
    }

    public PdfNamespace getNamespace() {
        return null;
    }

    public boolean currentRoleIsStandard() {
        return StandardNamespaces.roleBelongsToStandardNamespace(this.currRole.getValue(), StandardNamespaces.PDF_1_7);
    }

    public boolean currentRoleShallBeMappedToStandard() {
        return !currentRoleIsStandard();
    }

    public boolean resolveNextMapping() {
        PdfName mappedRole = this.roleMap.getAsName(this.currRole);
        if (mappedRole == null) {
            return false;
        }
        this.currRole = mappedRole;
        return true;
    }
}
