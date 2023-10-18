package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
import com.itextpdf.kernel.pdf.colorspace.PdfPattern;
import com.itextpdf.kernel.pdf.colorspace.PdfShading;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class PdfResources extends PdfObjectWrapper<PdfDictionary> {

    /* renamed from: Cs */
    private static final String f1420Cs = "Cs";

    /* renamed from: F */
    private static final String f1421F = "F";

    /* renamed from: Fm */
    private static final String f1422Fm = "Fm";

    /* renamed from: Gs */
    private static final String f1423Gs = "Gs";

    /* renamed from: Im */
    private static final String f1424Im = "Im";

    /* renamed from: P */
    private static final String f1425P = "P";

    /* renamed from: Pr */
    private static final String f1426Pr = "Pr";

    /* renamed from: Sh */
    private static final String f1427Sh = "Sh";
    private static final long serialVersionUID = 7160318458835945391L;
    private ResourceNameGenerator csNamesGen;
    private ResourceNameGenerator egsNamesGen;
    private ResourceNameGenerator fontNamesGen;
    private ResourceNameGenerator formNamesGen;
    private ResourceNameGenerator imageNamesGen;
    private boolean isModified;
    private ResourceNameGenerator patternNamesGen;
    private ResourceNameGenerator propNamesGen;
    private boolean readOnly;
    private Map<PdfObject, PdfName> resourceToName;
    private ResourceNameGenerator shadingNamesGen;

    public PdfResources(PdfDictionary pdfObject) {
        super(pdfObject);
        this.resourceToName = new HashMap();
        this.fontNamesGen = new ResourceNameGenerator(PdfName.Font, f1421F);
        this.imageNamesGen = new ResourceNameGenerator(PdfName.XObject, f1424Im);
        this.formNamesGen = new ResourceNameGenerator(PdfName.XObject, f1422Fm);
        this.egsNamesGen = new ResourceNameGenerator(PdfName.ExtGState, f1423Gs);
        this.propNamesGen = new ResourceNameGenerator(PdfName.Properties, f1426Pr);
        this.csNamesGen = new ResourceNameGenerator(PdfName.ColorSpace, f1420Cs);
        this.patternNamesGen = new ResourceNameGenerator(PdfName.Pattern, "P");
        this.shadingNamesGen = new ResourceNameGenerator(PdfName.Shading, f1427Sh);
        this.readOnly = false;
        this.isModified = false;
        buildResources(pdfObject);
    }

    public PdfResources() {
        this(new PdfDictionary());
    }

    public PdfName addFont(PdfDocument pdfDocument, PdfFont font) {
        pdfDocument.addFont(font);
        return addResource(font, this.fontNamesGen);
    }

    public PdfName addImage(PdfImageXObject image) {
        return addResource(image, this.imageNamesGen);
    }

    public PdfName addImage(PdfStream image) {
        return addResource((PdfObject) image, this.imageNamesGen);
    }

    public PdfImageXObject getImage(PdfName name) {
        PdfStream image = getResource(PdfName.XObject).getAsStream(name);
        if (image == null || !PdfName.Image.equals(image.getAsName(PdfName.Subtype))) {
            return null;
        }
        return new PdfImageXObject(image);
    }

    public PdfName addForm(PdfFormXObject form) {
        return addResource(form, this.formNamesGen);
    }

    public PdfName addForm(PdfStream form) {
        return addResource((PdfObject) form, this.formNamesGen);
    }

    public PdfName addForm(PdfFormXObject form, PdfName name) {
        if (getResourceNames(PdfName.XObject).contains(name)) {
            return addResource(form, this.formNamesGen);
        }
        addResource(form.getPdfObject(), PdfName.XObject, name);
        return name;
    }

    public PdfFormXObject getForm(PdfName name) {
        PdfStream form = getResource(PdfName.XObject).getAsStream(name);
        if (form == null || !PdfName.Form.equals(form.getAsName(PdfName.Subtype))) {
            return null;
        }
        return new PdfFormXObject(form);
    }

    public PdfName addExtGState(PdfExtGState extGState) {
        return addResource(extGState, this.egsNamesGen);
    }

    public PdfName addExtGState(PdfDictionary extGState) {
        return addResource((PdfObject) extGState, this.egsNamesGen);
    }

    public PdfExtGState getPdfExtGState(PdfName name) {
        PdfDictionary dic = getResource(PdfName.ExtGState).getAsDictionary(name);
        if (dic != null) {
            return new PdfExtGState(dic);
        }
        return null;
    }

    public PdfName addProperties(PdfDictionary properties) {
        return addResource((PdfObject) properties, this.propNamesGen);
    }

    public PdfObject getProperties(PdfName name) {
        return getResourceObject(PdfName.Properties, name);
    }

    public PdfName addColorSpace(PdfColorSpace cs) {
        return addResource(cs, this.csNamesGen);
    }

    public PdfName addColorSpace(PdfObject colorSpace) {
        return addResource(colorSpace, this.csNamesGen);
    }

    public PdfColorSpace getColorSpace(PdfName name) {
        PdfObject colorSpace = getResourceObject(PdfName.ColorSpace, name);
        if (colorSpace != null) {
            return PdfColorSpace.makeColorSpace(colorSpace);
        }
        return null;
    }

    public PdfName addPattern(PdfPattern pattern) {
        return addResource(pattern, this.patternNamesGen);
    }

    public PdfName addPattern(PdfDictionary pattern) {
        return addResource((PdfObject) pattern, this.patternNamesGen);
    }

    public PdfPattern getPattern(PdfName name) {
        PdfObject pattern = getResourceObject(PdfName.Pattern, name);
        if (pattern instanceof PdfDictionary) {
            return PdfPattern.getPatternInstance((PdfDictionary) pattern);
        }
        return null;
    }

    public PdfName addShading(PdfShading shading) {
        return addResource(shading, this.shadingNamesGen);
    }

    public PdfName addShading(PdfDictionary shading) {
        return addResource((PdfObject) shading, this.shadingNamesGen);
    }

    public PdfShading getShading(PdfName name) {
        PdfObject shading = getResourceObject(PdfName.Shading, name);
        if (shading instanceof PdfDictionary) {
            return PdfShading.makeShading((PdfDictionary) shading);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public boolean isReadOnly() {
        return this.readOnly;
    }

    /* access modifiers changed from: protected */
    public void setReadOnly(boolean readOnly2) {
        this.readOnly = readOnly2;
    }

    /* access modifiers changed from: protected */
    public boolean isModified() {
        return this.isModified;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void setModified(boolean isModified2) {
        this.isModified = isModified2;
    }

    public PdfObjectWrapper<PdfDictionary> setModified() {
        this.isModified = true;
        return super.setModified();
    }

    public void setDefaultGray(PdfColorSpace defaultCs) {
        addResource(defaultCs.getPdfObject(), PdfName.ColorSpace, PdfName.DefaultGray);
    }

    public void setDefaultRgb(PdfColorSpace defaultCs) {
        addResource(defaultCs.getPdfObject(), PdfName.ColorSpace, PdfName.DefaultRGB);
    }

    public void setDefaultCmyk(PdfColorSpace defaultCs) {
        addResource(defaultCs.getPdfObject(), PdfName.ColorSpace, PdfName.DefaultCMYK);
    }

    public <T extends PdfObject> PdfName getResourceName(PdfObjectWrapper<T> resource) {
        return getResourceName((PdfObject) resource.getPdfObject());
    }

    public PdfName getResourceName(PdfObject resource) {
        PdfName resName = this.resourceToName.get(resource);
        if (resName == null) {
            return this.resourceToName.get(resource.getIndirectReference());
        }
        return resName;
    }

    public Set<PdfName> getResourceNames() {
        Set<PdfName> names = new TreeSet<>();
        for (PdfName resType : ((PdfDictionary) getPdfObject()).keySet()) {
            names.addAll(getResourceNames(resType));
        }
        return names;
    }

    public PdfArray getProcSet() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.ProcSet);
    }

    public void setProcSet(PdfArray array) {
        ((PdfDictionary) getPdfObject()).put(PdfName.ProcSet, array);
    }

    public Set<PdfName> getResourceNames(PdfName resType) {
        PdfDictionary resourceCategory = ((PdfDictionary) getPdfObject()).getAsDictionary(resType);
        return resourceCategory == null ? Collections.emptySet() : resourceCategory.keySet();
    }

    public PdfDictionary getResource(PdfName resType) {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(resType);
    }

    public PdfObject getResourceObject(PdfName resType, PdfName resName) {
        PdfDictionary resource = getResource(resType);
        if (resource != null) {
            return resource.get(resName);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public <T extends PdfObject> PdfName addResource(PdfObjectWrapper<T> resource, ResourceNameGenerator nameGen) {
        return addResource((PdfObject) resource.getPdfObject(), nameGen);
    }

    /* access modifiers changed from: protected */
    public void addResource(PdfObject resource, PdfName resType, PdfName resName) {
        if (resType.equals(PdfName.XObject)) {
            checkAndResolveCircularReferences(resource);
        }
        if (this.readOnly) {
            setPdfObject(((PdfDictionary) getPdfObject()).clone(Collections.emptyList()));
            buildResources((PdfDictionary) getPdfObject());
            this.isModified = true;
            this.readOnly = false;
        }
        if (!((PdfDictionary) getPdfObject()).containsKey(resType) || !((PdfDictionary) getPdfObject()).getAsDictionary(resType).containsKey(resName)) {
            this.resourceToName.put(resource, resName);
            PdfDictionary resourceCategory = ((PdfDictionary) getPdfObject()).getAsDictionary(resType);
            if (resourceCategory == null) {
                PdfDictionary pdfDictionary = new PdfDictionary();
                resourceCategory = pdfDictionary;
                ((PdfDictionary) getPdfObject()).put(resType, pdfDictionary);
            } else {
                resourceCategory.setModified();
            }
            resourceCategory.put(resName, resource);
            setModified();
        }
    }

    /* access modifiers changed from: package-private */
    public PdfName addResource(PdfObject resource, ResourceNameGenerator nameGen) {
        PdfName resName = getResourceName(resource);
        if (resName != null) {
            return resName;
        }
        PdfName resName2 = nameGen.generate(this);
        addResource(resource, nameGen.getResourceType(), resName2);
        return resName2;
    }

    /* access modifiers changed from: protected */
    public void buildResources(PdfDictionary dictionary) {
        for (PdfName resourceType : dictionary.keySet()) {
            if (((PdfDictionary) getPdfObject()).get(resourceType) == null) {
                ((PdfDictionary) getPdfObject()).put(resourceType, new PdfDictionary());
            }
            PdfDictionary resources = dictionary.getAsDictionary(resourceType);
            if (resources != null) {
                for (PdfName resourceName : resources.keySet()) {
                    this.resourceToName.put(resources.get(resourceName, false), resourceName);
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000a, code lost:
        r0 = (com.itextpdf.kernel.pdf.PdfDictionary) r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void checkAndResolveCircularReferences(com.itextpdf.kernel.pdf.PdfObject r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.itextpdf.kernel.pdf.PdfDictionary
            if (r0 == 0) goto L_0x0053
            boolean r0 = r6.isFlushed()
            if (r0 != 0) goto L_0x0053
            r0 = r6
            com.itextpdf.kernel.pdf.PdfDictionary r0 = (com.itextpdf.kernel.pdf.PdfDictionary) r0
            com.itextpdf.kernel.pdf.PdfName r1 = com.itextpdf.kernel.pdf.PdfName.Resources
            com.itextpdf.kernel.pdf.PdfObject r1 = r0.get(r1)
            if (r1 == 0) goto L_0x0053
            com.itextpdf.kernel.pdf.PdfIndirectReference r2 = r1.getIndirectReference()
            if (r2 == 0) goto L_0x0053
            com.itextpdf.kernel.pdf.PdfIndirectReference r2 = r1.getIndirectReference()
            com.itextpdf.kernel.pdf.PdfObject r3 = r5.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r3 = (com.itextpdf.kernel.pdf.PdfDictionary) r3
            com.itextpdf.kernel.pdf.PdfIndirectReference r3 = r3.getIndirectReference()
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0053
            com.itextpdf.kernel.pdf.PdfObject r2 = r5.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r2 = (com.itextpdf.kernel.pdf.PdfDictionary) r2
            com.itextpdf.kernel.pdf.PdfObject r2 = r2.clone()
            com.itextpdf.kernel.pdf.PdfObject r3 = r5.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r3 = (com.itextpdf.kernel.pdf.PdfDictionary) r3
            com.itextpdf.kernel.pdf.PdfIndirectReference r3 = r3.getIndirectReference()
            com.itextpdf.kernel.pdf.PdfDocument r3 = r3.getDocument()
            r2.makeIndirect(r3)
            com.itextpdf.kernel.pdf.PdfName r3 = com.itextpdf.kernel.pdf.PdfName.Resources
            com.itextpdf.kernel.pdf.PdfIndirectReference r4 = r2.getIndirectReference()
            r0.put(r3, r4)
        L_0x0053:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.pdf.PdfResources.checkAndResolveCircularReferences(com.itextpdf.kernel.pdf.PdfObject):void");
    }

    static class ResourceNameGenerator implements Serializable {
        private static final long serialVersionUID = 1729961083476558303L;
        private int counter;
        private String prefix;
        private PdfName resourceType;

        public ResourceNameGenerator(PdfName resourceType2, String prefix2, int seed) {
            this.prefix = prefix2;
            this.resourceType = resourceType2;
            this.counter = seed;
        }

        public ResourceNameGenerator(PdfName resourceType2, String prefix2) {
            this(resourceType2, prefix2, 1);
        }

        public PdfName getResourceType() {
            return this.resourceType;
        }

        public PdfName generate(PdfResources resources) {
            StringBuilder append = new StringBuilder().append(this.prefix);
            int i = this.counter;
            this.counter = i + 1;
            PdfName newName = new PdfName(append.append(i).toString());
            PdfDictionary r = (PdfDictionary) resources.getPdfObject();
            if (r.containsKey(this.resourceType)) {
                while (r.getAsDictionary(this.resourceType).containsKey(newName)) {
                    StringBuilder append2 = new StringBuilder().append(this.prefix);
                    int i2 = this.counter;
                    this.counter = i2 + 1;
                    newName = new PdfName(append2.append(i2).toString());
                }
            }
            return newName;
        }
    }
}
