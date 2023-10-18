package com.itextpdf.p026io;

import com.itextpdf.p026io.util.MessageFormatUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* renamed from: com.itextpdf.io.IOException */
public class IOException extends RuntimeException {
    public static final String AllFillBitsPrecedingEolCodeMustBe0 = "All fill bits preceding eol code must be 0.";
    public static final String BadEndiannessTag0x4949Or0x4d4d = "Bad endianness tag: 0x4949 or 0x4d4d.";
    public static final String BadMagicNumberShouldBe42 = "Bad magic number. Should be 42.";
    public static final String BitsPerComponentMustBe1_2_4or8 = "Bits per component must be 1, 2, 4 or 8.";
    public static final String BitsPerSample1IsNotSupported = "Bits per sample {0} is not supported.";
    public static final String BmpImageException = "Bmp image exception.";
    public static final String BytesCanBeAssignedToByteArrayOutputStreamOnly = "Bytes can be assigned to ByteArrayOutputStream only.";
    public static final String BytesCanBeResetInByteArrayOutputStreamOnly = "Bytes can be reset in ByteArrayOutputStream only.";
    public static final String CannotFind1Frame = "Cannot find frame number {0} (zero-based)";
    public static final String CannotGetTiffImageColor = "Cannot get TIFF image color.";
    public static final String CannotHandleBoxSizesHigherThan2_32 = "Cannot handle box sizes higher than 2^32.";
    public static final String CannotInflateTiffImage = "Cannot inflate TIFF image.";
    public static final String CannotReadTiffImage = "Cannot read TIFF image.";
    public static final String CannotWriteByte = "Cannot write byte.";
    public static final String CannotWriteBytes = "Cannot write bytes.";
    public static final String CannotWriteFloatNumber = "Cannot write float number.";
    public static final String CannotWriteIntNumber = "Cannot write int number.";
    public static final String CcittCompressionTypeMustBeCcittg4Ccittg3_1dOrCcittg3_2d = "CCITT compression type must be CCITTG4, CCITTG3_1D or CCITTG3_2D.";
    public static final String CharacterCodeException = "Character code exception.";
    public static final String Cmap1WasNotFound = "The CMap {0} was not found.";
    public static final String ColorDepthIsNotSupported = "The color depth {0} is not supported.";
    public static final String ColorSpaceIsNotSupported = "The color space {0} is not supported.";
    public static final String ComponentsMustBe1_3Or4 = "Components must be 1, 3 or 4.";
    public static final String Compression1IsNotSupported = "Compression {0} is not supported.";

