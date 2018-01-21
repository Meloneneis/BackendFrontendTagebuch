package com.example.niklas.backendtagebuch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.niklas.backendtagebuch.adapter.listview.EntryOverviewListAdapter;
import com.example.niklas.backendtagebuch.database.EntryDatabase;
import com.example.niklas.backendtagebuch.model.Entry;

import java.util.Calendar;
import java.util.Comparator;
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
        this.data = EntryDatabase.getInstance(this).readAllEntries();
        Button btndeleteall = (Button) findViewById(R.id.btndeleteall);
        Button btndeletefirst = (Button) findViewById(R.id.btndeletefirst);
        Button newEntrybtn = (Button) findViewById(R.id.createEntry);
        this.adapter = new EntryOverviewListAdapter(this, data);
        this.listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object element = adapterView.getAdapter().getItem(i);

                if(element instanceof Entry){
                    Entry entry = (Entry) element;
                    Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                    intent.putExtra(ContentActivity.ENTRY_ID_KEY,entry.getId());
                    startActivity(intent);
                }



            }
        });
        refreshListView();
        newEntrybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CreateNewEntry.class);
                startActivity(intent);
            }
        });

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
                    Entry first = database.getFirstEntry();
                    if(first != null){
                        database.deleteEntry(first);
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
