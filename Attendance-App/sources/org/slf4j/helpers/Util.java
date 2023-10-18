package org.slf4j.helpers;

public final class Util {
    private static ClassContextSecurityManager SECURITY_MANAGER;
    private static boolean SECURITY_MANAGER_CREATION_ALREADY_ATTEMPTED = false;

    private Util() {
    }

    public static String safeGetSystemProperty(String key) {
        if (key != null) {
            try {
                return System.getProperty(key);
            } catch (SecurityException e) {
                return null;
            }
        } else {
            throw new IllegalArgumentException("null input");
        }
    }

    public static boolean safeGetBooleanSystemProperty(String key) {
        String value = safeGetSystemProperty(key);
        if (value == null) {
            return false;
        }
        return value.equalsIgnoreCase("true");
    }

    private static final class ClassContextSecurityManager extends SecurityManager {
        private ClassContextSecurityManager() {
        }

        /* access modifiers changed from: protected */
        public Class<?>[] getClassContext() {
            return super.getClassContext();
        }
    }

    private static ClassContextSecurityManager getSecurityManager() {
        ClassContextSecurityManager classContextSecurityManager = SECURITY_MANAGER;
        if (classContextSecurityManager != null) {
            return classContextSecurityManager;
        }
        if (SECURITY_MANAGER_CREATION_ALREADY_ATTEMPTED) {
            return null;
        }
        ClassContextSecurityManager safeCreateSecurityManager = safeCreateSecurityManager();
        SECURITY_MANAGER = safeCreateSecurityManager;
        SECURITY_MANAGER_CREATION_ALREADY_ATTEMPTED = true;
        return safeCreateSecurityManager;
    }

    private static ClassContextSecurityManager safeCreateSecurityManager() {
        try {
            return new ClassContextSecurityManager();
        } catch (SecurityException e) {
            return null;
        }
    }

    public static Class<?> getCallingClass() {
        ClassContextSecurityManager securityManager = getSecurityManager();
        if (securityManager == null) {
            return null;
        }
        Class<?>[] trace = securityManager.getClassContext();
        String thisClassName = Util.class.getName();
        int i = 0;
        while (i < trace.length && !thisClassName.equals(trace[i].getName())) {
            i++;
        }
        if (i < trace.length && i + 2 < trace.length) {
            return trace[i + 2];
        }
        throw new IllegalStateException("Failed to find org.slf4j.helpers.Util or its caller in the stack; this should not happen");
    }

    public static final void report(String msg, Throwable t) {
        System.err.println(msg);
        System.err.println("Reported exception:");
        t.printStackTrace();
    }

    public static final void report(String msg) {
        System.err.println("SLF4J: " + msg);
    }
}
