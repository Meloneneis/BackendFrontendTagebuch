package com.example.niklas.backendtagebuch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.niklas.backendtagebuch.database.EntryDatabase;
import com.example.niklas.backendtagebuch.model.Entry;

public class ContentActivity extends AppCompatActivity {

    public static final String ENTRY_ID_KEY = "ID";
    private Entry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        long id = getIntent().getLongExtra(ENTRY_ID_KEY,0);
        this.entry = EntryDatabase.getInstance(this).readEntry(id);
    }
}
