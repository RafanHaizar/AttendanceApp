package p004pl.droidsonroids.relinker.elf;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import p004pl.droidsonroids.relinker.elf.Elf;

/* renamed from: pl.droidsonroids.relinker.elf.Section64Header */
public class Section64Header extends Elf.SectionHeader {
    public Section64Header(ElfParser parser, Elf.Header header, int index) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        this.info = parser.readWord(buffer, header.shoff + ((long) (header.shentsize * index)) + 44);
    }
}
