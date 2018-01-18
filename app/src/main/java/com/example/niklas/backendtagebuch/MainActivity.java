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

public class MainActivity extends AppCompatActivity {
        private ListView listView;
        private EntryOverviewListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.listView = (ListView) findViewById(R.id.entries);
        Button btnnew = (Button) findViewById(R.id.btnnew);
        Button btndeleteall = (Button) findViewById(R.id.btndeleteall);
        Button btndeletefirst = (Button) findViewById(R.id.btndeletefirst);
        Button btnsort = (Button) findViewById(R.id.btnsort);

        /*data.add(new Entry("bla", Calendar.getInstance()));*/
        this.adapter = new EntryOverviewListAdapter(this, EntryDatabase.getInstance(this).getAllEntriesAsCursor());
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

        /* not working yet loool
        if(btnsort != null){
            btnsort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.btnsort(new Comparator<Entry>()){
                        public int compare(final Entry entry, final Entry e1){
                            return 0;
                        }
                    }

                }
            });
        }
        */

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
        adapter.changeCursor(EntryDatabase.getInstance(this).getAllEntriesAsCursor());
    }
}
