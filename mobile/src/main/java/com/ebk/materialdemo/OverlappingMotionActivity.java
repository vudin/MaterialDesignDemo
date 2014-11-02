package com.ebk.materialdemo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


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


    public void expand(View v) {
        // previously invisible view
        View myView = findViewById(R.id.sample_view);
        Animation a = AnimationUtils.loadAnimation(OverlappingMotionActivity.this, R.anim.scale_up_from_center);
        myView.setAnimation(a);
        myView.setVisibility(View.VISIBLE);
    }

    public void collapse(View v) {
        View myView = findViewById(R.id.sample_view);
        Animation a = AnimationUtils.loadAnimation(OverlappingMotionActivity.this, R.anim.scale_down_from_center);
        myView.setAnimation(a);
        myView.setVisibility(View.INVISIBLE);
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
