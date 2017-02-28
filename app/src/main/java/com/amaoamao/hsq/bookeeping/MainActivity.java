package com.amaoamao.hsq.bookeeping;

import android.animation.Animator;
import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        initFab();

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
}
