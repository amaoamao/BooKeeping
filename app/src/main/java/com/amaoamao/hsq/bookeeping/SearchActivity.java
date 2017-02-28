package com.amaoamao.hsq.bookeeping;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public static void start(Context c) {
        Intent intent = new Intent(c, SearchActivity.class);
        c.startActivity(intent);
    }
}
