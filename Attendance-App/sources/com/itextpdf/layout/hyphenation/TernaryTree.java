package com.itextpdf.layout.hyphenation;

import java.io.Serializable;
import java.util.Enumeration;
import kotlin.jvm.internal.CharCompanionObject;

public class TernaryTree implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected static final int BLOCK_SIZE = 2048;
    private static final long serialVersionUID = 3175412271203716160L;

    /* renamed from: eq */
    protected char[] f1527eq;
    protected char freenode;

    /* renamed from: hi */
    protected char[] f1528hi;

    /* renamed from: kv */
    protected CharVector f1529kv;
    protected int length;

    /* renamed from: lo */
    protected char[] f1530lo;
    protected char root;

    /* renamed from: sc */
    protected char[] f1531sc;

    TernaryTree() {
        init();
    }

    TernaryTree(TernaryTree tt) {
        this.root = tt.root;
        this.freenode = tt.freenode;
        this.length = tt.length;
        this.f1530lo = (char[]) tt.f1530lo.clone();
        this.f1528hi = (char[]) tt.f1528hi.clone();
        this.f1527eq = (char[]) tt.f1527eq.clone();
        this.f1531sc = (char[]) tt.f1531sc.clone();
        this.f1529kv = new CharVector(tt.f1529kv);
    }

    /* access modifiers changed from: protected */
    public void init() {
        this.root = 0;
        this.freenode = 1;
        this.length = 0;
        this.f1530lo = new char[2048];
        this.f1528hi = new char[2048];
        this.f1527eq = new char[2048];
        this.f1531sc = new char[2048];
        this.f1529kv = new CharVector();
    }

    public void insert(String key, char val) {
        int len = key.length() + 1;
        int i = this.freenode + len;
        char[] cArr = this.f1527eq;
        if (i > cArr.length) {
            redimNodeArrays(cArr.length + 2048);
        }
        int len2 = len - 1;
        char[] strkey = new char[len];
        key.getChars(0, len2, strkey, 0);
        strkey[len2] = 0;
        this.root = insert(new TreeInsertionParams(this.root, strkey, 0, val));
    }

    public void insert(char[] key, int start, char val) {
        int strlen = this.freenode + strlen(key) + 1;
        char[] cArr = this.f1527eq;
        if (strlen > cArr.length) {
            redimNodeArrays(cArr.length + 2048);
        }
        this.root = insert(new TreeInsertionParams(this.root, key, start, val));
    }

    private Character insertNewBranchIfNeeded(TreeInsertionParams params) {
        char p = params.f1532p;
        char[] key = params.key;
        int start = params.start;
        char val = params.val;
        int len = strlen(key, start);
        if (p != 0) {
            return null;
        }
        char c = this.freenode;
        this.freenode = (char) (c + 1);
        char p2 = c;
        this.f1527eq[p2] = val;
        this.length++;
        this.f1528hi[p2] = 0;
        if (len > 0) {
            this.f1531sc[p2] = CharCompanionObject.MAX_VALUE;
            this.f1530lo[p2] = (char) this.f1529kv.alloc(len + 1);
            strcpy(this.f1529kv.getArray(), this.f1530lo[p2], key, start);
        } else {
            this.f1531sc[p2] = 0;
            this.f1530lo[p2] = 0;
        }
        return Character.valueOf(p2);
    }

    private char insertIntoExistingBranch(TreeInsertionParams params) {
        char initialP = params.f1532p;
        TreeInsertionParams paramsToInsertNext = params;
        while (true) {
            if (paramsToInsertNext == null) {
                break;
            }
            char p = paramsToInsertNext.f1532p;
            if (p != 0) {
                char[] key = paramsToInsertNext.key;
                int start = paramsToInsertNext.start;
                char val = paramsToInsertNext.val;
                int len = strlen(key, start);
                paramsToInsertNext = null;
                char[] cArr = this.f1531sc;
                if (cArr[p] == 65535) {
                    char pp = this.freenode;
                    this.freenode = (char) (pp + 1);
                    char[] cArr2 = this.f1530lo;
                    cArr2[pp] = cArr2[p];
                    char[] cArr3 = this.f1527eq;
                    cArr3[pp] = cArr3[p];
                    cArr2[p] = 0;
                    if (len <= 0) {
                        cArr[pp] = CharCompanionObject.MAX_VALUE;
                        this.f1528hi[p] = pp;
                        cArr[p] = 0;
                        cArr3[p] = val;
                        this.length++;
                        break;
                    }
                    cArr[p] = this.f1529kv.get(cArr2[pp]);
                    this.f1527eq[p] = pp;
                    char[] cArr4 = this.f1530lo;
                    char c = (char) (cArr4[pp] + 1);
                    cArr4[pp] = c;
                    if (this.f1529kv.get(c) == 0) {
                        this.f1530lo[pp] = 0;
                        this.f1531sc[pp] = 0;
                        this.f1528hi[pp] = 0;
                    } else {
                        this.f1531sc[pp] = CharCompanionObject.MAX_VALUE;
                    }
                }
                char s = key[start];
                char c2 = this.f1531sc[p];
                if (s < c2) {
                    TreeInsertionParams branchParams = new TreeInsertionParams(this.f1530lo[p], key, start, val);
                    Character insertNew = insertNewBranchIfNeeded(branchParams);
                    if (insertNew == null) {
                        paramsToInsertNext = branchParams;
                    } else {
                        this.f1530lo[p] = insertNew.charValue();
                    }
                } else if (s != c2) {
                    TreeInsertionParams branchParams2 = new TreeInsertionParams(this.f1528hi[p], key, start, val);
                    Character insertNew2 = insertNewBranchIfNeeded(branchParams2);
                    if (insertNew2 == null) {
                        paramsToInsertNext = branchParams2;
                    } else {
                        this.f1528hi[p] = insertNew2.charValue();
                    }
                } else if (s != 0) {
                    TreeInsertionParams branchParams3 = new TreeInsertionParams(this.f1527eq[p], key, start + 1, val);
                    Character insertNew3 = insertNewBranchIfNeeded(branchParams3);
                    if (insertNew3 == null) {
                        paramsToInsertNext = branchParams3;
                    } else {
                        this.f1527eq[p] = insertNew3.charValue();
                    }
                } else {
                    this.f1527eq[p] = val;
                }
            } else {
                throw new AssertionError();
            }
        }
        return initialP;
    }

    private char insert(TreeInsertionParams params) {
        Character newBranch = insertNewBranchIfNeeded(params);
        if (newBranch == null) {
            return insertIntoExistingBranch(params);
        }
        return newBranch.charValue();
    }

    public static int strcmp(char[] a, int startA, char[] b, int startB) {
        while (a[startA] == b[startB]) {
            if (a[startA] == 0) {
                return 0;
            }
            startA++;
            startB++;
        }
        return a[startA] - b[startB];
    }

    public static int strcmp(String str, char[] a, int start) {
        int len = str.length();
        int i = 0;
        while (i < len) {
            int d = str.charAt(i) - a[start + i];
            if (d != 0 || a[start + i] == 0) {
                return d;
            }
            i++;
        }
        if (a[start + i] != 0) {
            return -a[start + i];
        }
        return 0;
    }

    public static void strcpy(char[] dst, int di, char[] src, int si) {
        while (src[si] != 0) {
            dst[di] = src[si];
            di++;
            si++;
        }
        dst[di] = 0;
    }

    public static int strlen(char[] a, int start) {
        int len = 0;
        int i = start;
        while (i < a.length && a[i] != 0) {
            len++;
            i++;
        }
        return len;
    }

    public static int strlen(char[] a) {
        return strlen(a, 0);
    }

    public int find(String key) {
        int len = key.length();
        char[] strkey = new char[(len + 1)];
        key.getChars(0, len, strkey, 0);
        strkey[len] = 0;
        return find(strkey, 0);
    }

    public int find(char[] key, int start) {
        char p = this.root;
        int i = start;
        while (p != 0) {
            char c = this.f1531sc[p];
            if (c != 65535) {
                char c2 = key[i];
                int d = c2 - c;
                if (d == 0) {
                    if (c2 == 0) {
                        return this.f1527eq[p];
                    }
                    i++;
                    p = this.f1527eq[p];
                } else if (d < 0) {
                    p = this.f1530lo[p];
                } else {
                    p = this.f1528hi[p];
                }
            } else if (strcmp(key, i, this.f1529kv.getArray(), this.f1530lo[p]) == 0) {
                return this.f1527eq[p];
            } else {
                return -1;
            }
        }
        return -1;
    }

    public boolean knows(String key) {
        return find(key) >= 0;
    }

    private void redimNodeArrays(int newsize) {
        char[] cArr = this.f1530lo;
        int len = newsize < cArr.length ? newsize : cArr.length;
        char[] na = new char[newsize];
        System.arraycopy(cArr, 0, na, 0, len);
        this.f1530lo = na;
        char[] na2 = new char[newsize];
        System.arraycopy(this.f1528hi, 0, na2, 0, len);
        this.f1528hi = na2;
        char[] na3 = new char[newsize];
        System.arraycopy(this.f1527eq, 0, na3, 0, len);
        this.f1527eq = na3;
        char[] na4 = new char[newsize];
        System.arraycopy(this.f1531sc, 0, na4, 0, len);
        this.f1531sc = na4;
    }

    public int size() {
        return this.length;
    }

    /* access modifiers changed from: protected */
    public void insertBalanced(String[] k, char[] v, int offset, int n) {
        if (n >= 1) {
            int m = n >> 1;
            insert(k[m + offset], v[m + offset]);
            insertBalanced(k, v, offset, m);
            insertBalanced(k, v, offset + m + 1, (n - m) - 1);
        }
    }

    public void balance() {
        int i = 0;
        int n = this.length;
        String[] k = new String[n];
        char[] v = new char[n];
        TernaryTreeIterator iter = new TernaryTreeIterator(this);
        while (iter.hasMoreElements()) {
            v[i] = iter.getValue();
            k[i] = (String) iter.nextElement();
            i++;
        }
        init();
        insertBalanced(k, v, 0, n);
    }

    public void trimToSize() {
        balance();
        redimNodeArrays(this.freenode);
        CharVector kx = new CharVector();
        kx.alloc(1);
        compact(kx, new TernaryTree(), this.root);
        this.f1529kv = kx;
        kx.trimToSize();
    }

    private void compact(CharVector kx, TernaryTree map, char p) {
        if (p != 0) {
            if (this.f1531sc[p] == 65535) {
                int k = map.find(this.f1529kv.getArray(), this.f1530lo[p]);
                if (k < 0) {
                    k = kx.alloc(strlen(this.f1529kv.getArray(), this.f1530lo[p]) + 1);
                    strcpy(kx.getArray(), k, this.f1529kv.getArray(), this.f1530lo[p]);
                    map.insert(kx.getArray(), k, (char) k);
                }
                this.f1530lo[p] = (char) k;
                return;
            }
            compact(kx, map, this.f1530lo[p]);
            if (this.f1531sc[p] != 0) {
                compact(kx, map, this.f1527eq[p]);
            }
            compact(kx, map, this.f1528hi[p]);
        }
    }

    public Enumeration keys() {
        return new TernaryTreeIterator(this);
    }

    private static class TreeInsertionParams {
        char[] key;

        /* renamed from: p */
        char f1532p;
        int start;
        char val;

        public TreeInsertionParams(char p, char[] key2, int start2, char val2) {
            this.f1532p = p;
            this.key = key2;
            this.start = start2;
            this.val = val2;
        }
    }
}
