package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.crypto.securityhandler.UnsupportedSecurityHandlerException;
import com.itextpdf.kernel.pdf.filters.FilterHandlers;
import com.itextpdf.kernel.pdf.filters.IFilterHandler;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.source.ByteBuffer;
import com.itextpdf.p026io.source.ByteUtils;
import com.itextpdf.p026io.source.IRandomAccessSource;
import com.itextpdf.p026io.source.PdfTokenizer;
import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import com.itextpdf.p026io.source.RandomAccessSourceFactory;
import com.itextpdf.p026io.source.WindowRandomAccessSource;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import kotlin.UByte;
import org.slf4j.LoggerFactory;

public class PdfReader implements Closeable, Serializable {
    protected static boolean correctStreamLength = true;
    private static final byte[] endobj = ByteUtils.getIsoBytes("endobj");
    private static final byte[] endstream = ByteUtils.getIsoBytes(endstream1);
    private static final String endstream1 = "endstream";
    private static final String endstream2 = "\nendstream";
    private static final String endstream3 = "\r\nendstream";
    private static final String endstream4 = "\rendstream";
    private static final long serialVersionUID = -3584187443691964939L;
    private PdfIndirectReference currentIndirectReference;
    protected PdfEncryption decrypt;
    protected boolean encrypted;
    protected long eofPos;
    protected boolean fixedXref;
    protected PdfVersion headerPdfVersion;
    protected boolean hybridXref;
    protected long lastXref;
    private boolean memorySavingMode;
    protected PdfAConformanceLevel pdfAConformanceLevel;
    protected PdfDocument pdfDocument;
    protected ReaderProperties properties;
    protected boolean rebuiltXref;
    private String sourcePath;
    protected PdfTokenizer tokens;
    protected PdfDictionary trailer;
    private boolean unethicalReading;
    protected boolean xrefStm;

    public PdfReader(IRandomAccessSource byteSource, ReaderProperties properties2) throws IOException {
        this.encrypted = false;
        this.rebuiltXref = false;
        this.hybridXref = false;
        this.fixedXref = false;
        this.xrefStm = false;
        this.properties = properties2;
        this.tokens = getOffsetTokeniser(byteSource);
    }

    public PdfReader(InputStream is, ReaderProperties properties2) throws IOException {
        this(new RandomAccessSourceFactory().createSource(is), properties2);
    }

    public PdfReader(File file) throws FileNotFoundException, IOException {
        this(file.getAbsolutePath());
    }

    public PdfReader(InputStream is) throws IOException {
        this(is, new ReaderProperties());
    }

    public PdfReader(String filename, ReaderProperties properties2) throws IOException {
        this(new RandomAccessSourceFactory().setForceRead(false).createBestSource(filename), properties2);
        this.sourcePath = filename;
    }

    public PdfReader(String filename) throws IOException {
        this(filename, new ReaderProperties());
    }

    public void close() throws IOException {
        this.tokens.close();
    }

    public PdfReader setUnethicalReading(boolean unethicalReading2) {
        this.unethicalReading = unethicalReading2;
        return this;
    }

    public PdfReader setMemorySavingMode(boolean memorySavingMode2) {
        this.memorySavingMode = memorySavingMode2;
        return this;
    }

    public boolean isCloseStream() {
        return this.tokens.isCloseStream();
    }

    public void setCloseStream(boolean closeStream) {
        this.tokens.setCloseStream(closeStream);
    }

    public boolean hasRebuiltXref() {
        PdfDocument pdfDocument2 = this.pdfDocument;
        if (pdfDocument2 != null && pdfDocument2.getXref().isReadingCompleted()) {
            return this.rebuiltXref;
        }
        throw new PdfException(PdfException.DocumentHasNotBeenReadYet);
    }

    public boolean hasHybridXref() {
        PdfDocument pdfDocument2 = this.pdfDocument;
        if (pdfDocument2 != null && pdfDocument2.getXref().isReadingCompleted()) {
            return this.hybridXref;
        }
        throw new PdfException(PdfException.DocumentHasNotBeenReadYet);
    }

    public boolean hasXrefStm() {
        PdfDocument pdfDocument2 = this.pdfDocument;
        if (pdfDocument2 != null && pdfDocument2.getXref().isReadingCompleted()) {
            return this.xrefStm;
        }
        throw new PdfException(PdfException.DocumentHasNotBeenReadYet);
    }

    public boolean hasFixedXref() {
        PdfDocument pdfDocument2 = this.pdfDocument;
        if (pdfDocument2 != null && pdfDocument2.getXref().isReadingCompleted()) {
            return this.fixedXref;
        }
        throw new PdfException(PdfException.DocumentHasNotBeenReadYet);
    }

    public long getLastXref() {
        PdfDocument pdfDocument2 = this.pdfDocument;
        if (pdfDocument2 != null && pdfDocument2.getXref().isReadingCompleted()) {
            return this.lastXref;
        }
        throw new PdfException(PdfException.DocumentHasNotBeenReadYet);
    }

    public byte[] readStreamBytes(PdfStream stream, boolean decode) throws IOException {
        byte[] b = readStreamBytesRaw(stream);
        if (!decode || b == null) {
            return b;
        }
        return decodeBytes(b, stream);
    }

    public byte[] readStreamBytesRaw(PdfStream stream) throws IOException {
        PdfName type = stream.getAsName(PdfName.Type);
        if (!PdfName.XRefStm.equals(type) && !PdfName.ObjStm.equals(type)) {
            checkPdfStreamLength(stream);
        }
        if (stream.getOffset() <= 0) {
            return null;
        }
        int length = stream.getLength();
        if (length <= 0) {
            return new byte[0];
        }
        RandomAccessFileOrArray file = this.tokens.getSafeFile();
        try {
            file.seek(stream.getOffset());
            byte[] bytes = new byte[length];
            file.readFully(bytes);
            PdfEncryption pdfEncryption = this.decrypt;
            if (pdfEncryption != null && !pdfEncryption.isEmbeddedFilesOnly()) {
                PdfObject filter = stream.get(PdfName.Filter, true);
                boolean skip = false;
                if (filter != null) {
                    if (PdfName.Crypt.equals(filter)) {
                        skip = true;
                    } else if (filter.getType() == 1) {
                        PdfArray filters = (PdfArray) filter;
                        int k = 0;
                        while (true) {
                            if (k < filters.size()) {
                                if (!filters.isEmpty() && PdfName.Crypt.equals(filters.get(k, true))) {
                                    skip = true;
                                    break;
                                }
                                k++;
                            } else {
                                break;
                            }
                        }
                    }
                    filter.release();
                }
                if (!skip) {
                    this.decrypt.setHashKeyForNextObject(stream.getIndirectReference().getObjNumber(), stream.getIndirectReference().getGenNumber());
                    bytes = this.decrypt.decryptByteArray(bytes);
                }
            }
            return bytes;
        } finally {
            try {
                file.close();
            } catch (Exception e) {
            }
        }
    }

    public InputStream readStream(PdfStream stream, boolean decode) throws IOException {
        byte[] bytes = readStreamBytes(stream, decode);
        if (bytes != null) {
            return new ByteArrayInputStream(bytes);
        }
        return null;
    }

