package com.itextpdf.p026io.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: com.itextpdf.io.util.SystemUtil */
public final class SystemUtil {
    private static final String SPLIT_REGEX = "((\".+?\"|[^'\\s]|'.+?')+)\\s*";

    @Deprecated
    public static long getSystemTimeMillis() {
        return System.currentTimeMillis();
    }

    public static long getTimeBasedSeed() {
        return System.currentTimeMillis();
    }

    public static int getTimeBasedIntSeed() {
        return (int) System.currentTimeMillis();
    }

    public static long getRelativeTimeMillis() {
        return System.currentTimeMillis();
    }

    public static long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    public static String getPropertyOrEnvironmentVariable(String name) {
        String s = System.getProperty(name);
        if (s == null) {
            return System.getenv(name);
        }
        return s;
    }

    public static boolean runProcessAndWait(String exec, String params) throws IOException, InterruptedException {
        return runProcessAndWait(exec, params, (String) null);
    }

    public static boolean runProcessAndWait(String exec, String params, String workingDirPath) throws IOException, InterruptedException {
        return runProcessAndGetExitCode(exec, params, workingDirPath) == 0;
    }

    public static int runProcessAndGetExitCode(String exec, String params) throws IOException, InterruptedException {
        return runProcessAndGetExitCode(exec, params, (String) null);
    }

    public static int runProcessAndGetExitCode(String exec, String params, String workingDirPath) throws IOException, InterruptedException {
        Process p = runProcess(exec, params, workingDirPath);
        System.out.println(getProcessOutput(p));
        return p.waitFor();
    }

    public static String runProcessAndGetOutput(String command, String params) throws IOException {
        return getProcessOutput(runProcess(command, params, (String) null));
    }

    public static StringBuilder runProcessAndCollectErrors(String execPath, String params) throws IOException {
        return printProcessErrorsOutput(runProcess(execPath, params, (String) null));
    }

    static Process runProcess(String execPath, String params, String workingDirPath) throws IOException {
        List<String> cmdList = prepareProcessArguments(execPath, params);
        String[] cmdArray = (String[]) cmdList.toArray(new String[cmdList.size()]);
        if (workingDirPath == null) {
            return Runtime.getRuntime().exec(cmdArray);
        }
        return Runtime.getRuntime().exec(cmdArray, (String[]) null, new File(workingDirPath));
    }

    static List<String> prepareProcessArguments(String exec, String params) {
        List<String> cmdList;
        if (new File(exec).exists()) {
            cmdList = new ArrayList<>(Collections.singletonList(exec));
        } else {
            cmdList = new ArrayList<>(splitIntoProcessArguments(exec));
        }
        cmdList.addAll(splitIntoProcessArguments(params));
        return cmdList;
    }

    static List<String> splitIntoProcessArguments(String line) {
        List<String> list = new ArrayList<>();
        Matcher m = Pattern.compile(SPLIT_REGEX).matcher(line);
        while (m.find()) {
            list.add(m.group(1).replace("'", "").replace("\"", "").trim());
        }
        return list;
    }

    static String getProcessOutput(Process p) throws IOException {
        BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        StringBuilder result = new StringBuilder();
        while (true) {
            String readLine = bri.readLine();
            String line = readLine;
            if (readLine == null) {
                break;
            }
            result.append(line);
        }
        bri.close();
        if (result.length() > 0) {
            result.append(10);
        }
        while (true) {
            String readLine2 = bre.readLine();
            String line2 = readLine2;
            if (readLine2 != null) {
                result.append(line2);
            } else {
                bre.close();
                return result.toString();
            }
        }
    }

    static StringBuilder printProcessErrorsOutput(Process p) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        while (true) {
            String readLine = bre.readLine();
            String line = readLine;
            if (readLine != null) {
                System.out.println(line);
                builder.append(line);
            } else {
                bre.close();
                return builder;
            }
        }
    }
}
