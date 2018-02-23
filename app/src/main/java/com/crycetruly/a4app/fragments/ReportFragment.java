package com.crycetruly.a4app.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crycetruly.a4app.MainActivity;
import com.crycetruly.a4app.R;
import com.crycetruly.a4app.Start2Activity;
import com.crycetruly.a4app.StartActivity;
import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {
    private FirebaseFirestore firestore;
    private FirebaseAuth muth;
    private TextInputEditText bithday, desc, phone, occurdate;
    private Button adddata;
    CheckBox checked;
    private ProgressBar progressBar;
    private static final String TAG = "ReportFragment";
    private String district;
    private String province;
    ScrollView view2;
    LinearLayout one, two;
    RadioGroup group;
    SwipeButton enableButton;
    Boolean isFriend = false;
    String reported_by;
    private Spinner spinner;
    private String categorySelected = "";
    private String reportedby = "";
    private String reportedFrom = "";
    TextView where;
    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);




        return view;
    }

}
