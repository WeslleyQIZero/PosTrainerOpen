package com.wiseass.postrainer.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.wiseass.postrainer.R;
import com.wiseass.postrainer.adapter.CustomPagerAdapter;
import com.wiseass.postrainer.model.objects.Movement;
import com.wiseass.postrainer.ui.fragment.FragmentMovementDetailText;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;

/**
 * Created by Ryan on 21/04/2016.
 */
public class ActivityMovementDetail extends AppCompatActivity {
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String BUNDLE_ITEM = "BUNDLE_ITEM";

    private String[] imageResourceNames;
    private ViewPager pager;
    private CustomPagerAdapter adapter;
    private PageIndicator indicator;
    private Movement movement;
    private FragmentManager manager;

    //TODO: Create appropriate loading screens, as fragment and view pager take a moment to load

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));

        Bundle extras = getIntent().getBundleExtra(BUNDLE_EXTRAS);
        movement = extras.getParcelable(BUNDLE_ITEM);
        imageResourceNames = movement.getImageResId();

        toolbar.setTitle(movement.getName());

        setUpImagePager();
        setUpTextFragment();
    }

    private void setUpTextFragment() {
        manager = getSupportFragmentManager();

        Fragment textFrag = FragmentMovementDetailText.newInstance(movement.getDescription(), movement.getSteps());
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

        transaction.replace(R.id.cont_movement_detail_text, textFrag);
        transaction.commit();
    }

    private void setUpImagePager(){
        pager = (ViewPager) findViewById(R.id.pag_movement_detail_image);
        indicator = (PageIndicator) findViewById(R.id.cpi_movement_detail_image);

        adapter = new CustomPagerAdapter(getSupportFragmentManager(), getImageResourceIds());
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);

    }

    private ArrayList<Integer> getImageResourceIds(){
        ArrayList<Integer> imageResourceIds = new ArrayList<>();
        for (int i = 0; i < imageResourceNames.length; i++){
            imageResourceIds.add(
                    getResources().getIdentifier(imageResourceNames[i],
                            "drawable",
                            getPackageName())
            );
        }
        return  imageResourceIds;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_detail, menu);
        return true;
    }
}

