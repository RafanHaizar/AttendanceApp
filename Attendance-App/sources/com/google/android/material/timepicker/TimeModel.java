package com.google.android.material.timepicker;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.material.C1087R;
import java.util.Arrays;

class TimeModel implements Parcelable {
    public static final Parcelable.Creator<TimeModel> CREATOR = new Parcelable.Creator<TimeModel>() {
        public TimeModel createFromParcel(Parcel in) {
            return new TimeModel(in);
        }

        public TimeModel[] newArray(int size) {
            return new TimeModel[size];
        }
    };
    public static final String NUMBER_FORMAT = "%d";
    public static final String ZERO_LEADING_NUMBER_FORMAT = "%02d";
    final int format;
    int hour;
    private final MaxInputValidator hourInputValidator;
    int minute;
    private final MaxInputValidator minuteInputValidator;
    int period;
    int selection;

    public TimeModel() {
        this(0);
    }

    public TimeModel(int format2) {
        this(0, 0, 10, format2);
    }

    public TimeModel(int hour2, int minute2, int selection2, int format2) {
        this.hour = hour2;
        this.minute = minute2;
        this.selection = selection2;
        this.format = format2;
        this.period = getPeriod(hour2);
        this.minuteInputValidator = new MaxInputValidator(59);
        this.hourInputValidator = new MaxInputValidator(format2 == 1 ? 24 : 12);
    }

    protected TimeModel(Parcel in) {
        this(in.readInt(), in.readInt(), in.readInt(), in.readInt());
    }

    public void setHourOfDay(int hour2) {
        this.period = getPeriod(hour2);
        this.hour = hour2;
    }

    private static int getPeriod(int hourOfDay) {
        return hourOfDay >= 12 ? 1 : 0;
    }

    public void setHour(int hour2) {
        if (this.format == 1) {
            this.hour = hour2;
        } else {
            this.hour = (hour2 % 12) + (this.period == 1 ? 12 : 0);
        }
    }

    public void setMinute(int minute2) {
        this.minute = minute2 % 60;
    }

    public int getHourForDisplay() {
        if (this.format == 1) {
            return this.hour % 24;
        }
        int i = this.hour;
        if (i % 12 == 0) {
            return 12;
        }
        if (this.period == 1) {
            return i - 12;
        }
        return i;
    }

    public int getHourContentDescriptionResId() {
        return this.format == 1 ? C1087R.string.material_hour_24h_suffix : C1087R.string.material_hour_suffix;
    }

    public MaxInputValidator getMinuteInputValidator() {
        return this.minuteInputValidator;
    }

    public MaxInputValidator getHourInputValidator() {
        return this.hourInputValidator;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.format), Integer.valueOf(this.hour), Integer.valueOf(this.minute), Integer.valueOf(this.selection)});
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeModel)) {
            return false;
        }
        TimeModel that = (TimeModel) o;
        if (this.hour == that.hour && this.minute == that.minute && this.format == that.format && this.selection == that.selection) {
            return true;
        }
        return false;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.hour);
        dest.writeInt(this.minute);
        dest.writeInt(this.selection);
        dest.writeInt(this.format);
    }

    public void setPeriod(int period2) {
        if (period2 != this.period) {
            this.period = period2;
            int i = this.hour;
            if (i < 12 && period2 == 1) {
                this.hour = i + 12;
            } else if (i >= 12 && period2 == 0) {
                this.hour = i - 12;
            }
        }
    }

    public static String formatText(Resources resources, CharSequence text) {
        return formatText(resources, text, ZERO_LEADING_NUMBER_FORMAT);
    }

    public static String formatText(Resources resources, CharSequence text, String format2) {
        try {
            return String.format(resources.getConfiguration().locale, format2, new Object[]{Integer.valueOf(Integer.parseInt(String.valueOf(text)))});
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
