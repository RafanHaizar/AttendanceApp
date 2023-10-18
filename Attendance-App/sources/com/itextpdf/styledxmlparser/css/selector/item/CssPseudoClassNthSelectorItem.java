package com.itextpdf.styledxmlparser.css.selector.item;

import com.itextpdf.styledxmlparser.node.ICustomElementNode;
import com.itextpdf.styledxmlparser.node.IDocumentNode;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import java.util.List;
import org.slf4j.Marker;

class CssPseudoClassNthSelectorItem extends CssPseudoClassChildSelectorItem {
    private int nthA;
    private int nthB;

    CssPseudoClassNthSelectorItem(String pseudoClass, String arguments) {
        super(pseudoClass, arguments);
        getNthArguments();
    }

    public boolean matches(INode node) {
        if (!(node instanceof IElementNode) || (node instanceof ICustomElementNode) || (node instanceof IDocumentNode)) {
            return false;
        }
        List<INode> children = getAllSiblings(node);
        if (children.isEmpty() || !resolveNth(node, children)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void getNthArguments() {
        if (!this.arguments.matches("((-|\\+)?[0-9]*n(\\s*(-|\\+)\\s*[0-9]+)?|(-|\\+)?[0-9]+|odd|even)")) {
            this.nthA = 0;
            this.nthB = 0;
        } else if (this.arguments.equals("odd")) {
            this.nthA = 2;
            this.nthB = 1;
        } else if (this.arguments.equals("even")) {
            this.nthA = 2;
            this.nthB = 0;
        } else {
            int indexOfN = this.arguments.indexOf(110);
            int i = -1;
            if (indexOfN == -1) {
                this.nthA = 0;
                this.nthB = Integer.parseInt(this.arguments);
                return;
            }
            String aParticle = this.arguments.substring(0, indexOfN).trim();
            if (aParticle.isEmpty()) {
                this.nthA = 0;
            } else if (aParticle.length() != 1 || Character.isDigit(aParticle.charAt(0))) {
                this.nthA = Integer.parseInt(aParticle);
            } else {
                if (aParticle.equals(Marker.ANY_NON_NULL_MARKER)) {
                    i = 1;
                }
                this.nthA = i;
            }
            String bParticle = this.arguments.substring(indexOfN + 1).trim();
            if (!bParticle.isEmpty()) {
                this.nthB = Integer.parseInt(bParticle.charAt(0) + bParticle.substring(1).trim());
            } else {
                this.nthB = 0;
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean resolveNth(INode node, List<INode> children) {
        if (!children.contains(node)) {
            return false;
        }
        int temp = this.nthA;
        if (temp > 0) {
            int temp2 = (children.indexOf(node) + 1) - this.nthB;
            if (temp2 < 0 || temp2 % this.nthA != 0) {
                return false;
            }
            return true;
        } else if (temp < 0) {
            int temp3 = (children.indexOf(node) + 1) - this.nthB;
            if (temp3 > 0 || temp3 % this.nthA != 0) {
                return false;
            }
            return true;
        } else if ((children.indexOf(node) + 1) - this.nthB == 0) {
            return true;
        } else {
            return false;
        }
    }
}
