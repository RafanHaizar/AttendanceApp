package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.core.util.Pair;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.C1087R;
import com.google.android.material.button.MaterialButton;
import java.util.Calendar;
import java.util.Iterator;

public final class MaterialCalendar<S> extends PickerFragment<S> {
    private static final String CALENDAR_CONSTRAINTS_KEY = "CALENDAR_CONSTRAINTS_KEY";
    private static final String CURRENT_MONTH_KEY = "CURRENT_MONTH_KEY";
    private static final String DAY_VIEW_DECORATOR_KEY = "DAY_VIEW_DECORATOR_KEY";
    private static final String GRID_SELECTOR_KEY = "GRID_SELECTOR_KEY";
    static final Object MONTHS_VIEW_GROUP_TAG = "MONTHS_VIEW_GROUP_TAG";
    static final Object NAVIGATION_NEXT_TAG = "NAVIGATION_NEXT_TAG";
    static final Object NAVIGATION_PREV_TAG = "NAVIGATION_PREV_TAG";
    static final Object SELECTOR_TOGGLE_TAG = "SELECTOR_TOGGLE_TAG";
    private static final int SMOOTH_SCROLL_MAX = 3;
    private static final String THEME_RES_ID_KEY = "THEME_RES_ID_KEY";
    /* access modifiers changed from: private */
    public CalendarConstraints calendarConstraints;
    private CalendarSelector calendarSelector;
    /* access modifiers changed from: private */
    public CalendarStyle calendarStyle;
    /* access modifiers changed from: private */
    public Month current;
    /* access modifiers changed from: private */
    public DateSelector<S> dateSelector;
    /* access modifiers changed from: private */
    public View dayFrame;
    private DayViewDecorator dayViewDecorator;
    private View monthNext;
    private View monthPrev;
    /* access modifiers changed from: private */
    public RecyclerView recyclerView;
    private int themeResId;
    private View yearFrame;
    /* access modifiers changed from: private */
    public RecyclerView yearSelector;

    enum CalendarSelector {
        DAY,
        YEAR
    }

    interface OnDayClickListener {
        void onDayClick(long j);
    }

    public static <T> MaterialCalendar<T> newInstance(DateSelector<T> dateSelector2, int themeResId2, CalendarConstraints calendarConstraints2) {
        return newInstance(dateSelector2, themeResId2, calendarConstraints2, (DayViewDecorator) null);
    }

