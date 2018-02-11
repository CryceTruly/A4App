package com.crycetruly.a4app;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class AppIntroActivity extends AppIntro2 {
    private static final String TAG = "AppIntroActivity";

    @Override
    public void onDonePressed() {
        Log.d(TAG, "onDonePressed: done");
        startActivity(new Intent(this,MainActivity.class));
        super.onDonePressed();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        startActivity(new Intent(this,MainActivity.class));
        super.onSkipPressed(currentFragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setBarColor(Color.parseColor("#000"));


        addSlide(AppIntroFragment.newInstance("Have been assualted", "Have you been raped and are looking for help?", R.drawable.counsellor, R.color.colorPrimary));
        addSlide(AppIntroFragment.newInstance("Get Treatment", "Locate nearby health units where you can get Post Exposure Prevention?",R.drawable.counsellor, R.color.colorPrimary));
        addSlide(AppIntroFragment.newInstance("Counselling", "Get helped by engaging in a one on one talk with a medical counsellor who will help you with with your issues",R.drawable.counsellor, R.color.colorPrimary));
        addSlide(AppIntroFragment.newInstance("Seek Justice", "Report to the law enforcers so that the proprietors can be put to law",R.drawable.police, R.color.colorPrimary));


    }


}

