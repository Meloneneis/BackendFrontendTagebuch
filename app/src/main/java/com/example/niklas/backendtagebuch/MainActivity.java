package com.example.niklas.backendtagebuch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.niklas.backendtagebuch.adapter.listview.EntryOverviewListAdapter;
import com.example.niklas.backendtagebuch.database.EntryDatabase;
import com.example.niklas.backendtagebuch.model.Entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
        private ListView listView;
        private List<Entry> data;
        private EntryOverviewListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.listView = (ListView) findViewById(R.id.entries);
        Button btnnew = (Button) findViewById(R.id.btnnew);
        Button btndeleteall = (Button) findViewById(R.id.btndeleteall);
        Button btndeletefirst = (Button) findViewById(R.id.btndeletefirst);

        this.data = EntryDatabase.getInstance(this).readAllEntries();
        /*data.add(new Entry("bla", Calendar.getInstance()));*/
        this.adapter = new EntryOverviewListAdapter(this, data);
        this.listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object element = adapterView.getAdapter().getItem(i);

            }
        });
        if(btnnew != null){
            btnnew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EntryDatabase database = EntryDatabase.getInstance(MainActivity.this);
                    database.createEntry(new Entry("bla", Calendar.getInstance()));
                    refreshListView();
                }
            });
        }
        if(btndeleteall != null){
            btndeleteall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EntryDatabase database = EntryDatabase.getInstance(MainActivity.this);
                    database.deleteallEntries();
                    refreshListView();
                }
            });
        }
        if(btndeletefirst != null){
            btndeletefirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EntryDatabase database = EntryDatabase.getInstance(MainActivity.this);
                    if(data.size()> 0){
                        database.deleteEntry(data.get(0));
                        refreshListView();
                    }
                }
            });
        }
    }

    private void refreshListView(){
        data.clear();
        data.addAll(EntryDatabase.getInstance(this).readAllEntries());
        adapter.notifyDataSetChanged();
    }
}
