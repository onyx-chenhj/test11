package com.android.systemui.view.ratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.so.test11.R;


public class RatingBarView extends RelativeLayout {

    private final String TAG = RatingBarView.class.getSimpleName();

    private View layout;

    private ShapeDrawableRatingBar mRatingBar;
    private TextView mIndicatorTextView;

    private IRatingBarViewChangeListener mRatingBarViewChangeListener;

    private boolean isGlobalLayout = false;

    private int mLabelWidth;
    private int mLabelHeight;
    private int mRatingBarLeft;
    private int mLabelTop;

    private float mIndicatorTextSize;
    private boolean mIndicatorTextShow;

    public interface IRatingBarViewChangeListener {
        void onRatingValueChanged(RatingBar ratingBar, float rating, boolean fromUser);
    }

    public RatingBarView(Context context) {
        this(context, null);
    }

    public RatingBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        layout = View.inflate(context, R.layout.iget_custom_rating_bar_layout, this);
        mRatingBar = layout.findViewById(R.id.iget_custom_rating_bar);

        final TypedArray aa = context.obtainStyledAttributes(
                attrs, R.styleable.ShapeDrawableRatingBar, defStyle, 0);

        Drawable mBackgroundDrawable = aa.getDrawable(R.styleable.ShapeDrawableRatingBar_sdr_backgroundDrawable);
        Drawable mSecondaryDrawable = aa.getDrawable(R.styleable.ShapeDrawableRatingBar_sdr_secondaryDrawable);
        Drawable mProgressDrawable = aa.getDrawable(R.styleable.ShapeDrawableRatingBar_sdr_progressDrawable);

        aa.recycle();
        if (mBackgroundDrawable == null || mProgressDrawable == null) {
            Log.e(TAG, "need background and progress drawable resource.");
        } else {
            mRatingBar.setProgressDrawable(mBackgroundDrawable, mSecondaryDrawable, mProgressDrawable);
        }

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.RatingBarView, defStyle, 0);

        mIndicatorTextSize = a.getDimensionPixelSize(R.styleable.RatingBarView_indicatorTextSize, 17);
        mIndicatorTextShow = a.getBoolean(R.styleable.RatingBarView_indicatorTextShow, true);

        mRatingBar.setRatingBarViewChangeListener(mListener);

        a.recycle();

        layout.getViewTreeObserver().addOnGlobalLayoutListener( () -> {
            if (!isGlobalLayout) {
                isGlobalLayout = true;
                updateIndicatorLocation();
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mIndicatorTextView = findViewById(R.id.label);
        mIndicatorTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mIndicatorTextSize);
        mIndicatorTextView.setVisibility(mIndicatorTextShow ? VISIBLE : GONE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            updateIndicatorLocation();
        }
    }

    private void updateIndicatorLocation() {
        mLabelWidth = calculationLabelWidth();
        mIndicatorTextView.setWidth(mLabelWidth);
        mLabelHeight = mIndicatorTextView.getHeight();
        mRatingBarLeft = mRatingBar.getLeft();
        mLabelTop = mIndicatorTextView.getTop();
        refreshIndicatorLayout();
    }

    private int calculationLabelWidth() {
        return mRatingBar.getStepWidth() * 3;
    }

    public ShapeDrawableRatingBar getRatingBar() {
        return mRatingBar;
    }

    public void setMax(int max) {
        mRatingBar.setMax(max);
    }

    public void setNumStars(int numStars) {
        mRatingBar.setNumStars(numStars);
    }

    public void setProgress(int value) {
        mRatingBar.setProgress(value);
        refreshIndicatorLayout();
    }

    public int getValue() {
        return mRatingBar.getProgress();
    }

    public void setOnRatingBarViewChangeListener(IRatingBarViewChangeListener listener) {
        mRatingBarViewChangeListener = listener;
    }

    private final IRatingBarViewChangeListener mListener = new IRatingBarViewChangeListener() {

        @Override
        public void onRatingValueChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            refreshIndicatorLayout();
            if (mRatingBarViewChangeListener != null) {
                mRatingBarViewChangeListener.onRatingValueChanged(ratingBar, rating, fromUser);
            }
        }
    };

    private void refreshIndicatorLayout() {
        int index = mRatingBar.getProgress();
        int offset = (index -1 ) * mRatingBar.getStepWidth();
        int extendLength = (calculationLabelWidth() - mRatingBar.getStepWidth()) / 2;
        int left = mRatingBarLeft + offset - extendLength;
        //int right = mRatingBarLeft + offset + mRatingBar.getStepWidth() + extendLength;
        int right = left + mLabelWidth;
        int visibility = (mIndicatorTextShow && index > 0) ? View.VISIBLE : View.INVISIBLE;
        mIndicatorTextView.setText(String.valueOf(index));
        mIndicatorTextView.setVisibility(visibility);
        mIndicatorTextView.layout(left, getHeight()/2 - mLabelHeight - 15, right, getHeight()/2 - 15);

    }
}