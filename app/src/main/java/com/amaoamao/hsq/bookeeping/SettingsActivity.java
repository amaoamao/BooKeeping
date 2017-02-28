package com.amaoamao.hsq.bookeeping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amaoamao.hsq.bookeeping.Entity.Account;

public class SettingsActivity extends AppCompatActivity {
    public static void start(Context c) {
        Intent intent = new Intent(c, SettingsActivity.class);
        c.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void newAccount(View view) {
        new MaterialDialog.Builder(this).title("账户名").input(null, null, false, (dialog, input) -> {
            Account account = new Account();
            account.setName(input.toString());
            account.save();
        }).show();
    }

    public void setPlan(View view) {
        SharedPreferences preferences = getPreferences(0);

        new MaterialDialog.Builder(this).title("预算").input(null, String.valueOf(preferences.getInt("PLAN", 0)), false, (dialog, input) -> {
            preferences.edit().putInt("PLAN", Integer.parseInt(input.toString())).apply();

        }).show();
    }
}
