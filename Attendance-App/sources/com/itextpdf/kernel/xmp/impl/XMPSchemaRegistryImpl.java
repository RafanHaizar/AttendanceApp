package com.itextpdf.kernel.xmp.impl;

import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.xmp.PdfConst;
import com.itextpdf.kernel.xmp.XMPConst;
import com.itextpdf.kernel.xmp.XMPError;
import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.kernel.xmp.XMPSchemaRegistry;
import com.itextpdf.kernel.xmp.options.AliasOptions;
import com.itextpdf.kernel.xmp.properties.XMPAliasInfo;
import com.itextpdf.svg.SvgConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public final class XMPSchemaRegistryImpl implements XMPConst, XMPSchemaRegistry {
    private Map aliasMap = new HashMap();
    private Map namespaceToPrefixMap = new HashMap();

    /* renamed from: p */
    private Pattern f1521p = Pattern.compile("[/*?\\[\\]]");
    private Map prefixToNamespaceMap = new HashMap();

    public XMPSchemaRegistryImpl() {
        try {
            registerStandardNamespaces();
            registerStandardAliases();
        } catch (XMPException e) {
            throw new RuntimeException("The XMPSchemaRegistry cannot be initialized!");
        }
    }

    public synchronized String registerNamespace(String namespaceURI, String suggestedPrefix) throws XMPException {
        ParameterAsserts.assertSchemaNS(namespaceURI);
        ParameterAsserts.assertPrefix(suggestedPrefix);
        if (suggestedPrefix.charAt(suggestedPrefix.length() - 1) != ':') {
            suggestedPrefix = suggestedPrefix + ':';
        }
        if (Utils.isXMLNameNS(suggestedPrefix.substring(0, suggestedPrefix.length() - 1))) {
            String registeredPrefix = (String) this.namespaceToPrefixMap.get(namespaceURI);
            String registeredNS = (String) this.prefixToNamespaceMap.get(suggestedPrefix);
            if (registeredPrefix != null) {
                return registeredPrefix;
            }
            if (registeredNS != null) {
                String generatedPrefix = suggestedPrefix;
                int i = 1;
                while (this.prefixToNamespaceMap.containsKey(generatedPrefix)) {
                    generatedPrefix = suggestedPrefix.substring(0, suggestedPrefix.length() - 1) + "_" + i + "_:";
                    i++;
                }
                suggestedPrefix = generatedPrefix;
            }
            this.prefixToNamespaceMap.put(suggestedPrefix, namespaceURI);
            this.namespaceToPrefixMap.put(namespaceURI, suggestedPrefix);
            return suggestedPrefix;
        }
        throw new XMPException("The prefix is a bad XML name", XMPError.BADXML);
    }

    public synchronized void deleteNamespace(String namespaceURI) {
        String prefixToDelete = getNamespacePrefix(namespaceURI);
        if (prefixToDelete != null) {
            this.namespaceToPrefixMap.remove(namespaceURI);
            this.prefixToNamespaceMap.remove(prefixToDelete);
        }
    }

    public synchronized String getNamespacePrefix(String namespaceURI) {
        return (String) this.namespaceToPrefixMap.get(namespaceURI);
    }

    public synchronized String getNamespaceURI(String namespacePrefix) {
        if (namespacePrefix != null) {
            if (!namespacePrefix.endsWith(":")) {
                namespacePrefix = namespacePrefix + ":";
            }
        }
        return (String) this.prefixToNamespaceMap.get(namespacePrefix);
    }

    public synchronized Map getNamespaces() {
        return Collections.unmodifiableMap(new TreeMap(this.namespaceToPrefixMap));
    }

    public synchronized Map getPrefixes() {
        return Collections.unmodifiableMap(new TreeMap(this.prefixToNamespaceMap));
    }

    private void registerStandardNamespaces() throws XMPException {
        registerNamespace(XMPConst.NS_XML, "xml");
        registerNamespace(XMPConst.NS_RDF, "rdf");
        registerNamespace(XMPConst.NS_DC, "dc");
        registerNamespace(XMPConst.NS_IPTCCORE, "Iptc4xmpCore");
        registerNamespace(XMPConst.NS_IPTCEXT, "Iptc4xmpExt");
        registerNamespace(XMPConst.NS_DICOM, "DICOM");
        registerNamespace(XMPConst.NS_PLUS, "plus");
        registerNamespace(XMPConst.NS_X, SvgConstants.Attributes.f1641X);
        registerNamespace(XMPConst.NS_IX, "iX");
        registerNamespace(XMPConst.NS_XMP, "xmp");
        registerNamespace(XMPConst.NS_XMP_RIGHTS, "xmpRights");
        registerNamespace(XMPConst.NS_XMP_MM, "xmpMM");
        registerNamespace(XMPConst.NS_XMP_BJ, "xmpBJ");
        registerNamespace(XMPConst.NS_XMP_NOTE, "xmpNote");
        registerNamespace(XMPConst.NS_PDF, "pdf");
        registerNamespace(XMPConst.NS_PDFX, "pdfx");
        registerNamespace(XMPConst.NS_PDFX_ID, "pdfxid");
        registerNamespace(XMPConst.NS_PDFA_SCHEMA, "pdfaSchema");
        registerNamespace(XMPConst.NS_PDFA_PROPERTY, "pdfaProperty");
        registerNamespace(XMPConst.NS_PDFA_TYPE, "pdfaType");
        registerNamespace(XMPConst.NS_PDFA_FIELD, "pdfaField");
        registerNamespace(XMPConst.NS_PDFA_ID, "pdfaid");
        registerNamespace(XMPConst.NS_PDFUA_ID, "pdfuaid");
        registerNamespace(XMPConst.NS_PDFA_EXTENSION, "pdfaExtension");
        registerNamespace(XMPConst.NS_PHOTOSHOP, "photoshop");
        registerNamespace(XMPConst.NS_PSALBUM, "album");
        registerNamespace(XMPConst.NS_EXIF, "exif");
        registerNamespace(XMPConst.NS_EXIFX, "exifEX");
        registerNamespace(XMPConst.NS_EXIF_AUX, "aux");
        registerNamespace(XMPConst.NS_TIFF, "tiff");
        registerNamespace(XMPConst.NS_PNG, "png");
        registerNamespace(XMPConst.NS_JPEG, "jpeg");
        registerNamespace(XMPConst.NS_JP2K, "jp2k");
        registerNamespace(XMPConst.NS_CAMERARAW, "crs");
        registerNamespace(XMPConst.NS_ADOBESTOCKPHOTO, "bmsp");
        registerNamespace(XMPConst.NS_CREATOR_ATOM, "creatorAtom");
        registerNamespace(XMPConst.NS_ASF, "asf");
        registerNamespace(XMPConst.NS_WAV, "wav");
        registerNamespace(XMPConst.NS_BWF, "bext");
        registerNamespace(XMPConst.NS_RIFFINFO, "riffinfo");
        registerNamespace(XMPConst.NS_SCRIPT, "xmpScript");
        registerNamespace(XMPConst.NS_TXMP, "txmp");
        registerNamespace(XMPConst.NS_SWF, "swf");
        registerNamespace(XMPConst.NS_DM, "xmpDM");
        registerNamespace(XMPConst.NS_TRANSIENT, "xmpx");
        registerNamespace(XMPConst.TYPE_TEXT, "xmpT");
        registerNamespace(XMPConst.TYPE_PAGEDFILE, "xmpTPg");
        registerNamespace(XMPConst.TYPE_GRAPHICS, "xmpG");
        registerNamespace(XMPConst.TYPE_IMAGE, "xmpGImg");
        registerNamespace(XMPConst.TYPE_FONT, "stFnt");
        registerNamespace(XMPConst.TYPE_DIMENSIONS, "stDim");
        registerNamespace(XMPConst.TYPE_RESOURCEEVENT, "stEvt");
        registerNamespace(XMPConst.TYPE_RESOURCEREF, "stRef");
        registerNamespace(XMPConst.TYPE_ST_VERSION, "stVer");
        registerNamespace(XMPConst.TYPE_ST_JOB, "stJob");
        registerNamespace(XMPConst.TYPE_MANIFESTITEM, "stMfs");
        registerNamespace(XMPConst.TYPE_IDENTIFIERQUAL, "xmpidq");
    }

    public synchronized XMPAliasInfo resolveAlias(String aliasNS, String aliasProp) {
        String aliasPrefix = getNamespacePrefix(aliasNS);
        if (aliasPrefix == null) {
            return null;
        }
        return (XMPAliasInfo) this.aliasMap.get(aliasPrefix + aliasProp);
    }

    public synchronized XMPAliasInfo findAlias(String qname) {
        return (XMPAliasInfo) this.aliasMap.get(qname);
    }

    public synchronized XMPAliasInfo[] findAliases(String aliasNS) {
        List<XMPAliasInfo> result;
        String prefix = getNamespacePrefix(aliasNS);
        result = new ArrayList<>();
        if (prefix != null) {
            for (String qname : this.aliasMap.keySet()) {
                if (qname.startsWith(prefix)) {
                    result.add(findAlias(qname));
                }
            }
        }
        return (XMPAliasInfo[]) result.toArray(new XMPAliasInfo[result.size()]);
    }

    /* access modifiers changed from: package-private */
    public synchronized void registerAlias(String aliasNS, String aliasProp, String actualNS, String actualProp, AliasOptions aliasForm) throws XMPException {
        ParameterAsserts.assertSchemaNS(aliasNS);
        ParameterAsserts.assertPropName(aliasProp);
        ParameterAsserts.assertSchemaNS(actualNS);
        ParameterAsserts.assertPropName(actualProp);
        final AliasOptions aliasOpts = aliasForm != null ? new AliasOptions(XMPNodeUtils.verifySetOptions(aliasForm.toPropertyOptions(), (Object) null).getOptions()) : new AliasOptions();
        if (this.f1521p.matcher(aliasProp).find() || this.f1521p.matcher(actualProp).find()) {
            throw new XMPException("Alias and actual property names must be simple", 102);
        }
        String aliasPrefix = getNamespacePrefix(aliasNS);
        String actualPrefix = getNamespacePrefix(actualNS);
        if (aliasPrefix == null) {
            throw new XMPException("Alias namespace is not registered", 101);
        } else if (actualPrefix != null) {
            String key = aliasPrefix + aliasProp;
            if (this.aliasMap.containsKey(key)) {
                throw new XMPException("Alias is already existing", 4);
            } else if (!this.aliasMap.containsKey(actualPrefix + actualProp)) {
                final String str = actualNS;
                final String str2 = actualPrefix;
                final String str3 = actualProp;
                this.aliasMap.put(key, new XMPAliasInfo() {
                    public String getNamespace() {
                        return str;
                    }

                    public String getPrefix() {
                        return str2;
                    }

                    public String getPropName() {
                        return str3;
                    }

                    public AliasOptions getAliasForm() {
                        return aliasOpts;
                    }

                    public String toString() {
                        return str2 + str3 + " NS(" + str + "), FORM (" + getAliasForm() + ")";
                    }
                });
            } else {
                throw new XMPException("Actual property is already an alias, use the base property", 4);
            }
        } else {
            throw new XMPException("Actual namespace is not registered", 101);
        }
    }

    public synchronized Map getAliases() {
        return Collections.unmodifiableMap(new TreeMap(this.aliasMap));
    }

    private void registerStandardAliases() throws XMPException {
        AliasOptions aliasToArrayOrdered = new AliasOptions().setArrayOrdered(true);
        AliasOptions aliasToArrayAltText = new AliasOptions().setArrayAltText(true);
        registerAlias(XMPConst.NS_XMP, "Author", XMPConst.NS_DC, PdfConst.Creator, aliasToArrayOrdered);
        registerAlias(XMPConst.NS_XMP, "Authors", XMPConst.NS_DC, PdfConst.Creator, (AliasOptions) null);
        registerAlias(XMPConst.NS_XMP, "Description", XMPConst.NS_DC, PdfConst.Description, (AliasOptions) null);
        registerAlias(XMPConst.NS_XMP, "Format", XMPConst.NS_DC, PdfConst.Format, (AliasOptions) null);
        registerAlias(XMPConst.NS_XMP, PdfConst.Keywords, XMPConst.NS_DC, "subject", (AliasOptions) null);
        registerAlias(XMPConst.NS_XMP, "Locale", XMPConst.NS_DC, PdfConst.Language, (AliasOptions) null);
        registerAlias(XMPConst.NS_XMP, StandardRoles.TITLE, XMPConst.NS_DC, "title", (AliasOptions) null);
        registerAlias(XMPConst.NS_XMP_RIGHTS, "Copyright", XMPConst.NS_DC, PdfConst.Rights, (AliasOptions) null);
        registerAlias(XMPConst.NS_PDF, "Author", XMPConst.NS_DC, PdfConst.Creator, aliasToArrayOrdered);
        registerAlias(XMPConst.NS_PDF, PdfConst.BaseURL, XMPConst.NS_XMP, PdfConst.BaseURL, (AliasOptions) null);
        registerAlias(XMPConst.NS_PDF, "CreationDate", XMPConst.NS_XMP, PdfConst.CreateDate, (AliasOptions) null);
        registerAlias(XMPConst.NS_PDF, "Creator", XMPConst.NS_XMP, PdfConst.CreatorTool, (AliasOptions) null);
        registerAlias(XMPConst.NS_PDF, "ModDate", XMPConst.NS_XMP, PdfConst.ModifyDate, (AliasOptions) null);
        AliasOptions aliasOptions = aliasToArrayAltText;
        registerAlias(XMPConst.NS_PDF, "Subject", XMPConst.NS_DC, PdfConst.Description, aliasOptions);
        registerAlias(XMPConst.NS_PDF, StandardRoles.TITLE, XMPConst.NS_DC, "title", aliasOptions);
        registerAlias(XMPConst.NS_PHOTOSHOP, "Author", XMPConst.NS_DC, PdfConst.Creator, aliasToArrayOrdered);
        registerAlias(XMPConst.NS_PHOTOSHOP, StandardRoles.CAPTION, XMPConst.NS_DC, PdfConst.Description, aliasOptions);
        registerAlias(XMPConst.NS_PHOTOSHOP, "Copyright", XMPConst.NS_DC, PdfConst.Rights, aliasOptions);
        registerAlias(XMPConst.NS_PHOTOSHOP, PdfConst.Keywords, XMPConst.NS_DC, "subject", (AliasOptions) null);
        registerAlias(XMPConst.NS_PHOTOSHOP, "Marked", XMPConst.NS_XMP_RIGHTS, "Marked", (AliasOptions) null);
        registerAlias(XMPConst.NS_PHOTOSHOP, StandardRoles.TITLE, XMPConst.NS_DC, "title", aliasToArrayAltText);
        registerAlias(XMPConst.NS_PHOTOSHOP, "WebStatement", XMPConst.NS_XMP_RIGHTS, "WebStatement", (AliasOptions) null);
        registerAlias(XMPConst.NS_TIFF, "Artist", XMPConst.NS_DC, PdfConst.Creator, aliasToArrayOrdered);
        registerAlias(XMPConst.NS_TIFF, "Copyright", XMPConst.NS_DC, PdfConst.Rights, (AliasOptions) null);
        registerAlias(XMPConst.NS_TIFF, "DateTime", XMPConst.NS_XMP, PdfConst.ModifyDate, (AliasOptions) null);
        registerAlias(XMPConst.NS_TIFF, "ImageDescription", XMPConst.NS_DC, PdfConst.Description, (AliasOptions) null);
        registerAlias(XMPConst.NS_TIFF, "Software", XMPConst.NS_XMP, PdfConst.CreatorTool, (AliasOptions) null);
        registerAlias(XMPConst.NS_PNG, "Author", XMPConst.NS_DC, PdfConst.Creator, aliasToArrayOrdered);
        registerAlias(XMPConst.NS_PNG, "Copyright", XMPConst.NS_DC, PdfConst.Rights, aliasToArrayAltText);
        registerAlias(XMPConst.NS_PNG, "CreationTime", XMPConst.NS_XMP, PdfConst.CreateDate, (AliasOptions) null);
        registerAlias(XMPConst.NS_PNG, "Description", XMPConst.NS_DC, PdfConst.Description, aliasToArrayAltText);
        registerAlias(XMPConst.NS_PNG, "ModificationTime", XMPConst.NS_XMP, PdfConst.ModifyDate, (AliasOptions) null);
        registerAlias(XMPConst.NS_PNG, "Software", XMPConst.NS_XMP, PdfConst.CreatorTool, (AliasOptions) null);
        registerAlias(XMPConst.NS_PNG, StandardRoles.TITLE, XMPConst.NS_DC, "title", aliasToArrayAltText);
    }
}
