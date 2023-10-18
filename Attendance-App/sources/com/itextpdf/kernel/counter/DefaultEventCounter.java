package com.itextpdf.kernel.counter;

import com.itextpdf.kernel.Version;
import com.itextpdf.kernel.counter.context.UnknownContext;
import com.itextpdf.kernel.counter.event.IEvent;
import com.itextpdf.kernel.counter.event.IMetaInfo;
import com.itextpdf.p026io.codec.Base64;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultEventCounter extends EventCounter {
    private static final int[] REPEAT = {10000, 5000, 1000};
    private static byte[] message_1 = Base64.decode("DQoNCllvdSBhcmUgdXNpbmcgaVRleHQgdW5kZXIgdGhlIEFHUEwuDQoNCklmIHRoaXMgaXMgeW91ciBpbnRlbnRpb24sIHlvdSBoYXZlIHB1Ymxpc2hlZCB5b3VyIG93biBzb3VyY2UgY29kZSBhcyBBR1BMIHNvZnR3YXJlIHRvby4NClBsZWFzZSBsZXQgdXMga25vdyB3aGVyZSB0byBmaW5kIHlvdXIgc291cmNlIGNvZGUgYnkgc2VuZGluZyBhIG1haWwgdG8gYWdwbEBpdGV4dHBkZi5jb20NCldlJ2QgYmUgaG9ub3JlZCB0byBhZGQgaXQgdG8gb3VyIGxpc3Qgb2YgQUdQTCBwcm9qZWN0cyBidWlsdCBvbiB0b3Agb2YgaVRleHQgb3IgaVRleHRTaGFycA0KYW5kIHdlJ2xsIGV4cGxhaW4gaG93IHRvIHJlbW92ZSB0aGlzIG1lc3NhZ2UgZnJvbSB5b3VyIGVycm9yIGxvZ3MuDQoNCklmIHRoaXMgd2Fzbid0IHlvdXIgaW50ZW50aW9uLCB5b3UgYXJlIHByb2JhYmx5IHVzaW5nIGlUZXh0IGluIGEgbm9uLWZyZWUgZW52aXJvbm1lbnQuDQpJbiB0aGlzIGNhc2UsIHBsZWFzZSBjb250YWN0IHVzIGJ5IGZpbGxpbmcgb3V0IHRoaXMgZm9ybTogaHR0cDovL2l0ZXh0cGRmLmNvbS9zYWxlcw0KSWYgeW91IGFyZSBhIGN1c3RvbWVyLCB3ZSdsbCBleHBsYWluIGhvdyB0byBpbnN0YWxsIHlvdXIgbGljZW5zZSBrZXkgdG8gYXZvaWQgdGhpcyBtZXNzYWdlLg0KSWYgeW91J3JlIG5vdCBhIGN1c3RvbWVyLCB3ZSdsbCBleHBsYWluIHRoZSBiZW5lZml0cyBvZiBiZWNvbWluZyBhIGN1c3RvbWVyLg0KDQo=");
    private static byte[] message_2 = Base64.decode("WW91ciBsaWNlbnNlIGhhcyBleHBpcmVkISBZb3UgYXJlIG5vdyB1c2luZyBpVGV4dCB1bmRlciB0aGUgQUdQTC4NCg0KSWYgdGhpcyBpcyB5b3VyIGludGVudGlvbiwgeW91IHNob3VsZCBoYXZlIHB1Ymxpc2hlZCB5b3VyIG93biBzb3VyY2UgY29kZSBhcyBBR1BMIHNvZnR3YXJlIHRvby4NClBsZWFzZSBsZXQgdXMga25vdyB3aGVyZSB0byBmaW5kIHlvdXIgc291cmNlIGNvZGUgYnkgc2VuZGluZyBhIG1haWwgdG8gYWdwbEBpdGV4dHBkZi5jb20NCldlJ2QgYmUgaG9ub3JlZCB0byBhZGQgaXQgdG8gb3VyIGxpc3Qgb2YgQUdQTCBwcm9qZWN0cyBidWlsdCBvbiB0b3Agb2YgaVRleHQgb3IgaVRleHRTaGFycA0KYW5kIHdlJ2xsIGV4cGxhaW4gaG93IHRvIHJlbW92ZSB0aGlzIG1lc3NhZ2UgZnJvbSB5b3VyIGVycm9yIGxvZ3MuDQoNCklmIHRoaXMgd2Fzbid0IHlvdXIgaW50ZW50aW9uLCBwbGVhc2UgY29udGFjdCB1cyBieSBmaWxsaW5nIG91dCB0aGlzIGZvcm06IGh0dHA6Ly9pdGV4dHBkZi5jb20vc2FsZXMgb3IgYnkgY29udGFjdGluZyBvdXIgc2FsZXMgZGVwYXJ0bWVudC4=");
    private final AtomicLong count = new AtomicLong();
    private int level = 0;
    private Logger logger;
    private int repeatLevel = 10000;

    public DefaultEventCounter() {
        super(UnknownContext.RESTRICTIVE);
    }

    /* access modifiers changed from: protected */
    public void onEvent(IEvent event, IMetaInfo metaInfo) {
        if (this.count.incrementAndGet() > ((long) this.repeatLevel)) {
            if (Version.isAGPLVersion() || Version.isExpired()) {
                String message = new String(message_1, StandardCharsets.ISO_8859_1);
                if (Version.isExpired()) {
                    message = new String(message_2, StandardCharsets.ISO_8859_1);
                }
                int i = this.level + 1;
                this.level = i;
                if (i == 1) {
                    this.repeatLevel = REPEAT[1];
                } else {
                    this.repeatLevel = REPEAT[2];
                }
                if (this.logger == null) {
                    this.logger = LoggerFactory.getLogger(getClass());
                }
                this.logger.info(message);
            }
            this.count.set(0);
        }
    }
}
