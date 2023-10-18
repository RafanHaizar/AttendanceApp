package com.itextpdf.signatures;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDate;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNull;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.ReaderProperties;
import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.p026io.source.IRandomAccessSource;
import com.itextpdf.p026io.source.PdfTokenizer;
import com.itextpdf.p026io.source.RASInputStream;
import com.itextpdf.p026io.source.RandomAccessSourceFactory;
import com.itextpdf.p026io.source.WindowRandomAccessSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignatureUtil {
    private PdfAcroForm acroForm;
    private PdfDocument document;
    private List<String> orderedSignatureNames;
    private Map<String, int[]> sigNames;
    private int totalRevisions;

    @Deprecated
    public static long[] asLongArray(PdfArray pdfArray) {
        long[] rslt = new long[pdfArray.size()];
        for (int k = 0; k < rslt.length; k++) {
            rslt[k] = pdfArray.getAsNumber(k).longValue();
        }
        return rslt;
    }

    public SignatureUtil(PdfDocument document2) {
        this.document = document2;
        this.acroForm = PdfAcroForm.getAcroForm(document2, document2.getWriter() != null);
    }

    @Deprecated
    public PdfPKCS7 verifySignature(String name) {
        return readSignatureData(name, (String) null);
    }

    @Deprecated
    public PdfPKCS7 verifySignature(String name, String provider) {
        return readSignatureData(name, provider);
    }

    public PdfPKCS7 readSignatureData(String signatureFieldName) {
        return readSignatureData(signatureFieldName, (String) null);
    }

    public PdfPKCS7 readSignatureData(String signatureFieldName, String securityProvider) {
        PdfPKCS7 pk;
        PdfSignature signature = getSignature(signatureFieldName);
        if (signature == null) {
            return null;
        }
        try {
            PdfName sub = signature.getSubFilter();
            PdfString contents = signature.getContents();
            if (sub.equals(PdfName.Adbe_x509_rsa_sha1)) {
                PdfString cert = ((PdfDictionary) signature.getPdfObject()).getAsString(PdfName.Cert);
                if (cert == null) {
                    cert = ((PdfDictionary) signature.getPdfObject()).getAsArray(PdfName.Cert).getAsString(0);
                }
                pk = new PdfPKCS7(PdfEncodings.convertToBytes(contents.getValue(), (String) null), cert.getValueBytes(), securityProvider);
            } else {
                pk = new PdfPKCS7(PdfEncodings.convertToBytes(contents.getValue(), (String) null), sub, securityProvider);
            }
            updateByteRange(pk, signature);
            PdfString date = signature.getDate();
            if (date != null) {
                pk.setSignDate(PdfDate.decode(date.toString()));
            }
            pk.setSignName(signature.getName());
            String reason = signature.getReason();
            if (reason != null) {
                pk.setReason(reason);
            }
            String location = signature.getLocation();
            if (location != null) {
                pk.setLocation(location);
            }
            return pk;
        } catch (Exception e) {
            throw new PdfException((Throwable) e);
        }
    }

    public PdfSignature getSignature(String name) {
        PdfDictionary sigDict = getSignatureDictionary(name);
        if (sigDict != null) {
            return new PdfSignature(sigDict);
        }
        return null;
    }

    public PdfDictionary getSignatureDictionary(String name) {
        getSignatureNames();
        if (this.acroForm == null || !this.sigNames.containsKey(name)) {
            return null;
        }
        return ((PdfDictionary) this.acroForm.getField(name).getPdfObject()).getAsDictionary(PdfName.f1406V);
    }

    private void updateByteRange(PdfPKCS7 pkcs7, PdfSignature signature) {
        PdfArray b = signature.getByteRange();
        InputStream rg = null;
        try {
            InputStream rg2 = new RASInputStream(new RandomAccessSourceFactory().createRanged(this.document.getReader().getSafeFile().createSourceView(), b.toLongArray()));
            byte[] buf = new byte[8192];
            while (true) {
                int read = rg2.read(buf, 0, buf.length);
                int rd = read;
                if (read > 0) {
                    pkcs7.update(buf, 0, rd);
                } else {
                    try {
                        rg2.close();
                        return;
                    } catch (IOException e) {
                        throw new PdfException((Throwable) e);
                    }
                }
            }
        } catch (Exception e2) {
            throw new PdfException((Throwable) e2);
        } catch (Throwable e3) {
            if (rg != null) {
                try {
                    rg.close();
                } catch (IOException e4) {
                    throw new PdfException((Throwable) e4);
                }
            }
            throw e3;
        }
    }

    public List<String> getSignatureNames() {
        if (this.sigNames != null) {
            return new ArrayList(this.orderedSignatureNames);
        }
        this.sigNames = new HashMap();
        this.orderedSignatureNames = new ArrayList();
        populateSignatureNames();
        return new ArrayList(this.orderedSignatureNames);
    }

    public List<String> getBlankSignatureNames() {
        getSignatureNames();
        List<String> sigs = new ArrayList<>();
        PdfAcroForm pdfAcroForm = this.acroForm;
        if (pdfAcroForm != null) {
            for (Map.Entry<String, PdfFormField> entry : pdfAcroForm.getFormFields().entrySet()) {
                if (PdfName.Sig.equals(((PdfDictionary) entry.getValue().getPdfObject()).getAsName(PdfName.f1327FT)) && !this.sigNames.containsKey(entry.getKey())) {
                    sigs.add(entry.getKey());
                }
            }
        }
        return sigs;
    }

    public int getTotalRevisions() {
        getSignatureNames();
        return this.totalRevisions;
    }

    public int getRevision(String field) {
        getSignatureNames();
        String field2 = getTranslatedFieldName(field);
        if (!this.sigNames.containsKey(field2)) {
            return 0;
        }
        return this.sigNames.get(field2)[1];
    }

    public String getTranslatedFieldName(String name) {
        String namex;
        PdfAcroForm pdfAcroForm = this.acroForm;
        if (pdfAcroForm == null || !pdfAcroForm.getXfaForm().isXfaPresent() || (namex = this.acroForm.getXfaForm().findFieldName(name)) == null) {
            return name;
        }
        return namex;
    }

    public InputStream extractRevision(String field) throws IOException {
        getSignatureNames();
        if (!this.sigNames.containsKey(field)) {
            return null;
        }
        return new RASInputStream(new WindowRandomAccessSource(this.document.getReader().getSafeFile().createSourceView(), 0, (long) this.sigNames.get(field)[0]));
    }

    public boolean signatureCoversWholeDocument(String name) {
        getSignatureNames();
        if (!this.sigNames.containsKey(name)) {
            return false;
        }
        try {
            return new ContentsChecker(this.document.getReader().getSafeFile().createSourceView()).checkWhetherSignatureCoversWholeDocument(this.acroForm.getField(name));
        } catch (IOException e) {
            throw new PdfException((Throwable) e);
        }
    }

    public boolean doesSignatureFieldExist(String name) {
        return getBlankSignatureNames().contains(name) || getSignatureNames().contains(name);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v3, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void populateSignatureNames() {
        /*
            r15 = this;
            com.itextpdf.forms.PdfAcroForm r0 = r15.acroForm
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            com.itextpdf.forms.PdfAcroForm r1 = r15.acroForm
            java.util.Map r1 = r1.getFormFields()
            java.util.Set r1 = r1.entrySet()
            java.util.Iterator r1 = r1.iterator()
        L_0x0018:
            boolean r2 = r1.hasNext()
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L_0x0090
            java.lang.Object r2 = r1.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.Object r5 = r2.getValue()
            com.itextpdf.forms.fields.PdfFormField r5 = (com.itextpdf.forms.fields.PdfFormField) r5
            com.itextpdf.kernel.pdf.PdfObject r6 = r5.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r6 = (com.itextpdf.kernel.pdf.PdfDictionary) r6
            com.itextpdf.kernel.pdf.PdfName r7 = com.itextpdf.kernel.pdf.PdfName.Sig
            com.itextpdf.kernel.pdf.PdfName r8 = com.itextpdf.kernel.pdf.PdfName.f1327FT
            com.itextpdf.kernel.pdf.PdfObject r8 = r6.get(r8)
            boolean r7 = r7.equals(r8)
            if (r7 != 0) goto L_0x0041
            goto L_0x0018
        L_0x0041:
            com.itextpdf.kernel.pdf.PdfName r7 = com.itextpdf.kernel.pdf.PdfName.f1406V
            com.itextpdf.kernel.pdf.PdfDictionary r7 = r6.getAsDictionary(r7)
            if (r7 != 0) goto L_0x004a
            goto L_0x0018
        L_0x004a:
            com.itextpdf.kernel.pdf.PdfName r8 = com.itextpdf.kernel.pdf.PdfName.Contents
            com.itextpdf.kernel.pdf.PdfString r8 = r7.getAsString(r8)
            if (r8 != 0) goto L_0x0053
            goto L_0x0018
        L_0x0053:
            r8.markAsUnencryptedObject()
            com.itextpdf.kernel.pdf.PdfName r9 = com.itextpdf.kernel.pdf.PdfName.ByteRange
            com.itextpdf.kernel.pdf.PdfArray r9 = r7.getAsArray(r9)
            if (r9 != 0) goto L_0x005f
            goto L_0x0018
        L_0x005f:
            int r10 = r9.size()
            r11 = 2
            if (r10 >= r11) goto L_0x0067
            goto L_0x0018
        L_0x0067:
            int r12 = r10 + -1
            com.itextpdf.kernel.pdf.PdfNumber r12 = r9.getAsNumber(r12)
            int r12 = r12.intValue()
            int r13 = r10 + -2
            com.itextpdf.kernel.pdf.PdfNumber r13 = r9.getAsNumber(r13)
            int r13 = r13.intValue()
            int r12 = r12 + r13
            java.lang.Object[] r13 = new java.lang.Object[r11]
            java.lang.Object r14 = r2.getKey()
            r13[r3] = r14
            int[] r11 = new int[r11]
            r11[r3] = r12
            r11[r4] = r3
            r13[r4] = r11
            r0.add(r13)
            goto L_0x0018
        L_0x0090:
            com.itextpdf.signatures.SignatureUtil$SorterComparator r1 = new com.itextpdf.signatures.SignatureUtil$SorterComparator
            r2 = 0
            r1.<init>()
            java.util.Collections.sort(r0, r1)
            int r1 = r0.size()
            if (r1 <= 0) goto L_0x00f9
            int r1 = r0.size()     // Catch:{ IOException -> 0x00d0 }
            int r1 = r1 - r4
            java.lang.Object r1 = r0.get(r1)     // Catch:{ IOException -> 0x00d0 }
            java.lang.Object[] r1 = (java.lang.Object[]) r1     // Catch:{ IOException -> 0x00d0 }
            r1 = r1[r4]     // Catch:{ IOException -> 0x00d0 }
            int[] r1 = (int[]) r1     // Catch:{ IOException -> 0x00d0 }
            int[] r1 = (int[]) r1     // Catch:{ IOException -> 0x00d0 }
            r1 = r1[r3]     // Catch:{ IOException -> 0x00d0 }
            long r1 = (long) r1     // Catch:{ IOException -> 0x00d0 }
            com.itextpdf.kernel.pdf.PdfDocument r5 = r15.document     // Catch:{ IOException -> 0x00d0 }
            com.itextpdf.kernel.pdf.PdfReader r5 = r5.getReader()     // Catch:{ IOException -> 0x00d0 }
            long r5 = r5.getFileLength()     // Catch:{ IOException -> 0x00d0 }
            int r7 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r7 != 0) goto L_0x00c8
            int r1 = r0.size()     // Catch:{ IOException -> 0x00d0 }
            r15.totalRevisions = r1     // Catch:{ IOException -> 0x00d0 }
            goto L_0x00cf
        L_0x00c8:
            int r1 = r0.size()     // Catch:{ IOException -> 0x00d0 }
            int r1 = r1 + r4
            r15.totalRevisions = r1     // Catch:{ IOException -> 0x00d0 }
        L_0x00cf:
            goto L_0x00d1
        L_0x00d0:
            r1 = move-exception
        L_0x00d1:
            r1 = 0
        L_0x00d2:
            int r2 = r0.size()
            if (r1 >= r2) goto L_0x00f9
            java.lang.Object r2 = r0.get(r1)
            java.lang.Object[] r2 = (java.lang.Object[]) r2
            r5 = r2[r3]
            java.lang.String r5 = (java.lang.String) r5
            r6 = r2[r4]
            int[] r6 = (int[]) r6
            int[] r6 = (int[]) r6
            int r7 = r1 + 1
            r6[r4] = r7
            java.util.Map<java.lang.String, int[]> r7 = r15.sigNames
            r7.put(r5, r6)
            java.util.List<java.lang.String> r7 = r15.orderedSignatureNames
            r7.add(r5)
            int r1 = r1 + 1
            goto L_0x00d2
        L_0x00f9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.signatures.SignatureUtil.populateSignatureNames():void");
    }

    private static class SorterComparator implements Comparator<Object[]> {
        private SorterComparator() {
        }

        public int compare(Object[] o1, Object[] o2) {
            return o1[1][0] - o2[1][0];
        }
    }

    private static class ContentsChecker extends PdfReader {
        private long contentsEnd;
        private int contentsLevel = 1;
        private long contentsStart;
        private int currentLevel = 0;
        private boolean rangeIsCorrect = false;
        private boolean searchInV = true;

        public ContentsChecker(IRandomAccessSource byteSource) throws IOException {
            super(byteSource, (ReaderProperties) null);
        }

        public boolean checkWhetherSignatureCoversWholeDocument(PdfFormField signatureField) {
            long signatureOffset;
            this.rangeIsCorrect = false;
            PdfDictionary signature = (PdfDictionary) signatureField.getValue();
            int[] byteRange = ((PdfArray) signature.get(PdfName.ByteRange)).toIntArray();
            try {
                if (4 == byteRange.length && byteRange[0] == 0) {
                    if (this.tokens.getSafeFile().length() == ((long) (byteRange[2] + byteRange[3]))) {
                        this.contentsStart = (long) byteRange[1];
                        this.contentsEnd = (long) byteRange[2];
                        if (signature.getIndirectReference() != null) {
                            signatureOffset = signature.getIndirectReference().getOffset();
                            this.searchInV = true;
                        } else {
                            signatureOffset = ((PdfDictionary) signatureField.getPdfObject()).getIndirectReference().getOffset();
                            this.searchInV = false;
                            this.contentsLevel++;
                        }
                        try {
                            this.tokens.seek(signatureOffset);
                            this.tokens.nextValidToken();
                            readObject(false, false);
                            return this.rangeIsCorrect;
                        } catch (IOException e) {
                            return false;
                        }
                    }
                }
                return false;
            } catch (IOException e2) {
                return false;
            }
        }

        /* access modifiers changed from: protected */
        public PdfDictionary readDictionary(boolean objStm) throws IOException {
            PdfObject obj;
            int ch;
            boolean z = objStm;
            this.currentLevel++;
            PdfDictionary dic = new PdfDictionary();
            while (true) {
                if (this.rangeIsCorrect) {
                    break;
                }
                this.tokens.nextValidToken();
                if (this.tokens.getTokenType() == PdfTokenizer.TokenType.EndDic) {
                    this.currentLevel--;
                    break;
                }
                if (this.tokens.getTokenType() != PdfTokenizer.TokenType.Name) {
                    this.tokens.throwError(PdfException.DictionaryKey1IsNotAName, this.tokens.getStringValue());
                }
                PdfName name = readPdfName(true);
                if (PdfName.Contents.equals(name) && this.searchInV && this.contentsLevel == this.currentLevel) {
                    long startPosition = this.tokens.getPosition();
                    int whiteSpacesCount = -1;
                    do {
                        ch = this.tokens.read();
                        whiteSpacesCount++;
                        if (ch == -1 || !PdfTokenizer.isWhitespace(ch)) {
                            this.tokens.seek(startPosition);
                            obj = readObject(true, z);
                        }
                        ch = this.tokens.read();
                        whiteSpacesCount++;
                        break;
                    } while (!PdfTokenizer.isWhitespace(ch));
                    this.tokens.seek(startPosition);
                    obj = readObject(true, z);
                    if (this.tokens.getPosition() == this.contentsEnd) {
                        long j = startPosition;
                        if (((long) whiteSpacesCount) + startPosition == this.contentsStart) {
                            this.rangeIsCorrect = true;
                        }
                    }
                } else if (!PdfName.f1406V.equals(name) || this.searchInV || 1 != this.currentLevel) {
                    obj = readObject(true, z);
                } else {
                    this.searchInV = true;
                    obj = readObject(true, z);
                    this.searchInV = false;
                }
                if (obj == null) {
                    if (this.tokens.getTokenType() == PdfTokenizer.TokenType.EndDic) {
                        this.tokens.throwError(PdfException.UnexpectedGtGt, new Object[0]);
                    }
                    if (this.tokens.getTokenType() == PdfTokenizer.TokenType.EndArray) {
                        this.tokens.throwError("Unexpected close bracket.", new Object[0]);
                    }
                }
                dic.put(name, obj);
            }
            return dic;
        }

        /* access modifiers changed from: protected */
        public PdfObject readReference(boolean readAsDirect) {
            return new PdfNull();
        }
    }
}
