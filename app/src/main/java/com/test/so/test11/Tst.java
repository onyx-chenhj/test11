package com.test.so.test11;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.FrameLayout;

public class Tst extends FrameLayout {
    public Tst(@NonNull Context context) {
        super(context);
    }

    public Tst(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Tst(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {


        return super.dispatchKeyEvent(event);
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_PAGE_UP){
            android.util.Log.i("chenhj1111", "KEYCODE_PAGE_UP");
        } else if (keyCode == KeyEvent.KEYCODE_PAGE_DOWN) {
            android.util.Log.i("chenhj1111", "KEYCODE_PAGE_DOWN");
        }
        return super.onKeyDown(keyCode, event);
    }*/
}
