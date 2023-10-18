package com.itextpdf.p026io.util;

import com.itextpdf.p026io.IoExceptionMessage;
import java.io.IOException;

/* renamed from: com.itextpdf.io.util.GhostscriptHelper */
public class GhostscriptHelper {
    public static final String GHOSTSCRIPT_ENVIRONMENT_VARIABLE = "ITEXT_GS_EXEC";
    @Deprecated
    static final String GHOSTSCRIPT_ENVIRONMENT_VARIABLE_LEGACY = "gsExec";
    static final String GHOSTSCRIPT_KEYWORD = "GPL Ghostscript";
    private static final String GHOSTSCRIPT_PARAMS = " -dSAFER -dNOPAUSE -dBATCH -sDEVICE=png16m -r150 {0} -sOutputFile=\"{1}\" \"{2}\"";
    private String gsExec;

    public GhostscriptHelper() {
        this((String) null);
    }

    public GhostscriptHelper(String newGsExec) {
        this.gsExec = newGsExec;
        if (newGsExec == null) {
            String propertyOrEnvironmentVariable = SystemUtil.getPropertyOrEnvironmentVariable(GHOSTSCRIPT_ENVIRONMENT_VARIABLE);
            this.gsExec = propertyOrEnvironmentVariable;
            if (propertyOrEnvironmentVariable == null) {
                this.gsExec = SystemUtil.getPropertyOrEnvironmentVariable(GHOSTSCRIPT_ENVIRONMENT_VARIABLE_LEGACY);
            }
        }
        if (!CliCommandUtil.isVersionCommandExecutable(this.gsExec, GHOSTSCRIPT_KEYWORD)) {
            throw new IllegalArgumentException(IoExceptionMessage.GS_ENVIRONMENT_VARIABLE_IS_NOT_SPECIFIED);
        }
    }

    public String getCliExecutionCommand() {
        return this.gsExec;
    }

    public void runGhostScriptImageGeneration(String pdf, String outDir, String image) throws IOException, InterruptedException {
        runGhostScriptImageGeneration(pdf, outDir, image, (String) null);
    }

    public void runGhostScriptImageGeneration(String pdf, String outDir, String image, String pageList) throws IOException, InterruptedException {
        if (FileUtil.directoryExists(outDir)) {
            if (!SystemUtil.runProcessAndWait(this.gsExec, MessageFormatUtil.format(GHOSTSCRIPT_PARAMS, pageList == null ? "" : "-sPageList=<pagelist>".replace("<pagelist>", pageList), outDir + image, pdf))) {
                throw new GhostscriptExecutionException(IoExceptionMessage.GHOSTSCRIPT_FAILED.replace("<filename>", pdf));
            }
            return;
        }
        throw new IllegalArgumentException(IoExceptionMessage.CANNOT_OPEN_OUTPUT_DIRECTORY.replace("<filename>", pdf));
    }

    /* renamed from: com.itextpdf.io.util.GhostscriptHelper$GhostscriptExecutionException */
    public static class GhostscriptExecutionException extends RuntimeException {
        public GhostscriptExecutionException(String msg) {
            super(msg);
        }
    }
}
