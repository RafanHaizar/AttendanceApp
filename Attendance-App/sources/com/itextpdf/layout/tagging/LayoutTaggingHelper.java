package com.itextpdf.layout.tagging;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.TagStructureContext;
import com.itextpdf.kernel.pdf.tagutils.TagTreePointer;
import com.itextpdf.kernel.pdf.tagutils.WaitingTagsManager;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.renderer.AreaBreakRenderer;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.p026io.LogMessageConstant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.LoggerFactory;

public class LayoutTaggingHelper {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final int RETVAL_NO_PARENT = -1;
    private final int RETVAL_PARENT_AND_KID_FINISHED = -2;
    private Map<IRenderer, TagTreePointer> autoTaggingPointerSavedPosition;
    private TagStructureContext context;
    private PdfDocument document;
    private Map<PdfObject, TaggingDummyElement> existingTagsDummies;
    private boolean immediateFlush;
    private Map<TaggingHintKey, List<TaggingHintKey>> kidsHints;
    private Map<TaggingHintKey, TaggingHintKey> parentHints;
    private Map<String, List<ITaggingRule>> taggingRules;

    public LayoutTaggingHelper(PdfDocument document2, boolean immediateFlush2) {
        this.document = document2;
        this.context = document2.getTagStructureContext();
        this.immediateFlush = immediateFlush2;
        this.kidsHints = new LinkedHashMap();
        this.parentHints = new LinkedHashMap();
        this.autoTaggingPointerSavedPosition = new HashMap();
        this.taggingRules = new HashMap();
        registerRules(this.context.getTagStructureTargetVersion());
        this.existingTagsDummies = new LinkedHashMap();
    }

    public static void addTreeHints(LayoutTaggingHelper taggingHelper, IRenderer rootRenderer) {
        List<IRenderer> childRenderers = rootRenderer.getChildRenderers();
        if (childRenderers != null) {
            taggingHelper.addKidsHint((IPropertyContainer) rootRenderer, (Iterable<? extends IPropertyContainer>) childRenderers);
            for (IRenderer childRenderer : childRenderers) {
                addTreeHints(taggingHelper, childRenderer);
            }
        }
    }

    public static TaggingHintKey getHintKey(IPropertyContainer container) {
        return (TaggingHintKey) container.getProperty(109);
    }

    public static TaggingHintKey getOrCreateHintKey(IPropertyContainer container) {
        return getOrCreateHintKey(container, true);
    }

    public void addKidsHint(TagTreePointer parentPointer, Iterable<? extends IPropertyContainer> newKids) {
        PdfDictionary pointerStructElem = (PdfDictionary) this.context.getPointerStructElem(parentPointer).getPdfObject();
        TaggingDummyElement dummy = this.existingTagsDummies.get(pointerStructElem);
        if (dummy == null) {
            dummy = new TaggingDummyElement(parentPointer.getRole());
            this.existingTagsDummies.put(pointerStructElem, dummy);
        }
        this.context.getWaitingTagsManager().assignWaitingState(parentPointer, getOrCreateHintKey(dummy));
        addKidsHint((IPropertyContainer) dummy, newKids);
    }

    public void addKidsHint(IPropertyContainer parent, Iterable<? extends IPropertyContainer> newKids) {
        addKidsHint(parent, newKids, -1);
    }

    public void addKidsHint(IPropertyContainer parent, Iterable<? extends IPropertyContainer> newKids, int insertIndex) {
        if (!(parent instanceof AreaBreakRenderer)) {
            TaggingHintKey parentKey = getOrCreateHintKey(parent);
            List<TaggingHintKey> newKidsKeys = new ArrayList<>();
            for (IPropertyContainer kid : newKids) {
                if (!(kid instanceof AreaBreakRenderer)) {
                    newKidsKeys.add(getOrCreateHintKey(kid));
                } else {
                    return;
                }
            }
            addKidsHint(parentKey, (Collection<TaggingHintKey>) newKidsKeys, insertIndex);
        }
    }

    public void addKidsHint(TaggingHintKey parentKey, Collection<TaggingHintKey> newKidsKeys) {
        addKidsHint(parentKey, newKidsKeys, -1);
    }

    public void addKidsHint(TaggingHintKey parentKey, Collection<TaggingHintKey> newKidsKeys, int insertIndex) {
        addKidsHint(parentKey, newKidsKeys, insertIndex, false);
    }

