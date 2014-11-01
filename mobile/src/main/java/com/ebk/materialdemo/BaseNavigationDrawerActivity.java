package com.ebk.materialdemo;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public abstract class BaseNavigationDrawerActivity extends ActionBarActivity {

    protected abstract int getLayoutId();
    protected abstract int getActivityTitleResId();

    private int selectedDrawerItemPosition = 0;
    private ListView drawerList;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        getSupportActionBar().setTitle(getString(getActivityTitleResId()));

        String[] drawerItemTitles = getResources().getStringArray(R.array.navigation_items);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(getString(R.string.app_name));
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(getString(getActivityTitleResId()));
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Set the adapter for the list view
        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, drawerItemTitles));
        // Set the list's click listener
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setSelectedDrawerItemPosition(int selectedDrawerItemPosition) {
        this.selectedDrawerItemPosition = selectedDrawerItemPosition;
        drawerList.setItemChecked(selectedDrawerItemPosition, true);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectItem(int position) {
        if (position != selectedDrawerItemPosition) {
            switch (position) {
                case 0:
                    selectedDrawerItemPosition = 0;
                    startActivity(new Intent(this, CountriesActivity.class));
                    break;
                case 1:
                    selectedDrawerItemPosition = 1;
                    startActivity(new Intent(this, PlanetsActivity.class));
                    break;
                case 2:
                    selectedDrawerItemPosition = 1;
                    startActivity(new Intent(this, OverlappingMotionActivity.class));
                    break;
                default:
                    break;
            }
            this.finish();
        }
        // Highlight the selected item, update the title, and close the drawer
        drawerList.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawerList);
    }
}
