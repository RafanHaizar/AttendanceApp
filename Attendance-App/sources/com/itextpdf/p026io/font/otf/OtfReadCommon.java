package com.itextpdf.p026io.font.otf;

import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* renamed from: com.itextpdf.io.font.otf.OtfReadCommon */
public class OtfReadCommon {
    public static int[] readUShortArray(RandomAccessFileOrArray rf, int size, int location) throws IOException {
        int[] ret = new int[size];
        for (int k = 0; k < size; k++) {
            int offset = rf.readUnsignedShort();
            ret[k] = offset == 0 ? offset : offset + location;
        }
        return ret;
    }

    public static int[] readUShortArray(RandomAccessFileOrArray rf, int size) throws IOException {
        return readUShortArray(rf, size, 0);
    }

    public static void readCoverages(RandomAccessFileOrArray rf, int[] locations, List<Set<Integer>> coverage) throws IOException {
        for (int location : locations) {
            coverage.add(new HashSet(readCoverageFormat(rf, location)));
        }
    }

    public static List<Integer> readCoverageFormat(RandomAccessFileOrArray rf, int coverageLocation) throws IOException {
        List<Integer> glyphIds;
        rf.seek((long) coverageLocation);
        int coverageFormat = rf.readShort();
        if (coverageFormat == 1) {
            int glyphCount = rf.readShort();
            glyphIds = new ArrayList<>(glyphCount);
            for (int i = 0; i < glyphCount; i++) {
                glyphIds.add(Integer.valueOf(rf.readShort()));
            }
        } else if (coverageFormat == 2) {
            int rangeCount = rf.readShort();
            glyphIds = new ArrayList<>();
            for (int i2 = 0; i2 < rangeCount; i2++) {
                readRangeRecord(rf, glyphIds);
            }
        } else {
            throw new UnsupportedOperationException(MessageFormatUtil.format("Invalid coverage format: {0}", Integer.valueOf(coverageFormat)));
        }
        return Collections.unmodifiableList(glyphIds);
    }

    private static void readRangeRecord(RandomAccessFileOrArray rf, List<Integer> glyphIds) throws IOException {
        int startGlyphId = rf.readShort();
        int endGlyphId = rf.readShort();
        short readShort = rf.readShort();
        for (int glyphId = startGlyphId; glyphId <= endGlyphId; glyphId++) {
            glyphIds.add(Integer.valueOf(glyphId));
        }
    }

    public static GposValueRecord readGposValueRecord(OpenTypeFontTableReader tableReader, int mask) throws IOException {
        GposValueRecord vr = new GposValueRecord();
        if ((mask & 1) != 0) {
            vr.XPlacement = (tableReader.f1214rf.readShort() * 1000) / tableReader.getUnitsPerEm();
        }
        if ((mask & 2) != 0) {
            vr.YPlacement = (tableReader.f1214rf.readShort() * 1000) / tableReader.getUnitsPerEm();
        }
        if ((mask & 4) != 0) {
            vr.XAdvance = (tableReader.f1214rf.readShort() * 1000) / tableReader.getUnitsPerEm();
        }
        if ((mask & 8) != 0) {
            vr.YAdvance = (tableReader.f1214rf.readShort() * 1000) / tableReader.getUnitsPerEm();
        }
        if ((mask & 16) != 0) {
            tableReader.f1214rf.skip(2);
        }
        if ((mask & 32) != 0) {
            tableReader.f1214rf.skip(2);
        }
        if ((mask & 64) != 0) {
            tableReader.f1214rf.skip(2);
        }
        if ((mask & 128) != 0) {
            tableReader.f1214rf.skip(2);
        }
        return vr;
    }

    public static GposAnchor readGposAnchor(OpenTypeFontTableReader tableReader, int location) throws IOException {
        if (location == 0) {
            return null;
        }
        tableReader.f1214rf.seek((long) location);
        int readUnsignedShort = tableReader.f1214rf.readUnsignedShort();
        GposAnchor t = new GposAnchor();
        t.XCoordinate = (tableReader.f1214rf.readShort() * 1000) / tableReader.getUnitsPerEm();
        t.YCoordinate = (tableReader.f1214rf.readShort() * 1000) / tableReader.getUnitsPerEm();
        return t;
    }

