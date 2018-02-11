package com.crycetruly.a4app;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crycetruly.a4app.MapActivity;
import com.crycetruly.a4app.R;

public class HealthUnitDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    String key;
    String name,pic,desc,opentime,district;
    TextView names,des,open;
    ImageView imageView;
    TextView button,dist;
    String lat,lng;
    private static final String TAG = "HealthUnitDetailActivit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_unit_detail);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("HealthUnit Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name=getIntent().getStringExtra("cname");
        desc=getIntent().getStringExtra("cprofile");
        pic=getIntent().getStringExtra("cpic");
        opentime=getIntent().getStringExtra("copen");
        district=getIntent().getStringExtra("district");

        dist=findViewById(R.id.district);
        dist.setText(district);

        lat=getIntent().getStringExtra("lat");
                lng=getIntent().getStringExtra("lng");

        button=findViewById(R.id.map);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getBaseContext(),MapActivity.class);
                i.putExtra("healthunitid",key);
                i.putExtra("name",name);
                i.putExtra("desc",desc);
                i.putExtra("lat",lat);
                i.putExtra("lng",lng);
                startActivity(i);


            }
        });






        imageView=findViewById(R.id.healthunitphoto);
        open=findViewById(R.id.open);
        open.setText(opentime);
        names=findViewById(R.id.name);
        des=findViewById(R.id.description);
        Glide.with(getBaseContext()).load(pic).into(imageView);
        names.setText(name);
        des.setText(desc);
        key=getIntent().getStringExtra("snap");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
