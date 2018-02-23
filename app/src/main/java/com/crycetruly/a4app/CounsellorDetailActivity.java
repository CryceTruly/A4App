package com.crycetruly.a4app;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
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

public class CounsellorDetailActivity extends AppCompatActivity {
Toolbar toolbar;
String key;
String name,pic,desc;
TextView names,des;
ImageView imageView;
Button message;
    private static final String TAG = "CounsellorDetailActivit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor_detail);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Counsellor Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name=getIntent().getStringExtra("cname");
        desc=getIntent().getStringExtra("cprofile");
        pic=getIntent().getStringExtra("cpic");
        message=findViewById(R.id.messagebtn);


        imageView=findViewById(R.id.cpic);
        names=findViewById(R.id.cname);
        des=findViewById(R.id.cprofile);
        Glide.with(getBaseContext()).load(pic).into(imageView);
        names.setText(name);
        des.setText(desc);
        key=getIntent().getStringExtra("snap");
        Log.d(TAG, "onCreate: "+key);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getBaseContext(),ChatActivity.class);
                i.putExtra("user_id",key);
                i.putExtra("name",name);
                i.putExtra("image",pic);
                startActivity(i);
            }
        });

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
