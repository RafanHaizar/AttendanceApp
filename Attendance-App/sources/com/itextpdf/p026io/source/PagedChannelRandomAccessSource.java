package com.itextpdf.p026io.source;

import com.itextpdf.p026io.LogMessageConstant;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.LinkedList;
import org.slf4j.LoggerFactory;

/* renamed from: com.itextpdf.io.source.PagedChannelRandomAccessSource */
class PagedChannelRandomAccessSource extends GroupedRandomAccessSource implements IRandomAccessSource {
    public static final int DEFAULT_MAX_OPEN_BUFFERS = 16;
    public static final int DEFAULT_TOTAL_BUFSIZE = 67108864;
    private static final long serialVersionUID = 4297575388315637274L;
    private final int bufferSize;
    private final FileChannel channel;
    private final MRU<IRandomAccessSource> mru;

    public PagedChannelRandomAccessSource(FileChannel channel2) throws IOException {
        this(channel2, DEFAULT_TOTAL_BUFSIZE, 16);
    }

    public PagedChannelRandomAccessSource(FileChannel channel2, int totalBufferSize, int maxOpenBuffers) throws IOException {
        super(buildSources(channel2, totalBufferSize / maxOpenBuffers));
        this.channel = channel2;
        this.bufferSize = totalBufferSize / maxOpenBuffers;
        this.mru = new MRU<>(maxOpenBuffers);
    }

    private static IRandomAccessSource[] buildSources(FileChannel channel2, int bufferSize2) throws IOException {
        int i = bufferSize2;
        long size = channel2.size();
        if (size > 0) {
            int bufferCount = ((int) (size / ((long) i))) + (size % ((long) i) == 0 ? 0 : 1);
            MappedChannelRandomAccessSource[] sources = new MappedChannelRandomAccessSource[bufferCount];
            for (int i2 = 0; i2 < bufferCount; i2++) {
                long pageOffset = ((long) i2) * ((long) i);
                sources[i2] = new MappedChannelRandomAccessSource(channel2, pageOffset, Math.min(size - pageOffset, (long) i));
            }
            return sources;
        }
        throw new IOException("File size must be greater than zero");
    }

    /* access modifiers changed from: protected */
    public int getStartingSourceIndex(long offset) {
        return (int) (offset / ((long) this.bufferSize));
    }

    /* access modifiers changed from: protected */
    public void sourceReleased(IRandomAccessSource source) throws IOException {
        IRandomAccessSource old = this.mru.enqueue(source);
        if (old != null) {
            old.close();
        }
    }

    /* access modifiers changed from: protected */
    public void sourceInUse(IRandomAccessSource source) throws IOException {
        ((MappedChannelRandomAccessSource) source).open();
    }

    public void close() throws IOException {
        Class<PagedChannelRandomAccessSource> cls = PagedChannelRandomAccessSource.class;
        try {
            super.close();
        } finally {
            try {
                this.channel.close();
            } catch (Exception ex) {
                LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.FILE_CHANNEL_CLOSING_FAILED, (Throwable) ex);
            }
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        throw new NotSerializableException(getClass().toString());
    }

    private void readObject(ObjectInputStream in) throws IOException {
        throw new NotSerializableException(getClass().toString());
    }

    /* renamed from: com.itextpdf.io.source.PagedChannelRandomAccessSource$MRU */
    private static class MRU<E> {
        private final int limit;
        private LinkedList<E> queue = new LinkedList<>();

        public MRU(int limit2) {
            this.limit = limit2;
        }

        public E enqueue(E newElement) {
            if (this.queue.size() > 0 && this.queue.getFirst() == newElement) {
                return null;
            }
            Iterator<E> it = this.queue.iterator();
            while (it.hasNext()) {
                if (newElement == it.next()) {
                    it.remove();
                    this.queue.addFirst(newElement);
                    return null;
                }
            }
            this.queue.addFirst(newElement);
            if (this.queue.size() > this.limit) {
                return this.queue.removeLast();
            }
            return null;
        }
    }
}
