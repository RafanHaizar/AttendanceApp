package androidx.emoji2.text;

import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.MetaKeyKeyListener;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
import androidx.emoji2.text.EmojiCompat;
import androidx.emoji2.text.MetadataRepo;
import java.util.Arrays;

final class EmojiProcessor {
    private static final int ACTION_ADVANCE_BOTH = 1;
    private static final int ACTION_ADVANCE_END = 2;
    private static final int ACTION_FLUSH = 3;
    private final int[] mEmojiAsDefaultStyleExceptions;
    private EmojiCompat.GlyphChecker mGlyphChecker;
    private final MetadataRepo mMetadataRepo;
    private final EmojiCompat.SpanFactory mSpanFactory;
    private final boolean mUseEmojiAsDefaultStyle;

    EmojiProcessor(MetadataRepo metadataRepo, EmojiCompat.SpanFactory spanFactory, EmojiCompat.GlyphChecker glyphChecker, boolean useEmojiAsDefaultStyle, int[] emojiAsDefaultStyleExceptions) {
        this.mSpanFactory = spanFactory;
        this.mMetadataRepo = metadataRepo;
        this.mGlyphChecker = glyphChecker;
        this.mUseEmojiAsDefaultStyle = useEmojiAsDefaultStyle;
        this.mEmojiAsDefaultStyleExceptions = emojiAsDefaultStyleExceptions;
    }

    /* access modifiers changed from: package-private */
    public int getEmojiMatch(CharSequence charSequence) {
        return getEmojiMatch(charSequence, this.mMetadataRepo.getMetadataVersion());
    }