    /* renamed from: CompressionJpegIsOnlySupportedWithASingleStripThisImageHas1Strips */
    public static final String f1187xe866cd90 = "Compression jpeg is only supported with a single strip. This image has {0} strips.";
    public static final String DirectoryNumberIsTooLarge = "Directory number is too large.";
    public static final String EolCodeWordEncounteredInBlackRun = "EOL code word encountered in Black run.";
    public static final String EolCodeWordEncounteredInWhiteRun = "EOL code word encountered in White run.";
    public static final String ErrorAtFilePointer1 = "Error at file pointer {0}.";
    public static final String ErrorReadingString = "Error reading string.";
    public static final String ErrorWithJpMarker = "Error with JP marker.";
    public static final String ExpectedFtypMarker = "Expected FTYP marker.";
    public static final String ExpectedIhdrMarker = "Expected IHDR marker.";
    public static final String ExpectedJp2hMarker = "Expected JP2H marker.";
    public static final String ExpectedJpMarker = "Expected JP marker.";
    public static final String ExpectedTrailingZeroBitsForByteAlignedLines = "Expected trailing zero bits for byte-aligned lines";
    public static final String ExtraSamplesAreNotSupported = "Extra samples are not supported.";
    public static final String FdfStartxrefNotFound = "FDF startxref not found.";
    public static final String FirstScanlineMustBe1dEncoded = "First scanline must be 1D encoded.";
    public static final String FontFile1NotFound = "Font file {0} not found.";
    public static final String GifImageException = "GIF image exception.";
    public static final String GifSignatureNotFound = "GIF signature not found.";
    public static final String GtNotExpected = "'>' not expected.";
    public static final String IccProfileContains0ComponentsWhileImageDataContains1Components = "ICC profile contains {0} components, while the image data contains {1} components.";
    public static final String IllegalValueForPredictorInTiffFile = "Illegal value for predictor in TIFF file.";
    public static final String ImageFormatCannotBeRecognized = "Image format cannot be recognized.";
    public static final String ImageIsNotMaskYouMustCallImageDataMakeMask = "Image is not a mask. You must call ImageData#makeMask().";
    public static final String ImageMaskCannotContainAnotherImageMask = "Image mask cannot contain another image mask.";
    public static final String IncompletePalette = "Incomplete palette.";
    public static final String InvalidBmpFileCompression = "Invalid BMP file compression.";
    public static final String InvalidCodeEncountered = "Invalid code encountered.";
    public static final String InvalidCodeEncounteredWhileDecoding2dGroup3CompressedData = "Invalid code encountered while decoding 2D group 3 compressed data.";
    public static final String InvalidCodeEncounteredWhileDecoding2dGroup4CompressedData = "Invalid code encountered while decoding 2D group 4 compressed data.";
    public static final String InvalidIccProfile = "Invalid ICC profile.";
    public static final String InvalidJpeg2000File = "Invalid JPEG2000 file.";
    public static final String InvalidMagicValueForBmpFileMustBeBM = "Invalid magic value for bmp file. Must be 'BM'";
    public static final String InvalidTtcFile = "{0} is not a valid TTC file.";
    public static final String InvalidWoff2File = "Invalid WOFF2 font file.";
    public static final String InvalidWoffFile = "Invalid WOFF font file.";
    public static final String IoException = "I/O exception.";
    public static final String Jbig2ImageException = "JBIG2 image exception.";
    public static final String Jpeg2000ImageException = "JPEG2000 image exception.";
    public static final String JpegImageException = "JPEG image exception.";
    public static final String MissingTagsForOjpegCompression = "Missing tag(s) for OJPEG compression";
    public static final String NValueIsNotSupported = "N value {1} is not supported.";
    public static final String NotAtTrueTypeFile = "{0} is not a true type file";
    public static final String PageNumberMustBeGtEq1 = "Page number must be >= 1.";
    public static final String PdfHeaderNotFound = "PDF header not found.";
    public static final String PdfStartxrefNotFound = "PDF startxref not found.";
    public static final String Photometric1IsNotSupported = "Photometric {0} is not supported.";
    public static final String PlanarImagesAreNotSupported = "Planar images are not supported.";
    public static final String PngImageException = "PNG image exception.";
    public static final String PrematureEofWhileReadingJpeg = "Premature EOF while reading JPEG.";
    public static final String ScanlineMustBeginWithEolCodeWord = "Scanline must begin with EOL code word.";
    public static final String TableDoesNotExist = "Table {0} does not exist.";
    public static final String TableDoesNotExistsIn = "Table {0} does not exist in {1}";
    public static final String ThisImageCanNotBeAnImageMask = "This image can not be an image mask.";
    public static final String Tiff50StyleLzwCodesAreNotSupported = "TIFF 5.0-style LZW codes are not supported.";
    public static final String TiffFillOrderTagMustBeEither1Or2 = "TIFF_FILL_ORDER tag must be either 1 or 2.";
    public static final String TiffImageException = "TIFF image exception.";
    public static final String TilesAreNotSupported = "Tiles are not supported.";
    public static final String TransparencyLengthMustBeEqualTo2WithCcittImages = "Transparency length must be equal to 2 with CCITT images";
    public static final String TtcIndexDoesNotExistInThisTtcFile = "TTC index doesn't exist in this TTC file.";
    public static final String TypeOfFont1IsNotRecognized = "Type of font {0} is not recognized.";
    public static final String TypeOfFontIsNotRecognized = "Type of font is not recognized.";
    public static final String UnexpectedCloseBracket = "Unexpected close bracket.";
    public static final String UnexpectedGtGt = "Unexpected '>>'.";
    public static final String UnknownCompressionType1 = "Unknown compression type {0}.";
    public static final String UnknownIOException = "Unknown I/O exception.";
    public static final String UnknownPngFilter = "Unknown PNG filter.";
    public static final String UnsupportedBoxSizeEqEq0 = "Unsupported box size == 0.";
    public static final String UnsupportedEncodingException = "Unsupported encoding exception.";
    public static final String _1BitSamplesAreNotSupportedForHorizontalDifferencingPredictor = "{0} bit samples are not supported for horizontal differencing predictor.";
    public static final String _1CorruptedJfifMarker = "{0} corrupted jfif marker.";
    public static final String _1IsNotAValidJpegFile = "{0} is not a valid jpeg file.";
    public static final String _1IsNotAnAfmOrPfmFontFile = "{0} is not an afm or pfm font file.";
    public static final String _1MustHave8BitsPerComponent = "{0} must have 8 bits per component.";
    public static final String _1NotFoundAsFileOrResource = "{0} not found as file or resource.";
    public static final String _1UnsupportedJpegMarker2 = "{0} unsupported jpeg marker {1}.";
    private List<Object> messageParams;
    protected Object obj;

    public IOException(String message) {
        super(message);
    }

    public IOException(Throwable cause) {
        this(UnknownIOException, cause);
    }

    public IOException(String message, Object obj2) {
        this(message);
        this.obj = obj2;
    }

    public IOException(String message, Throwable cause) {
        super(message, cause);
    }

    public IOException(String message, Throwable cause, Object obj2) {
        this(message, cause);
        this.obj = obj2;
    }

    public String getMessage() {
        List<Object> list = this.messageParams;
        if (list == null || list.size() == 0) {
            return super.getMessage();
        }
        return MessageFormatUtil.format(super.getMessage(), getMessageParams());
    }

    /* access modifiers changed from: protected */
    public Object[] getMessageParams() {
        Object[] parameters = new Object[this.messageParams.size()];
        for (int i = 0; i < this.messageParams.size(); i++) {
            parameters[i] = this.messageParams.get(i);
        }
        return parameters;
    }

    public IOException setMessageParams(Object... messageParams2) {
        ArrayList arrayList = new ArrayList();
        this.messageParams = arrayList;
        Collections.addAll(arrayList, messageParams2);
        return this;
    }
}
