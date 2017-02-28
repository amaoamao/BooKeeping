package com.amaoamao.hsq.bookeeping;

import android.animation.Animator;
import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amaoamao.hsq.bookeeping.Entity.Debt;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener, DetailFragment.OnListFragmentInteractionListener, AddDebtFragment.OnFragmentInteractionListener {
    private FloatingActionButton fab;
    private View.OnClickListener listenerAdd = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fab.setOnClickListener(null);
            Fragment newFragment = AddDebtFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fg_main, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            fab.hide();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ;
        initFab();
        initToolBar();
    }

    private void initToolBar() {
//        ActionBar supportActionBar = getSupportActionBar();

    }


    private void initFab() {
        fab = (FloatingActionButton) findViewById(R.id.fab_add);
        fab.setOnClickListener(listenerAdd);
    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.get(fragments.size() - 1) instanceof AddDebtFragment) {
            ((AddDebtFragment) fragments.get(fragments.size() - 1)).showReveal(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(Debt item) {
        refreshRV();
    }

    public void onButtonClick(View view) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        ((AddDebtFragment) fragments.get(fragments.size() - 1)).onButtonClick(view);
    }

    public void setFabVisible() {
        fab.show();
        fab.setOnClickListener(listenerAdd);
    }

    public void refreshRV() {
        for (Fragment f : getSupportFragmentManager().getFragments()) {
            if (f instanceof DetailFragment) {
                ((DetailFragment) f).refreshRV();
            } else if (f instanceof MainFragment) {
                ((MainFragment) f).initTopBar(0);
            } else if (f instanceof AccountFragment) {
                ((AccountFragment) f).refreshRV();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                SearchActivity.start(this);
                break;
            case R.id.action_settings:
                SettingsActivity.start(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
