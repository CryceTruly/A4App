package com.crycetruly.a4app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.crycetruly.a4app.fragments.AccountFragment;
import com.crycetruly.a4app.fragments.HealthUnitsFragment;
import com.crycetruly.a4app.fragments.HomeFragment;
import com.crycetruly.a4app.fragments.ReportFragment;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    private String key_switch;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    Fragment report,justice,home,account;
    Toolbar current;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
       initFragments();
        current=findViewById(R.id.toolbar);
       setSupportActionBar(current);
getSupportActionBar().setDisplayHomeAsUpEnabled(true);

fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();

   
        try{
            key_switch=getIntent().getStringExtra("key");
            switch (key_switch){
                case "about":
                    current.setTitle("Me");
                    AccountFragment fragment=new AccountFragment();
                    fragmentTransaction.replace(R.id.main,fragment);
                    fragmentTransaction.commit();
                    Log.d(TAG, "onCreate: About");

                    break;
                case "report":
                    current.setTitle("Report");
                    Fragment fragment1=new ReportFragment();
                    fragmentTransaction.replace(R.id.main,fragment1);
                    fragmentTransaction.commit();
                    Log.d(TAG, "onCreate: Share");
                    break;
                case "treat":
                    current.setTitle("Measure");
                    Fragment fragment2=new HomeFragment();
                    fragmentTransaction.replace(R.id.main,fragment2);
                    fragmentTransaction.commit();
                    Log.d(TAG, "onCreate: Test");
                    break;
                case "council":
                    current.setTitle("Councillors");
                    Fragment fragment3=new HealthUnitsFragment();
                    fragmentTransaction.replace(R.id.main,fragment3);
                    fragmentTransaction.commit();
                    Log.d(TAG, "onCreate: History");
                    break;
            }
        }catch (NullPointerException ex){
            Log.d(TAG, "onCreate: NullPointerException "+ex.getMessage());
            ex.printStackTrace();
        }
    }
    private void initFragments() {
        Log.d(TAG, "initFragments: ");
        report = new ReportFragment();
        justice = new HealthUnitsFragment();
        account = new AccountFragment();
        home = new HomeFragment();
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
