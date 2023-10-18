package org.bouncycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import org.bouncycastle.util.Arrays;

public class ServerSRPParams {

    /* renamed from: B */
    protected BigInteger f635B;

    /* renamed from: N */
    protected BigInteger f636N;

    /* renamed from: g */
    protected BigInteger f637g;

    /* renamed from: s */
    protected byte[] f638s;

    public ServerSRPParams(BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr, BigInteger bigInteger3) {
        this.f636N = bigInteger;
        this.f637g = bigInteger2;
        this.f638s = Arrays.clone(bArr);
        this.f635B = bigInteger3;
    }

    public static ServerSRPParams parse(InputStream inputStream) throws IOException {
        return new ServerSRPParams(TlsSRPUtils.readSRPParameter(inputStream), TlsSRPUtils.readSRPParameter(inputStream), TlsUtils.readOpaque8(inputStream), TlsSRPUtils.readSRPParameter(inputStream));
    }

    public void encode(OutputStream outputStream) throws IOException {
        TlsSRPUtils.writeSRPParameter(this.f636N, outputStream);
        TlsSRPUtils.writeSRPParameter(this.f637g, outputStream);
        TlsUtils.writeOpaque8(this.f638s, outputStream);
        TlsSRPUtils.writeSRPParameter(this.f635B, outputStream);
    }

    public BigInteger getB() {
        return this.f635B;
    }

    public BigInteger getG() {
        return this.f637g;
    }

    public BigInteger getN() {
        return this.f636N;
    }

    public byte[] getS() {
        return this.f638s;
    }
}
