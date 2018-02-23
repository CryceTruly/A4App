package com.crycetruly.a4app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.auth.ui.phone.PhoneActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AddPhoneActivity extends AppCompatActivity {
    private Toolbar toolbar;
    EditText editText,name;
    Button button;
    private static final String TAG = "AddPhoneActivity";
    ProgressBar progressBar;
    LinearLayout relativeLayout,mainrel;
    DatabaseReference ref;

    Button addname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone);
FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
progressBar=findViewById(R.id.progress_bar);
progressBar.setVisibility(View.GONE);
relativeLayout=findViewById(R.id.main);
mainrel=findViewById(R.id.main_rel_layout);
name=findViewById(R.id.nameInputSystem);
addname=findViewById(R.id.addnamebtn);
relativeLayout.setVisibility(View.GONE);
ref= FirebaseDatabase.getInstance().getReference();



addname.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        final String nametext=name.getText().toString();
        if(!TextUtils.isEmpty(nametext))
        {
            Map map=new HashMap();
            map.put("name",nametext);
            map.put("phone",FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
            map.put("device_token", FirebaseInstanceId.getInstance().getToken());
            ref.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        UserProfileChangeRequest.Builder req
                                = new UserProfileChangeRequest.Builder().setDisplayName(nametext);
                        req.build();

                        if (getIntent().hasExtra("from")) {
                            Intent i = new Intent(AddPhoneActivity.this, DetailActivity.class);
                            AddPhoneActivity.this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            i.putExtra("key", "council");
                            startActivity(i);
                        } else {


                            Intent i = new Intent(getBaseContext(), DetailActivity.class);
                            AddPhoneActivity.this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            i.putExtra("key", "report");
                            startActivity(i);
                        }
                    }
                }
            });
        }
    }
});



    if (user.getPhoneNumber().length()==0){

    }else{
        SharedPreferences sharedPreferences = getSharedPreferences("reportby", MODE_PRIVATE);
        String by = sharedPreferences.getString("reportedby", "");
        if (by.equals("self")) {
            Log.d(TAG, "onCreateView: self");
            Intent i = new Intent(this, StartActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }else {
            Log.d(TAG, "onCreateView: not self");
            Intent i = new Intent(this, Start2Activity.class);
            startActivity(i);

        }
        Log.d(TAG, "onCreate: +"+user.getPhoneNumber());
    }

        toolbar = findViewById(R.id.phonevtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add your Phone Number to Continue");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText = findViewById(R.id.searchUserField);

        button = findViewById(R.id.verify);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = editText.getText().toString();

                if (phone.length() > 9){




                    progressBar.setVisibility(View.VISIBLE);
                    editText.setEnabled(false);
                    button.setEnabled(false);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phone,
                            60,
                            TimeUnit.SECONDS,
                            AddPhoneActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            progressBar.setVisibility(View.VISIBLE);
                                            editText.setEnabled(false);
                                            button.setEnabled(false);

                                            mainrel.setVisibility(View.GONE);
                                            relativeLayout.setVisibility(View.VISIBLE);
                                            button.setVisibility(View.GONE);




                                        }
                                    });
                                }

                                @Override
                                public void onVerificationFailed(FirebaseException e) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(AddPhoneActivity.this, "Could not verify your phone,try again later", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(AddPhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    editText.setEnabled(true);
                                    button.setEnabled(true);
                                    relativeLayout.setVisibility(View.GONE);
                                }
                            }
                    );
                }else {
                    Toast.makeText(AddPhoneActivity.this, "Phone is invalid,should be atleat 10 characters,no country code", Toast.LENGTH_SHORT).show();
                return;}



            }
        });


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
