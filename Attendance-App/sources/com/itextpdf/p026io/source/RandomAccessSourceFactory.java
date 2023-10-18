package com.itextpdf.p026io.source;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.p026io.util.ResourceUtil;
import com.itextpdf.p026io.util.StreamUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.net.URL;
import java.nio.channels.FileChannel;

/* renamed from: com.itextpdf.io.source.RandomAccessSourceFactory */
public final class RandomAccessSourceFactory implements Serializable {
    private static final long serialVersionUID = -8958482579413233761L;
    private boolean exclusivelyLockFile = false;
    private boolean forceRead = false;
    private boolean usePlainRandomAccess = false;

    public RandomAccessSourceFactory setForceRead(boolean forceRead2) {
        this.forceRead = forceRead2;
        return this;
    }

    public RandomAccessSourceFactory setUsePlainRandomAccess(boolean usePlainRandomAccess2) {
        this.usePlainRandomAccess = usePlainRandomAccess2;
        return this;
    }

    public RandomAccessSourceFactory setExclusivelyLockFile(boolean exclusivelyLockFile2) {
        this.exclusivelyLockFile = exclusivelyLockFile2;
        return this;
    }

    public IRandomAccessSource createSource(byte[] data) {
        return new ArrayRandomAccessSource(data);
    }

    public IRandomAccessSource createSource(RandomAccessFile raf) throws IOException {
        return new RAFRandomAccessSource(raf);
    }

    public IRandomAccessSource createSource(URL url) throws IOException {
        InputStream stream = url.openStream();
        try {
            return createSource(stream);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }
    }

    public IRandomAccessSource createSource(InputStream inputStream) throws IOException {
        return createSource(StreamUtil.inputStreamToArray(inputStream));
    }

    public IRandomAccessSource createBestSource(String filename) throws IOException {
        File file = new File(filename);
        if (!file.canRead()) {
            if (filename.startsWith("file:/") || filename.startsWith("http://") || filename.startsWith("https://") || filename.startsWith("jar:") || filename.startsWith("wsjar:") || filename.startsWith("wsjar:") || filename.startsWith("vfszip:")) {
                return createSource(new URL(filename));
            }
            return createByReadingToMemory(filename);
        } else if (this.forceRead) {
            return createByReadingToMemory((InputStream) new FileInputStream(filename));
        } else {
            RandomAccessFile raf = new RandomAccessFile(file, this.exclusivelyLockFile ? "rw" : "r");
            if (this.exclusivelyLockFile) {
                raf.getChannel().lock();
            }
            if (this.usePlainRandomAccess) {
                return new RAFRandomAccessSource(raf);
            }
            try {
                if (raf.length() <= 0) {
                    return new RAFRandomAccessSource(raf);
                }
                return createBestSource(raf.getChannel());
            } catch (IOException e) {
                if (exceptionIsMapFailureException(e)) {
                    return new RAFRandomAccessSource(raf);
                }
                throw e;
            } catch (Exception e2) {
                try {
                    raf.close();
                } catch (IOException e3) {
                }
                throw e2;
            }
        }
    }

    public IRandomAccessSource createBestSource(FileChannel channel) throws IOException {
        if (channel.size() <= 67108864) {
            return new GetBufferedRandomAccessSource(new FileChannelRandomAccessSource(channel));
        }
        return new GetBufferedRandomAccessSource(new PagedChannelRandomAccessSource(channel));
    }

    public IRandomAccessSource createRanged(IRandomAccessSource source, long[] ranges) throws IOException {
        IRandomAccessSource[] sources = new IRandomAccessSource[(ranges.length / 2)];
        for (int i = 0; i < ranges.length; i += 2) {
            sources[i / 2] = new WindowRandomAccessSource(source, ranges[i], ranges[i + 1]);
        }
        return new GroupedRandomAccessSource(sources);
    }

    private IRandomAccessSource createByReadingToMemory(String filename) throws IOException {
        InputStream stream = ResourceUtil.getResourceStream(filename);
        if (stream != null) {
            return createByReadingToMemory(stream);
        }
        throw new IOException(MessageFormatUtil.format(com.itextpdf.p026io.IOException._1NotFoundAsFileOrResource, filename));
    }

    private IRandomAccessSource createByReadingToMemory(InputStream stream) throws IOException {
        try {
            return new ArrayRandomAccessSource(StreamUtil.inputStreamToArray(stream));
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }
    }

    private static boolean exceptionIsMapFailureException(IOException e) {
        if (e.getMessage() == null || !e.getMessage().contains("Map failed")) {
            return false;
        }
        return true;
    }
}
