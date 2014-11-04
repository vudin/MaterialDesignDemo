package com.ebk.materialdemo;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebk.materialdemo.widget.ObservableScrollView;
import com.squareup.picasso.Picasso;


public class PlanetDetailActivity extends ActionBarActivity {

    private static final float PHOTO_ASPECT_RATIO = 1.0f;

    private ObservableScrollView scrollView;

    private Toolbar toolbar;
    private View toolBarContainer;

    private TextView name;
    private View nameContainer;

    private ImageView image;
    private View imageContainer;

    private View detailContainer;

    private int lightVibrantColorFromImage;
    private int imageHeightPixels;
    private int toolBarHeightPixels;

    private float mMaxHeaderElevation;
    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new GlobalLayoutListener();
    private ScrollCallback scrollListener = new ScrollCallback();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet_detail);

        // Get extras
        String planetName = getIntent().getStringExtra(PlanetsActivity.EXTRA_NAME);
        int planetImageResId = getIntent().getIntExtra(PlanetsActivity.EXTRA_IMAGE, R.drawable.earth);

        // Setup views
        setupScrollView();
        setupToolBar();
        setupImage(planetImageResId);
        setupTitle(planetName);
        setupDetail();

        // Get palette from image
        Palette.generateAsync(BitmapFactory.decodeResource(getResources(), planetImageResId), new PaletteListener());
    }

    private void setupScrollView() {
        scrollView = (ObservableScrollView) findViewById(R.id.scroll_view);
        scrollView.addCallbacks(scrollListener);

        ViewTreeObserver vto = scrollView.getViewTreeObserver();
        if (vto.isAlive()) {
            vto.addOnGlobalLayoutListener(mGlobalLayoutListener);
        }
    }

    private void setupToolBar() {
        // Configure the Toolbar
        toolBarContainer = findViewById(R.id.toolbar_container);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupImage(int planetImageResId) {
        imageContainer = findViewById(R.id.image_container);
        image = (ImageView) findViewById(R.id.planet_image);
        ViewCompat.setTransitionName(image, PlanetsActivity.EXTRA_IMAGE);
        // Load image asynchronously
        Picasso.with(this).load(planetImageResId).into(image);
    }

    private void setupTitle(String planetName) {
        mMaxHeaderElevation = getResources().getDimensionPixelSize(R.dimen.planet_detail_max_header_elevation);
        nameContainer = findViewById(R.id.header_container);
        name = (TextView) findViewById(R.id.planet_name);
//        ViewCompat.setTransitionName(name, PlanetsActivity.EXTRA_NAME);
        name.setText(planetName);
    }

    private void setupDetail() {
        detailContainer = findViewById(R.id.detail_container);
        TextView detail = (TextView) findViewById(R.id.planet_detail);
        detail.setText(R.string.lorem_ipsum);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (scrollView == null) {
            return;
        }

        ViewTreeObserver vto = scrollView.getViewTreeObserver();
        if (vto.isAlive()) {
            vto.removeGlobalOnLayoutListener(mGlobalLayoutListener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void computePhotoAndScrollingMetrics() {
        int mHeaderHeightPixels = name.getHeight();

        imageHeightPixels = 0;
        imageHeightPixels = (int) (image.getWidth() / PHOTO_ASPECT_RATIO);
        imageHeightPixels = Math.min(imageHeightPixels, scrollView.getHeight() * 2 / 3);

        toolBarHeightPixels = 0;
        toolBarHeightPixels = toolbar.getHeight();

        ViewGroup.LayoutParams lp;
        lp = imageContainer.getLayoutParams();
        if (lp.height != imageHeightPixels) {
            lp.height = imageHeightPixels;
            imageContainer.setLayoutParams(lp);
        }

        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) detailContainer.getLayoutParams();
        if (mlp.topMargin != mHeaderHeightPixels + imageHeightPixels) {
            mlp.topMargin = mHeaderHeightPixels + imageHeightPixels;
            detailContainer.setLayoutParams(mlp);
        }

        scrollListener.onScrollChanged(0, 0); // trigger scroll handling
    }

    public float getProgress(int value, int min, int max) {
        if (min == max) {
            throw new IllegalArgumentException("Max (" + max + ") cannot equal min (" + min + ")");
        }
        return (value - min) / (float) (max - min);
    }

    private class ScrollCallback implements ObservableScrollView.Callbacks {
        @Override
        public void onScrollChanged(int deltaX, int deltaY) {
            // Reposition the name bar -- it's normally anchored to the top of the detail part,
            // but locks to the top of the screen on scroll
            int scrollY = scrollView.getScrollY();

            float newTop = Math.max(imageHeightPixels, scrollY);

            if (imageHeightPixels - scrollY <= toolBarHeightPixels) { // We reached the toolbar (actionbar)
                newTop = scrollY + toolBarHeightPixels;
                toolbar.setBackgroundColor(lightVibrantColorFromImage);
                toolBarContainer.setBackgroundColor(getResources().getColor(android.R.color.white));
            } else {
                toolbar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                toolBarContainer.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }

            nameContainer.setTranslationY(newTop);

            float gapFillProgress = 1;
            if (imageHeightPixels != 0) {
                gapFillProgress = Math.min(Math.max(getProgress(scrollY, 0, imageHeightPixels), 0), 1);
            }

            ViewCompat.setElevation(nameContainer, gapFillProgress * mMaxHeaderElevation);

            // Move background photo (with parallax effect)
            imageContainer.setTranslationY(scrollY * 0.5f);
        }
    }

    private class GlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            computePhotoAndScrollingMetrics();
        }
    }

    private class PaletteListener implements Palette.PaletteAsyncListener {
        @Override
        public void onGenerated(Palette palette) {
            lightVibrantColorFromImage = palette.getLightVibrantColor(R.color.light_blue);
            name.setBackgroundColor(lightVibrantColorFromImage);
        }
    }
}