    public static byte[] decodeBytes(byte[] b, PdfDictionary streamDictionary) {
        return decodeBytes(b, streamDictionary, FilterHandlers.getDefaultFilterHandlers());
    }

    public static byte[] decodeBytes(byte[] b, PdfDictionary streamDictionary, Map<PdfName, IFilterHandler> filterHandlers) {
        PdfDictionary decodeParams;
        byte[] b2;
        PdfDictionary pdfDictionary = streamDictionary;
        if (b == null) {
            return null;
        }
        PdfObject filter = pdfDictionary.get(PdfName.Filter);
        PdfArray filters = new PdfArray();
        boolean z = true;
        if (filter != null) {
            if (filter.getType() == 6) {
                filters.add(filter);
            } else if (filter.getType() == 1) {
                filters = (PdfArray) filter;
            }
        }
        MemoryLimitsAwareHandler memoryLimitsAwareHandler = null;
        if (streamDictionary.getIndirectReference() != null) {
            memoryLimitsAwareHandler = streamDictionary.getIndirectReference().getDocument().memoryLimitsAwareHandler;
        }
        boolean memoryLimitsAwarenessRequired = memoryLimitsAwareHandler != null && memoryLimitsAwareHandler.isMemoryLimitsAwarenessRequiredOnDecompression(filters);
        if (memoryLimitsAwarenessRequired) {
            memoryLimitsAwareHandler.beginDecompressedPdfStreamProcessing();
        }
        PdfArray dp = new PdfArray();
        PdfObject dpo = pdfDictionary.get(PdfName.DecodeParms);
        byte b3 = 3;
        if (dpo == null || !(dpo.getType() == 3 || dpo.getType() == 1)) {
            if (dpo != null) {
                dpo.release();
            }
            dpo = pdfDictionary.get(PdfName.f1314DP);
        }
        if (dpo != null) {
            if (dpo.getType() == 3) {
                dp.add(dpo);
            } else if (dpo.getType() == 1) {
                dp = (PdfArray) dpo;
            }
            dpo.release();
        }
        int j = 0;
        byte[] b4 = b;
        while (j < filters.size()) {
            PdfName filterName = (PdfName) filters.get(j);
            IFilterHandler filterHandler = filterHandlers.get(filterName);
            if (filterHandler != null) {
                if (j < dp.size()) {
                    PdfObject dpEntry = dp.get(j, z);
                    if (dpEntry == null || dpEntry.getType() == 7) {
                        decodeParams = null;
                    } else if (dpEntry.getType() == b3) {
                        decodeParams = (PdfDictionary) dpEntry;
                    } else {
                        throw new PdfException(PdfException.DecodeParameterType1IsNotSupported).setMessageParams(dpEntry.getClass().toString());
                    }
                } else {
                    decodeParams = null;
                }
                byte[] b5 = filterHandler.decode(b4, filterName, decodeParams, pdfDictionary);
                if (memoryLimitsAwarenessRequired) {
                    b2 = b5;
                    memoryLimitsAwareHandler.considerBytesOccupiedByDecompressedPdfStream((long) b5.length);
                } else {
                    b2 = b5;
                }
                j++;
                b4 = b2;
                z = true;
                b3 = 3;
            } else {
                throw new PdfException(PdfException.Filter1IsNotSupported).setMessageParams(filterName);
            }
        }
        Map<PdfName, IFilterHandler> map = filterHandlers;
        if (memoryLimitsAwarenessRequired) {
            memoryLimitsAwareHandler.endDecompressedPdfStreamProcessing();
        }
        return b4;
    }

    public RandomAccessFileOrArray getSafeFile() {
        return this.tokens.getSafeFile();
    }

    public long getFileLength() throws IOException {
        return this.tokens.getSafeFile().length();
    }

    public boolean isOpenedWithFullPermission() {
        PdfDocument pdfDocument2 = this.pdfDocument;
        if (pdfDocument2 != null && pdfDocument2.getXref().isReadingCompleted()) {
            return !this.encrypted || this.decrypt.isOpenedWithFullPermission() || this.unethicalReading;
        }
        throw new PdfException(PdfException.DocumentHasNotBeenReadYet);
    }

    public long getPermissions() {
        PdfDocument pdfDocument2 = this.pdfDocument;
        if (pdfDocument2 == null || !pdfDocument2.getXref().isReadingCompleted()) {
            throw new PdfException(PdfException.DocumentHasNotBeenReadYet);
        } else if (!this.encrypted || this.decrypt.getPermissions() == null) {
            return 0;
        } else {
            return this.decrypt.getPermissions().longValue();
        }
    }

    public int getCryptoMode() {
        PdfDocument pdfDocument2 = this.pdfDocument;
        if (pdfDocument2 == null || !pdfDocument2.getXref().isReadingCompleted()) {
            throw new PdfException(PdfException.DocumentHasNotBeenReadYet);
        }
        PdfEncryption pdfEncryption = this.decrypt;
        if (pdfEncryption == null) {
            return -1;
        }
        return pdfEncryption.getCryptoMode();
    }

    public PdfAConformanceLevel getPdfAConformanceLevel() {
        return this.pdfAConformanceLevel;
    }

    public byte[] computeUserPassword() {
        PdfDocument pdfDocument2 = this.pdfDocument;
        if (pdfDocument2 == null || !pdfDocument2.getXref().isReadingCompleted()) {
            throw new PdfException(PdfException.DocumentHasNotBeenReadYet);
        } else if (!this.encrypted || !this.decrypt.isOpenedWithFullPermission()) {
            return null;
        } else {
            return this.decrypt.computeUserPassword(this.properties.password);
        }
    }

    public byte[] getOriginalFileId() {
        PdfDocument pdfDocument2 = this.pdfDocument;
        if (pdfDocument2 == null || !pdfDocument2.getXref().isReadingCompleted()) {
            throw new PdfException(PdfException.DocumentHasNotBeenReadYet);
        }
        PdfArray id = this.trailer.getAsArray(PdfName.f1341ID);
        if (id == null || id.size() != 2) {
            return new byte[0];
        }
        return ByteUtils.getIsoBytes(id.getAsString(0).getValue());
    }

    public byte[] getModifiedFileId() {
        PdfDocument pdfDocument2 = this.pdfDocument;
        if (pdfDocument2 == null || !pdfDocument2.getXref().isReadingCompleted()) {
            throw new PdfException(PdfException.DocumentHasNotBeenReadYet);
        }
        PdfArray id = this.trailer.getAsArray(PdfName.f1341ID);
        if (id == null || id.size() != 2) {
            return new byte[0];
        }
        return ByteUtils.getIsoBytes(id.getAsString(1).getValue());
    }

    public boolean isEncrypted() {
        PdfDocument pdfDocument2 = this.pdfDocument;
        if (pdfDocument2 != null && pdfDocument2.getXref().isReadingCompleted()) {
            return this.encrypted;
        }
        throw new PdfException(PdfException.DocumentHasNotBeenReadYet);
    }

