package com.itextpdf.kernel;

import com.itextpdf.p026io.util.MessageFormatUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LicenseVersionException extends RuntimeException {
    public static final String LICENSE_FILE_NOT_LOADED = "License file not loaded.";
    public static final String MAJOR_VERSION_IS_NOT_NUMERIC = "Major version is not numeric";
    public static final String MINOR_VERSION_IS_NOT_NUMERIC = "Minor version is not numeric";
    public static final String NO_I_TEXT7_LICENSE_IS_LOADED_BUT_AN_I_TEXT5_LICENSE_IS_LOADED = "No iText7 License is loaded but an iText5 license is loaded.";

    /* renamed from: THE_MAJOR_VERSION_OF_THE_LICENSE_0_IS_HIGHER_THAN_THE_MAJOR_VERSION_1_OF_THE_CORE_LIBRARY */
    public static final String f1234x66dec3ad = "The major version of the license ({0}) is higher than the major version ({1}) of the Core library.";

    /* renamed from: THE_MAJOR_VERSION_OF_THE_LICENSE_0_IS_LOWER_THAN_THE_MAJOR_VERSION_1_OF_THE_CORE_LIBRARY */
    public static final String f1235x9b479215 = "The major version of the license ({0}) is lower than the major version ({1}) of the Core library.";

    /* renamed from: THE_MINOR_VERSION_OF_THE_LICENSE_0_IS_HIGHER_THAN_THE_MINOR_VERSION_1_OF_THE_CORE_LIBRARY */
    public static final String f1236x25262ea5 = "The minor version of the license ({0}) is higher than the minor version ({1}) of the Core library.";

    /* renamed from: THE_MINOR_VERSION_OF_THE_LICENSE_0_IS_LOWER_THAN_THE_MINOR_VERSION_1_OF_THE_CORE_LIBRARY */
    public static final String f1237x72491995 = "The minor version of the license ({0}) is lower than the minor version ({1}) of the Core library.";
    public static final String UNKNOWN_EXCEPTION_WHEN_CHECKING_LICENSE_VERSION = "Unknown Exception when checking License version";
    public static final String VERSION_STRING_IS_EMPTY_AND_CANNOT_BE_PARSED = "Version string is empty and cannot be parsed.";
    private List<Object> messageParams;
    protected Object object;

    public LicenseVersionException(String message) {
        super(message);
    }

    public LicenseVersionException(Throwable cause) {
        this(UNKNOWN_EXCEPTION_WHEN_CHECKING_LICENSE_VERSION, cause);
    }

    public LicenseVersionException(String message, Object obj) {
        this(message);
        this.object = obj;
    }

    public LicenseVersionException(String message, Throwable cause) {
        super(message, cause);
    }

    public LicenseVersionException(String message, Throwable cause, Object obj) {
        this(message, cause);
        this.object = obj;
    }

    public String getMessage() {
        List<Object> list = this.messageParams;
        if (list == null || list.size() == 0) {
            return super.getMessage();
        }
        return MessageFormatUtil.format(super.getMessage(), getMessageParams());
    }

    public LicenseVersionException setMessageParams(Object... messageParams2) {
        ArrayList arrayList = new ArrayList();
        this.messageParams = arrayList;
        Collections.addAll(arrayList, messageParams2);
        return this;
    }

    /* access modifiers changed from: protected */
    public Object[] getMessageParams() {
        Object[] parameters = new Object[this.messageParams.size()];
        for (int i = 0; i < this.messageParams.size(); i++) {
            parameters[i] = this.messageParams.get(i);
        }
        return parameters;
    }
}
