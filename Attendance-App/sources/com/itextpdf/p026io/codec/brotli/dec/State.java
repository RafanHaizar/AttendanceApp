package com.itextpdf.p026io.codec.brotli.dec;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.itextpdf.io.codec.brotli.dec.State */
final class State {
    final int[] blockLenTrees = new int[3240];
    final int[] blockLength = new int[3];
    final int[] blockTypeRb = new int[6];
    final int[] blockTypeTrees = new int[3240];

    /* renamed from: br */
    final BitReader f1207br = new BitReader();
    int bytesToIgnore = 0;
    int bytesToWrite;
    int bytesWritten;
    int contextLookupOffset1;
    int contextLookupOffset2;
    byte[] contextMap;
    int contextMapSlice;
    byte[] contextModes;
    int copyDst;
    int copyLength;
    byte[] customDictionary = new byte[0];
    byte[] distContextMap;
    int distContextMapSlice;
    final int[] distRb = {16, 15, 11, 4};
    int distRbIdx = 0;
    int distance;
    int distanceCode;
    int distancePostfixBits;
    int distancePostfixMask;
    long expectedTotalSize = 0;
    final HuffmanTreeGroup hGroup0 = new HuffmanTreeGroup();
    final HuffmanTreeGroup hGroup1 = new HuffmanTreeGroup();
    final HuffmanTreeGroup hGroup2 = new HuffmanTreeGroup();
    boolean inputEnd;
    int insertLength;
    boolean isMetadata;
    boolean isUncompressed;

    /* renamed from: j */
    int f1208j;
    int literalTree;
    int literalTreeIndex = 0;
    int maxBackwardDistance;
    int maxDistance = 0;
    int maxRingBufferSize;
    int metaBlockLength;
    int nextRunningState;
    final int[] numBlockTypes = new int[3];
    int numDirectDistanceCodes;
    byte[] output;
    int outputLength;
    int outputOffset;
    int outputUsed;
    int pos = 0;
    byte[] ringBuffer;
    int ringBufferSize = 0;
    int runningState = 0;
    int treeCommandOffset;
    boolean trivialLiteralContext = false;

    State() {
    }

    private static int decodeWindowBits(BitReader br) {
        if (BitReader.readBits(br, 1) == 0) {
            return 16;
        }
        int n = BitReader.readBits(br, 3);
        if (n != 0) {
            return n + 17;
        }
        int n2 = BitReader.readBits(br, 3);
        if (n2 != 0) {
            return n2 + 8;
        }
        return 17;
    }

    static void setInput(State state, InputStream input) {
        if (state.runningState == 0) {
            BitReader.init(state.f1207br, input);
            int windowBits = decodeWindowBits(state.f1207br);
            if (windowBits != 9) {
                int i = 1 << windowBits;
                state.maxRingBufferSize = i;
                state.maxBackwardDistance = i - 16;
                state.runningState = 1;
                return;
            }
            throw new BrotliRuntimeException("Invalid 'windowBits' code");
        }
        throw new IllegalStateException("State MUST be uninitialized");
    }

    static void close(State state) throws IOException {
        int i = state.runningState;
        if (i == 0) {
            throw new IllegalStateException("State MUST be initialized");
        } else if (i != 11) {
            state.runningState = 11;
            BitReader.close(state.f1207br);
        }
    }
}