    public void setRoleHint(IPropertyContainer hintOwner, String role) {
        getOrCreateHintKey(hintOwner).setOverriddenRole(role);
    }

    /* JADX WARNING: type inference failed for: r2v11, types: [com.itextpdf.layout.IPropertyContainer] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isArtifact(com.itextpdf.layout.IPropertyContainer r5) {
        /*
            r4 = this;
            com.itextpdf.layout.tagging.TaggingHintKey r0 = getHintKey(r5)
            if (r0 == 0) goto L_0x000b
            boolean r1 = r0.isArtifact()
            return r1
        L_0x000b:
            r1 = 0
            boolean r2 = r5 instanceof com.itextpdf.layout.renderer.IRenderer
            if (r2 == 0) goto L_0x0026
            r2 = r5
            com.itextpdf.layout.renderer.IRenderer r2 = (com.itextpdf.layout.renderer.IRenderer) r2
            com.itextpdf.layout.IPropertyContainer r2 = r2.getModelElement()
            boolean r2 = r2 instanceof com.itextpdf.layout.tagging.IAccessibleElement
            if (r2 == 0) goto L_0x0026
            r2 = r5
            com.itextpdf.layout.renderer.IRenderer r2 = (com.itextpdf.layout.renderer.IRenderer) r2
            com.itextpdf.layout.IPropertyContainer r2 = r2.getModelElement()
            r1 = r2
            com.itextpdf.layout.tagging.IAccessibleElement r1 = (com.itextpdf.layout.tagging.IAccessibleElement) r1
            goto L_0x002d
        L_0x0026:
            boolean r2 = r5 instanceof com.itextpdf.layout.tagging.IAccessibleElement
            if (r2 == 0) goto L_0x002d
            r1 = r5
            com.itextpdf.layout.tagging.IAccessibleElement r1 = (com.itextpdf.layout.tagging.IAccessibleElement) r1
        L_0x002d:
            if (r1 == 0) goto L_0x003e
            com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties r2 = r1.getAccessibilityProperties()
            java.lang.String r2 = r2.getRole()
            java.lang.String r3 = "Artifact"
            boolean r2 = r3.equals(r2)
            return r2
        L_0x003e:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.tagging.LayoutTaggingHelper.isArtifact(com.itextpdf.layout.IPropertyContainer):boolean");
    }

    public void markArtifactHint(IPropertyContainer hintOwner) {
        markArtifactHint(getOrCreateHintKey(hintOwner));
    }

    public void markArtifactHint(TaggingHintKey hintKey) {
        hintKey.setArtifact();
        hintKey.setFinished();
        TagTreePointer existingArtifactTag = new TagTreePointer(this.document);
        if (this.context.getWaitingTagsManager().tryMovePointerToWaitingTag(existingArtifactTag, hintKey)) {
            LoggerFactory.getLogger((Class<?>) LayoutTaggingHelper.class).error(LogMessageConstant.ALREADY_TAGGED_HINT_MARKED_ARTIFACT);
            this.context.getWaitingTagsManager().removeWaitingState(hintKey);
            if (this.immediateFlush) {
                existingArtifactTag.flushParentsIfAllKidsFlushed();
            }
        }
        for (TaggingHintKey kidKey : getKidsHint(hintKey)) {
            markArtifactHint(kidKey);
        }
        removeParentHint(hintKey);
    }

    public TagTreePointer useAutoTaggingPointerAndRememberItsPosition(IRenderer renderer) {
        TagTreePointer autoTaggingPointer = this.context.getAutoTaggingPointer();
        this.autoTaggingPointerSavedPosition.put(renderer, new TagTreePointer(autoTaggingPointer));
        return autoTaggingPointer;
    }

    public void restoreAutoTaggingPointerPosition(IRenderer renderer) {
        TagTreePointer autoTaggingPointer = this.context.getAutoTaggingPointer();
        TagTreePointer position = this.autoTaggingPointerSavedPosition.remove(renderer);
        if (position != null) {
            autoTaggingPointer.moveToPointer(position);
        }
    }

    public List<TaggingHintKey> getKidsHint(TaggingHintKey parent) {
        List<TaggingHintKey> kidsHint = this.kidsHints.get(parent);
        if (kidsHint == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(kidsHint);
    }

    public List<TaggingHintKey> getAccessibleKidsHint(TaggingHintKey parent) {
        List<TaggingHintKey> kidsHint = this.kidsHints.get(parent);
        if (kidsHint == null) {
            return Collections.emptyList();
        }
        List<TaggingHintKey> accessibleKids = new ArrayList<>();
        for (TaggingHintKey kid : kidsHint) {
            if (isNonAccessibleHint(kid)) {
                accessibleKids.addAll(getAccessibleKidsHint(kid));
            } else {
                accessibleKids.add(kid);
            }
        }
        return accessibleKids;
    }

    public TaggingHintKey getParentHint(IPropertyContainer hintOwner) {
        TaggingHintKey hintKey = getHintKey(hintOwner);
        if (hintKey == null) {
            return null;
        }
        return getParentHint(hintKey);
    }

    public TaggingHintKey getParentHint(TaggingHintKey hintKey) {
        return this.parentHints.get(hintKey);
    }

    public TaggingHintKey getAccessibleParentHint(TaggingHintKey hintKey) {
        do {
            hintKey = getParentHint(hintKey);
            if (hintKey == null || !isNonAccessibleHint(hintKey)) {
                return hintKey;
            }
            hintKey = getParentHint(hintKey);
            break;
        } while (!isNonAccessibleHint(hintKey));
        return hintKey;
    }

    public void releaseFinishedHints() {
        Set<TaggingHintKey> allHints = new HashSet<>();
        for (Map.Entry<TaggingHintKey, TaggingHintKey> entry : this.parentHints.entrySet()) {
            allHints.add(entry.getKey());
            allHints.add(entry.getValue());
        }
        for (TaggingHintKey hint : allHints) {
            if (hint.isFinished() && !isNonAccessibleHint(hint) && !(hint.getAccessibleElement() instanceof TaggingDummyElement)) {
                finishDummyKids(getKidsHint(hint));
            }
        }
        Set<TaggingHintKey> hintsToBeHeld = new HashSet<>();
        for (TaggingHintKey hint2 : allHints) {
            if (!isNonAccessibleHint(hint2)) {
                boolean holdTheFirstFinishedToBeFound = false;
                for (TaggingHintKey sibling : getAccessibleKidsHint(hint2)) {
                    if (!sibling.isFinished()) {
                        holdTheFirstFinishedToBeFound = true;
                    } else if (holdTheFirstFinishedToBeFound) {
                        hintsToBeHeld.add(sibling);
                        holdTheFirstFinishedToBeFound = false;
                    }
                }
            }
        }
        for (TaggingHintKey hint3 : allHints) {
            if (hint3.isFinished()) {
                releaseHint(hint3, hintsToBeHeld, true);
            }
        }
    }

    public void releaseAllHints() {
        for (TaggingDummyElement dummy : this.existingTagsDummies.values()) {
            finishTaggingHint(dummy);
            finishDummyKids(getKidsHint(getHintKey(dummy)));
        }
        this.existingTagsDummies.clear();
        releaseFinishedHints();
        Set<TaggingHintKey> hangingHints = new HashSet<>();
        for (Map.Entry<TaggingHintKey, TaggingHintKey> entry : this.parentHints.entrySet()) {
            hangingHints.add(entry.getKey());
            hangingHints.add(entry.getValue());
        }
        for (TaggingHintKey hint : hangingHints) {
            releaseHint(hint, (Set<TaggingHintKey>) null, false);
        }
        if (!this.parentHints.isEmpty()) {
            throw new AssertionError();
        } else if (!this.kidsHints.isEmpty()) {
            throw new AssertionError();
        }
    }

    public boolean createTag(IRenderer renderer, TagTreePointer tagPointer) {
        TaggingHintKey hintKey = getHintKey(renderer);
        boolean noHint = hintKey == null;
        if (noHint) {
            hintKey = getOrCreateHintKey(renderer, false);
        }
        boolean created = createTag(hintKey, tagPointer);
        if (noHint) {
            hintKey.setFinished();
            this.context.getWaitingTagsManager().removeWaitingState(hintKey);
        }
        return created;
    }

    public boolean createTag(TaggingHintKey hintKey, TagTreePointer tagPointer) {
        if (hintKey.isArtifact()) {
            return false;
        }
        boolean created = createSingleTag(hintKey, tagPointer);
        if (created) {
            for (TaggingHintKey hint : getAccessibleKidsHint(hintKey)) {
                if (hint.getAccessibleElement() instanceof TaggingDummyElement) {
                    createTag(hint, new TagTreePointer(this.document));
                }
            }
        }
        return created;
    }

    public void finishTaggingHint(IPropertyContainer hintOwner) {
        TaggingHintKey rendererKey = getHintKey(hintOwner);
        if (rendererKey != null && !rendererKey.isFinished()) {
            if (!rendererKey.isElementBasedFinishingOnly() || (hintOwner instanceof IElement)) {
                if (!isNonAccessibleHint(rendererKey)) {
                    String role = rendererKey.getAccessibleElement().getAccessibilityProperties().getRole();
                    if (rendererKey.getOverriddenRole() != null) {
                        role = rendererKey.getOverriddenRole();
                    }
                    List<ITaggingRule> rules = this.taggingRules.get(role);
                    boolean ruleResult = true;
                    if (rules != null) {
                        for (ITaggingRule rule : rules) {
                            ruleResult = ruleResult && rule.onTagFinish(this, rendererKey);
                        }
                    }
                    if (!ruleResult) {
                        return;
                    }
                }
                rendererKey.setFinished();
            }
        }
    }

    public int replaceKidHint(TaggingHintKey kidHintKey, Collection<TaggingHintKey> newKidsHintKeys) {
        TaggingHintKey parentKey = getParentHint(kidHintKey);
        if (parentKey == null) {
            return -1;
        }
        Class<LayoutTaggingHelper> cls = LayoutTaggingHelper.class;
        if (kidHintKey.isFinished()) {
            LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.CANNOT_REPLACE_FINISHED_HINT);
            return -1;
        }
        int kidIndex = removeParentHint(kidHintKey);
        List<TaggingHintKey> kidsToBeAdded = new ArrayList<>();
        for (TaggingHintKey newKidKey : newKidsHintKeys) {
            int i = removeParentHint(newKidKey);
            if (i == -2 || (i == -1 && newKidKey.isFinished())) {
                LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.CANNOT_MOVE_FINISHED_HINT);
            } else {
                kidsToBeAdded.add(newKidKey);
            }
        }
        addKidsHint(parentKey, kidsToBeAdded, kidIndex, true);
        return kidIndex;
    }

    public int moveKidHint(TaggingHintKey hintKeyOfKidToMove, TaggingHintKey newParent) {
        return moveKidHint(hintKeyOfKidToMove, newParent, -1);
    }

    public int moveKidHint(TaggingHintKey hintKeyOfKidToMove, TaggingHintKey newParent, int insertIndex) {
        Class<LayoutTaggingHelper> cls = LayoutTaggingHelper.class;
        if (newParent.isFinished()) {
            LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.CANNOT_MOVE_HINT_TO_FINISHED_PARENT);
            return -1;
        }
        int removeRes = removeParentHint(hintKeyOfKidToMove);
        if (removeRes == -2 || (removeRes == -1 && hintKeyOfKidToMove.isFinished())) {
            LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.CANNOT_MOVE_FINISHED_HINT);
            return -1;
        }
        addKidsHint(newParent, Collections.singletonList(hintKeyOfKidToMove), insertIndex, true);
        return removeRes;
    }

    public PdfDocument getPdfDocument() {
        return this.document;
    }

    /* JADX WARNING: type inference failed for: r3v18, types: [com.itextpdf.layout.IPropertyContainer] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.itextpdf.layout.tagging.TaggingHintKey getOrCreateHintKey(com.itextpdf.layout.IPropertyContainer r5, boolean r6) {
        /*
            r0 = 109(0x6d, float:1.53E-43)
            java.lang.Object r1 = r5.getProperty(r0)
            com.itextpdf.layout.tagging.TaggingHintKey r1 = (com.itextpdf.layout.tagging.TaggingHintKey) r1
            if (r1 != 0) goto L_0x0065
            r2 = 0
            boolean r3 = r5 instanceof com.itextpdf.layout.tagging.IAccessibleElement
            if (r3 == 0) goto L_0x0013
            r2 = r5
            com.itextpdf.layout.tagging.IAccessibleElement r2 = (com.itextpdf.layout.tagging.IAccessibleElement) r2
            goto L_0x002c
        L_0x0013:
            boolean r3 = r5 instanceof com.itextpdf.layout.renderer.IRenderer
            if (r3 == 0) goto L_0x002c
            r3 = r5
            com.itextpdf.layout.renderer.IRenderer r3 = (com.itextpdf.layout.renderer.IRenderer) r3
            com.itextpdf.layout.IPropertyContainer r3 = r3.getModelElement()
            boolean r3 = r3 instanceof com.itextpdf.layout.tagging.IAccessibleElement
            if (r3 == 0) goto L_0x002c
            r3 = r5
            com.itextpdf.layout.renderer.IRenderer r3 = (com.itextpdf.layout.renderer.IRenderer) r3
            com.itextpdf.layout.IPropertyContainer r3 = r3.getModelElement()
            r2 = r3
            com.itextpdf.layout.tagging.IAccessibleElement r2 = (com.itextpdf.layout.tagging.IAccessibleElement) r2
        L_0x002c:
            com.itextpdf.layout.tagging.TaggingHintKey r3 = new com.itextpdf.layout.tagging.TaggingHintKey
            boolean r4 = r5 instanceof com.itextpdf.layout.element.IElement
            r3.<init>(r2, r4)
            r1 = r3
            if (r2 == 0) goto L_0x004c
            com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties r3 = r2.getAccessibilityProperties()
            java.lang.String r3 = r3.getRole()
            java.lang.String r4 = "Artifact"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x004c
            r1.setArtifact()
            r1.setFinished()
        L_0x004c:
            if (r6 == 0) goto L_0x0065
            boolean r3 = r2 instanceof com.itextpdf.layout.element.ILargeElement
            if (r3 == 0) goto L_0x0062
            r3 = r2
            com.itextpdf.layout.element.ILargeElement r3 = (com.itextpdf.layout.element.ILargeElement) r3
            boolean r3 = r3.isComplete()
            if (r3 != 0) goto L_0x0062
            r3 = r2
            com.itextpdf.layout.element.ILargeElement r3 = (com.itextpdf.layout.element.ILargeElement) r3
            r3.setProperty(r0, r1)
            goto L_0x0065
        L_0x0062:
            r5.setProperty(r0, r1)
        L_0x0065:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.tagging.LayoutTaggingHelper.getOrCreateHintKey(com.itextpdf.layout.IPropertyContainer, boolean):com.itextpdf.layout.tagging.TaggingHintKey");
    }

    private void addKidsHint(TaggingHintKey parentKey, Collection<TaggingHintKey> newKidsKeys, int insertIndex, boolean skipFinishedChecks) {
        if (!newKidsKeys.isEmpty()) {
            if (parentKey.isArtifact()) {
                for (TaggingHintKey kid : newKidsKeys) {
                    markArtifactHint(kid);
                }
                return;
            }
            Class<LayoutTaggingHelper> cls = LayoutTaggingHelper.class;
            if (skipFinishedChecks || !parentKey.isFinished()) {
                List<TaggingHintKey> kidsHint = this.kidsHints.get(parentKey);
                if (kidsHint == null) {
                    kidsHint = new ArrayList<>();
                }
                TaggingHintKey parentTagHint = isNonAccessibleHint(parentKey) ? getAccessibleParentHint(parentKey) : parentKey;
                boolean parentTagAlreadyCreated = parentTagHint != null && isTagAlreadyExistsForHint(parentTagHint);
                for (TaggingHintKey kidKey : newKidsKeys) {
                    if (!kidKey.isArtifact() && getParentHint(kidKey) == null) {
                        if (skipFinishedChecks || !kidKey.isFinished()) {
                            if (insertIndex > -1) {
                                kidsHint.add(insertIndex, kidKey);
                                insertIndex++;
                            } else {
                                kidsHint.add(kidKey);
                            }
                            this.parentHints.put(kidKey, parentKey);
                            if (parentTagAlreadyCreated) {
                                if (kidKey.getAccessibleElement() instanceof TaggingDummyElement) {
                                    createTag(kidKey, new TagTreePointer(this.document));
                                }
                                if (isNonAccessibleHint(kidKey)) {
                                    for (TaggingHintKey nestedKid : getAccessibleKidsHint(kidKey)) {
                                        if (nestedKid.getAccessibleElement() instanceof TaggingDummyElement) {
                                            createTag(nestedKid, new TagTreePointer(this.document));
                                        }
                                        moveKidTagIfCreated(parentTagHint, nestedKid);
                                    }
                                } else {
                                    moveKidTagIfCreated(parentTagHint, kidKey);
                                }
                            }
                        } else {
                            LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.CANNOT_ADD_FINISHED_HINT_AS_A_NEW_KID_HINT);
                        }
                    }
                }
                if (!kidsHint.isEmpty()) {
                    this.kidsHints.put(parentKey, kidsHint);
                    return;
                }
                return;
            }
            LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.CANNOT_ADD_HINTS_TO_FINISHED_PARENT);
        }
    }

    private boolean createSingleTag(TaggingHintKey hintKey, TagTreePointer tagPointer) {
        if (hintKey.isFinished()) {
            LoggerFactory.getLogger((Class<?>) LayoutTaggingHelper.class).error(LogMessageConstant.ATTEMPT_TO_CREATE_A_TAG_FOR_FINISHED_HINT);
            return false;
        } else if (isNonAccessibleHint(hintKey)) {
            this.context.getWaitingTagsManager().tryMovePointerToWaitingTag(tagPointer, getAccessibleParentHint(hintKey));
            return false;
        } else {
            WaitingTagsManager waitingTagsManager = this.context.getWaitingTagsManager();
            if (waitingTagsManager.tryMovePointerToWaitingTag(tagPointer, hintKey)) {
                return false;
            }
            IAccessibleElement modelElement = hintKey.getAccessibleElement();
            TaggingHintKey parentHint = getAccessibleParentHint(hintKey);
            int ind = -1;
            if (parentHint != null && waitingTagsManager.tryMovePointerToWaitingTag(tagPointer, parentHint)) {
                List<TaggingHintKey> siblingsHint = getAccessibleKidsHint(parentHint);
                ind = getNearestNextSiblingTagIndex(waitingTagsManager, tagPointer, siblingsHint, siblingsHint.indexOf(hintKey));
            }
            tagPointer.addTag(ind, modelElement.getAccessibilityProperties());
            if (hintKey.getOverriddenRole() != null) {
                tagPointer.setRole(hintKey.getOverriddenRole());
            }
            waitingTagsManager.assignWaitingState(tagPointer, hintKey);
            for (TaggingHintKey kidKey : getAccessibleKidsHint(hintKey)) {
                moveKidTagIfCreated(hintKey, kidKey);
            }
            return true;
        }
    }

    private int removeParentHint(TaggingHintKey hintKey) {
        TaggingHintKey parentHint = this.parentHints.get(hintKey);
        if (parentHint == null) {
            return -1;
        }
        TaggingHintKey accessibleParentHint = getAccessibleParentHint(hintKey);
        if (!hintKey.isFinished() || !parentHint.isFinished() || (accessibleParentHint != null && !accessibleParentHint.isFinished())) {
            return removeParentHint(hintKey, parentHint);
        }
        return -2;
    }

    private int removeParentHint(TaggingHintKey hintKey, TaggingHintKey parentHint) {
        this.parentHints.remove(hintKey);
        List<TaggingHintKey> kidsHint = this.kidsHints.get(parentHint);
        int size = kidsHint.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            } else if (kidsHint.get(i) == hintKey) {
                kidsHint.remove(i);
                break;
            } else {
                i++;
            }
        }
        if (i < size) {
            if (kidsHint.isEmpty()) {
                this.kidsHints.remove(parentHint);
            }
            return i;
        }
        throw new AssertionError();
    }

    private void finishDummyKids(List<TaggingHintKey> taggingHintKeys) {
        for (TaggingHintKey hintKey : taggingHintKeys) {
            boolean isDummy = hintKey.getAccessibleElement() instanceof TaggingDummyElement;
            if (isDummy) {
                finishTaggingHint((IPropertyContainer) hintKey.getAccessibleElement());
            }
            if (isNonAccessibleHint(hintKey) || isDummy) {
                finishDummyKids(getKidsHint(hintKey));
            }
        }
    }

    private void moveKidTagIfCreated(TaggingHintKey parentKey, TaggingHintKey kidKey) {
        TagTreePointer kidPointer = new TagTreePointer(this.document);
        WaitingTagsManager waitingTagsManager = this.context.getWaitingTagsManager();
        if (waitingTagsManager.tryMovePointerToWaitingTag(kidPointer, kidKey)) {
            TagTreePointer parentPointer = new TagTreePointer(this.document);
            if (waitingTagsManager.tryMovePointerToWaitingTag(parentPointer, parentKey)) {
                parentPointer.setNextNewKidIndex(getNearestNextSiblingTagIndex(waitingTagsManager, parentPointer, getAccessibleKidsHint(parentKey), getAccessibleKidsHint(parentKey).indexOf(kidKey)));
                kidPointer.relocate(parentPointer);
            }
        }
    }

    private int getNearestNextSiblingTagIndex(WaitingTagsManager waitingTagsManager, TagTreePointer parentPointer, List<TaggingHintKey> siblingsHint, int start) {
        TagTreePointer nextSiblingPointer = new TagTreePointer(this.document);
        while (true) {
            start++;
            if (start >= siblingsHint.size()) {
                return -1;
            }
            if (waitingTagsManager.tryMovePointerToWaitingTag(nextSiblingPointer, siblingsHint.get(start)) && parentPointer.isPointingToSameTag(new TagTreePointer(nextSiblingPointer).moveToParent())) {
                return nextSiblingPointer.getIndexInParentKidsList();
            }
        }
    }

    private static boolean isNonAccessibleHint(TaggingHintKey hintKey) {
        return hintKey.getAccessibleElement() == null || hintKey.getAccessibleElement().getAccessibilityProperties().getRole() == null;
    }

    private boolean isTagAlreadyExistsForHint(TaggingHintKey tagHint) {
        return this.context.getWaitingTagsManager().isObjectAssociatedWithWaitingTag(tagHint);
    }

    private void releaseHint(TaggingHintKey hint, Set<TaggingHintKey> hintsToBeHeld, boolean checkContextIsFinished) {
        TaggingHintKey parentHint = this.parentHints.get(hint);
        List<TaggingHintKey> kidsHint = this.kidsHints.get(hint);
        if (checkContextIsFinished && parentHint != null && isSomeParentNotFinished(parentHint)) {
            return;
        }
        if (checkContextIsFinished && kidsHint != null && isSomeKidNotFinished(hint)) {
            return;
        }
        if (!checkContextIsFinished || hintsToBeHeld == null || !hintsToBeHeld.contains(hint)) {
            if (parentHint != null) {
                removeParentHint(hint, parentHint);
            }
            if (kidsHint != null) {
                for (TaggingHintKey kidHint : kidsHint) {
                    this.parentHints.remove(kidHint);
                }
                this.kidsHints.remove(hint);
            }
            TagTreePointer tagPointer = new TagTreePointer(this.document);
            if (this.context.getWaitingTagsManager().tryMovePointerToWaitingTag(tagPointer, hint)) {
                this.context.getWaitingTagsManager().removeWaitingState(hint);
                if (this.immediateFlush) {
                    tagPointer.flushParentsIfAllKidsFlushed();
                    return;
                }
                return;
            }
            this.context.getWaitingTagsManager().removeWaitingState(hint);
        }
    }

    private boolean isSomeParentNotFinished(TaggingHintKey parentHint) {
        TaggingHintKey hintKey = parentHint;
        while (hintKey != null) {
            if (!hintKey.isFinished()) {
                return true;
            }
            if (!isNonAccessibleHint(hintKey)) {
                return false;
            }
            hintKey = getParentHint(hintKey);
        }
        return false;
    }

    private boolean isSomeKidNotFinished(TaggingHintKey hint) {
        for (TaggingHintKey kidHint : getKidsHint(hint)) {
            if (!kidHint.isFinished()) {
                return true;
            }
            if (isNonAccessibleHint(kidHint) && isSomeKidNotFinished(kidHint)) {
                return true;
            }
        }
        return false;
    }

    private void registerRules(PdfVersion pdfVersion) {
        ITaggingRule tableRule = new TableTaggingRule();
        registerSingleRule(StandardRoles.TABLE, tableRule);
        registerSingleRule(StandardRoles.TFOOT, tableRule);
        registerSingleRule(StandardRoles.THEAD, tableRule);
        if (pdfVersion.compareTo(PdfVersion.PDF_1_5) < 0) {
            TableTaggingPriorToOneFiveVersionRule priorToOneFiveRule = new TableTaggingPriorToOneFiveVersionRule();
            registerSingleRule(StandardRoles.TABLE, priorToOneFiveRule);
            registerSingleRule(StandardRoles.THEAD, priorToOneFiveRule);
            registerSingleRule(StandardRoles.TFOOT, priorToOneFiveRule);
        }
    }

    private void registerSingleRule(String role, ITaggingRule rule) {
        List<ITaggingRule> rules = this.taggingRules.get(role);
        if (rules == null) {
            rules = new ArrayList<>();
            this.taggingRules.put(role, rules);
        }
        rules.add(rule);
    }
}
