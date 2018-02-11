package com.crycetruly.a4app;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crycetruly.a4app.models.Councillor;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import de.hdodenhof.circleimageview.CircleImageView;

public class CouncillorsActivity extends AppCompatActivity {
    Toolbar toolbar;
    private RecyclerView recyclerview;
    private ProgressBar progressBar;
    private Context mContext = this;
    private FirestoreRecyclerAdapter adapter;
    private static final String TAG = "CouncillorsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_councillors);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Counsellors");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar=findViewById(R.id.progress_bar);


        init();

        Query query = FirebaseFirestore.getInstance()
                .collection("counsellors");

        FirestoreRecyclerOptions<Councillor> options = new FirestoreRecyclerOptions.Builder<Councillor>()
                .setQuery(query, Councillor.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Councillor, CouncillorViewHollder>(options) {
            @Override
            public void onBindViewHolder(@NonNull final CouncillorViewHollder holder, final int position, final Councillor model) {
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String key = getSnapshots().getSnapshot(position).getId();
                        Log.d(TAG, "onClick: key");
                        Intent i=new Intent(getBaseContext(),CounsellorDetailActivity.class);
                        i.putExtra("snap",key);
                        i.putExtra("cname",model.getName());
                        i.putExtra("cpic",model.getPhoto());
                        i.putExtra("cprofile",model.getProfile());
                        Pair[] pairs=new Pair[3];

                        pairs[0]=new Pair<View,String>(holder.image,"cpic");
                        pairs[1]=new Pair<View,String>(holder.name,"cname");
                        pairs[2]=new Pair<View,String>(holder.desc,"cprofile");

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            ActivityOptions activityOptions=ActivityOptions.makeSceneTransitionAnimation(CouncillorsActivity.this,pairs);
                            startActivity(i,activityOptions.toBundle());

                        }
                        startActivity(i);
                    }
                });
                holder.setGender(model.getGender());
                holder.setName(model.getName());
                holder.setProfileDesc(model.getProfile());
                holder.setPhoto(model.getPhoto(),getApplicationContext());
                holder.setAwayAvailable(model.isAway());
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onBindViewHolder: onbindviewholder called");
            }

            @Override
            public CouncillorViewHollder onCreateViewHolder(ViewGroup group, int i) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.single_councillor, group, false);

                return new CouncillorViewHollder(view);
            }
        };

        recyclerview.setAdapter(adapter
        );

    }

    @Override
    protected void onStart() {
        adapter.startListening();
        super.onStart();
    }

    @Override
    protected void onStop() {
        adapter.stopListening();
        super.onStop();
    }

    private void init() {
        progressBar = findViewById(R.id.progress_bar);
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public static class CouncillorViewHollder extends RecyclerView.ViewHolder {
        View mView;
         CircleImageView image;
        TextView name,desc;

        public CouncillorViewHollder(View itemView) {
            super(itemView);
            mView = itemView;
            image= mView.findViewById(R.id.cpic);
            name=mView.findViewById(R.id.cname);
            desc=mView.findViewById(R.id.cprofile);
        }


        public void setPhoto(String photo, Context context) {
            CircleImageView imageView = mView.findViewById(R.id.cpic);
            Glide.with(context).load(photo).into(imageView);
        }

        public void setName(String name) {
            TextView textView = mView.findViewById(R.id.cname);
            textView.setText(name.trim());
        }

        public void setGender(String name) {
            TextView genderview = mView.findViewById(R.id.gender);
            genderview.setText(name.substring(0, 1));
            genderview.setTypeface(genderview.getTypeface(), Typeface.BOLD);
        }

        public void setProfileDesc(String name) {
            TextView prof = mView.findViewById(R.id.cprofile);
            prof.setText(name.trim());
        }

        public void setAwayAvailable(boolean time) {
            TextView away = mView.findViewById(R.id.away);
            if (time) {
                away.setText("Available");
            } else {
                away.setText("Away");
            }
        }
    }}