package com.itextpdf.kernel.pdf.filters;

import com.itextpdf.kernel.KernelLogMessageConstant;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DctDecodeFilter implements IFilterHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger((Class<?>) DctDecodeFilter.class);

    public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary) {
        LOGGER.info(KernelLogMessageConstant.DCTDECODE_FILTER_DECODING);
        return b;
    }
}
