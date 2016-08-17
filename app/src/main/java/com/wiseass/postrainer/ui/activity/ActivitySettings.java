package com.wiseass.postrainer.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.wiseass.postrainer.R;
import com.wiseass.postrainer.ui.fragment.FragmentNavigationDrawer;

public class ActivitySettings extends AppCompatActivity {

    private Button disclaimer, contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.settings_title);

        FragmentNavigationDrawer drawerFragment = (FragmentNavigationDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        drawerFragment.setUp(R.id.fragment_navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout_settings), toolbar);

        disclaimer = (Button)findViewById(R.id.btn_settings_disclaimer);
        disclaimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(getString(R.string.disclaimer_title), getString(R.string.disclaimer_body));
            }
        });
        contact = (Button)findViewById(R.id.btn_settings_contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(getString(R.string.contact_title), getString(R.string.contact_info));
            }
        });


    }

    private void showDialog(String title, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(true);

        AlertDialog alert = dialog.create();

        alert.show();
    }
}
