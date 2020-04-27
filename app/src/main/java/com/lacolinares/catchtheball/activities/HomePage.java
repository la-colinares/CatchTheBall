package com.lacolinares.catchtheball.activities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;

import com.lacolinares.catchtheball.R;
import com.lacolinares.catchtheball.fragments.Frag_about;
import com.lacolinares.catchtheball.fragments.Frag_home;
import com.lacolinares.catchtheball.fragments.Frag_scores;
import com.lacolinares.catchtheball.slidingrootnav_menu.DrawerAdapter;
import com.lacolinares.catchtheball.slidingrootnav_menu.DrawerItem;
import com.lacolinares.catchtheball.slidingrootnav_menu.SimpleItem;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePage extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_HOME = 0;
    private static final int POS_SCORES = 1;
    private static final int POS_ABOUT = 2;
    private static final int POS_EXIT = 3;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private SlidingRootNav slidingRootNav;
    private String[] screenTitles;
    private Drawable[] screenIcons;

    private Context mContext;

    public HomePage() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        mContext = this;

        //NAVIGATION
        slidingRootNav = new SlidingRootNavBuilder(HomePage.this)
                .withToolbarMenuToggle(mToolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(true)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter drawAdapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_HOME).setChecked(true),
                createItemFor(POS_SCORES),
                createItemFor(POS_ABOUT),
                createItemFor(POS_EXIT)));
        drawAdapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(mContext));
        list.setAdapter(drawAdapter);

        drawAdapter.setSelected(POS_HOME);
    }

    @Override
    public void onItemSelected(int position) {

        if (position == POS_HOME) {
            mToolbar.setTitle("Home");
            mToolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
            Frag_home home = new Frag_home();
            showFragment(home);
        }
        if (position == POS_SCORES) {
            mToolbar.setTitle("Scores");
            mToolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
            Frag_scores scores = new Frag_scores();
            showFragment(scores);
        }
        if (position == POS_ABOUT) {
            mToolbar.setTitle("About");
            mToolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
            Frag_about about = new Frag_about();
            showFragment(about);
        }
        if (position == POS_EXIT) {
            onBackPressed();
        }

        slidingRootNav.closeMenu();

    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();

    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorSecondary))
                .withSelectedIconTint(color(R.color.colorWhite))
                .withSelectedTextTint(color(R.color.colorWhite));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.def_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
