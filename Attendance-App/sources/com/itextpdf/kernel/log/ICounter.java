package com.itextpdf.kernel.log;

@Deprecated
public interface ICounter {
    void onDocumentRead(long j);

    void onDocumentWritten(long j);
}
