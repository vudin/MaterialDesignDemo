package com.ebk.materialdemo;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class CountriesActivity extends BaseNavigationDrawerActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSelectedDrawerItemPosition(0);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setProgressBackgroundColor(R.color.accent_light);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_purple,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshListener());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        populateList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_countries;
    }

    @Override
    protected int getActivityTitleResId() {
        return R.string.title_activity_countries;
    }

    private void populateList() {
        RecyclerAdapter adapter = new RecyclerAdapter(getSortedCountryList());
        adapter.setOnItemClickListener(new RecyclerViewOnItemClickListener());
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<String> getSortedCountryList() {
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries);
        return countries;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo, menu);
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

    private class SwipeRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    populateList();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 5000); //adding five sec delay
        }
    }

    private static class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        private final ArrayList<String> dataset;
        public OnItemClickListener itemClickListener;

        public RecyclerAdapter(ArrayList<String> myDataset) {
            dataset = myDataset;
        }

        public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            // set the view's size, margins, paddings and layout parameters
            return new ViewHolder((CardView) v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText(dataset.get(position));
        }

        @Override
        public int getItemCount() {
            return dataset.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public final CardView cardView;
            public final TextView textView;

            public ViewHolder(CardView v) {
                super(v);
                cardView = (CardView) v.findViewById(R.id.card_view);
                textView = (TextView) v.findViewById(R.id.text_view);
                cardView.setOnClickListener(this);
                textView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (itemClickListener == null) {
                    Log.e("MATERIAL_DEMO", "You should first set an OnItemClickListener to this adapter.");
                    return;
                }
                if (v.getId() == R.id.card_view) {
                    itemClickListener.onItemClick(v, getPosition());
                } else if (v.getId() == R.id.text_view) {
                    itemClickListener.onItemClick(v, getPosition());
                }
            }
        }

        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }
    }

    private class RecyclerViewOnItemClickListener implements RecyclerAdapter.OnItemClickListener {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onItemClick(final View view, int position) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (view.getId() == R.id.card_view) {
                    view.animate().translationZ(15f).setDuration(500).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            view.animate().translationZ(1f).setDuration(500).start();

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).start();
                }
            } else if (view.getId() == R.id.text_view) {
                Toast.makeText(CountriesActivity.this, "Text " + position + " Clicked", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
