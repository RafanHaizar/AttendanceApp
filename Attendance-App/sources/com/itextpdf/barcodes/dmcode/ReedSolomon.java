package com.itextpdf.barcodes.dmcode;

import androidx.recyclerview.widget.ItemTouchHelper;
import com.itextpdf.kernel.pdf.canvas.wmf.MetaDo;
import com.itextpdf.kernel.xmp.XMPError;
import com.itextpdf.p026io.codec.TIFFConstants;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.bouncycastle.math.Primes;

public final class ReedSolomon {
    private static final int[] alog = {1, 2, 4, 8, 16, 32, 64, 128, 45, 90, 180, 69, CipherSuite.TLS_PSK_WITH_RC4_128_SHA, 57, 114, 228, 229, 231, 227, 235, 251, 219, CipherSuite.TLS_DH_anon_WITH_SEED_CBC_SHA, 27, 54, 108, 216, CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384, 23, 46, 92, CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA256, 93, CipherSuite.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256, 89, CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA256, 73, CipherSuite.TLS_RSA_PSK_WITH_RC4_128_SHA, 9, 18, 36, 72, CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA, 13, 26, 52, 104, 208, CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA, 55, 110, 220, CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA, 7, 14, 28, 56, 112, 224, 237, MetaDo.META_CREATEPALETTE, CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA256, CipherSuite.TLS_DHE_PSK_WITH_AES_256_GCM_SHA384, 123, 246, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256, CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA384, 115, 230, 225, 239, 243, XMPError.BADXMP, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA256, 91, CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA256, 65, 130, 41, 82, CipherSuite.TLS_DH_DSS_WITH_AES_128_GCM_SHA256, 101, XMPError.BADRDF, CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA384, 95, CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA256, 81, CipherSuite.TLS_DHE_DSS_WITH_AES_128_GCM_SHA256, 105, 210, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA, 63, 126, 252, 213, CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA, 35, 70, CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA, 53, 106, 212, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA, 39, 78, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, 21, 42, 84, 168, 125, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 217, CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384, 19, 38, 76, CipherSuite.TLS_DH_RSA_WITH_SEED_CBC_SHA, 29, 58, 116, 232, 253, 215, 131, 43, 86, CipherSuite.TLS_RSA_PSK_WITH_AES_128_GCM_SHA256, 117, 234, 249, 223, CipherSuite.TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA, 11, 22, 44, 88, CipherSuite.TLS_PSK_WITH_NULL_SHA256, 77, CipherSuite.TLS_DHE_RSA_WITH_SEED_CBC_SHA, 25, 50, 100, 200, CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256, 87, CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA256, 113, 226, 233, 255, Primes.SMALL_FACTOR_LIMIT, CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA, 59, 118, 236, 245, 199, CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384, 107, 214, 129, 47, 94, 188, 85, CipherSuite.TLS_DHE_PSK_WITH_AES_128_GCM_SHA256, 121, 242, XMPError.BADXML, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256, 83, CipherSuite.TLS_DH_anon_WITH_AES_128_GCM_SHA256, 97, CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA256, CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384, 127, TIFFConstants.TIFFTAG_SUBFILETYPE, 209, CipherSuite.TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA, 51, 102, XMPError.BADSTREAM, CipherSuite.TLS_DHE_PSK_WITH_NULL_SHA384, 71, CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA, 49, 98, CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256, CipherSuite.TLS_DH_DSS_WITH_AES_256_GCM_SHA384, 103, 206, CipherSuite.TLS_PSK_WITH_NULL_SHA384, 79, CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, 17, 34, 68, 136, 61, 122, 244, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256, CipherSuite.TLS_DH_anon_WITH_AES_256_GCM_SHA384, 99, 198, CipherSuite.TLS_DH_RSA_WITH_AES_256_GCM_SHA384, 111, 222, CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA, 15, 30, 60, 120, 240, 205, CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA384, 67, CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA, 33, 66, CipherSuite.TLS_RSA_WITH_CAMELLIA_256_CBC_SHA, 37, 74, CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA, 5, 10, 20, 40, 80, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, 109, 218, CipherSuite.TLS_DHE_DSS_WITH_SEED_CBC_SHA, 31, 62, 124, 248, 221, CipherSuite.TLS_DH_DSS_WITH_SEED_CBC_SHA, 3, 6, 12, 24, 48, 96, 192, CipherSuite.TLS_RSA_PSK_WITH_AES_256_GCM_SHA384, 119, 238, 241, 207, CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA384, 75, CipherSuite.TLS_RSA_WITH_SEED_CBC_SHA, 1};
    private static final int[] log = {0, 255, 1, 240, 2, 225, 241, 53, 3, 38, 226, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA, 242, 43, 54, 210, 4, CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA256, 39, 114, 227, 106, CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA, 28, 243, CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA, 44, 23, 55, 118, Primes.SMALL_FACTOR_LIMIT, 234, 5, 219, CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256, 96, 40, 222, 115, 103, 228, 78, 107, 125, CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA, 8, 29, CipherSuite.TLS_DHE_DSS_WITH_AES_128_GCM_SHA256, 244, CipherSuite.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256, CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA, 180, 45, 99, 24, 49, 56, 13, 119, CipherSuite.TLS_DHE_DSS_WITH_SEED_CBC_SHA, 212, 199, 235, 91, 6, 76, 220, 217, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256, 11, 97, CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA256, 41, 36, 223, 253, 116, CipherSuite.TLS_PSK_WITH_RC4_128_SHA, 104, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256, 229, 86, 79, CipherSuite.TLS_DHE_PSK_WITH_AES_256_GCM_SHA384, 108, CipherSuite.TLS_DH_DSS_WITH_AES_256_GCM_SHA384, 126, CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA, 136, 34, 9, 74, 30, 32, CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384, 84, 245, CipherSuite.TLS_RSA_PSK_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA256, XMPError.BADSTREAM, CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA, 81, CipherSuite.TLS_DHE_PSK_WITH_NULL_SHA384, CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA256, 46, 88, 100, CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384, 25, 231, 50, 207, 57, CipherSuite.TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA, 14, 67, 120, 128, CipherSuite.TLS_DHE_RSA_WITH_SEED_CBC_SHA, 248, 213, CipherSuite.TLS_DH_anon_WITH_AES_256_GCM_SHA384, 200, 63, 236, 110, 92, CipherSuite.TLS_PSK_WITH_NULL_SHA256, 7, CipherSuite.TLS_DH_RSA_WITH_AES_256_GCM_SHA384, 77, 124, 221, 102, 218, 95, 198, 90, 12, CipherSuite.TLS_DH_RSA_WITH_SEED_CBC_SHA, 98, 48, CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA384, CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA384, 42, 209, 37, CipherSuite.TLS_RSA_WITH_CAMELLIA_256_CBC_SHA, 224, 52, TIFFConstants.TIFFTAG_SUBFILETYPE, 239, 117, 233, CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA, 22, 105, 27, CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA256, 113, 230, 206, 87, CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, 80, CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256, CipherSuite.TLS_RSA_PSK_WITH_AES_128_GCM_SHA256, XMPError.BADXMP, 109, CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA384, CipherSuite.TLS_DH_anon_WITH_AES_128_GCM_SHA256, 62, 127, MetaDo.META_CREATEPALETTE, CipherSuite.TLS_RSA_PSK_WITH_RC4_128_SHA, 66, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA, 192, 35, 252, 10, CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA384, 75, 216, 31, 83, 33, 73, CipherSuite.TLS_DH_DSS_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA, 85, CipherSuite.TLS_DHE_PSK_WITH_AES_128_GCM_SHA256, 246, 65, CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA256, 61, 188, XMPError.BADRDF, 205, CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA, CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384, 82, 72, CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA256, 215, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256, 251, 47, CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA256, 89, CipherSuite.TLS_DH_DSS_WITH_SEED_CBC_SHA, 101, 94, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, 123, 26, 112, 232, 21, 51, 238, 208, 131, 58, 69, CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA, 18, 15, 16, 68, 17, 121, CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA, 129, 19, CipherSuite.TLS_DH_anon_WITH_SEED_CBC_SHA, 59, 249, 70, 214, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 168, 71, XMPError.BADXML, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, 64, 60, 237, 130, 111, 20, 93, 122, CipherSuite.TLS_PSK_WITH_NULL_SHA384, CipherSuite.TLS_RSA_WITH_SEED_CBC_SHA};
    private static final int[] poly10 = {28, 24, CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA384, CipherSuite.TLS_DH_anon_WITH_AES_128_GCM_SHA256, 223, 248, 116, 255, 110, 61};
    private static final int[] poly11 = {CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA384, CipherSuite.TLS_PSK_WITH_RC4_128_SHA, 205, 12, CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA256, 168, 39, 245, 60, 97, 120};
    private static final int[] poly12 = {41, CipherSuite.TLS_DHE_DSS_WITH_SEED_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, 91, 61, 42, CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA, 213, 97, CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA256, 100, 242};
    private static final int[] poly14 = {CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, 97, 192, 252, 95, 9, CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384, 119, CipherSuite.TLS_PSK_WITH_RC4_128_SHA, 45, 18, CipherSuite.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256, 83, CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA384};
    private static final int[] poly18 = {83, CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA256, 100, 39, 188, 75, 66, 61, 241, 213, 109, 129, 94, TIFFConstants.TIFFTAG_SUBFILETYPE, 225, 48, 90, 188};
    private static final int[] poly20 = {15, CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA256, 244, 9, 233, 71, 168, 2, 188, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DHE_DSS_WITH_SEED_CBC_SHA, CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA, 253, 79, 108, 82, 27, CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA256, CipherSuite.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256, CipherSuite.TLS_RSA_PSK_WITH_AES_128_GCM_SHA256};
    private static final int[] poly24 = {52, CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA256, 88, 205, 109, 39, CipherSuite.TLS_PSK_WITH_NULL_SHA256, 21, CipherSuite.TLS_DH_anon_WITH_SEED_CBC_SHA, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256, 251, 223, CipherSuite.TLS_DH_anon_WITH_SEED_CBC_SHA, 21, 5, CipherSuite.TLS_RSA_PSK_WITH_AES_128_GCM_SHA256, TIFFConstants.TIFFTAG_SUBFILETYPE, 124, 12, CipherSuite.TLS_DHE_PSK_WITH_NULL_SHA384, CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA256, 96, 50, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256};
    private static final int[] poly28 = {Primes.SMALL_FACTOR_LIMIT, 231, 43, 97, 71, 96, 103, CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA256, 37, CipherSuite.TLS_DH_DSS_WITH_SEED_CBC_SHA, CipherSuite.TLS_DHE_PSK_WITH_AES_128_GCM_SHA256, 53, 75, 34, 249, 121, 17, CipherSuite.TLS_PSK_WITH_RC4_128_SHA, 110, 213, CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA, 136, 120, CipherSuite.TLS_DH_DSS_WITH_SEED_CBC_SHA, 233, 168, 93, 255};
    private static final int[] poly36 = {245, 127, 242, 218, 130, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, CipherSuite.TLS_DHE_DSS_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DHE_PSK_WITH_NULL_SHA384, 102, 120, 84, CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA384, 220, 251, 80, CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA256, 229, 18, 2, 4, 68, 33, 101, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA, 95, 119, 115, 44, CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA384, CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA256, 59, 25, 225, 98, 81, 112};
    private static final int[] poly42 = {77, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA, 31, 19, 38, 22, CipherSuite.TLS_DHE_DSS_WITH_SEED_CBC_SHA, MetaDo.META_CREATEPALETTE, 105, 122, 2, 245, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA, 242, 8, CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA384, 95, 100, 9, CipherSuite.TLS_DH_anon_WITH_AES_256_GCM_SHA384, 105, 214, 111, 57, 121, 21, 1, 253, 57, 54, 101, 248, XMPError.BADRDF, 69, 50, CipherSuite.TLS_RSA_WITH_SEED_CBC_SHA, CipherSuite.TLS_PSK_WITH_NULL_SHA384, 226, 5, 9, 5};
    private static final int[] poly48 = {245, CipherSuite.TLS_RSA_WITH_CAMELLIA_256_CBC_SHA, CipherSuite.TLS_RSA_PSK_WITH_AES_128_GCM_SHA256, 223, 96, 32, 117, 22, 238, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA, 238, 231, 205, 188, 237, 87, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256, 106, 16, CipherSuite.TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA, 118, 23, 37, 90, CipherSuite.TLS_DHE_PSK_WITH_AES_128_GCM_SHA256, 205, 131, 88, 120, 100, 66, CipherSuite.TLS_PSK_WITH_RC4_128_SHA, CipherSuite.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256, 240, 82, 44, CipherSuite.TLS_PSK_WITH_NULL_SHA256, 87, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA256, CipherSuite.TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA384, 69, 213, 92, 253, 225, 19};
    private static final int[] poly5 = {228, 48, 15, 111, 62};
    private static final int[] poly56 = {CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA384, 9, 223, 238, 12, 17, 220, 208, 100, 29, CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA384, CipherSuite.TLS_DHE_PSK_WITH_AES_128_GCM_SHA256, 230, 192, 215, 235, CipherSuite.TLS_RSA_WITH_SEED_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384, 36, 223, 38, 200, CipherSuite.TLS_RSA_WITH_CAMELLIA_256_CBC_SHA, 54, 228, CipherSuite.TLS_RSA_PSK_WITH_RC4_128_SHA, 218, 234, 117, XMPError.BADXMP, 29, 232, CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA, 238, 22, CipherSuite.TLS_RSA_WITH_SEED_CBC_SHA, XMPError.BADXML, 117, 62, 207, CipherSuite.TLS_DH_DSS_WITH_AES_128_GCM_SHA256, 13, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA, 245, 127, 67, MetaDo.META_CREATEPALETTE, 28, CipherSuite.TLS_DH_anon_WITH_SEED_CBC_SHA, 43, XMPError.BADXMP, 107, 233, 53, CipherSuite.TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA, 46};
    private static final int[] poly62 = {242, 93, CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384, 50, CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA, 210, 39, 118, XMPError.BADRDF, 188, XMPError.BADXML, CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256, CipherSuite.TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA, 108, CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256, 37, CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA384, 112, CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA, 230, 245, 63, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256, CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA256, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 106, CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA384, 221, CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA384, 64, 114, 71, CipherSuite.TLS_DH_RSA_WITH_AES_256_GCM_SHA384, 44, CipherSuite.TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA, 6, 27, 218, 51, 63, 87, 10, 40, 130, 188, 17, CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384, 31, CipherSuite.TLS_PSK_WITH_NULL_SHA256, CipherSuite.TLS_DHE_PSK_WITH_AES_128_GCM_SHA256, 4, 107, 232, 7, 94, CipherSuite.TLS_DH_anon_WITH_AES_128_GCM_SHA256, 224, 124, 86, 47, 11, XMPError.BADSTREAM};
    private static final int[] poly68 = {220, 228, CipherSuite.TLS_RSA_PSK_WITH_AES_256_GCM_SHA384, 89, 251, CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384, 56, 89, 33, CipherSuite.TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA, 244, CipherSuite.TLS_DHE_RSA_WITH_SEED_CBC_SHA, 36, 73, 127, 213, 136, 248, 180, 234, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256, CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_PSK_WITH_NULL_SHA384, 68, 122, 93, 213, 15, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, 227, 236, 66, CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA, CipherSuite.TLS_DHE_DSS_WITH_SEED_CBC_SHA, CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA384, XMPError.BADRDF, CipherSuite.TLS_DH_anon_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA384, 25, 220, 232, 96, 210, 231, 136, 223, 239, CipherSuite.TLS_DHE_PSK_WITH_NULL_SHA384, 241, 59, 52, CipherSuite.TLS_RSA_PSK_WITH_AES_128_GCM_SHA256, 25, 49, 232, Primes.SMALL_FACTOR_LIMIT, CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256, 64, 54, 108, CipherSuite.TLS_DHE_DSS_WITH_SEED_CBC_SHA, CipherSuite.TLS_RSA_WITH_CAMELLIA_256_CBC_SHA, 63, 96, 103, 82, CipherSuite.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256};
    private static final int[] poly7 = {23, 68, CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA, CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA, 240, 92, TIFFConstants.TIFFTAG_SUBFILETYPE};

