package com.itextpdf.kernel.xmp.impl;

import com.itextpdf.kernel.xmp.XMPConst;
import com.itextpdf.kernel.xmp.XMPError;
import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.kernel.xmp.options.PropertyOptions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.text.Typography;

class XMPNode implements Comparable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private boolean alias;
    private List children;
    private boolean hasAliases;
    private boolean hasValueChild;
    private boolean implicit;
    private String name;
    private PropertyOptions options;
    private XMPNode parent;
    private List qualifier;
    private String value;

    public XMPNode(String name2, String value2, PropertyOptions options2) {
        this.children = null;
        this.qualifier = null;
        this.options = null;
        this.name = name2;
        this.value = value2;
        this.options = options2;
    }

    public XMPNode(String name2, PropertyOptions options2) {
        this(name2, (String) null, options2);
    }

    public void clear() {
        this.options = null;
        this.name = null;
        this.value = null;
        this.children = null;
        this.qualifier = null;
    }

    public XMPNode getParent() {
        return this.parent;
    }

    public XMPNode getChild(int index) {
        return (XMPNode) getChildren().get(index - 1);
    }

    public void addChild(XMPNode node) throws XMPException {
        assertChildNotExisting(node.getName());
        node.setParent(this);
        getChildren().add(node);
    }

    public void addChild(int index, XMPNode node) throws XMPException {
        assertChildNotExisting(node.getName());
        node.setParent(this);
        getChildren().add(index - 1, node);
    }

    public void replaceChild(int index, XMPNode node) {
        node.setParent(this);
        getChildren().set(index - 1, node);
    }

    public void removeChild(int itemIndex) {
        getChildren().remove(itemIndex - 1);
        cleanupChildren();
    }

    public void removeChild(XMPNode node) {
        getChildren().remove(node);
        cleanupChildren();
    }

    /* access modifiers changed from: protected */
    public void cleanupChildren() {
        if (this.children.size() == 0) {
            this.children = null;
        }
    }

    public void removeChildren() {
        this.children = null;
    }

    public int getChildrenLength() {
        List list = this.children;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public XMPNode findChildByName(String expr) {
        return find(getChildren(), expr);
    }

    public XMPNode getQualifier(int index) {
        return (XMPNode) getQualifier().get(index - 1);
    }

    public int getQualifierLength() {
        List list = this.qualifier;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void addQualifier(XMPNode qualNode) throws XMPException {
        assertQualifierNotExisting(qualNode.getName());
        qualNode.setParent(this);
        qualNode.getOptions().setQualifier(true);
        getOptions().setHasQualifiers(true);
        if (qualNode.isLanguageNode()) {
            this.options.setHasLanguage(true);
            getQualifier().add(0, qualNode);
        } else if (qualNode.isTypeNode()) {
            this.options.setHasType(true);
            getQualifier().add(this.options.getHasLanguage() ? 1 : 0, qualNode);
        } else {
            getQualifier().add(qualNode);
        }
    }

    public void removeQualifier(XMPNode qualNode) {
        PropertyOptions opts = getOptions();
        if (qualNode.isLanguageNode()) {
            opts.setHasLanguage(false);
        } else if (qualNode.isTypeNode()) {
            opts.setHasType(false);
        }
        getQualifier().remove(qualNode);
        if (this.qualifier.size() == 0) {
            opts.setHasQualifiers(false);
            this.qualifier = null;
        }
    }

    public void removeQualifiers() {
        PropertyOptions opts = getOptions();
        opts.setHasQualifiers(false);
        opts.setHasLanguage(false);
        opts.setHasType(false);
        this.qualifier = null;
    }

    public XMPNode findQualifierByName(String expr) {
        return find(this.qualifier, expr);
    }

    public boolean hasChildren() {
        List list = this.children;
        return list != null && list.size() > 0;
    }

    public Iterator iterateChildren() {
        if (this.children != null) {
            return getChildren().iterator();
        }
        return Collections.emptyIterator();
    }

    public boolean hasQualifier() {
        List list = this.qualifier;
        return list != null && list.size() > 0;
    }

    public Iterator iterateQualifier() {
        if (this.qualifier == null) {
            return Collections.emptyIterator();
        }
        final Iterator it = getQualifier().iterator();
        return new Iterator() {
            public boolean hasNext() {
                return it.hasNext();
            }

            public Object next() {
                return it.next();
            }

            public void remove() {
                throw new UnsupportedOperationException("remove() is not allowed due to the internal contraints");
            }
        };
    }

    public Object clone() {
        PropertyOptions newOptions;
        try {
            newOptions = new PropertyOptions(getOptions().getOptions());
        } catch (XMPException e) {
            newOptions = new PropertyOptions();
        }
        XMPNode newNode = new XMPNode(this.name, this.value, newOptions);
        cloneSubtree(newNode);
        return newNode;
    }

    public void cloneSubtree(XMPNode destination) {
        try {
            Iterator it = iterateChildren();
            while (it.hasNext()) {
                destination.addChild((XMPNode) ((XMPNode) it.next()).clone());
            }
            Iterator it2 = iterateQualifier();
            while (it2.hasNext()) {
                destination.addQualifier((XMPNode) ((XMPNode) it2.next()).clone());
            }
        } catch (XMPException e) {
            throw new AssertionError();
        }
    }

    public String dumpNode(boolean recursive) {
        StringBuffer result = new StringBuffer(512);
        dumpNode(result, recursive, 0, 0);
        return result.toString();
    }

    public int compareTo(Object xmpNode) {
        if (getOptions().isSchemaNode()) {
            return this.value.compareTo(((XMPNode) xmpNode).getValue());
        }
        return this.name.compareTo(((XMPNode) xmpNode).getName());
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value2) {
        this.value = value2;
    }

    public PropertyOptions getOptions() {
        if (this.options == null) {
            this.options = new PropertyOptions();
        }
        return this.options;
    }

    public void setOptions(PropertyOptions options2) {
        this.options = options2;
    }

    public boolean isImplicit() {
        return this.implicit;
    }

    public void setImplicit(boolean implicit2) {
        this.implicit = implicit2;
    }

    public boolean getHasAliases() {
        return this.hasAliases;
    }

    public void setHasAliases(boolean hasAliases2) {
        this.hasAliases = hasAliases2;
    }

    public boolean isAlias() {
        return this.alias;
    }

    public void setAlias(boolean alias2) {
        this.alias = alias2;
    }

    public boolean getHasValueChild() {
        return this.hasValueChild;
    }

    public void setHasValueChild(boolean hasValueChild2) {
        this.hasValueChild = hasValueChild2;
    }

    public void sort() {
        if (hasQualifier()) {
            XMPNode[] quals = (XMPNode[]) getQualifier().toArray(new XMPNode[getQualifierLength()]);
            int sortFrom = 0;
            while (quals.length > sortFrom && (XMPConst.XML_LANG.equals(quals[sortFrom].getName()) || XMPConst.RDF_TYPE.equals(quals[sortFrom].getName()))) {
                quals[sortFrom].sort();
                sortFrom++;
            }
            Arrays.sort(quals, sortFrom, quals.length);
            ListIterator it = this.qualifier.listIterator();
            for (int j = 0; j < quals.length; j++) {
                it.next();
                it.set(quals[j]);
                quals[j].sort();
            }
        }
        if (hasChildren()) {
            if (!getOptions().isArray()) {
                Collections.sort(this.children);
            }
            Iterator it2 = iterateChildren();
            while (it2.hasNext()) {
                ((XMPNode) it2.next()).sort();
            }
        }
    }

    private void dumpNode(StringBuffer result, boolean recursive, int indent, int index) {
        for (int i = 0; i < indent; i++) {
            result.append(9);
        }
        if (this.parent == null) {
            result.append("ROOT NODE");
            String str = this.name;
            if (str != null && str.length() > 0) {
                result.append(" (");
                result.append(this.name);
                result.append(')');
            }
        } else if (getOptions().isQualifier()) {
            result.append('?');
            result.append(this.name);
        } else if (getParent().getOptions().isArray()) {
            result.append('[');
            result.append(index);
            result.append(']');
        } else {
            result.append(this.name);
        }
        String str2 = this.value;
        if (str2 != null && str2.length() > 0) {
            result.append(" = \"");
            result.append(this.value);
            result.append(Typography.quote);
        }
        if (getOptions().containsOneOf(-1)) {
            result.append("\t(");
            result.append(getOptions().toString());
            result.append(" : ");
            result.append(getOptions().getOptionsString());
            result.append(')');
        }
        result.append(10);
        if (recursive && hasQualifier()) {
            XMPNode[] quals = (XMPNode[]) getQualifier().toArray(new XMPNode[getQualifierLength()]);
            int i2 = 0;
            while (quals.length > i2 && (XMPConst.XML_LANG.equals(quals[i2].getName()) || XMPConst.RDF_TYPE.equals(quals[i2].getName()))) {
                i2++;
            }
            Arrays.sort(quals, i2, quals.length);
            for (int i3 = 0; i3 < quals.length; i3++) {
                quals[i3].dumpNode(result, recursive, indent + 2, i3 + 1);
            }
        }
        if (recursive && hasChildren()) {
            XMPNode[] children2 = (XMPNode[]) getChildren().toArray(new XMPNode[getChildrenLength()]);
            if (!getOptions().isArray()) {
                Arrays.sort(children2);
            }
            for (int i4 = 0; i4 < children2.length; i4++) {
                children2[i4].dumpNode(result, recursive, indent + 1, i4 + 1);
            }
        }
    }

    private boolean isLanguageNode() {
        return XMPConst.XML_LANG.equals(this.name);
    }

    private boolean isTypeNode() {
        return XMPConst.RDF_TYPE.equals(this.name);
    }

    /* access modifiers changed from: protected */
    public List getChildren() {
        if (this.children == null) {
            this.children = new ArrayList(0);
        }
        return this.children;
    }

    public List getUnmodifiableChildren() {
        return Collections.unmodifiableList(new ArrayList(getChildren()));
    }

    private List getQualifier() {
        if (this.qualifier == null) {
            this.qualifier = new ArrayList(0);
        }
        return this.qualifier;
    }

    /* access modifiers changed from: protected */
    public void setParent(XMPNode parent2) {
        this.parent = parent2;
    }

    private XMPNode find(List list, String expr) {
        if (list == null) {
            return null;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            XMPNode child = (XMPNode) it.next();
            if (child.getName().equals(expr)) {
                return child;
            }
        }
        return null;
    }

    private void assertChildNotExisting(String childName) throws XMPException {
        if (!XMPConst.ARRAY_ITEM_NAME.equals(childName) && findChildByName(childName) != null) {
            throw new XMPException("Duplicate property or field node '" + childName + "'", XMPError.BADXMP);
        }
    }

    private void assertQualifierNotExisting(String qualifierName) throws XMPException {
        if (!XMPConst.ARRAY_ITEM_NAME.equals(qualifierName) && findQualifierByName(qualifierName) != null) {
            throw new XMPException("Duplicate '" + qualifierName + "' qualifier", XMPError.BADXMP);
        }
    }
}
