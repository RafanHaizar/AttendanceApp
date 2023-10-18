package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.PdfException;
import java.io.Serializable;
import java.util.HashSet;

public class MemoryLimitsAwareHandler implements Serializable {
    private static final int SINGLE_DECOMPRESSED_PDF_STREAM_MIN_SIZE = 21474836;
    private static final int SINGLE_SCALE_COEFFICIENT = 100;
    private static final long SUM_OF_DECOMPRESSED_PDF_STREAMW_MIN_SIZE = 107374182;
    private static final int SUM_SCALE_COEFFICIENT = 500;
    private static final long serialVersionUID = 2499046471291214639L;
    private long allMemoryUsedForDecompression;
    boolean considerCurrentPdfStream;
    private long maxSizeOfDecompressedPdfStreamsSum;
    private int maxSizeOfSingleDecompressedPdfStream;
    private long memoryUsedForCurrentPdfStreamDecompression;

    public MemoryLimitsAwareHandler() {
        this.allMemoryUsedForDecompression = 0;
        this.memoryUsedForCurrentPdfStreamDecompression = 0;
        this.considerCurrentPdfStream = false;
        this.maxSizeOfSingleDecompressedPdfStream = SINGLE_DECOMPRESSED_PDF_STREAM_MIN_SIZE;
        this.maxSizeOfDecompressedPdfStreamsSum = SUM_OF_DECOMPRESSED_PDF_STREAMW_MIN_SIZE;
    }

    public MemoryLimitsAwareHandler(long documentSize) {
        this.allMemoryUsedForDecompression = 0;
        this.memoryUsedForCurrentPdfStreamDecompression = 0;
        this.considerCurrentPdfStream = false;
        this.maxSizeOfSingleDecompressedPdfStream = (int) calculateDefaultParameter(documentSize, 100, 21474836);
        this.maxSizeOfDecompressedPdfStreamsSum = calculateDefaultParameter(documentSize, 500, SUM_OF_DECOMPRESSED_PDF_STREAMW_MIN_SIZE);
    }

    public int getMaxSizeOfSingleDecompressedPdfStream() {
        return this.maxSizeOfSingleDecompressedPdfStream;
    }

    public MemoryLimitsAwareHandler setMaxSizeOfSingleDecompressedPdfStream(int maxSizeOfSingleDecompressedPdfStream2) {
        this.maxSizeOfSingleDecompressedPdfStream = maxSizeOfSingleDecompressedPdfStream2;
        return this;
    }

    public long getMaxSizeOfDecompressedPdfStreamsSum() {
        return this.maxSizeOfDecompressedPdfStreamsSum;
    }

    public MemoryLimitsAwareHandler setMaxSizeOfDecompressedPdfStreamsSum(long maxSizeOfDecompressedPdfStreamsSum2) {
        this.maxSizeOfDecompressedPdfStreamsSum = maxSizeOfDecompressedPdfStreamsSum2;
        return this;
    }

    public boolean isMemoryLimitsAwarenessRequiredOnDecompression(PdfArray filters) {
        HashSet<PdfName> filterSet = new HashSet<>();
        for (int index = 0; index < filters.size(); index++) {
            if (!filterSet.add(filters.getAsName(index))) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public MemoryLimitsAwareHandler considerBytesOccupiedByDecompressedPdfStream(long numOfOccupiedBytes) {
        if (this.considerCurrentPdfStream && this.memoryUsedForCurrentPdfStreamDecompression < numOfOccupiedBytes) {
            this.memoryUsedForCurrentPdfStreamDecompression = numOfOccupiedBytes;
            if (numOfOccupiedBytes > ((long) this.maxSizeOfSingleDecompressedPdfStream)) {
                throw new MemoryLimitsAwareException(PdfException.DuringDecompressionSingleStreamOccupiedMoreMemoryThanAllowed);
            }
        }
        return this;
    }

    /* access modifiers changed from: package-private */
    public MemoryLimitsAwareHandler beginDecompressedPdfStreamProcessing() {
        ensureCurrentStreamIsReset();
        this.considerCurrentPdfStream = true;
        return this;
    }

    /* access modifiers changed from: package-private */
    public MemoryLimitsAwareHandler endDecompressedPdfStreamProcessing() {
        long j = this.allMemoryUsedForDecompression + this.memoryUsedForCurrentPdfStreamDecompression;
        this.allMemoryUsedForDecompression = j;
        if (j <= this.maxSizeOfDecompressedPdfStreamsSum) {
            ensureCurrentStreamIsReset();
            this.considerCurrentPdfStream = false;
            return this;
        }
        throw new MemoryLimitsAwareException(PdfException.f1242xd7ab4c0c);
    }

    /* access modifiers changed from: package-private */
    public long getAllMemoryUsedForDecompression() {
        return this.allMemoryUsedForDecompression;
    }

    private static long calculateDefaultParameter(long documentSize, int scale, long min) {
        long result = ((long) scale) * documentSize;
        if (result < min) {
            result = min;
        }
        if (result > ((long) scale) * min) {
            return min * ((long) scale);
        }
        return result;
    }

    private void ensureCurrentStreamIsReset() {
        this.memoryUsedForCurrentPdfStreamDecompression = 0;
    }
}