    /* access modifiers changed from: protected */
    public void readPdf() throws IOException {
        String version = this.tokens.checkPdfHeader();
        try {
            this.headerPdfVersion = PdfVersion.fromString(version);
            try {
                readXref();
            } catch (RuntimeException ex) {
                LoggerFactory.getLogger((Class<?>) PdfReader.class).error("Error occurred while reading cross reference table. Cross reference table will be rebuilt.", (Throwable) ex);
                rebuildXref();
            }
            this.pdfDocument.getXref().markReadingCompleted();
            readDecryptObj();
        } catch (IllegalArgumentException e) {
            throw new PdfException(PdfException.PdfVersionNotValid, (Object) version);
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    public void readObjectStream(PdfStream objectStream) throws IOException {
        PdfObject obj;
        int objectStreamNumber = objectStream.getIndirectReference().getObjNumber();
        int first = objectStream.getAsNumber(PdfName.First).intValue();
        int n = objectStream.getAsNumber(PdfName.f1357N).intValue();
        byte[] bytes = readStreamBytes(objectStream, true);
        PdfTokenizer saveTokens = this.tokens;
        try {
            this.tokens = new PdfTokenizer(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(bytes)));
            int[] address = new int[n];
            int[] objNumber = new int[n];
            boolean ok = true;
            int k = 0;
            while (true) {
                if (k >= n) {
                    break;
                }
                ok = this.tokens.nextToken();
                if (!ok) {
                    break;
                } else if (this.tokens.getTokenType() != PdfTokenizer.TokenType.Number) {
                    ok = false;
                    break;
                } else {
                    objNumber[k] = this.tokens.getIntValue();
                    ok = this.tokens.nextToken();
                    if (!ok) {
                        break;
                    } else if (this.tokens.getTokenType() != PdfTokenizer.TokenType.Number) {
                        ok = false;
                        break;
                    } else {
                        address[k] = this.tokens.getIntValue() + first;
                        k++;
                    }
                }
            }
            if (ok) {
                for (int k2 = 0; k2 < n; k2++) {
                    this.tokens.seek((long) address[k2]);
                    this.tokens.nextToken();
                    PdfIndirectReference reference = this.pdfDocument.getXref().get(objNumber[k2]);
                    if (reference.refersTo == null) {
                        if (reference.getObjStreamNumber() == objectStreamNumber) {
                            if (this.tokens.getTokenType() == PdfTokenizer.TokenType.Number) {
                                obj = new PdfNumber(this.tokens.getByteContent());
                            } else {
                                this.tokens.seek((long) address[k2]);
                                obj = readObject(false, true);
                            }
                            reference.setRefersTo(obj);
                            obj.setIndirectReference(reference);
                        }
                    }
                }
                objectStream.getIndirectReference().setState(16);
                this.tokens = saveTokens;
                return;
            }
            throw new PdfException(PdfException.ErrorWhileReadingObjectStream);
        } catch (Throwable th) {
            this.tokens = saveTokens;
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public PdfObject readObject(PdfIndirectReference reference) {
        return readObject(reference, true);
    }

    /* access modifiers changed from: protected */
    public PdfObject readObject(boolean readAsDirect) throws IOException {
        return readObject(readAsDirect, false);
    }

    /* access modifiers changed from: protected */
    public PdfObject readReference(boolean readAsDirect) {
        int num = this.tokens.getObjNr();
        if (num < 0) {
            return createPdfNullInstance(readAsDirect);
        }
        PdfXrefTable table = this.pdfDocument.getXref();
        PdfIndirectReference reference = table.get(num);
        Class<PdfReader> cls = PdfReader.class;
        if (reference != null) {
            if (reference.isFree()) {
                LoggerFactory.getLogger((Class<?>) cls).warn(MessageFormatUtil.format(LogMessageConstant.INVALID_INDIRECT_REFERENCE, Integer.valueOf(this.tokens.getObjNr()), Integer.valueOf(this.tokens.getGenNr())));
                return createPdfNullInstance(readAsDirect);
            } else if (reference.getGenNumber() == this.tokens.getGenNr()) {
                return reference;
            } else {
                if (this.fixedXref) {
                    LoggerFactory.getLogger((Class<?>) cls).warn(MessageFormatUtil.format(LogMessageConstant.INVALID_INDIRECT_REFERENCE, Integer.valueOf(this.tokens.getObjNr()), Integer.valueOf(this.tokens.getGenNr())));
                    return createPdfNullInstance(readAsDirect);
                }
                throw new PdfException(PdfException.InvalidIndirectReference1, (Object) MessageFormatUtil.format("{0} {1} R", Integer.valueOf(reference.getObjNumber()), Integer.valueOf(reference.getGenNumber())));
            }
        } else if (table.isReadingCompleted()) {
            LoggerFactory.getLogger((Class<?>) cls).warn(MessageFormatUtil.format(LogMessageConstant.INVALID_INDIRECT_REFERENCE, Integer.valueOf(this.tokens.getObjNr()), Integer.valueOf(this.tokens.getGenNr())));
            return createPdfNullInstance(readAsDirect);
        } else {
            return table.add((PdfIndirectReference) new PdfIndirectReference(this.pdfDocument, num, this.tokens.getGenNr(), 0).setState(4));
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00a1 A[LOOP:1: B:31:0x00a1->B:64:0x00a1, LOOP_START] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00d2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.kernel.pdf.PdfObject readObject(boolean r10, boolean r11) throws java.io.IOException {
        /*
            r9 = this;
            com.itextpdf.io.source.PdfTokenizer r0 = r9.tokens
            r0.nextValidToken()
            com.itextpdf.io.source.PdfTokenizer r0 = r9.tokens
            com.itextpdf.io.source.PdfTokenizer$TokenType r0 = r0.getTokenType()
            int[] r1 = com.itextpdf.kernel.pdf.PdfReader.C14281.$SwitchMap$com$itextpdf$io$source$PdfTokenizer$TokenType
            int r2 = r0.ordinal()
            r1 = r1[r2]
            switch(r1) {
                case 1: goto L_0x0079;
                case 2: goto L_0x0074;
                case 3: goto L_0x0068;
                case 4: goto L_0x0037;
                case 5: goto L_0x0032;
                case 6: goto L_0x002d;
                case 7: goto L_0x0025;
                default: goto L_0x0016;
            }
        L_0x0016:
            com.itextpdf.io.source.PdfTokenizer r1 = r9.tokens
            byte[] r2 = com.itextpdf.p026io.source.PdfTokenizer.Null
            boolean r1 = r1.tokenValueEqualsTo(r2)
            if (r1 == 0) goto L_0x00d8
            com.itextpdf.kernel.pdf.PdfObject r1 = r9.createPdfNullInstance(r10)
            return r1
        L_0x0025:
            com.itextpdf.kernel.PdfException r1 = new com.itextpdf.kernel.PdfException
            java.lang.String r2 = "Unexpected end of file."
            r1.<init>((java.lang.String) r2)
            throw r1
        L_0x002d:
            com.itextpdf.kernel.pdf.PdfObject r1 = r9.readReference(r10)
            return r1
        L_0x0032:
            com.itextpdf.kernel.pdf.PdfName r1 = r9.readPdfName(r10)
            return r1
        L_0x0037:
            com.itextpdf.kernel.pdf.PdfString r1 = new com.itextpdf.kernel.pdf.PdfString
            com.itextpdf.io.source.PdfTokenizer r2 = r9.tokens
            byte[] r2 = r2.getByteContent()
            com.itextpdf.io.source.PdfTokenizer r3 = r9.tokens
            boolean r3 = r3.isHexString()
            r1.<init>((byte[]) r2, (boolean) r3)
            boolean r2 = r9.encrypted
            if (r2 == 0) goto L_0x0067
            com.itextpdf.kernel.pdf.PdfEncryption r2 = r9.decrypt
            boolean r2 = r2.isEmbeddedFilesOnly()
            if (r2 != 0) goto L_0x0067
            if (r11 != 0) goto L_0x0067
            com.itextpdf.kernel.pdf.PdfIndirectReference r2 = r9.currentIndirectReference
            int r2 = r2.getObjNumber()
            com.itextpdf.kernel.pdf.PdfIndirectReference r3 = r9.currentIndirectReference
            int r3 = r3.getGenNumber()
            com.itextpdf.kernel.pdf.PdfEncryption r4 = r9.decrypt
            r1.setDecryption(r2, r3, r4)
        L_0x0067:
            return r1
        L_0x0068:
            com.itextpdf.kernel.pdf.PdfNumber r1 = new com.itextpdf.kernel.pdf.PdfNumber
            com.itextpdf.io.source.PdfTokenizer r2 = r9.tokens
            byte[] r2 = r2.getByteContent()
            r1.<init>((byte[]) r2)
            return r1
        L_0x0074:
            com.itextpdf.kernel.pdf.PdfArray r1 = r9.readArray(r11)
            return r1
        L_0x0079:
            com.itextpdf.kernel.pdf.PdfDictionary r1 = r9.readDictionary(r11)
            com.itextpdf.io.source.PdfTokenizer r2 = r9.tokens
            long r2 = r2.getPosition()
        L_0x0083:
            com.itextpdf.io.source.PdfTokenizer r4 = r9.tokens
            boolean r4 = r4.nextToken()
            if (r4 == 0) goto L_0x0095
            com.itextpdf.io.source.PdfTokenizer r5 = r9.tokens
            com.itextpdf.io.source.PdfTokenizer$TokenType r5 = r5.getTokenType()
            com.itextpdf.io.source.PdfTokenizer$TokenType r6 = com.itextpdf.p026io.source.PdfTokenizer.TokenType.Comment
            if (r5 == r6) goto L_0x0083
        L_0x0095:
            if (r4 == 0) goto L_0x00d2
            com.itextpdf.io.source.PdfTokenizer r5 = r9.tokens
            byte[] r6 = com.itextpdf.p026io.source.PdfTokenizer.Stream
            boolean r5 = r5.tokenValueEqualsTo(r6)
            if (r5 == 0) goto L_0x00d2
        L_0x00a1:
            com.itextpdf.io.source.PdfTokenizer r5 = r9.tokens
            int r5 = r5.read()
            r6 = 32
            if (r5 == r6) goto L_0x00a1
            r6 = 9
            if (r5 == r6) goto L_0x00a1
            if (r5 == 0) goto L_0x00a1
            r6 = 12
            if (r5 == r6) goto L_0x00a1
            r6 = 10
            if (r5 == r6) goto L_0x00bf
            com.itextpdf.io.source.PdfTokenizer r7 = r9.tokens
            int r5 = r7.read()
        L_0x00bf:
            if (r5 == r6) goto L_0x00c6
            com.itextpdf.io.source.PdfTokenizer r6 = r9.tokens
            r6.backOnePosition(r5)
        L_0x00c6:
            com.itextpdf.kernel.pdf.PdfStream r6 = new com.itextpdf.kernel.pdf.PdfStream
            com.itextpdf.io.source.PdfTokenizer r7 = r9.tokens
            long r7 = r7.getPosition()
            r6.<init>((long) r7, (com.itextpdf.kernel.pdf.PdfDictionary) r1)
            return r6
        L_0x00d2:
            com.itextpdf.io.source.PdfTokenizer r5 = r9.tokens
            r5.seek(r2)
            return r1
        L_0x00d8:
            com.itextpdf.io.source.PdfTokenizer r1 = r9.tokens
            byte[] r2 = com.itextpdf.p026io.source.PdfTokenizer.True
            boolean r1 = r1.tokenValueEqualsTo(r2)
            if (r1 == 0) goto L_0x00ee
            if (r10 == 0) goto L_0x00e7
            com.itextpdf.kernel.pdf.PdfBoolean r1 = com.itextpdf.kernel.pdf.PdfBoolean.TRUE
            return r1
        L_0x00e7:
            com.itextpdf.kernel.pdf.PdfBoolean r1 = new com.itextpdf.kernel.pdf.PdfBoolean
            r2 = 1
            r1.<init>(r2)
            return r1
        L_0x00ee:
            com.itextpdf.io.source.PdfTokenizer r1 = r9.tokens
            byte[] r2 = com.itextpdf.p026io.source.PdfTokenizer.False
            boolean r1 = r1.tokenValueEqualsTo(r2)
            if (r1 == 0) goto L_0x0104
            if (r10 == 0) goto L_0x00fd
            com.itextpdf.kernel.pdf.PdfBoolean r1 = com.itextpdf.kernel.pdf.PdfBoolean.FALSE
            return r1
        L_0x00fd:
            com.itextpdf.kernel.pdf.PdfBoolean r1 = new com.itextpdf.kernel.pdf.PdfBoolean
            r2 = 0
            r1.<init>(r2)
            return r1
        L_0x0104:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.pdf.PdfReader.readObject(boolean, boolean):com.itextpdf.kernel.pdf.PdfObject");
    }

    /* renamed from: com.itextpdf.kernel.pdf.PdfReader$1 */
    static /* synthetic */ class C14281 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$io$source$PdfTokenizer$TokenType;

        static {
            int[] iArr = new int[PdfTokenizer.TokenType.values().length];
            $SwitchMap$com$itextpdf$io$source$PdfTokenizer$TokenType = iArr;
            try {
                iArr[PdfTokenizer.TokenType.StartDic.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$itextpdf$io$source$PdfTokenizer$TokenType[PdfTokenizer.TokenType.StartArray.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$itextpdf$io$source$PdfTokenizer$TokenType[PdfTokenizer.TokenType.Number.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$itextpdf$io$source$PdfTokenizer$TokenType[PdfTokenizer.TokenType.String.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$itextpdf$io$source$PdfTokenizer$TokenType[PdfTokenizer.TokenType.Name.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$itextpdf$io$source$PdfTokenizer$TokenType[PdfTokenizer.TokenType.Ref.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$itextpdf$io$source$PdfTokenizer$TokenType[PdfTokenizer.TokenType.EndOfFile.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public PdfName readPdfName(boolean readAsDirect) {
        PdfName cachedName;
        if (!readAsDirect || (cachedName = PdfName.staticNames.get(this.tokens.getStringValue())) == null) {
            return new PdfName(this.tokens.getByteContent());
        }
        return cachedName;
    }

    /* access modifiers changed from: protected */
    public PdfDictionary readDictionary(boolean objStm) throws IOException {
        PdfDictionary dic = new PdfDictionary();
        while (true) {
            this.tokens.nextValidToken();
            if (this.tokens.getTokenType() == PdfTokenizer.TokenType.EndDic) {
                return dic;
            }
            if (this.tokens.getTokenType() != PdfTokenizer.TokenType.Name) {
                PdfTokenizer pdfTokenizer = this.tokens;
                pdfTokenizer.throwError(PdfException.DictionaryKey1IsNotAName, pdfTokenizer.getStringValue());
            }
            PdfName name = readPdfName(true);
            PdfObject obj = readObject(true, objStm);
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
    }

    /* access modifiers changed from: protected */
    public PdfArray readArray(boolean objStm) throws IOException {
        PdfArray array = new PdfArray();
        while (true) {
            PdfObject obj = readObject(true, objStm);
            if (obj == null) {
                if (this.tokens.getTokenType() == PdfTokenizer.TokenType.EndArray) {
                    return array;
                }
                if (this.tokens.getTokenType() == PdfTokenizer.TokenType.EndDic) {
                    this.tokens.throwError(PdfException.UnexpectedGtGt, new Object[0]);
                }
            }
            array.add(obj);
        }
    }

    /* access modifiers changed from: protected */
    public void readXref() throws IOException {
        PdfTokenizer pdfTokenizer = this.tokens;
        pdfTokenizer.seek(pdfTokenizer.getStartxref());
        this.tokens.nextToken();
        if (this.tokens.tokenValueEqualsTo(PdfTokenizer.Startxref)) {
            this.tokens.nextToken();
            if (this.tokens.getTokenType() == PdfTokenizer.TokenType.Number) {
                long startxref = this.tokens.getLongValue();
                this.lastXref = startxref;
                this.eofPos = this.tokens.getPosition();
                try {
                    if (readXrefStream(startxref)) {
                        this.xrefStm = true;
                        return;
                    }
                } catch (Exception e) {
                }
                this.pdfDocument.getXref().clear();
                this.tokens.seek(startxref);
                this.trailer = readXrefSection();
                PdfDictionary trailer2 = this.trailer;
                while (true) {
                    PdfNumber prev = (PdfNumber) trailer2.get(PdfName.Prev);
                    if (prev == null) {
                        if (this.trailer.getAsInt(PdfName.Size) == null) {
                            throw new PdfException(PdfException.InvalidXrefTable);
                        }
                        return;
                    } else if (prev.longValue() != startxref) {
                        startxref = prev.longValue();
                        this.tokens.seek(startxref);
                        trailer2 = readXrefSection();
                    } else {
                        throw new PdfException(PdfException.TrailerPrevEntryPointsToItsOwnCrossReferenceSection);
                    }
                }
            } else {
                throw new PdfException(PdfException.PdfStartxrefIsNotFollowedByANumber, (Object) this.tokens);
            }
        } else {
            throw new PdfException("PDF startxref not found.", (Object) this.tokens);
        }
    }

    /* access modifiers changed from: protected */
    public PdfDictionary readXrefSection() throws IOException {
        PdfIndirectReference reference;
        this.tokens.nextValidToken();
        if (!this.tokens.tokenValueEqualsTo(PdfTokenizer.Xref)) {
            this.tokens.throwError(PdfException.XrefSubsectionNotFound, new Object[0]);
        }
        PdfXrefTable xref = this.pdfDocument.getXref();
        while (true) {
            this.tokens.nextValidToken();
            int i = 1;
            if (this.tokens.tokenValueEqualsTo(PdfTokenizer.Trailer)) {
                break;
            }
            if (this.tokens.getTokenType() != PdfTokenizer.TokenType.Number) {
                this.tokens.throwError(PdfException.ObjectNumberOfTheFirstObjectInThisXrefSubsectionNotFound, new Object[0]);
            }
            int start = this.tokens.getIntValue();
            this.tokens.nextValidToken();
            if (this.tokens.getTokenType() != PdfTokenizer.TokenType.Number) {
                this.tokens.throwError(PdfException.NumberOfEntriesInThisXrefSubsectionNotFound, new Object[0]);
            }
            int end = this.tokens.getIntValue() + start;
            int num = start;
            while (num < end) {
                this.tokens.nextValidToken();
                long pos = this.tokens.getLongValue();
                this.tokens.nextValidToken();
                int gen = this.tokens.getIntValue();
                this.tokens.nextValidToken();
                if (pos == 0 && gen == 65535 && num == i && start != 0) {
                    num = 0;
                    end--;
                } else {
                    PdfIndirectReference reference2 = xref.get(num);
                    boolean refReadingState = reference2 != null && reference2.checkState(4) && reference2.getGenNumber() == gen;
                    boolean refFirstEncountered = reference2 == null || (!refReadingState && reference2.getDocument() == null);
                    if (refFirstEncountered) {
                        PdfIndirectReference pdfIndirectReference = reference2;
                        reference = new PdfIndirectReference(this.pdfDocument, num, gen, pos);
                    } else {
                        PdfIndirectReference reference3 = reference2;
                        if (refReadingState) {
                            reference3.setOffset(pos);
                            reference3.clearState(4);
                            reference = reference3;
                        }
                    }
                    if (this.tokens.tokenValueEqualsTo(PdfTokenizer.f1232N)) {
                        if (pos == 0) {
                            this.tokens.throwError(PdfException.FilePosition1CrossReferenceEntryInThisXrefSubsection, new Object[0]);
                        }
                    } else if (!this.tokens.tokenValueEqualsTo(PdfTokenizer.f1231F)) {
                        this.tokens.throwError(PdfException.InvalidCrossReferenceEntryInThisXrefSubsection, new Object[0]);
                    } else if (refFirstEncountered) {
                        reference.setState(2);
                    }
                    if (refFirstEncountered) {
                        xref.add(reference);
                    }
                }
                i = 1;
                num++;
            }
        }
        PdfDictionary trailer2 = (PdfDictionary) readObject(false);
        PdfObject xrs = trailer2.get(PdfName.XRefStm);
        if (xrs != null && xrs.getType() == 8) {
            try {
                readXrefStream((long) ((PdfNumber) xrs).intValue());
                this.xrefStm = true;
                this.hybridXref = true;
            } catch (IOException e) {
                xref.clear();
                throw e;
            }
        }
        return trailer2;
    }

    /* access modifiers changed from: protected */
    public boolean readXrefStream(long ptr) throws IOException {
        PdfArray index;
        PdfStream xrefStream;
        int length;
        int[] wc;
        PdfIndirectReference newReference;
        long ptr2 = ptr;
        while (ptr2 != -1) {
            this.tokens.seek(ptr2);
            if (!this.tokens.nextToken() || this.tokens.getTokenType() != PdfTokenizer.TokenType.Number) {
                return false;
            }
            if (!this.tokens.nextToken()) {
                return false;
            } else if (this.tokens.getTokenType() != PdfTokenizer.TokenType.Number) {
                long j = ptr2;
                return false;
            } else if (!this.tokens.nextToken()) {
                return false;
            } else if (!this.tokens.tokenValueEqualsTo(PdfTokenizer.Obj)) {
                long j2 = ptr2;
                return false;
            } else {
                PdfXrefTable xref = this.pdfDocument.getXref();
                PdfObject object = readObject(false);
                if (object.getType() == 9) {
                    PdfStream xrefStream2 = (PdfStream) object;
                    if (!PdfName.XRef.equals(xrefStream2.get(PdfName.Type))) {
                        return false;
                    }
                    if (this.trailer == null) {
                        PdfDictionary pdfDictionary = new PdfDictionary();
                        this.trailer = pdfDictionary;
                        pdfDictionary.putAll(xrefStream2);
                        this.trailer.remove(PdfName.DecodeParms);
                        this.trailer.remove(PdfName.Filter);
                        this.trailer.remove(PdfName.Prev);
                        this.trailer.remove(PdfName.Length);
                    }
                    int size = ((PdfNumber) xrefStream2.get(PdfName.Size)).intValue();
                    PdfObject obj = xrefStream2.get(PdfName.Index);
                    if (obj == null) {
                        index = new PdfArray();
                        index.add(new PdfNumber(0));
                        index.add(new PdfNumber(size));
                    } else {
                        index = (PdfArray) obj;
                    }
                    PdfArray w = xrefStream2.getAsArray(PdfName.f1409W);
                    long prev = -1;
                    PdfObject obj2 = xrefStream2.get(PdfName.Prev);
                    if (obj2 != null) {
                        prev = ((PdfNumber) obj2).longValue();
                    }
                    xref.setCapacity(size);
                    byte[] b = readStreamBytes(xrefStream2, true);
                    int bptr = 0;
                    int[] wc2 = new int[3];
                    long j3 = ptr2;
                    for (int k = 0; k < 3; k++) {
                        wc2[k] = w.getAsNumber(k).intValue();
                    }
                    int idx = 0;
                    while (idx < index.size()) {
                        int start = index.getAsNumber(idx).intValue();
                        int length2 = index.getAsNumber(idx + 1).intValue();
                        PdfObject object2 = object;
                        xref.setCapacity(start + length2);
                        while (true) {
                            int length3 = length2 - 1;
                            if (length2 > 0) {
                                int type = 1;
                                int bptr2 = 0;
                                if (wc2[0] > 0) {
                                    type = 0;
                                    length = length3;
                                    int k2 = 0;
                                    while (true) {
                                        xrefStream = xrefStream2;
                                        if (k2 < wc2[bptr2]) {
                                            type = (type << 8) + (b[bptr] & UByte.MAX_VALUE);
                                            k2++;
                                            bptr++;
                                            xrefStream2 = xrefStream;
                                            bptr2 = 0;
                                        }
                                    }
                                } else {
                                    length = length3;
                                    xrefStream = xrefStream2;
                                }
                                long field2 = 0;
                                int size2 = size;
                                int k3 = 0;
                                while (true) {
                                    PdfObject obj3 = obj2;
                                    if (k3 < wc2[1]) {
                                        field2 = (field2 << 8) + ((long) (b[bptr] & UByte.MAX_VALUE));
                                        k3++;
                                        bptr++;
                                        obj2 = obj3;
                                        index = index;
                                    } else {
                                        PdfArray index2 = index;
                                        int field3 = 0;
                                        int k4 = 0;
                                        while (true) {
                                            PdfArray w2 = w;
                                            if (k4 < wc2[2]) {
                                                field3 = (field3 << 8) + (b[bptr] & UByte.MAX_VALUE);
                                                k4++;
                                                bptr++;
                                                w = w2;
                                            } else {
                                                int base = start;
                                                switch (type) {
                                                    case 0:
                                                        wc = wc2;
                                                        int i = type;
                                                        newReference = (PdfIndirectReference) new PdfIndirectReference(this.pdfDocument, base, field3, field2).setState(2);
                                                        break;
                                                    case 1:
                                                        wc = wc2;
                                                        int i2 = type;
                                                        newReference = new PdfIndirectReference(this.pdfDocument, base, field3, field2);
                                                        break;
                                                    case 2:
                                                        wc = wc2;
                                                        int i3 = type;
                                                        newReference = new PdfIndirectReference(this.pdfDocument, base, 0, (long) field3);
                                                        newReference.setObjStreamNumber((int) field2);
                                                        break;
                                                    default:
                                                        int[] iArr = wc2;
                                                        int i4 = type;
                                                        throw new PdfException(PdfException.InvalidXrefStream);
                                                }
                                                PdfIndirectReference reference = xref.get(base);
                                                boolean refReadingState = reference != null && reference.checkState(4) && reference.getGenNumber() == newReference.getGenNumber();
                                                if (reference == null || (!refReadingState && reference.getDocument() == null)) {
                                                    xref.add(newReference);
                                                    long j4 = field2;
                                                } else if (refReadingState) {
                                                    long j5 = field2;
                                                    reference.setOffset(newReference.getOffset());
                                                    reference.setObjStreamNumber(newReference.getObjStreamNumber());
                                                    reference.clearState(4);
                                                }
                                                start++;
                                                size = size2;
                                                length2 = length;
                                                xrefStream2 = xrefStream;
                                                obj2 = obj3;
                                                index = index2;
                                                w = w2;
                                                wc2 = wc;
                                            }
                                        }
                                    }
                                }
                            } else {
                                int i5 = length3;
                                PdfStream pdfStream = xrefStream2;
                                int i6 = size;
                                PdfObject pdfObject = obj2;
                                PdfArray pdfArray = index;
                                PdfArray pdfArray2 = w;
                                idx += 2;
                                object = object2;
                            }
                        }
                    }
                    PdfObject pdfObject2 = object;
                    PdfStream pdfStream2 = xrefStream2;
                    int i7 = size;
                    PdfObject pdfObject3 = obj2;
                    PdfArray pdfArray3 = index;
                    PdfArray pdfArray4 = w;
                    ptr2 = prev;
                } else {
                    long j6 = ptr2;
                    return false;
                }
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void fixXref() throws IOException {
        int[] obj;
        this.fixedXref = true;
        PdfXrefTable xref = this.pdfDocument.getXref();
        this.tokens.seek(0);
        ByteBuffer buffer = new ByteBuffer(24);
        PdfTokenizer lineTokeniser = new PdfTokenizer(new RandomAccessFileOrArray(new ReusableRandomAccessSource(buffer)));
        while (true) {
            long pos = this.tokens.getPosition();
            buffer.reset();
            if (this.tokens.readLineSegment(buffer, true)) {
                if (buffer.get(0) >= 48 && buffer.get(0) <= 57 && (obj = PdfTokenizer.checkObjectStart(lineTokeniser)) != null) {
                    int num = obj[0];
                    int gen = obj[1];
                    PdfIndirectReference reference = xref.get(num);
                    if (reference != null && reference.getGenNumber() == gen) {
                        reference.fixOffset(pos);
                    }
                }
            } else {
                return;
            }
        }
    }

    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r2v1, types: [boolean, int] */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* access modifiers changed from: protected */
    public void rebuildXref() throws IOException {
        boolean z;
        ? r2 = 0;
        this.xrefStm = false;
        this.hybridXref = false;
        this.rebuiltXref = true;
        PdfXrefTable xref = this.pdfDocument.getXref();
        xref.clear();
        this.tokens.seek(0);
        this.trailer = null;
        ByteBuffer buffer = new ByteBuffer(24);
        PdfTokenizer lineTokeniser = new PdfTokenizer(new RandomAccessFileOrArray(new ReusableRandomAccessSource(buffer)));
        while (true) {
            long pos = this.tokens.getPosition();
            buffer.reset();
            if (!this.tokens.readLineSegment(buffer, true)) {
                break;
            }
            if (buffer.get(r2) == 116) {
                if (!PdfTokenizer.checkTrailer(buffer)) {
                    z = r2;
                    r2 = z;
                } else {
                    this.tokens.seek(pos);
                    this.tokens.nextToken();
                    long pos2 = this.tokens.getPosition();
                    try {
                        PdfDictionary dic = (PdfDictionary) readObject((boolean) r2);
                        if (dic.get(PdfName.Root, r2) != null) {
                            this.trailer = dic;
                        } else {
                            this.tokens.seek(pos2);
                        }
                    } catch (Exception e) {
                        this.tokens.seek(pos2);
                    }
                }
            } else if (buffer.get(r2) >= 48 && buffer.get(r2) <= 57) {
                int[] obj = PdfTokenizer.checkObjectStart(lineTokeniser);
                if (obj == null) {
                    z = r2;
                    r2 = z;
                } else {
                    int num = obj[r2];
                    int gen = obj[1];
                    if (xref.get(num) == null || xref.get(num).getGenNumber() <= gen) {
                        int i = gen;
                        PdfIndirectReference pdfIndirectReference = r7;
                        PdfIndirectReference pdfIndirectReference2 = new PdfIndirectReference(this.pdfDocument, num, gen, pos);
                        xref.add(pdfIndirectReference);
                    }
                }
            }
            z = false;
            r2 = z;
        }
        if (this.trailer == null) {
            throw new PdfException(PdfException.TrailerNotFound);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isMemorySavingMode() {
        return this.memorySavingMode;
    }

    private void readDecryptObj() {
        PdfDictionary enc;
        if (!this.encrypted && (enc = this.trailer.getAsDictionary(PdfName.Encrypt)) != null) {
            this.encrypted = true;
            PdfName filter = enc.getAsName(PdfName.Filter);
            if (PdfName.Adobe_PubSec.equals(filter)) {
                if (this.properties.certificate != null) {
                    this.decrypt = new PdfEncryption(enc, this.properties.certificateKey, this.properties.certificate, this.properties.certificateKeyProvider, this.properties.externalDecryptionProcess);
                    return;
                }
                throw new PdfException(PdfException.f1240xf3889bd1);
            } else if (PdfName.Standard.equals(filter)) {
                this.decrypt = new PdfEncryption(enc, this.properties.password, getOriginalFileId());
            } else {
                throw new UnsupportedSecurityHandlerException(MessageFormatUtil.format(UnsupportedSecurityHandlerException.UnsupportedSecurityHandler, filter));
            }
        }
    }

    private static PdfTokenizer getOffsetTokeniser(IRandomAccessSource byteSource) throws IOException {
        PdfTokenizer tok = new PdfTokenizer(new RandomAccessFileOrArray(byteSource));
        int offset = tok.getHeaderOffset();
        if (offset != 0) {
            return new PdfTokenizer(new RandomAccessFileOrArray(new WindowRandomAccessSource(byteSource, (long) offset)));
        }
        return tok;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.itextpdf.kernel.pdf.PdfObject readObject(com.itextpdf.kernel.pdf.PdfIndirectReference r8, boolean r9) {
        /*
            r7 = this;
            r0 = 0
            if (r8 != 0) goto L_0x0004
            return r0
        L_0x0004:
            com.itextpdf.kernel.pdf.PdfObject r1 = r8.refersTo
            if (r1 == 0) goto L_0x000b
            com.itextpdf.kernel.pdf.PdfObject r0 = r8.refersTo
            return r0
        L_0x000b:
            r7.currentIndirectReference = r8     // Catch:{ IOException -> 0x0098 }
            int r1 = r8.getObjStreamNumber()     // Catch:{ IOException -> 0x0098 }
            r2 = 0
            if (r1 <= 0) goto L_0x002e
            com.itextpdf.kernel.pdf.PdfDocument r0 = r7.pdfDocument     // Catch:{ IOException -> 0x0098 }
            com.itextpdf.kernel.pdf.PdfXrefTable r0 = r0.getXref()     // Catch:{ IOException -> 0x0098 }
            int r1 = r8.getObjStreamNumber()     // Catch:{ IOException -> 0x0098 }
            com.itextpdf.kernel.pdf.PdfIndirectReference r0 = r0.get(r1)     // Catch:{ IOException -> 0x0098 }
            com.itextpdf.kernel.pdf.PdfObject r0 = r0.getRefersTo(r2)     // Catch:{ IOException -> 0x0098 }
            com.itextpdf.kernel.pdf.PdfStream r0 = (com.itextpdf.kernel.pdf.PdfStream) r0     // Catch:{ IOException -> 0x0098 }
            r7.readObjectStream(r0)     // Catch:{ IOException -> 0x0098 }
            com.itextpdf.kernel.pdf.PdfObject r1 = r8.refersTo     // Catch:{ IOException -> 0x0098 }
            return r1
        L_0x002e:
            long r3 = r8.getOffset()     // Catch:{ IOException -> 0x0098 }
            r5 = 0
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 <= 0) goto L_0x0097
            com.itextpdf.io.source.PdfTokenizer r1 = r7.tokens     // Catch:{ RuntimeException -> 0x007d }
            long r3 = r8.getOffset()     // Catch:{ RuntimeException -> 0x007d }
            r1.seek(r3)     // Catch:{ RuntimeException -> 0x007d }
            com.itextpdf.io.source.PdfTokenizer r1 = r7.tokens     // Catch:{ RuntimeException -> 0x007d }
            r1.nextValidToken()     // Catch:{ RuntimeException -> 0x007d }
            com.itextpdf.io.source.PdfTokenizer r1 = r7.tokens     // Catch:{ RuntimeException -> 0x007d }
            com.itextpdf.io.source.PdfTokenizer$TokenType r1 = r1.getTokenType()     // Catch:{ RuntimeException -> 0x007d }
            com.itextpdf.io.source.PdfTokenizer$TokenType r3 = com.itextpdf.p026io.source.PdfTokenizer.TokenType.Obj     // Catch:{ RuntimeException -> 0x007d }
            if (r1 != r3) goto L_0x0068
            com.itextpdf.io.source.PdfTokenizer r1 = r7.tokens     // Catch:{ RuntimeException -> 0x007d }
            int r1 = r1.getObjNr()     // Catch:{ RuntimeException -> 0x007d }
            int r3 = r8.getObjNumber()     // Catch:{ RuntimeException -> 0x007d }
            if (r1 != r3) goto L_0x0068
            com.itextpdf.io.source.PdfTokenizer r1 = r7.tokens     // Catch:{ RuntimeException -> 0x007d }
            int r1 = r1.getGenNr()     // Catch:{ RuntimeException -> 0x007d }
            int r3 = r8.getGenNumber()     // Catch:{ RuntimeException -> 0x007d }
            if (r1 == r3) goto L_0x0078
        L_0x0068:
            com.itextpdf.io.source.PdfTokenizer r1 = r7.tokens     // Catch:{ RuntimeException -> 0x007d }
            java.lang.String r3 = "Invalid offset for object {0}."
            r4 = 1
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ RuntimeException -> 0x007d }
            java.lang.String r5 = r8.toString()     // Catch:{ RuntimeException -> 0x007d }
            r4[r2] = r5     // Catch:{ RuntimeException -> 0x007d }
            r1.throwError(r3, r4)     // Catch:{ RuntimeException -> 0x007d }
        L_0x0078:
            com.itextpdf.kernel.pdf.PdfObject r1 = r7.readObject((boolean) r2)     // Catch:{ RuntimeException -> 0x007d }
            goto L_0x008e
        L_0x007d:
            r1 = move-exception
            if (r9 == 0) goto L_0x0095
            int r3 = r8.getObjStreamNumber()     // Catch:{ IOException -> 0x0098 }
            if (r3 != 0) goto L_0x0095
            r7.fixXref()     // Catch:{ IOException -> 0x0098 }
            com.itextpdf.kernel.pdf.PdfObject r2 = r7.readObject((com.itextpdf.kernel.pdf.PdfIndirectReference) r8, (boolean) r2)     // Catch:{ IOException -> 0x0098 }
            r1 = r2
        L_0x008e:
            if (r1 == 0) goto L_0x0094
            com.itextpdf.kernel.pdf.PdfObject r0 = r1.setIndirectReference(r8)     // Catch:{ IOException -> 0x0098 }
        L_0x0094:
            return r0
        L_0x0095:
            throw r1     // Catch:{ IOException -> 0x0098 }
        L_0x0097:
            return r0
        L_0x0098:
            r0 = move-exception
            com.itextpdf.kernel.PdfException r1 = new com.itextpdf.kernel.PdfException
            java.lang.String r2 = "Cannot read PdfObject."
            r1.<init>((java.lang.String) r2, (java.lang.Throwable) r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.pdf.PdfReader.readObject(com.itextpdf.kernel.pdf.PdfIndirectReference, boolean):com.itextpdf.kernel.pdf.PdfObject");
    }

    private void checkPdfStreamLength(PdfStream pdfStream) throws IOException {
        long pos;
        PdfStream pdfStream2 = pdfStream;
        if (correctStreamLength) {
            long fileLength = this.tokens.length();
            long start = pdfStream.getOffset();
            boolean calc = false;
            int streamLength = 0;
            PdfNumber pdfNumber = pdfStream2.getAsNumber(PdfName.Length);
            if (pdfNumber != null) {
                streamLength = pdfNumber.intValue();
                if (((long) streamLength) + start > fileLength - 20) {
                    calc = true;
                } else {
                    this.tokens.seek(((long) streamLength) + start);
                    String line = this.tokens.readString(20);
                    if (!line.startsWith(endstream2) && !line.startsWith(endstream3) && !line.startsWith(endstream4) && !line.startsWith(endstream1)) {
                        calc = true;
                    }
                }
            } else {
                pdfNumber = new PdfNumber(0);
                pdfStream2.put(PdfName.Length, pdfNumber);
                calc = true;
            }
            if (calc) {
                ByteBuffer line2 = new ByteBuffer(16);
                this.tokens.seek(start);
                while (true) {
                    pos = this.tokens.getPosition();
                    line2.reset();
                    if (!this.tokens.readLineSegment(line2, false)) {
                        long j = fileLength;
                        boolean z = calc;
                        break;
                    } else if (line2.startsWith(endstream)) {
                        streamLength = (int) (pos - start);
                        long j2 = fileLength;
                        boolean z2 = calc;
                        break;
                    } else if (line2.startsWith(endobj)) {
                        long j3 = fileLength;
                        this.tokens.seek(pos - 16);
                        int index = this.tokens.readString(16).indexOf(endstream1);
                        if (index >= 0) {
                            boolean z3 = calc;
                            int i = streamLength;
                            pos = (pos - 16) + ((long) index);
                        } else {
                            int i2 = streamLength;
                        }
                        streamLength = (int) (pos - start);
                    } else {
                        boolean z4 = calc;
                        int i3 = streamLength;
                    }
                }
                this.tokens.seek(pos - 2);
                if (this.tokens.read() == 13) {
                    streamLength--;
                }
                this.tokens.seek(pos - 1);
                if (this.tokens.read() == 10) {
                    streamLength--;
                }
                pdfNumber.setValue(streamLength);
                pdfStream2.updateLength(streamLength);
                return;
            }
            boolean z5 = calc;
            int i4 = streamLength;
        }
    }

    private PdfObject createPdfNullInstance(boolean readAsDirect) {
        if (readAsDirect) {
            return PdfNull.PDF_NULL;
        }
        return new PdfNull();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (this.sourcePath != null && this.tokens == null) {
            this.tokens = getOffsetTokeniser(new RandomAccessSourceFactory().setForceRead(false).createBestSource(this.sourcePath));
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        if (this.sourcePath != null) {
            PdfTokenizer tempTokens = this.tokens;
            this.tokens = null;
            out.defaultWriteObject();
            this.tokens = tempTokens;
            return;
        }
        out.defaultWriteObject();
    }

    protected static class ReusableRandomAccessSource implements IRandomAccessSource {
        private ByteBuffer buffer;

        public ReusableRandomAccessSource(ByteBuffer buffer2) {
            if (buffer2 != null) {
                this.buffer = buffer2;
                return;
            }
            throw new IllegalArgumentException("Passed byte buffer can not be null.");
        }

        public int get(long offset) {
            if (offset >= ((long) this.buffer.size())) {
                return -1;
            }
            return this.buffer.getInternalBuffer()[(int) offset] & UByte.MAX_VALUE;
        }

        public int get(long offset, byte[] bytes, int off, int len) {
            ByteBuffer byteBuffer = this.buffer;
            if (byteBuffer == null) {
                throw new IllegalStateException("Already closed");
            } else if (offset >= ((long) byteBuffer.size())) {
                return -1;
            } else {
                if (((long) len) + offset > ((long) this.buffer.size())) {
                    len = (int) (((long) this.buffer.size()) - offset);
                }
                System.arraycopy(this.buffer.getInternalBuffer(), (int) offset, bytes, off, len);
                return len;
            }
        }

        public long length() {
            return (long) this.buffer.size();
        }

        public void close() throws IOException {
            this.buffer = null;
        }
    }
}
