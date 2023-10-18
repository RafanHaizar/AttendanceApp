package com.itextpdf.kernel.pdf.canvas.parser.listener;

import java.util.Collection;

public interface ILocationExtractionStrategy extends IEventListener {
    Collection<IPdfTextLocation> getResultantLocations();
}
