package com.example.niklas.backendtagebuch;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.niklas.backendtagebuch.database.EntryDatabase;
import com.example.niklas.backendtagebuch.model.Entry;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;

public class CreateNewEntry extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    public Button locateme;
    public Button back;
    public GoogleMap map;
    public Marker marker;
    public LocationManager locationManager;
    public EditText title;
    public EditText content;
    public EditText date;
    public Button save;
    public Entry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_entry);

        this.entry = new Entry();
        this.locateme = (Button) findViewById(R.id.locate);
        this.save = (Button) findViewById(R.id.save);
        this.title = (EditText) findViewById(R.id.title);
        this.content = (EditText) findViewById(R.id.content);
        this.back = (Button) findViewById(R.id.back);
        this.date = (EditText) findViewById(R.id.date);

        Intent intent = getIntent();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment2);
        mapFragment.getMapAsync(this);

        this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (this.locateme != null) {
            this.locateme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    searchPosition();
                }
            });
        }

        this.date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                entry.setDate(editable.toString().length() == 0 ? null : editable.toString());
            }
        });

        this.title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                entry.setTitle(editable.toString().length() == 0 ? null : editable.toString());
            }
        });

        this.content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                entry.setContent(editable.toString().length() == 0 ? null : editable.toString());
            }
        });

        this.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if((entry.getLocation() == null) || (entry.getDate() == null) || (entry.getTitle() == null) || (entry.getContent() == null)){
                    Toast.makeText(CreateNewEntry.this, "Fehler beim Speichern, bitte alle Angababen befüllen.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                EntryDatabase.getInstance(CreateNewEntry.this).createEntry(entry);
            }}
        });

        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateNewEntry.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CreateNewEntry.this, MainActivity.class);
        startActivity(intent);
    }



    public void searchPosition() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermission(5);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 50, this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng position = new LatLng(location.getLatitude(),location.getLongitude());
        entry.setLocation(position);
        if(this.map != null) {

            if (this.marker != null) {
                this.marker.remove();
            }
            this.marker = map.addMarker(new MarkerOptions().position(position));
            this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
        }
        removeListener();
    }

    private void removeListener(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            this.requestPermission(4);
        }
        this.locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void requestPermission(final int resultCode){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, resultCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 5:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    searchPosition();
                }
                break;
            case 4:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    removeListener();
                }
                break;
        }

    }

}
