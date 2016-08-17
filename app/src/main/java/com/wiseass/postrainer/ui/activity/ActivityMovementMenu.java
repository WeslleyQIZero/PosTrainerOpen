package com.wiseass.postrainer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.wiseass.postrainer.R;
import com.wiseass.postrainer.adapter.MovementListAdapter;
import com.wiseass.postrainer.api.UpdateAdapterTask;
import com.wiseass.postrainer.model.objects.Movement;
import com.wiseass.postrainer.model.objects.MovementList;
import com.wiseass.postrainer.ui.fragment.FragmentNavigationDrawer;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class ActivityMovementMenu extends AppCompatActivity implements MovementListAdapter.OnItemClickListener {
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String BUNDLE_ITEM = "BUNDLE_ITEM";

    private RecyclerView movementRecView;
    private MovementListAdapter adapter;
    private MovementList movementList;
    private TranslateAnimation scale;

    private Animation slideInLeft, slideOutRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        slideInLeft = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        slideOutRight = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);

        toolbar.setTitle(R.string.movement_menu_title);

        FragmentNavigationDrawer drawerFragment = (FragmentNavigationDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout_menu), toolbar);

        movementRecView = (RecyclerView) findViewById(R.id.lst_movements);
        movementRecView.setLayoutManager(new LinearLayoutManager(this));

        movementRecView.getItemAnimator().setChangeDuration(0);



        movementList = parseJsonData(this.getResources().openRawResource(R.raw.movements));

        updateRecyclerView(movementList);
    }

    /**
     * Load initial Json data from raw resource. This may need to be an Asynctask, but we'll see
     */
    public MovementList parseJsonData(InputStream raw) {
        Gson gson = new Gson();
        Reader rd;
        rd = new BufferedReader(new InputStreamReader(raw));
        return gson.fromJson(rd, MovementList.class);
    }

    public void updateRecyclerView(MovementList movements) {
        if (adapter == null) {
            adapter = new MovementListAdapter(this, movements.getMovements());
            movementRecView.setAdapter(adapter);
            adapter.setOnClickListener(this);
        } else {
           // animateRecViewOut();
            UpdateAdapterTask update = new UpdateAdapterTask(movements.getMovements());
            update.setUpdateCompleteListener(new UpdateAdapterTask.OnUpdateComplete() {
                @Override
                public void setUpdateComplete() {
                    //animateRecViewIn();
                }
            });
            update.execute(adapter);

        }

    }

    /*
    public void animateRecViewOut() {
        movementRecView.clearAnimation();
        movementRecView.setAnimation(slideOutRight);
        movementRecView.animate();

    }

    public void animateRecViewIn() {
        movementRecView.clearAnimation();
        movementRecView.setAnimation(slideInLeft);
        movementRecView.animate();
    }*/

    @Override
    public void onMovementCardClick(Movement selectedMovement) {
        Intent i = new Intent(this, ActivityMovementDetail.class);
        Bundle extras = new Bundle();
        extras.putParcelable(BUNDLE_ITEM, selectedMovement);
        i.putExtra(BUNDLE_EXTRAS, extras);
        startActivity(i);
    }
}
