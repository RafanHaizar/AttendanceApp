package com.itextpdf.p026io.codec.brotli.dec;

import kotlin.UByte;

/* renamed from: com.itextpdf.io.codec.brotli.dec.Decode */
final class Decode {
    private static final int CODE_LENGTH_CODES = 18;
    private static final int[] CODE_LENGTH_CODE_ORDER = {1, 2, 3, 4, 0, 5, 17, 6, 16, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    private static final int CODE_LENGTH_REPEAT_CODE = 16;
    private static final int DEFAULT_CODE_LENGTH = 8;
    private static final int DISTANCE_CONTEXT_BITS = 2;
    private static final int[] DISTANCE_SHORT_CODE_INDEX_OFFSET = {3, 2, 1, 0, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2};
    private static final int[] DISTANCE_SHORT_CODE_VALUE_OFFSET = {0, 0, 0, 0, -1, 1, -2, 2, -3, 3, -1, 1, -2, 2, -3, 3};
    private static final int[] FIXED_TABLE = {131072, 131076, 131075, 196610, 131072, 131076, 131075, 262145, 131072, 131076, 131075, 196610, 131072, 131076, 131075, 262149};
    private static final int HUFFMAN_TABLE_BITS = 8;
    private static final int HUFFMAN_TABLE_MASK = 255;
    private static final int LITERAL_CONTEXT_BITS = 6;
    private static final int NUM_BLOCK_LENGTH_CODES = 26;
    private static final int NUM_DISTANCE_SHORT_CODES = 16;
    private static final int NUM_INSERT_AND_COPY_CODES = 704;
    private static final int NUM_LITERAL_CODES = 256;

    Decode() {
    }

    private static int decodeVarLenUnsignedByte(BitReader br) {
        if (BitReader.readBits(br, 1) == 0) {
            return 0;
        }
        int n = BitReader.readBits(br, 3);
        if (n == 0) {
            return 1;
        }
        return BitReader.readBits(br, n) + (1 << n);
    }

    private static void decodeMetaBlockLength(BitReader br, State state) {
        boolean z = true;
        state.inputEnd = BitReader.readBits(br, 1) == 1;
        state.metaBlockLength = 0;
        state.isUncompressed = false;
        state.isMetadata = false;
        if (!state.inputEnd || BitReader.readBits(br, 1) == 0) {
            int sizeNibbles = BitReader.readBits(br, 2) + 4;
            if (sizeNibbles == 7) {
                state.isMetadata = true;
                if (BitReader.readBits(br, 1) == 0) {
                    int sizeBytes = BitReader.readBits(br, 2);
                    if (sizeBytes != 0) {
                        for (int i = 0; i < sizeBytes; i++) {
                            int bits = BitReader.readBits(br, 8);
                            if (bits == 0 && i + 1 == sizeBytes && sizeBytes > 1) {
                                throw new BrotliRuntimeException("Exuberant nibble");
                            }
                            state.metaBlockLength |= bits << (i * 8);
                        }
                    } else {
                        return;
                    }
                } else {
                    throw new BrotliRuntimeException("Corrupted reserved bit");
                }
            } else {
                for (int i2 = 0; i2 < sizeNibbles; i2++) {
                    int bits2 = BitReader.readBits(br, 4);
                    if (bits2 == 0 && i2 + 1 == sizeNibbles && sizeNibbles > 4) {
                        throw new BrotliRuntimeException("Exuberant nibble");
                    }
                    state.metaBlockLength |= bits2 << (i2 * 4);
                }
            }
            state.metaBlockLength++;
            if (!state.inputEnd) {
                if (BitReader.readBits(br, 1) != 1) {
                    z = false;
                }
                state.isUncompressed = z;
            }
        }
    }

    private static int readSymbol(int[] table, int offset, BitReader br) {
        int val = (int) (br.accumulator >>> br.bitOffset);
        int offset2 = offset + (val & 255);
        int bits = table[offset2] >> 16;
        int sym = table[offset2] & 65535;
        if (bits <= 8) {
            br.bitOffset += bits;
            return sym;
        }
        int offset3 = offset2 + sym + ((val & ((1 << bits) - 1)) >>> 8);
        br.bitOffset += (table[offset3] >> 16) + 8;
        return 65535 & table[offset3];
    }

    private static int readBlockLength(int[] table, int offset, BitReader br) {
        BitReader.fillBitWindow(br);
        int code = readSymbol(table, offset, br);
        return Prefix.BLOCK_LENGTH_OFFSET[code] + BitReader.readBits(br, Prefix.BLOCK_LENGTH_N_BITS[code]);
    }

    private static int translateShortCodes(int code, int[] ringBuffer, int index) {
        if (code < 16) {
            return ringBuffer[(index + DISTANCE_SHORT_CODE_INDEX_OFFSET[code]) & 3] + DISTANCE_SHORT_CODE_VALUE_OFFSET[code];
        }
        return (code - 16) + 1;
    }

    private static void moveToFront(int[] v, int index) {
        int value = v[index];
        while (index > 0) {
            v[index] = v[index - 1];
            index--;
        }
        v[0] = value;
    }

    private static void inverseMoveToFrontTransform(byte[] v, int vLen) {
        int[] mtf = new int[256];
        for (int i = 0; i < 256; i++) {
            mtf[i] = i;
        }
        for (int i2 = 0; i2 < vLen; i2++) {
            int index = v[i2] & 255;
            v[i2] = (byte) mtf[index];
            if (index != 0) {
                moveToFront(mtf, index);
            }
        }
    }

    private static void readHuffmanCodeLengths(int[] codeLengthCodeLengths, int numSymbols, int[] codeLengths, BitReader br) {
        int i = numSymbols;
        int[] iArr = codeLengths;
        BitReader bitReader = br;
        int prevCodeLen = 0;
        int prevCodeLen2 = 8;
        int repeat = 0;
        int repeatCodeLen = 0;
        int space = 32768;
        int[] table = new int[32];
        Huffman.buildHuffmanTable(table, 0, 5, codeLengthCodeLengths, 18);
        while (prevCodeLen < i && space > 0) {
            BitReader.readMoreInput(br);
            BitReader.fillBitWindow(br);
            int p = ((int) (bitReader.accumulator >>> bitReader.bitOffset)) & 31;
            bitReader.bitOffset += table[p] >> 16;
            int codeLen = table[p] & 65535;
            if (codeLen < 16) {
                repeat = 0;
                int symbol = prevCodeLen + 1;
                iArr[prevCodeLen] = codeLen;
                if (codeLen != 0) {
                    space -= 32768 >> codeLen;
                    prevCodeLen2 = codeLen;
                    prevCodeLen = symbol;
                } else {
                    prevCodeLen = symbol;
                }
            } else {
                int symbol2 = codeLen - 14;
                int newLen = 0;
                if (codeLen == 16) {
                    newLen = prevCodeLen2;
                }
                if (repeatCodeLen != newLen) {
                    repeat = 0;
                    repeatCodeLen = newLen;
                }
                int oldRepeat = repeat;
                if (repeat > 0) {
                    repeat = (repeat - 2) << symbol2;
                }
                repeat += BitReader.readBits(bitReader, symbol2) + 3;
                int repeatDelta = repeat - oldRepeat;
                if (prevCodeLen + repeatDelta <= i) {
                    int i2 = 0;
                    while (i2 < repeatDelta) {
                        iArr[prevCodeLen] = repeatCodeLen;
                        i2++;
                        prevCodeLen++;
                    }
                    if (repeatCodeLen != 0) {
                        space -= repeatDelta << (15 - repeatCodeLen);
                    }
                } else {
                    int i3 = prevCodeLen2;
                    throw new BrotliRuntimeException("symbol + repeatDelta > numSymbols");
                }
            }
            bitReader = br;
        }
        if (space == 0) {
            Utils.fillWithZeroes(iArr, prevCodeLen, i - prevCodeLen);
            return;
        }
        throw new BrotliRuntimeException("Unused space");
    }

    static void readHuffmanCode(int alphabetSize, int[] table, int offset, BitReader br) {
        int i = alphabetSize;
        BitReader bitReader = br;
        boolean ok = true;
        BitReader.readMoreInput(br);
        int[] codeLengths = new int[i];
        int simpleCodeOrSkip = BitReader.readBits(bitReader, 2);
        boolean z = false;
        if (simpleCodeOrSkip == 1) {
            int maxBitsCounter = i - 1;
            int maxBits = 0;
            int[] symbols = new int[4];
            int numSymbols = BitReader.readBits(bitReader, 2) + 1;
            while (maxBitsCounter != 0) {
                maxBitsCounter >>= 1;
                maxBits++;
            }
            for (int i2 = 0; i2 < numSymbols; i2++) {
                symbols[i2] = BitReader.readBits(bitReader, maxBits) % i;
                codeLengths[symbols[i2]] = 2;
            }
            codeLengths[symbols[0]] = 1;
            switch (numSymbols) {
                case 1:
                    break;
                case 2:
                    if (symbols[0] != symbols[1]) {
                        z = true;
                    }
                    ok = z;
                    codeLengths[symbols[1]] = 1;
                    break;
                case 3:
                    if (!(symbols[0] == symbols[1] || symbols[0] == symbols[2] || symbols[1] == symbols[2])) {
                        z = true;
                    }
                    ok = z;
                    break;
                default:
                    ok = (symbols[0] == symbols[1] || symbols[0] == symbols[2] || symbols[0] == symbols[3] || symbols[1] == symbols[2] || symbols[1] == symbols[3] || symbols[2] == symbols[3]) ? false : true;
                    if (BitReader.readBits(bitReader, 1) != 1) {
                        codeLengths[symbols[0]] = 2;
                        break;
                    } else {
                        codeLengths[symbols[2]] = 3;
                        codeLengths[symbols[3]] = 3;
                        break;
                    }
                    break;
            }
        } else {
            int[] codeLengthCodeLengths = new int[18];
            int space = 32;
            int numCodes = 0;
            for (int i3 = simpleCodeOrSkip; i3 < 18 && space > 0; i3++) {
                int codeLenIdx = CODE_LENGTH_CODE_ORDER[i3];
                BitReader.fillBitWindow(br);
                int p = ((int) (bitReader.accumulator >>> bitReader.bitOffset)) & 15;
                int i4 = bitReader.bitOffset;
                int[] iArr = FIXED_TABLE;
                bitReader.bitOffset = i4 + (iArr[p] >> 16);
                int v = iArr[p] & 65535;
                codeLengthCodeLengths[codeLenIdx] = v;
                if (v != 0) {
                    space -= 32 >> v;
                    numCodes++;
                }
            }
            if (numCodes == 1 || space == 0) {
                z = true;
            }
            ok = z;
            readHuffmanCodeLengths(codeLengthCodeLengths, i, codeLengths, bitReader);
        }
        if (ok) {
            Huffman.buildHuffmanTable(table, offset, 8, codeLengths, i);
            return;
        }
        int[] iArr2 = table;
        int i5 = offset;
        throw new BrotliRuntimeException("Can't readHuffmanCode");
    }

    private static int decodeContextMap(int contextMapSize, byte[] contextMap, BitReader br) {
        BitReader.readMoreInput(br);
        int numTrees = decodeVarLenUnsignedByte(br) + 1;
        if (numTrees == 1) {
            Utils.fillWithZeroes(contextMap, 0, contextMapSize);
            return numTrees;
        }
        int maxRunLengthPrefix = 0;
        if (BitReader.readBits(br, 1) == 1) {
            maxRunLengthPrefix = BitReader.readBits(br, 4) + 1;
        }
        int[] table = new int[1080];
        readHuffmanCode(numTrees + maxRunLengthPrefix, table, 0, br);
        int i = 0;
        while (i < contextMapSize) {
            BitReader.readMoreInput(br);
            BitReader.fillBitWindow(br);
            int code = readSymbol(table, 0, br);
            if (code == 0) {
                contextMap[i] = 0;
                i++;
            } else if (code <= maxRunLengthPrefix) {
                int reps = (1 << code) + BitReader.readBits(br, code);
                while (reps != 0) {
                    if (i < contextMapSize) {
                        contextMap[i] = 0;
                        i++;
                        reps--;
                    } else {
                        throw new BrotliRuntimeException("Corrupted context map");
                    }
                }
                continue;
            } else {
                contextMap[i] = (byte) (code - maxRunLengthPrefix);
                i++;
            }
        }
        if (BitReader.readBits(br, 1) == 1) {
            inverseMoveToFrontTransform(contextMap, contextMapSize);
        }
        return numTrees;
    }

    private static void decodeBlockTypeAndLength(State state, int treeType) {
        int blockType;
        BitReader br = state.f1207br;
        int[] ringBuffers = state.blockTypeRb;
        int offset = treeType * 2;
        BitReader.fillBitWindow(br);
        int blockType2 = readSymbol(state.blockTypeTrees, treeType * 1080, br);
        state.blockLength[treeType] = readBlockLength(state.blockLenTrees, treeType * 1080, br);
        if (blockType2 == 1) {
            blockType = ringBuffers[offset + 1] + 1;
        } else if (blockType2 == 0) {
            blockType = ringBuffers[offset];
        } else {
            blockType = blockType2 - 2;
        }
        if (blockType >= state.numBlockTypes[treeType]) {
            blockType -= state.numBlockTypes[treeType];
        }
        ringBuffers[offset] = ringBuffers[offset + 1];
        ringBuffers[offset + 1] = blockType;
    }

    private static void decodeLiteralBlockSwitch(State state) {
        decodeBlockTypeAndLength(state, 0);
        int literalBlockType = state.blockTypeRb[1];
        state.contextMapSlice = literalBlockType << 6;
        state.literalTreeIndex = state.contextMap[state.contextMapSlice] & UByte.MAX_VALUE;
        state.literalTree = state.hGroup0.trees[state.literalTreeIndex];
        byte contextMode = state.contextModes[literalBlockType];
        state.contextLookupOffset1 = Context.LOOKUP_OFFSETS[contextMode];
        state.contextLookupOffset2 = Context.LOOKUP_OFFSETS[contextMode + 1];
    }

    private static void decodeCommandBlockSwitch(State state) {
        decodeBlockTypeAndLength(state, 1);
        state.treeCommandOffset = state.hGroup1.trees[state.blockTypeRb[3]];
    }

    private static void decodeDistanceBlockSwitch(State state) {
        decodeBlockTypeAndLength(state, 2);
        state.distContextMapSlice = state.blockTypeRb[5] << 2;
    }

    private static void maybeReallocateRingBuffer(State state) {
        int newSize = state.maxRingBufferSize;
        if (((long) newSize) > state.expectedTotalSize) {
            while ((newSize >> 1) > ((int) state.expectedTotalSize) + state.customDictionary.length) {
                newSize >>= 1;
            }
            if (!state.inputEnd && newSize < 16384 && state.maxRingBufferSize >= 16384) {
                newSize = 16384;
            }
        }
        if (newSize > state.ringBufferSize) {
            byte[] newBuffer = new byte[(newSize + 37)];
            if (state.ringBuffer != null) {
                System.arraycopy(state.ringBuffer, 0, newBuffer, 0, state.ringBufferSize);
            } else if (state.customDictionary.length != 0) {
                int length = state.customDictionary.length;
                int offset = 0;
                if (length > state.maxBackwardDistance) {
                    offset = length - state.maxBackwardDistance;
                    length = state.maxBackwardDistance;
                }
                System.arraycopy(state.customDictionary, offset, newBuffer, 0, length);
                state.pos = length;
                state.bytesToIgnore = length;
            }
            state.ringBuffer = newBuffer;
            state.ringBufferSize = newSize;
        }
    }

    private static void readMetablockInfo(State state) {
        BitReader br = state.f1207br;
        if (state.inputEnd) {
            state.nextRunningState = 10;
            state.bytesToWrite = state.pos;
            state.bytesWritten = 0;
            state.runningState = 12;
            return;
        }
        state.hGroup0.codes = null;
        state.hGroup0.trees = null;
        state.hGroup1.codes = null;
        state.hGroup1.trees = null;
        state.hGroup2.codes = null;
        state.hGroup2.trees = null;
        BitReader.readMoreInput(br);
        decodeMetaBlockLength(br, state);
        if (state.metaBlockLength != 0 || state.isMetadata) {
            if (state.isUncompressed || state.isMetadata) {
                BitReader.jumpToByteBoundary(br);
                state.runningState = state.isMetadata ? 4 : 5;
            } else {
                state.runningState = 2;
            }
            if (!state.isMetadata) {
                state.expectedTotalSize += (long) state.metaBlockLength;
                if (state.ringBufferSize < state.maxRingBufferSize) {
                    maybeReallocateRingBuffer(state);
                }
            }
        }
    }

    private static void readMetablockHuffmanCodesAndContextMaps(State state) {
        BitReader br = state.f1207br;
        for (int i = 0; i < 3; i++) {
            state.numBlockTypes[i] = decodeVarLenUnsignedByte(br) + 1;
            state.blockLength[i] = 268435456;
            if (state.numBlockTypes[i] > 1) {
                readHuffmanCode(state.numBlockTypes[i] + 2, state.blockTypeTrees, i * 1080, br);
                readHuffmanCode(26, state.blockLenTrees, i * 1080, br);
                state.blockLength[i] = readBlockLength(state.blockLenTrees, i * 1080, br);
            }
        }
        BitReader.readMoreInput(br);
        state.distancePostfixBits = BitReader.readBits(br, 2);
        state.numDirectDistanceCodes = (BitReader.readBits(br, 4) << state.distancePostfixBits) + 16;
        state.distancePostfixMask = (1 << state.distancePostfixBits) - 1;
        int numDistanceCodes = state.numDirectDistanceCodes + (48 << state.distancePostfixBits);
        state.contextModes = new byte[state.numBlockTypes[0]];
        int i2 = 0;
        while (i2 < state.numBlockTypes[0]) {
            int limit = Math.min(i2 + 96, state.numBlockTypes[0]);
            while (i2 < limit) {
                state.contextModes[i2] = (byte) (BitReader.readBits(br, 2) << 1);
                i2++;
            }
            BitReader.readMoreInput(br);
        }
        state.contextMap = new byte[(state.numBlockTypes[0] << 6)];
        int numLiteralTrees = decodeContextMap(state.numBlockTypes[0] << 6, state.contextMap, br);
        state.trivialLiteralContext = true;
        int j = 0;
        while (true) {
            if (j >= (state.numBlockTypes[0] << 6)) {
                break;
            } else if (state.contextMap[j] != (j >> 6)) {
                state.trivialLiteralContext = false;
                break;
            } else {
                j++;
            }
        }
        state.distContextMap = new byte[(state.numBlockTypes[2] << 2)];
        int numDistTrees = decodeContextMap(state.numBlockTypes[2] << 2, state.distContextMap, br);
        HuffmanTreeGroup.init(state.hGroup0, 256, numLiteralTrees);
        HuffmanTreeGroup.init(state.hGroup1, 704, state.numBlockTypes[1]);
        HuffmanTreeGroup.init(state.hGroup2, numDistanceCodes, numDistTrees);
        HuffmanTreeGroup.decode(state.hGroup0, br);
        HuffmanTreeGroup.decode(state.hGroup1, br);
        HuffmanTreeGroup.decode(state.hGroup2, br);
        state.contextMapSlice = 0;
        state.distContextMapSlice = 0;
        state.contextLookupOffset1 = Context.LOOKUP_OFFSETS[state.contextModes[0]];
        state.contextLookupOffset2 = Context.LOOKUP_OFFSETS[state.contextModes[0] + 1];
        state.literalTreeIndex = 0;
        state.literalTree = state.hGroup0.trees[0];
        state.treeCommandOffset = state.hGroup1.trees[0];
        int[] iArr = state.blockTypeRb;
        int[] iArr2 = state.blockTypeRb;
        state.blockTypeRb[4] = 1;
        iArr2[2] = 1;
        iArr[0] = 1;
        int[] iArr3 = state.blockTypeRb;
        int[] iArr4 = state.blockTypeRb;
        state.blockTypeRb[5] = 0;
        iArr4[3] = 0;
        iArr3[1] = 0;
    }

    private static void copyUncompressedData(State state) {
        BitReader br = state.f1207br;
        byte[] ringBuffer = state.ringBuffer;
        if (state.metaBlockLength <= 0) {
            BitReader.reload(br);
            state.runningState = 1;
            return;
        }
        int chunkLength = Math.min(state.ringBufferSize - state.pos, state.metaBlockLength);
        BitReader.copyBytes(br, ringBuffer, state.pos, chunkLength);
        state.metaBlockLength -= chunkLength;
        state.pos += chunkLength;
        if (state.pos == state.ringBufferSize) {
            state.nextRunningState = 5;
            state.bytesToWrite = state.ringBufferSize;
            state.bytesWritten = 0;
            state.runningState = 12;
            return;
        }
        BitReader.reload(br);
        state.runningState = 1;
    }

    private static boolean writeRingBuffer(State state) {
        if (state.bytesToIgnore != 0) {
            state.bytesWritten += state.bytesToIgnore;
            state.bytesToIgnore = 0;
        }
        int toWrite = Math.min(state.outputLength - state.outputUsed, state.bytesToWrite - state.bytesWritten);
        if (toWrite != 0) {
            System.arraycopy(state.ringBuffer, state.bytesWritten, state.output, state.outputOffset + state.outputUsed, toWrite);
            state.outputUsed += toWrite;
            state.bytesWritten += toWrite;
        }
        if (state.outputUsed < state.outputLength) {
            return true;
        }
        return false;
    }

    static void setCustomDictionary(State state, byte[] data) {
        state.customDictionary = data == null ? new byte[0] : data;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x0015, code lost:
        continue;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0101, code lost:
        r0.runningState = 1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:156:0x0015 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0176 A[LOOP:2: B:51:0x0176->B:57:0x01ac, LOOP_START] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x01b9  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0232  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void decompress(com.itextpdf.p026io.codec.brotli.dec.State r22) {
        /*
            r0 = r22
            int r1 = r0.runningState
            if (r1 == 0) goto L_0x03b9
            int r1 = r0.runningState
            r2 = 11
            if (r1 == r2) goto L_0x03b1
            com.itextpdf.io.codec.brotli.dec.BitReader r1 = r0.f1207br
            int r2 = r0.ringBufferSize
            r3 = 1
            int r2 = r2 - r3
            byte[] r4 = r0.ringBuffer
            r10 = r4
        L_0x0015:
            int r4 = r0.runningState
            java.lang.String r5 = "Invalid metablock length"
            r6 = 10
            if (r4 == r6) goto L_0x0399
            int r4 = r0.runningState
            r11 = 8
            java.lang.String r6 = "Invalid backward reference"
            r7 = 4
            r12 = 12
            r8 = 7
            r9 = 6
            r13 = 2
            r14 = 3
            r15 = 0
            switch(r4) {
                case 1: goto L_0x0384;
                case 2: goto L_0x0105;
                case 3: goto L_0x010a;
                case 4: goto L_0x00f1;
                case 5: goto L_0x00ec;
                case 6: goto L_0x0172;
                case 7: goto L_0x0307;
                case 8: goto L_0x00de;
                case 9: goto L_0x0064;
                case 10: goto L_0x002e;
                case 11: goto L_0x002e;
                case 12: goto L_0x0049;
                default: goto L_0x002e;
            }
        L_0x002e:
            com.itextpdf.io.codec.brotli.dec.BrotliRuntimeException r3 = new com.itextpdf.io.codec.brotli.dec.BrotliRuntimeException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Unexpected state "
            java.lang.StringBuilder r4 = r4.append(r5)
            int r5 = r0.runningState
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        L_0x0049:
            boolean r4 = writeRingBuffer(r22)
            if (r4 != 0) goto L_0x0050
            return
        L_0x0050:
            int r4 = r0.pos
            int r5 = r0.maxBackwardDistance
            if (r4 < r5) goto L_0x005a
            int r4 = r0.maxBackwardDistance
            r0.maxDistance = r4
        L_0x005a:
            int r4 = r0.pos
            r4 = r4 & r2
            r0.pos = r4
            int r4 = r0.nextRunningState
            r0.runningState = r4
            goto L_0x0015
        L_0x0064:
            int r4 = r0.copyLength
            if (r4 < r7) goto L_0x00d8
            int r4 = r0.copyLength
            r5 = 24
            if (r4 > r5) goto L_0x00d8
            int[] r4 = com.itextpdf.p026io.codec.brotli.dec.Dictionary.OFFSETS_BY_LENGTH
            int r5 = r0.copyLength
            r4 = r4[r5]
            int r5 = r0.distance
            int r7 = r0.maxDistance
            int r5 = r5 - r7
            int r13 = r5 + -1
            int[] r5 = com.itextpdf.p026io.codec.brotli.dec.Dictionary.SIZE_BITS_BY_LENGTH
            int r7 = r0.copyLength
            r16 = r5[r7]
            int r5 = r3 << r16
            int r17 = r5 + -1
            r18 = r13 & r17
            int r9 = r13 >>> r16
            int r5 = r0.copyLength
            int r5 = r5 * r18
            int r19 = r4 + r5
            com.itextpdf.io.codec.brotli.dec.Transform[] r4 = com.itextpdf.p026io.codec.brotli.dec.Transform.TRANSFORMS
            int r4 = r4.length
            if (r9 >= r4) goto L_0x00d2
            int r5 = r0.copyDst
            java.nio.ByteBuffer r6 = com.itextpdf.p026io.codec.brotli.dec.Dictionary.getData()
            int r8 = r0.copyLength
            com.itextpdf.io.codec.brotli.dec.Transform[] r4 = com.itextpdf.p026io.codec.brotli.dec.Transform.TRANSFORMS
            r20 = r4[r9]
            r4 = r10
            r7 = r19
            r21 = r9
            r9 = r20
            int r4 = com.itextpdf.p026io.codec.brotli.dec.Transform.transformDictionaryWord(r4, r5, r6, r7, r8, r9)
            int r5 = r0.copyDst
            int r5 = r5 + r4
            r0.copyDst = r5
            int r5 = r0.pos
            int r5 = r5 + r4
            r0.pos = r5
            int r5 = r0.metaBlockLength
            int r5 = r5 - r4
            r0.metaBlockLength = r5
            int r5 = r0.copyDst
            int r6 = r0.ringBufferSize
            if (r5 < r6) goto L_0x00cc
            r0.nextRunningState = r11
            int r5 = r0.ringBufferSize
            r0.bytesToWrite = r5
            r0.bytesWritten = r15
            r0.runningState = r12
            goto L_0x0015
        L_0x00cc:
            r0.runningState = r14
            goto L_0x0015
        L_0x00d2:
            com.itextpdf.io.codec.brotli.dec.BrotliRuntimeException r3 = new com.itextpdf.io.codec.brotli.dec.BrotliRuntimeException
            r3.<init>(r6)
            throw r3
        L_0x00d8:
            com.itextpdf.io.codec.brotli.dec.BrotliRuntimeException r3 = new com.itextpdf.io.codec.brotli.dec.BrotliRuntimeException
            r3.<init>(r6)
            throw r3
        L_0x00de:
            int r4 = r0.ringBufferSize
            int r5 = r0.copyDst
            int r6 = r0.ringBufferSize
            int r5 = r5 - r6
            java.lang.System.arraycopy(r10, r4, r10, r15, r5)
            r0.runningState = r14
            goto L_0x0015
        L_0x00ec:
            copyUncompressedData(r22)
            goto L_0x0015
        L_0x00f1:
            int r4 = r0.metaBlockLength
            if (r4 <= 0) goto L_0x0101
            com.itextpdf.p026io.codec.brotli.dec.BitReader.readMoreInput(r1)
            com.itextpdf.p026io.codec.brotli.dec.BitReader.readBits(r1, r11)
            int r4 = r0.metaBlockLength
            int r4 = r4 - r3
            r0.metaBlockLength = r4
            goto L_0x00f1
        L_0x0101:
            r0.runningState = r3
            goto L_0x0015
        L_0x0105:
            readMetablockHuffmanCodesAndContextMaps(r22)
            r0.runningState = r14
        L_0x010a:
            int r4 = r0.metaBlockLength
            if (r4 > 0) goto L_0x0112
            r0.runningState = r3
            goto L_0x0015
        L_0x0112:
            com.itextpdf.p026io.codec.brotli.dec.BitReader.readMoreInput(r1)
            int[] r4 = r0.blockLength
            r4 = r4[r3]
            if (r4 != 0) goto L_0x011e
            decodeCommandBlockSwitch(r22)
        L_0x011e:
            int[] r4 = r0.blockLength
            r5 = r4[r3]
            int r5 = r5 - r3
            r4[r3] = r5
            com.itextpdf.p026io.codec.brotli.dec.BitReader.fillBitWindow(r1)
            com.itextpdf.io.codec.brotli.dec.HuffmanTreeGroup r4 = r0.hGroup1
            int[] r4 = r4.codes
            int r5 = r0.treeCommandOffset
            int r4 = readSymbol(r4, r5, r1)
            int r5 = r4 >>> 6
            r0.distanceCode = r15
            if (r5 < r13) goto L_0x013d
            int r5 = r5 + -2
            r11 = -1
            r0.distanceCode = r11
        L_0x013d:
            int[] r11 = com.itextpdf.p026io.codec.brotli.dec.Prefix.INSERT_RANGE_LUT
            r11 = r11[r5]
            int r16 = r4 >>> 3
            r16 = r16 & 7
            int r11 = r11 + r16
            int[] r16 = com.itextpdf.p026io.codec.brotli.dec.Prefix.COPY_RANGE_LUT
            r16 = r16[r5]
            r17 = r4 & 7
            int r16 = r16 + r17
            int[] r17 = com.itextpdf.p026io.codec.brotli.dec.Prefix.INSERT_LENGTH_OFFSET
            r17 = r17[r11]
            int[] r18 = com.itextpdf.p026io.codec.brotli.dec.Prefix.INSERT_LENGTH_N_BITS
            r8 = r18[r11]
            int r8 = com.itextpdf.p026io.codec.brotli.dec.BitReader.readBits(r1, r8)
            int r8 = r17 + r8
            r0.insertLength = r8
            int[] r8 = com.itextpdf.p026io.codec.brotli.dec.Prefix.COPY_LENGTH_OFFSET
            r8 = r8[r16]
            int[] r17 = com.itextpdf.p026io.codec.brotli.dec.Prefix.COPY_LENGTH_N_BITS
            r7 = r17[r16]
            int r7 = com.itextpdf.p026io.codec.brotli.dec.BitReader.readBits(r1, r7)
            int r8 = r8 + r7
            r0.copyLength = r8
            r0.f1208j = r15
            r0.runningState = r9
        L_0x0172:
            boolean r4 = r0.trivialLiteralContext
            if (r4 == 0) goto L_0x01b9
        L_0x0176:
            int r4 = r0.f1208j
            int r5 = r0.insertLength
            if (r4 >= r5) goto L_0x022c
            com.itextpdf.p026io.codec.brotli.dec.BitReader.readMoreInput(r1)
            int[] r4 = r0.blockLength
            r4 = r4[r15]
            if (r4 != 0) goto L_0x0188
            decodeLiteralBlockSwitch(r22)
        L_0x0188:
            int[] r4 = r0.blockLength
            r5 = r4[r15]
            int r5 = r5 - r3
            r4[r15] = r5
            com.itextpdf.p026io.codec.brotli.dec.BitReader.fillBitWindow(r1)
            int r4 = r0.pos
            com.itextpdf.io.codec.brotli.dec.HuffmanTreeGroup r5 = r0.hGroup0
            int[] r5 = r5.codes
            int r7 = r0.literalTree
            int r5 = readSymbol(r5, r7, r1)
            byte r5 = (byte) r5
            r10[r4] = r5
            int r4 = r0.f1208j
            int r4 = r4 + r3
            r0.f1208j = r4
            int r4 = r0.pos
            int r5 = r4 + 1
            r0.pos = r5
            if (r4 != r2) goto L_0x0176
            r0.nextRunningState = r9
            int r4 = r0.ringBufferSize
            r0.bytesToWrite = r4
            r0.bytesWritten = r15
            r0.runningState = r12
            goto L_0x022c
        L_0x01b9:
            int r4 = r0.pos
            int r4 = r4 - r3
            r4 = r4 & r2
            byte r4 = r10[r4]
            r4 = r4 & 255(0xff, float:3.57E-43)
            int r5 = r0.pos
            int r5 = r5 - r13
            r5 = r5 & r2
            byte r5 = r10[r5]
            r5 = r5 & 255(0xff, float:3.57E-43)
        L_0x01c9:
            int r7 = r0.f1208j
            int r8 = r0.insertLength
            if (r7 >= r8) goto L_0x022c
            com.itextpdf.p026io.codec.brotli.dec.BitReader.readMoreInput(r1)
            int[] r7 = r0.blockLength
            r7 = r7[r15]
            if (r7 != 0) goto L_0x01db
            decodeLiteralBlockSwitch(r22)
        L_0x01db:
            byte[] r7 = r0.contextMap
            int r8 = r0.contextMapSlice
            int[] r11 = com.itextpdf.p026io.codec.brotli.dec.Context.LOOKUP
            int r13 = r0.contextLookupOffset1
            int r13 = r13 + r4
            r11 = r11[r13]
            int[] r13 = com.itextpdf.p026io.codec.brotli.dec.Context.LOOKUP
            int r14 = r0.contextLookupOffset2
            int r14 = r14 + r5
            r13 = r13[r14]
            r11 = r11 | r13
            int r8 = r8 + r11
            byte r7 = r7[r8]
            r7 = r7 & 255(0xff, float:3.57E-43)
            int[] r8 = r0.blockLength
            r11 = r8[r15]
            int r11 = r11 - r3
            r8[r15] = r11
            r5 = r4
            com.itextpdf.p026io.codec.brotli.dec.BitReader.fillBitWindow(r1)
            com.itextpdf.io.codec.brotli.dec.HuffmanTreeGroup r8 = r0.hGroup0
            int[] r8 = r8.codes
            com.itextpdf.io.codec.brotli.dec.HuffmanTreeGroup r11 = r0.hGroup0
            int[] r11 = r11.trees
            r11 = r11[r7]
            int r4 = readSymbol(r8, r11, r1)
            int r8 = r0.pos
            byte r11 = (byte) r4
            r10[r8] = r11
            int r8 = r0.f1208j
            int r8 = r8 + r3
            r0.f1208j = r8
            int r8 = r0.pos
            int r11 = r8 + 1
            r0.pos = r11
            if (r8 != r2) goto L_0x0229
            r0.nextRunningState = r9
            int r8 = r0.ringBufferSize
            r0.bytesToWrite = r8
            r0.bytesWritten = r15
            r0.runningState = r12
            goto L_0x022c
        L_0x0229:
            r13 = 2
            r14 = 3
            goto L_0x01c9
        L_0x022c:
            int r4 = r0.runningState
            if (r4 == r9) goto L_0x0232
            goto L_0x0015
        L_0x0232:
            int r4 = r0.metaBlockLength
            int r5 = r0.insertLength
            int r4 = r4 - r5
            r0.metaBlockLength = r4
            int r4 = r0.metaBlockLength
            if (r4 > 0) goto L_0x0242
            r4 = 3
            r0.runningState = r4
            goto L_0x0015
        L_0x0242:
            int r4 = r0.distanceCode
            if (r4 >= 0) goto L_0x02b4
            com.itextpdf.p026io.codec.brotli.dec.BitReader.readMoreInput(r1)
            int[] r4 = r0.blockLength
            r5 = 2
            r4 = r4[r5]
            if (r4 != 0) goto L_0x0253
            decodeDistanceBlockSwitch(r22)
        L_0x0253:
            int[] r4 = r0.blockLength
            r7 = r4[r5]
            int r7 = r7 - r3
            r4[r5] = r7
            com.itextpdf.p026io.codec.brotli.dec.BitReader.fillBitWindow(r1)
            com.itextpdf.io.codec.brotli.dec.HuffmanTreeGroup r4 = r0.hGroup2
            int[] r4 = r4.codes
            com.itextpdf.io.codec.brotli.dec.HuffmanTreeGroup r5 = r0.hGroup2
            int[] r5 = r5.trees
            byte[] r7 = r0.distContextMap
            int r8 = r0.distContextMapSlice
            int r9 = r0.copyLength
            r11 = 4
            if (r9 <= r11) goto L_0x0270
            r9 = 3
            goto L_0x0274
        L_0x0270:
            int r9 = r0.copyLength
            r11 = 2
            int r9 = r9 - r11
        L_0x0274:
            int r8 = r8 + r9
            byte r7 = r7[r8]
            r7 = r7 & 255(0xff, float:3.57E-43)
            r5 = r5[r7]
            int r4 = readSymbol(r4, r5, r1)
            r0.distanceCode = r4
            int r4 = r0.distanceCode
            int r5 = r0.numDirectDistanceCodes
            if (r4 < r5) goto L_0x02b4
            int r4 = r0.distanceCode
            int r5 = r0.numDirectDistanceCodes
            int r4 = r4 - r5
            r0.distanceCode = r4
            int r4 = r0.distanceCode
            int r5 = r0.distancePostfixMask
            r4 = r4 & r5
            int r5 = r0.distanceCode
            int r7 = r0.distancePostfixBits
            int r5 = r5 >>> r7
            r0.distanceCode = r5
            int r5 = r0.distanceCode
            int r5 = r5 >>> r3
            int r5 = r5 + r3
            int r7 = r0.distanceCode
            r7 = r7 & r3
            r8 = 2
            int r7 = r7 + r8
            int r7 = r7 << r5
            r8 = 4
            int r7 = r7 - r8
            int r8 = r0.numDirectDistanceCodes
            int r8 = r8 + r4
            int r9 = com.itextpdf.p026io.codec.brotli.dec.BitReader.readBits(r1, r5)
            int r9 = r9 + r7
            int r11 = r0.distancePostfixBits
            int r9 = r9 << r11
            int r8 = r8 + r9
            r0.distanceCode = r8
        L_0x02b4:
            int r4 = r0.distanceCode
            int[] r5 = r0.distRb
            int r7 = r0.distRbIdx
            int r4 = translateShortCodes(r4, r5, r7)
            r0.distance = r4
            int r4 = r0.distance
            if (r4 < 0) goto L_0x037c
            int r4 = r0.maxDistance
            int r5 = r0.maxBackwardDistance
            if (r4 == r5) goto L_0x02d5
            int r4 = r0.pos
            int r5 = r0.maxBackwardDistance
            if (r4 >= r5) goto L_0x02d5
            int r4 = r0.pos
            r0.maxDistance = r4
            goto L_0x02d9
        L_0x02d5:
            int r4 = r0.maxBackwardDistance
            r0.maxDistance = r4
        L_0x02d9:
            int r4 = r0.pos
            r0.copyDst = r4
            int r4 = r0.distance
            int r5 = r0.maxDistance
            if (r4 <= r5) goto L_0x02e9
            r4 = 9
            r0.runningState = r4
            goto L_0x0015
        L_0x02e9:
            int r4 = r0.distanceCode
            if (r4 <= 0) goto L_0x02fc
            int[] r4 = r0.distRb
            int r5 = r0.distRbIdx
            r7 = 3
            r5 = r5 & r7
            int r7 = r0.distance
            r4[r5] = r7
            int r4 = r0.distRbIdx
            int r4 = r4 + r3
            r0.distRbIdx = r4
        L_0x02fc:
            int r4 = r0.copyLength
            int r5 = r0.metaBlockLength
            if (r4 > r5) goto L_0x0376
            r0.f1208j = r15
            r4 = 7
            r0.runningState = r4
        L_0x0307:
            int r4 = r0.pos
            int r5 = r0.distance
            int r4 = r4 - r5
            r4 = r4 & r2
            int r5 = r0.pos
            int r6 = r0.copyLength
            int r7 = r0.f1208j
            int r6 = r6 - r7
            int r7 = r4 + r6
            if (r7 >= r2) goto L_0x033c
            int r7 = r5 + r6
            if (r7 >= r2) goto L_0x033c
            r7 = 0
        L_0x031d:
            if (r7 >= r6) goto L_0x032c
            int r8 = r5 + 1
            int r9 = r4 + 1
            byte r4 = r10[r4]
            r10[r5] = r4
            int r7 = r7 + 1
            r5 = r8
            r4 = r9
            goto L_0x031d
        L_0x032c:
            int r7 = r0.f1208j
            int r7 = r7 + r6
            r0.f1208j = r7
            int r7 = r0.metaBlockLength
            int r7 = r7 - r6
            r0.metaBlockLength = r7
            int r7 = r0.pos
            int r7 = r7 + r6
            r0.pos = r7
            goto L_0x036c
        L_0x033c:
            int r7 = r0.f1208j
            int r8 = r0.copyLength
            if (r7 >= r8) goto L_0x036c
            int r7 = r0.pos
            int r8 = r0.pos
            int r9 = r0.distance
            int r8 = r8 - r9
            r8 = r8 & r2
            byte r8 = r10[r8]
            r10[r7] = r8
            int r7 = r0.metaBlockLength
            int r7 = r7 - r3
            r0.metaBlockLength = r7
            int r7 = r0.f1208j
            int r7 = r7 + r3
            r0.f1208j = r7
            int r7 = r0.pos
            int r8 = r7 + 1
            r0.pos = r8
            if (r7 != r2) goto L_0x033c
            r7 = 7
            r0.nextRunningState = r7
            int r7 = r0.ringBufferSize
            r0.bytesToWrite = r7
            r0.bytesWritten = r15
            r0.runningState = r12
        L_0x036c:
            int r7 = r0.runningState
            r8 = 7
            if (r7 != r8) goto L_0x0015
            r7 = 3
            r0.runningState = r7
            goto L_0x0015
        L_0x0376:
            com.itextpdf.io.codec.brotli.dec.BrotliRuntimeException r3 = new com.itextpdf.io.codec.brotli.dec.BrotliRuntimeException
            r3.<init>(r6)
            throw r3
        L_0x037c:
            com.itextpdf.io.codec.brotli.dec.BrotliRuntimeException r3 = new com.itextpdf.io.codec.brotli.dec.BrotliRuntimeException
            java.lang.String r4 = "Negative distance"
            r3.<init>(r4)
            throw r3
        L_0x0384:
            int r4 = r0.metaBlockLength
            if (r4 < 0) goto L_0x0393
            readMetablockInfo(r22)
            int r4 = r0.ringBufferSize
            int r2 = r4 + -1
            byte[] r10 = r0.ringBuffer
            goto L_0x0015
        L_0x0393:
            com.itextpdf.io.codec.brotli.dec.BrotliRuntimeException r3 = new com.itextpdf.io.codec.brotli.dec.BrotliRuntimeException
            r3.<init>(r5)
            throw r3
        L_0x0399:
            int r4 = r0.runningState
            if (r4 != r6) goto L_0x03b0
            int r4 = r0.metaBlockLength
            if (r4 < 0) goto L_0x03aa
            com.itextpdf.p026io.codec.brotli.dec.BitReader.jumpToByteBoundary(r1)
            com.itextpdf.io.codec.brotli.dec.BitReader r4 = r0.f1207br
            com.itextpdf.p026io.codec.brotli.dec.BitReader.checkHealth(r4, r3)
            goto L_0x03b0
        L_0x03aa:
            com.itextpdf.io.codec.brotli.dec.BrotliRuntimeException r3 = new com.itextpdf.io.codec.brotli.dec.BrotliRuntimeException
            r3.<init>(r5)
            throw r3
        L_0x03b0:
            return
        L_0x03b1:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "Can't decompress after close"
            r1.<init>(r2)
            throw r1
        L_0x03b9:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "Can't decompress until initialized"
            r1.<init>(r2)
            goto L_0x03c2
        L_0x03c1:
            throw r1
        L_0x03c2:
            goto L_0x03c1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.codec.brotli.dec.Decode.decompress(com.itextpdf.io.codec.brotli.dec.State):void");
    }
}
