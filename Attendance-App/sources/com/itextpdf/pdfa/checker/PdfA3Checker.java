package com.itextpdf.pdfa.checker;

import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.pdfa.PdfAConformanceException;
import com.itextpdf.pdfa.PdfAConformanceLogMessageConstant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.LoggerFactory;

public class PdfA3Checker extends PdfA2Checker {
    protected static final Set<PdfName> allowedAFRelationships = new HashSet(Arrays.asList(new PdfName[]{PdfName.Source, PdfName.Data, PdfName.Alternative, PdfName.Supplement, PdfName.Unspecified}));
    private static final long serialVersionUID = 6280825718658124941L;

    public PdfA3Checker(PdfAConformanceLevel conformanceLevel) {
        super(conformanceLevel);
    }

    /* access modifiers changed from: protected */
    public void checkFileSpec(PdfDictionary fileSpec) {
        PdfName relationship = fileSpec.getAsName(PdfName.AFRelationship);
        if (relationship == null || !allowedAFRelationships.contains(relationship)) {
            throw new PdfAConformanceException(PdfAConformanceException.f1567x2d643492);
        } else if (!fileSpec.containsKey(PdfName.f1321EF)) {
        } else {
            if (!fileSpec.containsKey(PdfName.f1324F) || !fileSpec.containsKey(PdfName.f1405UF) || !fileSpec.containsKey(PdfName.Desc)) {
                throw new PdfAConformanceException(PdfAConformanceException.FILE_SPECIFICATION_DICTIONARY_SHALL_CONTAIN_F_KEY_AND_UF_KEY);
            }
            PdfStream embeddedFile = fileSpec.getAsDictionary(PdfName.f1321EF).getAsStream(PdfName.f1324F);
            if (embeddedFile == null) {
                throw new PdfAConformanceException(PdfAConformanceException.f1565x7f5ec81);
            } else if (!embeddedFile.containsKey(PdfName.Subtype)) {
                throw new PdfAConformanceException(PdfAConformanceException.f1573xd73efa40);
            } else if (embeddedFile.containsKey(PdfName.Params)) {
                PdfObject params = embeddedFile.get(PdfName.Params);
                if (!params.isDictionary()) {
                    throw new PdfAConformanceException(PdfAConformanceException.EMBEDDED_FILE_SHALL_CONTAIN_PARAMS_KEY_WITH_DICTIONARY_AS_VALUE);
                } else if (((PdfDictionary) params).getAsString(PdfName.ModDate) == null) {
                    throw new PdfAConformanceException(PdfAConformanceException.EMBEDDED_FILE_SHALL_CONTAIN_PARAMS_KEY_WITH_VALID_MODDATE_KEY);
                }
            } else {
                LoggerFactory.getLogger((Class<?>) PdfAChecker.class).warn(PdfAConformanceLogMessageConstant.EMBEDDED_FILE_SHOULD_CONTAIN_PARAMS_KEY);
            }
        }
    }
}