    public static List<OtfMarkRecord> readMarkArray(OpenTypeFontTableReader tableReader, int location) throws IOException {
        tableReader.f1214rf.seek((long) location);
        int markCount = tableReader.f1214rf.readUnsignedShort();
        int[] classes = new int[markCount];
        int[] locations = new int[markCount];
        for (int k = 0; k < markCount; k++) {
            classes[k] = tableReader.f1214rf.readUnsignedShort();
            locations[k] = location + tableReader.f1214rf.readUnsignedShort();
        }
        List<OtfMarkRecord> marks = new ArrayList<>();
        for (int k2 = 0; k2 < markCount; k2++) {
            OtfMarkRecord rec = new OtfMarkRecord();
            rec.markClass = classes[k2];
            rec.anchor = readGposAnchor(tableReader, locations[k2]);
            marks.add(rec);
        }
        return marks;
    }

    public static SubstLookupRecord[] readSubstLookupRecords(RandomAccessFileOrArray rf, int substCount) throws IOException {
        SubstLookupRecord[] substLookUpRecords = new SubstLookupRecord[substCount];
        for (int i = 0; i < substCount; i++) {
            SubstLookupRecord slr = new SubstLookupRecord();
            slr.sequenceIndex = rf.readUnsignedShort();
            slr.lookupListIndex = rf.readUnsignedShort();
            substLookUpRecords[i] = slr;
        }
        return substLookUpRecords;
    }

    public static PosLookupRecord[] readPosLookupRecords(RandomAccessFileOrArray rf, int recordCount) throws IOException {
        PosLookupRecord[] posLookUpRecords = new PosLookupRecord[recordCount];
        for (int i = 0; i < recordCount; i++) {
            PosLookupRecord lookupRecord = new PosLookupRecord();
            lookupRecord.sequenceIndex = rf.readUnsignedShort();
            lookupRecord.lookupListIndex = rf.readUnsignedShort();
            posLookUpRecords[i] = lookupRecord;
        }
        return posLookUpRecords;
    }

    public static GposAnchor[] readAnchorArray(OpenTypeFontTableReader tableReader, int[] locations, int left, int right) throws IOException {
        GposAnchor[] anchors = new GposAnchor[(right - left)];
        for (int i = left; i < right; i++) {
            anchors[i - left] = readGposAnchor(tableReader, locations[i]);
        }
        return anchors;
    }

    public static List<GposAnchor[]> readBaseArray(OpenTypeFontTableReader tableReader, int classCount, int location) throws IOException {
        List<GposAnchor[]> baseArray = new ArrayList<>();
        tableReader.f1214rf.seek((long) location);
        int baseCount = tableReader.f1214rf.readUnsignedShort();
        int[] anchorLocations = readUShortArray(tableReader.f1214rf, baseCount * classCount, location);
        int idx = 0;
        for (int k = 0; k < baseCount; k++) {
            baseArray.add(readAnchorArray(tableReader, anchorLocations, idx, idx + classCount));
            idx += classCount;
        }
        return baseArray;
    }

    public static List<List<GposAnchor[]>> readLigatureArray(OpenTypeFontTableReader tableReader, int classCount, int location) throws IOException {
        List<List<GposAnchor[]>> ligatureArray = new ArrayList<>();
        tableReader.f1214rf.seek((long) location);
        int ligatureCount = tableReader.f1214rf.readUnsignedShort();
        int[] ligatureAttachLocations = readUShortArray(tableReader.f1214rf, ligatureCount, location);
        for (int liga = 0; liga < ligatureCount; liga++) {
            int ligatureAttachLocation = ligatureAttachLocations[liga];
            List<GposAnchor[]> ligatureAttach = new ArrayList<>();
            tableReader.f1214rf.seek((long) ligatureAttachLocation);
            int componentCount = tableReader.f1214rf.readUnsignedShort();
            int[] componentRecordsLocation = readUShortArray(tableReader.f1214rf, classCount * componentCount, ligatureAttachLocation);
            int idx = 0;
            for (int k = 0; k < componentCount; k++) {
                ligatureAttach.add(readAnchorArray(tableReader, componentRecordsLocation, idx, idx + classCount));
                idx += classCount;
            }
            ligatureArray.add(ligatureAttach);
        }
        return ligatureArray;
    }
}
