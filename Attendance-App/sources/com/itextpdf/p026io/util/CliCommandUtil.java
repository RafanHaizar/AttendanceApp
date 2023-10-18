package com.itextpdf.p026io.util;

/* renamed from: com.itextpdf.io.util.CliCommandUtil */
public final class CliCommandUtil {
    private CliCommandUtil() {
    }

    public static boolean isVersionCommandExecutable(String command, String versionText) {
        if (command == null || versionText == null) {
            return false;
        }
        try {
            return SystemUtil.runProcessAndGetOutput(command, "-version").contains(versionText);
        } catch (Exception e) {
            return false;
        }
    }
}
