package com.itextpdf.p026io.font.woff2;

import com.itextpdf.p026io.codec.brotli.dec.BrotliInputStream;
import com.itextpdf.p026io.font.woff2.Woff2Common;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* renamed from: com.itextpdf.io.font.woff2.Woff2Dec */
class Woff2Dec {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int FLAG_ARG_1_AND_2_ARE_WORDS = 1;
    private static final int FLAG_MORE_COMPONENTS = 32;
    private static final int FLAG_WE_HAVE_AN_X_AND_Y_SCALE = 64;
    private static final int FLAG_WE_HAVE_A_SCALE = 8;
    private static final int FLAG_WE_HAVE_A_TWO_BY_TWO = 128;
    private static final int FLAG_WE_HAVE_INSTRUCTIONS = 256;
    private static final int kCheckSumAdjustmentOffset = 8;
    private static final int kCompositeGlyphBegin = 10;
    private static final int kDefaultGlyphBuf = 5120;
    private static final int kEndPtsOfContoursOffset = 10;
    private static final int kGlyfOnCurve = 1;
    private static final int kGlyfRepeat = 8;
    private static final int kGlyfThisXIsSame = 16;
    private static final int kGlyfThisYIsSame = 32;
    private static final int kGlyfXShort = 2;
    private static final int kGlyfYShort = 4;
    private static final float kMaxPlausibleCompressionRatio = 100.0f;

    Woff2Dec() {
    }

    /* renamed from: com.itextpdf.io.font.woff2.Woff2Dec$TtcFont */
    private static class TtcFont {
        public int dst_offset;
        public int flavor;
        public int header_checksum;
        public short[] table_indices;

        private TtcFont() {
        }
    }

    /* renamed from: com.itextpdf.io.font.woff2.Woff2Dec$Woff2Header */
    private static class Woff2Header {
        public int compressed_length;
        public long compressed_offset;
        public int flavor;
        public int header_version;
        public short num_tables;
        public Woff2Common.Table[] tables;
        public TtcFont[] ttc_fonts;
        public int uncompressed_size;

        private Woff2Header() {
        }
    }

    /* renamed from: com.itextpdf.io.font.woff2.Woff2Dec$Woff2FontInfo */
    private static class Woff2FontInfo {
        public short index_format;
        public short num_glyphs;
        public short num_hmetrics;
        public Map<Integer, Integer> table_entry_by_tag;
        public short[] x_mins;

        private Woff2FontInfo() {
            this.table_entry_by_tag = new HashMap();
        }
    }

    /* renamed from: com.itextpdf.io.font.woff2.Woff2Dec$RebuildMetadata */
    private static class RebuildMetadata {
        Map<TableChecksumInfo, Integer> checksums;
        Woff2FontInfo[] font_infos;
        int header_checksum;

        private RebuildMetadata() {
            this.checksums = new HashMap();
        }
    }

    /* renamed from: com.itextpdf.io.font.woff2.Woff2Dec$TableChecksumInfo */
    private static class TableChecksumInfo {
        public int offset;
        public int tag;

        public TableChecksumInfo(int tag2, int offset2) {
            this.tag = tag2;
            this.offset = offset2;
        }

