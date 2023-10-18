package androidx.core.net;

import android.net.TrafficStats;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

public final class TrafficStatsCompat {
    @Deprecated
    public static void clearThreadStatsTag() {
        TrafficStats.clearThreadStatsTag();
    }

    @Deprecated
    public static int getThreadStatsTag() {
        return TrafficStats.getThreadStatsTag();
    }

    @Deprecated
    public static void incrementOperationCount(int operationCount) {
        TrafficStats.incrementOperationCount(operationCount);
    }

    @Deprecated
    public static void incrementOperationCount(int tag, int operationCount) {
        TrafficStats.incrementOperationCount(tag, operationCount);
    }

    @Deprecated
    public static void setThreadStatsTag(int tag) {
        TrafficStats.setThreadStatsTag(tag);
    }

    @Deprecated
    public static void tagSocket(Socket socket) throws SocketException {
        TrafficStats.tagSocket(socket);
    }

    @Deprecated
    public static void untagSocket(Socket socket) throws SocketException {
        TrafficStats.untagSocket(socket);
    }

    public static void tagDatagramSocket(DatagramSocket socket) throws SocketException {
        if (Build.VERSION.SDK_INT >= 24) {
            Api24Impl.tagDatagramSocket(socket);
            return;
        }
        ParcelFileDescriptor pfd = ParcelFileDescriptor.fromDatagramSocket(socket);
        TrafficStats.tagSocket(new DatagramSocketWrapper(socket, pfd.getFileDescriptor()));
        pfd.detachFd();
    }

    public static void untagDatagramSocket(DatagramSocket socket) throws SocketException {
        if (Build.VERSION.SDK_INT >= 24) {
            Api24Impl.untagDatagramSocket(socket);
            return;
        }
        ParcelFileDescriptor pfd = ParcelFileDescriptor.fromDatagramSocket(socket);
        TrafficStats.untagSocket(new DatagramSocketWrapper(socket, pfd.getFileDescriptor()));
        pfd.detachFd();
    }

    private TrafficStatsCompat() {
    }

    static class Api24Impl {
        private Api24Impl() {
        }

        static void tagDatagramSocket(DatagramSocket socket) throws SocketException {
            TrafficStats.tagDatagramSocket(socket);
        }

        static void untagDatagramSocket(DatagramSocket socket) throws SocketException {
            TrafficStats.untagDatagramSocket(socket);
        }
    }
}
