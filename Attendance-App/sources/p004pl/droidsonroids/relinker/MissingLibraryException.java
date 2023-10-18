package p004pl.droidsonroids.relinker;

import java.util.Arrays;

/* renamed from: pl.droidsonroids.relinker.MissingLibraryException */
public class MissingLibraryException extends RuntimeException {
    public MissingLibraryException(String library, String[] wantedABIs, String[] supportedABIs) {
        super("Could not find '" + library + "'. Looked for: " + Arrays.toString(wantedABIs) + ", but only found: " + Arrays.toString(supportedABIs) + ".");
    }
}
