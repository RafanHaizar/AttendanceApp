package com.itextpdf.kernel.xmp.options;

import com.itextpdf.kernel.xmp.XMPException;
import java.util.HashMap;
import java.util.Map;

public abstract class Options {
    private Map optionNames = null;
    private int options = 0;

    /* access modifiers changed from: protected */
    public abstract String defineOptionName(int i);

    /* access modifiers changed from: protected */
    public abstract int getValidOptions();

    public Options() {
    }

    public Options(int options2) throws XMPException {
        assertOptionsValid(options2);
        setOptions(options2);
    }

    public void clear() {
        this.options = 0;
    }

    public boolean isExactly(int optionBits) {
        return getOptions() == optionBits;
    }

    public boolean containsAllOptions(int optionBits) {
        return (getOptions() & optionBits) == optionBits;
    }

    public boolean containsOneOf(int optionBits) {
        return (getOptions() & optionBits) != 0;
    }

    /* access modifiers changed from: protected */
    public boolean getOption(int optionBit) {
        return (this.options & optionBit) != 0;
    }

    public void setOption(int optionBits, boolean value) {
        int i = this.options;
        this.options = value ? i | optionBits : i & (optionBits ^ -1);
    }

    public int getOptions() {
        return this.options;
    }

    public void setOptions(int options2) throws XMPException {
        assertOptionsValid(options2);
        this.options = options2;
    }

    public boolean equals(Object obj) {
        return getOptions() == ((Options) obj).getOptions();
    }

    public int hashCode() {
        return getOptions();
    }

    public String getOptionsString() {
        if (this.options == 0) {
            return "<none>";
        }
        StringBuffer sb = new StringBuffer();
        int theBits = this.options;
        while (theBits != 0) {
            int oneLessBit = (theBits - 1) & theBits;
            sb.append(getOptionName(theBits ^ oneLessBit));
            if (oneLessBit != 0) {
                sb.append(" | ");
            }
            theBits = oneLessBit;
        }
        return sb.toString();
    }

    public String toString() {
        return "0x" + Integer.toHexString(this.options);
    }

    /* access modifiers changed from: protected */
    public void assertConsistency(int options2) throws XMPException {
    }

    private void assertOptionsValid(int options2) throws XMPException {
        int invalidOptions = (getValidOptions() ^ -1) & options2;
        if (invalidOptions == 0) {
            assertConsistency(options2);
            return;
        }
        throw new XMPException("The option bit(s) 0x" + Integer.toHexString(invalidOptions) + " are invalid!", 103);
    }

    private String getOptionName(int option) {
        HashMap optionsNames = procureOptionNames();
        Integer key = new Integer(option);
        if (!optionsNames.containsKey(key)) {
            return null;
        }
        String result = defineOptionName(option);
        if (result == null) {
            return "<option name not defined>";
        }
        optionsNames.put(key, result);
        return result;
    }

    private HashMap procureOptionNames() {
        if (this.optionNames == null) {
            this.optionNames = new HashMap();
        }
        return (HashMap) this.optionNames;
    }
}
