package org.bouncycastle.jcajce.provider.asymmetric.p015dh;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Enumeration;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPrivateKeySpec;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.p008x9.DomainParameters;
import org.bouncycastle.asn1.p008x9.ValidationParams;
import org.bouncycastle.asn1.p008x9.X9ObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.DHParameter;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.crypto.params.DHPrivateKeyParameters;
import org.bouncycastle.crypto.params.DHValidationParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.bouncycastle.jcajce.spec.DHDomainParameterSpec;
import org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;

/* renamed from: org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPrivateKey */
public class BCDHPrivateKey implements DHPrivateKey, PKCS12BagAttributeCarrier {
    static final long serialVersionUID = 311058815616901812L;
    private transient PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();
    private transient DHPrivateKeyParameters dhPrivateKey;
    private transient DHParameterSpec dhSpec;
    private transient PrivateKeyInfo info;

    /* renamed from: x */
    private BigInteger f646x;

    protected BCDHPrivateKey() {
    }

    BCDHPrivateKey(DHPrivateKey dHPrivateKey) {
        this.f646x = dHPrivateKey.getX();
        this.dhSpec = dHPrivateKey.getParams();
    }

    BCDHPrivateKey(DHPrivateKeySpec dHPrivateKeySpec) {
        this.f646x = dHPrivateKeySpec.getX();
        this.dhSpec = new DHParameterSpec(dHPrivateKeySpec.getP(), dHPrivateKeySpec.getG());
    }

    public BCDHPrivateKey(PrivateKeyInfo privateKeyInfo) throws IOException {
        DHPrivateKeyParameters dHPrivateKeyParameters;
        ASN1Sequence instance = ASN1Sequence.getInstance(privateKeyInfo.getPrivateKeyAlgorithm().getParameters());
        ASN1ObjectIdentifier algorithm = privateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm();
        this.info = privateKeyInfo;
        this.f646x = ((ASN1Integer) privateKeyInfo.parsePrivateKey()).getValue();
        if (algorithm.equals((ASN1Primitive) PKCSObjectIdentifiers.dhKeyAgreement)) {
            DHParameter instance2 = DHParameter.getInstance(instance);
            if (instance2.getL() != null) {
                this.dhSpec = new DHParameterSpec(instance2.getP(), instance2.getG(), instance2.getL().intValue());
                dHPrivateKeyParameters = new DHPrivateKeyParameters(this.f646x, new DHParameters(instance2.getP(), instance2.getG(), (BigInteger) null, instance2.getL().intValue()));
            } else {
                this.dhSpec = new DHParameterSpec(instance2.getP(), instance2.getG());
                dHPrivateKeyParameters = new DHPrivateKeyParameters(this.f646x, new DHParameters(instance2.getP(), instance2.getG()));
            }
        } else if (algorithm.equals((ASN1Primitive) X9ObjectIdentifiers.dhpublicnumber)) {
            DomainParameters instance3 = DomainParameters.getInstance(instance);
            this.dhSpec = new DHDomainParameterSpec(instance3.getP(), instance3.getQ(), instance3.getG(), instance3.getJ(), 0);
            dHPrivateKeyParameters = new DHPrivateKeyParameters(this.f646x, new DHParameters(instance3.getP(), instance3.getG(), instance3.getQ(), instance3.getJ(), (DHValidationParameters) null));
        } else {
            throw new IllegalArgumentException("unknown algorithm type: " + algorithm);
        }
        this.dhPrivateKey = dHPrivateKeyParameters;
    }

    BCDHPrivateKey(DHPrivateKeyParameters dHPrivateKeyParameters) {
        this.f646x = dHPrivateKeyParameters.getX();
        this.dhSpec = new DHDomainParameterSpec(dHPrivateKeyParameters.getParameters());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.dhSpec = new DHParameterSpec((BigInteger) objectInputStream.readObject(), (BigInteger) objectInputStream.readObject(), objectInputStream.readInt());
        this.info = null;
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.dhSpec.getP());
        objectOutputStream.writeObject(this.dhSpec.getG());
        objectOutputStream.writeInt(this.dhSpec.getL());
    }

    /* access modifiers changed from: package-private */
    public DHPrivateKeyParameters engineGetKeyParameters() {
        DHPrivateKeyParameters dHPrivateKeyParameters = this.dhPrivateKey;
        if (dHPrivateKeyParameters != null) {
            return dHPrivateKeyParameters;
        }
        DHParameterSpec dHParameterSpec = this.dhSpec;
        return dHParameterSpec instanceof DHDomainParameterSpec ? new DHPrivateKeyParameters(this.f646x, ((DHDomainParameterSpec) dHParameterSpec).getDomainParameters()) : new DHPrivateKeyParameters(this.f646x, new DHParameters(dHParameterSpec.getP(), this.dhSpec.getG(), (BigInteger) null, this.dhSpec.getL()));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DHPrivateKey)) {
            return false;
        }
        DHPrivateKey dHPrivateKey = (DHPrivateKey) obj;
        return getX().equals(dHPrivateKey.getX()) && getParams().getG().equals(dHPrivateKey.getParams().getG()) && getParams().getP().equals(dHPrivateKey.getParams().getP()) && getParams().getL() == dHPrivateKey.getParams().getL();
    }

    public String getAlgorithm() {
        return "DH";
    }

    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return this.attrCarrier.getBagAttribute(aSN1ObjectIdentifier);
    }

    public Enumeration getBagAttributeKeys() {
        return this.attrCarrier.getBagAttributeKeys();
    }

    public byte[] getEncoded() {
        PrivateKeyInfo privateKeyInfo;
        try {
            PrivateKeyInfo privateKeyInfo2 = this.info;
            if (privateKeyInfo2 != null) {
                return privateKeyInfo2.getEncoded(ASN1Encoding.DER);
            }
            DHParameterSpec dHParameterSpec = this.dhSpec;
            if (!(dHParameterSpec instanceof DHDomainParameterSpec) || ((DHDomainParameterSpec) dHParameterSpec).getQ() == null) {
                privateKeyInfo = new PrivateKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.dhKeyAgreement, new DHParameter(this.dhSpec.getP(), this.dhSpec.getG(), this.dhSpec.getL()).toASN1Primitive()), new ASN1Integer(getX()));
            } else {
                DHParameters domainParameters = ((DHDomainParameterSpec) this.dhSpec).getDomainParameters();
                DHValidationParameters validationParameters = domainParameters.getValidationParameters();
                privateKeyInfo = new PrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.dhpublicnumber, new DomainParameters(domainParameters.getP(), domainParameters.getG(), domainParameters.getQ(), domainParameters.getJ(), validationParameters != null ? new ValidationParams(validationParameters.getSeed(), validationParameters.getCounter()) : null).toASN1Primitive()), new ASN1Integer(getX()));
            }
            return privateKeyInfo.getEncoded(ASN1Encoding.DER);
        } catch (Exception e) {
            return null;
        }
    }

    public String getFormat() {
        return "PKCS#8";
    }

    public DHParameterSpec getParams() {
        return this.dhSpec;
    }

    public BigInteger getX() {
        return this.f646x;
    }

    public int hashCode() {
        return ((getX().hashCode() ^ getParams().getG().hashCode()) ^ getParams().getP().hashCode()) ^ getParams().getL();
    }

    public void setBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.attrCarrier.setBagAttribute(aSN1ObjectIdentifier, aSN1Encodable);
    }

    public String toString() {
        return DHUtil.privateKeyToString("DH", this.f646x, new DHParameters(this.dhSpec.getP(), this.dhSpec.getG()));
    }
}
