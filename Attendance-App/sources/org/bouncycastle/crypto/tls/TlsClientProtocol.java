package org.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.crypto.tls.TlsProtocol;
import org.bouncycastle.util.Arrays;

public class TlsClientProtocol extends TlsProtocol {
    protected TlsAuthentication authentication = null;
    protected CertificateRequest certificateRequest = null;
    protected CertificateStatus certificateStatus = null;
    protected TlsKeyExchange keyExchange = null;
    protected byte[] selectedSessionID = null;
    protected TlsClient tlsClient = null;
    TlsClientContextImpl tlsClientContext = null;

    public TlsClientProtocol(InputStream inputStream, OutputStream outputStream, SecureRandom secureRandom) {
        super(inputStream, outputStream, secureRandom);
    }

    public TlsClientProtocol(SecureRandom secureRandom) {
        super(secureRandom);
    }

    /* access modifiers changed from: protected */
    public void cleanupHandshake() {
        super.cleanupHandshake();
        this.selectedSessionID = null;
        this.keyExchange = null;
        this.authentication = null;
        this.certificateStatus = null;
        this.certificateRequest = null;
    }

    public void connect(TlsClient tlsClient2) throws IOException {
        SessionParameters exportSessionParameters;
        if (tlsClient2 == null) {
            throw new IllegalArgumentException("'tlsClient' cannot be null");
        } else if (this.tlsClient == null) {
            this.tlsClient = tlsClient2;
            this.securityParameters = new SecurityParameters();
            this.securityParameters.entity = 1;
            this.tlsClientContext = new TlsClientContextImpl(this.secureRandom, this.securityParameters);
            this.securityParameters.clientRandom = createRandomBlock(tlsClient2.shouldUseGMTUnixTime(), this.tlsClientContext.getNonceRandomGenerator());
            this.tlsClient.init(this.tlsClientContext);
            this.recordStream.init(this.tlsClientContext);
            tlsClient2.notifyCloseHandle(this);
            TlsSession sessionToResume = tlsClient2.getSessionToResume();
            if (sessionToResume != null && sessionToResume.isResumable() && (exportSessionParameters = sessionToResume.exportSessionParameters()) != null && exportSessionParameters.isExtendedMasterSecret()) {
                this.tlsSession = sessionToResume;
                this.sessionParameters = exportSessionParameters;
            }
            sendClientHelloMessage();
            this.connection_state = 1;
            blockForHandshake();
        } else {
            throw new IllegalStateException("'connect' can only be called once");
        }
    }

    /* access modifiers changed from: protected */
    public TlsContext getContext() {
        return this.tlsClientContext;
    }

    /* access modifiers changed from: package-private */
    public AbstractTlsContext getContextAdmin() {
        return this.tlsClientContext;
    }

