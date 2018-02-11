package com.crycetruly.a4app;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MeActivity extends AppCompatActivity {
    Toolbar toolbar;
    private static final String TAG = "MeActivity";
    TextView adminarea;
    ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        adminarea = findViewById(R.id.adminarea);
        getSupportActionBar().setTitle("Me");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = findViewById(R.id.user_id);
        textView.setText("User " + FirebaseAuth.getInstance().getUid());
        TextView location = findViewById(R.id.location);
        SharedPreferences preferences = getSharedPreferences("locality", MODE_PRIVATE);
        String province = preferences.getString("adminarea", "");
        String loc = preferences.getString("locality", "");
        location.setText(loc);
        String sub = preferences.getString("feature", "");
        if (!sub.equals("")) {
            location.append(" " + sub);
        }
        Log.d(TAG, "onCreate: " + preferences.getString("aaddress", ""));

        if (!province.equals("")) {
            adminarea.setText(province);


        }
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
