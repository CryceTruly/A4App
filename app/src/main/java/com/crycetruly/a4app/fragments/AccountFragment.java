package com.crycetruly.a4app.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crycetruly.a4app.R;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Elia on 1/27/2018.
 */

public class AccountFragment extends Fragment {
    TextView adminarea,user_name;
    private static final String TAG = "AccountFragment";
    public AccountFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_me,container,false);
        adminarea = v.findViewById(R.id.adminarea);
        user_name=v.findViewById(R.id.user_name);
        user_name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        TextView textView = v.findViewById(R.id.user_id);
        textView.setText("User " + FirebaseAuth.getInstance().getUid());
        TextView location = v.findViewById(R.id.location);
        SharedPreferences preferences = getActivity().getSharedPreferences("locality", MODE_PRIVATE);
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
        return v;
    }


}
