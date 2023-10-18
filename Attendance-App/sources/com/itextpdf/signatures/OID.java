package com.itextpdf.signatures;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public final class OID {

    public static final class X509Extensions {
        public static final String AUTHORITY_INFO_ACCESS = "1.3.6.1.5.5.7.1.1";
        public static final String AUTHORITY_KEY_IDENTIFIER = "2.5.29.35";
        public static final String BASIC_CONSTRAINTS = "2.5.29.19";
        public static final String CERTIFICATE_POLICIES = "2.5.29.32";
        public static final String CRL_DISTRIBUTION_POINTS = "2.5.29.31";
        public static final String EXTENDED_KEY_USAGE = "2.5.29.37";
        public static final String FRESHEST_CRL = "2.5.29.46";
        public static final String ID_KP_TIMESTAMPING = "1.3.6.1.5.5.7.3.8";
        public static final String ID_PKIX_OCSP_NOCHECK = "1.3.6.1.5.5.7.48.1.5";
        public static final String INHIBIT_ANY_POLICY = "2.5.29.54";
        public static final String ISSUER_ALTERNATIVE_NAME = "2.5.29.18";
        public static final String KEY_USAGE = "2.5.29.15";
        public static final String NAME_CONSTRAINTS = "2.5.29.30";
        public static final String POLICY_CONSTRAINTS = "2.5.29.36";
        public static final String POLICY_MAPPINGS = "2.5.29.33";
        public static final String SUBJECT_ALTERNATIVE_NAME = "2.5.29.17";
        public static final String SUBJECT_DIRECTORY_ATTRIBUTES = "2.5.29.9";
        public static final String SUBJECT_INFO_ACCESS = "1.3.6.1.5.5.7.1.11";
        public static final String SUBJECT_KEY_IDENTIFIER = "2.5.29.14";
        public static final Set<String> SUPPORTED_CRITICAL_EXTENSIONS = Collections.unmodifiableSet(new LinkedHashSet(Arrays.asList(new String[]{KEY_USAGE, CERTIFICATE_POLICIES, POLICY_MAPPINGS, SUBJECT_ALTERNATIVE_NAME, ISSUER_ALTERNATIVE_NAME, BASIC_CONSTRAINTS, NAME_CONSTRAINTS, POLICY_CONSTRAINTS, EXTENDED_KEY_USAGE, CRL_DISTRIBUTION_POINTS, INHIBIT_ANY_POLICY, ID_PKIX_OCSP_NOCHECK})));
    }

    private OID() {
    }
}
