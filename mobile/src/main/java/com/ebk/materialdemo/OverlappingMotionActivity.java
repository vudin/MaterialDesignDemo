package com.ebk.materialdemo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;


public class OverlappingMotionActivity extends BaseNavigationDrawerActivity {
    GridView gridView;
    ExpandAnimationGridAdapter gridAdapter;

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

        gridView = (GridView) findViewById(R.id.numbers_grid);
        gridAdapter = new ExpandAnimationGridAdapter(this, 12);
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
}