    private static int[] getPoly(int nc) {
        switch (nc) {
            case 5:
                return poly5;
            case 7:
                return poly7;
            case 10:
                return poly10;
            case 11:
                return poly11;
            case 12:
                return poly12;
            case 14:
                return poly14;
            case 18:
                return poly18;
            case 20:
                return poly20;
            case 24:
                return poly24;
            case 28:
                return poly28;
            case 36:
                return poly36;
            case 42:
                return poly42;
            case 48:
                return poly48;
            case 56:
                return poly56;
            case 62:
                return poly62;
            case 68:
                return poly68;
            default:
                return null;
        }
    }

    private static void reedSolomonBlock(byte[] wd, int nd, byte[] ncout, int nc, int[] c) {
        byte b;
        for (int i = 0; i <= nc; i++) {
            ncout[i] = 0;
        }
        for (int i2 = 0; i2 < nd; i2++) {
            int k = (ncout[0] ^ wd[i2]) & 255;
            for (int j = 0; j < nc; j++) {
                byte b2 = ncout[j + 1];
                if (k == 0) {
                    b = 0;
                } else {
                    int[] iArr = alog;
                    int[] iArr2 = log;
                    b = (byte) iArr[(iArr2[k] + iArr2[c[(nc - j) - 1]]) % 255];
                }
                ncout[j] = (byte) (b2 ^ b);
            }
        }
    }

    public static void generateECC(byte[] wd, int nd, int datablock, int nc) {
        int blocks = (nd + 2) / datablock;
        byte[] buf = new byte[256];
        byte[] ecc = new byte[256];
        int[] c = getPoly(nc);
        for (int b = 0; b < blocks; b++) {
            int p = 0;
            int n = b;
            while (n < nd) {
                buf[p] = wd[n];
                n += blocks;
                p++;
            }
            reedSolomonBlock(buf, p, ecc, nc, c);
            int p2 = 0;
            int n2 = b;
            while (n2 < nc * blocks) {
                wd[nd + n2] = ecc[p2];
                n2 += blocks;
                p2++;
            }
        }
    }
}
