package com.itextpdf.p026io.util;

import java.io.Serializable;
import java.util.Arrays;
import org.bouncycastle.crypto.tls.CipherSuite;

/* renamed from: com.itextpdf.io.util.IntHashtable */
public class IntHashtable implements Cloneable, Serializable {
    private static final long serialVersionUID = 7354463962269093965L;
    private int count;
    private float loadFactor;
    private Entry[] table;
    private int threshold;

    public IntHashtable() {
        this(CipherSuite.TLS_RSA_WITH_SEED_CBC_SHA, 0.75f);
    }

    public IntHashtable(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    public IntHashtable(int initialCapacity, float loadFactor2) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException(MessageFormatUtil.format("Illegal Capacity: {0}", Integer.valueOf(initialCapacity)));
        } else if (loadFactor2 > 0.0f) {
            initialCapacity = initialCapacity == 0 ? 1 : initialCapacity;
            this.loadFactor = loadFactor2;
            this.table = new Entry[initialCapacity];
            this.threshold = (int) (((float) initialCapacity) * loadFactor2);
        } else {
            throw new IllegalArgumentException(MessageFormatUtil.format("Illegal Load: {0}", Float.valueOf(loadFactor2)));
        }
    }

    public IntHashtable(IntHashtable o) {
        this(o.table.length, o.loadFactor);
    }

    public int size() {
        return this.count;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    public boolean contains(int value) {
        Entry[] tab = this.table;
        int i = tab.length;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return false;
            }
            for (Entry e = tab[i2]; e != null; e = e.next) {
                if (e.value == value) {
                    return true;
                }
            }
            i = i2;
        }
    }

    public boolean containsValue(int value) {
        return contains(value);
    }

    public boolean containsKey(int key) {
        Entry[] tab = this.table;
        for (Entry e = tab[(Integer.MAX_VALUE & key) % tab.length]; e != null; e = e.next) {
            if (e.key == key) {
                return true;
            }
        }
        return false;
    }

    public int get(int key) {
        Entry[] tab = this.table;
        for (Entry e = tab[(Integer.MAX_VALUE & key) % tab.length]; e != null; e = e.next) {
            if (e.key == key) {
                return e.value;
            }
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public void rehash() {
        int oldCapacity = this.table.length;
        Entry[] oldMap = this.table;
        int newCapacity = (oldCapacity * 2) + 1;
        Entry[] newMap = new Entry[newCapacity];
        this.threshold = (int) (((float) newCapacity) * this.loadFactor);
        this.table = newMap;
        int i = oldCapacity;
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                Entry old = oldMap[i2];
                while (old != null) {
                    Entry e = old;
                    old = old.next;
                    int index = (e.key & Integer.MAX_VALUE) % newCapacity;
                    e.next = newMap[index];
                    newMap[index] = e;
                }
                i = i2;
            } else {
                return;
            }
        }
    }

    public int put(int key, int value) {
        Entry[] tab = this.table;
        int index = (key & Integer.MAX_VALUE) % tab.length;
        for (Entry e = tab[index]; e != null; e = e.next) {
            if (e.key == key) {
                int old = e.value;
                e.value = value;
                return old;
            }
        }
        if (this.count >= this.threshold) {
            rehash();
            tab = this.table;
            index = (Integer.MAX_VALUE & key) % tab.length;
        }
        tab[index] = new Entry(key, value, tab[index]);
        this.count++;
        return 0;
    }

    public int remove(int key) {
        Entry[] tab = this.table;
        int index = (Integer.MAX_VALUE & key) % tab.length;
        Entry prev = null;
        for (Entry e = tab[index]; e != null; e = e.next) {
            if (e.key == key) {
                if (prev != null) {
                    prev.next = e.next;
                } else {
                    tab[index] = e.next;
                }
                this.count--;
                int oldValue = e.value;
                e.value = 0;
                return oldValue;
            }
            prev = e;
        }
        return 0;
    }

    public void clear() {
        Entry[] tab = this.table;
        int index = tab.length;
        while (true) {
            index--;
            if (index >= 0) {
                tab[index] = null;
            } else {
                this.count = 0;
                return;
            }
        }
    }

    /* renamed from: com.itextpdf.io.util.IntHashtable$Entry */
    public static class Entry implements Serializable {
        private static final long serialVersionUID = 8057670534065316193L;
        int key;
        Entry next;
        int value;

        Entry(int key2, int value2, Entry next2) {
            this.key = key2;
            this.value = value2;
            this.next = next2;
        }

        public int getKey() {
            return this.key;
        }

        public int getValue() {
            return this.value;
        }

        /* access modifiers changed from: protected */
        public Object clone() throws CloneNotSupportedException {
            int i = this.key;
            int i2 = this.value;
            Entry entry = this.next;
            return new Entry(i, i2, entry != null ? (Entry) entry.clone() : null);
        }

        public String toString() {
            return MessageFormatUtil.format("{0}={1}", Integer.valueOf(this.key), Integer.valueOf(this.value));
        }
    }

    public int[] toOrderedKeys() {
        int[] res = getKeys();
        Arrays.sort(res);
        return res;
    }

    public int[] getKeys() {
        int index;
        int[] res = new int[this.count];
        int ptr = 0;
        int index2 = this.table.length;
        Entry entry = null;
        while (true) {
            if (entry == null) {
                while (true) {
                    index = index2 - 1;
                    if (index2 <= 0) {
                        break;
                    }
                    Entry entry2 = this.table[index];
                    entry = entry2;
                    if (entry2 != null) {
                        break;
                    }
                    index2 = index;
                }
                index2 = index;
            }
            if (entry == null) {
                return res;
            }
            Entry e = entry;
            entry = e.next;
            res[ptr] = e.key;
            ptr++;
        }
    }

    public int getOneKey() {
        if (this.count == 0) {
            return 0;
        }
        int index = this.table.length;
        Entry entry = null;
        while (true) {
            int index2 = index - 1;
            if (index <= 0) {
                break;
            }
            Entry entry2 = this.table[index2];
            entry = entry2;
            if (entry2 != null) {
                break;
            }
            index = index2;
        }
        if (entry == null) {
            return 0;
        }
        return entry.key;
    }

    public Object clone() throws CloneNotSupportedException {
        try {
            IntHashtable t = new IntHashtable(this);
            t.table = new Entry[this.table.length];
            int i = this.table.length;
            while (true) {
                int i2 = i - 1;
                if (i <= 0) {
                    return t;
                }
                Entry[] entryArr = t.table;
                Entry entry = this.table[i2];
                entryArr[i2] = entry != null ? (Entry) entry.clone() : null;
                i = i2;
            }
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }
}
