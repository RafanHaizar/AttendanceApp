package com.itextpdf.layout.hyphenation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HyphenationTree extends TernaryTree implements IPatternConsumer {
    private static final long serialVersionUID = -7842107987915665573L;
    protected TernaryTree classmap = new TernaryTree();
    private transient TernaryTree ivalues;
    protected Map<String, List> stoplist = new HashMap(23);
    protected ByteVector vspace;

    public HyphenationTree() {
        ByteVector byteVector = new ByteVector();
        this.vspace = byteVector;
        byteVector.alloc(1);
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
    }

    /* access modifiers changed from: protected */
    public int packValues(String values) {
        int n = values.length();
        int m = (n & 1) == 1 ? (n >> 1) + 2 : (n >> 1) + 1;
        int offset = this.vspace.alloc(m);
        byte[] va = this.vspace.getArray();
        for (int i = 0; i < n; i++) {
            int j = i >> 1;
            byte v = (byte) (((values.charAt(i) - '0') + 1) & 15);
            if ((i & 1) == 1) {
                va[j + offset] = (byte) (va[j + offset] | v);
            } else {
                va[j + offset] = (byte) (v << 4);
            }
        }
        va[(m - 1) + offset] = 0;
        return offset;
    }

    /* access modifiers changed from: protected */
    public String unpackValues(int k) {
        StringBuffer buf = new StringBuffer();
        int k2 = k + 1;
        byte v = this.vspace.get(k);
        while (v != 0) {
            buf.append((char) (((v >>> 4) - 1) + 48));
            char c = (char) (v & 15);
            if (c == 0) {
                break;
            }
            buf.append((char) ((c - 1) + 48));
            v = this.vspace.get(k2);
            k2++;
        }
        return buf.toString();
    }

    public void loadPatterns(String filename) throws HyphenationException, FileNotFoundException {
        loadPatterns(new FileInputStream(filename), filename);
    }

    public void loadPatterns(InputStream stream, String name) throws HyphenationException {
        PatternParser pp = new PatternParser(this);
        this.ivalues = new TernaryTree();
        pp.parse(stream, name);
        trimToSize();
        this.vspace.trimToSize();
        this.classmap.trimToSize();
        this.ivalues = null;
    }

    public String findPattern(String pat) {
        int k = super.find(pat);
        if (k >= 0) {
            return unpackValues(k);
        }
        return "";
    }

    /* access modifiers changed from: protected */
    public int hstrcmp(char[] s, int si, char[] t, int ti) {
        while (s[si] == t[ti]) {
            if (s[si] == 0) {
                return 0;
            }
            si++;
            ti++;
        }
        if (t[ti] == 0) {
            return 0;
        }
        return s[si] - t[ti];
    }

    /* access modifiers changed from: protected */
    public byte[] getValues(int k) {
        StringBuffer buf = new StringBuffer();
        int k2 = k + 1;
        byte v = this.vspace.get(k);
        while (v != 0) {
            buf.append((char) ((v >>> 4) - 1));
            char c = (char) (v & 15);
            if (c == 0) {
                break;
            }
            buf.append((char) (c - 1));
            v = this.vspace.get(k2);
            k2++;
        }
        byte[] res = new byte[buf.length()];
        for (int i = 0; i < res.length; i++) {
            res[i] = (byte) buf.charAt(i);
        }
        return res;
    }

    /* access modifiers changed from: protected */
    public void searchPatterns(char[] word, int index, byte[] il) {
        int i = index;
        char sp = word[i];
        char p = this.root;
        while (p > 0 && p < this.f1531sc.length) {
            if (this.f1531sc[p] != 65535) {
                int d = sp - this.f1531sc[p];
                if (d == 0) {
                    if (sp != 0) {
                        i++;
                        sp = word[i];
                        p = this.f1527eq[p];
                        char q = p;
                        while (true) {
                            if (q <= 0 || q >= this.f1531sc.length || this.f1531sc[q] == 65535) {
                                break;
                            } else if (this.f1531sc[q] == 0) {
                                byte[] values = getValues(this.f1527eq[q]);
                                int j = index;
                                for (int k = 0; k < values.length; k++) {
                                    if (j < il.length && values[k] > il[j]) {
                                        il[j] = values[k];
                                    }
                                    j++;
                                }
                            } else {
                                q = this.f1530lo[q];
                            }
                        }
                    } else {
                        return;
                    }
                } else {
                    p = d < 0 ? this.f1530lo[p] : this.f1528hi[p];
                }
            } else if (hstrcmp(word, i, this.f1529kv.getArray(), this.f1530lo[p]) == 0) {
                byte[] values2 = getValues(this.f1527eq[p]);
                int j2 = index;
                for (int k2 = 0; k2 < values2.length; k2++) {
                    if (j2 < il.length && values2[k2] > il[j2]) {
                        il[j2] = values2[k2];
                    }
                    j2++;
                }
                return;
            } else {
                return;
            }
        }
    }

    public Hyphenation hyphenate(String word, int remainCharCount, int pushCharCount) {
        char[] w = word.toCharArray();
        if (isMultiPartWord(w, w.length)) {
            return new Hyphenation(new String(w), getHyphPointsForWords(splitOnNonCharacters(w), remainCharCount, pushCharCount));
        }
        return hyphenate(w, 0, w.length, remainCharCount, pushCharCount);
    }

    private boolean isMultiPartWord(char[] w, int len) {
        int wordParts = 0;
        for (int i = 0; i < len; i++) {
            char[] c = new char[2];
            c[0] = w[i];
            if (this.classmap.find(c, 0) > 0) {
                if (wordParts > 1) {
                    return true;
                }
                wordParts = 1;
            } else if (wordParts == 1) {
                wordParts++;
            }
        }
        return false;
    }

    private List<char[]> splitOnNonCharacters(char[] word) {
        int i;
        List<Integer> breakPoints = getNonLetterBreaks(word);
        if (breakPoints.size() == 0) {
            return Collections.emptyList();
        }
        List<char[]> words = new ArrayList<>();
        for (int ibreak = 0; ibreak < breakPoints.size(); ibreak++) {
            if (ibreak == 0) {
                i = 0;
            } else {
                i = breakPoints.get(ibreak - 1).intValue();
            }
            words.add(getWordFromCharArray(word, i, breakPoints.get(ibreak).intValue()));
        }
        if ((word.length - breakPoints.get(breakPoints.size() - 1).intValue()) - 1 > 1) {
            words.add(getWordFromCharArray(word, breakPoints.get(breakPoints.size() - 1).intValue(), word.length));
        }
        return words;
    }

    private List<Integer> getNonLetterBreaks(char[] word) {
        char[] c = new char[2];
        List<Integer> breakPoints = new ArrayList<>();
        boolean foundLetter = false;
        for (int i = 0; i < word.length; i++) {
            c[0] = word[i];
            if (this.classmap.find(c, 0) >= 0) {
                foundLetter = true;
            } else if (foundLetter) {
                breakPoints.add(Integer.valueOf(i));
            }
        }
        return breakPoints;
    }

    private char[] getWordFromCharArray(char[] word, int startIndex, int endIndex) {
        char[] newWord = new char[(endIndex - (startIndex == 0 ? startIndex : startIndex + 1))];
        int iChar = 0;
        int i = startIndex == 0 ? 0 : startIndex + 1;
        while (i < endIndex) {
            newWord[iChar] = word[i];
            i++;
            iChar++;
        }
        return newWord;
    }

    private int[] getHyphPointsForWords(List<char[]> nonLetterWords, int remainCharCount, int pushCharCount) {
        int[] breaks = new int[0];
        int iNonLetterWord = 0;
        while (iNonLetterWord < nonLetterWords.size()) {
            char[] nonLetterWord = nonLetterWords.get(iNonLetterWord);
            Hyphenation curHyph = hyphenate(nonLetterWord, 0, nonLetterWord.length, iNonLetterWord == 0 ? remainCharCount : 1, iNonLetterWord == nonLetterWords.size() - 1 ? pushCharCount : 1);
            if (curHyph != null) {
                int[] combined = new int[(breaks.length + curHyph.getHyphenationPoints().length)];
                int[] hyphPoints = curHyph.getHyphenationPoints();
                int foreWordsSize = calcForeWordsSize(nonLetterWords, iNonLetterWord);
                for (int i = 0; i < hyphPoints.length; i++) {
                    hyphPoints[i] = hyphPoints[i] + foreWordsSize;
                }
                System.arraycopy(breaks, 0, combined, 0, breaks.length);
                System.arraycopy(hyphPoints, 0, combined, breaks.length, hyphPoints.length);
                breaks = combined;
            }
            iNonLetterWord++;
        }
        return breaks;
    }

    private int calcForeWordsSize(List<char[]> nonLetterWords, int iNonLetterWord) {
        int result = 0;
        for (int i = 0; i < iNonLetterWord; i++) {
            result += nonLetterWords.get(i).length + 1;
        }
        return result;
    }

    public Hyphenation hyphenate(char[] w, int offset, int len, int remainCharCount, int pushCharCount) {
        char[] cArr = w;
        int i = len;
        int i2 = remainCharCount;
        char[] word = new char[(i + 3)];
        char[] c = new char[2];
        int iIgnoreAtBeginning = 0;
        int iLength = len;
        boolean bEndOfLetters = false;
        for (int i3 = 1; i3 <= i; i3++) {
            c[0] = cArr[(offset + i3) - 1];
            int nc = this.classmap.find(c, 0);
            if (nc < 0) {
                if (i3 == iIgnoreAtBeginning + 1) {
                    iIgnoreAtBeginning++;
                } else {
                    bEndOfLetters = true;
                }
                iLength--;
            } else if (bEndOfLetters) {
                return null;
            } else {
                word[i3 - iIgnoreAtBeginning] = (char) nc;
            }
        }
        int len2 = iLength;
        if (len2 < i2 + pushCharCount) {
            return null;
        }
        int[] result = new int[(len2 + 1)];
        int k = 0;
        String sw = new String(word, 1, len2);
        if (this.stoplist.containsKey(sw)) {
            ArrayList hw = (ArrayList) this.stoplist.get(sw);
            int j = 0;
            int i4 = 0;
            while (i4 < hw.size()) {
                Object o = hw.get(i4);
                char[] c2 = c;
                if ((o instanceof String) && (j = j + ((String) o).length()) >= i2 && j < len2 - pushCharCount) {
                    result[k] = j + iIgnoreAtBeginning;
                    k++;
                }
                i4++;
                c = c2;
            }
        } else {
            word[0] = '.';
            word[len2 + 1] = '.';
            word[len2 + 2] = 0;
            byte[] il = new byte[(len2 + 3)];
            for (int i5 = 0; i5 < len2 + 1; i5++) {
                searchPatterns(word, i5, il);
            }
            for (int i6 = 0; i6 < len2; i6++) {
                if ((il[i6 + 1] & 1) == 1 && i6 >= i2 && i6 <= len2 - pushCharCount) {
                    result[k] = i6;
                    k++;
                }
            }
        }
        if (k <= 0) {
            return null;
        }
        int[] res = new int[k];
        System.arraycopy(result, 0, res, 0, k);
        return new Hyphenation(new String(cArr, iIgnoreAtBeginning, len2), res);
    }

    public void addClass(String chargroup) {
        if (chargroup.length() > 0) {
            char equivChar = chargroup.charAt(0);
            char[] key = new char[2];
            key[1] = 0;
            for (int i = 0; i < chargroup.length(); i++) {
                key[0] = chargroup.charAt(i);
                this.classmap.insert(key, 0, equivChar);
            }
        }
    }

    public void addException(String word, List hyphenatedword) {
        this.stoplist.put(word, hyphenatedword);
    }

    public void addPattern(String pattern, String ivalue) {
        int k = this.ivalues.find(ivalue);
        if (k <= 0) {
            k = packValues(ivalue);
            this.ivalues.insert(ivalue, (char) k);
        }
        insert(pattern, (char) k);
    }
}
