package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import com.google.android.material.C1087R;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.resources.MaterialResources;

final class CalendarStyle {
    final CalendarItemStyle day;
    final CalendarItemStyle invalidDay;
    final Paint rangeFill;
    final CalendarItemStyle selectedDay;
    final CalendarItemStyle selectedYear;
    final CalendarItemStyle todayDay;
    final CalendarItemStyle todayYear;
    final CalendarItemStyle year;

    CalendarStyle(Context context) {
        TypedArray calendarAttributes = context.obtainStyledAttributes(MaterialAttributes.resolveOrThrow(context, C1087R.attr.materialCalendarStyle, MaterialCalendar.class.getCanonicalName()), C1087R.styleable.MaterialCalendar);
        this.day = CalendarItemStyle.create(context, calendarAttributes.getResourceId(C1087R.styleable.MaterialCalendar_dayStyle, 0));
        this.invalidDay = CalendarItemStyle.create(context, calendarAttributes.getResourceId(C1087R.styleable.MaterialCalendar_dayInvalidStyle, 0));
        this.selectedDay = CalendarItemStyle.create(context, calendarAttributes.getResourceId(C1087R.styleable.MaterialCalendar_daySelectedStyle, 0));
        this.todayDay = CalendarItemStyle.create(context, calendarAttributes.getResourceId(C1087R.styleable.MaterialCalendar_dayTodayStyle, 0));
        ColorStateList rangeFillColorList = MaterialResources.getColorStateList(context, calendarAttributes, C1087R.styleable.MaterialCalendar_rangeFillColor);
        this.year = CalendarItemStyle.create(context, calendarAttributes.getResourceId(C1087R.styleable.MaterialCalendar_yearStyle, 0));
        this.selectedYear = CalendarItemStyle.create(context, calendarAttributes.getResourceId(C1087R.styleable.MaterialCalendar_yearSelectedStyle, 0));
        this.todayYear = CalendarItemStyle.create(context, calendarAttributes.getResourceId(C1087R.styleable.MaterialCalendar_yearTodayStyle, 0));
        Paint paint = new Paint();
        this.rangeFill = paint;
        paint.setColor(rangeFillColorList.getDefaultColor());
        calendarAttributes.recycle();
    }
}
