package com.itextpdf.forms.fields;

import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.layout.TextLayoutResult;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.TextRenderer;

class FormFieldValueNonTrimmingTextRenderer extends TextRenderer {
    private boolean callTrimFirst = false;

    public FormFieldValueNonTrimmingTextRenderer(Text textElement) {
        super(textElement);
    }

    public IRenderer getNextRenderer() {
        return new FormFieldValueNonTrimmingTextRenderer((Text) getModelElement());
    }

    public LayoutResult layout(LayoutContext layoutContext) {
        LayoutResult baseLayoutResult = super.layout(layoutContext);
        if ((baseLayoutResult instanceof TextLayoutResult) && (baseLayoutResult.getOverflowRenderer() instanceof FormFieldValueNonTrimmingTextRenderer) && !((TextLayoutResult) baseLayoutResult).isSplitForcedByNewline()) {
            ((FormFieldValueNonTrimmingTextRenderer) baseLayoutResult.getOverflowRenderer()).setCallTrimFirst(true);
        }
        return baseLayoutResult;
    }

    public void trimFirst() {
        if (this.callTrimFirst) {
            super.trimFirst();
        }
    }

    private void setCallTrimFirst(boolean callTrimFirst2) {
        this.callTrimFirst = callTrimFirst2;
    }
}
