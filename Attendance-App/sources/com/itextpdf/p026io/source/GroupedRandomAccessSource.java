package com.itextpdf.p026io.source;

import com.itextpdf.p026io.LogMessageConstant;
import java.io.IOException;
import java.io.Serializable;
import org.slf4j.LoggerFactory;

/* renamed from: com.itextpdf.io.source.GroupedRandomAccessSource */
class GroupedRandomAccessSource implements IRandomAccessSource, Serializable {
    private static final long serialVersionUID = 3417070797788862099L;
    private SourceEntry currentSourceEntry;
    private final long size;
    private final SourceEntry[] sources;

    public GroupedRandomAccessSource(IRandomAccessSource[] sources2) throws IOException {
        this.sources = new SourceEntry[sources2.length];
        long totalSize = 0;
        for (int i = 0; i < sources2.length; i++) {
            this.sources[i] = new SourceEntry(i, sources2[i], totalSize);
            totalSize += sources2[i].length();
        }
        this.size = totalSize;
        SourceEntry sourceEntry = this.sources[sources2.length - 1];
        this.currentSourceEntry = sourceEntry;
        sourceInUse(sourceEntry.source);
    }

    /* access modifiers changed from: protected */
    public int getStartingSourceIndex(long offset) {
        if (offset >= this.currentSourceEntry.firstByte) {
            return this.currentSourceEntry.index;
        }
        return 0;
    }

    private SourceEntry getSourceEntryForOffset(long offset) throws IOException {
        if (offset >= this.size) {
            return null;
        }
        if (offset >= this.currentSourceEntry.firstByte && offset <= this.currentSourceEntry.lastByte) {
            return this.currentSourceEntry;
        }
        sourceReleased(this.currentSourceEntry.source);
        int i = getStartingSourceIndex(offset);
        while (true) {
            SourceEntry[] sourceEntryArr = this.sources;
            if (i >= sourceEntryArr.length) {
                return null;
            }
            if (offset < sourceEntryArr[i].firstByte || offset > this.sources[i].lastByte) {
                i++;
            } else {
                SourceEntry sourceEntry = this.sources[i];
                this.currentSourceEntry = sourceEntry;
                sourceInUse(sourceEntry.source);
                return this.currentSourceEntry;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void sourceReleased(IRandomAccessSource source) throws IOException {
    }

    /* access modifiers changed from: protected */
    public void sourceInUse(IRandomAccessSource source) throws IOException {
    }

    public int get(long position) throws IOException {
        SourceEntry entry = getSourceEntryForOffset(position);
        if (entry == null) {
            return -1;
        }
        return entry.source.get(entry.offsetN(position));
    }

    public int get(long position, byte[] bytes, int off, int len) throws IOException {
        int i = len;
        SourceEntry entry = getSourceEntryForOffset(position);
        if (entry == null) {
            return -1;
        }
        long position2 = position;
        SourceEntry entry2 = entry;
        long offN = entry.offsetN(position2);
        int remaining = len;
        int off2 = off;
        while (true) {
            if (remaining <= 0) {
                break;
            } else if (entry2 == null) {
                break;
            } else if (offN > entry2.source.length()) {
                break;
            } else {
                int count = entry2.source.get(offN, bytes, off2, remaining);
                if (count == -1) {
                    break;
                }
                off2 += count;
                position2 += (long) count;
                remaining -= count;
                offN = 0;
                entry2 = getSourceEntryForOffset(position2);
            }
        }
        if (remaining == i) {
            return -1;
        }
        return i - remaining;
    }

    public long length() {
        return this.size;
    }

    public void close() throws IOException {
        Class<GroupedRandomAccessSource> cls = GroupedRandomAccessSource.class;
        IOException firstThrownIOExc = null;
        for (SourceEntry entry : this.sources) {
            try {
                entry.source.close();
            } catch (IOException ex) {
                if (firstThrownIOExc == null) {
                    firstThrownIOExc = ex;
                } else {
                    LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.ONE_OF_GROUPED_SOURCES_CLOSING_FAILED, (Throwable) ex);
                }
            } catch (Exception ex2) {
                LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.ONE_OF_GROUPED_SOURCES_CLOSING_FAILED, (Throwable) ex2);
            }
        }
        if (firstThrownIOExc != null) {
            throw firstThrownIOExc;
        }
    }

    /* renamed from: com.itextpdf.io.source.GroupedRandomAccessSource$SourceEntry */
    private static class SourceEntry implements Serializable {
        private static final long serialVersionUID = 924305549309252826L;
        final long firstByte;
        final int index;
        final long lastByte;
        final IRandomAccessSource source;

        public SourceEntry(int index2, IRandomAccessSource source2, long offset) {
            this.index = index2;
            this.source = source2;
            this.firstByte = offset;
            this.lastByte = (source2.length() + offset) - 1;
        }

        public long offsetN(long absoluteOffset) {
            return absoluteOffset - this.firstByte;
        }
    }
}
