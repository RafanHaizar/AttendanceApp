package androidx.core.hardware.fingerprint;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Handler;
import androidx.core.p001os.CancellationSignal;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.Mac;

@Deprecated
public class FingerprintManagerCompat {
    private final Context mContext;

    public static FingerprintManagerCompat from(Context context) {
        return new FingerprintManagerCompat(context);
    }

    private FingerprintManagerCompat(Context context) {
        this.mContext = context;
    }

    public boolean hasEnrolledFingerprints() {
        FingerprintManager fp;
        if (Build.VERSION.SDK_INT < 23 || (fp = getFingerprintManagerOrNull(this.mContext)) == null || !Api23Impl.hasEnrolledFingerprints(fp)) {
            return false;
        }
        return true;
    }

    public boolean isHardwareDetected() {
        FingerprintManager fp;
        if (Build.VERSION.SDK_INT < 23 || (fp = getFingerprintManagerOrNull(this.mContext)) == null || !Api23Impl.isHardwareDetected(fp)) {
            return false;
        }
        return true;
    }

    public void authenticate(CryptoObject crypto, int flags, CancellationSignal cancel, AuthenticationCallback callback, Handler handler) {
        FingerprintManager fp;
        android.os.CancellationSignal cancellationSignal;
        if (Build.VERSION.SDK_INT >= 23 && (fp = getFingerprintManagerOrNull(this.mContext)) != null) {
            if (cancel != null) {
                cancellationSignal = (android.os.CancellationSignal) cancel.getCancellationSignalObject();
            } else {
                cancellationSignal = null;
            }
            Api23Impl.authenticate(fp, wrapCryptoObject(crypto), cancellationSignal, flags, wrapCallback(callback), handler);
        }
    }

    private static FingerprintManager getFingerprintManagerOrNull(Context context) {
        return Api23Impl.getFingerprintManagerOrNull(context);
    }

    private static FingerprintManager.CryptoObject wrapCryptoObject(CryptoObject cryptoObject) {
        return Api23Impl.wrapCryptoObject(cryptoObject);
    }

    static CryptoObject unwrapCryptoObject(FingerprintManager.CryptoObject cryptoObject) {
        return Api23Impl.unwrapCryptoObject(cryptoObject);
    }

    private static FingerprintManager.AuthenticationCallback wrapCallback(final AuthenticationCallback callback) {
        return new FingerprintManager.AuthenticationCallback() {
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                AuthenticationCallback.this.onAuthenticationError(errMsgId, errString);
            }

            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                AuthenticationCallback.this.onAuthenticationHelp(helpMsgId, helpString);
            }

            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                AuthenticationCallback.this.onAuthenticationSucceeded(new AuthenticationResult(FingerprintManagerCompat.unwrapCryptoObject(Api23Impl.getCryptoObject(result))));
            }

            public void onAuthenticationFailed() {
                AuthenticationCallback.this.onAuthenticationFailed();
            }
        };
    }

    public static class CryptoObject {
        private final Cipher mCipher;
        private final Mac mMac;
        private final Signature mSignature;

        public CryptoObject(Signature signature) {
            this.mSignature = signature;
            this.mCipher = null;
            this.mMac = null;
        }

        public CryptoObject(Cipher cipher) {
            this.mCipher = cipher;
            this.mSignature = null;
            this.mMac = null;
        }

        public CryptoObject(Mac mac) {
            this.mMac = mac;
            this.mCipher = null;
            this.mSignature = null;
        }

        public Signature getSignature() {
            return this.mSignature;
        }

        public Cipher getCipher() {
            return this.mCipher;
        }

        public Mac getMac() {
            return this.mMac;
        }
    }

    public static final class AuthenticationResult {
        private final CryptoObject mCryptoObject;

        public AuthenticationResult(CryptoObject crypto) {
            this.mCryptoObject = crypto;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }
    }

    public static abstract class AuthenticationCallback {
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
        }

        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        }

        public void onAuthenticationSucceeded(AuthenticationResult result) {
        }

        public void onAuthenticationFailed() {
        }
    }

    static class Api23Impl {
        private Api23Impl() {
        }

        static boolean hasEnrolledFingerprints(Object fingerprintManager) {
            return ((FingerprintManager) fingerprintManager).hasEnrolledFingerprints();
        }

        static boolean isHardwareDetected(Object fingerprintManager) {
            return ((FingerprintManager) fingerprintManager).isHardwareDetected();
        }

        static void authenticate(Object fingerprintManager, Object crypto, android.os.CancellationSignal cancel, int flags, Object callback, Handler handler) {
            ((FingerprintManager) fingerprintManager).authenticate((FingerprintManager.CryptoObject) crypto, cancel, flags, (FingerprintManager.AuthenticationCallback) callback, handler);
        }

        static FingerprintManager.CryptoObject getCryptoObject(Object authenticationResult) {
            return ((FingerprintManager.AuthenticationResult) authenticationResult).getCryptoObject();
        }

        public static FingerprintManager getFingerprintManagerOrNull(Context context) {
            if (Build.VERSION.SDK_INT == 23) {
                return (FingerprintManager) context.getSystemService(FingerprintManager.class);
            }
            if (Build.VERSION.SDK_INT <= 23 || !context.getPackageManager().hasSystemFeature("android.hardware.fingerprint")) {
                return null;
            }
            return (FingerprintManager) context.getSystemService(FingerprintManager.class);
        }

        public static FingerprintManager.CryptoObject wrapCryptoObject(CryptoObject cryptoObject) {
            if (cryptoObject == null) {
                return null;
            }
            if (cryptoObject.getCipher() != null) {
                return new FingerprintManager.CryptoObject(cryptoObject.getCipher());
            }
            if (cryptoObject.getSignature() != null) {
                return new FingerprintManager.CryptoObject(cryptoObject.getSignature());
            }
            if (cryptoObject.getMac() != null) {
                return new FingerprintManager.CryptoObject(cryptoObject.getMac());
            }
            return null;
        }

        public static CryptoObject unwrapCryptoObject(Object cryptoObjectObj) {
            FingerprintManager.CryptoObject cryptoObject = (FingerprintManager.CryptoObject) cryptoObjectObj;
            if (cryptoObject == null) {
                return null;
            }
            if (cryptoObject.getCipher() != null) {
                return new CryptoObject(cryptoObject.getCipher());
            }
            if (cryptoObject.getSignature() != null) {
                return new CryptoObject(cryptoObject.getSignature());
            }
            if (cryptoObject.getMac() != null) {
                return new CryptoObject(cryptoObject.getMac());
            }
            return null;
        }
    }
}