        public int hashCode() {
            return (new Integer(this.tag).hashCode() * 13) + new Integer(this.offset).hashCode();
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof TableChecksumInfo)) {
                return false;
            }
            TableChecksumInfo info = (TableChecksumInfo) o;
            if (this.tag == info.tag && this.offset == info.offset) {
                return true;
            }
            return false;
        }
    }

    private static int withSign(int flag, int baseval) {
        return (flag & 1) != 0 ? baseval : -baseval;
    }

    private static int tripletDecode(byte[] data, int flags_in_offset, int in_offset, int in_size, int n_points, Woff2Common.Point[] result) {
        int n_data_bytes;
        int dy;
        int dx;
        int i = in_size;
        int i2 = n_points;
        int x = 0;
        int y = 0;
        if (i2 <= i) {
            int triplet_index = 0;
            for (int i3 = 0; i3 < i2; i3++) {
                int flag = JavaUnsignedUtil.asU8(data[i3 + flags_in_offset]);
                boolean on_curve = (flag >> 7) == 0;
                int flag2 = flag & 127;
                if (flag2 < 84) {
                    n_data_bytes = 1;
                } else if (flag2 < 120) {
                    n_data_bytes = 2;
                } else if (flag2 < 124) {
                    n_data_bytes = 3;
                } else {
                    n_data_bytes = 4;
                }
                if (triplet_index + n_data_bytes > i || triplet_index + n_data_bytes < triplet_index) {
                    throw new FontCompressionException(FontCompressionException.RECONSTRUCT_GLYPH_FAILED);
                }
                if (flag2 < 10) {
                    dx = 0;
                    dy = withSign(flag2, ((flag2 & 14) << 7) + JavaUnsignedUtil.asU8(data[in_offset + triplet_index]));
                } else if (flag2 < 20) {
                    dx = withSign(flag2, (((flag2 - 10) & 14) << 7) + JavaUnsignedUtil.asU8(data[in_offset + triplet_index]));
                    dy = 0;
                } else if (flag2 < 84) {
                    int b0 = flag2 - 20;
                    int b1 = JavaUnsignedUtil.asU8(data[in_offset + triplet_index]);
                    int dx2 = withSign(flag2, (b0 & 48) + 1 + (b1 >> 4));
                    dy = withSign(flag2 >> 1, ((b0 & 12) << 2) + 1 + (b1 & 15));
                    dx = dx2;
                } else if (flag2 < 120) {
                    int b02 = flag2 - 84;
                    int dx3 = withSign(flag2, ((b02 / 12) << 8) + 1 + JavaUnsignedUtil.asU8(data[in_offset + triplet_index]));
                    dy = withSign(flag2 >> 1, (((b02 % 12) >> 2) << 8) + 1 + JavaUnsignedUtil.asU8(data[in_offset + triplet_index + 1]));
                    dx = dx3;
                } else if (flag2 < 124) {
                    int b2 = JavaUnsignedUtil.asU8(data[in_offset + triplet_index + 1]);
                    int withSign = withSign(flag2, (JavaUnsignedUtil.asU8(data[in_offset + triplet_index]) << 4) + (b2 >> 4));
                    dy = withSign(flag2 >> 1, ((b2 & 15) << 8) + JavaUnsignedUtil.asU8(data[in_offset + triplet_index + 2]));
                    dx = withSign;
                } else {
                    dx = withSign(flag2, (JavaUnsignedUtil.asU8(data[in_offset + triplet_index]) << 8) + JavaUnsignedUtil.asU8(data[in_offset + triplet_index + 1]));
                    dy = withSign(flag2 >> 1, (JavaUnsignedUtil.asU8(data[(in_offset + triplet_index) + 2]) << 8) + JavaUnsignedUtil.asU8(data[in_offset + triplet_index + 3]));
                }
                triplet_index += n_data_bytes;
                x += dx;
                y += dy;
                result[i3] = new Woff2Common.Point(x, y, on_curve);
            }
            return triplet_index;
        }
        throw new FontCompressionException(FontCompressionException.RECONSTRUCT_GLYPH_FAILED);
    }

    private static int storePoints(int n_points, Woff2Common.Point[] points, int n_contours, int instruction_length, byte[] dst, int dst_size) {
        int i = n_points;
        byte[] bArr = dst;
        int i2 = dst_size;
        int flag_offset = (n_contours * 2) + 10 + 2 + instruction_length;
        int i3 = 65535;
        int repeat_count = 0;
        int last_x = 0;
        int last_y = 0;
        int x_bytes = 0;
        int y_bytes = 0;
        for (int i4 = 0; i4 < i; i4++) {
            Woff2Common.Point point = points[i4];
            int flag = point.on_curve;
            int dx = point.f1216x - last_x;
            int dy = point.f1217y - last_y;
            int i5 = 0;
            if (dx == 0) {
                flag |= 16;
                int i6 = last_x;
            } else {
                int i7 = last_x;
                if (dx <= -256 || dx >= 256) {
                    x_bytes += 2;
                } else {
                    flag |= (dx > 0 ? 16 : 0) | 2;
                    x_bytes++;
                }
            }
            if (dy == 0) {
                flag |= 32;
            } else if (dy <= -256 || dy >= 256) {
                y_bytes += 2;
            } else {
                if (dy > 0) {
                    i5 = 32;
                }
                flag |= i5 | 4;
                y_bytes++;
            }
            if (flag != i3 || repeat_count == 255) {
                if (repeat_count == 0) {
                    int last_flag = i3;
                } else if (flag_offset < i2) {
                    char c = i3;
                    bArr[flag_offset] = (byte) repeat_count;
                    flag_offset++;
                } else {
                    int last_flag2 = i3;
                    throw new FontCompressionException(FontCompressionException.RECONSTRUCT_POINT_FAILED);
                }
                if (flag_offset < i2) {
                    bArr[flag_offset] = (byte) flag;
                    repeat_count = 0;
                    flag_offset++;
                } else {
                    throw new FontCompressionException(FontCompressionException.RECONSTRUCT_POINT_FAILED);
                }
            } else {
                int i8 = flag_offset - 1;
                bArr[i8] = (byte) (bArr[i8] | 8);
                repeat_count++;
                char c2 = i3;
            }
            last_x = point.f1216x;
            last_y = point.f1217y;
            i3 = flag;
        }
        int last_flag3 = i3;
        int i9 = last_x;
        if (repeat_count != 0) {
            if (flag_offset < i2) {
                bArr[flag_offset] = (byte) repeat_count;
                flag_offset++;
            } else {
                throw new FontCompressionException(FontCompressionException.RECONSTRUCT_POINT_FAILED);
            }
        }
        int xy_bytes = x_bytes + y_bytes;
        if (xy_bytes < x_bytes || flag_offset + xy_bytes < flag_offset || flag_offset + xy_bytes > i2) {
            throw new FontCompressionException(FontCompressionException.RECONSTRUCT_POINT_FAILED);
        }
        int x_offset = flag_offset;
        int y_offset = flag_offset + x_bytes;
        int last_x2 = 0;
        int last_y2 = 0;
        for (int i10 = 0; i10 < i; i10++) {
            int dx2 = points[i10].f1216x - last_x2;
            if (dx2 != 0) {
                if (dx2 <= -256 || dx2 >= 256) {
                    x_offset = StoreBytes.storeU16(bArr, x_offset, dx2);
                } else {
                    bArr[x_offset] = (byte) Math.abs(dx2);
                    x_offset++;
                }
            }
            last_x2 += dx2;
            int dy2 = points[i10].f1217y - last_y2;
            if (dy2 != 0) {
                if (dy2 <= -256 || dy2 >= 256) {
                    y_offset = StoreBytes.storeU16(bArr, y_offset, dy2);
                } else {
                    bArr[y_offset] = (byte) Math.abs(dy2);
                    y_offset++;
                }
            }
            last_y2 += dy2;
        }
        return y_offset;
    }

    private static void computeBbox(int n_points, Woff2Common.Point[] points, byte[] dst) {
        int x_min = 0;
        int y_min = 0;
        int x_max = 0;
        int y_max = 0;
        if (n_points > 0) {
            x_min = points[0].f1216x;
            x_max = points[0].f1216x;
            y_min = points[0].f1217y;
            y_max = points[0].f1217y;
        }
        for (int i = 1; i < n_points; i++) {
            int x = points[i].f1216x;
            int y = points[i].f1217y;
            x_min = Math.min(x, x_min);
            x_max = Math.max(x, x_max);
            y_min = Math.min(y, y_min);
            y_max = Math.max(y, y_max);
        }
        int offset = StoreBytes.storeU16(dst, StoreBytes.storeU16(dst, StoreBytes.storeU16(dst, StoreBytes.storeU16(dst, 2, x_min), y_min), x_max), y_max);
    }

    private static CompositeGlyphInfo sizeOfComposite(Buffer composite_stream) {
        int arg_size;
        Buffer composite_stream2 = new Buffer(composite_stream);
        int start_offset = composite_stream2.getOffset();
        boolean we_have_instructions = false;
        int flags = 32;
        while ((flags & 32) != 0) {
            flags = JavaUnsignedUtil.asU16(composite_stream2.readShort());
            we_have_instructions |= (flags & 256) != 0;
            if ((flags & 1) != 0) {
                arg_size = 2 + 4;
            } else {
                arg_size = 2 + 2;
            }
            if ((flags & 8) != 0) {
                arg_size += 2;
            } else if ((flags & 64) != 0) {
                arg_size += 4;
            } else if ((flags & 128) != 0) {
                arg_size += 8;
            }
            composite_stream2.skip(arg_size);
        }
        return new CompositeGlyphInfo(composite_stream2.getOffset() - start_offset, we_have_instructions);
    }

    /* renamed from: com.itextpdf.io.font.woff2.Woff2Dec$CompositeGlyphInfo */
    private static class CompositeGlyphInfo {
        public boolean have_instructions;
        public int size;

        public CompositeGlyphInfo(int size2, boolean have_instructions2) {
            this.size = size2;
            this.have_instructions = have_instructions2;
        }
    }

    private static void pad4(Woff2Out out) {
        byte[] zeroes = {0, 0, 0};
        if (out.size() + 3 >= out.size()) {
            int pad_bytes = Round.round4(out.size()) - out.size();
            if (pad_bytes > 0) {
                out.write(zeroes, 0, pad_bytes);
                return;
            }
            return;
        }
        throw new FontCompressionException(FontCompressionException.PADDING_OVERFLOW);
    }

    private static int storeLoca(int[] loca_values, int index_format, Woff2Out out) {
        long loca_size = (long) loca_values.length;
        long offset_size = index_format != 0 ? 4 : 2;
        if (((loca_size << 2) >> 2) == loca_size) {
            byte[] loca_content = new byte[((int) (loca_size * offset_size))];
            int offset = 0;
            for (int value : loca_values) {
                if (index_format != 0) {
                    offset = StoreBytes.storeU32(loca_content, offset, value);
                } else {
                    offset = StoreBytes.storeU16(loca_content, offset, value >> 1);
                }
            }
            int checksum = Woff2Common.computeULongSum(loca_content, 0, loca_content.length);
            out.write(loca_content, 0, loca_content.length);
            return checksum;
        }
        throw new FontCompressionException(FontCompressionException.LOCA_SIZE_OVERFLOW);
    }

    private static Checksums reconstructGlyf(byte[] data, int data_offset, Woff2Common.Table glyf_table, int glyph_checksum, Woff2Common.Table loca_table, int loca_checksum, Woff2FontInfo info, Woff2Out out) {
        Buffer composite_stream;
        int[] loca_values;
        int i;
        int bbox_bitmap_offset;
        byte[] bitmap;
        int points_size;
        int bitmap_length;
        ArrayList<Integer> n_points_vec;
        Buffer glyph_stream;
        String str;
        Buffer flag_stream;
        byte[] glyph_buf;
        Woff2Common.Point[] points;
        Buffer composite_stream2;
        Buffer instruction_stream;
        Woff2FontInfo woff2FontInfo;
        Woff2Common.Point[] points2;
        byte[] glyph_buf2;
        int instruction_size;
        byte[] bArr = data;
        int i2 = data_offset;
        Woff2Common.Table table = glyf_table;
        Woff2Common.Table table2 = loca_table;
        Woff2FontInfo woff2FontInfo2 = info;
        Woff2Out woff2Out = out;
        Buffer file = new Buffer(bArr, i2, table.transform_length);
        ArrayList<StreamInfo> substreams = new ArrayList<>(7);
        int glyf_start = out.size();
        int readInt = file.readInt();
        woff2FontInfo2.num_glyphs = file.readShort();
        woff2FontInfo2.index_format = file.readShort();
        int i3 = table.transform_length;
        String str2 = FontCompressionException.RECONSTRUCT_GLYF_TABLE_FAILED;
        if (36 <= i3) {
            int i4 = 0;
            int offset = 36;
            while (i4 < 7) {
                int substream_size = file.readInt();
                if (substream_size <= table.transform_length - offset) {
                    substreams.add(new StreamInfo(i2 + offset, substream_size));
                    offset += substream_size;
                    i4++;
                } else {
                    throw new FontCompressionException(str2);
                }
            }
            Buffer n_contour_stream = new Buffer(bArr, substreams.get(0).offset, substreams.get(0).length);
            Buffer n_points_stream = new Buffer(bArr, substreams.get(1).offset, substreams.get(1).length);
            Buffer flag_stream2 = new Buffer(bArr, substreams.get(2).offset, substreams.get(2).length);
            Buffer glyph_stream2 = new Buffer(bArr, substreams.get(3).offset, substreams.get(3).length);
            Buffer composite_stream3 = new Buffer(bArr, substreams.get(4).offset, substreams.get(4).length);
            Buffer bbox_stream = new Buffer(bArr, substreams.get(5).offset, substreams.get(5).length);
            Buffer instruction_stream2 = new Buffer(bArr, substreams.get(6).offset, substreams.get(6).length);
            int[] loca_values2 = new int[(JavaUnsignedUtil.asU16(woff2FontInfo2.num_glyphs) + 1)];
            ArrayList<Integer> n_points_vec2 = new ArrayList<>();
            int[] loca_values3 = loca_values2;
            Buffer buffer = file;
            Woff2Common.Point[] points3 = new Woff2Common.Point[0];
            int bbox_bitmap_offset2 = bbox_stream.getInitialOffset();
            int bitmap_length2 = ((JavaUnsignedUtil.asU16(woff2FontInfo2.num_glyphs) + 31) >> 5) << 2;
            bbox_stream.skip(bitmap_length2);
            ArrayList<StreamInfo> arrayList = substreams;
            byte[] glyph_buf3 = new byte[kDefaultGlyphBuf];
            woff2FontInfo2.x_mins = new short[JavaUnsignedUtil.asU16(woff2FontInfo2.num_glyphs)];
            int i5 = 0;
            int glyph_buf_size = kDefaultGlyphBuf;
            int points_size2 = 0;
            while (i5 < JavaUnsignedUtil.asU16(woff2FontInfo2.num_glyphs)) {
                int glyph_size = 0;
                boolean have_bbox = false;
                byte[] bitmap2 = new byte[bitmap_length2];
                int glyf_start2 = glyf_start;
                System.arraycopy(bArr, bbox_bitmap_offset2, bitmap2, 0, bitmap_length2);
                if ((bArr[bbox_bitmap_offset2 + (i5 >> 3)] & (128 >> (i5 & 7))) != 0) {
                    have_bbox = true;
                }
                int n_contours = JavaUnsignedUtil.asU16(n_contour_stream.readShort());
                int bitmap_length3 = bitmap_length2;
                if (n_contours != 65535) {
                    i = i5;
                    bitmap = bitmap2;
                    bbox_bitmap_offset = bbox_bitmap_offset2;
                    if (n_contours > 0) {
                        n_points_vec2.clear();
                        int total_n_points = 0;
                        int j = 0;
                        while (j < n_contours) {
                            int n_points_contour = VariableLength.read255UShort(n_points_stream);
                            n_points_vec2.add(Integer.valueOf(n_points_contour));
                            if (total_n_points + n_points_contour >= total_n_points) {
                                total_n_points += n_points_contour;
                                j++;
                            } else {
                                throw new FontCompressionException(str2);
                            }
                        }
                        int flag_size = total_n_points;
                        if (flag_size <= flag_stream2.getLength() - flag_stream2.getOffset()) {
                            int flags_buf_offset = flag_stream2.getOffset() + flag_stream2.getInitialOffset();
                            int triplet_buf_offset = glyph_stream2.getOffset() + glyph_stream2.getInitialOffset();
                            int triplet_size = glyph_stream2.getLength() - glyph_stream2.getOffset();
                            if (points_size2 < total_n_points) {
                                points_size2 = total_n_points;
                                points2 = new Woff2Common.Point[points_size2];
                            } else {
                                points2 = points3;
                            }
                            points_size = points_size2;
                            n_points_vec = n_points_vec2;
                            loca_values = loca_values3;
                            bitmap_length = bitmap_length3;
                            Woff2Common.Point[] points4 = points2;
                            composite_stream = composite_stream3;
                            int i6 = flags_buf_offset;
                            int i7 = triplet_buf_offset;
                            flag_stream = flag_stream2;
                            glyph_stream = glyph_stream2;
                            Buffer instruction_stream3 = instruction_stream2;
                            str = str2;
                            int triplet_bytes_consumed = tripletDecode(data, flags_buf_offset, triplet_buf_offset, triplet_size, total_n_points, points4);
                            flag_stream.skip(flag_size);
                            glyph_stream.skip(triplet_bytes_consumed);
                            int instruction_size2 = VariableLength.read255UShort(glyph_stream);
                            if (total_n_points >= 134217728 || instruction_size2 >= 1073741824) {
                                Buffer buffer2 = instruction_stream3;
                                int i8 = triplet_bytes_consumed;
                                throw new FontCompressionException(str);
                            }
                            int size_needed = (n_contours * 2) + 12 + (total_n_points * 5) + instruction_size2;
                            if (glyph_buf_size < size_needed) {
                                glyph_buf2 = new byte[size_needed];
                                glyph_buf_size = size_needed;
                            } else {
                                glyph_buf2 = glyph_buf3;
                            }
                            int glyph_size2 = StoreBytes.storeU16(glyph_buf2, 0, n_contours);
                            if (have_bbox) {
                                bbox_stream.read(glyph_buf2, glyph_size2, 8);
                                points = points4;
                            } else {
                                points = points4;
                                computeBbox(total_n_points, points, glyph_buf2);
                            }
                            int glyph_size3 = 10;
                            int end_point = -1;
                            int i9 = triplet_bytes_consumed;
                            int contour_ix = 0;
                            while (contour_ix < n_contours) {
                                end_point += n_points_vec.get(contour_ix).intValue();
                                int size_needed2 = size_needed;
                                if (end_point < 65536) {
                                    glyph_size3 = StoreBytes.storeU16(glyph_buf2, glyph_size3, end_point);
                                    contour_ix++;
                                    size_needed = size_needed2;
                                } else {
                                    throw new FontCompressionException(str);
                                }
                            }
                            int contour_ix2 = StoreBytes.storeU16(glyph_buf2, glyph_size3, instruction_size2);
                            composite_stream2 = instruction_stream3;
                            composite_stream2.read(glyph_buf2, contour_ix2, instruction_size2);
                            int glyph_size4 = contour_ix2 + instruction_size2;
                            glyph_size = storePoints(total_n_points, points, n_contours, instruction_size2, glyph_buf2, glyph_buf_size);
                            glyph_buf = glyph_buf2;
                        } else {
                            Buffer composite_stream4 = instruction_stream2;
                            int i10 = points_size2;
                            ArrayList<Integer> arrayList2 = n_points_vec2;
                            throw new FontCompressionException(str2);
                        }
                    } else {
                        composite_stream = composite_stream3;
                        flag_stream = flag_stream2;
                        glyph_stream = glyph_stream2;
                        composite_stream2 = instruction_stream2;
                        int points_size3 = points_size2;
                        loca_values = loca_values3;
                        bitmap_length = bitmap_length3;
                        n_points_vec = n_points_vec2;
                        str = str2;
                        points = points3;
                        points_size = points_size3;
                        glyph_buf = glyph_buf3;
                    }
                } else if (have_bbox) {
                    CompositeGlyphInfo compositeGlyphInfo = sizeOfComposite(composite_stream3);
                    boolean have_instructions = compositeGlyphInfo.have_instructions;
                    bitmap = bitmap2;
                    int composite_size = compositeGlyphInfo.size;
                    if (have_instructions) {
                        CompositeGlyphInfo compositeGlyphInfo2 = compositeGlyphInfo;
                        instruction_size = VariableLength.read255UShort(glyph_stream2);
                    } else {
                        CompositeGlyphInfo compositeGlyphInfo3 = compositeGlyphInfo;
                        instruction_size = 0;
                    }
                    bbox_bitmap_offset = bbox_bitmap_offset2;
                    int bbox_bitmap_offset3 = composite_size + 12 + instruction_size;
                    if (glyph_buf_size < bbox_bitmap_offset3) {
                        i = i5;
                        glyph_buf = new byte[bbox_bitmap_offset3];
                        glyph_buf_size = bbox_bitmap_offset3;
                    } else {
                        i = i5;
                        glyph_buf = glyph_buf3;
                    }
                    int glyph_size5 = StoreBytes.storeU16(glyph_buf, 0, n_contours);
                    int glyph_buf_size2 = glyph_buf_size;
                    bbox_stream.read(glyph_buf, glyph_size5, 8);
                    int glyph_size6 = glyph_size5 + 8;
                    composite_stream3.read(glyph_buf, glyph_size6, composite_size);
                    glyph_size = glyph_size6 + composite_size;
                    if (have_instructions) {
                        int glyph_size7 = StoreBytes.storeU16(glyph_buf, glyph_size, instruction_size);
                        instruction_stream2.read(glyph_buf, glyph_size7, instruction_size);
                        glyph_size = glyph_size7 + instruction_size;
                    }
                    composite_stream = composite_stream3;
                    flag_stream = flag_stream2;
                    glyph_stream = glyph_stream2;
                    composite_stream2 = instruction_stream2;
                    loca_values = loca_values3;
                    bitmap_length = bitmap_length3;
                    glyph_buf_size = glyph_buf_size2;
                    str = str2;
                    points = points3;
                    points_size = points_size2;
                    n_points_vec = n_points_vec2;
                } else {
                    throw new FontCompressionException(str2);
                }
                int[] loca_values4 = loca_values;
                loca_values4[i] = out.size() - glyf_start2;
                Woff2Out woff2Out2 = out;
                woff2Out2.write(glyph_buf, 0, glyph_size);
                pad4(out);
                int glyph_checksum2 = glyph_checksum + Woff2Common.computeULongSum(glyph_buf, 0, glyph_size);
                if (n_contours > 0) {
                    woff2FontInfo = info;
                    instruction_stream = composite_stream2;
                    byte[] bArr2 = bitmap;
                    woff2FontInfo.x_mins[i] = new Buffer(glyph_buf, 2, 2).readShort();
                } else {
                    woff2FontInfo = info;
                    instruction_stream = composite_stream2;
                    byte[] bArr3 = bitmap;
                }
                bArr = data;
                Woff2Out woff2Out3 = woff2Out2;
                glyph_checksum = glyph_checksum2;
                glyph_buf3 = glyph_buf;
                flag_stream2 = flag_stream;
                glyph_stream2 = glyph_stream;
                glyf_start = glyf_start2;
                bbox_bitmap_offset2 = bbox_bitmap_offset;
                woff2FontInfo2 = woff2FontInfo;
                i5 = i + 1;
                n_points_vec2 = n_points_vec;
                points_size2 = points_size;
                composite_stream3 = composite_stream;
                points3 = points;
                str2 = str;
                instruction_stream2 = instruction_stream;
                int i11 = bitmap_length;
                loca_values3 = loca_values4;
                bitmap_length2 = i11;
            }
            int i12 = i5;
            int i13 = bbox_bitmap_offset2;
            int i14 = points_size2;
            ArrayList<Integer> arrayList3 = n_points_vec2;
            Buffer buffer3 = glyph_stream2;
            Woff2FontInfo woff2FontInfo3 = woff2FontInfo2;
            Buffer buffer4 = flag_stream2;
            int[] iArr = loca_values3;
            int i15 = bitmap_length2;
            int[] loca_values5 = iArr;
            Woff2Common.Table table3 = glyf_table;
            table3.dst_length = out.size() - table3.dst_offset;
            Woff2Common.Table table4 = loca_table;
            table4.dst_offset = out.size();
            loca_values5[JavaUnsignedUtil.asU16(woff2FontInfo3.num_glyphs)] = table3.dst_length;
            int loca_checksum2 = storeLoca(loca_values5, woff2FontInfo3.index_format, out);
            table4.dst_length = out.size() - table4.dst_offset;
            return new Checksums(loca_checksum2, glyph_checksum);
        }
        throw new FontCompressionException(str2);
    }

    /* renamed from: com.itextpdf.io.font.woff2.Woff2Dec$Checksums */
    private static class Checksums {
        public int glyph_checksum;
        public int loca_checksum;

        public Checksums(int loca_checksum2, int glyph_checksum2) {
            this.loca_checksum = loca_checksum2;
            this.glyph_checksum = glyph_checksum2;
        }
    }

    /* renamed from: com.itextpdf.io.font.woff2.Woff2Dec$StreamInfo */
    private static class StreamInfo {
        public int length;
        public int offset;

        public StreamInfo(int offset2, int length2) {
            this.offset = offset2;
            this.length = length2;
        }
    }

    private static Woff2Common.Table findTable(ArrayList<Woff2Common.Table> tables, int tag) {
        Iterator<Woff2Common.Table> it = tables.iterator();
        while (it.hasNext()) {
            Woff2Common.Table table = it.next();
            if (table.tag == tag) {
                return table;
            }
        }
        return null;
    }

    private static short readNumHMetrics(byte[] data, int offset, int data_length) {
        Buffer buffer = new Buffer(data, offset, data_length);
        buffer.skip(34);
        return buffer.readShort();
    }

    private static int reconstructTransformedHmtx(byte[] transformed_buf, int transformed_offset, int transformed_size, int num_glyphs, int num_hmetrics, short[] x_mins, Woff2Out out) {
        short lsb;
        short lsb2;
        int i = num_glyphs;
        int i2 = num_hmetrics;
        short[] sArr = x_mins;
        Buffer hmtx_buff_in = new Buffer(transformed_buf, transformed_offset, transformed_size);
        int hmtx_flags = JavaUnsignedUtil.asU8(hmtx_buff_in.readByte());
        boolean has_proportional_lsbs = (hmtx_flags & 1) == 0;
        boolean has_monospace_lsbs = (hmtx_flags & 2) == 0;
        if (has_proportional_lsbs && has_monospace_lsbs) {
            throw new FontCompressionException(FontCompressionException.RECONSTRUCT_HMTX_TABLE_FAILED);
        } else if (sArr == null || sArr.length != i) {
            Woff2Out woff2Out = out;
            throw new FontCompressionException(FontCompressionException.RECONSTRUCT_HMTX_TABLE_FAILED);
        } else if (i2 > i) {
            Woff2Out woff2Out2 = out;
            throw new FontCompressionException(FontCompressionException.RECONSTRUCT_HMTX_TABLE_FAILED);
        } else if (i2 >= 1) {
            short[] advance_widths = new short[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                advance_widths[i3] = hmtx_buff_in.readShort();
            }
            short[] lsbs = new short[i];
            for (int i4 = 0; i4 < i2; i4++) {
                if (has_proportional_lsbs) {
                    lsb2 = hmtx_buff_in.readShort();
                } else {
                    lsb2 = sArr[i4];
                }
                lsbs[i4] = lsb2;
            }
            for (int i5 = num_hmetrics; i5 < i; i5++) {
                if (has_monospace_lsbs) {
                    lsb = hmtx_buff_in.readShort();
                } else {
                    lsb = sArr[i5];
                }
                lsbs[i5] = lsb;
            }
            int hmtx_output_size = (i * 2) + (i2 * 2);
            byte[] hmtx_table = new byte[hmtx_output_size];
            int dst_offset = 0;
            int i6 = 0;
            while (i6 < i) {
                if (i6 < i2) {
                    dst_offset = StoreBytes.storeU16(hmtx_table, dst_offset, advance_widths[i6]);
                }
                dst_offset = StoreBytes.storeU16(hmtx_table, dst_offset, lsbs[i6]);
                i6++;
                i = num_glyphs;
            }
            int checksum = Woff2Common.computeULongSum(hmtx_table, 0, hmtx_output_size);
            out.write(hmtx_table, 0, hmtx_output_size);
            return checksum;
        } else {
            Woff2Out woff2Out3 = out;
            throw new FontCompressionException(FontCompressionException.RECONSTRUCT_HMTX_TABLE_FAILED);
        }
    }

    private static void woff2Uncompress(byte[] dst_buf, int dst_offset, int dst_length, byte[] src_buf, int src_offset, int src_length) {
        int remain = dst_length;
        try {
            BrotliInputStream stream = new BrotliInputStream(new ByteArrayInputStream(src_buf, src_offset, src_length));
            while (remain > 0) {
                int read = stream.read(dst_buf, dst_offset, dst_length);
                if (read >= 0) {
                    remain -= read;
                } else {
                    throw new FontCompressionException(FontCompressionException.BROTLI_DECODING_FAILED);
                }
            }
            if (stream.read() != -1) {
                throw new FontCompressionException(FontCompressionException.BROTLI_DECODING_FAILED);
            } else if (remain != 0) {
                throw new FontCompressionException(FontCompressionException.BROTLI_DECODING_FAILED);
            }
        } catch (IOException e) {
            throw new FontCompressionException(FontCompressionException.BROTLI_DECODING_FAILED);
        }
    }

    private static void readTableDirectory(Buffer file, Woff2Common.Table[] tables, int num_tables) {
        int tag;
        int src_offset = 0;
        int i = 0;
        while (i < num_tables) {
            Woff2Common.Table table = new Woff2Common.Table();
            tables[i] = table;
            int flag_byte = JavaUnsignedUtil.asU8(file.readByte());
            if ((flag_byte & 63) == 63) {
                tag = file.readInt();
            } else {
                tag = TableTags.kKnownTags[flag_byte & 63];
            }
            int flags = 0;
            int xform_version = (flag_byte >> 6) & 3;
            if (tag == 1735162214 || tag == 1819239265) {
                if (xform_version == 0) {
                    flags = 0 | 256;
                }
            } else if (xform_version != 0) {
                flags = 0 | 256;
            }
            int flags2 = flags | xform_version;
            int dst_length = VariableLength.readBase128(file);
            int transform_length = dst_length;
            if ((flags2 & 256) != 0) {
                transform_length = VariableLength.readBase128(file);
                if (tag == 1819239265 && transform_length != 0) {
                    throw new FontCompressionException(FontCompressionException.READ_TABLE_DIRECTORY_FAILED);
                }
            }
            if (src_offset + transform_length >= src_offset) {
                table.src_offset = src_offset;
                table.src_length = transform_length;
                src_offset += transform_length;
                table.tag = tag;
                table.flags = flags2;
                table.transform_length = transform_length;
                table.dst_length = dst_length;
                i++;
            } else {
                throw new FontCompressionException(FontCompressionException.READ_TABLE_DIRECTORY_FAILED);
            }
        }
    }

    private static int storeOffsetTable(byte[] result, int offset, int flavor, int num_tables) {
        int offset2 = StoreBytes.storeU16(result, StoreBytes.storeU32(result, offset, flavor), num_tables);
        int max_pow2 = 0;
        while ((1 << (max_pow2 + 1)) <= num_tables) {
            max_pow2++;
        }
        int output_search_range = (1 << max_pow2) << 4;
        return StoreBytes.storeU16(result, StoreBytes.storeU16(result, StoreBytes.storeU16(result, offset2, output_search_range), max_pow2), (num_tables << 4) - output_search_range);
    }

    private static int storeTableEntry(byte[] result, int offset, int tag) {
        return StoreBytes.storeU32(result, StoreBytes.storeU32(result, StoreBytes.storeU32(result, StoreBytes.storeU32(result, offset, tag), 0), 0), 0);
    }

    private static long computeOffsetToFirstTable(Woff2Header hdr) {
        long offset = (long) ((hdr.num_tables * 16) + 12);
        if (hdr.header_version != 0) {
            offset = (long) (Woff2Common.collectionHeaderSize(hdr.header_version, hdr.ttc_fonts.length) + (hdr.ttc_fonts.length * 12));
            for (TtcFont ttc_font : hdr.ttc_fonts) {
                offset += (long) (ttc_font.table_indices.length * 16);
            }
        }
        return offset;
    }

    private static ArrayList<Woff2Common.Table> tables(Woff2Header hdr, int font_index) {
        ArrayList<Woff2Common.Table> tables = new ArrayList<>();
        int i = 0;
        if (hdr.header_version != 0) {
            short[] sArr = hdr.ttc_fonts[font_index].table_indices;
            int length = sArr.length;
            while (i < length) {
                tables.add(hdr.tables[JavaUnsignedUtil.asU16(sArr[i])]);
                i++;
            }
        } else {
            Woff2Common.Table[] tableArr = hdr.tables;
            int length2 = tableArr.length;
            while (i < length2) {
                tables.add(tableArr[i]);
                i++;
            }
        }
        return tables;
    }

    private static void reconstructFont(byte[] transformed_buf, int transformed_buf_offset, int transformed_buf_size, RebuildMetadata metadata, Woff2Header hdr, int font_index, Woff2Out out) {
        int i;
        String str;
        Woff2Common.Table table;
        int checksum;
        TableChecksumInfo checksum_key;
        byte[] bArr = transformed_buf;
        RebuildMetadata rebuildMetadata = metadata;
        Woff2Header woff2Header = hdr;
        Woff2Out woff2Out = out;
        int dest_offset = out.size();
        byte[] table_entry = new byte[12];
        Woff2FontInfo info = rebuildMetadata.font_infos[font_index];
        ArrayList<Woff2Common.Table> tables = tables(hdr, font_index);
        boolean z = true;
        boolean z2 = findTable(tables, TableTags.kGlyfTableTag) != null;
        if (findTable(tables, TableTags.kLocaTableTag) == null) {
            z = false;
        }
        String str2 = FontCompressionException.RECONSTRUCT_TABLE_DIRECTORY_FAILED;
        if (z2 == z) {
            int font_checksum = rebuildMetadata.header_checksum;
            if (woff2Header.header_version != 0) {
                font_checksum = woff2Header.ttc_fonts[font_index].header_checksum;
            }
            int font_checksum2 = font_checksum;
            int loca_checksum = 0;
            int loca_checksum2 = 0;
            int dest_offset2 = dest_offset;
            while (loca_checksum2 < tables.size()) {
                Woff2Common.Table table2 = tables.get(loca_checksum2);
                TableChecksumInfo checksum_key2 = new TableChecksumInfo(table2.tag, table2.src_offset);
                boolean reused = rebuildMetadata.checksums.containsKey(checksum_key2);
                if (font_index != 0 || !reused) {
                    int i2 = loca_checksum2;
                    if (((long) table2.src_offset) + ((long) table2.src_length) <= ((long) transformed_buf_size)) {
                        if (table2.tag == 1751672161) {
                            info.num_hmetrics = readNumHMetrics(bArr, transformed_buf_offset + table2.src_offset, table2.src_length);
                        }
                        if (!reused) {
                            if ((table2.flags & 256) != 256) {
                                if (table2.tag == 1751474532) {
                                    if (table2.src_length >= 12) {
                                        StoreBytes.storeU32(bArr, transformed_buf_offset + table2.src_offset + 8, 0);
                                    } else {
                                        throw new FontCompressionException(str2);
                                    }
                                }
                                table2.dst_offset = dest_offset2;
                                checksum = Woff2Common.computeULongSum(bArr, transformed_buf_offset + table2.src_offset, table2.src_length);
                                woff2Out.write(bArr, transformed_buf_offset + table2.src_offset, table2.src_length);
                                int i3 = dest_offset2;
                                str = str2;
                                checksum_key = checksum_key2;
                                i = i2;
                                table = table2;
                            } else if (table2.tag == 1735162214) {
                                table2.dst_offset = dest_offset2;
                                Woff2Common.Table table3 = table2;
                                i = i2;
                                int i4 = dest_offset2;
                                str = str2;
                                checksum_key = checksum_key2;
                                Checksums resultChecksum = reconstructGlyf(transformed_buf, transformed_buf_offset + table2.src_offset, table3, 0, findTable(tables, TableTags.kLocaTableTag), loca_checksum, info, out);
                                checksum = resultChecksum.glyph_checksum;
                                loca_checksum = resultChecksum.loca_checksum;
                                table = table3;
                            } else {
                                int dest_offset3 = dest_offset2;
                                str = str2;
                                checksum_key = checksum_key2;
                                i = i2;
                                table = table2;
                                if (table.tag == 1819239265) {
                                    checksum = loca_checksum;
                                } else if (table.tag == 1752003704) {
                                    table.dst_offset = dest_offset3;
                                    checksum = reconstructTransformedHmtx(transformed_buf, transformed_buf_offset + table.src_offset, table.src_length, JavaUnsignedUtil.asU16(info.num_glyphs), JavaUnsignedUtil.asU16(info.num_hmetrics), info.x_mins, out);
                                } else {
                                    throw new FontCompressionException(str);
                                }
                            }
                            rebuildMetadata.checksums.put(checksum_key, Integer.valueOf(checksum));
                        } else {
                            str = str2;
                            TableChecksumInfo checksum_key3 = checksum_key2;
                            i = i2;
                            table = table2;
                            checksum = rebuildMetadata.checksums.get(checksum_key3).intValue();
                        }
                        StoreBytes.storeU32(table_entry, 0, checksum);
                        StoreBytes.storeU32(table_entry, 4, table.dst_offset);
                        StoreBytes.storeU32(table_entry, 8, table.dst_length);
                        woff2Out.write(table_entry, 0, info.table_entry_by_tag.get(Integer.valueOf(table.tag)).intValue() + 4, 12);
                        font_checksum2 = font_checksum2 + checksum + Woff2Common.computeULongSum(table_entry, 0, 12);
                        pad4(out);
                        byte[] table_entry2 = table_entry;
                        if (((long) table.dst_offset) + ((long) table.dst_length) <= ((long) out.size())) {
                            dest_offset2 = out.size();
                            loca_checksum2 = i + 1;
                            bArr = transformed_buf;
                            table_entry = table_entry2;
                            str2 = str;
                            Woff2Header woff2Header2 = hdr;
                        } else {
                            throw new FontCompressionException(str);
                        }
                    } else {
                        TableChecksumInfo tableChecksumInfo = checksum_key2;
                        Woff2Common.Table table4 = table2;
                        throw new FontCompressionException(str2);
                    }
                } else {
                    throw new FontCompressionException(str2);
                }
            }
            int i5 = loca_checksum2;
            int i6 = dest_offset2;
            String str3 = str2;
            byte[] bArr2 = table_entry;
            Woff2Common.Table head_table = findTable(tables, TableTags.kHeadTableTag);
            if (head_table == null) {
                return;
            }
            if (head_table.dst_length >= 12) {
                byte[] checksum_adjustment = new byte[4];
                StoreBytes.storeU32(checksum_adjustment, 0, -1313820742 - font_checksum2);
                woff2Out.write(checksum_adjustment, 0, head_table.dst_offset + 8, 4);
                return;
            }
            throw new FontCompressionException(str3);
        }
        throw new FontCompressionException(str2);
    }

    private static void readWoff2Header(byte[] data, int length, Woff2Header hdr) {
        int i = length;
        Woff2Header woff2Header = hdr;
        Buffer file = new Buffer(data, 0, i);
        int signature = file.readInt();
        if (signature == 2001684018) {
            woff2Header.flavor = file.readInt();
            int reported_length = file.readInt();
            if (reported_length <= 0) {
                throw new AssertionError();
            } else if (i == reported_length) {
                woff2Header.num_tables = file.readShort();
                if (woff2Header.num_tables != 0) {
                    file.skip(6);
                    woff2Header.compressed_length = file.readInt();
                    if (woff2Header.compressed_length >= 0) {
                        file.skip(4);
                        int meta_offset = file.readInt();
                        if (meta_offset >= 0) {
                            int meta_length = file.readInt();
                            if (meta_length >= 0) {
                                int meta_length_orig = file.readInt();
                                if (meta_length_orig < 0) {
                                    throw new AssertionError();
                                } else if (meta_offset == 0 || (meta_offset < i && i - meta_offset >= meta_length)) {
                                    int priv_offset = file.readInt();
                                    if (priv_offset >= 0) {
                                        int priv_length = file.readInt();
                                        if (priv_length < 0) {
                                            throw new AssertionError();
                                        } else if (priv_offset == 0 || (priv_offset < i && i - priv_offset >= priv_length)) {
                                            woff2Header.tables = new Woff2Common.Table[woff2Header.num_tables];
                                            readTableDirectory(file, woff2Header.tables, woff2Header.num_tables);
                                            Woff2Common.Table last_table = woff2Header.tables[woff2Header.tables.length - 1];
                                            woff2Header.uncompressed_size = last_table.src_offset + last_table.src_length;
                                            if (woff2Header.uncompressed_size <= 0) {
                                                throw new AssertionError();
                                            } else if (woff2Header.uncompressed_size >= last_table.src_offset) {
                                                woff2Header.header_version = 0;
                                                if (woff2Header.flavor == 1953784678) {
                                                    woff2Header.header_version = file.readInt();
                                                    if (woff2Header.header_version == 65536 || woff2Header.header_version == 131072) {
                                                        int num_fonts = VariableLength.read255UShort(file);
                                                        woff2Header.ttc_fonts = new TtcFont[num_fonts];
                                                        int i2 = 0;
                                                        while (i2 < num_fonts) {
                                                            int signature2 = signature;
                                                            TtcFont ttc_font = new TtcFont();
                                                            woff2Header.ttc_fonts[i2] = ttc_font;
                                                            int num_tables = VariableLength.read255UShort(file);
                                                            int reported_length2 = reported_length;
                                                            ttc_font.flavor = file.readInt();
                                                            ttc_font.table_indices = new short[num_tables];
                                                            Woff2Common.Table glyf_table = null;
                                                            Woff2Common.Table loca_table = null;
                                                            int meta_length_orig2 = meta_length_orig;
                                                            int j = 0;
                                                            while (j < num_tables) {
                                                                int num_tables2 = num_tables;
                                                                int table_idx = VariableLength.read255UShort(file);
                                                                Woff2Common.Table last_table2 = last_table;
                                                                if (table_idx < woff2Header.tables.length) {
                                                                    TtcFont ttc_font2 = ttc_font;
                                                                    ttc_font.table_indices[j] = (short) table_idx;
                                                                    Woff2Common.Table table = woff2Header.tables[table_idx];
                                                                    int i3 = table_idx;
                                                                    if (table.tag == 1819239265) {
                                                                        loca_table = table;
                                                                    }
                                                                    if (table.tag == 1735162214) {
                                                                        glyf_table = table;
                                                                    }
                                                                    j++;
                                                                    num_tables = num_tables2;
                                                                    last_table = last_table2;
                                                                    ttc_font = ttc_font2;
                                                                } else {
                                                                    throw new FontCompressionException(FontCompressionException.READ_COLLECTION_HEADER_FAILED);
                                                                }
                                                            }
                                                            int i4 = num_tables;
                                                            Woff2Common.Table last_table3 = last_table;
                                                            if ((glyf_table == null) == (loca_table == null)) {
                                                                i2++;
                                                                byte[] bArr = data;
                                                                signature = signature2;
                                                                reported_length = reported_length2;
                                                                meta_length_orig = meta_length_orig2;
                                                                last_table = last_table3;
                                                            } else {
                                                                throw new FontCompressionException(FontCompressionException.READ_COLLECTION_HEADER_FAILED);
                                                            }
                                                        }
                                                        int i5 = reported_length;
                                                        int i6 = meta_length_orig;
                                                        Woff2Common.Table table2 = last_table;
                                                    } else {
                                                        throw new FontCompressionException(FontCompressionException.READ_COLLECTION_HEADER_FAILED);
                                                    }
                                                } else {
                                                    int i7 = reported_length;
                                                    int i8 = meta_length_orig;
                                                    Woff2Common.Table table3 = last_table;
                                                }
                                                long first_table_offset = computeOffsetToFirstTable(hdr);
                                                woff2Header.compressed_offset = (long) file.getOffset();
                                                if (woff2Header.compressed_offset <= 2147483647L) {
                                                    long src_offset = Round.round4(woff2Header.compressed_offset + ((long) woff2Header.compressed_length));
                                                    long j2 = first_table_offset;
                                                    Buffer buffer = file;
                                                    if (src_offset <= ((long) i)) {
                                                        if (meta_offset != 0) {
                                                            if (src_offset == ((long) meta_offset)) {
                                                                src_offset = (long) Round.round4(meta_offset + meta_length);
                                                                if (src_offset > 2147483647L) {
                                                                    throw new FontCompressionException(FontCompressionException.READ_HEADER_FAILED);
                                                                }
                                                            } else {
                                                                throw new FontCompressionException(FontCompressionException.READ_HEADER_FAILED);
                                                            }
                                                        }
                                                        if (priv_offset != 0) {
                                                            if (src_offset == ((long) priv_offset)) {
                                                                src_offset = (long) Round.round4(priv_offset + priv_length);
                                                                if (src_offset > 2147483647L) {
                                                                    throw new FontCompressionException(FontCompressionException.READ_HEADER_FAILED);
                                                                }
                                                            } else {
                                                                throw new FontCompressionException(FontCompressionException.READ_HEADER_FAILED);
                                                            }
                                                        }
                                                        if (src_offset != ((long) Round.round4(length))) {
                                                            throw new FontCompressionException(FontCompressionException.READ_HEADER_FAILED);
                                                        }
                                                        return;
                                                    }
                                                    throw new FontCompressionException(FontCompressionException.READ_HEADER_FAILED);
                                                }
                                                throw new FontCompressionException(FontCompressionException.READ_HEADER_FAILED);
                                            } else {
                                                throw new FontCompressionException(FontCompressionException.READ_HEADER_FAILED);
                                            }
                                        } else {
                                            throw new FontCompressionException(FontCompressionException.READ_HEADER_FAILED);
                                        }
                                    } else {
                                        throw new AssertionError();
                                    }
                                } else {
                                    throw new FontCompressionException(FontCompressionException.READ_HEADER_FAILED);
                                }
                            } else {
                                throw new AssertionError();
                            }
                        } else {
                            throw new AssertionError();
                        }
                    } else {
                        throw new AssertionError();
                    }
                } else {
                    throw new FontCompressionException(FontCompressionException.READ_HEADER_FAILED);
                }
            } else {
                throw new FontCompressionException(FontCompressionException.READ_HEADER_FAILED);
            }
        } else {
            Buffer buffer2 = file;
            throw new FontCompressionException(FontCompressionException.INCORRECT_SIGNATURE);
        }
    }

    private static void writeHeaders(byte[] data, int length, RebuildMetadata metadata, Woff2Header hdr, Woff2Out out) {
        RebuildMetadata rebuildMetadata = metadata;
        Woff2Header woff2Header = hdr;
        long firstTableOffset = computeOffsetToFirstTable(hdr);
        if (firstTableOffset <= 2147483647L) {
            byte[] output = new byte[((int) firstTableOffset)];
            List<Woff2Common.Table> sorted_tables = new ArrayList<>(Arrays.asList(woff2Header.tables));
            if (woff2Header.header_version != 0) {
                TtcFont[] ttcFontArr = woff2Header.ttc_fonts;
                int length2 = ttcFontArr.length;
                int i = 0;
                while (i < length2) {
                    TtcFont ttc_font = ttcFontArr[i];
                    Map<Integer, Short> sorted_index_by_tag = new TreeMap<>();
                    short[] sArr = ttc_font.table_indices;
                    int length3 = sArr.length;
                    int i2 = 0;
                    while (i2 < length3) {
                        short table_index = sArr[i2];
                        sorted_index_by_tag.put(Integer.valueOf(woff2Header.tables[table_index].tag), Short.valueOf(table_index));
                        i2++;
                        firstTableOffset = firstTableOffset;
                    }
                    long firstTableOffset2 = firstTableOffset;
                    short index = 0;
                    for (Map.Entry<Integer, Short> i3 : sorted_index_by_tag.entrySet()) {
                        ttc_font.table_indices[index] = i3.getValue().shortValue();
                        index = (short) (index + 1);
                    }
                    i++;
                    firstTableOffset = firstTableOffset2;
                }
            } else {
                Collections.sort(sorted_tables);
            }
            byte[] result = output;
            C14151 r7 = null;
            if (woff2Header.header_version != 0) {
                int offset = StoreBytes.storeU32(result, StoreBytes.storeU32(result, StoreBytes.storeU32(result, 0, woff2Header.flavor), woff2Header.header_version), woff2Header.ttc_fonts.length);
                int offset_table = offset;
                for (int i4 = 0; i4 < woff2Header.ttc_fonts.length; i4++) {
                    offset = StoreBytes.storeU32(result, offset, 0);
                }
                if (woff2Header.header_version == 131072) {
                    offset = StoreBytes.storeU32(result, StoreBytes.storeU32(result, StoreBytes.storeU32(result, offset, 0), 0), 0);
                }
                rebuildMetadata.font_infos = new Woff2FontInfo[woff2Header.ttc_fonts.length];
                int i5 = 0;
                while (i5 < woff2Header.ttc_fonts.length) {
                    TtcFont ttc_font2 = woff2Header.ttc_fonts[i5];
                    int offset_table2 = StoreBytes.storeU32(result, offset_table, offset);
                    ttc_font2.dst_offset = offset;
                    int offset2 = storeOffsetTable(result, offset2, ttc_font2.flavor, ttc_font2.table_indices.length);
                    rebuildMetadata.font_infos[i5] = new Woff2FontInfo();
                    short[] sArr2 = ttc_font2.table_indices;
                    int length4 = sArr2.length;
                    int i6 = 0;
                    while (i6 < length4) {
                        int tag = woff2Header.tables[sArr2[i6]].tag;
                        rebuildMetadata.font_infos[i5].table_entry_by_tag.put(Integer.valueOf(tag), Integer.valueOf(offset2));
                        offset2 = storeTableEntry(result, offset2, tag);
                        i6++;
                        offset_table2 = offset_table2;
                    }
                    ttc_font2.header_checksum = Woff2Common.computeULongSum(output, ttc_font2.dst_offset, offset2 - ttc_font2.dst_offset);
                    i5++;
                    offset_table = offset_table2;
                    r7 = null;
                }
            } else {
                rebuildMetadata.font_infos = new Woff2FontInfo[1];
                int offset3 = storeOffsetTable(result, 0, woff2Header.flavor, woff2Header.num_tables);
                char c = 0;
                rebuildMetadata.font_infos[0] = new Woff2FontInfo();
                int i7 = 0;
                while (i7 < woff2Header.num_tables) {
                    rebuildMetadata.font_infos[c].table_entry_by_tag.put(Integer.valueOf(sorted_tables.get(i7).tag), Integer.valueOf(offset3));
                    offset3 = storeTableEntry(result, offset3, sorted_tables.get(i7).tag);
                    i7++;
                    c = 0;
                }
            }
            out.write(output, 0, output.length);
            rebuildMetadata.header_checksum = Woff2Common.computeULongSum(output, 0, output.length);
            return;
        }
        throw new AssertionError();
    }

    public static int computeWoff2FinalSize(byte[] data, int length) {
        Buffer file = new Buffer(data, 0, length);
        file.skip(16);
        return file.readInt();
    }

    public static void convertWoff2ToTtf(byte[] data, int length, Woff2Out out) {
        RebuildMetadata metadata = new RebuildMetadata();
        Woff2Header hdr = new Woff2Header();
        readWoff2Header(data, length, hdr);
        writeHeaders(data, length, metadata, hdr, out);
        float compression_ratio = ((float) hdr.uncompressed_size) / ((float) length);
        if (compression_ratio <= kMaxPlausibleCompressionRatio) {
            byte[] uncompressed_buf = new byte[hdr.uncompressed_size];
            woff2Uncompress(uncompressed_buf, 0, hdr.uncompressed_size, data, (int) hdr.compressed_offset, hdr.compressed_length);
            for (int i = 0; i < metadata.font_infos.length; i++) {
                reconstructFont(uncompressed_buf, 0, hdr.uncompressed_size, metadata, hdr, i, out);
            }
            return;
        }
        throw new FontCompressionException(MessageFormatUtil.format("Implausible compression ratio {0}", Float.valueOf(compression_ratio)));
    }
}
