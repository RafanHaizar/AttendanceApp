package p004pl.droidsonroids.relinker.elf;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import p004pl.droidsonroids.relinker.elf.Elf;

/* renamed from: pl.droidsonroids.relinker.elf.Elf64Header */
public class Elf64Header extends Elf.Header {
    private final ElfParser parser;

    public Elf64Header(boolean bigEndian, ElfParser parser2) throws IOException {
        this.bigEndian = bigEndian;
        this.parser = parser2;
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        this.type = parser2.readHalf(buffer, 16);
        this.phoff = parser2.readLong(buffer, 32);
        this.shoff = parser2.readLong(buffer, 40);
        this.phentsize = parser2.readHalf(buffer, 54);
        this.phnum = parser2.readHalf(buffer, 56);
        this.shentsize = parser2.readHalf(buffer, 58);
        this.shnum = parser2.readHalf(buffer, 60);
        this.shstrndx = parser2.readHalf(buffer, 62);
    }

    public Elf.SectionHeader getSectionHeader(int index) throws IOException {
        return new Section64Header(this.parser, this, index);
    }

    public Elf.ProgramHeader getProgramHeader(long index) throws IOException {
        return new Program64Header(this.parser, this, index);
    }

    public Elf.DynamicStructure getDynamicStructure(long baseOffset, int index) throws IOException {
        return new Dynamic64Structure(this.parser, this, baseOffset, index);
    }
}
