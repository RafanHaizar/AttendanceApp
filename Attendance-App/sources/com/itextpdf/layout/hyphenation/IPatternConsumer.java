package com.itextpdf.layout.hyphenation;

import java.util.List;

public interface IPatternConsumer {
    void addClass(String str);

    void addException(String str, List list);

    void addPattern(String str, String str2);
}