    /* access modifiers changed from: package-private */
    public int getEmojiMatch(CharSequence charSequence, int metadataVersion) {
        ProcessorSm sm = new ProcessorSm(this.mMetadataRepo.getRootNode(), this.mUseEmojiAsDefaultStyle, this.mEmojiAsDefaultStyleExceptions);
        int end = charSequence.length();
        int currentOffset = 0;
        int potentialSubsequenceMatch = 0;
        int subsequenceMatch = 0;
        while (currentOffset < end) {
            int codePoint = Character.codePointAt(charSequence, currentOffset);
            int action = sm.check(codePoint);
            EmojiMetadata currentNode = sm.getCurrentMetadata();
            switch (action) {
                case 1:
                    currentOffset += Character.charCount(codePoint);
                    potentialSubsequenceMatch = 0;
                    break;
                case 2:
                    currentOffset += Character.charCount(codePoint);
                    break;
                case 3:
                    currentNode = sm.getFlushMetadata();
                    if (currentNode.getCompatAdded() <= metadataVersion) {
                        subsequenceMatch++;
                        break;
                    }
                    break;
            }
            if (currentNode != null && currentNode.getCompatAdded() <= metadataVersion) {
                potentialSubsequenceMatch++;
            }
        }
        if (subsequenceMatch != 0) {
            return 2;
        }
        if (sm.isInFlushableState() && sm.getCurrentMetadata().getCompatAdded() <= metadataVersion) {
            return 1;
        }
        if (potentialSubsequenceMatch == 0) {
            return 0;
        }
        return 2;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0048 A[Catch:{ all -> 0x0130 }] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0066 A[Catch:{ all -> 0x0130 }] */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0129  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.CharSequence process(java.lang.CharSequence r10, int r11, int r12, int r13, boolean r14) {
        /*
            r9 = this;
            boolean r0 = r10 instanceof androidx.emoji2.text.SpannableBuilder
            if (r0 == 0) goto L_0x000a
            r1 = r10
            androidx.emoji2.text.SpannableBuilder r1 = (androidx.emoji2.text.SpannableBuilder) r1
            r1.beginBatchEdit()
        L_0x000a:
            r1 = 0
            if (r0 != 0) goto L_0x002c
            boolean r2 = r10 instanceof android.text.Spannable     // Catch:{ all -> 0x0130 }
            if (r2 == 0) goto L_0x0012
            goto L_0x002c
        L_0x0012:
            boolean r2 = r10 instanceof android.text.Spanned     // Catch:{ all -> 0x0130 }
            if (r2 == 0) goto L_0x0035
            r2 = r10
            android.text.Spanned r2 = (android.text.Spanned) r2     // Catch:{ all -> 0x0130 }
            int r3 = r11 + -1
            int r4 = r12 + 1
            java.lang.Class<androidx.emoji2.text.EmojiSpan> r5 = androidx.emoji2.text.EmojiSpan.class
            int r2 = r2.nextSpanTransition(r3, r4, r5)     // Catch:{ all -> 0x0130 }
            if (r2 > r12) goto L_0x0035
            androidx.emoji2.text.UnprecomputeTextOnModificationSpannable r3 = new androidx.emoji2.text.UnprecomputeTextOnModificationSpannable     // Catch:{ all -> 0x0130 }
            r3.<init>((java.lang.CharSequence) r10)     // Catch:{ all -> 0x0130 }
            r1 = r3
            goto L_0x0035
        L_0x002c:
            androidx.emoji2.text.UnprecomputeTextOnModificationSpannable r2 = new androidx.emoji2.text.UnprecomputeTextOnModificationSpannable     // Catch:{ all -> 0x0130 }
            r3 = r10
            android.text.Spannable r3 = (android.text.Spannable) r3     // Catch:{ all -> 0x0130 }
            r2.<init>((android.text.Spannable) r3)     // Catch:{ all -> 0x0130 }
            r1 = r2
        L_0x0035:
            if (r1 == 0) goto L_0x0064
            java.lang.Class<androidx.emoji2.text.EmojiSpan> r2 = androidx.emoji2.text.EmojiSpan.class
            java.lang.Object[] r2 = r1.getSpans(r11, r12, r2)     // Catch:{ all -> 0x0130 }
            androidx.emoji2.text.EmojiSpan[] r2 = (androidx.emoji2.text.EmojiSpan[]) r2     // Catch:{ all -> 0x0130 }
            if (r2 == 0) goto L_0x0064
            int r3 = r2.length     // Catch:{ all -> 0x0130 }
            if (r3 <= 0) goto L_0x0064
            int r3 = r2.length     // Catch:{ all -> 0x0130 }
            r4 = 0
        L_0x0046:
            if (r4 >= r3) goto L_0x0064
            r5 = r2[r4]     // Catch:{ all -> 0x0130 }
            int r6 = r1.getSpanStart(r5)     // Catch:{ all -> 0x0130 }
            int r7 = r1.getSpanEnd(r5)     // Catch:{ all -> 0x0130 }
            if (r6 == r12) goto L_0x0057
            r1.removeSpan(r5)     // Catch:{ all -> 0x0130 }
        L_0x0057:
            int r8 = java.lang.Math.min(r6, r11)     // Catch:{ all -> 0x0130 }
            r11 = r8
            int r8 = java.lang.Math.max(r7, r12)     // Catch:{ all -> 0x0130 }
            r12 = r8
            int r4 = r4 + 1
            goto L_0x0046
        L_0x0064:
            if (r11 == r12) goto L_0x0126
            int r2 = r10.length()     // Catch:{ all -> 0x0130 }
            if (r11 < r2) goto L_0x006e
            goto L_0x0126
        L_0x006e:
            r2 = 2147483647(0x7fffffff, float:NaN)
            if (r13 == r2) goto L_0x0084
            if (r1 == 0) goto L_0x0084
            int r2 = r1.length()     // Catch:{ all -> 0x0130 }
            java.lang.Class<androidx.emoji2.text.EmojiSpan> r3 = androidx.emoji2.text.EmojiSpan.class
            r4 = 0
            java.lang.Object[] r2 = r1.getSpans(r4, r2, r3)     // Catch:{ all -> 0x0130 }
            androidx.emoji2.text.EmojiSpan[] r2 = (androidx.emoji2.text.EmojiSpan[]) r2     // Catch:{ all -> 0x0130 }
            int r2 = r2.length     // Catch:{ all -> 0x0130 }
            int r13 = r13 - r2
        L_0x0084:
            r2 = 0
            androidx.emoji2.text.EmojiProcessor$ProcessorSm r3 = new androidx.emoji2.text.EmojiProcessor$ProcessorSm     // Catch:{ all -> 0x0130 }
            androidx.emoji2.text.MetadataRepo r4 = r9.mMetadataRepo     // Catch:{ all -> 0x0130 }
            androidx.emoji2.text.MetadataRepo$Node r4 = r4.getRootNode()     // Catch:{ all -> 0x0130 }
            boolean r5 = r9.mUseEmojiAsDefaultStyle     // Catch:{ all -> 0x0130 }
            int[] r6 = r9.mEmojiAsDefaultStyleExceptions     // Catch:{ all -> 0x0130 }
            r3.<init>(r4, r5, r6)     // Catch:{ all -> 0x0130 }
            r4 = r11
            int r5 = java.lang.Character.codePointAt(r10, r4)     // Catch:{ all -> 0x0130 }
        L_0x0099:
            if (r4 >= r12) goto L_0x00e8
            if (r2 >= r13) goto L_0x00e8
            int r6 = r3.check(r5)     // Catch:{ all -> 0x0130 }
            switch(r6) {
                case 1: goto L_0x00d6;
                case 2: goto L_0x00c9;
                case 3: goto L_0x00a5;
                default: goto L_0x00a4;
            }     // Catch:{ all -> 0x0130 }
        L_0x00a4:
            goto L_0x00e7
        L_0x00a5:
            if (r14 != 0) goto L_0x00b1
            androidx.emoji2.text.EmojiMetadata r7 = r3.getFlushMetadata()     // Catch:{ all -> 0x0130 }
            boolean r7 = r9.hasGlyph(r10, r11, r4, r7)     // Catch:{ all -> 0x0130 }
            if (r7 != 0) goto L_0x00c7
        L_0x00b1:
            if (r1 != 0) goto L_0x00be
            androidx.emoji2.text.UnprecomputeTextOnModificationSpannable r7 = new androidx.emoji2.text.UnprecomputeTextOnModificationSpannable     // Catch:{ all -> 0x0130 }
            android.text.SpannableString r8 = new android.text.SpannableString     // Catch:{ all -> 0x0130 }
            r8.<init>(r10)     // Catch:{ all -> 0x0130 }
            r7.<init>((android.text.Spannable) r8)     // Catch:{ all -> 0x0130 }
            r1 = r7
        L_0x00be:
            androidx.emoji2.text.EmojiMetadata r7 = r3.getFlushMetadata()     // Catch:{ all -> 0x0130 }
            r9.addEmoji(r1, r7, r11, r4)     // Catch:{ all -> 0x0130 }
            int r2 = r2 + 1
        L_0x00c7:
            r11 = r4
            goto L_0x00e7
        L_0x00c9:
            int r7 = java.lang.Character.charCount(r5)     // Catch:{ all -> 0x0130 }
            int r4 = r4 + r7
            if (r4 >= r12) goto L_0x00e7
            int r7 = java.lang.Character.codePointAt(r10, r4)     // Catch:{ all -> 0x0130 }
            r5 = r7
            goto L_0x00e7
        L_0x00d6:
            int r7 = java.lang.Character.codePointAt(r10, r11)     // Catch:{ all -> 0x0130 }
            int r7 = java.lang.Character.charCount(r7)     // Catch:{ all -> 0x0130 }
            int r11 = r11 + r7
            r4 = r11
            if (r4 >= r12) goto L_0x00e7
            int r7 = java.lang.Character.codePointAt(r10, r4)     // Catch:{ all -> 0x0130 }
            r5 = r7
        L_0x00e7:
            goto L_0x0099
        L_0x00e8:
            boolean r6 = r3.isInFlushableState()     // Catch:{ all -> 0x0130 }
            if (r6 == 0) goto L_0x010d
            if (r2 >= r13) goto L_0x010d
            if (r14 != 0) goto L_0x00fc
            androidx.emoji2.text.EmojiMetadata r6 = r3.getCurrentMetadata()     // Catch:{ all -> 0x0130 }
            boolean r6 = r9.hasGlyph(r10, r11, r4, r6)     // Catch:{ all -> 0x0130 }
            if (r6 != 0) goto L_0x010d
        L_0x00fc:
            if (r1 != 0) goto L_0x0104
            androidx.emoji2.text.UnprecomputeTextOnModificationSpannable r6 = new androidx.emoji2.text.UnprecomputeTextOnModificationSpannable     // Catch:{ all -> 0x0130 }
            r6.<init>((java.lang.CharSequence) r10)     // Catch:{ all -> 0x0130 }
            r1 = r6
        L_0x0104:
            androidx.emoji2.text.EmojiMetadata r6 = r3.getCurrentMetadata()     // Catch:{ all -> 0x0130 }
            r9.addEmoji(r1, r6, r11, r4)     // Catch:{ all -> 0x0130 }
            int r2 = r2 + 1
        L_0x010d:
            if (r1 == 0) goto L_0x011c
            android.text.Spannable r6 = r1.getUnwrappedSpannable()     // Catch:{ all -> 0x0130 }
            if (r0 == 0) goto L_0x011b
            r7 = r10
            androidx.emoji2.text.SpannableBuilder r7 = (androidx.emoji2.text.SpannableBuilder) r7
            r7.endBatchEdit()
        L_0x011b:
            return r6
        L_0x011c:
            if (r0 == 0) goto L_0x0125
            r6 = r10
            androidx.emoji2.text.SpannableBuilder r6 = (androidx.emoji2.text.SpannableBuilder) r6
            r6.endBatchEdit()
        L_0x0125:
            return r10
        L_0x0126:
            if (r0 == 0) goto L_0x012f
            r2 = r10
            androidx.emoji2.text.SpannableBuilder r2 = (androidx.emoji2.text.SpannableBuilder) r2
            r2.endBatchEdit()
        L_0x012f:
            return r10
        L_0x0130:
            r1 = move-exception
            if (r0 == 0) goto L_0x0139
            r2 = r10
            androidx.emoji2.text.SpannableBuilder r2 = (androidx.emoji2.text.SpannableBuilder) r2
            r2.endBatchEdit()
        L_0x0139:
            goto L_0x013b
        L_0x013a:
            throw r1
        L_0x013b:
            goto L_0x013a
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.emoji2.text.EmojiProcessor.process(java.lang.CharSequence, int, int, int, boolean):java.lang.CharSequence");
    }

    static boolean handleOnKeyDown(Editable editable, int keyCode, KeyEvent event) {
        boolean handled;
        switch (keyCode) {
            case 67:
                handled = delete(editable, event, false);
                break;
            case 112:
                handled = delete(editable, event, true);
                break;
            default:
                handled = false;
                break;
        }
        if (!handled) {
            return false;
        }
        MetaKeyKeyListener.adjustMetaAfterKeypress(editable);
        return true;
    }

    private static boolean delete(Editable content, KeyEvent event, boolean forwardDelete) {
        EmojiSpan[] spans;
        if (hasModifiers(event)) {
            return false;
        }
        int start = Selection.getSelectionStart(content);
        int end = Selection.getSelectionEnd(content);
        if (!hasInvalidSelection(start, end) && (spans = (EmojiSpan[]) content.getSpans(start, end, EmojiSpan.class)) != null && spans.length > 0) {
            int length = spans.length;
            int index = 0;
            while (index < length) {
                EmojiSpan span = spans[index];
                int spanStart = content.getSpanStart(span);
                int spanEnd = content.getSpanEnd(span);
                if ((!forwardDelete || spanStart != start) && ((forwardDelete || spanEnd != start) && (start <= spanStart || start >= spanEnd))) {
                    index++;
                } else {
                    content.delete(spanStart, spanEnd);
                    return true;
                }
            }
        }
        return false;
    }

    static boolean handleDeleteSurroundingText(InputConnection inputConnection, Editable editable, int beforeLength, int afterLength, boolean inCodePoints) {
        int end;
        int start;
        if (editable == null || inputConnection == null || beforeLength < 0 || afterLength < 0) {
            return false;
        }
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        if (hasInvalidSelection(selectionStart, selectionEnd)) {
            return false;
        }
        if (inCodePoints) {
            start = CodepointIndexFinder.findIndexBackward(editable, selectionStart, Math.max(beforeLength, 0));
            end = CodepointIndexFinder.findIndexForward(editable, selectionEnd, Math.max(afterLength, 0));
            if (start == -1 || end == -1) {
                return false;
            }
        } else {
            start = Math.max(selectionStart - beforeLength, 0);
            end = Math.min(selectionEnd + afterLength, editable.length());
        }
        EmojiSpan[] spans = (EmojiSpan[]) editable.getSpans(start, end, EmojiSpan.class);
        if (spans == null || spans.length <= 0) {
            return false;
        }
        for (EmojiSpan span : spans) {
            int spanStart = editable.getSpanStart(span);
            int spanEnd = editable.getSpanEnd(span);
            start = Math.min(spanStart, start);
            end = Math.max(spanEnd, end);
        }
        int start2 = Math.max(start, 0);
        int end2 = Math.min(end, editable.length());
        inputConnection.beginBatchEdit();
        editable.delete(start2, end2);
        inputConnection.endBatchEdit();
        return true;
    }

    private static boolean hasInvalidSelection(int start, int end) {
        return start == -1 || end == -1 || start != end;
    }

    private static boolean hasModifiers(KeyEvent event) {
        return !KeyEvent.metaStateHasNoModifiers(event.getMetaState());
    }

    private void addEmoji(Spannable spannable, EmojiMetadata metadata, int start, int end) {
        spannable.setSpan(this.mSpanFactory.createSpan(metadata), start, end, 33);
    }

    private boolean hasGlyph(CharSequence charSequence, int start, int end, EmojiMetadata metadata) {
        if (metadata.getHasGlyph() == 0) {
            metadata.setHasGlyph(this.mGlyphChecker.hasGlyph(charSequence, start, end, metadata.getSdkAdded()));
        }
        return metadata.getHasGlyph() == 2;
    }

    static final class ProcessorSm {
        private static final int STATE_DEFAULT = 1;
        private static final int STATE_WALKING = 2;
        private int mCurrentDepth;
        private MetadataRepo.Node mCurrentNode;
        private final int[] mEmojiAsDefaultStyleExceptions;
        private MetadataRepo.Node mFlushNode;
        private int mLastCodepoint;
        private final MetadataRepo.Node mRootNode;
        private int mState = 1;
        private final boolean mUseEmojiAsDefaultStyle;

        ProcessorSm(MetadataRepo.Node rootNode, boolean useEmojiAsDefaultStyle, int[] emojiAsDefaultStyleExceptions) {
            this.mRootNode = rootNode;
            this.mCurrentNode = rootNode;
            this.mUseEmojiAsDefaultStyle = useEmojiAsDefaultStyle;
            this.mEmojiAsDefaultStyleExceptions = emojiAsDefaultStyleExceptions;
        }

        /* access modifiers changed from: package-private */
        public int check(int codePoint) {
            int action;
            MetadataRepo.Node node = this.mCurrentNode.get(codePoint);
            switch (this.mState) {
                case 2:
                    if (node == null) {
                        if (isTextStyle(codePoint) == 0) {
                            if (isEmojiStyle(codePoint) == 0) {
                                if (this.mCurrentNode.getData() != null) {
                                    if (this.mCurrentDepth == 1) {
                                        if (!shouldUseEmojiPresentationStyleForSingleCodepoint()) {
                                            action = reset();
                                            break;
                                        } else {
                                            this.mFlushNode = this.mCurrentNode;
                                            action = 3;
                                            reset();
                                            break;
                                        }
                                    } else {
                                        this.mFlushNode = this.mCurrentNode;
                                        action = 3;
                                        reset();
                                        break;
                                    }
                                } else {
                                    action = reset();
                                    break;
                                }
                            } else {
                                action = 2;
                                break;
                            }
                        } else {
                            action = reset();
                            break;
                        }
                    } else {
                        this.mCurrentNode = node;
                        this.mCurrentDepth++;
                        action = 2;
                        break;
                    }
                default:
                    if (node != null) {
                        this.mState = 2;
                        this.mCurrentNode = node;
                        this.mCurrentDepth = 1;
                        action = 2;
                        break;
                    } else {
                        action = reset();
                        break;
                    }
            }
            this.mLastCodepoint = codePoint;
            return action;
        }

        private int reset() {
            this.mState = 1;
            this.mCurrentNode = this.mRootNode;
            this.mCurrentDepth = 0;
            return 1;
        }

        /* access modifiers changed from: package-private */
        public EmojiMetadata getFlushMetadata() {
            return this.mFlushNode.getData();
        }

        /* access modifiers changed from: package-private */
        public EmojiMetadata getCurrentMetadata() {
            return this.mCurrentNode.getData();
        }

        /* access modifiers changed from: package-private */
        public boolean isInFlushableState() {
            if (this.mState != 2 || this.mCurrentNode.getData() == null || (this.mCurrentDepth <= 1 && !shouldUseEmojiPresentationStyleForSingleCodepoint())) {
                return false;
            }
            return true;
        }

        private boolean shouldUseEmojiPresentationStyleForSingleCodepoint() {
            if (this.mCurrentNode.getData().isDefaultEmoji() || isEmojiStyle(this.mLastCodepoint)) {
                return true;
            }
            if (this.mUseEmojiAsDefaultStyle) {
                if (this.mEmojiAsDefaultStyleExceptions == null) {
                    return true;
                }
                if (Arrays.binarySearch(this.mEmojiAsDefaultStyleExceptions, this.mCurrentNode.getData().getCodepointAt(0)) < 0) {
                    return true;
                }
            }
            return false;
        }

        private static boolean isEmojiStyle(int codePoint) {
            return codePoint == 65039;
        }

        private static boolean isTextStyle(int codePoint) {
            return codePoint == 65038;
        }
    }

    private static final class CodepointIndexFinder {
        private static final int INVALID_INDEX = -1;

        private CodepointIndexFinder() {
        }

        static int findIndexBackward(CharSequence cs, int from, int numCodePoints) {
            int currentIndex = from;
            boolean waitingHighSurrogate = false;
            int length = cs.length();
            if (currentIndex < 0 || length < currentIndex || numCodePoints < 0) {
                return -1;
            }
            int remainingCodePoints = numCodePoints;
            while (remainingCodePoints != 0) {
                currentIndex--;
                if (currentIndex >= 0) {
                    char c = cs.charAt(currentIndex);
                    if (waitingHighSurrogate) {
                        if (!Character.isHighSurrogate(c)) {
                            return -1;
                        }
                        waitingHighSurrogate = false;
                        remainingCodePoints--;
                    } else if (!Character.isSurrogate(c)) {
                        remainingCodePoints--;
                    } else if (Character.isHighSurrogate(c)) {
                        return -1;
                    } else {
                        waitingHighSurrogate = true;
                    }
                } else if (waitingHighSurrogate) {
                    return -1;
                } else {
                    return 0;
                }
            }
            return currentIndex;
        }

        static int findIndexForward(CharSequence cs, int from, int numCodePoints) {
            int currentIndex = from;
            boolean waitingLowSurrogate = false;
            int length = cs.length();
            if (currentIndex < 0 || length < currentIndex || numCodePoints < 0) {
                return -1;
            }
            int remainingCodePoints = numCodePoints;
            while (remainingCodePoints != 0) {
                if (currentIndex < length) {
                    char c = cs.charAt(currentIndex);
                    if (waitingLowSurrogate) {
                        if (!Character.isLowSurrogate(c)) {
                            return -1;
                        }
                        remainingCodePoints--;
                        waitingLowSurrogate = false;
                        currentIndex++;
                    } else if (!Character.isSurrogate(c)) {
                        remainingCodePoints--;
                        currentIndex++;
                    } else if (Character.isLowSurrogate(c)) {
                        return -1;
                    } else {
                        waitingLowSurrogate = true;
                        currentIndex++;
                    }
                } else if (waitingLowSurrogate) {
                    return -1;
                } else {
                    return length;
                }
            }
            return currentIndex;
        }
    }
}
