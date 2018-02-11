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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.crycetruly.a4app.MainActivity;
import com.crycetruly.a4app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {
private FirebaseFirestore firestore;
private FirebaseAuth muth;
private TextInputEditText bithday,desc,phone,occurdate;
private Button adddata;
private ProgressBar progressBar;
    private static final String TAG = "ReportFragment";
    private String district;
    private String province;
    ScrollView view2;
    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_report, container, false);
        init(view);

firestore=FirebaseFirestore.getInstance();


        adddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phonenum=phone.getText().toString();
                String descr=desc.getText().toString();
                String occurdat=occurdate.getText().toString();
                String birthd=bithday.getText().toString();
                validateVals(phonenum,descr,occurdat,birthd);

            }
        });

        return view;
    }

    private void validateVals(String phonenum, String descr, String occurdat, String birthd) {
        Log.d(TAG, "validateVals: ");
        if (TextUtils.isEmpty(phonenum) || TextUtils.isEmpty(descr) || TextUtils.isEmpty(occurdat) || TextUtils.isEmpty(birthd)) {
            Toast.makeText(getContext(), "Please fill all field", Toast.LENGTH_SHORT).show();
            return;
        }
            if (TextUtils.isEmpty(phonenum)){
                phone.setError("The phone number is required");
                return;
            }else  if (phonenum.length()<10){
                Toast.makeText(getContext(), "Phone is invalid", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(descr)){
                desc.setError("Please add some description");
                return;
            }


            if(TextUtils.isEmpty(occurdat)){
                occurdate.setError("Please choose date of the incident");
                return;
            }
            if (TextUtils.isEmpty(birthd)){
                bithday.setError("Please choose your date of birth");
                return;
            }
        SharedPreferences preferences=getContext().getSharedPreferences("locality",MODE_PRIVATE);
         district=preferences.getString("locality","");
         province=preferences.getString("adminarea","");

        sendInformationToFirestore(phonenum,occurdat,birthd,descr,district,province);

    }

    private void sendInformationToFirestore(String phonenum, String occurdat, String birthd, String descr, String district, String province) {
        Log.d(TAG, "sendInformationToFirestore: "+phonenum+occurdat+birthd+descr+district+province);
        progressBar.setVisibility(View.VISIBLE);
        adddata.setEnabled(false);
        adddata.setText("Submitting your case");
        Map<String,Object> stringMap=new HashMap<>();
        stringMap.put("phone",phonenum);
        stringMap.put("happened",occurdat);
        stringMap.put("description",descr);
        stringMap.put("district",district);
        stringMap.put("province",province);
        stringMap.put("user",FirebaseAuth.getInstance().getCurrentUser().getUid());
        stringMap.put("reported", System.currentTimeMillis());

        firestore.collection("cases").add(stringMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Log.d(TAG, "onComplete: commpleted");
              startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

    }

    private void init(View view) {
        bithday=view.findViewById(R.id.birthday);
        desc=view.findViewById(R.id.desc);
        phone=view.findViewById(R.id.phone);
        occurdate=view.findViewById(R.id.occurdate);
        adddata=view.findViewById(R.id.adddata);
        progressBar=view.findViewById(R.id.bar);
        view2=view.findViewById(R.id.view);}

}
