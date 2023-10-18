package p004pl.droidsonroids.gif;

import androidx.core.view.PointerIconCompat;
import java.util.Locale;

/* renamed from: pl.droidsonroids.gif.GifError */
public enum GifError {
    NO_ERROR(0, "No error"),
    OPEN_FAILED(101, "Failed to open given input"),
    READ_FAILED(102, "Failed to read from given input"),
    NOT_GIF_FILE(103, "Data is not in GIF format"),
    NO_SCRN_DSCR(104, "No screen descriptor detected"),
    NO_IMAG_DSCR(105, "No image descriptor detected"),
    NO_COLOR_MAP(106, "Neither global nor local color map found"),
    WRONG_RECORD(107, "Wrong record type detected"),
    DATA_TOO_BIG(108, "Number of pixels bigger than width * height"),
    NOT_ENOUGH_MEM(109, "Failed to allocate required memory"),
    CLOSE_FAILED(110, "Failed to close given input"),
    NOT_READABLE(111, "Given file was not opened for read"),
    IMAGE_DEFECT(112, "Image is defective, decoding aborted"),
    EOF_TOO_SOON(113, "Image EOF detected before image complete"),
    NO_FRAMES(1000, "No frames found, at least one frame required"),
    INVALID_SCR_DIMS(PointerIconCompat.TYPE_CONTEXT_MENU, "Invalid screen size, dimensions must be positive"),
    INVALID_IMG_DIMS(PointerIconCompat.TYPE_HAND, "Invalid image size, dimensions must be positive"),
    IMG_NOT_CONFINED(PointerIconCompat.TYPE_HELP, "Image size exceeds screen size"),
    REWIND_FAILED(PointerIconCompat.TYPE_WAIT, "Input source rewind failed, animation stopped"),
    INVALID_BYTE_BUFFER(1005, "Invalid and/or indirect byte buffer specified"),
    UNKNOWN(-1, "Unknown error");
    
    public final String description;
    int errorCode;

    private GifError(int code, String description2) {
        this.errorCode = code;
        this.description = description2;
    }

    static GifError fromCode(int code) {
        for (GifError err : values()) {
            if (err.errorCode == code) {
                return err;
            }
        }
        GifError unk = UNKNOWN;
        unk.errorCode = code;
        return unk;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    /* access modifiers changed from: package-private */
    public String getFormattedDescription() {
        return String.format(Locale.ENGLISH, "GifError %d: %s", new Object[]{Integer.valueOf(this.errorCode), this.description});
    }
}
