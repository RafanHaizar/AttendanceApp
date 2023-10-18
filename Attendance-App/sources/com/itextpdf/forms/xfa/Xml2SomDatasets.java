package com.itextpdf.forms.xfa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

class Xml2SomDatasets extends Xml2Som {
    public Xml2SomDatasets(Node n) {
        this.order = new ArrayList();
        this.name2Node = new HashMap();
        this.stack = new Stack();
        this.anform = 0;
        this.inverseSearch = new HashMap();
        processDatasetsInternal(n);
    }

    public Node insertNode(Node n, String shortName) {
        Stack<String> localStack = splitParts(shortName);
        Document doc = n.getOwnerDocument();
        Node n2 = null;
        Node n3 = n.getFirstChild();
        while (n3.getNodeType() != 1) {
            n3 = n3.getNextSibling();
        }
        for (int k = 0; k < localStack.size(); k++) {
            String part = (String) localStack.get(k);
            int idx = part.lastIndexOf(91);
            String name = part.substring(0, idx);
            int idx2 = Integer.parseInt(part.substring(idx + 1, part.length() - 1));
            int found = -1;
            n2 = n3.getFirstChild();
            while (n2 != null && (n2.getNodeType() != 1 || !escapeSom(n2.getLocalName()).equals(name) || (found = found + 1) != idx2)) {
                n2 = n2.getNextSibling();
            }
            while (found < idx2) {
                n2 = n3.appendChild(doc.createElementNS((String) null, name));
                Node attr = doc.createAttributeNS(XfaForm.XFA_DATA_SCHEMA, "dataNode");
                attr.setNodeValue("dataGroup");
                n2.getAttributes().setNamedItemNS(attr);
                found++;
            }
            n3 = n2;
        }
        inverseSearchAdd(this.inverseSearch, localStack, shortName);
        this.name2Node.put(shortName, n2);
        this.order.add(shortName);
        return n2;
    }

    private static boolean hasChildren(Node n) {
        Node dataNodeN = n.getAttributes().getNamedItemNS(XfaForm.XFA_DATA_SCHEMA, "dataNode");
        if (dataNodeN != null) {
            String dataNode = dataNodeN.getNodeValue();
            if ("dataGroup".equals(dataNode)) {
                return true;
            }
            if ("dataValue".equals(dataNode)) {
                return false;
            }
        }
        if (!n.hasChildNodes()) {
            return false;
        }
        for (Node n2 = n.getFirstChild(); n2 != null; n2 = n2.getNextSibling()) {
            if (n2.getNodeType() == 1) {
                return true;
            }
        }
        return false;
    }

    private void processDatasetsInternal(Node n) {
        Integer i;
        if (n != null) {
            Map<String, Integer> ss = new HashMap<>();
            for (Node n2 = n.getFirstChild(); n2 != null; n2 = n2.getNextSibling()) {
                if (n2.getNodeType() == 1) {
                    String s = escapeSom(n2.getLocalName());
                    Integer i2 = ss.get(s);
                    if (i2 == null) {
                        i = 0;
                    } else {
                        i = Integer.valueOf(i2.intValue() + 1);
                    }
                    ss.put(s, i);
                    this.stack.push(String.format("%s[%s]", new Object[]{s, i.toString()}));
                    if (hasChildren(n2)) {
                        processDatasetsInternal(n2);
                    }
                    String unstack = printStack();
                    this.order.add(unstack);
                    inverseSearchAdd(unstack);
                    this.name2Node.put(unstack, n2);
                    this.stack.pop();
                }
            }
        }
    }
}