    public static <T> MaterialCalendar<T> newInstance(DateSelector<T> dateSelector2, int themeResId2, CalendarConstraints calendarConstraints2, DayViewDecorator dayViewDecorator2) {
        MaterialCalendar<T> materialCalendar = new MaterialCalendar<>();
        Bundle args = new Bundle();
        args.putInt(THEME_RES_ID_KEY, themeResId2);
        args.putParcelable(GRID_SELECTOR_KEY, dateSelector2);
        args.putParcelable(CALENDAR_CONSTRAINTS_KEY, calendarConstraints2);
        args.putParcelable(DAY_VIEW_DECORATOR_KEY, dayViewDecorator2);
        args.putParcelable(CURRENT_MONTH_KEY, calendarConstraints2.getOpenAt());
        materialCalendar.setArguments(args);
        return materialCalendar;
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(THEME_RES_ID_KEY, this.themeResId);
        bundle.putParcelable(GRID_SELECTOR_KEY, this.dateSelector);
        bundle.putParcelable(CALENDAR_CONSTRAINTS_KEY, this.calendarConstraints);
        bundle.putParcelable(DAY_VIEW_DECORATOR_KEY, this.dayViewDecorator);
        bundle.putParcelable(CURRENT_MONTH_KEY, this.current);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle activeBundle = bundle == null ? getArguments() : bundle;
        this.themeResId = activeBundle.getInt(THEME_RES_ID_KEY);
        this.dateSelector = (DateSelector) activeBundle.getParcelable(GRID_SELECTOR_KEY);
        this.calendarConstraints = (CalendarConstraints) activeBundle.getParcelable(CALENDAR_CONSTRAINTS_KEY);
        this.dayViewDecorator = (DayViewDecorator) activeBundle.getParcelable(DAY_VIEW_DECORATOR_KEY);
        this.current = (Month) activeBundle.getParcelable(CURRENT_MONTH_KEY);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int orientation;
        int layout;
        DaysOfWeekAdapter daysOfWeekAdapter;
        ContextThemeWrapper themedContext = new ContextThemeWrapper(getContext(), this.themeResId);
        this.calendarStyle = new CalendarStyle(themedContext);
        LayoutInflater themedInflater = layoutInflater.cloneInContext(themedContext);
        Month earliestMonth = this.calendarConstraints.getStart();
        if (MaterialDatePicker.isFullscreen(themedContext)) {
            layout = C1087R.C1092layout.mtrl_calendar_vertical;
            orientation = 1;
        } else {
            layout = C1087R.C1092layout.mtrl_calendar_horizontal;
            orientation = 0;
        }
        View root = themedInflater.inflate(layout, viewGroup, false);
        root.setMinimumHeight(getDialogPickerHeight(requireContext()));
        GridView daysHeader = (GridView) root.findViewById(C1087R.C1090id.mtrl_calendar_days_of_week);
        ViewCompat.setAccessibilityDelegate(daysHeader, new AccessibilityDelegateCompat() {
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setCollectionInfo((Object) null);
            }
        });
        int firstDayOfWeek = this.calendarConstraints.getFirstDayOfWeek();
        if (firstDayOfWeek <= 0) {
            daysOfWeekAdapter = new DaysOfWeekAdapter();
        }
        daysHeader.setAdapter(daysOfWeekAdapter);
        daysHeader.setNumColumns(earliestMonth.daysInWeek);
        daysHeader.setEnabled(false);
        this.recyclerView = (RecyclerView) root.findViewById(C1087R.C1090id.mtrl_calendar_months);
        int i = firstDayOfWeek;
        final int firstDayOfWeek2 = orientation;
        C11682 r0 = new SmoothCalendarLayoutManager(getContext(), orientation, false) {
            /* access modifiers changed from: protected */
            public void calculateExtraLayoutSpace(RecyclerView.State state, int[] ints) {
                if (firstDayOfWeek2 == 0) {
                    ints[0] = MaterialCalendar.this.recyclerView.getWidth();
                    ints[1] = MaterialCalendar.this.recyclerView.getWidth();
                    return;
                }
                ints[0] = MaterialCalendar.this.recyclerView.getHeight();
                ints[1] = MaterialCalendar.this.recyclerView.getHeight();
            }
        };
        this.recyclerView.setLayoutManager(r0);
        this.recyclerView.setTag(MONTHS_VIEW_GROUP_TAG);
        DateSelector<S> dateSelector2 = this.dateSelector;
        GridView gridView = daysHeader;
        C11682 r19 = r0;
        View root2 = root;
        CalendarConstraints calendarConstraints2 = this.calendarConstraints;
        int i2 = layout;
        Month month = earliestMonth;
        MonthsPagerAdapter monthsPagerAdapter = new MonthsPagerAdapter(themedContext, dateSelector2, calendarConstraints2, this.dayViewDecorator, new OnDayClickListener() {
            public void onDayClick(long day) {
                if (MaterialCalendar.this.calendarConstraints.getDateValidator().isValid(day)) {
                    MaterialCalendar.this.dateSelector.select(day);
                    Iterator it = MaterialCalendar.this.onSelectionChangedListeners.iterator();
                    while (it.hasNext()) {
                        ((OnSelectionChangedListener) it.next()).onSelectionChanged(MaterialCalendar.this.dateSelector.getSelection());
                    }
                    MaterialCalendar.this.recyclerView.getAdapter().notifyDataSetChanged();
                    if (MaterialCalendar.this.yearSelector != null) {
                        MaterialCalendar.this.yearSelector.getAdapter().notifyDataSetChanged();
                    }
                }
            }
        });
        this.recyclerView.setAdapter(monthsPagerAdapter);
        int columns = themedContext.getResources().getInteger(C1087R.integer.mtrl_calendar_year_selector_span);
        RecyclerView recyclerView2 = (RecyclerView) root2.findViewById(C1087R.C1090id.mtrl_calendar_year_selector_frame);
        this.yearSelector = recyclerView2;
        if (recyclerView2 != null) {
            recyclerView2.setHasFixedSize(true);
            this.yearSelector.setLayoutManager(new GridLayoutManager((Context) themedContext, columns, 1, false));
            this.yearSelector.setAdapter(new YearGridAdapter(this));
            this.yearSelector.addItemDecoration(createItemDecoration());
        }
        if (root2.findViewById(C1087R.C1090id.month_navigation_fragment_toggle) != null) {
            addActionsToMonthNavigation(root2, monthsPagerAdapter);
        }
        if (!MaterialDatePicker.isFullscreen(themedContext)) {
            new PagerSnapHelper().attachToRecyclerView(this.recyclerView);
        }
        this.recyclerView.scrollToPosition(monthsPagerAdapter.getPosition(this.current));
        setUpForAccessibility();
        return root2;
    }

    private void setUpForAccessibility() {
        ViewCompat.setAccessibilityDelegate(this.recyclerView, new AccessibilityDelegateCompat() {
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setScrollable(false);
            }
        });
    }

    private RecyclerView.ItemDecoration createItemDecoration() {
        return new RecyclerView.ItemDecoration() {
            private final Calendar endItem = UtcDates.getUtcCalendar();
            private final Calendar startItem = UtcDates.getUtcCalendar();

            public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
                int firstHighlightPosition;
                Iterator<Pair<Long, Long>> it;
                Pair<Long, Long> range;
                GridLayoutManager layoutManager;
                YearGridAdapter adapter;
                int i;
                if ((recyclerView.getAdapter() instanceof YearGridAdapter) && (recyclerView.getLayoutManager() instanceof GridLayoutManager)) {
                    YearGridAdapter adapter2 = (YearGridAdapter) recyclerView.getAdapter();
                    GridLayoutManager layoutManager2 = (GridLayoutManager) recyclerView.getLayoutManager();
                    Iterator<Pair<Long, Long>> it2 = MaterialCalendar.this.dateSelector.getSelectedRanges().iterator();
                    while (it2.hasNext()) {
                        Pair<Long, Long> range2 = it2.next();
                        if (range2.first == null) {
                            GridLayoutManager gridLayoutManager = layoutManager2;
                            Iterator<Pair<Long, Long>> it3 = it2;
                            Pair<Long, Long> pair = range2;
                        } else if (range2.second != null) {
                            this.startItem.setTimeInMillis(((Long) range2.first).longValue());
                            this.endItem.setTimeInMillis(((Long) range2.second).longValue());
                            int firstHighlightPosition2 = adapter2.getPositionForYear(this.startItem.get(1));
                            int lastHighlightPosition = adapter2.getPositionForYear(this.endItem.get(1));
                            View firstView = layoutManager2.findViewByPosition(firstHighlightPosition2);
                            View lastView = layoutManager2.findViewByPosition(lastHighlightPosition);
                            int firstRow = firstHighlightPosition2 / layoutManager2.getSpanCount();
                            int lastRow = lastHighlightPosition / layoutManager2.getSpanCount();
                            int row = firstRow;
                            while (row <= lastRow) {
                                View viewInRow = layoutManager2.findViewByPosition(layoutManager2.getSpanCount() * row);
                                if (viewInRow == null) {
                                    adapter = adapter2;
                                    layoutManager = layoutManager2;
                                    it = it2;
                                    range = range2;
                                    firstHighlightPosition = firstHighlightPosition2;
                                } else {
                                    int top = viewInRow.getTop() + MaterialCalendar.this.calendarStyle.year.getTopInset();
                                    adapter = adapter2;
                                    int bottom = viewInRow.getBottom() - MaterialCalendar.this.calendarStyle.year.getBottomInset();
                                    int left = row == firstRow ? firstView.getLeft() + (firstView.getWidth() / 2) : 0;
                                    if (row == lastRow) {
                                        i = lastView.getLeft() + (lastView.getWidth() / 2);
                                    } else {
                                        i = recyclerView.getWidth();
                                    }
                                    int right = i;
                                    layoutManager = layoutManager2;
                                    int i2 = left;
                                    it = it2;
                                    int right2 = right;
                                    range = range2;
                                    int i3 = right2;
                                    firstHighlightPosition = firstHighlightPosition2;
                                    canvas.drawRect((float) left, (float) top, (float) right2, (float) bottom, MaterialCalendar.this.calendarStyle.rangeFill);
                                }
                                row++;
                                adapter2 = adapter;
                                layoutManager2 = layoutManager;
                                range2 = range;
                                it2 = it;
                                firstHighlightPosition2 = firstHighlightPosition;
                            }
                            GridLayoutManager gridLayoutManager2 = layoutManager2;
                            Iterator<Pair<Long, Long>> it4 = it2;
                            Pair<Long, Long> pair2 = range2;
                            int i4 = firstHighlightPosition2;
                        }
                    }
                }
            }
        };
    }

    /* access modifiers changed from: package-private */
    public Month getCurrentMonth() {
        return this.current;
    }

    /* access modifiers changed from: package-private */
    public CalendarConstraints getCalendarConstraints() {
        return this.calendarConstraints;
    }

    /* access modifiers changed from: package-private */
    public void setCurrentMonth(Month moveTo) {
        MonthsPagerAdapter adapter = (MonthsPagerAdapter) this.recyclerView.getAdapter();
        int moveToPosition = adapter.getPosition(moveTo);
        int distance = moveToPosition - adapter.getPosition(this.current);
        boolean z = true;
        boolean jump = Math.abs(distance) > 3;
        if (distance <= 0) {
            z = false;
        }
        boolean isForward = z;
        this.current = moveTo;
        if (jump && isForward) {
            this.recyclerView.scrollToPosition(moveToPosition - 3);
            postSmoothRecyclerViewScroll(moveToPosition);
        } else if (jump) {
            this.recyclerView.scrollToPosition(moveToPosition + 3);
            postSmoothRecyclerViewScroll(moveToPosition);
        } else {
            postSmoothRecyclerViewScroll(moveToPosition);
        }
    }

    public DateSelector<S> getDateSelector() {
        return this.dateSelector;
    }

    /* access modifiers changed from: package-private */
    public CalendarStyle getCalendarStyle() {
        return this.calendarStyle;
    }

    static int getDayHeight(Context context) {
        return context.getResources().getDimensionPixelSize(C1087R.dimen.mtrl_calendar_day_height);
    }

    /* access modifiers changed from: package-private */
    public void setSelector(CalendarSelector selector) {
        this.calendarSelector = selector;
        if (selector == CalendarSelector.YEAR) {
            this.yearSelector.getLayoutManager().scrollToPosition(((YearGridAdapter) this.yearSelector.getAdapter()).getPositionForYear(this.current.year));
            this.yearFrame.setVisibility(0);
            this.dayFrame.setVisibility(8);
            this.monthPrev.setVisibility(8);
            this.monthNext.setVisibility(8);
        } else if (selector == CalendarSelector.DAY) {
            this.yearFrame.setVisibility(8);
            this.dayFrame.setVisibility(0);
            this.monthPrev.setVisibility(0);
            this.monthNext.setVisibility(0);
            setCurrentMonth(this.current);
        }
    }

    /* access modifiers changed from: package-private */
    public void toggleVisibleSelector() {
        if (this.calendarSelector == CalendarSelector.YEAR) {
            setSelector(CalendarSelector.DAY);
        } else if (this.calendarSelector == CalendarSelector.DAY) {
            setSelector(CalendarSelector.YEAR);
        }
    }

    private void addActionsToMonthNavigation(View root, final MonthsPagerAdapter monthsPagerAdapter) {
        final MaterialButton monthDropSelect = (MaterialButton) root.findViewById(C1087R.C1090id.month_navigation_fragment_toggle);
        monthDropSelect.setTag(SELECTOR_TOGGLE_TAG);
        ViewCompat.setAccessibilityDelegate(monthDropSelect, new AccessibilityDelegateCompat() {
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                String str;
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                if (MaterialCalendar.this.dayFrame.getVisibility() == 0) {
                    str = MaterialCalendar.this.getString(C1087R.string.mtrl_picker_toggle_to_year_selection);
                } else {
                    str = MaterialCalendar.this.getString(C1087R.string.mtrl_picker_toggle_to_day_selection);
                }
                accessibilityNodeInfoCompat.setHintText(str);
            }
        });
        View findViewById = root.findViewById(C1087R.C1090id.month_navigation_previous);
        this.monthPrev = findViewById;
        findViewById.setTag(NAVIGATION_PREV_TAG);
        View findViewById2 = root.findViewById(C1087R.C1090id.month_navigation_next);
        this.monthNext = findViewById2;
        findViewById2.setTag(NAVIGATION_NEXT_TAG);
        this.yearFrame = root.findViewById(C1087R.C1090id.mtrl_calendar_year_selector_frame);
        this.dayFrame = root.findViewById(C1087R.C1090id.mtrl_calendar_day_selector_frame);
        setSelector(CalendarSelector.DAY);
        monthDropSelect.setText(this.current.getLongName());
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int currentItem;
                if (dx < 0) {
                    currentItem = MaterialCalendar.this.getLayoutManager().findFirstVisibleItemPosition();
                } else {
                    currentItem = MaterialCalendar.this.getLayoutManager().findLastVisibleItemPosition();
                }
                Month unused = MaterialCalendar.this.current = monthsPagerAdapter.getPageMonth(currentItem);
                monthDropSelect.setText(monthsPagerAdapter.getPageTitle(currentItem));
            }

            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == 0) {
                    recyclerView.announceForAccessibility(monthDropSelect.getText());
                }
            }
        });
        monthDropSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MaterialCalendar.this.toggleVisibleSelector();
            }
        });
        this.monthNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int currentItem = MaterialCalendar.this.getLayoutManager().findFirstVisibleItemPosition();
                if (currentItem + 1 < MaterialCalendar.this.recyclerView.getAdapter().getItemCount()) {
                    MaterialCalendar.this.setCurrentMonth(monthsPagerAdapter.getPageMonth(currentItem + 1));
                }
            }
        });
        this.monthPrev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int currentItem = MaterialCalendar.this.getLayoutManager().findLastVisibleItemPosition();
                if (currentItem - 1 >= 0) {
                    MaterialCalendar.this.setCurrentMonth(monthsPagerAdapter.getPageMonth(currentItem - 1));
                }
            }
        });
    }

    private void postSmoothRecyclerViewScroll(final int position) {
        this.recyclerView.post(new Runnable() {
            public void run() {
                MaterialCalendar.this.recyclerView.smoothScrollToPosition(position);
            }
        });
    }

    private static int getDialogPickerHeight(Context context) {
        Resources resources = context.getResources();
        int navigationHeight = resources.getDimensionPixelSize(C1087R.dimen.mtrl_calendar_navigation_height) + resources.getDimensionPixelOffset(C1087R.dimen.mtrl_calendar_navigation_top_padding) + resources.getDimensionPixelOffset(C1087R.dimen.mtrl_calendar_navigation_bottom_padding);
        int daysOfWeekHeight = resources.getDimensionPixelSize(C1087R.dimen.mtrl_calendar_days_of_week_height);
        return navigationHeight + daysOfWeekHeight + (MonthAdapter.MAXIMUM_WEEKS * resources.getDimensionPixelSize(C1087R.dimen.mtrl_calendar_day_height)) + ((MonthAdapter.MAXIMUM_WEEKS - 1) * resources.getDimensionPixelOffset(C1087R.dimen.mtrl_calendar_month_vertical_padding)) + resources.getDimensionPixelOffset(C1087R.dimen.mtrl_calendar_bottom_padding);
    }

    /* access modifiers changed from: package-private */
    public LinearLayoutManager getLayoutManager() {
        return (LinearLayoutManager) this.recyclerView.getLayoutManager();
    }

    public boolean addOnSelectionChangedListener(OnSelectionChangedListener<S> listener) {
        return super.addOnSelectionChangedListener(listener);
    }
}