    /* access modifiers changed from: protected */
    public TlsPeer getPeer() {
        return this.tlsClient;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:125:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0096, code lost:
        r5.keyExchange.skipServerCredentials();
        r5.authentication = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x009d, code lost:
        r5.keyExchange.skipServerKeyExchange();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00a2, code lost:
        assertEmpty(r7);
        r5.connection_state = 8;
        r5.recordStream.getHandshakeHash().sealHashAlgorithms();
        r6 = r5.tlsClient.getClientSupplementalData();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00b8, code lost:
        if (r6 == null) goto L_0x00bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00ba, code lost:
        sendSupplementalDataMessage(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00bd, code lost:
        r5.connection_state = 9;
        r6 = r5.certificateRequest;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00c3, code lost:
        if (r6 != null) goto L_0x00cc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00c5, code lost:
        r5.keyExchange.skipClientCredentials();
        r6 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00cc, code lost:
        r6 = r5.authentication.getClientCredentials(r6);
        r7 = r5.keyExchange;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00d4, code lost:
        if (r6 != null) goto L_0x00dc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00d6, code lost:
        r7.skipClientCredentials();
        r7 = org.bouncycastle.crypto.tls.Certificate.EMPTY_CHAIN;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00dc, code lost:
        r7.processClientCredentials(r6);
        r7 = r6.getCertificate();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00e3, code lost:
        sendCertificateMessage(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00e6, code lost:
        r5.connection_state = 10;
        sendClientKeyExchangeMessage();
        r5.connection_state = 11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00f7, code lost:
        if (org.bouncycastle.crypto.tls.TlsUtils.isSSL(getContext()) == false) goto L_0x0102;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00f9, code lost:
        establishMasterSecret(getContext(), r5.keyExchange);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0102, code lost:
        r7 = r5.recordStream.prepareToFinish();
        r5.securityParameters.sessionHash = getCurrentPRFHash(getContext(), r7, (byte[]) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x011c, code lost:
        if (org.bouncycastle.crypto.tls.TlsUtils.isSSL(getContext()) != false) goto L_0x0127;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x011e, code lost:
        establishMasterSecret(getContext(), r5.keyExchange);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0127, code lost:
        r5.recordStream.setPendingConnectionState(getPeer().getCompression(), getPeer().getCipher());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x013c, code lost:
        if (r6 == null) goto L_0x016d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0140, code lost:
        if ((r6 instanceof org.bouncycastle.crypto.tls.TlsSignerCredentials) == false) goto L_0x016d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0142, code lost:
        r6 = (org.bouncycastle.crypto.tls.TlsSignerCredentials) r6;
        r0 = org.bouncycastle.crypto.tls.TlsUtils.getSignatureAndHashAlgorithm(getContext(), r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x014c, code lost:
        if (r0 != null) goto L_0x0155;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x014e, code lost:
        r7 = r5.securityParameters.getSessionHash();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0155, code lost:
        r7 = r7.getFinalHash(r0.getHash());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x015d, code lost:
        sendCertificateVerifyMessage(new org.bouncycastle.crypto.tls.DigitallySigned(r0, r6.generateCertificateSignature(r7)));
        r5.connection_state = 12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x016d, code lost:
        sendChangeCipherSpecMessage();
        sendFinishedMessage();
        r5.connection_state = 13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01c6, code lost:
        r5.keyExchange.skipServerCredentials();
        r5.authentication = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01cd, code lost:
        r5.keyExchange.processServerKeyExchange(r7);
        assertEmpty(r7);
        r6 = 6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleHandshakeMessage(short r6, java.io.ByteArrayInputStream r7) throws java.io.IOException {
        /*
            r5 = this;
            boolean r0 = r5.resumedSession
            r1 = 13
            r2 = 15
            r3 = 2
            r4 = 10
            if (r0 == 0) goto L_0x002a
            r0 = 20
            if (r6 != r0) goto L_0x0024
            short r6 = r5.connection_state
            if (r6 != r3) goto L_0x0024
            r5.processFinishedMessage(r7)
            r5.connection_state = r2
            r5.sendChangeCipherSpecMessage()
            r5.sendFinishedMessage()
            r5.connection_state = r1
            r5.completeHandshake()
            return
        L_0x0024:
            org.bouncycastle.crypto.tls.TlsFatalAlert r6 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r6.<init>(r4)
            throw r6
        L_0x002a:
            r0 = 0
            switch(r6) {
                case 0: goto L_0x0284;
                case 2: goto L_0x0234;
                case 4: goto L_0x0216;
                case 11: goto L_0x01da;
                case 12: goto L_0x01b8;
                case 13: goto L_0x0177;
                case 14: goto L_0x0088;
                case 20: goto L_0x0069;
                case 22: goto L_0x0048;
                case 23: goto L_0x0034;
                default: goto L_0x002e;
            }
        L_0x002e:
            org.bouncycastle.crypto.tls.TlsFatalAlert r6 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r6.<init>(r4)
            throw r6
        L_0x0034:
            short r6 = r5.connection_state
            switch(r6) {
                case 2: goto L_0x003f;
                default: goto L_0x0039;
            }
        L_0x0039:
            org.bouncycastle.crypto.tls.TlsFatalAlert r6 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r6.<init>(r4)
            throw r6
        L_0x003f:
            java.util.Vector r6 = readSupplementalDataMessage(r7)
            r5.handleSupplementalData(r6)
            goto L_0x0290
        L_0x0048:
            short r6 = r5.connection_state
            switch(r6) {
                case 4: goto L_0x0053;
                default: goto L_0x004d;
            }
        L_0x004d:
            org.bouncycastle.crypto.tls.TlsFatalAlert r6 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r6.<init>(r4)
            throw r6
        L_0x0053:
            boolean r6 = r5.allowCertificateStatus
            if (r6 == 0) goto L_0x0063
            org.bouncycastle.crypto.tls.CertificateStatus r6 = org.bouncycastle.crypto.tls.CertificateStatus.parse(r7)
            r5.certificateStatus = r6
            assertEmpty(r7)
            r6 = 5
            goto L_0x01d6
        L_0x0063:
            org.bouncycastle.crypto.tls.TlsFatalAlert r6 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r6.<init>(r4)
            throw r6
        L_0x0069:
            short r6 = r5.connection_state
            switch(r6) {
                case 13: goto L_0x0074;
                case 14: goto L_0x0078;
                default: goto L_0x006e;
            }
        L_0x006e:
            org.bouncycastle.crypto.tls.TlsFatalAlert r6 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r6.<init>(r4)
            throw r6
        L_0x0074:
            boolean r6 = r5.expectSessionTicket
            if (r6 != 0) goto L_0x0082
        L_0x0078:
            r5.processFinishedMessage(r7)
            r5.connection_state = r2
            r5.completeHandshake()
            goto L_0x0290
        L_0x0082:
            org.bouncycastle.crypto.tls.TlsFatalAlert r6 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r6.<init>(r4)
            throw r6
        L_0x0088:
            short r6 = r5.connection_state
            switch(r6) {
                case 2: goto L_0x0093;
                case 3: goto L_0x0096;
                case 4: goto L_0x009d;
                case 5: goto L_0x009d;
                case 6: goto L_0x00a2;
                case 7: goto L_0x00a2;
                default: goto L_0x008d;
            }
        L_0x008d:
            org.bouncycastle.crypto.tls.TlsFatalAlert r6 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r6.<init>(r4)
            throw r6
        L_0x0093:
            r5.handleSupplementalData(r0)
        L_0x0096:
            org.bouncycastle.crypto.tls.TlsKeyExchange r6 = r5.keyExchange
            r6.skipServerCredentials()
            r5.authentication = r0
        L_0x009d:
            org.bouncycastle.crypto.tls.TlsKeyExchange r6 = r5.keyExchange
            r6.skipServerKeyExchange()
        L_0x00a2:
            assertEmpty(r7)
            r6 = 8
            r5.connection_state = r6
            org.bouncycastle.crypto.tls.RecordStream r6 = r5.recordStream
            org.bouncycastle.crypto.tls.TlsHandshakeHash r6 = r6.getHandshakeHash()
            r6.sealHashAlgorithms()
            org.bouncycastle.crypto.tls.TlsClient r6 = r5.tlsClient
            java.util.Vector r6 = r6.getClientSupplementalData()
            if (r6 == 0) goto L_0x00bd
            r5.sendSupplementalDataMessage(r6)
        L_0x00bd:
            r6 = 9
            r5.connection_state = r6
            org.bouncycastle.crypto.tls.CertificateRequest r6 = r5.certificateRequest
            if (r6 != 0) goto L_0x00cc
            org.bouncycastle.crypto.tls.TlsKeyExchange r6 = r5.keyExchange
            r6.skipClientCredentials()
            r6 = r0
            goto L_0x00e6
        L_0x00cc:
            org.bouncycastle.crypto.tls.TlsAuthentication r7 = r5.authentication
            org.bouncycastle.crypto.tls.TlsCredentials r6 = r7.getClientCredentials(r6)
            org.bouncycastle.crypto.tls.TlsKeyExchange r7 = r5.keyExchange
            if (r6 != 0) goto L_0x00dc
            r7.skipClientCredentials()
            org.bouncycastle.crypto.tls.Certificate r7 = org.bouncycastle.crypto.tls.Certificate.EMPTY_CHAIN
            goto L_0x00e3
        L_0x00dc:
            r7.processClientCredentials(r6)
            org.bouncycastle.crypto.tls.Certificate r7 = r6.getCertificate()
        L_0x00e3:
            r5.sendCertificateMessage(r7)
        L_0x00e6:
            r5.connection_state = r4
            r5.sendClientKeyExchangeMessage()
            r7 = 11
            r5.connection_state = r7
            org.bouncycastle.crypto.tls.TlsContext r7 = r5.getContext()
            boolean r7 = org.bouncycastle.crypto.tls.TlsUtils.isSSL(r7)
            if (r7 == 0) goto L_0x0102
            org.bouncycastle.crypto.tls.TlsContext r7 = r5.getContext()
            org.bouncycastle.crypto.tls.TlsKeyExchange r2 = r5.keyExchange
            establishMasterSecret(r7, r2)
        L_0x0102:
            org.bouncycastle.crypto.tls.RecordStream r7 = r5.recordStream
            org.bouncycastle.crypto.tls.TlsHandshakeHash r7 = r7.prepareToFinish()
            org.bouncycastle.crypto.tls.SecurityParameters r2 = r5.securityParameters
            org.bouncycastle.crypto.tls.TlsContext r3 = r5.getContext()
            byte[] r0 = getCurrentPRFHash(r3, r7, r0)
            r2.sessionHash = r0
            org.bouncycastle.crypto.tls.TlsContext r0 = r5.getContext()
            boolean r0 = org.bouncycastle.crypto.tls.TlsUtils.isSSL(r0)
            if (r0 != 0) goto L_0x0127
            org.bouncycastle.crypto.tls.TlsContext r0 = r5.getContext()
            org.bouncycastle.crypto.tls.TlsKeyExchange r2 = r5.keyExchange
            establishMasterSecret(r0, r2)
        L_0x0127:
            org.bouncycastle.crypto.tls.RecordStream r0 = r5.recordStream
            org.bouncycastle.crypto.tls.TlsPeer r2 = r5.getPeer()
            org.bouncycastle.crypto.tls.TlsCompression r2 = r2.getCompression()
            org.bouncycastle.crypto.tls.TlsPeer r3 = r5.getPeer()
            org.bouncycastle.crypto.tls.TlsCipher r3 = r3.getCipher()
            r0.setPendingConnectionState(r2, r3)
            if (r6 == 0) goto L_0x016d
            boolean r0 = r6 instanceof org.bouncycastle.crypto.tls.TlsSignerCredentials
            if (r0 == 0) goto L_0x016d
            org.bouncycastle.crypto.tls.TlsSignerCredentials r6 = (org.bouncycastle.crypto.tls.TlsSignerCredentials) r6
            org.bouncycastle.crypto.tls.TlsContext r0 = r5.getContext()
            org.bouncycastle.crypto.tls.SignatureAndHashAlgorithm r0 = org.bouncycastle.crypto.tls.TlsUtils.getSignatureAndHashAlgorithm(r0, r6)
            if (r0 != 0) goto L_0x0155
            org.bouncycastle.crypto.tls.SecurityParameters r7 = r5.securityParameters
            byte[] r7 = r7.getSessionHash()
            goto L_0x015d
        L_0x0155:
            short r2 = r0.getHash()
            byte[] r7 = r7.getFinalHash(r2)
        L_0x015d:
            byte[] r6 = r6.generateCertificateSignature(r7)
            org.bouncycastle.crypto.tls.DigitallySigned r7 = new org.bouncycastle.crypto.tls.DigitallySigned
            r7.<init>(r0, r6)
            r5.sendCertificateVerifyMessage(r7)
            r6 = 12
            r5.connection_state = r6
        L_0x016d:
            r5.sendChangeCipherSpecMessage()
            r5.sendFinishedMessage()
            r5.connection_state = r1
            goto L_0x0290
        L_0x0177:
            short r6 = r5.connection_state
            switch(r6) {
                case 4: goto L_0x0182;
                case 5: goto L_0x0182;
                case 6: goto L_0x0187;
                default: goto L_0x017c;
            }
        L_0x017c:
            org.bouncycastle.crypto.tls.TlsFatalAlert r6 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r6.<init>(r4)
            throw r6
        L_0x0182:
            org.bouncycastle.crypto.tls.TlsKeyExchange r6 = r5.keyExchange
            r6.skipServerKeyExchange()
        L_0x0187:
            org.bouncycastle.crypto.tls.TlsAuthentication r6 = r5.authentication
            if (r6 == 0) goto L_0x01b0
            org.bouncycastle.crypto.tls.TlsContext r6 = r5.getContext()
            org.bouncycastle.crypto.tls.CertificateRequest r6 = org.bouncycastle.crypto.tls.CertificateRequest.parse(r6, r7)
            r5.certificateRequest = r6
            assertEmpty(r7)
            org.bouncycastle.crypto.tls.TlsKeyExchange r6 = r5.keyExchange
            org.bouncycastle.crypto.tls.CertificateRequest r7 = r5.certificateRequest
            r6.validateCertificateRequest(r7)
            org.bouncycastle.crypto.tls.RecordStream r6 = r5.recordStream
            org.bouncycastle.crypto.tls.TlsHandshakeHash r6 = r6.getHandshakeHash()
            org.bouncycastle.crypto.tls.CertificateRequest r7 = r5.certificateRequest
            java.util.Vector r7 = r7.getSupportedSignatureAlgorithms()
            org.bouncycastle.crypto.tls.TlsUtils.trackHashAlgorithms(r6, r7)
            r6 = 7
            goto L_0x01d6
        L_0x01b0:
            org.bouncycastle.crypto.tls.TlsFatalAlert r6 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r7 = 40
            r6.<init>(r7)
            throw r6
        L_0x01b8:
            short r6 = r5.connection_state
            switch(r6) {
                case 2: goto L_0x01c3;
                case 3: goto L_0x01c6;
                case 4: goto L_0x01cd;
                case 5: goto L_0x01cd;
                default: goto L_0x01bd;
            }
        L_0x01bd:
            org.bouncycastle.crypto.tls.TlsFatalAlert r6 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r6.<init>(r4)
            throw r6
        L_0x01c3:
            r5.handleSupplementalData(r0)
        L_0x01c6:
            org.bouncycastle.crypto.tls.TlsKeyExchange r6 = r5.keyExchange
            r6.skipServerCredentials()
            r5.authentication = r0
        L_0x01cd:
            org.bouncycastle.crypto.tls.TlsKeyExchange r6 = r5.keyExchange
            r6.processServerKeyExchange(r7)
            assertEmpty(r7)
            r6 = 6
        L_0x01d6:
            r5.connection_state = r6
            goto L_0x0290
        L_0x01da:
            short r6 = r5.connection_state
            switch(r6) {
                case 2: goto L_0x01e5;
                case 3: goto L_0x01e8;
                default: goto L_0x01df;
            }
        L_0x01df:
            org.bouncycastle.crypto.tls.TlsFatalAlert r6 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r6.<init>(r4)
            throw r6
        L_0x01e5:
            r5.handleSupplementalData(r0)
        L_0x01e8:
            org.bouncycastle.crypto.tls.Certificate r6 = org.bouncycastle.crypto.tls.Certificate.parse(r7)
            r5.peerCertificate = r6
            assertEmpty(r7)
            org.bouncycastle.crypto.tls.Certificate r6 = r5.peerCertificate
            if (r6 == 0) goto L_0x01fd
            org.bouncycastle.crypto.tls.Certificate r6 = r5.peerCertificate
            boolean r6 = r6.isEmpty()
            if (r6 == 0) goto L_0x0200
        L_0x01fd:
            r6 = 0
            r5.allowCertificateStatus = r6
        L_0x0200:
            org.bouncycastle.crypto.tls.TlsKeyExchange r6 = r5.keyExchange
            org.bouncycastle.crypto.tls.Certificate r7 = r5.peerCertificate
            r6.processServerCertificate(r7)
            org.bouncycastle.crypto.tls.TlsClient r6 = r5.tlsClient
            org.bouncycastle.crypto.tls.TlsAuthentication r6 = r6.getAuthentication()
            r5.authentication = r6
            org.bouncycastle.crypto.tls.Certificate r7 = r5.peerCertificate
            r6.notifyServerCertificate(r7)
            r6 = 4
            goto L_0x01d6
        L_0x0216:
            short r6 = r5.connection_state
            switch(r6) {
                case 13: goto L_0x0221;
                default: goto L_0x021b;
            }
        L_0x021b:
            org.bouncycastle.crypto.tls.TlsFatalAlert r6 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r6.<init>(r4)
            throw r6
        L_0x0221:
            boolean r6 = r5.expectSessionTicket
            if (r6 == 0) goto L_0x022e
            r5.invalidateSession()
            r5.receiveNewSessionTicketMessage(r7)
            r6 = 14
            goto L_0x01d6
        L_0x022e:
            org.bouncycastle.crypto.tls.TlsFatalAlert r6 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r6.<init>(r4)
            throw r6
        L_0x0234:
            short r6 = r5.connection_state
            switch(r6) {
                case 1: goto L_0x023f;
                default: goto L_0x0239;
            }
        L_0x0239:
            org.bouncycastle.crypto.tls.TlsFatalAlert r6 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r6.<init>(r4)
            throw r6
        L_0x023f:
            r5.receiveServerHelloMessage(r7)
            r5.connection_state = r3
            org.bouncycastle.crypto.tls.RecordStream r6 = r5.recordStream
            r6.notifyHelloComplete()
            r5.applyMaxFragmentLengthExtension()
            boolean r6 = r5.resumedSession
            if (r6 == 0) goto L_0x0274
            org.bouncycastle.crypto.tls.SecurityParameters r6 = r5.securityParameters
            org.bouncycastle.crypto.tls.SessionParameters r7 = r5.sessionParameters
            byte[] r7 = r7.getMasterSecret()
            byte[] r7 = org.bouncycastle.util.Arrays.clone((byte[]) r7)
            r6.masterSecret = r7
            org.bouncycastle.crypto.tls.RecordStream r6 = r5.recordStream
            org.bouncycastle.crypto.tls.TlsPeer r7 = r5.getPeer()
            org.bouncycastle.crypto.tls.TlsCompression r7 = r7.getCompression()
            org.bouncycastle.crypto.tls.TlsPeer r0 = r5.getPeer()
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.getCipher()
            r6.setPendingConnectionState(r7, r0)
            goto L_0x0290
        L_0x0274:
            r5.invalidateSession()
            byte[] r6 = r5.selectedSessionID
            int r7 = r6.length
            if (r7 <= 0) goto L_0x0290
            org.bouncycastle.crypto.tls.TlsSessionImpl r7 = new org.bouncycastle.crypto.tls.TlsSessionImpl
            r7.<init>(r6, r0)
            r5.tlsSession = r7
            goto L_0x0290
        L_0x0284:
            assertEmpty(r7)
            short r6 = r5.connection_state
            r7 = 16
            if (r6 != r7) goto L_0x0290
            r5.refuseRenegotiation()
        L_0x0290:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.tls.TlsClientProtocol.handleHandshakeMessage(short, java.io.ByteArrayInputStream):void");
    }

    /* access modifiers changed from: protected */
    public void handleSupplementalData(Vector vector) throws IOException {
        this.tlsClient.processServerSupplementalData(vector);
        this.connection_state = 3;
        TlsKeyExchange keyExchange2 = this.tlsClient.getKeyExchange();
        this.keyExchange = keyExchange2;
        keyExchange2.init(getContext());
    }

    /* access modifiers changed from: protected */
    public void receiveNewSessionTicketMessage(ByteArrayInputStream byteArrayInputStream) throws IOException {
        NewSessionTicket parse = NewSessionTicket.parse(byteArrayInputStream);
        assertEmpty(byteArrayInputStream);
        this.tlsClient.notifyNewSessionTicket(parse);
    }

    /* access modifiers changed from: protected */
    public void receiveServerHelloMessage(ByteArrayInputStream byteArrayInputStream) throws IOException {
        ProtocolVersion readVersion = TlsUtils.readVersion(byteArrayInputStream);
        if (readVersion.isDTLS()) {
            throw new TlsFatalAlert(47);
        } else if (!readVersion.equals(this.recordStream.getReadVersion())) {
            throw new TlsFatalAlert(47);
        } else if (readVersion.isEqualOrEarlierVersionOf(getContext().getClientVersion())) {
            this.recordStream.setWriteVersion(readVersion);
            getContextAdmin().setServerVersion(readVersion);
            this.tlsClient.notifyServerVersion(readVersion);
            this.securityParameters.serverRandom = TlsUtils.readFully(32, (InputStream) byteArrayInputStream);
            byte[] readOpaque8 = TlsUtils.readOpaque8(byteArrayInputStream);
            this.selectedSessionID = readOpaque8;
            if (readOpaque8.length <= 32) {
                this.tlsClient.notifySessionID(readOpaque8);
                boolean z = false;
                this.resumedSession = this.selectedSessionID.length > 0 && this.tlsSession != null && Arrays.areEqual(this.selectedSessionID, this.tlsSession.getSessionID());
                int readUint16 = TlsUtils.readUint16(byteArrayInputStream);
                if (!Arrays.contains(this.offeredCipherSuites, readUint16) || readUint16 == 0 || CipherSuite.isSCSV(readUint16) || !TlsUtils.isValidCipherSuiteForVersion(readUint16, getContext().getServerVersion())) {
                    throw new TlsFatalAlert(47);
                }
                this.tlsClient.notifySelectedCipherSuite(readUint16);
                short readUint8 = TlsUtils.readUint8(byteArrayInputStream);
                if (Arrays.contains(this.offeredCompressionMethods, readUint8)) {
                    this.tlsClient.notifySelectedCompressionMethod(readUint8);
                    this.serverExtensions = readExtensions(byteArrayInputStream);
                    this.securityParameters.extendedMasterSecret = !TlsUtils.isSSL(this.tlsClientContext) && TlsExtensionsUtils.hasExtendedMasterSecretExtension(this.serverExtensions);
                    if (this.securityParameters.isExtendedMasterSecret() || (!this.resumedSession && !this.tlsClient.requiresExtendedMasterSecret())) {
                        if (this.serverExtensions != null) {
                            Enumeration keys = this.serverExtensions.keys();
                            while (keys.hasMoreElements()) {
                                Integer num = (Integer) keys.nextElement();
                                if (!num.equals(EXT_RenegotiationInfo)) {
                                    if (TlsUtils.getExtensionData(this.clientExtensions, num) != null) {
                                        boolean z2 = this.resumedSession;
                                    } else {
                                        throw new TlsFatalAlert(AlertDescription.unsupported_extension);
                                    }
                                }
                            }
                        }
                        byte[] extensionData = TlsUtils.getExtensionData(this.serverExtensions, EXT_RenegotiationInfo);
                        if (extensionData != null) {
                            this.secure_renegotiation = true;
                            if (!Arrays.constantTimeAreEqual(extensionData, createRenegotiationInfo(TlsUtils.EMPTY_BYTES))) {
                                throw new TlsFatalAlert(40);
                            }
                        }
                        this.tlsClient.notifySecureRenegotiation(this.secure_renegotiation);
                        Hashtable hashtable = this.clientExtensions;
                        Hashtable hashtable2 = this.serverExtensions;
                        if (this.resumedSession) {
                            if (readUint16 == this.sessionParameters.getCipherSuite() && readUint8 == this.sessionParameters.getCompressionAlgorithm()) {
                                hashtable2 = this.sessionParameters.readServerExtensions();
                                hashtable = null;
                            } else {
                                throw new TlsFatalAlert(47);
                            }
                        }
                        this.securityParameters.cipherSuite = readUint16;
                        this.securityParameters.compressionAlgorithm = readUint8;
                        if (hashtable2 != null && !hashtable2.isEmpty()) {
                            boolean hasEncryptThenMACExtension = TlsExtensionsUtils.hasEncryptThenMACExtension(hashtable2);
                            if (!hasEncryptThenMACExtension || TlsUtils.isBlockCipherSuite(readUint16)) {
                                this.securityParameters.encryptThenMAC = hasEncryptThenMACExtension;
                                this.securityParameters.maxFragmentLength = processMaxFragmentLengthExtension(hashtable, hashtable2, 47);
                                this.securityParameters.truncatedHMac = TlsExtensionsUtils.hasTruncatedHMacExtension(hashtable2);
                                this.allowCertificateStatus = !this.resumedSession && TlsUtils.hasExpectedEmptyExtensionData(hashtable2, TlsExtensionsUtils.EXT_status_request, 47);
                                if (!this.resumedSession && TlsUtils.hasExpectedEmptyExtensionData(hashtable2, TlsProtocol.EXT_SessionTicket, 47)) {
                                    z = true;
                                }
                                this.expectSessionTicket = z;
                            } else {
                                throw new TlsFatalAlert(47);
                            }
                        }
                        if (hashtable != null) {
                            this.tlsClient.processServerExtensions(hashtable2);
                        }
                        this.securityParameters.prfAlgorithm = getPRFAlgorithm(getContext(), this.securityParameters.getCipherSuite());
                        this.securityParameters.verifyDataLength = 12;
                        return;
                    }
                    throw new TlsFatalAlert(40);
                }
                throw new TlsFatalAlert(47);
            }
            throw new TlsFatalAlert(47);
        } else {
            throw new TlsFatalAlert(47);
        }
    }

    /* access modifiers changed from: protected */
    public void sendCertificateVerifyMessage(DigitallySigned digitallySigned) throws IOException {
        TlsProtocol.HandshakeMessage handshakeMessage = new TlsProtocol.HandshakeMessage(this, 15);
        digitallySigned.encode(handshakeMessage);
        handshakeMessage.writeToRecordStream();
    }

    /* access modifiers changed from: protected */
    public void sendClientHelloMessage() throws IOException {
        this.recordStream.setWriteVersion(this.tlsClient.getClientHelloRecordLayerVersion());
        ProtocolVersion clientVersion = this.tlsClient.getClientVersion();
        if (!clientVersion.isDTLS()) {
            getContextAdmin().setClientVersion(clientVersion);
            byte[] bArr = TlsUtils.EMPTY_BYTES;
            if (this.tlsSession != null && ((bArr = this.tlsSession.getSessionID()) == null || bArr.length > 32)) {
                bArr = TlsUtils.EMPTY_BYTES;
            }
            boolean isFallback = this.tlsClient.isFallback();
            this.offeredCipherSuites = this.tlsClient.getCipherSuites();
            this.offeredCompressionMethods = this.tlsClient.getCompressionMethods();
            if (bArr.length > 0 && this.sessionParameters != null && (!this.sessionParameters.isExtendedMasterSecret() || !Arrays.contains(this.offeredCipherSuites, this.sessionParameters.getCipherSuite()) || !Arrays.contains(this.offeredCompressionMethods, this.sessionParameters.getCompressionAlgorithm()))) {
                bArr = TlsUtils.EMPTY_BYTES;
            }
            this.clientExtensions = TlsExtensionsUtils.ensureExtensionsInitialised(this.tlsClient.getClientExtensions());
            if (!clientVersion.isSSL()) {
                TlsExtensionsUtils.addExtendedMasterSecretExtension(this.clientExtensions);
            }
            TlsProtocol.HandshakeMessage handshakeMessage = new TlsProtocol.HandshakeMessage(this, 1);
            TlsUtils.writeVersion(clientVersion, handshakeMessage);
            handshakeMessage.write(this.securityParameters.getClientRandom());
            TlsUtils.writeOpaque8(bArr, handshakeMessage);
            boolean z = TlsUtils.getExtensionData(this.clientExtensions, EXT_RenegotiationInfo) == null;
            boolean z2 = !Arrays.contains(this.offeredCipherSuites, 255);
            if (z && z2) {
                this.offeredCipherSuites = Arrays.append(this.offeredCipherSuites, 255);
            }
            if (isFallback && !Arrays.contains(this.offeredCipherSuites, (int) CipherSuite.TLS_FALLBACK_SCSV)) {
                this.offeredCipherSuites = Arrays.append(this.offeredCipherSuites, (int) CipherSuite.TLS_FALLBACK_SCSV);
            }
            TlsUtils.writeUint16ArrayWithUint16Length(this.offeredCipherSuites, handshakeMessage);
            TlsUtils.writeUint8ArrayWithUint8Length(this.offeredCompressionMethods, handshakeMessage);
            writeExtensions(handshakeMessage, this.clientExtensions);
            handshakeMessage.writeToRecordStream();
            return;
        }
        throw new TlsFatalAlert(80);
    }

    /* access modifiers changed from: protected */
    public void sendClientKeyExchangeMessage() throws IOException {
        TlsProtocol.HandshakeMessage handshakeMessage = new TlsProtocol.HandshakeMessage(this, 16);
        this.keyExchange.generateClientKeyExchange(handshakeMessage);
        handshakeMessage.writeToRecordStream();
    }
}
