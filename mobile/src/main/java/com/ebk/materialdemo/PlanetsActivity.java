package com.ebk.materialdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class PlanetsActivity extends BaseNavigationDrawerActivity {

    public static final String EXTRA_IMAGE = "IMAGE";
    public static final String EXTRA_NAME = "NAME";
    private int[] imageResIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSelectedDrawerItemPosition(1);

        imageResIds = new int[] {
                R.drawable.mercury,
                R.drawable.venus,
                R.drawable.earth,
                R.drawable.mars,
                R.drawable.jupiter,
                R.drawable.saturn,
                R.drawable.uranus,
                R.drawable.neptune,
                R.drawable.pluto
        };

        GridView gridView = (GridView) findViewById(R.id.planets_grid);
        gridView.setAdapter(new GridViewAdapter(this, imageResIds, getResources().getStringArray(R.array.planet_names)));
        gridView.setOnItemClickListener(new GridItemClickListener());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_planets;
    }

    @Override
    protected int getActivityTitleResId() {
        return R.string.title_activity_planets;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.planets, menu);
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

    private static class GridViewAdapter extends BaseAdapter {

        private final LayoutInflater inflater;
        private Context context;
        private final int[] imageResIds;
        private final String[] names;

        public GridViewAdapter(Context context, int[] imageResIds, String[] names) {
            this.context = context;
            this.imageResIds = imageResIds;
            this.names = names;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return imageResIds.length;
        }

        @Override
        public Object getItem(int position) {
            return "Item " + String.valueOf(position + 1);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.grid_item_planet, null);
                holder = new ViewHolder();
                holder.planetImage = (ImageView) convertView.findViewById(R.id.grid_item_planet_image);
                holder.planetName = (TextView) convertView.findViewById(R.id.grid_item_planet_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Picasso.with(context).load(imageResIds[position]).into(holder.planetImage);
            holder.planetName.setText(names[position]);

            return convertView;
        }

        private static class ViewHolder {
            private ImageView planetImage;
            private TextView planetName;
        }

    }

    private class GridItemClickListener implements AdapterView.OnItemClickListener {
        @SuppressWarnings("unchecked")
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Define transition options
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(PlanetsActivity.this,
                    new Pair<View, String>(view.findViewById(R.id.grid_item_planet_image), EXTRA_IMAGE),
                    new Pair<View, String>(view.findViewById(R.id.grid_item_planet_name), EXTRA_NAME));

            // Create intent
            Intent intent = new Intent(PlanetsActivity.this, PlanetDetailActivity.class);
            intent.putExtra(EXTRA_IMAGE, imageResIds[position]);
            intent.putExtra(EXTRA_NAME, getResources().getStringArray(R.array.planet_names)[position]);

            // Start activity with defined options
            ActivityCompat.startActivity(PlanetsActivity.this, intent, options.toBundle());
        }
    }
}
