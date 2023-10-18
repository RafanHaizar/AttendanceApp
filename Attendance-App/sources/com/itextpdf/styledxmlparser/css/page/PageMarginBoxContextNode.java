package com.itextpdf.styledxmlparser.css.page;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.styledxmlparser.css.CssContextNode;
import com.itextpdf.styledxmlparser.node.INode;

public class PageMarginBoxContextNode extends CssContextNode {
    public static final String PAGE_MARGIN_BOX_TAG = "_064ef03_page-margin-box";
    private Rectangle containingBlockForMarginBox;
    private String marginBoxName;
    private Rectangle pageMarginBoxRectangle;

    public PageMarginBoxContextNode(INode parentNode, String marginBoxName2) {
        super(parentNode);
        this.marginBoxName = marginBoxName2;
        if (!(parentNode instanceof PageContextNode)) {
            throw new IllegalArgumentException("Page-margin-box context node shall have a page context node as parent.");
        }
    }

    public String getMarginBoxName() {
        return this.marginBoxName;
    }

    public void setPageMarginBoxRectangle(Rectangle pageMarginBoxRectangle2) {
        this.pageMarginBoxRectangle = pageMarginBoxRectangle2;
    }

    public Rectangle getPageMarginBoxRectangle() {
        return this.pageMarginBoxRectangle;
    }

    public void setContainingBlockForMarginBox(Rectangle containingBlockForMarginBox2) {
        this.containingBlockForMarginBox = containingBlockForMarginBox2;
    }

    public Rectangle getContainingBlockForMarginBox() {
        return this.containingBlockForMarginBox;
    }
}
