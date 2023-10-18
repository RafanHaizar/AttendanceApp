package com.itextpdf.kernel.xmp.impl;

import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.kernel.xmp.XMPConst;
import com.itextpdf.kernel.xmp.XMPError;
import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.kernel.xmp.XMPMetaFactory;
import com.itextpdf.kernel.xmp.XMPSchemaRegistry;
import com.itextpdf.kernel.xmp.options.PropertyOptions;
import com.itextpdf.svg.SvgConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ParseRDF implements XMPError, XMPConst {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String DEFAULT_PREFIX = "_dflt";
    public static final int RDFTERM_ABOUT = 3;
    public static final int RDFTERM_ABOUT_EACH = 10;
    public static final int RDFTERM_ABOUT_EACH_PREFIX = 11;
    public static final int RDFTERM_BAG_ID = 12;
    public static final int RDFTERM_DATATYPE = 7;
    public static final int RDFTERM_DESCRIPTION = 8;
    public static final int RDFTERM_FIRST_CORE = 1;
    public static final int RDFTERM_FIRST_OLD = 10;
    public static final int RDFTERM_FIRST_SYNTAX = 1;
    public static final int RDFTERM_ID = 2;
    public static final int RDFTERM_LAST_CORE = 7;
    public static final int RDFTERM_LAST_OLD = 12;
    public static final int RDFTERM_LAST_SYNTAX = 9;
    public static final int RDFTERM_LI = 9;
    public static final int RDFTERM_NODE_ID = 6;
    public static final int RDFTERM_OTHER = 0;
    public static final int RDFTERM_PARSE_TYPE = 4;
    public static final int RDFTERM_RDF = 1;
    public static final int RDFTERM_RESOURCE = 5;

    static XMPMetaImpl parse(Node xmlRoot) throws XMPException {
        XMPMetaImpl xmp = new XMPMetaImpl();
        rdf_RDF(xmp, xmlRoot);
        return xmp;
    }

    static void rdf_RDF(XMPMetaImpl xmp, Node rdfRdfNode) throws XMPException {
        if (rdfRdfNode.hasAttributes()) {
            rdf_NodeElementList(xmp, xmp.getRoot(), rdfRdfNode);
            return;
        }
        throw new XMPException("Invalid attributes of rdf:RDF element", XMPError.BADRDF);
    }

    private static void rdf_NodeElementList(XMPMetaImpl xmp, XMPNode xmpParent, Node rdfRdfNode) throws XMPException {
        for (int i = 0; i < rdfRdfNode.getChildNodes().getLength(); i++) {
            Node child = rdfRdfNode.getChildNodes().item(i);
            if (!isWhitespaceNode(child)) {
                rdf_NodeElement(xmp, xmpParent, child, true);
            }
        }
    }

    private static void rdf_NodeElement(XMPMetaImpl xmp, XMPNode xmpParent, Node xmlNode, boolean isTopLevel) throws XMPException {
        int nodeTerm = getRDFTermKind(xmlNode);
        if (nodeTerm != 8 && nodeTerm != 0) {
            throw new XMPException("Node element must be rdf:Description or typed node", XMPError.BADRDF);
        } else if (!isTopLevel || nodeTerm != 0) {
            rdf_NodeElementAttrs(xmp, xmpParent, xmlNode, isTopLevel);
            rdf_PropertyElementList(xmp, xmpParent, xmlNode, isTopLevel);
        } else {
            throw new XMPException("Top level typed node not allowed", XMPError.BADXMP);
        }
    }

    private static void rdf_NodeElementAttrs(XMPMetaImpl xmp, XMPNode xmpParent, Node xmlNode, boolean isTopLevel) throws XMPException {
        int exclusiveAttrs = 0;
        for (int i = 0; i < xmlNode.getAttributes().getLength(); i++) {
            Node attribute = xmlNode.getAttributes().item(i);
            if (!SvgConstants.Attributes.XMLNS.equals(attribute.getPrefix()) && (attribute.getPrefix() != null || !SvgConstants.Attributes.XMLNS.equals(attribute.getNodeName()))) {
                int attrTerm = getRDFTermKind(attribute);
                switch (attrTerm) {
                    case 0:
                        addChildNode(xmp, xmpParent, attribute, attribute.getNodeValue(), isTopLevel);
                        break;
                    case 2:
                    case 3:
                    case 6:
                        if (exclusiveAttrs <= 0) {
                            exclusiveAttrs++;
                            if (isTopLevel && attrTerm == 3) {
                                if (xmpParent.getName() == null || xmpParent.getName().length() <= 0) {
                                    xmpParent.setName(attribute.getNodeValue());
                                    break;
                                } else if (xmpParent.getName().equals(attribute.getNodeValue())) {
                                    break;
                                } else {
                                    throw new XMPException("Mismatched top level rdf:about values", XMPError.BADXMP);
                                }
                            }
                        } else {
                            throw new XMPException("Mutally exclusive about, ID, nodeID attributes", XMPError.BADRDF);
                        }
                        break;
                    default:
                        throw new XMPException("Invalid nodeElement attribute", XMPError.BADRDF);
                }
            }
        }
    }

    private static void rdf_PropertyElementList(XMPMetaImpl xmp, XMPNode xmpParent, Node xmlParent, boolean isTopLevel) throws XMPException {
        for (int i = 0; i < xmlParent.getChildNodes().getLength(); i++) {
            Node currChild = xmlParent.getChildNodes().item(i);
            if (!isWhitespaceNode(currChild)) {
                if (currChild.getNodeType() == 1) {
                    rdf_PropertyElement(xmp, xmpParent, currChild, isTopLevel);
                } else {
                    throw new XMPException("Expected property element node not found", XMPError.BADRDF);
                }
            }
        }
    }

    private static void rdf_PropertyElement(XMPMetaImpl xmp, XMPNode xmpParent, Node xmlNode, boolean isTopLevel) throws XMPException {
        if (isPropertyElementName(getRDFTermKind(xmlNode))) {
            NamedNodeMap attributes = xmlNode.getAttributes();
            List<String> nsAttrs = null;
            for (int i = 0; i < attributes.getLength(); i++) {
                Node attribute = attributes.item(i);
                if (SvgConstants.Attributes.XMLNS.equals(attribute.getPrefix()) || (attribute.getPrefix() == null && SvgConstants.Attributes.XMLNS.equals(attribute.getNodeName()))) {
                    if (nsAttrs == null) {
                        nsAttrs = new ArrayList<>();
                    }
                    nsAttrs.add(attribute.getNodeName());
                }
            }
            if (nsAttrs != null) {
                for (String ns : nsAttrs) {
                    attributes.removeNamedItem(ns);
                }
            }
            if (attributes.getLength() > 3) {
                rdf_EmptyPropertyElement(xmp, xmpParent, xmlNode, isTopLevel);
                return;
            }
            int i2 = 0;
            while (i2 < attributes.getLength()) {
                Node attribute2 = attributes.item(i2);
                String attrLocal = attribute2.getLocalName();
                String attrNS = attribute2.getNamespaceURI();
                String attrValue = attribute2.getNodeValue();
                if (XMPConst.XML_LANG.equals(attribute2.getNodeName()) && (!"ID".equals(attrLocal) || !XMPConst.NS_RDF.equals(attrNS))) {
                    i2++;
                } else if ("datatype".equals(attrLocal) && XMPConst.NS_RDF.equals(attrNS)) {
                    rdf_LiteralPropertyElement(xmp, xmpParent, xmlNode, isTopLevel);
                    return;
                } else if (!"parseType".equals(attrLocal) || !XMPConst.NS_RDF.equals(attrNS)) {
                    rdf_EmptyPropertyElement(xmp, xmpParent, xmlNode, isTopLevel);
                    return;
                } else if ("Literal".equals(attrValue)) {
                    rdf_ParseTypeLiteralPropertyElement();
                    return;
                } else if ("Resource".equals(attrValue)) {
                    rdf_ParseTypeResourcePropertyElement(xmp, xmpParent, xmlNode, isTopLevel);
                    return;
                } else if ("Collection".equals(attrValue)) {
                    rdf_ParseTypeCollectionPropertyElement();
                    return;
                } else {
                    rdf_ParseTypeOtherPropertyElement();
                    return;
                }
            }
            if (xmlNode.hasChildNodes() != 0) {
                for (int i3 = 0; i3 < xmlNode.getChildNodes().getLength(); i3++) {
                    if (xmlNode.getChildNodes().item(i3).getNodeType() != 3) {
                        rdf_ResourcePropertyElement(xmp, xmpParent, xmlNode, isTopLevel);
                        return;
                    }
                }
                rdf_LiteralPropertyElement(xmp, xmpParent, xmlNode, isTopLevel);
                return;
            }
            rdf_EmptyPropertyElement(xmp, xmpParent, xmlNode, isTopLevel);
            return;
        }
        throw new XMPException("Invalid property element name", XMPError.BADRDF);
    }

    private static void rdf_ResourcePropertyElement(XMPMetaImpl xmp, XMPNode xmpParent, Node xmlNode, boolean isTopLevel) throws XMPException {
        if (!isTopLevel || !"iX:changes".equals(xmlNode.getNodeName())) {
            XMPNode newCompound = addChildNode(xmp, xmpParent, xmlNode, "", isTopLevel);
            for (int i = 0; i < xmlNode.getAttributes().getLength(); i++) {
                Node attribute = xmlNode.getAttributes().item(i);
                if (!SvgConstants.Attributes.XMLNS.equals(attribute.getPrefix()) && (attribute.getPrefix() != null || !SvgConstants.Attributes.XMLNS.equals(attribute.getNodeName()))) {
                    String attrLocal = attribute.getLocalName();
                    String attrNS = attribute.getNamespaceURI();
                    if (XMPConst.XML_LANG.equals(attribute.getNodeName())) {
                        addQualifierNode(newCompound, XMPConst.XML_LANG, attribute.getNodeValue());
                    } else if (!"ID".equals(attrLocal) || !XMPConst.NS_RDF.equals(attrNS)) {
                        throw new XMPException("Invalid attribute for resource property element", XMPError.BADRDF);
                    }
                }
            }
            boolean found = false;
            for (int i2 = 0; i2 < xmlNode.getChildNodes().getLength(); i2++) {
                Node currChild = xmlNode.getChildNodes().item(i2);
                if (!isWhitespaceNode(currChild)) {
                    if (currChild.getNodeType() == 1 && !found) {
                        boolean isRDF = XMPConst.NS_RDF.equals(currChild.getNamespaceURI());
                        String childLocal = currChild.getLocalName();
                        if (isRDF && "Bag".equals(childLocal)) {
                            newCompound.getOptions().setArray(true);
                        } else if (isRDF && "Seq".equals(childLocal)) {
                            newCompound.getOptions().setArray(true).setArrayOrdered(true);
                        } else if (!isRDF || !"Alt".equals(childLocal)) {
                            newCompound.getOptions().setStruct(true);
                            if (!isRDF && !"Description".equals(childLocal)) {
                                String typeName = currChild.getNamespaceURI();
                                if (typeName != null) {
                                    addQualifierNode(newCompound, XMPConst.RDF_TYPE, typeName + ':' + childLocal);
                                } else {
                                    throw new XMPException("All XML elements must be in a namespace", XMPError.BADXMP);
                                }
                            }
                        } else {
                            newCompound.getOptions().setArray(true).setArrayOrdered(true).setArrayAlternate(true);
                        }
                        rdf_NodeElement(xmp, newCompound, currChild, false);
                        if (newCompound.getHasValueChild()) {
                            fixupQualifiedNode(newCompound);
                        } else if (newCompound.getOptions().isArrayAlternate()) {
                            XMPNodeUtils.detectAltText(newCompound);
                        }
                        found = true;
                    } else if (found) {
                        throw new XMPException("Invalid child of resource property element", XMPError.BADRDF);
                    } else {
                        throw new XMPException("Children of resource property element must be XML elements", XMPError.BADRDF);
                    }
                }
            }
            if (!found) {
                throw new XMPException("Missing child of resource property element", XMPError.BADRDF);
            }
        }
    }

    private static void rdf_LiteralPropertyElement(XMPMetaImpl xmp, XMPNode xmpParent, Node xmlNode, boolean isTopLevel) throws XMPException {
        XMPNode newChild = addChildNode(xmp, xmpParent, xmlNode, (String) null, isTopLevel);
        for (int i = 0; i < xmlNode.getAttributes().getLength(); i++) {
            Node attribute = xmlNode.getAttributes().item(i);
            if (!SvgConstants.Attributes.XMLNS.equals(attribute.getPrefix()) && (attribute.getPrefix() != null || !SvgConstants.Attributes.XMLNS.equals(attribute.getNodeName()))) {
                String attrNS = attribute.getNamespaceURI();
                String attrLocal = attribute.getLocalName();
                if (XMPConst.XML_LANG.equals(attribute.getNodeName())) {
                    addQualifierNode(newChild, XMPConst.XML_LANG, attribute.getNodeValue());
                } else if (!XMPConst.NS_RDF.equals(attrNS) || (!"ID".equals(attrLocal) && !"datatype".equals(attrLocal))) {
                    throw new XMPException("Invalid attribute for literal property element", XMPError.BADRDF);
                }
            }
        }
        String textValue = "";
        int i2 = 0;
        while (i2 < xmlNode.getChildNodes().getLength()) {
            Node child = xmlNode.getChildNodes().item(i2);
            if (child.getNodeType() == 3) {
                textValue = textValue + child.getNodeValue();
                i2++;
            } else {
                throw new XMPException("Invalid child of literal property element", XMPError.BADRDF);
            }
        }
        newChild.setValue(textValue);
    }

    private static void rdf_ParseTypeLiteralPropertyElement() throws XMPException {
        throw new XMPException("ParseTypeLiteral property element not allowed", XMPError.BADXMP);
    }

    private static void rdf_ParseTypeResourcePropertyElement(XMPMetaImpl xmp, XMPNode xmpParent, Node xmlNode, boolean isTopLevel) throws XMPException {
        XMPNode newStruct = addChildNode(xmp, xmpParent, xmlNode, "", isTopLevel);
        newStruct.getOptions().setStruct(true);
        for (int i = 0; i < xmlNode.getAttributes().getLength(); i++) {
            Node attribute = xmlNode.getAttributes().item(i);
            if (!SvgConstants.Attributes.XMLNS.equals(attribute.getPrefix()) && (attribute.getPrefix() != null || !SvgConstants.Attributes.XMLNS.equals(attribute.getNodeName()))) {
                String attrLocal = attribute.getLocalName();
                String attrNS = attribute.getNamespaceURI();
                if (XMPConst.XML_LANG.equals(attribute.getNodeName())) {
                    addQualifierNode(newStruct, XMPConst.XML_LANG, attribute.getNodeValue());
                } else if (!XMPConst.NS_RDF.equals(attrNS) || (!"ID".equals(attrLocal) && !"parseType".equals(attrLocal))) {
                    throw new XMPException("Invalid attribute for ParseTypeResource property element", XMPError.BADRDF);
                }
            }
        }
        rdf_PropertyElementList(xmp, newStruct, xmlNode, false);
        if (newStruct.getHasValueChild()) {
            fixupQualifiedNode(newStruct);
        }
    }

    private static void rdf_ParseTypeCollectionPropertyElement() throws XMPException {
        throw new XMPException("ParseTypeCollection property element not allowed", XMPError.BADXMP);
    }

    private static void rdf_ParseTypeOtherPropertyElement() throws XMPException {
        throw new XMPException("ParseTypeOther property element not allowed", XMPError.BADXMP);
    }

    private static void rdf_EmptyPropertyElement(XMPMetaImpl xmp, XMPNode xmpParent, Node xmlNode, boolean isTopLevel) throws XMPException {
        boolean hasResourceAttr;
        boolean hasPropertyAttrs;
        XMPMetaImpl xMPMetaImpl = xmp;
        boolean hasPropertyAttrs2 = false;
        boolean hasResourceAttr2 = false;
        boolean hasNodeIDAttr = false;
        boolean hasValueAttr = false;
        Node valueNode = null;
        if (!xmlNode.hasChildNodes()) {
            for (int i = 0; i < xmlNode.getAttributes().getLength(); i++) {
                Node attribute = xmlNode.getAttributes().item(i);
                if (!SvgConstants.Attributes.XMLNS.equals(attribute.getPrefix()) && (attribute.getPrefix() != null || !SvgConstants.Attributes.XMLNS.equals(attribute.getNodeName()))) {
                    switch (getRDFTermKind(attribute)) {
                        case 0:
                            if (!XfdfConstants.VALUE.equals(attribute.getLocalName()) || !XMPConst.NS_RDF.equals(attribute.getNamespaceURI())) {
                                if (XMPConst.XML_LANG.equals(attribute.getNodeName())) {
                                    break;
                                } else {
                                    hasPropertyAttrs2 = true;
                                    break;
                                }
                            } else if (!hasResourceAttr2) {
                                hasValueAttr = true;
                                valueNode = attribute;
                                break;
                            } else {
                                throw new XMPException("Empty property element can't have both rdf:value and rdf:resource", XMPError.BADXMP);
                            }
                            break;
                        case 2:
                            break;
                        case 5:
                            if (hasNodeIDAttr) {
                                throw new XMPException("Empty property element can't have both rdf:resource and rdf:nodeID", XMPError.BADRDF);
                            } else if (!hasValueAttr) {
                                hasResourceAttr2 = true;
                                if (hasValueAttr) {
                                    break;
                                } else {
                                    valueNode = attribute;
                                    break;
                                }
                            } else {
                                throw new XMPException("Empty property element can't have both rdf:value and rdf:resource", XMPError.BADXMP);
                            }
                        case 6:
                            if (!hasResourceAttr2) {
                                hasNodeIDAttr = true;
                                break;
                            } else {
                                throw new XMPException("Empty property element can't have both rdf:resource and rdf:nodeID", XMPError.BADRDF);
                            }
                        default:
                            throw new XMPException("Unrecognized attribute of empty property element", XMPError.BADRDF);
                    }
                }
            }
            String str = "";
            XMPNode childNode = addChildNode(xMPMetaImpl, xmpParent, xmlNode, str, isTopLevel);
            boolean childIsStruct = false;
            if (hasValueAttr || hasResourceAttr2) {
                if (valueNode != null) {
                    str = valueNode.getNodeValue();
                }
                childNode.setValue(str);
                if (!hasValueAttr) {
                    childNode.getOptions().setURI(true);
                }
            } else if (hasPropertyAttrs2) {
                childNode.getOptions().setStruct(true);
                childIsStruct = true;
            }
            int i2 = 0;
            while (i2 < xmlNode.getAttributes().getLength()) {
                Node attribute2 = xmlNode.getAttributes().item(i2);
                if (attribute2 != valueNode) {
                    hasPropertyAttrs = hasPropertyAttrs2;
                    if (SvgConstants.Attributes.XMLNS.equals(attribute2.getPrefix())) {
                        hasResourceAttr = hasResourceAttr2;
                    } else if (attribute2.getPrefix() != null || !SvgConstants.Attributes.XMLNS.equals(attribute2.getNodeName())) {
                        int attrTerm = getRDFTermKind(attribute2);
                        switch (attrTerm) {
                            case 0:
                                hasResourceAttr = hasResourceAttr2;
                                if (childIsStruct) {
                                    if (!XMPConst.XML_LANG.equals(attribute2.getNodeName())) {
                                        addChildNode(xMPMetaImpl, childNode, attribute2, attribute2.getNodeValue(), false);
                                        break;
                                    } else {
                                        addQualifierNode(childNode, XMPConst.XML_LANG, attribute2.getNodeValue());
                                        break;
                                    }
                                } else {
                                    addQualifierNode(childNode, attribute2.getNodeName(), attribute2.getNodeValue());
                                    break;
                                }
                            case 2:
                            case 6:
                                hasResourceAttr = hasResourceAttr2;
                                break;
                            case 5:
                                hasResourceAttr = hasResourceAttr2;
                                addQualifierNode(childNode, "rdf:resource", attribute2.getNodeValue());
                                break;
                            default:
                                int i3 = attrTerm;
                                boolean z = hasResourceAttr2;
                                throw new XMPException("Unrecognized attribute of empty property element", XMPError.BADRDF);
                        }
                    } else {
                        hasResourceAttr = hasResourceAttr2;
                    }
                } else {
                    hasPropertyAttrs = hasPropertyAttrs2;
                    hasResourceAttr = hasResourceAttr2;
                }
                i2++;
                hasPropertyAttrs2 = hasPropertyAttrs;
                hasResourceAttr2 = hasResourceAttr;
            }
            return;
        }
        XMPNode xMPNode = xmpParent;
        Node node = xmlNode;
        boolean z2 = isTopLevel;
        throw new XMPException("Nested content not allowed with rdf:resource or property attributes", XMPError.BADRDF);
    }

    private static XMPNode addChildNode(XMPMetaImpl xmp, XMPNode xmpParent, Node xmlNode, String value, boolean isTopLevel) throws XMPException {
        XMPSchemaRegistry registry = XMPMetaFactory.getSchemaRegistry();
        String namespace = xmlNode.getNamespaceURI();
        if (namespace != null) {
            if (XMPConst.NS_DC_DEPRECATED.equals(namespace)) {
                namespace = XMPConst.NS_DC;
            }
            String prefix = registry.getNamespacePrefix(namespace);
            if (prefix == null) {
                prefix = registry.registerNamespace(namespace, xmlNode.getPrefix() != null ? xmlNode.getPrefix() : DEFAULT_PREFIX);
            }
            String prefix2 = prefix + xmlNode.getLocalName();
            PropertyOptions childOptions = new PropertyOptions();
            boolean isAlias = false;
            if (isTopLevel) {
                XMPNode schemaNode = XMPNodeUtils.findSchemaNode(xmp.getRoot(), namespace, DEFAULT_PREFIX, true);
                schemaNode.setImplicit(false);
                xmpParent = schemaNode;
                if (registry.findAlias(prefix2) != null) {
                    isAlias = true;
                    xmp.getRoot().setHasAliases(true);
                    schemaNode.setHasAliases(true);
                }
            }
            boolean isArrayItem = "rdf:li".equals(prefix2);
            boolean isValueNode = "rdf:value".equals(prefix2);
            XMPNode newChild = new XMPNode(prefix2, value, childOptions);
            newChild.setAlias(isAlias);
            if (!isValueNode) {
                xmpParent.addChild(newChild);
            } else {
                xmpParent.addChild(1, newChild);
            }
            if (isValueNode) {
                if (isTopLevel || !xmpParent.getOptions().isStruct()) {
                    throw new XMPException("Misplaced rdf:value element", XMPError.BADRDF);
                }
                xmpParent.setHasValueChild(true);
            }
            if (isArrayItem) {
                if (xmpParent.getOptions().isArray()) {
                    newChild.setName(XMPConst.ARRAY_ITEM_NAME);
                } else {
                    throw new XMPException("Misplaced rdf:li element", XMPError.BADRDF);
                }
            }
            return newChild;
        }
        throw new XMPException("XML namespace required for all elements and attributes", XMPError.BADRDF);
    }

    private static XMPNode addQualifierNode(XMPNode xmpParent, String name, String value) throws XMPException {
        XMPNode newQual = new XMPNode(name, XMPConst.XML_LANG.equals(name) ? Utils.normalizeLangValue(value) : value, (PropertyOptions) null);
        xmpParent.addQualifier(newQual);
        return newQual;
    }

    private static void fixupQualifiedNode(XMPNode xmpParent) throws XMPException {
        if (!xmpParent.getOptions().isStruct() || !xmpParent.hasChildren()) {
            throw new AssertionError();
        }
        XMPNode valueNode = xmpParent.getChild(1);
        if ("rdf:value".equals(valueNode.getName())) {
            if (valueNode.getOptions().getHasLanguage()) {
                if (!xmpParent.getOptions().getHasLanguage()) {
                    XMPNode langQual = valueNode.getQualifier(1);
                    valueNode.removeQualifier(langQual);
                    xmpParent.addQualifier(langQual);
                } else {
                    throw new XMPException("Redundant xml:lang for rdf:value element", XMPError.BADXMP);
                }
            }
            for (int i = 1; i <= valueNode.getQualifierLength(); i++) {
                xmpParent.addQualifier(valueNode.getQualifier(i));
            }
            for (int i2 = 2; i2 <= xmpParent.getChildrenLength(); i2++) {
                xmpParent.addQualifier(xmpParent.getChild(i2));
            }
            if (xmpParent.getOptions().isStruct() || xmpParent.getHasValueChild()) {
                xmpParent.setHasValueChild(false);
                xmpParent.getOptions().setStruct(false);
                xmpParent.getOptions().mergeWith(valueNode.getOptions());
                xmpParent.setValue(valueNode.getValue());
                xmpParent.removeChildren();
                Iterator it = valueNode.iterateChildren();
                while (it.hasNext()) {
                    xmpParent.addChild((XMPNode) it.next());
                }
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    private static boolean isWhitespaceNode(Node node) {
        if (node.getNodeType() != 3) {
            return false;
        }
        String value = node.getNodeValue();
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isWhitespace(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isPropertyElementName(int term) {
        if (term == 8 || isOldTerm(term)) {
            return false;
        }
        return !isCoreSyntaxTerm(term);
    }

    private static boolean isOldTerm(int term) {
        return 10 <= term && term <= 12;
    }

    private static boolean isCoreSyntaxTerm(int term) {
        return 1 <= term && term <= 7;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int getRDFTermKind(org.w3c.dom.Node r18) {
        /*
            r0 = r18
            java.lang.String r1 = r18.getLocalName()
            java.lang.String r2 = r18.getNamespaceURI()
            java.lang.String r3 = "ID"
            java.lang.String r4 = "about"
            java.lang.String r5 = "http://www.w3.org/1999/02/22-rdf-syntax-ns#"
            if (r2 != 0) goto L_0x0035
            boolean r6 = r4.equals(r1)
            if (r6 != 0) goto L_0x001e
            boolean r6 = r3.equals(r1)
            if (r6 == 0) goto L_0x0035
        L_0x001e:
            boolean r6 = r0 instanceof org.w3c.dom.Attr
            if (r6 == 0) goto L_0x0035
            r6 = r0
            org.w3c.dom.Attr r6 = (org.w3c.dom.Attr) r6
            org.w3c.dom.Element r6 = r6.getOwnerElement()
            java.lang.String r6 = r6.getNamespaceURI()
            boolean r6 = r5.equals(r6)
            if (r6 == 0) goto L_0x0035
            java.lang.String r2 = "http://www.w3.org/1999/02/22-rdf-syntax-ns#"
        L_0x0035:
            boolean r5 = r5.equals(r2)
            r6 = 0
            if (r5 == 0) goto L_0x00e5
            int r5 = r1.hashCode()
            r7 = 11
            r8 = 10
            r9 = 7
            r10 = 6
            r11 = 2
            r12 = 1
            r13 = 5
            r14 = 3
            r15 = 8
            r16 = 4
            r17 = 9
            switch(r5) {
                case -1833071475: goto L_0x00c7;
                case -1340118226: goto L_0x00bc;
                case -1040171363: goto L_0x00b1;
                case -341064690: goto L_0x00a6;
                case -56677412: goto L_0x009c;
                case 2331: goto L_0x0094;
                case 3453: goto L_0x008a;
                case 80980: goto L_0x0080;
                case 92611469: goto L_0x0078;
                case 93496099: goto L_0x006d;
                case 670789472: goto L_0x0061;
                case 1790024164: goto L_0x0055;
                default: goto L_0x0053;
            }
        L_0x0053:
            goto L_0x00d2
        L_0x0055:
            java.lang.String r3 = "datatype"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0053
            r3 = 8
            goto L_0x00d3
        L_0x0061:
            java.lang.String r3 = "aboutEachPrefix"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0053
            r3 = 10
            goto L_0x00d3
        L_0x006d:
            java.lang.String r3 = "bagID"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0053
            r3 = 11
            goto L_0x00d3
        L_0x0078:
            boolean r3 = r1.equals(r4)
            if (r3 == 0) goto L_0x0053
            r3 = 3
            goto L_0x00d3
        L_0x0080:
            java.lang.String r3 = "RDF"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0053
            r3 = 5
            goto L_0x00d3
        L_0x008a:
            java.lang.String r3 = "li"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0053
            r3 = 0
            goto L_0x00d3
        L_0x0094:
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0053
            r3 = 6
            goto L_0x00d3
        L_0x009c:
            java.lang.String r3 = "Description"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0053
            r3 = 2
            goto L_0x00d3
        L_0x00a6:
            java.lang.String r3 = "resource"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0053
            r3 = 4
            goto L_0x00d3
        L_0x00b1:
            java.lang.String r3 = "nodeID"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0053
            r3 = 7
            goto L_0x00d3
        L_0x00bc:
            java.lang.String r3 = "aboutEach"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0053
            r3 = 9
            goto L_0x00d3
        L_0x00c7:
            java.lang.String r3 = "parseType"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0053
            r3 = 1
            goto L_0x00d3
        L_0x00d2:
            r3 = -1
        L_0x00d3:
            switch(r3) {
                case 0: goto L_0x00e4;
                case 1: goto L_0x00e3;
                case 2: goto L_0x00e2;
                case 3: goto L_0x00e1;
                case 4: goto L_0x00e0;
                case 5: goto L_0x00df;
                case 6: goto L_0x00de;
                case 7: goto L_0x00dd;
                case 8: goto L_0x00dc;
                case 9: goto L_0x00db;
                case 10: goto L_0x00da;
                case 11: goto L_0x00d7;
                default: goto L_0x00d6;
            }
        L_0x00d6:
            goto L_0x00e5
        L_0x00d7:
            r3 = 12
            return r3
        L_0x00da:
            return r7
        L_0x00db:
            return r8
        L_0x00dc:
            return r9
        L_0x00dd:
            return r10
        L_0x00de:
            return r11
        L_0x00df:
            return r12
        L_0x00e0:
            return r13
        L_0x00e1:
            return r14
        L_0x00e2:
            return r15
        L_0x00e3:
            return r16
        L_0x00e4:
            return r17
        L_0x00e5:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.xmp.impl.ParseRDF.getRDFTermKind(org.w3c.dom.Node):int");
    }
}
