package p004pl.droidsonroids.relinker.elf;

import java.io.IOException;

/* renamed from: pl.droidsonroids.relinker.elf.Elf */
public interface Elf {

    /* renamed from: pl.droidsonroids.relinker.elf.Elf$DynamicStructure */
    public static abstract class DynamicStructure {
        public static final int DT_NEEDED = 1;
        public static final int DT_NULL = 0;
        public static final int DT_STRTAB = 5;
        public long tag;
        public long val;
    }

    /* renamed from: pl.droidsonroids.relinker.elf.Elf$Header */
    public static abstract class Header {
        public static final int ELFCLASS32 = 1;
        public static final int ELFCLASS64 = 2;
        public static final int ELFDATA2MSB = 2;
        public boolean bigEndian;
        public int phentsize;
        public int phnum;
        public long phoff;
        public int shentsize;
        public int shnum;
        public long shoff;
        public int shstrndx;
        public int type;

        public abstract DynamicStructure getDynamicStructure(long j, int i) throws IOException;

        public abstract ProgramHeader getProgramHeader(long j) throws IOException;

        public abstract SectionHeader getSectionHeader(int i) throws IOException;
    }

    /* renamed from: pl.droidsonroids.relinker.elf.Elf$ProgramHeader */
    public static abstract class ProgramHeader {
        public static final int PT_DYNAMIC = 2;
        public static final int PT_LOAD = 1;
        public long memsz;
        public long offset;
        public long type;
        public long vaddr;
    }

    /* renamed from: pl.droidsonroids.relinker.elf.Elf$SectionHeader */
    public static abstract class SectionHeader {
        public long info;
    }
}
