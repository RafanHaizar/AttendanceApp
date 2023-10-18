package androidx.core.view.accessibility;

import android.graphics.Rect;
import android.graphics.Region;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.C0672R;
import androidx.core.p001os.BuildCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AccessibilityNodeInfoCompat {
    public static final int ACTION_ACCESSIBILITY_FOCUS = 64;
    public static final String ACTION_ARGUMENT_COLUMN_INT = "android.view.accessibility.action.ARGUMENT_COLUMN_INT";
    public static final String ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN = "ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN";
    public static final String ACTION_ARGUMENT_HTML_ELEMENT_STRING = "ACTION_ARGUMENT_HTML_ELEMENT_STRING";
    public static final String ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT = "ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT";
    public static final String ACTION_ARGUMENT_MOVE_WINDOW_X = "ACTION_ARGUMENT_MOVE_WINDOW_X";
    public static final String ACTION_ARGUMENT_MOVE_WINDOW_Y = "ACTION_ARGUMENT_MOVE_WINDOW_Y";
    public static final String ACTION_ARGUMENT_PRESS_AND_HOLD_DURATION_MILLIS_INT = "android.view.accessibility.action.ARGUMENT_PRESS_AND_HOLD_DURATION_MILLIS_INT";
    public static final String ACTION_ARGUMENT_PROGRESS_VALUE = "android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE";
    public static final String ACTION_ARGUMENT_ROW_INT = "android.view.accessibility.action.ARGUMENT_ROW_INT";
    public static final String ACTION_ARGUMENT_SELECTION_END_INT = "ACTION_ARGUMENT_SELECTION_END_INT";
    public static final String ACTION_ARGUMENT_SELECTION_START_INT = "ACTION_ARGUMENT_SELECTION_START_INT";
    public static final String ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE = "ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE";
    public static final int ACTION_CLEAR_ACCESSIBILITY_FOCUS = 128;
    public static final int ACTION_CLEAR_FOCUS = 2;
    public static final int ACTION_CLEAR_SELECTION = 8;
    public static final int ACTION_CLICK = 16;
    public static final int ACTION_COLLAPSE = 524288;
    public static final int ACTION_COPY = 16384;
    public static final int ACTION_CUT = 65536;
    public static final int ACTION_DISMISS = 1048576;
    public static final int ACTION_EXPAND = 262144;
    public static final int ACTION_FOCUS = 1;
    public static final int ACTION_LONG_CLICK = 32;
    public static final int ACTION_NEXT_AT_MOVEMENT_GRANULARITY = 256;
    public static final int ACTION_NEXT_HTML_ELEMENT = 1024;
    public static final int ACTION_PASTE = 32768;
    public static final int ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = 512;
    public static final int ACTION_PREVIOUS_HTML_ELEMENT = 2048;
    public static final int ACTION_SCROLL_BACKWARD = 8192;
    public static final int ACTION_SCROLL_FORWARD = 4096;
    public static final int ACTION_SELECT = 4;
    public static final int ACTION_SET_SELECTION = 131072;
    public static final int ACTION_SET_TEXT = 2097152;
    private static final int BOOLEAN_PROPERTY_IS_HEADING = 2;
    private static final int BOOLEAN_PROPERTY_IS_SHOWING_HINT = 4;
    private static final int BOOLEAN_PROPERTY_IS_TEXT_ENTRY_KEY = 8;
    private static final String BOOLEAN_PROPERTY_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.BOOLEAN_PROPERTY_KEY";
    private static final int BOOLEAN_PROPERTY_SCREEN_READER_FOCUSABLE = 1;
    public static final String EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH = "android.core.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH";
    public static final int EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_MAX_LENGTH = 20000;
    public static final String EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_START_INDEX = "android.core.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_ARG_START_INDEX";
    public static final String EXTRA_DATA_TEXT_CHARACTER_LOCATION_KEY = "android.core.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_KEY";
    public static final int FOCUS_ACCESSIBILITY = 2;
    public static final int FOCUS_INPUT = 1;
    private static final String HINT_TEXT_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.HINT_TEXT_KEY";
    public static final int MOVEMENT_GRANULARITY_CHARACTER = 1;
    public static final int MOVEMENT_GRANULARITY_LINE = 4;
    public static final int MOVEMENT_GRANULARITY_PAGE = 16;
    public static final int MOVEMENT_GRANULARITY_PARAGRAPH = 8;
    public static final int MOVEMENT_GRANULARITY_WORD = 2;
    private static final String PANE_TITLE_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.PANE_TITLE_KEY";
    private static final String ROLE_DESCRIPTION_KEY = "AccessibilityNodeInfo.roleDescription";
    private static final String SPANS_ACTION_ID_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ACTION_ID_KEY";
    private static final String SPANS_END_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_END_KEY";
    private static final String SPANS_FLAGS_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_FLAGS_KEY";
    private static final String SPANS_ID_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ID_KEY";
    private static final String SPANS_START_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY";
    private static final String STATE_DESCRIPTION_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.STATE_DESCRIPTION_KEY";
    private static final String TOOLTIP_TEXT_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.TOOLTIP_TEXT_KEY";
    private static final String UNIQUE_ID_KEY = "androidx.view.accessibility.AccessibilityNodeInfoCompat.UNIQUE_ID_KEY";
    private static int sClickableSpanId = 0;
    private final AccessibilityNodeInfo mInfo;
    public int mParentVirtualDescendantId = -1;
    private int mVirtualDescendantId = -1;

    public static class AccessibilityActionCompat {
        public static final AccessibilityActionCompat ACTION_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(64, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_CLEAR_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(128, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_CLEAR_FOCUS = new AccessibilityActionCompat(2, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_CLEAR_SELECTION = new AccessibilityActionCompat(8, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_CLICK = new AccessibilityActionCompat(16, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_COLLAPSE = new AccessibilityActionCompat(524288, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_CONTEXT_CLICK;
        public static final AccessibilityActionCompat ACTION_COPY = new AccessibilityActionCompat(16384, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_CUT = new AccessibilityActionCompat(65536, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_DISMISS = new AccessibilityActionCompat(1048576, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_DRAG_CANCEL = new AccessibilityActionCompat(Build.VERSION.SDK_INT >= 32 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_DRAG_CANCEL : null, 16908375, (CharSequence) null, (AccessibilityViewCommand) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) null);
        public static final AccessibilityActionCompat ACTION_DRAG_DROP;
        public static final AccessibilityActionCompat ACTION_DRAG_START;
        public static final AccessibilityActionCompat ACTION_EXPAND = new AccessibilityActionCompat(262144, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_FOCUS = new AccessibilityActionCompat(1, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_HIDE_TOOLTIP;
        public static final AccessibilityActionCompat ACTION_IME_ENTER;
        public static final AccessibilityActionCompat ACTION_LONG_CLICK = new AccessibilityActionCompat(32, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_MOVE_WINDOW = new AccessibilityActionCompat(Build.VERSION.SDK_INT >= 26 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_MOVE_WINDOW : null, 16908354, (CharSequence) null, (AccessibilityViewCommand) null, AccessibilityViewCommand.MoveWindowArguments.class);
        public static final AccessibilityActionCompat ACTION_NEXT_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(256, (CharSequence) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) AccessibilityViewCommand.MoveAtGranularityArguments.class);
        public static final AccessibilityActionCompat ACTION_NEXT_HTML_ELEMENT = new AccessibilityActionCompat(1024, (CharSequence) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) AccessibilityViewCommand.MoveHtmlArguments.class);
        public static final AccessibilityActionCompat ACTION_PAGE_DOWN = new AccessibilityActionCompat(Build.VERSION.SDK_INT >= 29 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_DOWN : null, 16908359, (CharSequence) null, (AccessibilityViewCommand) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) null);
        public static final AccessibilityActionCompat ACTION_PAGE_LEFT = new AccessibilityActionCompat(Build.VERSION.SDK_INT >= 29 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_LEFT : null, 16908360, (CharSequence) null, (AccessibilityViewCommand) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) null);
        public static final AccessibilityActionCompat ACTION_PAGE_RIGHT = new AccessibilityActionCompat(Build.VERSION.SDK_INT >= 29 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_RIGHT : null, 16908361, (CharSequence) null, (AccessibilityViewCommand) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) null);
        public static final AccessibilityActionCompat ACTION_PAGE_UP = new AccessibilityActionCompat(Build.VERSION.SDK_INT >= 29 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_UP : null, 16908358, (CharSequence) null, (AccessibilityViewCommand) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) null);
        public static final AccessibilityActionCompat ACTION_PASTE = new AccessibilityActionCompat(32768, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_PRESS_AND_HOLD;
        public static final AccessibilityActionCompat ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(512, (CharSequence) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) AccessibilityViewCommand.MoveAtGranularityArguments.class);
        public static final AccessibilityActionCompat ACTION_PREVIOUS_HTML_ELEMENT = new AccessibilityActionCompat(2048, (CharSequence) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) AccessibilityViewCommand.MoveHtmlArguments.class);
        public static final AccessibilityActionCompat ACTION_SCROLL_BACKWARD = new AccessibilityActionCompat(8192, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_SCROLL_DOWN = new AccessibilityActionCompat(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN : null, 16908346, (CharSequence) null, (AccessibilityViewCommand) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) null);
        public static final AccessibilityActionCompat ACTION_SCROLL_FORWARD = new AccessibilityActionCompat(4096, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_SCROLL_LEFT = new AccessibilityActionCompat(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT : null, 16908345, (CharSequence) null, (AccessibilityViewCommand) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) null);
        public static final AccessibilityActionCompat ACTION_SCROLL_RIGHT = new AccessibilityActionCompat(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT : null, 16908347, (CharSequence) null, (AccessibilityViewCommand) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) null);
        public static final AccessibilityActionCompat ACTION_SCROLL_TO_POSITION;
        public static final AccessibilityActionCompat ACTION_SCROLL_UP = new AccessibilityActionCompat(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP : null, 16908344, (CharSequence) null, (AccessibilityViewCommand) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) null);
        public static final AccessibilityActionCompat ACTION_SELECT = new AccessibilityActionCompat(4, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_SET_PROGRESS;
        public static final AccessibilityActionCompat ACTION_SET_SELECTION = new AccessibilityActionCompat(131072, (CharSequence) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) AccessibilityViewCommand.SetSelectionArguments.class);
        public static final AccessibilityActionCompat ACTION_SET_TEXT = new AccessibilityActionCompat(2097152, (CharSequence) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) AccessibilityViewCommand.SetTextArguments.class);
        public static final AccessibilityActionCompat ACTION_SHOW_ON_SCREEN;
        public static final AccessibilityActionCompat ACTION_SHOW_TEXT_SUGGESTIONS;
        public static final AccessibilityActionCompat ACTION_SHOW_TOOLTIP = new AccessibilityActionCompat(Build.VERSION.SDK_INT >= 28 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TOOLTIP : null, 16908356, (CharSequence) null, (AccessibilityViewCommand) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) null);
        private static final String TAG = "A11yActionCompat";
        final Object mAction;
        protected final AccessibilityViewCommand mCommand;
        private final int mId;
        private final Class<? extends AccessibilityViewCommand.CommandArguments> mViewCommandArgumentClass;

        static {
            AccessibilityNodeInfo.AccessibilityAction accessibilityAction;
            AccessibilityNodeInfo.AccessibilityAction accessibilityAction2;
            AccessibilityNodeInfo.AccessibilityAction accessibilityAction3;
            AccessibilityNodeInfo.AccessibilityAction accessibilityAction4;
            AccessibilityNodeInfo.AccessibilityAction accessibilityAction5;
            AccessibilityNodeInfo.AccessibilityAction accessibilityAction6;
            AccessibilityNodeInfo.AccessibilityAction accessibilityAction7;
            AccessibilityNodeInfo.AccessibilityAction accessibilityAction8;
            AccessibilityNodeInfo.AccessibilityAction accessibilityAction9;
            AccessibilityNodeInfo.AccessibilityAction accessibilityAction10;
            if (Build.VERSION.SDK_INT >= 23) {
                accessibilityAction = AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN;
            } else {
                accessibilityAction = null;
            }
            ACTION_SHOW_ON_SCREEN = new AccessibilityActionCompat(accessibilityAction, 16908342, (CharSequence) null, (AccessibilityViewCommand) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) null);
            if (Build.VERSION.SDK_INT >= 23) {
                accessibilityAction2 = AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION;
            } else {
                accessibilityAction2 = null;
            }
            ACTION_SCROLL_TO_POSITION = new AccessibilityActionCompat(accessibilityAction2, 16908343, (CharSequence) null, (AccessibilityViewCommand) null, AccessibilityViewCommand.ScrollToPositionArguments.class);
            if (Build.VERSION.SDK_INT >= 23) {
                accessibilityAction3 = AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK;
            } else {
                accessibilityAction3 = null;
            }
            ACTION_CONTEXT_CLICK = new AccessibilityActionCompat(accessibilityAction3, 16908348, (CharSequence) null, (AccessibilityViewCommand) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) null);
            if (Build.VERSION.SDK_INT >= 24) {
                accessibilityAction4 = AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS;
            } else {
                accessibilityAction4 = null;
            }
            ACTION_SET_PROGRESS = new AccessibilityActionCompat(accessibilityAction4, 16908349, (CharSequence) null, (AccessibilityViewCommand) null, AccessibilityViewCommand.SetProgressArguments.class);
            if (Build.VERSION.SDK_INT >= 28) {
                accessibilityAction5 = AccessibilityNodeInfo.AccessibilityAction.ACTION_HIDE_TOOLTIP;
            } else {
                accessibilityAction5 = null;
            }
            ACTION_HIDE_TOOLTIP = new AccessibilityActionCompat(accessibilityAction5, 16908357, (CharSequence) null, (AccessibilityViewCommand) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) null);
            if (Build.VERSION.SDK_INT >= 30) {
                accessibilityAction6 = AccessibilityNodeInfo.AccessibilityAction.ACTION_PRESS_AND_HOLD;
            } else {
                accessibilityAction6 = null;
            }
            ACTION_PRESS_AND_HOLD = new AccessibilityActionCompat(accessibilityAction6, 16908362, (CharSequence) null, (AccessibilityViewCommand) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) null);
            if (Build.VERSION.SDK_INT >= 30) {
                accessibilityAction7 = AccessibilityNodeInfo.AccessibilityAction.ACTION_IME_ENTER;
            } else {
                accessibilityAction7 = null;
            }
            ACTION_IME_ENTER = new AccessibilityActionCompat(accessibilityAction7, 16908372, (CharSequence) null, (AccessibilityViewCommand) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) null);
            if (Build.VERSION.SDK_INT >= 32) {
                accessibilityAction8 = AccessibilityNodeInfo.AccessibilityAction.ACTION_DRAG_START;
            } else {
                accessibilityAction8 = null;
            }
            ACTION_DRAG_START = new AccessibilityActionCompat(accessibilityAction8, 16908373, (CharSequence) null, (AccessibilityViewCommand) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) null);
            if (Build.VERSION.SDK_INT >= 32) {
                accessibilityAction9 = AccessibilityNodeInfo.AccessibilityAction.ACTION_DRAG_DROP;
            } else {
                accessibilityAction9 = null;
            }
            ACTION_DRAG_DROP = new AccessibilityActionCompat(accessibilityAction9, 16908374, (CharSequence) null, (AccessibilityViewCommand) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) null);
            if (Build.VERSION.SDK_INT >= 33) {
                accessibilityAction10 = AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TEXT_SUGGESTIONS;
            } else {
                accessibilityAction10 = null;
            }
            ACTION_SHOW_TEXT_SUGGESTIONS = new AccessibilityActionCompat(accessibilityAction10, 16908376, (CharSequence) null, (AccessibilityViewCommand) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) null);
        }

        public AccessibilityActionCompat(int actionId, CharSequence label) {
            this((Object) null, actionId, label, (AccessibilityViewCommand) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) null);
        }

        public AccessibilityActionCompat(int actionId, CharSequence label, AccessibilityViewCommand command) {
            this((Object) null, actionId, label, command, (Class<? extends AccessibilityViewCommand.CommandArguments>) null);
        }

        AccessibilityActionCompat(Object action) {
            this(action, 0, (CharSequence) null, (AccessibilityViewCommand) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) null);
        }

        private AccessibilityActionCompat(int actionId, CharSequence label, Class<? extends AccessibilityViewCommand.CommandArguments> viewCommandArgumentClass) {
            this((Object) null, actionId, label, (AccessibilityViewCommand) null, viewCommandArgumentClass);
        }

        AccessibilityActionCompat(Object action, int id, CharSequence label, AccessibilityViewCommand command, Class<? extends AccessibilityViewCommand.CommandArguments> viewCommandArgumentClass) {
            this.mId = id;
            this.mCommand = command;
            if (Build.VERSION.SDK_INT < 21 || action != null) {
                this.mAction = action;
            } else {
                this.mAction = new AccessibilityNodeInfo.AccessibilityAction(id, label);
            }
            this.mViewCommandArgumentClass = viewCommandArgumentClass;
        }

        public int getId() {
            if (Build.VERSION.SDK_INT >= 21) {
                return ((AccessibilityNodeInfo.AccessibilityAction) this.mAction).getId();
            }
            return 0;
        }

        public CharSequence getLabel() {
            if (Build.VERSION.SDK_INT >= 21) {
                return ((AccessibilityNodeInfo.AccessibilityAction) this.mAction).getLabel();
            }
            return null;
        }

        public boolean perform(View view, Bundle arguments) {
            if (this.mCommand == null) {
                return false;
            }
            AccessibilityViewCommand.CommandArguments viewCommandArgument = null;
            Class<? extends AccessibilityViewCommand.CommandArguments> cls = this.mViewCommandArgumentClass;
            if (cls != null) {
                try {
                    viewCommandArgument = (AccessibilityViewCommand.CommandArguments) cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                    viewCommandArgument.setBundle(arguments);
                } catch (Exception e) {
                    Class<? extends AccessibilityViewCommand.CommandArguments> cls2 = this.mViewCommandArgumentClass;
                    Log.e(TAG, "Failed to execute command with argument class ViewCommandArgument: " + (cls2 == null ? "null" : cls2.getName()), e);
                }
            }
            return this.mCommand.perform(view, viewCommandArgument);
        }

        public AccessibilityActionCompat createReplacementAction(CharSequence label, AccessibilityViewCommand command) {
            return new AccessibilityActionCompat((Object) null, this.mId, label, command, this.mViewCommandArgumentClass);
        }

        public int hashCode() {
            Object obj = this.mAction;
            if (obj != null) {
                return obj.hashCode();
            }
            return 0;
        }

        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof AccessibilityActionCompat)) {
                return false;
            }
            AccessibilityActionCompat other = (AccessibilityActionCompat) obj;
            Object obj2 = this.mAction;
            if (obj2 == null) {
                if (other.mAction != null) {
                    return false;
                }
                return true;
            } else if (!obj2.equals(other.mAction)) {
                return false;
            } else {
                return true;
            }
        }
    }

    public static class CollectionInfoCompat {
        public static final int SELECTION_MODE_MULTIPLE = 2;
        public static final int SELECTION_MODE_NONE = 0;
        public static final int SELECTION_MODE_SINGLE = 1;
        final Object mInfo;

        public static CollectionInfoCompat obtain(int rowCount, int columnCount, boolean hierarchical, int selectionMode) {
            if (Build.VERSION.SDK_INT >= 21) {
                return new CollectionInfoCompat(AccessibilityNodeInfo.CollectionInfo.obtain(rowCount, columnCount, hierarchical, selectionMode));
            }
            return new CollectionInfoCompat(AccessibilityNodeInfo.CollectionInfo.obtain(rowCount, columnCount, hierarchical));
        }

        public static CollectionInfoCompat obtain(int rowCount, int columnCount, boolean hierarchical) {
            return new CollectionInfoCompat(AccessibilityNodeInfo.CollectionInfo.obtain(rowCount, columnCount, hierarchical));
        }

        CollectionInfoCompat(Object info) {
            this.mInfo = info;
        }

        public int getColumnCount() {
            return ((AccessibilityNodeInfo.CollectionInfo) this.mInfo).getColumnCount();
        }

        public int getRowCount() {
            return ((AccessibilityNodeInfo.CollectionInfo) this.mInfo).getRowCount();
        }

        public boolean isHierarchical() {
            return ((AccessibilityNodeInfo.CollectionInfo) this.mInfo).isHierarchical();
        }

        public int getSelectionMode() {
            if (Build.VERSION.SDK_INT >= 21) {
                return ((AccessibilityNodeInfo.CollectionInfo) this.mInfo).getSelectionMode();
            }
            return 0;
        }
    }

    public static class CollectionItemInfoCompat {
        final Object mInfo;

        public static CollectionItemInfoCompat obtain(int rowIndex, int rowSpan, int columnIndex, int columnSpan, boolean heading, boolean selected) {
            if (Build.VERSION.SDK_INT >= 21) {
                return new CollectionItemInfoCompat(AccessibilityNodeInfo.CollectionItemInfo.obtain(rowIndex, rowSpan, columnIndex, columnSpan, heading, selected));
            }
            return new CollectionItemInfoCompat(AccessibilityNodeInfo.CollectionItemInfo.obtain(rowIndex, rowSpan, columnIndex, columnSpan, heading));
        }

        public static CollectionItemInfoCompat obtain(int rowIndex, int rowSpan, int columnIndex, int columnSpan, boolean heading) {
            return new CollectionItemInfoCompat(AccessibilityNodeInfo.CollectionItemInfo.obtain(rowIndex, rowSpan, columnIndex, columnSpan, heading));
        }

        CollectionItemInfoCompat(Object info) {
            this.mInfo = info;
        }

        public int getColumnIndex() {
            return ((AccessibilityNodeInfo.CollectionItemInfo) this.mInfo).getColumnIndex();
        }

        public int getColumnSpan() {
            return ((AccessibilityNodeInfo.CollectionItemInfo) this.mInfo).getColumnSpan();
        }

        public int getRowIndex() {
            return ((AccessibilityNodeInfo.CollectionItemInfo) this.mInfo).getRowIndex();
        }

        public int getRowSpan() {
            return ((AccessibilityNodeInfo.CollectionItemInfo) this.mInfo).getRowSpan();
        }

        @Deprecated
        public boolean isHeading() {
            return ((AccessibilityNodeInfo.CollectionItemInfo) this.mInfo).isHeading();
        }

        public boolean isSelected() {
            if (Build.VERSION.SDK_INT >= 21) {
                return ((AccessibilityNodeInfo.CollectionItemInfo) this.mInfo).isSelected();
            }
            return false;
        }
    }

    public static class RangeInfoCompat {
        public static final int RANGE_TYPE_FLOAT = 1;
        public static final int RANGE_TYPE_INT = 0;
        public static final int RANGE_TYPE_PERCENT = 2;
        final Object mInfo;

        public static RangeInfoCompat obtain(int type, float min, float max, float current) {
            return new RangeInfoCompat(AccessibilityNodeInfo.RangeInfo.obtain(type, min, max, current));
        }

        RangeInfoCompat(Object info) {
            this.mInfo = info;
        }

        public float getCurrent() {
            return ((AccessibilityNodeInfo.RangeInfo) this.mInfo).getCurrent();
        }

        public float getMax() {
            return ((AccessibilityNodeInfo.RangeInfo) this.mInfo).getMax();
        }

        public float getMin() {
            return ((AccessibilityNodeInfo.RangeInfo) this.mInfo).getMin();
        }

        public int getType() {
            return ((AccessibilityNodeInfo.RangeInfo) this.mInfo).getType();
        }
    }

    public static final class TouchDelegateInfoCompat {
        final AccessibilityNodeInfo.TouchDelegateInfo mInfo;

        public TouchDelegateInfoCompat(Map<Region, View> targetMap) {
            if (Build.VERSION.SDK_INT >= 29) {
                this.mInfo = new AccessibilityNodeInfo.TouchDelegateInfo(targetMap);
            } else {
                this.mInfo = null;
            }
        }

        TouchDelegateInfoCompat(AccessibilityNodeInfo.TouchDelegateInfo info) {
            this.mInfo = info;
        }

        public int getRegionCount() {
            if (Build.VERSION.SDK_INT >= 29) {
                return this.mInfo.getRegionCount();
            }
            return 0;
        }

        public Region getRegionAt(int index) {
            if (Build.VERSION.SDK_INT >= 29) {
                return this.mInfo.getRegionAt(index);
            }
            return null;
        }

        public AccessibilityNodeInfoCompat getTargetForRegion(Region region) {
            AccessibilityNodeInfo info;
            if (Build.VERSION.SDK_INT < 29 || (info = this.mInfo.getTargetForRegion(region)) == null) {
                return null;
            }
            return AccessibilityNodeInfoCompat.wrap(info);
        }
    }

    static AccessibilityNodeInfoCompat wrapNonNullInstance(Object object) {
        if (object != null) {
            return new AccessibilityNodeInfoCompat(object);
        }
        return null;
    }

    @Deprecated
    public AccessibilityNodeInfoCompat(Object info) {
        this.mInfo = (AccessibilityNodeInfo) info;
    }

    private AccessibilityNodeInfoCompat(AccessibilityNodeInfo info) {
        this.mInfo = info;
    }

    public static AccessibilityNodeInfoCompat wrap(AccessibilityNodeInfo info) {
        return new AccessibilityNodeInfoCompat(info);
    }

    public AccessibilityNodeInfo unwrap() {
        return this.mInfo;
    }

    @Deprecated
    public Object getInfo() {
        return this.mInfo;
    }

    public static AccessibilityNodeInfoCompat obtain(View source) {
        return wrap(AccessibilityNodeInfo.obtain(source));
    }

    public static AccessibilityNodeInfoCompat obtain(View root, int virtualDescendantId) {
        return wrapNonNullInstance(AccessibilityNodeInfo.obtain(root, virtualDescendantId));
    }

    public static AccessibilityNodeInfoCompat obtain() {
        return wrap(AccessibilityNodeInfo.obtain());
    }

    public static AccessibilityNodeInfoCompat obtain(AccessibilityNodeInfoCompat info) {
        return wrap(AccessibilityNodeInfo.obtain(info.mInfo));
    }

    public void setSource(View source) {
        this.mVirtualDescendantId = -1;
        this.mInfo.setSource(source);
    }

    public void setSource(View root, int virtualDescendantId) {
        this.mVirtualDescendantId = virtualDescendantId;
        this.mInfo.setSource(root, virtualDescendantId);
    }

    public AccessibilityNodeInfoCompat findFocus(int focus) {
        return wrapNonNullInstance(this.mInfo.findFocus(focus));
    }

    public AccessibilityNodeInfoCompat focusSearch(int direction) {
        return wrapNonNullInstance(this.mInfo.focusSearch(direction));
    }

    public int getWindowId() {
        return this.mInfo.getWindowId();
    }

    public int getChildCount() {
        return this.mInfo.getChildCount();
    }

    public AccessibilityNodeInfoCompat getChild(int index) {
        return wrapNonNullInstance(this.mInfo.getChild(index));
    }

    public void addChild(View child) {
        this.mInfo.addChild(child);
    }

    public void addChild(View root, int virtualDescendantId) {
        this.mInfo.addChild(root, virtualDescendantId);
    }

    public boolean removeChild(View child) {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.mInfo.removeChild(child);
        }
        return false;
    }

    public boolean removeChild(View root, int virtualDescendantId) {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.mInfo.removeChild(root, virtualDescendantId);
        }
        return false;
    }

    @Deprecated
    public int getActions() {
        return this.mInfo.getActions();
    }

    public void addAction(int action) {
        this.mInfo.addAction(action);
    }

    private List<Integer> extrasIntList(String key) {
        ArrayList<Integer> list = this.mInfo.getExtras().getIntegerArrayList(key);
        if (list != null) {
            return list;
        }
        ArrayList<Integer> list2 = new ArrayList<>();
        this.mInfo.getExtras().putIntegerArrayList(key, list2);
        return list2;
    }

    public void addAction(AccessibilityActionCompat action) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mInfo.addAction((AccessibilityNodeInfo.AccessibilityAction) action.mAction);
        }
    }

    public boolean removeAction(AccessibilityActionCompat action) {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.mInfo.removeAction((AccessibilityNodeInfo.AccessibilityAction) action.mAction);
        }
        return false;
    }

    public boolean performAction(int action) {
        return this.mInfo.performAction(action);
    }

    public boolean performAction(int action, Bundle arguments) {
        return this.mInfo.performAction(action, arguments);
    }

    public void setMovementGranularities(int granularities) {
        this.mInfo.setMovementGranularities(granularities);
    }

    public int getMovementGranularities() {
        return this.mInfo.getMovementGranularities();
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String text) {
        List<AccessibilityNodeInfoCompat> result = new ArrayList<>();
        List<AccessibilityNodeInfo> infos = this.mInfo.findAccessibilityNodeInfosByText(text);
        int infoCount = infos.size();
        for (int i = 0; i < infoCount; i++) {
            result.add(wrap(infos.get(i)));
        }
        return result;
    }

    public AccessibilityNodeInfoCompat getParent() {
        return wrapNonNullInstance(this.mInfo.getParent());
    }

    public void setParent(View parent) {
        this.mParentVirtualDescendantId = -1;
        this.mInfo.setParent(parent);
    }

    public void setParent(View root, int virtualDescendantId) {
        this.mParentVirtualDescendantId = virtualDescendantId;
        this.mInfo.setParent(root, virtualDescendantId);
    }

    @Deprecated
    public void getBoundsInParent(Rect outBounds) {
        this.mInfo.getBoundsInParent(outBounds);
    }

    @Deprecated
    public void setBoundsInParent(Rect bounds) {
        this.mInfo.setBoundsInParent(bounds);
    }

    public void getBoundsInScreen(Rect outBounds) {
        this.mInfo.getBoundsInScreen(outBounds);
    }

    public void setBoundsInScreen(Rect bounds) {
        this.mInfo.setBoundsInScreen(bounds);
    }

    public boolean isCheckable() {
        return this.mInfo.isCheckable();
    }

    public void setCheckable(boolean checkable) {
        this.mInfo.setCheckable(checkable);
    }

    public boolean isChecked() {
        return this.mInfo.isChecked();
    }

    public void setChecked(boolean checked) {
        this.mInfo.setChecked(checked);
    }

    public boolean isFocusable() {
        return this.mInfo.isFocusable();
    }

    public void setFocusable(boolean focusable) {
        this.mInfo.setFocusable(focusable);
    }

    public boolean isFocused() {
        return this.mInfo.isFocused();
    }

    public void setFocused(boolean focused) {
        this.mInfo.setFocused(focused);
    }

    public boolean isVisibleToUser() {
        return this.mInfo.isVisibleToUser();
    }

    public void setVisibleToUser(boolean visibleToUser) {
        this.mInfo.setVisibleToUser(visibleToUser);
    }

    public boolean isAccessibilityFocused() {
        return this.mInfo.isAccessibilityFocused();
    }

    public void setAccessibilityFocused(boolean focused) {
        this.mInfo.setAccessibilityFocused(focused);
    }

    public boolean isSelected() {
        return this.mInfo.isSelected();
    }

    public void setSelected(boolean selected) {
        this.mInfo.setSelected(selected);
    }

    public boolean isClickable() {
        return this.mInfo.isClickable();
    }

    public void setClickable(boolean clickable) {
        this.mInfo.setClickable(clickable);
    }

    public boolean isLongClickable() {
        return this.mInfo.isLongClickable();
    }

    public void setLongClickable(boolean longClickable) {
        this.mInfo.setLongClickable(longClickable);
    }

    public boolean isEnabled() {
        return this.mInfo.isEnabled();
    }

    public void setEnabled(boolean enabled) {
        this.mInfo.setEnabled(enabled);
    }

    public boolean isPassword() {
        return this.mInfo.isPassword();
    }

    public void setPassword(boolean password) {
        this.mInfo.setPassword(password);
    }

    public boolean isScrollable() {
        return this.mInfo.isScrollable();
    }

    public void setScrollable(boolean scrollable) {
        this.mInfo.setScrollable(scrollable);
    }

    public boolean isTextSelectable() {
        if (Build.VERSION.SDK_INT >= 33) {
            return Api33Impl.isTextSelectable(this.mInfo);
        }
        return false;
    }

    public void setTextSelectable(boolean selectableText) {
        if (Build.VERSION.SDK_INT >= 33) {
            Api33Impl.setTextSelectable(this.mInfo, selectableText);
        }
    }

    public boolean isImportantForAccessibility() {
        if (Build.VERSION.SDK_INT >= 24) {
            return this.mInfo.isImportantForAccessibility();
        }
        return true;
    }

    public void setImportantForAccessibility(boolean important) {
        if (Build.VERSION.SDK_INT >= 24) {
            this.mInfo.setImportantForAccessibility(important);
        }
    }

    public CharSequence getPackageName() {
        return this.mInfo.getPackageName();
    }

    public void setPackageName(CharSequence packageName) {
        this.mInfo.setPackageName(packageName);
    }

    public CharSequence getClassName() {
        return this.mInfo.getClassName();
    }

    public void setClassName(CharSequence className) {
        this.mInfo.setClassName(className);
    }

    public CharSequence getText() {
        if (!hasSpans()) {
            return this.mInfo.getText();
        }
        List<Integer> starts = extrasIntList(SPANS_START_KEY);
        List<Integer> ends = extrasIntList(SPANS_END_KEY);
        List<Integer> flags = extrasIntList(SPANS_FLAGS_KEY);
        List<Integer> ids = extrasIntList(SPANS_ID_KEY);
        Spannable spannable = new SpannableString(TextUtils.substring(this.mInfo.getText(), 0, this.mInfo.getText().length()));
        for (int i = 0; i < starts.size(); i++) {
            spannable.setSpan(new AccessibilityClickableSpanCompat(ids.get(i).intValue(), this, getExtras().getInt(SPANS_ACTION_ID_KEY)), starts.get(i).intValue(), ends.get(i).intValue(), flags.get(i).intValue());
        }
        return spannable;
    }

    public void setText(CharSequence text) {
        this.mInfo.setText(text);
    }

    public void addSpansToExtras(CharSequence text, View view) {
        if (Build.VERSION.SDK_INT < 26) {
            clearExtrasSpans();
            removeCollectedSpans(view);
            ClickableSpan[] spans = getClickableSpans(text);
            if (spans != null && spans.length > 0) {
                getExtras().putInt(SPANS_ACTION_ID_KEY, C0672R.C0675id.accessibility_action_clickable_span);
                SparseArray<WeakReference<ClickableSpan>> tagSpans = getOrCreateSpansFromViewTags(view);
                int i = 0;
                while (spans != null && i < spans.length) {
                    int id = idForClickableSpan(spans[i], tagSpans);
                    tagSpans.put(id, new WeakReference(spans[i]));
                    addSpanLocationToExtras(spans[i], (Spanned) text, id);
                    i++;
                }
            }
        }
    }

    private SparseArray<WeakReference<ClickableSpan>> getOrCreateSpansFromViewTags(View host) {
        SparseArray<WeakReference<ClickableSpan>> spans = getSpansFromViewTags(host);
        if (spans != null) {
            return spans;
        }
        SparseArray<WeakReference<ClickableSpan>> spans2 = new SparseArray<>();
        host.setTag(C0672R.C0675id.tag_accessibility_clickable_spans, spans2);
        return spans2;
    }

    private SparseArray<WeakReference<ClickableSpan>> getSpansFromViewTags(View host) {
        return (SparseArray) host.getTag(C0672R.C0675id.tag_accessibility_clickable_spans);
    }

    public static ClickableSpan[] getClickableSpans(CharSequence text) {
        if (text instanceof Spanned) {
            return (ClickableSpan[]) ((Spanned) text).getSpans(0, text.length(), ClickableSpan.class);
        }
        return null;
    }

    private int idForClickableSpan(ClickableSpan span, SparseArray<WeakReference<ClickableSpan>> spans) {
        if (spans != null) {
            for (int i = 0; i < spans.size(); i++) {
                if (span.equals((ClickableSpan) spans.valueAt(i).get())) {
                    return spans.keyAt(i);
                }
            }
        }
        int i2 = sClickableSpanId;
        sClickableSpanId = i2 + 1;
        return i2;
    }

    private boolean hasSpans() {
        return !extrasIntList(SPANS_START_KEY).isEmpty();
    }

    private void clearExtrasSpans() {
        this.mInfo.getExtras().remove(SPANS_START_KEY);
        this.mInfo.getExtras().remove(SPANS_END_KEY);
        this.mInfo.getExtras().remove(SPANS_FLAGS_KEY);
        this.mInfo.getExtras().remove(SPANS_ID_KEY);
    }

    private void addSpanLocationToExtras(ClickableSpan span, Spanned spanned, int id) {
        extrasIntList(SPANS_START_KEY).add(Integer.valueOf(spanned.getSpanStart(span)));
        extrasIntList(SPANS_END_KEY).add(Integer.valueOf(spanned.getSpanEnd(span)));
        extrasIntList(SPANS_FLAGS_KEY).add(Integer.valueOf(spanned.getSpanFlags(span)));
        extrasIntList(SPANS_ID_KEY).add(Integer.valueOf(id));
    }

    private void removeCollectedSpans(View view) {
        SparseArray<WeakReference<ClickableSpan>> spans = getSpansFromViewTags(view);
        if (spans != null) {
            List<Integer> toBeRemovedIndices = new ArrayList<>();
            for (int i = 0; i < spans.size(); i++) {
                if (spans.valueAt(i).get() == null) {
                    toBeRemovedIndices.add(Integer.valueOf(i));
                }
            }
            for (int i2 = 0; i2 < toBeRemovedIndices.size(); i2++) {
                spans.remove(toBeRemovedIndices.get(i2).intValue());
            }
        }
    }

    public CharSequence getContentDescription() {
        return this.mInfo.getContentDescription();
    }

    public CharSequence getStateDescription() {
        if (BuildCompat.isAtLeastR()) {
            return this.mInfo.getStateDescription();
        }
        return this.mInfo.getExtras().getCharSequence(STATE_DESCRIPTION_KEY);
    }

    public void setContentDescription(CharSequence contentDescription) {
        this.mInfo.setContentDescription(contentDescription);
    }

    public void setStateDescription(CharSequence stateDescription) {
        if (BuildCompat.isAtLeastR()) {
            this.mInfo.setStateDescription(stateDescription);
        } else {
            this.mInfo.getExtras().putCharSequence(STATE_DESCRIPTION_KEY, stateDescription);
        }
    }

    public String getUniqueId() {
        if (BuildCompat.isAtLeastT()) {
            return this.mInfo.getUniqueId();
        }
        return this.mInfo.getExtras().getString(UNIQUE_ID_KEY);
    }

    public void setUniqueId(String uniqueId) {
        if (BuildCompat.isAtLeastT()) {
            this.mInfo.setUniqueId(uniqueId);
        } else {
            this.mInfo.getExtras().putString(UNIQUE_ID_KEY, uniqueId);
        }
    }

    public void recycle() {
        this.mInfo.recycle();
    }

    public void setViewIdResourceName(String viewId) {
        this.mInfo.setViewIdResourceName(viewId);
    }

    public String getViewIdResourceName() {
        return this.mInfo.getViewIdResourceName();
    }

    public int getLiveRegion() {
        return this.mInfo.getLiveRegion();
    }

    public void setLiveRegion(int mode) {
        this.mInfo.setLiveRegion(mode);
    }

    public int getDrawingOrder() {
        if (Build.VERSION.SDK_INT >= 24) {
            return this.mInfo.getDrawingOrder();
        }
        return 0;
    }

    public void setDrawingOrder(int drawingOrderInParent) {
        if (Build.VERSION.SDK_INT >= 24) {
            this.mInfo.setDrawingOrder(drawingOrderInParent);
        }
    }

    public CollectionInfoCompat getCollectionInfo() {
        AccessibilityNodeInfo.CollectionInfo info = this.mInfo.getCollectionInfo();
        if (info != null) {
            return new CollectionInfoCompat(info);
        }
        return null;
    }

    public void setCollectionInfo(Object collectionInfo) {
        AccessibilityNodeInfo.CollectionInfo collectionInfo2;
        AccessibilityNodeInfo accessibilityNodeInfo = this.mInfo;
        if (collectionInfo == null) {
            collectionInfo2 = null;
        } else {
            collectionInfo2 = (AccessibilityNodeInfo.CollectionInfo) ((CollectionInfoCompat) collectionInfo).mInfo;
        }
        accessibilityNodeInfo.setCollectionInfo(collectionInfo2);
    }

    public void setCollectionItemInfo(Object collectionItemInfo) {
        AccessibilityNodeInfo.CollectionItemInfo collectionItemInfo2;
        AccessibilityNodeInfo accessibilityNodeInfo = this.mInfo;
        if (collectionItemInfo == null) {
            collectionItemInfo2 = null;
        } else {
            collectionItemInfo2 = (AccessibilityNodeInfo.CollectionItemInfo) ((CollectionItemInfoCompat) collectionItemInfo).mInfo;
        }
        accessibilityNodeInfo.setCollectionItemInfo(collectionItemInfo2);
    }

    public CollectionItemInfoCompat getCollectionItemInfo() {
        AccessibilityNodeInfo.CollectionItemInfo info = this.mInfo.getCollectionItemInfo();
        if (info != null) {
            return new CollectionItemInfoCompat(info);
        }
        return null;
    }

    public RangeInfoCompat getRangeInfo() {
        AccessibilityNodeInfo.RangeInfo info = this.mInfo.getRangeInfo();
        if (info != null) {
            return new RangeInfoCompat(info);
        }
        return null;
    }

    public void setRangeInfo(RangeInfoCompat rangeInfo) {
        this.mInfo.setRangeInfo((AccessibilityNodeInfo.RangeInfo) rangeInfo.mInfo);
    }

    public AccessibilityNodeInfo.ExtraRenderingInfo getExtraRenderingInfo() {
        if (Build.VERSION.SDK_INT >= 33) {
            return Api33Impl.getExtraRenderingInfo(this.mInfo);
        }
        return null;
    }

    public List<AccessibilityActionCompat> getActionList() {
        List<AccessibilityNodeInfo.AccessibilityAction> list = null;
        if (Build.VERSION.SDK_INT >= 21) {
            list = this.mInfo.getActionList();
        }
        if (list == null) {
            return Collections.emptyList();
        }
        List<AccessibilityActionCompat> result = new ArrayList<>();
        int actionCount = list.size();
        for (int i = 0; i < actionCount; i++) {
            result.add(new AccessibilityActionCompat(list.get(i)));
        }
        return result;
    }

    public void setContentInvalid(boolean contentInvalid) {
        this.mInfo.setContentInvalid(contentInvalid);
    }

    public boolean isContentInvalid() {
        return this.mInfo.isContentInvalid();
    }

    public boolean isContextClickable() {
        if (Build.VERSION.SDK_INT >= 23) {
            return this.mInfo.isContextClickable();
        }
        return false;
    }

    public void setContextClickable(boolean contextClickable) {
        if (Build.VERSION.SDK_INT >= 23) {
            this.mInfo.setContextClickable(contextClickable);
        }
    }

    public CharSequence getHintText() {
        if (Build.VERSION.SDK_INT >= 26) {
            return this.mInfo.getHintText();
        }
        return this.mInfo.getExtras().getCharSequence(HINT_TEXT_KEY);
    }

    public void setHintText(CharSequence hintText) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.mInfo.setHintText(hintText);
        } else {
            this.mInfo.getExtras().putCharSequence(HINT_TEXT_KEY, hintText);
        }
    }

    public void setError(CharSequence error) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mInfo.setError(error);
        }
    }

    public CharSequence getError() {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.mInfo.getError();
        }
        return null;
    }

    public void setLabelFor(View labeled) {
        this.mInfo.setLabelFor(labeled);
    }

    public void setLabelFor(View root, int virtualDescendantId) {
        this.mInfo.setLabelFor(root, virtualDescendantId);
    }

    public AccessibilityNodeInfoCompat getLabelFor() {
        return wrapNonNullInstance(this.mInfo.getLabelFor());
    }

    public void setLabeledBy(View label) {
        this.mInfo.setLabeledBy(label);
    }

    public void setLabeledBy(View root, int virtualDescendantId) {
        this.mInfo.setLabeledBy(root, virtualDescendantId);
    }

    public AccessibilityNodeInfoCompat getLabeledBy() {
        return wrapNonNullInstance(this.mInfo.getLabeledBy());
    }

    public boolean canOpenPopup() {
        return this.mInfo.canOpenPopup();
    }

    public void setCanOpenPopup(boolean opensPopup) {
        this.mInfo.setCanOpenPopup(opensPopup);
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByViewId(String viewId) {
        List<AccessibilityNodeInfo> nodes = this.mInfo.findAccessibilityNodeInfosByViewId(viewId);
        List<AccessibilityNodeInfoCompat> result = new ArrayList<>();
        for (AccessibilityNodeInfo node : nodes) {
            result.add(wrap(node));
        }
        return result;
    }

    public Bundle getExtras() {
        return this.mInfo.getExtras();
    }

    public int getInputType() {
        return this.mInfo.getInputType();
    }

    public void setInputType(int inputType) {
        this.mInfo.setInputType(inputType);
    }

    public List<String> getAvailableExtraData() {
        if (Build.VERSION.SDK_INT >= 26) {
            return this.mInfo.getAvailableExtraData();
        }
        return Collections.emptyList();
    }

    public void setAvailableExtraData(List<String> extraDataKeys) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.mInfo.setAvailableExtraData(extraDataKeys);
        }
    }

    public void setMaxTextLength(int max) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mInfo.setMaxTextLength(max);
        }
    }

    public int getMaxTextLength() {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.mInfo.getMaxTextLength();
        }
        return -1;
    }

    public void setTextSelection(int start, int end) {
        this.mInfo.setTextSelection(start, end);
    }

    public int getTextSelectionStart() {
        return this.mInfo.getTextSelectionStart();
    }

    public int getTextSelectionEnd() {
        return this.mInfo.getTextSelectionEnd();
    }

    public AccessibilityNodeInfoCompat getTraversalBefore() {
        if (Build.VERSION.SDK_INT >= 22) {
            return wrapNonNullInstance(this.mInfo.getTraversalBefore());
        }
        return null;
    }

    public void setTraversalBefore(View view) {
        if (Build.VERSION.SDK_INT >= 22) {
            this.mInfo.setTraversalBefore(view);
        }
    }

    public void setTraversalBefore(View root, int virtualDescendantId) {
        if (Build.VERSION.SDK_INT >= 22) {
            this.mInfo.setTraversalBefore(root, virtualDescendantId);
        }
    }

    public AccessibilityNodeInfoCompat getTraversalAfter() {
        if (Build.VERSION.SDK_INT >= 22) {
            return wrapNonNullInstance(this.mInfo.getTraversalAfter());
        }
        return null;
    }

    public void setTraversalAfter(View view) {
        if (Build.VERSION.SDK_INT >= 22) {
            this.mInfo.setTraversalAfter(view);
        }
    }

    public void setTraversalAfter(View root, int virtualDescendantId) {
        if (Build.VERSION.SDK_INT >= 22) {
            this.mInfo.setTraversalAfter(root, virtualDescendantId);
        }
    }

    public AccessibilityWindowInfoCompat getWindow() {
        if (Build.VERSION.SDK_INT >= 21) {
            return AccessibilityWindowInfoCompat.wrapNonNullInstance(this.mInfo.getWindow());
        }
        return null;
    }

    public boolean isDismissable() {
        return this.mInfo.isDismissable();
    }

    public void setDismissable(boolean dismissable) {
        this.mInfo.setDismissable(dismissable);
    }

    public boolean isEditable() {
        return this.mInfo.isEditable();
    }

    public void setEditable(boolean editable) {
        this.mInfo.setEditable(editable);
    }

    public boolean isMultiLine() {
        return this.mInfo.isMultiLine();
    }

    public void setMultiLine(boolean multiLine) {
        this.mInfo.setMultiLine(multiLine);
    }

    public CharSequence getTooltipText() {
        if (Build.VERSION.SDK_INT >= 28) {
            return this.mInfo.getTooltipText();
        }
        return this.mInfo.getExtras().getCharSequence(TOOLTIP_TEXT_KEY);
    }

    public void setTooltipText(CharSequence tooltipText) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.mInfo.setTooltipText(tooltipText);
        } else {
            this.mInfo.getExtras().putCharSequence(TOOLTIP_TEXT_KEY, tooltipText);
        }
    }

    public void setPaneTitle(CharSequence paneTitle) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.mInfo.setPaneTitle(paneTitle);
        } else {
            this.mInfo.getExtras().putCharSequence(PANE_TITLE_KEY, paneTitle);
        }
    }

    public CharSequence getPaneTitle() {
        if (Build.VERSION.SDK_INT >= 28) {
            return this.mInfo.getPaneTitle();
        }
        return this.mInfo.getExtras().getCharSequence(PANE_TITLE_KEY);
    }

    public boolean isScreenReaderFocusable() {
        if (Build.VERSION.SDK_INT >= 28) {
            return this.mInfo.isScreenReaderFocusable();
        }
        return getBooleanProperty(1);
    }

    public void setScreenReaderFocusable(boolean screenReaderFocusable) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.mInfo.setScreenReaderFocusable(screenReaderFocusable);
        } else {
            setBooleanProperty(1, screenReaderFocusable);
        }
    }

    public boolean isShowingHintText() {
        if (Build.VERSION.SDK_INT >= 26) {
            return this.mInfo.isShowingHintText();
        }
        return getBooleanProperty(4);
    }

    public void setShowingHintText(boolean showingHintText) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.mInfo.setShowingHintText(showingHintText);
        } else {
            setBooleanProperty(4, showingHintText);
        }
    }

    public boolean isHeading() {
        if (Build.VERSION.SDK_INT >= 28) {
            return this.mInfo.isHeading();
        }
        if (getBooleanProperty(2)) {
            return true;
        }
        CollectionItemInfoCompat collectionItemInfo = getCollectionItemInfo();
        if (collectionItemInfo == null || !collectionItemInfo.isHeading()) {
            return false;
        }
        return true;
    }

    public void setHeading(boolean isHeading) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.mInfo.setHeading(isHeading);
        } else {
            setBooleanProperty(2, isHeading);
        }
    }

    public boolean isTextEntryKey() {
        if (Build.VERSION.SDK_INT >= 29) {
            return this.mInfo.isTextEntryKey();
        }
        return getBooleanProperty(8);
    }

    public void setTextEntryKey(boolean isTextEntryKey) {
        if (Build.VERSION.SDK_INT >= 29) {
            this.mInfo.setTextEntryKey(isTextEntryKey);
        } else {
            setBooleanProperty(8, isTextEntryKey);
        }
    }

    public boolean refresh() {
        return this.mInfo.refresh();
    }

    public CharSequence getRoleDescription() {
        return this.mInfo.getExtras().getCharSequence(ROLE_DESCRIPTION_KEY);
    }

    public void setRoleDescription(CharSequence roleDescription) {
        this.mInfo.getExtras().putCharSequence(ROLE_DESCRIPTION_KEY, roleDescription);
    }

    public TouchDelegateInfoCompat getTouchDelegateInfo() {
        AccessibilityNodeInfo.TouchDelegateInfo delegateInfo;
        if (Build.VERSION.SDK_INT < 29 || (delegateInfo = this.mInfo.getTouchDelegateInfo()) == null) {
            return null;
        }
        return new TouchDelegateInfoCompat(delegateInfo);
    }

    public void setTouchDelegateInfo(TouchDelegateInfoCompat delegatedInfo) {
        if (Build.VERSION.SDK_INT >= 29) {
            this.mInfo.setTouchDelegateInfo(delegatedInfo.mInfo);
        }
    }

    public int hashCode() {
        AccessibilityNodeInfo accessibilityNodeInfo = this.mInfo;
        if (accessibilityNodeInfo == null) {
            return 0;
        }
        return accessibilityNodeInfo.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AccessibilityNodeInfoCompat)) {
            return false;
        }
        AccessibilityNodeInfoCompat other = (AccessibilityNodeInfoCompat) obj;
        AccessibilityNodeInfo accessibilityNodeInfo = this.mInfo;
        if (accessibilityNodeInfo == null) {
            if (other.mInfo != null) {
                return false;
            }
        } else if (!accessibilityNodeInfo.equals(other.mInfo)) {
            return false;
        }
        if (this.mVirtualDescendantId == other.mVirtualDescendantId && this.mParentVirtualDescendantId == other.mParentVirtualDescendantId) {
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        Rect bounds = new Rect();
        getBoundsInParent(bounds);
        builder.append("; boundsInParent: " + bounds);
        getBoundsInScreen(bounds);
        builder.append("; boundsInScreen: " + bounds);
        builder.append("; packageName: ").append(getPackageName());
        builder.append("; className: ").append(getClassName());
        builder.append("; text: ").append(getText());
        builder.append("; contentDescription: ").append(getContentDescription());
        builder.append("; viewId: ").append(getViewIdResourceName());
        builder.append("; uniqueId: ").append(getUniqueId());
        builder.append("; checkable: ").append(isCheckable());
        builder.append("; checked: ").append(isChecked());
        builder.append("; focusable: ").append(isFocusable());
        builder.append("; focused: ").append(isFocused());
        builder.append("; selected: ").append(isSelected());
        builder.append("; clickable: ").append(isClickable());
        builder.append("; longClickable: ").append(isLongClickable());
        builder.append("; enabled: ").append(isEnabled());
        builder.append("; password: ").append(isPassword());
        builder.append("; scrollable: " + isScrollable());
        builder.append("; [");
        if (Build.VERSION.SDK_INT >= 21) {
            List<AccessibilityActionCompat> actions = getActionList();
            for (int i = 0; i < actions.size(); i++) {
                AccessibilityActionCompat action = actions.get(i);
                String actionName = getActionSymbolicName(action.getId());
                if (actionName.equals("ACTION_UNKNOWN") && action.getLabel() != null) {
                    actionName = action.getLabel().toString();
                }
                builder.append(actionName);
                if (i != actions.size() - 1) {
                    builder.append(", ");
                }
            }
        } else {
            int actionBits = getActions();
            while (actionBits != 0) {
                int action2 = 1 << Integer.numberOfTrailingZeros(actionBits);
                actionBits &= action2 ^ -1;
                builder.append(getActionSymbolicName(action2));
                if (actionBits != 0) {
                    builder.append(", ");
                }
            }
        }
        builder.append("]");
        return builder.toString();
    }

    private void setBooleanProperty(int property, boolean value) {
        Bundle extras = getExtras();
        if (extras != null) {
            int i = 0;
            int booleanProperties = extras.getInt(BOOLEAN_PROPERTY_KEY, 0) & (property ^ -1);
            if (value) {
                i = property;
            }
            extras.putInt(BOOLEAN_PROPERTY_KEY, i | booleanProperties);
        }
    }

    private boolean getBooleanProperty(int property) {
        Bundle extras = getExtras();
        if (extras != null && (extras.getInt(BOOLEAN_PROPERTY_KEY, 0) & property) == property) {
            return true;
        }
        return false;
    }

    private static String getActionSymbolicName(int action) {
        switch (action) {
            case 1:
                return "ACTION_FOCUS";
            case 2:
                return "ACTION_CLEAR_FOCUS";
            case 4:
                return "ACTION_SELECT";
            case 8:
                return "ACTION_CLEAR_SELECTION";
            case 16:
                return "ACTION_CLICK";
            case 32:
                return "ACTION_LONG_CLICK";
            case 64:
                return "ACTION_ACCESSIBILITY_FOCUS";
            case 128:
                return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
            case 256:
                return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
            case 512:
                return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
            case 1024:
                return "ACTION_NEXT_HTML_ELEMENT";
            case 2048:
                return "ACTION_PREVIOUS_HTML_ELEMENT";
            case 4096:
                return "ACTION_SCROLL_FORWARD";
            case 8192:
                return "ACTION_SCROLL_BACKWARD";
            case 16384:
                return "ACTION_COPY";
            case 32768:
                return "ACTION_PASTE";
            case 65536:
                return "ACTION_CUT";
            case 131072:
                return "ACTION_SET_SELECTION";
            case 262144:
                return "ACTION_EXPAND";
            case 524288:
                return "ACTION_COLLAPSE";
            case 2097152:
                return "ACTION_SET_TEXT";
            case 16908342:
                return "ACTION_SHOW_ON_SCREEN";
            case 16908343:
                return "ACTION_SCROLL_TO_POSITION";
            case 16908344:
                return "ACTION_SCROLL_UP";
            case 16908345:
                return "ACTION_SCROLL_LEFT";
            case 16908346:
                return "ACTION_SCROLL_DOWN";
            case 16908347:
                return "ACTION_SCROLL_RIGHT";
            case 16908348:
                return "ACTION_CONTEXT_CLICK";
            case 16908349:
                return "ACTION_SET_PROGRESS";
            case 16908354:
                return "ACTION_MOVE_WINDOW";
            case 16908356:
                return "ACTION_SHOW_TOOLTIP";
            case 16908357:
                return "ACTION_HIDE_TOOLTIP";
            case 16908358:
                return "ACTION_PAGE_UP";
            case 16908359:
                return "ACTION_PAGE_DOWN";
            case 16908360:
                return "ACTION_PAGE_LEFT";
            case 16908361:
                return "ACTION_PAGE_RIGHT";
            case 16908362:
                return "ACTION_PRESS_AND_HOLD";
            case 16908372:
                return "ACTION_IME_ENTER";
            case 16908373:
                return "ACTION_DRAG_START";
            case 16908374:
                return "ACTION_DRAG_DROP";
            case 16908375:
                return "ACTION_DRAG_CANCEL";
            default:
                return "ACTION_UNKNOWN";
        }
    }

    private static class Api33Impl {
        private Api33Impl() {
        }

        public static AccessibilityNodeInfo.ExtraRenderingInfo getExtraRenderingInfo(AccessibilityNodeInfo info) {
            return info.getExtraRenderingInfo();
        }

        public static boolean isTextSelectable(AccessibilityNodeInfo info) {
            return info.isTextSelectable();
        }

        public static void setTextSelectable(AccessibilityNodeInfo info, boolean selectable) {
            info.setTextSelectable(selectable);
        }
    }
}
