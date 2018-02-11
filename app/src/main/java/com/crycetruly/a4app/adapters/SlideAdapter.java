package com.crycetruly.a4app.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crycetruly.a4app.R;

/**
 * Created by Elia on 12/4/2017.
 */

public class SlideAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;

    public SlideAdapter(Context context) {
        this.context = context;
    }
    private int [] slideImages={
            R.drawable.one,R.drawable.one,R.drawable.one
    };
    private String [] headings={
            "What is PEP ?","Where can i get PEP ?","How effective is Pep ?"
    };

    private String [] descriptions={
          "PEP (post-exposure prophylaxis) means taking antiretroviral medicines (ART) after being potentially exposed to HIV to prevent becoming infected. PEP should be used only in emergency situations and must be started within 72 hours after a recent possible exposure to HIV. If you think you’ve recently been exposed to HIV during sex or through sharing needles and works to prepare drugs or if you’ve been sexually assaulted, talk to your health care provider or an emergency room doctor about PEP right away." ," PEP (post-exposure prophylaxis) means taking antiretroviral medicines (ART) after being potentially exposed to HIV to prevent becoming infected. PEP should be used only in emergency situations and must be started within 72 hours after a recent possible exposure to HIV. If you think you’ve recently been exposed to HIV during sex or through sharing needles and works to prepare drugs or if you’ve been sexually assaulted, talk to your health care provider or an emergency room doctor about PEP right away","  PEP (post-exposure prophylaxis) means taking antiretroviral medicines (ART) after being potentially exposed to HIV to prevent becoming infected. PEP should be used only in emergency situations and must be started within 72 hours after a recent possible exposure to HIV. If you think you’ve recently been exposed to HIV during sex or through sharing needles and works to prepare drugs or if you’ve been sexually assaulted, talk to your health care provider or an emergency room doctor about PEP right away"
    };

    @Override
    public int getCount() {
        return slideImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v=inflater.inflate(R.layout.slide_layout,container,false);


        TextView heading=v.findViewById(R.id.top);
        TextView bottom=v.findViewById(R.id.bottom);
        ImageView imageView=v.findViewById(R.id.pic);

        imageView.setImageResource(slideImages[position]);
        heading.setText(headings[position]);
        bottom.setText(descriptions[position]);

        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
