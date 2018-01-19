package com.example.niklas.backendtagebuch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.niklas.backendtagebuch.database.EntryDatabase;
import com.example.niklas.backendtagebuch.model.Entry;

import java.util.Calendar;

public class ContentActivity extends AppCompatActivity {

    public static final String ENTRY_ID_KEY = "ID";
    TextView title;
    TextView date;
    TextView content;
    private Entry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        long id = getIntent().getLongExtra(ENTRY_ID_KEY,0);
        title=(TextView) findViewById(R.id.title);
        date=(TextView) findViewById(R.id.date);
        content=(TextView) findViewById(R.id.content);
        this.entry = EntryDatabase.getInstance(this).readEntry(id);
        title.setText(entry.getTitle());
        date.setText(getDateInString(entry.getDate()));
        content.setText(entry.getContent());

    }

    private String getDateInString(Calendar calendar){
        return calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR);
    }
}
