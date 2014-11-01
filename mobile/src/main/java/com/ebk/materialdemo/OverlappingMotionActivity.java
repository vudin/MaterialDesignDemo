package com.ebk.materialdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;


public class OverlappingMotionActivity extends BaseNavigationDrawerActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_overlapping_motion;
    }

    @Override
    protected int getActivityTitleResId() {
        return R.string.title_overlapping_motion;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSelectedDrawerItemPosition(2);
    }


    public void animateThat(View v) {
        // previously invisible view
        View myView = findViewById(R.id.sample_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (myView.getVisibility() == View.VISIBLE) {
                hideViewAnimated(myView);
            } else {
                myView.setVisibility(View.VISIBLE);
                showViewAnimated(myView);
            }
        } else {
            myView.setVisibility(myView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        }
    }

    @SuppressLint("NewApi")
    private void showViewAnimated(View myView) {


// get the center for the clipping circle
        int cx = (myView.getLeft() + myView.getRight()) / 2;
        int cy = (myView.getTop() + myView.getBottom()) / 2;

// get the final radius for the clipping circle
        int finalRadius = myView.getWidth();

// create and start the animator for this view
// (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
        anim.start();
    }

    @SuppressLint("NewApi")
    private void hideViewAnimated(final View myView) {

// get the center for the clipping circle
        int cx = (myView.getLeft() + myView.getRight()) / 2;
        int cy = (myView.getTop() + myView.getBottom()) / 2;

// get the initial radius for the clipping circle
        int initialRadius = myView.getWidth();

// create the animation (the final radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

// make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.INVISIBLE);
            }
        });

// start the animation
        anim.start();
    }

    public void forceit(View v) {
        showViewAnimated(findViewById(R.id.sample_view));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.overlapping_animation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
