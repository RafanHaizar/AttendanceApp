package androidx.core.net;

import android.net.Uri;
import androidx.core.util.Preconditions;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.text.Typography;

public final class MailTo {
    private static final String BCC = "bcc";
    private static final String BODY = "body";

    /* renamed from: CC */
    private static final String f1038CC = "cc";
    private static final String MAILTO = "mailto";
    public static final String MAILTO_SCHEME = "mailto:";
    private static final String SUBJECT = "subject";

    /* renamed from: TO */
    private static final String f1039TO = "to";
    private HashMap<String, String> mHeaders = new HashMap<>();

    private MailTo() {
    }

    public static boolean isMailTo(String uri) {
        return uri != null && uri.startsWith(MAILTO_SCHEME);
    }

    public static boolean isMailTo(Uri uri) {
        return uri != null && MAILTO.equals(uri.getScheme());
    }

    public static MailTo parse(String uri) throws ParseException {
        String query;
        String address;
        Preconditions.checkNotNull(uri);
        if (isMailTo(uri)) {
            int fragmentIndex = uri.indexOf(35);
            if (fragmentIndex != -1) {
                uri = uri.substring(0, fragmentIndex);
            }
            int queryIndex = uri.indexOf(63);
            if (queryIndex == -1) {
                address = Uri.decode(uri.substring(MAILTO_SCHEME.length()));
                query = null;
            } else {
                address = Uri.decode(uri.substring(MAILTO_SCHEME.length(), queryIndex));
                query = uri.substring(queryIndex + 1);
            }
            MailTo mailTo = new MailTo();
            if (query != null) {
                for (String queryParameter : query.split("&")) {
                    String[] nameValueArray = queryParameter.split("=", 2);
                    if (nameValueArray.length != 0) {
                        mailTo.mHeaders.put(Uri.decode(nameValueArray[0]).toLowerCase(Locale.ROOT), nameValueArray.length > 1 ? Uri.decode(nameValueArray[1]) : null);
                    }
                }
            }
            String toParameter = mailTo.getTo();
            if (toParameter != null) {
                address = address + ", " + toParameter;
            }
            mailTo.mHeaders.put("to", address);
            return mailTo;
        }
        throw new ParseException("Not a mailto scheme");
    }

    public static MailTo parse(Uri uri) throws ParseException {
        return parse(uri.toString());
    }

    public String getTo() {
        return this.mHeaders.get("to");
    }

    public String getCc() {
        return this.mHeaders.get(f1038CC);
    }

    public String getBcc() {
        return this.mHeaders.get(BCC);
    }

    public String getSubject() {
        return this.mHeaders.get("subject");
    }

    public String getBody() {
        return this.mHeaders.get(BODY);
    }

    public Map<String, String> getHeaders() {
        return this.mHeaders;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(MAILTO_SCHEME);
        sb.append('?');
        for (Map.Entry<String, String> header : this.mHeaders.entrySet()) {
            sb.append(Uri.encode(header.getKey()));
            sb.append('=');
            sb.append(Uri.encode(header.getValue()));
            sb.append(Typography.amp);
        }
        return sb.toString();
    }
}
