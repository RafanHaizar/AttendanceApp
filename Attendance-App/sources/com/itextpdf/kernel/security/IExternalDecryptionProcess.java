package com.itextpdf.kernel.security;

import java.io.Serializable;
import org.bouncycastle.cms.Recipient;
import org.bouncycastle.cms.RecipientId;

public interface IExternalDecryptionProcess extends Serializable {
    Recipient getCmsRecipient();

    RecipientId getCmsRecipientId();
}
