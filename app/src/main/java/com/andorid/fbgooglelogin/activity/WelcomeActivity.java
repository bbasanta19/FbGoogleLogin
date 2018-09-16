package com.andorid.fbgooglelogin.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andorid.fbgooglelogin.MainActivity;
import com.andorid.fbgooglelogin.PrefManager;
import com.andorid.fbgooglelogin.R;
import com.andorid.fbgooglelogin.adapter.ViewPagerAdapter;


public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;
    PrefManager prefManager;
    LinearLayout dotsLayout;
    Button btnSkip, btnNext;
    private int[] layouts;
    private TextView[] dots;
    private ViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            //finish();
        }

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);

        viewPager = findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.layout_dots);
        btnNext = findViewById(R.id.btn_next);
        btnSkip = findViewById(R.id.btn_skip);

        btnSkip.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        // add layouts here
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4};

        //adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        viewPagerAdapter = new ViewPagerAdapter(this, layouts);
        viewPager.setAdapter(viewPagerAdapter);


        ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);

                // changing the next buttom text NEXT / GOT IT
                if (position == layouts.length - 1) {
                    btnNext.setText(getString(R.string.start));
                    btnSkip.setVisibility(View.GONE);
                } else {
                    btnNext.setText(getString(R.string.next));
                    btnSkip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];
        int[] colorActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorInActive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInActive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[currentPage].setTextColor(colorActive[currentPage]);
        }
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void launchHomeScreen() {
        //prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_skip:
                launchHomeScreen();
                break;

            case R.id.btn_next:
                int current = viewPager.getCurrentItem() + 1;
                if (current < layouts.length) {
                    //move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
        }
    }

}
