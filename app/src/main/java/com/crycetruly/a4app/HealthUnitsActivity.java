package com.crycetruly.a4app;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.crycetruly.a4app.models.Healthunit;
import com.crycetruly.a4app.utils.Preferences;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HealthUnitsActivity extends AppCompatActivity {
    private static final int NUM_COLUMNS =2 ;
    private RecyclerView recyclerView;
    private static final String TAG = "HealthUnitsActivity";
    FirestoreRecyclerAdapter adapter;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_units);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Where to get Pep");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d(TAG, "onCreate: started "+getLocalClassName());

        progressBar=findViewById(R.id.progress_bar);

        recyclerView=findViewById(R.id.recyclerview);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setHasFixedSize(true);
        Query query = FirebaseFirestore.getInstance()
                .collection("healthunits")
                .whereEqualTo("locality", Preferences.getLocality(this));


        FirestoreRecyclerOptions<Healthunit> options = new FirestoreRecyclerOptions.Builder<Healthunit>()
                .setQuery(query, Healthunit.class)
                .build();

         adapter = new FirestoreRecyclerAdapter<Healthunit, ChatHolder>(options) {
            @Override
            public void onBindViewHolder(@NonNull final ChatHolder holder, final int position, final Healthunit model) {
                Log.d(TAG, "onBindViewHolder: "+model.toString());
               holder.setLocality(model.getLocality());
               holder.setName(model.getName());
                holder.setDescription(model.getDescription());
                holder.setOpen(model.getOpen());
               holder.setPhoto(model.getPhoto(),getApplicationContext());
               progressBar.setVisibility(View.GONE);



                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String key = getSnapshots().getSnapshot(position).getId();
                        Log.d(TAG, "onClick: key");
                        Intent i=new Intent(getBaseContext(),HealthUnitDetailActivity.class);
                        i.putExtra("snap",key);
                        i.putExtra("cname",model.getName());
                        i.putExtra("district",model.getLocality());
                        i.putExtra("cpic",model.getPhoto());
                        i.putExtra("cprofile",model.getDescription());
                        i.putExtra("copen",model.getOpen());
                        i.putExtra("lat",model.getLat());
                        i.putExtra("lng",model.getLng());
                        Pair[] pairs=new Pair[4];

                        pairs[0]=new Pair<View,String>(holder.cpic,"cpic");
                        pairs[1]=new Pair<View,String>(holder.cname,"cname");
                        pairs[2]=new Pair<View,String>(holder.cprofile,"cprofile");
                        pairs[3]=new Pair<View,String>(holder.copen,"copen");

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            ActivityOptions activityOptions=ActivityOptions.makeSceneTransitionAnimation(HealthUnitsActivity.this,pairs);
                            startActivity(i,activityOptions.toBundle());

                        }
                        startActivity(i);
                    }
                });



            }

            @Override
            public ChatHolder onCreateViewHolder(ViewGroup group, int i) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.custom_layoutfile, group, false);

                return new ChatHolder(view);
            }
        };
        
        recyclerView.setAdapter(adapter);

    }
    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: "+getLocalClassName());
        super.onStart();
        adapter.startListening();
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

    public static class ChatHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView map,cname,cdistrict,copen,cprofile;
        ProgressBar progressBar2;
        ImageView cpic;
        public ChatHolder(View itemView) {
            super(itemView);
            mView=itemView;
            map=mView.findViewById(R.id.map);
            cname=mView.findViewById(R.id.name);
            cdistrict=mView.findViewById(R.id.district);
            copen=mView.findViewById(R.id.open);
            cprofile=mView.findViewById(R.id.description);
            cpic=mView.findViewById(R.id.healthunitphoto);



        }
        public void setName(String name){
            TextView textView=mView.findViewById(R.id.name);
            textView.setText(name);
        }
        public void setLocality(String district){
            TextView textView=mView.findViewById(R.id.district);
            textView.setText(district);
        }
        public void setDescription(String district){
            TextView textView=mView.findViewById(R.id.description);
            textView.setText(district);
        }
        public void setOpen(String open){
            TextView textView=mView.findViewById(R.id.open);
            textView.setText(open);
        }

        public void setPhoto(String photo,Context contex){
            ImageView textView=mView.findViewById(R.id.healthunitphoto);
            Glide.with(contex).load(photo).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    Log.d(TAG, "onLoadFailed: loading photo failed "+e.getMessage());

                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    Log.d(TAG, "onResourceReady: photo loaded ");
                    return false;
                }
            }).into(textView);
        }
        
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: stopped "+getLocalClassName());
        adapter.stopListening();
    }
}
