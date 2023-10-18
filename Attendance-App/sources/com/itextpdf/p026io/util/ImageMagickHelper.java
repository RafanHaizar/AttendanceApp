package com.itextpdf.p026io.util;

import com.itextpdf.p026io.IoExceptionMessage;
import java.io.IOException;

/* renamed from: com.itextpdf.io.util.ImageMagickHelper */
public class ImageMagickHelper {
    public static final String MAGICK_COMPARE_ENVIRONMENT_VARIABLE = "ITEXT_MAGICK_COMPARE_EXEC";
    @Deprecated
    static final String MAGICK_COMPARE_ENVIRONMENT_VARIABLE_LEGACY = "compareExec";
    static final String MAGICK_COMPARE_KEYWORD = "ImageMagick Studio LLC";
    private String compareExec;

    public ImageMagickHelper() {
        this((String) null);
    }

    public ImageMagickHelper(String newCompareExec) {
        this.compareExec = newCompareExec;
        if (newCompareExec == null) {
            String propertyOrEnvironmentVariable = SystemUtil.getPropertyOrEnvironmentVariable(MAGICK_COMPARE_ENVIRONMENT_VARIABLE);
            this.compareExec = propertyOrEnvironmentVariable;
            if (propertyOrEnvironmentVariable == null) {
                this.compareExec = SystemUtil.getPropertyOrEnvironmentVariable(MAGICK_COMPARE_ENVIRONMENT_VARIABLE_LEGACY);
            }
        }
        if (!CliCommandUtil.isVersionCommandExecutable(this.compareExec, MAGICK_COMPARE_KEYWORD)) {
            throw new IllegalArgumentException(IoExceptionMessage.COMPARE_COMMAND_SPECIFIED_INCORRECTLY);
        }
    }

    public String getCliExecutionCommand() {
        return this.compareExec;
    }

    public boolean runImageMagickImageCompare(String outImageFilePath, String cmpImageFilePath, String diffImageName) throws IOException, InterruptedException {
        return runImageMagickImageCompare(outImageFilePath, cmpImageFilePath, diffImageName, (String) null);
    }

    public boolean runImageMagickImageCompare(String outImageFilePath, String cmpImageFilePath, String diffImageName, String fuzzValue) throws IOException, InterruptedException {
        String fuzzValue2 = fuzzValue == null ? "" : " -metric AE -fuzz <fuzzValue>%".replace("<fuzzValue>", fuzzValue);
        StringBuilder currCompareParams = new StringBuilder();
        currCompareParams.append(fuzzValue2).append(" '").append(outImageFilePath).append("' '").append(cmpImageFilePath).append("' '").append(diffImageName).append("'");
        return SystemUtil.runProcessAndWait(this.compareExec, currCompareParams.toString());
    }
}
