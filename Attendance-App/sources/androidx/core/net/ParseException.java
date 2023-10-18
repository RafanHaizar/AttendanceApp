package androidx.core.net;

public class ParseException extends RuntimeException {
    public final String response;

    ParseException(String response2) {
        super(response2);
        this.response = response2;
    }
}
