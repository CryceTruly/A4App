package com.crycetruly.a4app.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crycetruly.a4app.HealthUnitsActivity;
import com.crycetruly.a4app.MainActivity;
import com.crycetruly.a4app.R;
import com.crycetruly.a4app.adapters.SlideAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HealthUnitsFragment extends Fragment {
    private SlideAdapter adapter;
    private RelativeLayout relativeLayout;
    private ViewPager pager;
    private TextView[] dots;
    private Button back,next,showme;
    private int mCurrentPge;
    private static final String TAG = "HealthUnitsFragment";

    public HealthUnitsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_justice, container, false);
    pager=v.findViewById(R.id.pager);
    back=v.findViewById(R.id.button);
    next=v.findViewById(R.id.button2);
        showme=v.findViewById(R.id.button3);
    adapter =new SlideAdapter(getContext());
        pager.setAdapter(adapter);
        back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pager.setCurrentItem(mCurrentPge-1);
        }
    });
        next.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pager.setCurrentItem(mCurrentPge+1);
            if (mCurrentPge==2)
            {
                Log.d(TAG, "onClick: done");
                //startActivity(new Intent(MainActivity.this,HomeActivity.class));
            }
        }
    });
        pager.addOnPageChangeListener(onPageChangeListener);

        return v;
}


    ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurrentPge=position;
            if (position ==0){
                back.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);
                showme.setVisibility(View.VISIBLE);
                showme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getContext(), HealthUnitsActivity.class));
                    }
                });
                back.setEnabled(false);
                next.setEnabled(true);
                back.setText("");
                next.setText("More");

            }else if (position==1){
                back.setEnabled(true);
                next.setEnabled(true);
                back.setVisibility(View.VISIBLE);
                back.setText("Back");
                next.setVisibility(View.VISIBLE);
                showme.setVisibility(View.VISIBLE);
                showme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getContext(), HealthUnitsActivity.class));
                    }
                });
                next.setText("More");

            }else {
                back.setEnabled(true);
                next.setEnabled(true);
                back.setVisibility(View.VISIBLE);
                back.setText("Back");
                next.setText("More");
                showme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getContext(), HealthUnitsActivity.class));
                    }
                });
                next.setVisibility(View.GONE);
                showme.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
