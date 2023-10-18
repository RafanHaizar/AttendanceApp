package com.itextpdf.kernel.xmp.impl;

import com.itextpdf.kernel.xmp.XMPConst;
import com.itextpdf.kernel.xmp.XMPDateTime;
import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.kernel.xmp.XMPIterator;
import com.itextpdf.kernel.xmp.XMPMeta;
import com.itextpdf.kernel.xmp.XMPPathFactory;
import com.itextpdf.kernel.xmp.XMPUtils;
import com.itextpdf.kernel.xmp.impl.xpath.XMPPath;
import com.itextpdf.kernel.xmp.impl.xpath.XMPPathParser;
import com.itextpdf.kernel.xmp.options.IteratorOptions;
import com.itextpdf.kernel.xmp.options.ParseOptions;
import com.itextpdf.kernel.xmp.options.PropertyOptions;
import com.itextpdf.kernel.xmp.properties.XMPProperty;
import java.util.Calendar;

public class XMPMetaImpl implements XMPConst, XMPMeta {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int VALUE_BASE64 = 7;
    private static final int VALUE_BOOLEAN = 1;
    private static final int VALUE_CALENDAR = 6;
    private static final int VALUE_DATE = 5;
    private static final int VALUE_DOUBLE = 4;
    private static final int VALUE_INTEGER = 2;
    private static final int VALUE_LONG = 3;
    private static final int VALUE_STRING = 0;
    private String packetHeader;
    private XMPNode tree;

    public XMPMetaImpl() {
        this.packetHeader = null;
        this.tree = new XMPNode((String) null, (String) null, (PropertyOptions) null);
    }

    public XMPMetaImpl(XMPNode tree2) {
        this.packetHeader = null;
        this.tree = tree2;
    }

    public void appendArrayItem(String schemaNS, String arrayName, PropertyOptions arrayOptions, String itemValue, PropertyOptions itemOptions) throws XMPException {
        ParameterAsserts.assertSchemaNS(schemaNS);
        ParameterAsserts.assertArrayName(arrayName);
        if (arrayOptions == null) {
            arrayOptions = new PropertyOptions();
        }
        if (arrayOptions.isOnlyArrayOptions()) {
            PropertyOptions arrayOptions2 = XMPNodeUtils.verifySetOptions(arrayOptions, (Object) null);
            XMPPath arrayPath = XMPPathParser.expandXPath(schemaNS, arrayName);
            XMPNode arrayNode = XMPNodeUtils.findNode(this.tree, arrayPath, false, (PropertyOptions) null);
            if (arrayNode != null) {
                if (!arrayNode.getOptions().isArray()) {
                    throw new XMPException("The named property is not an array", 102);
                }
            } else if (arrayOptions2.isArray()) {
                arrayNode = XMPNodeUtils.findNode(this.tree, arrayPath, true, arrayOptions2);
                if (arrayNode == null) {
                    throw new XMPException("Failure creating array node", 102);
                }
            } else {
                throw new XMPException("Explicit arrayOptions required to create new array", 103);
            }
            doSetArrayItem(arrayNode, -1, itemValue, itemOptions, true);
            return;
        }
        throw new XMPException("Only array form flags allowed for arrayOptions", 103);
    }

    public void appendArrayItem(String schemaNS, String arrayName, String itemValue) throws XMPException {
        appendArrayItem(schemaNS, arrayName, (PropertyOptions) null, itemValue, (PropertyOptions) null);
    }

