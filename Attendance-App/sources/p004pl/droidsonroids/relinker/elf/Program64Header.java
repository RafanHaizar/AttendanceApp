package p004pl.droidsonroids.relinker.elf;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import p004pl.droidsonroids.relinker.elf.Elf;

/* renamed from: pl.droidsonroids.relinker.elf.Program64Header */
public class Program64Header extends Elf.ProgramHeader {
    public Program64Header(ElfParser parser, Elf.Header header, long index) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long baseOffset = header.phoff + (((long) header.phentsize) * index);
        this.type = parser.readWord(buffer, baseOffset);
        this.offset = parser.readLong(buffer, 8 + baseOffset);
        this.vaddr = parser.readLong(buffer, 16 + baseOffset);
        this.memsz = parser.readLong(buffer, 40 + baseOffset);
    }
}
