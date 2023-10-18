package com.itextpdf.p026io.source;

import com.itextpdf.p026io.LogMessageConstant;
import java.io.IOException;
import java.nio.channels.FileChannel;
import org.slf4j.LoggerFactory;

/* renamed from: com.itextpdf.io.source.FileChannelRandomAccessSource */
public class FileChannelRandomAccessSource implements IRandomAccessSource {
    private final FileChannel channel;
    private final MappedChannelRandomAccessSource source;

    public FileChannelRandomAccessSource(FileChannel channel2) throws IOException {
        this.channel = channel2;
        if (channel2.size() != 0) {
            MappedChannelRandomAccessSource mappedChannelRandomAccessSource = new MappedChannelRandomAccessSource(channel2, 0, channel2.size());
            this.source = mappedChannelRandomAccessSource;
            mappedChannelRandomAccessSource.open();
            return;
        }
        throw new IOException("File size is 0 bytes");
    }

    public void close() throws IOException {
        Class<FileChannelRandomAccessSource> cls = FileChannelRandomAccessSource.class;
        try {
            this.source.close();
        } finally {
            try {
                this.channel.close();
            } catch (Exception ex) {
                LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.FILE_CHANNEL_CLOSING_FAILED, (Throwable) ex);
            }
        }
    }

    public int get(long position) throws IOException {
        return this.source.get(position);
    }

    public int get(long position, byte[] bytes, int off, int len) throws IOException {
        return this.source.get(position, bytes, off, len);
    }

    public long length() {
        return this.source.length();
    }
}
