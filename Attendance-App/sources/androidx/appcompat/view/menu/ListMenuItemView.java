package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.C0503R;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.ViewCompat;

public class ListMenuItemView extends LinearLayout implements MenuView.ItemView, AbsListView.SelectionBoundsAdjuster {
    private static final String TAG = "ListMenuItemView";
    private Drawable mBackground;
    private CheckBox mCheckBox;
    private LinearLayout mContent;
    private boolean mForceShowIcon;
    private ImageView mGroupDivider;
    private boolean mHasListDivider;
    private ImageView mIconView;
    private LayoutInflater mInflater;
    private MenuItemImpl mItemData;
    private boolean mPreserveIconSpacing;
    private RadioButton mRadioButton;
    private TextView mShortcutView;
    private Drawable mSubMenuArrow;
    private ImageView mSubMenuArrowView;
    private int mTextAppearance;
    private Context mTextAppearanceContext;
    private TextView mTitleView;

    public ListMenuItemView(Context context, AttributeSet attrs) {
        this(context, attrs, C0503R.attr.listMenuViewStyle);
    }

    public ListMenuItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs, C0503R.styleable.MenuView, defStyleAttr, 0);
        this.mBackground = a.getDrawable(C0503R.styleable.MenuView_android_itemBackground);
        this.mTextAppearance = a.getResourceId(C0503R.styleable.MenuView_android_itemTextAppearance, -1);
        this.mPreserveIconSpacing = a.getBoolean(C0503R.styleable.MenuView_preserveIconSpacing, false);
        this.mTextAppearanceContext = context;
        this.mSubMenuArrow = a.getDrawable(C0503R.styleable.MenuView_subMenuArrow);
        TypedArray b = context.getTheme().obtainStyledAttributes((AttributeSet) null, new int[]{16843049}, C0503R.attr.dropDownListViewStyle, 0);
        this.mHasListDivider = b.hasValue(0);
        a.recycle();
        b.recycle();
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        ViewCompat.setBackground(this, this.mBackground);
        TextView textView = (TextView) findViewById(C0503R.C0506id.title);
        this.mTitleView = textView;
        int i = this.mTextAppearance;
        if (i != -1) {
            textView.setTextAppearance(this.mTextAppearanceContext, i);
        }
        this.mShortcutView = (TextView) findViewById(C0503R.C0506id.shortcut);
        ImageView imageView = (ImageView) findViewById(C0503R.C0506id.submenuarrow);
        this.mSubMenuArrowView = imageView;
        if (imageView != null) {
            imageView.setImageDrawable(this.mSubMenuArrow);
        }
        this.mGroupDivider = (ImageView) findViewById(C0503R.C0506id.group_divider);
        this.mContent = (LinearLayout) findViewById(C0503R.C0506id.content);
    }

    public void initialize(MenuItemImpl itemData, int menuType) {
        this.mItemData = itemData;
        setVisibility(itemData.isVisible() ? 0 : 8);
        setTitle(itemData.getTitleForItemView(this));
        setCheckable(itemData.isCheckable());
        setShortcut(itemData.shouldShowShortcut(), itemData.getShortcut());
        setIcon(itemData.getIcon());
        setEnabled(itemData.isEnabled());
        setSubMenuArrowVisible(itemData.hasSubMenu());
        setContentDescription(itemData.getContentDescription());
    }

    private void addContentView(View v) {
        addContentView(v, -1);
    }

    private void addContentView(View v, int index) {
        LinearLayout linearLayout = this.mContent;
        if (linearLayout != null) {
            linearLayout.addView(v, index);
        } else {
            addView(v, index);
        }
    }

    public void setForceShowIcon(boolean forceShow) {
        this.mForceShowIcon = forceShow;
        this.mPreserveIconSpacing = forceShow;
    }

    public void setTitle(CharSequence title) {
        if (title != null) {
            this.mTitleView.setText(title);
            if (this.mTitleView.getVisibility() != 0) {
                this.mTitleView.setVisibility(0);
            }
        } else if (this.mTitleView.getVisibility() != 8) {
            this.mTitleView.setVisibility(8);
        }
    }

    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    public void setCheckable(boolean checkable) {
        CompoundButton otherCompoundButton;
        CompoundButton compoundButton;
        if (checkable || this.mRadioButton != null || this.mCheckBox != null) {
            if (this.mItemData.isExclusiveCheckable()) {
                if (this.mRadioButton == null) {
                    insertRadioButton();
                }
                compoundButton = this.mRadioButton;
                otherCompoundButton = this.mCheckBox;
            } else {
                if (this.mCheckBox == null) {
                    insertCheckBox();
                }
                compoundButton = this.mCheckBox;
                otherCompoundButton = this.mRadioButton;
            }
            if (checkable) {
                compoundButton.setChecked(this.mItemData.isChecked());
                if (compoundButton.getVisibility() != 0) {
                    compoundButton.setVisibility(0);
                }
                if (otherCompoundButton != null && otherCompoundButton.getVisibility() != 8) {
                    otherCompoundButton.setVisibility(8);
                    return;
                }
                return;
            }
            CheckBox checkBox = this.mCheckBox;
            if (checkBox != null) {
                checkBox.setVisibility(8);
            }
            RadioButton radioButton = this.mRadioButton;
            if (radioButton != null) {
                radioButton.setVisibility(8);
            }
        }
    }

    public void setChecked(boolean checked) {
        CompoundButton compoundButton;
        if (this.mItemData.isExclusiveCheckable()) {
            if (this.mRadioButton == null) {
                insertRadioButton();
            }
            compoundButton = this.mRadioButton;
        } else {
            if (this.mCheckBox == null) {
                insertCheckBox();
            }
            compoundButton = this.mCheckBox;
        }
        compoundButton.setChecked(checked);
    }

    private void setSubMenuArrowVisible(boolean hasSubmenu) {
        ImageView imageView = this.mSubMenuArrowView;
        if (imageView != null) {
            imageView.setVisibility(hasSubmenu ? 0 : 8);
        }
    }

    public void setShortcut(boolean showShortcut, char shortcutKey) {
        int newVisibility = (!showShortcut || !this.mItemData.shouldShowShortcut()) ? 8 : 0;
        if (newVisibility == 0) {
            this.mShortcutView.setText(this.mItemData.getShortcutLabel());
        }
        if (this.mShortcutView.getVisibility() != newVisibility) {
            this.mShortcutView.setVisibility(newVisibility);
        }
    }

    public void setIcon(Drawable icon) {
        boolean showIcon = this.mItemData.shouldShowIcon() || this.mForceShowIcon;
        if (showIcon || this.mPreserveIconSpacing) {
            ImageView imageView = this.mIconView;
            if (imageView != null || icon != null || this.mPreserveIconSpacing) {
                if (imageView == null) {
                    insertIconView();
                }
                if (icon != null || this.mPreserveIconSpacing) {
                    this.mIconView.setImageDrawable(showIcon ? icon : null);
                    if (this.mIconView.getVisibility() != 0) {
                        this.mIconView.setVisibility(0);
                        return;
                    }
                    return;
                }
                this.mIconView.setVisibility(8);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mIconView != null && this.mPreserveIconSpacing) {
            ViewGroup.LayoutParams lp = getLayoutParams();
            LinearLayout.LayoutParams iconLp = (LinearLayout.LayoutParams) this.mIconView.getLayoutParams();
            if (lp.height > 0 && iconLp.width <= 0) {
                iconLp.width = lp.height;
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void insertIconView() {
        ImageView imageView = (ImageView) getInflater().inflate(C0503R.C0508layout.abc_list_menu_item_icon, this, false);
        this.mIconView = imageView;
        addContentView(imageView, 0);
    }

    private void insertRadioButton() {
        RadioButton radioButton = (RadioButton) getInflater().inflate(C0503R.C0508layout.abc_list_menu_item_radio, this, false);
        this.mRadioButton = radioButton;
        addContentView(radioButton);
    }

    private void insertCheckBox() {
        CheckBox checkBox = (CheckBox) getInflater().inflate(C0503R.C0508layout.abc_list_menu_item_checkbox, this, false);
        this.mCheckBox = checkBox;
        addContentView(checkBox);
    }

    public boolean prefersCondensedTitle() {
        return false;
    }

    public boolean showsIcon() {
        return this.mForceShowIcon;
    }

    private LayoutInflater getInflater() {
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(getContext());
        }
        return this.mInflater;
    }

    public void setGroupDividerEnabled(boolean groupDividerEnabled) {
        ImageView imageView = this.mGroupDivider;
        if (imageView != null) {
            imageView.setVisibility((this.mHasListDivider || !groupDividerEnabled) ? 8 : 0);
        }
    }

    public void adjustListItemSelectionBounds(Rect rect) {
        ImageView imageView = this.mGroupDivider;
        if (imageView != null && imageView.getVisibility() == 0) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.mGroupDivider.getLayoutParams();
            rect.top += this.mGroupDivider.getHeight() + lp.topMargin + lp.bottomMargin;
        }
    }
}
