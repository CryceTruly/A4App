package com.crycetruly.a4app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationItemView;
    private FrameLayout frameLayout;
    private Fragment home;

    private CardView card1,card2,card3;
    CardView card4;
    private static final String TAG = "MainActivity";
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mLocationPermissionsGranted;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 11;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainn);
        initWidgets();
        initLocationStuff();

        try {
            FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "You are signed in anonomously", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onComplete: user" + FirebaseAuth.getInstance().getCurrentUser().getUid());

                    }
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        setUpToolbar();
    }


        private void initWidgets() {
            mContext=MainActivity.this;
            card1=findViewById(R.id.card1);
            card2=findViewById(R.id.card2);
            card3=findViewById(R.id.card3);
            card4=findViewById(R.id.card4);
            card1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(mContext,DetailActivity.class);
                    MainActivity.this.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    i.putExtra("key","treat");
                    startActivity(i);

                }
            });

            card2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(mContext,DetailActivity.class);
                    MainActivity.this.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    i.putExtra("key","council");
                    startActivity(i);

                }
            });

            card3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    CharSequence [] op={"Report by Self","Report by some one else"};
                    builder.setItems(op, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (i==0){
                                Intent intent=new Intent(getBaseContext(),AddPhoneActivity.class);
                                SharedPreferences sharedPreferences= getSharedPreferences("reportby",MODE_PRIVATE);
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putString("reportedby","self");
                                editor.apply();
                                startActivity(intent);
                            }else {
                                Intent intent=new Intent(getBaseContext(),AddPhoneActivity.class);
                                SharedPreferences sharedPreferences= getSharedPreferences("reportby",MODE_PRIVATE);
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putString("reportedby","other");
                                editor.apply();
                                startActivity(intent);
                            }
                        }
                    }).setCancelable(false).show();


//               todo use me
//                    Intent i=new Intent(mContext,DetailActivity.class);
//                    MainActivity.this.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
//                    i.putExtra("key","report");
//                    startActivity(i);

                }
            });

            card4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(mContext,DetailActivity.class);
                    MainActivity.this.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    i.putExtra("key","about");
                    startActivity(i);

                }
            });
        }

    public void initLocationStuff() {
getLocationPermission();
getDeviceLocation();

    }
        public  void getDeviceLocation() {
            Log.d(TAG, "getDeviceLocation: getting the devices current location");

            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getBaseContext());

            try {
                if (mLocationPermissionsGranted) {

                    final Task location = mFusedLocationProviderClient.getLastLocation();
                    location.addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "onComplete: found location!");
                                Location currentLocation = (Location) task.getResult();
                                Geocoder geocoder = new Geocoder(getBaseContext());
                                try {
                                    try {

                                        List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);

                                        try {
                                            Log.d(TAG, "onComplete: " + currentLocation.getLatitude() + currentLocation.getLongitude());
                                        } catch (NullPointerException e) {
                                            Log.d(TAG, "onComplete: current location is null");

                                        }
                                        Address place = addresses.get(0);
                                        Log.d(TAG, "onComplete: " + place.toString());
                                        Log.d(TAG, "onComplete: " + place.getLocality());
                                        SharedPreferences preferences = getSharedPreferences("locality", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("locality", place.getLocality());
                                        try {
                                            editor.putString("aaddress", place.getAddressLine(1));
                                        } catch (Exception e) {

                                        }

                                        try {
                                            editor.putString("adminarea", place.getAdminArea());
                                        } catch (Exception e) {

                                        }
                                        try {
                                            editor.putString("sublocality", place.getSubLocality());
                                        } catch (Exception e) {

                                        }
                                        try {

                                            editor.putString("feature", place.getFeatureName());
                                            editor.putString("subadminarea", place.getSubAdminArea());
                                        } catch (Exception E) {

                                        }
                                        editor.apply();
                                    } catch (NullPointerException e) {
                                       }


                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Log.d(TAG, "onComplete: something wrong happemed" + e.getMessage());
                                }


                            } else {
                                Log.d(TAG, "onComplete: current location is null");
                                Toast.makeText(getBaseContext(), "unable to get current location", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            } catch (SecurityException e) {
                Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
            }
        }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;

            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;


                }
            }
        }

    }

    private void setUpToolbar() {
        Log.d(TAG, "setUpToolbar: ");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setSubtitle("Dont Die with it");
    }



    private void setFragment(Fragment fragment) {
        Log.d(TAG, "setFragment: ");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }

}
