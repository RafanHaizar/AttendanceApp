package com.itextpdf.kernel.pdf.tagutils;

import com.itextpdf.kernel.pdf.tagging.PdfNamespace;
import java.io.Serializable;

public interface IRoleMappingResolver extends Serializable {
    boolean currentRoleIsStandard();

    boolean currentRoleShallBeMappedToStandard();

    PdfNamespace getNamespace();

    String getRole();

    boolean resolveNextMapping();
}
