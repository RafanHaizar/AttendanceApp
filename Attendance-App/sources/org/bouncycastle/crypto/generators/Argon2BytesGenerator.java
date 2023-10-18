package org.bouncycastle.crypto.generators;

import org.bouncycastle.asn1.cmc.BodyPartID;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.Blake2bDigest;
import org.bouncycastle.crypto.params.Argon2Parameters;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;
import org.bouncycastle.util.encoders.Hex;

public class Argon2BytesGenerator {
    private static final int ARGON2_ADDRESSES_IN_BLOCK = 128;
    private static final int ARGON2_BLOCK_SIZE = 1024;
    private static final int ARGON2_PREHASH_DIGEST_LENGTH = 64;
    private static final int ARGON2_PREHASH_SEED_LENGTH = 72;
    private static final int ARGON2_QWORDS_IN_BLOCK = 128;
    private static final int ARGON2_SYNC_POINTS = 4;
    private static final int MAX_PARALLELISM = 16777216;
    private static final int MIN_ITERATIONS = 1;
    private static final int MIN_OUTLEN = 4;
    private static final int MIN_PARALLELISM = 1;
    private int laneLength;
    private Block[] memory;
    private Argon2Parameters parameters;
    private byte[] result;
    private int segmentLength;

    private static class Block {
        private static final int SIZE = 128;
        /* access modifiers changed from: private */

        /* renamed from: v */
        public final long[] f441v;

        private Block() {
            this.f441v = new long[128];
        }

        /* access modifiers changed from: private */
        public void copyBlock(Block block) {
            System.arraycopy(block.f441v, 0, this.f441v, 0, 128);
        }

        /* access modifiers changed from: private */
        public void xor(Block block, Block block2) {
            for (int i = 0; i < 128; i++) {
                this.f441v[i] = block.f441v[i] ^ block2.f441v[i];
            }
        }

        /* access modifiers changed from: private */
        public void xorWith(Block block) {
            int i = 0;
            while (true) {
                long[] jArr = this.f441v;
                if (i < jArr.length) {
                    jArr[i] = jArr[i] ^ block.f441v[i];
                    i++;
                } else {
                    return;
                }
            }
        }

        public Block clear() {
            Arrays.fill(this.f441v, 0);
            return this;
        }

        /* access modifiers changed from: package-private */
        public void fromBytes(byte[] bArr) {
            if (bArr.length == 1024) {
                for (int i = 0; i < 128; i++) {
                    this.f441v[i] = Pack.littleEndianToLong(bArr, i * 8);
                }
                return;
            }
            throw new IllegalArgumentException("input shorter than blocksize");
        }

        /* access modifiers changed from: package-private */
        public byte[] toBytes() {
            byte[] bArr = new byte[1024];
            for (int i = 0; i < 128; i++) {
                Pack.longToLittleEndian(this.f441v[i], bArr, i * 8);
            }
            return bArr;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < 128; i++) {
                stringBuffer.append(Hex.toHexString(Pack.longToLittleEndian(this.f441v[i])));
            }
            return stringBuffer.toString();
        }

