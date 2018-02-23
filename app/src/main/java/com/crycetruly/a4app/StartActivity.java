package com.crycetruly.a4app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.crycetruly.a4app.utils.Handy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StartActivity extends AppCompatActivity {
    private String district, reported_by = "";
    private static final String TAG = "StartActivity";
    private static String dateComparable;
    private static int datetoday;
    TextInputEditText event_date;
    Button submit;
    ProgressBar progressBar;
    TextInputEditText desc, occurdate, where, age, when;
    FirebaseFirestore firestore;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_report);
        event_date = findViewById(R.id.date);
        event_date.setEnabled(false);
        submit = findViewById(R.id.submitt);
        toolbar=findViewById(R.id.idea);

        setUpToolbar();

        firestore=FirebaseFirestore.getInstance();
        init();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });


        //SORT THE ARRAYFIRST
        String[] districts = getResources().getStringArray(R.array.districts);
        ArrayList<String> d = new ArrayList<>(Arrays.asList(districts));
        Collections.sort(d);
        Spinner spinner1 = findViewById(R.id.spinnerdistrict);
        final ArrayAdapter<CharSequence> sequenceArrayAdapter =
                new ArrayAdapter<CharSequence>
                        (this, android.R.layout.simple_spinner_dropdown_item);
        sequenceArrayAdapter.addAll(d);
        spinner1.setAdapter(sequenceArrayAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                district = String.valueOf(sequenceArrayAdapter.getItem(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void validate() {
        Log.d(TAG, "validate: validating");
        SharedPreferences preferences = getSharedPreferences("locality", MODE_PRIVATE);
        String province = preferences.getString("adminarea", "");
        String reportedFrom = preferences.getString("locality", "");
        String years = age.getText().toString().trim();
        String whenn = when.getText().toString().trim();
        String description = desc.getText().toString().trim();
        String phone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        long reported = System.currentTimeMillis();


        if (TextUtils.isEmpty(years)) {
            Toast.makeText(this, "Please add victim`s age", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(district)) {
            Toast.makeText(this, "Please choose where it happened", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Please add some description of what happened", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(whenn)) {
            Toast.makeText(this, "Please choose when it happened", Toast.LENGTH_SHORT).show();
            return;
        } else if (datetoday > Handy.nowTime()) {
            Toast.makeText(this, "The occur date if after today,please choose another date", Toast.LENGTH_SHORT).show();
            return;
        }
    submit.setEnabled(false);
        submit.setText("Submitting");

        progressBar.setVisibility(View.VISIBLE);

        submitDataToFireStore(province, reportedFrom, years, whenn, description, district, phone, reported);

    }
    private void submitDataToFireStore(final String province, String reportedFrom, String years, String whenn, String description, String district, String phone, long reported) {
        Log.d(TAG, "submitDataToFireStore: ");

        Map<String, Object> stringMap = new HashMap<>();
        stringMap.put("phone", phone);
        stringMap.put("happened", whenn);
        stringMap.put("reportedFrom", reportedFrom);
        stringMap.put("description", description);
        stringMap.put("district", district);
        stringMap.put("province", province);
        stringMap.put("reported_by", "self");
        stringMap.put("user", FirebaseAuth.getInstance().getCurrentUser().getUid());
        stringMap.put("reported", reported);

        firestore.collection("cases").add(stringMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Log.d(TAG, "onComplete: commpleted");
                Toast.makeText(getBaseContext(), "Your case has been submitted successfuly", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StartActivity.this, "Failed to submit your case :"+e.getMessage(), Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            submit.setText("Submit");
            submit.setEnabled(true);
            }
        });
    }



//
////---------provinces
//        //SORT THE ARRAYFIRST
//        String[] provinces = getResources().getStringArray(R.array.relations);
//        ArrayList<String> province = new ArrayList<>(Arrays.asList(provinces));
//        Collections.sort(province);
//        Spinner spinner2 = findViewById(R.id.relation);
//        final ArrayAdapter<CharSequence> sequenceArrayAdapter1 =
//                new ArrayAdapter<CharSequence>
//                        (getBaseContext(), android.R.layout.simple_spinner_dropdown_item);
//        sequenceArrayAdapter1.addAll(province);
//        spinner2.setAdapter(sequenceArrayAdapter1);
//
//        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                reported_by = String.valueOf(sequenceArrayAdapter1.getItem(i));
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//
//    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @android.support.annotation.NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String date = String.valueOf(String.format("%02d", day)) + "/" + String.valueOf(String.format("%02d", month + 1)) + "/" + String.valueOf(year);
            String testdate = String.valueOf(String.valueOf(year) + String.valueOf(String.format("%02d", month + 1)) + String.format("%02d", day)) + "/";
            dateComparable = testdate.replace("/", "");
            datetoday = Integer.parseInt(dateComparable);
            TextInputEditText editText = getActivity().findViewById(R.id.date);

            editText.setText(date);
        }
    }

    private void init() {

        occurdate = findViewById(R.id.date);
        desc = findViewById(R.id.desc);
        progressBar = findViewById(R.id.bar);
        age = findViewById(R.id.age);
        when = findViewById(R.id.date);
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

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onCreateView: not self");
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        super.onBackPressed();
    }

    private void setUpToolbar() {
        Log.d(TAG, "setUpToolbar: ");
        toolbar=findViewById(R.id.idea);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Report");
    }
}
