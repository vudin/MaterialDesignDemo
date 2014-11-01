package com.ebk.materialdemo;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class PlanetDetailActivity extends ActionBarActivity {

    private TextView name;
    private int lightVibrantColorFromImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet_detail);

        // Get extras
        String planetName = getIntent().getStringExtra(PlanetsActivity.EXTRA_NAME);
        int planetImageResId = getIntent().getIntExtra(PlanetsActivity.EXTRA_IMAGE, R.drawable.earth);

        // Configure the Toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView image = (ImageView) findViewById(R.id.planet_image);
        ViewCompat.setTransitionName(image, PlanetsActivity.EXTRA_IMAGE);
        Picasso.with(this).load(planetImageResId).into(image);

        name = (TextView) findViewById(R.id.planet_name);
        ViewCompat.setTransitionName(name, PlanetsActivity.EXTRA_NAME);
        name.setText(planetName);

        Palette.generateAsync(BitmapFactory.decodeResource(getResources(), planetImageResId), new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                lightVibrantColorFromImage = palette.getLightVibrantColor(R.color.light_blue);
                name.setBackgroundColor(lightVibrantColorFromImage);
            }
        });

        TextView detail = (TextView) findViewById(R.id.planet_detail);
        detail.setText(R.string.lorem_ipsum);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.planet_detail, menu);
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
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