        public void xor(Block block, Block block2, Block block3) {
            for (int i = 0; i < 128; i++) {
                this.f441v[i] = (block.f441v[i] ^ block2.f441v[i]) ^ block3.f441v[i];
            }
        }
    }

    private static class FillBlock {

        /* renamed from: R */
        Block f442R;

        /* renamed from: Z */
        Block f443Z;
        Block addressBlock;
        Block inputBlock;
        Block zeroBlock;

        private FillBlock() {
            this.f442R = new Block();
            this.f443Z = new Block();
            this.addressBlock = new Block();
            this.zeroBlock = new Block();
            this.inputBlock = new Block();
        }

        private void applyBlake() {
            for (int i = 0; i < 8; i++) {
                int i2 = i * 16;
                Argon2BytesGenerator.roundFunction(this.f443Z, i2, i2 + 1, i2 + 2, i2 + 3, i2 + 4, i2 + 5, i2 + 6, i2 + 7, i2 + 8, i2 + 9, i2 + 10, i2 + 11, i2 + 12, i2 + 13, i2 + 14, i2 + 15);
            }
            for (int i3 = 0; i3 < 8; i3++) {
                int i4 = i3 * 2;
                Argon2BytesGenerator.roundFunction(this.f443Z, i4, i4 + 1, i4 + 16, i4 + 17, i4 + 32, i4 + 33, i4 + 48, i4 + 49, i4 + 64, i4 + 65, i4 + 80, i4 + 81, i4 + 96, i4 + 97, i4 + 112, i4 + 113);
            }
        }

        /* access modifiers changed from: private */
        public void fillBlock(Block block, Block block2, Block block3) {
            if (block == this.zeroBlock) {
                this.f442R.copyBlock(block2);
            } else {
                this.f442R.xor(block, block2);
            }
            this.f443Z.copyBlock(this.f442R);
            applyBlake();
            block3.xor(this.f442R, this.f443Z);
        }

        /* access modifiers changed from: private */
        public void fillBlockWithXor(Block block, Block block2, Block block3) {
            this.f442R.xor(block, block2);
            this.f443Z.copyBlock(this.f442R);
            applyBlake();
            block3.xor(this.f442R, this.f443Z, block3);
        }
    }

    private static class Position {
        int index;
        int lane;
        int pass;
        int slice;

        Position() {
        }

        /* access modifiers changed from: package-private */
        public void update(int i, int i2, int i3, int i4) {
            this.pass = i;
            this.lane = i2;
            this.slice = i3;
            this.index = i4;
        }
    }

    /* renamed from: F */
    private static void m121F(Block block, int i, int i2, int i3, int i4) {
        fBlaMka(block, i, i2);
        rotr64(block, i4, i, 32);
        fBlaMka(block, i3, i4);
        rotr64(block, i2, i3, 24);
        fBlaMka(block, i, i2);
        rotr64(block, i4, i, 16);
        fBlaMka(block, i3, i4);
        rotr64(block, i2, i3, 63);
    }

    private static void addByteString(Digest digest, byte[] bArr) {
        if (bArr != null) {
            addIntToLittleEndian(digest, bArr.length);
            digest.update(bArr, 0, bArr.length);
            return;
        }
        addIntToLittleEndian(digest, 0);
    }

    private static void addIntToLittleEndian(Digest digest, int i) {
        digest.update((byte) i);
        digest.update((byte) (i >>> 8));
        digest.update((byte) (i >>> 16));
        digest.update((byte) (i >>> 24));
    }

    private void digest(int i) {
        Block block = this.memory[this.laneLength - 1];
        for (int i2 = 1; i2 < this.parameters.getLanes(); i2++) {
            int i3 = this.laneLength;
            block.xorWith(this.memory[(i2 * i3) + (i3 - 1)]);
        }
        this.result = hash(block.toBytes(), i);
    }

    private void doInit(Argon2Parameters argon2Parameters) {
        int memory2 = argon2Parameters.getMemory();
        if (memory2 < argon2Parameters.getLanes() * 8) {
            memory2 = argon2Parameters.getLanes() * 8;
        }
        int lanes = memory2 / (argon2Parameters.getLanes() * 4);
        this.segmentLength = lanes;
        this.laneLength = lanes * 4;
        initMemory(lanes * argon2Parameters.getLanes() * 4);
    }

    private static void fBlaMka(Block block, int i, int i2) {
        block.f441v[i] = block.f441v[i] + block.f441v[i2] + ((block.f441v[i] & BodyPartID.bodyIdMax) * (BodyPartID.bodyIdMax & block.f441v[i2]) * 2);
    }

    private void fillFirstBlocks(byte[] bArr) {
        byte[] initialHashLong = getInitialHashLong(bArr, new byte[]{0, 0, 0, 0});
        byte[] initialHashLong2 = getInitialHashLong(bArr, new byte[]{1, 0, 0, 0});
        for (int i = 0; i < this.parameters.getLanes(); i++) {
            Pack.intToLittleEndian(i, initialHashLong, 68);
            Pack.intToLittleEndian(i, initialHashLong2, 68);
            this.memory[(this.laneLength * i) + 0].fromBytes(hash(initialHashLong, 1024));
            this.memory[(this.laneLength * i) + 1].fromBytes(hash(initialHashLong2, 1024));
        }
    }

    private void fillMemoryBlocks() {
        FillBlock fillBlock = new FillBlock();
        Position position = new Position();
        for (int i = 0; i < this.parameters.getIterations(); i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                for (int i3 = 0; i3 < this.parameters.getLanes(); i3++) {
                    position.update(i, i3, i2, 0);
                    fillSegment(fillBlock, position);
                }
            }
        }
    }

    private void fillSegment(FillBlock fillBlock, Position position) {
        Block block;
        Block block2;
        Block block3;
        FillBlock fillBlock2 = fillBlock;
        Position position2 = position;
        boolean isDataIndependentAddressing = isDataIndependentAddressing(position2);
        int startingIndex = getStartingIndex(position);
        int i = (position2.lane * this.laneLength) + (position2.slice * this.segmentLength) + startingIndex;
        int prevOffset = getPrevOffset(i);
        if (isDataIndependentAddressing) {
            block3 = fillBlock2.addressBlock.clear();
            block2 = fillBlock2.zeroBlock.clear();
            block = fillBlock2.inputBlock.clear();
            initAddressBlocks(fillBlock, position, block2, block, block3);
        } else {
            block3 = null;
            block2 = null;
            block = null;
        }
        position2.index = startingIndex;
        int i2 = prevOffset;
        int i3 = i;
        while (position2.index < this.segmentLength) {
            int rotatePrevOffset = rotatePrevOffset(i3, i2);
            long pseudoRandom = getPseudoRandom(fillBlock, position, block3, block, block2, rotatePrevOffset, isDataIndependentAddressing);
            int refLane = getRefLane(position2, pseudoRandom);
            int refColumn = getRefColumn(position2, pseudoRandom, refLane == position2.lane);
            Block[] blockArr = this.memory;
            Block block4 = blockArr[rotatePrevOffset];
            Block block5 = blockArr[(this.laneLength * refLane) + refColumn];
            Block block6 = blockArr[i3];
            if (isWithXor(position2)) {
                fillBlock2.fillBlockWithXor(block4, block5, block6);
            } else {
                fillBlock2.fillBlock(block4, block5, block6);
            }
            position2.index++;
            i3++;
            i2 = rotatePrevOffset + 1;
        }
    }

    private byte[] getInitialHashLong(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[72];
        System.arraycopy(bArr, 0, bArr3, 0, 64);
        System.arraycopy(bArr2, 0, bArr3, 64, 4);
        return bArr3;
    }

    private int getPrevOffset(int i) {
        int i2 = this.laneLength;
        return i % i2 == 0 ? (i + i2) - 1 : i - 1;
    }

    private long getPseudoRandom(FillBlock fillBlock, Position position, Block block, Block block2, Block block3, int i, boolean z) {
        if (!z) {
            return this.memory[i].f441v[0];
        }
        if (position.index % 128 == 0) {
            nextAddresses(fillBlock, block3, block2, block);
        }
        return block.f441v[position.index % 128];
    }

    private int getRefColumn(Position position, long j, boolean z) {
        int i;
        int i2 = -1;
        int i3 = 0;
        if (position.pass != 0) {
            int i4 = this.segmentLength;
            int i5 = this.laneLength;
            int i6 = ((position.slice + 1) * i4) % i5;
            int i7 = i5 - i4;
            int i8 = position.index;
            if (z) {
                i = (i7 + i8) - 1;
            } else {
                if (i8 != 0) {
                    i2 = 0;
                }
                i = i7 + i2;
            }
            i3 = i6;
        } else if (z) {
            i = ((position.slice * this.segmentLength) + position.index) - 1;
        } else {
            int i9 = position.slice * this.segmentLength;
            if (position.index != 0) {
                i2 = 0;
            }
            i = i9 + i2;
        }
        long j2 = j & BodyPartID.bodyIdMax;
        return ((int) (((long) i3) + (((long) (i - 1)) - ((((long) i) * ((j2 * j2) >>> 32)) >>> 32)))) % this.laneLength;
    }

    private int getRefLane(Position position, long j) {
        int lanes = (int) ((j >>> 32) % ((long) this.parameters.getLanes()));
        return (position.pass == 0 && position.slice == 0) ? position.lane : lanes;
    }

    private static int getStartingIndex(Position position) {
        return (position.pass == 0 && position.slice == 0) ? 2 : 0;
    }

    private byte[] hash(byte[] bArr, int i) {
        byte[] bArr2 = new byte[i];
        byte[] intToLittleEndian = Pack.intToLittleEndian(i);
        if (i <= 64) {
            Blake2bDigest blake2bDigest = new Blake2bDigest(i * 8);
            blake2bDigest.update(intToLittleEndian, 0, intToLittleEndian.length);
            blake2bDigest.update(bArr, 0, bArr.length);
            blake2bDigest.doFinal(bArr2, 0);
        } else {
            Blake2bDigest blake2bDigest2 = new Blake2bDigest(512);
            byte[] bArr3 = new byte[64];
            blake2bDigest2.update(intToLittleEndian, 0, intToLittleEndian.length);
            blake2bDigest2.update(bArr, 0, bArr.length);
            blake2bDigest2.doFinal(bArr3, 0);
            System.arraycopy(bArr3, 0, bArr2, 0, 32);
            int i2 = 2;
            int i3 = ((i + 31) / 32) - 2;
            int i4 = 32;
            while (i2 <= i3) {
                blake2bDigest2.update(bArr3, 0, 64);
                blake2bDigest2.doFinal(bArr3, 0);
                System.arraycopy(bArr3, 0, bArr2, i4, 32);
                i2++;
                i4 += 32;
            }
            Blake2bDigest blake2bDigest3 = new Blake2bDigest((i - (i3 * 32)) * 8);
            blake2bDigest3.update(bArr3, 0, 64);
            blake2bDigest3.doFinal(bArr2, i4);
        }
        return bArr2;
    }

    private void initAddressBlocks(FillBlock fillBlock, Position position, Block block, Block block2, Block block3) {
        block2.f441v[0] = intToLong(position.pass);
        block2.f441v[1] = intToLong(position.lane);
        block2.f441v[2] = intToLong(position.slice);
        block2.f441v[3] = intToLong(this.memory.length);
        block2.f441v[4] = intToLong(this.parameters.getIterations());
        block2.f441v[5] = intToLong(this.parameters.getType());
        if (position.pass == 0 && position.slice == 0) {
            nextAddresses(fillBlock, block, block2, block3);
        }
    }

    private void initMemory(int i) {
        this.memory = new Block[i];
        int i2 = 0;
        while (true) {
            Block[] blockArr = this.memory;
            if (i2 < blockArr.length) {
                blockArr[i2] = new Block();
                i2++;
            } else {
                return;
            }
        }
    }

    private byte[] initialHash(Argon2Parameters argon2Parameters, int i, byte[] bArr) {
        Blake2bDigest blake2bDigest = new Blake2bDigest(512);
        addIntToLittleEndian(blake2bDigest, argon2Parameters.getLanes());
        addIntToLittleEndian(blake2bDigest, i);
        addIntToLittleEndian(blake2bDigest, argon2Parameters.getMemory());
        addIntToLittleEndian(blake2bDigest, argon2Parameters.getIterations());
        addIntToLittleEndian(blake2bDigest, argon2Parameters.getVersion());
        addIntToLittleEndian(blake2bDigest, argon2Parameters.getType());
        addByteString(blake2bDigest, bArr);
        addByteString(blake2bDigest, argon2Parameters.getSalt());
        addByteString(blake2bDigest, argon2Parameters.getSecret());
        addByteString(blake2bDigest, argon2Parameters.getAdditional());
        byte[] bArr2 = new byte[blake2bDigest.getDigestSize()];
        blake2bDigest.doFinal(bArr2, 0);
        return bArr2;
    }

    private void initialize(byte[] bArr, int i) {
        fillFirstBlocks(initialHash(this.parameters, i, bArr));
    }

    private long intToLong(int i) {
        return ((long) i) & BodyPartID.bodyIdMax;
    }

    private boolean isDataIndependentAddressing(Position position) {
        if (this.parameters.getType() != 1) {
            return this.parameters.getType() == 2 && position.pass == 0 && position.slice < 2;
        }
        return true;
    }

    private boolean isWithXor(Position position) {
        return (position.pass == 0 || this.parameters.getVersion() == 16) ? false : true;
    }

    private void nextAddresses(FillBlock fillBlock, Block block, Block block2, Block block3) {
        long[] access$400 = block2.f441v;
        access$400[6] = access$400[6] + 1;
        fillBlock.fillBlock(block, block2, block3);
        fillBlock.fillBlock(block, block3, block3);
    }

    private void reset() {
        int i = 0;
        while (true) {
            Block[] blockArr = this.memory;
            if (i < blockArr.length) {
                blockArr[i].clear();
                i++;
            } else {
                this.memory = null;
                Arrays.fill(this.result, (byte) 0);
                return;
            }
        }
    }

    private int rotatePrevOffset(int i, int i2) {
        return i % this.laneLength == 1 ? i - 1 : i2;
    }

    private static void rotr64(Block block, int i, int i2, long j) {
        long j2 = block.f441v[i] ^ block.f441v[i2];
        block.f441v[i] = (j2 << ((int) (64 - j))) | (j2 >>> ((int) j));
    }

    /* access modifiers changed from: private */
    public static void roundFunction(Block block, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16) {
        Block block2 = block;
        int i17 = i;
        int i18 = i2;
        int i19 = i3;
        int i20 = i4;
        int i21 = i6;
        int i22 = i7;
        int i23 = i8;
        int i24 = i9;
        int i25 = i10;
        int i26 = i11;
        int i27 = i12;
        int i28 = i13;
        int i29 = i14;
        int i30 = i15;
        m121F(block2, i17, i5, i24, i28);
        m121F(block2, i18, i21, i25, i29);
        m121F(block2, i19, i22, i26, i30);
        int i31 = i16;
        m121F(block2, i20, i23, i27, i31);
        m121F(block2, i17, i21, i26, i31);
        m121F(block2, i18, i22, i27, i28);
        m121F(block2, i19, i23, i24, i29);
        m121F(block2, i20, i5, i25, i30);
    }

    public int generateBytes(byte[] bArr, byte[] bArr2) {
        return generateBytes(bArr, bArr2, 0, bArr2.length);
    }

    public int generateBytes(byte[] bArr, byte[] bArr2, int i, int i2) {
        if (i2 >= 4) {
            initialize(bArr, i2);
            fillMemoryBlocks();
            digest(i2);
            System.arraycopy(this.result, 0, bArr2, i, i2);
            reset();
            return i2;
        }
        throw new IllegalStateException("output length less than 4");
    }

    public int generateBytes(char[] cArr, byte[] bArr) {
        return generateBytes(this.parameters.getCharToByteConverter().convert(cArr), bArr);
    }

    public int generateBytes(char[] cArr, byte[] bArr, int i, int i2) {
        return generateBytes(this.parameters.getCharToByteConverter().convert(cArr), bArr, i, i2);
    }

    public void init(Argon2Parameters argon2Parameters) {
        this.parameters = argon2Parameters;
        if (argon2Parameters.getLanes() < 1) {
            throw new IllegalStateException("lanes must be greater than 1");
        } else if (argon2Parameters.getLanes() > 16777216) {
            throw new IllegalStateException("lanes must be less than 16777216");
        } else if (argon2Parameters.getMemory() < argon2Parameters.getLanes() * 2) {
            throw new IllegalStateException("memory is less than: " + (argon2Parameters.getLanes() * 2) + " expected " + (argon2Parameters.getLanes() * 2));
        } else if (argon2Parameters.getIterations() >= 1) {
            doInit(argon2Parameters);
        } else {
            throw new IllegalStateException("iterations is less than: 1");
        }
    }
}
