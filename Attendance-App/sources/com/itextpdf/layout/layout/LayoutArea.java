package com.itextpdf.layout.layout;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.p026io.util.HashCode;
import com.itextpdf.p026io.util.MessageFormatUtil;

public class LayoutArea implements Cloneable {
    protected Rectangle bBox;
    protected int pageNumber;

    public LayoutArea(int pageNumber2, Rectangle bBox2) {
        this.pageNumber = pageNumber2;
        this.bBox = bBox2;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public Rectangle getBBox() {
        return this.bBox;
    }

    public void setBBox(Rectangle bbox) {
        this.bBox = bbox;
    }

    public LayoutArea clone() {
        try {
            LayoutArea clone = (LayoutArea) super.clone();
            clone.bBox = this.bBox.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }
        LayoutArea that = (LayoutArea) obj;
        if (this.pageNumber != that.pageNumber || !this.bBox.equalsWithEpsilon(that.bBox)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        HashCode hashCode = new HashCode();
        hashCode.append(this.pageNumber).append(this.bBox.hashCode());
        return hashCode.hashCode();
    }

    public String toString() {
        return MessageFormatUtil.format("{0}, page {1}", this.bBox.toString(), Integer.valueOf(this.pageNumber));
    }
}
