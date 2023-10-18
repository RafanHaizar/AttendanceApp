package com.itextpdf.kernel.pdf.canvas.parser.listener;

import com.itextpdf.kernel.pdf.canvas.parser.filter.IEventFilter;

public class FilteredTextEventListener extends FilteredEventListener implements ITextExtractionStrategy {
    public FilteredTextEventListener(ITextExtractionStrategy delegate, IEventFilter... filterSet) {
        super(delegate, filterSet);
    }

    public String getResultantText() {
        StringBuilder sb = new StringBuilder();
        for (IEventListener delegate : this.delegates) {
            if (delegate instanceof ITextExtractionStrategy) {
                sb.append(((ITextExtractionStrategy) delegate).getResultantText());
            }
        }
        return sb.toString();
    }
}
