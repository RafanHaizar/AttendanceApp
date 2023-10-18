package org.bouncycastle.operator.jcajce;

import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.jcajce.p014io.OutputStreamFactory;
import org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.RuntimeOperatorException;

public class JcaContentSignerBuilder {
    private OperatorHelper helper = new OperatorHelper(new DefaultJcaJceHelper());
    private SecureRandom random;
    private AlgorithmIdentifier sigAlgId;
    private AlgorithmParameterSpec sigAlgSpec;
    private String signatureAlgorithm;

    public JcaContentSignerBuilder(String str) {
        this.signatureAlgorithm = str;
        this.sigAlgId = new DefaultSignatureAlgorithmIdentifierFinder().find(str);
        this.sigAlgSpec = null;
    }

    public JcaContentSignerBuilder(String str, AlgorithmParameterSpec algorithmParameterSpec) {
        this.signatureAlgorithm = str;
        if (algorithmParameterSpec instanceof PSSParameterSpec) {
            PSSParameterSpec pSSParameterSpec = (PSSParameterSpec) algorithmParameterSpec;
            this.sigAlgSpec = pSSParameterSpec;
            this.sigAlgId = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_RSASSA_PSS, createPSSParams(pSSParameterSpec));
            return;
        }
        throw new IllegalArgumentException("unknown sigParamSpec: " + (algorithmParameterSpec == null ? "null" : algorithmParameterSpec.getClass().getName()));
    }

    private static RSASSAPSSparams createPSSParams(PSSParameterSpec pSSParameterSpec) {
        DefaultDigestAlgorithmIdentifierFinder defaultDigestAlgorithmIdentifierFinder = new DefaultDigestAlgorithmIdentifierFinder();
        return new RSASSAPSSparams(defaultDigestAlgorithmIdentifierFinder.find(pSSParameterSpec.getDigestAlgorithm()), new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, defaultDigestAlgorithmIdentifierFinder.find(((MGF1ParameterSpec) pSSParameterSpec.getMGFParameters()).getDigestAlgorithm())), new ASN1Integer((long) pSSParameterSpec.getSaltLength()), new ASN1Integer((long) pSSParameterSpec.getTrailerField()));
    }

    public ContentSigner build(PrivateKey privateKey) throws OperatorCreationException {
        try {
            Signature createSignature = this.helper.createSignature(this.sigAlgId);
            AlgorithmIdentifier algorithmIdentifier = this.sigAlgId;
            SecureRandom secureRandom = this.random;
            if (secureRandom != null) {
                createSignature.initSign(privateKey, secureRandom);
            } else {
                createSignature.initSign(privateKey);
            }
            return new ContentSigner(createSignature, algorithmIdentifier) {
                private OutputStream stream;
                final /* synthetic */ Signature val$sig;
                final /* synthetic */ AlgorithmIdentifier val$signatureAlgId;

                {
                    this.val$sig = r2;
                    this.val$signatureAlgId = r3;
                    this.stream = OutputStreamFactory.createStream(r2);
                }

                public AlgorithmIdentifier getAlgorithmIdentifier() {
                    return this.val$signatureAlgId;
                }

                public OutputStream getOutputStream() {
                    return this.stream;
                }

                public byte[] getSignature() {
                    try {
                        return this.val$sig.sign();
                    } catch (SignatureException e) {
                        throw new RuntimeOperatorException("exception obtaining signature: " + e.getMessage(), e);
                    }
                }
            };
        } catch (GeneralSecurityException e) {
            throw new OperatorCreationException("cannot create signer: " + e.getMessage(), e);
        }
    }

    public JcaContentSignerBuilder setProvider(String str) {
        this.helper = new OperatorHelper(new NamedJcaJceHelper(str));
        return this;
    }

    public JcaContentSignerBuilder setProvider(Provider provider) {
        this.helper = new OperatorHelper(new ProviderJcaJceHelper(provider));
        return this;
    }

    public JcaContentSignerBuilder setSecureRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
        return this;
    }
}
