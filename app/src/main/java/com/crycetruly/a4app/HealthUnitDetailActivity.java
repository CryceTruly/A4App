package com.crycetruly.a4app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crycetruly.a4app.MapActivity;
import com.crycetruly.a4app.R;

public class HealthUnitDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    String key;
    String name, pic, desc, opentime, district;
    TextView names, des, open;
    ImageView imageView;
    TextView button, dist;
    String lat, lng, phone;
    RelativeLayout it;
    private static final String TAG = "HealthUnitDetailActivit";
    private boolean mPermissionGranted=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_unit_detail);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("HealthUnit Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = getIntent().getStringExtra("cname");
        desc = getIntent().getStringExtra("cprofile");
        pic = getIntent().getStringExtra("cpic");
        opentime = getIntent().getStringExtra("copen");
        district = getIntent().getStringExtra("district");
        phone = getIntent().getStringExtra("phone");

        it = findViewById(R.id.it)
        ;
        dist = findViewById(R.id.district);
        dist.setText(district);

        lat = getIntent().getStringExtra("lat");
        lng = getIntent().getStringExtra("lng");




        imageView = findViewById(R.id.healthunitphoto);
        open = findViewById(R.id.open);
        open.setText(opentime);
        names = findViewById(R.id.name);
        des = findViewById(R.id.description);
        Glide.with(getBaseContext()).load(pic).into(imageView);
        names.setText(name);
        des.setText(desc);
        key = getIntent().getStringExtra("snap");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.unitdetail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.one:
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.putExtra(Intent.EXTRA_PHONE_NUMBER, Integer.parseInt("0" + phone));

                if (intent.resolveActivity(getPackageManager()) != null) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                       ActivityCompat.requestPermissions(this,new String[]{"android.Manifest.permission.CALL_PHONE"},5);

                    }else{

                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(this, "Unable to handle data", Toast.LENGTH_SHORT).show();
                }


                Log.d(TAG, "onOptionsItemSelected: calling");
                break;

            case R.id.two:
              Intent intent1=new Intent(Intent.ACTION_SENDTO);
              intent1.setType("plain/text");
              intent1.putExtra(Intent.EXTRA_PHONE_NUMBER,phone);
              if(intent1.resolveActivity(getPackageManager())!=null){
                  startActivity(intent1);
            }else{
                  Toast.makeText(this, "No apps", Toast.LENGTH_SHORT).show();
              }


                Log.d(TAG, "onOptionsItemSelected: messahe");
                break;
            case R.id.three:
                Intent i=new Intent(getBaseContext(),MapActivity.class);
                i.putExtra("healthunitid",key);
                i.putExtra("name",name);
                i.putExtra("desc",desc);
                i.putExtra("lat",lat);
                i.putExtra("lng",lng);
                startActivity(i);
                break;
        }



        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==5){
            if (grantResults.length>0){
                mPermissionGranted=true;
            }else{
                Toast.makeText(this, "No permissions to make phone calls", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
