package com.itextpdf.kernel.counter;

import com.itextpdf.kernel.counter.context.GenericContext;
import com.itextpdf.kernel.counter.context.IContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

public class ContextManager {
    private static final ContextManager instance = new ContextManager();
    private final SortedMap<String, IContext> contextMappings = new TreeMap(new LengthComparator());

    private ContextManager() {
        registerGenericContext(Arrays.asList(new String[]{NamespaceConstant.CORE_IO, NamespaceConstant.CORE_KERNEL, NamespaceConstant.CORE_LAYOUT, NamespaceConstant.CORE_BARCODES, NamespaceConstant.CORE_PDFA, NamespaceConstant.CORE_SIGN, NamespaceConstant.CORE_FORMS, NamespaceConstant.CORE_SXP, NamespaceConstant.CORE_SVG}), Collections.singletonList(NamespaceConstant.ITEXT));
        registerGenericContext(Collections.singletonList(NamespaceConstant.PDF_DEBUG), Collections.singletonList(NamespaceConstant.PDF_DEBUG));
        registerGenericContext(Collections.singletonList(NamespaceConstant.PDF_HTML), Collections.singletonList(NamespaceConstant.PDF_HTML));
        registerGenericContext(Collections.singletonList(NamespaceConstant.PDF_INVOICE), Collections.singletonList(NamespaceConstant.PDF_INVOICE));
        registerGenericContext(Collections.singletonList(NamespaceConstant.PDF_SWEEP), Collections.singletonList(NamespaceConstant.PDF_SWEEP));
        registerGenericContext(Collections.singletonList(NamespaceConstant.PDF_OCR_TESSERACT4), Collections.singletonList(NamespaceConstant.PDF_OCR_TESSERACT4));
        registerGenericContext(Collections.singletonList(NamespaceConstant.PDF_OCR), Collections.emptyList());
    }

    public static ContextManager getInstance() {
        return instance;
    }

    public IContext getContext(Class<?> clazz) {
        if (clazz != null) {
            return getContext(clazz.getName());
        }
        return null;
    }

    public IContext getContext(String className) {
        return getNamespaceMapping(getRecognisedNamespace(className));
    }

    /* access modifiers changed from: package-private */
    public String getRecognisedNamespace(String className) {
        if (className == null) {
            return null;
        }
        for (String namespace : this.contextMappings.keySet()) {
            if (className.toLowerCase().startsWith(namespace)) {
                return namespace;
            }
        }
        return null;
    }

    private IContext getNamespaceMapping(String namespace) {
        if (namespace != null) {
            return (IContext) this.contextMappings.get(namespace);
        }
        return null;
    }

    private void registerGenericContext(Collection<String> namespaces, Collection<String> eventIds) {
        GenericContext context = new GenericContext(eventIds);
        for (String namespace : namespaces) {
            registerContext(namespace.toLowerCase(), context);
        }
    }

    private void registerContext(String namespace, IContext context) {
        this.contextMappings.put(namespace, context);
    }

    private static class LengthComparator implements Comparator<String> {
        private LengthComparator() {
        }

        public int compare(String o1, String o2) {
            int lengthComparison = -Integer.compare(o1.length(), o2.length());
            if (lengthComparison != 0) {
                return lengthComparison;
            }
            return o1.compareTo(o2);
        }
    }
}
