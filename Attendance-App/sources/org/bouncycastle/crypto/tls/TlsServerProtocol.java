package org.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.tls.TlsProtocol;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.util.Arrays;

public class TlsServerProtocol extends TlsProtocol {
    protected CertificateRequest certificateRequest = null;
    protected short clientCertificateType = -1;
    protected TlsKeyExchange keyExchange = null;
    protected TlsHandshakeHash prepareFinishHash = null;
    protected TlsCredentials serverCredentials = null;
    protected TlsServer tlsServer = null;
    TlsServerContextImpl tlsServerContext = null;

    public TlsServerProtocol(InputStream inputStream, OutputStream outputStream, SecureRandom secureRandom) {
        super(inputStream, outputStream, secureRandom);
    }

    public TlsServerProtocol(SecureRandom secureRandom) {
        super(secureRandom);
    }

    public void accept(TlsServer tlsServer2) throws IOException {
        if (tlsServer2 == null) {
            throw new IllegalArgumentException("'tlsServer' cannot be null");
        } else if (this.tlsServer == null) {
            this.tlsServer = tlsServer2;
            this.securityParameters = new SecurityParameters();
            this.securityParameters.entity = 0;
            this.tlsServerContext = new TlsServerContextImpl(this.secureRandom, this.securityParameters);
            this.securityParameters.serverRandom = createRandomBlock(tlsServer2.shouldUseGMTUnixTime(), this.tlsServerContext.getNonceRandomGenerator());
            this.tlsServer.init(this.tlsServerContext);
            this.recordStream.init(this.tlsServerContext);
            tlsServer2.notifyCloseHandle(this);
            this.recordStream.setRestrictReadVersion(false);
            blockForHandshake();
        } else {
            throw new IllegalStateException("'accept' can only be called once");
        }
    }

    /* access modifiers changed from: protected */
    public void cleanupHandshake() {
        super.cleanupHandshake();
        this.keyExchange = null;
        this.serverCredentials = null;
        this.certificateRequest = null;
        this.prepareFinishHash = null;
    }

    /* access modifiers changed from: protected */
    public boolean expectCertificateVerifyMessage() {
        short s = this.clientCertificateType;
        return s >= 0 && TlsUtils.hasSigningCapability(s);
    }

    /* access modifiers changed from: protected */
    public TlsContext getContext() {
        return this.tlsServerContext;
    }

    /* access modifiers changed from: package-private */
    public AbstractTlsContext getContextAdmin() {
        return this.tlsServerContext;
    }

