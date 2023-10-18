package p004pl.droidsonroids.relinker.elf;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import p004pl.droidsonroids.relinker.elf.Elf;

/* renamed from: pl.droidsonroids.relinker.elf.Program32Header */
public class Program32Header extends Elf.ProgramHeader {
    public Program32Header(ElfParser parser, Elf.Header header, long index) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long baseOffset = header.phoff + (((long) header.phentsize) * index);
        this.type = parser.readWord(buffer, baseOffset);
        this.offset = parser.readWord(buffer, 4 + baseOffset);
        this.vaddr = parser.readWord(buffer, 8 + baseOffset);
        this.memsz = parser.readWord(buffer, 20 + baseOffset);
    }
}