    public int countArrayItems(String schemaNS, String arrayName) throws XMPException {
        ParameterAsserts.assertSchemaNS(schemaNS);
        ParameterAsserts.assertArrayName(arrayName);
        XMPNode arrayNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(schemaNS, arrayName), false, (PropertyOptions) null);
        if (arrayNode == null) {
            return 0;
        }
        if (arrayNode.getOptions().isArray()) {
            return arrayNode.getChildrenLength();
        }
        throw new XMPException("The named property is not an array", 102);
    }

    public void deleteArrayItem(String schemaNS, String arrayName, int itemIndex) {
        try {
            ParameterAsserts.assertSchemaNS(schemaNS);
            ParameterAsserts.assertArrayName(arrayName);
            deleteProperty(schemaNS, XMPPathFactory.composeArrayItemPath(arrayName, itemIndex));
        } catch (XMPException e) {
        }
    }

    public void deleteProperty(String schemaNS, String propName) {
        try {
            ParameterAsserts.assertSchemaNS(schemaNS);
            ParameterAsserts.assertPropName(propName);
            XMPNode propNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(schemaNS, propName), false, (PropertyOptions) null);
            if (propNode != null) {
                XMPNodeUtils.deleteNode(propNode);
            }
        } catch (XMPException e) {
        }
    }

    public void deleteQualifier(String schemaNS, String propName, String qualNS, String qualName) {
        try {
            ParameterAsserts.assertSchemaNS(schemaNS);
            ParameterAsserts.assertPropName(propName);
            deleteProperty(schemaNS, propName + XMPPathFactory.composeQualifierPath(qualNS, qualName));
        } catch (XMPException e) {
        }
    }

    public void deleteStructField(String schemaNS, String structName, String fieldNS, String fieldName) {
        try {
            ParameterAsserts.assertSchemaNS(schemaNS);
            ParameterAsserts.assertStructName(structName);
            deleteProperty(schemaNS, structName + XMPPathFactory.composeStructFieldPath(fieldNS, fieldName));
        } catch (XMPException e) {
        }
    }

    public boolean doesPropertyExist(String schemaNS, String propName) {
        try {
            ParameterAsserts.assertSchemaNS(schemaNS);
            ParameterAsserts.assertPropName(propName);
            if (XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(schemaNS, propName), false, (PropertyOptions) null) != null) {
                return true;
            }
            return false;
        } catch (XMPException e) {
            return false;
        }
    }

    public boolean doesArrayItemExist(String schemaNS, String arrayName, int itemIndex) {
        try {
            ParameterAsserts.assertSchemaNS(schemaNS);
            ParameterAsserts.assertArrayName(arrayName);
            return doesPropertyExist(schemaNS, XMPPathFactory.composeArrayItemPath(arrayName, itemIndex));
        } catch (XMPException e) {
            return false;
        }
    }

    public boolean doesStructFieldExist(String schemaNS, String structName, String fieldNS, String fieldName) {
        try {
            ParameterAsserts.assertSchemaNS(schemaNS);
            ParameterAsserts.assertStructName(structName);
            return doesPropertyExist(schemaNS, structName + XMPPathFactory.composeStructFieldPath(fieldNS, fieldName));
        } catch (XMPException e) {
            return false;
        }
    }

    public boolean doesQualifierExist(String schemaNS, String propName, String qualNS, String qualName) {
        try {
            ParameterAsserts.assertSchemaNS(schemaNS);
            ParameterAsserts.assertPropName(propName);
            return doesPropertyExist(schemaNS, propName + XMPPathFactory.composeQualifierPath(qualNS, qualName));
        } catch (XMPException e) {
            return false;
        }
    }

    public XMPProperty getArrayItem(String schemaNS, String arrayName, int itemIndex) throws XMPException {
        ParameterAsserts.assertSchemaNS(schemaNS);
        ParameterAsserts.assertArrayName(arrayName);
        return getProperty(schemaNS, XMPPathFactory.composeArrayItemPath(arrayName, itemIndex));
    }

    public XMPProperty getLocalizedText(String schemaNS, String altTextName, String genericLang, String specificLang) throws XMPException {
        ParameterAsserts.assertSchemaNS(schemaNS);
        ParameterAsserts.assertArrayName(altTextName);
        ParameterAsserts.assertSpecificLang(specificLang);
        String genericLang2 = genericLang != null ? Utils.normalizeLangValue(genericLang) : null;
        String specificLang2 = Utils.normalizeLangValue(specificLang);
        XMPNode arrayNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(schemaNS, altTextName), false, (PropertyOptions) null);
        if (arrayNode == null) {
            return null;
        }
        Object[] result = XMPNodeUtils.chooseLocalizedText(arrayNode, genericLang2, specificLang2);
        int match = ((Integer) result[0]).intValue();
        final XMPNode itemNode = (XMPNode) result[1];
        if (match != 0) {
            return new XMPProperty() {
                public String getValue() {
                    return itemNode.getValue();
                }

                public PropertyOptions getOptions() {
                    return itemNode.getOptions();
                }

                public String getLanguage() {
                    return itemNode.getQualifier(1).getValue();
                }

                public String toString() {
                    return itemNode.getValue();
                }
            };
        }
        return null;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: com.itextpdf.kernel.xmp.impl.XMPNode} */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x009e, code lost:
        throw new com.itextpdf.kernel.xmp.XMPException("Language qualifier must be first", 102);
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setLocalizedText(java.lang.String r19, java.lang.String r20, java.lang.String r21, java.lang.String r22, java.lang.String r23, com.itextpdf.kernel.xmp.options.PropertyOptions r24) throws com.itextpdf.kernel.xmp.XMPException {
        /*
            r18 = this;
            r0 = r23
            com.itextpdf.kernel.xmp.impl.ParameterAsserts.assertSchemaNS(r19)
            com.itextpdf.kernel.xmp.impl.ParameterAsserts.assertArrayName(r20)
            com.itextpdf.kernel.xmp.impl.ParameterAsserts.assertSpecificLang(r22)
            if (r21 == 0) goto L_0x0012
            java.lang.String r2 = com.itextpdf.kernel.xmp.impl.Utils.normalizeLangValue(r21)
            goto L_0x0013
        L_0x0012:
            r2 = 0
        L_0x0013:
            java.lang.String r3 = com.itextpdf.kernel.xmp.impl.Utils.normalizeLangValue(r22)
            com.itextpdf.kernel.xmp.impl.xpath.XMPPath r4 = com.itextpdf.kernel.xmp.impl.xpath.XMPPathParser.expandXPath(r19, r20)
            r5 = r18
            com.itextpdf.kernel.xmp.impl.XMPNode r6 = r5.tree
            com.itextpdf.kernel.xmp.options.PropertyOptions r7 = new com.itextpdf.kernel.xmp.options.PropertyOptions
            r8 = 7680(0x1e00, float:1.0762E-41)
            r7.<init>(r8)
            r8 = 1
            com.itextpdf.kernel.xmp.impl.XMPNode r6 = com.itextpdf.kernel.xmp.impl.XMPNodeUtils.findNode(r6, r4, r8, r7)
            r7 = 102(0x66, float:1.43E-43)
            if (r6 == 0) goto L_0x01a5
            com.itextpdf.kernel.xmp.options.PropertyOptions r9 = r6.getOptions()
            boolean r9 = r9.isArrayAltText()
            if (r9 != 0) goto L_0x0059
            boolean r9 = r6.hasChildren()
            if (r9 != 0) goto L_0x0051
            com.itextpdf.kernel.xmp.options.PropertyOptions r9 = r6.getOptions()
            boolean r9 = r9.isArrayAlternate()
            if (r9 == 0) goto L_0x0051
            com.itextpdf.kernel.xmp.options.PropertyOptions r9 = r6.getOptions()
            r9.setArrayAltText(r8)
            goto L_0x0059
        L_0x0051:
            com.itextpdf.kernel.xmp.XMPException r1 = new com.itextpdf.kernel.xmp.XMPException
            java.lang.String r8 = "Specified property is no alt-text array"
            r1.<init>(r8, r7)
            throw r1
        L_0x0059:
            r9 = 0
            r10 = 0
            java.util.Iterator r11 = r6.iterateChildren()
        L_0x005f:
            boolean r12 = r11.hasNext()
            java.lang.String r13 = "x-default"
            if (r12 == 0) goto L_0x009f
            java.lang.Object r12 = r11.next()
            com.itextpdf.kernel.xmp.impl.XMPNode r12 = (com.itextpdf.kernel.xmp.impl.XMPNode) r12
            boolean r14 = r12.hasQualifier()
            if (r14 == 0) goto L_0x0097
            com.itextpdf.kernel.xmp.impl.XMPNode r14 = r12.getQualifier(r8)
            java.lang.String r14 = r14.getName()
            java.lang.String r15 = "xml:lang"
            boolean r14 = r15.equals(r14)
            if (r14 == 0) goto L_0x0097
            com.itextpdf.kernel.xmp.impl.XMPNode r14 = r12.getQualifier(r8)
            java.lang.String r14 = r14.getValue()
            boolean r14 = r13.equals(r14)
            if (r14 == 0) goto L_0x0096
            r10 = r12
            r9 = 1
            goto L_0x009f
        L_0x0096:
            goto L_0x005f
        L_0x0097:
            com.itextpdf.kernel.xmp.XMPException r1 = new com.itextpdf.kernel.xmp.XMPException
            java.lang.String r8 = "Language qualifier must be first"
            r1.<init>(r8, r7)
            throw r1
        L_0x009f:
            if (r10 == 0) goto L_0x00ad
            int r7 = r6.getChildrenLength()
            if (r7 <= r8) goto L_0x00ad
            r6.removeChild((com.itextpdf.kernel.xmp.impl.XMPNode) r10)
            r6.addChild(r8, r10)
        L_0x00ad:
            java.lang.Object[] r7 = com.itextpdf.kernel.xmp.impl.XMPNodeUtils.chooseLocalizedText(r6, r2, r3)
            r11 = 0
            r11 = r7[r11]
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r11 = r11.intValue()
            r12 = r7[r8]
            com.itextpdf.kernel.xmp.impl.XMPNode r12 = (com.itextpdf.kernel.xmp.impl.XMPNode) r12
            boolean r14 = r13.equals(r3)
            switch(r11) {
                case 0: goto L_0x018d;
                case 1: goto L_0x011d;
                case 2: goto L_0x00ff;
                case 3: goto L_0x00f1;
                case 4: goto L_0x00df;
                case 5: goto L_0x00d1;
                default: goto L_0x00c5;
            }
        L_0x00c5:
            r16 = r2
            com.itextpdf.kernel.xmp.XMPException r1 = new com.itextpdf.kernel.xmp.XMPException
            java.lang.String r2 = "Unexpected result from ChooseLocalizedText"
            r8 = 9
            r1.<init>(r2, r8)
            throw r1
        L_0x00d1:
            com.itextpdf.kernel.xmp.impl.XMPNodeUtils.appendLangItem(r6, r3, r0)
            if (r14 == 0) goto L_0x00db
            r9 = 1
            r16 = r2
            goto L_0x0198
        L_0x00db:
            r16 = r2
            goto L_0x0198
        L_0x00df:
            if (r10 == 0) goto L_0x00ea
            int r1 = r6.getChildrenLength()
            if (r1 != r8) goto L_0x00ea
            r10.setValue(r0)
        L_0x00ea:
            com.itextpdf.kernel.xmp.impl.XMPNodeUtils.appendLangItem(r6, r3, r0)
            r16 = r2
            goto L_0x0198
        L_0x00f1:
            com.itextpdf.kernel.xmp.impl.XMPNodeUtils.appendLangItem(r6, r3, r0)
            if (r14 == 0) goto L_0x00fb
            r9 = 1
            r16 = r2
            goto L_0x0198
        L_0x00fb:
            r16 = r2
            goto L_0x0198
        L_0x00ff:
            if (r9 == 0) goto L_0x0116
            if (r10 == r12) goto L_0x0116
            if (r10 == 0) goto L_0x0116
            java.lang.String r1 = r10.getValue()
            java.lang.String r15 = r12.getValue()
            boolean r1 = r1.equals(r15)
            if (r1 == 0) goto L_0x0116
            r10.setValue(r0)
        L_0x0116:
            r12.setValue(r0)
            r16 = r2
            goto L_0x0198
        L_0x011d:
            if (r14 != 0) goto L_0x013c
            if (r9 == 0) goto L_0x0136
            if (r10 == r12) goto L_0x0136
            if (r10 == 0) goto L_0x0136
            java.lang.String r1 = r10.getValue()
            java.lang.String r15 = r12.getValue()
            boolean r1 = r1.equals(r15)
            if (r1 == 0) goto L_0x0136
            r10.setValue(r0)
        L_0x0136:
            r12.setValue(r0)
            r16 = r2
            goto L_0x0198
        L_0x013c:
            if (r9 == 0) goto L_0x0185
            if (r10 != r12) goto L_0x0185
            java.util.Iterator r15 = r6.iterateChildren()
        L_0x0144:
            boolean r16 = r15.hasNext()
            if (r16 == 0) goto L_0x017d
            java.lang.Object r16 = r15.next()
            r1 = r16
            com.itextpdf.kernel.xmp.impl.XMPNode r1 = (com.itextpdf.kernel.xmp.impl.XMPNode) r1
            if (r1 == r10) goto L_0x0179
            java.lang.String r8 = r1.getValue()
            if (r10 == 0) goto L_0x0165
            java.lang.String r16 = r10.getValue()
            r17 = r16
            r16 = r2
            r2 = r17
            goto L_0x0168
        L_0x0165:
            r16 = r2
            r2 = 0
        L_0x0168:
            boolean r2 = r8.equals(r2)
            if (r2 != 0) goto L_0x0172
            r2 = r16
            r8 = 1
            goto L_0x0144
        L_0x0172:
            r1.setValue(r0)
            r2 = r16
            r8 = 1
            goto L_0x0144
        L_0x0179:
            r16 = r2
            r8 = 1
            goto L_0x0144
        L_0x017d:
            r16 = r2
            if (r10 == 0) goto L_0x0198
            r10.setValue(r0)
            goto L_0x0198
        L_0x0185:
            r16 = r2
            java.lang.AssertionError r1 = new java.lang.AssertionError
            r1.<init>()
            throw r1
        L_0x018d:
            r16 = r2
            com.itextpdf.kernel.xmp.impl.XMPNodeUtils.appendLangItem(r6, r13, r0)
            r9 = 1
            if (r14 != 0) goto L_0x0198
            com.itextpdf.kernel.xmp.impl.XMPNodeUtils.appendLangItem(r6, r3, r0)
        L_0x0198:
            if (r9 != 0) goto L_0x01a4
            int r1 = r6.getChildrenLength()
            r2 = 1
            if (r1 != r2) goto L_0x01a4
            com.itextpdf.kernel.xmp.impl.XMPNodeUtils.appendLangItem(r6, r13, r0)
        L_0x01a4:
            return
        L_0x01a5:
            r16 = r2
            com.itextpdf.kernel.xmp.XMPException r1 = new com.itextpdf.kernel.xmp.XMPException
            java.lang.String r2 = "Failed to find or create array node"
            r1.<init>(r2, r7)
            goto L_0x01b0
        L_0x01af:
            throw r1
        L_0x01b0:
            goto L_0x01af
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.xmp.impl.XMPMetaImpl.setLocalizedText(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.itextpdf.kernel.xmp.options.PropertyOptions):void");
    }

    public void setLocalizedText(String schemaNS, String altTextName, String genericLang, String specificLang, String itemValue) throws XMPException {
        setLocalizedText(schemaNS, altTextName, genericLang, specificLang, itemValue, (PropertyOptions) null);
    }

    public XMPProperty getProperty(String schemaNS, String propName) throws XMPException {
        return getProperty(schemaNS, propName, 0);
    }

    /* access modifiers changed from: protected */
    public XMPProperty getProperty(String schemaNS, String propName, int valueType) throws XMPException {
        ParameterAsserts.assertSchemaNS(schemaNS);
        ParameterAsserts.assertPropName(propName);
        final XMPNode propNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(schemaNS, propName), false, (PropertyOptions) null);
        if (propNode == null) {
            return null;
        }
        if (valueType == 0 || !propNode.getOptions().isCompositeProperty()) {
            final Object value = evaluateNodeValue(valueType, propNode);
            return new XMPProperty() {
                public String getValue() {
                    Object obj = value;
                    if (obj != null) {
                        return obj.toString();
                    }
                    return null;
                }

                public PropertyOptions getOptions() {
                    return propNode.getOptions();
                }

                public String getLanguage() {
                    return null;
                }

                public String toString() {
                    return value.toString();
                }
            };
        }
        throw new XMPException("Property must be simple when a value type is requested", 102);
    }

    /* access modifiers changed from: protected */
    public Object getPropertyObject(String schemaNS, String propName, int valueType) throws XMPException {
        ParameterAsserts.assertSchemaNS(schemaNS);
        ParameterAsserts.assertPropName(propName);
        XMPNode propNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(schemaNS, propName), false, (PropertyOptions) null);
        if (propNode == null) {
            return null;
        }
        if (valueType == 0 || !propNode.getOptions().isCompositeProperty()) {
            return evaluateNodeValue(valueType, propNode);
        }
        throw new XMPException("Property must be simple when a value type is requested", 102);
    }

    public Boolean getPropertyBoolean(String schemaNS, String propName) throws XMPException {
        return (Boolean) getPropertyObject(schemaNS, propName, 1);
    }

    public void setPropertyBoolean(String schemaNS, String propName, boolean propValue, PropertyOptions options) throws XMPException {
        setProperty(schemaNS, propName, propValue ? XMPConst.TRUESTR : XMPConst.FALSESTR, options);
    }

    public void setPropertyBoolean(String schemaNS, String propName, boolean propValue) throws XMPException {
        setProperty(schemaNS, propName, propValue ? XMPConst.TRUESTR : XMPConst.FALSESTR, (PropertyOptions) null);
    }

    public Integer getPropertyInteger(String schemaNS, String propName) throws XMPException {
        return (Integer) getPropertyObject(schemaNS, propName, 2);
    }

    public void setPropertyInteger(String schemaNS, String propName, int propValue, PropertyOptions options) throws XMPException {
        setProperty(schemaNS, propName, new Integer(propValue), options);
    }

    public void setPropertyInteger(String schemaNS, String propName, int propValue) throws XMPException {
        setProperty(schemaNS, propName, new Integer(propValue), (PropertyOptions) null);
    }

    public Long getPropertyLong(String schemaNS, String propName) throws XMPException {
        return (Long) getPropertyObject(schemaNS, propName, 3);
    }

    public void setPropertyLong(String schemaNS, String propName, long propValue, PropertyOptions options) throws XMPException {
        setProperty(schemaNS, propName, new Long(propValue), options);
    }

    public void setPropertyLong(String schemaNS, String propName, long propValue) throws XMPException {
        setProperty(schemaNS, propName, new Long(propValue), (PropertyOptions) null);
    }

    public Double getPropertyDouble(String schemaNS, String propName) throws XMPException {
        return (Double) getPropertyObject(schemaNS, propName, 4);
    }

    public void setPropertyDouble(String schemaNS, String propName, double propValue, PropertyOptions options) throws XMPException {
        setProperty(schemaNS, propName, new Double(propValue), options);
    }

    public void setPropertyDouble(String schemaNS, String propName, double propValue) throws XMPException {
        setProperty(schemaNS, propName, new Double(propValue), (PropertyOptions) null);
    }

    public XMPDateTime getPropertyDate(String schemaNS, String propName) throws XMPException {
        return (XMPDateTime) getPropertyObject(schemaNS, propName, 5);
    }

    public void setPropertyDate(String schemaNS, String propName, XMPDateTime propValue, PropertyOptions options) throws XMPException {
        setProperty(schemaNS, propName, propValue, options);
    }

    public void setPropertyDate(String schemaNS, String propName, XMPDateTime propValue) throws XMPException {
        setProperty(schemaNS, propName, propValue, (PropertyOptions) null);
    }

    public Calendar getPropertyCalendar(String schemaNS, String propName) throws XMPException {
        return (Calendar) getPropertyObject(schemaNS, propName, 6);
    }

    public void setPropertyCalendar(String schemaNS, String propName, Calendar propValue, PropertyOptions options) throws XMPException {
        setProperty(schemaNS, propName, propValue, options);
    }

    public void setPropertyCalendar(String schemaNS, String propName, Calendar propValue) throws XMPException {
        setProperty(schemaNS, propName, propValue, (PropertyOptions) null);
    }

    public byte[] getPropertyBase64(String schemaNS, String propName) throws XMPException {
        return (byte[]) getPropertyObject(schemaNS, propName, 7);
    }

    public String getPropertyString(String schemaNS, String propName) throws XMPException {
        return (String) getPropertyObject(schemaNS, propName, 0);
    }

    public void setPropertyBase64(String schemaNS, String propName, byte[] propValue, PropertyOptions options) throws XMPException {
        setProperty(schemaNS, propName, propValue, options);
    }

    public void setPropertyBase64(String schemaNS, String propName, byte[] propValue) throws XMPException {
        setProperty(schemaNS, propName, propValue, (PropertyOptions) null);
    }

    public XMPProperty getQualifier(String schemaNS, String propName, String qualNS, String qualName) throws XMPException {
        ParameterAsserts.assertSchemaNS(schemaNS);
        ParameterAsserts.assertPropName(propName);
        return getProperty(schemaNS, propName + XMPPathFactory.composeQualifierPath(qualNS, qualName));
    }

    public XMPProperty getStructField(String schemaNS, String structName, String fieldNS, String fieldName) throws XMPException {
        ParameterAsserts.assertSchemaNS(schemaNS);
        ParameterAsserts.assertStructName(structName);
        return getProperty(schemaNS, structName + XMPPathFactory.composeStructFieldPath(fieldNS, fieldName));
    }

    public XMPIterator iterator() throws XMPException {
        return iterator((String) null, (String) null, (IteratorOptions) null);
    }

    public XMPIterator iterator(IteratorOptions options) throws XMPException {
        return iterator((String) null, (String) null, options);
    }

    public XMPIterator iterator(String schemaNS, String propName, IteratorOptions options) throws XMPException {
        return new XMPIteratorImpl(this, schemaNS, propName, options);
    }

    public void setArrayItem(String schemaNS, String arrayName, int itemIndex, String itemValue, PropertyOptions options) throws XMPException {
        ParameterAsserts.assertSchemaNS(schemaNS);
        ParameterAsserts.assertArrayName(arrayName);
        XMPNode arrayNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(schemaNS, arrayName), false, (PropertyOptions) null);
        if (arrayNode != null) {
            doSetArrayItem(arrayNode, itemIndex, itemValue, options, false);
            return;
        }
        throw new XMPException("Specified array does not exist", 102);
    }

    public void setArrayItem(String schemaNS, String arrayName, int itemIndex, String itemValue) throws XMPException {
        setArrayItem(schemaNS, arrayName, itemIndex, itemValue, (PropertyOptions) null);
    }

    public void insertArrayItem(String schemaNS, String arrayName, int itemIndex, String itemValue, PropertyOptions options) throws XMPException {
        ParameterAsserts.assertSchemaNS(schemaNS);
        ParameterAsserts.assertArrayName(arrayName);
        XMPNode arrayNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(schemaNS, arrayName), false, (PropertyOptions) null);
        if (arrayNode != null) {
            doSetArrayItem(arrayNode, itemIndex, itemValue, options, true);
            return;
        }
        throw new XMPException("Specified array does not exist", 102);
    }

    public void insertArrayItem(String schemaNS, String arrayName, int itemIndex, String itemValue) throws XMPException {
        insertArrayItem(schemaNS, arrayName, itemIndex, itemValue, (PropertyOptions) null);
    }

    public void setProperty(String schemaNS, String propName, Object propValue, PropertyOptions options) throws XMPException {
        ParameterAsserts.assertSchemaNS(schemaNS);
        ParameterAsserts.assertPropName(propName);
        PropertyOptions options2 = XMPNodeUtils.verifySetOptions(options, propValue);
        XMPNode propNode = XMPNodeUtils.findNode(this.tree, XMPPathParser.expandXPath(schemaNS, propName), true, options2);
        if (propNode != null) {
            setNode(propNode, propValue, options2, false);
            return;
        }
        throw new XMPException("Specified property does not exist", 102);
    }

    public void setProperty(String schemaNS, String propName, Object propValue) throws XMPException {
        setProperty(schemaNS, propName, propValue, (PropertyOptions) null);
    }

    public void setQualifier(String schemaNS, String propName, String qualNS, String qualName, String qualValue, PropertyOptions options) throws XMPException {
        ParameterAsserts.assertSchemaNS(schemaNS);
        ParameterAsserts.assertPropName(propName);
        if (doesPropertyExist(schemaNS, propName)) {
            setProperty(schemaNS, propName + XMPPathFactory.composeQualifierPath(qualNS, qualName), qualValue, options);
            return;
        }
        throw new XMPException("Specified property does not exist!", 102);
    }

    public void setQualifier(String schemaNS, String propName, String qualNS, String qualName, String qualValue) throws XMPException {
        setQualifier(schemaNS, propName, qualNS, qualName, qualValue, (PropertyOptions) null);
    }

    public void setStructField(String schemaNS, String structName, String fieldNS, String fieldName, String fieldValue, PropertyOptions options) throws XMPException {
        ParameterAsserts.assertSchemaNS(schemaNS);
        ParameterAsserts.assertStructName(structName);
        setProperty(schemaNS, structName + XMPPathFactory.composeStructFieldPath(fieldNS, fieldName), fieldValue, options);
    }

    public void setStructField(String schemaNS, String structName, String fieldNS, String fieldName, String fieldValue) throws XMPException {
        setStructField(schemaNS, structName, fieldNS, fieldName, fieldValue, (PropertyOptions) null);
    }

    public String getObjectName() {
        return this.tree.getName() != null ? this.tree.getName() : "";
    }

    public void setObjectName(String name) {
        this.tree.setName(name);
    }

    public String getPacketHeader() {
        return this.packetHeader;
    }

    public void setPacketHeader(String packetHeader2) {
        this.packetHeader = packetHeader2;
    }

    public Object clone() {
        return new XMPMetaImpl((XMPNode) this.tree.clone());
    }

    public String dumpObject() {
        return getRoot().dumpNode(true);
    }

    public void sort() {
        this.tree.sort();
    }

    public void normalize(ParseOptions options) throws XMPException {
        if (options == null) {
            options = new ParseOptions();
        }
        XMPNormalizer.process(this, options);
    }

    public XMPNode getRoot() {
        return this.tree;
    }

    private void doSetArrayItem(XMPNode arrayNode, int itemIndex, String itemValue, PropertyOptions itemOptions, boolean insert) throws XMPException {
        XMPNode itemNode = new XMPNode(XMPConst.ARRAY_ITEM_NAME, (PropertyOptions) null);
        PropertyOptions itemOptions2 = XMPNodeUtils.verifySetOptions(itemOptions, itemValue);
        int maxIndex = arrayNode.getChildrenLength();
        if (insert) {
            maxIndex++;
        }
        if (itemIndex == -1) {
            itemIndex = maxIndex;
        }
        if (1 > itemIndex || itemIndex > maxIndex) {
            throw new XMPException("Array index out of bounds", 104);
        }
        if (!insert) {
            arrayNode.removeChild(itemIndex);
        }
        arrayNode.addChild(itemIndex, itemNode);
        setNode(itemNode, itemValue, itemOptions2, false);
    }

    /* access modifiers changed from: package-private */
    public void setNode(XMPNode node, Object value, PropertyOptions newOptions, boolean deleteExisting) throws XMPException {
        if (deleteExisting) {
            node.clear();
        }
        node.getOptions().mergeWith(newOptions);
        if (!node.getOptions().isCompositeProperty()) {
            XMPNodeUtils.setNodeValue(node, value);
        } else if (value == null || value.toString().length() <= 0) {
            node.removeChildren();
        } else {
            throw new XMPException("Composite nodes can't have values", 102);
        }
    }

    private Object evaluateNodeValue(int valueType, XMPNode propNode) throws XMPException {
        String rawValue = propNode.getValue();
        switch (valueType) {
            case 1:
                return Boolean.valueOf(XMPUtils.convertToBoolean(rawValue));
            case 2:
                return Integer.valueOf(XMPUtils.convertToInteger(rawValue));
            case 3:
                return Long.valueOf(XMPUtils.convertToLong(rawValue));
            case 4:
                return Double.valueOf(XMPUtils.convertToDouble(rawValue));
            case 5:
                return XMPUtils.convertToDate(rawValue);
            case 6:
                return XMPUtils.convertToDate(rawValue).getCalendar();
            case 7:
                return XMPUtils.decodeBase64(rawValue);
            default:
                return (rawValue != null || propNode.getOptions().isCompositeProperty()) ? rawValue : "";
        }
    }
}