    /* access modifiers changed from: protected */
    public TlsPeer getPeer() {
        return this.tlsServer;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0023, code lost:
        notifyClientCertificate(org.bouncycastle.crypto.tls.Certificate.EMPTY_CHAIN);
        r2.connection_state = 10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleAlertWarningMessage(short r3) throws java.io.IOException {
        /*
            r2 = this;
            super.handleAlertWarningMessage(r3)
            switch(r3) {
                case 41: goto L_0x0007;
                default: goto L_0x0006;
            }
        L_0x0006:
            return
        L_0x0007:
            org.bouncycastle.crypto.tls.TlsContext r3 = r2.getContext()
            boolean r3 = org.bouncycastle.crypto.tls.TlsUtils.isSSL(r3)
            r0 = 10
            if (r3 == 0) goto L_0x002b
            org.bouncycastle.crypto.tls.CertificateRequest r3 = r2.certificateRequest
            if (r3 == 0) goto L_0x002b
            short r3 = r2.connection_state
            switch(r3) {
                case 8: goto L_0x001d;
                case 9: goto L_0x0023;
                default: goto L_0x001c;
            }
        L_0x001c:
            goto L_0x002b
        L_0x001d:
            org.bouncycastle.crypto.tls.TlsServer r3 = r2.tlsServer
            r1 = 0
            r3.processClientSupplementalData(r1)
        L_0x0023:
            org.bouncycastle.crypto.tls.Certificate r3 = org.bouncycastle.crypto.tls.Certificate.EMPTY_CHAIN
            r2.notifyClientCertificate(r3)
            r2.connection_state = r0
            return
        L_0x002b:
            org.bouncycastle.crypto.tls.TlsFatalAlert r3 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r3.<init>(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.tls.TlsServerProtocol.handleAlertWarningMessage(short):void");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0074, code lost:
        if (r2.certificateRequest != null) goto L_0x007c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0076, code lost:
        r2.keyExchange.skipClientCredentials();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0084, code lost:
        if (org.bouncycastle.crypto.tls.TlsUtils.isTLSv12(getContext()) != false) goto L_0x00a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x008e, code lost:
        if (org.bouncycastle.crypto.tls.TlsUtils.isSSL(getContext()) == false) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0092, code lost:
        if (r2.peerCertificate == null) goto L_0x0095;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x009a, code lost:
        throw new org.bouncycastle.crypto.tls.TlsFatalAlert(10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x009b, code lost:
        notifyClientCertificate(org.bouncycastle.crypto.tls.Certificate.EMPTY_CHAIN);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00a0, code lost:
        receiveClientKeyExchangeMessage(r4);
        r3 = 11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00ae, code lost:
        throw new org.bouncycastle.crypto.tls.TlsFatalAlert(10);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleHandshakeMessage(short r3, java.io.ByteArrayInputStream r4) throws java.io.IOException {
        /*
            r2 = this;
            r0 = 0
            r1 = 10
            switch(r3) {
                case 1: goto L_0x00ed;
                case 11: goto L_0x00cc;
                case 15: goto L_0x00af;
                case 16: goto L_0x0062;
                case 20: goto L_0x0024;
                case 23: goto L_0x000c;
                default: goto L_0x0006;
            }
        L_0x0006:
            org.bouncycastle.crypto.tls.TlsFatalAlert r3 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r3.<init>(r1)
            throw r3
        L_0x000c:
            short r3 = r2.connection_state
            switch(r3) {
                case 8: goto L_0x0017;
                default: goto L_0x0011;
            }
        L_0x0011:
            org.bouncycastle.crypto.tls.TlsFatalAlert r3 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r3.<init>(r1)
            throw r3
        L_0x0017:
            org.bouncycastle.crypto.tls.TlsServer r3 = r2.tlsServer
            java.util.Vector r4 = readSupplementalDataMessage(r4)
            r3.processClientSupplementalData(r4)
            r3 = 9
            goto L_0x00a5
        L_0x0024:
            short r3 = r2.connection_state
            switch(r3) {
                case 11: goto L_0x002f;
                case 12: goto L_0x0035;
                default: goto L_0x0029;
            }
        L_0x0029:
            org.bouncycastle.crypto.tls.TlsFatalAlert r3 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r3.<init>(r1)
            throw r3
        L_0x002f:
            boolean r3 = r2.expectCertificateVerifyMessage()
            if (r3 != 0) goto L_0x005c
        L_0x0035:
            r2.processFinishedMessage(r4)
            r3 = 13
            r2.connection_state = r3
            boolean r3 = r2.expectSessionTicket
            if (r3 == 0) goto L_0x0049
            org.bouncycastle.crypto.tls.TlsServer r3 = r2.tlsServer
            org.bouncycastle.crypto.tls.NewSessionTicket r3 = r3.getNewSessionTicket()
            r2.sendNewSessionTicketMessage(r3)
        L_0x0049:
            r3 = 14
            r2.connection_state = r3
            r2.sendChangeCipherSpecMessage()
            r2.sendFinishedMessage()
            r3 = 15
            r2.connection_state = r3
            r2.completeHandshake()
            goto L_0x01d0
        L_0x005c:
            org.bouncycastle.crypto.tls.TlsFatalAlert r3 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r3.<init>(r1)
            throw r3
        L_0x0062:
            short r3 = r2.connection_state
            switch(r3) {
                case 8: goto L_0x006d;
                case 9: goto L_0x0072;
                case 10: goto L_0x00a0;
                default: goto L_0x0067;
            }
        L_0x0067:
            org.bouncycastle.crypto.tls.TlsFatalAlert r3 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r3.<init>(r1)
            throw r3
        L_0x006d:
            org.bouncycastle.crypto.tls.TlsServer r3 = r2.tlsServer
            r3.processClientSupplementalData(r0)
        L_0x0072:
            org.bouncycastle.crypto.tls.CertificateRequest r3 = r2.certificateRequest
            if (r3 != 0) goto L_0x007c
            org.bouncycastle.crypto.tls.TlsKeyExchange r3 = r2.keyExchange
            r3.skipClientCredentials()
            goto L_0x00a0
        L_0x007c:
            org.bouncycastle.crypto.tls.TlsContext r3 = r2.getContext()
            boolean r3 = org.bouncycastle.crypto.tls.TlsUtils.isTLSv12((org.bouncycastle.crypto.tls.TlsContext) r3)
            if (r3 != 0) goto L_0x00a9
            org.bouncycastle.crypto.tls.TlsContext r3 = r2.getContext()
            boolean r3 = org.bouncycastle.crypto.tls.TlsUtils.isSSL(r3)
            if (r3 == 0) goto L_0x009b
            org.bouncycastle.crypto.tls.Certificate r3 = r2.peerCertificate
            if (r3 == 0) goto L_0x0095
            goto L_0x00a0
        L_0x0095:
            org.bouncycastle.crypto.tls.TlsFatalAlert r3 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r3.<init>(r1)
            throw r3
        L_0x009b:
            org.bouncycastle.crypto.tls.Certificate r3 = org.bouncycastle.crypto.tls.Certificate.EMPTY_CHAIN
            r2.notifyClientCertificate(r3)
        L_0x00a0:
            r2.receiveClientKeyExchangeMessage(r4)
            r3 = 11
        L_0x00a5:
            r2.connection_state = r3
            goto L_0x01d0
        L_0x00a9:
            org.bouncycastle.crypto.tls.TlsFatalAlert r3 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r3.<init>(r1)
            throw r3
        L_0x00af:
            short r3 = r2.connection_state
            switch(r3) {
                case 11: goto L_0x00ba;
                default: goto L_0x00b4;
            }
        L_0x00b4:
            org.bouncycastle.crypto.tls.TlsFatalAlert r3 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r3.<init>(r1)
            throw r3
        L_0x00ba:
            boolean r3 = r2.expectCertificateVerifyMessage()
            if (r3 == 0) goto L_0x00c6
            r2.receiveCertificateVerifyMessage(r4)
            r3 = 12
            goto L_0x00a5
        L_0x00c6:
            org.bouncycastle.crypto.tls.TlsFatalAlert r3 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r3.<init>(r1)
            throw r3
        L_0x00cc:
            short r3 = r2.connection_state
            switch(r3) {
                case 8: goto L_0x00d7;
                case 9: goto L_0x00dc;
                default: goto L_0x00d1;
            }
        L_0x00d1:
            org.bouncycastle.crypto.tls.TlsFatalAlert r3 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r3.<init>(r1)
            throw r3
        L_0x00d7:
            org.bouncycastle.crypto.tls.TlsServer r3 = r2.tlsServer
            r3.processClientSupplementalData(r0)
        L_0x00dc:
            org.bouncycastle.crypto.tls.CertificateRequest r3 = r2.certificateRequest
            if (r3 == 0) goto L_0x00e7
            r2.receiveCertificateMessage(r4)
            r2.connection_state = r1
            goto L_0x01d0
        L_0x00e7:
            org.bouncycastle.crypto.tls.TlsFatalAlert r3 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r3.<init>(r1)
            throw r3
        L_0x00ed:
            short r3 = r2.connection_state
            switch(r3) {
                case 0: goto L_0x00fd;
                case 16: goto L_0x00f8;
                default: goto L_0x00f2;
            }
        L_0x00f2:
            org.bouncycastle.crypto.tls.TlsFatalAlert r3 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r3.<init>(r1)
            throw r3
        L_0x00f8:
            r2.refuseRenegotiation()
            goto L_0x01d0
        L_0x00fd:
            r2.receiveClientHelloMessage(r4)
            r3 = 1
            r2.connection_state = r3
            r2.sendServerHelloMessage()
            r4 = 2
            r2.connection_state = r4
            org.bouncycastle.crypto.tls.RecordStream r4 = r2.recordStream
            r4.notifyHelloComplete()
            org.bouncycastle.crypto.tls.TlsServer r4 = r2.tlsServer
            java.util.Vector r4 = r4.getServerSupplementalData()
            if (r4 == 0) goto L_0x0119
            r2.sendSupplementalDataMessage(r4)
        L_0x0119:
            r4 = 3
            r2.connection_state = r4
            org.bouncycastle.crypto.tls.TlsServer r4 = r2.tlsServer
            org.bouncycastle.crypto.tls.TlsKeyExchange r4 = r4.getKeyExchange()
            r2.keyExchange = r4
            org.bouncycastle.crypto.tls.TlsContext r1 = r2.getContext()
            r4.init(r1)
            org.bouncycastle.crypto.tls.TlsServer r4 = r2.tlsServer
            org.bouncycastle.crypto.tls.TlsCredentials r4 = r4.getCredentials()
            r2.serverCredentials = r4
            if (r4 != 0) goto L_0x013b
            org.bouncycastle.crypto.tls.TlsKeyExchange r4 = r2.keyExchange
            r4.skipServerCredentials()
            goto L_0x0149
        L_0x013b:
            org.bouncycastle.crypto.tls.TlsKeyExchange r0 = r2.keyExchange
            r0.processServerCredentials(r4)
            org.bouncycastle.crypto.tls.TlsCredentials r4 = r2.serverCredentials
            org.bouncycastle.crypto.tls.Certificate r0 = r4.getCertificate()
            r2.sendCertificateMessage(r0)
        L_0x0149:
            r4 = 4
            r2.connection_state = r4
            r4 = 0
            if (r0 == 0) goto L_0x0155
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x0157
        L_0x0155:
            r2.allowCertificateStatus = r4
        L_0x0157:
            boolean r0 = r2.allowCertificateStatus
            if (r0 == 0) goto L_0x0166
            org.bouncycastle.crypto.tls.TlsServer r0 = r2.tlsServer
            org.bouncycastle.crypto.tls.CertificateStatus r0 = r0.getCertificateStatus()
            if (r0 == 0) goto L_0x0166
            r2.sendCertificateStatusMessage(r0)
        L_0x0166:
            r0 = 5
            r2.connection_state = r0
            org.bouncycastle.crypto.tls.TlsKeyExchange r0 = r2.keyExchange
            byte[] r0 = r0.generateServerKeyExchange()
            if (r0 == 0) goto L_0x0174
            r2.sendServerKeyExchangeMessage(r0)
        L_0x0174:
            r0 = 6
            r2.connection_state = r0
            org.bouncycastle.crypto.tls.TlsCredentials r0 = r2.serverCredentials
            if (r0 == 0) goto L_0x01bd
            org.bouncycastle.crypto.tls.TlsServer r0 = r2.tlsServer
            org.bouncycastle.crypto.tls.CertificateRequest r0 = r0.getCertificateRequest()
            r2.certificateRequest = r0
            if (r0 == 0) goto L_0x01bd
            org.bouncycastle.crypto.tls.TlsContext r0 = r2.getContext()
            boolean r0 = org.bouncycastle.crypto.tls.TlsUtils.isTLSv12((org.bouncycastle.crypto.tls.TlsContext) r0)
            org.bouncycastle.crypto.tls.CertificateRequest r1 = r2.certificateRequest
            java.util.Vector r1 = r1.getSupportedSignatureAlgorithms()
            if (r1 == 0) goto L_0x0196
            goto L_0x0197
        L_0x0196:
            r3 = 0
        L_0x0197:
            if (r0 != r3) goto L_0x01b5
            org.bouncycastle.crypto.tls.TlsKeyExchange r3 = r2.keyExchange
            org.bouncycastle.crypto.tls.CertificateRequest r4 = r2.certificateRequest
            r3.validateCertificateRequest(r4)
            org.bouncycastle.crypto.tls.CertificateRequest r3 = r2.certificateRequest
            r2.sendCertificateRequestMessage(r3)
            org.bouncycastle.crypto.tls.RecordStream r3 = r2.recordStream
            org.bouncycastle.crypto.tls.TlsHandshakeHash r3 = r3.getHandshakeHash()
            org.bouncycastle.crypto.tls.CertificateRequest r4 = r2.certificateRequest
            java.util.Vector r4 = r4.getSupportedSignatureAlgorithms()
            org.bouncycastle.crypto.tls.TlsUtils.trackHashAlgorithms(r3, r4)
            goto L_0x01bd
        L_0x01b5:
            org.bouncycastle.crypto.tls.TlsFatalAlert r3 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r4 = 80
            r3.<init>(r4)
            throw r3
        L_0x01bd:
            r3 = 7
            r2.connection_state = r3
            r2.sendServerHelloDoneMessage()
            r3 = 8
            r2.connection_state = r3
            org.bouncycastle.crypto.tls.RecordStream r3 = r2.recordStream
            org.bouncycastle.crypto.tls.TlsHandshakeHash r3 = r3.getHandshakeHash()
            r3.sealHashAlgorithms()
        L_0x01d0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.tls.TlsServerProtocol.handleHandshakeMessage(short, java.io.ByteArrayInputStream):void");
    }

    /* access modifiers changed from: protected */
    public void notifyClientCertificate(Certificate certificate) throws IOException {
        if (this.certificateRequest == null) {
            throw new IllegalStateException();
        } else if (this.peerCertificate == null) {
            this.peerCertificate = certificate;
            if (certificate.isEmpty()) {
                this.keyExchange.skipClientCredentials();
            } else {
                this.clientCertificateType = TlsUtils.getClientCertificateType(certificate, this.serverCredentials.getCertificate());
                this.keyExchange.processClientCertificate(certificate);
            }
            this.tlsServer.notifyClientCertificate(certificate);
        } else {
            throw new TlsFatalAlert(10);
        }
    }

    /* access modifiers changed from: protected */
    public void receiveCertificateMessage(ByteArrayInputStream byteArrayInputStream) throws IOException {
        Certificate parse = Certificate.parse(byteArrayInputStream);
        assertEmpty(byteArrayInputStream);
        notifyClientCertificate(parse);
    }

    /* access modifiers changed from: protected */
    public void receiveCertificateVerifyMessage(ByteArrayInputStream byteArrayInputStream) throws IOException {
        byte[] bArr;
        if (this.certificateRequest != null) {
            DigitallySigned parse = DigitallySigned.parse(getContext(), byteArrayInputStream);
            assertEmpty(byteArrayInputStream);
            try {
                SignatureAndHashAlgorithm algorithm = parse.getAlgorithm();
                if (TlsUtils.isTLSv12(getContext())) {
                    TlsUtils.verifySupportedSignatureAlgorithm(this.certificateRequest.getSupportedSignatureAlgorithms(), algorithm);
                    bArr = this.prepareFinishHash.getFinalHash(algorithm.getHash());
                } else {
                    bArr = this.securityParameters.getSessionHash();
                }
                AsymmetricKeyParameter createKey = PublicKeyFactory.createKey(this.peerCertificate.getCertificateAt(0).getSubjectPublicKeyInfo());
                TlsSigner createTlsSigner = TlsUtils.createTlsSigner(this.clientCertificateType);
                createTlsSigner.init(getContext());
                if (!createTlsSigner.verifyRawSignature(algorithm, parse.getSignature(), createKey, bArr)) {
                    throw new TlsFatalAlert(51);
                }
            } catch (TlsFatalAlert e) {
                throw e;
            } catch (Exception e2) {
                throw new TlsFatalAlert(51, e2);
            }
        } else {
            throw new IllegalStateException();
        }
    }

    /* access modifiers changed from: protected */
    public void receiveClientHelloMessage(ByteArrayInputStream byteArrayInputStream) throws IOException {
        ProtocolVersion readVersion = TlsUtils.readVersion(byteArrayInputStream);
        this.recordStream.setWriteVersion(readVersion);
        if (!readVersion.isDTLS()) {
            byte[] readFully = TlsUtils.readFully(32, (InputStream) byteArrayInputStream);
            if (TlsUtils.readOpaque8(byteArrayInputStream).length <= 32) {
                int readUint16 = TlsUtils.readUint16(byteArrayInputStream);
                if (readUint16 < 2 || (readUint16 & 1) != 0) {
                    throw new TlsFatalAlert(50);
                }
                this.offeredCipherSuites = TlsUtils.readUint16Array(readUint16 / 2, byteArrayInputStream);
                short readUint8 = TlsUtils.readUint8(byteArrayInputStream);
                if (readUint8 >= 1) {
                    this.offeredCompressionMethods = TlsUtils.readUint8Array(readUint8, byteArrayInputStream);
                    this.clientExtensions = readExtensions(byteArrayInputStream);
                    this.securityParameters.extendedMasterSecret = TlsExtensionsUtils.hasExtendedMasterSecretExtension(this.clientExtensions);
                    if (this.securityParameters.isExtendedMasterSecret() || !this.tlsServer.requiresExtendedMasterSecret()) {
                        getContextAdmin().setClientVersion(readVersion);
                        this.tlsServer.notifyClientVersion(readVersion);
                        this.tlsServer.notifyFallback(Arrays.contains(this.offeredCipherSuites, (int) CipherSuite.TLS_FALLBACK_SCSV));
                        this.securityParameters.clientRandom = readFully;
                        this.tlsServer.notifyOfferedCipherSuites(this.offeredCipherSuites);
                        this.tlsServer.notifyOfferedCompressionMethods(this.offeredCompressionMethods);
                        if (Arrays.contains(this.offeredCipherSuites, 255)) {
                            this.secure_renegotiation = true;
                        }
                        byte[] extensionData = TlsUtils.getExtensionData(this.clientExtensions, EXT_RenegotiationInfo);
                        if (extensionData != null) {
                            this.secure_renegotiation = true;
                            if (!Arrays.constantTimeAreEqual(extensionData, createRenegotiationInfo(TlsUtils.EMPTY_BYTES))) {
                                throw new TlsFatalAlert(40);
                            }
                        }
                        this.tlsServer.notifySecureRenegotiation(this.secure_renegotiation);
                        if (this.clientExtensions != null) {
                            TlsExtensionsUtils.getPaddingExtension(this.clientExtensions);
                            this.tlsServer.processClientExtensions(this.clientExtensions);
                            return;
                        }
                        return;
                    }
                    throw new TlsFatalAlert(40);
                }
                throw new TlsFatalAlert(47);
            }
            throw new TlsFatalAlert(47);
        }
        throw new TlsFatalAlert(47);
    }

    /* access modifiers changed from: protected */
    public void receiveClientKeyExchangeMessage(ByteArrayInputStream byteArrayInputStream) throws IOException {
        this.keyExchange.processClientKeyExchange(byteArrayInputStream);
        assertEmpty(byteArrayInputStream);
        if (TlsUtils.isSSL(getContext())) {
            establishMasterSecret(getContext(), this.keyExchange);
        }
        this.prepareFinishHash = this.recordStream.prepareToFinish();
        this.securityParameters.sessionHash = getCurrentPRFHash(getContext(), this.prepareFinishHash, (byte[]) null);
        if (!TlsUtils.isSSL(getContext())) {
            establishMasterSecret(getContext(), this.keyExchange);
        }
        this.recordStream.setPendingConnectionState(getPeer().getCompression(), getPeer().getCipher());
    }

    /* access modifiers changed from: protected */
    public void sendCertificateRequestMessage(CertificateRequest certificateRequest2) throws IOException {
        TlsProtocol.HandshakeMessage handshakeMessage = new TlsProtocol.HandshakeMessage(this, 13);
        certificateRequest2.encode(handshakeMessage);
        handshakeMessage.writeToRecordStream();
    }

    /* access modifiers changed from: protected */
    public void sendCertificateStatusMessage(CertificateStatus certificateStatus) throws IOException {
        TlsProtocol.HandshakeMessage handshakeMessage = new TlsProtocol.HandshakeMessage(this, 22);
        certificateStatus.encode(handshakeMessage);
        handshakeMessage.writeToRecordStream();
    }

    /* access modifiers changed from: protected */
    public void sendNewSessionTicketMessage(NewSessionTicket newSessionTicket) throws IOException {
        if (newSessionTicket != null) {
            TlsProtocol.HandshakeMessage handshakeMessage = new TlsProtocol.HandshakeMessage(this, 4);
            newSessionTicket.encode(handshakeMessage);
            handshakeMessage.writeToRecordStream();
            return;
        }
        throw new TlsFatalAlert(80);
    }

    /* access modifiers changed from: protected */
    public void sendServerHelloDoneMessage() throws IOException {
        byte[] bArr = new byte[4];
        TlsUtils.writeUint8(14, bArr, 0);
        TlsUtils.writeUint24(0, bArr, 1);
        writeHandshakeMessage(bArr, 0, 4);
    }

    /* access modifiers changed from: protected */
    public void sendServerHelloMessage() throws IOException {
        TlsProtocol.HandshakeMessage handshakeMessage = new TlsProtocol.HandshakeMessage(this, 2);
        ProtocolVersion serverVersion = this.tlsServer.getServerVersion();
        if (serverVersion.isEqualOrEarlierVersionOf(getContext().getClientVersion())) {
            this.recordStream.setReadVersion(serverVersion);
            this.recordStream.setWriteVersion(serverVersion);
            boolean z = true;
            this.recordStream.setRestrictReadVersion(true);
            getContextAdmin().setServerVersion(serverVersion);
            TlsUtils.writeVersion(serverVersion, handshakeMessage);
            handshakeMessage.write(this.securityParameters.serverRandom);
            TlsUtils.writeOpaque8(TlsUtils.EMPTY_BYTES, handshakeMessage);
            int selectedCipherSuite = this.tlsServer.getSelectedCipherSuite();
            if (!Arrays.contains(this.offeredCipherSuites, selectedCipherSuite) || selectedCipherSuite == 0 || CipherSuite.isSCSV(selectedCipherSuite) || !TlsUtils.isValidCipherSuiteForVersion(selectedCipherSuite, getContext().getServerVersion())) {
                throw new TlsFatalAlert(80);
            }
            this.securityParameters.cipherSuite = selectedCipherSuite;
            short selectedCompressionMethod = this.tlsServer.getSelectedCompressionMethod();
            if (Arrays.contains(this.offeredCompressionMethods, selectedCompressionMethod)) {
                this.securityParameters.compressionAlgorithm = selectedCompressionMethod;
                TlsUtils.writeUint16(selectedCipherSuite, handshakeMessage);
                TlsUtils.writeUint8(selectedCompressionMethod, (OutputStream) handshakeMessage);
                this.serverExtensions = TlsExtensionsUtils.ensureExtensionsInitialised(this.tlsServer.getServerExtensions());
                if (this.secure_renegotiation) {
                    if (TlsUtils.getExtensionData(this.serverExtensions, EXT_RenegotiationInfo) == null) {
                        this.serverExtensions.put(EXT_RenegotiationInfo, createRenegotiationInfo(TlsUtils.EMPTY_BYTES));
                    }
                }
                if (TlsUtils.isSSL(this.tlsServerContext)) {
                    this.securityParameters.extendedMasterSecret = false;
                } else if (this.securityParameters.isExtendedMasterSecret()) {
                    TlsExtensionsUtils.addExtendedMasterSecretExtension(this.serverExtensions);
                }
                if (!this.serverExtensions.isEmpty()) {
                    this.securityParameters.encryptThenMAC = TlsExtensionsUtils.hasEncryptThenMACExtension(this.serverExtensions);
                    this.securityParameters.maxFragmentLength = processMaxFragmentLengthExtension(this.clientExtensions, this.serverExtensions, 80);
                    this.securityParameters.truncatedHMac = TlsExtensionsUtils.hasTruncatedHMacExtension(this.serverExtensions);
                    this.allowCertificateStatus = !this.resumedSession && TlsUtils.hasExpectedEmptyExtensionData(this.serverExtensions, TlsExtensionsUtils.EXT_status_request, 80);
                    if (this.resumedSession || !TlsUtils.hasExpectedEmptyExtensionData(this.serverExtensions, TlsProtocol.EXT_SessionTicket, 80)) {
                        z = false;
                    }
                    this.expectSessionTicket = z;
                    writeExtensions(handshakeMessage, this.serverExtensions);
                }
                this.securityParameters.prfAlgorithm = getPRFAlgorithm(getContext(), this.securityParameters.getCipherSuite());
                this.securityParameters.verifyDataLength = 12;
                applyMaxFragmentLengthExtension();
                handshakeMessage.writeToRecordStream();
                return;
            }
            throw new TlsFatalAlert(80);
        }
        throw new TlsFatalAlert(80);
    }

    /* access modifiers changed from: protected */
    public void sendServerKeyExchangeMessage(byte[] bArr) throws IOException {
        TlsProtocol.HandshakeMessage handshakeMessage = new TlsProtocol.HandshakeMessage(12, bArr.length);
        handshakeMessage.write(bArr);
        handshakeMessage.writeToRecordStream();
    }
}
