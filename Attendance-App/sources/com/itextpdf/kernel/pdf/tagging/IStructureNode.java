package com.itextpdf.kernel.pdf.tagging;

import com.itextpdf.kernel.pdf.PdfName;
import java.util.List;

public interface IStructureNode {
    List<IStructureNode> getKids();

    IStructureNode getParent();

    PdfName getRole();
}
