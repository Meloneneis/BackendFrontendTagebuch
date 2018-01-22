package com.example.niklas.backendtagebuch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.niklas.backendtagebuch.database.EntryDatabase;
import com.example.niklas.backendtagebuch.model.Entry;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

public class ContentActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String ENTRY_ID_KEY = "ID";
    TextView title;
    TextView date;
    TextView content;
    private Entry entry;
    Button share;
    Button delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        long id = getIntent().getLongExtra(ENTRY_ID_KEY,0);
        title=(TextView) findViewById(R.id.title);
        date=(TextView) findViewById(R.id.date_day);
        content=(TextView) findViewById(R.id.content);
        share=(Button) findViewById(R.id.share);
        delete = (Button) findViewById(R.id.delete);
        MapFragment fragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment);
        fragment.getMapAsync(this);
        this.entry = EntryDatabase.getInstance(this).readEntry(id);
        title.setText(entry.getTitle());
        date.setText(entry.getDate());
        content.setText(entry.getContent());
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String text = entry.getTitle() + "\nverfasst am " + entry.getDate()+ ":\n" + entry.getContent();
                intent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(intent, "Teile deinen Tag mit:"));
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EntryDatabase database = EntryDatabase.getInstance(ContentActivity.this);
                database.deleteEntry(entry);
                Intent intent = new Intent(ContentActivity.this, MainActivity.class);
                startActivity(intent);;

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(entry.getLocation()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(entry.getLocation(),15));
    }
}
