package org.bouncycastle.cms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.util.Iterable;

public class RecipientInformationStore implements Iterable<RecipientInformation> {
    private final List all;
    private final Map table;

    public RecipientInformationStore(Collection<RecipientInformation> collection) {
        this.table = new HashMap();
        for (RecipientInformation next : collection) {
            RecipientId rid = next.getRID();
            ArrayList arrayList = (ArrayList) this.table.get(rid);
            if (arrayList == null) {
                arrayList = new ArrayList(1);
                this.table.put(rid, arrayList);
            }
            arrayList.add(next);
        }
        this.all = new ArrayList(collection);
    }

    public RecipientInformationStore(RecipientInformation recipientInformation) {
        HashMap hashMap = new HashMap();
        this.table = hashMap;
        ArrayList arrayList = new ArrayList(1);
        this.all = arrayList;
        arrayList.add(recipientInformation);
        hashMap.put(recipientInformation.getRID(), arrayList);
    }

    public RecipientInformation get(RecipientId recipientId) {
        Collection<RecipientInformation> recipients = getRecipients(recipientId);
        if (recipients.size() == 0) {
            return null;
        }
        return recipients.iterator().next();
    }

    public Collection<RecipientInformation> getRecipients() {
        return new ArrayList(this.all);
    }

    public Collection<RecipientInformation> getRecipients(RecipientId recipientId) {
        if (recipientId instanceof KeyTransRecipientId) {
            KeyTransRecipientId keyTransRecipientId = (KeyTransRecipientId) recipientId;
            X500Name issuer = keyTransRecipientId.getIssuer();
            byte[] subjectKeyIdentifier = keyTransRecipientId.getSubjectKeyIdentifier();
            if (!(issuer == null || subjectKeyIdentifier == null)) {
                ArrayList arrayList = new ArrayList();
                Collection<RecipientInformation> recipients = getRecipients(new KeyTransRecipientId(issuer, keyTransRecipientId.getSerialNumber()));
                if (recipients != null) {
                    arrayList.addAll(recipients);
                }
                Collection<RecipientInformation> recipients2 = getRecipients(new KeyTransRecipientId(subjectKeyIdentifier));
                if (recipients2 != null) {
                    arrayList.addAll(recipients2);
                }
                return arrayList;
            }
        }
        ArrayList arrayList2 = (ArrayList) this.table.get(recipientId);
        return arrayList2 == null ? new ArrayList() : new ArrayList(arrayList2);
    }

    public Iterator<RecipientInformation> iterator() {
        return getRecipients().iterator();
    }

    public int size() {
        return this.all.size();
    }
}
