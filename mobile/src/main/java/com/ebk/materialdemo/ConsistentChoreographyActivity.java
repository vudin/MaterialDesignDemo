package com.ebk.materialdemo;

import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

/**
 * Created by dmontiel on 11/3/14.
 */
public class ConsistentChoreographyActivity extends BaseNavigationDrawerActivity implements ExpandAnimationGridAdapter.AnimationContainer {
    GridView gridView;
    ExpandAnimationGridAdapter gridAdapter;
    View bigView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_consistent_choreography;
    }

    @Override
    protected int getActivityTitleResId() {
        return R.string.title_consistent_choreography;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSelectedDrawerItemPosition(3);
        bigView = findViewById(R.id.big_view);
        gridView = (GridView) findViewById(R.id.numbers_grid);
        gridAdapter = new ExpandAnimationGridAdapter(this, 6, this);
        gridView.setAdapter(gridAdapter);
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

    public void again(View v) {
        Button button = (Button) v;
        gridAdapter.notifyDataSetChanged();
        button.setText((button.getText().equals(getString(R.string.collapse)) ? R.string.expand : R.string.collapse));
    }


    public void animateAtoCenterOfB(final View sourceView, final View destinationView) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) sourceView.getLayoutParams();
        TranslateAnimation anim = new TranslateAnimation(Animation.ABSOLUTE, params.leftMargin, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, params.topMargin, Animation.ABSOLUTE, 0);
        anim.setDuration(1000);
        anim.setFillAfter(true);
        anim.setFillEnabled(true);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {


                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);

                layoutParams.leftMargin = (int) (destinationView.getX());
                layoutParams.topMargin = (int) (destinationView.getY());
                sourceView.setLayoutParams(layoutParams);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        sourceView.startAnimation(anim);
    }


    @Override
    public void itemClicked(View v) {
//        animateAtoCenterOfB(v, bigView);
//        GridLayoutAnimationController anim = (GridLayoutAnimationController) AnimationUtils.loadLayoutAnimation(this, R.anim.grid_layout_animation);
//        gridView.setLayoutAnimation(anim);
//        gridView.startLayoutAnimation();

        //This is just for getting the from value, from where animation starts.
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();

        Animation moveRighttoLeft = new TranslateAnimation(0, width, 0, width);
        moveRighttoLeft.setDuration(700);

        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(moveRighttoLeft);
        v.setAnimation(animation);
    }
}
