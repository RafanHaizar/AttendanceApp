package p004pl.droidsonroids.relinker.elf;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import p004pl.droidsonroids.relinker.elf.Elf;

/* renamed from: pl.droidsonroids.relinker.elf.Dynamic64Structure */
public class Dynamic64Structure extends Elf.DynamicStructure {
    public Dynamic64Structure(ElfParser parser, Elf.Header header, long baseOffset, int index) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long baseOffset2 = baseOffset + ((long) (index * 16));
        this.tag = parser.readLong(buffer, baseOffset2);
        this.val = parser.readLong(buffer, 8 + baseOffset2);
    }
}
