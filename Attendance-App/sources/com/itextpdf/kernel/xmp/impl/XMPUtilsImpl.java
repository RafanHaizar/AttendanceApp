package com.itextpdf.kernel.xmp.impl;

import com.itextpdf.kernel.xmp.XMPConst;
import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.kernel.xmp.XMPMeta;
import com.itextpdf.kernel.xmp.XMPMetaFactory;
import com.itextpdf.kernel.xmp.impl.xpath.XMPPath;
import com.itextpdf.kernel.xmp.impl.xpath.XMPPathParser;
import com.itextpdf.kernel.xmp.options.PropertyOptions;
import com.itextpdf.kernel.xmp.properties.XMPAliasInfo;
import java.util.Iterator;
import kotlin.text.Typography;
import org.bouncycastle.crypto.tls.CipherSuite;

public final class XMPUtilsImpl implements XMPConst {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String COMMAS = ",，､﹐﹑、،՝";
    private static final String CONTROLS = "  ";
    private static final String QUOTES = "\"«»〝〞〟―‹›";
    private static final String SEMICOLA = ";；﹔؛;";
    private static final String SPACES = " 　〿";
    private static final int UCK_COMMA = 2;
    private static final int UCK_CONTROL = 5;
    private static final int UCK_NORMAL = 0;
    private static final int UCK_QUOTE = 4;
    private static final int UCK_SEMICOLON = 3;
    private static final int UCK_SPACE = 1;

    private XMPUtilsImpl() {
    }

