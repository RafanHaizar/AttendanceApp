package p004pl.droidsonroids.relinker.elf;

import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.UByte;
import kotlin.UShort;
import org.bouncycastle.asn1.cmc.BodyPartID;
import p004pl.droidsonroids.relinker.elf.Elf;

/* renamed from: pl.droidsonroids.relinker.elf.ElfParser */
public class ElfParser implements Closeable, Elf {
    private final int MAGIC = 1179403647;
    private final FileChannel channel;

    public ElfParser(File file) throws FileNotFoundException {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("File is null or does not exist");
        }
        this.channel = new FileInputStream(file).getChannel();
    }

    public Elf.Header parseHeader() throws IOException {
        this.channel.position(0);
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        if (readWord(buffer, 0) == 1179403647) {
            short fileClass = readByte(buffer, 4);
            boolean bigEndian = readByte(buffer, 5) == 2;
            if (fileClass == 1) {
                return new Elf32Header(bigEndian, this);
            }
            if (fileClass == 2) {
                return new Elf64Header(bigEndian, this);
            }
            throw new IllegalStateException("Invalid class type!");
        }
        throw new IllegalArgumentException("Invalid ELF Magic!");
    }

    public List<String> parseNeededDependencies() throws IOException {
        long numProgramHeaderEntries;
        long i;
        Elf.DynamicStructure dynStructure;
        long vStringTableOff;
        this.channel.position(0);
        List<String> dependencies = new ArrayList<>();
        Elf.Header header = parseHeader();
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long numProgramHeaderEntries2 = (long) header.phnum;
        if (numProgramHeaderEntries2 == 65535) {
            numProgramHeaderEntries = header.getSectionHeader(0).info;
        } else {
            numProgramHeaderEntries = numProgramHeaderEntries2;
        }
        long i2 = 0;
        while (true) {
            if (i2 >= numProgramHeaderEntries) {
                i = 0;
                break;
            }
            Elf.ProgramHeader programHeader = header.getProgramHeader(i2);
            if (programHeader.type == 2) {
                i = programHeader.offset;
                break;
            }
            i2++;
        }
        if (i == 0) {
            return Collections.unmodifiableList(dependencies);
        }
        int i3 = 0;
        List<Long> neededOffsets = new ArrayList<>();
        long vStringTableOff2 = 0;
        while (true) {
            dynStructure = header.getDynamicStructure(i, i3);
            vStringTableOff = vStringTableOff2;
            if (dynStructure.tag == 1) {
                neededOffsets.add(Long.valueOf(dynStructure.val));
            } else if (dynStructure.tag == 5) {
                vStringTableOff = dynStructure.val;
            }
            int i4 = i3 + 1;
            if (dynStructure.tag == 0) {
                break;
            }
            List<Long> list = neededOffsets;
            Elf.DynamicStructure dynamicStructure = dynStructure;
            vStringTableOff2 = vStringTableOff;
            i3 = i4;
        }
        if (vStringTableOff != 0) {
            Elf.DynamicStructure dynamicStructure2 = dynStructure;
            long stringTableOff = offsetFromVma(header, numProgramHeaderEntries, vStringTableOff);
            for (Long strOff : neededOffsets) {
                dependencies.add(readString(buffer, strOff.longValue() + stringTableOff));
            }
            return dependencies;
        }
        throw new IllegalStateException("String table offset not found!");
    }

    private long offsetFromVma(Elf.Header header, long numEntries, long vma) throws IOException {
        for (long i = 0; i < numEntries; i++) {
            Elf.ProgramHeader programHeader = header.getProgramHeader(i);
            if (programHeader.type == 1 && programHeader.vaddr <= vma && vma <= programHeader.vaddr + programHeader.memsz) {
                return (vma - programHeader.vaddr) + programHeader.offset;
            }
        }
        throw new IllegalStateException("Could not map vma to file offset!");
    }

    public void close() throws IOException {
        this.channel.close();
    }

    /* access modifiers changed from: protected */
    public String readString(ByteBuffer buffer, long offset) throws IOException {
        StringBuilder builder = new StringBuilder();
        while (true) {
            long offset2 = 1 + offset;
            short readByte = readByte(buffer, offset);
            short c = readByte;
            if (readByte == 0) {
                return builder.toString();
            }
            builder.append((char) c);
            offset = offset2;
        }
    }

    /* access modifiers changed from: protected */
    public long readLong(ByteBuffer buffer, long offset) throws IOException {
        read(buffer, offset, 8);
        return buffer.getLong();
    }

    /* access modifiers changed from: protected */
    public long readWord(ByteBuffer buffer, long offset) throws IOException {
        read(buffer, offset, 4);
        return ((long) buffer.getInt()) & BodyPartID.bodyIdMax;
    }

    /* access modifiers changed from: protected */
    public int readHalf(ByteBuffer buffer, long offset) throws IOException {
        read(buffer, offset, 2);
        return buffer.getShort() & UShort.MAX_VALUE;
    }

    /* access modifiers changed from: protected */
    public short readByte(ByteBuffer buffer, long offset) throws IOException {
        read(buffer, offset, 1);
        return (short) (buffer.get() & UByte.MAX_VALUE);
    }

    /* access modifiers changed from: protected */
    public void read(ByteBuffer buffer, long offset, int length) throws IOException {
        buffer.position(0);
        buffer.limit(length);
        long bytesRead = 0;
        while (bytesRead < ((long) length)) {
            int read = this.channel.read(buffer, offset + bytesRead);
            if (read != -1) {
                bytesRead += (long) read;
            } else {
                throw new EOFException();
            }
        }
        buffer.position(0);
    }
}
