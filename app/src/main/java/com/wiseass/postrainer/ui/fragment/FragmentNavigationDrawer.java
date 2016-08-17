package com.wiseass.postrainer.ui.fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.wiseass.postrainer.R;
import com.wiseass.postrainer.adapter.NavigationDrawerAdapter;
import com.wiseass.postrainer.ui.activity.ActivityReminders;
import com.wiseass.postrainer.ui.activity.ActivitySettings;
import com.wiseass.postrainer.ui.activity.ActivityMovementMenu;
import com.wiseass.postrainer.ui.widget.NavigationListItem;

import java.util.ArrayList;
import java.util.List;


public class FragmentNavigationDrawer extends Fragment {
    private static final String PREF_FILE_NAME = "";
    private static final String KEY_USER_LEARNED_DRAWER = "USER_LEARNED_DRAWER";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private NavigationDrawerAdapter adapterNav;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstance;
    private RecyclerView mNavList;

    public FragmentNavigationDrawer() {
        // Required empty public constructor
    }

    //TODO: something needs to be fixed here with containerView

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreference(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstance = true;
        }
    }

    public static List<NavigationListItem> getNavigationData() {
        List<NavigationListItem> data = new ArrayList<>();
        int[] icons = {R.drawable.ic_alarm_black_48dp, R.drawable.ic_accessibility_black_24dp, R.drawable.ic_info_outline_black_24dp};
        String[] titles = {"Reminders", "Movements", "About"};
        for (int i = 0; i < titles.length && i < icons.length; i++) {
            NavigationListItem item = new NavigationListItem();
            item.icon = icons[i];
            item.title = titles[i];
            data.add(item);
        }
        return data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        mNavList = (RecyclerView) layout.findViewById(R.id.navigation_list);
        adapterNav = new NavigationDrawerAdapter(getActivity(), getNavigationData());
        adapterNav.setOnClickListener(new NavigationDrawerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                Intent i;
                if (position == 0) {
                    i = new Intent(getActivity(), ActivityReminders.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.fade_in_shadow, R.anim.fade_out_right);
                } else if (position == 1) {
                    i = new Intent(getActivity(), ActivityMovementMenu.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.fade_in_shadow, R.anim.fade_out_right);
                } else if (position == 2) {
                    i = new Intent(getActivity(), ActivitySettings.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.fade_in_shadow, R.anim.fade_out_right);
                } /*else if (position == 3) {
                    i = new Intent(getActivity(), ActivityPreferences.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.fade_in_shadow, R.anim.fade_out_right);
                }*/
            }
        });

        mNavList.setAdapter(adapterNav);
        mNavList.setLayoutManager(new LinearLayoutManager(getActivity()));

        return layout;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolBar) {

        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolBar,
                R.string.drawer_open,
                R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                drawerView.bringToFront();
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreference(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

                getActivity().invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);

            }
        };
        /*if (!mUserLearnedDrawer && !mFromSavedInstance){
            mDrawerLayout.openDrawer(containerView);
        }*/
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }


    public static void saveToPreference(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String readFromPreference(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, value);
    }
}