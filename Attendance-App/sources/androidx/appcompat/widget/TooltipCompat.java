package androidx.appcompat.widget;

import android.os.Build;
import android.view.View;

public class TooltipCompat {
    public static void setTooltipText(View view, CharSequence tooltipText) {
        if (Build.VERSION.SDK_INT >= 26) {
            Api26Impl.setTooltipText(view, tooltipText);
        } else {
            TooltipCompatHandler.setTooltipText(view, tooltipText);
        }
    }

    private TooltipCompat() {
    }

    static class Api26Impl {
        private Api26Impl() {
        }

        static void setTooltipText(View view, CharSequence tooltipText) {
            view.setTooltipText(tooltipText);
        }
    }
}
