package com.test.so.test11;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RatingBar;

import com.android.systemui.view.ratingbar.RatingBarView;


public class TestUIActivity extends AppCompatActivity implements RatingBarView.IRatingBarViewChangeListener{

    private RatingBar mRatingBar;
    private RatingBarView mRatingBarViewMedium;
    private RatingBarView mRatingBarViewLarger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ui);
        Log.i("chenhj444", "TestUIActivity->onCreate");
        mRatingBarViewMedium = findViewById(R.id.rating_bar_view_medium);
        mRatingBarViewMedium.setNumStars(10);
        mRatingBarViewMedium.setMax(10);
        mRatingBarViewMedium.setProgress(4);
        mRatingBarViewMedium.setOnRatingBarViewChangeListener(this);

        mRatingBar = mRatingBarViewMedium.getRatingBar();

        /*mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.e("chenhj123", "2222TestUIActivity->onRatingChanged::rating = " + rating);
            }
        });*/

        /*mRatingBarViewLarger = findViewById(R.id.rating_bar_view_larger);
        mRatingBarViewLarger.setNumStars(8);
        mRatingBarViewLarger.setMax(8);
        mRatingBarViewLarger.setProgress(4);*/

        RatingBarView mRatingBarViewTest3 = findViewById(R.id.rating_bar_view_test3);
        mRatingBarViewTest3.setNumStars(28);
        mRatingBarViewTest3.setMax(28);
        mRatingBarViewTest3.setProgress(4);


        RatingBarView mRatingBarViewTest4 = findViewById(R.id.rating_bar_view_test4);
        mRatingBarViewTest4.setNumStars(16);
        mRatingBarViewTest4.setMax(16);
        mRatingBarViewTest4.setProgress(4);

    }


    @Override
    public void onRatingValueChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        Log.e("chenhj123", "TestUIActivity->onRatingValueChanged::rating = " + rating);
    }
}
