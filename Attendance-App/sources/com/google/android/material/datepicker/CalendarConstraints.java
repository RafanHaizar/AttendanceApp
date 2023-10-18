package com.google.android.material.datepicker;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.core.util.ObjectsCompat;
import java.util.Arrays;
import java.util.Objects;

public final class CalendarConstraints implements Parcelable {
    public static final Parcelable.Creator<CalendarConstraints> CREATOR = new Parcelable.Creator<CalendarConstraints>() {
        public CalendarConstraints createFromParcel(Parcel source) {
            return new CalendarConstraints((Month) source.readParcelable(Month.class.getClassLoader()), (Month) source.readParcelable(Month.class.getClassLoader()), (DateValidator) source.readParcelable(DateValidator.class.getClassLoader()), (Month) source.readParcelable(Month.class.getClassLoader()), source.readInt());
        }

        public CalendarConstraints[] newArray(int size) {
            return new CalendarConstraints[size];
        }
    };
    /* access modifiers changed from: private */
    public final Month end;
    /* access modifiers changed from: private */
    public final int firstDayOfWeek;
    private final int monthSpan;
    /* access modifiers changed from: private */
    public Month openAt;
    /* access modifiers changed from: private */
    public final Month start;
    /* access modifiers changed from: private */
    public final DateValidator validator;
    private final int yearSpan;

    public interface DateValidator extends Parcelable {
        boolean isValid(long j);
    }

    private CalendarConstraints(Month start2, Month end2, DateValidator validator2, Month openAt2, int firstDayOfWeek2) {
        Objects.requireNonNull(start2, "start cannot be null");
        Objects.requireNonNull(end2, "end cannot be null");
        Objects.requireNonNull(validator2, "validator cannot be null");
        this.start = start2;
        this.end = end2;
        this.openAt = openAt2;
        this.firstDayOfWeek = firstDayOfWeek2;
        this.validator = validator2;
        if (openAt2 != null && start2.compareTo(openAt2) > 0) {
            throw new IllegalArgumentException("start Month cannot be after current Month");
        } else if (openAt2 != null && openAt2.compareTo(end2) > 0) {
            throw new IllegalArgumentException("current Month cannot be after end Month");
        } else if (firstDayOfWeek2 < 0 || firstDayOfWeek2 > UtcDates.getUtcCalendar().getMaximum(7)) {
            throw new IllegalArgumentException("firstDayOfWeek is not valid");
        } else {
            this.monthSpan = start2.monthsUntil(end2) + 1;
            this.yearSpan = (end2.year - start2.year) + 1;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isWithinBounds(long date) {
        if (this.start.getDay(1) <= date) {
            Month month = this.end;
            if (date <= month.getDay(month.daysInMonth)) {
                return true;
            }
        }
        return false;
    }

    public DateValidator getDateValidator() {
        return this.validator;
    }

    /* access modifiers changed from: package-private */
    public Month getStart() {
        return this.start;
    }

    /* access modifiers changed from: package-private */
    public Month getEnd() {
        return this.end;
    }

    /* access modifiers changed from: package-private */
    public Month getOpenAt() {
        return this.openAt;
    }

    /* access modifiers changed from: package-private */
    public void setOpenAt(Month openAt2) {
        this.openAt = openAt2;
    }

    /* access modifiers changed from: package-private */
    public int getFirstDayOfWeek() {
        return this.firstDayOfWeek;
    }

    /* access modifiers changed from: package-private */
    public int getMonthSpan() {
        return this.monthSpan;
    }

    /* access modifiers changed from: package-private */
    public int getYearSpan() {
        return this.yearSpan;
    }

    public long getStartMs() {
        return this.start.timeInMillis;
    }

    public long getEndMs() {
        return this.end.timeInMillis;
    }

    public Long getOpenAtMs() {
        Month month = this.openAt;
        if (month == null) {
            return null;
        }
        return Long.valueOf(month.timeInMillis);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CalendarConstraints)) {
            return false;
        }
        CalendarConstraints that = (CalendarConstraints) o;
        if (!this.start.equals(that.start) || !this.end.equals(that.end) || !ObjectsCompat.equals(this.openAt, that.openAt) || this.firstDayOfWeek != that.firstDayOfWeek || !this.validator.equals(that.validator)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.start, this.end, this.openAt, Integer.valueOf(this.firstDayOfWeek), this.validator});
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.start, 0);
        dest.writeParcelable(this.end, 0);
        dest.writeParcelable(this.openAt, 0);
        dest.writeParcelable(this.validator, 0);
        dest.writeInt(this.firstDayOfWeek);
    }

    /* access modifiers changed from: package-private */
    public Month clamp(Month month) {
        if (month.compareTo(this.start) < 0) {
            return this.start;
        }
        if (month.compareTo(this.end) > 0) {
            return this.end;
        }
        return month;
    }

    public static final class Builder {
        private static final String DEEP_COPY_VALIDATOR_KEY = "DEEP_COPY_VALIDATOR_KEY";
        static final long DEFAULT_END = UtcDates.canonicalYearMonthDay(Month.create(2100, 11).timeInMillis);
        static final long DEFAULT_START = UtcDates.canonicalYearMonthDay(Month.create(1900, 0).timeInMillis);
        private long end = DEFAULT_END;
        private int firstDayOfWeek;
        private Long openAt;
        private long start = DEFAULT_START;
        private DateValidator validator = DateValidatorPointForward.from(Long.MIN_VALUE);

        public Builder() {
        }

        Builder(CalendarConstraints clone) {
            this.start = clone.start.timeInMillis;
            this.end = clone.end.timeInMillis;
            this.openAt = Long.valueOf(clone.openAt.timeInMillis);
            this.firstDayOfWeek = clone.firstDayOfWeek;
            this.validator = clone.validator;
        }

        public Builder setStart(long month) {
            this.start = month;
            return this;
        }

        public Builder setEnd(long month) {
            this.end = month;
            return this;
        }

        public Builder setOpenAt(long month) {
            this.openAt = Long.valueOf(month);
            return this;
        }

        public Builder setFirstDayOfWeek(int firstDayOfWeek2) {
            this.firstDayOfWeek = firstDayOfWeek2;
            return this;
        }

        public Builder setValidator(DateValidator validator2) {
            Objects.requireNonNull(validator2, "validator cannot be null");
            this.validator = validator2;
            return this;
        }

        public CalendarConstraints build() {
            Bundle deepCopyBundle = new Bundle();
            deepCopyBundle.putParcelable(DEEP_COPY_VALIDATOR_KEY, this.validator);
            Month create = Month.create(this.start);
            Month create2 = Month.create(this.end);
            DateValidator dateValidator = (DateValidator) deepCopyBundle.getParcelable(DEEP_COPY_VALIDATOR_KEY);
            Long l = this.openAt;
            return new CalendarConstraints(create, create2, dateValidator, l == null ? null : Month.create(l.longValue()), this.firstDayOfWeek);
        }
    }
}
