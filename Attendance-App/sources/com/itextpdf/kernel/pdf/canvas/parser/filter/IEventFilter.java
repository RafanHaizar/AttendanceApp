package com.itextpdf.kernel.pdf.canvas.parser.filter;

import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;

public interface IEventFilter {
    boolean accept(IEventData iEventData, EventType eventType);
}
