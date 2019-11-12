package com.android.systemui.view.ratingbar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

public class RatingBarLayout extends FrameLayout {
    private boolean isLayout = false;
    public RatingBarLayout(@NonNull Context context) {
        super(context);
    }

    public RatingBarLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RatingBarLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (!isLayout) {
            isLayout = true;
            super.onLayout(changed, left, top, right, bottom);
        }
    }
}
