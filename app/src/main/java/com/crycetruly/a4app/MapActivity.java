package com.crycetruly.a4app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class MapActivity extends AppCompatActivity {
    private static final String TAG = "MapActivity";
    private String pname, description;
    private double longtitude, latitude;
    private Context context;
    private GoogleMap mGoogleMap;
    private DatabaseReference mDatabase;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        context = MapActivity.this;
        toolbar = findViewById(R.id.mapbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pname = getIntent().getStringExtra("name");
        description = getIntent().getStringExtra("desc");
        latitude = Double.parseDouble(getIntent().getStringExtra("lat"));
        Log.d(TAG, "onDataChange: latitude" + latitude);
        longtitude = Double.parseDouble((getIntent().getStringExtra("lng")));
        Log.d(TAG, "onDataChange: " + longtitude);
        initMap(latitude, longtitude, pname, description);
        getSupportActionBar().setTitle(pname);

    }


    private void initMap(final double latitude, final double longtitude, final String pname, final String description) {
        Log.d(TAG, "initMap: cords" + latitude + longtitude);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                MarkerOptions options = new MarkerOptions()
                        .title(pname)
                        .visible(true).snippet(description)
                        .position(new LatLng(latitude, longtitude));
                mGoogleMap.addMarker(options);

                goToLocationZoom(latitude, longtitude, 15);

            }
        });
    }


    private void goToLocationZoom(double latitude, double longtitude, float zoom) {
        LatLng ll = new LatLng(latitude, longtitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.moveCamera(cameraUpdate);

    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
