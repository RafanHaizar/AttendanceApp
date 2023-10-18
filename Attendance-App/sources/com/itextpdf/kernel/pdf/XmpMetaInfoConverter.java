package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.xmp.PdfConst;
import com.itextpdf.kernel.xmp.XMPConst;
import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.kernel.xmp.XMPMeta;
import com.itextpdf.kernel.xmp.XMPMetaFactory;
import com.itextpdf.kernel.xmp.options.PropertyOptions;
import com.itextpdf.kernel.xmp.properties.XMPProperty;
import com.itextpdf.p026io.font.PdfEncodings;

class XmpMetaInfoConverter {
    private XmpMetaInfoConverter() {
    }

    static void appendMetadataToInfo(byte[] xmpMetadata, PdfDocumentInfo info) {
        if (xmpMetadata != null) {
            try {
                XMPMeta meta = XMPMetaFactory.parseFromBuffer(xmpMetadata);
                XMPProperty title = meta.getLocalizedText(XMPConst.NS_DC, "title", XMPConst.X_DEFAULT, XMPConst.X_DEFAULT);
                if (title != null) {
                    info.setTitle(title.getValue());
                }
                String author = fetchArrayIntoString(meta, XMPConst.NS_DC, PdfConst.Creator);
                if (author != null) {
                    info.setAuthor(author);
                }
                XMPProperty keywords = meta.getProperty(XMPConst.NS_PDF, PdfConst.Keywords);
                if (keywords != null) {
                    info.setKeywords(keywords.getValue());
                } else {
                    String keywordsStr = fetchArrayIntoString(meta, XMPConst.NS_DC, "subject");
                    if (keywordsStr != null) {
                        info.setKeywords(keywordsStr);
                    }
                }
                XMPProperty subject = meta.getLocalizedText(XMPConst.NS_DC, PdfConst.Description, XMPConst.X_DEFAULT, XMPConst.X_DEFAULT);
                if (subject != null) {
                    info.setSubject(subject.getValue());
                }
                XMPProperty creator = meta.getProperty(XMPConst.NS_XMP, PdfConst.CreatorTool);
                if (creator != null) {
                    info.setCreator(creator.getValue());
                }
                XMPProperty producer = meta.getProperty(XMPConst.NS_PDF, PdfConst.Producer);
                if (producer != null) {
                    info.put(PdfName.Producer, new PdfString(producer.getValue(), PdfEncodings.UNICODE_BIG));
                }
                XMPProperty trapped = meta.getProperty(XMPConst.NS_PDF, PdfConst.Trapped);
                if (trapped != null) {
                    info.setTrapped(new PdfName(trapped.getValue()));
                }
            } catch (XMPException e) {
            }
        }
    }

    static void appendDocumentInfoToMetadata(PdfDocumentInfo info, XMPMeta xmpMeta) throws XMPException {
        String value;
        XMPMeta xMPMeta = xmpMeta;
        PdfDictionary docInfo = info.getPdfObject();
        if (docInfo != null) {
            for (PdfName key : docInfo.keySet()) {
                PdfObject obj = docInfo.get(key);
                if (obj != null) {
                    if (obj.isString()) {
                        value = ((PdfString) obj).toUnicodeString();
                    } else if (obj.isName()) {
                        value = ((PdfName) obj).getValue();
                    }
                    if (PdfName.Title.equals(key)) {
                        xmpMeta.setLocalizedText(XMPConst.NS_DC, "title", XMPConst.X_DEFAULT, XMPConst.X_DEFAULT, value);
                    } else {
                        int i = 0;
                        if (PdfName.Author.equals(key)) {
                            String[] split = value.split(",|;");
                            int length = split.length;
                            while (i < length) {
                                String v = split[i];
                                if (v.trim().length() > 0) {
                                    appendArrayItemIfDoesNotExist(xMPMeta, XMPConst.NS_DC, PdfConst.Creator, v.trim(), 1024);
                                }
                                i++;
                            }
                        } else if (PdfName.Subject.equals(key)) {
                            xmpMeta.setLocalizedText(XMPConst.NS_DC, PdfConst.Description, XMPConst.X_DEFAULT, XMPConst.X_DEFAULT, value);
                        } else if (PdfName.Keywords.equals(key)) {
                            String[] split2 = value.split(",|;");
                            int length2 = split2.length;
                            while (i < length2) {
                                String v2 = split2[i];
                                if (v2.trim().length() > 0) {
                                    appendArrayItemIfDoesNotExist(xMPMeta, XMPConst.NS_DC, "subject", v2.trim(), 512);
                                }
                                i++;
                            }
                            xMPMeta.setProperty(XMPConst.NS_PDF, PdfConst.Keywords, value);
                        } else if (PdfName.Creator.equals(key)) {
                            xMPMeta.setProperty(XMPConst.NS_XMP, PdfConst.CreatorTool, value);
                        } else if (PdfName.Producer.equals(key)) {
                            xMPMeta.setProperty(XMPConst.NS_PDF, PdfConst.Producer, value);
                        } else if (PdfName.CreationDate.equals(key)) {
                            xMPMeta.setProperty(XMPConst.NS_XMP, PdfConst.CreateDate, PdfDate.getW3CDate(value));
                        } else if (PdfName.ModDate.equals(key)) {
                            xMPMeta.setProperty(XMPConst.NS_XMP, PdfConst.ModifyDate, PdfDate.getW3CDate(value));
                        } else if (PdfName.Trapped.equals(key)) {
                            xMPMeta.setProperty(XMPConst.NS_PDF, PdfConst.Trapped, value);
                        }
                    }
                }
            }
        }
    }

    private static void appendArrayItemIfDoesNotExist(XMPMeta meta, String ns, String arrayName, String value, int arrayOption) throws XMPException {
        int currentCnt = meta.countArrayItems(ns, arrayName);
        int i = 0;
        while (i < currentCnt) {
            if (!value.equals(meta.getArrayItem(ns, arrayName, i + 1).getValue())) {
                i++;
            } else {
                return;
            }
        }
        meta.appendArrayItem(ns, arrayName, new PropertyOptions(arrayOption), value, (PropertyOptions) null);
    }

    private static String fetchArrayIntoString(XMPMeta meta, String ns, String arrayName) throws XMPException {
        int keywordsCnt = meta.countArrayItems(ns, arrayName);
        StringBuilder sb = null;
        for (int i = 0; i < keywordsCnt; i++) {
            XMPProperty curKeyword = meta.getArrayItem(ns, arrayName, i + 1);
            if (sb == null) {
                sb = new StringBuilder();
            } else if (sb.length() > 0) {
                sb.append("; ");
            }
            sb.append(curKeyword.getValue());
        }
        if (sb != null) {
            return sb.toString();
        }
        return null;
    }
}
