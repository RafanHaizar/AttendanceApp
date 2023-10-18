package com.itextpdf.p026io.font.woff2;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.jvm.internal.ByteCompanionObject;

/* renamed from: com.itextpdf.io.font.woff2.VariableLength */
class VariableLength {
    VariableLength() {
    }

    public static int read255UShort(Buffer buf) {
        byte code = buf.readByte();
        if (JavaUnsignedUtil.asU8(code) == 253) {
            return JavaUnsignedUtil.asU16(buf.readShort());
        }
        if (JavaUnsignedUtil.asU8(code) == 255) {
            return JavaUnsignedUtil.asU8(buf.readByte()) + 253;
        }
        if (JavaUnsignedUtil.asU8(code) == 254) {
            return JavaUnsignedUtil.asU8(buf.readByte()) + TypedValues.PositionType.TYPE_PERCENT_X;
        }
        return JavaUnsignedUtil.asU8(code);
    }

    public static int readBase128(Buffer buf) {
        int result = 0;
        int i = 0;
        while (i < 5) {
            byte code = buf.readByte();
            if (i == 0 && JavaUnsignedUtil.asU8(code) == 128) {
                throw new FontCompressionException(FontCompressionException.READ_BASE_128_FAILED);
            } else if ((-33554432 & result) == 0) {
                result = (result << 7) | (code & 127);
                if ((code & ByteCompanionObject.MIN_VALUE) == 0) {
                    return result;
                }
                i++;
            } else {
                throw new FontCompressionException(FontCompressionException.READ_BASE_128_FAILED);
            }
        }
        throw new FontCompressionException(FontCompressionException.READ_BASE_128_FAILED);
    }
}
