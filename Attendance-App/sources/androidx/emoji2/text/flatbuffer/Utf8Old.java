package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.StandardCharsets;

public class Utf8Old extends Utf8 {
    private static final ThreadLocal<Cache> CACHE = ThreadLocal.withInitial(new Utf8Old$$ExternalSyntheticLambda0());

    private static class Cache {
        final CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
        final CharsetEncoder encoder = StandardCharsets.UTF_8.newEncoder();
        CharSequence lastInput = null;
        ByteBuffer lastOutput = null;

        Cache() {
        }
    }

    static /* synthetic */ Cache lambda$static$0() {
        return new Cache();
    }

    public int encodedLength(CharSequence in) {
        CharBuffer wrap;
        Cache cache = CACHE.get();
        int estimated = (int) (((float) in.length()) * cache.encoder.maxBytesPerChar());
        if (cache.lastOutput == null || cache.lastOutput.capacity() < estimated) {
            cache.lastOutput = ByteBuffer.allocate(Math.max(128, estimated));
        }
        cache.lastOutput.clear();
        cache.lastInput = in;
        if (in instanceof CharBuffer) {
            wrap = (CharBuffer) in;
        } else {
            wrap = CharBuffer.wrap(in);
        }
        CoderResult result = cache.encoder.encode(wrap, cache.lastOutput, true);
        if (result.isError()) {
            try {
                result.throwException();
            } catch (CharacterCodingException e) {
                throw new IllegalArgumentException("bad character encoding", e);
            }
        }
        cache.lastOutput.flip();
        return cache.lastOutput.remaining();
    }

    public void encodeUtf8(CharSequence in, ByteBuffer out) {
        Cache cache = CACHE.get();
        if (cache.lastInput != in) {
            encodedLength(in);
        }
        out.put(cache.lastOutput);
    }

    public String decodeUtf8(ByteBuffer buffer, int offset, int length) {
        CharsetDecoder decoder = CACHE.get().decoder;
        decoder.reset();
        ByteBuffer buffer2 = buffer.duplicate();
        buffer2.position(offset);
        buffer2.limit(offset + length);
        try {
            return decoder.decode(buffer2).toString();
        } catch (CharacterCodingException e) {
            throw new IllegalArgumentException("Bad encoding", e);
        }
    }
}
