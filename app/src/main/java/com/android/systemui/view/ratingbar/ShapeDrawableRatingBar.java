package com.android.systemui.view.ratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.RatingBar;

import com.test.so.test11.R;


public class ShapeDrawableRatingBar extends RatingBar {

    private final String TAG = ShapeDrawableRatingBar.class.getSimpleName();

    /**
     * TileBitmap to base the width off of.
     */
    @Nullable
    private Bitmap mSampleTile;

    private int stepWidth;

    private Drawable mBackgroundDrawable;
    private Drawable mSecondaryDrawable;
    private Drawable mProgressDrawable;

    private RatingBarView.IRatingBarViewChangeListener mRatingBarViewChangeListener;
    private OnRatingBarChangeListener mOnRatingBarChangeListener;

    public ShapeDrawableRatingBar(Context context) {
        this(context, null);
    }

    public ShapeDrawableRatingBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeDrawableRatingBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.ShapeDrawableRatingBar, defStyle, 0);

        mBackgroundDrawable = a.getDrawable(R.styleable.ShapeDrawableRatingBar_sdr_backgroundDrawable);
        mSecondaryDrawable = a.getDrawable(R.styleable.ShapeDrawableRatingBar_sdr_secondaryDrawable);
        mProgressDrawable = a.getDrawable(R.styleable.ShapeDrawableRatingBar_sdr_progressDrawable);
        a.recycle();
        initRatingBarProgressDrawable();
    }

    public void setRatingBarViewChangeListener(RatingBarView.IRatingBarViewChangeListener ratingBarViewChangeListener) {
        mRatingBarViewChangeListener = ratingBarViewChangeListener;
        super.setOnRatingBarChangeListener(mShapeDrawableRatingBarChangeListener);
    }

    @Override
    public void setOnRatingBarChangeListener(OnRatingBarChangeListener listener) {
        mOnRatingBarChangeListener = listener;
        super.setOnRatingBarChangeListener(mShapeDrawableRatingBarChangeListener);
    }

    private void initRatingBarProgressDrawable() {
        LayerDrawable layerDrawable = createProgressDrawable();
        if(layerDrawable != null) {
            setProgressDrawable(layerDrawable);
        }
    }

    public void setProgressDrawable(Drawable background, Drawable secondary, Drawable progress) {
        mBackgroundDrawable = background;
        mSecondaryDrawable = secondary;
        mProgressDrawable = progress;
        initRatingBarProgressDrawable();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mSampleTile != null) {
            stepWidth = mSampleTile.getWidth();
            int stepHeight = mSampleTile.getHeight();
            final int width  = stepWidth * getNumStars();
            int measuredWidth = resolveSizeAndState(width, widthMeasureSpec, 0);
            int measuredHeight = stepHeight * 2;
            setMeasuredDimension(measuredWidth, measuredHeight);
        }
    }

    protected LayerDrawable createProgressDrawable() {
        if (mBackgroundDrawable == null || mProgressDrawable == null) {
            Log.e(TAG, "need background and progress drawable resource.");
            return null;
        }
        final Drawable backgroundDrawable = createBackgroundDrawableShape();
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{
                backgroundDrawable,
                mSecondaryDrawable != null ? createSecondaryDrawableShape() : backgroundDrawable,
                createProgressDrawableShape()
        });
        layerDrawable.setId(0, android.R.id.background);
        layerDrawable.setId(1, android.R.id.secondaryProgress);
        layerDrawable.setId(2, android.R.id.progress);
        return layerDrawable;
    }

    protected Drawable createBackgroundDrawableShape() {
        //final Bitmap tileBitmap = drawableToBitmap(getResources().getDrawable(mBackgroundDrawableResId));
        final Bitmap tileBitmap = drawableToBitmap(mBackgroundDrawable);
        mSampleTile = tileBitmap;
        final ShapeDrawable shapeDrawable = new ShapeDrawable(getDrawableShape());
        final BitmapShader bitmapShader = new BitmapShader(tileBitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        shapeDrawable.getPaint().setShader(bitmapShader);
        return shapeDrawable;
    }

    protected Drawable createSecondaryDrawableShape() {
        //final Bitmap tileBitmap = drawableToBitmap(getResources().getDrawable(mSecondaryDrawableResId));
        final Bitmap tileBitmap = drawableToBitmap(mSecondaryDrawable);
        final ShapeDrawable shapeDrawable = new ShapeDrawable(getDrawableShape());
        final BitmapShader bitmapShader = new BitmapShader(tileBitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        shapeDrawable.getPaint().setShader(bitmapShader);
        return shapeDrawable;
    }

    protected Drawable createProgressDrawableShape() {
        //final Bitmap tileBitmap = drawableToBitmap(getResources().getDrawable(mProgressDrawableResId));
        final Bitmap tileBitmap = drawableToBitmap(mProgressDrawable);
        final ShapeDrawable shapeDrawable = new ShapeDrawable(getDrawableShape());
        final BitmapShader bitmapShader = new BitmapShader(tileBitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        shapeDrawable.getPaint().setShader(bitmapShader);
        return new ClipDrawable(shapeDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);
    }

    Shape getDrawableShape() {
        final float[] roundedCorners = new float[]{5, 5, 5, 5, 5, 5, 5, 5};
        return new RoundRectShape(roundedCorners, null, null);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public int getStepWidth() {
        return stepWidth;
    }


    private final RatingBar.OnRatingBarChangeListener mShapeDrawableRatingBarChangeListener = new RatingBar.OnRatingBarChangeListener() {

        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            if (mOnRatingBarChangeListener != null) {
                mOnRatingBarChangeListener.onRatingChanged(ratingBar, rating, fromUser);
            }
            if(mRatingBarViewChangeListener != null) {
                mRatingBarViewChangeListener.onRatingValueChanged(ratingBar, rating, fromUser);
            }
        }
    };
}