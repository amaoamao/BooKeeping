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
            fab.setImageResource(R.drawable.ic_done_white_48dp);
            fab.setOnClickListener(listenerDone);
        }
    };
    private View.OnClickListener listenerDone = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            if(((AddDebtFragment) fragments.get(fragments.size() - 1)).save(fab)){
                onBackPressed();
            }

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
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            fab.setImageResource(R.drawable.ic_add_white_48dp);
            fab.setOnClickListener(listenerAdd);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(Debt item) {

    }

}