    public static String catenateArrayItems(XMPMeta xmp, String schemaNS, String arrayName, String separator, String quotes, boolean allowCommas) throws XMPException {
        String separator2;
        String quotes2;
        ParameterAsserts.assertSchemaNS(schemaNS);
        ParameterAsserts.assertArrayName(arrayName);
        ParameterAsserts.assertImplementation(xmp);
        if (separator == null || separator.length() == 0) {
            separator2 = "; ";
        } else {
            separator2 = separator;
        }
        if (quotes == null || quotes.length() == 0) {
            quotes2 = "\"";
        } else {
            quotes2 = quotes;
        }
        XMPNode arrayNode = XMPNodeUtils.findNode(((XMPMetaImpl) xmp).getRoot(), XMPPathParser.expandXPath(schemaNS, arrayName), false, (PropertyOptions) null);
        if (arrayNode == null) {
            return "";
        }
        if (!arrayNode.getOptions().isArray() || arrayNode.getOptions().isArrayAlternate()) {
            boolean z = allowCommas;
            throw new XMPException("Named property must be non-alternate array", 4);
        }
        checkSeparator(separator2);
        char openQuote = quotes2.charAt(0);
        char closeQuote = checkQuotes(quotes2, openQuote);
        StringBuffer catinatedString = new StringBuffer();
        Iterator it = arrayNode.iterateChildren();
        while (it.hasNext()) {
            XMPNode currItem = (XMPNode) it.next();
            if (!currItem.getOptions().isCompositeProperty()) {
                catinatedString.append(applyQuotes(currItem.getValue(), openQuote, closeQuote, allowCommas));
                if (it.hasNext()) {
                    catinatedString.append(separator2);
                }
            } else {
                boolean z2 = allowCommas;
                throw new XMPException("Array items must be simple", 4);
            }
        }
        boolean z3 = allowCommas;
        return catinatedString.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005c, code lost:
        r9 = r0.charAt(r10 + 1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void separateArrayItems(com.itextpdf.kernel.xmp.XMPMeta r18, java.lang.String r19, java.lang.String r20, java.lang.String r21, com.itextpdf.kernel.xmp.options.PropertyOptions r22, boolean r23) throws com.itextpdf.kernel.xmp.XMPException {
        /*
            r0 = r21
            com.itextpdf.kernel.xmp.impl.ParameterAsserts.assertSchemaNS(r19)
            com.itextpdf.kernel.xmp.impl.ParameterAsserts.assertArrayName(r20)
            r1 = 4
            if (r0 == 0) goto L_0x0128
            com.itextpdf.kernel.xmp.impl.ParameterAsserts.assertImplementation(r18)
            r2 = r18
            com.itextpdf.kernel.xmp.impl.XMPMetaImpl r2 = (com.itextpdf.kernel.xmp.impl.XMPMetaImpl) r2
            r3 = r19
            r4 = r20
            r5 = r22
            com.itextpdf.kernel.xmp.impl.XMPNode r6 = separateFindCreateArray(r3, r4, r5, r2)
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            int r11 = r21.length()
        L_0x0024:
            if (r10 >= r11) goto L_0x0127
            r12 = r10
        L_0x0027:
            if (r12 >= r11) goto L_0x0039
            char r9 = r0.charAt(r12)
            int r8 = classifyCharacter(r9)
            if (r8 == 0) goto L_0x0039
            if (r8 != r1) goto L_0x0036
            goto L_0x0039
        L_0x0036:
            int r12 = r12 + 1
            goto L_0x0027
        L_0x0039:
            if (r12 < r11) goto L_0x003d
            goto L_0x0127
        L_0x003d:
            r13 = 1
            if (r8 == r1) goto L_0x0077
            r10 = r12
        L_0x0041:
            if (r10 >= r11) goto L_0x0071
            char r9 = r0.charAt(r10)
            int r8 = classifyCharacter(r9)
            if (r8 == 0) goto L_0x006e
            if (r8 == r1) goto L_0x006e
            r14 = 2
            if (r8 != r14) goto L_0x0055
            if (r23 == 0) goto L_0x0055
            goto L_0x006e
        L_0x0055:
            if (r8 == r13) goto L_0x0058
            goto L_0x0071
        L_0x0058:
            int r15 = r10 + 1
            if (r15 >= r11) goto L_0x0071
            int r15 = r10 + 1
            char r9 = r0.charAt(r15)
            int r7 = classifyCharacter(r9)
            if (r7 == 0) goto L_0x006e
            if (r7 == r1) goto L_0x006e
            if (r7 != r14) goto L_0x0071
            if (r23 == 0) goto L_0x0071
        L_0x006e:
            int r10 = r10 + 1
            goto L_0x0041
        L_0x0071:
            java.lang.String r13 = r0.substring(r12, r10)
            goto L_0x00f3
        L_0x0077:
            r14 = r9
            char r15 = getClosingQuote(r14)
            int r12 = r12 + 1
            java.lang.String r16 = ""
            r10 = r12
            r13 = r16
        L_0x0083:
            if (r10 >= r11) goto L_0x00f3
            char r9 = r0.charAt(r10)
            int r8 = classifyCharacter(r9)
            if (r8 != r1) goto L_0x00db
            boolean r17 = isSurroundingQuote(r9, r14, r15)
            if (r17 != 0) goto L_0x0096
            goto L_0x00db
        L_0x0096:
            int r1 = r10 + 1
            if (r1 >= r11) goto L_0x00a5
            int r1 = r10 + 1
            char r1 = r0.charAt(r1)
            int r7 = classifyCharacter(r1)
            goto L_0x00a8
        L_0x00a5:
            r7 = 3
            r1 = 59
        L_0x00a8:
            if (r9 != r1) goto L_0x00bf
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.StringBuilder r0 = r0.append(r13)
            java.lang.StringBuilder r0 = r0.append(r9)
            java.lang.String r0 = r0.toString()
            int r10 = r10 + 1
            r13 = r0
            goto L_0x00ed
        L_0x00bf:
            boolean r0 = isClosingingQuote(r9, r14, r15)
            if (r0 != 0) goto L_0x00d8
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.StringBuilder r0 = r0.append(r13)
            java.lang.StringBuilder r0 = r0.append(r9)
            java.lang.String r0 = r0.toString()
            r13 = r0
            goto L_0x00ed
        L_0x00d8:
            int r10 = r10 + 1
            goto L_0x00f3
        L_0x00db:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.StringBuilder r0 = r0.append(r13)
            java.lang.StringBuilder r0 = r0.append(r9)
            java.lang.String r0 = r0.toString()
            r13 = r0
        L_0x00ed:
            r0 = 1
            int r10 = r10 + r0
            r0 = r21
            r1 = 4
            goto L_0x0083
        L_0x00f3:
            r0 = -1
            r1 = 1
        L_0x00f5:
            int r14 = r6.getChildrenLength()
            if (r1 > r14) goto L_0x010e
            com.itextpdf.kernel.xmp.impl.XMPNode r14 = r6.getChild(r1)
            java.lang.String r14 = r14.getValue()
            boolean r14 = r13.equals(r14)
            if (r14 == 0) goto L_0x010b
            r0 = r1
            goto L_0x010e
        L_0x010b:
            int r1 = r1 + 1
            goto L_0x00f5
        L_0x010e:
            r1 = 0
            if (r0 >= 0) goto L_0x0120
            com.itextpdf.kernel.xmp.impl.XMPNode r14 = new com.itextpdf.kernel.xmp.impl.XMPNode
            java.lang.String r15 = "[]"
            r16 = r0
            r0 = 0
            r14.<init>(r15, r13, r0)
            r0 = r14
            r6.addChild(r0)
            goto L_0x0122
        L_0x0120:
            r16 = r0
        L_0x0122:
            r0 = r21
            r1 = 4
            goto L_0x0024
        L_0x0127:
            return
        L_0x0128:
            r3 = r19
            r4 = r20
            r5 = r22
            com.itextpdf.kernel.xmp.XMPException r0 = new com.itextpdf.kernel.xmp.XMPException
            java.lang.String r1 = "Parameter must not be null"
            r2 = 4
            r0.<init>(r1, r2)
            goto L_0x0138
        L_0x0137:
            throw r0
        L_0x0138:
            goto L_0x0137
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.xmp.impl.XMPUtilsImpl.separateArrayItems(com.itextpdf.kernel.xmp.XMPMeta, java.lang.String, java.lang.String, java.lang.String, com.itextpdf.kernel.xmp.options.PropertyOptions, boolean):void");
    }

    private static XMPNode separateFindCreateArray(String schemaNS, String arrayName, PropertyOptions arrayOptions, XMPMetaImpl xmp) throws XMPException {
        PropertyOptions arrayOptions2 = XMPNodeUtils.verifySetOptions(arrayOptions, (Object) null);
        if (arrayOptions2.isOnlyArrayOptions()) {
            XMPPath arrayPath = XMPPathParser.expandXPath(schemaNS, arrayName);
            XMPNode arrayNode = XMPNodeUtils.findNode(xmp.getRoot(), arrayPath, false, (PropertyOptions) null);
            if (arrayNode != null) {
                PropertyOptions arrayForm = arrayNode.getOptions();
                if (!arrayForm.isArray() || arrayForm.isArrayAlternate()) {
                    throw new XMPException("Named property must be non-alternate array", 102);
                } else if (arrayOptions2.equalArrayTypes(arrayForm)) {
                    throw new XMPException("Mismatch of specified and existing array form", 102);
                }
            } else {
                arrayNode = XMPNodeUtils.findNode(xmp.getRoot(), arrayPath, true, arrayOptions2.setArray(true));
                if (arrayNode == null) {
                    throw new XMPException("Failed to create named array", 102);
                }
            }
            return arrayNode;
        }
        throw new XMPException("Options can only provide array form", 103);
    }

    public static void removeProperties(XMPMeta xmp, String schemaNS, String propName, boolean doAllProperties, boolean includeAliases) throws XMPException {
        ParameterAsserts.assertImplementation(xmp);
        XMPMetaImpl xmpImpl = (XMPMetaImpl) xmp;
        if (propName == null || propName.length() <= 0) {
            if (schemaNS == null || schemaNS.length() <= 0) {
                Iterator it = xmpImpl.getRoot().iterateChildren();
                while (it.hasNext()) {
                    if (removeSchemaChildren((XMPNode) it.next(), doAllProperties)) {
                        it.remove();
                    }
                }
                return;
            }
            XMPNode schemaNode = XMPNodeUtils.findSchemaNode(xmpImpl.getRoot(), schemaNS, false);
            if (schemaNode != null && removeSchemaChildren(schemaNode, doAllProperties)) {
                xmpImpl.getRoot().removeChild(schemaNode);
            }
            if (includeAliases) {
                XMPAliasInfo[] aliases = XMPMetaFactory.getSchemaRegistry().findAliases(schemaNS);
                for (XMPAliasInfo info : aliases) {
                    XMPNode actualProp = XMPNodeUtils.findNode(xmpImpl.getRoot(), XMPPathParser.expandXPath(info.getNamespace(), info.getPropName()), false, (PropertyOptions) null);
                    if (actualProp != null) {
                        actualProp.getParent().removeChild(actualProp);
                    }
                }
            }
        } else if (schemaNS == null || schemaNS.length() == 0) {
            throw new XMPException("Property name requires schema namespace", 4);
        } else {
            XMPPath expPath = XMPPathParser.expandXPath(schemaNS, propName);
            XMPNode propNode = XMPNodeUtils.findNode(xmpImpl.getRoot(), expPath, false, (PropertyOptions) null);
            if (propNode == null) {
                return;
            }
            if (doAllProperties || !Utils.isInternalProperty(expPath.getSegment(0).getName(), expPath.getSegment(1).getName())) {
                XMPNode parent = propNode.getParent();
                parent.removeChild(propNode);
                if (parent.getOptions().isSchemaNode() && !parent.hasChildren()) {
                    parent.getParent().removeChild(parent);
                }
            }
        }
    }

    public static void appendProperties(XMPMeta source, XMPMeta destination, boolean doAllProperties, boolean replaceOldValues, boolean deleteEmptyValues) throws XMPException {
        ParameterAsserts.assertImplementation(source);
        ParameterAsserts.assertImplementation(destination);
        XMPMetaImpl dest = (XMPMetaImpl) destination;
        Iterator it = ((XMPMetaImpl) source).getRoot().iterateChildren();
        while (it.hasNext()) {
            XMPNode sourceSchema = (XMPNode) it.next();
            XMPNode destSchema = XMPNodeUtils.findSchemaNode(dest.getRoot(), sourceSchema.getName(), false);
            boolean createdSchema = false;
            if (destSchema == null) {
                destSchema = new XMPNode(sourceSchema.getName(), sourceSchema.getValue(), new PropertyOptions().setSchemaNode(true));
                dest.getRoot().addChild(destSchema);
                createdSchema = true;
            }
            Iterator ic = sourceSchema.iterateChildren();
            while (ic.hasNext()) {
                XMPNode sourceProp = (XMPNode) ic.next();
                if (doAllProperties || !Utils.isInternalProperty(sourceSchema.getName(), sourceProp.getName())) {
                    appendSubtree(dest, sourceProp, destSchema, replaceOldValues, deleteEmptyValues);
                }
            }
            if (!destSchema.hasChildren() && (createdSchema || deleteEmptyValues)) {
                dest.getRoot().removeChild(destSchema);
            }
        }
    }

    private static boolean removeSchemaChildren(XMPNode schemaNode, boolean doAllProperties) {
        Iterator it = schemaNode.iterateChildren();
        while (it.hasNext()) {
            XMPNode currProp = (XMPNode) it.next();
            if (doAllProperties || !Utils.isInternalProperty(schemaNode.getName(), currProp.getName())) {
                it.remove();
            }
        }
        return !schemaNode.hasChildren();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: com.itextpdf.kernel.xmp.impl.XMPNode} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void appendSubtree(com.itextpdf.kernel.xmp.impl.XMPMetaImpl r16, com.itextpdf.kernel.xmp.impl.XMPNode r17, com.itextpdf.kernel.xmp.impl.XMPNode r18, boolean r19, boolean r20) throws com.itextpdf.kernel.xmp.XMPException {
        /*
            r0 = r16
            r1 = r18
            r2 = r19
            r3 = r20
            java.lang.String r4 = r17.getName()
            r5 = 0
            com.itextpdf.kernel.xmp.impl.XMPNode r4 = com.itextpdf.kernel.xmp.impl.XMPNodeUtils.findChildNode(r1, r4, r5)
            r6 = 0
            r7 = 1
            if (r3 == 0) goto L_0x0039
            com.itextpdf.kernel.xmp.options.PropertyOptions r8 = r17.getOptions()
            boolean r8 = r8.isSimple()
            if (r8 == 0) goto L_0x0031
            java.lang.String r8 = r17.getValue()
            if (r8 == 0) goto L_0x002f
            java.lang.String r8 = r17.getValue()
            int r8 = r8.length()
            if (r8 != 0) goto L_0x0038
        L_0x002f:
            r5 = 1
            goto L_0x0038
        L_0x0031:
            boolean r8 = r17.hasChildren()
            if (r8 != 0) goto L_0x0038
            r5 = 1
        L_0x0038:
            r6 = r5
        L_0x0039:
            if (r3 == 0) goto L_0x0044
            if (r6 == 0) goto L_0x0044
            if (r4 == 0) goto L_0x0170
            r1.removeChild((com.itextpdf.kernel.xmp.impl.XMPNode) r4)
            goto L_0x0170
        L_0x0044:
            if (r4 != 0) goto L_0x0051
            java.lang.Object r5 = r17.clone()
            com.itextpdf.kernel.xmp.impl.XMPNode r5 = (com.itextpdf.kernel.xmp.impl.XMPNode) r5
            r1.addChild(r5)
            goto L_0x0170
        L_0x0051:
            if (r2 == 0) goto L_0x006d
            java.lang.String r5 = r17.getValue()
            com.itextpdf.kernel.xmp.options.PropertyOptions r8 = r17.getOptions()
            r0.setNode(r4, r5, r8, r7)
            r1.removeChild((com.itextpdf.kernel.xmp.impl.XMPNode) r4)
            java.lang.Object r5 = r17.clone()
            r4 = r5
            com.itextpdf.kernel.xmp.impl.XMPNode r4 = (com.itextpdf.kernel.xmp.impl.XMPNode) r4
            r1.addChild(r4)
            goto L_0x0170
        L_0x006d:
            com.itextpdf.kernel.xmp.options.PropertyOptions r5 = r17.getOptions()
            com.itextpdf.kernel.xmp.options.PropertyOptions r8 = r4.getOptions()
            if (r5 == r8) goto L_0x0078
            return
        L_0x0078:
            boolean r9 = r5.isStruct()
            if (r9 == 0) goto L_0x009f
            java.util.Iterator r7 = r17.iterateChildren()
        L_0x0082:
            boolean r9 = r7.hasNext()
            if (r9 == 0) goto L_0x009d
            java.lang.Object r9 = r7.next()
            com.itextpdf.kernel.xmp.impl.XMPNode r9 = (com.itextpdf.kernel.xmp.impl.XMPNode) r9
            appendSubtree(r0, r9, r4, r2, r3)
            if (r3 == 0) goto L_0x009c
            boolean r10 = r4.hasChildren()
            if (r10 != 0) goto L_0x009c
            r1.removeChild((com.itextpdf.kernel.xmp.impl.XMPNode) r4)
        L_0x009c:
            goto L_0x0082
        L_0x009d:
            goto L_0x0170
        L_0x009f:
            boolean r9 = r5.isArrayAltText()
            if (r9 == 0) goto L_0x0134
            java.util.Iterator r9 = r17.iterateChildren()
        L_0x00a9:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x0133
            java.lang.Object r10 = r9.next()
            com.itextpdf.kernel.xmp.impl.XMPNode r10 = (com.itextpdf.kernel.xmp.impl.XMPNode) r10
            boolean r11 = r10.hasQualifier()
            if (r11 == 0) goto L_0x00a9
            com.itextpdf.kernel.xmp.impl.XMPNode r11 = r10.getQualifier(r7)
            java.lang.String r11 = r11.getName()
            java.lang.String r12 = "xml:lang"
            boolean r11 = r12.equals(r11)
            if (r11 != 0) goto L_0x00cd
            goto L_0x00a9
        L_0x00cd:
            com.itextpdf.kernel.xmp.impl.XMPNode r11 = r10.getQualifier(r7)
            java.lang.String r11 = r11.getValue()
            int r11 = com.itextpdf.kernel.xmp.impl.XMPNodeUtils.lookupLanguageItem(r4, r11)
            r12 = -1
            if (r3 == 0) goto L_0x00fc
            java.lang.String r13 = r10.getValue()
            if (r13 == 0) goto L_0x00ed
            java.lang.String r13 = r10.getValue()
            int r13 = r13.length()
            if (r13 != 0) goto L_0x00fc
        L_0x00ed:
            if (r11 == r12) goto L_0x0131
            r4.removeChild((int) r11)
            boolean r12 = r4.hasChildren()
            if (r12 != 0) goto L_0x0131
            r1.removeChild((com.itextpdf.kernel.xmp.impl.XMPNode) r4)
            goto L_0x0131
        L_0x00fc:
            if (r11 != r12) goto L_0x0131
            com.itextpdf.kernel.xmp.impl.XMPNode r12 = r10.getQualifier(r7)
            java.lang.String r12 = r12.getValue()
            java.lang.String r13 = "x-default"
            boolean r12 = r13.equals(r12)
            if (r12 == 0) goto L_0x012e
            boolean r12 = r4.hasChildren()
            if (r12 != 0) goto L_0x0116
            goto L_0x012e
        L_0x0116:
            com.itextpdf.kernel.xmp.impl.XMPNode r12 = new com.itextpdf.kernel.xmp.impl.XMPNode
            java.lang.String r13 = r10.getName()
            java.lang.String r14 = r10.getValue()
            com.itextpdf.kernel.xmp.options.PropertyOptions r15 = r10.getOptions()
            r12.<init>(r13, r14, r15)
            r10.cloneSubtree(r12)
            r4.addChild(r7, r12)
            goto L_0x0131
        L_0x012e:
            r10.cloneSubtree(r4)
        L_0x0131:
            goto L_0x00a9
        L_0x0133:
            goto L_0x0170
        L_0x0134:
            boolean r7 = r5.isArray()
            if (r7 == 0) goto L_0x0170
            java.util.Iterator r7 = r17.iterateChildren()
        L_0x013e:
            boolean r9 = r7.hasNext()
            if (r9 == 0) goto L_0x0170
            java.lang.Object r9 = r7.next()
            com.itextpdf.kernel.xmp.impl.XMPNode r9 = (com.itextpdf.kernel.xmp.impl.XMPNode) r9
            r10 = 0
            java.util.Iterator r11 = r4.iterateChildren()
        L_0x014f:
            boolean r12 = r11.hasNext()
            if (r12 == 0) goto L_0x0163
            java.lang.Object r12 = r11.next()
            com.itextpdf.kernel.xmp.impl.XMPNode r12 = (com.itextpdf.kernel.xmp.impl.XMPNode) r12
            boolean r13 = itemValuesMatch(r9, r12)
            if (r13 == 0) goto L_0x0162
            r10 = 1
        L_0x0162:
            goto L_0x014f
        L_0x0163:
            if (r10 != 0) goto L_0x016f
            java.lang.Object r11 = r9.clone()
            r4 = r11
            com.itextpdf.kernel.xmp.impl.XMPNode r4 = (com.itextpdf.kernel.xmp.impl.XMPNode) r4
            r1.addChild(r4)
        L_0x016f:
            goto L_0x013e
        L_0x0170:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.xmp.impl.XMPUtilsImpl.appendSubtree(com.itextpdf.kernel.xmp.impl.XMPMetaImpl, com.itextpdf.kernel.xmp.impl.XMPNode, com.itextpdf.kernel.xmp.impl.XMPNode, boolean, boolean):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0075  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean itemValuesMatch(com.itextpdf.kernel.xmp.impl.XMPNode r10, com.itextpdf.kernel.xmp.impl.XMPNode r11) throws com.itextpdf.kernel.xmp.XMPException {
        /*
            com.itextpdf.kernel.xmp.options.PropertyOptions r0 = r10.getOptions()
            com.itextpdf.kernel.xmp.options.PropertyOptions r1 = r11.getOptions()
            boolean r2 = r0.equals(r1)
            r3 = 0
            if (r2 == 0) goto L_0x0010
            return r3
        L_0x0010:
            int r2 = r0.getOptions()
            r4 = 1
            if (r2 != 0) goto L_0x005a
            java.lang.String r2 = r10.getValue()
            java.lang.String r5 = r11.getValue()
            boolean r2 = r2.equals(r5)
            if (r2 != 0) goto L_0x0026
            return r3
        L_0x0026:
            com.itextpdf.kernel.xmp.options.PropertyOptions r2 = r10.getOptions()
            boolean r2 = r2.getHasLanguage()
            com.itextpdf.kernel.xmp.options.PropertyOptions r5 = r11.getOptions()
            boolean r5 = r5.getHasLanguage()
            if (r2 == r5) goto L_0x0039
            return r3
        L_0x0039:
            com.itextpdf.kernel.xmp.options.PropertyOptions r2 = r10.getOptions()
            boolean r2 = r2.getHasLanguage()
            if (r2 == 0) goto L_0x00c3
            com.itextpdf.kernel.xmp.impl.XMPNode r2 = r10.getQualifier(r4)
            java.lang.String r2 = r2.getValue()
            com.itextpdf.kernel.xmp.impl.XMPNode r5 = r11.getQualifier(r4)
            java.lang.String r5 = r5.getValue()
            boolean r2 = r2.equals(r5)
            if (r2 != 0) goto L_0x00c3
            return r3
        L_0x005a:
            boolean r2 = r0.isStruct()
            if (r2 == 0) goto L_0x008f
            int r2 = r10.getChildrenLength()
            int r5 = r11.getChildrenLength()
            if (r2 == r5) goto L_0x006b
            return r3
        L_0x006b:
            java.util.Iterator r2 = r10.iterateChildren()
        L_0x006f:
            boolean r5 = r2.hasNext()
            if (r5 == 0) goto L_0x008e
            java.lang.Object r5 = r2.next()
            com.itextpdf.kernel.xmp.impl.XMPNode r5 = (com.itextpdf.kernel.xmp.impl.XMPNode) r5
            java.lang.String r6 = r5.getName()
            com.itextpdf.kernel.xmp.impl.XMPNode r6 = com.itextpdf.kernel.xmp.impl.XMPNodeUtils.findChildNode(r11, r6, r3)
            if (r6 == 0) goto L_0x008d
            boolean r7 = itemValuesMatch(r5, r6)
            if (r7 != 0) goto L_0x008c
            goto L_0x008d
        L_0x008c:
            goto L_0x006f
        L_0x008d:
            return r3
        L_0x008e:
            goto L_0x00c3
        L_0x008f:
            boolean r2 = r0.isArray()
            if (r2 == 0) goto L_0x00c4
            java.util.Iterator r2 = r10.iterateChildren()
        L_0x0099:
            boolean r5 = r2.hasNext()
            if (r5 == 0) goto L_0x00c3
            java.lang.Object r5 = r2.next()
            com.itextpdf.kernel.xmp.impl.XMPNode r5 = (com.itextpdf.kernel.xmp.impl.XMPNode) r5
            r6 = 0
            java.util.Iterator r7 = r11.iterateChildren()
        L_0x00aa:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x00bf
            java.lang.Object r8 = r7.next()
            com.itextpdf.kernel.xmp.impl.XMPNode r8 = (com.itextpdf.kernel.xmp.impl.XMPNode) r8
            boolean r9 = itemValuesMatch(r5, r8)
            if (r9 == 0) goto L_0x00be
            r6 = 1
            goto L_0x00bf
        L_0x00be:
            goto L_0x00aa
        L_0x00bf:
            if (r6 != 0) goto L_0x00c2
            return r3
        L_0x00c2:
            goto L_0x0099
        L_0x00c3:
            return r4
        L_0x00c4:
            java.lang.AssertionError r2 = new java.lang.AssertionError
            r2.<init>()
            goto L_0x00cb
        L_0x00ca:
            throw r2
        L_0x00cb:
            goto L_0x00ca
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.xmp.impl.XMPUtilsImpl.itemValuesMatch(com.itextpdf.kernel.xmp.impl.XMPNode, com.itextpdf.kernel.xmp.impl.XMPNode):boolean");
    }

    private static void checkSeparator(String separator) throws XMPException {
        boolean haveSemicolon = false;
        for (int i = 0; i < separator.length(); i++) {
            int charKind = classifyCharacter(separator.charAt(i));
            if (charKind == 3) {
                if (!haveSemicolon) {
                    haveSemicolon = true;
                } else {
                    throw new XMPException("Separator can have only one semicolon", 4);
                }
            } else if (charKind != 1) {
                throw new XMPException("Separator can have only spaces and one semicolon", 4);
            }
        }
        if (!haveSemicolon) {
            throw new XMPException("Separator must have one semicolon", 4);
        }
    }

    private static char checkQuotes(String quotes, char openQuote) throws XMPException {
        char closeQuote;
        if (classifyCharacter(openQuote) == 4) {
            if (quotes.length() == 1) {
                closeQuote = openQuote;
            } else {
                char closeQuote2 = quotes.charAt(1);
                if (classifyCharacter(closeQuote2) == 4) {
                    closeQuote = closeQuote2;
                } else {
                    throw new XMPException("Invalid quoting character", 4);
                }
            }
            if (closeQuote == getClosingQuote(openQuote)) {
                return closeQuote;
            }
            throw new XMPException("Mismatched quote pair", 4);
        }
        throw new XMPException("Invalid quoting character", 4);
    }

    private static int classifyCharacter(char ch) {
        if (SPACES.indexOf(ch) >= 0) {
            return 1;
        }
        if (8192 <= ch && ch <= 8203) {
            return 1;
        }
        if (COMMAS.indexOf(ch) >= 0) {
            return 2;
        }
        if (SEMICOLA.indexOf(ch) >= 0) {
            return 3;
        }
        if (QUOTES.indexOf(ch) >= 0) {
            return 4;
        }
        if (12296 <= ch && ch <= 12303) {
            return 4;
        }
        if (8216 <= ch && ch <= 8223) {
            return 4;
        }
        if (ch < ' ' || CONTROLS.indexOf(ch) >= 0) {
            return 5;
        }
        return 0;
    }

    private static char getClosingQuote(char openQuote) {
        switch (openQuote) {
            case '\"':
                return Typography.quote;
            case CipherSuite.TLS_DHE_PSK_WITH_AES_256_GCM_SHA384:
                return 187;
            case CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA256:
                return 171;
            case 8213:
                return 8213;
            case 8216:
                return Typography.rightSingleQuote;
            case 8218:
                return 8219;
            case 8220:
                return Typography.rightDoubleQuote;
            case 8222:
                return 8223;
            case 8249:
                return 8250;
            case 8250:
                return 8249;
            case 12296:
                return 12297;
            case 12298:
                return 12299;
            case 12300:
                return 12301;
            case 12302:
                return 12303;
            case 12317:
                return 12319;
            default:
                return 0;
        }
    }

    private static String applyQuotes(String item, char openQuote, char closeQuote, boolean allowCommas) {
        if (item == null) {
            item = "";
        }
        boolean prevSpace = false;
        int i = 0;
        while (i < item.length()) {
            int charKind = classifyCharacter(item.charAt(i));
            if (i == 0 && charKind == 4) {
                break;
            }
            if (charKind != 1) {
                prevSpace = false;
                if (charKind != 3) {
                    if (charKind != 5) {
                        if (charKind == 2 && !allowCommas) {
                            break;
                        }
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            } else if (prevSpace) {
                break;
            } else {
                prevSpace = true;
            }
            i++;
        }
        if (i >= item.length()) {
            return item;
        }
        StringBuffer newItem = new StringBuffer(item.length() + 2);
        int splitPoint = 0;
        while (splitPoint <= i && classifyCharacter(item.charAt(i)) != 4) {
            splitPoint++;
        }
        newItem.append(openQuote).append(item.substring(0, splitPoint));
        for (int charOffset = splitPoint; charOffset < item.length(); charOffset++) {
            newItem.append(item.charAt(charOffset));
            if (classifyCharacter(item.charAt(charOffset)) == 4 && isSurroundingQuote(item.charAt(charOffset), openQuote, closeQuote)) {
                newItem.append(item.charAt(charOffset));
            }
        }
        newItem.append(closeQuote);
        return newItem.toString();
    }

    private static boolean isSurroundingQuote(char ch, char openQuote, char closeQuote) {
        return ch == openQuote || isClosingingQuote(ch, openQuote, closeQuote);
    }

    private static boolean isClosingingQuote(char ch, char openQuote, char closeQuote) {
        return ch == closeQuote || (openQuote == 12317 && ch == 12318) || ch == 12319;
    }
}
