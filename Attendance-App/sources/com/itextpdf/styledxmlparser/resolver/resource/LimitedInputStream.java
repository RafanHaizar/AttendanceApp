package com.itextpdf.styledxmlparser.resolver.resource;

import com.itextpdf.styledxmlparser.StyledXmlParserExceptionMessage;
import java.io.IOException;
import java.io.InputStream;

class LimitedInputStream extends InputStream {
    private InputStream inputStream;
    private boolean isLimitViolated;
    private boolean isStreamRead;
    private long readingByteLimit;

    public LimitedInputStream(InputStream inputStream2, long readingByteLimit2) {
        if (readingByteLimit2 >= 0) {
            this.isStreamRead = false;
            this.isLimitViolated = false;
            this.inputStream = inputStream2;
            this.readingByteLimit = readingByteLimit2;
            return;
        }
        throw new IllegalArgumentException(StyledXmlParserExceptionMessage.READING_BYTE_LIMIT_MUST_NOT_BE_LESS_ZERO);
    }

    public int read() throws IOException {
        if (this.isStreamRead) {
            return -1;
        }
        if (!this.isLimitViolated) {
            int nextByte = this.inputStream.read();
            this.readingByteLimit--;
            checkReadingByteLimit(nextByte);
            return nextByte;
        }
        throw new ReadingByteLimitException();
    }

    public int read(byte[] b) throws IOException {
        int numberOfReadingBytes;
        byte[] validArray;
        if (this.isStreamRead) {
            return -1;
        }
        if (!this.isLimitViolated) {
            long j = this.readingByteLimit;
            if (((long) b.length) > j) {
                if (j == 0) {
                    validArray = new byte[1];
                } else {
                    validArray = new byte[((int) j)];
                }
                numberOfReadingBytes = this.inputStream.read(validArray);
                if (numberOfReadingBytes != -1) {
                    System.arraycopy(validArray, 0, b, 0, numberOfReadingBytes);
                }
            } else {
                numberOfReadingBytes = this.inputStream.read(b);
            }
            this.readingByteLimit -= (long) numberOfReadingBytes;
            checkReadingByteLimit(numberOfReadingBytes);
            return numberOfReadingBytes;
        }
        throw new ReadingByteLimitException();
    }

    public int read(byte[] b, int off, int len) throws IOException {
        if (this.isStreamRead) {
            return -1;
        }
        if (!this.isLimitViolated) {
            long j = this.readingByteLimit;
            if (((long) len) > j) {
                if (j == 0) {
                    len = 1;
                } else {
                    len = (int) j;
                }
            }
            int numberOfReadingBytes = this.inputStream.read(b, off, len);
            this.readingByteLimit -= (long) numberOfReadingBytes;
            checkReadingByteLimit(numberOfReadingBytes);
            return numberOfReadingBytes;
        }
        throw new ReadingByteLimitException();
    }

    public void close() throws IOException {
        this.inputStream.close();
    }

    public long skip(long n) throws IOException {
        return this.inputStream.skip(n);
    }

    public int available() throws IOException {
        return this.inputStream.available();
    }

    public synchronized void mark(int readlimit) {
    }

    public synchronized void reset() throws IOException {
    }

    public boolean markSupported() {
        return false;
    }

    private void checkReadingByteLimit(int byteValue) throws ReadingByteLimitException {
        if (byteValue == -1) {
            this.isStreamRead = true;
        } else if (this.readingByteLimit < 0) {
            this.isLimitViolated = true;
            throw new ReadingByteLimitException();
        }
    }
}
