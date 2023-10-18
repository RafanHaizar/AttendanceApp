package com.itextpdf.kernel.log;

import com.itextpdf.p026io.util.MessageFormatUtil;

@Deprecated
public class SystemOutCounter implements ICounter {
    protected String name;

    public SystemOutCounter(String name2) {
        this.name = name2;
    }

    public SystemOutCounter() {
        this("iText");
    }

    public SystemOutCounter(Class<?> cls) {
        this(cls.getName());
    }

    public void onDocumentRead(long size) {
        System.out.println(MessageFormatUtil.format("[{0}] {1} bytes read", this.name, Long.valueOf(size)));
    }

    public void onDocumentWritten(long size) {
        System.out.println(MessageFormatUtil.format("[{0}] {1} bytes written", this.name, Long.valueOf(size)));
    }
}
