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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;

public class ContentActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String ENTRY_ID_KEY = "ID";
    TextView title;
    TextView date;
    TextView content;
    private Entry entry;
    Button share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        long id = getIntent().getLongExtra(ENTRY_ID_KEY,0);
        title=(TextView) findViewById(R.id.title);
        date=(TextView) findViewById(R.id.date);
        content=(TextView) findViewById(R.id.content);
        share=(Button) findViewById(R.id.share);
        MapFragment fragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment);
        fragment.getMapAsync(this);
        this.entry = EntryDatabase.getInstance(this).readEntry(id);
        title.setText(entry.getTitle());
        date.setText(getDateInString(entry.getDate()));
        content.setText(entry.getContent());
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String text = entry.getTitle() + "\nverfasst am " + getDateInString(entry.getDate())+ ":\n" + entry.getContent();
                intent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(intent, "Teile deinen Tag mit:"));
            }
        });

    }

    private String getDateInString(Calendar calendar){
        return calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng position = new LatLng(0,0);
        googleMap.addMarker(new MarkerOptions().position(position));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,15));
    }
}
